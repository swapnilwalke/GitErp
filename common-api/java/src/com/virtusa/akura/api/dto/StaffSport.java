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
 * StaffSport is to associates all the properties of StaffSport domain object.
 *
 * @author Virtusa Corporation
 */

public class StaffSport extends BaseDomain {

    /** Holds the default serial id. */
    private static final long serialVersionUID = 1L;

    /** Holds unique id for StaffSport. */
    private int staffSportId;

    /** Holds staff id for StaffSport. */
    private int staffId;

    /** Holds sport Category id for StaffSport. */
    private int sportCategoryId;

    /** Holds position id for StaffSport. */
    private int positionId;

    /** Holds description for StaffSport. */
    private String description;

    /** Holds year for StaffSport. */
    private Date year;

    /** Holds status for StaffSport. */
    private byte status;

    /**
     * Default constructor.
     */
    public StaffSport() {

        super();
    }

    /**
     * Overloaded constructor.
     *
     * @param intStaffSportId - holds integer type
     * @param intStaffId - holds integer type
     * @param intSportCategoryId - holds integer type
     * @param intPositionId - holds integer type
     * @param yearVal - holds date type
     * @param statusVal - holds byte type
     */
    public StaffSport(final int intStaffSportId, final int intStaffId, final int intSportCategoryId,
            final int intPositionId, final Date yearVal, final byte statusVal) {

        super();
        this.staffSportId = intStaffSportId;
        this.staffId = intStaffId;
        this.sportCategoryId = intSportCategoryId;
        this.positionId = intPositionId;
        if (yearVal != null) {
            this.year = (Date) yearVal.clone();
        }
        this.status = statusVal;
    }

    /**
     * Get the unique id of staffSportId for StaffSport.
     *
     * @return the staffSportId
     */
    public int getStaffSportId() {

        return staffSportId;
    }

    /**
     * Set the unique id of staffSportId for StaffSport.
     *
     * @param intStaffSportId the staffSportId to set
     */
    public void setStaffSportId(final int intStaffSportId) {

        this.staffSportId = intStaffSportId;
    }

    /**
     * Get the staff id for StaffSport.
     *
     * @return the staffId
     */
    public int getStaffId() {

        return staffId;
    }

    /**
     * Set the staff id for StaffSport.
     *
     * @param intStaffId the staffId to set
     */
    public void setStaffId(final int intStaffId) {

        this.staffId = intStaffId;
    }

    /**
     * Get the sport Category id for StaffSport.
     *
     * @return the sportCategoryId
     */
    public int getSportCategoryId() {

        return sportCategoryId;
    }

    /**
     * Set the sport Category id for StaffSport.
     *
     * @param intSportCategoryId the sportCategoryId to set
     */
    public void setSportCategoryId(final int intSportCategoryId) {

        this.sportCategoryId = intSportCategoryId;
    }

    /**
     * Get the position id for StaffSport.
     *
     * @return the positionId
     */
    public int getPositionId() {

        return positionId;
    }

    /**
     * Set the position id for StaffSport.
     *
     * @param intPositionId the positionId to set
     */
    public void setPositionId(final int intPositionId) {

        this.positionId = intPositionId;
    }

    /**
     * Get the description for StaffSport.
     *
     * @return the description
     */
    public String getDescription() {

        return description;
    }

    /**
     * Set the description for StaffSport.
     *
     * @param strDescription the description to set
     */
    public void setDescription(final String strDescription) {

        this.description = strDescription;
    }

    /**
     * Get the year for StaffSport.
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
     * Set the year for StaffSport.
     *
     * @param yearVal the year to set
     */
    public void setYear(final Date yearVal) {

        if (yearVal != null) {

            this.year = (Date) yearVal.clone();
        }
    }

    /**
     * Get the status for StaffSport.
     *
     * @return the status
     */
    public byte getStatus() {

        return status;
    }

    /**
     * Set the status for StaffSport.
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

        return " staff sport id = " + this.staffSportId + " staff id " + this.staffId + " sport id "
                + this.sportCategoryId + " position id " + this.positionId + " status " + this.status + " year "
                + this.year + " modified date/time = " + this.getModifiedTime();
    }

}
