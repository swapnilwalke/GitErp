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

/**
 * This class represents all properties of a SpecialEvents domain object.
 * 
 * @author Virtusa Corporation
 */
public class SpecialEvents extends BaseDomain implements Serializable {
    
    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the id of the SpecialEvents.
     */
    private int specialEventsId;
    
    /**
     * Represents the name of the SpecialEvents.
     */
    private String name;
    
    /**
     * Represents the description of the SpecialEvents.
     */
    private String description;
    
    /**
     * Represents the date of SpecialEvents.
     */
    private Date date;
    
    /**
     * Represents the participantCategory of the SpecialEvents.
     */
    private ParticipantCategory participantCategory;
    
    /**
     * return specialEventsId of the ParticipantCategory.
     * 
     * @return the specialEventsId
     */
    public int getSpecialEventsId() {
    
        return specialEventsId;
    }
    
    /**
     * Setter method for specialEventsId.
     * 
     * @param specialEventsIdRef the specialEventsId to set
     */
    public void setSpecialEventsId(int specialEventsIdRef) {
    
        this.specialEventsId = specialEventsIdRef;
    }
    
    /**
     * return name of the SpecialEvents.
     * 
     * @return the name
     */
    public String getName() {
    
        return name;
    }
    
    /**
     * Setter method for name.
     * 
     * @param nameRef the name to set
     */
    public void setName(String nameRef) {
    
        this.name = nameRef;
    }
    
    /**
     * return description of the SpecialEvents.
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
    
    /**
     * return date of the SpecialEvents.
     * 
     * @return the date
     */
    public Date getDate() {
    
        if (date != null) {
            return (Date) date.clone();
        }
        
        return null;
    }
    
    /**
     * Setter method for participantCategoryId.
     * 
     * @param dateRef the date to set
     */
    public void setDate(Date dateRef) {
    
        if (dateRef != null) {
            
            this.date = (Date) dateRef.clone();
        }
    }
    
    /**
     * return participantCategory of the SpecialEvents.
     * 
     * @return the participantCategory
     */
    public ParticipantCategory getParticipantCategory() {
    
        return participantCategory;
    }
    
    /**
     * Setter method for participantCategoryId.
     * 
     * @param participantCategoryRef the participantCategory to set
     */
    public void setParticipantCategory(ParticipantCategory participantCategoryRef) {
    
        this.participantCategory = participantCategoryRef;
    }
    
}
