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

package com.virtusa.akura.student.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.dto.StudentLeave;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.student.validator.StudentLeaveValidator;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.HolidayUtil;
import com.virtusa.akura.util.StaticDataUtil;

/**
 * The StudentLeaveController is to manage student leave tab functionalities such as add, edit and delete
 * leaves.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class StudentLeaveController {
    
    /**
     * Represents the error message for the certificate.
     */
    private static final String LEAVE_ERROR = "REF.UI.LEAVE.CERTIFICATE.ERROR";
    
    /** Represents the error message for the leaves applied on holidays. */
    private static final String LEAVE_ON_HOLIDAY_DATES = "REF.UI.LEAVE.HOLIDAY.DATES.ERROR";
    
    /** Represents the key of the already exist student leave. */
    private static final String ALREADY_EXIST = "STUDENT.LEAVE.ERROR";
    
    /** session attribute student id. */
    private static final String STUDENT_ID = "studentId";
    
    /** session attribute average academic life rating. */
    private static final String AVERAGE_ACADEMIC_LIFE_RATING = "averageAcademicLifeRating";
    
    /** session attribute average faith life rating. */
    private static final String AVERAGE_FAITH_LIFE_RATING = "averageFaithLifeRating";
    
    /** view post method student leave. */
    private static final String VIEW_POST_MANAGE_STUDENT_LEAVE = "redirect:studentLeave.htm";
    
    /** view get method student leave. */
    private static final String VIEW_GET_STUDENT_LEAVE = "student/studentLeave";
    
    /** model attribute of student leave. */
    private static final String MODEL_ATT_STUDENT_LEAVE = "studentLeave";
    
    /** String attribute for holding defalut_date_format. */
    private static final String DEFALUT_DATE_FORMAT = "yyyy-MM-dd";
    
    /** request mapping value for save or update student leave. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateStudentLeave.htm";
    
    /** request mapping value for delete student leave. */
    private static final String REQ_MAP_VALUE_DELETE = "/deleteStudentLeave.htm";
    
    /** model attribute of warning Level List. */
    private static final String MODEL_ATT_STUDENT_LEAVE_LIST = "studentLeaveList";
    
    /** request value for Student Leave id. */
    private static final String REQ_STUDENT_LEAVE_ID = "studentLeaveId";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** model attribute of student faithLife average value. */
    private static final String MODEL_ATT_STUDENT_FAITH_LIFE = "averageFaithLife";
    
    /** model attribute of student academic life average value. */
    private static final String MODEL_ATT_STUDENT_ACADEMIC_LIFE = "averageAcademicLife";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_DELETE = "REF.UI.STUDENTLEAVE.DELETE";
    
    /** Represents the key for the student first date error message. */
    private static final String STUDENT_FIRST_DATE_ERROR = "STUDENT.FIRST.DATE.LEAVE.ERROR";
    
    /** Represents the key for the student present date error message. */
    private static final String STUDENT_PRESENT_DATE_ERROR = "STUDENT.LEAVE.DATE.PRESENT.ERROR";
    
    /** key to define the averageAttendanceRating. */
    private static final String AVERAGE_ATTENDANCE_RATING = "averageAttendanceRating";
    
    /** key to define the attendanceRating. */
    private static final String MODEL_ATT_ATTENDANCE_RATING = "attendanceRating";
    
    /** Represents the maximum number of leave days. */
    private static final int MAXIMUM_LEAVE_DAYS = 3;
    
    /** StudentService attribute for holding studentService. */
    private StudentService studentService;
    
    /** StudentLeaveValidator attribute for holding studentLeaveValidator. */
    private StudentLeaveValidator studentLeaveValidator;
    
    /** key to hold string of year end date. */
    private static final String END_DATE = "-12-31";
    
    /** key to hold string of year start date. */
    private static final String START_DATE = "-01-01";
    
    /** key to hold string of "attendancePage". */
    private static final String ATTENDANCE_PAGE = "attendancePage";

    /** key to hold string of "true". */
    private static final String TRUE = "true";
    
    /** key to hold string of "false". */
    private static final String FALSE = "false";

    /** CommomService attribute for holding commonService. */
    private CommonService commonService;
    
    /**
     * Set the service instance to inject the service.
     * 
     * @param commonServiceRef the CommonService to set
     */
    public void setCommonService(CommonService commonServiceRef) {

        this.commonService = commonServiceRef;
    }
    
    /**
     * Set the service instance to inject the service.
     * 
     * @param studentServiceValue the studentService to set
     */
    public void setStudentService(StudentService studentServiceValue) {

        this.studentService = studentServiceValue;
    }
    
    /**
     * Set the studentLeaveValidator instance to inject the validator.
     * 
     * @param studentLeaveValidatorValue the studentLeaveValidator to set
     */
    public void setStudentLeaveValidator(StudentLeaveValidator studentLeaveValidatorValue) {

        this.studentLeaveValidator = studentLeaveValidatorValue;
    }
    
    /**
     * Handle GET requests for Student_Leave view.
     * 
     * @param model - ModelMap
     * @param session - {@link HttpSession}
     * @param request - {@link HttpServletRequest}
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showStudentDisciplineForm(ModelMap model, HttpSession session, HttpServletRequest request) {

        StudentLeave studentLeave = (StudentLeave) model.get(MODEL_ATT_STUDENT_LEAVE);
        
        if (studentLeave == null) {
            studentLeave = new StudentLeave();
        }
        
        if (request.getParameter(ATTENDANCE_PAGE) != null) {
            model.addAttribute(ATTENDANCE_PAGE, TRUE);
        }
        
        setRatingValue(model, session);
        model.addAttribute(MODEL_ATT_STUDENT_LEAVE, studentLeave);
        
        return VIEW_GET_STUDENT_LEAVE;
    }
    
    /**
     * sets average faithLife and academic values to model.
     * 
     * @param model - ModelMap
     * @param session - {@link HttpSession}
     */
    private void setRatingValue(ModelMap model, HttpSession session) {

        if (session.getAttribute(AVERAGE_FAITH_LIFE_RATING) != null) {
            double faithLifeAverage = (Double) session.getAttribute(AVERAGE_FAITH_LIFE_RATING);
            model.addAttribute(MODEL_ATT_STUDENT_FAITH_LIFE, (int) Math.round(faithLifeAverage));
        }
        if (session.getAttribute(AVERAGE_ACADEMIC_LIFE_RATING) != null) {
            double academicLifeAverage = (Double) session.getAttribute(AVERAGE_ACADEMIC_LIFE_RATING);
            model.addAttribute(MODEL_ATT_STUDENT_ACADEMIC_LIFE, (int) Math.round(academicLifeAverage));
        }
        if (session.getAttribute(AVERAGE_ATTENDANCE_RATING) != null) {
            double attendanceAverage = (Double) session.getAttribute(AVERAGE_ATTENDANCE_RATING);
            model.addAttribute(MODEL_ATT_ATTENDANCE_RATING, (int) Math.round(attendanceAverage));
        }
    }
    
    /**
     * intiBinder method is to bind date class with the date format.
     * 
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.initDirectFieldAccess();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFALUT_DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    /**
     * This method is to Add/Edit/Delete student leave.
     * 
     * @param studentLeave - {@link StudentLeave}
     * @param bindingResult - {@link BindingResult}
     * @param request - {@link HttpServletRequest}
     * @param model - {@link ModelMap}
     * @param session - {@link HttpSession}
     * @return the name of the view.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateStudentLeave(@ModelAttribute(MODEL_ATT_STUDENT_LEAVE) StudentLeave studentLeave,
            BindingResult bindingResult, HttpServletRequest request, ModelMap model, HttpSession session)
            throws AkuraAppException {

        studentLeaveValidator.validate(studentLeave, bindingResult);

        if (request.getParameter(ATTENDANCE_PAGE).equals(TRUE)) {
            model.addAttribute(ATTENDANCE_PAGE, TRUE);
        } else {
            model.addAttribute(ATTENDANCE_PAGE, FALSE);
        }
        
        if (bindingResult.hasErrors()) {
            setRatingValue(model, session);
            return VIEW_GET_STUDENT_LEAVE;
        } else {
            
            Date dateFrom = studentLeave.getFromDate();
            int studentId = (Integer) session.getAttribute(STUDENT_ID);
            
            // if the first date of the student is less than the start date of the leave,
            // save or edit.
            Date startedDate = studentService.getStudentStartedDate(studentId);
            
            if (startedDate != null && dateFrom.after(startedDate)
                    || (startedDate != null && startedDate.equals(dateFrom))) {
                Date dateTo = studentLeave.getToDate();
                Calendar fromDate = Calendar.getInstance();
                fromDate.setTime(dateFrom);
                Calendar toDate = Calendar.getInstance();
                toDate.setTime(dateTo);
                
                List<Holiday> holidayList = getHolidayList(dateFrom, dateTo);
                
                int holidayCount = HolidayUtil.countHolidays(fromDate, toDate, holidayList);
                int daysCount = StaticDataUtil.daysBetween(dateFrom, dateTo) - holidayCount;
                
                if (daysCount > MAXIMUM_LEAVE_DAYS) {
                
                    if (!studentLeave.getCertificategiven()) {
                        String message = new ErrorMsgLoader().getErrorMessage(LEAVE_ERROR);
                        model.addAttribute(MESSAGE, message);
                        return VIEW_GET_STUDENT_LEAVE;
                    }
                    
                } else if (daysCount == 0) {
                    String message = new ErrorMsgLoader().getErrorMessage(LEAVE_ON_HOLIDAY_DATES);
                    model.addAttribute(MESSAGE, message);
                    return VIEW_GET_STUDENT_LEAVE;
                }
                String reason = StaticDataUtil.removeExtraWhiteSpace(studentLeave.getReason());
                
                List<StudentLeave> existLeave = studentService.findAlreadyExistLeave(studentId, dateFrom, dateTo);
                
                if ((!existLeave.isEmpty() && studentLeave.getStudentLeaveId() == 0)
                        || (!existLeave.isEmpty() && existLeave.get(0).getStudentLeaveId() != studentLeave
                                .getStudentLeaveId())) {
                    model.addAttribute(MESSAGE, new ErrorMsgLoader().getErrorMessage(ALREADY_EXIST));
                    return VIEW_GET_STUDENT_LEAVE;
                } else if (studentService.isPresentDay(studentId, dateFrom, dateTo)) {
                    String message = new ErrorMsgLoader().getErrorMessage(STUDENT_PRESENT_DATE_ERROR);
                    model.addAttribute(MESSAGE, message);
                    return VIEW_GET_STUDENT_LEAVE;
                } else {
                    studentLeave.setStudentId(studentId);
                    studentLeave.setReason(reason);
                    studentLeave.setNoOfDays(daysCount);
                    
                    if (studentLeave.getStudentLeaveId() == 0) {
                        studentService.createStudentLeave(studentLeave);
                    } else {
                        studentService.updateStudentLeave(studentLeave);
                    }
                }
            } else {
                if (startedDate != null) {
                    String message = new ErrorMsgLoader().getErrorMessage(STUDENT_FIRST_DATE_ERROR);
                    model.addAttribute(MESSAGE, message);
                    return VIEW_GET_STUDENT_LEAVE;
                }
            }
        }
        
        return VIEW_POST_MANAGE_STUDENT_LEAVE;
    }
    
    /**
     * Returns the holiday list for a given time period.
     * 
     * @param startDate - start date of the report.
     * @param endDate - end date of the report.
     * @return ListHoliday - a list containing holiday objects.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private List<Holiday> getHolidayList(Date startDate, Date endDate) throws AkuraAppException {

        String strYr = DateUtil.getStringYear(startDate);
        
        String strStartDate = strYr + START_DATE;
        String strEndDate = strYr + END_DATE;
        
        Date startDateToSearch = DateUtil.getParseDate(strStartDate);
        Date endDateToSearch = DateUtil.getParseDate(strEndDate);
        
        return commonService.findHolidayByYear(startDateToSearch, endDateToSearch);
        
    }
    
    /**
     * Populate list of student leaves for the selected student.
     * 
     * @param session - {@link HttpSession}
     * @return list of student leaves.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(MODEL_ATT_STUDENT_LEAVE_LIST)
    public List<StudentLeave> populateStudentLeaveList(HttpSession session) throws AkuraAppException {

        List<StudentLeave> studentLeaves = new ArrayList<StudentLeave>();
        
        if (session.getAttribute(STUDENT_ID) != null) {
            int studentId = Integer.parseInt(session.getAttribute(STUDENT_ID).toString());
            studentLeaves = studentService.getStudentLeaveListByStudentId(studentId);
        }
        
        return studentLeaves;
    }
    
    /**
     * Delete a student leave.
     * 
     * @param request {@link HttpServletRequest}
     * @param model {@link ModelMap}
     * @return view of the student leave.
     * @throws AkuraAppException - throw the detailed exception.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteStudentLeave(HttpServletRequest request, ModelMap model) throws AkuraAppException {

        try {
            int studentLeaveId = Integer.parseInt(request.getParameter(REQ_STUDENT_LEAVE_ID));
            StudentLeave studentLeave = studentService.findStudentLeaveById(studentLeaveId);
            studentService.deleteStudentLeave(studentLeave);
        } catch (AkuraAppException e) {
            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
            StudentLeave studentLeave = new StudentLeave();
            model.addAttribute(MODEL_ATT_STUDENT_LEAVE, studentLeave);
            model.addAttribute(MESSAGE, message);
            
            return VIEW_GET_STUDENT_LEAVE;
        }
        return VIEW_POST_MANAGE_STUDENT_LEAVE;
    }
}
