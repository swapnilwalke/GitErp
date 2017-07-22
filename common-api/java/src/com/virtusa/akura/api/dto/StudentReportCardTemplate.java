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
 * The Class StudentReportCardTemplate.
 * 
 * @author Virtusa Corporation
 */
public class StudentReportCardTemplate {
    
    /** The student report card template id. */
    private int id;
    
    /** The subject. */
    private String subject;
    
    /** The first term marks. */
    private Float firstTermMarks;
    
    /** The second term marks. */
    private Float secondTermMarks;
    
    /** The third term marks. */
    private Float thirdTermMarks;
    
    /** The first term absent. */
    private String firstTermAbsent;
    
    /** The second term absent. */
    private String secondTermAbsent;
    
    /** The third term absent. */
    private String thirdTermAbsent;
    
    /** The third term absent. */
    private float maxMarks;
    
    /**
     * Gets the maximum marks.
     * 
     * @return the maxMarks.
     */
    public float getMaxMarks() {
    
        return maxMarks;
    }
    
    /**
     * Sets the maximum marks.
     * 
     * @param maxMarksVal the maximum marks to set
     */
    public void setMaxMarks(float maxMarksVal) {
    
        this.maxMarks = maxMarksVal;
    }
    
    /**
     * Gets the subject.
     * 
     * @return the subject
     */
    public String getSubject() {
    
        return subject;
    }
    
    /**
     * Sets the subject.
     * 
     * @param strSubject the subject to set
     */
    public void setSubject(String strSubject) {
    
        this.subject = strSubject;
    }
    
    /**
     * Gets the first term marks.
     * 
     * @return the firstTermMarks
     */
    public Float getFirstTermMarks() {
    
        return firstTermMarks;
    }
    
    /**
     * Sets the first term marks.
     * 
     * @param floatFirstTermMarks the new first term marks
     */
    public void setFirstTermMarks(Float floatFirstTermMarks) {
    
        this.firstTermMarks = floatFirstTermMarks;
    }
    
    /**
     * Gets the second term marks.
     * 
     * @return the secondTermMarks
     */
    public Float getSecondTermMarks() {
    
        return secondTermMarks;
    }
    
    /**
     * Sets the second term marks.
     * 
     * @param floatSecondTermMarks the new second term marks
     */
    public void setSecondTermMarks(Float floatSecondTermMarks) {
    
        this.secondTermMarks = floatSecondTermMarks;
    }
    
    /**
     * Gets the third term marks.
     * 
     * @return the thirdTermMarks
     */
    public Float getThirdTermMarks() {
    
        return thirdTermMarks;
    }
    
    /**
     * Sets the third term marks.
     * 
     * @param floatThirdTermMarks the new third term marks
     */
    public void setThirdTermMarks(Float floatThirdTermMarks) {
    
        this.thirdTermMarks = floatThirdTermMarks;
    }
    
    /**
     * Gets the student report card template id.
     * 
     * @return the studentReportCardTemplateId
     */
    public int getId() {
    
        return id;
    }
    
    /**
     * Sets the student report card template id.
     * 
     * @param intId the new student report card template id
     */
    public void setId(int intId) {
    
        this.id = intId;
    }
    
    /**
     * @return the firstTermAbsent
     */
    public String getFirstTermAbsent() {
    
        return firstTermAbsent;
    }
    
    /**
     * Sets the first term absent.
     * 
     * @param strFirstTermAbsent the new first term absent
     */
    public void setFirstTermAbsent(String strFirstTermAbsent) {
    
        this.firstTermAbsent = strFirstTermAbsent;
    }
    
    /**
     * @return the secondTermAbsent
     */
    public String getSecondTermAbsent() {
    
        return secondTermAbsent;
    }
    
    /**
     * Sets the second term absent.
     * 
     * @param strSecondTermAbsent the new second term absent
     */
    public void setSecondTermAbsent(String strSecondTermAbsent) {
    
        this.secondTermAbsent = strSecondTermAbsent;
    }
    
    /**
     * @return the thirdTermAbsent
     */
    public String getThirdTermAbsent() {
    
        return thirdTermAbsent;
    }
    
    /**
     * Sets the third term absent.
     * 
     * @param strThirdTermAbsent the new third term absent
     */
    public void setThirdTermAbsent(String strThirdTermAbsent) {
    
        this.thirdTermAbsent = strThirdTermAbsent;
    }
    
}
