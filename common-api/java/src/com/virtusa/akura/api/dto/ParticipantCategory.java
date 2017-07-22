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

/**
 * This class represents all the properties of a ParticipantCategory domain object.
 * 
 * @author Virtusa Corporation
 */
public class ParticipantCategory extends BaseDomain implements Serializable {
    
    /**
     * Represents the default serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the id for ParticipantCategory.
     */
    private int participantCategoryId;
    
    /**
     * Represents the description for a ParticipantCategory.
     */
    private String description;
    
    /**
     * return dailyTeacherAttendanceId of the ParticipantCategory.
     * 
     * @return the participantCategoryId
     */
    public int getParticipantCategoryId() {
    
        return participantCategoryId;
    }
    
    /**
     * Setter method for participantCategoryId.
     * 
     * @param participantCategoryIdRef the participantCategoryId to set
     */
    public void setParticipantCategoryId(int participantCategoryIdRef) {
    
        this.participantCategoryId = participantCategoryIdRef;
    }
    
    /**
     * return description of the ParticipantCategory.
     * 
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * Setter method for description.
     * 
     * @param descriptionRef the description to set
     */
    public void setDescription(String descriptionRef) {
    
        this.description = descriptionRef;
    }
    
}
