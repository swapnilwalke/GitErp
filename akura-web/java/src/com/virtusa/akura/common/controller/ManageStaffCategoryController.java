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

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.staff.validator.StaffCategoryValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * Staff controller for add, edit and delete staff categories in reference module.
 *
 * @author Virtusa Corporation
 */
@Controller
public class ManageStaffCategoryController {

	/** model attribute of showPanel. */
	private static final String MODEL_ATT_SHOW_PANEL = "showPanel";
	
    /** attribute for holding message. */
    private static final String MESSAGE = "message";

    /** model attribute of staff Category List. */
    private static final String STAFF_CATEGORY_LIST = "staffCategoryList";

    /** model attribute staff Category. */
    private static final String STAFF_CATEGORY = "staffCategory";

    /** attribute for holding error message key. */
    private static final String REF_UI_STAFFCATEGORY_DELETE = "REF.UI.STAFFCATEGORY.DELETE";

    /** attribute for holding error message key. */
    private static final String REF_UI_STAFFCATEGORY_DESCRIPTION = "REF.UI.STAFFCATEGORY.DESCRIPTION";

    /** view post method manageStaffCategory. */
    private static final String REDIRECT_MANAGE_STAFF_CATEGORY_HTM = "redirect:manageStaffCategory.htm";

    /** view get method manage Staff Category. */
    private static final String ADMIN_MANAGE_STAFF_CATEGORY = "reference/manageStaffCategory";

    /** request mapping value for manage delete staff category. */
    private static final String MANAGE_DELETE_STAFF_CATEGORY_HTM = "/manageDeleteStaffCategory.htm";

    /** request mapping value for manage save or update staff category. */
    private static final String MANAGE_SAVE_OR_UPDATE_STAFF_CATEGORY_HTM = "/manageSaveOrUpdateStaffCategory.htm";

    /** request mapping value for manage staff category. */
    private static final String MANAGE_STAFF_CATEGORY_HTM = "/manageStaffCategory.htm";
    
    /** String constant TRUE. */
	private static final String TRUE = "TRUE";

    /** attribute for holding space. */
    private static final String REPLACE_STRING = " ";

    /** attribute for holding regular expression. */
    private static final String CHECK_STRING = "( )+";

    /**
     * Represents an instance of staffCommonService.
     */
    private StaffCommonService staffCommonService;

    /**
     * Represents an instance of StaffCategoryValidator.
     */
    private StaffCategoryValidator staffCategoryValidator;
    
    /**
     * Sets the staffcommon service.
     *
     * @param objstaffCommonService the commonService to set
     */
    public void setStaffCommonService(StaffCommonService objstaffCommonService) {
    
        this.staffCommonService = objstaffCommonService;
    }

    /**
     * Sets an instance of StaffCategoryValidator.
     *
     * @param staffCategoryValidatorVal - the relevant instance of StaffCategoryValidator
     */
    public void setStaffCategoryValidator(StaffCategoryValidator staffCategoryValidatorVal) {

        staffCategoryValidator = staffCategoryValidatorVal;
    }

    /**
     * Initializes the reference data that is to be loaded on the requested view page.
     *
     * @param model - a HashMap that contains information of the StaffCategory
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(MANAGE_STAFF_CATEGORY_HTM)
    public final String manageStaffCategory(final ModelMap model) throws AkuraAppException {

        StaffCategory staffCategory = new StaffCategory();
        List<StaffCategory> staffCategoryList = staffCommonService.getAllStaffCategories();
        SortUtil.sortStaffCategoryList(staffCategoryList);
        model.addAttribute(STAFF_CATEGORY, staffCategory);
        model.addAttribute(STAFF_CATEGORY_LIST, staffCategoryList);
        return ADMIN_MANAGE_STAFF_CATEGORY;
    }

    /**
     * Saves or updates the Staff Category.
     *
     * @param model - a HashMap that contains information of the StaffCategory
     * @param staffCategory - an instance of StaffCategory
     * @param result - containing list of errors and nationality instance to which data was bound
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when saving or updating a StaffCategory.
     */
    @RequestMapping(value = MANAGE_SAVE_OR_UPDATE_STAFF_CATEGORY_HTM, method = RequestMethod.POST)
    public final String saveOrUpdateStaffCategory(@ModelAttribute(STAFF_CATEGORY) final StaffCategory staffCategory,
            BindingResult result, final ModelMap model) throws AkuraAppException {

        String message;
        try {
        	
            List<StaffCategory> staffCategoryList = staffCommonService.getAllStaffCategories();
            SortUtil.sortStaffCategoryList(staffCategoryList);
            model.addAttribute(STAFF_CATEGORY_LIST, staffCategoryList);
            staffCategoryValidator.validate(staffCategory, result);
            
            if (result.hasErrors()) {
            	model.addAttribute(STAFF_CATEGORY, staffCategory);
            	model.addAttribute(MODEL_ATT_SHOW_PANEL, TRUE);
                return ADMIN_MANAGE_STAFF_CATEGORY;
            } else {
            	
                int id = staffCategory.getCatogaryID();
                String description = staffCategory.getDescription().trim();
                description = description.replaceAll(CHECK_STRING, REPLACE_STRING);
                
                if (id > 0) {
                    // if the description equals to the description of the relevant
                    // object updates the object. Otherwise checks weather the given
                    // description is already in the database.If it exists returns an
                    // error message.
                    boolean isAcademic = staffCategory.isAcademic();
                    StaffCategory findStaffCategory = staffCommonService.getStaffCategory(id);
                    if (!findStaffCategory.getDescription().equalsIgnoreCase(description)) {
                        if (isExistsStaffCategory(description)) {
                            message = new ErrorMsgLoader().getErrorMessage(REF_UI_STAFFCATEGORY_DESCRIPTION);
                            model.addAttribute(STAFF_CATEGORY, staffCategory);
                        	model.addAttribute(MODEL_ATT_SHOW_PANEL, TRUE);
                            model.addAttribute(MESSAGE, message);
                            return ADMIN_MANAGE_STAFF_CATEGORY;
                        }

                    }
                    findStaffCategory.setAcademic(isAcademic);
                    findStaffCategory.setDescription(description);
                    staffCommonService.updateStaffCategory(findStaffCategory);
                    
                } else {
                    message = new ErrorMsgLoader().getErrorMessage(REF_UI_STAFFCATEGORY_DESCRIPTION);

                    if (isExistsStaffCategory(description)) {
                    	model.addAttribute(STAFF_CATEGORY, staffCategory);
                    	model.addAttribute(MODEL_ATT_SHOW_PANEL, TRUE);
                        model.addAttribute(MESSAGE, message);
                        return ADMIN_MANAGE_STAFF_CATEGORY;
                    }

                    if (!isExistsStaffCategory(description)) {
                        staffCategory.setDescription(description);
                        staffCommonService.addStaffCategory(staffCategory);
                    }
                }
                return REDIRECT_MANAGE_STAFF_CATEGORY_HTM;
            }
        } catch (AkuraAppException e) {
            throw e;
        }
    }

    /**
     * Check whether Staff Category is already exist.
     *
     * @param description - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsStaffCategory(String description) throws AkuraAppException {

        boolean isExists = false;
        List<StaffCategory> staffCategoryList = staffCommonService.getAllStaffCategories();
        for (StaffCategory searchStaffCategory : staffCategoryList) {
            isExists = searchStaffCategory.getDescription().equalsIgnoreCase(description);
            if (isExists) {
                break;
            }
        }
        return isExists;
    }

    /**
     * Deletes the relevant StaffCategory object.
     *
     * @param staffCategory - an instance of StaffCategory
     * @param model - a HashMap that contains information of the StaffCategory
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a StaffCategory instance.
     */
    @RequestMapping(MANAGE_DELETE_STAFF_CATEGORY_HTM)
    public final String deleteStaffCategory(@ModelAttribute(STAFF_CATEGORY) final StaffCategory staffCategory,
            ModelMap model) throws AkuraAppException {

        String message;
        try {
            int id = staffCategory.getCatogaryID();
            StaffCategory findStaffCategory = staffCommonService.getStaffCategory(id);
            staffCommonService.deleteStaffCategory(findStaffCategory);
            return REDIRECT_MANAGE_STAFF_CATEGORY_HTM;
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                message = new ErrorMsgLoader().getErrorMessage(REF_UI_STAFFCATEGORY_DELETE);
                StaffCategory newStaffCategory = new StaffCategory();
                List<StaffCategory> staffCategoryList = staffCommonService.getAllStaffCategories();
                SortUtil.sortStaffCategoryList(staffCategoryList);
                model.addAttribute(STAFF_CATEGORY_LIST, staffCategoryList);
                model.addAttribute(MESSAGE, message);
                model.addAttribute(STAFF_CATEGORY, newStaffCategory);
                return ADMIN_MANAGE_STAFF_CATEGORY;
            } else {
                throw e;
            }
        }
    }
}
