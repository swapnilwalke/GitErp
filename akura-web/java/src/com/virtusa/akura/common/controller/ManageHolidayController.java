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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.HolidayValidator;
import com.virtusa.akura.util.DateUtil;

/**
 * ManageHolidayController is to handle Add/Edit/Delete/View operations for Holiday reference module.
 *
 * @author Virtusa Corporation
 */
@Controller
public class ManageHolidayController {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(ManageHolidayController.class);

    /** view get method holiday. */
    private static final String VIEW_GET_CREATE_HOLIDAY = "reference/manageHoliday";

    /** string to hold request mapping for save or update Holiday. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateHoliday.htm";

    /** string to hold request mapping for delete Holiday. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteHoliday.htm";

    /** string to hold request mapping for populate Holiday. */
    private static final String REQ_MAP_VALUE_POPULATE = "/populateHolidayData.htm";

    /** string to hold model attribute of holiday. */
    private static final String MODEL_ATT_HOLIDAY = "holiday";

    /** model attribute of Holiday list. */
    private static final String MODEL_ATT_HOLIDAY_LIST = "holidayList";

    /** model attribute of selected holiday object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";

    /** model attribute of message. */
    private static final String MODEL_ATT_MESSAGE = "message";

    /** model attribute of selected year. */
    private static final String MODEL_ATT_DATE = "selectedYr";

    /** key to hold error message to log. */
    private static final String ERROR_TO_LOG = "Error while processiong manageHoliday controller-->";

    /** key to hold string of year end date. */
    private static final String LAST_DATE = "-12-31";

    /** key to hold string of year start date. */
    private static final String FIRST_DATE = "-01-01";

    /** key to hold request parameter of selected year. */
    private static final String SELECTED_YEAR = "selectedYear";

    /** key to hold date format. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /** Model attribute of "showEditSection". */
    private static final String SHOW_EDIT_SECTION = "showEditSection";

    /** key to hold string of request success. */
    private static final String REQUEST_SUCCESS = "success";

    /** CommonService attribute for holding commonService. */
    private CommonService commonService;

    /** HolidayValidator attribute for holding holidayValidator. */
    private HolidayValidator holidayValidator;

    /**
     * sets the CommonService object.
     *
     * @param commonServiceObj the commonService to set
     */
    public void setCommonService(CommonService commonServiceObj) {

        this.commonService = commonServiceObj;
    }

    /**
     * sets the HolidayValidator object.
     *
     * @param holidayValidatorObj the holidayValidator to set
     */
    public void setHolidayValidator(HolidayValidator holidayValidatorObj) {

        this.holidayValidator = holidayValidatorObj;
    }

    /**
     * ManageHolidayController constructor.
     */
    public ManageHolidayController() {

    }

    /**
     * intiBinder method is to bind date class with the date format.
     *
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.initDirectFieldAccess();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    /**
     * handle GET requests for Holiday_details view.
     *
     * @param model - ModelMap
     * @param session - HttpSession
     * @param request - HttpServletRequest
     * @return the name of the view.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showHolidayDetail(ModelMap model, HttpSession session, HttpServletRequest request)
            throws AkuraAppException {

        Holiday holiday = (Holiday) model.get(MODEL_ATT_HOLIDAY);
        if (holiday == null) {
            holiday = new Holiday();
        }
        model.addAttribute(MODEL_ATT_HOLIDAY, holiday);
        request.setAttribute("success", Boolean.FALSE);

        return populateHolidayData(request, session, model);
    }

    /**
     * Create or update Holiday object details.
     *
     * @param holiday - Holiday object.
     * @param bindingResult - BindingResult
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - The exception details that occurred when save or update a Holiday instance.
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateHoliday(@ModelAttribute(MODEL_ATT_HOLIDAY) Holiday holiday, BindingResult bindingResult,
            HttpServletRequest request, HttpSession session, ModelMap model) throws AkuraAppException {

        String returnPath = null;
        String strMessage = null;
        Holiday selectedObj = null;

        // validate holiday object.
        holidayValidator.validate(holiday, bindingResult);
        if (bindingResult.hasErrors()) {
            if (holiday.getHolidayId() != 0) {
                selectedObj = commonService.findHolidayById(holiday.getHolidayId());
                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
            }

            if (holiday.getEndDate() != null) {
                request.setAttribute(REQUEST_SUCCESS, Boolean.TRUE);
            }

            model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
            returnPath = populateHolidayData(request, session, model);

        } else {
            // check the existence of holiday object
            // if (commonService.isExistHoliday(holiday)) {

            if (commonService.isExistHolidayByDate(holiday)) {

                if (holiday.getHolidayId() != 0) {
                    // allow to edit the same holiday object
                    if (commonService.getHolidayListWithInDateRange(holiday)) {

                        commonService.updateHoliday(holiday);

                    } else {
                        strMessage = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.HOLIDAY_EXIST);
                        model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                        model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                        selectedObj = commonService.findHolidayById(holiday.getHolidayId());
                        model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                    }

                } else {
                    strMessage = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.HOLIDAY_EXIST);
                    model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                    model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                }

                returnPath = populateHolidayData(request, session, model);

            } else {
                if (holiday.getHolidayId() == 0) {
                    commonService.saveHoliday(holiday);
                } else {
                    commonService.updateHoliday(holiday);
                }

                returnPath = populateHolidayData(request, session, model);
            }
        }
        return returnPath;
    }

    /**
     * Delete Holiday details.
     *
     * @param holiday - Holiday object.
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - The exception details that occurred when deleting a Holiday instance.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteHoliday(@ModelAttribute(MODEL_ATT_HOLIDAY) Holiday holiday, HttpServletRequest request,
            HttpSession session, ModelMap model) throws AkuraAppException {

        String returnPath = null;
        String strMessage = null;

        try {

            commonService.deleteHoliday(holiday.getHolidayId());
            returnPath = populateHolidayData(request, session, model);

        } catch (AkuraAppException e) {

            if (e.getCause() instanceof DataIntegrityViolationException) {

                strMessage = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.CANT_DELETE_HOLIDAY);

                Holiday newHoliday = new Holiday();
                List<Holiday> holidayList = commonService.findHolidays();

                model.addAttribute(MODEL_ATT_HOLIDAY, newHoliday);
                model.addAttribute(MODEL_ATT_HOLIDAY_LIST, holidayList);
                model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                returnPath = populateHolidayData(request, session, model);

            } else {
                throw e;
            }

        }
        return returnPath;
    }

    /**
     * populate data for holiday page for a selected year.
     *
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    @RequestMapping(value = REQ_MAP_VALUE_POPULATE, method = RequestMethod.POST)
    public String populateHolidayData(HttpServletRequest request, HttpSession session, ModelMap model)
            throws AkuraAppException {

        try {

            String strYr = null;
            // get the year of holiday
            if (request.getParameter(SELECTED_YEAR) != null) {
                strYr = request.getParameter(SELECTED_YEAR);
            } else {
                Date currentDate = new Date();
                strYr = DateUtil.getStringYear(currentDate);
            }

            String strStartDate = strYr + FIRST_DATE;
            String strEndDate = strYr + LAST_DATE;

            Date startDateToSearch = DateUtil.getParseDate(strStartDate);
            Date endDateToSearch = DateUtil.getParseDate(strEndDate);

            List<Holiday> holidayList = commonService.findHolidayByYear(startDateToSearch, endDateToSearch);

            Holiday newHoliday = (Holiday) model.get(MODEL_ATT_HOLIDAY);
            if (newHoliday == null) {
                newHoliday = new Holiday();
            }
            model.addAttribute(MODEL_ATT_HOLIDAY, newHoliday);
            model.addAttribute(MODEL_ATT_HOLIDAY_LIST, holidayList);
            model.addAttribute(MODEL_ATT_DATE, strYr);

        } catch (AkuraAppException e) {
            LOG.error(ERROR_TO_LOG + e.toString());
            throw e;
        }
        return VIEW_GET_CREATE_HOLIDAY;
    }

}
