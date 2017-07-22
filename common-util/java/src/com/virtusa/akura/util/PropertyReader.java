/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

package com.virtusa.akura.util;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * @author Virtusa Corporation
 */

public final class PropertyReader {
    
    /** string constant for Log error massage for. */
    private static final String NOT_FIND_THE_BUNDLE = " not find the bundle";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(PropertyReader.class);
    
    /**
     * The map contains the resource bundle of the property file.
     */
    private static final Map<String, ResourceBundle> RESOURCE_MAP = new HashMap<String, ResourceBundle>();
    
    /**
     * The default constructor.
     */
    private PropertyReader() {

    }
    
    /**
     * Get property when the file, name and reload flag is specified.
     * 
     * @param fileName the name of the property file.
     * @param key key the name of the property.
     * @return the property value.
     */
    public static String getPropertyValue(String fileName, String key) {

        String value = null;
        ResourceBundle resBundle = PropertyReader.RESOURCE_MAP.get(fileName);
        if (resBundle == null) {
            LOG.debug(NOT_FIND_THE_BUNDLE);
            try {
                resBundle = ResourceBundle.getBundle(fileName);
                PropertyReader.RESOURCE_MAP.put(fileName, resBundle);
            } catch (MissingResourceException ex) {
                resBundle = null;
            }
        }
        if (resBundle != null) {
            try {
                value = resBundle.getString(key);
            } catch (MissingResourceException ex) {
                value = null;
            }
        }
        return value;
    }
    
}
