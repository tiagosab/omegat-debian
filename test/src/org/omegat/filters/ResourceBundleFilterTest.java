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

package org.omegat.filters;

import org.omegat.filters2.IAlignCallback;
import org.omegat.filters2.IFilter;
import org.omegat.filters2.text.bundles.ResourceBundleFilter;

public class ResourceBundleFilterTest extends TestFilterBase {
    public void testParse() throws Exception {
        parse(new ResourceBundleFilter(),
                "test/data/filters/resourceBundle/file-ResourceBundleFilter.properties");
    }

    public void testTranslate() throws Exception {
        translateText(new ResourceBundleFilter(),
                "test/data/filters/resourceBundle/file-ResourceBundleFilter.properties");
    }

    public void testAlign() throws Exception {
        final AlignResult ar = new AlignResult();
        align(new ResourceBundleFilter(), "resourceBundle/file-ResourceBundleFilter.properties",
                "resourceBundle/file-ResourceBundleFilter_be.properties", new IAlignCallback() {
                    public void addTranslation(String id, String source, String translation, boolean isFuzzy,
                            String comment, IFilter filter) {
                        ar.found = id.equals("ID") && source.equals("Value") && translation.equals("test");
                    }
                });
        assertTrue(ar.found);
    }

    public static class AlignResult {
        boolean found = false;
    }
}
