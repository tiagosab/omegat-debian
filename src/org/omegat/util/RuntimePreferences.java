/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2008 Alex Buloichik
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

package org.omegat.util;

/**
 * Class for store runtime-only preferences, which shouldn't be saved to config
 * dir.
 * 
 * @author Alex Buloichik <alex73mail@gmail.com>
 */
public class RuntimePreferences {

    /** Quiet mode. */
    private static boolean quietMode;

    /** Force use specified config dir. */
    private static String configDir;

    /**
     * Filename to use for creating a TMX that contains all segments in the project.
     */
    private static String pseudoTranslateTMXFile;

    /**
     * Choice of types of translation for all segments in the optional, special 
     * TMX file that contains all segments of the project.
     */
    public enum PSEUDO_TRANSLATE_TYPE {
        EQUAL, EMPTY;
        public static PSEUDO_TRANSLATE_TYPE parse(String s) {
            try {
                return valueOf(s.toUpperCase().replace('-', '_'));
            } catch (Exception ex) {
                // default mode
                return EQUAL;
            }
        }
    };
    /**
     * Type of translation for all segments in the optional, special TMX file 
     * that contains all segments of the project.
     */
    private static PSEUDO_TRANSLATE_TYPE pseudo_translate_type = PSEUDO_TRANSLATE_TYPE.EQUAL; 

    public static boolean isQuietMode() {
        return quietMode;
    }

    public static void setQuietMode(boolean v) {
        quietMode = v;
    }

    public static String getConfigDir() {
        return configDir;
    }

    public static void setConfigDir(String v) {
        configDir = v;
    }

    public static void setPseudoTranslateTMXFile(String filename) {
        pseudoTranslateTMXFile = filename;
    }

    public static String getPseudoTranslateTMXFile() {
        return pseudoTranslateTMXFile;
    }

    public static void setPseudoTranslateType(String type) {
        pseudo_translate_type = PSEUDO_TRANSLATE_TYPE.parse(type);
    }
    
    public static PSEUDO_TRANSLATE_TYPE getPseudoTranslateType() {
        return pseudo_translate_type; 
    }
}