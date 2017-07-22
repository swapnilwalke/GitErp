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
import com.virtusa.akura.api.dto.StudentDiscipline;
import com.virtusa.akura.api.dto.StudentLeave;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.dto.WarningLevel;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.student.validator.StudentDisciplineValidator;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.SortUtil;

/**
 * The StudentDisciplineController is to manage student discipline tab functionalities such as add, edit and
 * delete disciplines.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class StudentDisciplineController {
    
    /** session attribute of UserLogin. */
    private static final String SESSION_USER_LOGIN = "userLogin";
    
    /** view post method student discipline. */
    private static final String VIEW_POST_MANAGE_STUDENT_DISCIPLINE = "redirect:studentDiscipline.htm";
    
    /** view get method student discipline. */
    private static final String VIEW_GET_STUDENT_DISCIPLINE = "student/studentDiscipline";
    
    /** model attribute of student discipline. */
    private static final String MODEL_ATT_STUDENT_DISCIPLINE = "studentDiscipline";
    
    /** model attribute of student discipline list. */
    private static final String MODEL_ATT_STUDENT_DISCIPLINE_LIST = "studentDisciplineList";
    
    /** model attribute of warning Level List. */
    private static final String MODEL_ATT_WARNING_LEVEL_LIST = "warningLevelList";
    
    /** request mapping value for save or update student Discipline. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateStudentDiscipline.htm";
    
    /** request mapping value for delete Student Discipline. */
    private static final String REQ_MAP_VALUE_DELETE = "/deleteStudentDiscipline.htm";
    
    /** request value for Student Discipline id. */
    private static final String REQ_STUDENTDISCIPLINEID = "studentDisciplineId";
    
    /** session value for Student id. */
    private static final String STUDENT_ID = "studentId";
    
    /** session value for FaithLifeRating. */
    private static final String SESSION_FAITHLIFE_RATING = "averageFaithLifeRating";
    
    /** session value for AcademicLifeRating. */
    private static final String SESSION_ACADEMICLIFE_RATING = "averageAcademicLifeRating";
    
    /** key to define the averageAttendanceRating. */
    private static final String AVERAGE_ATTENDANCE_RATING = "averageAttendanceRating";
    
    /** key to define the attendanceRating. */
    private static final String MODEL_ATT_ATTENDANCE_RATING = "attendanceRating";
    
    /** String attribute for holding defalut_date_format. */
    private static final String DEFALUT_DATE_FORMAT = "yyyy-MM-dd";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** model attribute of student faithLife average value. */
    private static final String MODEL_ATT_STUDENT_FAITH_LIFE = "averageFaithLife";
    
    /** model attribute of student academic life average value. */
    private static final String MODEL_ATT_STUDENT_ACADEMIC_LIFE = "averageAcademicLife";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_DELETE = "REF.UI.STUDENTDISCIPLINE.DELETE";
    
    /** Represents the key for the student first date error message. */
    private static final String STUDENT_FIRST_DATE_ERROR = "STUDENT.FIRST.DATE.DISCIPLINARY.ERROR";
    
    /** StudentService attribute for holding studentService. */
    private StudentService studentService;
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** Holding studentDisciplineValidator instance of {@link StudentDisciplineValidator}. */
    private StudentDisciplineValidator studentDisciplineValidator;
    
    /** model attribute of message. */
    private static final String MODEL_ATT_MESSAGE = "message";
    
    /** model attribute of message. */
    private static final String DISPLAY_PANEL = "displayPanel";
    
    /** model attribute name for studentLeaveListToCheck. */
    private static final String MODEL_ATT_STUD_LEAVE_CHECK = "studentLeaveListToCheck";
    
    /** Represents the key for the student leave or holiday error. */
    private static final String STUDENT_LEAVE_DATE_DISCIPLINARY_ERROR = "STUDENT.LEAVE.DATE.DISCIPLINARY.ERROR";
    
    /** Represent the String constant "-01-01". */
    private static final String FIRST_DAY_STRING = "-01-01";
    
    /** Represent the String constant "-12-31". */
    private static final String LAST_DAY_STRING = "-12-31";
    
    /**
     * Set the studentDisciplineValidator to inject the validator.
     * 
     * @param studentDisciplineValidatorValue the studentDisciplineValidator to set
     */
    public void setStudentDisciplineValidator(StudentDisciplineValidator studentDisciplineValidatorValue) {

        this.studentDisciplineValidator = studentDisciplineValidatorValue;
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
     * Sets the common service.
     * 
     * @param objCommonService the commonService to set
     */
    public void setCommonService(CommonService objCommonService) {

        this.commonService = objCommonService;
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
     * handle GET requests for Student_Discipline view.
     * 
     * @param model - ModelMap
     * @param session - {@link HttpSession}
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showStudentDisciplineForm(ModelMap model, HttpSession session) {

        StudentDiscipline studentDiscipline = (StudentDiscipline) model.get(MODEL_ATT_STUDENT_DISCIPLINE);
        
        if (studentDiscipline == null) {
            studentDiscipline = new StudentDiscipline();
        }
        
        setAverageRatingValue(model, session);
        
        model.addAttribute(MODEL_ATT_STUDENT_DISCIPLINE, studentDiscipline);
        
        return VIEW_GET_STUDENT_DISCIPLINE;
    }
    
    /**
     * sets average faithLife and academic values to model.
     * 
     * @param model - ModelMap
     * @param session - {@link HttpSession}
     */
    private void setAverageRatingValue(ModelMap model, HttpSession session) {

        if (session.getAttribute(SESSION_FAITHLIFE_RATING) != null) {
            double faithLifeAverage = (Double) session.getAttribute(SESSION_FAITHLIFE_RATING);
            model.addAttribute(MODEL_ATT_STUDENT_FAITH_LIFE, (int) Math.round(faithLifeAverage));
        }
        
        if (session.getAttribute(SESSION_ACADEMICLIFE_RATING) != null) {
            double academicLifeAverage = (Double) session.getAttribute(SESSION_ACADEMICLIFE_RATING);
            model.addAttribute(MODEL_ATT_STUDENT_ACADEMIC_LIFE, (int) Math.round(academicLifeAverage));
        }
        if (session.getAttribute(AVERAGE_ATTENDANCE_RATING) != null) {
            double attendanceAverage = (Double) session.getAttribute(AVERAGE_ATTENDANCE_RATING);
            model.addAttribute(MODEL_ATT_ATTENDANCE_RATING, (int) Math.round(attendanceAverage));
        }
        
    }
    
    /**
     * Get all the available disciplines for a particular student.
     * 
     * @param session - {@link HttpSession}
     * @return list of StudentDiscipline
     * @throws AkuraAppException - Throw detailed exception
     */
    @ModelAttribute(MODEL_ATT_STUDENT_DISCIPLINE_LIST)
    public List<StudentDiscipline> populateStudentDiscipline(HttpSession session) throws AkuraAppException {

        int studentId = 0;
        
        if (session.getAttribute(STUDENT_ID) != null) {
            studentId = Integer.parseInt(session.getAttribute(STUDENT_ID) + String.valueOf(""));
        }
        
        return studentService.viewStudentDisciplineInfo(studentId);
        
    }
    
    /**
     * Populate list of student disciplines for the selected student.
     * 
     * @return list of WarningLevel
     * @throws AkuraAppException - Throw detailed exception
     */
    @ModelAttribute(MODEL_ATT_WARNING_LEVEL_LIST)
    public List<WarningLevel> populateWarningLevel() throws AkuraAppException {

        return SortUtil.sortWarningLevelList(commonService.viewAllWarningLevelInfo());
        
    }
    
    /**
     * If today is within any StudentLeave period, this method will return that list.
     * 
     * @param session - to get the studentId.
     * @return list of StudentLeave.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    @ModelAttribute(MODEL_ATT_STUD_LEAVE_CHECK)
    public List<StudentLeave> checkTodayIsWithinLeavePeriod(HttpSession session) throws AkuraAppException {

        int studentId = 0;
        
        if (session.getAttribute(STUDENT_ID) != null) {
            
            studentId = Integer.parseInt(session.getAttribute(STUDENT_ID) + String.valueOf(AkuraConstant.EMPTY_STRING));
        }
        
        return studentService.checkTodayIsWithinLeavePeriod(studentId);
    }
    
    /**
     * Manage Student Discipline details.
     * 
     * @param studentDiscipline - {@link StudentDiscipline}.
     * @param request - HttpServletRequest
     * @param model {@link ModelMap}
     * @param session {@link HttpSession}
     * @param bindingResult {@link BindingResult}
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - throw this
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateStudentDiscipline(
            @ModelAttribute(MODEL_ATT_STUDENT_DISCIPLINE) StudentDiscipline studentDiscipline,
            BindingResult bindingResult, HttpServletRequest request, ModelMap model, HttpSession session)
            throws AkuraAppException {

        String strMessage = null;
        String result = VIEW_POST_MANAGE_STUDENT_DISCIPLINE;
        studentDisciplineValidator.validate(studentDiscipline, bindingResult);
        
        if (bindingResult.hasErrors()) {
            setAverageRatingValue(model, session);
            model.addAttribute(DISPLAY_PANEL, true);
            result = VIEW_GET_STUDENT_DISCIPLINE;
            
        } else {
            // check if StudentDiscipline exist or not
            if (studentDiscipline.getStudentDisciplineId() == 0
                    && isExistsStudentDiscipline((Integer) session.getAttribute(STUDENT_ID), studentDiscipline)) {
                
                strMessage = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.STUDENT_DESCIPLINE_EXIST);
                model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                model.addAttribute(DISPLAY_PANEL, true);
                result = VIEW_GET_STUDENT_DISCIPLINE;
            } else if (session.getAttribute(STUDENT_ID) != null) {
                int studentId = Integer.parseInt(session.getAttribute(STUDENT_ID).toString());
                
                if (session.getAttribute(SESSION_USER_LOGIN) != null) {
                    
                    if (studentDiscipline != null) {
                        
                        UserLogin userLogin = (UserLogin) session.getAttribute(SESSION_USER_LOGIN);
                        
                        studentDiscipline.setStudentId(studentId);
                        
                        // check if this student is on leave on the date of disciplinary action.
                        if ((studentService.checkStudentIsOnLeave(studentId, studentDiscipline.getDate())
                                || DateUtil.isHoliday(
                                        getHolidayList(DateUtil.getStringYear(studentDiscipline.getDate())),
                                        studentDiscipline.getDate()))&&
                                        (studentService.getStudentStatusId(studentId)!=1) ) {
                            
                            strMessage = new ErrorMsgLoader().getErrorMessage(STUDENT_LEAVE_DATE_DISCIPLINARY_ERROR);
                            model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                            model.addAttribute(DISPLAY_PANEL, true);
                            result = VIEW_GET_STUDENT_DISCIPLINE;
                            
                        } else {
                            
                            // if the start date of the student is less than the disciplinary date,
                            // save or edit.
                            Date startedDate = studentService.getStudentStartedDate(studentId);
                            
                            if (startedDate != null && studentDiscipline.getDate().after(startedDate)
                                    || (startedDate != null && startedDate.equals(studentDiscipline.getDate()))) {
                                if (studentDiscipline.getStudentDisciplineId() == 0) {
                                    // add new record
                                    studentDiscipline.setUserLoginId(userLogin.getUserLoginId());
                                    studentService.addStudentDisciplineInfo(studentDiscipline);
                                } else {
                                    // edit record, UserLoginId must Not change( because admin can change any
                                    // record)
                                    if (studentDiscipline.getUserLoginId() == userLogin.getUserLoginId()
                                            || userLogin.getUserRoleId() == 1) {
                                        studentService.editStudentDisciplineInfo(studentDiscipline);
                                    }
                                }
                            } else {
                                if (startedDate != null) {
                                    strMessage = new ErrorMsgLoader().getErrorMessage(STUDENT_FIRST_DATE_ERROR);
                                    model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                                    model.addAttribute(DISPLAY_PANEL, true);
                                    result = VIEW_GET_STUDENT_DISCIPLINE;
                                }
                            }
                            
                        }
                    }
                    
                }
            }
        }
        return result;
    }
    
    /**
     * Delete a Student Discipline.
     * 
     * @param request {@link HttpServletRequest}
     * @param model {@link ModelMap}
     * @return name of the view.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteStudentDiscipline(HttpServletRequest request, ModelMap model) {

        StudentDiscipline studentDiscipline = null;
        
        try {
            int studentDisciplineId = Integer.parseInt(request.getParameter(REQ_STUDENTDISCIPLINEID));
            studentDiscipline = studentService.viewStudentDisciplineInfoById(studentDisciplineId);
            studentService.deleteStudentDisciplineInfo(studentDiscipline);
            
        } catch (AkuraAppException e) {
            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
            model.addAttribute(MODEL_ATT_STUDENT_DISCIPLINE, studentDiscipline);
            model.addAttribute(MESSAGE, message);
            
            return VIEW_GET_STUDENT_DISCIPLINE;
        }
        
        return VIEW_POST_MANAGE_STUDENT_DISCIPLINE;
    }
    
    /**
     * Check whether Discipline is already added for selected student.
     * 
     * @param studentId - int
     * @param studentDisciplineObj - StudentDisciplineObj
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    private boolean isExistsStudentDiscipline(int studentId, StudentDiscipline studentDisciplineObj)
            throws AkuraAppException {

        boolean isExists = false;
        
        String comment1 = studentDisciplineObj.getComment().trim();
        String descript = comment1.replaceAll(" ", "");
        Date date = studentDisciplineObj.getDate();
        int disciplinaryActionId = studentDisciplineObj.getStudentDisciplineId();
        List<StudentDiscipline> disciplinaryAction =
                studentService.findStudentDisciplineListForStudent(studentId, date);
        
        for (StudentDiscipline sda : disciplinaryAction) {
            if (sda.getComment() != null) {
                boolean check =
                        (disciplinaryActionId == 0) ? sda.getComment().replaceAll(" ", "").equalsIgnoreCase(descript)
                                : sda.getComment().replaceAll(" ", "").equals(descript);
                if (check) {
                    isExists = check;
                    break;
                }
            }
        }
        
        return isExists;
    }
    
    /**
     * Get the Holiday List for the given time period.
     * 
     * @param year - string year.
     * @return ListHoliday - a list containing holiday objects.
     * @throws AkuraAppException - AkuraAppException
     */
    private List<Holiday> getHolidayList(String year) throws AkuraAppException {

        String strStartDate = year + FIRST_DAY_STRING;
        String strEndDate = year + LAST_DAY_STRING;
        
        Date startDateToSearch = DateUtil.getParseDate(strStartDate);
        Date endDateToSearch = DateUtil.getParseDate(strEndDate);
        
        return commonService.findHolidayByYear(startDateToSearch, endDateToSearch);
    }
}
