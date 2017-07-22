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
 * The DTO class to holds the data for Student Marks view.
 * 
 * @author Virtusa Corporation
 */
public class StudentSpecialEventParticipationViewId implements Serializable {
    
    /** Student's admission no. */
    private String admissionNo;
    
    /** Special event's participation id. */
    private int specialEventParticipationId;
    
    /**
     * Getter method for admissionNo.
     * 
     * @return admission no
     */
    public String getAdmissionNo() {
    
        return admissionNo;
    }
    
    /**
     * Setter method for admission no.
     * 
     * @param vAdmissionNo String
     */
    public void setAdmissionNo(String vAdmissionNo) {
    
        this.admissionNo = vAdmissionNo;
    }
    
    /**
     * Getter method for specialEventParticipationId.
     * 
     * @return special event's participant id
     */
    public int getSpecialEventParticipationId() {
    
        return specialEventParticipationId;
    }
    
    /**
     * Setter method for specialEventParticipationId.
     * 
     * @param vSpecialEventParticipationId int
     */
    public void setSpecialEventParticipationId(int vSpecialEventParticipationId) {
    
        this.specialEventParticipationId = vSpecialEventParticipationId;
    }
    
}
