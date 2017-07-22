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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.GradingValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageGradingInformationController is to handle Add/Edit/Delete operations for Grading Information
 * reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageGradingInformationController {
    
    /** view post method manage grading. */
    private static final String VIEW_POST_MANAGE_GRADING = "redirect:manageGradingInformation.htm";
    
    /** view get method manage grading. */
    private static final String VIEW_GET_MANAGE_GRADING = "reference/manageGradingInformation";

    /** model attribute of grading. */
    private static final String MODEL_ATT_GRADING = "grading";
    
    /** model attribute of Grading list. */
    private static final String MODEL_ATT_GRADING_LIST = "gradingList";
    
    /** request mapping value for save or update Grading. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateGrading.htm";
    
    /** request mapping value for delete Grading. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteGrading.htm";
    
    /** request attribute for religionId. */
    private static final String REQ_GRADINGID = "gradingId";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY = "REF.UI.GRADING.DESCRIPTION.EXIST";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_DELETE = "REF.UI.GRADING.DELETE";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** Model attribute of "showEditSection". */
    private static final String SHOW_EDIT_SECTION = "showEditSection";
    
    /** Holds gradingValidator instance of {@link GradingValidator}. */
    private GradingValidator gradingValidator;
    
    /**
     * Set the gradingValidator to inject the validator.
     * 
     * @param gradingValidatorValue the gradingValidator to set
     */
    public void setGradingValidator(GradingValidator gradingValidatorValue) {
    
        this.gradingValidator = gradingValidatorValue;
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
     * Handle GET requests for GradingInformation view.
     * 
     * @param model - {@link ModelMap}
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showGradingInformation(ModelMap model) {
    
        Grading grading = (Grading) model.get(MODEL_ATT_GRADING);
        
        if (grading == null) {
            grading = new Grading();
        }
        model.addAttribute(MODEL_ATT_GRADING, grading);
        
        return VIEW_GET_MANAGE_GRADING;
    }
    
    /**
     * This method is to handle Add/Edit Grading information.
     * 
     * @param grading - {@link Grading}
     * @param request - {@link HttpServletRequest}
     * @param model - {@link ModelMap}
     * @param bindingResult - {@link BindingResult}
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - throw this detailed exception
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateGrading(@ModelAttribute(MODEL_ATT_GRADING) Grading grading, BindingResult bindingResult,
            HttpServletRequest request, ModelMap model) throws AkuraAppException {
        
        Grading selectedObj = null;
        gradingValidator.validate(grading, bindingResult);
        
        if (bindingResult.hasErrors()) {
            if(grading.getGradingId() !=0){
                selectedObj = commonService.findGradingById(grading.getGradingId());
                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                
            }
            model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
            return VIEW_GET_MANAGE_GRADING;
        } else {
            
            String strGradeAcronym = grading.getGradeAcronym().trim();
            String strDescription = grading.getDescription().trim();
            int gradingId = grading.getGradingId();

            if(gradingId == 0){
                
                if (commonService.isExistsGradingAcronym(strGradeAcronym,gradingId)
                        ||commonService.isExistsGradingDecription(strDescription,gradingId)){
                    String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
                  model.addAttribute(MESSAGE, message);
                  selectedObj = commonService.findGradingById(grading.getGradingId());
                  model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                  model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                  return VIEW_GET_MANAGE_GRADING;
                  
                }else{
                    commonService.createGrading(grading);
                } 
            }else{
                
                if (commonService.isExistsGradingAcronym(strGradeAcronym,gradingId)
                        ||commonService.isExistsGradingDecription(strDescription,gradingId)){
                    String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
                  model.addAttribute(MESSAGE, message);
                  selectedObj = commonService.findGradingById(grading.getGradingId());
                  model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                  model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                  return VIEW_GET_MANAGE_GRADING;
                
            }else{
                commonService.updateGrading(grading);
            }
        }
        return VIEW_POST_MANAGE_GRADING;
    }
    } 
    /**
     * Delete a grading information from reference module.
     * 
     * @param request - {@link HttpServletRequest}
     * @param model - {@link ModelMap}
     * @throws AkuraAppException - throw detailed exception
     * @return name of the view.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteGrading(HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        try {
            int gradingId = Integer.parseInt(request.getParameter(REQ_GRADINGID));
            commonService.deleteGrading(gradingId);
        } catch (AkuraAppException e) {
            
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                Grading newGrading = new Grading();
                model.addAttribute(MODEL_ATT_GRADING, newGrading);
                model.addAttribute(MESSAGE, message);
                return VIEW_GET_MANAGE_GRADING;
            }
        }
        return VIEW_POST_MANAGE_GRADING;
    }
    
    /**
     * Method is to return Grading list.
     * 
     * @return list of Grading objects.
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_GRADING_LIST)
    public List<Grading> populateGradingList() throws AkuraAppException {
    
        return SortUtil.sortGradingList(commonService.getGradingList());
    }
}
