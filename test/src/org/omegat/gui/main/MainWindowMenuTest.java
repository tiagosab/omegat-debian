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

package org.omegat.gui.main;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.omegat.core.TestCore;

/**
 * @author Alex Buloichik
 */
public class MainWindowMenuTest extends TestCore {
    /**
     * Check MainWindow for all menu items action handlers exist.
     * 
     * @throws Exception
     */
    public void testMenuActions() throws Exception {
        int count = 0;

        Map<String, Method> existsMethods = new HashMap<String, Method>();

        for (Method m : MainWindowMenuHandler.class.getDeclaredMethods()) {
            if (Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers())) {
                if (m.getParameterTypes().length == 0) {
                    existsMethods.put(m.getName(), m);
                }
            }
        }

        for (Field f : MainWindowMenu.class.getDeclaredFields()) {
            if (JMenuItem.class.isAssignableFrom(f.getType()) && f.getType() != JMenu.class) {
                count++;
                String actionMethodName = f.getName() + "ActionPerformed";
                Method m = MainWindowMenuHandler.class.getMethod(actionMethodName);
                assertNotNull("Action method not defined for " + f.getName(), m);
                assertNotNull(existsMethods.remove(actionMethodName));
            } else {
                System.out.println("Action not need for " + f);
            }
        }
        assertTrue("menu items not found", count > 30);
        assertTrue("There is action handlers in MainWindow which doesn't used in menu: " + existsMethods.keySet(),
                existsMethods.isEmpty());
    }
}
