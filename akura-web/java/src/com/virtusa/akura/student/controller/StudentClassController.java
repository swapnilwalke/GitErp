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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.list.TreeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentSubTermMark;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.dto.SubTerm;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.SortUtil;

/**
 * This is a controller where controls manage student class details.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class StudentClassController {
    
    /** SchoolService attribute for holding studentService. */
    @Autowired
    private CommonService commonService;
    
    /** SchoolService attribute for holding studentService. */
    @Autowired
    private StudentService studentService;
    
    /** constant fot holding `Empty`. */
    private static final String EMPTY = "Empty";
    
    /** model attribute of ClassGradeList object. */
    private static final String MODEL_CLASS_GRADE_LIST = "ClassGradeList";
    
    /** model attribute of GradeList object. */
    private static final String MODEL_GRADE_LIST = "GradeList";
    
    /** model attribute of GradeId object. */
    private static final String MODEL_GRADE_ID = "GradeId";
    
    /** model attribute of Grade object. */
    private static final String MODEL_GRADE = "Grade";
    
    /** model attribute of LastList object. */
    private static final String MODEL_LAST_LIST = "LastList";
    
    /** model attribute of message_success object. */
    private static final String MODEL_MESSAGE_SUCCESS = "message_success";
    
    /** attribute for holding error key REF.UI.STUDENT.SAMECLASS.ASSIGNED. */
    private static final String REF_UI_STUDENT_SAMECLASS_ASSIGNED = "REF.UI.STUDENT.SAMECLASS.ASSIGNED";
    
    /** constant for holding `NewStudents`. */
    private static final String NEW_STUDENTS = "NewStudents";
    
    /** constant for holding `_01`. */
    private static final String UNDER_SCOPE = "-01";
    
    /** Represents the model attribute key for the no students error message. */
    private static final String ERROR_MESSAGE = "errorMessage";
    
    /** Represents the key for the no new students error message. */
    private static final String REF_UI_STUDENT_CLASS_STUDENT_NODATA = "REF.UI.STUDENT.CLASS.STUDENT.NODATA";
    
    /** Represents the key for the no students error message. */
    private static final String REF_UI_STUDENT_CLASS_NEW_STUDENT_NODATA = "REF.UI.STUDENT.CLASS.NEW.STUDENT.NODATA";
    
    /** Represents the array that selected to the 'ToList'. */
    private static final String SELECTED_ARRAY = "selectedArray";
    
    /** Represents the array that selected to the 'FromList'. */
    private static final String REMOVED_FROM_ARRAY = "removedFromArray";
    
    /** Represents the array that selected toList. */
    private static final String SELECTED_TO_LIST = "selectedToList";
    
    /** Represents the attribute for new selected grade. */
    private static final String NEW_SELECTED_GRADE = "NewSelectedGrade";
    
    /** Represents the attribute for new selected year. */
    private static final String SELECTED_NEW_YEAR = "selectedNewYear";
    
    /** request attribute of `NewSelectedGrade` value. */
    private static final String REQUEST_NEW_SELECTED_GRADE = NEW_SELECTED_GRADE;
    
    /** request attribute of `NewSelectedYear` value. */
    private static final String REQUEST_NEW_SELECTED_YEAR = "NewSelectedYear";
    
    /** request attribute of `ToList` value. */
    private static final String REQUEST_TO_LIST = "ToList";
    
    /** Represents the error message for assigning students into future year. */
    private static final String STUDENT_FUTURE_ASSIGNMENT_ERROR = "STUDENT.FUTURE.ASSIGNMENT.ERROR";
    
    /**
     * The mandatory field error code is to display when mandatory fields not filled, interpretable as message
     * key.
     */
    public static final String MANDATORY_FIELD_ERROR_CODE = "REF.UI.MANDATORY.FIELD.REQUIRED";
    
    /** The message code is to display when student(s) assigned for a new class successfully as message key. */
    public static final String STUDENT_SUCCESSFULLY_ASSIGNED = "REF.UI.STUDENT.SUCCESSFULLY.ASSIGNED";
    
    /** The message code is to display when student(s) assigned for a new class successfully as message key. */
    public static final String STUDENT_SUBJECTS_ASSIGNED = "REF.UI.STUDENT.SUBJECTS.ASSIGNED";
    
    /** The message code is to display when student(s) assigned for a new class successfully as message key. */
    public static final String STUDENT_ALREADY_ASSIGNED = REF_UI_STUDENT_SAMECLASS_ASSIGNED;
    
    /** attribute for holding view page. */
    private static final String VIEW_STUDENT_CLASS_PAGE = "student/studentClass";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_STUDENT_CLASS = "/studentClass.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_RESET_PAGE = "/resetForm.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_SEARCH_STUDENT = "/searchStudentByGradeYear.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_SAVE_STUDENT_LIST = "/saveStudentList.htm";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** attribute for holding parameter name. */
    private static final String PARAM_NAME_SELECTED_GRADE = "SelectedGrade";
    
    /** attribute for holding parameter name. */
    private static final String PARAM_NAME_SELECTED_YEAR = "SelectedYear";
    
    /** attribute for holding parameter name. */
    private static final String PARAM_NAME_FOR_NEW_STUDENTS = NEW_STUDENTS;
    
    /** Represents the key for the marking completion error. */
    private static final String STUDENT_MARK_COMPLETION = "REF.UI.STUDENT.MARK.COMPLETION";
    
    /** attribute for holding title key. */
    private static final String FUTURE_STUDENT_CLASS_ALLOCATION_EXIST = "STUDENT.CLASS.ALLOCATION.EXIST";
    
    /** attribute for holding title key. */
    private static final String FUTURE_STUDENT_CLASS_ALLOCATION_NOT_EXIST = "STUDENT.CLASS.ALLOCATION.NOT.EXIST";
    /** attribute for holding title key. */
    private static final String CURRENT_STUDENT_CLASS_ALLOCATION_EXIST = "CURRENT.STUDENT.CLASS.ALLOCATION.EXIST";
    
    /** attribute for holding title key. */
    private static final String CURRENT_STUDENT_CLASS_ALLOCATION_NOT_EXIST = "CURRENT.STUDENT.CLASS.ALLOCATION.NOT.EXIST";
    
    /**
     * CommonService to store a single service.
     * 
     * @param commonServiceVal to set CommonService
     */
    public void setCommonService(CommonService commonServiceVal) {
    
        this.commonService = commonServiceVal;
    }
    
    /**
     * studentServiceVal to store a single student service.
     * 
     * @param studentServiceVal StudentService to set
     */
    public void setStudentService(StudentService studentServiceVal) {
    
        this.studentService = studentServiceVal;
    }
    
    /**
     * handle GET requests for StudentClass view.
     * 
     * @param request - HttpServletRequest
     * @return the name of the view.
     * @throws AkuraException throws when exception occurs.
     */
    @RequestMapping(value = ACTION_FOR_STUDENT_CLASS, method = RequestMethod.GET)
    public String showGardeList(HttpServletRequest request) throws AkuraException {
    
        return VIEW_STUDENT_CLASS_PAGE;
    }
    
    /**
     * handle GET requests for StudentClass view.
     * 
     * @param request - HttpServletRequest
     * @return the name of the view.
     * @throws AkuraException throws when exception occurs.
     */
    @RequestMapping(value = ACTION_FOR_RESET_PAGE, method = RequestMethod.POST)
    public String resetForm(HttpServletRequest request) throws AkuraException {
    
        return VIEW_STUDENT_CLASS_PAGE;
    }
    
    /**
     * handle POST requests for StudentClass view with data.
     * 
     * @param request - HttpServletRequest
     * @return the name of the view.
     * @throws AkuraException throws when exception occurs.
     */
    @RequestMapping(value = ACTION_FOR_SEARCH_STUDENT, method = RequestMethod.POST)
    public ModelAndView searchStudentByGradeYear(HttpServletRequest request) throws AkuraException {
    
        String grade = request.getParameter(PARAM_NAME_SELECTED_GRADE);
        String year = request.getParameter(PARAM_NAME_SELECTED_YEAR);
        Map<String, Object> finalMap = new TreeMap<String, Object>();
        ModelMap modelMap = new ModelMap();
        Grade gradeObj = null;
        int intYear = 0;
        if (grade != null && !grade.equals("") && !grade.equals(PARAM_NAME_FOR_NEW_STUDENTS)) {
            int intGrade = Integer.parseInt(grade);
            gradeObj = commonService.findGradeById(intGrade);
        }
        if (year != null && !year.equals("")) {
            intYear = Integer.parseInt(year);
            
            if (intYear > 0) {
                modelMap.addAttribute(PARAM_NAME_SELECTED_YEAR, intYear);
            }
        }
        if (gradeObj != null) {
            modelMap.addAttribute(MODEL_GRADE_ID, gradeObj.getGradeId());
            
        }
      
            if (grade.equals(PARAM_NAME_FOR_NEW_STUDENTS)) {
                modelMap.addAttribute(PARAM_NAME_FOR_NEW_STUDENTS, Boolean.TRUE);
            } 
    
        
        // starts validations
        if (!grade.equals("") && !year.equals("")) {
            
            if (grade.equals(PARAM_NAME_FOR_NEW_STUDENTS)) {
                List<Object> studentList = studentService.getNewStudentForYear(intYear);
                finalMap.put(EMPTY, new TreeList(studentList));
                
                // if the students list is empty set the error message.
                if (studentList.isEmpty()) {
                    modelMap.addAttribute(ERROR_MESSAGE,
                            new ErrorMsgLoader().getErrorMessage(REF_UI_STUDENT_CLASS_NEW_STUDENT_NODATA));
                }
            } else {
                modelMap.addAttribute(MODEL_GRADE, gradeObj.getDescription());
                
                finalMap = this.getStudentListWithCurrentAllocation(intYear, gradeObj);
                
                // if the students list is empty set the error message.
                if (finalMap.values().isEmpty()) {
                    modelMap.addAttribute(ERROR_MESSAGE,
                            new ErrorMsgLoader().getErrorMessage(REF_UI_STUDENT_CLASS_STUDENT_NODATA));
                }
            }
            
        } else {
            // error message
            String message = new ErrorMsgLoader().getErrorMessage(MANDATORY_FIELD_ERROR_CODE);
            modelMap.addAttribute(MESSAGE, message);
        }
        
        modelMap.addAttribute(MODEL_LAST_LIST, finalMap);
        return new ModelAndView(VIEW_STUDENT_CLASS_PAGE, modelMap);
    }
    
    /**
     * handle POST requests for StudentClass view with data.
     * 
     * @param request - HttpServletRequest
     * @return the name of the view.
     * @throws AkuraException throws when exception occurs.
     */
    @RequestMapping(value = ACTION_FOR_SAVE_STUDENT_LIST, method = RequestMethod.POST)
    public ModelAndView saveStudentList(HttpServletRequest request) throws AkuraException {
    
        String[] toList = request.getParameterValues(REQUEST_TO_LIST);
        String grade = request.getParameter(PARAM_NAME_SELECTED_GRADE);
        String year = request.getParameter(PARAM_NAME_SELECTED_YEAR);
        String newYear = request.getParameter(REQUEST_NEW_SELECTED_YEAR);
        String newClassGrade = request.getParameter(REQUEST_NEW_SELECTED_GRADE);
        
        int selectedNewYear = 0;
        int clsGradeId = 0;
        if (newClassGrade != null && !"".equals(newClassGrade)) {
            clsGradeId = Integer.parseInt(newClassGrade);
        }
        if (newYear != null && !"".equals(newYear)) {
            selectedNewYear = generateSelectedYear(newYear);
        }
        request.getParameterMap();
        ModelMap modelMap = new ModelMap();
        
        // starts validations - all mandatory parameters should be not null
        if ((!"".equals(newYear)) && (!"".equals(newClassGrade)) && (toList != null) && (!"".equals(grade))
                && (!"".equals(year))) {
            boolean fail = false;
            StringBuilder failStudents = new StringBuilder();
            String newYearConverted = newYear + UNDER_SCOPE + UNDER_SCOPE;
            Date dateSelectedYear = DateUtil.getDateTypeYearValue(newYearConverted); // selected year
            int intNewYear = DateUtil.getYearFromDate(dateSelectedYear);
            
            ClassGrade classGradeNew = commonService.findClassGrade(clsGradeId);
            List<StudentClassInfo> studentClassNewList = new ArrayList<StudentClassInfo>();
            
            // handling student in the list which not belongs to any class
            if (NEW_STUDENTS.equals(grade)) {
                for (String z : toList) { // iterating student id from the input list
                    int studId = Integer.parseInt(z);
                    
                    // assigns the new students that the first date of school is less than or equal
                    // to the new selected date.
                    assignNewStudents(dateSelectedYear, classGradeNew, studentClassNewList, intNewYear, studId);
                }
            } else {
                for (String z : toList) { // iterating student id from the input list
                
                    int studId = Integer.parseInt(z);
                    List<StudentClassInfo> studentClassInfos =
                            studentService.getStudentClassInfoByStudentId(studId, intNewYear);
                    
                    // as year, student id and class grade id is unique in db, only one or no record is coming
                    if (studentClassInfos == null || studentClassInfos.isEmpty()) {
                        
                        // there is no item for new year selected. hence creating new one and add to list.
                        StudentClassInfo sciObjNew = createNewClassInfo(dateSelectedYear, classGradeNew, studId);
                        studentClassNewList.add(sciObjNew);
                    } else {
                        // taking the 1st item as there will be only one item
                        StudentClassInfo item = studentClassInfos.get(0);
                        
                        // if it is same grade, no issues, no marks have been entered for the student for
                        // different grade. lazy false
                        if (item.getClassGrade().getGrade().getGradeId() == classGradeNew.getGrade().getGradeId()
                                || item.getStudentTermMarks() == null || item.getStudentTermMarks().isEmpty()) {
                            item.setClassGrade(classGradeNew);
                            studentClassNewList.add(item);
                        } else {
                            if (failStudents.length() == 0) {
                                failStudents.append(item.getStudent().getFullName());
                            } else {
                                failStudents.append(",");
                                failStudents.append(item.getStudent().getFullName());
                            }
                            fail = true;
                        }
                    }
                }
            }
            if (!fail) {
                // checks the marking completion status.
                List<Boolean> markingStatus = studentService.isExistMarkingCompleted(clsGradeId, newYear);
                
                boolean status = checkMarkCompletionStatus(grade, newYear, clsGradeId, markingStatus);
                if (status) {
                    
                    // assigns into a class.
                    assignToClass(request, clsGradeId, modelMap, intNewYear, studentClassNewList);
                } else {
                    getErrorMessage(modelMap, STUDENT_MARK_COMPLETION, clsGradeId, request, intNewYear);
                }
            } else {
                String message = new ErrorMsgLoader().getErrorMessage(STUDENT_SUBJECTS_ASSIGNED);
                modelMap.addAttribute(MESSAGE, message + failStudents.toString());
                populatedSelectedValues(modelMap, clsGradeId, request, intNewYear);
            }
        } else {
            getErrorMessage(modelMap, MANDATORY_FIELD_ERROR_CODE, clsGradeId, request, selectedNewYear);
        }
        if (!modelMap.containsValue(new ErrorMsgLoader().getErrorMessage(STUDENT_SUCCESSFULLY_ASSIGNED))) {
            Map<String, Object> finalMap = new TreeMap<String, Object>();
            loadClassInfoPage(grade, year, modelMap, finalMap);
        }
        return new ModelAndView(VIEW_STUDENT_CLASS_PAGE, modelMap);
    }
    
    /**
     * Assigns the students into classes if the marking is not completed.
     * 
     * @param request - the request scope related data.
     * @param clsGradeId - the relevant class grade key.
     * @param modelMap - a hash map contains the student class related data.
     * @param intNewYear - the selected year.
     * @param studentClassNewList - a list of students to be assigned to classes.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void assignToClass(HttpServletRequest request, int clsGradeId, ModelMap modelMap, int intNewYear,
            List<StudentClassInfo> studentClassNewList) throws AkuraAppException {
    
        if (!studentClassNewList.isEmpty()) {
            studentService.updateStudentClassInfoObjects(studentClassNewList);
            
            // Auto assign main subjects of grade.
            for (StudentClassInfo studentClassInfo : studentClassNewList) {
                List<GradeSubject> gradeSubjects =
                        commonService.getGradeSubjectIdListByGrade(studentClassInfo.getClassGrade().getGrade()
                                .getGradeId());
                List<Term> terms = commonService.getTermList();
                Iterator<Term> termIterator = terms.iterator();
                List<StudentTermMark> studentTermMarkList = new ArrayList<StudentTermMark>();
                List<StudentSubTermMark> studentSubTermMarkList = new ArrayList<StudentSubTermMark>();
                while (termIterator.hasNext()) {
                    
                    Term term = (Term) termIterator.next();
                    boolean isMarkComplete =
                            studentService.isMarkingCompletedForTerm(clsGradeId, term.getTermId(), intNewYear);
                    if (!isMarkComplete) {
                        for (GradeSubject gradeSubject : gradeSubjects) {
                            
                            // Check student term mark exist
                            boolean isExist =
                                    studentService.isStudentTermMarkExist(studentClassInfo.getStudentClassInfoId(),
                                            gradeSubject.getGradeSubjectId(), term.getTermId());
                            if (!isExist) {
                                if (!gradeSubject.getIsOptionalSubject()) {
                                    
                                    // add term marks into the list.
                                    addTermMarksToList(studentClassInfo, studentTermMarkList, term, gradeSubject);
                                }
                            }
                        }
                    }
                }
                if (studentTermMarkList.size() > 0) {
                    List<StudentTermMark> savedTermMarkList =
                            studentService.saveOrUpdateStudentSubjectList(studentTermMarkList);
                    
                    List<SubTerm> subterms = commonService.getSubTermList();
                    Iterator<SubTerm> subTermIterator = subterms.iterator();
                    while (subTermIterator.hasNext()) {
                        SubTerm subTerm = (SubTerm) subTermIterator.next();
                        for (StudentTermMark studentTermMark : savedTermMarkList) {
                            if (subTerm.getTermId() == studentTermMark.getTermId()) {
                                
                                // add the student sub term marks into the list.
                                addSubTermMarkToList(studentSubTermMarkList, subTerm, studentTermMark);
                            }
                        }
                    }
                    studentService.generateSubtermMarkRecords(studentSubTermMarkList);
                }
            }
            String message = new ErrorMsgLoader().getErrorMessage(STUDENT_SUCCESSFULLY_ASSIGNED);
            modelMap.addAttribute(MODEL_MESSAGE_SUCCESS, message);
        } else {
            getErrorMessage(modelMap, STUDENT_FUTURE_ASSIGNMENT_ERROR, clsGradeId, request, intNewYear);
        }
    }
    
    /**
     * Generates the selected year.
     * 
     * @param newYear - the selected new year.
     * @return - the selected new year.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private int generateSelectedYear(final String newYear) throws AkuraAppException {
    
        String newYearConverted = newYear + UNDER_SCOPE + UNDER_SCOPE;
        Date dateSelectedYear = DateUtil.getDateTypeYearValue(newYearConverted); // selected year
        return DateUtil.getYearFromDate(dateSelectedYear);
    }
    
    /**
     * Adds the student term marks of the relevant student into the term marks list.
     * 
     * @param studentClassInfo - the related class information of the student.
     * @param studentTermMarkList - the list of the term marks of students for the relevant class grade.
     * @param term - the relevant term.
     * @param gradeSubject - the grade subject
     */
    private void addTermMarksToList(StudentClassInfo studentClassInfo, List<StudentTermMark> studentTermMarkList,
            Term term, GradeSubject gradeSubject) {
    
        StudentTermMark studentTermMark = new StudentTermMark();
        studentTermMark.setStudentClassInfoId(studentClassInfo.getStudentClassInfoId());
        studentTermMark.setGradeSubjectId(gradeSubject.getGradeSubjectId());
        studentTermMark.setTermId(term.getTermId());
        studentTermMarkList.add(studentTermMark);
    }
    
    /**
     * Adds the student sub term marks into the sub term marks list.
     * 
     * @param studentSubTermMarkList - a list of student sub term marks of the students for a relevant class
     *        grade.
     * @param subTerm - the sub term
     * @param studentTermMark - sub term mark
     */
    private void addSubTermMarkToList(List<StudentSubTermMark> studentSubTermMarkList, SubTerm subTerm,
            StudentTermMark studentTermMark) {
    
        StudentSubTermMark subtermMark = new StudentSubTermMark();
        subtermMark.setStuTermMarkID(studentTermMark.getStudentTermMarkId());
        subtermMark.setSubtermID(subTerm.getSubTermId());
        studentSubTermMarkList.add(subtermMark);
    }
    
    /**
     * Generates the error messages for a relevant key.
     * 
     * @param modelMap - the hash map contains the student class related data.
     * @param errorMessageKey - the key of the error messages.
     * @param request - the request scope objects
     * @param clsGradeId - the relevant class grade key.
     * @param newYear - the selected new year.
     */
    private void getErrorMessage(ModelMap modelMap, String errorMessageKey, int clsGradeId, HttpServletRequest request,
            int newYear) {
    
        populatedSelectedValues(modelMap, clsGradeId, request, newYear);
        String message = new ErrorMsgLoader().getErrorMessage(errorMessageKey);
        modelMap.addAttribute(MESSAGE, message);
    }
    
    /**
     * Populates the model map with the selected values to display the panels.
     * 
     * @param modelMap - the hash map contains the student class related data.
     * @param request - the request scope objects
     * @param clsGradeId - the relevant class grade key.
     * @param newYear - the selected new year.
     */
    private void populatedSelectedValues(ModelMap modelMap, int clsGradeId, HttpServletRequest request, int newYear) {
    
        modelMap.addAttribute(SELECTED_NEW_YEAR, newYear);
        modelMap.addAttribute(NEW_SELECTED_GRADE, clsGradeId);
        String selectedList = (String) request.getParameter(SELECTED_ARRAY);
        if (!selectedList.isEmpty()) {
            modelMap.addAttribute(SELECTED_TO_LIST, selectedList);
        }
        modelMap.addAttribute(REMOVED_FROM_ARRAY, request.getParameter(REMOVED_FROM_ARRAY));
    }
    
    /**
     * Assigns the new students into the selected year that is after or equals to the first date at the
     * school.
     * 
     * @param dateSelectedYear - the selected assign year.
     * @param classGradeNew - the selected new class.
     * @param studentClassNewList - the list of the class assignment.
     * @param intNewYear - the new year.
     * @param studId - the key of the student.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void assignNewStudents(Date dateSelectedYear, ClassGrade classGradeNew,
            List<StudentClassInfo> studentClassNewList, int intNewYear, int studId) throws AkuraAppException {
    
        Date startedDate = studentService.getStudentStartedDate(studId);
        if (startedDate != null) {
            int firstDayAtSchool = DateUtil.getYearFromDate(startedDate);
            
            // if the first date at school is before the new selected year do not
            // assign to the particular class.
            if (firstDayAtSchool <= intNewYear) {
                StudentClassInfo sciObjNew = createNewClassInfo(dateSelectedYear, classGradeNew, studId);
                studentClassNewList.add(sciObjNew);
            }
        }
    }
    
    /**
     * Checks the marking completion status.
     * 
     * @param grade - the relevant grade.
     * @param year - the year
     * @param clsGradeId - the relevant class grade key.
     * @param markingStatus - the list of the marking status.
     * @return - the status of the marking completion.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private boolean checkMarkCompletionStatus(String grade, String year, int clsGradeId, List<Boolean> markingStatus)
            throws AkuraAppException {
    
        boolean isMarkingCompleted = false;
        boolean isMarkingNotCompletedForTerms = false;
        boolean isExistMaringStatus = markingStatus.isEmpty();
        
        // if marking status records are there for the relevant class grade and the year,
        // then assign existing students into classes.
        if (!isExistMaringStatus) {
            
            int noOfTermsList = studentService.getNoOfTerms();
            int markingStatusSize = markingStatus.size();
            
            // checks weather there are at least one record that is not complete the
            // marking for the relevant class grade and the year.
            isMarkingNotCompletedForTerms =
                    markingStatusSize < noOfTermsList
                            || (markingStatusSize == noOfTermsList && markingStatus.contains(Boolean.FALSE)) ? true
                            : false;
            
            // checks the marking status for the completed markings for a relevant class grade key,
            // year.
            isMarkingCompleted = studentService.isMarkingCompleted(clsGradeId, year, true);
        }
        // if marking not completed for all the terms and the not a new student or if the marking is
        // not completed at least one term for a new student, update the records.
        
        return (!isMarkingCompleted && !NEW_STUDENTS.equals(grade))
                || (NEW_STUDENTS.equals(grade) && isMarkingNotCompletedForTerms)
                || (NEW_STUDENTS.equals(grade) && isExistMaringStatus);
    }
    
    /**
     * Loads the Class info page back after save.
     * 
     * @param grade the grade.
     * @param year the year.
     * @param modelMap the model map.
     * @param finalMap the final map.
     * @throws AkuraAppException the AkuraAppException
     */
    private void loadClassInfoPage(String grade, String year, ModelMap modelMap, Map<String, Object> finalMap)
            throws AkuraAppException {
    
        if (!"".equals(grade) && !"".equals(year)) {
            int intYear = Integer.parseInt(year);
            
            if (!grade.equals(PARAM_NAME_FOR_NEW_STUDENTS)) {
                
                // converts
                int intGrade = Integer.parseInt(grade);
                Grade gradeObj = commonService.findGradeById(intGrade);
                modelMap.addAttribute(MODEL_GRADE, gradeObj.getDescription());
                modelMap.addAttribute(MODEL_GRADE_ID, gradeObj.getGradeId());
                finalMap = this.getStudentList(intYear, gradeObj);
                
            } else {
                modelMap.addAttribute(PARAM_NAME_FOR_NEW_STUDENTS, Boolean.TRUE);
                List<Object> studentList = studentService.getNewStudentForYear(intYear);
                finalMap.put(EMPTY, new TreeList(studentList));
            }
            modelMap.addAttribute(PARAM_NAME_SELECTED_YEAR, intYear);
        }
        modelMap.addAttribute(MODEL_LAST_LIST, finalMap);
    }
    
    /**
     * creates new ClassInfo Object.
     * 
     * @param dateSelectedYear year.
     * @param classGradeNew the class grade.
     * @param studId the student id.
     * @return the Class info Object.
     * @throws AkuraAppException the AkuraAppException.
     */
    private StudentClassInfo createNewClassInfo(Date dateSelectedYear, ClassGrade classGradeNew, int studId)
            throws AkuraAppException {
    
        StudentClassInfo sciObjNew = new StudentClassInfo();
        Student student = studentService.findStudent(studId);
        sciObjNew.setClassGrade(classGradeNew);
        sciObjNew.setStudent(student);
        sciObjNew.setYear(dateSelectedYear);
        sciObjNew.setCheckMonitor(false);
        return sciObjNew;
    }
    
    /**
     * Method to retrieve the student list given by grade and current year.
     * 
     * @param gradeObj Grade type object which a student belongs to
     * @param year integer type object which specify the current year
     * @return the map.
     * @throws AkuraAppException throws when exception occurs.
     */
    private Map<String, Object> getStudentList(int year, Grade gradeObj) throws AkuraAppException {
    
        Map<String, Object> finalMap = new TreeMap<String, Object>(); // final map
        Iterator<ClassGrade> clsGrdList = commonService.getClassGradeListByGrade(gradeObj).iterator();
        List<StudentClassInfo> stClaInfoList = studentService.getStudentSearchByGradeYear(gradeObj.getGradeId(), year);
        
        while (clsGrdList.hasNext()) {
            ClassGrade classGradeObj = (ClassGrade) clsGrdList.next();
            List<StudentClassInfo> stList = new ArrayList<StudentClassInfo>();
            
            for (StudentClassInfo studentClassInfo : stClaInfoList) {
                if (studentClassInfo.getClassGrade().getDescription().equals(classGradeObj.getDescription())) {
                    stList.add(studentClassInfo);
                    // int studentId=studentClassInfo.getStudent().getStudentId();
                    
                }
            }
            
            if (!stList.isEmpty()) {
                stList = SortUtil.sortStudentClassInfoList(stList);
                finalMap.put(classGradeObj.getDescription(), new TreeList(stList));
                
            }
        }
        return finalMap;
    }
    
    /**
     * Method to retrieve the student list given by grade and current year.
     * 
     * @param gradeObj Grade type object which a student belongs to
     * @param year integer type object which specify the current year
     * @return the map.
     * @throws AkuraAppException throws when exception occurs.
     */
    private Map<String, Object> getStudentListWithCurrentAllocation(int year, Grade gradeObj) throws AkuraAppException {
    
        Map<String, Object> finalMap = new TreeMap<String, Object>(); // final map
        Iterator<ClassGrade> clsGrdList = commonService.getClassGradeListByGrade(gradeObj).iterator();
        List<StudentClassInfo> stClaInfoList = studentService.getStudentSearchByGradeYear(gradeObj.getGradeId(), year);
        
        // get current year(yyyy) in string
        Date currentDate = new Date();
        String strYr = DateUtil.getStringYear(currentDate);
        int intCurrentYr = Integer.parseInt(strYr);
        
        int allocationCheckYr;
        if (year < intCurrentYr) {
            allocationCheckYr = intCurrentYr;
        } else if(year ==  intCurrentYr){
            allocationCheckYr = intCurrentYr + 1;
        }else{
            allocationCheckYr = intCurrentYr + 2;
        }
        
        while (clsGrdList.hasNext()) {
            ClassGrade classGradeObj = (ClassGrade) clsGrdList.next();
            
            Map<StudentClassInfo, String> stMap = new LinkedHashMap<StudentClassInfo, String>();
            
            for (StudentClassInfo studentClassInfo : stClaInfoList) {
                if (studentClassInfo.getClassGrade().getDescription().equals(classGradeObj.getDescription())) {
                    
                    int studentId = studentClassInfo.getStudent().getStudentId();
                    List<StudentClassInfo> stClassAllocationInfo =
                            studentService.getStudentClassInfoByStudent(studentId, allocationCheckYr);
                    String found=""; 
                    if(year < intCurrentYr){
                    found = new ErrorMsgLoader().getErrorMessage(CURRENT_STUDENT_CLASS_ALLOCATION_NOT_EXIST);
                    }
                    if(year == intCurrentYr){
                        found = new ErrorMsgLoader().getErrorMessage(FUTURE_STUDENT_CLASS_ALLOCATION_NOT_EXIST);   
                    }
                    
                    if (!stClassAllocationInfo.isEmpty()) {
                        if(year < intCurrentYr){
                            found = new ErrorMsgLoader().getErrorMessage(CURRENT_STUDENT_CLASS_ALLOCATION_EXIST);
                            }
                            if(year == intCurrentYr){
                                found = new ErrorMsgLoader().getErrorMessage(FUTURE_STUDENT_CLASS_ALLOCATION_EXIST);   
                            }                      
                        
                    }
                    stMap.put(studentClassInfo, found);
                    
                }
            }
            if (!stMap.isEmpty()) {
                
                finalMap.put(classGradeObj.getDescription(), stMap);
                
            }
            
        }
        return finalMap;
    }
    
    /**
     * Method to retrieve all the Grade data.
     * 
     * @return List of Grade objects
     * @throws AkuraAppException throws when error occurs
     */
    @ModelAttribute(MODEL_GRADE_LIST)
    public List<Grade> populateGradeListData() throws AkuraAppException {
    
        return SortUtil.sortGradeList(commonService.getGradeList());// make the list to sorted list
    }
    
    /**
     * Method to retrieve all the ClassGrade data.
     * 
     * @return List of ClassGrade objects
     * @throws AkuraAppException throws when error occurs
     */
    @ModelAttribute(MODEL_CLASS_GRADE_LIST)
    public List<ClassGrade> populateClassGradeListData() throws AkuraAppException {
    
        return SortUtil.sortClassGradeList(commonService.getClassGradeList());// make the list to sorted list
    }
}
