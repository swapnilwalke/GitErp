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

import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.api.dto.SportCategory;
import com.virtusa.akura.api.dto.SportSub;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.SportCategoryValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageSportController is to handle Add/Edit/Delete/View operations for SportCategory reference module.
 *
 * @author Virtusa Corporation
 */
@Controller
public class ManageSportCategoryController {

    /** error message for delete category. */
    private static final String ERROR_MESSAGE_SPORT_CATEGORY_DELETE = "REF.UI.SPORT.CATEGORY.DELETE";

    /** error message for exists category. */
    private static final String ERROR_MESSAGE_CATEGORY_DESCRIPTION_EXIST = "REF.UI.RECORD.EXIST.ERROR";

    /** view get method create sportCategory. */
    private static final String VIEW_GET_CREATE_SPORT_CATEGORY = "reference/manageSportCategory";

    /** view post method manage sportCategory. */
    private static final String VIEW_POST_CREAT_SPORT_CATEGORY = "redirect:manageSportCategory.htm";

    /** request mapping value for save or update SportCategory. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateSportCategory.htm";

    /** request mapping value for delete SportCategory. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteSportCategory.htm";

    /** model attribute of SportCategory list. */
    private static final String MODEL_ATT_SPORT_CATEGORY_LIST = "sportCategoryList";

    /** model attribute of Sport list. */
    private static final String MODEL_ATT_SPORT_LIST = "sportList";

    /** model attribute of SportSub list. */
    private static final String MODEL_ATT_SPORT_SUB_LIST = "sportSubList";

    /** model attribute of SportCategory. */
    private static final String MODEL_ATT_SPORT_CATEGORY = "sportCategory";

    /** model attribute of message. */
    private static final String MODEL_ATT_MESSAGE = "message";

    /** model attribute of selected sport object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** model attribute of edit pane add remove. */
    private static final String EDIT_PANE = "editPane";

    /** CommonService attribute for holding commonService. */
    private CommonService commonService;

    /** SportCategoryValidator attribute for holding sportCategoryValidator. */
    private SportCategoryValidator sportCategoryValidator;
    


    /**
     * sets the SportCategoryValidator object.
     *
     * @param sportCategoryValidatorObj the sportCategoryValidator to set
     */
    public void setSportCategoryValidator(SportCategoryValidator sportCategoryValidatorObj) {

        this.sportCategoryValidator = sportCategoryValidatorObj;
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
     * ManageSportCategoryController constructor.
     */
    public ManageSportCategoryController() {

    }

    /**
     * handle GET requests for SportCategory_details view.
     *
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showSportCategoryDetail(ModelMap model) {

        SportCategory sportCategory = (SportCategory) model.get(MODEL_ATT_SPORT_CATEGORY);

        if (sportCategory == null) {
            sportCategory = new SportCategory();
        }

        model.addAttribute(MODEL_ATT_SPORT_CATEGORY, sportCategory);

        return VIEW_GET_CREATE_SPORT_CATEGORY;
    }

    /**
     * Method is to return SportCategory list.
     *
     * @return list of SportCategory
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_SPORT_CATEGORY_LIST)
    public List<SportCategory> populateSportCategoryList() throws AkuraAppException {

        return SortUtil.sortSportCategoriesList(commonService.getSportCategoriesList());
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

    /**
     * Create or update SportCategory object details.
     *
     * @param sportCategory - SportCategory object.
     * @param bindingResult - BindingResult
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when save or update a SportCategory instance.
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateSportCategory(@ModelAttribute(MODEL_ATT_SPORT_CATEGORY) SportCategory sportCategory,
            BindingResult bindingResult, HttpServletRequest request, ModelMap model) throws AkuraAppException {

        String strMessage = null;
        String returnPage = null;
        SportCategory selectedObj = null;

        // validations
        sportCategoryValidator.validate(sportCategory, bindingResult);

        if (bindingResult.hasErrors()) {
        	selectedObj = commonService.findSportCategoryById(sportCategory.getSportCategoryId());
        	model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
        	model.addAttribute(EDIT_PANE, EDIT_PANE);
            returnPage = VIEW_GET_CREATE_SPORT_CATEGORY;
        } else {

            int intSport = sportCategory.getSport().getSportId();
            int intSportSub = sportCategory.getSportSubCategory().getSportSubId();
            int intSportCategoryId = sportCategory.getSportCategoryId();

            
            // sportCategory already exist.
            if (commonService.isExistsSportCategory(intSport, intSportSub)) {
            	
            	SportCategory existObj = commonService.findSportCategoryById(intSportCategoryId);
            	
				if (intSportCategoryId != 0 && ((existObj.getSport().getSportId() == intSport)
						&& (existObj.getSportSubCategory().getSportSubId() == intSportSub))) {

					returnPage = VIEW_GET_CREATE_SPORT_CATEGORY;
            		
            	} else {
            		
	                strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_CATEGORY_DESCRIPTION_EXIST);
	                model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
	                
	                if (intSportCategoryId == 0) {                	
	                	model.addAttribute(EDIT_PANE, EDIT_PANE);                
	                }
	                if (intSportCategoryId != 0) {               	
	                    model.addAttribute(MODEL_ATT_SELECTED_OBJECT, sportCategory);
	                    model.addAttribute(EDIT_PANE, EDIT_PANE);
	                }
            	}
                returnPage = VIEW_GET_CREATE_SPORT_CATEGORY;

            } else {
                Sport sportObj = commonService.findSportById(intSport);
                SportSub sportSubObj = commonService.findSportSubById(intSportSub);

                sportCategory.setSport(sportObj);
                sportCategory.setSportSubCategory(sportSubObj);

                if (intSportCategoryId == 0) {
                    commonService.addSportCategory(sportCategory);
                } else {
                    commonService.editSportCategory(sportCategory);
                }
                returnPage = VIEW_POST_CREAT_SPORT_CATEGORY;
            }

        }
        return returnPage;
    }

    /**
     * Delete SportCategory details.
     *
     * @param sportCategory - SportCategory object.
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when deleting a SportCategory instance.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteSportCategory(@ModelAttribute(MODEL_ATT_SPORT_CATEGORY) SportCategory sportCategory,
            HttpServletRequest request, ModelMap model) throws AkuraAppException {

        String returnPage = null;

        try {
            commonService.deleteSportCategory(sportCategory.getSportCategoryId());
            returnPage = VIEW_POST_CREAT_SPORT_CATEGORY;
        } catch (AkuraAppException e) {

            if (e.getCause() instanceof DataIntegrityViolationException) {
                String strMessage = null;
                strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_SPORT_CATEGORY_DELETE);
                SportCategory newSportCategory = new SportCategory();
                List<SportCategory> sportCategoryList = commonService.getSportCategoriesList();
                model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                model.addAttribute(MODEL_ATT_SPORT_CATEGORY, newSportCategory);
                model.addAttribute(MODEL_ATT_SPORT_CATEGORY_LIST, sportCategoryList);
                returnPage = VIEW_GET_CREATE_SPORT_CATEGORY;
            } else {
                throw e;
            }
        }
        return returnPage;
    }
}
