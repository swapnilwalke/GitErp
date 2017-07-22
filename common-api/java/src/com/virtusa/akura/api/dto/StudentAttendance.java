
package com.virtusa.akura.api.dto;

import java.io.Serializable;

/**
 * The class that is mapped to StudentAttendance View.
 */
public class StudentAttendance implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * property dayname of type java.lang.String. used to store the day name of a student's attendance.
     */
    private String dayname;
    
    /**
     * property studentID of type java.lang.String. used to store the ID of a student .
     */
    private String studentID;
    
    /**
     * property studentName of type java.lang.String. used to store the name of a student .
     */
    private String studentName;
    
    /**
     * property date of type java.java.lang.String. used to store the date considered for the
     * StudentAttendance.
     */
    private String date;
    
    /**
     * property nameWithInitials of type java.lang.String. used to store the name with initials of a student .
     */
    private String nameWithInitials;
    
	/**
     * property timeIn of type java.lang.String. used to store the time in value of a student on a particular
     * day.
     */
    private String timeIn;
    
    /**
     * property timeOut of type java.lang.String. used to store the time out value of a student on a
     * particular day.
     */
    private String timeOut;
    
    /**
     * property numOfHours of type long. used to store the time period spent by a student on a particular day.
     */
    private double numOfHours;
    
    /**
     * The Default constructor.
     */
    public StudentAttendance() {
    
    }
    
    /**
     * @return the dayname
     */
    public String getDayname() {
    
        return dayname;
    }
    
    /**
     * used to set the day name.
     * 
     * @param objDaynameStr the daynameStr to set
     */
    public void setDayname(String objDaynameStr) {
    
        this.dayname = objDaynameStr;
    }
    
    /**
     * @return the studentID
     */
    public String getStudentID() {
    
        return studentID;
    }
    
    /**
     * @param studentIDStr the studentID to set
     */
    public void setStudentID(String studentIDStr) {
    
        this.studentID = studentIDStr;
    }
    
    /**
     * @return the studentName
     */
    public String getStudentName() {
    
        return studentName;
    }
    
    /**
     * @param studentNameStr the studentName to set
     */
    public void setStudentName(String studentNameStr) {
    
        this.studentName = studentNameStr;
    }
    
    /**
     * @return the date
     */
    public String getDate() {
    
        return date;
    }
    
    /**
     * @param dateStr the date to set
     */
    public void setDate(String dateStr) {
    
        this.date = dateStr;
    }
    
    /**
     * @return the timeIn
     */
    public String getTimeIn() {
    
        return timeIn;
    }
    
    /**
     * @param timeInStr the timeIn to set
     */
    public void setTimeIn(String timeInStr) {
    
        this.timeIn = timeInStr;
    }
    
    /**
     * @return the timeOut
     */
    public String getTimeOut() {
    
        return timeOut;
    }
    
    /**
     * @param timeOutStr the timeOut to set
     */
    public void setTimeOut(String timeOutStr) {
    
        this.timeOut = timeOutStr;
    }
    
    /**
     * @return the numOfHours
     */
    public double getNumOfHours() {
    
        // BigDecimal bd = new BigDecimal(this.numOfHours).setScale(2, RoundingMode.HALF_UP);
        // this.numOfHours=bd.doubleValue();
        // this.numOfHours.setScale(2,BigDecimal.ROUND_HALF_UP);
        return this.numOfHours;
    }
    
    /**
     * @param numOfHoursDouble the numOfHours to set
     */
    public void setNumOfHours(double numOfHoursDouble) {
    
        // BigDecimal bd = new BigDecimal(numOfHoursDouble).setScale(2, RoundingMode.HALF_UP);
        this.numOfHours = numOfHoursDouble;
    }
    
    /**
     * @return the NameWithInitials
     */
    public String getNameWithInitials() {
		return nameWithInitials;
	}

    /**
     * @param nameWithInitial the nameWithInitial  to set
     */
	public void setNameWithInitials(String nameWithInitial) {
		this.nameWithInitials = nameWithInitial;
	}
    
}
