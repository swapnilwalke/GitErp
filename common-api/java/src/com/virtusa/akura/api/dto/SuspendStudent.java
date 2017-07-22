
package com.virtusa.akura.api.dto;

import java.util.Date;

/**
 * @author Virtusa Corporation
 */

public class SuspendStudent extends BaseDomain {
    
    /** Holds serial version id. */
    private static final long serialVersionUID = 1L;
    
    /** Holds suspend Student Id for suspendStudent object. */
    private int suspendStudentId;
    
    /** Holds the student for suspendStudent object. */
    private Student student;
    
    /** Holds activated Date for suspendStudent object. */
    private Date activatedDate;
    
    /** Holds curtailedOrExtendedReason for suspendStudent object. */
    private String curtailedOrExtendedReason;
    
    /** Holds the date of suspend for suspendStudent object. */
    private Date fromDate;
    
    /** Holds the date to suspend for suspendStudent object. */
    private Date toDate;
    
    /** Holds the disiplinaryActionId for suspendStudent object. */
    private int disciplinaryActionId;
    
    /** Holds the description for suspendStudent object. */
    private String description;
    
    /** Holds the noOfDays for suspendStudent object. */
    private int noOfDays;
    
    /**
     * Get the ActivatedDate.
     * 
     * @return the activatedDate.
     */
    public Date getActivatedDate() {

        return activatedDate;
    }
    
    /**
     * Set the activatedDate.
     * 
     * @param activatedDateVal the activatedDate to set.
     */
    public void setActivatedDate(Date activatedDateVal) {

        this.activatedDate = activatedDateVal;
    }
    
    /**
     * Get the CurtailedOrExtendedReason.
     * 
     * @return the curtailedOrExtendedReason.
     */
    public String getCurtailedOrExtendedReason() {

        return curtailedOrExtendedReason;
    }
    
    /**
     * Set the curtailedOrExtendedReason.
     * 
     * @param curtailedOrExtendedReasonVal the curtailedOrExtendedReason to set.
     */
    public void setCurtailedOrExtendedReason(String curtailedOrExtendedReasonVal) {

        this.curtailedOrExtendedReason = curtailedOrExtendedReasonVal;
    }
    
    /**
     * Get the Suspend Student Id.
     * 
     * @return the suspendStudentId.
     */
    public int getSuspendStudentId() {

        return suspendStudentId;
    }
    
    /**
     * Set the Suspend Student Id.
     * 
     * @param suspendStudentIdVal - int the suspend Student Id to set.
     */
    public void setSuspendStudentId(int suspendStudentIdVal) {

        this.suspendStudentId = suspendStudentIdVal;
    }
    
    /**
     * Get the Student.
     * 
     * @return the student.
     */
    public Student getStudent() {

        return student;
    }
    
    /**
     * Set the student.
     * 
     * @param studentVal the student to set.
     */
    public void setStudent(Student studentVal) {

        this.student = studentVal;
    }
    
    /**
     * Get the fromDate.
     * 
     * @return the fromDate.
     */
    public Date getFromDate() {

        return fromDate;
    }
    
    /**
     * Set the fromDate.
     * 
     * @param fromDateVal the fromDate to set.
     */
    public void setFromDate(Date fromDateVal) {

        this.fromDate = fromDateVal;
    }
    
    /**
     * Get the toDate.
     * 
     * @return the toDate.
     */
    public Date getToDate() {

        return toDate;
    }
    
    /**
     * Set the toDate.
     * 
     * @param toDateVal the toDate to set.
     */
    public void setToDate(Date toDateVal) {

        this.toDate = toDateVal;
    }
    
    /**
     * Get the disciplinaryActionId.
     * 
     * @return the disciplinaryActionId.
     */
    public int getDisciplinaryActionId() {

        return disciplinaryActionId;
    }
    
    /**
     * Set the disciplinaryActionId.
     * 
     * @param disciplinaryActionIdVal the disciplinaryActionId to set.
     */
    public void setDisciplinaryActionId(int disciplinaryActionIdVal) {

        this.disciplinaryActionId = disciplinaryActionIdVal;
    }
    
    /**
     * Get the getDescription.
     * 
     * @return the getDescription.
     */
    public String getDescription() {

        return description;
    }
    
    /**
     * Set the description.
     * 
     * @param descriptionVal the description to set
     */
    public void setDescription(String descriptionVal) {

        this.description = descriptionVal;
    }
    
    /**
     * Get the noOfDays.
     * 
     * @return the noOfDays.
     */
    public int getNoOfDays() {

        return noOfDays;
    }
    
    /**
     * Set the noOfDays.
     * 
     * @param noOfDaysVal the noOfDays to set.
     */
    public void setNoOfDays(int noOfDaysVal) {

        this.noOfDays = noOfDaysVal;
    }
    
    /**
     * String attribute for suspendStudentId.
     */
    private static final String SUSPEND_STUDENT_ID = "SuspendStudent [suspendStudentId=";
    
    /**
     * String attribute for Student.
     */
    private static final String STUDENT = ", student=";
    
    /**
     * String attribute for Student.
     */
    private static final String ACTIVATE_DATE = ", activatedDate=";
    
    /**
     * String attribute for curtailedOrExtendedReason.
     */
    private static final String CURTAILED_OR_EXTENDED_REASON = ", curtailedOrExtendedReason=";
    
    /**
     * String attribute for fromDate.
     */
    private static final String FROM_DATE = ", fromDate=";
    
    /**
     * String attribute for toDate.
     */
    private static final String TO_DATE = ", toDate=";
    
    /**
     * String attribute for disciplinaryActionId.
     */
    private static final String DISCIPLINARY_ACTION_ID = ", disciplinaryActionId=";
    
    /**
     * String attribute for description.
     */
    private static final String DESCRIPTION = ", description=";
    
    /**
     * String attribute for description.
     */
    private static final String NO_OF_DAYS = ", noOfDays=";
    
    /** Default constructor for StaffPastService. */
    public SuspendStudent() {

        super();
    }
    
    /**
     * The SuspendStudent object description.
     * 
     * @return the string representation of SuspendStudent object.
     */
    
    /**
     * Override Object class hashCode method.
     * 
     * @return integer hashCode of this object.
     */
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + suspendStudentId;
        return result;
    }
    
    /**
     * The String representation of Suspend student domain object.
     * 
     * @return string representation of Suspend student.
     */
    @Override
    public String toString() {

        return SUSPEND_STUDENT_ID + suspendStudentId + STUDENT + student + ACTIVATE_DATE + activatedDate
                + CURTAILED_OR_EXTENDED_REASON + curtailedOrExtendedReason + FROM_DATE + fromDate + TO_DATE + toDate
                + DISCIPLINARY_ACTION_ID + disciplinaryActionId + DESCRIPTION + description + NO_OF_DAYS + noOfDays
                + "]";
    }
    
    /**
     * Override the equals method of the Object class.
     * 
     * @param obj - Object to be compared with.
     * @return true if both objects are equal.
     */
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SuspendStudent other = (SuspendStudent) obj;
        if (suspendStudentId != other.suspendStudentId)
            return false;
        return true;
    }
    
}
