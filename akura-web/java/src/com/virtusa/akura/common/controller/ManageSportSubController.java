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

import com.virtusa.akura.api.dto.SportSub;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.SportSubValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageSportSubController is to handle Add/Edit/Delete/View operations for SportSub reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageSportSubController {
    
    /** error message for sport sub delete. */
    private static final String ERROR_MSG_SPORTSUB_DELETE = "REF.UI.SPORTSUB.DELETE";
    
    /** request value for manage delete sport sub. */
    private static final String REQ_VALUE_DELETE_SPORT_SUB = "/manageDeleteSportSub.htm";
    
    /** error message for sport sub description exits. */
    private static final String ERROR_MSG_SPORTSUB_DESCRIPTION_EXIST = "REF.UI.SPORTSUB.DESCRIPTION.EXIST";
    
    /** request value for save or update sport sub. */
    private static final String REQ_VALUE_SAVEORUPDATE_SPORT_SUB = "/saveOrUpdateSportSub.htm";
    
    /** view post method create sportSub. */
    private static final String VIEW_POST_CREAT_SPORT_SUB = "redirect:manageSportSub.htm";
    
    /** view get method create sportSub. */
    private static final String VIEW_GET_CREATE_SPORT_SUB = "reference/manageSportSub";

    /** model attribute of SportSub. */
    private static final String MODEL_ATT_SPORT_SUB = "sportSub";
    
    /** model attribute of SportSub list. */
    private static final String MODEL_ATT_SPORT_SUB_LIST = "sportSubList";
    
    /** model attribute of selected sportSub object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** model attribute of message. */
    private static final String MODEL_ATT_MESSAGE = "message";
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** SportSubValidator attribute for holding sportSubValidator. */
    private SportSubValidator sportSubValidator;
    
    /** model attribute of Selected Sport Sub Category. */
    private static final String MODEL_ATT_SELECTED_SPORT_AGE_GROUPS = "selectedObjId";

    /** request parameter for hold panelDisplay state. */
    private static final String DISPLAY_PANEL = "displayPanel";
    
    /**
     * sets the SportSubValidator object.
     * 
     * @param sportSubValidatorObj the sportSubValidator to set
     */
    public void setSportSubValidator(SportSubValidator sportSubValidatorObj) {
    
        this.sportSubValidator = sportSubValidatorObj;
    }
    
    /**
     * sets the CommonService object.
     * 
     * @param commonServiceObj the commonService to set
     */
    public void setCommonService(CommonService commonServiceObj) {
    
        this.commonService = commonServiceObj;
    }
    
    /**
     * ManageSportSubController constructor.
     */
    public ManageSportSubController() {
    
    }
    
    /**
     * handle GET requests for SportSub_details view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showSportSubDetail(ModelMap model) {
    
        SportSub sportSub = (SportSub) model.get(MODEL_ATT_SPORT_SUB);
        
        if (sportSub == null) {
            sportSub = new SportSub();
        }
        
        model.addAttribute(MODEL_ATT_SPORT_SUB, sportSub);
        
        return VIEW_GET_CREATE_SPORT_SUB;
    }
    
    /**
     * Create or update SportSub object details.
     * 
     * @param sportSub - SportSub object.
     * @param bindingResult - BindingResult
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when save or update a SportSub instance.
     */
    @RequestMapping(value = REQ_VALUE_SAVEORUPDATE_SPORT_SUB, method = RequestMethod.POST)
    public String saveOrUpdateSportSub(@ModelAttribute(MODEL_ATT_SPORT_SUB) SportSub sportSub,
            BindingResult bindingResult, HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        SportSub selectedObject = null;
        String strDescription = sportSub.getDescription().trim();
        int intSportSubId = sportSub.getSportSubId();
        
        // validations
        sportSubValidator.validate(sportSub, bindingResult);
        if (bindingResult.hasErrors()) {
            request.setAttribute(DISPLAY_PANEL, true);
        	model.addAttribute(MODEL_ATT_SELECTED_SPORT_AGE_GROUPS, intSportSubId);
            return VIEW_GET_CREATE_SPORT_SUB;
        } else {
            // sportSub already exist.
        	boolean isExistsSportSub = false;
        	isExistsSportSub = commonService
					.isExistsSportSub(strDescription);
			if (commonService.isExistsSportSub(strDescription)) {
                
                
                if (intSportSubId != 0) {
                    selectedObject = commonService.findSportSubById(intSportSubId);
                    model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObject);
                } else {
                    String strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_SPORTSUB_DESCRIPTION_EXIST);
                    model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                    model.addAttribute(MODEL_ATT_SELECTED_SPORT_AGE_GROUPS, intSportSubId);
                    request.setAttribute(DISPLAY_PANEL, true);
                }
                return VIEW_GET_CREATE_SPORT_SUB;
                
            } else {
                if (intSportSubId == 0) {
                    commonService.addSportSub(sportSub);
                } else {
                    commonService.editSportSub(sportSub);
                }
            }
            return VIEW_POST_CREAT_SPORT_SUB;
        }
    }
    
    /**
     * Delete SportSub details.
     * 
     * @param sportSub - SportSub object.
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when deleting a SportSub instance.
     */
    @RequestMapping(value = REQ_VALUE_DELETE_SPORT_SUB, method = RequestMethod.POST)
    public String deleteSportSub(@ModelAttribute(MODEL_ATT_SPORT_SUB) SportSub sportSub, HttpServletRequest request,
            ModelMap model) throws AkuraAppException {
    
        String strMessage = null;
        try {
            commonService.deleteSportSub(sportSub.getSportSubId());
            return VIEW_POST_CREAT_SPORT_SUB;
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_SPORTSUB_DELETE);
                SportSub newSportSub = new SportSub();
                List<SportSub> sportSubList = populateSportSubList();
                model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                model.addAttribute(MODEL_ATT_SPORT_SUB, newSportSub);
                model.addAttribute(MODEL_ATT_SPORT_SUB_LIST, sportSubList);
                return VIEW_GET_CREATE_SPORT_SUB;
            } else {
                throw e;
            }
        }
        
    }
    
   
    
    /**
     * Method is to return SportSub list.
     * 
     * @return list of SportSub
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_SPORT_SUB_LIST)
    public List<SportSub> populateSportSubList() throws AkuraAppException {
    
        return SortUtil.sortSportSubList(commonService.getSportSubsList());
    }
}
