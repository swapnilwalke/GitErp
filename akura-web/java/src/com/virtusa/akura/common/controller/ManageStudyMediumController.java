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

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.StudyMedium;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.StudyMediumValidator;

/**
 * ManageStudyMediumController is to handle view/Add/Edit/Delete operations for Study Mediums in reference
 * module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageStudyMediumController {
    
    /** Empty String constant. */
    private static final String EMPTY_STRING = "";

    /** Error massage no results found. */
    private static final String NO_SEARCH_RESULT_MEDIUM = "REF.UI.NO.SEARCH.RESULT.MEDIUM";
    
    /** Error massage for update fails. */
    private static final String MEDIUM_EXIST = "REF.UI.STUDY.MEDIUM.EXISTS";
    
    /** Request parameter name. */
    private static final String SEARCH_DESCRIPTION = "searchDescription";
    
    /** Command name to use in forms. */
    private static final String STUDY_MEDIUM = "studyMedium";
    
    /** Model attribute key name for massage. */
    private static final String MESSAGE = "message";
    
    /** Represents commonService - {@link CommonService}. */
    private CommonService commonService;
    
    /** error message for sport sub delete. */
    private static final String ERROR_MSG_MEDIUM_DELETE = "REF.UI.STUDY.MEDIUM.DELETE";
    
    /** view path to manageStudyMediums.htm. */
    private static final String RETURN_VIEW_PATH = "reference/manageStudyMediums";
    
    /** request mapping value for manage Study Mediums. */
    private static final String PAGE_LOAD_URL_STUDY_MEDIUM = "/manageStudyMediums.htm";
    
    /** request mapping value for Search Study Mediums. */
    private static final String SEARCH_URL_STUDY_MEDIUM = "/manageSMSearch.htm";
    
    /** request mapping value for Save or Update Study Mediums. */
    private static final String SAVE_UPDATE_URL_STUDY_MEDIUM = "/manageSaveOrUpdateMedium.htm";
    
    /** Model attribute name for study mediums list. */
    private static final String DELETE_URL_STUDY_MEDIUM = "/manageDeleteMedium.htm";
    
    /** request mapping value for delete Study Mediums. */
    private static final String MEDIUM_LIST = "mediumList";
    
    /** attribute for holding message. */
    private static final String EDITPANE = "editpane";

    /** Model attribute of "selectedObjId". */
    private static final String SELECTED_OBJ_ID = "selectedObjId";

    
    /** Holds StudyMediumValidator instance. */
    private StudyMediumValidator studyMediumValidator;
    
    /**
     * Set the setStudyMediumValidator to inject the Validator.
     * 
     * @param studyMediumValidatorArg the StudyMediumValidator to set
     */
    public void setStudyMediumValidator(StudyMediumValidator studyMediumValidatorArg) {
    
        this.studyMediumValidator = studyMediumValidatorArg;
    }
    
    /**
     * @param commonServiceArg the commonService to set
     */
    public void setCommonService(CommonService commonServiceArg) {
    
        this.commonService = commonServiceArg;
    }
    
    /**
     * handle GET requests for Study Mediums view. When loading the page.
     * 
     * @param model - ModelMap
     * @param studyMedium - Model attribute
     * @return the name of the view.
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @RequestMapping(value = PAGE_LOAD_URL_STUDY_MEDIUM, method = RequestMethod.GET)
    public String findAllStudyMediums(ModelMap model, @ModelAttribute(STUDY_MEDIUM) final StudyMedium studyMedium)
            throws AkuraAppException {
    
        model.addAttribute(MEDIUM_LIST, commonService.getStudyMediumList());
        model.addAttribute(SEARCH_DESCRIPTION, EMPTY_STRING);
        return RETURN_VIEW_PATH;
        
    }
    
    /**
     * handle Post requests for Study Mediums view. When form submitted as manageSMSearch action.
     * 
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @param studyMedium - Model attribute
     * @return the name of the view.
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @RequestMapping(value = SEARCH_URL_STUDY_MEDIUM, method = RequestMethod.POST)
    public String findStudyMedium(HttpServletRequest request, ModelMap model,
            @ModelAttribute(STUDY_MEDIUM) final StudyMedium studyMedium) throws AkuraAppException {
    
        String searchDescription = request.getParameter(SEARCH_DESCRIPTION).trim();
        request.setAttribute(SEARCH_DESCRIPTION, searchDescription);
        
        List<StudyMedium> studyMediums = commonService.findStudyMedium(searchDescription);
        if (studyMediums == null || studyMediums.isEmpty()) {
            String message = new ErrorMsgLoader().getErrorMessage(NO_SEARCH_RESULT_MEDIUM);
            model.addAttribute(MESSAGE, message);
            
        }
        model.addAttribute(MEDIUM_LIST, studyMediums);
        
        return RETURN_VIEW_PATH;
        
    }
    
    /**
     * handle Post requests for Study Mediums view. When form submitted as add or edit actions.
     * 
     * @param request - object
     * @param model - ModelMap
     * @param studyMedium - Model attribute
     * @param result -{@link BindingResult}
     * @return the name of the view.
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @RequestMapping(value = SAVE_UPDATE_URL_STUDY_MEDIUM, method = RequestMethod.POST)
    public String saveOrUpdateStudyMedium(HttpServletRequest request, ModelMap model,
            @ModelAttribute(STUDY_MEDIUM) final StudyMedium studyMedium, BindingResult result) 
                    throws AkuraAppException {
    
        studyMediumValidator.validate(studyMedium, result);
        
        if(result.hasErrors()){
            model.addAttribute(EDITPANE, EDITPANE);
            int studyMediumId = studyMedium.getStudyMediumId();
            model.addAttribute(SELECTED_OBJ_ID, studyMediumId );
        }
        
        if (!result.hasErrors()) {
            try {
                commonService.saveOrUpdateStudyMedium(studyMedium);
            } catch (AkuraAppException e) {
                
                model.addAttribute(EDITPANE, EDITPANE);
                int studyMediumId = studyMedium.getStudyMediumId();
                model.addAttribute(SELECTED_OBJ_ID, studyMediumId);
                String message = new ErrorMsgLoader().getErrorMessage(MEDIUM_EXIST);
                model.addAttribute(MESSAGE, message);
            }
        }
        
        List<StudyMedium> studyMediums = null;
        // after adding new record, should load full list
        // after edit record, it should load record that matches the search string
        if (studyMedium.getStudyMediumId().intValue() == 0) {
            model.addAttribute(SEARCH_DESCRIPTION, EMPTY_STRING);
            studyMediums = commonService.getStudyMediumList();
        } else {
            String searchDescription = request.getParameter(SEARCH_DESCRIPTION).trim();
            studyMediums = commonService.findStudyMedium(searchDescription);
           // model.addAttribute(EDITPANE, studyMedium.getStudyMediumId());
            model.addAttribute(SEARCH_DESCRIPTION, searchDescription);
        }
       
        model.addAttribute(MEDIUM_LIST, studyMediums);
        
        return RETURN_VIEW_PATH;
    }
    
    /**
     * handle Post requests for Study Mediums view. When form submit as delete action.
     * 
     * @param request - request object
     * @param model - ModelMap
     * @param studyMedium - Model attribute
     * @return the name of the view.
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @RequestMapping(value = DELETE_URL_STUDY_MEDIUM, method = RequestMethod.POST)
    public String deleteStudyMedium(HttpServletRequest request, ModelMap model,
            @ModelAttribute(STUDY_MEDIUM) final StudyMedium studyMedium) throws AkuraAppException {
    
        try {
            commonService.deleteStudyMedium(studyMedium);
        } catch (AkuraAppException e) {
            
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_MEDIUM_DELETE);
                model.addAttribute(MESSAGE, message);
            } else {
                throw e;
            }
        }
        
        // table must fill with previous search criteria
        return this.findStudyMedium(request, model, studyMedium);
        
    }
    
}
