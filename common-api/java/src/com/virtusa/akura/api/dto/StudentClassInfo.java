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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * This class represents all the properties of StudentClassInfo.
 * 
 * @author Virtusa Corporation
 */

public class StudentClassInfo extends AuditableBaseDomain {
    
    /** string constant for holding ` year = `. */
    private static final String YEAR = " year = ";
    
    /** string constant for holding ` class grade id = `. */
    private static final String CLASS_GRADE_ID = " class grade id = ";
    
    /** string constant for holding ` student id = `. */
    private static final String STUDENT_ID = " student id = ";
    
    /** string constant for holding ` is monitor = `. */
    private static final String IS_MONITOR = " is monitor = ";
    
    /** string constant for holding ` modified date/time`. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** string constant for holding `student class info id = `. */
    private static final String STUDENT_CLASS_INFO_ID = "student class info id = ";
    
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * The unique ID for the student class info class.
     */
    private int studentClassInfoId;
    
    /**
     * Holds classGrade.
     */
    private ClassGrade classGrade;
    
    /**
     * Holds student.
     */
    private Student student;
    
    /**
     * Whether the student is monitor in the class.
     */
    private boolean checkMonitor;
    
    /**
     * Date object variable to hold year.
     */
    private Date year;
    
    /**
     * Holds studentTermMarks.
     */
    private Set<StudentTermMark> studentTermMarks = new HashSet<StudentTermMark>(0);
    
    /**
     * Default constructor to constructs a new student class info object.
     */
    public StudentClassInfo() {
    
    }
    
    /**
     * Constructs a new student class info object with the given student class info Id.
     * 
     * @param intStudentClassInfoId specifies the student class info Id, defined by an integer
     */
    public StudentClassInfo(int intStudentClassInfoId) {
    
        this.studentClassInfoId = intStudentClassInfoId;
        
    }
    
    /**
     * checks the student is a monitor in the class.
     * 
     * @return checkMonitor - boolean type object
     */
    public boolean isCheckMonitor() {
    
        return checkMonitor;
    }
    
    /**
     * Sets the flag that indicates the student is a monitor in the class.
     * 
     * @param chMonitor specifies the student class info Id, defined by an integer
     */
    public void setCheckMonitor(boolean chMonitor) {
    
        this.checkMonitor = chMonitor;
    }
    
    /**
     * Gets the class grade of the student.
     * 
     * @return the classGrade
     */
    
    public ClassGrade getClassGrade() {
    
        return classGrade;
    }
    
    /**
     * Sets the class grade of the student.
     * 
     * @param objClassGrade specifies the class grade object, defined by an ClassGrade type
     */
   
    public void setClassGrade(ClassGrade objClassGrade) {
    
        this.classGrade = objClassGrade;
    }
    
    /**
     * Gets the student of the student class info object.
     * 
     * @return the student - Student type object
     */
    @JsonIgnore
    public Student getStudent() {
    
        return student;
    }
    
    /**
     * Sets the student for the student class info object.
     * 
     * @param objStudent specifies the student object, defined by an Student type
     */
    @JsonIgnore
    public void setStudent(Student objStudent) {
    
        this.student = objStudent;
    }
    
    /**
     * Gets the student term mark of the student class info object.
     * 
     * @return the studentTermMarks - StudentTermMark type object
     */
    public Set<StudentTermMark> getStudentTermMarks() {
    
        return studentTermMarks;
    }
    
    /**
     * Sets the student term mark of the student class info object.
     * 
     * @param collStudentTermMarks specifies set of StudentTermMark objects, defined by as Set
     */
    public void setStudentTermMarks(Set<StudentTermMark> collStudentTermMarks) {
    
        this.studentTermMarks = collStudentTermMarks;
    }
    
    /**
     * Gets student class info Id for the student class info object.
     * 
     * @return studentClassInfoId - integer type object
     */
    public int getStudentClassInfoId() {
    
        return studentClassInfoId;
    }
    
    /**
     * Sets student class info Id for the student class info object.
     * 
     * @param intStudentClassInfoId specifies set of student class info Id, defined by an integer
     */
    public void setStudentClassInfoId(int intStudentClassInfoId) {
    
        this.studentClassInfoId = intStudentClassInfoId;
    }
    
    /**
     * Gets the year which holds the student class information.
     * 
     * @return year - Date type object
     */
    public Date getYear() {
    
        if (year != null) {
            return (Date) year.clone();
        }
        return null;
    }
    
    /**
     * Sets the year which holds the student class information.
     * 
     * @param dtYear specifies year, defined by Date type object
     */
    public void setYear(Date dtYear) {
    
        if (dtYear != null) {
            this.year = (Date) dtYear.clone();
        }
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return STUDENT_CLASS_INFO_ID + this.studentClassInfoId + MODIFIED_DATE_TIME + this.getModifiedTime()
                + IS_MONITOR + this.isCheckMonitor() + STUDENT_ID + this.student.getStudentId() + CLASS_GRADE_ID
                + this.classGrade.getClassGradeId() + YEAR + this.year;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String auditToString() {
    
        return "StudentClassInfo [studentClassInfoId=" + studentClassInfoId + ", classGrade="
                + classGrade.getDescription() + ", student=" + student.getFullName() + ", year=" + year + "]";
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + studentClassInfoId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StudentClassInfo other = (StudentClassInfo) obj;
        if (studentClassInfoId != other.studentClassInfoId)
            return false;
        return true;
    }

   
    
    
}
