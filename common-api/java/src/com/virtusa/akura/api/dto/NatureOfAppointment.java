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


public class NatureOfAppointment extends BaseDomain implements Serializable{

    /** Generated serial id. */
    private static final long serialVersionUID = 1L;
    
    /** attribute for holding string. */
    private static final String NATURE_OF_APPOINTMENT_ID = "nautureOfAppointmentId=";
    
    /** attribute for holding string. */
    private static final String DESCRIPTION = "description=";
    
    /** holds natureOfAppintmentId. */
    private int nautureOfAppointmentId;
    
    /** holds description. */
    private String description;

    /**
     * Default constructor.
     */
    public NatureOfAppointment() {

        super();
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intNautureOfAppointmentId type int
     * @param strDescription type string
     */
    public NatureOfAppointment(int intNautureOfAppointmentId, String strDescription) {

        super();
        this.nautureOfAppointmentId = intNautureOfAppointmentId;
        this.description = strDescription;
    }

    /**
     * @return the nautureOfAppointmentId
     */
    public int getNautureOfAppointmentId() {
    
        return nautureOfAppointmentId;
    }

    /**
     * @param intNautureOfAppointmentId the nautureOfAppointmentId to set
     */
    public void setNautureOfAppointmentId(int intNautureOfAppointmentId) {
    
        this.nautureOfAppointmentId = intNautureOfAppointmentId;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }

    /**
     * @param strDescription the description to set
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

        return NATURE_OF_APPOINTMENT_ID + nautureOfAppointmentId + DESCRIPTION + description;
    }
 
    
}
