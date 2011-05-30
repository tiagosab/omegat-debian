/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool
          with fuzzy matching, translation memory, keyword search,
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2000-2006 Keith Godfrey and Maxym Mykhalchuk
               2007-2008 Didier Briel, Alex Buloichik, Martin Fleurke
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

package org.omegat.filters3.xml.xhtml;

import java.net.URL;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.omegat.filters3.Attribute;
import org.omegat.filters3.Attributes;
import org.omegat.filters3.xml.DefaultXMLDialect;
import org.omegat.util.Log;
import org.xml.sax.InputSource;

/**
 * This class specifies XHTML dialect of XML.
 *
 * @author Maxym Mykhalchuk
 * @author Didier Briel
 * @author Alex Buloichik
 * @author Martin Fleurke
 */
public class XHTMLDialect extends DefaultXMLDialect
{
    private static final Pattern XHTML_PUBLIC_DTD =
            Pattern.compile("-//W3C//DTD XHTML.*");                             // NOI18N

    public XHTMLDialect()
    {
        defineConstraint(CONSTRAINT_PUBLIC_DOCTYPE, XHTML_PUBLIC_DTD);
    }

    private static final Pattern PUBLIC_XHTML =
            Pattern.compile("-//W3C//DTD\\s+XHTML.+");                          // NOI18N

    private static final String DTD =
            "/org/omegat/filters3/xml/xhtml/res/xhtml2-flat.dtd";               // NOI18N

    private Boolean translateValue = false;
    private Boolean translateButtonValue = false;

    /** A regular Expression Pattern to be matched to the strings to be translated.
     * If there is a match, the string should not be translated
     */
    private Pattern skipRegExpPattern;

    /** a map of attribute-name and attribute value pairs that,
     *  if exist in a meta-tag, indicate that the meta-tag should not be translated
     */
    private HashMap<String, String> skipMetaAttributes;

    /**
     * Resolves external entites if child filter needs it. Default
     * implementation returns <code>null</code>.
     */
    public InputSource resolveEntity(String publicId, String systemId) {
        if (publicId != null && PUBLIC_XHTML.matcher(publicId).matches()
                && systemId.endsWith(".dtd")) {
            URL dtdresource = XHTMLDialect.class.getResource(DTD);
            return new InputSource(dtdresource.toExternalForm());
        } else
            return null;
    }

    /**
     * Actually defines the dialect.
     * It cannot be done during creation, because options are not known
     * at that step.
     */
    public void defineDialect(XHTMLOptions options)
    {
        if (options == null)
            options = new XHTMLOptions();

        defineParagraphTags(new String[]
        {
            "html", "head", "title", "body",                                    // NOI18N
            "address", "blockquote", "center", "div",                           // NOI18N
            "h1", "h2", "h3", "h4", "h5",                                       // NOI18N
            "table", "th", "tr", "td",                                          // NOI18N
            "p",                                                                // NOI18N
            "ol", "ul", "li",                                                   // NOI18N
            "dl", "dt", "dd",                                                   // NOI18N
            "form", "textarea", "fieldset", "legend", "label",                  // NOI18N
            "select", "option", "hr"                                            // NOI18N
        });
        // Optional paragraph on BR
        if (options.getParagraphOnBr())
            defineParagraphTag("br");                                           // NOI18N

        defineShortcut("br", "br");                                             // NOI18N

        definePreformatTags(new String[]
        {
            "textarea",                                                         // NOI18N
            "pre",                                                              // NOI18N
        });

        defineIntactTags(new String[]
        {
            "style", "script", "object", "embed",                               // NOI18N
        });

        defineTranslatableAttributes(new String[]{
            "abbr",                                                             // NOI18N
            "alt",                                                              // NOI18N
            "content",                                                          // NOI18N
            "summary",                                                          // NOI18N
            "title",                                                            // NOI18N
        });

        if (options.getTranslateHref())
            defineTranslatableAttribute("href");                                // NOI18N
        if (options.getTranslateSrc())
            defineTranslatableTagAttribute("img", "src");                       // NOI18N
        if (options.getTranslateLang())
            defineTranslatableAttributes(new String[]{
                "lang",                                                         // NOI18N
                "xml:lang",                                                     // NOI18N
            });
        if (options.getTranslateHreflang())
            defineTranslatableAttribute("hreflang");                            // NOI18N
        if ( (this.translateValue = options.getTranslateValue())
           ||(this.translateButtonValue = options.getTranslateButtonValue())
           )
            defineTranslatableTagAttribute("input", "value");                   // NOI18N

        //Prepare matcher
        String skipRegExp = options.getSkipRegExp();
        if (skipRegExp != null && skipRegExp.length()>0)
        {
            try
            {
            this.skipRegExpPattern = Pattern.compile(skipRegExp, Pattern.CASE_INSENSITIVE);
            }
            catch (PatternSyntaxException e)
            {
            Log.log(e);
            }
        }

        // Prepare set of attributes that indicate not to translate a meta-tag
        String skipMetaString = options.getSkipMeta();
        skipMetaAttributes = new HashMap<String, String>();
        String[] skipMetaAttributesStringarray = skipMetaString.split(",");
        for (int i=0; i<skipMetaAttributesStringarray.length; i++) {
            String keyvalue = skipMetaAttributesStringarray[i].trim().toUpperCase();
            skipMetaAttributes.put(keyvalue, "");
        }
    }

    /**
     * Returns for a given attribute of a given tag if the attribute should be 
     * translated with the given other attributes present.
     * If the tagAttribute is returned by getTranslatable(Tag)Attributes(),
     * this function is called to further test the attribute within its context.
     * This allows for example the XHTML filter to not translate the value
     * attribute of an input-element, except when it is a button or submit or
     * reset.
     */
    public Boolean validateTranslatableTagAttribute(String tag, 
                                                    String attribute, 
                                                    Attributes atts) {
        // special case:
        if ("INPUT".equalsIgnoreCase(tag) && attribute.equalsIgnoreCase("value")) {
            // special handling of input tags value attribute.
            if (this.translateValue) 
                return true;
            else if (this.translateButtonValue) {
                // translate the value only for buttons
                for (int i=0; i<atts.size();i++) {
                    Attribute otherAttribute = atts.get(i);
                    if ("type".equalsIgnoreCase(otherAttribute.getName()) &&
                            ( "button".equalsIgnoreCase(otherAttribute.getValue())
                            ||"submit".equalsIgnoreCase(otherAttribute.getValue())
                            ||"reset".equalsIgnoreCase(otherAttribute.getValue())
                            )
                       ) {
                        return super.validateTranslatableTagAttribute(tag, attribute, atts);
                    }
                }
                // don't translate for other input elements
                return false;
            } else
                // should not be possible, because validateTranslatableTagAttribute 
                // is only called when input.value is in translatable(Tag)Attributes.
                return super.validateTranslatableTagAttribute(tag, attribute, atts);
        } else if ("META".equalsIgnoreCase(tag) && "content".equalsIgnoreCase(attribute)) {
            // Special handling of meta-tag: depending on the other attributes
            // the content attribute should or should not be translated.
            // The group of attribute-value pairs indicating non-translation
            // are stored in the configuration
            boolean doSkipMetaTag=false;
            for (int i=0; i<atts.size(); i++) {
                Attribute otherAttribute = atts.get(i);
                String name = otherAttribute.getName();
                String value = otherAttribute.getValue();
                if (name==null || value == null) 
                    continue;
                doSkipMetaTag = checkDoSkipMetaTag(name, value);
                if (doSkipMetaTag) break;
            }
            if (doSkipMetaTag) {
                return false;
            } else {
                return super.validateTranslatableTagAttribute(tag, attribute, atts);
            }
        } else {
            // default:
            return super.validateTranslatableTagAttribute(tag, attribute, atts);
        }
    }

    public Pattern getSkipRegExpPattern() {
        return skipRegExpPattern;
    }

    public HashMap<String, String> getSkipMetaAttributes() {
        return skipMetaAttributes;
    }

    public boolean checkDoSkipMetaTag(String key, String value) {
        return skipMetaAttributes.
                containsKey(key.toUpperCase()+ "=" + value.toUpperCase());
    }
}
