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
import java.util.Date;

/**
 * This class represents all properties of a AttendanceShedular domain object.
 *
 * @author Virtusa Corporation
 */
public class AttendanceShedular implements Serializable {

    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;

    /** Represents the constant, time of the swipe. */
    private static final String TIME = "time";

    /** Represents the constant, date. */
    private static final String DATE = "date";

    /** Represents the constant, admissionNo. */
    private static final String ADDMISSION_NUMBER = "addmission number";

    /** Represents the admissionNo. */
    private String addmissionNo;

    /** Represents the date of the present. */
    private Date date;

    /** Represents the time. */
    private String timeIn;

    /** Represents the identification of the reader. */
    private String readerId;

    /** Represents the status of in or out time. */
    private double status;

    /**
     * Returns the admission number.
     *
     * @return - the admission number.
     */
    public String getAddmissionNo() {

        return addmissionNo;
    }

    /**
     * Sets the admission number.
     *
     * @param strAddmissionNo - the admission number.
     */
    public void setAddmissionNo(String strAddmissionNo) {

        this.addmissionNo = strAddmissionNo;
    }

    /**
     * Returns the date of the absent.
     *
     * @return - the date of the absent.
     */
    public Date getDate() {

        if (date != null) {
            return (Date) date.clone();
        }

        return null;
    }

    /**
     * Sets the date of the present.
     *
     * @param strDate - the date of the present.
     */
    public void setDate(final Date strDate) {

        if (strDate != null) {

            this.date = (Date) strDate.clone();
        }
    }

    /**
     * Returns the time of the in or out.
     *
     * @return - the time of the swipe in or out.
     */
    public String getTimeIn() {

        return timeIn;
    }

    /**
     * Sets the time.
     *
     * @param strTimeIn - time of the in or out.
     */
    public void setTimeIn(String strTimeIn) {

        this.timeIn = strTimeIn;
    }

    /**
     * Returns the status of the swipe.
     *
     * @return - the status of the swipe.
     */
    public double getStatus() {

        return status;
    }

    /**
     * Sets the status of the swipe.
     *
     * @param dStatus - the in or out status.
     */
    public void setStatus(double dStatus) {

        this.status = dStatus;
    }

    /**
     * Represents the identification number of the reader.
     *
     * @return - the identification number of the reader.
     */
    public String getReaderId() {

        return readerId;
    }

    /**
     * Sets the identification number of the reader.
     *
     * @param strReaderIdValue - identification number of the reader.
     */
    public void setReaderId(String strReaderIdValue) {

        this.readerId = strReaderIdValue;
    }

    /**
     * Returns the details for the current object.
     *
     * @return - the current object details.
     */
    public final String toString() {

        return ADDMISSION_NUMBER + this.addmissionNo + DATE + this.date + TIME + this.timeIn;
    }

}
