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

/**
 * This class is to associates all the properties of Language domain object.
 * 
 * @author Virtusa Corporation
 */
public class Language extends BaseDomain {
    
    /** attribute for holding string. */
    private static final String LANGUAGE = ", language=";
    
    /** attribute for holding string. */
    private static final String LANGUAGE_ID = "languageId=";
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * property languageId type int.
     */
    private int languageId;
    
    /**
     * property language type String.
     */
    private String language;
    
    /**
     * @return the languageId
     */
    public int getLanguageId() {
    
        return languageId;
    }
    
    /**
     * @param intLanguageId the languageId to set
     */
    public void setLanguageId(int intLanguageId) {
    
        this.languageId = intLanguageId;
    }
    
    /**
     * @return the language
     */
    public String getLanguage() {
    
        return language;
    }
    
    /**
     * @param strLanguage the language to set
     */
    public void setLanguage(String strLanguage) {
    
        this.language = strLanguage;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return LANGUAGE_ID + languageId + LANGUAGE + language;
    }
    
}
