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

package com.virtusa.akura.staff.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.ClubSociety;
import com.virtusa.akura.api.dto.Position;
import com.virtusa.akura.api.dto.Seminar;
import com.virtusa.akura.api.dto.SportCategory;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffClubSociety;
import com.virtusa.akura.api.dto.StaffExternalActivity;
import com.virtusa.akura.api.dto.StaffSeminar;
import com.virtusa.akura.api.dto.StaffSport;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.SortUtil;

/**
 * Controller class to handle all the actions related to staff extra curricular. <br>
 * Add/Edit/Delete/View club & society information of a staff <br>
 * Add/Edit/Delete/View sport information of a staff <br>
 * Add/Edit/Delete/View external activity information of a staff <br>
 * Add/Edit/Delete/View seminar information of a staff <br>
 *
 * @author Virtusa Corporation
 */

@Controller
public class StaffExtraCurricularController {

    /** The Constant SPORT_CATEGORIES_LIST. */
    private static final String SPORT_CATEGORIES_LIST = "sportCategoriesList";

    /** Holds Logger to log the errors. */
    private static final Logger LOG = Logger.getLogger(StaffExtraCurricularController.class);

    /** Holds jsp for view staff extra curricular. */
    private static final String REQ_MAP_VALUE_JSP_VIEW = "staff/staffExtraCurricular";

    /** Holds URL for view staff extra curricular. */
    private static final String REQ_MAP_VALUE_VIEW = "/staffExtraCurricular.htm";

    /** Holds URL for populate staff extra curricular. */
    private static final String REQ_MAP_VALUE_POPULATE_DATA = "/populateDataForSelectedYear.htm";

    /** Holds URL for save or update StaffClubSociety. */
    private static final String REQ_MAP_VALUE_SAVE_UPDATE_STAFFCLUBSOCIETY = "/saveOrUpdateStaffClubSociety";

    /** Holds URL for save or update StaffClubSociety. */
    private static final String REQ_MAP_VALUE_SAVE_UPDATE_STAFFEXTERNALACTIVITY = "/saveOrUpdateStaffExternalActivity";

    /** Holds URL for save or update StaffClubSociety. */
    private static final String REQ_MAP_VALUE_SAVE_UPDATE_STAFFSEMINAR = "/saveOrUpdateStaffSeminar";

    /** Holds URL for save or update StaffClubSociety. */
    private static final String REQ_MAP_VALUE_SAVE_UPDATE_STAFFSPORT = "/saveOrUpdateStaffSport";

    /** Holds URL for delete StaffClubSociety. */
    private static final String REQ_MAP_VALUE_DELETE_STAFFCLUBSOCIETY = "/deleteStaffClubSociety.htm";

    /** Holds URL for delete StaffClubSociety. */
    private static final String REQ_MAP_VALUE_DELETE_STAFFEXTERNALACTIVITY = "/deleteStaffExternalActivity.htm";

    /** Holds URL for delete StaffClubSociety. */
    private static final String REQ_MAP_VALUE_DELETE_SEMINAR = "/deleteStaffSeminar.htm";

    /** Holds URL for delete StaffClubSociety. */
    private static final String REQ_MAP_VALUE_DELETE_SPORT = "/deleteStaffSport.htm";

    /** Holds request parameter for selected year. */
    private static final String REQ_PAR_SELECTED_YEAR = "selectedYear";

    /** Holds request parameter for staff Club Society Id. */
    private static final String REQ_PAR_STAFFCLUBSOCIETYID = "staffClubSocietyId";

    /** Holds request parameter for staff Club Society Id. */
    private static final String REQ_PAR_STAFFEXTERNALACTIVITYID = "staffExternalActivityId";

    /** Holds request parameter for staff Club Society Id. */
    private static final String REQ_PAR_STAFFSEMINARID = "staffSeminarId";

    /** Holds request parameter for staff Club Society Id. */
    private static final String REQ_PAR_STAFFSPORTID = "staffSportId";

    /** Holds session attribute for staff Id. */
    private static final String REQ_PAR_STAFFID = "staffId";

    /** Holds attribute of ClubSocietyMessage for hold error messages related to club&society. */
    private static final String MODEL_ATT_CLUBSOCIETYMESSAGE = "ClubSocietyMessage";

    /** Holds attribute of ClubSocietyMessage for hold error messages related to club&society. */
    private static final String MODEL_ATT_EXTERNALACTIVITYMESSAGE = "ExternalActivityMessage";

    /** Holds attribute of ClubSocietyMessage for hold error messages related to club&society. */
    private static final String MODEL_ATT_SEMINARMESSAGE = "SeminarMessage";

    /** Holds attribute of ClubSocietyMessage for hold error messages related to club&society. */
    private static final String MODEL_ATT_SPORTMESSAGE = "SportMessage";

    /** Model attribute of staffClubSocieties. */
    private static final String MODEL_ATT_STAFF_CLUB_SOCIETIES = "staffClubSocieties";

    /** Model attribute of staffClubSocieties. */
    private static final String MODEL_ATT_STAFF_EXTERNAL_ACTIVITIES = "staffExternalActivities";

    /** Model attribute of staffSeminars. */
    private static final String MODEL_ATT_STAFF_SEMINARS = "staffSeminars";

    /** Model attribute of staffSeminars. */
    private static final String MODEL_ATT_STAFF_SPORTS = "staffSports";

    /** Model attribute of staffClubSociety. */
    private static final String MODEL_ATT_STAFFCLUBSOCIETY = "staffClubSociety";

    /** Model attribute of staffExternalActivity. */
    private static final String MODEL_ATT_STAFFEXTERNALACTIVITY = "staffExternalActivity";

    /** Model attribute of staffExternalActivity. */
    private static final String MODEL_ATT_STAFFSEMINAR = "staffSeminar";

    /** Model attribute of staffSport. */
    private static final String MODEL_ATT_STAFFSPORT = "staffSport";
    
    /** Model attribute of staffSport. */
    private static final String  DEPARTURE_DATE = "departureDate";
   

    /** Model attribute of Club or Society list. */
    private static final String MODEL_ATT_CLUB_SOCIETY_LIST = "clubSocietyList";

    /** Model attribute of position list. */
    private static final String MODEL_ATT_POSITION_LIST = "positionList";

    /** Model attribute of Seminar list. */
    private static final String MODEL_ATT_SEMINAR_LIST = "seminarList";

    /** Holds error message key for delete. */
    private static final String ERROR_MSG_DELETE = "REF.UI.CLUB.DELETE";

    /** Holds error message key. */
    private static final String ERROR_MSG_SAVEORUPDATE = "STAFF.EXTRA.CURRICULAR.EXIST";

    /** Holds instance of a staffService. */
    private StaffService staffService;

    /** Holds instance of a commonService. */
    private CommonService commonService;

    /**
     * Registers the staff service to inject with controller.
     *
     * @param insStaffService the staffService to set
     */
    public void setStaffService(StaffService insStaffService) {

        this.staffService = insStaffService;
    }

    /**
     * Registers the common service to inject with controller.
     *
     * @param insCommonService the commonService to set
     */
    public void setCommonService(CommonService insCommonService) {

        this.commonService = insCommonService;
    }

    /**
     * Returns staff extra curricular main page which has staff club & society, staff sport, staff seminar and
     * staff external activity information.
     *
     * @param modelMap - holds ModelMap object
     * @param request - holds HttpServletRequest object
     * @return view page for staff extra curricular.
     * @throws AkuraAppException - throw a detailed exception when view
     */
    @RequestMapping(value = REQ_MAP_VALUE_VIEW, method = RequestMethod.GET)
    public String viewStaffExtraCurricularPage(ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {
        
        Staff obj; 
        int staffId = getSessionStaffId(request.getSession());  
        obj = (Staff) staffService.viewStaff(staffId);
        Date departureDate = staffService.findStaff(obj.getStaffId()).getDateOfDeparture();
        modelMap.addAttribute(DEPARTURE_DATE, departureDate);     
       
        if(!modelMap.containsAttribute(MODEL_ATT_STAFFCLUBSOCIETY)){
        	modelMap.addAttribute(MODEL_ATT_STAFFCLUBSOCIETY, new StaffClubSociety());
       }  
        if(!modelMap.containsAttribute(MODEL_ATT_STAFFEXTERNALACTIVITY)){
        	 modelMap.addAttribute(MODEL_ATT_STAFFEXTERNALACTIVITY, new StaffExternalActivity());
      } 
        if(!modelMap.containsAttribute(MODEL_ATT_STAFFSPORT)){
        	 modelMap.addAttribute(MODEL_ATT_STAFFSPORT, new StaffSport());
     }  
        if(!modelMap.containsAttribute(MODEL_ATT_STAFFSEMINAR)){
        	modelMap.addAttribute(MODEL_ATT_STAFFSEMINAR, new StaffSeminar());
    } 
       
       
        
      
       
        return populateDataForSelectedYear(modelMap, request);
    }

    /**
     * Populates staff extra curricular data according to selected year.
     *
     * @param modelMap - holds ModelMap object
     * @param request - holds HttpServletRequest object
     * @return staff extra curricular details for selected year
     * @throws AkuraAppException - throws detailed exception when retrieve club&society list
     */
    @RequestMapping(value = REQ_MAP_VALUE_POPULATE_DATA, method = RequestMethod.GET)
    public String populateDataForSelectedYear(ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {

        String selectedYear = request.getParameter(REQ_PAR_SELECTED_YEAR);
        Date dateSelectedYear = null;

        // As default use the current year, if there is no selected year
        if (selectedYear == null) {
            dateSelectedYear = DateUtil.currentYear();
        } else {
            dateSelectedYear = DateUtil.getDateTypeYearValue(selectedYear);
        }

        // Get all the staff club&societies for the selected year
        List<?> staffClubSocieties =
                staffService.getStaffClubSocietyList(getSessionStaffId(request.getSession()), dateSelectedYear);

        // Get all the staff external activities for the selected year
        List<StaffExternalActivity> staffExternalActivities =
                staffService.getStaffExternalActivityList(getSessionStaffId(request.getSession()), dateSelectedYear);

        // Get all the staff seminars for the selected year
        List<?> staffSeminars =
                staffService.getStaffSeminarList(getSessionStaffId(request.getSession()), dateSelectedYear);

        // Get all the staff sports for the selected year
        List<StaffSport> staffSports =
                staffService.getStaffSportsList(getSessionStaffId(request.getSession()), dateSelectedYear);

        modelMap.addAttribute(REQ_PAR_SELECTED_YEAR, DateUtil.getYearFromDate(dateSelectedYear));
        modelMap.addAttribute(MODEL_ATT_STAFF_CLUB_SOCIETIES, staffClubSocieties);
        modelMap.addAttribute(MODEL_ATT_STAFF_EXTERNAL_ACTIVITIES, staffExternalActivities);
        modelMap.addAttribute(MODEL_ATT_STAFF_SEMINARS, staffSeminars);
        modelMap.addAttribute(MODEL_ATT_STAFF_SPORTS, staffSports);

        return REQ_MAP_VALUE_JSP_VIEW;
    }

    /**
     * Handles save and update staff club society according to selected year.
     *
     * @param modelMap - holds ModelMap object
     * @param request - holds HttpServletRequest object
     * @param staffClubSociety - holds StaffClubSociety object
     * @return view of staff extra curricular
     * @throws AkuraAppException throws detailed exception when add/edit club&society
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVE_UPDATE_STAFFCLUBSOCIETY, method = RequestMethod.POST)
    public String saveOrUpdateStaffClubSociety(ModelMap modelMap, HttpServletRequest request,
            @ModelAttribute(MODEL_ATT_STAFFCLUBSOCIETY) StaffClubSociety staffClubSociety) throws AkuraAppException {
 
        try {
            if (staffClubSociety.getClubSocietyId() == 0 || staffClubSociety.getPositionId() == 0) {
                String message = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
                modelMap.addAttribute(MODEL_ATT_CLUBSOCIETYMESSAGE, message);
            } else {
                String selectedYear = request.getParameter(REQ_PAR_SELECTED_YEAR);

                Date selectedDateYear = DateUtil.getDateTypeYearValue(selectedYear);
                staffClubSociety.setYear(selectedDateYear);

             int staffId = getSessionStaffId(request.getSession());
                staffClubSociety.setStaffId(staffId);

                // If StaffClubSocietyId is 0, Add a new staff club&society otherwise it's an update
                if (staffClubSociety.getStaffClubSocietyId() == 0) {

                    // Check whether it's already exist before add, if exist populate a message
                    if (isExistStaffClubSociety(staffId, selectedDateYear, staffClubSociety.getClubSocietyId())) {
                        generateErrorMessageExistStaffClubSociety(modelMap);
                    } else {
                        staffService.addStaffClubSociety(staffClubSociety);
                    }
                } else {

                    // It's allowed to edit only the position for an added club&society
                    if (!isExistStaffClubSociety(staffId, selectedDateYear, staffClubSociety.getClubSocietyId())
                            || (staffService.getStaffClubSociety(staffClubSociety.getStaffClubSocietyId())
                                    .getClubSocietyId() == staffClubSociety.getClubSocietyId())) {
                        staffService.modifyStaffClubSociety(staffClubSociety);
                    } else {

                        // If it's already exist when update, populate a message
                        generateErrorMessageExistStaffClubSociety(modelMap);
                    }
                }
            }
        } catch (AkuraAppException e) {
            LOG.error("StaffExtraCurricularController - error occured while add or edit staff club society "
                    + e.toString());
            throw e;
        }
        
        modelMap.addAttribute(MODEL_ATT_STAFFCLUBSOCIETY, staffClubSociety);

     return viewStaffExtraCurricularPage(modelMap, request);
    }
    

    /**
     * Get staff id from request session.
     *
     * @param session - holds HttpSession object
     * @return staff id integer type
     * @throws AkuraAppException - throws detailed exception
     */
    private int getSessionStaffId(HttpSession session) throws AkuraAppException {

        return (session.getAttribute(REQ_PAR_STAFFID) != null) ? Integer.parseInt(session.getAttribute(REQ_PAR_STAFFID)
                .toString()) : 0;
    }

    /**
     * Populate error messages when a StaffClubSociety already exist.
     *
     * @param modelMap - holds ModelMap object
     */
    private void generateErrorMessageExistStaffClubSociety(ModelMap modelMap) {

        String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_SAVEORUPDATE);
        modelMap.addAttribute(MODEL_ATT_CLUBSOCIETYMESSAGE, message);
    }

    /**
     * Checks whether the club and society already exist.
     *
     * @param staffId - holds integer type
     * @param selectedYear - holds date type
     * @param clubSocietyId - holds integer type
     * @return true if already exist otherwise false
     * @throws AkuraAppException throws detailed exception when check is already exist club&society
     */
    private boolean isExistStaffClubSociety(int staffId, Date selectedYear, int clubSocietyId)
    throws AkuraAppException {

        List<StaffClubSociety> staffClubSociety =
                staffService.getStaffClubSociety(staffId, selectedYear, clubSocietyId);

        boolean isExist = false;

        if (staffClubSociety.size() > 0) {
            isExist = true;
        }

        return isExist;
    }

    /**
     * Handles delete staff club society according to selected year.
     *
     * @param modelMap - holds ModelMap object
     * @param request - holds HttpServletRequest object
     * @return view of staff extra curricular
     * @throws AkuraAppException throws detailed exception when delete club&society
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE_STAFFCLUBSOCIETY, method = RequestMethod.POST)
    public String deleteStaffClubSociety(ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {

        try {
            String selectedYear = request.getParameter(REQ_PAR_SELECTED_YEAR);
            modelMap.addAttribute(REQ_PAR_SELECTED_YEAR, selectedYear);

            if (request.getParameter(REQ_PAR_STAFFCLUBSOCIETYID) != null) {
                int staffClubSocietyId = Integer.parseInt(request.getParameter(REQ_PAR_STAFFCLUBSOCIETYID).toString());
                StaffClubSociety staffClubSociety = staffService.getStaffClubSociety(staffClubSocietyId);
                staffService.deleteStaffClubSociety(staffClubSociety);
            }
        } catch (AkuraAppException e) {
            LOG.error("Error While Deleting a StaffClubSociety" + e.toString());
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                modelMap.addAttribute(MODEL_ATT_CLUBSOCIETYMESSAGE, message);
            } else {
                throw e;
            }
        }

        return viewStaffExtraCurricularPage(modelMap, request);
    }

    /**
     * Handles save and update staff external activity according to selected year.
     *
     * @param modelMap - holds ModelMap object
     * @param request - holds HttpServletRequest object
     * @param staffExternalActivity - holds StaffExternalActivity object
     * @return view of staff extra curricular
     * @throws AkuraAppException throws detailed exception when add/edit external activity
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVE_UPDATE_STAFFEXTERNALACTIVITY, method = RequestMethod.POST)
    public String saveOrUpdateStaffExternalActivity(ModelMap modelMap, HttpServletRequest request,
            @ModelAttribute(MODEL_ATT_STAFFEXTERNALACTIVITY) StaffExternalActivity staffExternalActivity)
            throws AkuraAppException {

        try {
            if (staffExternalActivity.getExternalActivity() == "" || staffExternalActivity.getAchievement() == "") {
                String message = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
                modelMap.addAttribute(MODEL_ATT_EXTERNALACTIVITYMESSAGE, message);
            } else {
                String selectedYear = request.getParameter(REQ_PAR_SELECTED_YEAR);

                Date selectedDateYear = DateUtil.getDateTypeYearValue(selectedYear);
                staffExternalActivity.setYear(selectedDateYear);

                int staffId = getSessionStaffId(request.getSession());
                staffExternalActivity.setStaffId(staffId);

                // If StaffExternalActivityId is 0, Add a new staff club&society otherwise it's an update
                if (isExistStaffExternalActivity(staffId, selectedDateYear,
                        staffExternalActivity.getExternalActivity(), staffExternalActivity.getAchievement())) {
                    generateErrorMessageExistStaffExternalActivity(modelMap);
                } else {
                    if (staffExternalActivity.getStaffExternalActivityId() == 0) {
                        staffService.addStaffExternalActivity(staffExternalActivity);
                    } else {
                        staffService.modifyStaffExternalActivity(staffExternalActivity);
                    }
                }
            }
        } catch (AkuraAppException e) {
            LOG.error("StaffExtraCurricularController - error occured while add or edit staff external activity "
                    + e.toString());
            throw e;
        }
        modelMap.addAttribute(MODEL_ATT_STAFFEXTERNALACTIVITY, staffExternalActivity);
        return viewStaffExtraCurricularPage(modelMap, request);
    }

    /**
     * Populate error messages when a Staff external activity already exist.
     *
     * @param modelMap - holds ModelMap object
     */
    private void generateErrorMessageExistStaffExternalActivity(ModelMap modelMap) {

        String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_SAVEORUPDATE);
        modelMap.addAttribute(MODEL_ATT_EXTERNALACTIVITYMESSAGE, message);
    }

    /**
     * Checks whether the staff external activity and achievement already exist.
     *
     * @param staffId - holds integer type
     * @param selectedYear - holds date type
     * @param activity - holds string type
     * @param achievement - holds string type
     * @return true if already exist otherwise false
     * @throws AkuraAppException throws detailed exception when check is already exist external activity
     */
    private boolean isExistStaffExternalActivity(int staffId, Date selectedYear, String activity, String achievement)
            throws AkuraAppException {

        List<StaffExternalActivity> stafExternalActivities =
                staffService.getStaffExternalActivity(staffId, selectedYear, activity, achievement);

        boolean isExist = false;

        if (stafExternalActivities.size() > 0) {
            isExist = true;
        }

        return isExist;
    }

    /**
     * Handles delete staff external activity according to selected year.
     *
     * @param modelMap - holds ModelMap object
     * @param request - holds HttpServletRequest object
     * @return view of staff extra curricular
     * @throws AkuraAppException throws detailed exception when delete external activity
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE_STAFFEXTERNALACTIVITY, method = RequestMethod.POST)
    public String deleteStaffExternalActivity(ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {

        try {
            String selectedYear = request.getParameter(REQ_PAR_SELECTED_YEAR);
            modelMap.addAttribute(REQ_PAR_SELECTED_YEAR, selectedYear);

            if (request.getParameter(REQ_PAR_STAFFEXTERNALACTIVITYID) != null) {
                int staffExternalActivityId =
                        Integer.parseInt(request.getParameter(REQ_PAR_STAFFEXTERNALACTIVITYID).toString());
                StaffExternalActivity staffExternalActivity =
                        staffService.getStaffExternalActivity(staffExternalActivityId);
                staffService.deleteStaffExternalActivity(staffExternalActivity);
            }
        } catch (AkuraAppException e) {
            LOG.error("Error While Deleting a StaffExternalActivity" + e.toString());
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                modelMap.addAttribute(MODEL_ATT_EXTERNALACTIVITYMESSAGE, message);
            } else {
                throw e;
            }
        }

        return viewStaffExtraCurricularPage(modelMap, request);
    }

    /**
     * Handles save and update staff seminar according to selected year.
     *
     * @param modelMap - holds ModelMap object
     * @param request - holds HttpServletRequest object
     * @param staffSeminar - holds StaffSeminar object
     * @return view of staff extra curricular
     * @throws AkuraAppException throws detailed exception when add/edit seminar
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVE_UPDATE_STAFFSEMINAR, method = RequestMethod.POST)
    public String saveOrUpdateStaffSeminar(ModelMap modelMap, HttpServletRequest request,
            @ModelAttribute(MODEL_ATT_STAFFSEMINAR) StaffSeminar staffSeminar) throws AkuraAppException {

        try {
            if (staffSeminar.getSeminarId() == 0) {
                String message = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
                modelMap.addAttribute(MODEL_ATT_SEMINARMESSAGE, message);
            } else {
                String selectedYear = request.getParameter(REQ_PAR_SELECTED_YEAR);

                Date selectedDateYear = DateUtil.getDateTypeYearValue(selectedYear);
                staffSeminar.setYear(selectedDateYear);

                int staffId = getSessionStaffId(request.getSession());
                staffSeminar.setStaffId(staffId);

                // If StaffSeminarId is 0, Add a new staff club&society otherwise it's an update
                if (staffSeminar.getStaffSeminarId() == 0) {
                    staffService.addStaffSeminar(staffSeminar);
                } else {
                    staffService.modifyStaffSeminar(staffSeminar);
                }
            }
        } catch (AkuraAppException e) {
            LOG.error("StaffExtraCurricularController - error occured while add or edit staff seminar " + e.toString());
            throw e;
        }
        modelMap.addAttribute(MODEL_ATT_STAFFSEMINAR, staffSeminar);
        return viewStaffExtraCurricularPage(modelMap, request);
    }

    /**
     * Handles delete staff seminar according to selected year.
     *
     * @param modelMap - holds ModelMap object
     * @param request - holds HttpServletRequest object
     * @return view of staff extra curricular
     * @throws AkuraAppException throws detailed exception when delete seminar
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE_SEMINAR, method = RequestMethod.POST)
    public String deleteStaffSeminar(ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {

        try {
            String selectedYear = request.getParameter(REQ_PAR_SELECTED_YEAR);
            modelMap.addAttribute(REQ_PAR_SELECTED_YEAR, selectedYear);

            if (request.getParameter(REQ_PAR_STAFFSEMINARID) != null) {
                int staffSeminarId = Integer.parseInt(request.getParameter(REQ_PAR_STAFFSEMINARID).toString());
                StaffSeminar staffSeminar = staffService.getStaffSeminar(staffSeminarId);
                staffService.deleteStaffSeminar(staffSeminar);
            }
        } catch (AkuraAppException e) {
            LOG.error("Error While Deleting a StaffSeminar" + e.toString());
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                modelMap.addAttribute(MODEL_ATT_SEMINARMESSAGE, message);
            } else {
                throw e;
            }
        }

        return viewStaffExtraCurricularPage(modelMap, request);
    }

    /**
     * Handles save and update staff sport according to selected year.
     *
     * @param modelMap - holds ModelMap object
     * @param request - holds HttpServletRequest object
     * @param staffSport - holds StaffSport object
     * @return view of staff extra curricular
     * @throws AkuraAppException throws detailed exception when add/edit sport
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVE_UPDATE_STAFFSPORT, method = RequestMethod.POST)
    public String saveOrUpdateStaffSport(ModelMap modelMap, HttpServletRequest request,
            @ModelAttribute(MODEL_ATT_STAFFSPORT) StaffSport staffSport) throws AkuraAppException {

        try {
            if (staffSport.getSportCategoryId() == 0 || staffSport.getPositionId() == 0) {
                String message = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
                modelMap.addAttribute(MODEL_ATT_SPORTMESSAGE, message);
            } else {
                String selectedYear = request.getParameter(REQ_PAR_SELECTED_YEAR);

                Date selectedDateYear = DateUtil.getDateTypeYearValue(selectedYear);
                staffSport.setYear(selectedDateYear);

                int staffId = getSessionStaffId(request.getSession());
                staffSport.setStaffId(staffId);

                // If StaffSportId is 0, Add a new staff sport otherwise it's an update
                if (staffSport.getStaffSportId() == 0) {
                    if (isExistStaffSport(staffId, selectedDateYear, staffSport.getSportCategoryId())) {
                        generateStaffSportErrorMessage(modelMap);
                    } else {
                        staffService.addStaffSport(staffSport);
                    }
                } else {
                    if (!isExistStaffSport(staffId, selectedDateYear, staffSport.getSportCategoryId())
                            || (staffService.getStaffSport(staffSport.getStaffSportId()).getSportCategoryId()
                                    == staffSport
                                    .getSportCategoryId())) {
                        staffService.modifyStaffSport(staffSport);
                    } else {
                        generateStaffSportErrorMessage(modelMap);
                    }
                }
            }
        } catch (AkuraAppException e) {
            LOG.error("StaffExtraCurricularController - error occured while add or edit staff sport " + e.toString());
            throw e;
        }
        modelMap.addAttribute(MODEL_ATT_STAFFSPORT, staffSport);
        return viewStaffExtraCurricularPage(modelMap, request);
    }

    /**
     * Generate staff sport error message.
     *
     * @param modelMap the model map
     */
    private void generateStaffSportErrorMessage(ModelMap modelMap) {

        String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_SAVEORUPDATE);
        modelMap.addAttribute(MODEL_ATT_SPORTMESSAGE, message);
    }

    /**
     * Checks if is exist staff sport.
     *
     * @param staffId the staff id
     * @param selectedYear the selected year
     * @param sportCategoryId the sport category id
     * @return true, if is exist staff sport
     * @throws AkuraAppException the sMS app exception
     */
    private boolean isExistStaffSport(int staffId, Date selectedYear, int sportCategoryId) throws AkuraAppException {

        List<StaffSport> staffSports = staffService.getStaffSports(staffId, selectedYear, sportCategoryId);

        boolean isExist = false;

        if (staffSports.size() > 0) {
            isExist = true;
        }

        return isExist;
    }

    /**
     * Handles delete staff sport according to selected year.
     *
     * @param modelMap - holds ModelMap object
     * @param request - holds HttpServletRequest object
     * @return view of staff extra curricular
     * @throws AkuraAppException throws detailed exception when delete sport
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE_SPORT, method = RequestMethod.POST)
    public String deleteStaffSport(ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {

        try {
            String selectedYear = request.getParameter(REQ_PAR_SELECTED_YEAR);
            modelMap.addAttribute(REQ_PAR_SELECTED_YEAR, selectedYear);

            if (request.getParameter(REQ_PAR_STAFFSPORTID) != null) {
                int staffSportId = Integer.parseInt(request.getParameter(REQ_PAR_STAFFSPORTID).toString());
                StaffSport staffSport = staffService.getStaffSport(staffSportId);
                staffService.deleteStaffSport(staffSport);
            }
        } catch (AkuraAppException e) {
            LOG.error("Error While Deleting a StaffSport" + e.toString());
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                modelMap.addAttribute(MODEL_ATT_SPORTMESSAGE, message);
            } else {
                throw e;
            }
        }

        return viewStaffExtraCurricularPage(modelMap, request);
    }

    /**
     * Returns all the available list of ClubSociety.
     *
     * @return list of ClubSociety
     * @throws AkuraAppException - throws detailed exception when retrieve club&society list
     */
    @ModelAttribute(MODEL_ATT_CLUB_SOCIETY_LIST)
    public List<ClubSociety> populateClubSocietyList() throws AkuraAppException {

        return SortUtil.sortClubSocietyList(commonService.getClubsSocietiesList());
    }

    /**
     * Returns all the available list of Position.
     *
     * @return list of Position
     * @throws AkuraAppException - throws detailed exception when retrieve position list
     */
    @ModelAttribute(MODEL_ATT_POSITION_LIST)
    public List<Position> populatePositionList() throws AkuraAppException {

        return SortUtil.sortPositionList(commonService.getPositionList());
    }

    /**
     * Returns all the available list of Seminar list.
     *
     * @return list of Seminar
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_SEMINAR_LIST)
    public List<Seminar> populateSeminarList() throws AkuraAppException {

        return SortUtil.sortSeminarList(commonService.getSeminars());
    }

    /**
     * Returns all the available list of SportCategory list.
     *
     * @return list of SportCategory
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(SPORT_CATEGORIES_LIST)
    public List<SportCategory> populateSportCategoryList() throws AkuraAppException {

    	return SortUtil.sortSportCategoriesList(commonService.getSportCategoriesList());
    }
}
