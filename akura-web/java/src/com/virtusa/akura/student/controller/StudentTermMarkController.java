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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentSubTermMark;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.dto.StudentTermMarkSubjectCriteria;
import com.virtusa.akura.api.dto.SubTerm;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.enums.UserRole;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.StaticDataUtil;

/**
 * This is a controller where controls manage student term marks details.
 * 
 * @author Virtusa Corporation
 */

@Controller
public class StudentTermMarkController {
    
    /** Constant to hold markingCompleted string value. */
    private static final String SHOW_MARKING_COMPLETE = "showMarkingComplete";
    
    /** Constant to hold markingCompleted string value. */
    private static final String MARKING_COMPLETED = "markingCompleted";
    
    /** The Constant IS_MARKS. */
    private static final String IS_MARKS = "isMarks";
    
    /** The Constant FALSE. */
    private static final String FALSE = "false";
    
    /** The Constant STUDENT_SUB_TERM_MARKS_ALLOW_MARKS. */
    private static final String STUDENT_SUB_TERM_MARKS_ALLOW_MARKS = "student.sub.term.marks.allow.marks";
    
    /** Represents the regex pattern. */
    private static final String REGEXP_STRINGONLY = "[^A-Za-z ]";
    
    /** The Constant CONFIG. */
    private static final String CONFIG = "config";
    
    /**
     * commonService To invoke service methods.
     */
    private CommonService commonService;
    
    /**
     * UserService To invoke service methods.
     */
    private UserService userService;
    
    /**
     * studentService To invoke service methods.
     */
    private StudentService studentService;
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_INVALID_GRADING_ACRONYM = "REF.UI.STUDENT.MARKASSIGN.INVALIDGRADING";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_INVALID_TERM_MARK = "REF.UI.STUDENT.MARKASSIGN.INVALIDMARK";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_NO_TERM_MARK = "REF.UI.STUDENT.MARKASSIGN.NORECORD";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_EMPTY_TERM_MARK = "REF.UI.STUDENT.MARKASSIGN.EMPTYMARK";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_NO_SUBJECTS = "REF.UI.STUDENT.SUBJECTASSIGN.NOSUBJECTS";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_NO_CLASS = "REF.UI.STUDENT.MARKASSIGN.NULL.CLASS";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_NO_STUDENTS = "REF.UI.STUDENT.SUBJECTASSIGN.NOSTUDENTS";
    
    /** The Constant SUB_TERM_MARK_ID. */
    private static final String SUB_TERM_MARK_ID = "subTermMarkId";
    
    /** The Constant MONTHLYMARKS. */
    private static final String MONTHLYMARKS = "monthlymarks";
    
    /** The Constant TERMMARKID. */
    private static final String TERMMARKID = "termmarkid";
    
    /** The Constant GRADESUBJECTMARK. */
    private static final String GRADESUBJECTMARK = "gradesubjectmark";
    
    /** The Constant SELECT_GRID_ID. */
    private static final String SELECT_GRID_ID = "selectGridId";
    
    /** The Constant SAVE_OR_UPDATE_STUDENT_TERM_MARK_HTM. */
    private static final String SAVE_OR_UPDATE_STUDENT_TERM_MARK_HTM = "/saveOrUpdateStudentTermMark.htm";
    
    /** The Constant TERM_ID. */
    private static final String TERM_ID = "termId";
    
    /** The Constant CLASS_GRADE_ID. */
    private static final String CLASS_GRADE_ID = "classGradeId";
    
    /** The Constant GRADING_TYPE. */
    private static final String GRADING_TYPE = "gradingType";
    
    /** The Constant GRADE_SUBJECT_LIST. */
    private static final String GRADE_SUBJECT_LIST = "gradeSubjectList";
    
    /** The Constant STUDENT_LIST. */
    private static final String STUDENT_LIST = "studentList";
    
    /** The Constant GRADING_MAP. */
    private static final String GRADING_MAP = "gradingMap";
    
    /** The Constant SUBTERM_LIST_SIZE. */
    private static final String SUBTERM_LIST_SIZE = "subtermListSize";
    
    /** The Constant SUBTERM_LIST. */
    private static final String SUBTERM_LIST = "subtermList";
    
    /** The Constant STUDENTSUBTERMMARKMAP. */
    private static final String STUDENTSUBTERMMARKMAP = "studentsubtermmarkmap";
    
    /** The Constant STUDENTTERMMARKMAP. */
    private static final String STUDENTTERMMARKMAP = "studenttermmarkmap";
    
    /** The Constant SELECT_GRID. */
    private static final String SELECT_GRID = "selectGrid";
    
    /** The Constant SELECTED_TERM. */
    private static final String SELECTED_TERM = "selectedTerm";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_CLASS = "selectedClass";
    
    /** The Constant SEARCH_STUDENT_TERM_MARK_LIST_HTM. */
    private static final String SEARCH_STUDENT_TERM_MARK_LIST_HTM = "/searchStudentTermMarkList.htm";
    
    /** The Constant ADMIN_STUDENT_MARK. */
    private static final String ADMIN_STUDENT_MARK = "student/studentMark";
    
    /** The Constant SEARCHCLASSSTUDENTS_HTM. */
    private static final String SEARCHCLASSSTUDENTS_HTM = "searchclassstudents.htm";
    
    /** The Constant MODIFY_URL. */
    private static final String MODIFY_URL = "modifyURL";
    
    /** The Constant SEARCH_STUDENT_DTO. */
    private static final String SEARCH_STUDENT_DTO = "searchStudentDTO";
    
    /** The Constant STUDENT_TERM_LIST. */
    private static final String STUDENT_TERM_LIST = "studentTermList";
    
    /** The Constant GRADE_CLASS_LIST. */
    private static final String GRADE_CLASS_LIST = "gradeClassList";
    
    /** session attribute for user. */
    private static final String USER_LOGIN = "userLogin";
    
    /**
     * setter to inject StudentService object.
     * 
     * @param objStudentService the studentService to set
     */
    public void setStudentService(StudentService objStudentService) {
    
        this.studentService = objStudentService;
    }
    
    /**
     * setter to inject CommonService object.
     * 
     * @param objCommonService the commonService to set
     */
    public void setCommonService(CommonService objCommonService) {
    
        this.commonService = objCommonService;
    }
    
    /**
     * setter to inject UserService object.
     * 
     * @param objUserService the userService to set
     */
    public void setUserService(UserService objUserService) {
    
        this.userService = objUserService;
    }
    
    /**
     * Method to set values to class list in JSP.
     * 
     * @return a list of student classes.
     * @throws AkuraException SMS exception throw.
     */
    @ModelAttribute(GRADE_CLASS_LIST)
    public List<ClassGrade> populateStudentClassList() throws AkuraException {
    
        return SortUtil.sortClassGradeList(commonService.getClassGradeList());
    }
    
    /**
     * Method to set values to term list in JSP.
     * 
     * @return a list of terms.
     * @throws AkuraAppException sms exception throws.
     */
    @ModelAttribute(STUDENT_TERM_LIST)
    public List<Term> populateStudentTermList() throws AkuraAppException {
    
        return SortUtil.sortTermList(commonService.getTermList());
    }
    
    /**
     * Method to map GET requests from JSP.
     * 
     * @param model to hold model object for JSP.
     * @return the JSP to display.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showStudentTermMarkUserForm(ModelMap model) {
    
        StudentTermMarkSubjectCriteria studentTermMarkSubjectCriteria =
                (StudentTermMarkSubjectCriteria) model.get(SEARCH_STUDENT_DTO);
        if (studentTermMarkSubjectCriteria == null) {
            studentTermMarkSubjectCriteria = new StudentTermMarkSubjectCriteria();
        }
        
        model.addAttribute(MODIFY_URL, SEARCHCLASSSTUDENTS_HTM);
        model.addAttribute(SEARCH_STUDENT_DTO, studentTermMarkSubjectCriteria);
        
        return ADMIN_STUDENT_MARK;
    }
    
    /**
     * Method to map POST requests from JSP related to search.
     * 
     * @param request the request object
     * @param map the JSP model object
     * @return the JSP to display.
     * @throws AkuraAppException - throw detail exception
     */
    @RequestMapping(method = RequestMethod.POST, value = SEARCH_STUDENT_TERM_MARK_LIST_HTM)
    public String searchstudentsubjectlist(HttpServletRequest request, ModelMap map) throws AkuraAppException {
    
        try {
            String classGradeId =
                    (request.getParameter(SELECTED_CLASS) != null) ? request.getParameter(SELECTED_CLASS) : "0";
            String termId = request.getParameter(SELECTED_TERM);
            String gradingType = request.getParameter(SELECT_GRID);
            if (classGradeId.equals("0") || termId.equals("0") || gradingType.equals("0")) {
                populateErrorMessages(map, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            } else {
                
                List<StudentClassInfo> studentClassInfoList =
                        studentService.getClassStudentList(Integer.parseInt(classGradeId), DateUtil.currentYearOnly());
                
                List<GradeSubject> gradeSubjectList =
                        (!classGradeId.equals("0")) ? commonService.getGradeSubjectList(commonService
                                .findClassGrade(Integer.parseInt(classGradeId)).getGrade().getGradeId()) : null;
                
                if ((gradeSubjectList != null && gradeSubjectList.size() > 0)
                        && (studentClassInfoList != null && studentClassInfoList.size() > 0)) {
                    
                    List<StudentTermMark> studentTermMarkList =
                            studentService.getSelectedSubjectMarksByGrade(Integer.parseInt(termId),
                                    Integer.parseInt(classGradeId));
                    
                    Map<String, StudentTermMark> termmarkMap = new HashMap<String, StudentTermMark>();
                    List<Integer> termMarkIdList = new ArrayList<Integer>();
                    
                    // Generates student term mark map.
                    populateStudentTermMarkMap(termMarkIdList, studentTermMarkList, termmarkMap);
                    
                    if (gradingType.equalsIgnoreCase(AkuraConstant.TERM_GRADE)) {
                        
                        // set marking complete check box status
                        boolean showMarkingComplete =
                                isAuthorizedForSetMarkingComplete(request.getSession(), Integer.parseInt(classGradeId));
                        map.addAttribute(SHOW_MARKING_COMPLETE, showMarkingComplete);
                        
                        map.addAttribute(STUDENTTERMMARKMAP, termmarkMap);
                        
                        Calendar currentDate = Calendar.getInstance();
                        int year = currentDate.get(Calendar.YEAR);
                        
                        map.addAttribute(
                                MARKING_COMPLETED,
                                studentService.isMarkingCompletedForTerm(Integer.parseInt(classGradeId),
                                        Integer.parseInt(termId), year));
                    } else if (gradingType.equalsIgnoreCase(AkuraConstant.MONTHLY_GRADE)) {
                        
                        List<StudentSubTermMark> subtermMarkList = null;
                        Map<String, StudentSubTermMark> subTermmarkMap = new HashMap<String, StudentSubTermMark>();
                        
                        if (!termMarkIdList.isEmpty()) {
                            subtermMarkList = studentService.getSelectedSubTermMarksByGrade(termMarkIdList);
                        }
                        
                        if (subtermMarkList != null) {
                            
                            // Generates student sub term mark map.
                            populateStudentSubTermMarkMap(studentTermMarkList, subtermMarkList, subTermmarkMap);
                        }
                        map.addAttribute(STUDENTSUBTERMMARKMAP, subTermmarkMap);
                        
                        // Generates sub terms map.
                        populateSubTermList(map, termId);
                        
                        // Generates grading map.
                        populateGradingMap(map);
                        
                        map.addAttribute(IS_MARKS,
                                PropertyReader.getPropertyValue(CONFIG, STUDENT_SUB_TERM_MARKS_ALLOW_MARKS));
                    }
                    
                    map.addAttribute(STUDENT_LIST, studentClassInfoList);
                    map.addAttribute(GRADE_SUBJECT_LIST, gradeSubjectList);
                } else if (classGradeId.equals("0")) {
                    
                    // Generates error message when class is not selected
                    populateErrorMessages(map, ERROR_MSG_KEY_NO_CLASS);
                } else if ((gradeSubjectList == null || gradeSubjectList.isEmpty())) {
                    
                    // Generates error message when grade subject list is empty
                    populateErrorMessages(map, ERROR_MSG_KEY_NO_SUBJECTS);
                } else if ((studentClassInfoList == null || studentClassInfoList.isEmpty())) {
                    
                    // Generates error message when student class info list is empty
                    populateErrorMessages(map, ERROR_MSG_KEY_NO_STUDENTS);
                }
            }
            
            map.addAttribute(GRADING_TYPE, gradingType);
            map.addAttribute(CLASS_GRADE_ID, classGradeId);
            map.addAttribute(TERM_ID, termId);
        } catch (AkuraAppException e) {
            throw e;
        }
        return ADMIN_STUDENT_MARK;
    }
    
    /**
     * check whether this user is authorized for set 'marking complete'.
     * 
     * @param session - holds current session
     * @param classGradeId - class grade id
     * @return true for authorized users and false for unauthorized users.
     * @throws AkuraAppException - throw detail exception
     */
    private boolean isAuthorizedForSetMarkingComplete(HttpSession session, int classGradeId) throws AkuraAppException {
    
        boolean result = false;
        
        // get the current login user
        UserLogin currentUser = (UserLogin) session.getAttribute(USER_LOGIN);
        Date year = DateUtil.getDate(DateUtil.currentYearOnly());
        
        if (currentUser.getUserRoleId() == UserRole.ROLE_ADMIN.getUserRoleId()) {
            result = true;
        } else if (currentUser.getUserRoleId() == UserRole.ROLE_TEACHER.getUserRoleId()) {
            result = userService.isClassTeacher(currentUser.getUserLoginId(), year, classGradeId);
        }
        
        return result;
    }
    
    /**
     * Generates error message according to the message key.
     * 
     * @param map - holds {@link ModelMap}
     * @param messageKey - holds String type
     */
    private void populateErrorMessages(ModelMap map, String messageKey) {
    
        String message = new ErrorMsgLoader().getErrorMessage(messageKey);
        map.addAttribute(AkuraConstant.MESSAGE, message);
    }
    
    /**
     * Generates sub terms list and the size of list.
     * 
     * @param map - holds {@link ModelMap}
     * @param termId - holds integer type
     * @throws AkuraAppException - throw detail exception
     */
    private void populateSubTermList(ModelMap map, String termId) throws AkuraAppException {
    
        List<SubTerm> subTermsOfATerm = commonService.getSubTermsOfATerm(Integer.parseInt(termId));
        map.addAttribute(SUBTERM_LIST, subTermsOfATerm);
        map.addAttribute(SUBTERM_LIST_SIZE, subTermsOfATerm.size());
    }
    
    /**
     * Generates grading map.
     * 
     * @param map - holds {@link ModelMap}
     * @throws AkuraAppException - throw detail exception
     */
    private void populateGradingMap(ModelMap map) throws AkuraAppException {
    
        Map<Integer, String> gradingmap = new HashMap<Integer, String>();
        List<Grading> gradinglist = commonService.getGradingList();
        for (Grading grade : gradinglist) {
            gradingmap.put(grade.getGradingId(), grade.getGradeAcronym());
        }
        map.addAttribute(GRADING_MAP, gradingmap);
    }
    
    /**
     * Generates student sub term mark map.
     * 
     * @param studentTermMarkList - List of StudentTermMark
     * @param subtermMarkList - List of StudentSubTermMark
     * @param subTermmarkMap - Map with String key and StudentSubTermMark value
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void populateStudentSubTermMarkMap(List<StudentTermMark> studentTermMarkList,
            List<StudentSubTermMark> subtermMarkList, Map<String, StudentSubTermMark> subTermmarkMap)
            throws AkuraAppException {
    
        for (StudentSubTermMark subtermmark : subtermMarkList) {
            int gradeSubjectID = -1;
            int studentClassInfoID = -1;
            
            for (StudentTermMark termmark : studentTermMarkList) {
                if (termmark.getStudentTermMarkId() == subtermmark.getStuTermMarkID()) {
                    
                    gradeSubjectID = termmark.getGradeSubjectId();
                    studentClassInfoID = termmark.getStudentClassInfoId();
                }
            }
            if (subtermmark.getMarks() != null) {
                
                // gets the maximum mark for the grade subject key.
                int maximumMark = commonService.findGradeSubjectMaxMarkById(gradeSubjectID);
                subtermmark.setMarks(StaticDataUtil.previewMaximumMark(subtermmark.getMarks(), maximumMark));
            }
            int subTermId = subtermmark.getSubtermID();
            String subTermMarkIds =
                    Integer.toString(gradeSubjectID) + "," + Integer.toString(studentClassInfoID) + ","
                            + Integer.toString(subTermId);
            subTermmarkMap.put(subTermMarkIds, subtermmark);
            
        }
    }
    
    /**
     * Populate student term mark map.
     * 
     * @param termMarkIdList the term mark id list
     * @param studentTermMarkList the student term mark list
     * @param termmarkMap the term mark map
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void populateStudentTermMarkMap(List<Integer> termMarkIdList, List<StudentTermMark> studentTermMarkList,
            Map<String, StudentTermMark> termmarkMap) throws AkuraAppException {
    
        for (StudentTermMark termmark : studentTermMarkList) {
            int gradeSubjectID = termmark.getGradeSubjectId();
            int studentClassInfoID = termmark.getStudentClassInfoId();
            
            // gets the maximum mark for the grade subject key.
            int maximumMark = commonService.findGradeSubjectMaxMarkById(gradeSubjectID);
            termmark.setMarks(StaticDataUtil.previewMaximumMark(termmark.getMarks(), maximumMark));
            termMarkIdList.add(termmark.getStudentTermMarkId());
            String subjecttermmarkIds = Integer.toString(gradeSubjectID) + "," + Integer.toString(studentClassInfoID);
            termmarkMap.put(subjecttermmarkIds, termmark);
        }
    }
    
    /**
     * Method to save marks from JSP related to search.
     * 
     * @param request the request object
     * @param map the JSP model object
     * @return the JSP to display.
     * @throws AkuraAppException - throw detail exception
     */
    @RequestMapping(method = RequestMethod.POST, value = SAVE_OR_UPDATE_STUDENT_TERM_MARK_HTM)
    public String saveOrUpdateStudentSubjectList(HttpServletRequest request, ModelMap map) throws AkuraAppException {
    
        try {
            
            String gradingType = request.getParameter(SELECT_GRID_ID);
            ErrorMsgLoader errorMsgLoader = null;
            if (AkuraConstant.TERM_GRADE.equalsIgnoreCase(gradingType)) {
                
                // Handle term wise grades
                populateTermGrades(request, map, errorMsgLoader);
                searchstudentsubjectlist(request, map);
            } else if (AkuraConstant.MONTHLY_GRADE.equalsIgnoreCase(gradingType)) {
                
                // Check whether allow to enter marks or grading.
                // if it is "true" should allow to enter only marks for sub terms.
                String isMarks = PropertyReader.getPropertyValue(CONFIG, STUDENT_SUB_TERM_MARKS_ALLOW_MARKS);
                
                if (FALSE.equals(isMarks.trim())) {
                    
                    // Handle sub term wise grades
                    populateMonthlyGrades(request, map, errorMsgLoader);
                    searchstudentsubjectlist(request, map);
                } else {
                    
                    // Handle sub term marks.
                    handleMonlthyTermMarks(request, map, errorMsgLoader, isMarks);
                    searchstudentsubjectlist(request, map);
                    
                }
            }
        } catch (NullPointerException ex) {
            ErrorMsgLoader errorMsg = new ErrorMsgLoader();
            String message = errorMsg.getErrorMessage(ERROR_MSG_KEY_NO_TERM_MARK);
            map.addAttribute(AkuraConstant.MESSAGE, message);
            
        } catch (AkuraAppException e) {
            throw e;
        }
        
        return ADMIN_STUDENT_MARK;
    }
    
    /**
     * Handle monlthy term marks.
     * 
     * @param request the request
     * @param map the map
     * @param errorMsgLoader the error msg loader
     * @param isMarks the is marks
     * @throws AkuraAppException the akura app exception
     */
    private void handleMonlthyTermMarks(HttpServletRequest request, ModelMap map, ErrorMsgLoader errorMsgLoader,
            String isMarks) throws AkuraAppException {
    
        String[] studentSubTermMarks = request.getParameterValues(MONTHLYMARKS);
        String[] studentSubTermMarkIDs = request.getParameterValues(SUB_TERM_MARK_ID);
        List<StudentSubTermMark> studentSubTermMarkList = new ArrayList<StudentSubTermMark>();
        
        for (int i = 0; i < studentSubTermMarks.length; i++) {
            
            if (AkuraConstant.ABSENT.equalsIgnoreCase(studentSubTermMarks[i])) {
                StudentSubTermMark studentSubTermMark =
                        studentService.findStudentSubTermMark(Integer.parseInt(studentSubTermMarkIDs[i]));
                studentSubTermMark.setAbsent(true);
                studentSubTermMark.seteditMarkState(true);
                studentSubTermMarkList.add(studentSubTermMark);
            } else {
                
                if (StringUtils.isAlpha(studentSubTermMarks[i].trim())
                        && !StringUtils.isEmpty(studentSubTermMarks[i].trim())) {
                    errorMsgLoader = new ErrorMsgLoader();
                    String message = errorMsgLoader.getErrorMessage(ERROR_MSG_KEY_INVALID_TERM_MARK);
                    map.addAttribute(AkuraConstant.MESSAGE, message);
                } else {
                    float studentMonthlyMark = 0;
                    if (!StringUtils.isEmpty(studentSubTermMarks[i].trim())) {
                        studentMonthlyMark = Float.parseFloat(studentSubTermMarks[i]);
                    }
                    StudentSubTermMark studentSubTermMark =
                            studentService.findStudentSubTermMark(Integer.parseInt(studentSubTermMarkIDs[i]));
                    
                    // retrieves the relevant student term mark object.
                    StudentTermMark studentTermMark = getStudentTermMark(studentSubTermMark);
                    
                    // retrieves the maximum grade subject mark.
                    float gradeSubjectMaxMark =
                            commonService.findGradeSubjectMaxMarkById(studentTermMark.getGradeSubjectId());
                    
                    if (studentMonthlyMark >= AkuraConstant.MINIMUM_MARK && studentMonthlyMark <= gradeSubjectMaxMark) {
                        
                        studentSubTermMark.setGradingID(null);
                        
                        // calculates the marks and set.
                        studentSubTermMark.setMarks(setMark(studentMonthlyMark, gradeSubjectMaxMark));
                        
                        studentSubTermMark.setAbsent(false);
                        if (studentMonthlyMark != AkuraConstant.MINIMUM_MARK) {
                            studentSubTermMark.seteditMarkState(true);
                        }
                        studentSubTermMarkList.add(studentSubTermMark);
                    } else {
                        errorMsgLoader = new ErrorMsgLoader();
                        String message = errorMsgLoader.getErrorMessage(ERROR_MSG_KEY_INVALID_TERM_MARK);
                        map.addAttribute(AkuraConstant.MESSAGE, message);
                    }
                    
                }
            }
        }
        studentService.updateStudentSubTermMarkList(studentSubTermMarkList);
        
        map.addAttribute(IS_MARKS, isMarks);
    }
    
    /**
     * Retrieves the student term mark object relevant to the student sub term mark object.
     * 
     * @param studentSubTermMark - the relevant student sub term mark object.
     * @return - the student term mark object.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private StudentTermMark getStudentTermMark(StudentSubTermMark studentSubTermMark) throws AkuraAppException {
    
        int studentTermMarkId = studentSubTermMark.getStuTermMarkID();
        return studentService.findStudentTermMark(studentTermMarkId);
    }
    
    /**
     * Handle sub term wise grades.
     * 
     * @param request - {@link HttpServletRequest}
     * @param map - {@link ModelMap}
     * @param errorMsgLoader - {@link ErrorMsgLoader}
     * @throws AkuraAppException - throw detail exception
     */
    private void populateMonthlyGrades(HttpServletRequest request, ModelMap map, ErrorMsgLoader errorMsgLoader)
            throws AkuraAppException {
    
        String[] studentsubTermMarks = request.getParameterValues(MONTHLYMARKS);
        String[] studentSubTermMarkIDs = request.getParameterValues(SUB_TERM_MARK_ID);
        List<StudentSubTermMark> studentSubTermMarkList = new ArrayList<StudentSubTermMark>();
        Map<String, Integer> gradingmap = new HashMap<String, Integer>();
        List<Grading> gradinglist = commonService.getGradingList();
        for (Grading grade : gradinglist) {
            gradingmap.put(grade.getGradeAcronym(), grade.getGradingId());
        }
        
        for (int i = 0; i < studentsubTermMarks.length; i++) {
            boolean notEditedId = false;
            String gradeAcronim = studentsubTermMarks[i];
            Integer gradingId = null;
            if (gradeAcronim != "") {
                gradingId = gradingmap.get(gradeAcronim);
            } else if ("".equalsIgnoreCase(gradeAcronim)) {
                notEditedId = true;
            }
            if (AkuraConstant.ABSENT.equalsIgnoreCase(studentsubTermMarks[i])) {
                StudentSubTermMark studentSubTermMark =
                        studentService.findStudentSubTermMark(Integer.parseInt(studentSubTermMarkIDs[i]));
                studentSubTermMark.setAbsent(true);
                studentSubTermMark.seteditMarkState(true);
                studentSubTermMarkList.add(studentSubTermMark);
            } else if (gradingId != null) {
                StudentSubTermMark studentSubTermMark =
                        studentService.findStudentSubTermMark(Integer.parseInt(studentSubTermMarkIDs[i]));
                studentSubTermMark.setGradingID(gradingId);
                studentSubTermMark.setMarks(null);
                studentSubTermMark.seteditMarkState(true);
                studentSubTermMark.setAbsent(false);
                studentSubTermMarkList.add(studentSubTermMark);
                
            } else if (!notEditedId) {
                errorMsgLoader = new ErrorMsgLoader();
                String message = errorMsgLoader.getErrorMessage(ERROR_MSG_KEY_INVALID_GRADING_ACRONYM);
                map.addAttribute(AkuraConstant.MESSAGE, message);
            }
        }
        studentService.updateStudentSubTermMarkList(studentSubTermMarkList);
    }
    
    /**
     * Handle term wise grades.
     * 
     * @param request - {@link HttpServletRequest}
     * @param map - {@link ModelMap}
     * @param errorMsgLoader - {@link ErrorMsgLoader}
     * @throws AkuraAppException - throw detail exception
     */
    private void populateTermGrades(HttpServletRequest request, ModelMap map, ErrorMsgLoader errorMsgLoader)
            throws AkuraAppException {
    
        String message;
        String[] studentMarks = request.getParameterValues(GRADESUBJECTMARK);
        String[] studentTermMarkIDs = request.getParameterValues(TERMMARKID);
        List<StudentTermMark> studentTermMarkList = new ArrayList<StudentTermMark>();
        try {
            for (int i = 0; i < studentMarks.length; i++) {
                if (AkuraConstant.ABSENT.equalsIgnoreCase(studentMarks[i])) {
                    StudentTermMark studentTermMark =
                            studentService.findStudentTermMark(Integer.parseInt(studentTermMarkIDs[i]));
                    studentTermMark.setAbsent(true);
                    studentTermMark.setMarks(0);
                    studentTermMark.seteditMarkState(true);
                    studentTermMarkList.add(studentTermMark);
                } else if (!studentMarks[i].isEmpty()) {
                    Matcher makeMatch = checkPattern(studentMarks[i]);
                    
                    if (!makeMatch.find()) {
                        map.addAttribute(AkuraConstant.MESSAGE, AkuraWebConstant.MISMATCH_ERROR_MARKS);
                    } else {
                        float studentMark = Float.parseFloat(studentMarks[i]);
                        StudentTermMark studentTermMark =
                                studentService.findStudentTermMark(Integer.parseInt(studentTermMarkIDs[i]));
                        int gradeSubjectId = studentTermMark.getGradeSubjectId();
                        final float gradeSubjectMaxMark = commonService.findGradeSubjectMaxMarkById(gradeSubjectId);
                        if (studentMark >= AkuraConstant.MINIMUM_MARK && studentMark <= gradeSubjectMaxMark) {
                            
                            studentTermMark.setAbsent(false);
                            
                            // calculates the marks and save into the database.
                            float maxMark = setMark(studentMark, gradeSubjectMaxMark);
                            
                            studentTermMark.setMarks(maxMark);
                            
                            // if the mark is not the default value or updated then change the status.
                            if (studentMark != AkuraConstant.MINIMUM_MARK) {
                                studentTermMark.seteditMarkState(true);
                            }
                            
                            studentTermMarkList.add(studentTermMark);
                        } else {
                            errorMsgLoader = new ErrorMsgLoader();
                            message = errorMsgLoader.getErrorMessage(ERROR_MSG_KEY_INVALID_TERM_MARK);
                            map.addAttribute(AkuraConstant.MESSAGE, message);
                        }
                    }
                } else {
                    errorMsgLoader = new ErrorMsgLoader();
                    message = errorMsgLoader.getErrorMessage(ERROR_MSG_KEY_EMPTY_TERM_MARK);
                    map.addAttribute(AkuraConstant.MESSAGE, message);
                    
                }
            }
        } catch (NumberFormatException ex) {
            ErrorMsgLoader errorMsg = new ErrorMsgLoader();
            message = errorMsg.getErrorMessage(ERROR_MSG_KEY_INVALID_TERM_MARK);
            map.addAttribute(AkuraConstant.MESSAGE, message);
            
        }
        studentService.updateStudentSubjectList(studentTermMarkList);
        
        if (!studentMarks[0].isEmpty()) {
            StudentTermMark studentTermMark =
                    studentService.findStudentTermMark(Integer.parseInt(studentTermMarkIDs[0]));
            StudentClassInfo studentClassinfo =
                    studentService.findStudentClassInfoById(studentTermMark.getStudentClassInfoId());
            boolean markingCompleted = false;
            if (MARKING_COMPLETED.equals(request.getParameter(MARKING_COMPLETED))) {
                markingCompleted = true;
            }
            studentService.setMarkingFlag(markingCompleted, studentClassinfo.getClassGrade().getGrade().getGradeId(),
                    studentClassinfo.getClassGrade().getClassGradeId(), studentTermMark.getTermId(),
                    studentClassinfo.getYear());
        }
    }
    
    /**
     * Calculates the maximum mark and round it into one decimal. Sets to the student term/ sub term marks
     * object.
     * 
     * @param studentMark - the marks of the student.
     * @param gradeSubjectMaxMark - maximum mark for the grade subject.
     * @return - the maximum mark.
     */
    private float setMark(final float studentMark, final float gradeSubjectMaxMark) {
    
        final int percentage = 100;
        float maxMark = (studentMark / gradeSubjectMaxMark) * percentage;
        maxMark = (float) StaticDataUtil.roundNumber(maxMark, 0);
        return maxMark;
    }
    
    /**
     * Checks the pattern of the float.
     * 
     * @param marks - the marks of the Exam for a student.
     * @return - the status of the pattern of the Marks.
     */
    private Matcher checkPattern(final String marks) {
    
        Pattern stringOnly = Pattern.compile(REGEXP_STRINGONLY);
        return stringOnly.matcher(marks);
    }
}
