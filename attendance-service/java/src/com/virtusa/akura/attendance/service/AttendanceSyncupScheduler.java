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

package com.virtusa.akura.attendance.service;

import com.virtusa.akura.api.exception.AkuraAppException;


/**
 * This schedules to copy the data of the student and teacher attendance data into the database.
 *
 * @author Virtusa Corporation
 */
public interface AttendanceSyncupScheduler {

    /**
     * Schedules to extract the data from the spreadsheet.
     *
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void extract() throws AkuraAppException;

    /**
     * This method is used to read the data's from an excel file.
     *
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void transform() throws AkuraAppException;

    /**
     * Schedules to load the data from the text file into the relevant table.
     *
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void load() throws AkuraAppException;

    /**
     * Schedules to clean the copied files.
     *
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void clean() throws AkuraAppException;
}

