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
 * This class is to associates properties of MarkingFlag.
 * 
 * @author Virtusa Corporation
 */
public class MarkingFlag extends BaseDomain implements Serializable {
    
    /** Default serial id. */
    private static final long serialVersionUID = 1L;
    
    /** Holds primary key of marking flag object. */
    private int markingFlagId;
    
    /** Holds class grade id of marking done. */
    private int classGradeId;
    
    /** Holds term id of marking done. */
    private int termId;
    
    /** Holds year of marking done. */
    private Date year;
    
    /** Holds boolean value to marking is completed or not. */
    private boolean markingCompleted;
    
    /** Holds modified time of the object. */
    private Date modifiedTime;
    
    /** Holds grade id of marking done. */
    private int gradeId;
    
    /**
     * Getter method to get marking flag id.
     * 
     * @return marking flag id
     */
    public int getMarkingFlagId() {
    
        return markingFlagId;
    }
    
    /**
     * Setter method to set marking flag id.
     * 
     * @param vMarkingFlagId marking flag id.
     */
    public void setMarkingFlagId(int vMarkingFlagId) {
    
        this.markingFlagId = vMarkingFlagId;
    }
    
    /**
     * Getter method to get class grade id.
     * 
     * @return class grade id
     */
    public int getClassGradeId() {
    
        return classGradeId;
    }
    
    /**
     * Setter method to set class grade id.
     * 
     * @param vClassGradeId class grade id
     */
    public void setClassGradeId(int vClassGradeId) {
    
        this.classGradeId = vClassGradeId;
    }
    
    /**
     * Getter method get term id.
     * 
     * @return term id
     */
    public int getTermId() {
    
        return termId;
    }
    
    /**
     * Setter method to set term id.
     * 
     * @param vTermId term id
     */
    public void setTermId(int vTermId) {
    
        this.termId = vTermId;
    }
    
    /**
     * Getter method to get year.
     * 
     * @return year
     */
    public Date getYear() {
    
        return year;
    }
    
    /**
     * Setter method to set year.
     * 
     * @param vYear year
     */
    public void setYear(Date vYear) {
    
        this.year = vYear;
    }
    
    /**
     * This method is used to check whether marking is completed or not.
     * 
     * @return boolean value
     */
    public boolean isMarkingCompleted() {
    
        return markingCompleted;
    }
    
    /**
     * This method set true if marking is completed or false if not.
     * 
     * @param vMarkingCompleted boolean value
     */
    public void setMarkingCompleted(boolean vMarkingCompleted) {
    
        this.markingCompleted = vMarkingCompleted;
    }
    
    /**
     * Getter method to get modified time.
     * 
     * @return modified time
     */
    public Date getModifiedTime() {
    
        return modifiedTime;
    }
    
    /**
     * Setter method to set modified time.
     * 
     * @param vModifiedTime modified time.
     */
    public void setModifiedTime(Date vModifiedTime) {
    
        this.modifiedTime = vModifiedTime;
    }

    /**
     * Getter method to set grade id.
     * 
     * @return grade id
     */
    public int getGradeId() {
    
        return gradeId;
    }

    /**
     * Setter method to set grade id.
     * 
     * @param gradeId grade id
     */
    public void setGradeId(int gradeId) {
    
        this.gradeId = gradeId;
    }
    
    
    
}
