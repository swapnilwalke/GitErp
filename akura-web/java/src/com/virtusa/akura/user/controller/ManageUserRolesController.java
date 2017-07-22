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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.Module;
import com.virtusa.akura.api.dto.Privilege;
import com.virtusa.akura.api.dto.Tab;
import com.virtusa.akura.api.dto.UserRole;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.user.validator.UserRoleValidator;

/**
 * The ManageUserRolesController is to manage user role functionalities such as create, edit and delete user
 * roles.
 * 
 * @author Virtusa Corporation
 */

@Controller
public class ManageUserRolesController {
    
    /**
     * Represents an instance of UserService.
     */
    private UserService userService;
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(ManageUserRolesController.class);
    
    /**
     * Represents an instance of the UserRoleValidator.
     */
    private UserRoleValidator userRoleValidator;
    
    /** String constant for hold model attribute `,`. */
    private static final String STRING_COMMA = ",";
    
    /** String constant for hold model attribute `privilegeList`. */
    private static final String PRIVILEGE_LIST = "privilegeList";
    
    /** String constant for hold model attribute `roleList`. */
    private static final String ROLE_LIST = "roleList";
    
    /** String constant for hold model attribute `rolePrivilegeList`. */
    private static final String ROLE_PRIVILEGE_LIST = "rolePrivilegeList";
    
    /** String constant for hold model attribute `moduleList`. */
    private static final String MODULE_LIST = "moduleList";
    
    /** String constant for hold error key of duplicate recored. */
    private static final String ERROR_MSG_KEY_DUPLICATE = "USER.ROLE.MANAGE.USER.ROLE.DUPLICATE";
    
    /** String constant for hold error key of can not delete recored. */
    private static final String ERROR_MSG_KEY_DELETE = "USER.ROLE.MANAGE.USER.ROLE.DELETE";
    
    /** String constant for hold key of success recored. */
    private static final String ERROR_MSG_KEY_SUCCESS = "USER.ROLE.MANAGE.USER.ROLE.SUCCESS";
    
    /** String constant for hold key of updated recored. */
    private static final String ERROR_MSG_KEY_UPDATE = "USER.ROLE.MANAGE.USER.ROLE.UPDATED";
    
    /** String constant for hold request parameter 'selectedRole'. */
    private static final String SELECTED_ROLE = "selectedRole";
    
    /** view post method manage user roles. */
    private static final String REDIRECT_MANAGE_USER_ROLES_HTM = "redirect:manageUserRoles.htm";
    
    /** request mapping value for save or update user role. */
    private static final String SAVE_OR_UPDATE_USER_ROLE_HTM = "saveOrUpdateUserRole.htm";
    
    /** request mapping value for delete user role. */
    private static final String DELETE_USER_ROLE_HTM = "deleteUserRole.htm";
    
    /** string constant for hold 'message'. */
    private static final String MESSAGE_STR = "message";

    /** string constant for hold 'message'. */
    private static final String MESSAGE_SUCCESS_STR = "success_message";
    
    /** The id of the user role. */
    private static final String USER_ROLE_ID = "userRoleId";
    
    /** String constant to hold model attribute 'userRoleList'. */
    private static final String MODEL_ATT_USER_ROLE_LIST = "userRoleList";
    
    /** String constant to hold view `manageUserRole`. */
    private static final String MANAGE_USER_ROLE_VIEW = "manageUserRole";
    
    /** String constant to hold view `userRoleDetails`. */
    private static final String USER_ROLE_DETAILS_VIEW = "userRoleDetails";
    
    /** String constant to hold url `newUserRoleDetails.htm`. */
    private static final String NEW_USER_ROLE_HTM = "newUserRoleDetails.htm";
    
    /** String constant to hold url `viewUserRoleDetails.htm`. */
    private static final String VIEW_USER_ROLE_HTM = "viewUserRoleDetails.htm";
    
    /** String constant to hold `userRole`. */
    private static final String USER_ROLE = "userRole";
    
    /**
     * Set userService.
     * 
     * @param userServiceObj the userService to set
     */
    public void setUserService(UserService userServiceObj) {

        this.userService = userServiceObj;
    }
    
    /**
     * Set userService.
     * 
     * @param userRoleValidatorObj the UserRoleValidator to set
     */
    public void setUserRoleValidator(UserRoleValidator userRoleValidatorObj) {

        this.userRoleValidator = userRoleValidatorObj;
    }
    
    /**
     * handle GET requests for Manage System Users view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public final String showManageUserRolesView(ModelMap model) {

        UserRole userRole = (UserRole) model.get(USER_ROLE);
        
        if (userRole == null) {
            userRole = new UserRole();
        }
        model.addAttribute(USER_ROLE, userRole);
        
        return MANAGE_USER_ROLE_VIEW;
    }
    
    /**
     * Add new user role form.
     * 
     * @param request - the request object
     * @param model - {@link ModelMap}
     * @return view of the user role details.
     * @throws AkuraAppException - Throw detail exception.
     */
    @RequestMapping(value = NEW_USER_ROLE_HTM)
    public String newUserRoleDetailsForm(HttpServletRequest request, ModelMap model) throws AkuraAppException {

        UserRole userRole = (UserRole) model.get(USER_ROLE);
        
        if (userRole == null) {
            userRole = new UserRole();
        }
        model.addAttribute(USER_ROLE, userRole);
        
        // set the module list
        List<Module> moduleList = userService.getAllModules();
        model.addAttribute(MODULE_LIST, moduleList);
        
        return USER_ROLE_DETAILS_VIEW;
    }
    
    /**
     * View the user role details.
     * 
     * @param request - the request object
     * @param model - {@link ModelMap}
     * @param session - {@link HttpSession}
     * @return view of the staff details.
     * @throws AkuraAppException - Throw detail exception.
     */
    @RequestMapping(value = VIEW_USER_ROLE_HTM)
    public String showUserRoleDetails(HttpServletRequest request, ModelMap model, HttpSession session)
            throws AkuraAppException {

        String returnResult = REDIRECT_MANAGE_USER_ROLES_HTM;
        
        if ((session.getAttribute(USER_ROLE_ID) != null) && ((Integer) session.getAttribute(USER_ROLE_ID) != 0)) {
            int userRoleId = Integer.parseInt(session.getAttribute(USER_ROLE_ID).toString());
            
            // get and set the user role
            UserRole userRole = (UserRole) userService.findUserRole(userRoleId);
            model.addAttribute(USER_ROLE, userRole);
            
            // get and set the privilege list
            List<Privilege> privilegeList = userService.getPrivilegesByUserRole(userRole);
            model.addAttribute(ROLE_PRIVILEGE_LIST, privilegeList);
            
            // set the module list
            List<Module> moduleList = userService.getAllModules();
            model.addAttribute(MODULE_LIST, moduleList);
            
            returnResult = USER_ROLE_DETAILS_VIEW;
        }
        
        return returnResult;
    }
    
    /**
     * Saves or updates the relevant UserRole object.
     * 
     * @param request - the request object
     * @param model - a HashMap that contains information of the UserRole
     * @param userRole - an instance of UserRole
     * @param result - containing list of errors and UserRole instance to which data was bound
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when saving or updating a UserRole
     *         instance.
     */
    @RequestMapping(value = SAVE_OR_UPDATE_USER_ROLE_HTM, method = RequestMethod.POST)
    public final String saveOrUpdateUserRole(final ModelMap model, HttpServletRequest request,
            @ModelAttribute(USER_ROLE) final UserRole userRole, BindingResult result) throws AkuraAppException {

        String returnResult = USER_ROLE_DETAILS_VIEW;
        LOG.debug("Save or update an instance of UserRole");
        boolean isNewUserRole = false;
        
        userRoleValidator.validate(userRole, result);
        
        if (!result.hasErrors()) {
            
            // create tab and privilege lists
            List<Privilege> privilegeList = new ArrayList<Privilege>();
            List<Integer> privilegeIdList = new ArrayList<Integer>();
            List<Tab> tabList = new ArrayList<Tab>();
            
            String tabs = request.getParameter(ROLE_LIST);
            String privileges = request.getParameter(PRIVILEGE_LIST);
            
            StringTokenizer tabToken = new StringTokenizer(tabs, STRING_COMMA);
            StringTokenizer privilegeToken = new StringTokenizer(privileges, STRING_COMMA);
            
            Tab tab;
            Privilege privilege;
            
            int privilegeId = 0;
            while (privilegeToken.hasMoreTokens()) {
                privilegeId = Integer.parseInt(privilegeToken.nextToken());
                privilege = new Privilege();
                privilege.setPrivilegeId(privilegeId);
                privilegeList.add(privilege);
                privilegeIdList.add(privilegeId);
            }
            
            while (tabToken.hasMoreTokens()) {
                tab = new Tab();
                tab.setTabId(Integer.parseInt(tabToken.nextToken()));
                tabList.add(tab);
            }
            
            if (privilegeIdList.size() != 0) {
                privilegeList.addAll(userService.getDependenciesList(privilegeIdList));
                tabList.addAll(userService.getDependenciesTabIdList(privilegeIdList));
            }
            // remove duplicates
            Set<Privilege> privilegeSet = new HashSet<Privilege>(privilegeList);
            privilegeList.clear();
            privilegeList.addAll(privilegeSet);
            
            Set<Tab> tabSet = new HashSet<Tab>(tabList);
            tabList.clear();
            tabList.addAll(tabSet);
            
            String description = userRole.getDescription().trim();
            String role = userRole.getRole().trim();
            int roleId = userRole.getUserRoleId();
            
            
            try {
                if (roleId > 0) {
                    isNewUserRole = false;
                    UserRole userRoleObj = userService.findUserRole(userRole.getUserRoleId());
                    userRoleObj.setRole(role);
                    userRoleObj.setIsSystemRole(false);
                    userRoleObj.setDescription(description);
                    userService.updateUserRole(userRoleObj);
                } else {
                    isNewUserRole = true;
                    userRole.setRole(role);
                    userRole.setIsSystemRole(false);
                    userRole.setDescription(description);
                    userService.createUserRole(userRole);
                }
                
                // grant or revoke Privileges to user role
                userService.grantOrRevokePrivileges(userRole, privilegeList, tabList);
                
                // add success message
                String message = "";
                if (isNewUserRole) {
                    message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY_SUCCESS);
                } else {
                    message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY_UPDATE);
                }
                model.addAttribute(MESSAGE_SUCCESS_STR, message);
                
            } catch (AkuraAppException e) {
                if (e.getCause() instanceof DataIntegrityViolationException) {
                    String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY_DUPLICATE);
                    UserRole userRoleOjb = null;
                    if (!isNewUserRole && userRole.getUserRoleId() > 0) {
                        userRoleOjb = userService.findUserRole(roleId);
                    } else {
                        userRoleOjb = new UserRole();
                    }
                    model.addAttribute(USER_ROLE, userRoleOjb);
                    model.addAttribute(MESSAGE_STR, message);
                } else {
                    throw e;
                }
            }
        }
        
        if (userRole.getUserRoleId() != 0) {
            // get and set the privilege list
            List<Privilege> privilegeListModule = userService.getPrivilegesByUserRole(userRole);
            model.addAttribute(ROLE_PRIVILEGE_LIST, privilegeListModule);
        }
        
        // set the module list
        List<Module> moduleList = userService.getAllModules();
        model.addAttribute(MODULE_LIST, moduleList);
        
        // set the user role id in session
        request.getSession().setAttribute(USER_ROLE_ID, userRole.getUserRoleId());
        return returnResult;
    }
    
    /**
     * Deletes the relevant object of the UserRole.
     * 
     * @param request - an instance of HttpServletRequest that contains the request scope parameters.
     * @param model - {@link ModelMap}
     * @return - redirect to user role management page.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = DELETE_USER_ROLE_HTM)
    public String deleteUserRole(final HttpServletRequest request, final ModelMap model) throws AkuraAppException {

        final int userRoleId = Integer.parseInt(request.getParameter(SELECTED_ROLE));
        String returnResult = REDIRECT_MANAGE_USER_ROLES_HTM; // the view of the result.
        final UserRole userRole = userService.findUserRole(userRoleId);
        try {
            userService.deleteUserRole(userRole);
        } catch (AkuraAppException e) {
            final String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY_DELETE);
            model.addAttribute(USER_ROLE, new UserRole());
            model.addAttribute(MESSAGE_STR, message);
            returnResult = MANAGE_USER_ROLE_VIEW;
        }
        
        return returnResult;
    }
    
    /**
     * Method is to populate the existing user roles.
     * 
     * @param model - {@link ModelMap}
     * @return list of UserRoles
     * @throws AkuraAppException - Detailed exception.
     */
    @ModelAttribute(MODEL_ATT_USER_ROLE_LIST)
    public List<UserRole> populateUserRoles(ModelMap model) throws AkuraAppException {

        return userService.getUserRoleList();
        
    }
}
