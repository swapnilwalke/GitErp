/*
 * © 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND 

 * PROPRIETARY INFORMATION The information contained herein (the 

 * 'Proprietary Information') is highly confidential and proprietary to and 

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published, 

 * communicated, disclosed or divulged to any person, firm, corporation or 

 * other legal entity, directly or indirectly, without the prior written 

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.api.dto;

/**
 * @author Virtusa Corporation
 */

public class TeacherWisePresentAbsentTemplate {
    
    /**
     * property teacherRegNo type java.lang.String. used to track a particular teacher.
     */
    private String teacherRegNo;
    
    /**
     * property dateFrom type java.lang.String. used to track attendance starting from this day.
     */
    private String dateFrom;
    
    /**
     * property dateTo type java.lang.String. used to track attendance ending from this day.
     */
    private String dateTo;
    
    /**
     * The Default constructor.
     */
    public TeacherWisePresentAbsentTemplate() {
    
    }
    
    /**
     * @return teacherRegNo
     */
    public String getTeacherRegNo() {
    
        return teacherRegNo;
    }
    
    /**
     * @param teacherRegNoRef teacherRegNo to set.
     */
    public void setTeacherRegNo(String teacherRegNoRef) {
    
        this.teacherRegNo = teacherRegNoRef;
    }
    
    /**
     * @return dateFrom
     */
    public String getDateFrom() {
    
        return dateFrom;
    }
    
    /**
     * @param dateFromRef dateFrom to set.
     */
    public void setDateFrom(String dateFromRef) {
    
        this.dateFrom = dateFromRef;
    }
    
    /**
     * @return dateTo
     */
    public String getDateTo() {
    
        return dateTo;
    }
    
    /**
     * @param dateToRef to dateTo set.
     */
    public void setDateTo(String dateToRef) {
    
        this.dateTo = dateToRef;
    }
    
}
