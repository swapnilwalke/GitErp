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

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.Race;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.RaceValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * This class handles the requests for domains of the Race related functions.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class RaceController {
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(RaceController.class);
    
    /**
     * Represents an instance of RaceValidator.
     */
    private RaceValidator raceValidator;
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** view get method manage Race. */
    private static final String VIEW_GET_MANAGE_RACE = "reference/manageRace";
    
    /** view post method Race. */
    private static final String VIEW_POST_MANAGE_RACE = "redirect:manageRace.htm";
    
    /** model attribute of Race . */
    private static final String RACE = "race";
    
    /** model attribute of Race List. */
    private static final String MODEL_ATT_RACE_LIST = "raceList";
    
    /** request mapping value for save/update race. */
    private static final String REQ_MAP_SAVE_UPDATE_RACE = "/manageSaveOrUpdateRace.htm";
    
    /** request mapping value for manage race. */
    private static final String REQ_MAP_MANAGE_RACE = "/manageRace.htm";
    
    /** request mapping value for manage delete race. */
    private static final String REQ_MAP_DELETE_RACE = "/manageDeleteRace.htm";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** model attribute of selected club society list. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** model attribute of edit pane add remove. */
    private static final String EDIT_PANE = "editPane";
    
    /**
     * setter method from CommonService.
     * 
     * @param commonServiceVal - CommonService
     */
    
    public void setCommonService(CommonService commonServiceVal) {

        this.commonService = commonServiceVal;
    }
    
    /**
     * Sets an instance of RaceValidator.
     * 
     * @param raceValidatorVal - the relevant instance of RaceValidator
     */
    public void setRaceValidator(RaceValidator raceValidatorVal) {

        raceValidator = raceValidatorVal;
    }
    
    /**
     * Method is to return race reference data.
     * 
     * @throws AkuraAppException - AkuraAppException
     * @return raceList - race reference list.
     */
    @ModelAttribute(MODEL_ATT_RACE_LIST)
    public final List<Race> populateRaceList() throws AkuraAppException {

        return SortUtil.sortRaceList(commonService.getRaceList());
    }
    
    /**
     * Initializes the reference data that is to be loaded on the requested view page.
     * 
     * @param model - a HashMap that contains information of the Race
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(REQ_MAP_MANAGE_RACE)
    public final String manageRace(final ModelMap model) throws AkuraAppException {

        Race race = (Race) model.get(RACE);
        
        if (race == null) {
            race = new Race();
        }
        
        model.addAttribute(RACE, race);
        return VIEW_GET_MANAGE_RACE;
    }
    
    /**
     * Create or update Race object details.
     * 
     * @param race - Race object.
     * @param bindingResult - BindingResult
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when save or update a Race instance.
     */
    @RequestMapping(value = REQ_MAP_SAVE_UPDATE_RACE, method = RequestMethod.POST)
    public String saveOrUpdateRace(@ModelAttribute(RACE) Race race, BindingResult bindingResult,
            HttpServletRequest request, ModelMap model) throws AkuraAppException {

        int intRaceId = race.getRaceId();
        String strMessage=null;
        String strDescription=null;
        Race selectedObj = null;
        
        // validations
        raceValidator.validate(race, bindingResult);
        
        if (bindingResult.hasErrors()) {
        	selectedObj = commonService.findRace(intRaceId);
        	model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
        	model.addAttribute(EDIT_PANE, EDIT_PANE);
            return VIEW_GET_MANAGE_RACE;
            
        } else {
            strDescription = race.getDescription().trim().replaceAll("\\s+", " ");
            strMessage = new ErrorMsgLoader().getErrorMessage("REF.UI.RECORD.EXIST.ERROR");
            race.setDescription(strDescription);
            
            // race already exist.
            if (commonService.isRaceExist(strDescription)) {
                if (intRaceId == 0) {
                    strMessage = new ErrorMsgLoader().getErrorMessage("REF.UI.RECORD.EXIST.ERROR");
                    model.addAttribute(MESSAGE, strMessage);
                	model.addAttribute(EDIT_PANE, EDIT_PANE);
                    return VIEW_GET_MANAGE_RACE;
                    
                } else {
                    if (commonService.findRace(race.getRaceId()).getDescription()
                            .compareToIgnoreCase(strDescription) == 0) {
                        
                        if (commonService.findRace(race.getRaceId()).getDescription().equals(strDescription)) {
                            
                            Race newRace = new Race();
                            newRace = commonService.findRace(race.getRaceId());
                            model.addAttribute(RACE, newRace);
                            
                        } else {
                            
                            commonService.updateRace(race);
                            Race newRace = new Race();
                            newRace = commonService.findRace(race.getRaceId());
                            model.addAttribute(RACE, newRace);
                            
                        }
                        
                    } else {
                        strMessage = new ErrorMsgLoader().getErrorMessage("REF.UI.RECORD.EXIST.ERROR");
                        model.addAttribute(MESSAGE, strMessage);
                        selectedObj = commonService.findRace(intRaceId);
                    	model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);  
                        model.addAttribute(EDIT_PANE, EDIT_PANE);
                        
                        return VIEW_GET_MANAGE_RACE;
                    }
                    
                }
                
            } else {
                //race.setDescription(strDescription);
                if (intRaceId == 0) {
                    commonService.addRace(race);
                } else {
                    try {
                        commonService.updateRace(race);
                    } catch (AkuraAppException e) {
                        strMessage = new ErrorMsgLoader().getErrorMessage("REF.UI.RECORD.EXIST.ERROR");                                           
                        model.addAttribute(MESSAGE, strMessage);
                    	model.addAttribute(EDIT_PANE, EDIT_PANE);
                        return VIEW_GET_MANAGE_RACE;
                    }
                }
                
            }
            
        }
        
        return VIEW_POST_MANAGE_RACE;
    }
    
    /**
     * Deletes the relevant Race object.
     * 
     * @param model - a HashMap that contains information of the Race
     * @param race - an instance of Race
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a Race instance.
     */
    @RequestMapping(REQ_MAP_DELETE_RACE)
    public final String deleteRace(final ModelMap model, @ModelAttribute(RACE) final Race race)
            throws AkuraAppException {

        try {
            int id = race.getRaceId();
            Race findRace = commonService.findRace(id);
            commonService.deleteRace(findRace);
            return VIEW_POST_MANAGE_RACE;
        } catch (AkuraAppException e) {
            LOG.debug("Database error has occured");
            
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message;
                message = new ErrorMsgLoader().getErrorMessage("REF.UI.RACE.DELETE");
                Race newRace = new Race();
                model.addAttribute(MESSAGE, message);
                model.addAttribute(RACE, newRace);
                return VIEW_GET_MANAGE_RACE;
            } else {
                throw e;
            }
        }
    }
    
}
