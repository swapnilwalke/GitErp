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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.AdminDetails;
import com.virtusa.akura.api.dto.Assignment;
import com.virtusa.akura.api.dto.AssignmentMarkView;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ClassWiseStudentsSubjectsDTO;
import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.dto.StudentAssignmentMark;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.SortUtil;

/**
 * The StudentAssignmentMarkController handle add student assignment marks entry and search by class.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class StudentAssignmentMarkController {
    
    /** The Constant STUDENT_ASSIGNMENT_MARK_LIST. */
    private static final String STUDENT_NEW_ASSIGNMENT_MARK_LIST = "studentNewAssignmentMarkList";
    
    /** The Constant STUDENT_OLD_ASSIGNMENT_MARK_LIST. */
    private static final String STUDENT_OLD_ASSIGNMENT_MARK_LIST = "studentOldAssignmentMarkList";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_CLASS = "selectedClass";
    
    /** The Constant SELECTED_ASSIGNMENT. */
    private static final String SELECTED_ASSIGNMENT_ID = "selectedAssignment";
    
    /** The Constant SEARCH_STUDENT_ASSIGNMENT_MARKS. */
    private static final String SEARCH_STUDENT_ASSIGNMENT_MARKS = "searchStudentAssignmentMarks.htm";
    
    /** The Constant GRADE_CLASS_LIST. */
    private static final String CLASS_GRADE_LIST = "classGradeList";
    
    /** The Constant STUDENT_STUDENT_ASSIGNMENT_MARKS. */
    private static final String STUDENT_STUDENT_ASSIGNMENT_MARKS = "student/studentAssignmentMarks";
    
    /** The Constant STUDENT_ASSIGNMENT_MARKS_HTM. */
    private static final String STUDENT_ASSIGNMENT_MARKS_HTM = "/studentAssignmentMarks.htm";
    
    /** The Constant SAVE_STUDENT_ASSIGNMENT_MARKS_HTM. */
    private static final String SAVE_STUDENT_ASSIGNMENT_MARKS_HTM = "saveStudentAssignmentMarks.htm";
    
    /** The Constant POPULATE_ASSIGNMENT_LIST_HTM. */
    private static final String POPULATE_ASSIGNMENT_LIST_HTM = "/populateAssignmentList.htm";
    
    /** The Constant REF_UI_STUDENT_MARKASSIGN_INVALIDGRADING. */
    private static final String REF_UI_STUDENT_MARKASSIGN_INVALIDGRADING = "REF.UI.STUDENT.MARKASSIGN.INVALIDGRADING";
    
    /** The Constant STUDENT_ASSIGNMENT_MARKS_INVALID. */
    private static final String STUDENT_ASSIGNMENT_MARKS_INVALID = "STUDENT.ASSIGNMENT.MARKS.INVALID";
    
    /** The Constant ASSIGNMENT_MARK_VALUE. */
    private static final String ASSIGNMENT_MARK_VALUE = "assignmentMarkValue";
    
    /** Represents the model key for the selected assignment key. */
    private static final String SELECTED_ASSIGNMENT_KEY = "selectedAssignmentKey";
    
    /** The Constant CLASS_GRADE_ID. */
    private static final String CLASS_GRADE_ID = "classGradeId";
    
    /** The Constant ASSIGNMENT_ID. */
    private static final String ASSIGNMENT_ID = "assignmentId";
    
    /** The Constant MESSAGE. */
    private static final String MESSAGE = "message";
    
    /** The Constant ASSIGNMENT. */
    private static final String ASSIGNMENT = "assignment";
    
    /** The Constant ASSIGNMENT_MARK_KEY. */
    private static final String ASSIGNMENT_MARK_KEY = "assignmentMarkKey";
    
    /** The Constant UPDATE_STUDENT_ASSIGNMENT_MARKS_HTM. */
    private static final String UPDATE_STUDENT_ASSIGNMENT_MARKS_HTM = "/updateStudentAssignmentMarks.htm";
    
    /** The Constant MARKS_TYPE. */
    private static final String MARKS_TYPE = "marksType";
    
    /** The Constant TRUE. */
    private static final String TRUE = "true";
    
    /** The Constant ROLE_TEACHER. */
    private static final String ROLE_TEACHER = "ROLE_TEACHER";
    
    /** The Constant USER. */
    private static final String USER = "user";
    
    /** The Constant STUDENT_ASSIGNMENT_MARKS_NO_RESULTS. */
    private static final String STUDENT_ASSIGNMENT_MARKS_NO_RESULTS = "STUDENT.ASSIGNMENT.MARKS.NO.RESULTS";
    
    /** attribute for holding student id. */
    private static final String STUDENT_ID = "studentId";
    
    /** attribute for holding view page. */
    private static final String VIEW_ASSIGNMENT_MARKS_DETAILS_PAGE = "student/assignmentMarksDetails";
    
    /** attribute for holding action. */
    private static final String VIEW_ACTION_FOR_SHOW_ASSIGNMENT_MARKS = "/showAssignmentMarks.htm";
    
    /** The Constant STUDENT_ASSIGNMENT_MARKS_EMPTY. */
    private static final String STUDENT_ASSIGNMENT_MARKS_EMPTY = "STUDENT.ASSIGNMENT.MARKS.EMPTY";
    
    /** The Constant REGEX_PATTERN. */
    private static final String REGEX_PATTERN = "[^0-9.]";
    
    /** attribute for holding model attribute key. */
    private static final String STUDENT_CLASS_INFO_LIST_TO_CHECK_STATUS = "studentClassInfoList";
    
    /** The common service. */
    private CommonService commonService;
    
    /** The student service. */
    private StudentService studentService;
    
    /**
     * Sets the common service.
     * 
     * @param objCommonService the commonService to set
     */
    public void setCommonService(CommonService objCommonService) {
    
        this.commonService = objCommonService;
    }
    
    /**
     * Sets the student service.
     * 
     * @param objStudentService the studentService to set
     */
    public void setStudentService(StudentService objStudentService) {
    
        this.studentService = objStudentService;
    }
    
    /**
     * View assignment mark entry page.
     * 
     * @param modelMap the model map
     * @return the model and view
     * @throws AkuraAppException the akura app exception
     */
    @RequestMapping(value = STUDENT_ASSIGNMENT_MARKS_HTM)
    public ModelAndView viewAssignmentMarkEntryPage(ModelMap modelMap) throws AkuraAppException {
    
        return new ModelAndView(STUDENT_STUDENT_ASSIGNMENT_MARKS);
    }
    
    /**
     * Search students for assignment mark entry.
     * 
     * @param modelMap the model map
     * @param request the request
     * @return the model and view
     * @throws AkuraAppException the akura app exception
     */
    @RequestMapping(value = SEARCH_STUDENT_ASSIGNMENT_MARKS)
    public ModelAndView searchStudentAssignmentMarks(ModelMap modelMap, HttpServletRequest request)
            throws AkuraAppException {
    
        String selectedAssignmentId = request.getParameter(SELECTED_ASSIGNMENT_ID);
        String selectedClass = request.getParameter(SELECTED_CLASS);
        int assignmentId = 0;
        int classId = 0;
        
        // checks
        if (selectedClass != null) {
            classId = Integer.parseInt(selectedClass);
            modelMap.addAttribute(SELECTED_CLASS, classId);
        }
        if (selectedAssignmentId != null) {
            assignmentId = Integer.parseInt(selectedAssignmentId);
            modelMap.addAttribute(SELECTED_ASSIGNMENT_KEY, assignmentId);
        }
        if (AkuraWebConstant.STRING_ZERO.equals(selectedAssignmentId) || selectedAssignmentId == null
                || AkuraWebConstant.STRING_ZERO.equals(selectedClass)) {
            String message = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_SLECTED_FIELD_ERROR_CODE);
            modelMap.addAttribute(MESSAGE, message);
        } else {
            
            Assignment assignment = commonService.findAssignmentById(assignmentId);
            
            // save new student's into assignment mark table
            
            int gradeSubjectId = assignment.getGradeSubject().getGradeSubjectId();
            Date currentYear = DateUtil.currentYear();
            
            StudentAssignmentMark assignMark = null;
            List<StudentAssignmentMark> studentAssignmentMarksList = new ArrayList<StudentAssignmentMark>();
            
            List<Integer> studentClassInfoObjList =
                    studentService.getStudentClassInfoIdsNotInAssignmentMarks(DateUtil.currentYearOnly());
            
            if (!studentClassInfoObjList.isEmpty()) {
                for (Integer stuClassInfoInt : studentClassInfoObjList) {
                    assignMark = new StudentAssignmentMark();
                    
                    assignMark.setStudentClassInfoId(stuClassInfoInt);
                    assignMark.setAssignmentId(Integer.parseInt(selectedAssignmentId));
                    assignMark.setGradeSubjectId(gradeSubjectId);
                    assignMark.setYear(currentYear);
                    studentAssignmentMarksList.add(assignMark);
                    
                    studentService.saveStudentAssignmentMarksList(studentAssignmentMarksList);
                }
            }
            
            List<Object[]> studentAssignmentMarks =
                    studentService.getStudentAssignmentMarksList(assignment.getGradeSubject().getGradeSubjectId(),
                            Integer.parseInt(selectedAssignmentId), DateUtil.currentYearOnly(),
                            Integer.parseInt(selectedClass));
            
            if (!studentAssignmentMarks.isEmpty()) {
                
                populateStudentsAssignmentMarks(modelMap, assignment, studentAssignmentMarks);
            } else {
                
                List<ClassWiseStudentsSubjectsDTO> classWiseStudentsSubjectsList =
                        studentService.getClassWiseStudentListBySubject(Integer.parseInt(selectedClass), assignment
                                .getGradeSubject().getGradeSubjectId(), DateUtil.currentYear());
                
                List<StudentClassInfo> checkSuspendedOrNot = new ArrayList<StudentClassInfo>();
                
                for (ClassWiseStudentsSubjectsDTO classWiseStudentsSubjectsDTO : classWiseStudentsSubjectsList) {
                    
                    StudentClassInfo studentClassInfo =
                            studentService.getStudentClassInfoByStudentClassInfoId(classWiseStudentsSubjectsDTO
                                    .getStudentClassInfoId());
                    
                    checkSuspendedOrNot.add(studentClassInfo);
                    
                }
                
                modelMap.addAttribute(STUDENT_CLASS_INFO_LIST_TO_CHECK_STATUS, checkSuspendedOrNot);
                modelMap.addAttribute(STUDENT_NEW_ASSIGNMENT_MARK_LIST, classWiseStudentsSubjectsList);
                
                if (classWiseStudentsSubjectsList.isEmpty()) {
                    String message = new ErrorMsgLoader().getErrorMessage(STUDENT_ASSIGNMENT_MARKS_NO_RESULTS);
                    modelMap.addAttribute(MESSAGE, message);
                }
            }
            
            modelMap.addAttribute(ASSIGNMENT, assignment);
            modelMap.addAttribute(SELECTED_CLASS, selectedClass);
        }
        
        return new ModelAndView(STUDENT_STUDENT_ASSIGNMENT_MARKS);
    }
    
    /**
     * Populate students assignment marks.
     * 
     * @param modelMap the model map
     * @param assignment the assignment
     * @param studentAssignmentMarks the student assignment marks
     * @throws AkuraAppException the akura app exception
     */
    private void populateStudentsAssignmentMarks(ModelMap modelMap, Assignment assignment,
            List<Object[]> studentAssignmentMarks) throws AkuraAppException {
    
        int indexOne = 1;
        int indexZero = 0;
        
        Map<StudentAssignmentMark, String> studentAssignmentMarksMap =
                new LinkedHashMap<StudentAssignmentMark, String>();
        for (Object[] object : studentAssignmentMarks) {
            StudentAssignmentMark studentAssignmentMark = (StudentAssignmentMark) object[indexZero];
            String studentName = (String) object[indexOne];
            if (assignment.getIsMarks()) {
                if (studentAssignmentMark.getMarks() == null && studentAssignmentMark.getIsAbsent()) {
                    studentAssignmentMarksMap.put(studentAssignmentMark, studentName
                            + AkuraWebConstant.UNDERSCORE_STRING + AkuraWebConstant.ABSENT_STRING);
                } else {
                    studentAssignmentMarksMap.put(studentAssignmentMark, studentName
                            + AkuraWebConstant.UNDERSCORE_STRING + studentAssignmentMark.getMarks());
                }
            } else {
                String gradingValue = AkuraWebConstant.EMPTY_STRING;
                if (studentAssignmentMark.getGradingId() != null) {
                    Grading grading = commonService.findGradingById(studentAssignmentMark.getGradingId());
                    gradingValue = grading.getGradeAcronym();
                } else {
                    if (studentAssignmentMark.getIsAbsent()) {
                        gradingValue = AkuraWebConstant.ABSENT_STRING;
                    }
                }
                
                studentAssignmentMarksMap.put(studentAssignmentMark, studentName + AkuraWebConstant.UNDERSCORE_STRING
                        + gradingValue);
            }
        }
        
        List<StudentClassInfo> checkSuspendedOrNot = new ArrayList<StudentClassInfo>();
        
        for (StudentAssignmentMark studentAssignmentMark : studentAssignmentMarksMap.keySet()) {
            
            StudentClassInfo studentClassInfo =
                    studentService.getStudentClassInfoByStudentClassInfoId(studentAssignmentMark
                            .getStudentClassInfoId());
            
            checkSuspendedOrNot.add(studentClassInfo);
        }
        
        modelMap.addAttribute(STUDENT_CLASS_INFO_LIST_TO_CHECK_STATUS, checkSuspendedOrNot);
        modelMap.addAttribute(STUDENT_OLD_ASSIGNMENT_MARK_LIST, studentAssignmentMarksMap);
    }
    
    /**
     * Save student assignment marks.
     * 
     * @param modelMap the model map
     * @param request the request
     * @return the model and view
     * @throws AkuraAppException the akura app exception
     */
    @RequestMapping(value = SAVE_STUDENT_ASSIGNMENT_MARKS_HTM)
    public ModelAndView saveStudentAssignmentMarks(ModelMap modelMap, HttpServletRequest request)
            throws AkuraAppException {
    
        String assignmentId = request.getParameter(ASSIGNMENT_ID);
        String classGradeId = request.getParameter(CLASS_GRADE_ID);
        String[] studentAssignmentMarksArray = request.getParameterValues(ASSIGNMENT_MARK_VALUE);
        int intAssignmentId = Integer.parseInt(assignmentId);
        
        Assignment assignment = commonService.findAssignmentById(intAssignmentId);
        
        boolean enteredMarks = false;
        for (String assignmentMark : studentAssignmentMarksArray) {
            if (StringUtils.isNotEmpty(assignmentMark)) {
                enteredMarks = true;
                break;
            }
        }
        
        List<StudentAssignmentMark> studentAssignmentMarksList = new ArrayList<StudentAssignmentMark>();
        
        if (enteredMarks) {
            
            int gradeSubjectId = 0;
            if (assignment != null) {
                gradeSubjectId = assignment.getGradeSubject().getGradeSubjectId();
            }
            
            int intClassGradeId = 0;
            if (classGradeId != null) {
                intClassGradeId = Integer.parseInt(classGradeId);
            }
            
            List<ClassWiseStudentsSubjectsDTO> classWiseStudentsSubjectsDTOs =
                    studentService.getClassWiseStudentListBySubject(intClassGradeId, gradeSubjectId,
                            DateUtil.currentYear());
            
            addAssignmentMark(modelMap, assignmentId, studentAssignmentMarksArray, assignment,
                    classWiseStudentsSubjectsDTOs, studentAssignmentMarksList);
            
        } else {
            String message = AkuraWebConstant.EMPTY_STRING;
            if (assignment.getIsMarks()) {
                message = new ErrorMsgLoader().getErrorMessage(STUDENT_ASSIGNMENT_MARKS_EMPTY);
            } else {
                message = new ErrorMsgLoader().getErrorMessage(REF_UI_STUDENT_MARKASSIGN_INVALIDGRADING);
            }
            modelMap.addAttribute(MESSAGE, message);
        }
        searchStudentAssignmentMarks(modelMap, request);
        return new ModelAndView(STUDENT_STUDENT_ASSIGNMENT_MARKS);
    }
    
    /**
     * Adds the assignment mark.
     * 
     * @param modelMap the model map
     * @param assignmentId the assignment id
     * @param studentAssignmentMarksArray the student assignment marks array
     * @param assignment the assignment
     * @param classWiseStudentsSubjectsDTOs the class wise students subjects dt os
     * @param studentAssignmentMarksList the student assignment marks list
     * @throws AkuraAppException the akura app exception
     */
    private void addAssignmentMark(ModelMap modelMap, String assignmentId, String[] studentAssignmentMarksArray,
            Assignment assignment, List<ClassWiseStudentsSubjectsDTO> classWiseStudentsSubjectsDTOs,
            List<StudentAssignmentMark> studentAssignmentMarksList) throws AkuraAppException {
    
        int count = 0;
        
        for (ClassWiseStudentsSubjectsDTO classWiseStudentsSubjectsDTO : classWiseStudentsSubjectsDTOs) {
            StudentAssignmentMark studentAssignmentMark = new StudentAssignmentMark();
            studentAssignmentMark.setAssignmentId(Integer.parseInt(assignmentId));
            studentAssignmentMark.setGradeSubjectId(classWiseStudentsSubjectsDTO.getGradeSubjectId());
            studentAssignmentMark.setStudentClassInfoId(classWiseStudentsSubjectsDTO.getStudentClassInfoId());
            studentAssignmentMark.setYear(DateUtil.currentYear());
            
            if (studentAssignmentMarksArray[count].equalsIgnoreCase(AkuraWebConstant.ABSENT_STRING)) {
                studentAssignmentMark.setIsAbsent(true);
                if (assignment.getIsMarks()) {
                    studentAssignmentMark.setMarks(null);
                } else {
                    studentAssignmentMark.setGradingId(null);
                }
            } else {
                if (!assignment.getIsMarks()) {
                    Map<String, Integer> gradingmap = gradingMap();
                    if (gradingmap.get(studentAssignmentMarksArray[count]) != null) {
                        studentAssignmentMark.setGradingId(gradingmap.get(studentAssignmentMarksArray[count]));
                    } else {
                        if ("".equals(studentAssignmentMarksArray[count])) {
                            studentAssignmentMark.setGradingId(null);
                            studentAssignmentMark.setIsAbsent(false);
                        } else {
                            String message =
                                    new ErrorMsgLoader().getErrorMessage(REF_UI_STUDENT_MARKASSIGN_INVALIDGRADING);
                            modelMap.addAttribute(MESSAGE, message);
                        }
                    }
                } else {
                    Pattern numbersOnly = Pattern.compile(REGEX_PATTERN);
                    String mark = studentAssignmentMarksArray[count].trim();
                    Matcher makeMatch = numbersOnly.matcher(mark);
                    boolean match = makeMatch.find();
                    if (match) {
                        String message = new ErrorMsgLoader().getErrorMessage(STUDENT_ASSIGNMENT_MARKS_INVALID);
                        modelMap.addAttribute(MESSAGE, message);
                    } else {
                        
                        // adds the mark if mark is not empty.
                        addMark(modelMap, studentAssignmentMark, mark);
                    }
                    
                }
            }
            
            studentAssignmentMarksList.add(studentAssignmentMark);
            
            count++;
        }
        
        studentService.saveStudentAssignmentMarksList(studentAssignmentMarksList);
    }
    
    /**
     * Grading map.
     * 
     * @return the map
     * @throws AkuraAppException the akura app exception
     */
    private Map<String, Integer> gradingMap() throws AkuraAppException {
    
        Map<String, Integer> gradingmap = new HashMap<String, Integer>();
        List<Grading> gradinglist = commonService.getGradingList();
        for (Grading grade : gradinglist) {
            gradingmap.put(grade.getGradeAcronym(), grade.getGradingId());
        }
        return gradingmap;
    }
    
    /**
     * Update student assignment marks.
     * 
     * @param modelMap the model map
     * @param request the request
     * @return the model and view
     * @throws AkuraAppException the akura app exception
     */
    @RequestMapping(UPDATE_STUDENT_ASSIGNMENT_MARKS_HTM)
    public ModelAndView updateStudentAssignmentMarks(ModelMap modelMap, HttpServletRequest request)
            throws AkuraAppException {
    
        String[] studentAssignmentMarksKey = request.getParameterValues(ASSIGNMENT_MARK_KEY);
        String[] studentAssignmentMarksValue = request.getParameterValues(ASSIGNMENT_MARK_VALUE);
        String isMarks = request.getParameter(MARKS_TYPE);
        List<StudentAssignmentMark> studentAssignmentMarksList = new ArrayList<StudentAssignmentMark>();
        
        int count = 0;
        
        for (String strStudentAssignmentMark : studentAssignmentMarksKey) {
            
            StudentAssignmentMark studentAssignmentMark =
                    studentService.getStudentAssignmentMarkById(Integer.parseInt(strStudentAssignmentMark));
            
            if (AkuraWebConstant.ABSENT_STRING.equalsIgnoreCase(studentAssignmentMarksValue[count])) {
                studentAssignmentMark.setIsAbsent(true);
                if (TRUE.equals(isMarks)) {
                    studentAssignmentMark.setMarks(null);
                } else {
                    studentAssignmentMark.setGradingId(null);
                }
            } else {
                studentAssignmentMark.setIsAbsent(false); // added to fix the issue not update ab in
                // report
                if (TRUE.equals(isMarks)) {
                    String mark = studentAssignmentMarksValue[count].trim();
                    Pattern numbersOnly = Pattern.compile(REGEX_PATTERN);
                    Matcher makeMatch = numbersOnly.matcher(mark);
                    boolean match = makeMatch.find();
                    if (match) {
                        String message = new ErrorMsgLoader().getErrorMessage(STUDENT_ASSIGNMENT_MARKS_INVALID);
                        modelMap.addAttribute(MESSAGE, message);
                        studentAssignmentMark.setMarks(null);
                    } else {
                        
                        // adds the mark if mark is not empty.
                        addMark(modelMap, studentAssignmentMark, mark);
                    }
                } else {
                    
                    Map<String, Integer> gradingmap = gradingMap();
                    
                    if (gradingmap.get(studentAssignmentMarksValue[count]) != null) {
                        studentAssignmentMark.setGradingId(gradingmap.get(studentAssignmentMarksValue[count]));
                        studentAssignmentMark.setIsAbsent(false);
                    } else {
                        if ("".equals(studentAssignmentMarksValue[count])) {
                            studentAssignmentMark.setGradingId(null);
                            studentAssignmentMark.setIsAbsent(false);
                        } else {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("[");
                            
                            for (Entry<String, Integer> entry : gradingmap.entrySet()) {
                                stringBuilder.append(entry.getKey());
                            }
                            stringBuilder.append("]");
                            
                            Pattern acronymsOnly = Pattern.compile(stringBuilder.toString());
                            Matcher matcher = acronymsOnly.matcher(studentAssignmentMarksValue[count]);
                            
                            if (!matcher.matches()) {
                                String message =
                                        new ErrorMsgLoader().getErrorMessage(REF_UI_STUDENT_MARKASSIGN_INVALIDGRADING);
                                modelMap.addAttribute(MESSAGE, message);
                            }
                        }
                    }
                }
            }
            
            studentAssignmentMarksList.add(studentAssignmentMark);
            count++;
        }
        
        studentService.saveStudentAssignmentMarksList(studentAssignmentMarksList);
        searchStudentAssignmentMarks(modelMap, request);
        return new ModelAndView(STUDENT_STUDENT_ASSIGNMENT_MARKS);
    }
    
    /**
     * Adds the marks if marks is not empty.
     * 
     * @param modelMap - a hash map contains the marks.
     * @param studentAssignmentMark - an instance of student assignment marks.
     * @param mark - the relevant mark.
     */
    private void addMark(ModelMap modelMap, StudentAssignmentMark studentAssignmentMark, String mark) {
    
        if (!mark.isEmpty()) {
            if ((Float.parseFloat(mark) >= AkuraConstant.MINIMUM_MARK && Float.parseFloat(mark) <= AkuraConstant.MAXIMUM_MARK)) {
                float marks = Float.parseFloat(mark);
                studentAssignmentMark.setMarks(marks);
            } else {
                String message = new ErrorMsgLoader().getErrorMessage(STUDENT_ASSIGNMENT_MARKS_INVALID);
                modelMap.addAttribute(MESSAGE, message);
                // break;
            }
        }
    }
    
    /**
     * Populate student class list.
     * 
     * @param request the request
     * @return the list
     * @throws AkuraException the akura exception
     */
    @ModelAttribute(CLASS_GRADE_LIST)
    public List<ClassGrade> populateStudentClassList(HttpServletRequest request) throws AkuraException {
    
        List<ClassGrade> classGrades = commonService.getClassGradeList();
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(USER);
        
        if (userInfo instanceof AdminDetails) {
            AdminDetails adminDetails = (AdminDetails) userInfo;
            if (ROLE_TEACHER.equals(adminDetails.getRole().trim())) {
                classGrades = commonService.getClassGradeByStaffRegistrationNo(adminDetails.getRegistrationNo());
            }
        }
        return SortUtil.sortClassGrades(classGrades);
    }
    
    /**
     * Populate student assignment list.
     * 
     * @param request the request
     * @return the string
     * @throws AkuraException the akura exception
     */
    @RequestMapping(POPULATE_ASSIGNMENT_LIST_HTM)
    @ResponseBody
    public String populateStudentAssignmentList(HttpServletRequest request) throws AkuraException {
    
        String classGradeId = request.getParameter(CLASS_GRADE_ID);
        List<Object[]> assignments =
                SortUtil.sortStudentAssignmentMarks(commonService.getAssignmentsListByGrade(Integer
                        .parseInt(classGradeId)));
        StringBuilder assignmentsListBuilder = new StringBuilder();
        StringBuilder assignmentBuilder = new StringBuilder();
        boolean isFirst = true;
        int indexZero = 0;
        int indexOne = 1;
        
        if (!assignments.isEmpty()) {
            
            for (Object[] object : assignments) {
                Assignment assignment = (Assignment) object[indexZero];
                String subject = (String) object[indexOne];
                
                assignmentBuilder.append(assignment.getAssignmentId());
                assignmentBuilder.append(AkuraWebConstant.UNDERSCORE_STRING);
                assignmentBuilder.append(assignment.getName());
                assignmentBuilder.append(AkuraWebConstant.UNDERSCORE_STRING);
                assignmentBuilder.append(subject);
                
                if (isFirst) {
                    assignmentsListBuilder.append(assignmentBuilder.toString());
                    isFirst = false;
                } else {
                    assignmentsListBuilder.append(AkuraWebConstant.STRING_COMMA);
                    assignmentsListBuilder.append(assignmentBuilder.toString());
                }
                
                assignmentBuilder.delete(0, assignmentBuilder.length());
            }
        }
        return assignmentsListBuilder.toString();
    }
    
    /**
     * Method to map GET requests from JSP.
     * 
     * @return the JSP to display.
     * @param model to hold model object for JSP.
     * @param request of the HttpServletRequest
     * @param session - HttpSession attribute
     * @throws AkuraAppException when exception occurs
     */
    @RequestMapping(value = VIEW_ACTION_FOR_SHOW_ASSIGNMENT_MARKS, method = RequestMethod.GET)
    public String showAssignmentMarks(ModelMap model, HttpServletRequest request, HttpSession session)
            throws AkuraAppException {
    
        // Getting jsp request parameters.
        String selectedYear = request.getParameter(AkuraWebConstant.YEAR);
        String subjectStr = request.getParameter(AkuraConstant.SUBJECT).trim();
        int intStudentId = 0;
        
        if (session.getAttribute(STUDENT_ID) != null) {
            
            // Gets the studentId from the session.
            intStudentId = (Integer) session.getAttribute(STUDENT_ID);
        }
        // Parsing parameters to integer type variables.
        int year = Integer.parseInt(selectedYear);
        
        List<AssignmentMarkView> studentAssignmentMarks =
                studentService.getStudentAssignmentMarksBySubject(intStudentId, DateUtil.getDate(year), subjectStr);
        
        request.setAttribute(AkuraWebConstant.SUBJECT_STRING, subjectStr);
        request.setAttribute(AkuraWebConstant.YEAR_STRING, year);
        request.setAttribute(AkuraWebConstant.ASSIGNMENT_MARKS_LIST, studentAssignmentMarks);
        
        return VIEW_ASSIGNMENT_MARKS_DETAILS_PAGE;
    }
    
}
