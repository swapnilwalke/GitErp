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
import com.virtusa.akura.api.dto.DailyStudentAttendance;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.attendance.dao.DailyStudentAttendanceDao;
import com.virtusa.akura.student.dao.StudentDao;
import com.virtusa.akura.util.PropertyReader;

/**
 * This Service class schedules to copy the data of the student and teacher attendance data into the database.
 *
 * @author Virtusa Corporation
 */
public class AttendanceSyncupSchedulerStudentImpl extends AbstractAttendanceSyncupSchedular {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(AttendanceSyncupSchedulerStudentImpl.class);

    /** Represents the default swipe out time. */
    private static final String DEFAULT_TIME_OUT = "default.swipe.out";

    /** Represents the default swipe in time. */
    private static final String DEFAULT_TIME_IN = "default.swipe.in";

    /** Represent the name of the property file. */
    private static final String SYSTEM_CONFIG = "systemConfig";

    /** Represents the a set contains instances of DailyStudentAttendance. */
    private Set<DailyStudentAttendance> stuDailyAttendance = new LinkedHashSet<DailyStudentAttendance>();

    /** Represents an instance of DailyStudentAttendaceDao. */
    private DailyStudentAttendanceDao dailyStudentAttendaceDao;

    /** Represents the key for path of the spreadsheet. */
    private static final String STU_DIRECTORY_PATH = "schedular.excel.student.folder";

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** Represents the key for path of the copied path of the text file. */
    private static final String COPY_PROPERTY_PATH = "schedular.copied.directory";

    /** Represents the key for up to the student directory path. */
    private static final String STU_UPTO_DIR_PATH = "schedular.stu.dir.path";

    /** Represents a property of the attendanceSyncupSchedular for the student. */
    private String studentExcelPath;

    /** Represents an instance of StudentDao. */
    private StudentDao studentDao;

    /**
     * Injects value for the student bean.
     *
     * @param strStudentExcelPath - a property for the student bean.
     */
    public void setStudentExcelPath(String strStudentExcelPath) {

        this.studentExcelPath = strStudentExcelPath;
    }

    /**
     * Returns value for the student bean.
     *
     * @return the student bean.
     */
    public String getStudentExcelPath() {

        return studentExcelPath;
    }

    /**
     * Injects an instance of StudentDao.
     *
     * @param stuStudentDao - an instance of StudentDao.
     */
    public void setStudentDao(StudentDao stuStudentDao) {

        this.studentDao = stuStudentDao;
    }

    /** {@inheritDoc} */
    public void extract() throws AkuraAppException {

        if (getStudentExcelPath() != null) {
            File folder =
                    new File(PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME) + getStudentExcelPath());
            if (folder.exists()) {
                copyFiles(folder);
            } else {
                LOG.debug(AkuraConstant.DIRECTORY_DOES_NOT_EXIST);
            }
        }
    }

    /**
     * Returns the path of the student directory.
     *
     * @return - the path of the student directory.
     */
    public File getCopyDirLocation() {

        File file = null;
        if (getStudentExcelPath() != null) {
            file =
                    new File(PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STU_UPTO_DIR_PATH));
        }
        return file;
    }

    /**
     * Injects an instance of DailyStudentAttendaceDao.
     *
     * @param dDailyStudentAttendaceDao - an instance of DailyStudentAttendaceDao.
     */
    public void setDailyStudentAttendaceDao(DailyStudentAttendanceDao dDailyStudentAttendaceDao) {

        this.dailyStudentAttendaceDao = dDailyStudentAttendaceDao;
    }

    /** {@inheritDoc} */
    public void load() throws AkuraAppException {

        String threadName = Thread.currentThread().getName();
        LOG.info(threadName + AkuraConstant.SCHEDULAR_LOAD_BEGAN);
        dailyStudentAttendaceDao.loadDataIntoDatabase(stuDailyAttendance);
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
                        List<Integer> stuList = studentDao.getStudentId(id[0]);
                        if (!stuList.isEmpty()) {
                            addIntoAList(date, timeIn, timeOut, stuList);
                        } else {
                            LOG.info(AkuraConstant.SCHEDULAR_TRANSFORM_STU_ID + id[0]);
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
     * @param stuList - the primary key of the student.
     */
    private void addIntoAList(Date date, String timeIn, String timeOut, List<Integer> stuList) {

        DailyStudentAttendance attendace = new DailyStudentAttendance();
        attendace.setDate(date);
        attendace.setStudentId(stuList.get(0));
        attendace.setTimeIn(timeIn);
        attendace.setTimeOut(timeOut);
        stuDailyAttendance.add(attendace);
    }

    /** {@inheritDoc} */
    protected String getCopiedPath(String fileName) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateTime = dateFormat.format(calendar.getTime()).replace(":", "_");
        String copiedExcelPath = null;
        if (getStudentExcelPath() != null) {
            copiedExcelPath =
                    PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                    + PropertyReader.getPropertyValue(SYSTEM_CONFIG, COPY_PROPERTY_PATH) + dateTime + "," + fileName;
        }
        return copiedExcelPath;
    }

    /** {@inheritDoc} */
    public void clean() throws AkuraAppException {

        String threadName = Thread.currentThread().getName();
        LOG.info(threadName + AkuraConstant.SCHEDULAR_CLEAN_BEAGAN);
        File folder =
                new File(PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, COPY_PROPERTY_PATH));
        File tempDirectory =
                new File(PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STU_DIRECTORY_PATH));
        if (getStudentExcelPath() != null) {
            deleteFiles(folder, tempDirectory);
        }
        LOG.info(threadName + AkuraConstant.SCHEDULAR_CLEAN_COMPLETED);
    }
}
