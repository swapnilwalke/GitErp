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

package com.virtusa.akura.common.controller;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.GradeSubjectValidator;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;

/**
 * this class , assign subjects for selected grade, and provide manage, save or update and delete
 * functionalities.
 *
 * @author Virtusa Corporation
 */
@Controller
public class GradeSubjectController {

    /** String constant for hold request parameter `display_edit_panel`. */
    private static final String DISPLAY_EDIT_PANEL = "display_edit_panel";

    /** attribute for holding variable as dash. */
    private static final String VAR_DASH = "-";

    /** error message for field exists. */
    private static final String ERROR_MESSAGE_GRADESUBJECT_EXIST = "REF.UI.GRADESUBJECT.EXIST";

    /** view post method manage subject grade. */
    private static final String VIEW_POST_MANAGE_SUBJECT_GRADE = "redirect:manageSubjectGrade.htm";

    /** error message for field delete. */
    private static final String ERROR_MESSAGE_GRADESUBJECT_DELETE = "REF.UI.GRADESUBJECT.DELETE";

    /** error message for field required. */
    private static final String ERROR_MESSAGE_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** attribute for holding message. */
    private static final String VAR_MESSAGE = "message";

    /** view get method manage subject grade. */
    private static final String VIEW_GET_MANAGE_SUBJECT_GRADE = "reference/manageSubjectGrade";

    /** model attribute of subject list size. */
    private static final String MODEL_ATT_SUBJECT_LIST_SIZE = "subjectListSize";

    /** model attribute of subject list. */
    private static final String MODEL_ATT_SUBJECT_LIST = "subjectList";

    /** model attribute of grade subject. */
    private static final String MODEL_ATT_GRADE_SUBJECT = "gradeSubject";

    /** model attribute of grade list. */
    private static final String MODEL_ATT_GRADE_LIST = "gradeList";

    /** model attribute of grade subject. */
    private static final String MODEL_ATT_GRADE_SUBJECTS = "gradeSubjects";
    
    /** model attribute for selected grade id. */
    private static final String MODEL_ATT_SELECTED_GRADE_ID = "selectedGradeId";
    
    /** request mapping value for manage subject grade. */
    private static final String REQ_MAP_MANAGE_SUBJECT_GRADE = "/manageSubjectGrade.htm";

    /** request mapping value for manage subject grade. */
    private static final String REQ_MAP_UPDATE_GRADE_SUBJECT = "/saveOrUpdateGradeSubjects.htm";

    /** request mapping value for delete grade subject. */
    private static final String REQ_MAP_DELETE_GRADE_SUBJECT = "/deleteGradeSubject.htm";

    /** The Constant EMPTY. */
    private static final String EMPTY = "";

    /** The Constant OPTIONAL_SUBJECTS. */
    private static final String OPTIONAL_SUBJECTS = "optionalSubjects";

    /** The Constant COMMA. */
    private static final String COMMA = ",";

    /** The Constant ALL_SUBJECT_IDS. */
    private static final String ALL_SUBJECT_IDS = "allSubjectIds";

    /** The Constant ALL_SUBJECT_NAMES. */
    private static final String ALL_SUBJECT_NAMES = "allSubjectNames";

    /** The Constant EDIT_GRADE_SUBJECTS_HTM. */
    private static final String EDIT_GRADE_SUBJECTS_HTM = "editGradeSubjects.htm";

    /** The Constant AVAILABLE_SUBJECTS_LIST. */
    private static final String AVAILABLE_SUBJECTS_LIST = "availableSubjectsList";

    /** The Constant SELECTED_SUBJECTS_LIST. */
    private static final String SELECTED_SUBJECTS_LIST = "selectedSubjectsList";

    /** The Constant SELECTED_DESCRIPTION. */
    private static final String SELECTED_DESCRIPTION = "selectedDescription";

    /** The Constant GRADE_DESCRIPTION. */
    private static final String GRADE_DESCRIPTION = "gradeDescription";

    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_INVALID_MAXIMUM_MARK = "REF.UI.GRADE.CLASSES.MAXIMUM_MARK";
    
    /** Attribute for holding error message key. */
    private static final String ERROR_MSG_KEY_SUBJECTS_ALREADY_ASSIGNED = "REF.UI.GRADESUBJECT.SUBJECTS.ASSIGNED";

    /** The constant . */
    private static final String MAXIMUM_MARK = "maximumMarklist";

    /** The constant . */
    private static final String OPTIONAL_SUDJECT_IDS = "optionalSudjectIds";

    /** attribute for holding maximum mark. */
    public static final float MAX_MARK = 100;

    /** attribute for holding maximum mark. */
    public static final float MIN_MARK = 0;

    /** Represents the model name of message. */
    private static final String MESSAGE = "message";

    /** Holds the AkuraErrorMsg property file name. */
    private static final String AKURA_ERROR_MSG_PROPERTY = "AkuraErrorMsg";

    /** Holds the key for error message. */
    private static final String ERROR_MSG_GRADESUBJECT_DELETE_SUBJECTS = "REF.UI.GRADESUBJECT.DELETE.SUBJECTS";

    /**
     * Represents an instance of the CommonService.
     */
    private CommonService commonService;

    /**
     * Represents an instance of the GradeSubjectValidator.
     */
    private GradeSubjectValidator gradeSubjectValidator;

    /**
     * Setter method for the GradeSubjectValidator.
     *
     * @param gradeSubjectValidatorVal - the instance of the GradeSubjectValidator
     */
    public final void setGradeSubjectValidator(final GradeSubjectValidator gradeSubjectValidatorVal) {

        gradeSubjectValidator = gradeSubjectValidatorVal;
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
     * Returns a list of Grades.
     *
     * @return gradeList - a list of Grades.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(MODEL_ATT_GRADE_LIST)
    public List<Grade> populateGradeList() throws AkuraAppException {

        return SortUtil.sortGradeList(commonService.getGradeList());
    }

    /**
     * Returns a list of Subjects.
     *
     * @param model - a HashMap that contains information of the subject size
     * @return subjectList - a list of Subjects.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(MODEL_ATT_SUBJECT_LIST)
    public List<Subject> populateSubjectList(ModelMap model) throws AkuraAppException {

        List<Subject> subjectList = commonService.getSubjectList();
        model.addAttribute(MODEL_ATT_SUBJECT_LIST_SIZE, subjectList.size());
        return SortUtil.sortSubjectList(subjectList);
    }

    /**
     * Returns a map of subjects for a relevant grade.
     *
     * @return gradeSubjects - a map with subject for the relevant grades.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(MODEL_ATT_GRADE_SUBJECTS)
    public Map<String, List<?>> populateGradeSubjectsList() throws AkuraAppException {

        List<GradeSubject> gradeSubjectList = commonService.getGradeSubjectList();
        List<Integer> idList = new ArrayList<Integer>();
        Map<String, List<?>> gradeSubjects = new TreeMap<String, List<?>>();

        for (GradeSubject gradesSubject : gradeSubjectList) {

            if (!idList.contains(gradesSubject.getGrade().getGradeId())) {
                List<?> subjectsList = commonService.findSubjectsByGrade(gradesSubject.getGrade().getDescription());
                gradeSubjects.put(gradesSubject.getGrade().getDescription(), subjectsList);
            }
        }
        return gradeSubjects;
    }

    /**
     * Initializes the reference data that is to be used on the view.
     *
     * @param model - a HashMap that contains information of the GradeSubject object
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(REQ_MAP_MANAGE_SUBJECT_GRADE)
    public String manageSubjectGrade(ModelMap model) throws AkuraAppException {

        model.addAttribute(MODEL_ATT_GRADE_SUBJECT, new GradeSubject());
        return VIEW_GET_MANAGE_SUBJECT_GRADE;
    }

    /**
     * Edits the grade subject.
     *
     * @param model the model
     * @param gradeSubject the grade subject
     * @param result the result
     * @param request the request
     * @return the string
     * @throws AkuraAppException the akura app exception
     */
    @RequestMapping(EDIT_GRADE_SUBJECTS_HTM)
    public String editGradeSubject(final ModelMap model,
            @ModelAttribute(MODEL_ATT_GRADE_SUBJECT) final GradeSubject gradeSubject, BindingResult result,
            HttpServletRequest request) throws AkuraAppException {
        
            String gradeDescription = request.getParameter(GRADE_DESCRIPTION);
            List<GradeSubject> selectedSubjectsList = commonService.findSubjectsByGrade(gradeDescription);
            List<Integer> selectedSubjectsIdList = new ArrayList<Integer>();

            for (GradeSubject editGradeSubject : selectedSubjectsList) {
                selectedSubjectsIdList.add(editGradeSubject.getSubject().getSubjectId());
            }

            List<Subject> availableSubjectsList = commonService.getAvailableSubjectsList(selectedSubjectsIdList);

            model.addAttribute(SELECTED_DESCRIPTION, gradeDescription);
            model.addAttribute(SELECTED_SUBJECTS_LIST, selectedSubjectsList);
            model.addAttribute(AVAILABLE_SUBJECTS_LIST, availableSubjectsList);
            request.setAttribute(DISPLAY_EDIT_PANEL, true);

            return VIEW_GET_MANAGE_SUBJECT_GRADE;
        //}
        
        
    }

    /**
     * Save or update grade subjects.
     *
     * @param model the model
     * @param gradeSubject the grade subject
     * @param result the result
     * @param request the request
     * @return the string
     * @param errorMsgLoader - error massage
     * @throws AkuraException the akura app exception
     */
    @RequestMapping(REQ_MAP_UPDATE_GRADE_SUBJECT)
    public String saveOrUpdateGradeSubjects(final ModelMap model,
            @ModelAttribute(MODEL_ATT_GRADE_SUBJECT) final GradeSubject gradeSubject, BindingResult result,
            HttpServletRequest request, ErrorMsgLoader errorMsgLoader) throws AkuraException {

        String selectedSubjects = null;
        if (request.getParameter(ALL_SUBJECT_IDS) != null && !request.getParameter(ALL_SUBJECT_IDS).isEmpty()) {
            selectedSubjects = request.getParameter(ALL_SUBJECT_IDS);
        }

        gradeSubjectValidator.validate(gradeSubject, result);

        if (result.hasErrors()) {

            setRequestParameters(request);

            return VIEW_GET_MANAGE_SUBJECT_GRADE;
        } else {
            if (selectedSubjects == null) {

                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_MANDATORY_FIELD_REQUIRED);
                model.addAttribute(VAR_MESSAGE, message);
                request.setAttribute(DISPLAY_EDIT_PANEL, true);

                setRequestParameters(request);
                return VIEW_GET_MANAGE_SUBJECT_GRADE;

            } else {

                String maxM = request.getParameter(MAXIMUM_MARK);
                String[] maxArray = maxM.split(COMMA);
                for (String maximumMarks : maxArray) {
                    try {
                        int maxMa = Integer.parseInt(maximumMarks);
                        if (maxMa > MAX_MARK || maxMa < MIN_MARK) {
                            errorMsgLoader = new ErrorMsgLoader();
                            String message = errorMsgLoader.getErrorMessage(ERROR_MSG_KEY_INVALID_MAXIMUM_MARK);
                            model.addAttribute(AkuraConstant.MESSAGE, message);

                            setRequestParameters(request);
                            return VIEW_GET_MANAGE_SUBJECT_GRADE;
                        }
                    } catch (NumberFormatException e) {
                        model.addAttribute(MESSAGE, AkuraWebConstant.MISMATCH_ERROR_MARKS);
                        setRequestParameters(request);
                        return VIEW_GET_MANAGE_SUBJECT_GRADE;
                    }

                }

                String gradeDescription = request.getParameter(GRADE_DESCRIPTION);
                model.addAttribute(SELECTED_DESCRIPTION, gradeDescription);

                try {

                    Grade grade = commonService.findGradeById(gradeSubject.getGrade().getGradeId());
                    gradeSubject.setGrade(grade);
                    String[] allSubjectIds = selectedSubjects.split(COMMA);
                    String[] optionalSubjectIds = request.getParameterValues(OPTIONAL_SUBJECTS);

                    if (EMPTY.equals(gradeDescription.trim())) {
                    	
                    	// Check whether any subject already have assigned to the same grade.
                    	if (commonService.hasAlreadyAssignedSubjectsForGrade(grade.getDescription())) {
                    		
                    		errorMsgLoader = new ErrorMsgLoader();
                            String message = errorMsgLoader.getErrorMessage(ERROR_MSG_KEY_SUBJECTS_ALREADY_ASSIGNED);
                            model.addAttribute(AkuraConstant.MESSAGE, message);

                            model.addAttribute(MODEL_ATT_SELECTED_GRADE_ID, grade.getGradeId());
                            setRequestParameters(request);
                            return VIEW_GET_MANAGE_SUBJECT_GRADE;
                    		
                    	} else {
                    		
                    		for (int k = 0; k < maxArray.length; k++) {
                                for (int j = 0; j < allSubjectIds.length;) {
                                    handleSaveGradeSubject(
                                    		gradeSubject, optionalSubjectIds, allSubjectIds[k], maxArray[k]
                    				);
                                    break;
                                }
                            }
                    		
                    	}

                    } else {
                        List<GradeSubject> selectedSubjectsList = commonService.findSubjectsByGrade(gradeDescription);
                        if (!selectedSubjectsList.isEmpty()) {

                            // Already assign subject ids for selected grade
                            String[] oldAllSubjectIds = new String[selectedSubjectsList.size()];
                            String[] oldOptionalSubjectIds = new String[selectedSubjectsList.size()];

                            int i = 0;
                            for (GradeSubject oldGradeSubject : selectedSubjectsList) {
                                oldAllSubjectIds[i] =
                                        oldGradeSubject.getSubject().getSubjectId() + String.valueOf(EMPTY);

                                if (oldGradeSubject.getIsOptionalSubject()) {
                                    oldOptionalSubjectIds[i] =
                                            oldGradeSubject.getSubject().getSubjectId() + String.valueOf(EMPTY);
                                }

                                i++;
                            }

                            manageAddMoreGradeSubjects(gradeSubject, allSubjectIds, optionalSubjectIds,
                                    oldAllSubjectIds, maxArray);

                            // Remove previously added grade subjects
                            manageRemoveAddedGradeSubjects(gradeDescription, allSubjectIds, oldAllSubjectIds, request);

                            // edit maximum Marks
                            for (int k = 0; k < maxArray.length; k++) {
                                for (int j = 0; j < allSubjectIds.length;) {
                                    editMaximumMarks(gradeDescription, allSubjectIds[k], maxArray[k]);
                                    break;
                                }
                            }

                            updateMainSubjectAsOptional(gradeDescription, optionalSubjectIds, oldOptionalSubjectIds,
                                    allSubjectIds);

                            updateOptionalSubjectAsMain(gradeDescription, optionalSubjectIds, oldOptionalSubjectIds);
                        }
                    }

                } catch (AkuraAppException e) {

                    if (e.getCause() instanceof DataIntegrityViolationException) {
                        checkExistsGradeSubject(model, e);
                        setRequestParameters(request);
                        return VIEW_GET_MANAGE_SUBJECT_GRADE;
                    } else {
                        errorMsgLoader = new ErrorMsgLoader();
                        String message = errorMsgLoader.getErrorMessage(ERROR_MSG_KEY_INVALID_MAXIMUM_MARK);
                        model.addAttribute(AkuraConstant.MESSAGE, message);
                        setRequestParameters(request);
                        return VIEW_GET_MANAGE_SUBJECT_GRADE;

                    }

                }
                setRequestParameters(request);
                return VIEW_POST_MANAGE_SUBJECT_GRADE;
            }
        }
    }

    /**
     * set values into request.
     *
     * @param request - request.
     */
    private void setRequestParameters(HttpServletRequest request) {

        /* return open state data */
        request.setAttribute(ALL_SUBJECT_IDS, (String[]) request.getParameter(ALL_SUBJECT_IDS).split(","));
        request.setAttribute(ALL_SUBJECT_NAMES, (String[]) request.getParameter(ALL_SUBJECT_NAMES).split(","));
        request.setAttribute(MAXIMUM_MARK, (String[]) request.getParameter(MAXIMUM_MARK).split(","));
        request.setAttribute(OPTIONAL_SUDJECT_IDS, (String[]) request.getParameter(OPTIONAL_SUDJECT_IDS).split(","));
    }

    /**
     * Update optional subject as main.
     *
     * @param gradeDescription the grade description
     * @param optionalSubjectIds the optional subject ids
     * @param oldOptionalSubjectIds the old optional subject ids
     * @throws AkuraException - when occured the eceptioin
     */
    @SuppressWarnings("unchecked")
    private void updateOptionalSubjectAsMain(String gradeDescription, String[] optionalSubjectIds,
            String[] oldOptionalSubjectIds) throws AkuraException {

        List<String> toBeRemoveOptionalSubjects = new ArrayList<String>();
        if (optionalSubjectIds != null) {
            toBeRemoveOptionalSubjects =
                    ListUtils.subtract(Arrays.asList(oldOptionalSubjectIds), Arrays.asList(optionalSubjectIds));
        } else if (oldOptionalSubjectIds != null) {
            // oldOptionalSubjectIds != null && optionalSubjectIds == null
            toBeRemoveOptionalSubjects = Arrays.asList(oldOptionalSubjectIds);
        }
        if (!toBeRemoveOptionalSubjects.isEmpty()) {

            for (Object strSubjectId : toBeRemoveOptionalSubjects) {
                boolean isOptionalSubject = false;

                if (strSubjectId != null) {
                    handleUpdateGradeSubject(gradeDescription, strSubjectId, isOptionalSubject);
                }
            }
        }
    }

    /**
     * Update main subject as optional.
     *
     * @param gradeDescription the grade description
     * @param optionalSubjectIds the optional subject ids
     * @param oldOptionalSubjectIds the old optional subject ids
     * @param allSubjectIds - Subject ids
     * @throws AkuraException - when occured the Execption
     */
    @SuppressWarnings("unchecked")
    private void updateMainSubjectAsOptional(String gradeDescription, String[] optionalSubjectIds,
            String[] oldOptionalSubjectIds, String[] allSubjectIds) throws AkuraException {

        if (optionalSubjectIds != null) {

            List toBeAddOptionalSubjects =
                    ListUtils.subtract(Arrays.asList(optionalSubjectIds), Arrays.asList(oldOptionalSubjectIds));

            if (!toBeAddOptionalSubjects.isEmpty()) {

                for (Object strSubjectId : toBeAddOptionalSubjects) {
                    boolean isOptionalSubject = true;

                    if (strSubjectId != null) {
                        handleUpdateGradeSubject(gradeDescription, strSubjectId, isOptionalSubject);
                    }
                }
            }
        }
    }

    /**
     * Handle update grade subject.
     *
     * @param gradeDescription the grade description
     * @param strSubjectId the str subject id
     * @param isOptionalSubject the is optional subjects
     * @throws AkuraException - when occure the exception
     */
    private void handleUpdateGradeSubject(String gradeDescription, Object strSubjectId, boolean isOptionalSubject)
            throws AkuraException {

        int subjectId = Integer.parseInt(strSubjectId + String.valueOf(EMPTY));
        Subject optionalSubject = commonService.findSubject(subjectId);
        List<GradeSubject> updateGradeSubjectList1 =
                commonService.findGradeSubjectByDes(gradeDescription, optionalSubject.getDescription());

        if (updateGradeSubjectList1.size() > 0) {
            GradeSubject updateGradeSubject1 =
                    commonService.findGradeSubjectByDes(gradeDescription, optionalSubject.getDescription()).get(0);
            updateGradeSubject1.setIsOptionalSubject(isOptionalSubject);
            commonService.updateGradeSubject(updateGradeSubject1);

        }

    }

    /**
     * Manage remove added grade subjects.
     *
     * @param gradeDescription the grade description
     * @param allSubjectIds the all subject ids
     * @param oldAllSubjectIds the old all subject ids
     * @param request - request.
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    private void manageRemoveAddedGradeSubjects(String gradeDescription, String[] allSubjectIds,
            String[] oldAllSubjectIds, HttpServletRequest request) throws AkuraAppException {

        List toBeRemoved = ListUtils.subtract(Arrays.asList(oldAllSubjectIds), Arrays.asList(allSubjectIds));
        request.setAttribute(DISPLAY_EDIT_PANEL, Boolean.TRUE);

        if (!toBeRemoved.isEmpty()) {

            for (Object strSubjectId : toBeRemoved) {
                int subjectId = Integer.parseInt(strSubjectId + String.valueOf(EMPTY));
                Subject subject = commonService.findSubject(subjectId);
                List<GradeSubject> deleteGradeSubject =
                        commonService.findGradeSubjectByDes(gradeDescription, subject.getDescription());
                commonService.deleteGradeSubjectList(deleteGradeSubject);
            }
        }
    }

    /**
     * Manage add more grade subjects.
     *
     * @param gradeSubject the grade subject
     * @param allSubjectIds the all subject ids
     * @param optionalSubjectIds the optional subject ids
     * @param oldAllSubjectIds the old all subject ids
     * @param maxArray - Maximum Mark
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    private void manageAddMoreGradeSubjects(final GradeSubject gradeSubject, String[] allSubjectIds,
            String[] optionalSubjectIds, String[] oldAllSubjectIds, String[] maxArray) throws AkuraAppException {

        List<String> toBeAdded = ListUtils.subtract(Arrays.asList(allSubjectIds), Arrays.asList(oldAllSubjectIds));

        int listToBeAddedSize = toBeAdded.size();
        int allSubjectListSize = allSubjectIds.length - 1;
        List<String> maxMarkArray =
                Arrays.asList(maxArray).subList(allSubjectListSize - listToBeAddedSize, allSubjectListSize);

        if (!toBeAdded.isEmpty()) {

            for (String strSubjectId : toBeAdded) {
                int count = 0;
                handleSaveGradeSubject(gradeSubject, optionalSubjectIds, strSubjectId, maxMarkArray.get(count));
                count++;
            }
        }

    }

    /**
     * Handle save grade subject.
     *
     * @param gradeSubject the grade subject
     * @param optionalSubjectIds the optional subject ids
     * @param strSubjectId the str subject id
     * @param maxArray -Maximum Mark
     * @throws AkuraAppException the akura app exception
     */
    private void handleSaveGradeSubject(final GradeSubject gradeSubject, String[] optionalSubjectIds,
            String strSubjectId, String maxArray) throws AkuraAppException {

        boolean isOptionalSubject = false;

        if (optionalSubjectIds != null && Arrays.asList(optionalSubjectIds).contains(strSubjectId)) {
            isOptionalSubject = true;
        }

        gradeSubject.setIsOptionalSubject(isOptionalSubject);
        int subjectId = Integer.parseInt(strSubjectId);
        Subject subject = commonService.findSubject(subjectId);
        gradeSubject.setSubject(subject);
        int maximumMarks = Integer.parseInt(maxArray);
        gradeSubject.setMaximumMark(maximumMarks);
        gradeSubject.setSubjectCode(gradeSubject.getGrade().getDescription() + VAR_DASH
                + gradeSubject.getSubject().getDescription());
        commonService.addGradeSubject(gradeSubject);
    }

    /**
     * check if there are exists grade- subjects.
     *
     * @param model - a HashMap that contains information of the GradeSubject.
     * @param e - Pass AkuraAppEXception to check message status.
     */
    private void checkExistsGradeSubject(final ModelMap model, AkuraAppException e) {

        String message;

        message = PropertyReader.getPropertyValue(AKURA_ERROR_MSG_PROPERTY, ERROR_MSG_GRADESUBJECT_DELETE_SUBJECTS);

        if (!e.getErrorCode().equals(message)) {
            message = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_GRADESUBJECT_EXIST);
        }

        GradeSubject newGradeSubject = new GradeSubject();
        model.addAttribute(MODEL_ATT_GRADE_SUBJECT, newGradeSubject);
        model.addAttribute(VAR_MESSAGE, message);
    }

    /**
     * Deletes the relevant gradeSubject object.
     *
     * @param request - an instance of request scope
     * @param model - a HashMap that contains information of the GradeSubject
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a GradeSubject instance.
     */
    @RequestMapping(REQ_MAP_DELETE_GRADE_SUBJECT)
    public String deleteGradeSubject(HttpServletRequest request, ModelMap model) throws AkuraAppException {

        String redirectURL = VIEW_POST_MANAGE_SUBJECT_GRADE;

        try {
            Grade grade = commonService.getGradeByGradeName(String.valueOf(request.getParameter(GRADE_DESCRIPTION)));
            List<GradeSubject> gradeSubjectIdList = commonService.getGradeSubjectIdListByGrade(grade.getGradeId());
            commonService.deleteGradeSubjectList(gradeSubjectIdList);
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_GRADESUBJECT_DELETE);
                GradeSubject newGradeSubject = new GradeSubject();
                model.addAttribute(MODEL_ATT_GRADE_SUBJECT, newGradeSubject);
                model.addAttribute(VAR_MESSAGE, message);
                redirectURL = VIEW_GET_MANAGE_SUBJECT_GRADE;
            } else {
                throw e;
            }
        }

        return redirectURL;
    }

    /**
     * Edit maximum Marks.
     *
     * @param grade - grade description
     * @param allSubjectIds - Subject ids
     * @param maxArray - maximum Mark
     * @throws AkuraException throws when occurred exception
     */
    private void editMaximumMarks(String grade, String allSubjectIds, String maxArray) throws AkuraException {

        int subjectId = Integer.parseInt(allSubjectIds);
        int maximumMark = Integer.parseInt(maxArray);
        GradeSubject gradeSub = commonService.findGradeSubjectByGradeAndSubjectId(subjectId, grade);
        gradeSub.setMaximumMark(maximumMark);
        commonService.updateGradeSubject(gradeSub);

    }
}
