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

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.Exam;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.ExamValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageExamsController is to handle Add/Edit/Delete/View operations for Manage Exams in reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageExamsController {
    
    /** Represents the message for the edition of Exam. */
    private static final String EDIT_MESSAGE = "EDIT.EXAM.MESSAGE";
    
    /** string variable for error message description. */
    private static final String ERROR_MSG_EXAM_DESCRIPTION = "REF.UI.EXAM.DESCRIPTION";
    
    /** string variable for error message delete. */
    private static final String ERROR_MSG_EXAM_DELETE = "REF.UI.EXAM.DELETE";
    
    /** view post method Exam. */
    private static final String VIEW_POST_MANAGE_EXAM = "redirect:manageExam.htm";
    
    /** view get method Exam. */
    private static final String VIEW_GET_EXAM = "reference/manageExams";
    
    /** request mapping value for save or update Exam. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/manageSaveOrUpdateExam.htm";
    
    /** model attribute of Exam. */
    private static final String MODEL_ATT_EXAM = "exam";
    
    /** model attribute of Exam list. */
    private static final String MODEL_ATT_EXAM_LIST = "examList";
    
    /** request mapping value for delete Exam. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteExam.htm";
    
    /** request mapping value for add Exam. */
    private static final String REQ_MAP_VALUE_ADD = "/manageExam.htm";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** model attribute for Grade list. */
    private static final String MODEL_ATT_GRADE_LIST = "gradeList";
    
    /** model attribute of selected Grade. */
    private static final String MODEL_ATT_SELECTED_GRADE = "selectedGradeId";
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** Model attribute of "showEditSection". */
    private static final String SHOW_EDIT_SECTION = "showEditSection";
    
    /**
     * Represents an instance of ExamValidator.
     */
    private ExamValidator examValidator;
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /**
     * setter method from CommonService.
     * 
     * @param commonServiceVal - CommonService
     */
    
    public void setCommonService(CommonService commonServiceVal) {
    
        this.commonService = commonServiceVal;
    }
    
    /**
     * Sets an instance of ExamValidator.
     * 
     * @param examValidatorVal - the relevant instance of ExamValidator
     */
    public void setExamValidator(ExamValidator examValidatorVal) {
    
        examValidator = examValidatorVal;
    }
    
    /**
     * Method is to return exam reference data.
     * 
     * @throws AkuraAppException - AkuraAppException
     * @return examList - exam reference list.
     */
    @ModelAttribute(MODEL_ATT_EXAM_LIST)
    public final List<Exam> populateExamList() throws AkuraAppException {
    
        return SortUtil.sortExamList(commonService.getExamList());
    }
    
    /**
     * Method is to return exam reference data.
     * 
     * @throws AkuraAppException - AkuraAppException
     * @return examList - exam reference list.
     */
    @ModelAttribute(MODEL_ATT_GRADE_LIST)
    public final List<Grade> populateGradeList() throws AkuraAppException {
    
        return SortUtil.sortGradeList(commonService.getGradeList());
    }
    
    /**
     * Initializes the reference data that is to be previewed on the UI.
     * 
     * @param model - a HashMap that contains information of the Exam.
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(REQ_MAP_VALUE_ADD)
    public final String addExam(final ModelMap model) throws AkuraAppException {
    
        Exam exam = new Exam();
        model.addAttribute(MODEL_ATT_EXAM, exam);
        return VIEW_GET_EXAM;
    }
    
    /**
     * Saves or updates the relevant Exam.
     * 
     * @param model - a HashMap that contains information of the Exam
     * @param exam - an instance of Exam
     * @param result - containing list of errors and exam instance to which data was bound
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when saving or updating a Exam
     *         instance.
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public final String saveOrUpdateExam(@ModelAttribute(MODEL_ATT_EXAM) final Exam exam, BindingResult result,
            final ModelMap model) throws AkuraAppException {
    
        String retrunString;
        Exam selectedObj = null;
        examValidator.validate(exam, result);
        
        if (result.hasErrors()) {
            if (exam.getExamId() != 0) {
                selectedObj = commonService.findExamById(exam.getExamId());
                
                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                
            }
            model.addAttribute(MODEL_ATT_SELECTED_GRADE, exam.getGradeId());
            model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
            retrunString = VIEW_GET_EXAM;
            
        } else {
            String description = exam.getDescription().trim();
            
            try {
                
                int examId = exam.getExamId();
                if (examId > 0) {
                    int examSubjectId = commonService.findExamSubjectByExamId(examId);
                    if (examSubjectId == 0) {
                        commonService.updateExam(exam);
                        retrunString = VIEW_POST_MANAGE_EXAM;
                    } else {
                        String message = new ErrorMsgLoader().getErrorMessage(EDIT_MESSAGE);
                        model.addAttribute(MESSAGE, message);
                        selectedObj = commonService.findExamById(exam.getExamId());
                        model.addAttribute(MODEL_ATT_SELECTED_GRADE, exam.getGradeId());
                        model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                        model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                        retrunString = VIEW_GET_EXAM;
                    }
                } else {
                    
                    exam.setDescription(description);
                    commonService.addExam(exam);
                    retrunString = VIEW_POST_MANAGE_EXAM;
                }
                
            } catch (AkuraAppException e) {
                
                if (e.getCause() instanceof DataIntegrityViolationException) {
                    examMessages(model);
                    selectedObj = commonService.findExamById(exam.getExamId());
                    model.addAttribute(MODEL_ATT_SELECTED_GRADE, exam.getGradeId());
                    model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                    model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                    retrunString = VIEW_GET_EXAM;
                } else {
                    throw e;
                }
            }
        }
        return retrunString;
    }
    
    /**
     * Deletes the relevant Exam object.
     * 
     * @param model - a HashMap that contains information of the Exam
     * @param exam - an instance of Exam
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when delete a Exam instance.
     */
    @RequestMapping(REQ_MAP_VALUE_DELETE)
    public final String deleteExam(final ModelMap model, @ModelAttribute(MODEL_ATT_EXAM) final Exam exam)
            throws AkuraAppException {
    
        try {
            Exam findExam = commonService.findExam(exam.getExamId());
            commonService.deleteExam(findExam);
            return VIEW_POST_MANAGE_EXAM;
        } catch (AkuraAppException e) {
            
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_EXAM_DELETE);
                model.addAttribute(MESSAGE, message);
                model.addAttribute(MODEL_ATT_EXAM, exam);
                return VIEW_GET_EXAM;
                
            } else {
                throw e;
            }
        }
    }
    
    /**
     * exam method to be kept error message in model attribute.
     * 
     * @param model - a HashMap that contains information of the Exam
     */
    private void examMessages(final ModelMap model) {
    
        String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_EXAM_DESCRIPTION);
        Exam newExam = new Exam();
        model.addAttribute(MODEL_ATT_EXAM, newExam);
        model.addAttribute(MODEL_ATT_SELECTED_OBJECT, newExam);
        model.addAttribute(MODEL_ATT_SELECTED_GRADE, newExam.getGradeId());
        model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
        model.addAttribute(MESSAGE, message);
    }
}
