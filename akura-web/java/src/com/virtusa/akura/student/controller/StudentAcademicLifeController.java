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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.list.TreeList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.Achievement;
import com.virtusa.akura.api.dto.ExamResults;
import com.virtusa.akura.api.dto.PrefectType;
import com.virtusa.akura.api.dto.Scholarship;
import com.virtusa.akura.api.dto.Seminar;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentPrefect;
import com.virtusa.akura.api.dto.StudentScholarship;
import com.virtusa.akura.api.dto.StudentSeminar;
import com.virtusa.akura.api.dto.StudentSubjectAverageViewDTO;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.delegate.LoginDelegate;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;

/**
 * The Class StudentAcademicLifeController.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class StudentAcademicLifeController {
    
    /** holds request parameter 'selectedScholarshipType'. */
    private static final String SELECTED_SCHOLARSHIP_TYPE = "selectedScholarshipType";
    
    /** string constant for hold model attribute 'achievement'. */
    private static final String ACHIEVEMENT = "achievement";
    
    /** string constant for hold model attribute 'scholar'. */
    private static final String SCHOLAR = "scholar";
    
    /** string constant for hold model attribute 'prefect'. */
    private static final String PREFECT = "prefect";
    
    /** string constant for hold model attribute 'seminar'. */
    private static final String SEMINAR = "seminar";
    
    /** string constant for hold model attribute 'error_msg_section'. */
    private static final String ERROR_MSG_SECTION = "error_msg_section";
    
    /** Represents the exam marks detail page. */
    private static final String EXAM_MARKS_URL = "student/examMarksDetails";
    
    /** Represents the model name for the exam list. */
    private static final String EXAM_LIST = "examList";
    
    /** Represents the model name for the exam marks list. */
    private static final String EXAM_MARKS_LIST = "examMarksList";
    
    /** Represents the exam marks request mapping. */
    private static final String SHOW_EXAM_MARKS_RESULT = "/showExamMarks.htm";
    
    /** Request parameter name for Student Seminar List. */
    private static final String STU_SEM_LIST = "stuSemList";
    
    /** Request parameter name for Seminar List. */
    private static final String SEMINAR_LIST = "seminarList";
    
    /** constant hold parameter for student id. */
    private static final String STUDENT_ID = "studentId";
    
    /** Request parameter name for id(foreign key) in StudentSeminar. */
    private static final String SELECT_SEM_ID = "selectSemID";
    
    /** Request parameter name for Description in StudentSeminar. */
    private static final String SEMINAR_DETAIL_AREA = "seminarDetailArea";
    
    /** Request parameter name for id(key) in StudentSeminar. */
    private static final String STUDENT_SEM_ID = "StudentSemID";
    
    /** Url pattern to delete seminar for student. */
    private static final String DELETE_SEMINARS_DETAILS = "deleteSeminarsDetails.htm";
    
    /** Url pattern to edit or add new seminar for student. */
    
    private static final String SAVE_EDIT_SEMINARS_DETAILS = "saveEditSeminarsDetails.htm";
    
    /**
     * key to hold string selectedGrade.
     */
    private static final String SELECTED_GRADE = "selectedGrade";
    
    /** model attribute of studentGrade list. */
    private static final String MODEL_ATT_STUDENT_GRADE_LIST = "studentGradeList";
    
    /**
     * key to hold model attribute StudentClassId.
     */
    private static final String MODEL_ATT_SELECTED_ID = "selectedStudClassId";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_DUPLICATE_PREFECT_TYPE = "REF.UI.STUDENT.DUPLICATEPREFECTTYPE";
    
    /** attribute for holding error message key. */
    private static final String REF_UI_STUDENT_EPREFECT_TYPE_REQUIRED = "REF.UI.STUDENT.EPREFECT.TYPE.REQUIRED";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_DUPLICATE_SCHOLARSHIP_TYPE = "REF.UI.STUDENT.DUPLICATESCHOLARSHIP_TYPE";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_SCHOLARSHIP_REQUIRED = "REF.UI.STUDENT.SCHOLARSHIP.REQUIRED";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_DUPLICATE_ACHIEVEMENT_TYPE = "REF.UI.STUDENT.DUPLICATEACHIEVEMENT_TYPE";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_MANDATORY_FIELDS = "STA.UI.MANDATORY.FIELD.REQUIRED";
    
    /** attribute for holding view page. */
    private static final String VIEW_ACADEMIC_LIFE_PAGE = "student/academicDetails";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_SHOW_MARKS = "/showMarks.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_SAVE_PREFECT = "/savePrefectDetails.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_EDIT_PREFECT = "/editPrefectDetails.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_DELETE_PREFECT = "/deletePrefectDetails.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_SAVE_SCHOLARSHIP = "/saveScholarshipDetails.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_EDIT_SCHOLARSHIP = "/editScholarshipDetails.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_DELETE_SCHOLARSHIP = "/deleteScholarshipDetails.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_SAVE_ACHIEVEMENT = "/saveAchievementDetails.htm";
    
    /** key to define the averageFaithLifeRating. */
    private static final String AVERAGE_FAITH_LIFE_RATING = "averageFaithLifeRating";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_EDIT_ACHIEVEMENT = "/editAchievementDetails.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_DELETE_ACHIEVEMENT = "/deleteAchievementDetails.htm";
    
    /** attribute for holding map key. */
    private static final String MAP_KEY_NAME_ACHIEVEMENT = "Achievement";
    
    /** attribute for holding attribute name. */
    private static final String ATTR_NAME_YEAR = "Year";
    
    /** model attribute of student academic life average value. */
    private static final String MODEL_ATT_STUDENT_ACADEMIC_LIFE = "averageAcademicLife";
    
    /** key to define the averageAcademicLifeRating. */
    private static final String AVERAGE_ACADEMIC_LIFE_RATING = "averageAcademicLifeRating";
    
    /** model attribute of student faithLife average value. */
    private static final String MODEL_ATT_STUDENT_FAITH_LIFE = "averageFaithLife";
    
    /** key to define the averageAttendanceRating. */
    private static final String AVERAGE_ATTENDANCE_RATING = "averageAttendanceRating";
    
    /** key to define the attendanceRating. */
    private static final String MODEL_ATT_ATTENDANCE_RATING = "attendanceRating";
    
    /** property file name. */
    private static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";
    
    /** key to define the file size of the image. */
    private static final String COLOR_GREEN_LOWER = "color.green.lower";
    
    /** key to define the file size of the image. */
    private static final String COLOR_YELLOW_UPPER = "color.yellow.upper";
    
    /** key to define the file size of the image. */
    private static final String COLOR_YELLOW_LOWER = "color.yellow.lower";
    
    /** key to define the file size of the image. */
    private static final String COLOR_RED_UPPER = "color.red.upper";
    
    /** The Constant ACHIEVEMENT_TEXT_AREA. */
    private static final String ACHIEVEMENT_TEXT_AREA = "achievementTextArea";
    
    /** The Constant SELECTED_STUDENT_ACHIEVEMENT_ID. */
    private static final String SELECTED_STUDENT_ACHIEVEMENT_ID = "selectedStudentAchievementId";
    
    /** The Constant PREFECTS. */
    private static final String PREFECTS = "Prefects";
    
    /** The Constant SCHOLARSHIPS. */
    private static final String SCHOLARSHIPS = "Scholarships";
    
    /** The Constant PREFECT_TYPE_LIST. */
    private static final String PREFECT_TYPE_LIST = "PrefectTypeList";
    
    /** The Constant PREFECT_TYPE_LIST_SIZE. */
    private static final String PREFECT_TYPE_LIST_SIZE = "prefectTypeListSize";
    
    /** The Constant SCHOLARSHIP_LIST. */
    private static final String SCHOLARSHIP_LIST = "ScholarshipList";
    
    /** The Constant SCHOLARSHIP_LIST_SIZE. */
    private static final String SCHOLARSHIP_LIST_SIZE = "scholarshipListSize";
    
    /** The Constant STUDENT_GRADE. */
    private static final String STUDENT_GRADE = "studentGrade";
    
    /** The Constant TERM_LIST. */
    private static final String TERM_LIST = "TermList";
    
    /** The Constant RED_UPPER. */
    private static final String RED_UPPER = "redUpper";
    
    /** The Constant YELLOW_LOWER. */
    private static final String YELLOW_LOWER = "yellowLower";
    
    /** The Constant YELLOW_UPPER. */
    private static final String YELLOW_UPPER = "yellowUpper";
    
    /** The Constant GREEN_LOWER. */
    private static final String GREEN_LOWER = "greenLower";
    
    /** The Constant SELECTED_PREFECT_TYPE. */
    private static final String SELECTED_PREFECT_TYPE = "selectedPrefectType";
    
    /** The Constant AVERAGE_TERM_MARK_LIST. */
    private static final String AVERAGE_TERM_MARK_LIST = "AvgMarkList";
    
    /** The Constant TOTAL_TERM_MARK_LIST. */
    private static final String TOTAL_TERM_MARK_LIST = "TotMarkList";
    
    /** Represents an instance of StudentLoginService. */
    private LoginDelegate studentLoginDelegate;
    
    /**
     * Sets an instance of StudentLoginService.
     * 
     * @param studentLoginDelegateVal - an instance of StudentLoginService.
     */
    public void setStudentLoginDelegate(LoginDelegate studentLoginDelegateVal) {

        this.studentLoginDelegate = studentLoginDelegateVal;
    }
    
    /**
     * commonService To invoke service methods.
     */
    private CommonService commonService;
    
    /**
     * studentService To invoke service methods.
     */
    private StudentService studentService;
    
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
     * handle GET requests for Academic-details view.
     * 
     * @param request - HttpServletRequest attribute.
     * @param modelMap - ModelMap attribute.
     * @param session - HttpSession.
     * @return the name of the view.
     * @throws AkuraAppException - throws when exception occurs
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showStudentMarksDetails(HttpServletRequest request, ModelMap modelMap, HttpSession session)
            throws AkuraAppException {

        return showMarks(request, modelMap, session);
    }
    
    /**
     * Method is to return StudentClassInfo list.
     * 
     * @param session - HttpSession session
     * @return list of StudentClassInfo
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_STUDENT_GRADE_LIST)
    public List<StudentClassInfo> populateStudentGradeList(HttpSession session) throws AkuraAppException {

        int studentId = 0;
        if (session.getAttribute(AkuraConstant.STUDENT_ID) != null) {
            studentId = (Integer) session.getAttribute(AkuraConstant.STUDENT_ID);
        }
        List<StudentClassInfo> gredeList = studentService.getStudentClassInfoByStudentId2(studentId);
        List<StudentClassInfo> selectedGradeList = new ArrayList<StudentClassInfo>();
        
        for (StudentClassInfo classInfo : gredeList) {
            if (!classInfo.getYear().after(DateUtil.currentYear())) {
                selectedGradeList.add(classInfo);
            }
            
        }
        
        return selectedGradeList;
    }
    
    /**
     * handle POST requests for Academic-details view with student term mark data.
     * 
     * @param request - HttpServletRequest.
     * @param modelMap - ModelMap attribute.
     * @param session - HttpSession attribute.
     * @return the name of the view.
     * @throws AkuraAppException throws when exception occurs.
     */
    @RequestMapping(value = ACTION_FOR_SHOW_MARKS, method = RequestMethod.POST)
    public ModelAndView showMarks(HttpServletRequest request, ModelMap modelMap, HttpSession session)
            throws AkuraAppException {

        String yearA = null;
        String studentGrade = "";
        int intYear = 0;
        String selectedStudClassId = null;
        
        if (request.getParameter(SELECTED_GRADE) != null) {
            StudentClassInfo studentClassInfo =
                    studentService.findStudentClassInfoById(Integer.parseInt(request.getParameter(SELECTED_GRADE)));
            selectedStudClassId = studentClassInfo.getClassGrade().getGrade().getDescription();
            yearA = DateUtil.getStringYear(studentClassInfo.getYear());
            if (yearA.equals(DateUtil.getStringYear(new Date()))) {
                modelMap.addAttribute(AkuraConstant.ENABLE_ADD_EDIT_DELETE, AkuraConstant.EMPTY_STRING);
            }
        } else {
            int studentId = 0;
            if (session.getAttribute(STUDENT_ID) != null) {
                studentId = (Integer) session.getAttribute(STUDENT_ID);
            }
            
            Date currentDate = new Date();
            yearA = DateUtil.getStringYear(currentDate);
            
            intYear = Integer.parseInt(yearA);
            List<StudentClassInfo> studentClassInfo = studentService.getStudentClassInfoByStudentId(studentId, intYear);
            
            if (!studentClassInfo.isEmpty()) {
                studentGrade = studentClassInfo.get(0).getClassGrade().getGrade().getDescription();
            }
            selectedStudClassId = studentGrade;
            modelMap.addAttribute(AkuraConstant.ENABLE_ADD_EDIT_DELETE, AkuraConstant.EMPTY_STRING);
        }
        
        int intStudentId = getStudentSessionId(session);
        double avgAcademicLifeRating = 0.0;
        double faithLifeAverage = 0.0;
        double attendanceAverage = 0.0;
        
        Date dateTypeYear;
        
        if (yearA != null) {
            dateTypeYear = DateUtil.getDateTypeYearValue(yearA);
            intYear = Integer.parseInt(yearA);
        } else {
            
            intYear = DateUtil.currentYearOnly();
            yearA = Integer.toString(intYear);
            dateTypeYear = DateUtil.getDateTypeYearValue(yearA);
        }
        
        if (session.getAttribute(STUDENT_GRADE) != null) {
            
            studentGrade = (String) session.getAttribute(STUDENT_GRADE);
        }
        if (!"".equals(studentGrade)) { // when the student is assigned to a
            // class.
            // List<StudentTermMarkDTO> studentTermMarkObjList =
            // studentService.getStudentMarksSubjectTermByStudentIdYear(intStudentId, intYear);
            
            List<StudentSubjectAverageViewDTO> studentTermMarkObjList =
                    studentService.getStudentSubjectAverage(intStudentId, intYear);
            
            List<Map<String, Object>> mylastList = new ArrayList<Map<String, Object>>();
            List<String> tempSubjectList = new ArrayList<String>();
            
            for (StudentSubjectAverageViewDTO dto : studentTermMarkObjList) {
                String subject = dto.getSubject();
                
                boolean foundSubject = false;
                if (tempSubjectList.isEmpty()) {
                    foundSubject = false;
                } else if (tempSubjectList.contains(subject)) {
                    foundSubject = true;
                }
                
                if (!foundSubject) {
                    Map<String, Object> mymap = new TreeMap<String, Object>();
                    
                    for (StudentSubjectAverageViewDTO dtoObj : studentTermMarkObjList) {
                        
                        if (dtoObj.getSubject().equals(subject)) {
                            mymap.put("Subject", dtoObj.getSubject());
                            
                            if (dtoObj.isAbsent()) {
                                mymap.put(dtoObj.getTerm(), AkuraConstant.ABSENT + " (" + dtoObj.getGradeAverage()
                                        + ")" + " (" + dtoObj.getClassAverage() + ")");
                            } else {
                                // totMarks = totMarks + dtoObj.getMarks();
                                mymap.put(dtoObj.getTerm(), dtoObj.getMarks() + " (" + dtoObj.getGradeAverage() + ")"
                                        + " (" + dtoObj.getClassAverage() + ")");
                            }
                            
                            if (dtoObj.getTerm().equals("Term 1")) {
                                mymap.put("flag1", dtoObj.getMarks());
                            }
                            if (dtoObj.getTerm().equals("Term 2")) {
                                mymap.put("flag2", dtoObj.getMarks());
                            }
                            if (dtoObj.getTerm().equals("Term 3")) {
                                mymap.put("flag3", dtoObj.getMarks());
                            }
                        }
                    }
                    mylastList.add(mymap);
                    tempSubjectList.add(subject);
                    
                }
            }
            
            setTotalAndAverageMarks(modelMap, studentTermMarkObjList);
            
            TreeList lastList2 = new TreeList(mylastList);
            modelMap.addAttribute("LastList", lastList2);
        }
        Map<String, List<?>> mapNew = this.loadInformationToPage(intStudentId, intYear);
        if (mapNew != null) {
            
            if (mapNew.containsKey(PREFECTS)) {
                List<?> pList = mapNew.get(PREFECTS);
                if (!pList.isEmpty()) {
                    modelMap.addAttribute(PREFECTS, pList);
                }
            }
            if (mapNew.containsKey(SCHOLARSHIPS)) {
                List<?> sList = mapNew.get(SCHOLARSHIPS);
                if (!sList.isEmpty()) {
                    modelMap.addAttribute(SCHOLARSHIPS, sList);
                }
            }
        }
        populateSeminarsList(modelMap, intStudentId, dateTypeYear);
        
        List<Achievement> achList = this.loadAchievementDetailsForStudent(intStudentId, dateTypeYear);
        if (!achList.isEmpty()) {
            modelMap.addAttribute(MAP_KEY_NAME_ACHIEVEMENT, achList);
        }
        // handling term data
        List<Term> termList = commonService.getTermList();
        modelMap.addAttribute(MODEL_ATT_SELECTED_ID, selectedStudClassId);
        modelMap.addAttribute(ATTR_NAME_YEAR, intYear);
        modelMap.addAttribute(TERM_LIST, termList);
        modelMap.addAttribute(ATTR_NAME_YEAR, yearA);
        
        // if year equal current year
        if (dateTypeYear.equals(DateUtil.getDateTypeYearValue(Integer.toString(DateUtil.currentYearOnly())))) {
            if (session.getAttribute(AVERAGE_FAITH_LIFE_RATING) != null) {
                faithLifeAverage = (Double) session.getAttribute(AVERAGE_FAITH_LIFE_RATING);
                modelMap.addAttribute(MODEL_ATT_STUDENT_FAITH_LIFE, (int) Math.round(faithLifeAverage));
                
            }
            if (session.getAttribute(AVERAGE_ATTENDANCE_RATING) != null) {
                attendanceAverage = (Double) session.getAttribute(AVERAGE_ATTENDANCE_RATING);
                modelMap.addAttribute(MODEL_ATT_ATTENDANCE_RATING, (int) Math.round(attendanceAverage));
            }
            if (session.getAttribute(AVERAGE_ACADEMIC_LIFE_RATING) != null) {
                avgAcademicLifeRating = (Double) session.getAttribute(AVERAGE_ACADEMIC_LIFE_RATING);
                modelMap.addAttribute(MODEL_ATT_STUDENT_ACADEMIC_LIFE, (int) Math.round(avgAcademicLifeRating));
            }
            
        } else {
            
            Map<String, Double> averageMap =
                    studentLoginDelegate.populateStudentProgressBar(intStudentId, dateTypeYear);
            
            faithLifeAverage = averageMap.get(AVERAGE_FAITH_LIFE_RATING);
            modelMap.addAttribute(MODEL_ATT_STUDENT_FAITH_LIFE, (int) Math.round(faithLifeAverage));
            
            avgAcademicLifeRating = averageMap.get(AVERAGE_ACADEMIC_LIFE_RATING);
            modelMap.addAttribute(MODEL_ATT_STUDENT_ACADEMIC_LIFE, (int) Math.round(avgAcademicLifeRating));
            
            attendanceAverage = averageMap.get(AVERAGE_ATTENDANCE_RATING);
            modelMap.addAttribute(MODEL_ATT_ATTENDANCE_RATING, (int) Math.round(attendanceAverage));
        }
        
        // handling marks display color in the table
        double greenLower =
                Double.parseDouble(PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, COLOR_GREEN_LOWER));
        double yellowUpper =
                Double.parseDouble(PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, COLOR_YELLOW_UPPER));
        double yellowLower =
                Double.parseDouble(PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, COLOR_YELLOW_LOWER));
        double redUpper =
                Double.parseDouble(PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, COLOR_RED_UPPER));
        modelMap.addAttribute(GREEN_LOWER, greenLower);
        modelMap.addAttribute(YELLOW_UPPER, yellowUpper);
        modelMap.addAttribute(YELLOW_LOWER, yellowLower);
        modelMap.addAttribute(RED_UPPER, redUpper);
        return new ModelAndView(VIEW_ACADEMIC_LIFE_PAGE, modelMap);
    }
    
    /**
     * calculate and set average and total term marks.
     * 
     * @param modelMap - ModelMap attribute.
     * @param studentTermMarkObjList - List.
     * @throws AkuraAppException throws detailed exception when fails.
     */
    private void setTotalAndAverageMarks(ModelMap modelMap, List<StudentSubjectAverageViewDTO> studentTermMarkObjList)
            throws AkuraAppException {

        List<Map<String, Object>> totalMarksList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> averageMarksList = new ArrayList<Map<String, Object>>();
        Set<String> allTerms = new TreeSet<String>();
        Set<String> markedTerms = new TreeSet<String>();
        Set<String> noMarksTerms = new TreeSet<String>();
        for (Term term : commonService.getTermList()) {
            allTerms.add(term.getDescription());
            noMarksTerms.add(term.getDescription());
            
        }
        for (StudentSubjectAverageViewDTO studentAvgMarks : studentTermMarkObjList) {
            markedTerms.add(studentAvgMarks.getTerm());
        }
        
        // Remove terms with marks from all terms list to get the terms with no marks.
        noMarksTerms.removeAll(markedTerms);
        
        Iterator<String> termObj = allTerms.iterator();
        while (termObj.hasNext()) {
            int totalSubjectOfTerm = 0;
            double totalMarksOfTerm = 0.0;
            double avarageMarksOfTerm = 0.0;
            String term = termObj.next();
            
            for (StudentSubjectAverageViewDTO dto : studentTermMarkObjList) {
                
                if (term.equals(dto.getTerm())) {
                    totalMarksOfTerm = totalMarksOfTerm + dto.getMarks();
                    totalSubjectOfTerm++;
                }
            }
            
            if (totalMarksOfTerm != 0.0) {
                avarageMarksOfTerm = totalMarksOfTerm / totalSubjectOfTerm;
            }
            
            Map<String, Object> totalMarkMap = new TreeMap<String, Object>();
            Map<String, Object> averageMarkMap = new TreeMap<String, Object>();
            
            // set total marks and average marks as minus one, if this student doesn't have marks for this
            // term.
            if (totalMarksOfTerm == 0.0 && noMarksTerms.contains(term)) {
                
                totalMarkMap.put(term, AkuraConstant.MINUS_ONE);
                averageMarkMap.put(term, AkuraConstant.MINUS_ONE);
                
            } else {
                
                totalMarkMap.put(term, totalMarksOfTerm);
                averageMarkMap.put(term, avarageMarksOfTerm);
            }
            totalMarksList.add(totalMarkMap);
            averageMarksList.add(averageMarkMap);
            
        }
        
        modelMap.addAttribute(TOTAL_TERM_MARK_LIST, totalMarksList);
        modelMap.addAttribute(AVERAGE_TERM_MARK_LIST, averageMarksList);
    }
    
    // ----- End of student term mark view screen related methods
    // ----------------//
    // ----- Student prefect details related methods -------//
    /**
     * handle POST requests for Academic-details - student prefect details save action.
     * 
     * @param request - HttpServletRequest.
     * @param session - HttpSession object
     * @param modelMap - {@link ModelMap}
     * @return the name of the view.
     * @throws AkuraAppException throws when exception occurs.
     */
    @RequestMapping(value = ACTION_FOR_SAVE_PREFECT, method = RequestMethod.POST)
    public ModelAndView savePrefectDetails(HttpServletRequest request, HttpSession session, ModelMap modelMap)
            throws AkuraAppException {

        String prefectTypeId = request.getParameter(SELECTED_PREFECT_TYPE);
        int intStudentId = getStudentSessionId(session);
        
        // checking weather the retrieved parameters are null
        if ((!"".equals(prefectTypeId)) && (intStudentId != 0)) {
            
            int studentClassInfoId = (Integer.parseInt(request.getParameter(SELECTED_GRADE)));
            StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
            String yearB = DateUtil.getStringYear(studentClassInfo.getYear());
            int intpTypeId = Integer.parseInt(prefectTypeId);
            Date date = DateUtil.getDateTypeYearValue(yearB);
            boolean vFlag = false;
            
            if (prefectTypeId.equals("0")) {
                String messageKey = REF_UI_STUDENT_EPREFECT_TYPE_REQUIRED;
                request.setAttribute(SELECTED_PREFECT_TYPE, prefectTypeId);
                populateErrorMessages(modelMap, messageKey, PREFECT);
            } else {
                
                // validate the prefect type is already exists
                vFlag = this.valiatePrefect(intStudentId, intpTypeId, date);
                
                if (vFlag) { // error message
                
                    String messageKey = ERROR_MSG_KEY_DUPLICATE_PREFECT_TYPE;
                    request.setAttribute(SELECTED_PREFECT_TYPE, prefectTypeId);
                    populateErrorMessages(modelMap, messageKey, PREFECT);
                } else {
                    
                    addStudentPrefectDetails(intStudentId, intpTypeId, date);
                }
                modelMap.addAttribute(ATTR_NAME_YEAR, yearB);
            }
        }
        return showMarks(request, modelMap, session); // Returning to previous
        // action method
    }
    
    /**
     * Handles add student prefect details.
     * 
     * @param intStudentId - holds integer type
     * @param intpTypeId - holds integer type
     * @param date - holds date type
     * @throws AkuraAppException - throw detailed exception
     */
    private void addStudentPrefectDetails(int intStudentId, int intpTypeId, Date date) throws AkuraAppException {

        PrefectType prefectType = commonService.findPrefectTypeById(intpTypeId);
        Student student = studentService.findStudent(intStudentId);
        
        StudentPrefect studentPrefect = new StudentPrefect();
        studentPrefect.setPrefectType(prefectType);
        studentPrefect.setStudent(student);
        studentPrefect.setYear(date);
        
        studentService.saveStudentPrefectDetails(studentPrefect);
    }
    
    /**
     * Read student id from session.
     * 
     * @param session {@link HttpSession}
     * @return student id with integer type
     */
    private int getStudentSessionId(HttpSession session) {

        return (session.getAttribute(AkuraConstant.STUDENT_ID) != null) ? (Integer) session
                .getAttribute(AkuraConstant.STUDENT_ID) : 0;
    }
    
    /**
     * handle POST requests for Academic-details - student prefect details save action.
     * 
     * @param request - HttpServletRequest.
     * @param session - HttpSession object
     * @param modelMap - {@link ModelMap}
     * @return the name of the view.
     * @throws AkuraAppException throws when exception occurs.
     */
    @RequestMapping(value = ACTION_FOR_EDIT_PREFECT, method = RequestMethod.POST)
    public ModelAndView editPrefectDetails(HttpServletRequest request, HttpSession session, ModelMap modelMap)
            throws AkuraAppException {

        String prefectTypeId = request.getParameter(SELECTED_PREFECT_TYPE);
        String selectedStudPrefectId = request.getParameter("selectedStudentPrefectId");
        
        String yearC = null;
        int studentClassInfoId = 0;
        
        studentClassInfoId = (Integer.parseInt(request.getParameter(SELECTED_GRADE)));
        
        StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
        
        yearC = DateUtil.getStringYear(studentClassInfo.getYear());
        
        int intStudentId = getStudentSessionId(session);
        
        int intpTypeId = Integer.parseInt(prefectTypeId);
        boolean vFlag;
        Date date = DateUtil.getDateTypeYearValue(yearC);
        
        if (prefectTypeId.equals("0")) {
            String messageKey = REF_UI_STUDENT_EPREFECT_TYPE_REQUIRED;
            request.setAttribute(SELECTED_PREFECT_TYPE, prefectTypeId);
            populateErrorMessages(modelMap, messageKey, PREFECT);
        } else {
            
            // validate the prefect type is already
            vFlag = this.valiatePrefect(intStudentId, intpTypeId, date);
            
            if (vFlag) { // error message
                String messageKey = ERROR_MSG_KEY_DUPLICATE_PREFECT_TYPE;
                request.setAttribute(SELECTED_PREFECT_TYPE, prefectTypeId);
                populateErrorMessages(modelMap, messageKey, PREFECT);
            } else {
                int intselectedStudPrefectId = Integer.parseInt(selectedStudPrefectId);
                updateStudentPrefectDetails(intpTypeId, intselectedStudPrefectId);
            }
        }
        modelMap.addAttribute(ATTR_NAME_YEAR, yearC);
        return showMarks(request, modelMap, session); // Returning to previous
        // action method
    }
    
    /**
     * Handles edit student prefect details.
     * 
     * @param intpTypeId - holds integer type
     * @param intselectedStudPrefectId - holds integer type
     * @throws AkuraAppException - throw detailed exception
     */
    private void updateStudentPrefectDetails(int intpTypeId, int intselectedStudPrefectId) throws AkuraAppException {

        StudentPrefect studentPrefectObj = studentService.findStudentPrefectDetailsById(intselectedStudPrefectId);
        PrefectType prefectType = commonService.findPrefectTypeById(intpTypeId);
        studentPrefectObj.setPrefectType(prefectType);
        studentService.updateStudentPrefectDetails(studentPrefectObj);
    }
    
    /**
     * handle POST requests for Academic-details - student prefect details save action.
     * 
     * @param request - HttpServletRequest.
     * @param session - HttpSession object
     * @return the name of the view.
     * @throws AkuraAppException throws when exception occurs.
     */
    @RequestMapping(value = ACTION_FOR_DELETE_PREFECT, method = RequestMethod.POST)
    public ModelAndView deletePrefectDetails(HttpServletRequest request, HttpSession session) throws AkuraAppException {

        String selectedStudPrefectId = request.getParameter("selectedStudentPrefectId");
        
        String yearD = null;
        int studentClassInfoId = 0;
        
        studentClassInfoId = (Integer.parseInt(request.getParameter(SELECTED_GRADE)));
        
        StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
        
        yearD = DateUtil.getStringYear(studentClassInfo.getYear());
        
        int intselectedStudPrefectId = Integer.parseInt(selectedStudPrefectId);
        
        // Retrieving the StudentPrefect object given by student prefect id
        StudentPrefect studentPrefectObj = studentService.findStudentPrefectDetailsById(intselectedStudPrefectId);
        
        if (studentPrefectObj != null) {
            
            studentService.deleteStudentPrefectDetails(studentPrefectObj);
        }
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(ATTR_NAME_YEAR, yearD);
        return showMarks(request, modelMap, session); // Returning to previous
        // action method
    }
    
    /**
     * Method to validate studentPrefect types.
     * 
     * @param studentId - student id defined by integer type variable.
     * @param intpTypeId prefect type id defined by an integer.
     * @param year - year which holding the prefect details defined by Date type variable.
     * @return boolean value indicating the validation status
     * @throws AkuraAppException throws when error occurs
     */
    private boolean valiatePrefect(int studentId, int intpTypeId, Date year) throws AkuraAppException {

        boolean flag = false;
        List<StudentPrefect> stPreList = studentService.getAllStudentPrefectDetails();
        Iterator<StudentPrefect> itr = stPreList.iterator();
        while (itr.hasNext()) {
            StudentPrefect stPre = (StudentPrefect) itr.next();
            int preTypeId = stPre.getPrefectType().getPrefectTypeId();
            Date yearNew = stPre.getYear();
            int studentNewId = stPre.getStudent().getStudentId();
            if (preTypeId == intpTypeId && year.equals(yearNew) && studentId == studentNewId) {
                
                flag = true;
            }
        }
        return flag;
    }
    
    // ----- End of student prefect details related methods ------//
    // ----- Student scholarship details related methods -------//
    /**
     * handle POST requests for Academic-details - student scholarship details save action.
     * 
     * @param request - HttpServletRequest.
     * @param session - HttpSession object
     * @return the name of the view.
     * @throws AkuraAppException throws when exception occurs.
     */
    @RequestMapping(value = ACTION_FOR_SAVE_SCHOLARSHIP, method = RequestMethod.POST)
    public ModelAndView saveScholarshipDetails(HttpServletRequest request, HttpSession session)
            throws AkuraAppException {

        String messageKey;
        String scholarshipTypeId = request.getParameter(SELECTED_SCHOLARSHIP_TYPE);
        
        String yearE = null;
        int studentClassInfoId = 0;
        
        studentClassInfoId = (Integer.parseInt(request.getParameter(SELECTED_GRADE)));
        
        StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
        
        yearE = DateUtil.getStringYear(studentClassInfo.getYear());
        
        int intsTypeId = 0;
        if (!scholarshipTypeId.equals("")) {
            intsTypeId = Integer.parseInt(scholarshipTypeId);
        }
        
        int intStudentId = getStudentSessionId(session);
        
        Date date = DateUtil.getDateTypeYearValue(yearE);
        boolean vFlag = false;
        
        // validating the scholarship type is already exists
        vFlag = this.valiateScholarshipForStudent(intStudentId, intsTypeId, date);
        ModelMap modelMap = new ModelMap();
        
        if (vFlag) { // error message
            if (intsTypeId == 0) {
                messageKey = ERROR_MSG_KEY_SCHOLARSHIP_REQUIRED;
            } else {
                messageKey = ERROR_MSG_KEY_DUPLICATE_SCHOLARSHIP_TYPE;
            }
            request.setAttribute(SELECTED_SCHOLARSHIP_TYPE, scholarshipTypeId);
            populateErrorMessages(modelMap, messageKey, SCHOLAR);
            
        } else {
            addStudentScholarshipDetails(intsTypeId, intStudentId, date);
        }
        modelMap.addAttribute(ATTR_NAME_YEAR, yearE);
        return showMarks(request, modelMap, session); // Returning to previous
        // action method
    }
    
    /**
     * Handle add Student Scholarship Details.
     * 
     * @param intsTypeId - holds integer type
     * @param intStudentId - holds integer type
     * @param date - holds date type
     * @throws AkuraAppException - throw detailed exception
     */
    private void addStudentScholarshipDetails(int intsTypeId, int intStudentId, Date date) throws AkuraAppException {

        Scholarship scholarship = commonService.findScholarshipById(intsTypeId);
        Student student = studentService.findStudent(intStudentId);
        
        StudentScholarship studentScholarship = new StudentScholarship();
        studentScholarship.setScholarship(scholarship);
        studentScholarship.setStudent(student);
        studentScholarship.setYear(date);
        
        studentService.saveStudentScholarshipDetails(studentScholarship);
    }
    
    /**
     * handle POST requests for Academic-details - student prefect details save action.
     * 
     * @param request - HttpServletRequest.
     * @param session - HttpSession object
     * @return the name of the view.
     * @throws AkuraAppException throws when exception occurs.
     */
    @RequestMapping(value = ACTION_FOR_EDIT_SCHOLARSHIP, method = RequestMethod.POST)
    public ModelAndView editScholarshipDetails(HttpServletRequest request, HttpSession session)
            throws AkuraAppException {

        String scholarshipTypeId = request.getParameter(SELECTED_SCHOLARSHIP_TYPE);
        String selectedStudScholId = request.getParameter("selectedStudentScholarshipId");
        
        String yearF = null;
        int studentClassInfoId = 0;
        
        studentClassInfoId = (Integer.parseInt(request.getParameter(SELECTED_GRADE)));
        
        StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
        
        yearF = DateUtil.getStringYear(studentClassInfo.getYear());
        
        int intStudentId = getStudentSessionId(session);
        
        int intsTypeId = Integer.parseInt(scholarshipTypeId);
        boolean vFlag;
        Date date = DateUtil.getDateTypeYearValue(yearF);
        
        // validating the scholarship type is already exists
        vFlag = this.valiateScholarshipForStudent(intStudentId, intsTypeId, date);
        ModelMap modelMap = new ModelMap();
        
        if (vFlag) { // error message
            request.setAttribute(SELECTED_SCHOLARSHIP_TYPE, scholarshipTypeId);
            populateErrorMessages(modelMap, ERROR_MSG_KEY_DUPLICATE_PREFECT_TYPE, SCHOLAR);
        } else {
            int intselectedStudScholId = Integer.parseInt(selectedStudScholId);
            updateStudentScholarshipDetails(intsTypeId, intselectedStudScholId);
        }
        modelMap.addAttribute(ATTR_NAME_YEAR, yearF);
        return showMarks(request, modelMap, session);// Returning to previous
        // action method
    }
    
    /**
     * Handle update Student Scholarship Details.
     * 
     * @param intsTypeId - holds integer type
     * @param intselectedStudScholId - holds integer type
     * @throws AkuraAppException - throw detail exception
     */
    private void updateStudentScholarshipDetails(int intsTypeId, int intselectedStudScholId) throws AkuraAppException {

        StudentScholarship studentScholarshipObj =
                studentService.findStudentScholarshipDetailsById(intselectedStudScholId);
        
        Scholarship scholarship = commonService.findScholarshipById(intsTypeId);
        studentScholarshipObj.setScholarship(scholarship);
        
        studentService.updateStudentScholarshipDetails(studentScholarshipObj);
    }
    
    /**
     * handle POST requests for Academic-details - student achievement details delete action.
     * 
     * @param request - HttpServletRequest.
     * @param session - HttpSession.
     * @return the name of the view.
     * @throws AkuraAppException throws when exception occurs.
     */
    @RequestMapping(value = ACTION_FOR_DELETE_SCHOLARSHIP, method = RequestMethod.POST)
    public ModelAndView deleteScholarshipDetails(HttpServletRequest request, HttpSession session)
            throws AkuraAppException {

        String selectedStudPrefectId = request.getParameter("selectedStudentScholarshipId");
        
        String yearG = null;
        int studentClassInfoId = 0;
        
        studentClassInfoId = (Integer.parseInt(request.getParameter(SELECTED_GRADE)));
        
        StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
        
        yearG = DateUtil.getStringYear(studentClassInfo.getYear());
        
        int intselectedStudScholarshipId = Integer.parseInt(selectedStudPrefectId);
        StudentScholarship studentScholarship =
                studentService.findStudentScholarshipDetailsById(intselectedStudScholarshipId);
        if (studentScholarship != null) {
            
            studentService.deleteStudentScholarshipDetails(studentScholarship);
        }
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(ATTR_NAME_YEAR, yearG);
        return showMarks(request, modelMap, session);
    }
    
    /**
     * Method to validate studentScholarship types.
     * 
     * @param studentId - student id defined by integer.
     * @param intsTypeId scholarship type id defined by an integer.
     * @param year - year which holding the scholarship details defined by Date type variable.
     * @return boolean value indicating the validation status
     * @throws AkuraAppException throws when error occurs
     */
    public boolean valiateScholarshipForStudent(int studentId, int intsTypeId, Date year) throws AkuraAppException {

        boolean flag = false;
        List<StudentScholarship> stScholList = studentService.getAllStudentScholarshipDetails();
        Iterator<StudentScholarship> itr = stScholList.iterator();
        
        // check Scholarship type id
        if (intsTypeId == 0) {
            flag = true;
        } else {
            while (itr.hasNext()) {
                StudentScholarship studSchol = (StudentScholarship) itr.next();
                int scholTypeId = studSchol.getScholarship().getScholarshipId();
                Date yearNew = studSchol.getYear();
                int studentNewId = studSchol.getStudent().getStudentId();
                if (scholTypeId == intsTypeId && year.equals(yearNew) && studentId == studentNewId) {
                    
                    flag = true;
                }
            }
        }
        return flag;
    }
    
    // ----- End of student scholarship details related methods ------//
    // ----- Student achievement details related methods ------//
    /**
     * handle POST requests for Academic-details - student scholarship details save action.
     * 
     * @param request - HttpServletRequest.
     * @param session - HttpSession object.
     * @return the name of the view.
     * @throws AkuraAppException throws when exception occurs.
     */
    @RequestMapping(value = ACTION_FOR_SAVE_ACHIEVEMENT, method = RequestMethod.POST)
    public ModelAndView saveAchievementDetails(HttpServletRequest request, HttpSession session)
            throws AkuraAppException {

        String description = request.getParameter(ACHIEVEMENT_TEXT_AREA).trim();
        String yearH = null;
        int studentClassInfoId = 0;
        
        studentClassInfoId = (Integer.parseInt(request.getParameter(SELECTED_GRADE)));
        
        StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
        
        yearH = DateUtil.getStringYear(studentClassInfo.getYear());
        
        ModelMap modelMap = new ModelMap();
        if (!"".equals(description) && !"".equals(yearH)) {
            
            String studentAchievementDescription = description.replaceAll("( )+", " ");
            Date date = DateUtil.getDateTypeYearValue(yearH);
            int intStudentId = getStudentSessionId(session);
            if (!isExistsAchievement(intStudentId, studentAchievementDescription, date)) {
                
                addStudentAchievementDetails(intStudentId, studentAchievementDescription, date);
            } else {
                request.setAttribute(ACHIEVEMENT_TEXT_AREA, description);
                populateErrorMessages(modelMap, ERROR_MSG_KEY_DUPLICATE_ACHIEVEMENT_TYPE, ACHIEVEMENT);
            }
        } else {
            request.setAttribute(ACHIEVEMENT_TEXT_AREA, description);
            populateErrorMessages(modelMap, ERROR_MSG_KEY_MANDATORY_FIELDS, ACHIEVEMENT);
        }
        modelMap.addAttribute(ATTR_NAME_YEAR, yearH);
        return showMarks(request, modelMap, session);
    }
    
    /**
     * Handles add Student Achievement Details.
     * 
     * @param intStudentId - holds integer type
     * @param studentAchievementDescription - holds string type
     * @param date - holds date type
     * @throws AkuraAppException - throw detail exception
     */
    private void addStudentAchievementDetails(int intStudentId, String studentAchievementDescription, Date date)
            throws AkuraAppException {

        Student student = studentService.findStudent(intStudentId);
        Achievement achievement = new Achievement();
        achievement.setStudent(student);
        achievement.setDescription(studentAchievementDescription);
        achievement.setYear(date);
        achievement.setAcademic(true);
        
        studentService.addAchievement(achievement);
    }
    
    /**
     * handle POST requests for Academic-details - student achievement details edit action.
     * 
     * @param request - HttpServletRequest.
     * @param session - HttpSession.
     * @return the name of the view.
     * @throws AkuraAppException throws when exception occurs.
     */
    @RequestMapping(value = ACTION_FOR_EDIT_ACHIEVEMENT, method = RequestMethod.POST)
    public ModelAndView editAchievementDetails(HttpServletRequest request, HttpSession session)
            throws AkuraAppException {

        ModelMap modelMap = new ModelMap();
        String selectedStuAchievementId = request.getParameter(SELECTED_STUDENT_ACHIEVEMENT_ID);
        
        int studentClassInfoId =
                (request.getParameter(SELECTED_GRADE) != null) ? (Integer
                        .parseInt(request.getParameter(SELECTED_GRADE))) : 0;
        StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
        String yearI = DateUtil.getStringYear(studentClassInfo.getYear());
        
        if (!"".equals(selectedStuAchievementId) && !"".equals(yearI)) {
            
            String description = request.getParameter(ACHIEVEMENT_TEXT_AREA).trim();
            if (!"".equals(description)) {
                int intselectedStudAchievementId = Integer.parseInt(selectedStuAchievementId);
                Achievement studentAchievementObj = studentService.findAchievementById(intselectedStudAchievementId);
                if (studentAchievementObj != null) {
                    
                    String studentAchievementDescription = description.replaceAll("( )+", " ");
                    int studentId = studentAchievementObj.getStudent().getStudentId();
                    Date yearNew = studentAchievementObj.getYear();
                    
                    if (!isExistsAchievement(studentId, studentAchievementDescription, yearNew)) {
                        
                        studentAchievementObj.setDescription(studentAchievementDescription);
                        studentService.editAchievement(studentAchievementObj);
                    } else {
                        request.setAttribute(ACHIEVEMENT_TEXT_AREA, description);
                        populateErrorMessages(modelMap, ERROR_MSG_KEY_DUPLICATE_ACHIEVEMENT_TYPE, ACHIEVEMENT);
                    }
                }
            } else {
                request.setAttribute(ACHIEVEMENT_TEXT_AREA, description);
                populateErrorMessages(modelMap, ERROR_MSG_KEY_MANDATORY_FIELDS, ACHIEVEMENT);
            }
            modelMap.addAttribute(ATTR_NAME_YEAR, yearI);
        }
        return showMarks(request, modelMap, session);
    }
    
    /**
     * handle POST requests for Academic-details - student achievement details delete action.
     * 
     * @param request - HttpServletRequest.
     * @param session - HttpSession.
     * @return the name of the view.
     * @throws AkuraAppException throws when exception occurs.
     */
    @RequestMapping(value = ACTION_FOR_DELETE_ACHIEVEMENT, method = RequestMethod.POST)
    public ModelAndView deleteAchievementDetails(HttpServletRequest request, HttpSession session)
            throws AkuraAppException {

        String selectedStuAchievementId = request.getParameter(SELECTED_STUDENT_ACHIEVEMENT_ID);
        
        StudentClassInfo studentClassInfo =
                studentService.findStudentClassInfoById(Integer.parseInt(request.getParameter(SELECTED_GRADE)));
        
        int intselectedStudAchievementId = Integer.parseInt(selectedStuAchievementId);
        studentService.deleteAchievement(intselectedStudAchievementId);
        
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(ATTR_NAME_YEAR, DateUtil.getStringYear(studentClassInfo.getYear()));
        return showMarks(request, modelMap, session);
    }
    
    /**
     * Check whether Achievement is already added.
     * 
     * @param studentId - student Id
     * @param strDescription - achievement
     * @param year - Date
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsAchievement(int studentId, String strDescription, Date year) throws AkuraAppException {

        boolean isExists = false;
        Iterator<Achievement> stAchievementItr =
                studentService.getAchievementsListForStudent(studentId, year).iterator();
        
        while (stAchievementItr.hasNext()) {
            Achievement stAchievement = (Achievement) stAchievementItr.next();
            if (strDescription.equals(stAchievement.getDescription()) && (stAchievement.isAcademic())) {
                
                isExists = true;
            }
        }
        return isExists;
    }
    
    // ----- End of student achievement details related methods ------//
    
    // ----- Student Seminars details related methods ------//
    
    /**
     * handle POST requests for Academic-details - student Seminars details Add new record or Edit.
     * 
     * @param request - HttpServletRequest.
     * @param modelMap ModelMap attribute.
     * @param session - HttpSession object.
     * @return the name of the view.
     * @throws AkuraAppException throws when exception occurs.
     */
    @RequestMapping(value = SAVE_EDIT_SEMINARS_DETAILS, method = RequestMethod.POST)
    public ModelAndView saveEditSeminarsDetails(HttpServletRequest request, ModelMap modelMap, HttpSession session)
            throws AkuraAppException {

        String stuSemID = request.getParameter(STUDENT_SEM_ID);
        String ssDescription = request.getParameter(SEMINAR_DETAIL_AREA);
        String seminarID = request.getParameter(SELECT_SEM_ID);
        int studentId = getStudentSessionId(session);
        
        if (seminarID == null || seminarID.equals("0") || stuSemID == null) {
            // Validate the mandatory fields
            request.setAttribute(SELECT_SEM_ID, seminarID);
            request.setAttribute(SEMINAR_DETAIL_AREA, ssDescription);
            populateErrorMessages(modelMap, ERROR_MSG_KEY_MANDATORY_FIELDS, SEMINAR);
            return showMarks(request, modelMap, session);
        }
        
        String yearA;
        Date dateTypeYear;
        int studentClassInfoId = 0;
        
        // get the year from grade
        if (request.getParameter(SELECTED_GRADE) != null) {
            
            studentClassInfoId = (Integer.parseInt(request.getParameter(SELECTED_GRADE)));
            StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
            yearA = DateUtil.getStringYear(studentClassInfo.getYear());
        } else {
            
            Date currentDate = new Date();
            yearA = DateUtil.getStringYear(currentDate);
        }
        dateTypeYear = DateUtil.getDateTypeYearValue(yearA);
        
        // Convert parameters values to object attribute
        int studentSemId = Integer.parseInt(stuSemID);
        int seminarId = Integer.parseInt(seminarID);
        StudentSeminar studentSemObj = new StudentSeminar();
        Seminar seminar = new Seminar();
        seminar.setSeminarId(seminarId);
        studentSemObj.setStudentId(studentId);
        studentSemObj.setYear(dateTypeYear);
        
        studentSemObj.setStudentSeminarId(studentSemId);
        studentSemObj.setDescription(ssDescription);
        studentSemObj.setSeminar(seminar);
        
        if (studentSemId == 0) {
            // adding new record
            studentService.saveStudetnSeminar(studentSemObj);
        } else if (studentSemId > 0) {
            // editing
            studentService.updateStudentSeminar(studentSemObj);
        }
        
        return showMarks(request, modelMap, session);
    }
    
    /**
     * handle POST requests for Academic-details - student Seminars details delete the given record.
     * 
     * @param request - HttpServletRequest.
     * @param modelMap ModelMap attribute.
     * @param session - HttpSession object.
     * @return the name of the view.
     * @throws AkuraAppException throws when exception occurs.
     */
    @RequestMapping(value = DELETE_SEMINARS_DETAILS, method = RequestMethod.POST)
    public ModelAndView deleteSeminarsDetails(HttpServletRequest request, ModelMap modelMap, HttpSession session)
            throws AkuraAppException {

        String stuSemID = request.getParameter(STUDENT_SEM_ID);
        String ssDescription = request.getParameter(SEMINAR_DETAIL_AREA);
        if (stuSemID == null) {
            // Validate the mandatory fields
            request.setAttribute(SELECT_SEM_ID, stuSemID);
            request.setAttribute(SEMINAR_DETAIL_AREA, ssDescription);
            populateErrorMessages(modelMap, ERROR_MSG_KEY_MANDATORY_FIELDS, SEMINAR);
            return showMarks(request, modelMap, session);
        }
        
        studentService.deleteStudentSeminar(Integer.parseInt(stuSemID));
        
        return showMarks(request, modelMap, session);
    }
    
    /**
     * to populate Seminars list and studentSeminar List.
     * 
     * @param modelMap -ModelMap attribute
     * @param intStudentId - studentId for student
     * @param dateTypeYear - year of the seminar registered
     * @throws AkuraAppException - throws when exception occurs
     */
    private void populateSeminarsList(ModelMap modelMap, int intStudentId, Date dateTypeYear) throws AkuraAppException {

        List<Seminar> seminarList = studentService.getAllSeminars();
        if (seminarList != null) {
            modelMap.addAttribute(SEMINAR_LIST, seminarList);
        }
        List<StudentSeminar> stuSemList = studentService.getAllStudentSeminars(intStudentId, dateTypeYear);
        if (stuSemList != null) {
            modelMap.addAttribute(STU_SEM_LIST, stuSemList);
        }
    }
    
    // ----- End of student Seminars details related methods ------//
    
    /**
     * Method to retrieve all student related prefect scholarship and achievement details given by student id
     * and year.
     * 
     * @param studentId - specifies by an integer
     * @param year - specifies by an Date type object
     * @return Map consisting string keys and List of values
     * @throws AkuraAppException throws when exception occurs
     */
    public Map<String, List<?>> loadInformationToPage(int studentId, int year) throws AkuraAppException {

        Map<String, List<?>> map = new TreeMap<String, List<?>>();
        List<StudentPrefect> prefectList = studentService.getStudentPrefectDetailsByStudentId(studentId, year);
        if (!prefectList.isEmpty()) {
            
            map.put(PREFECTS, new TreeList(SortUtil.sortStudentPrefectListByName((prefectList))));
        }
        List<StudentScholarship> scholList = studentService.getStudentScholarshipDetailsByStudentId(studentId, year);
        if (!scholList.isEmpty()) {
            
            map.put(SCHOLARSHIPS, new TreeList(scholList));
        }
        return map;
    }
    
    /**
     * Method to retrieve all the prefect data.
     * 
     * @param model ModelMap attribute.
     * @return List of PrefectType objects
     * @throws AkuraAppException throws when exception occurs
     */
    @ModelAttribute(PREFECT_TYPE_LIST)
    public List<PrefectType> populatePrefectTypeData(ModelMap model) throws AkuraAppException {

        List<PrefectType> prefectList = commonService.getPrefectTypeList();
        model.addAttribute(PREFECT_TYPE_LIST_SIZE, prefectList.size());
        return SortUtil.sortPrefectTypeList(prefectList);
    }
    
    /**
     * Method to retrieve all the scholarship data.
     * 
     * @param model - ModelMap attribute.
     * @return List of Scholarship objects
     * @throws AkuraAppException throws when error occurs
     */
    @ModelAttribute(SCHOLARSHIP_LIST)
    public List<Scholarship> populateScholarshipData(ModelMap model) throws AkuraAppException {

        List<Scholarship> scholList = commonService.getScholarshipList();
        model.addAttribute(SCHOLARSHIP_LIST_SIZE, scholList.size());
        return SortUtil.sortScholarshipList(scholList);
    }
    
    /**
     * Method to retrieve Achievement details for a student.
     * 
     * @param studentId - student id defined by integer type variable.
     * @param year - year holding the achievement details for a particular student defined by Date type
     *        object.
     * @return List of Achievement objects
     * @throws AkuraAppException throws when exception occurs
     */
    public List<Achievement> loadAchievementDetailsForStudent(int studentId, Date year) throws AkuraAppException {

        List<Achievement> achievementListNew = new ArrayList<Achievement>();
        List<Achievement> achivementList = studentService.getAchievementsListForStudent(studentId, year);
        for (Achievement achievementObj : achivementList) {
            if (achievementObj.isAcademic()) {
                achievementListNew.add(achievementObj);
            }
        }
        return achievementListNew;
    }
    
    /**
     * Method to retrieve difference of the student first year and current year.
     * 
     * @param firstYear - year defined by integer type variable.
     * @return integer type object which holds a year.
     */
    public int getYearCount(int firstYear) {

        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int yearCount = 0;
        
        if (currentYear < firstYear) {
            
            yearCount = firstYear - currentYear; // Year difference
        } else {
            yearCount = currentYear - firstYear; // Year difference
        }
        return yearCount;
    }
    
    /**
     * Generates error message according to the message key.
     * 
     * @param map - holds {@link ModelMap}
     * @param messageKey - holds String type
     * @param errorMsgSection - holds error message section
     */
    private void populateErrorMessages(ModelMap map, String messageKey, String errorMsgSection) {

        String message = new ErrorMsgLoader().getErrorMessage(messageKey);
        map.addAttribute(AkuraConstant.MESSAGE, message);
        map.addAttribute(ERROR_MSG_SECTION, errorMsgSection);
    }
    
    /**
     * Displays the exam marks results for a particular student.
     * 
     * @param model to hold model object for JSP.
     * @param request - an instance of request scope object
     * @param session - an instance of session scope object
     * @return - the view name of the page
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = SHOW_EXAM_MARKS_RESULT, method = RequestMethod.GET)
    public String showExamMarksMarks(ModelMap model, HttpServletRequest request, HttpSession session)
            throws AkuraAppException {

        Map<Integer, String> examList = new LinkedHashMap<Integer, String>();
        // retrieves the year.
        String selectedYear = request.getParameter(AkuraWebConstant.YEAR);
        int studentId = (Integer) session.getAttribute(STUDENT_ID);
        
        // converts into date.
        int year = Integer.parseInt(selectedYear);
        
        List<ExamResults> studentExamMarks = studentService.getExamResults(studentId, DateUtil.getDate(year));
        for (ExamResults result : studentExamMarks) {
            examList.put(result.getExamId(), result.getExamDescription());
        }
        model.addAttribute(AkuraWebConstant.YEAR_STRING, year);
        model.addAttribute(EXAM_MARKS_LIST, studentExamMarks);
        model.addAttribute(EXAM_LIST, examList);
        return EXAM_MARKS_URL;
    }
}
