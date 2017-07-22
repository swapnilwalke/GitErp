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

import com.virtusa.akura.api.dto.AppointmentClassification;
import com.virtusa.akura.api.dto.ClubSociety;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.ClubSocietyValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageClubSocietyController is to handle Add/Edit/Delete/View operations for ClubSociety reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageClubSocietyController {
    
    /** Constant to hold error message reference. */
    private static final String REF_UI_CLUBSOCIETY_DESCRIPTION_EXIST = "REF.UI.CLUBSOCIETY.DESCRIPTION.EXIST";
    
    /** view post method create club society. */
    private static final String VIEW_POST_CREAT_CLUB_SOCIETY = "redirect:manageClubSociety.htm";
    
    /** view get method create club society. */
    private static final String VIEW_GET_CREATE_CLUB_SOCIETY = "reference/manageClubSociety";
    
    /** model attribute of club society. */
    private static final String MODEL_ATT_CLUB_SOCIETY = "clubSociety";
    
    /** model attribute of club society list. */
    private static final String MODEL_ATT_CLUB_SOCIETY_LIST = "clubSocietyList";
    
    /** model attribute of selected club society list. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** model attribute of message. */
    private static final String MODEL_ATT_MESSAGE = "message";
    
    /** request mapping value for save or update Club Society. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateClubSociety.htm";
    
    /** request mapping value for delete Club Society. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteClubSociety.htm";
    
    /** attribute for holding error message when club exist. */
    private static final String CLUBSOCIETY_EXIST = REF_UI_CLUBSOCIETY_DESCRIPTION_EXIST;
    
    /** attribute for holding error message when unable to delete club. */
    private static final String CLUB_DELETE_ERR = "REF.UI.CLUB.DELETE";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY = "REF.UI.RECORD.EXIST.ERROR";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** model attribute of edit pane add remove. */
    private static final String EDIT_PANE = "editPane";
    
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** ClubSocietyValidator attribute for holding clubSocietyValidator. */
    private ClubSocietyValidator clubSocietyValidator;
    
    
    
    /**
     * ManageClubSocietyController constructor.
     */
    public ManageClubSocietyController() {

    }
    
    /**
     * sets the ClubSocietyValidator object.
     * 
     * @param clubSocietyValidatorObj the clubSocietyValidator to set
     */
    public void setClubSocietyValidator(ClubSocietyValidator clubSocietyValidatorObj) {

        this.clubSocietyValidator = clubSocietyValidatorObj;
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
     * handle GET requests for Club society_details view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showClubSocietyDetail(ModelMap model) {

        ClubSociety clubSociety = (ClubSociety) model.get(MODEL_ATT_CLUB_SOCIETY);
        
        if (clubSociety == null) {
            clubSociety = new ClubSociety();
        }
        
        model.addAttribute(MODEL_ATT_CLUB_SOCIETY, clubSociety);
        
        return VIEW_GET_CREATE_CLUB_SOCIETY;
    }
    
    /**
     * Create or update Club Society details.
     * 
     * @param clubSociety - ClubSociety object.
     * @param bindingResult - BindingResult
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when save or update a ClubSociety instance.
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateClubSociety(@ModelAttribute(MODEL_ATT_CLUB_SOCIETY) ClubSociety clubSociety,
            BindingResult bindingResult, HttpServletRequest request, ModelMap model) throws AkuraAppException {

       ClubSociety selectedObj = null;
        
        // validations
        clubSocietyValidator.validate(clubSociety, bindingResult);
        
        if (bindingResult.hasErrors()) {
        	
        	selectedObj = commonService.findClubSocietyById(clubSociety.getClubSocietyId());
        	model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
        	model.addAttribute(EDIT_PANE, EDIT_PANE);
            return VIEW_GET_CREATE_CLUB_SOCIETY;
            
        } else {
            String strClubSocietyName =
                clubSociety.getName().trim().replaceAll("\\s+", " ");
        String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
        clubSociety.setName(strClubSocietyName);
        
        if (commonService.isExistsClubSociety(strClubSocietyName,clubSociety.getClubSocietyId())) {
        	
            if (clubSociety.getClubSocietyId() != 0) {
                
 
            	
                if (commonService.findClubSocietyById(clubSociety.getClubSocietyId())
                        .getName().compareToIgnoreCase(strClubSocietyName) == 0) {
                    
                    
                        
                        commonService.editClubSociety(clubSociety);
                        ClubSociety newClubSociety = new ClubSociety();
                        newClubSociety =
                                commonService.findClubSocietyById(clubSociety
                                        .getClubSocietyId());
                        model.addAttribute(MODEL_ATT_CLUB_SOCIETY, newClubSociety);
                        
                 
                    
                } else {
                    
                    model.addAttribute(MESSAGE, message);
                   	selectedObj = commonService.findClubSocietyById(clubSociety.getClubSocietyId());
                	model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                    model.addAttribute(EDIT_PANE, EDIT_PANE);
                    return VIEW_GET_CREATE_CLUB_SOCIETY;
                }
                
            } else {
                
                model.addAttribute(MESSAGE, message);
                model.addAttribute(EDIT_PANE, EDIT_PANE);
                return VIEW_GET_CREATE_CLUB_SOCIETY;
                
            }
            
        } else {
            
            if (clubSociety.getClubSocietyId() == 0) {
                
                commonService.addClubSociety(clubSociety);
                
            } else {
                
                commonService.editClubSociety(clubSociety);
            }
        }
    }
        return VIEW_POST_CREAT_CLUB_SOCIETY;
    }
    
    /**
     * Delete Club Society details.
     * 
     * @param clubSociety - ClubSociety object.
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when deleting a ClubSociety instance.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteClubSociety(@ModelAttribute(MODEL_ATT_CLUB_SOCIETY) ClubSociety clubSociety,
            HttpServletRequest request, ModelMap model) throws AkuraAppException {

        String strMessage = null;
        try {
            commonService.deleteClubSociety(clubSociety.getClubSocietyId());
            return VIEW_POST_CREAT_CLUB_SOCIETY;
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                strMessage = new ErrorMsgLoader().getErrorMessage(CLUB_DELETE_ERR);
                ClubSociety newClubSociety = new ClubSociety();
                List<ClubSociety> clubSocietyList = commonService.getClubsSocietiesList();
                model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                model.addAttribute(MODEL_ATT_CLUB_SOCIETY, newClubSociety);
                model.addAttribute(MODEL_ATT_CLUB_SOCIETY_LIST, clubSocietyList);
                return VIEW_GET_CREATE_CLUB_SOCIETY;
            } else {
                throw e;
            }
        }
        
    }
    
    /**
     * Method is to return clubSociety list.
     * 
     * @return list of ClubSociety
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_CLUB_SOCIETY_LIST)
    public List<ClubSociety> populateClubSocietyList() throws AkuraAppException {

        return SortUtil.sortClubSocietyList(commonService.getClubsSocietiesList());
    }
}
