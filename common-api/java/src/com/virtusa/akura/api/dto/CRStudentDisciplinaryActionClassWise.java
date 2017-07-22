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

import java.util.Date;

/**
 * Domain object to map data for CRStudentDisciplinaryActionClassWise.
 *
 * @author Virtusa Corporation
 */
public class CRStudentDisciplinaryActionClassWise {

    /**
     * descriptive text for admissionNo.
     */
    private String admissionNo;

    /**
     * descriptive text for fullName.
     */
    private String fullName;

    /**
     * descriptive text for warningLevel.
     */
    private String warningLevel;

    /**
     * descriptive text for classDescription.
     */
    private String classDescription;

    /**
     * descriptive text for warnDate.
     */
    private Date warnDate;

    /**
     * descriptive text for informedToParent.
     */
    private String informedToParent;

    /**
     * descriptive text for year.
     */
    private String year;

    /**
     * descriptive text for warning.
     */
    private String warning;

    /**
     * descriptive text for teacherName.
     */
    private String teacherName;

    /**
     * gets the teacherName.
     *
     * @return the teacherName
     */
    public String getTeacherName() {

        return teacherName;
    }

    /**
     * sets the teacherName.
     *
     * @param strTeacherName the teacherName to set
     */
    public void setTeacherName(String strTeacherName) {

        this.teacherName = strTeacherName;
    }

    /**
     * gets the admissionNo.
     *
     * @return the admissionNo
     */
    public String getAdmissionNo() {

        return admissionNo;
    }

    /**
     * sets the admissionNo.
     *
     * @param strAdmissionNo the admissionNo to set
     */
    public void setAdmissionNo(String strAdmissionNo) {

        this.admissionNo = strAdmissionNo;
    }

    /**
     * gets the fullName.
     *
     * @return the fullName
     */
    public String getFullName() {

        return fullName;
    }

    /**
     * sets the fullName.
     *
     * @param strFullName the fullName to set
     */
    public void setFullName(String strFullName) {

        this.fullName = strFullName;
    }

    /**
     * gets the warningLevel.
     *
     * @return the warningLevel
     */
    public String getWarningLevel() {

        return warningLevel;
    }

    /**
     * sets the warningLevel.
     *
     * @param strWarningLevel the warningLevel to set
     */
    public void setWarningLevel(String strWarningLevel) {

        this.warningLevel = strWarningLevel;
    }

    /**
     * gets the classDescription.
     *
     * @return the classDescription
     */
    public String getClassDescription() {

        return classDescription;
    }

    /**
     * sets the classDescription.
     *
     * @param strClassDescription the classDescription to set
     */
    public void setClassDescription(String strClassDescription) {

        this.classDescription = strClassDescription;
    }

    /**
     * gets the warnDate.
     *
     * @return the warnDate
     */
    public Date getWarnDate() {

        if (warnDate != null) {
            return (Date) warnDate.clone();
        }

        return null;
    }

    /**
     * sets the warnDate.
     *
     * @param warnDateObj the warnDate to set
     */
    public void setWarnDate(Date warnDateObj) {

        if (warnDateObj != null) {

            this.warnDate = (Date) warnDateObj.clone();
        }
    }

    /**
     * sets the informedToParent.
     *
     * @param strrInformedToParent the informedToParent to set
     */
    public void setInformedToParent(String strrInformedToParent) {

        this.informedToParent = strrInformedToParent;
    }

    /**
     * gets the year.
     *
     * @return the year
     */
    public String getYear() {

        return year;
    }

    /**
     * sets the year.
     *
     * @param strYear the year to set
     */
    public void setYear(String strYear) {

        this.year = strYear;
    }

    /**
     * gets the informedToParent.
     *
     * @return the informedToParent
     */
    public String getInformedToParent() {

        return informedToParent;
    }

    /**
     * gets the warning.
     *
     * @return the warning
     */
    public String getWarning() {

        return warning;
    }

    /**
     * sets the warning.
     *
     * @param strWarning the warning to set
     */
    public void setWarning(String strWarning) {

        this.warning = strWarning;
    }

}
