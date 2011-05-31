//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.22 at 05:32:35 PM EET 
//


package gen.core.tbx;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}titleStmt" minOccurs="0"/>
 *         &lt;element ref="{}publicationStmt" minOccurs="0"/>
 *         &lt;element ref="{}sourceDesc" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "titleStmt",
    "publicationStmt",
    "sourceDesc"
})
@XmlRootElement(name = "fileDesc")
public class FileDesc {

    protected TitleStmt titleStmt;
    protected PublicationStmt publicationStmt;
    @XmlElement(required = true)
    protected List<SourceDesc> sourceDesc;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the titleStmt property.
     * 
     * @return
     *     possible object is
     *     {@link TitleStmt }
     *     
     */
    public TitleStmt getTitleStmt() {
        return titleStmt;
    }

    /**
     * Sets the value of the titleStmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link TitleStmt }
     *     
     */
    public void setTitleStmt(TitleStmt value) {
        this.titleStmt = value;
    }

    /**
     * Gets the value of the publicationStmt property.
     * 
     * @return
     *     possible object is
     *     {@link PublicationStmt }
     *     
     */
    public PublicationStmt getPublicationStmt() {
        return publicationStmt;
    }

    /**
     * Sets the value of the publicationStmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link PublicationStmt }
     *     
     */
    public void setPublicationStmt(PublicationStmt value) {
        this.publicationStmt = value;
    }

    /**
     * Gets the value of the sourceDesc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceDesc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceDesc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SourceDesc }
     * 
     * 
     */
    public List<SourceDesc> getSourceDesc() {
        if (sourceDesc == null) {
            sourceDesc = new ArrayList<SourceDesc>();
        }
        return this.sourceDesc;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
