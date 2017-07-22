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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.util.SortUtil;

/**
 * Description: StaffSearchController controls staff search.
 *
 * @author Virtusa Corporation
 */
@Controller
public class StaffSearchController {

    /** session attribute of UserLogin. */
    private static final String SESSION_USER_LOGIN = "userLogin";

    /** string constant for hold value `acdamicType`. */
    private static final String ACDAMIC_TYPE = "acdamicType";

    /**
     * Represents the name of the view to be redirected.
     */
    private static final String REDIRECT_STAFF_SEARCH_HTM = "redirect:staffSearch.htm";

    /** The redirect view of staff member details. */
    private static final String REDIRECT_VIEW_STAFF_MEMBER_DETAILS = "redirect:viewStaffMemberDetails.htm";

    /** string constant for hold value deleteStaff. */
    private static final String MODEL_DELETE_STAFF = "/deleteStaff.htm";

    /**
     * Represents the key of the error message when deleting the Staff.
     */
    private static final String STA_UI_RECORD_DELETE_ERROR = "STA.UI.RECORD.DELETE.ERROR";

    /**
     * Name of the parameter name of the request for selected staff id.
     */
    private static final String SELECTED_STAFF_ID = "selectedStaffId";

    /**
     * Represents the name of the message.
     */
    private static final String MESSAGE = "message";

    /**
     * Represents the model name of the Staff.
     */
    private static final String STAFF = "staff";

    /**
     * Name of the view to be redirected.
     */
    private static final String SEARCH_STAFF_MEMBER = "staff/searchStaffMember";

    /**
     * Represents the model name of the maximum number of the staff.
     */
    private static final String MAX_NUMBER = "maxNumber";

    /**
     * Represents the model name of the total number of records for staff.
     */
    private static final String NUMBER_OF_RECORDS = "numberOfRecords";

    /**
     * Represents the model name of the list of staff.
     */
    private static final String STAFF_LIST = "staffList";

    /**
     * Represents the value for model attribute.
     */
    private static final String EMPTY = "Empty";

    /**
     * Name for search.
     */
    private static final String SEARCH = "search";

    /**
     * Name of the parameter name of the request for the start number for staff.
     */
    private static final String START_FROM = "startFrom";

    /**
     * Name of the parameter name of the request for the action.
     */
    private static final String ACTION_TYPE = "actionType";

    /** The staff id of the staff member. */
    private static final String STAFF_ID = "staffId";

    /**
     * {@link StaffService}.
     */
    private StaffService staffService;

    /** UserService attribute for holding userService. */
    private transient UserService userService;

    /**
     * Number of records per page.
     */
    private static final int PAGE_SIXE = 10;

    /**
     * Total number of records in staff search.
     */
    private transient int numberOfRecords;

    /**
     * Sets an instance of UserServiceImpl.
     *
     * @param userServiceValue - an instance of UserServiceImpl.
     */
    public void setUserService(final UserService userServiceValue) {

        this.userService = userServiceValue;
    }

    /**
     * Returns a newly created instance of Staff.
     *
     * @param model - a HashMap contains an instance of Staff.
     * @param session - HttpSession
     * @return - the name of the view.
     * @throws AkuraAppException - AkuraAppException
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showStudentSearchPage(final ModelMap model, final HttpSession session) throws AkuraAppException {

        String returnPage = SEARCH_STAFF_MEMBER;

        if (session.getAttribute(SESSION_USER_LOGIN) != null) {

            UserLogin userLogin = (UserLogin) session.getAttribute(SESSION_USER_LOGIN);
            if (userLogin.getUserRoleId() == 2) {

                int staffId = Integer.parseInt(userLogin.getUserIdentificationNo());
                session.setAttribute(STAFF_ID, staffId);
                returnPage = REDIRECT_VIEW_STAFF_MEMBER_DETAILS;
            } else {

                final Staff staff = new Staff();
                model.addAttribute(STAFF, staff);
            }
        }
        return returnPage;
    }

    /**
     * Searches the staff records for given parameters.
     *
     * @param staff - the instance of Staff.
     * @param modelMap - a HashMap contains the staff search related information.
     * @param request - an instance of HttpServletRequest that contains the request scope parameters.
     * @param session - HttpSession session
     * @return - an instance of ModelAndView to the staff search results.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView staffSearch(@ModelAttribute(STAFF) Staff staff, final HttpServletRequest request,
            final ModelMap modelMap, final HttpSession session) throws AkuraAppException {

        int maxNumber;
        final String actionType = request.getParameter(ACTION_TYPE);
        final int startFrom = Integer.parseInt(request.getParameter(START_FROM));
        final int endNumber = startFrom + PAGE_SIXE;

        List<Staff> staffList = null;
        if (actionType.equals(SEARCH)) {

            staffList = staffService.searchStaff(staff);
            numberOfRecords = staffList.size();
            
            session.setAttribute("staffCategory", staff.getStaffCategory());
            session.setAttribute("staffSearchCriteria", staff);
            
        } else {
        	
        	staff = (Staff)session.getAttribute("staffSearchCriteria");
            staff.setStaffCategory((StaffCategory)session.getAttribute("staffCategory"));
            staffList = staffService.searchStaff(staff);
            
            modelMap.addAttribute(STAFF, staff);
            
        }

        SortUtil.sortStaffList(staffList);

        if (numberOfRecords < endNumber) {
            maxNumber = numberOfRecords;
        } else {
            maxNumber = endNumber;
        }

        final List<Staff> reultsForPage = staffList.subList(startFrom, maxNumber);

        if (reultsForPage.isEmpty()) {
            modelMap.addAttribute(STAFF_LIST, EMPTY);
        } else {
            modelMap.addAttribute(STAFF_LIST, reultsForPage);
        }

        modelMap.addAttribute(NUMBER_OF_RECORDS, numberOfRecords);
        modelMap.addAttribute(START_FROM, startFrom);
        modelMap.addAttribute(MAX_NUMBER, maxNumber);
        modelMap.addAttribute(ACDAMIC_TYPE, (staff.getStaffCategory() != null ? staff.getStaffCategory().isAcademic()
                : AkuraWebConstant.EMPTY_STRING));

        return new ModelAndView(SEARCH_STAFF_MEMBER, modelMap);
    }

    /**
     * Deletes the relevant object of the Staff.
     *
     * @param request - an instance of HttpServletRequest that contains the request scope paramters.
     * @param model - {@link ModelMap}
     * @return - redirect to staff search page.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = MODEL_DELETE_STAFF)
    public String deleteStaff(final HttpServletRequest request, final ModelMap model) throws AkuraAppException {

        final int staffId = Integer.parseInt(request.getParameter(SELECTED_STAFF_ID));
        String returnResult = REDIRECT_STAFF_SEARCH_HTM; // the view of the result.
        final Staff staff = staffService.findStaff(staffId);
        try {

            staffService.deleteStaff(staffId);
            // deletes if user login exist for this student.
            deleteUserLogin(staff);
        } catch (AkuraAppException e) {
            final String message = new ErrorMsgLoader().getErrorMessage(STA_UI_RECORD_DELETE_ERROR);
            model.addAttribute(STAFF, new Staff());
            model.addAttribute(MESSAGE, message);
            returnResult = SEARCH_STAFF_MEMBER;
        }

        return returnResult;
    }

    /**
     * Deletes the userLogin related to the staff.
     *
     * @param staff - the instance of Staff.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void deleteUserLogin(final Staff staff) throws AkuraAppException {

        final UserLogin userLogin = userService.getUserLoginByIdentificationNo(staff.getRegistrationNo());
        if (userLogin != null) {
            userService.deleteUser(userLogin);
        }
    }

    /**
     * Returns an instance of StaffServiceImpl.
     *
     * @return - an instance of StaffServiceImpl.
     */
    public StaffService getStaffService() {

        return staffService;
    }

    /**
     * Sets an instance of StaffServiceImpl.
     *
     * @param stafService - an instance of StaffServiceImpl.
     */
    public void setStaffService(final StaffService stafService) {

        this.staffService = stafService;
    }

}
