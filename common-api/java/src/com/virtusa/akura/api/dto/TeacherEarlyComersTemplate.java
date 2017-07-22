
package com.virtusa.akura.api.dto;

/**
 * TeacherEarlyComersTemplate class.
 * 
 * @author Virtusa Corporation
 */
public class TeacherEarlyComersTemplate {
    
    /**
     * property dateFrom type java.lang.String. used to track start date of a date range.
     */
    private String dateFrom;
    
    /**
     * property dateTo type java.lang.String. used to track end date of a date range.
     */
    private String dateTo;
    
    /**
     * property timeInFrom type java.lang.String. used to track start time of a time slot.
     */
    private String timeInFrom;
    
    /**
     * property timeInTo type java.lang.String. used to track end time of a time slot.
     */
    private String timeInTo;
    
    /**
     * The Default constructor.
     */
    public TeacherEarlyComersTemplate() {
    
    }
    
    /**
     * @return the dateFrom
     */
    public String getDateFrom() {
    
        return dateFrom;
    }
    
    /**
     * @param objDateFrom the dateFrom to set
     */
    public void setDateFrom(String objDateFrom) {
    
        this.dateFrom = objDateFrom;
    }
    
    /**
     * @return the dateTo
     */
    public String getDateTo() {
    
        return dateTo;
    }
    
    /**
     * @param objDateTo the dateTo to set
     */
    public void setDateTo(String objDateTo) {
    
        this.dateTo = objDateTo;
    }
    
    /**
     * @return the timeInFrom
     */
    public String getTimeInFrom() {
    
        return timeInFrom;
    }
    
    /**
     * @param objTimeInFrom the timeInFrom to set
     */
    public void setTimeInFrom(String objTimeInFrom) {
    
        this.timeInFrom = objTimeInFrom;
    }
    
    /**
     * @return the timeInTo
     */
    public String getTimeInTo() {
    
        return timeInTo;
    }
    
    /**
     * @param objTimeInTo the timeInTo to set
     */
    public void setTimeInTo(String objTimeInTo) {
    
        this.timeInTo = objTimeInTo;
    }
    
}
