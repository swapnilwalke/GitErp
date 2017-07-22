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
 * StaffClubSociety class associates all the properties of StaffClubSociety domain object.
 *
 * @author Virtusa Corporation
 */

public class StaffClubSociety extends BaseDomain {

    /** The Constant MODIFIED_DATE_TIME. */
    private static final String MODIFIED_DATE_TIME = " modified date/time = ";

    /** The Constant YEAR2. */
    private static final String YEAR2 = " year ";

    /** The Constant STATUS2. */
    private static final String STATUS2 = " status ";

    /** The Constant POSITION_ID. */
    private static final String POSITION_ID = " position id ";

    /** The Constant STAFF_ID. */
    private static final String STAFF_ID = " staff id ";

    /** The Constant STAFF_CLUB_SOCIETY_ID. */
    private static final String STAFF_CLUB_SOCIETY_ID = " staff club society id = ";

    /** Holds the default serial id. */
    private static final long serialVersionUID = 1L;

    /** Holds unique id for StaffClubSociety. */
    private int staffClubSocietyId;

    /** Holds staffId for StaffClubSociety. */
    private int staffId;

    /** Holds clubSocietyId for StaffClubSociety. */
    private int clubSocietyId;

    /** Holds positionId for StaffClubSociety. */
    private int positionId;

    /** Holds year for StaffClubSociety. */
    private Date year;

    /** Holds status for StaffClubSociety. */
    private byte status;

    /**
     * Class constructor.
     */
    public StaffClubSociety() {

        super();
    }

    /**
     * Overloaded constructor.
     *
     * @param intStaffClubSocietyId - type integer
     * @param intStaffId - type integer
     * @param intClubSocietyId - type integer
     * @param intPositionId - type integer
     * @param yearVal - type date
     * @param statusVal - type byte
     */
    public StaffClubSociety(final int intStaffClubSocietyId, final int intStaffId, final int intClubSocietyId,
            final int intPositionId, final Date yearVal, final byte statusVal) {

        super();
        this.staffClubSocietyId = intStaffClubSocietyId;
        this.staffId = intStaffId;
        this.clubSocietyId = intClubSocietyId;
        this.positionId = intPositionId;
        if (yearVal != null) {
            this.year = (Date) yearVal.clone();
        }
        this.status = statusVal;
    }

    /**
     * Get the unique id of staffClubSocietyId for StaffClubSociety.
     *
     * @return the staffClubSocietyId
     */
    public int getStaffClubSocietyId() {

        return staffClubSocietyId;
    }

    /**
     * Set the unique id of staffClubSocietyId for StaffClubSociety.
     *
     * @param intStaffClubSocietyId the staffClubSocietyId to set
     */
    public void setStaffClubSocietyId(final int intStaffClubSocietyId) {

        this.staffClubSocietyId = intStaffClubSocietyId;
    }

    /**
     * Get the staffId for StaffClubSociety.
     *
     * @return the staffId
     */
    public int getStaffId() {

        return staffId;
    }

    /**
     * Set the staffId for StaffClubSociety.
     *
     * @param intStaffId the staffId to set
     */
    public void setStaffId(final int intStaffId) {

        this.staffId = intStaffId;
    }

    /**
     * Get the clubSocietyId for StaffClubSociety.
     *
     * @return the clubSocietyId
     */
    public int getClubSocietyId() {

        return clubSocietyId;
    }

    /**
     * Set the clubSocietyId for StaffClubSociety.
     *
     * @param intClubSocietyId the clubSocietyId to set
     */
    public void setClubSocietyId(final int intClubSocietyId) {

        this.clubSocietyId = intClubSocietyId;
    }

    /**
     * Get the positionId for StaffClubSociety.
     *
     * @return the positionId
     */
    public int getPositionId() {

        return positionId;
    }

    /**
     * Set the positionId for StaffClubSociety.
     *
     * @param intPositionId the positionId to set
     */
    public void setPositionId(final int intPositionId) {

        this.positionId = intPositionId;
    }

    /**
     * Get the year for StaffClubSociety.
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
     * Set the year for StaffClubSociety.
     *
     * @param yearVal the year to set
     */
    public void setYear(final Date yearVal) {

        if (yearVal != null) {

            this.year = (Date) yearVal.clone();
        }
    }

    /**
     * Get the status for StaffClubSociety.
     *
     * @return the status
     */
    public byte getStatus() {

        return status;
    }

    /**
     * Set the status for StaffClubSociety.
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

        return STAFF_CLUB_SOCIETY_ID + this.staffClubSocietyId + STAFF_ID + this.staffId + POSITION_ID
                + this.positionId + STATUS2 + this.status + YEAR2 + this.year + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }

}
