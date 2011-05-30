/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2007 Zoltan Bartko 
               2009 Didier Briel
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

package org.omegat.core.spellchecker;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import org.omegat.util.OConsts;
import org.omegat.util.PatternConsts;
import org.omegat.util.StaticUtils;

/**
 * Dictionary manager. Spell checking dictionaries' utility functions.
 * @author Zoltan Bartko - bartkozoltan@bartkozoltan.com
 * @author Didier Briel
 */
public class DictionaryManager {
    
    /** the directory string */
    private File dir;
    
    /**
     * Creates a new instance of DictionaryManager.
     * @param dirName : the directory where the spell checking dictionary files 
     * (*.(aff|dic) are available locally
     */
    public DictionaryManager(String dirName) {
        dir = new File(dirName);
    }
    
    /**
     * returns the dictionary directory
     */
    public String getDirectory() {
        return dir.getAbsolutePath();
    }
    
    /** return the file name only - e.g. until the first dot */
    private String getFileNameOnly(String filename) {
        int position;
        if ((position = filename.indexOf(".")) != -1)
            return filename.substring(0, position);
        else
            return null;
    }
    
    /**
     * returns a list of full names of dictionaries from a dictionary code list
     */
    public List<String> getDictionaryNameList(List<String> aList) {
        List<String> result = new ArrayList<String>();
        
        for (String dic : aList) {
            String parts[] = dic.split("_");
            Locale locale;
            if (parts.length == 1)
                locale = new Locale(parts[0]);
            else
                locale = new Locale(parts[0], parts[1]);
            result.add(dic + " - " + locale.getDisplayName());    
        }        
        
        return result;
    }
    
    /**
     * return a list of full names of the local dictionaries
     */
    public List<String> getLocalDictionaryNameList() {
        return getDictionaryNameList(getLocalDictionaryCodeList());
    }
    
    /**
     * returns a list of available dictionaries in the xx_YY form
     */
    public List<String> getLocalDictionaryCodeList() {
        List<String> result = new ArrayList<String>();
        
        String[] affixFiles;
        String[] dictionaryFiles;
        
        // get all affix files
        affixFiles = dir.list(new DictionaryFileNameFilter(
                OConsts.SC_AFFIX_EXTENSION));
        
        // get all dictionary files
        dictionaryFiles = dir.list(new DictionaryFileNameFilter(
                OConsts.SC_DICTIONARY_EXTENSION));
        
        // match them
        if (affixFiles != null && dictionaryFiles != null) {
            for (int i = 0; i < affixFiles.length; i++) {
                boolean match = false;

                // get the affix file name
                String affixName = getFileNameOnly(affixFiles[i]);
                if (affixName == null || affixName.equals(""))
                    continue;

                // cycle through the dictionary names
                for (int j = 0; j < dictionaryFiles.length; j++) {
                    // get the dic file name
                    String dicName = getFileNameOnly(dictionaryFiles[j]);
                    if (dicName == null || dicName.equals(""))
                        continue;

                    if (affixName.equals(dicName)) {
                        match = true;
                        break;
                    }
                }

                if (match)
                    result.add(affixName);
            }
        }
        
        return result;
    }
    
    /**
     * Uninstall (delete) a given dictionary from the dictionary directory
     * @param lang : the language code (xx_YY) of the dictionary to be deleted
     * @returns true upon success, otherwise false
     */
    public boolean uninstallDictionary(String lang) {
        if (lang == null || lang.equals(""))
            return false;
        
        String base = getDirectory() + File.separator + lang;
        
        File affFile = new File(base + OConsts.SC_AFFIX_EXTENSION);
        
        if (!affFile.delete())
            return false;
        
        File dicFile = new File(base + OConsts.SC_DICTIONARY_EXTENSION);
        
        if (!dicFile.delete())
            return false;
        
        return true;
    }
    
    /**
     * return a list of names of installable dictionaries 
     * (e.g. en_US - english (USA))
     */
    public List<String> getInstallableDictionaryNameList() throws IOException {
        return getDictionaryNameList(getInstallableDictionaryCodeList());
    }
    
    /**
     * returns a list of codes (xx_YY) of installable dictionaries
     */
    public List<String> getInstallableDictionaryCodeList() throws IOException {
        List<String> localDicList = getLocalDictionaryCodeList();
        
        List<String> remoteDicList = getRemoteDictionaryCodeList();
        
        List<String> result = new ArrayList<String>();
        
        // compare the two lists
        for (String dicCode : remoteDicList) {
            if (!localDicList.contains(dicCode))
                result.add(dicCode);
        }
        
        return result;
    }

    /**
     * downloads the list of available dictionaries from the net
     */
    private List<String> getRemoteDictionaryCodeList() throws IOException {
        List<String> result = new ArrayList<String>();
        
        // download the file
        String htmlfile = StaticUtils.downloadFileToString(
                OConsts.REMOTE_SC_DICTIONARY_LIST_LOCATION);
        
        // build a list of available language codes
        Matcher matcher = 
                PatternConsts.DICTIONARY_ZIP.matcher(htmlfile);
        
        while (matcher.find()) {
            // strip the quotes from the ends
            String match = matcher.group();
            int dotPosition = match.indexOf(".");
            // delete the '.zip"' 
            result.add(match.substring(1,dotPosition));
        }
        
        return result;
    }
    
    /**
     * installs a remote dictionary by downloading the corresponding zip file 
     * from the net and by installing the aff and dic file to the dictionary 
     * directory.
     * @param langCode : the language code (xx_YY)
     */
    public void installRemoteDictionary(String langCode) 
    throws MalformedURLException, IOException {
        // download the package in question to the disk to a temporary location
        String from = OConsts.REMOTE_SC_DICTIONARY_LIST_LOCATION + "/" + 
                langCode + ".zip";
        
        // TODO: replace this with something meaningful
        File tempFile = File.createTempFile(langCode, ".zip");
        
        String to = tempFile.getAbsolutePath();
        
        StaticUtils.downloadFileToDisk(from, to);
        
        // Dirty hack for the French dictionary. Since it is named
        // fr_FR_1-3-2.zip, we remove the "_1-3-2" portion
        // [ 2138846 ] French dictionary cannot be downloaded and installed
        int pos;
        if ( (pos = langCode.indexOf("_1-3-2", 0)) != -1 ){
            langCode = langCode.substring(0, pos);
        }

        List<String> filenames = new ArrayList<String>();
        
        filenames.add(langCode + OConsts.SC_AFFIX_EXTENSION);
        filenames.add(langCode + OConsts.SC_DICTIONARY_EXTENSION);
        
        dir.mkdirs();
        StaticUtils.extractFileFromJar(to,filenames,dir.getAbsolutePath());
    }
}
