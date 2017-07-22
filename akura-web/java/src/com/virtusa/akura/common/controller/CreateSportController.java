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

import com.virtusa.akura.api.dto.Section;
import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.SportValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * CreateSportController is to handle Add/Edit/Delete/View operations for Manage Sport in reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class CreateSportController {
    
    /** request mapping value for manage delete sport. */
    private static final String MANAGE_DELETE_SPORT_HTM = "/manageDeleteSport.htm";
    
    /** attribute for holding error message key. */
    private static final String REF_UI_SPORT_DELETE = "REF.UI.SPORT.DELETE";
    
    /** request mapping value for manage save or update sport. */
    private static final String SAVE_OR_UPDATE_SPORT_HTM = "/saveOrUpdateSport.htm";
    
    /** attribute for holding error message key. */
    private static final String REF_UI_SPORT_DESCRIPTION_EXIST = "REF.UI.SPORT.DESCRIPTION.EXIST";
    
    /** view post method create sport. */
    private static final String VIEW_POST_CREAT_SPORT = "redirect:manageSport.htm";
    
    /** view get method create sport. */
    private static final String VIEW_GET_CREATE_SPORT = "reference/createSport";

    /** model attribute of Sport. */
    private static final String MODEL_ATT_SPORT = "sport";
    
    /** model attribute of Sport list. */
    private static final String MODEL_ATT_SPORT_LIST = "sportList";
    
    /** model attribute of selected sport object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** model attribute of message. */
    private static final String MODEL_ATT_MESSAGE = "message";
    
    /** attribute for holding empty character. */
    private static final String STRING_EMPTY = "";
    
    /** attribute for holding space. */
    private static final String STRING_SPACE = " ";
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** SportValidator attribute for holding sportValidator. */
    private SportValidator sportValidator;
    
    /** model attribute of Selected Sport. */
    private static final String MODEL_ATT_SELECTED_SPORT = "selectedObjId";
    
    /** request parameter for hold panelDisplay state. */
    private static final String DISPLAY_PANEL = "displayPanel";
    
    /**
     * sets the SportValidator object.
     * 
     * @param sportValidatorObj the sportValidator to set
     */
    public void setSportValidator(SportValidator sportValidatorObj) {
    
        this.sportValidator = sportValidatorObj;
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
     * CreateSportController constructor.
     */
    public CreateSportController() {
    
    }
    
    /**
     * handle GET requests for Sport_details view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showSportDetail(ModelMap model) {
    
        Sport sport = (Sport) model.get(MODEL_ATT_SPORT);
        
        if (sport == null) {
            sport = new Sport();
        }
        
        model.addAttribute(MODEL_ATT_SPORT, sport);
        
        return VIEW_GET_CREATE_SPORT;
    }
    
    /**
     * Create or update Sport object details.
     * 
     * @param sport - Sport object.
     * @param bindingResult - BindingResult
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when save or update a Sport instance.
     */
    @RequestMapping(value = SAVE_OR_UPDATE_SPORT_HTM, method = RequestMethod.POST)
    public String saveOrUpdateSport(@ModelAttribute(MODEL_ATT_SPORT) Sport sport, BindingResult bindingResult,
            HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        String strMessage = null;
        Sport selectedObject = null;
        String strDescription = sport.getDescription().trim();
        int intSportId = sport.getSportId();
        
        // validations
        sportValidator.validate(sport, bindingResult);
        if (bindingResult.hasErrors()) {
            request.setAttribute(DISPLAY_PANEL, true);
            model.addAttribute(MODEL_ATT_SELECTED_SPORT, intSportId);
            return VIEW_GET_CREATE_SPORT;
        } else {
            // sport already exist.
            if (commonService.isExistsSport(strDescription)) {
                
                if (intSportId != 0) {
                    selectedObject = commonService.findSportById(intSportId);
                    model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObject);
                }else {
                    strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_SPORT_DESCRIPTION_EXIST);
                    model.addAttribute(MODEL_ATT_SELECTED_SPORT, intSportId);
                    model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                    request.setAttribute(DISPLAY_PANEL, true);
                }
                return VIEW_GET_CREATE_SPORT;
                
            } else {
                if (intSportId == 0) {
                    commonService.addSport(sport);
                } else {
                    commonService.editSport(sport);
                }
            }
            return VIEW_POST_CREAT_SPORT;
        }
    }
    
    /**
     * Delete Sport details.
     * 
     * @param sport - Sport object.
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when deleting a Sport instance.
     */
    @RequestMapping(value = MANAGE_DELETE_SPORT_HTM, method = RequestMethod.POST)
    public String deleteSport(@ModelAttribute(MODEL_ATT_SPORT) Sport sport, HttpServletRequest request, ModelMap model)
            throws AkuraAppException {
    
        String strMessage = null;
        try {
            commonService.deleteSport(sport.getSportId());
            return VIEW_POST_CREAT_SPORT;
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_SPORT_DELETE);
                Sport newSport = new Sport();
                List<Sport> sportList = commonService.getSportsList();
                model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                model.addAttribute(MODEL_ATT_SPORT, newSport);
                model.addAttribute(MODEL_ATT_SPORT_LIST, sportList);
                return VIEW_GET_CREATE_SPORT;
            } else {
                throw e;
            }
        }
    }
    
    /**
     * Method is to return Sport list.
     * 
     * @return list of Sport
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_SPORT_LIST)
    public List<Sport> populateSportList() throws AkuraAppException {
    
        return SortUtil.sortSportList(commonService.getSportsList());
    }
    
}
