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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.PrefectType;
import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentSearchCritiria;
import com.virtusa.akura.api.dto.StudentStatus;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.dto.WorkingSegment;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.SortUtil;

/**
 * Description: StudentSearchController controls student search.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class StudentSearchController {
    

    /** attribute for holding string new. */
    private static final String NEW = "New";
    
    /** attribute for holding string "year". */
    private static final String YEAR_STRING = "year";

    /** attribute for holding boolean value true. */
    private static final boolean BOOLEAN_VALUE_TRUE = true;
    
    /** attribute for holding boolean value false. */
    private static final boolean BOOLEAN_VALUE_FALSE = false;
    
    /** Model attribute of new student selected. */
    private static final String IS_SELECT_NEW = "isSelectNew";
    
    /** Model attribute of number of record. */
    private static final String NUMBER_OF_RECORDS = "numberOfRecords";
    
    /** Model attribute of criteria. */
    private static final String CRITIRIA = "critiria";
    
    /** Model attribute of search. */
    private static final String SEARCH = "search";
    
    /** Model attribute of Class list. */
    private static final String MODEL_ATT_CLASS_LIST = "classList";
    
    /** Model attribute of Sport list. */
    private static final String MODEL_ATT_SPORT_LIST = "sportList";
    
    /** Model attribute of Subject list. */
    private static final String MODEL_ATT_SUBJECT_LIST = "subjectList";
    
    /** Model attribute of PrefectType list. */
    private static final String MODEL_ATT_PREFECTTYPE_LIST = "prefectTypeList";
    
    /** Model attribute of Working Segment list. */
    private static final String MODEL_ATT_WORKING_SEGMENT_LIST = "workingSegmentList";
    
    /** Model attribute of Grade list. */
    private static final String MODEL_ATT_GRADE_LIST = "gradeList";
    
    /** Model attribute of Student's status list. */
    private static final String MODEL_ATT_STUDENT_STATUS_LIST = "studentStatusList";
    
    /** Model attribute of Student list. */
    private static final String STUDENT_LIST = "studentList";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_STUDENT_DELETE = "REF.UI.STUDENT.DELETE.ERROR";
    
    /** attribute for holding error message key in advance search. */
    private static final String STUDENT_ADVANCE_SEARCH_ERR = "REF.UI.STUDENT.ADVANCE.SEARCH";
    
    /** view get method student search page. */
    private static final String VIEW_GET_STUDENT_SEARCH_PAGE = "student/searchStudent";
    
    /** request mapping value for search. */
    private static final String REQ_MAP_VALUE_SEARCH = "/Search.htm";
    
    /** request mapping value for search student. */
    private static final String REQ_MAP_VALUE_SEARCH_STUDENT = "/studentSearch.htm";
    
    /** request mapping value for delete Student. */
    private static final String REQ_MAP_VALUE_DELETE = "/deleteStudent.htm";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** attribute for holding empty. */
    private static final String EMPTY = "Empty";
    
    /** attribute for holding request parameter name of selected studentId. */
    private static final String SELECTED_STUDENT_ID = "selectedStudentId";
    
    /** attribute for holding request parameter name of advanced search. */
    private static final String ADVANCED_SEARCH = "advancedSearch";
    
    /** attribute for holding request parameter name of start. */
    private static final String START_FROM = "startFrom";
    
    /** attribute for holding request parameter name of action type. */
    private static final String ACTION_TYPE = "actionType";
    
    /** Model attribute of "showEditSection". */
    private static final String SHOW_EDIT_SECTION = "showEditSection";
    
    /** attribute for holding "nocriteria". */
    private static final String NOCRITERIA = "nocriteria";

    /** attribute for holding "userId". */
    private static final String USER_ID_STRING = "userId";
    
    /** attribute for holding "lastDateOfYear". */
    private static final String LAST_DATE_OF_YEAR_PATTERN = "lastDateOfYear";
    
    /** attribute for holding "currentDate". */
    private static final String CURRENT_DATE_STRING = "currentDate";
    
    /** attribute for holding "yyyy-12-31". */
    private static final String LAST_DAY_OF_YEAR_PATTERN = "yyyy-12-31";
    
    /** attribute for holding "yyyy-MM-dd". */
    private static final String YEAR_PATTERN = "yyyy-MM-dd";
    
    /** attribute for holding "user". */
    private static final String USER_STRING = "user";
    
    /** StudentService attribute for holding studentService. */
    private StudentService studentService;
    
    /** UserService attribute for holding userService. */
    private UserService userService;
    
    /** Holds commonService instance of {@link CommonService}. */
    private CommonService commonService;
    
    /** Holds DateUtil instance. */
    private DateUtil dateUtil;
    
    /**
     * @return the dateUtil
     */
    public DateUtil getDateUtil() {

        return dateUtil;
    }
    
    /**
     * @param dateUtilVal the dateUtil to set
     */
    public void setDateUtil(DateUtil dateUtilVal) {

        this.dateUtil = dateUtilVal;
    }
    
    /**
     * Student details list in student search.
     */
    private List<?> studentList;
    
    /**
     * getter for StudentService.
     * 
     * @return studentService.
     */
    public StudentService getStudentService() {

        return studentService;
    }
    
    /**
     * setter for StudentService.
     * 
     * @param stuService - studentService
     */
    public void setStudentService(StudentService stuService) {

        this.studentService = stuService;
    }
    
    /**
     * @param userServiceValue the userService to set
     */
    public void setUserService(UserService userServiceValue) {

        this.userService = userServiceValue;
    }
    
    /**
     * Set the commonService to inject the service.
     * 
     * @param commonServiceValue the commonService to set
     */
    public void setCommonService(CommonService commonServiceValue) {

        this.commonService = commonServiceValue;
    }
    
    /**
     * This method handles the GET requests for Student_search view.
     * It returns a List of {@link Object} objects.
     * 
     * @param criteria - StudentSearchCritiria
     * @param request - HttpServletRequest.
     * @param model - ModelMap.
     * @param session - HttpSession
     * @return ModelAndView.
     * @throws AkuraAppException AkuraAppException.
     */
    @RequestMapping(REQ_MAP_VALUE_SEARCH)
    public ModelAndView studentSearch(@ModelAttribute(CRITIRIA) StudentSearchCritiria criteria,
            final HttpServletRequest request, ModelMap model, final HttpSession session) throws AkuraAppException {

        String actionType = request.getParameter(ACTION_TYPE);
        String strFrom = request.getParameter(START_FROM);
        int startFrom = Integer.parseInt(strFrom == null ? AkuraWebConstant.STRING_ZERO : strFrom);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        model.addAttribute(YEAR_STRING, year);
        criteria.setStartFrom(startFrom);
        
        int numberOfRecords;
        
        UserInfo userInfo = null;
        if (session.getAttribute(USER_STRING) != null) {
            userInfo = (UserInfo) session.getAttribute(USER_STRING);
        }
        
        int logginUserRoleId = userInfo != null ? userInfo.getUserRoleId() : 0;
        
        DateFormat dateFormat = new SimpleDateFormat(YEAR_PATTERN);
        DateFormat lastDateFormat = new SimpleDateFormat(LAST_DAY_OF_YEAR_PATTERN);
        Date date = new Date();
        
        try {
            model.addAttribute(CURRENT_DATE_STRING, DateUtil.convertStringToDate(dateFormat.format(date)));
            model.addAttribute(LAST_DATE_OF_YEAR_PATTERN, DateUtil.convertStringToDate(lastDateFormat.format(date)));
        } catch (ParseException e) {
            
            e.printStackTrace();
        }
        model.addAttribute(USER_ID_STRING, logginUserRoleId);
        
        if (SEARCH.equals(actionType)) {
            
            model.addAttribute(ACTION_TYPE, SEARCH);
            
            if (!criteria.getGrade().equals(AkuraWebConstant.STRING_ZERO)) {
                criteria.setYear(year);
            } else {
                criteria.setGrade(AkuraWebConstant.EMPTY_STRING);
            }
            if (criteria.getGrade().equals(NEW)) {
                model.addAttribute(IS_SELECT_NEW, BOOLEAN_VALUE_TRUE);
            }
            
            studentList = studentService.studentSearch(criteria);
            
        } else if (ADVANCED_SEARCH.equals(actionType)) {
            
            model.addAttribute(ACTION_TYPE, ADVANCED_SEARCH);
            
            if (criteria.getClazzId() == 0 && criteria.getSportId() == 0 && criteria.getSubjectId() == 0
                    && criteria.getPrefectTypeId() == 0 && criteria.getWorkingSegmentId() == 0) {
                
                String message = new ErrorMsgLoader().getErrorMessage(STUDENT_ADVANCE_SEARCH_ERR);
                
                model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                model.addAttribute(CRITIRIA, criteria);
                model.addAttribute(NOCRITERIA, message);
                return new ModelAndView(VIEW_GET_STUDENT_SEARCH_PAGE, model);
            }
            
            criteria.setYear(year);
            studentList = studentService.studentAdvanceSearch(criteria);
            
            // show the advanced search section if it returns no results.
            model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
        }
        numberOfRecords = studentList.size();
        int maxNumber = startFrom + AkuraConstant.PARAM_INDEX_TEN;
        criteria.setMaxNumber(maxNumber);
        
        List<?> searchReultsForPage = studentList;
        ModelMap modelMap = new ModelMap();
        
        if (searchReultsForPage.isEmpty()) {
            modelMap.addAttribute(STUDENT_LIST, EMPTY);
        } else {
            modelMap.addAttribute(STUDENT_LIST, searchReultsForPage);
        }
        
        modelMap.addAttribute(NUMBER_OF_RECORDS, numberOfRecords);
        modelMap.addAttribute(CRITIRIA, criteria);
        
        return new ModelAndView(VIEW_GET_STUDENT_SEARCH_PAGE, modelMap);
    }
    
    /**
     * Method is to return grade List.
     * 
     * @return gradeList - list of grades.
     * @throws AkuraAppException Exception
     */
    @ModelAttribute(MODEL_ATT_GRADE_LIST)
    public List<Grade> populateGradeList() throws AkuraAppException {

        return SortUtil.sortGradeList(commonService.getGradeList());
    }
    
    /**
     * Method is to return student status List.
     * 
     * @return studentStatusList - list of student's statuses.
     * @throws AkuraAppException Exception
     */
    @ModelAttribute(MODEL_ATT_STUDENT_STATUS_LIST)
    public List<StudentStatus> populateStudentStatusList() throws AkuraAppException {

        return commonService.getStudentStatusList();
    }
    
    /**
     * Method is to return grade List.
     * 
     * @return gradeList - list of grades.
     * @throws AkuraAppException Exception
     */
    @ModelAttribute(MODEL_ATT_CLASS_LIST)
    public List<ClassGrade> populateClassGradeList() throws AkuraAppException {

        return SortUtil.sortClassGradeList(commonService.getClassGradeList());
    }
    
    /**
     * Method is to return Sport list.
     * 
     * @return list of Sport
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_SPORT_LIST)
    public List<Sport> populateSportList() throws AkuraAppException {

        return SortUtil.sortSportList(commonService.getSportsList());
    }
    
    /**
     * Method is to return Subject list.
     * 
     * @return list of Subject
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_SUBJECT_LIST)
    public List<Subject> populateSubjectList() throws AkuraAppException {

        return SortUtil.sortSubjectList(commonService.getSubjectList());
    }
    
    /**
     * Method is to return PrefectType list.
     * 
     * @return list of PrefectType
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_PREFECTTYPE_LIST)
    public List<PrefectType> populatePrefectTypeList() throws AkuraAppException {

        return SortUtil.sortPrefectTypeList(commonService.getPrefectTypeList());
    }
    
    /**
     * Method is to return WorkingSegment list.
     * 
     * @return list of WorkingSegment
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_WORKING_SEGMENT_LIST)
    public List<WorkingSegment> populateWorkingSegmentList() throws AkuraAppException {

        return SortUtil.sortWorkingSegmentList(commonService.getWorkingSegmentList());
    }
    
    /**
     * handle GET requests for Search_student view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(REQ_MAP_VALUE_SEARCH_STUDENT)
    public String showStudentSearchPage(ModelMap model) {

        StudentSearchCritiria critiria = new StudentSearchCritiria();
        model.addAttribute(CRITIRIA, critiria);
        model.addAttribute(IS_SELECT_NEW, BOOLEAN_VALUE_FALSE);
        model.addAttribute(ACTION_TYPE, SEARCH);
        return VIEW_GET_STUDENT_SEARCH_PAGE;
        
    }
    
    /**
     * Method is to delete student.
     * 
     * @param request - HttpServletRequest
     * @param model - ModelMap attributes.
     * @return redirect to student search page.
     * @throws AkuraAppException Exception
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteStudent(HttpServletRequest request, ModelMap model) throws AkuraAppException {

        try {
            int studentId = Integer.parseInt(request.getParameter(SELECTED_STUDENT_ID));
            Student student = studentService.findStudent(studentId);
            
            // Delete if user login exist for this student
            UserLogin userLogin = userService.getUserLoginByIdentificationNo(student.getAdmissionNo());
            if (userLogin != null) {
                userService.deleteUser(userLogin);
            }
            
            studentService.deleteStudent(studentId);
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_STUDENT_DELETE);
                model.addAttribute(MESSAGE, message);
            }
        }
        StudentSearchCritiria critiria = new StudentSearchCritiria();
        model.addAttribute(CRITIRIA, critiria);
        return VIEW_GET_STUDENT_SEARCH_PAGE;
    }
    
}
