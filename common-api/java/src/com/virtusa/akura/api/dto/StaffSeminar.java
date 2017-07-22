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
 * StaffSeminar is to associates all the properties of StaffSeminar domain object.
 *
 * @author Virtusa Corporation
 */

public class StaffSeminar extends BaseDomain {

    /** Holds the default serial id. */
    private static final long serialVersionUID = 1L;

    /** Holds unique id for StaffSeminar. */
    private int staffSeminarId;

    /** Holds staff id for StaffSeminar. */
    private int staffId;

    /** Holds seminar id for StaffSeminar. */
    private int seminarId;

    /** Holds description for StaffSeminar. */
    private String description;

    /** Holds year for StaffSeminar. */
    private Date year;

    /** Holds status for StaffSeminar. */
    private byte status;

    /**
     * Default constructor.
     */
    public StaffSeminar() {

        super();
    }

    /**
     * Overloaded constructor.
     *
     * @param intStaffSeminarId - holds integer type
     * @param intStaffId - holds integer type
     * @param intSeminarId - holds integer type
     * @param yearVal - holds date type
     * @param statusVal - holds byte type
     */
    public StaffSeminar(final int intStaffSeminarId, final int intStaffId, final int intSeminarId, final Date yearVal,
            final byte statusVal) {

        super();
        this.staffSeminarId = intStaffSeminarId;
        this.staffId = intStaffId;
        this.seminarId = intSeminarId;
        if (yearVal != null) {
            this.year = (Date) yearVal.clone();
        }
        this.status = statusVal;
    }

    /**
     * Get the unique id of staffSeminarId for StaffSeminar.
     *
     * @return the staffSeminarId
     */
    public int getStaffSeminarId() {

        return staffSeminarId;
    }

    /**
     * Set the unique id of staffSeminarId for StaffSeminar.
     *
     * @param intStaffSeminarId the staffSeminarId to set
     */
    public void setStaffSeminarId(final int intStaffSeminarId) {

        this.staffSeminarId = intStaffSeminarId;
    }

    /**
     * Get the staff id for StaffSeminar.
     *
     * @return the staffId
     */
    public int getStaffId() {

        return staffId;
    }

    /**
     * Set the staff id for StaffSeminar.
     *
     * @param intStaffId the staffId to set
     */
    public void setStaffId(final int intStaffId) {

        this.staffId = intStaffId;
    }

    /**
     * Get the seminar id for StaffSeminar.
     *
     * @return the seminarId
     */
    public int getSeminarId() {

        return seminarId;
    }

    /**
     * Get the seminar id for StaffSeminar.
     *
     * @param intSeminarId the seminarId to set
     */
    public void setSeminarId(final int intSeminarId) {

        this.seminarId = intSeminarId;
    }

    /**
     * Get the description for StaffSeminar.
     *
     * @return the description
     */
    public String getDescription() {

        return description;
    }

    /**
     * Set the description for StaffSeminar.
     *
     * @param strDescription the description to set
     */
    public void setDescription(final String strDescription) {

        this.description = strDescription;
    }

    /**
     * Get the year for StaffSeminar.
     *
     * @return the year
     */
    public Date getYear() {

        if (year != null) {
            return (Date) year.clone();
        }

        return null;
    }

    /**
     * Set the year for StaffSeminar.
     *
     * @param yearVal the year to set
     */
    public void setYear(final Date yearVal) {

        if (yearVal != null) {

            this.year = (Date) yearVal.clone();
        }
    }

    /**
     * Get the status for StaffSeminar.
     *
     * @return the status
     */
    public byte getStatus() {

        return status;
    }

    /**
     * Set the status for StaffSeminar.
     *
     * @param statusVal the status to set
     */
    public void setStatus(final byte statusVal) {

        this.status = statusVal;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return - the current object details.
     */
    public String toString() {

        return " staff seminar id = " + this.staffSeminarId + " staff id " + this.staffId + " seminar id "
                + this.seminarId + " status " + this.status + " year " + this.year + " modified date/time = "
                + this.getModifiedTime();
    }
}
