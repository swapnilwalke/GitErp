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
 * Collects user inputs to generate disciplinary actions report.
 * 
 * @author Virtusa Corporation
 */

public class CRDisciplinaryActionsTemplate {
    
    /** String attribute for to admission no. */
    private static final String ADMISSION_NO = ", admissionNo=";
    
    /** String attribute for to student id. */
    private static final String STUDENT_ID = ", studentId=";
    
    /** String attribute for to student discipline id. */
    private static final String STUDENT_DISCIPLINE_ID = "CRDisciplinaryActionsTemplate [studentDisciplineId=";
    
    /**
     * Holds the studentDisciplineId for the Student.
     */
    private int studentDisciplineId;
    
    /**
     * Holds the student id of the Student.
     */
    private int studentId;
    
    /**
     * Holds the admission number of the Student.
     */
    private String admissionNo;
    
    /**
     * Holds the fullName of the Student.
     */
    private String fullName;
    
    /**
     * Holds the warning level of the Student.
     */
    private String warningLevel;
    
    /**
     * Holds the discipline category of the Student.
     */
    private String disciplineCategory;
    
    /**
     * holds the name of the class.
     */
    private String classDescription;
    
    /**
     * holds the year description of the grade.
     */
    private String gradeDescription;
    
    /**
     * @return studentDisciplineId Returns the studentDisciplineId.
     */
    public int getStudentDisciplineId() {
    
        return studentDisciplineId;
    }
    
    /**
     * @param stuDisciplineId Sets the value for the studentDisciplineId.
     */
    public void setStudentDisciplineId(int stuDisciplineId) {
    
        this.studentDisciplineId = stuDisciplineId;
    }
    
    /**
     * @return studentId Returns the id of the student.
     */
    public int getStudentId() {
    
        return studentId;
    }
    
    /**
     * @param stuId Sets the value for the studentId.
     */
    public void setStudentId(int stuId) {
    
        this.studentId = stuId;
    }
    
    /**
     * @return admissionNo Returns the admission number.
     */
    public String getAdmissionNo() {
    
        return admissionNo;
    }
    
    /**
     * @param admiNo Sets the value for the admission number.
     */
    public void setAdmissionNo(String admiNo) {
    
        this.admissionNo = admiNo;
    }
    
    /**
     * @return fullName Returns the full name of student.
     */
    public String getFullName() {
    
        return fullName;
    }
    
    /**
     * @param fName Sets the value for the full name.
     */
    public void setFullName(String fName) {
    
        this.fullName = fName;
    }
    
    /**
     * @return warningLevel Returns the warning level.
     */
    public String getWarningLevel() {
    
        return warningLevel;
    }
    
    /**
     * @param warnLevel Sets the value for the warning level.
     */
    public void setWarningLevel(String warnLevel) {
    
        this.warningLevel = warnLevel;
    }
    
    /**
     * @return disciplineCategory Returns the discipline category.
     */
    public String getDisciplineCategory() {
    
        return disciplineCategory;
    }
    
    /**
     * @param disCategory Sets the value for the name of the discipline category.
     */
    public void setDisciplineCategory(String disCategory) {
    
        this.disciplineCategory = disCategory;
    }
    
    /**
     * @return classDescription Returns the class
     */
    public String getClassDescription() {
    
        return classDescription;
    }
    
    /**
     * @param classDes Sets the value for the class.
     */
    public void setClassDescription(String classDes) {
    
        this.classDescription = classDes;
    }
    
    /**
     * @return gradeDescription Returns the grade.
     */
    public String getGradeDescription() {
    
        return gradeDescription;
    }
    
    /**
     * @param gradeDes Sets the value for the grade.
     */
    public void setGradeDescription(String gradeDes) {
    
        this.gradeDescription = gradeDes;
    }
    
    /**
     * Returns the details for the CRDisciplinaryActionsTemplate object.
     * 
     * @return - the CRDisciplinaryActionsTemplate object details.
     */
    @Override
    public String toString() {
    
        return STUDENT_DISCIPLINE_ID + studentDisciplineId + STUDENT_ID + studentId + ADMISSION_NO + admissionNo;
    }
    
}
