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
 * StaffExternalActivity is to associates all the properties of StaffExternalActivity domain object.
 *
 * @author Virtusa Corporation
 */
public class StaffExternalActivity extends BaseDomain {

    /** Holds the default serial id. */
    private static final long serialVersionUID = 1L;

    /** Holds unique id for StaffExternalActivity. */
    private int staffExternalActivityId;

    /** Holds staffId for StaffExternalActivity. */
    private int staffId;

    /** Holds externalActivity for StaffExternalActivity. */
    private String externalActivity;

    /** Holds achievement for StaffExternalActivity. */
    private String achievement;

    /** Holds year for StaffExternalActivity. */
    private Date year;

    /** Holds status for StaffExternalActivity. */
    private byte status;

    /**
     * Default constructor.
     */
    public StaffExternalActivity() {

        super();
    }

    /**
     * Overloaded constructor.
     *
     * @param intStaffExternalActivityId - holds integer type
     * @param intStaffId - holds integer type
     * @param strExternalActivity - holds string type
     * @param strAchievement - holds string type
     * @param yearVal - holds date type
     * @param statusVal - holds byte type
     */
    public StaffExternalActivity(final int intStaffExternalActivityId, final int intStaffId,
            final String strExternalActivity, final String strAchievement, final Date yearVal, final byte statusVal) {

        super();
        this.staffExternalActivityId = intStaffExternalActivityId;
        this.staffId = intStaffId;
        this.externalActivity = strExternalActivity;
        this.achievement = strAchievement;
        if (yearVal != null) {
            this.year = (Date) yearVal.clone();
        }
        this.status = statusVal;
    }

    /**
     * Get the unique id of staffExternalActivityId for StaffExternalActivity.
     *
     * @return the staffExternalActivityId
     */
    public int getStaffExternalActivityId() {

        return staffExternalActivityId;
    }

    /**
     * Set the unique id of staffExternalActivityId for StaffExternalActivity.
     *
     * @param intStaffExternalActivityId the staffExternalActivityId to set
     */
    public void setStaffExternalActivityId(final int intStaffExternalActivityId) {

        this.staffExternalActivityId = intStaffExternalActivityId;
    }

    /**
     * Get the staff id for StaffExternalActivity.
     *
     * @return the staffId
     */
    public int getStaffId() {

        return staffId;
    }

    /**
     * Set the staff id for StaffExternalActivity.
     *
     * @param intStaffId the staffId to set
     */
    public void setStaffId(final int intStaffId) {

        this.staffId = intStaffId;
    }

    /**
     * Get the externalActivity for StaffExternalActivity.
     *
     * @return the externalActivity
     */
    public String getExternalActivity() {

        return externalActivity;
    }

    /**
     * Set the externalActivity for StaffExternalActivity.
     *
     * @param strExternalActivity the externalActivity to set
     */
    public void setExternalActivity(final String strExternalActivity) {

        this.externalActivity = strExternalActivity;
    }

    /**
     * Get the achievement for StaffExternalActivity.
     *
     * @return the achievement
     */
    public String getAchievement() {

        return achievement;
    }

    /**
     * Get the achievement for StaffExternalActivity.
     *
     * @param strAchievement the achievement to set
     */
    public void setAchievement(final String strAchievement) {

        this.achievement = strAchievement;
    }

    /**
     * Get the year for StaffExternalActivity.
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
     * Set the year for StaffExternalActivity.
     *
     * @param yearVal the year to set
     */
    public void setYear(final Date yearVal) {

        if (yearVal != null) {

            this.year = (Date) yearVal.clone();
        }
    }

    /**
     * Get the status for StaffExternalActivity.
     *
     * @return the status
     */
    public byte getStatus() {

        return status;
    }

    /**
     * Set the status for StaffExternalActivity.
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

        return " staff external activity id = " + this.staffExternalActivityId + " staff id " + this.staffId
                + " external activity " + this.externalActivity + " achievement " + this.achievement + " status "
                + this.status + " year " + this.year + " modified date/time = " + this.getModifiedTime();
    }
}
