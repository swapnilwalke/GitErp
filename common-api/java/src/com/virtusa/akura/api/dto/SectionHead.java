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
import java.util.Date;

/**
 * Domain object to map data for ScheduledJob.
 * 
 * @author Virtusa Corporation
 */
public class SectionHead extends BaseDomain implements Serializable {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /** The constant for String "SectionHead sectionHeadId=". */
    private static final String SECTION_HEAD_SECTION_HEAD_ID = "SectionHead sectionHeadId=";
    
    /** The constant for String ", section=". */
    private static final String SECTION = ", section=";
    
    /** The constant for String ", startDate=". */
    private static final String START_DATE = ", startDate=";
    
    /** The constant for String ", endDate=". */
    private static final String END_DATE = ", endDate=";
    
    /** The constant for String ", notes=". */
    private static final String NOTES = ", notes=";
    
    /** The constant for String ", gradeId=". */
    private static final String GRADE_ID = ", gradeId=";
    
    /** The constant for String ", staff=". */
    private static final String STAFF = ", staff=";
    
    /** Holds the section of the Staff. */
    private Integer sectionId;
    
    /** This property is used only to store value of section to hidden field. */
    private Integer hiddenSectionId;
    
    /**
     * property sectionHeadId {@link int}.
     */
    private int sectionHeadId;
    
    /**
     * property staff {@link Staff}.
     */
    private Staff staff;
    
    /** This property is used only to store value of staff id to hidden field. */
    private Staff hiddenStaff;
    
    /**
     * property grade {@link int}.
     */
    private int gradeId;
    
    /** This property is used only to store value of grade id to hidden field. */
    private int hiddenGradeId;
    
    /**
     * Gets the grade id.
     * 
     * @return grade id.
     */
    public int getGradeId() {

        return gradeId;
    }
    
    /**
     * Sets the grade id.
     * 
     * @param gradeIdValue grade id.
     */
    public void setGradeId(int gradeIdValue) {

        this.gradeId = gradeIdValue;
    }
    
    /**
     * The section property.
     */
    private String section;
    
    /** This property is used only to store value of section to hidden field. */
    private String hiddenSection;
    
    /**
     * Holds the start date of the SectionHead.
     */
    private Date startDate;
    
    /**
     * Holds the end date of the SectionHead.
     */
    private Date endDate;
    
    /**
     * Holds the notes of the SectionHead.
     */
    private String notes;
    
    /**
     * @return the endDate
     */
    public Date getEndDate() {

        if (endDate != null) {
            return (Date) endDate.clone();
        }
        return null;
    }
    
    /**
     * @param dateEndDate the endDate to set
     */
    public void setEndDate(final Date dateEndDate) {

        if (dateEndDate != null) {
            this.endDate = (Date) dateEndDate.clone();
        }
    }
    
    /**
     * @return the notes
     */
    public String getNotes() {

        return notes;
    }
    
    /**
     * @param strNotes the notes to set
     */
    public void setNotes(final String strNotes) {

        this.notes = strNotes;
    }
    
    /**
     * @return the sectionHeadId
     */
    public int getSectionHeadId() {

        return sectionHeadId;
    }
    
    /**
     * @param intSectionHeadId the sectionHeadId to set
     */
    public void setSectionHeadId(int intSectionHeadId) {

        this.sectionHeadId = intSectionHeadId;
    }
    
    /**
     * @return the staff
     */
    public Staff getStaff() {

        return staff;
    }
    
    /**
     * @param objStaff the staff to set
     */
    public void setStaff(Staff objStaff) {

        this.staff = objStaff;
    }
    
    /**
     * @return the startDate
     */
    public Date getStartDate() {

        if (startDate != null) {
            return (Date) startDate.clone();
        }
        return null;
    }
    
    /**
     * @param dateStartDate the startDate to set
     */
    public void setStartDate(Date dateStartDate) {

        if (dateStartDate != null) {
            this.startDate = (Date) dateStartDate.clone();
        }
    }
    
    /**
     * Gets the section.
     * 
     * @return the section.
     */
    public String getSection() {

        return section;
    }
    
    /**
     * Sets the section.
     * 
     * @param sectionValue the section.
     */
    public void setSection(String sectionValue) {

        this.section = sectionValue;
    }
    
    /**
     * @return staff object which store in hidden field.
     */
    public Staff getHiddenStaff() {

        return hiddenStaff;
    }
    
    /**
     * @param hiddenStaffObj Staff object
     */
    public void setHiddenStaff(Staff hiddenStaffObj) {

        this.hiddenStaff = hiddenStaffObj;
    }
    
    /**
     * @return grade id which is store in hidden field
     */
    public int getHiddenGradeId() {

        return hiddenGradeId;
    }
    
    /**
     * @param hiddenGradeIdInt value of grade id
     */
    public void setHiddenGradeId(int hiddenGradeIdInt) {

        this.hiddenGradeId = hiddenGradeIdInt;
    }
    
    /**
     * @return section value store in hidden field
     */
    public String getHiddenSection() {

        return hiddenSection;
    }
    
    /**
     * @param hiddenSectionValue value of the section
     */
    public void setHiddenSection(String hiddenSectionValue) {

        this.hiddenSection = hiddenSectionValue;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {

        return SECTION_HEAD_SECTION_HEAD_ID + sectionHeadId + STAFF + staff + GRADE_ID + gradeId + SECTION + section
                + START_DATE + startDate + END_DATE + endDate + NOTES + notes;
    }
    
    /**
     * @return the hiddenSectionId
     */
    public Integer getHiddenSectionId() {

        return hiddenSectionId;
    }
    
    /**
     * @param hiddenSectionIdInt the hiddenSectionId to set
     */
    public void setHiddenSectionId(Integer hiddenSectionIdInt) {

        this.hiddenSectionId = hiddenSectionIdInt;
    }
    
    /**
     * @return the sectionId
     */
    public Integer getSectionId() {

        return sectionId;
    }
    
    /**
     * @param sectionIdInt the sectionId to set
     */
    public void setSectionId(Integer sectionIdInt) {

        this.sectionId = sectionIdInt;
    }
    
}
