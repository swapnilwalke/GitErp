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

import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dto.AttendanceDashboardDto;
import com.virtusa.akura.api.dto.BestStudentAttendanceTemplate;
import com.virtusa.akura.api.dto.DailyStudentAttendance;
import com.virtusa.akura.api.dto.DailyTeacherAttendance;
import com.virtusa.akura.api.dto.SpecialEventsAttendance;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * interface to declare the attendance related services.
 *
 * @author Virtusa Corporation
 */
public interface DailyAttendanceService {

    /**
     * Method is to save list of DailyStudentAttendance object.
     *
     * @param dailyStudentAttendance - DailyStudentAttendance object want to save.
     * @throws AkuraAppException when exception occurs.
     */
    void saveDailyStudentAttendance(List<DailyStudentAttendance> dailyStudentAttendance) throws AkuraAppException;

    /**
     * Method is to save list of SpecialEventsAttendance object.
     *
     * @param specialEventsAttendance - SpecialEventsAttendance object want to save.
     * @throws AkuraAppException when exception occurs.
     */
    void saveSpecialEventsAttendance(List<SpecialEventsAttendance> specialEventsAttendance) throws AkuraAppException;

    /**
     * Method is to save list of DailyTeacherAttendance object.
     *
     * @param dailyTeacherAttendance - DailyTeacherAttendance object want to save.
     * @throws AkuraAppException when exception occurs.
     */
    void saveDailyTeacherAttendance(List<DailyTeacherAttendance> dailyTeacherAttendance) throws AkuraAppException;

    /**
     * Generate attendance list of students.
     *
     * @param selectedDate of type string
     * @param classGradeId of type integer
     * @return list of type DailyStudentAttendance
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    List<DailyStudentAttendance> getStudentAttandanceList(Date selectedDate, int classGradeId) throws AkuraAppException;

    /**
     * Delete the relevant dailyStudentAttendance.
     *
     * @param dailyStudentAttendanceRef - the instance of the dailyStudentAttendance
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void deleteDailyStudentAttendance(DailyStudentAttendance dailyStudentAttendanceRef) throws AkuraAppException;

    /**
     * Delete the relevant dailyTeacherAttendance.
     *
     * @param dailyTeacherAttendanceRef - the instance of the dailyTeacherAttendance
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void deleteDailyTeacherAttendance(DailyTeacherAttendance dailyTeacherAttendanceRef) throws AkuraAppException;

    /**
     * Finds the DailyStudentAttendance object for the given studentId and date.
     *
     * @param studentId of type integer
     * @param date of type date
     * @return - a list of DailyStudentAttendance objects
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<DailyStudentAttendance> findByStudentId(int studentId, Date date) throws AkuraAppException;

    /**
     * Finds the SpecialEventsAttendance object for the given studentId and participationId.
     *
     * @param studentId - integer
     * @param participationId - integer
     * @return SpecialEventsAttendance object
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<SpecialEventsAttendance> getSpecialEventsAttendanceObjectByStudentId(int studentId, int participationId)
            throws AkuraAppException;

    /**
     * Finds the DailyTeacherAttendance object for the given staffId and date.
     *
     * @param staffId of type integer
     * @param date of type date
     * @return - a list of DailyTeacherAttendance objects
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<DailyTeacherAttendance> findByTeacherId(int staffId, Date date) throws AkuraAppException;

    /**
     * Generate attendance list of teachers.
     *
     * @param selectedDate of type string
     * @param staffType of type boolean
     * @return list of type DailyTeacherAttendance
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    List<DailyTeacherAttendance> getTeacherAttandanceList(Date selectedDate, boolean staffType)
            throws AkuraAppException;

    /**
     * Generate attendance list of teachers.
     *
     * @param selectedDate of type string
     * @param categoryId of type integer.
     * @return list of type DailyTeacherAttendance
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    List<DailyTeacherAttendance> getTeacherAttandanceListByCategoryId(Date selectedDate, int categoryId)
            throws AkuraAppException;

    /**
     * Generate attendance list of half day teachers.
     *
     * @param selectedDate of type string
     * @param staffType of type boolean
     * @return list of type DailyTeacherAttendance
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    List<StaffLeave> gethalfDayTeacherAttandanceList(Date selectedDate, boolean staffType) throws AkuraAppException;

    /**
     * Generate attendance list of staff.
     *
     * @param selectedDate of type string
     * @return list of type DailyTeacherAttendance
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    List<DailyTeacherAttendance> getStaffAttandanceList(Date selectedDate) throws AkuraAppException;

    /**
     * Searches the daily attendance for a given primary key of the student.
     *
     * @param studentId - primary key of the student.
     * @param year - the current year.
     * @return - a list of daily attendance of the student.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<DailyStudentAttendance> searchAttendance(int studentId, int year) throws AkuraAppException;

    /**
     * get student's attendance for given time rage.
     *
     * @param studentId studentId value
     * @param from date rage form
     * @param to date rage to
     * @return List
     * @throws AkuraAppException The exception details that occurred when processing.
     */
    List<DailyStudentAttendance> getAttendanceBettween(int studentId, Date from, Date to) throws AkuraAppException;

    /**
     * Generate special event attendance by special event participation id.
     *
     * @param participationId of type integer
     * @return list of type DailyTeacherAttendance
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    List<SpecialEventsAttendance> getSpecialEventAttandanceList(int participationId) throws AkuraAppException;

    /**
     * Delete the relevant SpecialEventsAttendance.
     *
     * @param specialEventsAttendanceRef - the instance of the SpecialEventsAttendance
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void deleteSpecialEventAttendance(SpecialEventsAttendance specialEventsAttendanceRef) throws AkuraAppException;

    /**
     * Get the list of DailyStudentAttendance of students with the status other than 1.
     *
     * @param selectedDate - date of interest.
     * @param classGradeId - class if the student.
     * @return list of Daily StudentAttendance.
     * @throws AkuraAppException - throws detailed exception when fails.
     * @param selectedDate
     * @param classGradeId
     * @return
     * @throws AkuraAppException
     */
    List<DailyStudentAttendance> getNonCurrentStudentAttendanceList(int classGradeId, Date selectedDate)
            throws AkuraAppException;

    /**
     * Update the time in and time out.
     *
     * @param selectedDate of type string
     * @param staffId of type int
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    void updateAttendanceForApprovedHalfDay(int staffId, Date selectedDate) throws AkuraAppException;

    /**
     * Get the best attendance information.
     *
     * @param attendanceDashboard of type AttendanceDashboardDto
     * @return list of BestStudentAttendanceTemplate
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    List<BestStudentAttendanceTemplate> getTopAttendanceList(AttendanceDashboardDto attendanceDashboard)
            throws AkuraAppException;

}
