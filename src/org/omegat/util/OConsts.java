/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2000-2006 Keith Godfrey and Maxym Mykhalchuk
               2007 - Zoltan Bartko - bartkozoltan@bartkozoltan.com
               Home page: http://www.omegat.org/omegat/omegat.html
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

import java.io.File;

/**
 * OmegaT-wide Constants.
 * <p>
 * // TODO
 * Note: Some constants that are used only in a single class,
 * or are more appropriate in another class (e.g. preference names)
 * are moved in appropriate class definitions.
 *
 * @author Keith Godfrey
 * @author Maxym Mykhalchuk
 * @author Zoltan Bartko
 */
public class OConsts
{
    
    // project file consts
    /** Project Filename */
    public static final String FILE_PROJECT	= "omegat.project";                 // NOI18N
    /** Project Version */
    public static final String PROJ_CUR_VERSION = "1.0";						// NOI18N
    
    public static final String TMX_EXTENSION	= ".tmx";							// NOI18N
    public static final String OMEGAT_TMX       = "-omegat";                    // NOI18N
    public static final String LEVEL1_TMX       = "-level1";                    // NOI18N
    public static final String LEVEL2_TMX       = "-level2";                    // NOI18N
    
    public static final String TMW_EXTENSION	= ".wf";	// for wordfast			// NOI18N
    
    // help
    public static final String HELP_HOME          = "index.html";            // NOI18N
    public static final String HELP_INSTANT_START = "instantStartGuideNoTOC.html";// NOI18N
    public static final String HELP_DIR           = "docs";                   // NOI18N
    public static final String HELP_LANG_INDEX    = "languageIndex.html";                  // NOI18N
    
    // status file consts
    public static final String STATUS_EXTENSION	= "project_save.tmx";				// NOI18N
    public static final String STATUS_RECOVER_EXTENSION	= ".recover";				// NOI18N
    public static final String BACKUP_EXTENSION	= ".backup";						// NOI18N
    
    /** Project subfolder for source files default name. */
    public static final String DEFAULT_SOURCE       = "source";                 // NOI18N
    /** Project subfolder for translated files default name. */
    public static final String DEFAULT_TARGET       = "target";                 // NOI18N
    /** Project subfolder for glossaries default name. */
    public static final String DEFAULT_GLOSSARY     = "glossary";               // NOI18N
    /** Project subfolder for legacy translation memories default name. */
    public static final String DEFAULT_TM           = "tm";                     // NOI18N
    /** Project subfolder for project's translation memory. */
    public static final String DEFAULT_INTERNAL     = "omegat";                 // NOI18N
    
    /** A marker that tells OmegaT that project's subfolder has default location. */
    public static final String DEFAULT_FOLDER_MARKER    = "__DEFAULT__";		// NOI18N

    /** The name of the file with project statistics: segments, words, chars count. */
    public static final String STATS_FILENAME   = "project_stats.txt";          // NOI18N
    
    /** The name of the file with the ignored words: one ignored word per line */
    public static final String IGNORED_WORD_LIST_FILE_NAME = "ignored_words.txt";   // NOI18N
    
    /** The name of the file with the correct (learned) words: one word per line */
    public static final String LEARNED_WORD_LIST_FILE_NAME = "learned_words.txt";   // NOI18N
    
    /** The name of the spell checking library */
    public static final String SPELLCHECKER_LIBRARY_NAME = "hunspell";  // NOI18N
    
    /** the native library directory */
    public static final String NATIVE_LIBRARY_DIR = "native";   // NOI18N
    
    /** affix file extension */
    public static final String SC_AFFIX_EXTENSION = ".aff";     // NOI18N
    
    /** dictionary file extension */
    public static final String SC_DICTIONARY_EXTENSION = ".dic";    // NOI18N
    
    /** The smallest threshold to detect a fuzzy match string */
    public static final int    FUZZY_MATCH_THRESHOLD    = 30;
    
    public static final int    ST_MAX_SEARCH_RESULTS    = 1000;
    
    public static final String TF_SRC_FONT_NAME		= "source_font";				// NOI18N
    public static final String TF_SRC_FONT_SIZE		= "source_font_size";			// NOI18N
    public static final String TF_FONT_DEFAULT		= "Dialog";					    // NOI18N
    public static final int TF_FONT_SIZE_DEFAULT	= 12;
    
    public static final String XB_COMMENT_SHORTCUT	= "!comment";					// NOI18N
    
    /** Number of fuzzy matches to display */
    public static final int MAX_NEAR_STRINGS            = 5;
    /** Number of fuzzy matches to store */
    public static final int MAX_STORED_NEAR_STRINGS	= 50;
    
    /**
     * The limit of bytes that AbstractFilter.isFileSupported may read.
     * 8k (8192 bytes) for now, as this is the default buffer size for BufferedReader.
     */
    public static final int READ_AHEAD_LIMIT = 8192;
    
    /**
     * The name of the OmegaT Jar file.
     * It is used to calculate the installation directory.
     */
    public static final String APPLICATION_JAR = "OmegaT.jar";                  // NOI18N
    
    /**
     * Application debug classpath.
     * It is used to calculate the installation directory
     * (in case of debugging -- the sources directory).
     */
    public static final String DEBUG_CLASSPATH = "build"+File.separator+"classes";           // NOI18N
    
    /** Encoding: "UTF-8". */
    public static final String UTF8 = "UTF-8";                                  // NOI18N
    
    public static final String REMOTE_SC_DICTIONARY_LIST_LOCATION = 
            "http://ftp.services.openoffice.org/pub/OpenOffice.org/contrib/dictionaries/";  // NOI18N
    
    public static final String segmentStartString = 
            StaticUtils.rtrim(OStrings.getSegmentStartMarker());
    public static final String segmentStartStringFull = OStrings.getSegmentStartMarker();
    public static final String segmentEndString = 
            StaticUtils.ltrim(OStrings.getSegmentEndMarker());
    public static final String segmentEndStringFull = OStrings.getSegmentEndMarker();
    
}
