
package com.virtusa.akura.api.dto;

import java.io.Serializable;

/**
 * Class to support retrieval of staff attendance.
 */

public class StaffAttendance implements Serializable {
    
    /**
     * Constant definition.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * property dateType of type String. used to store the type of day of an employee .
     */
    private String dateType;
    
    /**
     * property date of type java.sql.Date. used to track attendance of a specific day.
     */
    private String date;
    
    /**
     * property employeeID of type String. used to store the id of an employee .
     */
    private String employeeID;
    
    /**
     * property employeeName of type String. used to store the name of an employee .
     */
    private String employeeName;
    
    /**
     * property employeeNameWithInitials of type String. used to store the name with initials of an employee .
     */
    private String employeeNameWithInitials;
    
    /**
     * property timeIn of type java.sql.Time. used to store the time in of an employee .
     */
    private String timeIn;
    
    /**
     * property timeOut of type java.sql.Time. used to store the time out of an employee .
     */
    
    private String timeOut;
    
    /**
     * property dateOfDeparture of type java.sql.Time. used to store the departure date of an employee .
     */
    private String dateOfDeparture;
    
    /**
     * property reason of type String. used to store the reason of the leave .
     */
    private String reason;
    
    /**
     * property numOfHours of type long. used to store the time period spent by a student on a particular day.
     */
    private double numOfHours;
    
    /**
     * The Default constructor.
     */
    public StaffAttendance() {
    
    }
    
    /**
     * @return the date
     */
    public String getDate() {
    
        return date;
    }
    
    /**
     * @param dateRef the date to set
     */
    public void setDate(String dateRef) {
    
        this.date = dateRef;
    }
    
    /**
     * @return the employeeID
     */
    public String getEmployeeID() {
    
        return employeeID;
    }
    
    /**
     * @param employeeIDRef the employeeID to set
     */
    public void setEmployeeID(String employeeIDRef) {
    
        this.employeeID = employeeIDRef;
    }
    
    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
    
        return employeeName;
    }
    
    /**
     * @param employeeNameRef the employeeName to set
     */
    public void setEmployeeName(String employeeNameRef) {
    
        this.employeeName = employeeNameRef;
    }
    
    /**
     * @return the timeIn
     */
    public String getTimeIn() {
    
        return timeIn;
    }
    
    /**
     * @param timeInRef the timeIn to set
     */
    public void setTimeIn(String timeInRef) {
    
        this.timeIn = timeInRef;
    }
    
    /**
     * @return the timeOut
     */
    public String getTimeOut() {
    
        return timeOut;
    }
    
    /**
     * @param timeOutRef the timeOut to set
     */
    public void setTimeOut(String timeOutRef) {
    
        this.timeOut = timeOutRef;
    }
    
    /**
     * @return reason
     */
    public String getReason() {
    
        return reason;
    }
    
    /**
     * @param reasonRef reason to set.
     */
    public void setReason(String reasonRef) {
    
        this.reason = reasonRef;
    }
    
    /**
     * @return the numOfHours
     */
    public double getNumOfHours() {
    
        return numOfHours;
    }
    
    /**
     * @param numOfHoursRef the numOfHours to set
     */
    public void setNumOfHours(double numOfHoursRef) {
    
        this.numOfHours = numOfHoursRef;
    }
    
    /**
     * @return the dateOfDeparture
     */
    public String getDateOfDeparture() {
    
        return dateOfDeparture;
    }
    
    /**
     * @param dateOfDepartureObj the dateOfDeparture to set
     */
    public void setDateOfDeparture(String dateOfDepartureObj) {
    
        this.dateOfDeparture = dateOfDepartureObj;
    }
    
    /**
     * @return the dateType
     */
    public String getDateType() {
    
        return dateType;
    }
    
    /**
     * @param dateTypeRef the employee day type to set
     */
    public void setDateType(String dateTypeRef) {
    
        this.dateType = dateTypeRef;
    }

    /**
     * 
     * @return  the employeeNameWithInitials 
     */
	public String getEmployeeNameWithInitials() {
		return employeeNameWithInitials;
	}
	
	/**
	 * 
	 * @param employeeNameWithInitial - Employee Name with initials
	 */
	public void setEmployeeNameWithInitials(String employeeNameWithInitial) {
		this.employeeNameWithInitials = employeeNameWithInitial;
	}
    
    
    
}
