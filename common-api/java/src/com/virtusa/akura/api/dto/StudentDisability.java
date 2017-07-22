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

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Virtusa Corporation
 */
public class StudentDisability extends BaseDomain implements java.io.Serializable {
    
    /** long attribute for holding serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string otherInfo. */
    private static final String OTHER_INFO = ", otherInfo=";
    
    /** key to hold string behaviourDifficulties. */
    private static final String BEHAVIOUR_DIFFICULTIES = ", behaviourDifficulties=";
    
    /** key to hold string fits. */
    private static final String FITS = ", fits=";
    
    /** key to hold string physicalDisabilities. */
    private static final String PHYSICAL_DISABILITIES = ", physicalDisabilities=";
    
    /** key to hold string dyslexia. */
    private static final String DYSLEXIA = ", dyslexia=";
    
    /** key to hold string speechDifficulties. */
    private static final String SPEECH_DIFFICULTIES = ", speechDifficulties=";
    
    /** key to hold string visualImpairment. */
    private static final String VISUAL_IMPAIRMENT = ", visualImpairment=";
    
    /** key to hold string hearingImpairment. */
    private static final String HEARING_IMPAIRMENT = ", hearingImpairment=";
    
    /** key to hold string disabilityInfo. */
    private static final String DISABILITY_INFO = ", disabilityInfo=";
    
    /** key to hold string allergiesMedicalNotes. */
    private static final String ALLERGIES_MEDICAL_NOTES = ", allergiesMedicalNotes=";
    
    /** key to hold string studentId. */
    private static final String STUDENT_ID = "studentId=";
    
    /**
     * Holds studentId.
     */
    private int studentId;
    
    /**
     * Holds student.
     */
    private Student student;
    
    /**
     * Holds allergiesMedicalNotes.
     */
    private String allergiesMedicalNotes;
    
    /**
     * Holds disabilityInfo.
     */
    private String disabilityInfo;
    
    /**
     * Holds hearingImpairment.
     */
    private String hearingImpairment;
    
    /**
     * Holds visualImpairment.
     */
    private String visualImpairment;
    
    /**
     * Holds speechDifficulties.
     */
    private String speechDifficulties;
    
    /**
     * Holds dyslexia.
     */
    private String dyslexia;
    
    /**
     * Holds physicalDisabilities.
     */
    private String physicalDisabilities;
    
    /**
     * Holds fits.
     */
    private String fits;
    
    /**
     * Holds behaviourDifficulties.
     */
    private String behaviourDifficulties;
    
    /**
     * Holds otherInfo.
     */
    private String otherInfo;
    
    /**
     * @return the studentId
     */
    public int getStudentId() {
    
        return studentId;
    }
    
    /**
     * @param intStudentId the studentId to set
     */
    public void setStudentId(int intStudentId) {
    
        this.studentId = intStudentId;
    }
    
    /**
     * @return the student
     */
    @JsonIgnore
    public Student getStudent() {
    
        return student;
    }
    
    /**
     * @param objStudent the student to set
     */
    @JsonIgnore
    public void setStudent(Student objStudent) {
    
        this.student = objStudent;
    }
    
    /**
     * @return the allergiesMedicalNotes
     */
    public String getAllergiesMedicalNotes() {
    
        return allergiesMedicalNotes;
    }
    
    /**
     * @param strAllergiesMedicalNotes the allergiesMedicalNotes to set
     */
    public void setAllergiesMedicalNotes(String strAllergiesMedicalNotes) {
    
        this.allergiesMedicalNotes = strAllergiesMedicalNotes;
    }
    
    /**
     * @return the disabilityInfo
     */
    public String getDisabilityInfo() {
    
        return disabilityInfo;
    }
    
    /**
     * @param strDisabilityInfo the disabilityInfo to set
     */
    public void setDisabilityInfo(String strDisabilityInfo) {
    
        this.disabilityInfo = strDisabilityInfo;
    }
    
    /**
     * @return the hearingImpairment
     */
    public String getHearingImpairment() {
    
        return hearingImpairment;
    }
    
    /**
     * @param strHearingImpairment the hearingImpairment to set
     */
    public void setHearingImpairment(String strHearingImpairment) {
    
        this.hearingImpairment = strHearingImpairment;
    }
    
    /**
     * @return the visualImpairment
     */
    public String getVisualImpairment() {
    
        return visualImpairment;
    }
    
    /**
     * @param strVisualImpairment the visualImpairment to set
     */
    public void setVisualImpairment(String strVisualImpairment) {
    
        this.visualImpairment = strVisualImpairment;
    }
    
    /**
     * @return the speechDifficulties
     */
    public String getSpeechDifficulties() {
    
        return speechDifficulties;
    }
    
    /**
     * @param strSpeechDifficulties the speechDifficulties to set
     */
    public void setSpeechDifficulties(String strSpeechDifficulties) {
    
        this.speechDifficulties = strSpeechDifficulties;
    }
    
    /**
     * @return the dyslexia
     */
    public String getDyslexia() {
    
        return dyslexia;
    }
    
    /**
     * @param strDyslexia the dyslexia to set
     */
    public void setDyslexia(String strDyslexia) {
    
        this.dyslexia = strDyslexia;
    }
    
    /**
     * @return the physicalDisabilities
     */
    public String getPhysicalDisabilities() {
    
        return physicalDisabilities;
    }
    
    /**
     * @param strPhysicalDisabilities the physicalDisabilities to set
     */
    public void setPhysicalDisabilities(String strPhysicalDisabilities) {
    
        this.physicalDisabilities = strPhysicalDisabilities;
    }
    
    /**
     * @return the fits
     */
    public String getFits() {
    
        return fits;
    }
    
    /**
     * @param strFits the fits to set
     */
    public void setFits(String strFits) {
    
        this.fits = strFits;
    }
    
    /**
     * @return the behaviourDifficulties
     */
    public String getBehaviourDifficulties() {
    
        return behaviourDifficulties;
    }
    
    /**
     * @param strBehaviourDifficulties the behaviourDifficulties to set
     */
    public void setBehaviourDifficulties(String strBehaviourDifficulties) {
    
        this.behaviourDifficulties = strBehaviourDifficulties;
    }
    
    /**
     * @return the otherInfo
     */
    public String getOtherInfo() {
    
        return otherInfo;
    }
    
    /**
     * @param strOtherInfo the otherInfo to set
     */
    public void setOtherInfo(String strOtherInfo) {
    
        this.otherInfo = strOtherInfo;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return STUDENT_ID + this.studentId + ALLERGIES_MEDICAL_NOTES + this.allergiesMedicalNotes + DISABILITY_INFO
                + this.disabilityInfo + HEARING_IMPAIRMENT + this.hearingImpairment + VISUAL_IMPAIRMENT
                + this.visualImpairment + SPEECH_DIFFICULTIES + this.speechDifficulties + DYSLEXIA + this.dyslexia
                + PHYSICAL_DISABILITIES + this.physicalDisabilities + FITS + this.fits + BEHAVIOUR_DIFFICULTIES
                + this.behaviourDifficulties + OTHER_INFO + this.otherInfo;
    }
    
}
