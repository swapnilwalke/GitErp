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

import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.api.dto.StaffServiceCategory;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.StaffServiceCategoryValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageStaffServiceController is to handle Add/Edit/Delete/View operations for StaffServiceCategory
 * reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageStaffServiceCategoryController {
    
    /** The Constant MODEL_ATT_STAFF_SERVICE_CATEGORY_LIST. */
    private static final String MODEL_ATT_STAFF_SERVICE_CATEGORY_LIST = "staffServiceCategoryList";
    
    /** view get method create staff service category. */
    private static final String VIEW_GET_STAFF_SERVICE_CATEGORY = "reference/manageStaffServiceCategory";
    
    /** view post method create staff service category. */
    private static final String VIEW_POST_CREAT_STAFF_SERVICE_CATEGORY = "redirect:manageStaffServiceCategory.htm";
    
    /** model attribute of staff service category. */
    private static final String MODEL_ATT_STAFF_SERVICE_CATEGORY = "staffServiceCategory";
    
    /** request mapping value for delete Staff Service Category. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteStaffServiceCategory.htm";
    
    /** model attribute of message. */
    private static final String MODEL_ATT_MESSAGE = "message";
    
    /** request mapping value for save or update Staff Service category. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/manageSaveOrUpdateStaffServiceCategory.htm";
    
    /** attribute for holding error message when unable to delete Staff Service Category. */
    private static final String STAFF_SERVICE_CATEGORY_DELETE_ERR = "REF.UI.STAFF.SERVICE.CATEGORY.DELETE";
    
    /** model attribute of selected staff category list. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** attribute for holding error message when Staff Service Category exist. */
    private static final String STAFF_SERVICE_CATEGORY_EXIST = "REF.UI.RECORD.EXIST.ERROR";
    
    /** CommonService attribute for holding commonService . */
    private StaffCommonService staffCommonService;
    
    /** StaffServiceCategoryValidator attribute for holding staffServiceCategory. */
    private StaffServiceCategoryValidator staffServiceCategoryValidator;
    
    /** model attribute of Selected Staff Service Category. */
    private static final String MODEL_ATT_SELECTED_STAFF_SERVICE_CATEGORY = "selectedObjId";
    
    /** request parameter for hold panelDisplay state. */
    private static final String DISPLAY_PANEL = "displayPanel";
    
    /**
     * ManageStaffCategoryController Constructor.
     */
    public ManageStaffServiceCategoryController() {

    }
    
    /**
     * Gets the StaffCommonService Object.
     * 
     * @return commonService-
     */
    public StaffCommonService getStaffCommonService() {

        return staffCommonService;
    }
    
    /**
     * sets the StaffCommonService object.
     * 
     * @param staffCommonServiceObj for the StaffCommonService
     */
    public void setStaffCommonService(StaffCommonService staffCommonServiceObj) {

        staffCommonService = staffCommonServiceObj;
    }
    
    /**
     * Add or update the Staff Service Category.
     * 
     * @param staffServiceCategory - StaffServiceCategory
     * @param bindingResult - BindingResult.
     * @param model - link ModelMap
     * @return name of the view which redirected to
     * @param request - HttpServletRequest
     * @throws AkuraAppException - if error occurs when save or update a StaffServiceCategory instance.
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateStaffServiceCategory(
            @ModelAttribute(MODEL_ATT_STAFF_SERVICE_CATEGORY) StaffServiceCategory staffServiceCategory,
            BindingResult bindingResult, ModelMap model, HttpServletRequest request) throws AkuraAppException {

        staffServiceCategoryValidator.validate(staffServiceCategory, bindingResult);
        
        try {
            if (bindingResult.hasErrors()) {
                
                model.addAttribute(MODEL_ATT_SELECTED_STAFF_SERVICE_CATEGORY, staffServiceCategory.getServiceId());
                request.setAttribute(DISPLAY_PANEL, true);
                return VIEW_GET_STAFF_SERVICE_CATEGORY;
            } else {
                int intStaffServiceCategoryId = staffServiceCategory.getServiceId();
                // replace the spaces with only one space in staff service category Description.
                staffServiceCategory.setDescription(staffServiceCategory.getDescription().trim()
                        .replaceAll("\\s+", " "));
                
                if (staffCommonService.isExistsStaffServiceCategory(staffServiceCategory.getDescription())) {
                    
                    // give error message when adding to add exist staff service category
                    if (intStaffServiceCategoryId == 0
                            || (!(staffCommonService.findStaffServiceCategoryById(intStaffServiceCategoryId)
                                    .getDescription().equalsIgnoreCase(staffServiceCategory.getDescription())))) {
                        
                        String strMessage = new ErrorMsgLoader().getErrorMessage(STAFF_SERVICE_CATEGORY_EXIST);
                        model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                        model.addAttribute(MODEL_ATT_SELECTED_STAFF_SERVICE_CATEGORY,
                                staffServiceCategory.getServiceId());
                        request.setAttribute(DISPLAY_PANEL, true);
                        
                        // update if change same description
                    } else if (intStaffServiceCategoryId != 0) {
                        staffCommonService.editStaffServiceCategory(staffServiceCategory);
                        return VIEW_POST_CREAT_STAFF_SERVICE_CATEGORY;
                    } else {
                        model.addAttribute(MODEL_ATT_SELECTED_OBJECT, null);
                    }
                    return VIEW_GET_STAFF_SERVICE_CATEGORY;
                    
                } else {
                    
                    if (intStaffServiceCategoryId == 0) {
                        staffCommonService.addStaffServiceCategory(staffServiceCategory);
                    } else {
                        staffCommonService.editStaffServiceCategory(staffServiceCategory);
                    }
                }
            }
        } catch (AkuraAppException ex) {
            throw ex;
        }
        return VIEW_POST_CREAT_STAFF_SERVICE_CATEGORY;
    }
    
    /**
     * gets StaffServiceCategoryValidator object.
     * 
     * @return staffServiceCategoryValidator
     */
    public StaffServiceCategoryValidator getStaffServiceCategoryValidator() {

        return staffServiceCategoryValidator;
    }
    
    /**
     * sets the StaffServiceCategoryValidator object.
     * 
     * @param staffServiceCategoryValidatorObj for the StaffSeriviceCategoryValidator to set
     */
    public void setStaffServiceCategoryValidator(StaffServiceCategoryValidator staffServiceCategoryValidatorObj) {

        staffServiceCategoryValidator = staffServiceCategoryValidatorObj;
    }
    
    /**
     * Method to return StaffServiceCategoryList.
     * 
     * @return list of StaffServiceCategory
     * @throws AkuraAppException - Detailed Exception
     */
    @ModelAttribute(MODEL_ATT_STAFF_SERVICE_CATEGORY_LIST)
    public List<StaffServiceCategory> populateStaffServiceCategoryList() throws AkuraAppException {

        return SortUtil.sortStaffSeriviceCategoryList(staffCommonService.getStaffServiceCategoryList());
    }
    
    /**
     * Show Staff Service Category detail.
     * 
     * @param model the model
     * @return the string
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showStaffServiceCategoryDetails(ModelMap model) {

        StaffServiceCategory staffServiceCategory = (StaffServiceCategory) model.get(MODEL_ATT_STAFF_SERVICE_CATEGORY);
        
        if (staffServiceCategory == null) {
            staffServiceCategory = new StaffServiceCategory();
        }
        
        model.addAttribute(MODEL_ATT_STAFF_SERVICE_CATEGORY, staffServiceCategory);
        return VIEW_GET_STAFF_SERVICE_CATEGORY;
    }
    
    /**
     * Delete selected Staff Service Category details.
     * 
     * @param staffServiceCategory - StaffServiceCategory object.
     * @param model - ModelMap
     * @return name of the view which is redirected to
     * @throws AkuraAppException - if an error occur when deleting a Staff Service Category.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteStaffServiceCategory(
            @ModelAttribute(MODEL_ATT_STAFF_SERVICE_CATEGORY) StaffServiceCategory staffServiceCategory, ModelMap model)
            throws AkuraAppException {

        String strMessage = null;
        
        try {
            staffCommonService.deleteStaffServiceCategory(staffServiceCategory.getServiceId());
            return VIEW_POST_CREAT_STAFF_SERVICE_CATEGORY;
            
        } catch (AkuraAppException ex) {
            
            if (ex.getCause() instanceof DataIntegrityViolationException) {
                
                strMessage = new ErrorMsgLoader().getErrorMessage(STAFF_SERVICE_CATEGORY_DELETE_ERR);
                StaffServiceCategory newStaffSerCategory = new StaffServiceCategory();
                model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                model.addAttribute(MODEL_ATT_STAFF_SERVICE_CATEGORY, newStaffSerCategory);
                
                return VIEW_GET_STAFF_SERVICE_CATEGORY;
                
            } else {
                throw ex;
            }
        }
    }
}
