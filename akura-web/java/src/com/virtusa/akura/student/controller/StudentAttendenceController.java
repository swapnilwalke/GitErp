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

package com.virtusa.akura.student.controller;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.AttendeceStatus;
import com.virtusa.akura.api.dto.DailyStudentAttendance;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.dto.StudentLeave;
import com.virtusa.akura.api.dto.StudentWiseSwipInOutTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.attendance.service.DailyAttendanceService;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.student.validator.StudentAttendanceValidator;
import com.virtusa.akura.util.DateUtil;

/**
 * Controller class to generate a report of Student wise SwipIn SwipOut attendance.
 */

@Controller
public class StudentAttendenceController {
 
    /** session attribute name for student classGradeDescription. */
    private static final String STUDENT_GRADE = "studentGrade";
    
    /** request URL. */
    private static final String POPULATE_ATTENDENCE_HTM = "populateAttendence.htm";
    
    /** model map attribute name. */
    private static final String ATTENDECE_MAP = "attendeceMap";
    
    /** session attribute name for studentId(primary key). */
    private static final String STUDENT_ID = "studentId";
    
    /** view get method Student Wise Swipe In/Out. */
    private static final String GET_STUDENT_WISE_SWIP_IN_OUT_ATTENDANCE = "student/studentWiseSwipInOutAttendance";
    
    /** session attribute of average academic life rate value. */
    private static final String SESSION_ATT_AVERAGE_ACADEMIC_LIFE_RATING = "averageAcademicLifeRating";
    
    /** session attribute of average faith life rate value. */
    private static final String MODEL_ATT_AVERAGE_FAITH_LIFE_RATING = "averageFaithLifeRating";
    
    /** key to define the averageAttendanceRating. */
    private static final String AVERAGE_ATTENDANCE_RATING = "averageAttendanceRating";
    
    /** key to define the attendanceRating. */
    private static final String MODEL_ATT_ATTENDANCE_RATING = "attendanceRating";
    
    /** model attribute of student wise swipe in/out template. */
    private static final String STUDENT_WISE_SWIP_IN_OUT_TEMPLATE = "studentWiseSwipInOutTemplate";
    
    /** model attribute of student faithLife average value. */
    private static final String MODEL_ATT_STUDENT_FAITH_LIFE = "averageFaithLife";
    
    /** model attribute of student academic life average value. */
    private static final String MODEL_ATT_STUDENT_ACADEMIC_LIFE = "averageAcademicLife";
    
    /** holds validator instant. */
    private StudentAttendanceValidator studentAttendanceValidator;
    
    /** Represents the error message key for the first day at school of the student. */
    private static final String STUDENT_FIRST_DATE_ATTENDANCE_ERROR = "STUDENT.FIRST.DATE.ATTENDANCE.ERROR";

    /** Represents the model attribute name for the message. */
    private static final String MESSAGE = "message";

    /**
     * studentService of type StudentService to use methods related to SwipIn SwipOut attendance Reporting .
     */
    private StudentService studentService;
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** dailyAttendanceService To invoke service methods. */
    private DailyAttendanceService dailyAttendanceService;
    
    /**
     * set the dailyAttendanceService object.
     * 
     * @param dailyAttendanceServiceArg object to set
     */
    public void setDailyAttendanceService(DailyAttendanceService dailyAttendanceServiceArg) {
    
        this.dailyAttendanceService = dailyAttendanceServiceArg;
    }
    
    /**
     * Set commonService object.
     * 
     * @param commonServiceArg set commonService object.
     */
    
    public void setCommonService(CommonService commonServiceArg) {
    
        this.commonService = commonServiceArg;
    }
    
    /**
     * Sets an instance of StudentService.
     * 
     * @param studentServiceVal - the relevant instance of StudentService
     */
    
    public void setStudentService(StudentService studentServiceVal) {
    
        studentService = studentServiceVal;
    }
    
    /**
     * @param studentAttendanceValidatorArg instance to set
     */
    public void setStudentAttendanceValidator(StudentAttendanceValidator studentAttendanceValidatorArg) {
    
        this.studentAttendanceValidator = studentAttendanceValidatorArg;
    }
    
    /**
     * Display Form View for of the controller and binding it to
     * MonthlyStudentSwipInSwipOutAttendanceTemplate.
     * 
     * @param studentTemplate command object.
     * @param session of type HttpSession
     * @param modelMap of type ModelMap
     * @return java.lang.String
     * @throws AkuraAppException when exception occurs
     */
    @RequestMapping(value = "/studentAttendanceForm", method = RequestMethod.GET)
    public String showReportForm(HttpSession session,
            @ModelAttribute(STUDENT_WISE_SWIP_IN_OUT_TEMPLATE) StudentWiseSwipInOutTemplate studentTemplate,
            ModelMap modelMap) throws AkuraAppException {
    
        String clsGradeDes = (String) session.getAttribute(STUDENT_GRADE);
        
        // if student does not assign to a class, no need calculations, for current moth.
        if (clsGradeDes != null && !clsGradeDes.isEmpty()) {
            // studentWiseSwipInOutTemplate should fill with first day of current month and today
            
            // fist day of the month
            String fistDay = DateUtil.getFormatDate(DateUtil.getFistDayOfMonth());
            studentTemplate.setDateFrom(fistDay);
            // today
            studentTemplate.setDateTo(DateUtil.getFormatDate(new Date()));
            
            int studentId = (Integer) session.getAttribute(STUDENT_ID);
            Map<String, AttendeceStatus> allDays = getAllAttendanceStatus(studentTemplate, studentId,
                    modelMap);
            modelMap.addAttribute(ATTENDECE_MAP, allDays);
        }
        
        modelMap.addAttribute(STUDENT_WISE_SWIP_IN_OUT_TEMPLATE, studentTemplate);
        setupStudentRatingDetails(modelMap, session);
        return GET_STUDENT_WISE_SWIP_IN_OUT_ATTENDANCE;
    }
    
    /**
     * Setups the faith life and academic details rating for student.
     * 
     * @param model the model.
     * @param session the session.
     */
    private void setupStudentRatingDetails(ModelMap model, HttpSession session) {
    
        if (session.getAttribute(MODEL_ATT_AVERAGE_FAITH_LIFE_RATING) != null) {
            double faithLifeAverage = (Double) session.getAttribute(MODEL_ATT_AVERAGE_FAITH_LIFE_RATING);
            model.addAttribute(MODEL_ATT_STUDENT_FAITH_LIFE, (int) Math.round(faithLifeAverage));
        }
        if (session.getAttribute(SESSION_ATT_AVERAGE_ACADEMIC_LIFE_RATING) != null) {
            double academicLifeAverage = (Double) session.getAttribute(SESSION_ATT_AVERAGE_ACADEMIC_LIFE_RATING);
            model.addAttribute(MODEL_ATT_STUDENT_ACADEMIC_LIFE, (int) Math.round(academicLifeAverage));
        }
        if (session.getAttribute(AVERAGE_ATTENDANCE_RATING) != null) {
            double attendanceAverage = (Double) session.getAttribute(AVERAGE_ATTENDANCE_RATING);
            model.addAttribute(MODEL_ATT_ATTENDANCE_RATING, (int) Math.round(attendanceAverage));
        }
    }
    
    /**
     * request to get student attendant status for given time range.
     * 
     * @param session HttpSession
     * @param studentTemplate model object to bind parameters to request object.
     * @param errors validation falters
     * @param map model map
     * @return view name
     * @throws AkuraAppException exception occurs.
     */
    @RequestMapping(value = POPULATE_ATTENDENCE_HTM, method = RequestMethod.POST)
    public String populateAttendence(HttpSession session,
            @ModelAttribute(STUDENT_WISE_SWIP_IN_OUT_TEMPLATE) StudentWiseSwipInOutTemplate studentTemplate,
            BindingResult errors, ModelMap map) throws AkuraAppException {
    
        studentAttendanceValidator.validate(studentTemplate, errors);
        if (errors.hasErrors()) {
            setupStudentRatingDetails(map, session);
            return GET_STUDENT_WISE_SWIP_IN_OUT_ATTENDANCE;
            
        }
        int studentId = (Integer) session.getAttribute(STUDENT_ID);
        
        Map<String, AttendeceStatus> allDays = getAllAttendanceStatus(studentTemplate, studentId,
                map);
        
        map.addAttribute(ATTENDECE_MAP, allDays);
        setupStudentRatingDetails(map, session);
        return GET_STUDENT_WISE_SWIP_IN_OUT_ATTENDANCE;
    }
    
    /**
     * generate student attendance detail for given time range in studentTemplate. map key contains date and
     * value contains attendance status as AttendeceStatus object.
     * 
     * @param studentTemplate time rage as template
     * @param studentId for given studentId(primary key)
     * @param modelMap - a hash map related to the student attendance.
     * @return map with attendant status
     * @throws AkuraAppException when exception occurs
     */
    private Map<String, AttendeceStatus> getAllAttendanceStatus(StudentWiseSwipInOutTemplate studentTemplate,
            int studentId, ModelMap modelMap) throws AkuraAppException {
    
        Date from = DateUtil.getParseDate(studentTemplate.getDateFrom());
        Date to = DateUtil.getParseDate(studentTemplate.getDateTo());
        Map<String, AttendeceStatus> allDays = new TreeMap<String, AttendeceStatus>();
        
        // if the start date of the student is less than the attendance search from date,
        // save or edit.
        Date startedDate = studentService.getStudentStartedDate(studentId);
        
        if (startedDate != null && from.after(startedDate) || (startedDate != null && startedDate.equals(from))) {
            
            // map contains all the days with absent in default
            allDays = getDaysWithoutHolydays(from, to);
            
            // to overwrite the "allDays" map values for absent with a reason
            List<StudentLeave> leaveList = studentService.findAlreadyExistLeave(studentId, from, to);
            for (StudentLeave leave : leaveList) {
                Date leaveFrom = leave.getFromDate();
                Date leaveTo = leave.getToDate();
                
                for (Entry<String, AttendeceStatus> entry : allDays.entrySet()) {
                    Date day = DateUtil.getParseDate(entry.getKey());
                    if (day.equals(leaveFrom) || day.equals(leaveTo) || (day.after(leaveFrom) && day.before(leaveTo))) {
                        entry.getValue().setDescription(leave.getReason());
                    }
                }
            }
            
            // to overwrite the "allDays" map values for attended days
            List<DailyStudentAttendance> dalyAttendList =
                    dailyAttendanceService.getAttendanceBettween(studentId, from, to);
            for (DailyStudentAttendance dalyAttend : dalyAttendList) {
                
                String dayKey = DateUtil.getFormatDate(dalyAttend.getDate());
                if (allDays.containsKey(dayKey)) {
                    AttendeceStatus attendStatus = allDays.get(dayKey);
                    attendStatus.setAbsent(false);
                    attendStatus.setTimeIn(dalyAttend.getTimeIn());
                    attendStatus.setTimeOut(dalyAttend.getTimeOut());
                }
            }
        } else {
            String message = new ErrorMsgLoader().getErrorMessage(STUDENT_FIRST_DATE_ATTENDANCE_ERROR);
            modelMap.addAttribute(MESSAGE, message);
        }
        return allDays;
    }
    
    /**
     * get all days without special holidays and Saturday,Sunday for given Date range. map key contains date
     * and value contains AttendeceStatus object with default values(as absent day)
     * 
     * @param from from date
     * @param to to date
     * @return map
     * @throws AkuraAppException when exception occurs
     */
    private Map<String, AttendeceStatus> getDaysWithoutHolydays(Date from, Date to) throws AkuraAppException {
    
        Calendar calFrom = Calendar.getInstance();
        Calendar calTo = Calendar.getInstance();
        
        calFrom.setTime(from);
        calTo.setTime(to);
        
        List<Holiday> holidayList = commonService.findHolidayByYear(from, to);
        
        Map<String, AttendeceStatus> allDaysBetween = new TreeMap<String, AttendeceStatus>();
        
        // to get name ex Sunday ,Monday ..
        DateFormatSymbols symbols = new DateFormatSymbols();
        String[] weekDays = symbols.getWeekdays();
        
        while (calFrom.before(calTo) || calFrom.equals(calTo)) {
            
            int dyaOfWeek = calFrom.get(Calendar.DAY_OF_WEEK);
            // remove weekends and special holidays
            if (dyaOfWeek != Calendar.SATURDAY && dyaOfWeek != Calendar.SUNDAY
                    && !DateUtil.isHoliday(holidayList, calFrom.getTime())) {
                
                AttendeceStatus attSttus = new AttendeceStatus();
                attSttus.setDay(weekDays[dyaOfWeek]);
                allDaysBetween.put(DateUtil.getFormatDate(calFrom.getTime()), attSttus);
            }
            
            calFrom.set(Calendar.DATE, calFrom.get(Calendar.DATE) + 1);
        }
        
        return allDaysBetween;
    }
    
}
