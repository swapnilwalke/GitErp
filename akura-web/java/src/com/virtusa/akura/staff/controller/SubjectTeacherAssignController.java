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

package com.virtusa.akura.staff.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ClubSociety;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.dto.SportCategory;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.SubjectTeacher;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.SortUtil;

/**
 * Description: SubjectTeacherAssignController controls function of assigning Teachers to relevant subjects.
 * 
 * @author Virtusa Corporation
 */

@Controller
@SessionAttributes("subjectteacher")
public class SubjectTeacherAssignController {
    
    /** attribute for holding "populateGradeClassesList.htm". */
    private static final String POPULATE_GRADE_SUBJECT_LIST = "populateGradeClassesList.htm";
    
    /** attribute for holding "". */
    private static final String EMPTY_STRING = "";
    
    /** attribute for holding "0". */
    private static final String ZERO_STRING = "0";
    
    /** attribute for holding "updateSelectedSubjectTeacherId". */
    private static final String UPDATE_SELECTED_SUBJECT_TEACHER_ID = "updateSelectedSubjectTeacherId";
    
    /** attribute for holding "selectGradeSubjects". */
    private static final String SELECT_GRADE_SUBJECTS = "selectGradeSubjects";
    
    /** attribute for holding "updateSelectedSubject". */
    private static final String UPDATE_SELECTED_SUBJECT = "updateSelectedSubject";
    
    /** attribute for holding "updateSelectedGrade". */
    private static final String UPDATE_SELECTED_GRADE = "updateSelectedGrade";
    
    /** attribute for holding action searchsubjectteacher.htm. */
    private static final String SEARCH_SUBJECT_TEACHER_HTM = "/searchsubjectteacher.htm";
    
    /** attribute for holding action addSubjectTeacher.htm. */
    private static final String ADDSUBJECTTEACHER_HTM = "/addSubjectTeacher.htm";
    
    /** attribute for holding action deleteSubjectTeacher.htm. */
    private static final String DELETE_SUBJECTTEACHER_HTM = "/deleteSubjectTeacher.htm";
    
    /** attribute for holding action findClasses.htm. */
    private static final String FINDCLASSES_HTM = "/findClasses.htm";
    
    /** attribute for holding error key SUBJECTTEACHER.SEARCH.NO.RESULT. */
    private static final String SUBJECTTEACHER_SEARCH_NO_RESULT = "SUBJECTTEACHER.SEARCH.NO.RESULT";
    
    /** attribute for holding error key REF.UI.SUBJECTTEACHER.ALREADY.EXIST. */
    private static final String REF_UI_SUBJECTTEACHER_ALREADY_EXIST = "REF.UI.SUBJECTTEACHER.ALREADY.EXIST";
    
    /** attribute for holding model attribute teacherList. */
    private static final String TEACHER_LIST = "teacherList";
    
    /** attribute for holding model attribute classSize. */
    private static final String CLASS_SIZE = "classSize";
    
    /** attribute for holding model attribute schoolClassList. */
    private static final String SCHOOL_CLASS_LIST = "schoolClassList";
    
    /** attribute for holding model attribute acedemicstafflist. */
    private static final String ACEDEMICS_TAFF_LIST = "acedemicstafflist";
    
    /** attribute for holding model attribute gradeSubjectList. */
    private static final String GRADE_SUBJECT_LIST = "gradeSubjectList";
    
    /** attribute for holding model attribute gradeList. */
    private static final String GRADE_LIST = "gradeList";
    
    /** attribute for holding model attribute subjectList. */
    private static final String SUBJECT_LIST = "subjectList";
    
    /** attribute for holding request attribute lName. */
    private static final String REG_NO = "regNo";
    
    /** attribute for holding request attribute lName. */
    private static final String L_NAME = "lName";
    
    /** attribute for holding model attribute message. */
    private static final String MESSAGE = "message";
    
    /** attribute for holding model attribute message. */
    private static final String SUCCESS_MESSAGE = "successMessage";
    
    /** attribute for holding request object "selectedYearVal". */
    private static final String SELECTED_YEAR_VAL = "selectedYearVal";
    
    /** attribute for holding error key REF.UI.MANDATORY.FIELD.REQUIRED. */
    private static final String REF_UI_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";
    
    /** model attribute of current year. */
    private static final String MODEL_ATT_CURRENT_YEAR = "currentYear";
    
    /** attribute for holding selectedValue. */
    private static final String SELECTED_VALUE = "selectedValue";
    
    /** attribute for holding classId. */
    private static final String CLASS_ID = "classId";
    
    /** attribute for holding regNumber. */
    private static final String REG_NUMBER = "regNumber";
    
    /** attribute for holding lastName. */
    private static final String LAST_NAME = "lastName";
    
    /** attribute for holding staffId. */
    private static final String STAFF_ID = "staffId";
    
    /** attribute for holding view name staff/teacherSubjectAllocation. */
    private static final String VIEW_TEACHER_SUBJECT_ALLOCATION = "staff/teacherSubjectAllocation";
    
    /** attribute for holding model variable name subjectteacher. */
    private static final String SUBJECT_TEACHER = "subjectteacher";
    
    /** key to hold message when record added. */
    private static final String MESSAGE_RECORD_ADDED = "STAFF.TEACHER.SUBJECT.ALLOCATION";
    
    /** key to hold message when record updated. */
    private static final String MESSAGE_RECORD_UPDATED = "STAFF.TEACHER.SUBJECT.UPDATE";
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_SELECTED_OBJECT_ID = "ObjId";
    
    /**
     * {@link StaffService}.
     */
    private StaffService staffService;
    
    /**
     * Represents reference to a commonService.
     */
    private CommonService commonService;
    
    /**
     * setter for staffService.
     * 
     * @param stafService - staffService
     */
    public void setStaffService(StaffService stafService) {
    
        this.staffService = stafService;
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
     * get current year.
     * 
     * @return Date object
     * @throws AkuraAppException - throw this
     */
    @ModelAttribute(MODEL_ATT_CURRENT_YEAR)
    public int currentYear() throws AkuraAppException {
    
        return DateUtil.currentYearOnly();
    }
    
    /**
     * handle GET requests for Staff_module_Assign_SubjectTeacher_ view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showStudentDetailForm(ModelMap model) {
    
        SubjectTeacher teacher = (SubjectTeacher) model.get(SUBJECT_TEACHER);
        teacher = new SubjectTeacher();
        model.addAttribute(SUBJECT_TEACHER, teacher);
        return VIEW_TEACHER_SUBJECT_ALLOCATION;
    }
    
    /**
     * Deletes the relevant SubjectTeacher object.
     * 
     * @param request - an object of HttpServletRequest
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a subject teacher.
     */
    @RequestMapping(DELETE_SUBJECTTEACHER_HTM)
    public final ModelAndView deleteSubjectTeacher(final HttpServletRequest request) throws AkuraAppException {
    
        String staffId = request.getParameter(STAFF_ID);
        String gradeId = request.getParameter(UPDATE_SELECTED_GRADE);
        String subjectId = request.getParameter(UPDATE_SELECTED_SUBJECT);
        String year = request.getParameter(SELECTED_YEAR_VAL);
        int intStaffId = Integer.parseInt(staffId);
        int intGradeId = Integer.parseInt(gradeId);
        int intSubjectId = Integer.parseInt(subjectId);
        
        List<GradeSubject> gradeSubject = commonService.getGradeSubjectByGradeAndSubject(intGradeId, intSubjectId);
        
        int intGradeSubjectId = gradeSubject.get(0).getGradeSubjectId();
        staffService.deleteSubjectTeacher(intStaffId, intGradeSubjectId, year);
        String lastName = (String) request.getSession().getAttribute(LAST_NAME);
        String regNo = (String) request.getSession().getAttribute(REG_NUMBER);
        
        return showSearchResults(lastName, regNo);
    }
    
    /**
     * Assign a subject teacher object.
     * 
     * @param request - an object of HttpServletRequest
     * @param map - a HashMap that contains information of the District
     * @param subTeacher - subject teacher object.
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when add a subject teacher.
     */
    @RequestMapping(ADDSUBJECTTEACHER_HTM)
    public final String addSubjectTeacher(@ModelAttribute(SUBJECT_TEACHER) SubjectTeacher subTeacher, ModelMap map,
            final HttpServletRequest request) throws AkuraAppException {
    
        String message;
        String[] classIds = request.getParameterValues(CLASS_ID);
        String selectedGradeSubject = request.getParameter(SELECT_GRADE_SUBJECTS);
        String updateTeacherId = request.getParameter(UPDATE_SELECTED_SUBJECT_TEACHER_ID);
        SubjectTeacher subjectTeacher;
      
       // int intSubjectTeacherId = subTeacher.getSubjectTeacherId();
        
        // Validate the user inputs and show an error message if validation fails.
        if (subTeacher.getStaff().getStaffId() == 0 || subTeacher.getGradeId() == 0
                || selectedGradeSubject.equals(ZERO_STRING) || classIds == null
                || subTeacher.getYear().equals(ZERO_STRING)) {
            message = new ErrorMsgLoader().getErrorMessage(REF_UI_MANDATORY_FIELD_REQUIRED);
            map.addAttribute(MESSAGE, message);
            
            map.addAttribute("subTeacher", subTeacher);
            
            if (subTeacher.getSubjectTeacherId() != null) {
                subjectTeacher = staffService.findsubjectTeacherById(subTeacher.getSubjectTeacherId());
                map.addAttribute(MODEL_ATT_SELECTED_OBJECT, subjectTeacher);
            }
           // map.addAttribute(MODEL_ATT_SELECTED_OBJECT_ID, intSubjectTeacherId);
            
            return VIEW_TEACHER_SUBJECT_ALLOCATION;
            
        } else {
            int subjectId = Integer.parseInt(selectedGradeSubject);
            int gradeSubjectradeId =
                    commonService.getGradeSubjectByGradeAndSubject(subTeacher.getGradeId(), subjectId).get(0)
                            .getGradeSubjectId();
            int staffId = subTeacher.getStaff().getStaffId();
            String year = subTeacher.getYear();
            GradeSubject gradeSubject = commonService.findGradeSubject(gradeSubjectradeId);
            Staff staff = staffService.findStaff(staffId);
            subTeacher.setStaff(staff);
            subTeacher.setGradeSubject(gradeSubject);
            subTeacher.setYear(year);
            
            // Get the list of selected classes to an array list.
            List<String> selectedclassList = new ArrayList<String>();
            selectedclassList = Arrays.asList(classIds);
            
            // Check the action to perform, whether to update or add new record.
            boolean update = updateTeacherId.equals(EMPTY_STRING) ? false : true;
            
            if (update) {
                
                // Get the id of the subject teacher to be updated.
                updateTeacherId = (updateTeacherId.equals(EMPTY_STRING) ? ZERO_STRING : updateTeacherId);
                int updateId = Integer.parseInt(updateTeacherId);
                
                // Get the Subject teacher to be updated.
                SubjectTeacher subjectTeachertobeUpdated = staffService.findsubjectTeacherById(updateId);
                
                // If the subject teacher to be updated is null then assign it to the subject teacher in the
                // model.
                subjectTeachertobeUpdated =
                        (subjectTeachertobeUpdated == null ? subTeacher : subjectTeachertobeUpdated);
                
                // If this teacher exist show teacher exist error message.
                if (isExistsSubjectTeacher(staffId, gradeSubjectradeId, year)) {
                    
                    if (subjectTeachertobeUpdated.getSubjectTeacherId().equals(
                            staffService.getSubjectTeacherList(staffId, gradeSubjectradeId, year).get(0)
                                    .getSubjectTeacherId())) {
                        staffService.deleteSubjectTeacher(staffId, gradeSubjectradeId, year);
                    } else {
                        message = new ErrorMsgLoader().getErrorMessage(REF_UI_SUBJECTTEACHER_ALREADY_EXIST);
                        map.addAttribute(MESSAGE, message);
                        return VIEW_TEACHER_SUBJECT_ALLOCATION;
                    }
                }
                
                // Get the list of Subject teachers with the updating staffId, gradeId, subjectId and year.
                List<SubjectTeacher> subjectTeacherList =
                        staffService.getSubjectTeacherList(subjectTeachertobeUpdated.getStaff().getStaffId(),
                                subjectTeachertobeUpdated.getGradeSubject().getGradeSubjectId(),
                                subjectTeachertobeUpdated.getYear());
                
                // Delete the list of subject teachers.
                for (SubjectTeacher deleteOrAddTeacher : subjectTeacherList) {
                    staffService.deleteSubjectTeacher(deleteOrAddTeacher);
                }
                
                // Set the Subject teacher Class and add new subject teachers.
                setTeacherSubjects(subTeacher, selectedclassList);
                
                // Set the subject teacher allocation update successful message.
                message = new ErrorMsgLoader().getErrorMessage(MESSAGE_RECORD_UPDATED);
                map.addAttribute(SUCCESS_MESSAGE, message);
            } else {
                
                // If this teacher exist show teacher exist error message.
                if (isExistsSubjectTeacher(staffId, gradeSubjectradeId, year)) {
                    message = new ErrorMsgLoader().getErrorMessage(REF_UI_SUBJECTTEACHER_ALREADY_EXIST);
                    map.addAttribute(MESSAGE, message);
                    return VIEW_TEACHER_SUBJECT_ALLOCATION;
                }
                setTeacherSubjects(subTeacher, selectedclassList);
                
                // Set the new subject teacher allocation successful message.
                message = new ErrorMsgLoader().getErrorMessage(MESSAGE_RECORD_ADDED);
                map.addAttribute(SUCCESS_MESSAGE, message);
            }
        }
        return VIEW_TEACHER_SUBJECT_ALLOCATION;
    }
    
    /**
     * Set the Subject teacher Class and add new subject teachers.
     * 
     * @param subTeacher subject teacher to add classes.
     * @param selectedclassList the selected list of classes.
     * @throws AkuraAppException - if failed to assign classes.
     */
    private void setTeacherSubjects(SubjectTeacher subTeacher, List<String> selectedclassList) throws AkuraAppException {
    
        for (String addTeachers : selectedclassList) {
            SchoolClass schoolClass = commonService.findClass(Integer.valueOf(addTeachers));
            subTeacher.setSchoolClass(schoolClass);
            staffService.assignSubjectTeacher(subTeacher);
        }
    }
    
    /**
     * handle POST requests for staffSearch.
     * 
     * @param request - HttpServletRequest.
     * @return ModelAndView.
     * @throws AkuraAppException AkuraAppException.
     */
    @RequestMapping(method = RequestMethod.POST, value = SEARCH_SUBJECT_TEACHER_HTM)
    public ModelAndView staffSearch(final HttpServletRequest request) throws AkuraAppException {
    
        request.getSession().removeAttribute(LAST_NAME);
        request.getSession().removeAttribute(REG_NUMBER);
        String lName = request.getParameter(L_NAME);
        String regNo = request.getParameter(REG_NO);
        request.getSession().setAttribute(LAST_NAME, lName);
        request.getSession().setAttribute(REG_NUMBER, regNo);
        return showSearchResults(lName, regNo);
    }
    
    /**
     * Returns a list of classGrades.
     * 
     * @return gradeSubjectList - a list of grade subject.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(GRADE_SUBJECT_LIST)
    public List<GradeSubject> populateGradeSubjectList() throws AkuraAppException {
    
        return SortUtil.sortGradeSubjectList(commonService.getGradeSubjectList());
    }
    
    /**
     * Returns a list of Grades.
     * 
     * @return gradeList - a list of grade.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(GRADE_LIST)
    public List<Grade> populateGradeList() throws AkuraAppException {
    
        return SortUtil.sortGradeList(commonService.getGradeList());
    }
    
    /**
     * Returns a list of Subjects.
     * 
     * @return subjectList - a list of subjects.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(SUBJECT_LIST)
    public List<Subject> populateSubjectList() throws AkuraAppException {
    
        return SortUtil.sortSubjectList(commonService.getSubjectList());
    }
    
    /**
     * Returns a list of classGrades.
     * 
     * @return academicstafflist - a list of academic staff.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(ACEDEMICS_TAFF_LIST)
    public List<Staff> populateAcedemicStaffList() throws AkuraAppException {
    
        return SortUtil.sortStaffList(staffService.getAcedemicStaff());
    }
    
    /**
     * Method is to return schoolClass list.
     * 
     * @param model - {@link ModelMap}
     * @return list of schoolClass
     * @throws AkuraAppException - Detailed exception.
     */
    @ModelAttribute(SCHOOL_CLASS_LIST)
    public List<SchoolClass> populateSchoolClasss(ModelMap model) throws AkuraAppException {
    
        List<SchoolClass> schoolClasses = commonService.getClassList();
        model.addAttribute(CLASS_SIZE, schoolClasses.size());
        return schoolClasses;
    }
    
    /**
     * Check whether Grade is already added.
     * 
     * @param staffID - int Staff ID.
     * @param gradeSubjectId - int grade subject ID.
     * @param year assigned year.
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    private boolean isExistsSubjectTeacher(int staffID, int gradeSubjectId, String year) throws AkuraAppException {
    
        boolean isExists = staffService.getSubjectTeacherList(staffID, gradeSubjectId, year).isEmpty() ? false : true;
        return isExists;
    }
    
    /**
     * Method is to return GradeClass list.
     * 
     * @param request - HttpServletRequest
     * @param modelMap - ModelMap attribute.
     * @return list of classGrade
     * @throws AkuraAppException - Detailed exception
     */
    
    @ResponseBody
    @RequestMapping(value = POPULATE_GRADE_SUBJECT_LIST)
    public String populateGradeClass(ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {
    
        StringBuilder allGradeClass = new StringBuilder();
        int gradeId = Integer.parseInt(request.getParameter(SELECTED_VALUE));
        List<GradeSubject> gradeSubjectList = commonService.getGradeSubjectIdListByGrade(gradeId);
        boolean isFirst = true;
        StringBuilder classes = new StringBuilder();
        
        for (GradeSubject gradeSubject : gradeSubjectList) {
            classes.append(gradeSubject.getSubject().getDescription());
            classes.append("_");
            classes.append(gradeSubject.getSubject().getSubjectId());
            
            if (isFirst) {
                allGradeClass.append(classes.toString()); // no comma
                isFirst = false;
            } else {
                allGradeClass.append(","); // comma
                allGradeClass.append(classes.toString());
            }
            classes.delete(0, classes.length());
        }
        return allGradeClass.toString();
    }
    
    /**
     * Once the User selects a Grade from the drop down. findClassesOfGrade will return the classes of the
     * selected grade
     * 
     * @param modelMap of type ModelMap
     * @param request of type HttpServletRequest
     * @return java.lang.String - return string
     * @throws AkuraAppException throw exception if occur.
     */
    @ResponseBody
    @RequestMapping(value = FINDCLASSES_HTM)
    public String findClassesOfGrade(ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {
    
        StringBuilder allClasses = new StringBuilder();
        String selectedGradeDescription = request.getParameter(SELECTED_VALUE).toString();
        int selectedGradeID = Integer.parseInt(selectedGradeDescription);
        Grade selectedGrade = commonService.findGradeById(selectedGradeID);
        List<ClassGrade> classGrades = commonService.getClassGradeListByGrade(selectedGrade);
        boolean isFirst = true;
        StringBuilder classes = new StringBuilder();
        
        for (ClassGrade classGrade : classGrades) {
            classes.append(classGrade.getSchoolClass().getDescription());
            classes.append("-");
            classes.append(classGrade.getSchoolClass().getClassId());
            
            if (isFirst) {
                allClasses.append(classes.toString()); // no comma
                isFirst = false;
            } else {
                allClasses.append(","); // comma
                allClasses.append(classes.toString());
            }
            classes.delete(0, classes.length());
        }
        return allClasses.toString();
    }
    
   
    /**
     * Show the list of subject teachers.
     * 
     * @param lastName - Last the name of the subject teachers.
     * @param regNo - the reg number of the subject teacher.
     * @return a model with subject teacher list.
     * @throws AkuraAppException - a detailed exception throws when fails.
     */
    public ModelAndView showSearchResults(String lastName, String regNo) throws AkuraAppException {
    
        String message;
        List<SubjectTeacher> teacherList = staffService.getSubjectTeachers(lastName, regNo);
        ModelMap modelMap = new ModelMap();
        if (teacherList.isEmpty()) {
            message = new ErrorMsgLoader().getErrorMessage(SUBJECTTEACHER_SEARCH_NO_RESULT);
            modelMap.addAttribute(MESSAGE, message);
        } else {
            List<SubjectTeacher> groupbyTeacherList = new ArrayList<SubjectTeacher>();
            for (SubjectTeacher subjectTeacher : teacherList) {
                subjectTeacher.setSchoolClassList(staffService.getSchoolClassList(subjectTeacher.getStaff()
                        .getStaffId(), subjectTeacher.getGradeSubject().getGradeSubjectId(), subjectTeacher.getYear()));
                groupbyTeacherList.add(subjectTeacher);
            }
            modelMap.addAttribute(TEACHER_LIST, SortUtil.sortSubjectTeacherList(groupbyTeacherList));
        }
        return new ModelAndView(VIEW_TEACHER_SUBJECT_ALLOCATION, modelMap);
    }
}
