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

package com.virtusa.akura.attendance.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.DailyStudentAttendance;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.attendance.service.DailyAttendanceService;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;

/**
 * Controls the enter daily attendance manually related functions.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class DailyAttendanceController {
    
    /** attribute for holding url DailyAttendanceReload.htm. */
    private static final String DAILY_ATTENDANCE_RELOAD_HTM = "DailyAttendanceReload.htm";
    
    /** attribute for holding url changeDateList.htm. */
    private static final String CHANGE_DATE_LIST_HTM = "changeDateList.htm";
    
    /** attribute for holding error message key. */
    private static final String ATTENDANCE_SAVE_FAIL = "ATTENDANCE.SAVE.FAIL";
    
    /** attribute for holding minus two. */
    private static final int CONSTANT_MINUS_TWO = -2;
    
    /** Represents the default swipe out time. */
    private static final String DEFAULT_TIME_OUT = "default.swipe.out";
    
    /** Represents the default swipe in time. */
    private static final String DEFAULT_TIME_IN = "default.swipe.in";
    
    /** Represent the name of the property file. */
    private static final String SYSTEM_CONFIG = "systemConfig";
    
    /** attribute for holding string studentIdList. */
    private static final String STUDENT_ID_LIST = "studentIdList";
    
    /** attribute for holding string attendanceList. */
    private static final String ATTENDANCE_LIST = "attendanceList";
    
    /** attribute for holding error message key. */
    private static final String ATTENDANCE_SEARCH_NO_RESULT = "ATTENDANCE.SEARCH.NO.RESULT";
    
    /** attribute for holding string date. */
    private static final String DATE = "date";
    
    /** attribute for holding string Please Select. */
    private static final String PLEASE_SELECT = "0";
    
    /** attribute for holding string select. */
    private static final String SELECT = "select";
    
    /** attribute for holding url searchStudentAttendance.htm. */
    private static final String SEARCH_STUDENT_ATTENDANCE_HTM = "/searchStudentAttendance.htm";
    
    /** attribute for holding string total. */
    private static final String TOTAL = "total";
    
    /** attribute for holding string currentDate. */
    private static final String CURRENT_DATE = "currentDate";
    
    /** attribute for holding number five. */
    private static final int CONSTANT_FIVE = 5;
    
    /** attribute for holding date format. */
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    
    /** attribute for holding view name attendance/dailyAttendance. */
    private static final String ATTENDANCE_DAILY_ATTENDANCE = "attendance/dailyAttendance";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** Model attribute of studentList. */
    private static final String MODEL_ATT_STUDENT_LIST = "studentList";
    
    /** Model attribute of gradeClassList. */
    private static final String MODEL_ATT_GRADE_CLASS_LIST = "gradeClassList";
    
    /** Model attribute of currentDate. */
    private static final String MODEL_ATT_CURRENT_DATE = "dateList";
    
    /** Model attribute of classGradeId. */
    private static final String MODEL_ATT_CLASS_GRADE_ID = "classGradeId";
    
    /** attribute for holding url /saveorupdateStudentAttendance.htm. */
    private static final String REQ_VALUE_SAVEORUPDATE_STUDENT_ATTENDANCE = "/saveorupdateStudentAttendance.htm";
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** attribute for holding string. */
    private static final String END_YEAR = "-12-31";
    
    /** attribute for holding string. */
    private static final String START_YEAR = "-01-01";
    
    /** DailyAttendanceService attribute for holding dailyAttendanceService. */
    private DailyAttendanceService dailyAttendanceService;
    
    /**
     * Set dailyAttendanceService object.
     * 
     * @param dailyAttendanceServiceRef set dailyAttendanceService object.
     */
    public void setDailyAttendanceService(DailyAttendanceService dailyAttendanceServiceRef) {

        this.dailyAttendanceService = dailyAttendanceServiceRef;
    }
    
    /**
     * studentService To invoke service methods.
     */
    private StudentService studentService;
    
    /**
     * Set SchoolService object.
     * 
     * @param objCommonService set school service object.
     */
    public void setCommonService(CommonService objCommonService) {

        this.commonService = objCommonService;
    }
    
    /**
     * setter to inject StudentService object.
     * 
     * @param objStudentService the studentService to set
     */
    public void setStudentService(StudentService objStudentService) {

        this.studentService = objStudentService;
    }
    
    /**
     * Method is to return Grade class list reference data.
     * 
     * @return ClassGradeList - Class Grade reference data.
     * @throws AkuraAppException - throw SMSExeption.
     */
    @ModelAttribute(MODEL_ATT_GRADE_CLASS_LIST)
    public List<ClassGrade> populateGradeClassList() throws AkuraAppException {

        return SortUtil.sortClassGradeList(commonService.getClassGradeList());
    }
    
    /**
     * Method is to return date list.
     * 
     * @return current date - String
     * @throws AkuraAppException - throw SMSExeption.
     */
    @ModelAttribute(MODEL_ATT_CURRENT_DATE)
    public List<String> populateDateList() throws AkuraAppException {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        List<Holiday> holidayList = getHolidayList(String.valueOf(year));
        List<String> dateList = new ArrayList<String>();
        
        while (true) {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY) {
                calendar.add(Calendar.DATE, -1);
                
            } else if (dayOfWeek == Calendar.SUNDAY) {
                calendar.add(Calendar.DATE, CONSTANT_MINUS_TWO);
                
            } else {
                
                Date date = calendar.getTime();
                if (!DateUtil.isHoliday(holidayList, DateUtil.getParseDate(DateUtil.getFormatDate(date)))) {
                    dateList.add(DateUtil.getFormatDate(date));
                    calendar.add(Calendar.DATE, -1);
                } else {
                    calendar.add(Calendar.DATE, -1);
                }
                
            }
            
            if (dateList.size() == CONSTANT_FIVE) {
                break;
            }
            
        }
        
        return dateList;
    }
    
    /**
     * Get the Holiday List for the given time period.
     * 
     * @param year - string year.
     * @return ListHoliday - a list containing holiday objects.
     * @throws AkuraAppException - AkuraAppException
     */
    private List<Holiday> getHolidayList(String year) throws AkuraAppException {

        String strStartDate = year + START_YEAR;
        String strEndDate = year + END_YEAR;
        
        return commonService.findHolidayByYear(DateUtil.getParseDate(strStartDate), DateUtil.getParseDate(strEndDate));
    }
    
    /**
     * Navigate method for enter daily attendance manually .
     * 
     * @param modelMap of type ModelMap
     * @return java.lang.String
     */
    
    @RequestMapping(method = RequestMethod.GET)
    public String showDailyAttendance(final ModelMap modelMap) {

        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
        Calendar calendar = Calendar.getInstance();
        String currentDate = dateFormat.format(calendar.getTime());
        modelMap.addAttribute(CURRENT_DATE, currentDate);
        
        return ATTENDANCE_DAILY_ATTENDANCE;
    }
    
    /**
     * Method to remove save data when date List change.
     * 
     * @param request Http request.
     * @param modelMap - model map to set data.
     * @return String value of jsp page to direct.
     * @throws AkuraAppException throw exception if occur.
     */
    @RequestMapping(value = DAILY_ATTENDANCE_RELOAD_HTM, method = RequestMethod.POST)
    public String reloadPage(HttpServletRequest request, ModelMap modelMap) throws AkuraAppException {

        String classGradeId = request.getParameter(SELECT);
        if (classGradeId != null) {
            modelMap.addAttribute(MODEL_ATT_CLASS_GRADE_ID, classGradeId);
        }
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
        Calendar calendar = Calendar.getInstance();
        String currentDate = dateFormat.format(calendar.getTime());
        modelMap.addAttribute(CURRENT_DATE, currentDate);
        
        return ATTENDANCE_DAILY_ATTENDANCE;
    }
    
    /**
     * Method to remove save data when date List change.
     * 
     * @param request Http request.
     * @param model - model map to set data.
     * @return String value of jsp page to direct.
     * @throws AkuraAppException throw exception if occur.
     */
    @RequestMapping(value = CHANGE_DATE_LIST_HTM, method = RequestMethod.POST)
    public String onChageDateList(HttpServletRequest request, ModelMap model) throws AkuraAppException {

        String classGradeId = request.getParameter(SELECT);
        String date = request.getParameter(DATE);
        
        model.addAttribute(CURRENT_DATE, date);
        model.addAttribute(MODEL_ATT_CLASS_GRADE_ID, classGradeId);
        List<StudentClassInfo> studentList = new ArrayList<StudentClassInfo>();
        model.addAttribute(MODEL_ATT_STUDENT_LIST, SortUtil.sortStudentListByAdmissionNo(studentList));
        
        return ATTENDANCE_DAILY_ATTENDANCE;
    }
    
    /**
     * method to search attendance for given date and class.
     * 
     * @param request Http request.
     * @param map model map to set data.
     * @return String value of jsp page to direct.
     * @throws AkuraAppException throw exception if occur.
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST, value = SEARCH_STUDENT_ATTENDANCE_HTM)
    public String searchStudentAttendance(HttpServletRequest request, ModelMap map) throws AkuraAppException {

        String message;
        
        String classGradeId = request.getParameter(SELECT);
        if (classGradeId.equals(PLEASE_SELECT)) {
            message = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            map.addAttribute(MESSAGE, message);
        } else {
            int total = 0;
            String date = request.getParameter(DATE);
            Date dateObj = DateUtil.getParseDate(date);
            List<DailyStudentAttendance> dailyStudentAttendanceList =
                    dailyAttendanceService.getStudentAttandanceList(DateUtil.getParseDate(date),
                            Integer.parseInt(classGradeId));
            
            if (!dailyStudentAttendanceList.isEmpty()) {
                total = dailyStudentAttendanceList.size();
            }
            
            // get the student list in the particular class
            List<StudentClassInfo> studentList =
                    studentService.getPresentClassStudentList(Integer.parseInt(classGradeId),
                            DateUtil.getYearFromDate(DateUtil.getDateTypeYearValue(date)), dateObj);
            
            // get the non-current students list in the class.
            List<StudentClassInfo> nonCurrentStudentList =
                    studentService.getNonCurrentStudentClassInfoList(Integer.parseInt(classGradeId),
                            DateUtil.getYearFromDate(DateUtil.getDateTypeYearValue(date)), dateObj);
            
            // remove non-current students from all students list of the class.
            studentList = ListUtils.subtract(studentList, nonCurrentStudentList);
            
            //get the suspended and temporary leaved students who are yet to be activated.
            List<StudentClassInfo> suspendedAndTemporaryLeavedStudentsToBeActive =
                    studentService.getSuspendedAndTemporaryLeavedStudentsToBeActive(Integer.parseInt(classGradeId),
                            DateUtil.getYearFromDate(DateUtil.getDateTypeYearValue(date)), dateObj);
            
            // remove suspended and temporary leaved students from the current student list.
            studentList = ListUtils.subtract(studentList, suspendedAndTemporaryLeavedStudentsToBeActive);

            // if no students in that class
            if (studentList.isEmpty()) {
                message = new ErrorMsgLoader().getErrorMessage(ATTENDANCE_SEARCH_NO_RESULT);
                map.addAttribute(CURRENT_DATE, date);
                map.addAttribute(MODEL_ATT_CLASS_GRADE_ID, classGradeId);
                map.addAttribute(MESSAGE, message);
            } else {
                
                map.addAttribute(ATTENDANCE_LIST, dailyStudentAttendanceList);
                map.addAttribute(TOTAL, total);
                map.addAttribute(CURRENT_DATE, date);
                map.addAttribute(MODEL_ATT_STUDENT_LIST, SortUtil.sortStudentListByAdmissionNo(studentList));
                map.addAttribute(MODEL_ATT_CLASS_GRADE_ID, classGradeId);
                
            }
            
        }
        
        return ATTENDANCE_DAILY_ATTENDANCE;
        
    }
    
    /**
     * method to save or update student attendance object.
     * 
     * @param request Http request.
     * @param map model map to set data.
     * @return String value of jsp page to direct.
     * @throws AkuraAppException throw exception if occur.
     */
    @RequestMapping(method = RequestMethod.POST, value = REQ_VALUE_SAVEORUPDATE_STUDENT_ATTENDANCE)
    public String saveorupdateStudentAttendance(HttpServletRequest request, ModelMap map) throws AkuraAppException {

        String[] studentID = request.getParameterValues(STUDENT_ID_LIST);
        // selected class
        String classGradeId = request.getParameter(SELECT);
        // selected grade
        String date = request.getParameter(DATE);
        // get the daily student attendance list for given date and class
        List<DailyStudentAttendance> dailyStudentAttendanceList =
                dailyAttendanceService.getStudentAttandanceList(DateUtil.getParseDate(date),
                        Integer.parseInt(classGradeId));
        List<String> pastAttendanceList = new ArrayList<String>();
        // get the present students id list
        for (DailyStudentAttendance dailyAttendanceList : dailyStudentAttendanceList) {
            pastAttendanceList.add(dailyAttendanceList.getStudentId().toString());
        }
        try {
            if (studentID != null) {
                
                @SuppressWarnings("unchecked")
                // get the absent student list
                List<String> toBeRomoved = ListUtils.subtract((pastAttendanceList), Arrays.asList(studentID));
                @SuppressWarnings("unchecked")
                // get the present student list
                List<String> toBeAdd = ListUtils.subtract(Arrays.asList(studentID), pastAttendanceList);
                
                // add present students
                if (!toBeAdd.isEmpty()) {
                    
                    String timeIn = PropertyReader.getPropertyValue(SYSTEM_CONFIG, DEFAULT_TIME_IN);
                    String timeOut = PropertyReader.getPropertyValue(SYSTEM_CONFIG, DEFAULT_TIME_OUT);
                    List<DailyStudentAttendance> saveList = new ArrayList<DailyStudentAttendance>();
                    for (String presentStudentId : toBeAdd) {
                        
                        DailyStudentAttendance dailyStudentAttendance = new DailyStudentAttendance();
                        dailyStudentAttendance.setStudentId(Integer.valueOf(presentStudentId));
                        dailyStudentAttendance.setDate(DateUtil.getParseDate(date));
                        dailyStudentAttendance.setTimeIn(timeIn);
                        dailyStudentAttendance.setTimeOut(timeOut);
                        saveList.add(dailyStudentAttendance);
                    }
                    dailyAttendanceService.saveDailyStudentAttendance(saveList);
                }
                // remove absent students
                if (!toBeRomoved.isEmpty()) {
                    
                    deleteStudentAttendance(date, toBeRomoved);
                    
                }
                
                return searchStudentAttendance(request, map);
            } else if (studentID == null && (!dailyStudentAttendanceList.isEmpty())) {
                
                deleteStudentAttendance(date, pastAttendanceList);
                return searchStudentAttendance(request, map);
            }
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ATTENDANCE_SAVE_FAIL);
                
                map.addAttribute(MESSAGE, message);
                return ATTENDANCE_DAILY_ATTENDANCE;
            } else {
                throw e;
            }
        }
        
        return searchStudentAttendance(request, map);
        
    }
    
    /**
     * method to delete the student attendance records.
     * 
     * @param date of type Date
     * @param pastAttendanceList - list of type string
     * @throws AkuraAppException throw exception if occur.
     */
    private void deleteStudentAttendance(String date, List<String> pastAttendanceList) throws AkuraAppException {

        for (String absentStudentId : pastAttendanceList) {
            
            List<DailyStudentAttendance> dailyStudentAttendanceobject =
                    dailyAttendanceService.findByStudentId(Integer.parseInt(absentStudentId),
                            DateUtil.getParseDate(date));
            dailyAttendanceService.deleteDailyStudentAttendance(dailyStudentAttendanceobject.get(0));
        }
    }
    
}
