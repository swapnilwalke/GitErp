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
 * This class represents all properties of the attendance of a student.
 *
 * @author Virtusa Corporation
 */
public class StudentAttendanceReport implements Serializable {

    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;

    /** Represents the date of the attendance. */
    private Date date;

    /**
     * Represents the status of the notification when the student is absent.
     */
    private String notified;

    /** Represents the reason for the absent. */
    private String reason;

    /** Represent the customized constructor. */

    public StudentAttendanceReport() {

    }

    /**
     * Returns the date of the present.
     *
     * @return - the date of the present.
     */
    public final Date getDate() {

        if (date != null) {
            return (Date) date.clone();
        }

        return null;
    }

    /**
     * Sets the date of the attendance.
     *
     * @param dDate - date of the absent.
     */
    public final void setDate(final Date dDate) {

        if (dDate != null) {

            this.date = (Date) dDate.clone();
        }
    }

    /**
     * Returns the status of the notification.
     *
     * @return - the status of the notification.
     */
    public final String getNotified() {

        return notified;
    }

    /**
     * Sets the status of the notification.
     *
     * @param strNotified - the status of the notification.
     */
    public final void setNotified(final String strNotified) {

        this.notified = strNotified;
    }

    /**
     * Returns the reason for the absent.
     *
     * @return - the reason for the absent.
     */
    public final String getReason() {

        return reason;
    }

    /**
     * Sets the reason for the absent.
     *
     * @param strReason - the reason for the absent.
     */
    public final void setReason(final String strReason) {

        this.reason = strReason;
    }
}
