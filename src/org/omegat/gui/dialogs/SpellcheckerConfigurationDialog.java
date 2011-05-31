/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2007 Zoltan Bartko
               2008-2011 Didier Briel
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

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.omegat.core.spellchecker.DictionaryManager;
import org.omegat.util.Language;
import org.omegat.util.OStrings;
import org.omegat.util.Preferences;

/**
 * 
 * @author bartkoz
 * @author Didier Briel
 */
public class SpellcheckerConfigurationDialog extends javax.swing.JDialog {

    private final JFileChooser fileChooser = new JFileChooser();

    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;

    private int returnStatus = RET_CANCEL;

    /**
     * the project's current language
     */
    private Language currentLanguage;

    /**
     * The dictionary manager
     */
    private DictionaryManager dicMan;

    /**
     * the language list model
     */
    private DefaultListModel languageListModel;

    public int getReturnStatus() {
        return returnStatus;
    }

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    /**
     * Creates new form SpellcheckerConfigurationDialog
     */
    public SpellcheckerConfigurationDialog(Frame parent, Language current) {
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

        initComponents();
        this.pack();

        currentLanguage = current;

        languageListModel = new DefaultListModel();

        // initialize things from the preferences
        autoSpellcheckCheckBox.setSelected(Preferences.isPreference(Preferences.ALLOW_AUTO_SPELLCHECKING));
        updateDetailPanel();

        directoryTextField.setText(Preferences.getPreference(Preferences.SPELLCHECKER_DICTIONARY_DIRECTORY));
        updateLanguageList();
    }

    /**
     * Updates the language list based on the directory text field
     */
    public void updateLanguageList() {
        String dirName = directoryTextField.getText();

        // should we do anything?
        if (dirName == null || dirName.equals(""))
            return;

        dicMan = new DictionaryManager(dirName);

        List<String> aList = dicMan.getLocalDictionaryNameList();

        Collections.sort(aList);

        // initialize the language list model
        languageListModel.clear();

        for (String str : aList) {
            languageListModel.addElement(str);
        }

        // see if there is anything at all
        uninstallButton.setEnabled(!aList.isEmpty());

        languageList.setModel(languageListModel);
    }

    /**
     * Updates the state of the detail panel based on the check box state
     */
    private void updateDetailPanel() {
        boolean enabled = autoSpellcheckCheckBox.isSelected();
        detailPanel.setEnabled(enabled);
        contentLabel.setEnabled(enabled);
        directoryChooserButton.setEnabled(enabled);
        directoryLabel.setEnabled(enabled);
        directoryTextField.setEnabled(enabled);
        installButton.setEnabled(enabled);
        uninstallButton.setEnabled(enabled);
        languageScrollPane.setEnabled(enabled);
        languageList.setEnabled(enabled);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel2 = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        autoSpellcheckCheckBox = new javax.swing.JCheckBox();
        detailPanel = new javax.swing.JPanel();
        directoryTextField = new javax.swing.JTextField();
        languageScrollPane = new javax.swing.JScrollPane();
        languageList = new javax.swing.JList();
        contentLabel = new javax.swing.JLabel();
        directoryLabel = new javax.swing.JLabel();
        directoryChooserButton = new javax.swing.JButton();
        installButton = new javax.swing.JButton();
        uninstallButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(OStrings.getString("GUI_SPELLCHECKER_TITLE"));
        org.openide.awt.Mnemonics.setLocalizedText(cancelButton, OStrings.getString("BUTTON_CANCEL"));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(okButton, OStrings.getString("BUTTON_OK"));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
                org.jdesktop.layout.GroupLayout.LEADING).add(
                org.jdesktop.layout.GroupLayout.TRAILING,
                jPanel2Layout.createSequentialGroup().addContainerGap(342, Short.MAX_VALUE).add(okButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(cancelButton)));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
                org.jdesktop.layout.GroupLayout.LEADING).add(
                jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(cancelButton)
                        .add(okButton)));

        org.openide.awt.Mnemonics.setLocalizedText(autoSpellcheckCheckBox,
                OStrings.getString("GUI_SPELLCHECKER_AUTOSPELLCHECKCHECKBOX"));
        autoSpellcheckCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        autoSpellcheckCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        autoSpellcheckCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoSpellcheckCheckBoxActionPerformed(evt);
            }
        });

        detailPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        directoryTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directoryTextFieldActionPerformed(evt);
            }
        });

        languageScrollPane.setViewportView(languageList);

        org.openide.awt.Mnemonics.setLocalizedText(contentLabel,
                OStrings.getString("GUI_SPELLCHECKER_AVAILABLE_LABEL"));

        org.openide.awt.Mnemonics.setLocalizedText(directoryLabel,
                OStrings.getString("GUI_SPELLCHECKER_DICTIONARYLABEL"));

        org.openide.awt.Mnemonics.setLocalizedText(directoryChooserButton,
                OStrings.getString("GUI_SPELLCHECKER_DIRECTORYCHOOSERBUTTON"));
        directoryChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directoryChooserButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(installButton,
                OStrings.getString("GUI_SPELLCHECKER_INSTALLBUTTON"));
        installButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                installButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(uninstallButton,
                OStrings.getString("GUI_SPELLCHECKER_UNINSTALLBUTTON"));
        uninstallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uninstallButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout detailPanelLayout = new org.jdesktop.layout.GroupLayout(detailPanel);
        detailPanel.setLayout(detailPanelLayout);
        detailPanelLayout.setHorizontalGroup(detailPanelLayout.createParallelGroup(
                org.jdesktop.layout.GroupLayout.LEADING).add(
                detailPanelLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .add(detailPanelLayout
                                .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(contentLabel)
                                .add(directoryLabel)
                                .add(detailPanelLayout
                                        .createSequentialGroup()
                                        .add(detailPanelLayout
                                                .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                .add(languageScrollPane,
                                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 349,
                                                        Short.MAX_VALUE)
                                                .add(org.jdesktop.layout.GroupLayout.TRAILING,
                                                        directoryTextField,
                                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 349,
                                                        Short.MAX_VALUE))
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(detailPanelLayout
                                                .createParallelGroup(
                                                        org.jdesktop.layout.GroupLayout.TRAILING, false)
                                                .add(directoryChooserButton,
                                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                .add(installButton,
                                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                .add(uninstallButton,
                                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)))).addContainerGap()));
        detailPanelLayout.setVerticalGroup(detailPanelLayout.createParallelGroup(
                org.jdesktop.layout.GroupLayout.LEADING).add(
                org.jdesktop.layout.GroupLayout.TRAILING,
                detailPanelLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .add(directoryLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(detailPanelLayout
                                .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(directoryChooserButton)
                                .add(directoryTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(contentLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(detailPanelLayout
                                .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(detailPanelLayout.createSequentialGroup().add(installButton)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(uninstallButton))
                                .add(languageScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 123,
                                        Short.MAX_VALUE)).addContainerGap()));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout
                                .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(autoSpellcheckCheckBox)
                                .add(detailPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .add(autoSpellcheckCheckBox)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(detailPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void directoryTextFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_directoryTextFieldActionPerformed
        updateLanguageList();
    }// GEN-LAST:event_directoryTextFieldActionPerformed

    private void directoryChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_directoryChooserButtonActionPerformed
    // open a dialog box to choose the directory
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle(OStrings.getString("GUI_SPELLCHECKER_FILE_CHOOSER_TITLE"));
        int result = fileChooser.showOpenDialog(SpellcheckerConfigurationDialog.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            // we should write the result into the directory text field
            File file = fileChooser.getSelectedFile();
            directoryTextField.setText(file.getAbsolutePath());
        }
        updateLanguageList();
    }// GEN-LAST:event_directoryChooserButtonActionPerformed

    private void installButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_installButtonActionPerformed
        if (dicMan == null) {
            JOptionPane.showMessageDialog(this, OStrings.getString("GUI_SPELLCHECKER_INSTALL_UNABLE"),
                    OStrings.getString("GUI_SPELLCHECKER_INSTALL_UNABLE_TITLE"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        DictionaryInstallerDialog installerDialog;
        try {
            installerDialog = new DictionaryInstallerDialog(this, dicMan);
            installerDialog.setVisible(true);
            updateLanguageList();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.toString(), "error", JOptionPane.ERROR_MESSAGE);
        }
    }// GEN-LAST:event_installButtonActionPerformed

    private void uninstallButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_uninstallButtonActionPerformed
    // any dictionary manager available
        if (dicMan == null)
            return; // this should never happen - just in case it does

        if (currentLanguage != null) {
            Object[] selection = languageList.getSelectedValues();
            for (int i = 0; i < selection.length; i++) {
                String selectedItem = (String) selection[i];
                String selectedLocaleName = selectedItem.substring(0, selectedItem.indexOf(" "));

                if (selectedLocaleName.equals(currentLanguage.getLocaleCode())) {
                    if (JOptionPane.showConfirmDialog(this,
                            OStrings.getString("GUI_SPELLCHECKER_UNINSTALL_CURRENT"),
                            OStrings.getString("GUI_SPELLCHECKER_UNINSTALL_CURRENT_TITLE"),
                            JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
                        return;
                }

                if (!dicMan.uninstallDictionary(selectedLocaleName))
                    JOptionPane.showMessageDialog(this,
                            OStrings.getString("GUI_SPELLCHECKER_UNINSTALL_UNABLE"),
                            OStrings.getString("GUI_SPELLCHECKER_UNINSTALL_UNABLE_TITLE"),
                            JOptionPane.ERROR_MESSAGE);

                languageListModel.remove(languageList.getSelectedIndex());
                uninstallButton.setEnabled(languageListModel.size() > 0);
            }
        }
    }// GEN-LAST:event_uninstallButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }// GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_okButtonActionPerformed
    // save preferences
        Preferences.setPreference(Preferences.ALLOW_AUTO_SPELLCHECKING, autoSpellcheckCheckBox.isSelected());

        Preferences
                .setPreference(Preferences.SPELLCHECKER_DICTIONARY_DIRECTORY, directoryTextField.getText());

        doClose(RET_OK);
    }// GEN-LAST:event_okButtonActionPerformed

    private void autoSpellcheckCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_autoSpellcheckCheckBoxActionPerformed
        updateDetailPanel();
    }// GEN-LAST:event_autoSpellcheckCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox autoSpellcheckCheckBox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel contentLabel;
    private javax.swing.JPanel detailPanel;
    private javax.swing.JButton directoryChooserButton;
    private javax.swing.JLabel directoryLabel;
    private javax.swing.JTextField directoryTextField;
    private javax.swing.JButton installButton;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JList languageList;
    private javax.swing.JScrollPane languageScrollPane;
    private javax.swing.JButton okButton;
    private javax.swing.JButton uninstallButton;
    // End of variables declaration//GEN-END:variables

}
