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

package com.virtusa.akura.api.dto;

import java.io.Serializable;

/**
 * This class is to associates all the properties of Nature of appointment domain object.
 * 
 * @author Virtusa Corporation
 */

public class AppointmentNature extends BaseDomain implements Serializable {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /** attribute for holding string. */
    private static final String NATURE_OF_APPOINTMENT_ID = "nautureOfAppointmentId=";
    
    /** attribute for holding string. */
    private static final String DESCRIPTION = "description=";
    
    /**
     * property natureId type integer.
     */
    private int natureId;
    
    /**
     * property description type String.
     */
    private String description;
    
    /**
     * default constructor.
     */
    public AppointmentNature() {

    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intAppointmentNatureId type int
     */
    public AppointmentNature(int intAppointmentNatureId) {

        super();
        this.natureId = intAppointmentNatureId;
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intAppointmentNatureId type int
     * @param strDescription type string
     */
    public AppointmentNature(int intAppointmentNatureId, String strDescription) {

        super();
        this.natureId = intAppointmentNatureId;
        this.description = strDescription;
    }
    
    /**
     * Get the nature id.
     * 
     * @return the natureId.
     */
    public int getNatureId() {

        return natureId;
    }
    
    /**
     * Set the nature id.
     * 
     * @param intAppointmentNatureId the natureId to set.
     */
    public void setNatureId(int intAppointmentNatureId) {

        this.natureId = intAppointmentNatureId;
    }
    
    /**
     * Get the description.
     * 
     * @return the description.
     */
    public String getDescription() {

        return description;
    }
    
    /**
     * Set the description.
     * 
     * @param strDescription the description to set.
     */
    public void setDescription(String strDescription) {

        this.description = strDescription;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {

        return NATURE_OF_APPOINTMENT_ID + natureId + DESCRIPTION + description;
    } 
}
