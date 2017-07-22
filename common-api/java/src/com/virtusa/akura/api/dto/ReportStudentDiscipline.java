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
import java.sql.Date;

/**
 * Domain object to map data for student discipline actions.
 *
 * @author Virtusa Corporation
 */

public class ReportStudentDiscipline implements Serializable {

    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 8074837892865681737L;

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
     * Holds the warning.
     */
    private String warning;

    /**
     * Holds the warning level.
     */
    private String warningLevel;

    /**
     * holds the description of the class Grade.
     */
    private String classDescription;

    /**
     * holds the warn Date for the student.
     */
    private Date warnDate;

    /**
     * holds the teacher who gave the warning.
     */
    private String teacherName;

    /**
     * holds the status whether warning is informed to parent or not.
     */
    private boolean informedToParent;

    /**
     * Holds nameWithInitials.
     */
    private String nameWithInitials;

    /**
     * @return nameWithInitials Returns the value of student name.
     */
    public String getNameWithInitials() {

        return nameWithInitials;
    }

    /**
     * @param nameWithInitialsRef Sets the student name with initials.
     */
    public void setNameWithInitials(String nameWithInitialsRef) {

        this.nameWithInitials = nameWithInitialsRef;
    }

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
     * @return classGradeDescription - string.
     */
    public String getClassDescription() {

        return classDescription;
    }

    /**
     * @param classGradeDes Sets the value for the classGradeDescription.
     */
    public void setClassDescription(String classGradeDes) {

        this.classDescription = classGradeDes;
    }

    /**
     * @return warnDate Returns the warning date for student.
     */
    public Date getWarnDate() {

        return warnDate;
    }

    /**
     * @param warningDate Sets the the warning date for student.
     */
    public void setWarnDate(Date warningDate) {

        this.warnDate = warningDate;
    }

    /**
     * @return informedToParent Returns the whether parent is informed or not about the warning.
     */
    public boolean isInformedToParent() {

        return informedToParent;
    }

    /**
     * @param isInformedToParent Set whether parent is informed or not about the warning.
     */
    public void setInformedToParent(boolean isInformedToParent) {

        this.informedToParent = isInformedToParent;
    }

    /**
     * @return warning Returns the warning for student.
     */
    public String getWarning() {

        return warning;
    }

    /**
     * @param warningRef Sets the the warning date for student.
     */
    public void setWarning(String warningRef) {

        this.warning = warningRef;
    }

    /**
     * @return teacher name Returns the warning for teacher.
     */
    public String getTeacherName() {

        return teacherName;
    }

    /**
     * @param teacherNameRef Sets the the warning for teacher.
     */
    public void setTeacherName(String teacherNameRef) {

        this.teacherName = teacherNameRef;
    }

}
