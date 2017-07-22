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

import com.virtusa.akura.api.dto.DonationType;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.DonationTypeValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageClubSocietyController is to handle Add/Edit/Delete/View operations for Donation type reference
 * module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageDonationTypeController {
    
    /** string constant for holding URL `/saveOrUpdateDonationType.htm`. */
    private static final String SAVE_OR_UPDATE_DONATION_TYPE = "/saveOrUpdateDonationType.htm";
    
    /** string constant for holding URL `/DeleteDonationType.htm`. */
    private static final String DELETE_DONATION_TYPE = "/DeleteDonationType.htm";
    
    /** string constant for holding error key `REF.UI.DOANTIONTYPE.DESCRIPTION.EXIST`. */
    private static final String REF_UI_DOANTIONTYPE_DESCRIPTION_EXIST = "REF.UI.DOANTIONTYPE.DESCRIPTION.EXIST";
    
    /** string constant for holding error key `REF.UI.DOANTIONTYPE.DELETE`. */
    private static final String REF_UI_DOANTIONTYPE_DELETE = "REF.UI.DOANTIONTYPE.DELETE";
    
    /** view get method Manage Donation Type. */
    private static final String VIEW_GET_MANAGE_DONATION_TYPE = "reference/manageDonationType";
    
    /** model attribute of DonationType list. */
    private static final String MODEL_ATT_DONATION_TYPE_LIST = "DonationTypeList";
    
    /** model attribute of Sport list. */
    private static final String MODEL_ATT_DONATION_TYPE = "donationType";
    
    /** model attribute of message. */
    private static final String MODEL_ATT_MESSAGE = "message";
    
    /** model attribute of selected sport object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** model attribute of showPanel. */
	private static final String MODEL_ATT_SHOW_PANEL = "showPanel";
    
    /** view post method create sport. */
    private static final String VIEW_POST_MANAGE_DONATION_TYPE = "redirect:manageDonationType.htm";
    
    /** commonService attribute for holding CommonService. */
    private CommonService commonService;
    
    /** String constant TRUE. */
	private static final String TRUE = "TRUE";
    
    /** DonationTypeValidator attribute for holding donationTypeValidator. */
    private DonationTypeValidator donationTypeValidator;
    
    /**
     * gets the DonationTypeValidator object.
     * 
     * @return DonationTypeValidator object
     */
    public DonationTypeValidator getDonationTypeValidator() {

        return donationTypeValidator;
    }
    
    /**
     * sets the DonationTypeValidator object.
     * 
     * @param validator the DonationTypeValidator to set
     */
    public void setDonationTypeValidator(DonationTypeValidator validator) {

        this.donationTypeValidator = validator;
    }
    
    /**
     * gets the CommonService object.
     * 
     * @return CommonService object
     */
    public CommonService getCommonService() {

        return commonService;
    }
    
    /**
     * sets the CommonService object.
     * 
     * @param service the CommonService to set
     */
    public void setCommonService(CommonService service) {

        this.commonService = service;
    } 
    
    /**
     * handle GET requests for Club Donation_type view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showDonationTypeDetail(ModelMap model) {

        DonationType donationType = (DonationType) model.get(MODEL_ATT_DONATION_TYPE);
        
        if (donationType == null) {
            donationType = new DonationType();
        }
        
        model.addAttribute(MODEL_ATT_DONATION_TYPE, donationType);
        return VIEW_GET_MANAGE_DONATION_TYPE;
    }
    
    /**
     * Create or update Donation Type object details.
     * 
     * @param donationType - DonationType object.
     * @param bindingResult - BindingResult
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when save or update a Sport instance.
     */
    @RequestMapping(value = SAVE_OR_UPDATE_DONATION_TYPE, method = RequestMethod.POST)
    public String saveOrUpdateDonationType(@ModelAttribute(MODEL_ATT_DONATION_TYPE) DonationType donationType,
            BindingResult bindingResult, HttpServletRequest request, ModelMap model) throws AkuraAppException {

        String strMessage = null;
        DonationType selectedObject = null;
        String strDescription = donationType.getDescription().trim();
        String strDonationTypeId = String.valueOf(donationType.getDonationTypeId());
        
        // validations
        donationTypeValidator.validate(donationType, bindingResult);
        
        if (bindingResult.hasErrors()) {
        	model.addAttribute(MODEL_ATT_SHOW_PANEL, TRUE);
        	model.addAttribute(MODEL_ATT_SELECTED_OBJECT, donationType);
            return VIEW_GET_MANAGE_DONATION_TYPE;
        } else {
            
            // edit
            if (!strDonationTypeId.equals("0")) {
                int donationTypeId = Integer.parseInt(strDonationTypeId);
                selectedObject = commonService.findDonationType(donationTypeId);
                
                // if this donation type already not exist editing.
                if ((commonService.isExistsDonationType(strDescription) && 
                        selectedObject.getDescription().equals(strDescription))
                        || !commonService.isExistsDonationType(strDescription)) {
                    selectedObject.setDescription(strDescription);
                    commonService.modifyDonationType(donationType);
                } else {
                    
                    // donation type already exist.
                    strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_DOANTIONTYPE_DESCRIPTION_EXIST);
                    model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                    model.addAttribute(MODEL_ATT_SELECTED_OBJECT, donationType);
                    model.addAttribute(MODEL_ATT_SHOW_PANEL, TRUE);
                    return VIEW_GET_MANAGE_DONATION_TYPE;
                }
            } else {
                
                // add new
                if (!commonService.isExistsDonationType(strDescription)) {
                    commonService.addDonationType(donationType);
                } else {
                    strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_DOANTIONTYPE_DESCRIPTION_EXIST);
                    model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                    model.addAttribute(MODEL_ATT_SHOW_PANEL, TRUE);
                    model.addAttribute(MODEL_ATT_SELECTED_OBJECT, donationType);
                    return VIEW_GET_MANAGE_DONATION_TYPE;
                }
            }
            
            return VIEW_POST_MANAGE_DONATION_TYPE;
        }
    }
    
    /**
     * Delete DonationType details.
     * 
     * @param donationType - DonationType object.
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when deleting a Sport instance.
     */
    @RequestMapping(value = DELETE_DONATION_TYPE, method = RequestMethod.POST)
    public String deleteDonationType(@ModelAttribute(MODEL_ATT_DONATION_TYPE) DonationType donationType,
            HttpServletRequest request, ModelMap model) throws AkuraAppException {

        try {
            commonService.deleteDonationType(donationType);
            return VIEW_POST_MANAGE_DONATION_TYPE;
        } catch (AkuraAppException e) {
            
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_DOANTIONTYPE_DELETE);
                DonationType newDonationType = new DonationType();
                List<DonationType> donationTypeList = commonService.viewAllDonationType();
                model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                model.addAttribute(MODEL_ATT_DONATION_TYPE, newDonationType);
                model.addAttribute(MODEL_ATT_DONATION_TYPE_LIST, donationTypeList);
                return VIEW_GET_MANAGE_DONATION_TYPE;
            } else {
                throw e;
            }
        }
    }
    
    /**
     * Method is to return Donation Type list.
     * 
     * @return list of Donation Type
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_DONATION_TYPE_LIST)
    public List<DonationType> populateDonationTypeList() throws AkuraAppException {

        return SortUtil.sortDonationTypeList(commonService.viewAllDonationType());
    }
    
}
