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

package com.virtusa.akura.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.dto.UserRole;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.delegate.SystemUserDelegate;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.student.delegate.ParentSystemUserDelegate;
import com.virtusa.akura.student.delegate.StudentSystemUserDelegate;
import com.virtusa.akura.util.SortUtil;

/**
 * The ManageSystemUsersController is to manage System users functionalities such as Reset password,
 * disable/enable user and delete user.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageSystemUsersController {
    
    /** string constant for holding error key `USER.UI.DISABLE.CURRENT.USER.ERROR`. */
    private static final String USER_UI_DISABLE_CURRENT_USER_ERROR = "USER.UI.DISABLE.CURRENT.USER.ERROR";
    
    /** string constant for holding error key `USER.UI.PAST.USER.ENABLE.ERROR`. */
    private static final String USER_UI_PAST_USER_ENABLE_ERROR = "USER.UI.PAST.USER.ENABLE.ERROR";
    
    /** string constant for holding error key `USER.UI.DELETE.ACTIVE.USER.ERROR`. */
    private static final String USER_UI_DELETE_ACTIVE_USER_ERROR = "USER.UI.DELETE.ACTIVE.USER.ERROR";
    
    /** string constant for holding error key `USER.UI.DELETE.CURRENT.USER.ERROR`. */
    private static final String USER_UI_DELETE_CURRENT_USER_ERROR = "USER.UI.DELETE.CURRENT.USER.ERROR";
    
    /** string constant for holding error key `USER.UI.NO.RESULT.FOUND.USER.ERROR`. */
    private static final String USER_UI_NO_RESULT_FOUND_USER_ERROR = "USER.UI.NO.RESULT.FOUND.USER.ERROR";
    
    /** string constant for holding error key `USER.UI.DELETE.ERROR`. */
    private static final String USER_UI_DELETE_ERROR = "USER.UI.DELETE.ERROR";
    
    /** String constant to hold `message`. */
    private static final String MESSAGE = "message";
    
    /** String constant to hold redirect URL `redirect:manageSystemUsers.htm`. */
    private static final String REDIRECT_MANAGE_SYSTEM_USERS = "redirect:manageSystemUsers.htm";
    
    /** String constant to hold request URL `/editSystemUser.htm`. */
    private static final String EDIT_SYSTEM_USER = "/editSystemUser.htm";
    
    /** String constant to hold request URL `/deleteUserLogin.htm`. */
    private static final String DELETE_USER_LOGIN_URL = "/deleteUserLogin.htm";
    
    /** String constant to hold request URL `/unlockUser.htm`. */
    private static final String UNLOCK_USER_URL = "/unlockUser.htm";
    
    /** String constant to hold request URL `/updateUserStatus.htm`. */
    private static final String UPDATE_USER_STATUS_URL = "/updateUserStatus.htm";
    
    /** String constant to hold `0. */
    private static final String ZERO = "0";
    
    /** String constant to hold request parameter `selectedUserStatus`. */
    private static final String SELECTED_USER_STATUS = "selectedUserStatus";
    
    /** String constant to hold request parameter `selectedUserLoginId`. */
    private static final String SELECTED_USER_LOGIN_ID = "selectedUserLoginId";
    
    /** string constant for holding `userLogin`. */
    private static final String USER_LOGIN = "userLogin";
    
    /** string constant for holding `userLoginObj`. */
    private static final String USER_LOGIN_OBJ = "userLoginObj";
    
    /** string constant for holding model attribute `userLoginList`. */
    private static final String USER_LOGIN_LIST = "userLoginList";
    
    /** string constant for holding view name `Admin/manageSystemUsers`. */
    private static final String ADMIN_MANAGE_SYSTEM_USERS = "reference/manageSystemUsers";
    
    /** UserService attribute for holding userService. */
    private transient UserService userService;
    
    /**
     * Name of the parameter name of the request for the start number for staff.
     */
    private static final String START_FROM = "startFrom";
    
    /**
     * Represents the model name of the total number of records for staff.
     */
    private static final String NUMBER_OF_RECORDS = "numberOfRecords";
    
    /**
     * Represents the model name of the maximum number of the staff.
     */
    private static final String MAX_NUMBER = "maxNumber";
    
    /** path to email properties file. */
    public static final String EMAIL_PROPERTIES = "email";
    
    /** The admin email that send an email to user when password changes. */
    public static final String ADMIN_EMAIL = "admin.email";
    
    /** The constant for holding the view template for New Password notification email. */
    public static final String NEW_PASSWORD_TO_USER = "email.template.newPasswordToUser";
    
    /**
     * Name for search.
     */
    private static final String SEARCH = "search";
    
    /**
     * Name of the parameter name of the request for the action.
     */
    private static final String ACTION_TYPE = "actionType";
    
    /**
     * Number of records per page.
     */
    private static final int PAGE_SIXE = 10;
    
    /** String attribute for holding USER_LEVEL_LIST. */
    private static final String USER_LEVEL_LIST = "userLevelList";
    
    /** String attribute for holding USER_ROLE_MAP. */
    private static final String USER_ROLE_MAP = "userRoleMap";
    
    /** String attribute for holding VIEW_CREATE_SYSTEM_USER. */
    private static final String VIEW_CREATE_SYSTEM_USER = "reference/createSystemUser";
    
    /** String attribute for holding MODEL_ATT_USER_DETAIL. */
    private static final String MODEL_ATT_USER_DETAIL = "userDetail";
    
    /** String attribute for holding MODEL_ATT_USER_DETAIL. */
    private static final String MODEL_ATT_USER_IDENTIFICATION_NO = "identificationNo";
    
    /**
     * Represents an instance of StaffSystemUserDelegate.
     */
    private SystemUserDelegate staffSystemUserDelegate;
    
    /**
     * Represents an instance of studentSystemUserDelegate.
     */
    private StudentSystemUserDelegate studentSystemUserDelegate;
    
    /**
     * Represents an instance of parentSystemUserDelegate.
     */
    private ParentSystemUserDelegate parentSystemUserDelegate;
    
    /**
     * Gets an instance of UserService.
     * 
     * @return the UserService object.
     */
    public final UserService getUserService() {

        return userService;
    }
    
    /**
     * Sets an instance of UserService.
     * 
     * @param userServiceObj - an instance of UserService.
     */
    public final void setUserService(UserService userServiceObj) {

        this.userService = userServiceObj;
    }
    
    /**
     * Gets an instance of staffSystemUserDelegate.
     * 
     * @return the staffSystemUserDelegate object.
     */
    public final SystemUserDelegate getStaffSystemUserDelegate() {

        return staffSystemUserDelegate;
    }
    
    /**
     * Sets an instance of staffSystemUserDelegate.
     * 
     * @param studentSystemUserDelegateObj - an instance of staffSystemUserDelegate.
     */
    public final void setStudentSystemUserDelegate(StudentSystemUserDelegate studentSystemUserDelegateObj) {

        this.studentSystemUserDelegate = studentSystemUserDelegateObj;
    }
    
    /**
     * Sets an instance of staffSystemUserDelegate.
     * 
     * @param staffSystemUserDelegateObj - an instance of staffSystemUserDelegate.
     */
    public final void setStaffSystemUserDelegate(SystemUserDelegate staffSystemUserDelegateObj) {

        this.staffSystemUserDelegate = staffSystemUserDelegateObj;
    }
    
    /**
     * Sets an instance of parentSystemUserDelegate.
     * 
     * @param parentSystemUserDelegateObj - an instance of parentSystemUserDelegate.
     */
    public final void setParentSystemUserDelegate(ParentSystemUserDelegate parentSystemUserDelegateObj) {

        this.parentSystemUserDelegate = parentSystemUserDelegateObj;
    }
    
    /**
     * Total number of records in userLogin search.
     */
    private transient int numberOfRecords;
    
    /**
     * handle GET requests for Manage System Users view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public final String showManageSystemUsersView(ModelMap model) {

        UserLogin userLogin = (UserLogin) model.get(USER_LOGIN_OBJ);
        
        if (userLogin == null) {
            userLogin = new UserLogin();
        }
        model.addAttribute(USER_LOGIN_OBJ, userLogin);
        
        return ADMIN_MANAGE_SYSTEM_USERS;
    }
    
    /**
     * Searches the UserLogin records for given userName.
     * 
     * @param userLogin - the instance of userLogin.
     * @param modelMap - a HashMap contains the staff search related information.
     * @param request - an instance of HttpServletRequest that contains the request scope parameters.
     * @return - an instance of ModelAndView to the staff search results.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String searchSystemUser(@ModelAttribute(USER_LOGIN_OBJ) final UserLogin userLogin,
            final HttpServletRequest request, final ModelMap modelMap) throws AkuraAppException {

        int maxNumber;
        final int startFrom =
                Integer.parseInt(request.getParameter(START_FROM) != null ? request.getParameter(START_FROM) : "" + 0);
        final String actionType = (request.getParameter(ACTION_TYPE) != null ? request.getParameter(ACTION_TYPE) : "");
        final int endNumber = startFrom + PAGE_SIXE;
        final List<UserLogin> userLoginList = userService.searchUserLoginByUserNameAndRole(userLogin);
        if (actionType.equals(SEARCH)) {
            numberOfRecords = userLoginList.size();
        }
        
        SortUtil.sortUserLoginList(userLoginList);
        
        if (numberOfRecords < endNumber) {
            maxNumber = numberOfRecords;
        } else {
            maxNumber = endNumber;
        }
        
        final List<UserLogin> reultsForPage = userLoginList.subList(startFrom, maxNumber);
        
        if (!reultsForPage.isEmpty()) {
            modelMap.addAttribute(USER_LOGIN_LIST, reultsForPage);
        }
        if (userLoginList == null || userLoginList.size() == 0) {
            String message = new ErrorMsgLoader().getErrorMessage(USER_UI_NO_RESULT_FOUND_USER_ERROR);
            modelMap.addAttribute(MESSAGE, message);
        }
        modelMap.addAttribute(NUMBER_OF_RECORDS, numberOfRecords);
        modelMap.addAttribute(START_FROM, startFrom);
        modelMap.addAttribute(MAX_NUMBER, maxNumber);
        
        return ADMIN_MANAGE_SYSTEM_USERS;
    }
    
    /**
     * Deletes the relevant object of the UserLogin.
     * 
     * @param request - an instance of HttpServletRequest that contains the request scope paramters.
     * @param model - {@link ModelMap}
     * @return - redirect to ManageSystemUsers page.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = DELETE_USER_LOGIN_URL)
    public String deleteUserLogin(final HttpServletRequest request, final ModelMap model) throws AkuraAppException {

        final int userLoginId = Integer.parseInt(request.getParameter(SELECTED_USER_LOGIN_ID));
        String returnResult = REDIRECT_MANAGE_SYSTEM_USERS; // the view of the result.
        try {
            // get the current user
            UserLogin currentUserLogin = (UserLogin) request.getSession().getAttribute(USER_LOGIN);
            UserLogin userLogin = userService.findUserLogin(userLoginId);
            
            if (currentUserLogin != null && currentUserLogin.getUserLoginId() != userLoginId && !userLogin.isStatus()
                    || userLogin.getLoginAttempts() > 2) {
                userService.deleteUser(userLogin);
            } else if (currentUserLogin != null && currentUserLogin.getUserLoginId() == userLoginId) {
                String message = new ErrorMsgLoader().getErrorMessage(USER_UI_DELETE_CURRENT_USER_ERROR);
                model.addAttribute(MESSAGE, message);
                throw new AkuraAppException();
            } else if (userLogin.isStatus() || userLogin.getLoginAttempts() < AkuraConstant.PARAM_INDEX_THREE) {
                String message = new ErrorMsgLoader().getErrorMessage(USER_UI_DELETE_ACTIVE_USER_ERROR);
                model.addAttribute(MESSAGE, message);
                throw new AkuraAppException();
            }
        } catch (DataIntegrityViolationException e) {
            String message = new ErrorMsgLoader().getErrorMessage(USER_UI_DELETE_ERROR);
            model.addAttribute(MESSAGE, message);
            model.addAttribute(USER_LOGIN_OBJ, new UserLogin());
            returnResult = ADMIN_MANAGE_SYSTEM_USERS;
        } catch (AkuraAppException e) {
            model.addAttribute(USER_LOGIN_OBJ, new UserLogin());
            returnResult = ADMIN_MANAGE_SYSTEM_USERS;
        }
        
        return returnResult;
    }
    
    /**
     * Update the status of the UserLogin.
     * 
     * @param request - an instance of HttpServletRequest that contains the request scope parameters.
     * @param model - {@link ModelMap}
     * @return - redirect to ManageSystemUsers page.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = UPDATE_USER_STATUS_URL)
    public String updateUserStatus(final HttpServletRequest request, final ModelMap model) throws AkuraAppException {

        final int userLoginId =
                Integer.parseInt(request.getParameter(SELECTED_USER_LOGIN_ID) != null ? request
                        .getParameter(SELECTED_USER_LOGIN_ID) : 0 + "");
        String returnResult = REDIRECT_MANAGE_SYSTEM_USERS; // the view of the result.
        
        if (userLoginId != 0) {
            try {
                
                // get the current user
                UserLogin currentUserLogin = (UserLogin) request.getSession().getAttribute(USER_LOGIN);
                
                if (currentUserLogin != null && currentUserLogin.getUserLoginId() != userLoginId) {
                    UserLogin userLogin = userService.findUserLogin(userLoginId);
                    boolean userLoginStatus = request.getParameter(SELECTED_USER_STATUS).equals(ZERO) ? false : true;
                    boolean isPastUser = false;
                    
                    /* check whether user is a past user or not */
                    if (userLogin.getUserRoleId() == com.virtusa.akura.api.enums.UserRole.ROLE_CLERICALSTAFF
                            .getUserRoleId()
                            || userLogin.getUserRoleId() == com.virtusa.akura.api.enums.UserRole.ROLE_TEACHER
                                    .getUserRoleId()) {
                        isPastUser = staffSystemUserDelegate.isPastUser(userLogin.getUserIdentificationNo());
                    }
                    
                    if (!userLoginStatus) {
                        userLogin.setStatus(userLoginStatus);
                        userService.updateUser(userLogin);
                    } else if (userLoginStatus && !isPastUser) {
                        userLogin.setStatus(userLoginStatus);
                        userService.updateUser(userLogin);
                    } else {
                        String message = new ErrorMsgLoader().getErrorMessage(USER_UI_PAST_USER_ENABLE_ERROR);
                        model.addAttribute(MESSAGE, message);
                        throw new AkuraAppException();
                    }
                    
                } else {
                    String message = new ErrorMsgLoader().getErrorMessage(USER_UI_DISABLE_CURRENT_USER_ERROR);
                    model.addAttribute(MESSAGE, message);
                    throw new AkuraAppException();
                }
                
            } catch (AkuraAppException e) {
                model.addAttribute(USER_LOGIN_OBJ, new UserLogin());
                returnResult = ADMIN_MANAGE_SYSTEM_USERS;
            }
        }
        return returnResult;
    }
    
    /**
     * Update the status of the UserLogin.
     * 
     * @param request - an instance of HttpServletRequest that contains the request scope parameters.
     * @param model - {@link ModelMap}
     * @return - redirect to ManageSystemUsers page.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = EDIT_SYSTEM_USER)
    public String editSystemUser(final HttpServletRequest request, final ModelMap model) throws AkuraAppException {

        final String userLoginIdStr = request.getParameter(SELECTED_USER_LOGIN_ID);
        final int userLoginId = (userLoginIdStr != null && userLoginIdStr != "") ? Integer.parseInt(userLoginIdStr) : 0;
        String returnResult = REDIRECT_MANAGE_SYSTEM_USERS; // the view of the result.
        if (userLoginId != 0) {
            UserLogin userLogin = null;
            List<UserRole> userList = null;
            String identificationNo = "";
            try {
                userLogin = userService.findUserLogin(userLoginId);
                userList = SortUtil.sortUserRoleList(userService.getUserRoleList());
                
                // get user's registration No or admission No
                identificationNo = getUserIdentificationNo(userLogin);
                
                returnResult = VIEW_CREATE_SYSTEM_USER;
            } catch (AkuraAppException e) {
                returnResult = ADMIN_MANAGE_SYSTEM_USERS;
            }
            model.addAttribute(USER_LEVEL_LIST, userList);
            model.addAttribute(MODEL_ATT_USER_DETAIL, userLogin);
            model.addAttribute(MODEL_ATT_USER_IDENTIFICATION_NO, identificationNo);
        }
        return returnResult;
    }
    
    /**
     * Unlock UserLogin.
     * 
     * @param request - an instance of HttpServletRequest that contains the request scope parameters.
     * @param model - {@link ModelMap}
     * @return - redirect to ManageSystemUsers page.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = UNLOCK_USER_URL)
    public String unlockUser(final HttpServletRequest request, final ModelMap model) throws AkuraAppException {

        final String userLoginIdStr = request.getParameter(SELECTED_USER_LOGIN_ID);
        final int userLoginId = (userLoginIdStr != null && userLoginIdStr != "") ? Integer.parseInt(userLoginIdStr) : 0;
        String returnResult = REDIRECT_MANAGE_SYSTEM_USERS; // the view of the result.
        try {
            UserLogin userLogin = userService.findUserLogin(userLoginId);
            userLogin.setLoginAttempts(0);
            userService.updateUser(userLogin);
        } catch (AkuraAppException e) {
            returnResult = ADMIN_MANAGE_SYSTEM_USERS;
        }
        model.addAttribute(USER_LOGIN_OBJ, new UserLogin());
        
        return returnResult;
    }
    
    /**
     * Method is to return User Role.
     * 
     * @return userRoleList - userRoleList.
     * @throws AkuraAppException - AkuraAppException
     */
    @ModelAttribute(USER_LEVEL_LIST)
    public List<UserRole> populateUserLevelList() throws AkuraAppException {

        return SortUtil.sortUserRoleList(userService.getUserRoleList());
    }
    
    /**
     * Method is to return UserRole Map.
     * 
     * @return userRoleMap - userRoleMap.
     * @throws AkuraAppException - AkuraAppException
     */
    @ModelAttribute(USER_ROLE_MAP)
    public Map<Integer, String> populateUserRoleMap() throws AkuraAppException {

        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        
        for (UserRole role : userService.getUserRoleList()) {
            roleMap.put(role.getUserRoleId(), role.getRole());
        }
        return roleMap;
    }
    
    /**
     * get user Identification No.
     * 
     * @param userLogin - user Login object
     * @throws AkuraAppException - Throw detailed exception.
     * @return identification no.
     */
    private String getUserIdentificationNo(UserLogin userLogin) throws AkuraAppException {

        // get user's registration No or admission No
        String identificationNo = "";
        
        if (userLogin.getUserIdentificationNo() != null && !userLogin.getUserIdentificationNo().equals("")) {
            
            if (userLogin.getUserRoleId() == com.virtusa.akura.api.enums.UserRole.ROLE_CLERICALSTAFF.getUserRoleId()
                    || userLogin.getUserRoleId() == com.virtusa.akura.api.enums.UserRole.ROLE_TEACHER.getUserRoleId()) {
                
                identificationNo =
                        staffSystemUserDelegate.getIdentificationNoByKey(Integer.parseInt(userLogin
                                .getUserIdentificationNo()));
            }
            if (userLogin.getUserRoleId() == com.virtusa.akura.api.enums.UserRole.ROLE_STUDENT.getUserRoleId()) {
                
                identificationNo =
                        studentSystemUserDelegate.getIdentificationNoByKey(Integer.parseInt(userLogin
                                .getUserIdentificationNo()));
            }
            if (userLogin.getUserRoleId() == com.virtusa.akura.api.enums.UserRole.ROLE_PARENT.getUserRoleId()) {
                
                identificationNo =
                        parentSystemUserDelegate.getIdentificationNoByKey(Integer.parseInt(userLogin
                                .getUserIdentificationNo()));
            }
            
        } else {
            identificationNo = userLogin.getUserIdentificationNo();
        }
        return identificationNo;
    }
}
