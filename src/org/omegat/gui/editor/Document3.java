/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2009 Alex Buloichik
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

import java.awt.Font;

import javax.swing.event.DocumentEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Position;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * We need to redefine some standard document behavior.
 * 
 * @author Alex Buloichik (alex73mail@gmail.com)
 */
public class Document3 extends DefaultStyledDocument {
    enum ORIENTATION {
        /** Both segments is left aligned. */
        LTR,
        /** Both segments is right aligned. */
        RTL,
        /** Segments have different alignment, depends of language alignment. */
        DIFFER
    };

    protected final EditorController controller;

    /** Position of active translation in text. */
    Position activeTranslationBeginM1, activeTranslationEndP1;

    /**
     * Flag for check internal changes of content, which should be always
     * acceptable.
     */
    protected boolean trustedChangesInProgress = false;

    public Document3(final EditorController controller) {
        this.controller = controller;
        StyleContext styles = (StyleContext) getAttributeContext();
        Style def = styles.getStyle(StyleContext.DEFAULT_STYLE);
        def.addAttribute("FONT_ATTRIBUTE_KEY", new Font("Arial", 0, 18));
    }

    /**
     * Calculate the position of the start of the current translation
     */
    protected int getTranslationStart() {
        return activeTranslationBeginM1.getOffset() + 1;
    }

    /**
     * Calculate the position of the end of the current translation
     */
    protected int getTranslationEnd() {
        return activeTranslationEndP1.getOffset() - 1;
    }

    /**
     * Returns editor's font. Only bold style may be changed.
     */
    public Font getFont(AttributeSet attr) {
        if (StyleConstants.isBold(attr)) {
            return controller.boldFont;
        } else {
            return controller.baseFont;
        }
    }

    /**
     * Check if document is in edit mode, i.e. one of segment activated for
     * edit.
     */
    boolean isEditMode() {
        return activeTranslationBeginM1 != null
                && activeTranslationEndP1 != null;
    }

    /**
     * Stop edit mode, remove info about active translation position.
     */
    void stopEditMode() {
        activeTranslationBeginM1 = null;
        activeTranslationEndP1 = null;
    }

    /**
     * Extract active translation.
     * 
     * @return active translation text
     */
    String extractTranslation() {
        if (!isEditMode()) {
            return null;
        }
        int start = getTranslationStart();
        int end = getTranslationEnd();
        try {
            return getText(start, end - start);
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Set alignment for specified part of text.
     * 
     * @param beginOffset
     *            begin offset
     * @param endOffset
     *            end offset
     * @param isRightAlignment
     *            false - left alignment, true - right alignment
     */
    protected void setAlignment(int beginOffset, int endOffset,
            boolean isRightAlignment) {
        try {
            writeLock();

            DefaultDocumentEvent changes = new DefaultDocumentEvent(
                    beginOffset, endOffset - beginOffset,
                    DocumentEvent.EventType.CHANGE);

            Element root = getDefaultRootElement();
            int parBeg = root.getElementIndex(beginOffset);
            int parEnd = root.getElementIndex(endOffset - 1);
            for (int par = parBeg; par <= parEnd; par++) {
                Element el = root.getElement(par);
                MutableAttributeSet attr = (MutableAttributeSet) el
                        .getAttributes();
                attr.addAttribute(StyleConstants.Alignment,
                        isRightAlignment ? StyleConstants.ALIGN_RIGHT
                                : StyleConstants.ALIGN_LEFT);
            }

            changes.end();
            fireChangedUpdate(changes);
        } finally {
            writeUnlock();
        }
    }
}
