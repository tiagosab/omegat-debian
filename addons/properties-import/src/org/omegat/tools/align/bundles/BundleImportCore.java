/**************************************************************************
 OmegaT Addon - Import of legacy translations of Java(TM) Resource Bundles
 Copyright (C) 2004-05  Maxym Mykhalchuk
                        mihmax@gmail.com

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

package org.omegat.tools.align.bundles;

import java.io.IOException;
import org.omegat.util.Language;

/**
 * The main static launch point
 *
 * @author  Maxym Mykhalchuk
 */
public class BundleImportCore
{
    
    public static void doImport(String sourcelang, String targetlang, String folder, String tmxfile) throws IOException
    {
        Language source = new Language(sourcelang);
        Language target = new Language(targetlang);
        TMXSaver saver = new TMXSaver(source.getLanguage(), target.getLanguage(), tmxfile);
        FolderScanner scanner = new FolderScanner(folder, target.getLocale(), saver);
        scanner.scan();
        saver.save();
    }
    
}
