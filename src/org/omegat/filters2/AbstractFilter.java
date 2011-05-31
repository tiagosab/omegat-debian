/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2000-2006 Keith Godfrey and Maxym Mykhalchuk
               2006 Martin Wunderlich
               2009 Alex Buloichik
               2011 Didier Briel
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

package org.omegat.filters2;

import java.awt.Dialog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.omegat.util.NullBufferedWriter;
import org.omegat.util.OStrings;

/**
 * The base class for all filters (aka file handlers). Each filter should extend this class or one of its
 * decendants.
 * <p>
 * The process how the filter works is the following:
 * <ol>
 * <li>Source text is extracted.
 * <li>Tags are converted into shortcuts and these shortcuts are temporarily stored
 * <li>Source text with shortened tags is sent to OmegaT core
 * <li>Core returns a translation (or the same text if there's no translation)
 * <li>Tags shortcuts are expanded in translated text
 * <li>Translated text is written into the output file
 * </ol>
 * 
 * @author Maxym Mykhalchuk
 * @author Martin Wunderlich
 * @author Alex Buloichik (alex73mail@gmail.com)
 * @author Didier Briel
 */
public abstract class AbstractFilter implements IFilter {

    /**
     * This value represents to the user that the encoding is determined by the filter itself. "In code" the
     * <code>null</code> is used to represent automatic encoding selection.
     */
    public static String ENCODING_AUTO_HUMAN = OStrings.getString("ENCODING_AUTO");

    /** The original filename (with extension). */
    public static final String TFP_FILENAME = "${filename}";
    /** The original filename without extension. */
    public static final String TFP_NAMEONLY = "${nameOnly}";
    /** The original file extension. */
    public static final String TFP_EXTENSION = "${extension}";
    /** "xx_YY", locale code */
    public static final String TFP_TARGET_LOCALE = "${targetLocale}";
    /** "XX-YY", the TMX/XML language code */
    public static final String TFP_TARGET_LANGUAGE = "${targetLanguage}";
    /** language "XX" only */
    public static final String TFP_TARGET_LANG_CODE = "${targetLanguageCode}";
    /** country "YY" only */
    public static final String TFP_TARGET_COUNTRY_CODE = "${targetCountryCode}";
    /** Old spelling of the variable for country "YY" only */
    public static final String TFP_TARGET_COUTRY_CODE = "${targetCoutryCode}";

    /** All target filename patterns. */
    public static final String[] TARGET_FILENAME_PATTERNS = new String[] { TFP_FILENAME, TFP_NAMEONLY,
            TFP_EXTENSION, TFP_TARGET_LOCALE, TFP_TARGET_LANGUAGE, TFP_TARGET_LANG_CODE,
            TFP_TARGET_COUNTRY_CODE };

    /** Callback for parse. */
    protected IParseCallback entryParseCallback;

    /** Callback for translate. */
    protected ITranslateCallback entryTranslateCallback;

    /** Callback for align. */
    protected IAlignCallback entryAlignCallback;

    /** Options for processing time. */
    protected Map<String, String> processOptions;

    /**
     * The default output filename pattern.
     * <p>
     * It is equal to "${filename}", which means that the name of the translated file should be the same as
     * the name of the input file.
     */
    public static String TARGET_DEFAULT = TFP_FILENAME;

    /**
     * Human-readable name of the File Format this filter supports.
     * 
     * @return File format name
     */
    public abstract String getFileFormatName();

    /**
     * The default list of filter instances that this filter class has. One filter class may have different
     * filter instances, different by source file mask, encoding of the source file etc.
     * <p>
     * Note that the user may change the instances freely.
     * 
     * @return Default filter instances
     */
    public abstract Instance[] getDefaultInstances();

    /**
     * Whether source encoding can be varied by the user.
     * <p>
     * True means that OmegaT should handle all the encoding mess.
     * <p>
     * Return false to state that your filter doesn't need encoding management provided by OmegaT, because it
     * either autodetects the encoding based on file contents (like HTML filter does) or the encoding is fixed
     * (like in OpenOffice files).
     * 
     * @return whether source encoding can be changed by the user
     */
    public abstract boolean isSourceEncodingVariable();

    /**
     * Whether target encoding can be varied by the user.
     * <p>
     * True means that OmegaT should handle all the encoding mess.
     * <p>
     * Return false to state that your filter doesn't need encoding management provided by OmegaT, because the
     * encoding is fixed (like in OpenOffice files), or for some other reason.
     * 
     * @return whether target encoding can be changed by the user
     */
    public abstract boolean isTargetEncodingVariable();

    /**
     * Returns whether the file is supported by the filter, given the reader with file's contents. There
     * exists a version of this method that takes file and encoding {@link #isFileSupported(File,String)}. You
     * should override only one of the two.
     * <p>
     * By default returns true, because this method should be overriden only by filters that differentiate
     * input files not by extensions, but by file's content.
     * <p>
     * For example, DocBook files have .xml extension, as possibly many other XML files, so the filter should
     * check a DTD of the document.
     * 
     * @param reader
     *            The reader of the source file
     * @return Does the filter support the file
     */
    protected boolean isFileSupported(BufferedReader reader) {
        return true;
    }

    /**
     * Returns whether the file is supported by the filter, given the file and possible file's encoding (
     * <code>null</code> encoding means autodetect). Default implementation creates a reader and calls
     * {@link #isFileSupported(BufferedReader)}. You should override only one of the two.
     * <p>
     * For example, DocBook files have .xml extension, as possibly many other XML files, so the filter should
     * check a DTD of the document.
     * 
     * @param inFile
     *            Source file.
     * @param fc
     *            Filter context.
     * @return Does the filter support the file.
     */
    public boolean isFileSupported(File inFile, Map<String, String> config, FilterContext fc) {
        BufferedReader reader = null;
        try {
            reader = createReader(inFile, fc.getInEncoding());
            return isFileSupported(reader);
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                // ignore it
            }
        }
    }

    /**
     * Define fuzzy mark prefix for source which will be stored in TM. It's 'fuzzy' by default, but each
     * filter can redefine it.
     * 
     * @return fuzzy mark prefix
     */
    public String getFuzzyMark() {
        return "fuzzy";
    }

    /**
     * Returns the hint displayed while the user edits the filter, and when she adds/edits the instance of
     * this filter. The hint may be any string, preferably in a non-geek language.
     * 
     * @return The hint for editing the filter in a non-geek language.
     */
    public String getHint() {
        return "";
    }

    /**
     * OmegaT calls this to see whether the filter has any options. By default returns false, so filter
     * authors should override this to tell OmegaT core that this filter has options.
     * 
     * @return True if the filter has any options, and false otherwise.
     */
    public boolean hasOptions() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String> changeOptions(Dialog parent, Map<String, String> config) {
        return null;
    }

    /**
     * Creates a reader of an input file.
     * 
     * @param inFile
     *            The source file.
     * @param inEncoding
     *            Encoding of the input file, if the filter supports it. Otherwise null.
     * @return The reader for the source file
     * @throws UnsupportedEncodingException
     *             Thrown if JVM doesn't support the specified inEncoding
     * @throws IOException
     *             If any I/O Error occurs upon reader creation
     */
    protected BufferedReader createReader(File inFile, String inEncoding)
            throws UnsupportedEncodingException, IOException {
        InputStreamReader isr;
        if (inEncoding == null)
            isr = new InputStreamReader(new FileInputStream(inFile));
        else
            isr = new InputStreamReader(new FileInputStream(inFile), inEncoding);
        return new BufferedReader(isr);
    }

    /**
     * Creates a writer of the translated file.
     * 
     * @param outFile
     *            The target file
     * @param outEncoding
     *            Encoding of the target file, if the filter supports it. Otherwise null.
     * @return The writer for the target file
     * @throws UnsupportedEncodingException
     *             Thrown if JVM doesn't support the specified outEncoding
     * @throws IOException
     *             If any I/O Error occurs upon writer creation
     */
    protected BufferedWriter createWriter(File outFile, String outEncoding)
            throws UnsupportedEncodingException, IOException {
        OutputStreamWriter osw;
        if (outEncoding == null)
            osw = new OutputStreamWriter(new FileOutputStream(outFile));
        else
            osw = new OutputStreamWriter(new FileOutputStream(outFile), outEncoding);
        return new BufferedWriter(osw);
    }

    /**
     * Processes a single file given a reader and a writer. Generally this method should read strings from the
     * input reader and write them to the output reader. In order to let OmegaT know what strings are
     * translatable and to get their translation, filter should call {@link #processEntry(String)} method.
     * <p>
     * Note that outFile is never null, even when the project is loading. (in this case it writes no nowhere,
     * but anyway you may use it...)
     * <p>
     * If you need more control over processed files, override {@link #processFile(File,String,File,String)}
     * instead.
     * 
     * @param inFile
     *            Reader of the source file. It's the result of calling {@link #createReader(File,String)}.
     * @param outFile
     *            Writer of the target file on compilation (the result of calling
     *            {@link #createWriter(File, String)}), or a fictive writer to /dev/null.
     * @throws TranslationException
     *             Should be thrown when processed file has any format defects.
     * @throws IOException
     *             In case of any I/O error.
     */
    protected abstract void processFile(BufferedReader inFile, BufferedWriter outFile) throws IOException,
            TranslationException;

    /**
     * Processes a single file given an input and output files (output file may be null while loading files).
     * This method can be used to create a filter that works with the source/target files directly, rather
     * than using BufferedReader/BufferedWriter.
     * <p>
     * Generally this method should read strings from the input reader and write them to the output reader. In
     * order to let OmegaT know what strings are translatable and to get thair translation, filter should call
     * {@link #processEntry(String)} method.
     * <p>
     * If you override this method and do all the processing here, you should simply implement
     * {@link #processFile(BufferedReader,BufferedWriter)} with a stub.
     * <p>
     * Default implementation calls {@link #createReader(File,String)} to create a reader,
     * <code>new BufferedWriter(new StringWriter())</code> to create a writer for <code>null</code> output
     * file, or {@link #createWriter(File,String)} to create a writer if output file is not <code>null</code>;
     * then calls {@link #processFile(BufferedReader,BufferedWriter)} to process source file, and then closes
     * reader and writer.
     * 
     * @param inFile
     *            The source file.
     * @param outFile
     *            The target file.
     * @param fc
     *            Filter context.
     * @returns List of processed files (each element of type {@link File}) or null if the filter can not/did
     *          not process multiple files.
     * 
     * @throws IOException
     *             In case of any I/O error.
     * @throws TranslationException
     *             Should be thrown when processed file has any format defects.
     * 
     * @author Martin Wunderlich
     */
    protected void processFile(File inFile, File outFile, FilterContext fc) throws IOException,
            TranslationException {
        BufferedReader reader = createReader(inFile, fc.getInEncoding());
        try {
            BufferedWriter writer;

            if (outFile != null) {
                writer = createWriter(outFile, fc.getOutEncoding());
            } else {
                writer = new NullBufferedWriter();
            }

            try {
                processFile(reader, writer);
            } finally {
                writer.close();
            }
        } finally {
            reader.close();
        }
    }

    public void parseFile(File inFile, Map<String, String> config, FilterContext fc, IParseCallback callback)
            throws Exception {
        entryParseCallback = callback;
        entryTranslateCallback = null;
        entryAlignCallback = null;
        processOptions = config;

        try {
            processFile(inFile, null, fc);
        } finally {
            entryParseCallback = null;
            processOptions = null;
        }
    }

    public void alignFile(File inFile, File outFile, Map<String, String> config, FilterContext fc,
            IAlignCallback callback) throws Exception {
        entryParseCallback = null;
        entryTranslateCallback = null;
        entryAlignCallback = callback;
        processOptions = config;

        BufferedReader readerIn = createReader(inFile, fc.getInEncoding());
        BufferedReader readerOut = createReader(outFile, fc.getOutEncoding());

        try {
            alignFile(readerIn, readerOut);
        } finally {
            readerIn.close();
            readerOut.close();
        }
    }

    /**
     * Align source file against translated file.
     * 
     * @param sourceFile
     *            source file
     * @param translatedFile
     *            translated file
     */
    protected void alignFile(BufferedReader sourceFile, BufferedReader translatedFile) throws Exception {
    }

    public void translateFile(File inFile, File outFile, Map<String, String> config, FilterContext fc,
            ITranslateCallback callback) throws Exception {
        entryParseCallback = null;
        entryTranslateCallback = callback;
        entryAlignCallback = null;
        processOptions = config;

        try {
            processFile(inFile, outFile, fc);
        } finally {
            entryTranslateCallback = null;
            processOptions = null;
        }
    }

    /**
     * Call this method to:
     * <ul>
     * <li>Instruct OmegaT what source strings are translatable.
     * <li>Get the translation of each source string.
     * </ul>
     * 
     * @param entry
     *            Translatable source string
     * @return Translation of the source string. If there's no translation, returns the source string itself.
     */
    protected final String processEntry(String entry) {
        if (entryParseCallback != null) {
            entryParseCallback.addEntry(null, entry, null, false, null, this);
            return entry;
        } else {
            return entryTranslateCallback.getTranslation(null, entry);
        }
    }

    /**
     * Set both callbacks. Used for child XML filters only.
     * 
     * @param parseCallback
     * @param translateCallback
     */
    public void setCallbacks(IParseCallback parseCallback, ITranslateCallback translateCallback) {
        this.entryParseCallback = parseCallback;
        this.entryTranslateCallback = translateCallback;
    }
}
