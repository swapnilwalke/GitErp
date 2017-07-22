/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dto.AttendanceDashboardDto;
import com.virtusa.akura.api.dto.BestStudentAttendanceTemplate;
import com.virtusa.akura.api.dto.DailyStudentAttendance;
import com.virtusa.akura.api.dto.DailyTeacherAttendance;
import com.virtusa.akura.api.dto.SpecialEventsAttendance;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.attendance.dao.BestStudentAttendanceDao;
import com.virtusa.akura.attendance.dao.DailyStudentAttendanceDao;
import com.virtusa.akura.attendance.dao.DailyTeacherAttendanceDao;
import com.virtusa.akura.attendance.dao.SpecialEventsAttendanceDao;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.StaticDataUtil;

/**
 * Daily Attendance service implementation.
 *
 * @author Virtusa Corporation
 */
public class DailyAttendanceServiceImpl implements DailyAttendanceService {

    /** Represent the name of the default image name. */
    private static final String DEFAULT_IMAGE_NAME = "no_profile_image";

    /** Represent the name of the image upload path. */
    private static final String IMAGES_UPLOAD_PATH = "resources/attendanceDashboardImages/";

    /** Represent the value of the sixty persent. */
    private static final double SIXTY_PERSENT = 0.6;

    /** Represent the name of the property file. */
    private static final String SYSTEM_CONFIG = "systemConfig";

    /** Represents the default swipe out time. */
    private static final String DEFAULT_TIME_OUT_HLFDAY = "default.swipe.out.halfday";

    /** Represents the default swipe in time. */
    private static final String DEFAULT_TIME_IN_HLFDAY = "default.swipe.in.halfday";

    /**
     * Represents the name of the property file.
     */
    private static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";

    /**
     * Represents the key for the path of publication images path.
     */
    private static final String ATTENDANCE_FOLDER_CONFIG_KEY = "attendanceDashboardFolder.path";

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /**
     * Represents the extension of the default image.
     */
    private static final String IMAGE_EXTENSION = ".jpg";

    /**
     * dailyStudentAttendanceDao attribute for holding DailyStudentAttendanceDao.
     */
    private DailyStudentAttendanceDao dailyStudentAttendanceDao;

    /**
     * dailyTeacherAttendanceDao attribute for holding DailyTeacherAttendanceDao.
     */
    private DailyTeacherAttendanceDao dailyTeacherAttendanceDao;

    /**
     * specialEventsAttendanceDao attribute for holding SpecialEventsAttendanceDao.
     */
    private SpecialEventsAttendanceDao specialEventsAttendanceDao;

    /**
     * bestStudentAttendanceDao attribute for holding BestStudentAttendanceDao.
     */
    private BestStudentAttendanceDao bestStudentAttendanceDao;

    /**
     * Set specialEventsAttendanceDao object.
     *
     * @param specialEventsAttendanceDaoRef set specialEventsAttendanceDao object
     */
    public void setSpecialEventsAttendanceDao(SpecialEventsAttendanceDao specialEventsAttendanceDaoRef) {

        this.specialEventsAttendanceDao = specialEventsAttendanceDaoRef;
    }

    /**
     * Set DailyTeacherAttendanceDao object.
     *
     * @param dailyTeacherAttendanceDaoRef set DailyTeacherAttendanceDao object
     */
    public void setDailyTeacherAttendanceDao(DailyTeacherAttendanceDao dailyTeacherAttendanceDaoRef) {

        this.dailyTeacherAttendanceDao = dailyTeacherAttendanceDaoRef;
    }

    /**
     * Set DailyStudentAttendanceDao object.
     *
     * @param dailyStudentAttendanceDaoRef set DailyStudentAttendanceDao object
     */
    public void setDailyStudentAttendanceDao(DailyStudentAttendanceDao dailyStudentAttendanceDaoRef) {

        this.dailyStudentAttendanceDao = dailyStudentAttendanceDaoRef;
    }

    /**
     * @param bestStudentAttendanceDaoRef the bestStudentAttendanceDao to set
     */
    public void setBestStudentAttendanceDao(BestStudentAttendanceDao bestStudentAttendanceDaoRef) {

        this.bestStudentAttendanceDao = bestStudentAttendanceDaoRef;
    }

    /**
     * Method is to save list of DailyStudentAttendance object.
     *
     * @param dailyStudentAttendance - DailyStudentAttendance object want to save.
     * @throws AkuraAppException when exception occurs.
     */
    public void saveDailyStudentAttendance(List<DailyStudentAttendance> dailyStudentAttendance)
            throws AkuraAppException {

        dailyStudentAttendanceDao.saveOrUpdateAll(dailyStudentAttendance);
    }

    /**
     * {@inheritDoc}
     */
    public void saveSpecialEventsAttendance(List<SpecialEventsAttendance> specialEventsAttendance)
            throws AkuraAppException {

        specialEventsAttendanceDao.saveOrUpdateAll(specialEventsAttendance);
    }

    /**
     * Method is to save list of DailyTeacherAttendance object.
     *
     * @param dailyTeacherAttendance - DailyTeacherAttendance object want to save.
     * @throws AkuraAppException when exception occurs.
     */
    public void saveDailyTeacherAttendance(List<DailyTeacherAttendance> dailyTeacherAttendance)
            throws AkuraAppException {

        dailyTeacherAttendanceDao.saveOrUpdateAll(dailyTeacherAttendance);
    }

    /**
     * Generate Data list of students.
     *
     * @param selectedDate of type string
     * @param classGradeId of type integer
     * @return list of type DailyStudentAttendance
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public List<DailyStudentAttendance> getStudentAttandanceList(Date selectedDate, int classGradeId)
            throws AkuraAppException {

        return this.dailyStudentAttendanceDao.getStudentAttandanceList(selectedDate, classGradeId);
    }

    /**
     * {@inheritDoc}
     */
    public final List<DailyStudentAttendance> findByStudentId(int studentId, Date date) throws AkuraAppException {

        return dailyStudentAttendanceDao.findByStudentId(studentId, date);
    }

    /**
     * {@inheritDoc}
     */
    public final List<DailyTeacherAttendance> findByTeacherId(int staffId, Date date) throws AkuraAppException {

        return dailyTeacherAttendanceDao.findByTeacherId(staffId, date);
    }

    /**
     * {@inheritDoc}
     */
    public void deleteDailyStudentAttendance(DailyStudentAttendance dailyStudentAttendance) throws AkuraAppException {

        dailyStudentAttendanceDao.delete(dailyStudentAttendance);
    }

    /**
     * {@inheritDoc}
     */
    public void deleteDailyTeacherAttendance(DailyTeacherAttendance dailyTeacherAttendance) throws AkuraAppException {

        dailyTeacherAttendanceDao.delete(dailyTeacherAttendance);
    }

    /**
     * {@inheritDoc}
     */
    public List<DailyTeacherAttendance> getTeacherAttandanceList(Date selectedDate, boolean staffType)
            throws AkuraAppException {

        return this.dailyTeacherAttendanceDao.getTeacherAttandanceList(selectedDate, staffType);
    }

    /**
     * {@inheritDoc}
     */
    public List<DailyTeacherAttendance> getTeacherAttandanceListByCategoryId(Date selectedDate, int categoryId)
            throws AkuraAppException {

        return this.dailyTeacherAttendanceDao.getTeacherAttandanceListByCategoryId(selectedDate, categoryId);
    }

    /**
     * {@inheritDoc}
     */
    public List<StaffLeave> gethalfDayTeacherAttandanceList(Date selectedDate, boolean staffType)
            throws AkuraAppException {

        return this.dailyTeacherAttendanceDao.getHalfDayTeacherAttandanceList(selectedDate, staffType);
    }

    /**
     * {@inheritDoc}
     */
    public List<DailyTeacherAttendance> getStaffAttandanceList(Date selectedDate) throws AkuraAppException {

        return this.dailyTeacherAttendanceDao.getStaffAttandanceList(selectedDate);
    }

    /**
     * {@inheritDoc}
     */
    public List<SpecialEventsAttendance> getSpecialEventAttandanceList(int participationId) throws AkuraAppException {

        return this.specialEventsAttendanceDao.getSpecialEventAttandanceList(participationId);
    }

    /**
     * {@inheritDoc}
     */
    public List<DailyStudentAttendance> searchAttendance(int admissionNo, int date) throws AkuraAppException {

        return dailyStudentAttendanceDao.searchAttendance(admissionNo, date);
    }

    /** {@inheritDoc} */
    public List<DailyStudentAttendance> getAttendanceBettween(int studentId, Date from, Date to)
            throws AkuraAppException {

        return dailyStudentAttendanceDao.getAttendanceBettween(studentId, from, to);
    }

    /**
     * {@inheritDoc}
     */
    public List<SpecialEventsAttendance> getSpecialEventsAttendanceObjectByStudentId(int studentId, int participationId)
            throws AkuraAppException {

        return specialEventsAttendanceDao.getSpecialEventsAttendanceObjectByStudentId(studentId, participationId);
    }

    /**
     * {@inheritDoc}
     */
    public void deleteSpecialEventAttendance(SpecialEventsAttendance specialEventsAttendanceRef)
            throws AkuraAppException {

        specialEventsAttendanceDao.delete(specialEventsAttendanceRef);
    }

    /**
     * {@inheritDoc}
     */
    public List<DailyStudentAttendance> getNonCurrentStudentAttendanceList(int classGradeId, Date selectedDate)
            throws AkuraAppException {

        return dailyStudentAttendanceDao.getNonCurrentStudentAttendanceList(classGradeId, selectedDate);
    }

    /**
     * Update the time in and time out.
     *
     * @param selectedDate of type string
     * @param staffId of type int
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public void updateAttendanceForApprovedHalfDay(int staffId, Date selectedDate) throws AkuraAppException {

        // Loading relevant attendance record.
        List<DailyTeacherAttendance> dailyTeacherAttendanceList =
                (dailyTeacherAttendanceDao.findByTeacherId(staffId, selectedDate));

        // Checking for attendance entry available.
        if (dailyTeacherAttendanceList != null && !dailyTeacherAttendanceList.isEmpty()) {
            DailyTeacherAttendance dailyTeacherAttendance = dailyTeacherAttendanceList.get(0);

            // Set time in and time out for a half day.
            dailyTeacherAttendance.setTimeIn(PropertyReader.getPropertyValue(SYSTEM_CONFIG, DEFAULT_TIME_IN_HLFDAY));
            dailyTeacherAttendance.setTimeOut(PropertyReader.getPropertyValue(SYSTEM_CONFIG, DEFAULT_TIME_OUT_HLFDAY));

            // Update the record.
            dailyTeacherAttendanceDao.update(dailyTeacherAttendance);

        }

    }

    /**
     * @param attendanceDashboard - AttendanceDashboardDto
     * @return bestStudentAttendanceList - bestStudentAttendanceList
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public List<BestStudentAttendanceTemplate> getTopAttendanceList(AttendanceDashboardDto attendanceDashboard)
            throws AkuraAppException {

        List<BestStudentAttendanceTemplate> bestStudentAttendanceList = null;

        // Get the student attendance list.
        List<Object[]> bestStudentAttendanceObjects =
                bestStudentAttendanceDao.getTopAttendanceList(attendanceDashboard);
        if (bestStudentAttendanceObjects != null && !bestStudentAttendanceObjects.isEmpty()) {
            bestStudentAttendanceList = new ArrayList<BestStudentAttendanceTemplate>();
            String imageLoadPath = null;

            for (Object bestAttendanceObject : bestStudentAttendanceObjects) {

                int prserntDays =
                        Integer.parseInt(String
                                .valueOf(Array.get(bestAttendanceObject, AkuraConstant.PARAM_INDEX_ZERO)));

                // Check the 60% present attendance
                if (prserntDays >= Math.round(attendanceDashboard.getAcademicDays() * SIXTY_PERSENT)) {
                    BestStudentAttendanceTemplate bestStudentAttendance = new BestStudentAttendanceTemplate();
                    bestStudentAttendance.setStudentID(String.valueOf(Array.get(bestAttendanceObject,
                            AkuraConstant.PARAM_INDEX_ONE)));
                    bestStudentAttendance.setStudentName(String.valueOf(Array.get(bestAttendanceObject,
                            AkuraConstant.PARAM_INDEX_TWO)));
                    bestStudentAttendance.setPresentDays(prserntDays);
                    bestStudentAttendance.setClassDescription(String.valueOf(Array.get(bestAttendanceObject,
                            AkuraConstant.PARAM_INDEX_THREE)));

                    if (attendanceDashboard.getClassGradeId() > 0) {
                        bestStudentAttendance.setClassDescription(attendanceDashboard.getClassDescription());
                    }

                    // Set default image path.
                    imageLoadPath = IMAGES_UPLOAD_PATH + DEFAULT_IMAGE_NAME + IMAGE_EXTENSION;

                    if (Array.get(bestAttendanceObject, AkuraConstant.PARAM_INDEX_FOUR) != null) {
                        byte[] photo = (byte[]) (Array.get(bestAttendanceObject, AkuraConstant.PARAM_INDEX_FOUR));
                        String imageDownloadPath =
                                PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES,
                                                ATTENDANCE_FOLDER_CONFIG_KEY);
                        imageDownloadPath = imageDownloadPath + bestStudentAttendance.getStudentID() + IMAGE_EXTENSION;
                        // Write the image to server location
                        StaticDataUtil.previewProfile(imageDownloadPath, photo);
                        // Set the image path.
                        imageLoadPath = IMAGES_UPLOAD_PATH + bestStudentAttendance.getStudentID() + IMAGE_EXTENSION;
                    }
                    bestStudentAttendance.setImagePath(imageLoadPath);

                    // Get the 10th record's present value and add records with same present value to list.
                    if ((bestStudentAttendanceList.size() >= AkuraConstant.PARAM_INDEX_FOUR)
                            && (attendanceDashboard.getMinNo() >= AkuraConstant.PARAM_INDEX_SIX)) {
                        BestStudentAttendanceTemplate level =
                                bestStudentAttendanceList.get(AkuraConstant.PARAM_INDEX_THREE);
                        if (level.getPresentDays() == bestStudentAttendance.getPresentDays()) {
                            bestStudentAttendanceList.add(bestStudentAttendance);
                        }
                    } else {
                        bestStudentAttendanceList.add(bestStudentAttendance);
                    }
                }

            }
        }

        return bestStudentAttendanceList;
    }

}
