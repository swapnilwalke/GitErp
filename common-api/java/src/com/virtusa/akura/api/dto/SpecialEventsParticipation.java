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
 * This class represents all properties of a SpecialEventsParticipation domain object.
 * 
 * @author Virtusa Corporation
 */
public class SpecialEventsParticipation extends BaseDomain implements Serializable {
    
    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the id of the SpecialEventsParticipation.
     */
    private int specialEventsParticipationId;
    
    /**
     * Represents the specialEvents of the SpecialEventsParticipation.
     */
    private SpecialEvents specialEvents;
    
    /**
     * Represents the classGrade of the SpecialEventsParticipation.
     */
    private ClassGrade classGrade;
    
    /**
     * Represents the sportCategory of the SpecialEventsParticipation.
     */
    private SportCategory sportCategory;
    
    /**
     * Represents the clubSociety of the SpecialEventsParticipation.
     */
    private ClubSociety clubSociety;
    
    
    /**
     * return specialEventsParticipationId of the SpecialEventsParticipation.
     * 
     * @return the specialEventsParticipationId
     */
    public int getSpecialEventsParticipationId() {
    
        return specialEventsParticipationId;
    }

    /**
     * Setter method for specialEventsParticipationId.
     * 
     * @param specialEventsParticipationIdRef the specialEventsParticipationId to set
     */
    public void setSpecialEventsParticipationId(int specialEventsParticipationIdRef) {
    
        this.specialEventsParticipationId = specialEventsParticipationIdRef;
    }

    /**
     * return specialEvents of the SpecialEventsParticipation.
     * 
     * @return the specialEvents
     */
    public SpecialEvents getSpecialEvents() {
    
        return specialEvents;
    }

    /**
     * Setter method for specialEventsAttendanceId.
     * 
     * @param specialEventsRef the specialEvents to set
     */
    public void setSpecialEvents(SpecialEvents specialEventsRef) {
    
        this.specialEvents = specialEventsRef;
    }

    /**
     * return classGrade of the SpecialEventsParticipation.
     * 
     * @return the classGrade
     */
    public ClassGrade getClassGrade() {
    
        return classGrade;
    }

    /**
     * Setter method for specialEventsAttendanceId.
     * 
     * @param classGradeRef the classGrade to set
     */
    public void setClassGrade(ClassGrade classGradeRef) {
    
        this.classGrade = classGradeRef;
    }

    /**
     * return sportCategory of the SpecialEventsParticipation.
     * 
     * @return the sportCategory
     */
    public SportCategory getSportCategory() {
    
        return sportCategory;
    }

    /**
     * Setter method for sportCategory.
     * 
     * @param sportCategoryRef the sportCategory to set
     */
    public void setSportCategory(SportCategory sportCategoryRef) {
    
        this.sportCategory = sportCategoryRef;
    }

    /**
     * return clubSociety of the SpecialEventsParticipation.
     * 
     * @return the clubSociety
     */
    public ClubSociety getClubSociety() {
    
        return clubSociety;
    }

    /**
     * Setter method for clubSociety.
     * 
     * @param clubSocietyRef the clubSociety to set
     */
    public void setClubSociety(ClubSociety clubSocietyRef) {
    
        this.clubSociety = clubSocietyRef;
    }

    
   
    
}
