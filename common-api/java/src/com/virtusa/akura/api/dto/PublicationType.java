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
import java.util.Set;

/**
 * This class represents all properties of a PublicationType domain object.
 * 
 * @author Virtusa Corporation
 */
public class PublicationType extends BaseDomain implements Serializable {
    
    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the modified time for the toString().
     */
    private static final String MODIFIED_TIME = " modified date/time";
    
    /**
     * Represents the type for the toString().
     */
    private static final String TYPE = " type = ";
    
    /**
     * Represents the publication id for the toString().
     */
    private static final String PUBLICATION_TYPE = "publicationType id = ";
    
    /**
     * Represents the id of the PublicationType.
     */
    private int pTypeId;
    
    /**
     * Represents the type of the publication.News or an event.
     */
    private String type;
    
    /**
     * Represents the publications for a given publicationType object.
     */
    private Set<Publication> publicationSet;
    
    /**
     * Constructs a new PublicationType object.
     */
    public PublicationType() {
    
    }
    
    /**
     * Returns the id for this PublicationType object.
     * 
     * @return - the id of the PublicationType object
     */
    public int getpTypeId() {
    
        return pTypeId;
    }
    
    /**
     * Sets the id for this PublicationType object with the given key.
     * 
     * @param intPTypeId - id of the PublicationType.
     */
    public void setpTypeId(int intPTypeId) {
    
        this.pTypeId = intPTypeId;
    }
    
    /**
     * Returns the type for this PublicationType object.
     * 
     * @return strDescription - the type of the PublicationType object
     */
    public String getType() {
    
        return type;
    }
    
    /**
     * Sets the type for this PublicationType object with the given information, event or a news.
     * 
     * @param strType - the type of the PublicationType.
     */
    public void setType(String strType) {
    
        this.type = strType;
    }
    
    /**
     * Returns a set of publications.
     * 
     * @return - the set of publications.
     */
    public Set<Publication> getPublicationSet() {
    
        return publicationSet;
    }
    
    /**
     * Sets the instances of publication.
     * 
     * @param setPublicationSet - the set of publications.
     */
    public void setPublicationSet(Set<Publication> setPublicationSet) {
    
        this.publicationSet = setPublicationSet;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public final String toString() {
    
        return PUBLICATION_TYPE + this.pTypeId + TYPE + this.type + MODIFIED_TIME + this.getModifiedTime();
    }
}
