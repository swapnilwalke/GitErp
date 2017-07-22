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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.virtusa.akura.api.dto.AttendanceShedular;
import com.virtusa.akura.api.dto.DailyTeacherAttendance;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.attendance.dao.DailyTeacherAttendanceDao;
import com.virtusa.akura.staff.dao.StaffDao;
import com.virtusa.akura.util.PropertyReader;

/**
 * This Service class schedules to copy the data of the teacher attendance data into the database.
 *
 * @author Virtusa Corporation
 */
public class AttendanceSyncupSchedulerTeacherImpl extends AbstractAttendanceSyncupSchedular {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(AttendanceSyncupSchedulerTeacherImpl.class);

    /** Represents the default swipe out time. */
    private static final String DEFAULT_TIME_OUT = "default.swipe.out";

    /** Represents the default swipe in time. */
    private static final String DEFAULT_TIME_IN = "default.swipe.in";

    /** Represent the name of the property file. */
    private static final String SYSTEM_CONFIG = "systemConfig";

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** Represents the key for path of the staff related spreadsheet. */
    private static final String COPY_PROPERTY_STAFF_PATH = "schedular.copied.staff.directory";

    /** Represents the key for up to the student directory path. */
    private static final String STAFF_UPTO_DIR_PATH = "schedular.staff.dir.path";

    /** Represents the key for path of the spreadsheet. */
    private static final String STAFF_DIRECTORY_PATH = "schedular.excel.staff.folder";

    /** Represents the a set contains instances of DailyStudentAttendance. */
    private Set<DailyTeacherAttendance> staffDailyAttendance = new LinkedHashSet<DailyTeacherAttendance>();

    /** Represents an instance of DailyTeacherAttendanceDao. */
    private DailyTeacherAttendanceDao dailyTeacherAttendanceDao;

    /** Represents a property of the attendanceSyncupSchedular for the teacher. */
    private String teacherExcelPath;

    /** Represents an instance of StudentDao. */
    private StaffDao staffDao;

    /**
     * Injects value for the teacher bean.
     *
     * @param strTeacherExcelPath - a property for the teacher bean.
     */
    public void setTeacherExcelPath(String strTeacherExcelPath) {

        this.teacherExcelPath = strTeacherExcelPath;
    }

    /**
     * Returns value for the teacher bean.
     *
     * @return the teacher bean.
     */
    public String getTeacherExcelPath() {

        return teacherExcelPath;
    }

    /**
     * Injects an instance of StudentDao.
     *
     * @param staffStaffDao - an instance of StudentDao.
     */
    public void setStaffDao(StaffDao staffStaffDao) {

        this.staffDao = staffStaffDao;
    }

    /**
     * Injects an instance of DailyTeacherAttendanceDao.
     *
     * @param teaDailyTeacherAttendanceDao - an instance of DailyTeacherAttendanceDao.
     */
    public void setDailyTeacherAttendanceDao(DailyTeacherAttendanceDao teaDailyTeacherAttendanceDao) {

        this.dailyTeacherAttendanceDao = teaDailyTeacherAttendanceDao;
    }

    /** {@inheritDoc} */
    public void extract() throws AkuraAppException {

        if (getTeacherExcelPath() != null) {
            File folder =
                    new File(PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME) + getTeacherExcelPath());
            if (folder.exists()) {
                copyFiles(folder);
            } else {
                LOG.debug(AkuraConstant.DIRECTORY_DOES_NOT_EXIST);
            }
        }
    }

    /** {@inheritDoc} */
    public File getCopyDirLocation() {

        File file = null;
        if (getTeacherExcelPath() != null) {
            file = new File(PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STAFF_UPTO_DIR_PATH));
        }
        return file;
    }

    /** {@inheritDoc} */
    public void load() throws AkuraAppException {

        String threadName = Thread.currentThread().getName();
        LOG.info(threadName + AkuraConstant.SCHEDULAR_LOAD_BEGAN);
        dailyTeacherAttendanceDao.loadDataIntoDatabase(staffDailyAttendance);
        LOG.info(threadName + AkuraConstant.SCHEDULAR_LOAD_COMPLETED);
    }

    /** {@inheritDoc} */
    protected void formatFile(List<AttendanceShedular> attendanceList, Map<File, Boolean> rolbackStatus)
            throws AkuraAppException {

        String timeIn = PropertyReader.getPropertyValue(SYSTEM_CONFIG, DEFAULT_TIME_IN);
        String timeOut = PropertyReader.getPropertyValue(SYSTEM_CONFIG, DEFAULT_TIME_OUT);
        try {
            List<String> addmissionIdList = new ArrayList<String>(); // contains the admission numbers of the
            // AttendanceSchedular.
            List<Date> dateList = new ArrayList<Date>(); // contains the date of the AttendanceSchedular.

            for (AttendanceShedular attendanceShedular : attendanceList) {
                if (attendanceShedular != null) {
                    if (!(addmissionIdList.contains(attendanceShedular.getAddmissionNo()) && dateList
                            .contains(attendanceShedular.getDate()))) {
                        timeIn = attendanceShedular.getTimeIn();
                        String addmissionNo = attendanceShedular.getAddmissionNo();
                        Date date = attendanceShedular.getDate();

                        for (AttendanceShedular schedular : attendanceList) {
                            if (schedular.getAddmissionNo().equals(attendanceShedular.getAddmissionNo())
                                    && schedular.getDate().equals(attendanceShedular.getDate())) {
                                if (schedular.getStatus() == 1.0) {
                                    addmissionNo = schedular.getAddmissionNo();
                                    date = schedular.getDate();
                                    timeOut = schedular.getTimeIn();
                                }
                            }
                        }

                        String[] id = splitFromDot(addmissionNo);
                        int staffId = staffDao.findStaffIdForRegistrationNo(id[0]);
                        if (staffId != 0) {
                            addIntoAList(date, timeIn, timeOut, staffId);
                        } else {
                            LOG.info(AkuraConstant.SCHEDULAR_TRANSFORM_STAFF_ID + id[0]);
                        }
                        addmissionIdList.add(attendanceShedular.getAddmissionNo());
                        dateList.add(attendanceShedular.getDate());
                    }
                }
            }
        } catch (AkuraAppException e) {
            LOG.info(AkuraConstant.DATA_LOADED_ERROR);
            rolbackStatus.put(getCurrentFile(), true);
        }
    }

    /**
     * Adds the DailyStudentAttendance objects into a list.
     *
     * @param date - the date of the present.
     * @param timeIn - the time of the swipe in.
     * @param timeOut - the time of swipe out.
     * @param staffId - the primary key of the staff.
     */
    private void addIntoAList(Date date, String timeIn, String timeOut, int staffId) {

        DailyTeacherAttendance attendace = new DailyTeacherAttendance();
        attendace.setDate(date);
        attendace.setStaffId(staffId);
        attendace.setTimeIn(timeIn);
        attendace.setTimeOut(timeOut);
        staffDailyAttendance.add(attendace);
    }

    /** {@inheritDoc} */
    protected String getCopiedPath(String fileName) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateTime = dateFormat.format(calendar.getTime()).replace(":", "_");
        String copiedExcelPath = null;
        if (getTeacherExcelPath() != null) {
            copiedExcelPath =
                  PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                 + PropertyReader.getPropertyValue(SYSTEM_CONFIG, COPY_PROPERTY_STAFF_PATH) + dateTime + "," + fileName;
        }
        return copiedExcelPath;
    }

    /** {@inheritDoc} */
    public void clean() throws AkuraAppException {

        String threadName = Thread.currentThread().getName();
        LOG.info(threadName + AkuraConstant.SCHEDULAR_CLEAN_BEAGAN);

        if (getTeacherExcelPath() != null) {
            File folder =
                    new File(PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG, COPY_PROPERTY_STAFF_PATH));
            File tempDirectory =
                    new File(PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STAFF_DIRECTORY_PATH));
            deleteFiles(folder, tempDirectory);
        }
        LOG.info(threadName + AkuraConstant.SCHEDULAR_CLEAN_COMPLETED);
    }
}
