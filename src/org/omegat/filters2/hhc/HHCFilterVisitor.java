/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2000-2006 Keith Godfrey and Maxym Mykhalchuk
 Copyright (C) 2006 by Didier Briel
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

package org.omegat.filters2.hhc;

import java.io.BufferedWriter;

import org.htmlparser.Tag;
import org.omegat.filters2.html2.FilterVisitor;

/**
 * The part of HTML filter that actually does the job. This class is called back
 * by HTMLParser (http://sf.net/projects/htmlparser/).
 * 
 * @author Maxym Mykhalchuk
 * @author Didier Briel
 */
class HHCFilterVisitor extends FilterVisitor {
    public HHCFilterVisitor(HHCFilter2 hhcfilter, BufferedWriter bufwriter) {
        super(hhcfilter, bufwriter, null);
    }

    // ///////////////////////////////////////////////////////////////////////
    // Variable declaration
    // ///////////////////////////////////////////////////////////////////////

    /** Do we collect the translatable text now. */
    boolean text = false;

    public void visitTag(Tag tag) {
        if (isParagraphTag(tag) && text)
            endup();

        if ("PARAM".equals(tag.getTagName()) && "Name".equalsIgnoreCase(tag.getAttribute("name"))) {
            maybeTranslateAttribute(tag, "value");
        }
        queuePrefix(tag);
    }

    private boolean isParagraphTag(Tag tag) {
        String tagname = tag.getTagName();
        return tagname.equals("HTML") || tagname.equals("HEAD") || tagname.equals("BODY");
    }

}
