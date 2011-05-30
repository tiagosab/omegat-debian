/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2000-2006 Keith Godfrey and Maxym Mykhalchuk
               2009 Martin Fleurke, Alex Buloichik
               Home page: http://www.omegat.org/
               Support center: http://groups.yahoo.com/group/OmegaT/

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 **************************************************************************/

package org.omegat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.omegat.core.Core;
import org.omegat.core.CoreEvents;
import org.omegat.core.data.ProjectFactory;
import org.omegat.core.data.ProjectProperties;
import org.omegat.core.data.RealProject;
import org.omegat.core.data.StringEntry;
import org.omegat.core.data.TransMemory;
import org.omegat.util.Log;
import org.omegat.util.OConsts;
import org.omegat.util.OStrings;
import org.omegat.util.ProjectFileStorage;
import org.omegat.util.RuntimePreferences;
import org.omegat.util.TMXWriter;
import org.omegat.util.RuntimePreferences.PSEUDO_TRANSLATE_TYPE;

import com.vlsolutions.swing.docking.DockingDesktop;

/**
 * The main OmegaT class, used to launch the program.
 * 
 * @author Keith Godfrey
 * @author Martin Fleurke
 * @author Alex Buloichik
 */
public class Main {
    /** Application execution mode. */
    enum RUN_MODE {
        GUI, CONSOLE_TRANSLATE, CONSOLE_CREATEPSEUDOTRANSLATETMX;
        public static RUN_MODE parse(String s) {
            try {
                return valueOf(s.toUpperCase().replace('-', '_'));
            } catch (Exception ex) {
                // default mode
                return GUI;
            }
        }
    };

    /** Regexp for parse parameters. */
    protected static final Pattern PARAM = Pattern
            .compile("\\-\\-([A-Za-z\\-]+)(=(.+))?");

    /** Project location for load on startup. */
    protected static File projectLocation = null;

    /** Execution command line parameters. */
    protected static final Map<String, String> params = new TreeMap<String, String>();

    /** Execution mode. */
    protected static RUN_MODE runMode = RUN_MODE.GUI;

    public static void main(String[] args) {

        /*
         * Parse command line arguments info map.
         */
        for (String arg : args) {
            Matcher m = PARAM.matcher(arg);
            if (m.matches()) {
                params.put(m.group(1), m.group(3));
            } else {
                if (arg.startsWith("resource-bundle=")) {
                    // backward compatibility
                    params.put("resource-bundle", arg.substring(16));
                } else {
                    File f = new File(arg);
                    if (f.exists() && f.isDirectory()) {
                        projectLocation = f;
                    }
                }
            }
        }

        runMode = RUN_MODE.parse(params.get("mode"));

        String resourceBundle = params.get("resource-bundle");
        if (resourceBundle != null) {
            OStrings.loadBundle(resourceBundle);
        }

        String configDir = params.get("config-dir");
        if (configDir != null) {
            RuntimePreferences.setConfigDir(configDir);
        }

        if (params.containsKey("quiet")) {
            RuntimePreferences.setQuietMode(true);
        }

        if (params.containsKey("pseudotranslatetmx")) {
            RuntimePreferences.setPseudoTranslateTMXFile(params.get("pseudotranslatetmx"));
            RuntimePreferences.setPseudoTranslateType(params.get("pseudotranslatetype"));
        }

        Log.log("\n"
                + // NOI18N
                "==================================================================="
                + // NOI18N
                "\n" + // NOI18N
                OStrings.getDisplayVersion() + // NOI18N
                " (" + new Date() + ") " + // NOI18N
                " Locale " + Locale.getDefault()); // NOI18N

        Log.logRB("LOG_STARTUP_INFO", System.getProperty("java.vendor"), System
                .getProperty("java.version"), System.getProperty("java.home"));

        switch (runMode) {
        case GUI:
            runGUI();
            break;
        case CONSOLE_TRANSLATE:
        case CONSOLE_CREATEPSEUDOTRANSLATETMX:
            runConsoleTranslate(runMode);
            break;
        }
    }

    /**
     * Execute standard GUI.
     */
    protected static void runGUI() {
        Log.log("Docking Framework version: "
                + DockingDesktop.getDockingFrameworkVersion());
        Log.log("");
        try {
            // Workaround for JDK bug 6389282 (OmegaT bug bug 1555809)
            // it should be called before setLookAndFeel() for GTK LookandFeel
            // Contributed by Masaki Katakai (SF: katakai)
            UIManager.getInstalledLookAndFeels();

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // MacOSX-specific
            System.setProperty("apple.laf.useScreenMenuBar", "true"); // NOI18N
            System
                    .setProperty(
                            "com.apple.mrj.application.apple.menu.about.name",
                            "OmegaT"); // NOI18N
        } catch (Exception e) {
            // do nothing
            Log.logErrorRB("MAIN_ERROR_CANT_INIT_OSLF");
        }

        try {
            Core.initializeGUI(params);
        } catch (Throwable ex) {
            showError(ex);
        }

        CoreEvents.fireApplicationStartup();

        if (projectLocation != null) {
            try {
                ProjectProperties props = ProjectFileStorage
                        .loadProjectProperties(projectLocation);
                ProjectFactory.loadProject(props);
            } catch (Exception ex) {
                showError(ex);
            }
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // setVisible can't be executed directly, because we need to
                // call all application startup listeners for initialize UI
                Core.getMainWindow().getApplicationFrame().setVisible(true);
            }
        });
    }

    /**
     * Execute in console mode for translate.
     */
    protected static void runConsoleTranslate(RUN_MODE runMode) {
        Log.log("Console mode");
        Log.log("");

        System.out.println("Initializing");
        try {
            Core.initializeConsole(params);
        } catch (Throwable ex) {
            showError(ex);
        }
        try {
            System.out.println("Loading Project");

            // check if project okay
            ProjectProperties projectProperties = null;
            try {
                projectProperties = ProjectFileStorage
                        .loadProjectProperties(projectLocation);
                if (!projectProperties.verifyProject()) {
                    System.out.println("The project cannot be verified");
                    System.exit(1);
                }
            } catch (Exception ex) {
                Log.logErrorRB(ex, "PP_ERROR_UNABLE_TO_READ_PROJECT_FILE");
                System.out.println(OStrings.getString
                        ("PP_ERROR_UNABLE_TO_READ_PROJECT_FILE"));
                System.exit(1);
            }

            RealProject p = new RealProject(projectProperties, false);
            Core.setProject(p);

            switch (runMode) {
            case CONSOLE_TRANSLATE:
                System.out.println("Translating Project");
                p.compileProject();
                break;
            case CONSOLE_CREATEPSEUDOTRANSLATETMX:
                System.out.println("Translating Project");

                ProjectProperties m_config = p.getProjectProperties();
                List<StringEntry> m_strEntryList = p.getUniqueEntries();
                ArrayList<TransMemory> m_orphanedList = new ArrayList<TransMemory>();
                String pseudoTranslateTMXFilename = RuntimePreferences.getPseudoTranslateTMXFile();
                PSEUDO_TRANSLATE_TYPE pseudoTranslateType = RuntimePreferences.getPseudoTranslateType();

                String fname;
                if (pseudoTranslateTMXFilename != null && pseudoTranslateTMXFilename.length()>0) {
                    if (!pseudoTranslateTMXFilename.endsWith(OConsts.TMX_EXTENSION)) {
                        fname = pseudoTranslateTMXFilename+"."+OConsts.TMX_EXTENSION;
                    } else {
                        fname = pseudoTranslateTMXFilename;
                    }
                    
                } else {
                    fname="";
                }
                try {
                    TMXWriter.buildTMXFile(fname, false, false, true, m_config, m_strEntryList, m_orphanedList, true, pseudoTranslateType);
                } catch (IOException e) {
                    Log.logErrorRB("CT_ERROR_CREATING_TMX");
                    Log.log(e);
                    throw new IOException(OStrings.getString("CT_ERROR_CREATING_TMX") +
                            "\n" +                                                      // NOI18N
                            e.getMessage());
                }
                break;
            }

            System.out.println("Finished");
        } catch (Exception e) {
            System.err.println("An error has occured: " + e.toString());
            System.exit(1);
        }
    }

    public static void showError(Throwable ex) {
        switch (runMode) {
        case GUI:
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex
                    .getMessage(),
                    OStrings.getString("STARTUP_ERRORBOX_TITLE"),
                    JOptionPane.ERROR_MESSAGE);
            break;
        case CONSOLE_TRANSLATE:
            System.err.println(ex.getMessage());
            break;
        }
        System.exit(1);
    }
}
