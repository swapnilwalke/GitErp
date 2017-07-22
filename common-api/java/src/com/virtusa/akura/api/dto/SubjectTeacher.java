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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Virtusa Corporation
 */
public class SubjectTeacher extends BaseDomain implements Serializable {
    
    /** String attribute for to SUBJECT_TEACHER_ID. */
    private static final String SUBJECT_TEACHER_ID = "SubjectTeacher [subjectTeacherId=";
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds subjectTeacherId.
     */
    private Integer subjectTeacherId;
    
    /**
     * Holds gradeSubject.
     */
    private GradeSubject gradeSubject;
    
    /**
     * Holds year.
     */
    private String year;
    
    /**
     * Holds schoolClass.
     */
    private SchoolClass schoolClass;
    
    /**
     * List consits of school class list.
     */
    private List<SchoolClass> schoolClassList;
    
    /**
     * Holds staff.
     */
    private Staff staff;
    
    /**
     * Holds gradeId.
     */
    private Integer gradeId;
    
    /**
     * Holds subjectId.
     */
    private Integer subjectId;
    
    /**
     * Holds the Status of the SubjectTeacher.
     */
    private boolean deleted;
    
    /**
     * getter method of gradeId `deleted` attribute.
     * 
     * @return the deleted
     */
    public boolean getDeleted() {

        return deleted;
    }
    
    /**
     * setter method of the `deleted` attribute.
     * 
     * @param deletedFlag of the flag need to be set
     */
    public void setDeleted(boolean deletedFlag) {

        this.deleted = deletedFlag;
    }
    
    /**
     * Default constructor.
     */
    public SubjectTeacher() {

        schoolClassList = new ArrayList<SchoolClass>();
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param objSbjectTeacherId {@link SubjectTeacherId}
     */
    public SubjectTeacher(SubjectTeacherId objSbjectTeacherId) {

    }
    
    /**
     * getter method of the `subjectId` attribute.
     * 
     * @return the subjectId
     */
    public Integer getSubjectId() {

        return subjectId;
    }
    
    /**
     * setter method of the `subjectId` attribute.
     * 
     * @param subjectIdPara - subjectId of the flag need to be set
     */
    public void setSubjectId(Integer subjectIdPara) {

        this.subjectId = subjectIdPara;
    }
    
    /**
     * getter method of the `gradeId` attribute.
     * 
     * @return the gradeId
     */
    public Integer getGradeId() {

        return gradeId;
    }
    
    /**
     * setter method of the `gradeId` attribute.
     * 
     * @param gradeIdPara the gradeId to set
     */
    public void setGradeId(Integer gradeIdPara) {

        this.gradeId = gradeIdPara;
    }
    
    /**
     * getter method of the `subjectTeacherId` attribute.
     * 
     * @return the subjectTeacherId
     */
    public Integer getSubjectTeacherId() {

        return subjectTeacherId;
    }
    
    /**
     * setter method of the `subjectTeacherId` attribute.
     * 
     * @param intSbjectTeacherId the subjectTeacherId to set
     */
    public void setSubjectTeacherId(Integer intSbjectTeacherId) {

        this.subjectTeacherId = intSbjectTeacherId;
    }
    
    /**
     * getter method of the `gradeSubject` attribute.
     * 
     * @return the gradeSubject
     */
    public GradeSubject getGradeSubject() {

        return gradeSubject;
    }
    
    /**
     * setter method of the `gradeSubject` attribute.
     * 
     * @param objGradeSubject the gradeSubject to set
     */
    public void setGradeSubject(GradeSubject objGradeSubject) {

        this.gradeSubject = objGradeSubject;
    }
    
    /**
     * getter method of the `schoolClass` attribute.
     * 
     * @return the schoolClass
     */
    public SchoolClass getSchoolClass() {

        return schoolClass;
    }
    
    /**
     * setter method of the `schoolClass` attribute.
     * 
     * @param objSchoolClass the schoolClass to set
     */
    public void setSchoolClass(SchoolClass objSchoolClass) {

        this.schoolClass = objSchoolClass;
    }
    
    /**
     * getter method of the `schoolClassList` attribute.
     * 
     * @return the schoolClassList
     */
    public List<SchoolClass> getSchoolClassList() {

        return schoolClassList;
    }
    
    /**
     * setter method of the `schoolClassList` attribute.
     * 
     * @param objschoolClassList the schoolClassList to set
     */
    public void setSchoolClassList(List<SchoolClass> objschoolClassList) {

        this.schoolClassList = objschoolClassList;
    }
    
    /**
     * getter method of the `staff` attribute.
     * 
     * @return the staff
     */
    public Staff getStaff() {

        return staff;
    }
    
    /**
     * setter method of the `staff` attribute.
     * 
     * @param objStaff the staff to set
     */
    public void setStaff(Staff objStaff) {

        this.staff = objStaff;
    }
    
    /**
     * Get the year.
     * 
     * @return the year.
     */
    public String getYear() {

        return year;
    }
    
    /**
     * Set the year.
     * 
     * @param years the year of Teacher class allocation.
     */
    public void setYear(String years) {

        this.year = years;
    }
    
    /**
     * Returns a String that represents this Object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {

        return SUBJECT_TEACHER_ID + subjectTeacherId;
    }
    
}
