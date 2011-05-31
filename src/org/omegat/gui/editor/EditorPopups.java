/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool
          with fuzzy matching, translation memory, keyword search,
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2009 Didier Briel
               2010 Wildrich Fourie
               2010 Alex Buloichik
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

package org.omegat.gui.editor;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import org.omegat.core.Core;
import org.omegat.core.spellchecker.SpellCheckerMarker;
import org.omegat.util.Log;
import org.omegat.util.OStrings;
import org.omegat.util.gui.UIThreadsUtil;

/**
 * Some standard editor poups.
 * 
 * @author Alex Buloichik (alex73mail@gmail.com)
 */
public class EditorPopups {
    public static void init(EditorController ec) {
        ec.registerPopupMenuConstructors(10, new DefaultPopup());
        ec.registerPopupMenuConstructors(20, new SpellCheckerPopup(ec));
        ec.registerPopupMenuConstructors(30, new GoToSegmentPopup(ec));
    }

    /**
     * create the spell checker popup menu - suggestions for a wrong word, add
     * and ignore. Works only for the active segment, for the translation
     * 
     * @param point
     *            : where should the popup be shown
     */
    public static class SpellCheckerPopup implements IPopupMenuConstructor {
        protected final EditorController ec;

        public SpellCheckerPopup(EditorController ec) {
            this.ec = ec;
        }

        public void addItems(JPopupMenu menu, final JTextComponent comp, int mousepos,
                boolean isInActiveEntry, boolean isInActiveTranslation, SegmentBuilder sb) {
            if (!ec.getSettings().isAutoSpellChecking()) {
                // spellchecker disabled
            }
            if (!isInActiveTranslation) {
                // there is no need to display suggestions
                return;
            }

            try {
                // find the word boundaries
                final int wordStart = EditorUtils.getWordStart(comp, mousepos);
                final int wordEnd = EditorUtils.getWordEnd(comp, mousepos);

                final String word = comp.getText(wordStart, wordEnd - wordStart);

                final AbstractDocument xlDoc = (AbstractDocument) comp.getDocument();

                if (!Core.getSpellChecker().isCorrect(word)) {
                    // get the suggestions and create a menu
                    List<String> suggestions = Core.getSpellChecker().suggest(word);

                    // the suggestions
                    for (final String replacement : suggestions) {
                        JMenuItem item = menu.add(replacement);
                        item.addActionListener(new ActionListener() {
                            // the action: replace the word with the selected
                            // suggestion
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    int pos = comp.getCaretPosition();
                                    xlDoc.replace(wordStart, wordEnd - wordStart, replacement, null);
                                    comp.setCaretPosition(pos);
                                } catch (BadLocationException exc) {
                                    Log.log(exc);
                                }
                            }
                        });
                    }

                    // what if no action is done?
                    if (suggestions.size() == 0) {
                        JMenuItem item = menu.add(OStrings.getString("SC_NO_SUGGESTIONS"));
                        item.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                // just hide the menu
                            }
                        });
                    }

                    menu.add(new JSeparator());

                    // let us ignore it
                    JMenuItem item = menu.add(OStrings.getString("SC_IGNORE_ALL"));
                    item.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            addIgnoreWord(word, wordStart, false);
                        }
                    });

                    // or add it to the dictionary
                    item = menu.add(OStrings.getString("SC_ADD_TO_DICTIONARY"));
                    item.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            addIgnoreWord(word, wordStart, true);
                        }
                    });
                }
            } catch (BadLocationException ex) {
                Log.log(ex);
            }
        }

        /**
         * add a new word to the spell checker or ignore a word
         * 
         * @param word
         *            : the word in question
         * @param offset
         *            : the offset of the word in the editor
         * @param add
         *            : true for add, false for ignore
         */
        protected void addIgnoreWord(final String word, final int offset, final boolean add) {
            UIThreadsUtil.mustBeSwingThread();

            if (add) {
                Core.getSpellChecker().learnWord(word);
            } else {
                Core.getSpellChecker().ignoreWord(word);
            }

            ec.remarkOneMarker(SpellCheckerMarker.class.getName());
        }
    }

    public static class DefaultPopup implements IPopupMenuConstructor {
        /**
         * Creates the Cut, Copy and Paste menu items
         */
        public void addItems(JPopupMenu menu, final JTextComponent comp, int mousepos,
                boolean isInActiveEntry, boolean isInActiveTranslation, SegmentBuilder sb) {
            final String selText = comp.getSelectedText();
            final Clipboard omClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = omClipboard.getContents(this);

            boolean cutEnabled = false;
            boolean copyEnabled = false;
            boolean pasteEnabled = false;

            // Calc enabled/disabled
            if (selText != null && comp.getSelectionStart() <= mousepos && mousepos <= comp.getSelectionEnd()) {
                // only on selected text
                if (isInActiveTranslation) {
                    // cut only in editable zone
                    cutEnabled = true;
                }
                // copy in any place
                copyEnabled = true;
            }
            if (contents != null && isInActiveTranslation) {
                pasteEnabled = true;
            }

            // Cut
            JMenuItem cutContextItem = menu.add(OStrings.getString("CCP_CUT"));
            if (cutEnabled) {
                cutContextItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        comp.cut();
                    }
                });
            } else {
                cutContextItem.setEnabled(false);
            }

            // Copy
            JMenuItem copyContextItem = menu.add(OStrings.getString("CCP_COPY"));
            if (copyEnabled) {
                copyContextItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        comp.copy();
                    }
                });
            } else {
                copyContextItem.setEnabled(false);
            }

            // Paste
            JMenuItem pasteContextItem = menu.add(OStrings.getString("CCP_PASTE"));
            if (pasteEnabled) {
                pasteContextItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        comp.paste();
                    }
                });
            } else {
                pasteContextItem.setEnabled(false);
            }

            menu.addSeparator();
        }
    }

    public static class GoToSegmentPopup implements IPopupMenuConstructor {
        protected final EditorController ec;

        public GoToSegmentPopup(EditorController ec) {
            this.ec = ec;
        }

        /**
         * creates a popup menu for inactive segments - with an item allowing to
         * go to the given segment.
         */
        public void addItems(JPopupMenu menu, final JTextComponent comp, final int mousepos,
                boolean isInActiveEntry, boolean isInActiveTranslation, SegmentBuilder sb) {
            if (isInActiveEntry) {
                return;
            }

            JMenuItem item = menu.add(OStrings.getString("MW_PROMPT_SEG_NR_TITLE"));
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    comp.setCaretPosition(mousepos);
                    ec.goToSegmentAtLocation(comp.getCaretPosition());
                }
            });
        }
    }
}
