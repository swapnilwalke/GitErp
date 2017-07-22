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

package com.virtusa.akura.student.delegate;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentDetails;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.attendance.service.DailyAttendanceService;
import com.virtusa.akura.common.delegate.LoginDelegate;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentProgressBarService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.HolidayUtil;

/**
 * Controls the student login.
 * 
 * @author Virtusa Corporation
 */
@Component
public class StudentLoginDelegate implements LoginDelegate {
    
    /**
     * Log the error messages.
     */
    private static final Logger LOG = Logger.getLogger(StudentLoginDelegate.class);
    
    /** url for student login. */
    private static final String STUDENT_WELCOME = "student/welcome";
    
    /** attribute for student grade. */
    private static final String STUDENT_GRADE = "studentGrade";
    
    /** attribute for average accedemic life rating. */
    private static final String AVERAGE_ACADEMIC_LIFE_RATING = "averageAcademicLifeRating";
    
    /** attribute for average faith life rating. */
    private static final String AVERAGE_FAITH_LIFE_RATING = "averageFaithLifeRating";
    
    /** key for holding int value 100. */
    private static final int HUNDRED = 100;
    
    /** key to define the averageAcademicLifeRating. */
    private static final String AVERAGE_ATTENDANCE_RATING = "averageAttendanceRating";
    
    /** attribute for holding string. */
    private static final String END_YEAR = "-12-31";
    
    /** attribute for holding string. */
    private static final String START_YEAR = "-01-01";
    
    /** attribute for student. */
    private static final String STUDENT = "student";
    
    /** attribute for student id. */
    private static final String STUDENT_ID = "studentId";
    
    /** Represents an instance of StudentService. */
    private StudentService studentService;
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** StudentService attribute for holding studentProgressBarService. */
    private StudentProgressBarService studentProgressBarService;
    
    /** dailyAttendanceService attribute for holding DailyAttendanceService. */
    private DailyAttendanceService dailyAttendanceService;
    
    /**
     * sets the dailyAttendanceService object.
     * 
     * @param dailyAttendanceServiceRef the dailyAttendanceService to set
     */
    public void setDailyAttendanceService(DailyAttendanceService dailyAttendanceServiceRef) {

        this.dailyAttendanceService = dailyAttendanceServiceRef;
    }
    
    /**
     * setter method from CommonService.
     * 
     * @param commonServiceVal - CommonService
     */
    
    public void setCommonService(CommonService commonServiceVal) {

        this.commonService = commonServiceVal;
    }
    
    /**
     * sets the StudentProgressBarService object.
     * 
     * @param studentProgressBarServiceObj the studentProgressBarService to set
     */
    public void setStudentProgressBarService(StudentProgressBarService studentProgressBarServiceObj) {

        this.studentProgressBarService = studentProgressBarServiceObj;
    }
    
    /**
     * Sets an instance of StudentService.
     * 
     * @param studentServiceVal - an instance of StudentService.
     */
    public void setStudentService(StudentService studentServiceVal) {

        this.studentService = studentServiceVal;
    }
    
    /**
     * {@inheritDoc}
     */
    public String welcomeUser(UserInfo userInfo, HttpSession session) throws AkuraAppException {

        String userRedirectUrl = STUDENT_WELCOME;
        // int userLevelIdentifier =
        // studentService.findStudentIdForAdmissionNo(userInfo.getUserLevelIdentifier());
        String identificationNo = "";
        if (userInfo.getUserId() == 0) {
            identificationNo = userInfo.getUserLevelIdentifier().isEmpty() ? "0" : userInfo.getUserLevelIdentifier();
        } else {
            identificationNo = userInfo.getUserId() + "";
        }
        int studentId = Integer.parseInt(identificationNo);
        
        // populates the student related data
        populateStudentData(session, studentId);
        
        String currentYear = Integer.toString(DateUtil.currentYearOnly());
        Date year = DateUtil.getDateTypeYearValue(currentYear);
        // calculates the data for student progress bar
        Map<String, Double> averageMap = populateStudentProgressBar(studentId, year);
        
        for (Map.Entry<String, Double> entry : averageMap.entrySet()) {
            // set averages to the session
            session.setAttribute(entry.getKey(), entry.getValue());
        }
        
        userInfo.setUserId(studentId);
        userInfo.setDefaultUserHomeUrl(userRedirectUrl);
        
        // set student admission no
        ((StudentDetails) userInfo).setAdmissionNo(studentService.findStudent(studentId).getAdmissionNo());
        
        // return setUserInfo(session, userInfo, userRedirectUrl, userLevelIdentifier);
        return userRedirectUrl;
    }
    
    /**
     * Populates the student related data.
     * 
     * @param session the session.
     * @param userLevelIdentifier the user level identification.
     * @throws AkuraAppException the AkuraAppException.
     */
    private void populateStudentData(HttpSession session, int userLevelIdentifier) throws AkuraAppException {

        session.setAttribute(STUDENT_ID, userLevelIdentifier);
        Student student = studentService.findStudent(userLevelIdentifier);
        session.setAttribute(STUDENT, student);
        List<StudentClassInfo> studentClassInfos =
                studentService.getStudentClassInfoByStudentId(student.getStudentId(), DateUtil.currentYearOnly());
        
        if (!studentClassInfos.isEmpty()) {
            StudentClassInfo studentClassInfo = studentClassInfos.get(0);
            session.setAttribute(STUDENT_GRADE, studentClassInfo.getClassGrade().getGrade().getDescription());
        }
    }
    
    /**
     * populate student progress bar values.
     * 
     * @param studentId - integer
     * @param year - Date
     * @return average in double.
     * @throws AkuraAppException when exception occurs
     */
    public Map<String, Double> populateStudentProgressBar(int studentId, Date year) throws AkuraAppException {

        Map<String, Double> averageMap = new HashMap<String, Double>();
        
        averageMap.put(AVERAGE_ATTENDANCE_RATING, this.calculateAttendance(studentId, year));
        averageMap.put(AVERAGE_ACADEMIC_LIFE_RATING, this.calAcademicLifeValue(studentId, year));
        averageMap.put(AVERAGE_FAITH_LIFE_RATING, this.calFaithLifeValue(studentId, year));
        
        return averageMap;
    }
    
    /**
     * method to calculate attendance average.
     * 
     * @param studentId - integer
     * @return average in double. * @param year - Date
     * @throws AkuraAppException when exception occurs
     * @return attendance average in double.
     */
    private double calculateAttendance(int studentId, Date year) throws AkuraAppException {

        Student student = studentService.findStudent(studentId);
        
        String strStartDate = "";
        
        String strEndDate = DateUtil.getStringYear(year) + END_YEAR;
        
        Calendar firstDayCalendar = Calendar.getInstance();
        firstDayCalendar.setTime(student.getFirstSchoolDay());
        String firstDayYear = Integer.toString(firstDayCalendar.get(Calendar.YEAR));
        boolean checkFirstday = false;
        
        if (firstDayYear.equals(DateUtil.getStringYear(year))) {
            strStartDate = DateUtil.getFormatDate(student.getFirstSchoolDay());
            checkFirstday = true;
        } else {
            strStartDate = DateUtil.getStringYear(year) + START_YEAR;
        }
        
        List<Holiday> holidayList =
                commonService.findHolidayByYear(DateUtil.getParseDate(strStartDate), DateUtil.getParseDate(strEndDate));
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendar.getTime();
        dateTo = DateUtil.getParseDate(DateUtil.getFormatDate(dateTo));
        Date dateFrom = DateUtil.getParseDate(strStartDate);
        dateFrom = DateUtil.getParseDate(DateUtil.getFormatDate(dateFrom));
        Calendar fromDate = Calendar.getInstance();
        fromDate.setTime(dateFrom);
        
        Calendar toDate = Calendar.getInstance();
        toDate.setTime(dateTo);
        int holidayCount = HolidayUtil.countHolidays(fromDate, toDate, holidayList);
        fromDate.setTime(dateFrom);
        int numberOfDays = 0;
        
        if (checkFirstday) {
            if (!fromDate.after(toDate)) {
                int dayCount = toDate.get(Calendar.DAY_OF_YEAR) - fromDate.get(Calendar.DAY_OF_YEAR);
                numberOfDays = dayCount - holidayCount;
            }
            
        } else {
            
            numberOfDays = calendar.get(Calendar.DAY_OF_YEAR) - holidayCount;
        }
        
        numberOfDays++;
        int numbeeOfPresentDays = dailyAttendanceService.getAttendanceBettween(studentId, dateFrom, dateTo).size();
        
        double presentDays = (double) numbeeOfPresentDays / numberOfDays;
        
        return presentDays * HUNDRED;
        
    }
    
    /**
     * method to calculate faithLifeRating average.
     * 
     * @param studentId - integer
     * @param year - Date
     * @return average in double.
     * @throws AkuraAppException when exception occurs
     */
    private double calFaithLifeValue(int studentId, Date year) throws AkuraAppException {

        return studentProgressBarService.calculateFaithLifeRatingAverage(studentId, year);
        
    }
    
    /**
     * method to calculate academicLifeRating average.
     * 
     * @param studentId - integer
     * @param year - Date
     * @return average in double.
     * @throws AkuraAppException when exception occurs
     */
    private double calAcademicLifeValue(int studentId, Date year) throws AkuraAppException {

        return studentProgressBarService.calculateAcademicLifeRatingAverage(studentId, year);
        
    }
    
}
