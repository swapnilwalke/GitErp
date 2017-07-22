/*
 * < ÃKURA, This application manages the daily activities of a Teacher and a Student of a School>
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentSubTermMark;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.dto.SubTerm;
import com.virtusa.akura.api.dto.Term;
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
 * StudentSubjectController is to handle search, save or update student subject operation related to student
 * module.
 * 
 * @author Virtusa Corporation
 */
@Controller
@SessionAttributes("studentSubjectAssignment")
public class StudentSubjectController {
    
    /** Logger to log the exceptions. */
    private static final Logger LOG = Logger.getLogger(StudentSubjectController.class);
    
    /** Request attribute for gradesubjectcheck. */
    private static final String REQ_GRADESUBJECTCHECK = "gradesubjectcheck";
    
    /** Request mapping value for saveorupdatestudentsubject. */
    private static final String REQ_VALUE_SAVEORUPDATESTUDENTSUBJECT = "/saveorupdatestudentsubject.htm";
    
    /** View get method Admin/StudentSubject. */
    private static final String VIEW_ADMIN_STUDENT_SUBJECT = "student/studentSubject";
    
    /** Model attribute of MODEL_ATT_SHOW_EDIT. */
    private static final String MODEL_ATT_SHOW_EDIT = "showEdit";
    
    /** Model attribute of MODEL_ATT_THIS_YEAR. */
    private static final String MODEL_ATT_THIS_YEAR = "thisYear";
    
    /** Model attribute of MODEL_ATT_CLASS_GRADE_ID. */
    private static final String MODEL_ATT_CLASS_GRADE_ID = "classGradeId";
    
    /** Request attribute of termMarkIdMap. */
    private static final String REQ_TERM_MARK_ID_MAP = "termMarkIdMap";
    
    /** Model attribute of gradeSubjectList. */
    private static final String MODEL_ATT_GRADE_SUBJECT_LIST = "gradeSubjectList";
    
    /** Model attribute of studentList. */
    private static final String MODEL_ATT_STUDENT_LIST = "studentList";
    
    /** Request attribute for year. */
    private static final String REQ_YEAR = "year";
    
    /** Request attribute for select. */
    private static final String REQ_SELECT = "select";
    
    /** Request attribute for studentsubjectmap. */
    private static final String REQ_STUDENTSUBJECTMAP = "studentsubjectmap";
    
    /** Request mapping value for searchStudentSubjectList. */
    private static final String REQ_VALUE_SEARCH_STUDENT_SUBJECT_LIST = "/searchStudentSubjectList.htm";
    
    /** Model attribute of yearList. */
    private static final String MODEL_ATT_YEAR_LIST = "yearList";
    
    /** Model attribute of gradeClassList. */
    private static final String MODEL_ATT_GRADE_CLASS_LIST = "gradeClassList";
    
    /** StudentService attribute for holding studentService. */
    private StudentService studentService;
    
    /** commonService attribute for holding CommonService. */
    private CommonService commonService;
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY = "REF.UI.STUDENT.SUBJECTASSIGN.ERROR";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_NO_SUBJECTS = "REF.UI.STUDENT.SUBJECTASSIGN.NOSUBJECTS";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_NO_STUDENTS = "REF.UI.STUDENT.SUBJECTASSIGN.NOSTUDENTS";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_NULL_CLASS = "REF.UI.STUDENT.MARKASSIGN.NULL.CLASS";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG = "REF.UI.STUDENT.TERMMARKCOMPLETED";
    
    /**
     * Set StudentService object.
     * 
     * @param objStudentService set student service object.
     */
    public void setStudentService(StudentService objStudentService) {
    
        this.studentService = objStudentService;
    }
    
    /**
     * Set CommonService object.
     * 
     * @param objcommonService set common service object.
     */
    public void setCommonService(CommonService objcommonService) {
    
        this.commonService = objcommonService;
    }
    
    /**
     * Method is to return studyMedium list reference data.
     * 
     * @return studyMediumList - studyMedium reference data.
     * @throws AkuraException - throw SMSExeption.
     */
    @ModelAttribute(MODEL_ATT_GRADE_CLASS_LIST)
    public List<ClassGrade> populateGradeClassList() throws AkuraException {
    
        return SortUtil.sortClassGradeList(commonService.getClassGradeList());
    }
    
    /**
     * Returns a list of years.
     * 
     * @return gradeClassList - a list of years.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(MODEL_ATT_YEAR_LIST)
    public List<String> populateYearList() throws AkuraAppException {
    
        List<String> yearList = new ArrayList<String>();
        final int maxYear = 5;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        yearList.add(String.valueOf(year + 1));
        yearList.add(String.valueOf(year));
        for (int index = 1; index < maxYear; index++) {
            int newYear = year - index;
            yearList.add(String.valueOf(newYear));
        }
        return yearList;
    }
    
    /**
     * Redirects to the Student_subject.jsp.
     * 
     * @param request Http request.
     * @param map model map to set data.
     * @return String value of jsp page to direct.
     * @throws AkuraAppException throw exception if occur.
     */
    @RequestMapping(method = RequestMethod.POST, value = REQ_VALUE_SEARCH_STUDENT_SUBJECT_LIST)
    public String searchstudentsubjectlist(HttpServletRequest request, ModelMap map) throws AkuraAppException {
    
        String message;
        try {
            
            request.getSession().removeAttribute(REQ_STUDENTSUBJECTMAP);
            String classGradeId = request.getParameter(REQ_SELECT);
            String year = request.getParameter(REQ_YEAR);
            int intYear = Integer.parseInt(year);
            
            /* selected class null check */
            if (classGradeId == null) {
                ErrorMsgLoader errorMsgLoader = new ErrorMsgLoader();
                message = errorMsgLoader.getErrorMessage(ERROR_MSG_KEY_NULL_CLASS);
                map.addAttribute(MESSAGE, message);
            } else {
                
                if (classGradeId.equals("0")) {
                    ErrorMsgLoader errorMsgLoader = new ErrorMsgLoader();
                    message = errorMsgLoader.getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
                    map.addAttribute(MESSAGE, message);
                } else {
                    ClassGrade classGrade = commonService.findClassGrade(Integer.parseInt(classGradeId));
                    List<StudentClassInfo> studentTempList =
                            studentService.getPresentClassStudentList(Integer.parseInt(classGradeId),
                                    Integer.parseInt(year));
                    
                    List<StudentClassInfo> nonCurrentStudents =
                            studentService.getNonCurrentStudentClassInfoList(Integer.parseInt(classGradeId),
                                    Integer.parseInt(year), DateUtil.getParseDate(DateUtil.getFormatDate(new Date())));
                    
                    studentTempList = ListUtils.subtract(studentTempList, nonCurrentStudents);
                    
                    List<GradeSubject> gradeSubjectList =
                            commonService.getGradeSubjectList(classGrade.getGrade().getGradeId());
                    
                    List<StudentTermMark> studentSubjectList =
                            studentService.viewStudentTermMarkInfoOfClassGrade(classGrade);
                    
                    if ((gradeSubjectList != null && gradeSubjectList.size() > 0)
                            && (studentTempList != null && studentTempList.size() > 0)) {
                        
                        // view the marks Panel.
                        viewMarksPanel(request, map, studentTempList, gradeSubjectList, studentSubjectList);
                        
                    } else if ((gradeSubjectList == null || gradeSubjectList.isEmpty())) {
                        ErrorMsgLoader errorMsgLoader = new ErrorMsgLoader();
                        message = errorMsgLoader.getErrorMessage(ERROR_MSG_KEY_NO_SUBJECTS);
                        map.addAttribute(MESSAGE, message);
                        
                    } else if ((studentTempList == null || studentTempList.isEmpty())) {
                        ErrorMsgLoader errorMsgLoader = new ErrorMsgLoader();
                        message = errorMsgLoader.getErrorMessage(ERROR_MSG_KEY_NO_STUDENTS);
                        map.addAttribute(MESSAGE, message);
                    }
                }
                
                map.addAttribute(MODEL_ATT_CLASS_GRADE_ID, classGradeId);
                map.addAttribute(MODEL_ATT_THIS_YEAR, year);
                
                generateEditStatus(map, intYear);
            }
            return VIEW_ADMIN_STUDENT_SUBJECT;
            
        } catch (DataAccessException ex) {
            LOG.error("StudentSubjectController - error occured while search syudent Subjects ");
            throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, ex);
        } catch (AkuraAppException ex) {
            LOG.error("StudentSubjectController - error occured while search syudent Subjects ");
            throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, ex);
        }
    }
    
    /**
     * Generates the status of edit status.
     * 
     * @param map - a hash map contains the edit status related information.
     * @param intYear - the relevant year.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    private void generateEditStatus(ModelMap map, int intYear) throws AkuraAppException {
    
        int thisYear = DateUtil.currentYearOnly();
        boolean showEdit = false;
        if (thisYear <= intYear) {
            showEdit = true;
        }
        map.addAttribute(MODEL_ATT_SHOW_EDIT, showEdit);
    }
    
    /**
     * Generates the details to be opened the student subjects panel.
     * 
     * @param classGradeId - the key of the class grade.
     * @param year - the relevant year.
     * @param request - a request scope object to store data.
     * @param map - a hash map contains the related information.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    private void generateStudentSubjectView(int classGradeId, int year, HttpServletRequest request, ModelMap map)
            throws AkuraAppException {
    
        ClassGrade classGrade = commonService.findClassGrade(classGradeId);
        List<StudentClassInfo> studentTempList = studentService.getPresentClassStudentList(classGradeId, year);
        
        List<StudentClassInfo> nonCurrentStudents =
                studentService.getNonCurrentStudentClassInfoList(classGradeId, year,
                        DateUtil.getParseDate(DateUtil.getFormatDate(new Date())));
        
        studentTempList = ListUtils.subtract(studentTempList, nonCurrentStudents);
        
        List<GradeSubject> gradeSubjectList = commonService.getGradeSubjectList(classGrade.getGrade().getGradeId());
        
        List<StudentTermMark> studentSubjectList = studentService.viewStudentTermMarkInfoOfClassGrade(classGrade);
        
        if ((gradeSubjectList != null && gradeSubjectList.size() > 0)
                && (studentTempList != null && studentTempList.size() > 0)) {
            
            // view the marks Panel.
            viewMarksPanel(request, map, studentTempList, gradeSubjectList, studentSubjectList);
            
        }
        map.addAttribute(MODEL_ATT_CLASS_GRADE_ID, classGradeId);
        map.addAttribute(MODEL_ATT_THIS_YEAR, year);
        
        generateEditStatus(map, year);
        
    }
    
    /**
     * Make visible the student subject panel open when doing changes.
     * 
     * @param request - the object of the request scope to keep data.
     * @param map - a hash map contains the student subject related data.
     * @param studentTempList - a list of student class info objects.
     * @param gradeSubjectList - a list of grade subject list
     * @param studentSubjectList - a list of student subjects.
     * @throws AkuraAppException
     */
    private void viewMarksPanel(HttpServletRequest request, ModelMap map, List<StudentClassInfo> studentTempList,
            List<GradeSubject> gradeSubjectList, List<StudentTermMark> studentSubjectList) throws AkuraAppException {
    
        Map<String, Integer> studentsubjectmap = new HashMap<String, Integer>();
        Map<Integer, String> sessionStudentsubjectmap = new HashMap<Integer, String>();
        StudentClassInfo studentClassInfo;
        for (StudentTermMark studentsubject : studentSubjectList) {
            int gradeSubjectID = studentsubject.getGradeSubjectId();
            int studentClassInfoID = studentsubject.getStudentClassInfoId();
            int termId = studentsubject.getTermId();
            studentClassInfo = studentService.getStudentClassInfoByStudentClassInfoId(studentClassInfoID);
            Date year = studentClassInfo.getYear();
            String strYear = DateUtil.getFormatDate(year);
            String subjecttermmarkIds1 =
                    Integer.toString(gradeSubjectID) + "," + Integer.toString(studentClassInfoID) + ","
                            + Integer.toString(termId) + "," + strYear;
            String subjecttermmarkIds = Integer.toString(gradeSubjectID) + "," + Integer.toString(studentClassInfoID);
            int studentTermMarkId = studentsubject.getStudentTermMarkId();
            studentsubjectmap.put(subjecttermmarkIds, studentTermMarkId);
            sessionStudentsubjectmap.put(studentTermMarkId, subjecttermmarkIds1);
        }
        map.addAttribute(MODEL_ATT_STUDENT_LIST, studentTempList);
        map.addAttribute(MODEL_ATT_GRADE_SUBJECT_LIST, gradeSubjectList);
        map.addAttribute(REQ_STUDENTSUBJECTMAP, studentsubjectmap);
        
        request.getSession().setAttribute(REQ_TERM_MARK_ID_MAP, sessionStudentsubjectmap);
    }
    
    /**
     * Redirects to the Student_subject.jsp.
     * 
     * @param request Http request.
     * @param map model map to set data.
     * @return String value of jsp page to direct.
     * @throws AkuraAppException throw exception if occur.
     */
    @RequestMapping(method = RequestMethod.POST, value = REQ_VALUE_SAVEORUPDATESTUDENTSUBJECT)
    public String saveorupdatestudentsubjectlist(HttpServletRequest request, ModelMap map) throws AkuraAppException {
    
        String classGradeId = request.getParameter(REQ_SELECT);
        int clsGradeId = Integer.parseInt(classGradeId);
        String year = request.getParameter(REQ_YEAR);
        int intYear = Integer.parseInt(year);
        // ArrayList flagTermId = new ArrayList<Integer>();
        
        String message;
        try {
            String[] studentSubjectIDs = request.getParameterValues(REQ_GRADESUBJECTCHECK);
            
            if (studentSubjectIDs != null) {
                Set<String> newTermMarkIdSet = new HashSet<String>();
                List<Term> terms = this.commonService.getTermList();
                Iterator<Term> termIter = terms.iterator();
                List<Integer> termIdListToDelete = new ArrayList<Integer>();
                while (termIter.hasNext()) {
                    
                    List<StudentTermMark> termMarkList = new ArrayList<StudentTermMark>();
                    Term objTerm = (Term) termIter.next();
                    
                    boolean isMarkComplete =
                            studentService.isMarkingCompletedForTerm(clsGradeId, objTerm.getTermId(), intYear);
                    if (!isMarkComplete) {
                        // term which is not marks completed
                        // flagTermId.add(objTerm.getTermId());
                        
                        for (String strStudentSubjectID : studentSubjectIDs) {
                            
                            String[] arguments = strStudentSubjectID.split(",");
                            String gradeSubjectID = arguments[0];
                            String studentClassID = arguments[1];
                            String termmarkID = arguments[2];
                            int intTermMarkID = Integer.parseInt(termmarkID);
                            
                            if (intTermMarkID == -1) {
                                
                                StudentTermMark termMark = new StudentTermMark();
                                termMark.setStudentClassInfoId(Integer.parseInt(studentClassID));
                                termMark.setGradeSubjectId(Integer.parseInt(gradeSubjectID));
                                termMark.setTermId(objTerm.getTermId());
                                termMarkList.add(termMark);
                                
                            } else {
                                newTermMarkIdSet.add(gradeSubjectID + "," + studentClassID);
                            }
                        }
                        if (termMarkList.size() > 0) {
                            List<StudentSubTermMark> subtermMarkList = new ArrayList<StudentSubTermMark>();
                            List<StudentTermMark> changedTermMarkList = new ArrayList<StudentTermMark>();
                            changedTermMarkList = studentService.saveOrUpdateStudentSubjectList(termMarkList);
                            
                            List<SubTerm> subterms = this.commonService.getSubTermList();
                            Iterator<SubTerm> subTermIter = subterms.iterator();
                            while (subTermIter.hasNext()) {
                                SubTerm objSubTerm = (SubTerm) subTermIter.next();
                                for (StudentTermMark objStudentTermMark : changedTermMarkList) {
                                    
                                    if (objSubTerm.getTermId() == objStudentTermMark.getTermId()) {
                                        StudentSubTermMark subtermMark = new StudentSubTermMark();
                                        subtermMark.setStuTermMarkID(objStudentTermMark.getStudentTermMarkId());
                                        subtermMark.setSubtermID(objSubTerm.getSubTermId());
                                        subtermMarkList.add(subtermMark);
                                        
                                    }
                                }
                            }
                            studentService.generateSubtermMarkRecords(subtermMarkList);
                        }
                        
                        @SuppressWarnings("unchecked")
                        Map<Integer, String> termMarkIdMap =
                                (Map<Integer, String>) request.getSession().getAttribute(REQ_TERM_MARK_ID_MAP);
                        
                        for (int key : termMarkIdMap.keySet()) {
                            String[] arguments = termMarkIdMap.get(key).split(",");
                            String sessionPara = arguments[0] + "," + arguments[1];
                            String termID = arguments[2];
                            String strYear = arguments[3];
                            String gradeSubjectId = arguments[0];
                            int intTerm = Integer.parseInt(termID);
                            String formatedYear = year + "-01" + "-01";
                            if ((formatedYear.equalsIgnoreCase(strYear)) && (objTerm.getTermId() == intTerm)) {
                                if (!newTermMarkIdSet.contains(sessionPara)) {
                                    if (commonService.findGradeSubject(Integer.parseInt(gradeSubjectId))
                                            .getIsOptionalSubject()) {
                                        termIdListToDelete.add(key);
                                       }
                                }
                            }
                            
                        }
                        
                    } else {
                        ErrorMsgLoader errorMsgLoader = new ErrorMsgLoader();
                        message = errorMsgLoader.getErrorMessage(ERROR_MSG);
                        map.addAttribute(MESSAGE, message);
                    }
                }
                if (termIdListToDelete.size() > 0) {
                    studentService.deleteStudentTermMark(termIdListToDelete);
                    
                }
                
            } else {
                ErrorMsgLoader errorMsgLoader = new ErrorMsgLoader();
                message = errorMsgLoader.getErrorMessage(ERROR_MSG_KEY);
                map.addAttribute(MESSAGE, message);
            }
            request.getSession().removeAttribute(REQ_STUDENTSUBJECTMAP);
            map.addAttribute(MODEL_ATT_THIS_YEAR, DateUtil.currentYearOnly());
            
            // make open the student subject panel after processing.
            generateStudentSubjectView(clsGradeId, intYear, request, map);
            return VIEW_ADMIN_STUDENT_SUBJECT;
            
        } catch (NumberFormatException ex) {
            throw new AkuraAppException(AkuraConstant.DATE_CONVERSION_ERROR, ex);
        } catch (DataAccessException ex) {
            LOG.error("StudentSubjectController - error occured while Update syudent Subjects ");
            throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, ex);
        } catch (AkuraAppException ex) {
            LOG.error("StudentSubjectController - error occured while Update syudent Subjects ");
            throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, ex);
        } finally {
            request.getSession().removeAttribute(REQ_STUDENTSUBJECTMAP);
        }
    }
    
    /**
     * handle GET requests for StudentSubject view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     * @throws AkuraAppException throw exception if occur.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showStudentDetailForm(ModelMap model) throws AkuraAppException {
    
        int thisYear = DateUtil.currentYearOnly();
        model.addAttribute(MODEL_ATT_THIS_YEAR, thisYear);
        
        return VIEW_ADMIN_STUDENT_SUBJECT;
    }
    
}
