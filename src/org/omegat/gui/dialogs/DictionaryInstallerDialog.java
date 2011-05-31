/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2007 - Zoltan Bartko - bartkozoltan@bartkozoltan.com
               2008 Didier Briel
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

package org.omegat.gui.dialogs;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.omegat.core.spellchecker.DictionaryManager;
import org.omegat.util.OStrings;

/**
 * The spellchecker dictionary installer.
 * 
 * @author bartkoz
 * @author Didier Briel
 */
public class DictionaryInstallerDialog extends JDialog {

    /**
     * The dictionary manager in use
     */
    private DictionaryManager dicMan;

    /**
     * the list model
     */
    private DefaultListModel listModel;

    /** Creates new form DictionaryInstallerDialog */
    public DictionaryInstallerDialog(JDialog parent, DictionaryManager dicMan) throws IOException {
        super(parent, true);

        // HP
        // Handle escape key to close the window
        KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
        // END HP

        this.dicMan = dicMan;

        initComponents();

        UiInitComponents();

        List<String> list = dicMan.getInstallableDictionaryNameList();

        listModel = new DefaultListModel();

        for (String str : list) {
            listModel.addElement(str);
        }

        dictionaryList.setModel(listModel);
        dictionaryList.setEnabled(true);

        if (!list.isEmpty()) {
            installButton.setEnabled(true);
            infoTextArea.setText(OStrings.getString("GUI_DICTIONARY_INSTALLER_TEXT_GO"));
        } else {
            installButton.setEnabled(false);
            infoTextArea.setText(OStrings.getString("GUI_DICTIONARY_INSTALLER_TEXT_NOTHING"));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane2 = new javax.swing.JScrollPane();
        dictionaryList = new javax.swing.JList();
        closeButton = new javax.swing.JButton();
        installButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        infoTextArea = new javax.swing.JTextArea();
        listLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(OStrings.getString("GUI_DICTIONARY_INSTALLER_TITLE"));
        jScrollPane2.setViewportView(dictionaryList);

        org.openide.awt.Mnemonics.setLocalizedText(closeButton, OStrings.getString("BUTTON_CLOSE"));
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(installButton,
                OStrings.getString("GUI_DICTIONARY_INSTALLER_INSTALL"));
        installButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                installButtonActionPerformed(evt);
            }
        });

        infoTextArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        infoTextArea.setColumns(20);
        infoTextArea.setEditable(false);
        infoTextArea.setFont(new java.awt.Font("Dialog", 1, 12));
        infoTextArea.setLineWrap(true);
        infoTextArea.setRows(5);
        infoTextArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(infoTextArea);

        org.openide.awt.Mnemonics.setLocalizedText(listLabel,
                OStrings.getString("GUI_DICTIONARY_INSTALLER_AVAILABLE"));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout
                                .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(listLabel)
                                .add(layout
                                        .createSequentialGroup()
                                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 304,
                                                Short.MAX_VALUE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(layout
                                                .createParallelGroup(
                                                        org.jdesktop.layout.GroupLayout.TRAILING, false)
                                                .add(installButton,
                                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                .add(closeButton,
                                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 84,
                                                        Short.MAX_VALUE)))
                                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 394,
                                        Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(9, 9, 9)
                        .add(listLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout
                                .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING,
                                        layout.createSequentialGroup()
                                                .add(installButton)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED,
                                                        125, Short.MAX_VALUE).add(closeButton))
                                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 171,
                                        Short.MAX_VALUE)).addContainerGap()));
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void installButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_installButtonActionPerformed
        Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
        Cursor oldCursor = getCursor();
        setCursor(hourglassCursor);
        Object[] selection = dictionaryList.getSelectedValues();
        for (int i = 0; i < selection.length; i++) {
            // install the respective dictionaries
            String item = (String) selection[i];
            String langCode = (item).substring(0, item.indexOf(" "));
            try {
                dicMan.installRemoteDictionary(langCode);
                ((SpellcheckerConfigurationDialog) this.getParent()).updateLanguageList();
            } catch (MalformedURLException ex) {
                setCursor(oldCursor);
                JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                setCursor(hourglassCursor);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        setCursor(oldCursor);
    }// GEN-LAST:event_installButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closeButtonActionPerformed
        setVisible(false);
        dispose();
    }// GEN-LAST:event_closeButtonActionPerformed

    private void UiInitComponents() {
        infoTextArea.setText(OStrings.getString("GUI_DICTIONARY_INSTALLER_TEXT_WAIT"));
        installButton.setEnabled(false);
        dictionaryList.setEnabled(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JList dictionaryList;
    private javax.swing.JTextArea infoTextArea;
    private javax.swing.JButton installButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel listLabel;
    // End of variables declaration//GEN-END:variables

}
