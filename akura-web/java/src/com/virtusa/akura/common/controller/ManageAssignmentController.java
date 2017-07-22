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

package com.virtusa.akura.common.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.virtusa.akura.api.dto.Assignment;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.exception.AkuraAppException;

import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.AssignmentValidator;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.SortUtil;

/**
 * This class handles the requests for domains of the Add/Edit/Delete, related functions. main functions of
 * this class is for assign assignments for selected subjects which is belong to the Grade.
 *
 * @author Virtusa Corporation
 */

@Controller
@SessionAttributes("Wrapperassignment")
public class ManageAssignmentController {

    /** string attribute for holding update fail error message. */
    private static final String CANNOT_UPDATED = "REF.UI.ASSIGNMENT.CANNOT_UPDATED";

    /** string attribute for holding undefined message. */
    private static final String UNDEFINED = "undefined";

    /** request attribute for holding selected subject. */
    private static final String REQ_ATT_SELECT_SUBJECTS = "selectSubjects";

    /** attribute for holding message. */
    private static final String MESSAGE = "message";

    /** string attribute for holding description error message. */
    private static final String ERROR_MSG_ASSIGNMENT_DESCRIPTION = "REF.UI.ASSIGNMENT.DESCRIPTION";

    /** request mapping value for view class list of grade. */
    private static final String GRADE_SUBJECT_HTM = "/populateGradeSubjectList4.htm";

    /** view post method subject Assignment. */
    private static final String VIEW_POST_MANAGE_SUBJECT_ASSIGNMENT = "redirect:manageAssignment.htm";

    /** request mapping value for add class. */
    private static final String REQ_MAP_VALUE_ADD = "/manageAssignment.htm";

    /** request mapping value for delete Subject Assignments. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteAssignment.htm";

    /** key to hold string selectedValue. */
    private static final String SELECTED_VALUE = "selectedValue";

    /** model attribute of gradeList. */
    private static final String MODEL_ATT_GRADELIST = "gradeList";

    /** model attribute of subject Assignment Type list. */
    private static final String MODEL_ATT_SUBJECT_ASSIGNMENT_LIST = "assignmentList";

    /** model attribute of subjectList. */
    private static final String MODEL_ATT_SUBJECTLIST = "subjectList";

    /** model attribute of school class. */
    private static final String MODEL_ATT_ASSIGNMENT = "Wrapperassignment";

    /** view get method add class. */
    private static final String VIEW_GET_MANAGE_ADD_ASSIGNMENT = "reference/manageAssignment";

    /** request mapping value for save or update assignment. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/manageSaveOrUpdateAssignment.htm";

    /** String attribute for holding DEFALUT_DATE_FORMAT. */
    private static final String DEFALUT_DATE_FORMAT = "yyyy-MM-dd";

    /** key to hold error message when mandatory field null. */
    private static final String MANDATORY_FIELD_CAN_T_BE_NULL = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** string attribute for holding delete error message. */
    private static final String ERROR_MSG_ASSIGNMENT_DELETE = "REF.UI.ASSIGNMENT.DELETE";
    
    /** Model attribute of "showEditSection". */
    private static final String SHOW_EDIT_SECTION = "showEditSection";
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";

    /** CommonService attribute for holding commonService. */
    private CommonService commonService;

    /** The student service. */
    private StudentService studentService;

    /**
     * AssignmentValidator attribute for holding AssignmentValidator.
     */
    private AssignmentValidator assignmentValidator;

    /**
     * Sets the AssignmentValidator object.
     *
     * @param assignmentValidatorVal set AssignmentValidator object.
     */
    public void setAssignmentValidator(AssignmentValidator assignmentValidatorVal) {

        this.assignmentValidator = assignmentValidatorVal;
    }

    /**
     * setter method from CommonService.
     *
     * @param commonServiceVal - CommonService
     */

    public void setCommonService(CommonService commonServiceVal) {

        this.commonService = commonServiceVal;
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
     * @return the subject list
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(MODEL_ATT_SUBJECTLIST)
    public List<GradeSubject> populateSubjectList() throws AkuraAppException {

        return SortUtil.sortGradeSubjectList(commonService.getGradeSubjectList());
    }

    /**
     * intiBinder method is to bind date class with the date format.
     *
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFALUT_DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

    }

    /**
     * Method is to return assignment reference data.
     *
     * @throws AkuraAppException - AkuraAppException
     * @return assignmentList - assignment reference list.
     */
    @ModelAttribute(MODEL_ATT_SUBJECT_ASSIGNMENT_LIST)
    public final List<Assignment> populateAssignmentList() throws AkuraAppException {

        return SortUtil.sortAssignmenttList(commonService.getAssignmentList());
    }

    /**
     * Initializes the reference data -which are related to the Assignment, that is to be previewed on the UI.
     *
     * @param model - a HashMap that contains information of the assignment.
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(REQ_MAP_VALUE_ADD)
    public final String addAssignment(final ModelMap model) throws AkuraAppException {

        Assignment assignment = new Assignment();
        model.addAttribute(MODEL_ATT_ASSIGNMENT, assignment);
        return VIEW_GET_MANAGE_ADD_ASSIGNMENT;
    }

    /**
     * @return the Grade list.
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(MODEL_ATT_GRADELIST)
    public List<Grade> populateGradeList() throws AkuraAppException {

        return SortUtil.sortGradeList(commonService.getGradeList());
    }

    /**
     * Method is to return list of subjects which are related to the Grade.
     *
     * @return a map which has key - grade and value - list of grade subjects.
     * @param request of type HttpServletRequest
     * @param modelMap - a ModelMap contains the subjects.
     * @throws AkuraAppException - Details exception
     */
    @RequestMapping(value = GRADE_SUBJECT_HTM)
    @ResponseBody
    public String populateGradeSubject(ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {

        StringBuilder allGradeSubject = new StringBuilder();
        int gradeId = Integer.parseInt(request.getParameter(SELECTED_VALUE));

        List<GradeSubject> subjectGrades = commonService.getGradeSubjectIdListByGrade(gradeId);

        boolean isFirst = true;

        StringBuilder subjects = new StringBuilder();

        for (GradeSubject subjectGrade : subjectGrades) {
            subjects.append(subjectGrade.getSubject().getDescription());
            subjects.append(AkuraWebConstant.UNDERSCORE_STRING);
            subjects.append(subjectGrade.getGradeSubjectId());

            if (isFirst) {
                allGradeSubject.append(subjects.toString()); // no comma
                isFirst = false;
            } else {
                allGradeSubject.append(AkuraWebConstant.STRING_COMMA); // comma
                allGradeSubject.append(subjects.toString());
            }
            subjects.delete(0, subjects.length());
        }
        return allGradeSubject.toString();
    }

    /**
     * Saves or updates the relevant Assignment.
     *
     * @param model - a HashMap that contains information of the Assignment
     * @param assignment - an instance of Assignment
     * @param result - containing list of errors and assignment instance to which data was bound
     * @param request of type HttpServletRequest
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when saving or updating a Assignment
     *         instance.
     * @throws ParseException
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public final String saveOrUpdateAssignment(@ModelAttribute(MODEL_ATT_ASSIGNMENT) Assignment assignment,
            BindingResult result, final ModelMap model, HttpServletRequest request) throws AkuraAppException {

        String view = AkuraWebConstant.EMPTY_STRING;
        
        Assignment selectedObj = null;
        // call validate method , to be validated assignment
        assignmentValidator.validate(assignment, result);
        if (result.hasErrors()) {
            if(assignment.getAssignmentId()!=0){
                selectedObj = commonService.findAssignmentById(assignment.getAssignmentId());
                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
            }
            model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
            view = VIEW_GET_MANAGE_ADD_ASSIGNMENT;
            
        } else {

            if (request.getParameter(REQ_ATT_SELECT_SUBJECTS).equals(UNDEFINED)) {
                String message = new ErrorMsgLoader().getErrorMessage(MANDATORY_FIELD_CAN_T_BE_NULL);
                model.addAttribute(MESSAGE, message);
                selectedObj = commonService.findAssignmentById(assignment.getAssignmentId());
                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                return VIEW_GET_MANAGE_ADD_ASSIGNMENT;
            }

            if (request.getParameter(REQ_ATT_SELECT_SUBJECTS).equals(AkuraWebConstant.STRING_ZERO)) {

                String message = new ErrorMsgLoader().getErrorMessage(MANDATORY_FIELD_CAN_T_BE_NULL);
                model.addAttribute(MESSAGE, message);
                selectedObj = commonService.findAssignmentById(assignment.getAssignmentId());
                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                view = VIEW_GET_MANAGE_ADD_ASSIGNMENT;
            } else {

                // get grade subject id by selection of grade - > subject
                int gradeSubjectId = Integer.parseInt(request.getParameter(REQ_ATT_SELECT_SUBJECTS));

                // get grade-subject object , according to selected grade + subject
                GradeSubject gradeSubject = commonService.findGradeSubject(gradeSubjectId);

                // get the assignment id - it is generated auto on DB table - Assignment
                int id = assignment.getAssignmentId();

                // set the grade subject object for assignment object
                assignment.setGradeSubject(gradeSubject);

                try {
                    // get year from assignment submitted date.
                    int year = DateUtil.getYearFromDate(assignment.getDate());

                    // get assignment mark type
                    boolean marksType = assignment.getIsMarks();

                    // check grade subject , assignment name is already added(to be unique
                    // grade,subject,assignment)
                    boolean isExistAssignment =
                            commonService.isExistAssignmentGradeSubject(gradeSubjectId, assignment.getName(), year,
                                    marksType);

                    // pass assignment id ,and check if there is a reference for it
                    boolean isExistMarks = studentService.isExistAssignmentMarks(id);

                    Assignment a = commonService.findAssignmentById(id);

                    if (id > 0) {
                        String assName = a.getName();
                        String assDes = a.getDescription();
                        String assDate = DateUtil.getFormatDate(assignment.getDate());
                        String assGradeDes = a.getGradeSubject().getGrade().getDescription();
                        String assSubjectDes = a.getGradeSubject().getSubject().getDescription();

                        if (assName.equals(assignment.getName()) && assDes.equals(assignment.getDescription())
                                && DateUtil.getFormatDate(a.getDate()).equals(assDate)
                                && assGradeDes.equals(assignment.getGradeSubject().getGrade().getDescription())
                                && assSubjectDes.equals(assignment.getGradeSubject().getSubject().getDescription())
                                && a.getIsMarks() == assignment.getIsMarks()) {

                            model.addAttribute(MODEL_ATT_ASSIGNMENT, a);
                            model.addAttribute(MESSAGE, "");
                            selectedObj = commonService.findAssignmentById(assignment.getAssignmentId());
                            model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                            model.addAttribute(MODEL_ATT_SELECTED_OBJECT, assignment);
                            model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                            return VIEW_GET_MANAGE_ADD_ASSIGNMENT;

                        }
                    }

                    // check assignment record has been added already
                    if (id > 0 && !isExistMarks) {

                        // update assignment
                        commonService.updateAssignment(assignment);

                    } else {
                        if (!isExistAssignment && !isExistMarks) {
                            // to be added(save) the assignment
                            commonService.addAssignment(assignment);
                        } else { 
                            String message=null;
                            
                            if(id >0){// when edit record
                                message = new ErrorMsgLoader().getErrorMessage(CANNOT_UPDATED);
                                selectedObj = commonService.findAssignmentById(assignment.getAssignmentId());
                                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                                model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                            }else { // when add new record
                                message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_ASSIGNMENT_DESCRIPTION);
                                selectedObj = commonService.findAssignmentById(assignment.getAssignmentId());
                                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                                model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                            }
                            
                            model.addAttribute(MESSAGE, message);
                            selectedObj = commonService.findAssignmentById(assignment.getAssignmentId());
                            model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                            model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                            return VIEW_GET_MANAGE_ADD_ASSIGNMENT;
                        }
                    }

                    view = VIEW_POST_MANAGE_SUBJECT_ASSIGNMENT;

                } catch (AkuraAppException e) {
                    // check , if there are violations of add/update record, when they have references.
                    if (e.getCause() instanceof DataIntegrityViolationException) {
                        assignmentMessages(model);
                        selectedObj = commonService.findAssignmentById(assignment.getAssignmentId());
                        model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                        model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                        view = VIEW_GET_MANAGE_ADD_ASSIGNMENT;
                    } else {
                        throw e;
                    }
                }
            }

        }
        return view;
    }

    /**
     * Deletes the relevant Assignment object.
     *
     * @param model - a HashMap that contains information of the Assignment
     * @param assignment - an instance of Assignment
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a Assignment instance.
     */
    @RequestMapping(REQ_MAP_VALUE_DELETE)
    public final String deleteAssignmentType(final ModelMap model,
            @ModelAttribute(MODEL_ATT_ASSIGNMENT) final Assignment assignment) throws AkuraAppException {

        String message;
        try {
            // get the assignment id its, primary key
            int id = assignment.getAssignmentId();

            // find assignment object by giving its, primary key
            Assignment findAssignment = commonService.findAssignmentById(id);
            // call delete method to be removed assignment object
            commonService.deleteAssignment(findAssignment);

            return VIEW_POST_MANAGE_SUBJECT_ASSIGNMENT;
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_ASSIGNMENT_DELETE);

                // create new Assignment object
                Assignment newAssignment = new Assignment();
                model.addAttribute(MESSAGE, message);
                model.addAttribute(MODEL_ATT_ASSIGNMENT, newAssignment);
                return VIEW_GET_MANAGE_ADD_ASSIGNMENT;
            } else {
                throw e;
            }
        }
    }

    /**
     * assignmentMessages method to be kept error messages in model attribute.
     *
     * @param model - a HashMap that contains information of the Assignment
     */
    private void assignmentMessages(final ModelMap model) {

        String message;
        message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_ASSIGNMENT_DESCRIPTION);
        Assignment newAssignment = new Assignment();
        model.addAttribute(MODEL_ATT_ASSIGNMENT, newAssignment);
        model.addAttribute(MODEL_ATT_SELECTED_OBJECT, newAssignment);
        model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
        model.addAttribute(MESSAGE, message);

    }

}
