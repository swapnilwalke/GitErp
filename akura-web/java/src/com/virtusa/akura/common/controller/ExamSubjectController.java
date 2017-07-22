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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.virtusa.akura.api.dto.Exam;
import com.virtusa.akura.api.dto.ExamSubject;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.ExamSubjectValidator;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.StaticDataUtil;

/**
 * this class , assign subjects for a selected exam, and provide manage, save or update and delete
 * functionalities.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ExamSubjectController {
    
	/** Represents the parameter name of the edit. */
    private static final String EDIT = "edit";
    
    /** Represents the parameter name of the save. */
    private static final String SAVE = "save";
    
    /** Represents the parameter name of the exam subject. */
    private static final String EXAM_SUBJECT = "examSubject";
    
    /** Represents the error message for field when already exists. */
    private static final String FIND_SUBJECTS_BY_EXAM_MAPPING = "findSubjectsByExam.htm";
    
    /** Represents the error message for field exists. */
    private static final String ERROR_MESSAGE_EXAM_SUBJECT_EXIST = "REF.UI.EXAMSUBJECT.EXIST";
    
    /** Manages ExamSubject data. */
    private static final String VIEW_POST_MANAGE_EXAM_SUBJECT = "redirect:manageExamSubject.htm";
    
    /** Represents the hyphen. */
    private static final String STRING_HIPHEN = "-";
    
    /** Represents the true value for the add model attribute. */
    private static final String TRUE = "true";

    /** Represents the key for the add model attribute. */
	private static final String ADD = "add";

	/** Represents the mapping request for the add function. */
	private static final String ADD_STATE = "/addState";
    
    /** Represents the error message for field required. */
    private static final String ERROR_MESSAGE_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";
    
    /** Represents the model attribute for holding error message. */
    private static final String VAR_MESSAGE = "message";
    
    /** Represents the path of the exam subject view. */
    private static final String VIEW_GET_MANAGE_EXAM_SUBJECT = "reference/manageExamSubject";
    
    /** Represents the model attribute of subject list size. */
    private static final String MODEL_ATT_SUBJECT_LIST_SIZE = "subjectListSize";
    
    /** Represents the model attribute of exam subject. */
    private static final String MODEL_ATT_EXAM_SUBJECT = EXAM_SUBJECT;
    
    /** Represents the model attribute of exam list. */
    private static final String MODEL_ATT_EXAM_LIST = "examList";
    
    /** attribute for holding selectedValue. */
    private static final String SELECTED_VALUE = "selectedValue";
    
    /** Represents model attribute of exam subject. */
    private static final String MODEL_ATT_EXAM_SUBJECTS = "examSubjects";
    
    /** Represents the request mapping value for manage exam subject. */
    private static final String REQ_MAP_MANAGE_EXAM_SUBJECT = "/manageExamSubject.htm";
    
    /** Represents the request mapping value for manage exam subject. */
    private static final String REQ_MAP_UPDATE_EXAM_SUBJECT = "/saveOrUpdateExamSubjects.htm";
    
    /** Represents the request request mapping value for delete exam subject. */
    private static final String REQ_MAP_DELETE_EXAM_SUBJECT = "/deleteExamSubject.htm";
    
    /** Represents the Constant EMPTY. */
    private static final String EMPTY = "";
    
    /** Represents the Constant OPTIONAL_SUBJECTS. */
    private static final String OPTIONAL_SUBJECTS = "optionalSubjects";
    
    /** Represents the Constant COMMA. */
    private static final String COMMA = ",";
    
    /** Represents the Constant ALL_SUBJECT_IDS. */
    private static final String ALL_SUBJECT_IDS = "allSubjectIds";
    
    /** Represents the request mapping for update of the exam subject. */
    private static final String EDIT_EXAM_SUBJECTS_HTM = "editExamSubject.htm";
    
    /** The Constant AVAILABLE_SUBJECTS_LIST. */
    private static final String AVAILABLE_SUBJECTS_LIST = "availableSubjectsList";
    
    /** The Constant SELECTED_SUBJECTS_LIST. */
    private static final String SELECTED_SUBJECTS_LIST = "selectedSubjectsList";
    
    /** The Constant SELECTED_DESCRIPTION. */
    private static final String SELECTED_DESCRIPTION = "selectedDescription";
    
    /** Represents the request attribute name for exam description. */
    private static final String EXAM_DESCRIPTION = "examDescription";
    
    /** error message for field delete. */
    private static final String ERROR_MESSAGE_SUBJECT_EXAM_DELETE = "REF.UI.EXAMSUBJECT.DELETE";
    
    /** Represents the error message for already exist. */
    private static final String EXAM_SUBJECT_EXIST = "EXAM.SUBJECT.EXIST";
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** model attribute of selected Grade. */
    private static final String MODEL_ATT_SELECTED_EXAM_ID = "selectedExamId";
    
    /** The Constant ALL_SUBJECT_NAMES. */
    private static final String ALL_SUBJECT_NAMES = "allSubjectNames";
    
    /** The constant . */
    private static final String OPTIONAL_SUDJECT_IDS = "optionalSudjectIds";
    
    /** Represents an instance of the CommonService. */
    private transient CommonService commonService;
    
    /** Represents an instance of the GradeSubjectValidator. */
    private transient ExamSubjectValidator examSubjectValidator;
    
    /**
     * Setter method for the ExamSubjectValidator.
     * 
     * @param objExamSubjectValidator - the instance of the ExamSubjectValidator.
     */
    public final void setExamSubjectValidator(final ExamSubjectValidator objExamSubjectValidator) {
    
        examSubjectValidator = objExamSubjectValidator;
    }
    
    /**
     * Sets the common service.
     * 
     * @param objCommonService - an instance of CommonService.
     */
    public void setCommonService(final CommonService objCommonService) {
    
        this.commonService = objCommonService;
    }
    
    /**
     * Returns a list of Exams.
     * 
     * @return examList - a list of exams.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(MODEL_ATT_EXAM_LIST)
    public List<Exam> populateExamsList() throws AkuraAppException {
    
        return SortUtil.sortExamList(commonService.getExamList());
    }
    
    /**
     * Returns a list of Subjects.
     * 
     * @param model - a HashMap that contains information of the subject size
     * @param request - a request scope data
     * @return subjectList - a list of Subjects.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ResponseBody
    @RequestMapping(FIND_SUBJECTS_BY_EXAM_MAPPING)
    public String findSubjectsByExam(ModelMap model, final HttpServletRequest request) throws AkuraAppException {
    
        StringBuilder allClasses = null;
        String selectedValue = request.getParameter(SELECTED_VALUE);
        if (!AkuraWebConstant.STRING_ZERO.equals(selectedValue)) {
            final int examId = Integer.parseInt(selectedValue);
            Exam exam = commonService.findExamById(examId);
            List<?> subjectsListByExam = commonService.getSubjectListByExam(exam.getGradeId());
            Iterator<?> iterator = subjectsListByExam.iterator();
            int count = 0;
            int indexSubjectId = 1;
            boolean isFirst = true;
            allClasses = new StringBuilder();
            StringBuilder classes = new StringBuilder();
            while (iterator.hasNext()) {
                Object[] object = (Object[]) iterator.next();
                classes.append((String) object[0]);
                classes.append(STRING_HIPHEN);
                classes.append((Integer) object[indexSubjectId]);
                isFirst = StaticDataUtil.appendValues(allClasses, isFirst, classes, AkuraWebConstant.STRING_COMMA);
                count++;
            }
            model.addAttribute(MODEL_ATT_SUBJECT_LIST_SIZE, count);
        }
        return allClasses != null ? allClasses.toString() : null;
    }
    
    /**
     * Returns a map of subjects for a relevant exam.
     * 
     * @return examSubjects - a map with subject for the relevant exam.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(MODEL_ATT_EXAM_SUBJECTS)
    public Map<String, List<?>> populateExamSubjectsList() throws AkuraAppException {
    
        List<ExamSubject> examSubjectList = commonService.getAllExamSubjectList();
        List<Integer> idList = new ArrayList<Integer>();
        Map<String, List<?>> examSubjects = new TreeMap<String, List<?>>();
        
        for (ExamSubject examSubject : examSubjectList) {
            final String subjectDescription = examSubject.getExam().getDescription();
            if (!idList.contains(examSubject.getExam().getExamId())) {
                List<?> subjectsList = commonService.findSubjectsByExam(subjectDescription);
                examSubjects.put(subjectDescription + AkuraWebConstant.STRING_COMMA + examSubject.getExamSubjectId(),
                        subjectsList);
                idList.add(examSubject.getExam().getExamId());
            }
        }
        return examSubjects;
    }
    
    /**
     * Initializes the reference data that is to be used on the view.
     * 
     * @param model - a HashMap that contains information of the ExamSubject object
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(REQ_MAP_MANAGE_EXAM_SUBJECT)
    public String manageExamSubject(ModelMap model) throws AkuraAppException {
    	
        model.addAttribute(MODEL_ATT_EXAM_SUBJECT, new ExamSubject());
        return VIEW_GET_MANAGE_EXAM_SUBJECT;
    }
    
    /**
     * Sets an attribute for the add function to display the panel.
     * 
     * @param model - a HashMap that contains information of the ExamSubject object
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(ADD_STATE)
    public String addState(ModelMap model) throws AkuraAppException {
    	model.addAttribute(ADD , TRUE);
        model.addAttribute(MODEL_ATT_EXAM_SUBJECT, new ExamSubject());
        return VIEW_GET_MANAGE_EXAM_SUBJECT;
    }
    
    /**
     * Updates a relevant object of exam subject.
     * 
     * @param model - a hashMap contains the examSubject related data.
     * @param examSubject - the exam subject.
     * @param result - the error results.
     * @param request - an instance of HttpServletRequest.
     * @return - the view to be redirected.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(EDIT_EXAM_SUBJECTS_HTM)
    public String editExamSubject(final ModelMap model,
            @ModelAttribute(MODEL_ATT_EXAM_SUBJECT) final ExamSubject examSubject, final BindingResult result,
            HttpServletRequest request) throws AkuraAppException {
    
        String examDescription = request.getParameter(EXAM_DESCRIPTION);
        Exam exam = commonService.getExamByExamName(examDescription);
        List<ExamSubject> selectedSubjectsList = commonService.findSubjectsByExam(examDescription);
        List<Integer> selectedSubjectsIdList = new ArrayList<Integer>();
        
        List<String> allClasses = new ArrayList<String>();
        boolean isFirst = true;
        StringBuilder classes = new StringBuilder();
        for (ExamSubject examSubj : selectedSubjectsList) {
            selectedSubjectsIdList.add(examSubj.getSubject().getSubjectId());
        }
        List<?> subjectsListByExam = commonService.getSubjectListByExam(exam.getGradeId());
        Iterator<?> iterator = subjectsListByExam.iterator();
        int indexSubjectId = 1;
        while (iterator.hasNext()) {
            Object[] object = (Object[]) iterator.next();
            int subjectId = (Integer) object[indexSubjectId];
            if (!selectedSubjectsIdList.contains(subjectId)) {
                classes.append((String) object[0]);
                classes.append(STRING_HIPHEN);
                classes.append(subjectId);
                if (isFirst) {
                    allClasses.add(classes.toString()); // no comma
                    isFirst = false;
                } else {
                    allClasses.add(classes.toString());
                }
                classes.delete(0, classes.length());
            }
        }
        
        model.addAttribute(SELECTED_DESCRIPTION, examDescription);
        model.addAttribute(SELECTED_SUBJECTS_LIST, selectedSubjectsList);
        model.addAttribute(AVAILABLE_SUBJECTS_LIST, allClasses);
        return VIEW_GET_MANAGE_EXAM_SUBJECT;
    }
    
    /**
     * Saves or updates exam subjects.
     * 
     * @param model - a hash map contains the properties of exam subject.
     * @param examSubject an instance of exam subject.
     * @param result - the result of the exam subject.
     * @param request - an instance of HttpServletRequest scope.
     * @return the - the view to be redirected.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(REQ_MAP_UPDATE_EXAM_SUBJECT)
    public String saveOrUpdateExamSubjects(final ModelMap model,
            @ModelAttribute(MODEL_ATT_EXAM_SUBJECT) final ExamSubject examSubject, BindingResult result,
            HttpServletRequest request) throws AkuraAppException {
    
        String resultView = VIEW_POST_MANAGE_EXAM_SUBJECT;
        examSubjectValidator.validate(examSubject, result);
        
        String examDescription = request.getParameter(EXAM_DESCRIPTION);
        ExamSubject selectedObj = null;
        if (result.hasErrors()) {
            if (examSubject.getExamSubjectId() != 0) {
                
                selectedObj = commonService.findExamSubject(examSubject.getExamSubjectId());
                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                
            }
            model.addAttribute(MODEL_ATT_SELECTED_EXAM_ID, examSubject.getExam().getExamId());
            request.setAttribute(ALL_SUBJECT_IDS, (String[]) request.getParameter(ALL_SUBJECT_IDS).split(COMMA));
            request.setAttribute(ALL_SUBJECT_NAMES, (String[]) request.getParameter(ALL_SUBJECT_NAMES).split(COMMA));
            request.setAttribute(OPTIONAL_SUDJECT_IDS,
                    (String[]) request.getParameter(OPTIONAL_SUDJECT_IDS).split(COMMA));
            model.addAttribute(SELECTED_DESCRIPTION, examDescription);
            resultView = VIEW_GET_MANAGE_EXAM_SUBJECT;
        } else {
            
            String selectedSubjects = request.getParameter(ALL_SUBJECT_IDS);
            int examId = examSubject.getExam().getExamId();
            if ((selectedSubjects == null || selectedSubjects.isEmpty()) && examId > 0) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_MANDATORY_FIELD_REQUIRED);
                model.addAttribute(VAR_MESSAGE, message);
                selectedObj = commonService.findExamSubject(examSubject.getExamSubjectId());
                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                model.addAttribute(SELECTED_DESCRIPTION, examDescription);
                model.addAttribute(MODEL_ATT_SELECTED_EXAM_ID, examSubject.getExam().getExamId());
                request.setAttribute(ALL_SUBJECT_IDS, (String[]) request.getParameter(ALL_SUBJECT_IDS).split(COMMA));
                request.setAttribute(ALL_SUBJECT_NAMES, (String[]) 
                        request.getParameter(ALL_SUBJECT_NAMES).split(COMMA));
                request.setAttribute(OPTIONAL_SUDJECT_IDS,
                        (String[]) request.getParameter(OPTIONAL_SUDJECT_IDS).split(COMMA));
                resultView = VIEW_GET_MANAGE_EXAM_SUBJECT;
            } else {
                try {
                    boolean isExist = false;
                    String saveOrEdit = (String) request.getParameter(EXAM_SUBJECT);
                    if (SAVE.equals(saveOrEdit)) {
                        isExist = commonService.isAlreadyExistExamSubject(examId);
                    }
                    if (isExist) {
                        String message = new ErrorMsgLoader().getErrorMessage(EXAM_SUBJECT_EXIST);
                        model.addAttribute(VAR_MESSAGE, message);
                        selectedObj = commonService.findExamSubject(examSubject.getExamSubjectId());
                        model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                        model.addAttribute(MODEL_ATT_SELECTED_EXAM_ID, examSubject.getExam().getExamId());
                        request.setAttribute(ALL_SUBJECT_IDS,
                                (String[]) request.getParameter(ALL_SUBJECT_IDS).split(COMMA));
                        request.setAttribute(ALL_SUBJECT_NAMES, (String[]) request.getParameter(ALL_SUBJECT_NAMES)
                                .split(COMMA));
                        request.setAttribute(OPTIONAL_SUDJECT_IDS, (String[]) request
                                .getParameter(OPTIONAL_SUDJECT_IDS).split(COMMA));
                        resultView = VIEW_GET_MANAGE_EXAM_SUBJECT;
                        isExist = false;
                    } else {
                        if (!isExist || EDIT.equals(saveOrEdit)) {
                            Exam exam = commonService.findExamById(examSubject.getExam().getExamId());
                            examSubject.setExam(exam);
                            String[] allSubjectIds = selectedSubjects.split(COMMA);
                            String[] optionalSubjectIds = request.getParameterValues(OPTIONAL_SUBJECTS);
                            
                            if (AkuraConstant.EMPTY_STRING.equals(examDescription.trim())) {
                                for (String strSubjectId : allSubjectIds) {
                                    handleSaveExamSubject(examSubject, optionalSubjectIds, 
                                            strSubjectId, model, request);
                                }
                            } else {
                                List<ExamSubject> selectedSubjectsList =
                                        commonService.findSubjectsByExam(examDescription);
                                if (!selectedSubjectsList.isEmpty()) {
                                    
                                    // Already assign primary key of the subject for selected exam.
                                    String[] oldAllSubjectIds = new String[selectedSubjectsList.size()];
                                    String[] oldOptionalSubjectIds = new String[selectedSubjectsList.size()];
                                    
                                    int i = 0;
                                    for (ExamSubject oldGradeSubject : selectedSubjectsList) {
                                        oldAllSubjectIds[i] =
                                                oldGradeSubject.getSubject().getSubjectId() + String.valueOf(EMPTY);
                                        
                                        if (oldGradeSubject.getOptionalSubject()) {
                                            oldOptionalSubjectIds[i] =
                                                    oldGradeSubject.getSubject().getSubjectId() + String.valueOf(EMPTY);
                                        }
                                        
                                        i++;
                                    }
                                    
                                    // Add additionally added exam subjects
                                    manageAddMoreExamSubjects(examSubject, allSubjectIds, optionalSubjectIds,
                                            oldAllSubjectIds, model, request);
                                    
                                    // Remove previously added exam subjects
                                    manageRemoveAddedExamSubjects(examDescription, allSubjectIds, oldAllSubjectIds);
                                    
                                    updateMainSubjectAsOptional(examDescription, optionalSubjectIds,
                                            oldOptionalSubjectIds);
                                    
                                    updateOptionalSubjectAsMain(examDescription, optionalSubjectIds,
                                            oldOptionalSubjectIds);
                                }
                            }
                        }
                    }
                } catch (AkuraAppException e) {
                    if (e.getCause() instanceof DataIntegrityViolationException) {
                        checkExistsExamSubject(model);
                        selectedObj = commonService.findExamSubject(examSubject.getExamSubjectId());
                        model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                        model.addAttribute(MODEL_ATT_SELECTED_EXAM_ID, examSubject.getExam().getExamId());
                        model.addAttribute(SELECTED_DESCRIPTION, examDescription);
                        request.setAttribute(ALL_SUBJECT_IDS,
                                (String[]) request.getParameter(ALL_SUBJECT_IDS).split(COMMA));
                        request.setAttribute(ALL_SUBJECT_NAMES, (String[]) request.getParameter(ALL_SUBJECT_NAMES)
                                .split(COMMA));
                        request.setAttribute(OPTIONAL_SUDJECT_IDS, (String[]) request
                                .getParameter(OPTIONAL_SUDJECT_IDS).split(COMMA));
                        resultView = VIEW_GET_MANAGE_EXAM_SUBJECT;
                    } else {
                        throw e;
                    }
                }
                
            }
            
        }
        return resultView;
    }
    
    /**
     * Updates original subject for a given exam subject.
     * 
     * @param examDescription - the description of exam
     * @param optionalSubjectIds - a list containing the primary keys of original subjects for a given exam.
     * @param oldOptionalSubjectIds - the old list containing the primary keys of original subjects for a
     *        given exam.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void updateOptionalSubjectAsMain(final String examDescription, final String[] optionalSubjectIds,
            final String[] oldOptionalSubjectIds) throws AkuraAppException {
    
        List<?> toBeRemoveOptionalSubjects = new ArrayList();
        if (optionalSubjectIds != null) {
            toBeRemoveOptionalSubjects =
                    ListUtils.subtract(Arrays.asList(oldOptionalSubjectIds), Arrays.asList(optionalSubjectIds));
        } else if (oldOptionalSubjectIds != null) {
            toBeRemoveOptionalSubjects = Arrays.asList(oldOptionalSubjectIds);
        }
        if (!toBeRemoveOptionalSubjects.isEmpty()) {
            
            for (Object strSubjectId : toBeRemoveOptionalSubjects) {
                boolean isOptionalSubject = false;
                
                if (strSubjectId != null) {
                    handleUpdateExamSubject(examDescription, strSubjectId, isOptionalSubject);
                }
            }
        }
    }
    
    /**
     * Updates optional subject for a given exam subject.
     * 
     * @param examDescription - the description of exam
     * @param optionalSubjectIds - a list containing the primary keys of optional subjects for a given exam.
     * @param oldOptionalSubjectIds - the old list containing the primary keys of original subjects for a
     *        given exam.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void updateMainSubjectAsOptional(String examDescription, String[] optionalSubjectIds,
            String[] oldOptionalSubjectIds) throws AkuraAppException {
    
        if (optionalSubjectIds != null) {
            List<?> toBeAddOptionalSubjects =
                    ListUtils.subtract(Arrays.asList(optionalSubjectIds), Arrays.asList(oldOptionalSubjectIds));
            
            if (!toBeAddOptionalSubjects.isEmpty()) {
                
                for (Object strSubjectId : toBeAddOptionalSubjects) {
                    boolean isOptionalSubject = true;
                    
                    if (strSubjectId != null) {
                        handleUpdateExamSubject(examDescription, strSubjectId, isOptionalSubject);
                    }
                }
            }
        }
    }
    
    /**
     * Handle update exam subject.
     * 
     * @param examDescription - the description of selected exam.
     * @param strSubjectId - the selected primary key of subject for exam.
     * @param isOptionalSubject - the subject optional status.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void handleUpdateExamSubject(final String examDescription, final Object strSubjectId,
            final boolean isOptionalSubject) throws AkuraAppException {
    
        int subjectId = Integer.parseInt(strSubjectId + String.valueOf(EMPTY));
        Subject optionalSubject = commonService.findSubject(subjectId);
        List<ExamSubject> updateExamSubjectList =
                commonService.findExamSubjectByDes(examDescription, optionalSubject.getDescription());
        ExamSubject updateExamSubject = updateExamSubjectList.get(0);
        boolean isExist = commonService.isExistExamMarks(updateExamSubject.getExamSubjectId());
        if (!isExist) {
            if (updateExamSubjectList.size() > 0) {
                updateExamSubject.setOptionalSubject(isOptionalSubject);
                commonService.updateExamSubject(updateExamSubject);
            }
        }
    }
    
    /**
     * Manage remove added exam subjects.
     * 
     * @param examDescription - the exam description
     * @param allSubjectIds - the all subject keys
     * @param oldAllSubjectIds - the old all subject keys
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void manageRemoveAddedExamSubjects(String examDescription,
            String[] allSubjectIds, String[] oldAllSubjectIds)
            throws AkuraAppException {
    
        List<?> toBeRemoved = ListUtils.subtract(Arrays.asList(oldAllSubjectIds), Arrays.asList(allSubjectIds));
        
        if (!toBeRemoved.isEmpty()) {
            
            for (Object strSubjectId : toBeRemoved) {
                int subjectId = Integer.parseInt(strSubjectId + String.valueOf(EMPTY));
                Subject subject = commonService.findSubject(subjectId);
                List<ExamSubject> deleteExamSubject =
                        commonService.findExamSubjectByDes(examDescription, subject.getDescription());
                commonService.deleteExamSubjectList(deleteExamSubject);
            }
        }
    }
    
    /**
     * Manage add more exam subjects.
     * 
     * @param examSubject - an instance of ExamSubject.
     * @param allSubjectIds - an list of primary keys of subjects for a given examSubject.
     * @param optionalSubjectIds - an list of primary keys of optional subjects for a given examSubject
     * @param oldAllSubjectIds - an list of primary keys of old subjects for a given examSubject
     * @param model - a hash map containing the exam subject related data.
     * @param request - an instance of HttpServletRequest
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void manageAddMoreExamSubjects(final ExamSubject examSubject, String[] allSubjectIds,
            String[] optionalSubjectIds, String[] oldAllSubjectIds, ModelMap model, final HttpServletRequest request)
            throws AkuraAppException {
    
        List<?> toBeAdded = ListUtils.subtract(Arrays.asList(allSubjectIds), Arrays.asList(oldAllSubjectIds));
        
        if (!toBeAdded.isEmpty()) {
            for (Object strSubjectId : toBeAdded) {
                handleSaveExamSubject(examSubject, optionalSubjectIds, strSubjectId + String.valueOf(EMPTY), model,
                        request);
            }
        }
    }
    
    /**
     * Saves examSubject objects if not already exists.
     * 
     * @param examSubject - an object of examSubject.
     * @param optionalSubjectIds - a list of primary keys of optional subjects.
     * @param strSubjectId - the primary key of the subject.
     * @param model - a hash map containing the exam subject related data.
     * @param request - an instance of HttpServletRequest.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void handleSaveExamSubject(final ExamSubject examSubject, String[] optionalSubjectIds,
            final String strSubjectId, final ModelMap model, HttpServletRequest request) throws AkuraAppException {
    
        boolean isOptionalSubject = false;
        
        if (optionalSubjectIds != null && Arrays.asList(optionalSubjectIds).contains(strSubjectId)) {
            isOptionalSubject = true;
        }
        
        examSubject.setOptionalSubject(isOptionalSubject);
        int subjectId = Integer.parseInt(strSubjectId);
        Subject subject = commonService.findSubject(subjectId);
        examSubject.setSubject(subject);
        commonService.addExamSubject(examSubject);
    }
    
    /**
     * Checks if there are exists exam-subjects.
     * 
     * @param model - a HashMap that contains information of the examSubject.
     */
    private void checkExistsExamSubject(final ModelMap model) {
    
        String message = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_EXAM_SUBJECT_EXIST);
        ExamSubject newExamSubject = new ExamSubject();
        model.addAttribute(MODEL_ATT_EXAM_SUBJECT, newExamSubject);
        model.addAttribute(VAR_MESSAGE, message);
    }
    
    /**
     * Deletes the relevant examSubject object.
     * 
     * @param request - an instance of request scope
     * @param model - a HashMap that contains information of the examSubject
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a examSubject instance.
     */
    @RequestMapping(REQ_MAP_DELETE_EXAM_SUBJECT)
    public String deleteExamSubject(HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        String redirectURL = VIEW_POST_MANAGE_EXAM_SUBJECT;
        
        try {
            Exam exam = commonService.getExamByExamName(String.valueOf(request.getParameter(EXAM_DESCRIPTION)));
            List<ExamSubject> examSubjectIdList = commonService.getExamSubjectIdListByExam(exam.getExamId());
            commonService.deleteExamSubjectList(examSubjectIdList);
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_SUBJECT_EXAM_DELETE);
                ExamSubject newExamSubject = new ExamSubject();
                model.addAttribute(MODEL_ATT_EXAM_SUBJECT, newExamSubject);
                model.addAttribute(VAR_MESSAGE, message);
                redirectURL = VIEW_GET_MANAGE_EXAM_SUBJECT;
            } else {
                throw e;
            }
        }
        return redirectURL;
    }
}
