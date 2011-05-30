/**************************************************************************
 OmegaT - Java based Computer Assisted Translation (CAT) tool
 Copyright (C) 2002-2005  Keith Godfrey et al
                          keithgodfrey@users.sourceforge.net
                          907.223.2039

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

package org.omegat.tools.tmx.sentseg;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import org.omegat.core.threads.CommandThread;
import org.omegat.gui.OmegaTFileChooser;
import org.omegat.gui.main.MainWindow;
import org.omegat.gui.segmentation.SegmentationCustomizer;
import org.omegat.util.Language;
import org.omegat.util.StaticUtils;

/**
 * The dialog allowing to select a TMX file the user wants
 * to segment in sentences.
 *
 * @author Maxym Mykhalchuk
 */
public class SentSeg extends javax.swing.JFrame
{
    
    public static void main(String args[])
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.setProperty("apple.laf.useScreenMenuBar", "true");           // NOI18N
        }
        catch (Exception e)
        {
            // do nothing
            StaticUtils.log("Cannot Init Look and Feel");
        }
        
        MainWindow mw = new MainWindow();
        CommandThread.core = new CommandThread(mw);
        
        new SentSeg().setVisible(true);
    }
    
    
    /** Creates new form SentSeg */
    public SentSeg()
    {
        initComponents();
        initComponentsSomeMore();
        invalidate();
        pack();
    }

    /** User have already changed Target TMX field */
    private boolean targetChanged = false;
    
    /** User changed Source TMX Field */
    private void tmxFieldUpdated()
    {
        if( !targetChanged )
        {
            String tmx = tmxTextField.getText();
            String tmxl = tmx.toLowerCase();
            if( tmxl.endsWith(".tmx") )                                         // NOI18N
                tmx = tmx.substring(0, tmx.length()-4);
            String saved = tmx + "_SentSeg.tmx";                                // NOI18N
            saveTmxTextField.setText(saved);
            targetChanged = false;
        }
    }

    /** Additional UI init, not possible using NetBeans Form Editor */
    private void initComponentsSomeMore()
    {
        tmxTextField.getDocument().addUndoableEditListener(new UndoableEditListener()
        {
            public void undoableEditHappened(UndoableEditEvent e)
            {
                tmxFieldUpdated();
            }
        });
        
        saveTmxTextField.getDocument().addUndoableEditListener(new UndoableEditListener()
        {
            public void undoableEditHappened(UndoableEditEvent e)
            {
                targetChanged = true;
            }
        });
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        hintTextArea = new javax.swing.JTextArea();
        selectTmxLabel = new javax.swing.JLabel();
        tmxTextField = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        saveTmxLabel = new javax.swing.JLabel();
        saveTmxTextField = new javax.swing.JTextField();
        browseSaveButton = new javax.swing.JButton();
        buttonPanel = new javax.swing.JPanel();
        rulesPanel = new javax.swing.JPanel();
        rulesButton = new javax.swing.JButton();
        internalButtonsPanel = new javax.swing.JPanel();
        segmentButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        resultsTextArea = new javax.swing.JTextArea();
        srcLangLabel = new javax.swing.JLabel();
        tarLangLabel = new javax.swing.JLabel();
        srcLang = new javax.swing.JComboBox();
        tarLang = new javax.swing.JComboBox();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sentence Segment TMX File");
        hintTextArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        hintTextArea.setEditable(false);
        hintTextArea.setFont(new JLabel().getFont());
        hintTextArea.setLineWrap(true);
        hintTextArea.setText("Here you may sentence segment your TMX file that was created in older versions of OmegaT, or in projects with sentence segmenting turned off.\nNote that the number of sentences in source and translated units of the same segment must be the same for the function to work. Segments with mismatching number of source and translated sentences will not be resegmented.");
        hintTextArea.setWrapStyleWord(true);
        hintTextArea.setFocusable(false);
        hintTextArea.setPreferredSize(new java.awt.Dimension(400, 100));
        hintTextArea.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 5);
        getContentPane().add(hintTextArea, gridBagConstraints);

        selectTmxLabel.setLabelFor(tmxTextField);
        org.openide.awt.Mnemonics.setLocalizedText(selectTmxLabel, "Select a &TMX file to segment:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(selectTmxLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(tmxTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(browseButton, "&Browse...");
        browseButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                browseButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 5);
        getContentPane().add(browseButton, gridBagConstraints);

        saveTmxLabel.setLabelFor(saveTmxTextField);
        org.openide.awt.Mnemonics.setLocalizedText(saveTmxLabel, "Save a &new TMX file as:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(saveTmxLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(saveTmxTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(browseSaveButton, "B&rowse...");
        browseSaveButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                browseSaveButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 5);
        getContentPane().add(browseSaveButton, gridBagConstraints);

        buttonPanel.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(rulesButton, "&Segmentation Rules...");
        rulesButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                rulesButtonActionPerformed(evt);
            }
        });

        rulesPanel.add(rulesButton);

        buttonPanel.add(rulesPanel, java.awt.BorderLayout.WEST);

        internalButtonsPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        org.openide.awt.Mnemonics.setLocalizedText(segmentButton, "&Segment");
        segmentButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                segmentButtonActionPerformed(evt);
            }
        });

        internalButtonsPanel.add(segmentButton);

        org.openide.awt.Mnemonics.setLocalizedText(cancelButton, "&Close");
        cancelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cancelButtonActionPerformed(evt);
            }
        });

        internalButtonsPanel.add(cancelButton);

        buttonPanel.add(internalButtonsPanel, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 3, 0);
        getContentPane().add(buttonPanel, gridBagConstraints);

        resultsTextArea.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        resultsTextArea.setEditable(false);
        resultsTextArea.setFont(new JLabel().getFont());
        resultsTextArea.setLineWrap(true);
        resultsTextArea.setText("\n\n\n\n");
        resultsTextArea.setWrapStyleWord(true);
        resultsTextArea.setFocusable(false);
        resultsTextArea.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 5);
        getContentPane().add(resultsTextArea, gridBagConstraints);

        srcLangLabel.setLabelFor(srcLang);
        org.openide.awt.Mnemonics.setLocalizedText(srcLangLabel, "So&urce Language:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(srcLangLabel, gridBagConstraints);

        tarLangLabel.setLabelFor(tarLang);
        org.openide.awt.Mnemonics.setLocalizedText(tarLangLabel, "&Target Language:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(tarLangLabel, gridBagConstraints);

        srcLang.setModel(new DefaultComboBoxModel(Language.LANGUAGES));
        srcLang.setSelectedItem(new Language("en-US"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(srcLang, gridBagConstraints);

        tarLang.setModel(new DefaultComboBoxModel(Language.LANGUAGES));
        tarLang.setSelectedItem(new Language("en-GB"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(tarLang, gridBagConstraints);

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }
    // </editor-fold>//GEN-END:initComponents

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_browseButtonActionPerformed
    {//GEN-HEADEREND:event_browseButtonActionPerformed
        JFileChooser ch = new OmegaTFileChooser(tmxTextField.getText());
        if( JFileChooser.APPROVE_OPTION==ch.showOpenDialog(getParent()) )
        {
            tmxTextField.setText(ch.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_browseButtonActionPerformed

    private void browseSaveButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_browseSaveButtonActionPerformed
    {//GEN-HEADEREND:event_browseSaveButtonActionPerformed
        JFileChooser ch = new OmegaTFileChooser(saveTmxTextField.getText());
        if( JFileChooser.APPROVE_OPTION==ch.showSaveDialog(getParent()) )
        {
            saveTmxTextField.setText(ch.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_browseSaveButtonActionPerformed

    private void rulesButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rulesButtonActionPerformed
    {//GEN-HEADEREND:event_rulesButtonActionPerformed
        new SegmentationCustomizer(this).setVisible(true);
    }//GEN-LAST:event_rulesButtonActionPerformed

    private void segmentButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_segmentButtonActionPerformed
    {//GEN-HEADEREND:event_segmentButtonActionPerformed
        TMXResegmenter resegmenter = new TMXResegmenter(
                tmxTextField.getText(),
                saveTmxTextField.getText(),
                srcLang.getSelectedItem(),
                tarLang.getSelectedItem());
        try
        {
            resegmenter.resegment();
            StringBuffer res = new StringBuffer();
            res.append("Sentence segmentation complete.\n");
            res.append("Segments processed: " + resegmenter.getSourceSegmentsNumber()+"\n");
            res.append("Segments written: " + resegmenter.getTargetSegmentsNumber()+"\n");
            res.append("Segments with different number of sentences in source and translation: " + resegmenter.getMissegmentedSegmentsNumber());
            resultsTextArea.setText(res.toString());
        }
        catch( Exception e )
        {
            String error = "Sentence Segmenting of TMX '"+tmxTextField.getText()+"' failed. " +
                    "\n" + e.getLocalizedMessage() +
                    "\nDetails in 'log.txt'.";
            resultsTextArea.setText(error);
            StaticUtils.log(error);
            e.printStackTrace(StaticUtils.getLogStream());
        }
    }//GEN-LAST:event_segmentButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
    {//GEN-HEADEREND:event_cancelButtonActionPerformed
        setVisible(false);
        System.exit(0);
    }//GEN-LAST:event_cancelButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JButton browseSaveButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextArea hintTextArea;
    private javax.swing.JPanel internalButtonsPanel;
    private javax.swing.JTextArea resultsTextArea;
    private javax.swing.JButton rulesButton;
    private javax.swing.JPanel rulesPanel;
    private javax.swing.JLabel saveTmxLabel;
    private javax.swing.JTextField saveTmxTextField;
    private javax.swing.JButton segmentButton;
    private javax.swing.JLabel selectTmxLabel;
    private javax.swing.JComboBox srcLang;
    private javax.swing.JLabel srcLangLabel;
    private javax.swing.JComboBox tarLang;
    private javax.swing.JLabel tarLangLabel;
    private javax.swing.JTextField tmxTextField;
    // End of variables declaration//GEN-END:variables
    
}
