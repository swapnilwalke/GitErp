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

import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.HouseValidator;

/**
 * ManageHouseController is to handle Add/Edit/Delete operations for House reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageHouseController {
    
    /** view post method manage grade. */
    private static final String VIEW_POST_MANAGE_HOUSE = "redirect:manageHouse.htm";
    
    /** view get method manage grade. */
    private static final String VIEW_GET_MANAGE_HOUSE = "reference/createHouse";
    
    /** model attribute of House. */
    private static final String MODEL_ATT_HOUSE = "house";
    
    /** model attribute of Selected House. */
    private static final String MODEL_ATT_SELECTED_HOUSE = "selectedObjId";
    
    /** model attribute of House list. */
    private static final String MODEL_ATT_HOUSE_LIST = "houseList";
    
    /** request mapping value for save or update house. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateHouse.htm";
    
    /** request mapping value for delete house. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteHouse.htm";
    
    /** request attribute for religionId. */
    private static final String REQ_HOUSEID = "houseId";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY = "REF.UI.HOUSE.DESCRIPTION.EXIST";
    
    /** attribute for holding error message for color. */
    private static final String ERROR_MSG_KEY_FOR_COLOUR = "REF.UI.HOUSE.COLOUR.EXIST";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_DELETE = "REF.UI.HOUSE.DELETE";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** commonService attribute for holding CommonService. */
    private CommonService commonService;
    
    /** Holds houseValidator instance {@link HouseValidator}. */
    private HouseValidator houseValidator;
    
    /** request parameter for hold panelDisplay state. */
    private static final String DISPLAY_PANEL = "displayPanel";
    
    /**
     * Set the commonService to inject the service.
     * 
     * @param commonServiceVlaue the commonService to set
     */
    public void setCommonService(CommonService commonServiceVlaue) {
    
        this.commonService = commonServiceVlaue;
    }
    
    /**
     * Set the houseValidator to inject the validator.
     * 
     * @param houseValidatorValue the houseValidator to set
     */
    public void setHouseValidator(HouseValidator houseValidatorValue) {
    
        this.houseValidator = houseValidatorValue;
    }
    
    /**
     * Handle GET requests for House_details view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showHouseDetail(ModelMap model) {
    
        House house = (House) model.get(MODEL_ATT_HOUSE);
        
        if (house == null) {
            house = new House();
        }
        model.addAttribute(MODEL_ATT_HOUSE, house);
        
        return VIEW_GET_MANAGE_HOUSE;
    }
    
    /**
     * This method is to handle Add/Edit house details.
     * 
     * @param house - {@link House}
     * @param request - {@link HttpServletRequest}
     * @param model - {@link ModelMap}
     * @param bindingResult - {@link BindingResult}
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - throw this exception
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateHouse(@ModelAttribute(MODEL_ATT_HOUSE) House house, BindingResult bindingResult,
            HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        houseValidator.validate(house, bindingResult);
        
        if (bindingResult.hasErrors()) {
            model.addAttribute(MODEL_ATT_SELECTED_HOUSE, house.getHouseId());
            request.setAttribute(DISPLAY_PANEL, true);
            return VIEW_GET_MANAGE_HOUSE;
        } else {
            try {
                String strHouseName = house.getName().trim();
                String strHouseColor = house.getColour().trim();
                
                house.setName(strHouseName);
                house.setColour(strHouseColor);
                house.setDescription(house.getDescription().trim());
                
                // If the house id is zero, it's an new entry.
                if (house.getHouseId() == 0 || house.getColour() == null) {
                    List<House> houseObjects = commonService.getHouseList();
                    
                    for (House h : houseObjects) {
                        if (h.getColour().equals(house.getColour())) {
                            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY_FOR_COLOUR);
                            model.addAttribute(MODEL_ATT_SELECTED_HOUSE, house.getHouseId());
                            model.addAttribute(MESSAGE, message);
                            request.setAttribute(DISPLAY_PANEL, true);
                            return VIEW_GET_MANAGE_HOUSE;
                        }
                        
                    }
                    commonService.createHouse(house);
                } else {
               
                    List<House> houseObjects = commonService.getHouseList();
                       
                    for (House h : houseObjects) {
                        if (h.getColour().equals(house.getColour())) {
                            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY_FOR_COLOUR);
                            model.addAttribute(MODEL_ATT_SELECTED_HOUSE, house.getHouseId());
                            model.addAttribute(MESSAGE, message);
                            request.setAttribute(DISPLAY_PANEL, true);
                            return VIEW_GET_MANAGE_HOUSE;
                        }
                        
                    }
                   
                    commonService.updateHouse(house);
                }
                
            } catch (AkuraAppException e) {
                // If the House is already added, populate a message.
                if (e.getCause() instanceof DataIntegrityViolationException) {
                    String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
                    model.addAttribute(MODEL_ATT_SELECTED_HOUSE, house.getHouseId());
                    model.addAttribute(MESSAGE, message);
                    request.setAttribute(DISPLAY_PANEL, true);
                    return VIEW_GET_MANAGE_HOUSE;
                }
            }
        }
        
        return VIEW_POST_MANAGE_HOUSE;
    }
    
    /**
     * Delete a house from reference module.
     * 
     * @param request {@link HttpServletRequest}
     * @param model {@link ModelMap}
     * @throws AkuraAppException - Detailed exception
     * @return name of the view.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteHouse(HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        try {
            int houseId = Integer.parseInt(request.getParameter(REQ_HOUSEID));
            commonService.deleteHouse(houseId);
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                House newHouse = new House();
                model.addAttribute(MODEL_ATT_HOUSE, newHouse);
                model.addAttribute(MESSAGE, message);
                
                return VIEW_GET_MANAGE_HOUSE;
            }
        }
        
        return VIEW_POST_MANAGE_HOUSE;
    }
    
    /**
     * Method is to return House list.
     * 
     * @return list of House
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_HOUSE_LIST)
    public List<House> populateHouseList() throws AkuraAppException {
    
        return commonService.getHouseList();
    }
}
