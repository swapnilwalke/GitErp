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

import com.virtusa.akura.api.dto.Position;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.PositionValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManagePositionController is to handle Add/Edit/Delete operations for Position reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManagePositionController {
    
    /** view post method manage Position. */
    private static final String VIEW_POST_MANAGE_POSITION = "redirect:managePosition.htm";
    
    /** view get method manage Position. */
    private static final String VIEW_GET_MANAGE_POSITION = "reference/managePosition";
    
    /** model attribute of Position. */
    private static final String MODEL_ATT_POSITION = "position";
    
    /** model attribute of Position list. */
    private static final String MODEL_ATT_POSITION_LIST = "positionList";
    
    /** request mapping value for save or update position. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdatePosition.htm";
    
    /** request mapping value for delete position. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeletePosition.htm";
    
    /** request attribute for positionId. */
    private static final String REQ_POSITIONID = "positionId";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY = "REF.UI.POSITION.DESCRIPTION.EXIST";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_DELETE = "REF.UI.POSITION.DELETE";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** Holds commonService instance of {@link CommonService}. */
    private CommonService commonService;
    
    /** Holds positionValidator object {@link PositionValidator}. */
    private PositionValidator positionValidator;
    
    /** model attribute of edit pane add remove. */
    private static final String EDIT_PANE = "editPane";
    
    /** model attribute of selected club society list. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /**
     * Set the positionValidator to inject the validator.
     * 
     * @param positionValidatorValue the positionValidator to set
     */
    public void setPositionValidator(PositionValidator positionValidatorValue) {
    
        this.positionValidator = positionValidatorValue;
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
     * Handle GET requests for Position_details view.
     * 
     * @param model - {@link ModelMap}
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showPositionDetail(ModelMap model) {
    
        Position position = (Position) model.get(MODEL_ATT_POSITION);
        
        if (position == null) {
            position = new Position();
        }
        model.addAttribute(MODEL_ATT_POSITION, position);
        
        return VIEW_GET_MANAGE_POSITION;
    }
    
    /**
     * This method is to handle Add/Edit position details.
     * 
     * @param position - {@link Position}.
     * @param request - {@link HttpServletRequest}
     * @param model {@link ModelMap}
     * @param bindingResult {@link BindingResult}
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - throw this
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdatePosition(@ModelAttribute(MODEL_ATT_POSITION) Position position,
            BindingResult bindingResult, HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        // validate position object
    	
    	Position selectedObj = null;
        positionValidator.validate(position, bindingResult);
        
        if (bindingResult.hasErrors()) {
        	selectedObj = commonService.findPositionById(position.getPositionId());
        	model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
        	model.addAttribute(EDIT_PANE, EDIT_PANE);
            return VIEW_GET_MANAGE_POSITION;
        } else {
            
            String strPosition = position.getDescription().trim();
            
            // If the Position is already exist, populate a message.
            if (commonService.isExistPosition(position)) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
                
                if(position.getPositionId() != 0){
                	
                	selectedObj = commonService.findPositionById(position.getPositionId());
                	model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                }
                
                model.addAttribute(MESSAGE, message);
                model.addAttribute(EDIT_PANE, EDIT_PANE);
                return VIEW_GET_MANAGE_POSITION;
            } else {
                position.setDescription(strPosition);
                
                // If the position id is zero, it's an new entry.
                if (position.getPositionId() == 0) {
                    commonService.createPosition(position);
                } else {
                    commonService.updatePosition(position);
                }
            }
        }
        return VIEW_POST_MANAGE_POSITION;
    }
    
    /**
     * Delete a position from reference module.
     * 
     * @param request - {@link HttpServletRequest}
     * @param model - {@link ModelMap}
     * @throws AkuraAppException - Detailed exception
     * @return name of the view.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deletePosition(HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        try {
            int positionId = Integer.parseInt(request.getParameter(REQ_POSITIONID));
            commonService.deletePosition(positionId);
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                Position newPosition = new Position();
                model.addAttribute(MODEL_ATT_POSITION, newPosition);
                model.addAttribute(MESSAGE, message);
                
                return VIEW_GET_MANAGE_POSITION;
            }
        }
        return VIEW_POST_MANAGE_POSITION;
    }
    
    /**
     * Method is to return Position list.
     * 
     * @return list of Position
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_POSITION_LIST)
    public List<Position> populatePositionList() throws AkuraAppException {
    
        return SortUtil.sortPositionList(commonService.getPositionList());
    }
}
