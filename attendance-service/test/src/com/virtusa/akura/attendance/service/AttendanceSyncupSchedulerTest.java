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

import org.junit.Before;
import org.junit.Test;

import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.attendance.BaseAttendanceTest;

/**
 * This test class, tests all the functionalities based on the attendance schedular.
 *
 * @author Virtusa Corporation
 */
public class AttendanceSyncupSchedulerTest extends BaseAttendanceTest {

    /** Injects an instance of AttendanceSyncupScheduler. */
    private AttendanceSyncupScheduler fixedDelayTask;

    /**  @throws Exception - The exception details that occurred when processing. */
    @Before
    public void setUp() throws Exception {
        fixedDelayTask = new AttendanceSyncupSchedulerStudentImpl();
    }

    /**
     * Test method for {@link com.virtusa.AbstractAttendanceSyncupSchedular.attendance.service.
     * AttendanceSyncupSchedularAbstract#extract()}.
     *
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Test
    public void testExtract() throws AkuraAppException {
        fixedDelayTask.extract();
    }
}
