/*
 * < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>
 *
 * Copyright (C) 2012 Virtusa Corporation.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.virtusa.akura.api.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * This class represents all properties of a Publication domain object.
 *
 * @author Virtusa Corporation
 */
public class Publication extends BaseDomain implements Serializable {

    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents the modified time for the toString().
     */
    private static final String MODIFIED_TIME = " modified date/time";

    /**
     * Represents the header for the toString().
     */
    private static final String HEADER = " header = ";

    /**
     * Represents the publication id for the toString().
     */
    private static final String PUBLICATION_ID = "publicationId id = ";

    /**
     * Represents the id of the PublicationType.
     */
    private int publicationId;

    /**
     * Represents the header of the publication.
     */
    private String header;

    /**
     * Represents the description of the publication.
     */
    private String message;

    /**
     * Represents the image.
     */
    private byte[] image;

    /**
     * Represents the date of expire of the publication.
     */
    private Date expiryDate;

    /**
     * Represents the multipartFile of the publication.
     */
    private transient MultipartFile multipartFile;

    /**
     * Represents the publication type of the publication, event or a news.
     */
    private transient PublicationType pType;

    /**
     * Constructs a new Publication object.
     */
    public Publication() {

    }

    /**
     * Returns the id of the Publication object.
     *
     * @return - the id of the PublicationType object
     */
    public int getPublicationId() {

        return publicationId;
    }

    /**
     * Sets the id for this Publication object with the given key.
     *
     * @param strPublicationId - id of the Publication
     */
    public void setPublicationId(int strPublicationId) {

        this.publicationId = strPublicationId;
    }

    /**
     * Returns the header of the Publication object.
     *
     * @return - the header of the Publication object
     */
    public String getHeader() {

        return header;
    }

    /**
     * Sets the header of the Publication object.
     *
     * @param strHeader - the header of the Publication object
     */
    public void setHeader(String strHeader) {

        this.header = strHeader;
    }

    /**
     * Returns the description of the Publication object.
     *
     * @return - the description of the Publication object
     */
    public String getMessage() {

        return message;
    }

    /**
     * Sets the message of the publication object.
     *
     * @param strMessage - the message of the publication object.
     */
    public void setMessage(String strMessage) {

        this.message = strMessage;
    }

    /**
     * Returns the expire date of the publication object.
     *
     * @return - the expire date of the publication
     */
    public Date getExpiryDate() {

        if (expiryDate != null) {
            return (Date) expiryDate.clone();
        }

        return null;
    }

    /**
     * Sets the expire date of the publication object.
     *
     * @param dateExpiryDate - the date of the expire of the publication.
     */
    public void setExpiryDate(final Date dateExpiryDate) {

        if (dateExpiryDate != null) {

            this.expiryDate = (Date) dateExpiryDate.clone();
        }
    }

    /**
     * Returns the multipartFile of the image of the publication object.
     *
     * @return - the multipartFile of the image of the publication object
     */
    public MultipartFile getMultipartFile() {

        return multipartFile;
    }

    /**
     * Sets the multipartFile of the image.
     *
     * @param mMultipartFile - the multipartFile of the publication
     */
    public void setMultipartFile(final MultipartFile mMultipartFile) {

        this.multipartFile = mMultipartFile;
    }

    /**
     * Returns the array of bytes of the image.
     *
     * @return - the array of bytes of the image
     */
    public byte[] getImage() {
        
        if (image != null) {
            return (byte[]) image.clone();
        }
        return null;
    }

    /**
     * Sets the array of bytes of the image.
     *
     * @param byteImage - the array of bytes of the image
     */
    public void setImage(byte[] byteImage) {

        if (byteImage != null) {
            this.image = (byte[]) byteImage.clone();
        }
    }

    /**
     * Returns the publicationType of the publication object.
     *
     * @return - the publicationType of the publication object
     */
    public PublicationType getpType() {

        return pType;
    }

    /**
     * Sets the publicationType of the publication object.
     *
     * @param pubPType - the publicationType of the publication object
     */
    public void setpType(PublicationType pubPType) {

        this.pType = pubPType;
    }

    /**
     * Returns the details for the current object.
     *
     * @return - the current object details.
     */
    public final String toString() {

        return PUBLICATION_ID + this.publicationId + HEADER + this.header + MODIFIED_TIME + this.getModifiedTime();
    }
}
