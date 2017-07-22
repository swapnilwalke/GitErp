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

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.virtusa.akura.api.dto.AdminDetails;
import com.virtusa.akura.api.dto.ParentDetails;
import com.virtusa.akura.api.dto.Publication;
import com.virtusa.akura.api.dto.StudentDetails;
import com.virtusa.akura.api.dto.Tab;
import com.virtusa.akura.api.dto.UserDefinedRoleDetails;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.dto.UserRole;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.delegate.LoginDelegate;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.StaticDataUtil;

/**
 * This Controller class is responsible for navigating to the login panel and handling the login error
 * functionalities.
 */
@Controller
@SessionAttributes("userLogin")
public class LoginController {
    
    /** label of the pagination next. */
    private static final String PAGINATION_NEXT = "PAGINATION.NEXT";

    /** String constant for hold session attribute `roleTabList`. */
    private static final String ROLE_TAB_MAP = "roleTabMap";
    
    /** view name for parent welcome page. */
    private static final String STUDENT_PARENT_WELCOME = "student/parentWelcome";
    
    /** Represents the pagination path of the Parent. */
    private static final String GET_PARENT_PAGINATION_HTM = "/getParentPagination.htm";
    
    /** Represents the model name for the image path map. */
    private static final String MODEL_IMAGE_MAP = "imagePathMap";
    
    /** Represents the log message for the image preview. */
    private static final String PREIVEW_IMAGE_ERROR = "Preivew the image error ...";
    
    /** attribute for login error. */
    private static final String LOGIN_ERROR = "loginError";
    
    /** attribute for login page redirect. */
    private static final String LOGIN = "login";
    
    /** session attribute for user. */
    private static final String USER = "user";
    
    /** session attribute name for user name. */
    private static final String USER_NAME = "userName";
    
    /** session attribute name for user login details. */
    private static final String USER_LOGIN = "userLogin";
    
    /** url for admin login. */
    private static final String ADMIN_WELCOME = "admin/welcome";
    
    /** Represents the welcome page of the student. */
    private static final String STUDENT_WELCOME = "student/welcome";
    
    /** url for user login. */
    private static final String USER_LOGIN_HTM = "/login.htm";
    
    /** url for user login error . */
    private static final String USER_LOGIN_ERROR_HTM = "/loginError.htm";
    
    /** url for admin login . */
    private static final String ADMIN_LOGIN_HTM = "/adminWelcome.htm";
    
    /**
     * Represents the parameter name of the number of items.
     */
    private static final String ITEM_NO = "itemNo";
    
    /**
     * Represents the parameter name of the pagination.
     */
    private static final String PAGINATE = "paginate";
    
    /**
     * Represents the name of the model attribute for the pagination.
     */
    private static final String PAGINATION = "pagination";
    
    /**
     * Represents the key for the width for image.
     */
    private static final String IMAGE_WIDTH = "imageFile.width";
    
    /**
     * Represents the key for the height for image.
     */
    private static final String IMAGE_HIGHT = "imageFile.height";
    
    /**
     * Represents the name of the property file.
     */
    private static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";
    
    /**
     * Represents the model attribute name for the publication list.
     */
    private static final String PUBLICATION_LIST = "publicationList";
    
    /**
     * Represents the extension of the default image.
     */
    private static final String IMAGE_EXTENSION = ".jpg";
    
    /**
     * Represents the relative folder path for the publication images.
     */
    private static final String PUB_FOLDER_REL_PATH = "resources/publicationImages/";
    
    /**
     * Represents the key for the path of publication images path.
     */
    private static final String PUB_FOLDER_CONFIG_KEY = "publicationFolder.path";
    
    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";
    
    /**
     * Represents the model attribute name for the image height.
     */
    private static final String IMAGE_HEIGHT_NAME = "imageHeight";
    
    /**
     * Represents the model attribute name for the width of the image.
     */
    private static final String IMAGE_WIDTH_NAME = "imageWidth";
    
    /**
     * Represents the maximum number to be displayed at a time.
     */
    private static final int PAGINATION_NO = 3;
    
    /**
     * Represents the model attribute name for the minimum number of the item.
     */
    private static final String PAGE_MIN_NO = "minNo";
    
    /**
     * Represents the model attribute name for the maximum number of the item.
     */
    private static final String PAGE_MAX_NO = "maxNo";
    
    /**
     * Represents the model attribute name for the total number of items.
     */
    private static final String TOT_NO_OF_ITEMS = "totalItems";
    
    /**
     * Represents the model attribute name for exceeding the maximum number of items.
     */
    private static final String EXCEED_MAX = "exceedMax";
    
    /**
     * Represents the model attribute name for exceeding the maximum and minimum number of items.
     */
    private static final String EXCEED_MAX_AND_MIN = "exceedMaxAndMin";
    
    /**
     * Represents the model attribute name for exceeding the minimum number of items.
     */
    private static final String EXCEED_MIN = "exceedMin";
    
    /**
     * Represents the maximum number of the item.
     */
    private static final int MAX_NO = 3;
    
    /**
     * Represents the path of the default publication image.
     */
    private static final String DEFAULT_IMG_PATH = "default.events.image.path";
    
    /** Represents the pagination path of the student. */
    private static final String STUDENT_PAGINATION = "/getStudentPagination.htm";
    
    /** Represents the pagination path of the admin. */
    private static final String ADMIN_PAGINATION = "/getPagination.htm";
    
    /** URL to redirect password change page. */
    private static final String CHANGE_PASSWORD_HTM = "redirect:changePassword.htm";
    
    /** URL to redirect Security question page. */
    private static final String REDIRECT_QUESTION_HTM = "redirect:manageSecurityQuestion.htm";
    
    /** Represents an instance of StaffLoginService. */
    private LoginDelegate staffLoginDelegate;
    
    /** Represents an instance of StudentLoginService. */
    private LoginDelegate studentLoginDelegate;
    
    /** Represents an instance of ParentLoginDelegate. */
    private LoginDelegate parentLoginDelegate;
    
    /** Represents an instance of UserDefinedRoleLoginDelegate. */
    private LoginDelegate userDefinedRoleLoginDelegate;
    
    /**
     * Represents the key for the attendance progress bar display .
     */
    private static final String ATTENDANCE_PROGRESSBAR_DISPLAY = "attendance.progressbar.display";
    
    /** attribute for showAttendanceBar. */
    private static final String SHOW_ATTENDANCE_BAR = "showAttendanceBar";
    
    /**
     * Represents the key for the religious activities progress bar display .
     */
    private static final String RELIGIOUSACTIVITIES_PROGRESSBAR_DISPLAY = "religiousactivities.progressbar.display";
    
    /** attribute for showReligiousActivitiesBar. */
    private static final String SHOW_RELIGIOUS_ACTIVITIES_BAR = "showReligiousActivitiesBar";
    
    /**
     * Represents the key for the academic life progress bar display .
     */
    private static final String ACADEMICLIFE_PROGRESSBAR_DISPLAY = "academiclife.progressbar.display";
    
    /** attribute for showAcademicLifeBar. */
    private static final String SHOW_ACADEMIC_LIFE_BAR = "showAcademicLifeBar";
    
    /**
     * Sets an instance of userDefinedRoleLoginDelegate.
     * 
     * @param userDefinedRoleLoginDelegateArg object to set
     */
    public void setUserDefinedRoleLoginDelegate(LoginDelegate userDefinedRoleLoginDelegateArg) {
    
        this.userDefinedRoleLoginDelegate = userDefinedRoleLoginDelegateArg;
    }
    
    /**
     * Sets an instance of parentLoginDelegate.
     * 
     * @param parentLoginDelegateArg object to set
     */
    public void setParentLoginDelegate(LoginDelegate parentLoginDelegateArg) {
    
        this.parentLoginDelegate = parentLoginDelegateArg;
    }
    
    /**
     * Sets an instance of StaffLoginService.
     * 
     * @param staffLoginDelegateVal - an instance of StaffLoginService.
     */
    public void setStaffLoginDelegate(LoginDelegate staffLoginDelegateVal) {
    
        this.staffLoginDelegate = staffLoginDelegateVal;
    }
    
    /**
     * Sets an instance of StudentLoginService.
     * 
     * @param studentLoginDelegateVal - an instance of StudentLoginService.
     */
    public void setStudentLoginDelegate(LoginDelegate studentLoginDelegateVal) {
    
        this.studentLoginDelegate = studentLoginDelegateVal;
    }
    
    /**
     * Represents an instance of the CommonService.
     */
    private CommonService commonService;
    
    /**
     * Sets an instance of CommonService.
     * 
     * @param commonServiceVal - the instance of CommonService
     */
    public final void setCommonService(final CommonService commonServiceVal) {
    
        commonService = commonServiceVal;
    }
    
    /**
     * Log the error messages.
     */
    private static final Logger LOG = Logger.getLogger(LoginController.class);
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY = "REF.UI.USERLOGIN.ERROR";
    
    /** attribute for holding session spring error message key. */
    private static final String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";
    
    /** holds userService. */
    private UserService userService;
    
    /**
     * handle GET requests for Student_details view.
     * 
     * @param model - ModelMap
     * @param session - {@link HttpSession}
     * @return the name of the view.
     */
    @RequestMapping(value = USER_LOGIN_HTM, method = RequestMethod.GET)
    public String showUserLoginConsole(ModelMap model, HttpSession session) {
    
        session.invalidate();
        return LOGIN;
    }
    
    /**
     * handle GET requests for Student_details view.
     * 
     * @param model - ModelMap
     * @param session - Session
     * @return the name of the view.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = USER_LOGIN_ERROR_HTM, method = RequestMethod.GET)
    public String showUserLoginError(ModelMap model, HttpSession session) throws AkuraAppException {
    
        AuthenticationException authenticationException = null;
        String message = "";
        UserLogin userLogin = null;
        
        try {
            authenticationException = ((AuthenticationException) session.getAttribute(SPRING_SECURITY_LAST_EXCEPTION));
            if (authenticationException != null) {
                // get the user login
                userLogin =
                        userService.getAnyUser((String) (authenticationException.getAuthentication().getPrincipal()));
                throw authenticationException;
            }
            
        } catch (LockedException e) {
            message = authenticationException.getMessage();
            
        } catch (DisabledException e) {
            
            message = authenticationException.getMessage();
            
        } catch (BadCredentialsException e) {
            /* increase login attempts */
            
            // get the user login
            if (userLogin != null) {
                userLogin.setLoginAttempts(userLogin.getLoginAttempts() + 1);
                userService.updateUser(userLogin);
            }
            message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
        }
        
        model.addAttribute(LOGIN_ERROR, message);
        return LOGIN;
    }
    
    /**
     * @param session - UserLogin obj.
     * @param request - a request scope object.
     * @param model - a hash map contains the data.
     * @return name of the view which is redirected to.
     * @exception AkuraAppException AkuraAppException.
     */
    @RequestMapping(value = ADMIN_LOGIN_HTM, method = RequestMethod.GET)
    public String getAdminWelocomePanel(HttpSession session, HttpServletRequest request, final ModelMap model)
            throws AkuraAppException {
    
        // this can be either admissionNo or registrationNo of a student or a staff member.
        // it the user is admin who hasn't linked to staff table then value of userLevelIdentifier would be
        // zero.
        UserInfo userInfo = null;
        List<Tab> roleTabList = null;
        
        if (session.getAttribute(USER) != null) {
            // when user comes through home link after first login user following logic executes
            userInfo = (UserInfo) session.getAttribute(USER);
            
        } else {
            
            // if the user comes thorough login page for the fist time then following logic executes.
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AdminDetails
                    || SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof StudentDetails
                    || SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof ParentDetails
                    || SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDefinedRoleDetails) {
                userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
            
            // initiate role tab list
            int roleId = userInfo.getUserRoleId();
            
            UserRole userRole = userService.findUserRole(roleId);
            roleTabList = userService.getTabsByUserRole(userRole);
            Collections.sort(roleTabList);
            
            // create a tab map
            Map<Integer, Tab> tabMap = new HashMap<Integer, Tab>();
            for (Tab tab : roleTabList) {
                tabMap.put(tab.getTabId(), tab);
            }
            
            // arrange the map
            for (int tabId : tabMap.keySet()) {
                
                // get the current tab
                Tab currentTab = tabMap.get(tabId);
                
                Set<Tab> childTabSet = new TreeSet<Tab>();
                
                for (Tab childTab : currentTab.getTabs()) {
                    
                    // check the child tab is exist on the tabMap
                    if (tabMap.get(childTab.getTabId()) != null) {
                        childTabSet.add(childTab);
                    }
                }
                
                // set childTabSet to the current Tab
                currentTab.setTabs(childTabSet);
            }
            
            session.setAttribute(ROLE_TAB_MAP, tabMap);
        }
        
        return populateSessionData(session, userInfo, model, request);
    }
    
    /**
     * Populates the session data.
     * 
     * @param session the session.
     * @param userInfo the user info.
     * @param request - a request scope object.
     * @param model - a hash map contains the data.
     * @return the redirect URL.
     * @throws AkuraAppException the AkuraAppException.
     */
    private String populateSessionData(HttpSession session, UserInfo userInfo, ModelMap model,
            HttpServletRequest request) throws AkuraAppException {
    
        String userRedirectUrl = LOGIN;
        
        if (userInfo instanceof AdminDetails) {
            userRedirectUrl = ADMIN_WELCOME;
            userRedirectUrl = staffLoginDelegate.welcomeUser(userInfo, session);
            
        } else if (userInfo instanceof ParentDetails) {
            userRedirectUrl = parentLoginDelegate.welcomeUser(userInfo, session);
        } else if (userInfo instanceof StudentDetails) {
            userRedirectUrl = studentLoginDelegate.welcomeUser(userInfo, session);
        } else {
            userRedirectUrl = userDefinedRoleLoginDelegate.welcomeUser(userInfo, session);
        }
        
        // checking password reset or security question
        userRedirectUrl = getRedirectUrl(userRedirectUrl, session, userInfo);
        
        // enable of disable progress bar
        displayProgressBar(session);
        getPublications(model, request);
        return userRedirectUrl;
    }
    
    /**
     * set userRedirectUrl if password reset or security question page needed.
     * 
     * @param userRedirectUrl URL to set only redirect needed.
     * @param session HttpSession
     * @param userInfo UserInfo
     * @return redirect URL
     * @throws AkuraAppException when exception occurs
     */
    private String getRedirectUrl(String userRedirectUrl, HttpSession session, UserInfo userInfo)
            throws AkuraAppException {
    
        if (userInfo != null) {
            
            UserLogin userLogin = userService.getUser(userInfo.getUsername());
            session.setAttribute(USER_LOGIN, userLogin);
            
            /* check user password status whether system generated or not. */
            if (userLogin != null && userLogin.getGeneratedPassword()) {
                // do this if the user login to the system for the first time or the
                // user changed his/her password recently.
                if (!userService.isSecurityQuestionsExist(userLogin)) {
                    session.setAttribute(USER_NAME, userLogin.getUsername());
                    userRedirectUrl = REDIRECT_QUESTION_HTM;
                    
                } else {
                    userRedirectUrl = CHANGE_PASSWORD_HTM;
                }
            }
            /* reset the login attempts */
            // get the user login
            if (userLogin != null) {
                userLogin.setLoginAttempts(0);
                userService.updateUser(userLogin);
            }
            session.setAttribute(AkuraWebConstant.USER, userInfo);
        }
        return userRedirectUrl;
    }
    
    /**
     * Set the userServiceValue.
     * 
     * @param userServiceValue the userService to set
     */
    public void setUserService(UserService userServiceValue) {
    
        this.userService = userServiceValue;
    }
    
    /**
     * Returns the publication list , three items at a time. Image height, width, minimum , maximum number of
     * items and the total number of publication , are also set into the model attribute.
     * 
     * @param model - a HashMap that contains maximum, minimum , total number of publication items,
     *        imageHeight, imageWidth and a map containing the paths of the images for the publications.
     * @param request - an instance of HttpServletRequest contains the parametes of the request.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    public void getPublications(ModelMap model, HttpServletRequest request) throws AkuraAppException {
    
        try {
            LOG.debug(LoginController.class);
            
            int minNo = 0; // minimum number of the item to be published on the page.
            
            StaticDataUtil.getImageHeight(SYSTEM_CONFIG_PROPERTIES, IMAGE_HIGHT);
            StaticDataUtil.getImageWidth(SYSTEM_CONFIG_PROPERTIES, IMAGE_WIDTH);
            model.addAttribute(IMAGE_HEIGHT_NAME, StaticDataUtil.getImageHeight(SYSTEM_CONFIG_PROPERTIES, IMAGE_HIGHT));
            model.addAttribute(IMAGE_WIDTH_NAME, StaticDataUtil.getImageWidth(SYSTEM_CONFIG_PROPERTIES, IMAGE_WIDTH));
            
            Map<Integer, String> imagePathMap = new LinkedHashMap<Integer, String>();
            String pagination = (String) request.getParameter(PAGINATE);
            String maxViewNo = (String) request.getParameter(ITEM_NO);
            int maxPageNo = MAX_NO;
            int totalNoOfItems = commonService.getPageNo(); // gets the total number of publication items.
            
            // if the total number of items is less than the viewed number remove the pagination.
            if (maxViewNo != null && (Integer.parseInt(maxViewNo) > totalNoOfItems)) {
                pagination = null;
            }
            
            // while on the home page from one browser and from another browser that items will delete then
            // next button must be change to current state.
            if (maxViewNo != null && (Integer.parseInt(maxViewNo) == totalNoOfItems)) {
                if (Integer.parseInt(maxViewNo) > MAX_NO
                        && pagination.equals(new ErrorMsgLoader().getErrorMessage(PAGINATION_NEXT))) {
                    maxPageNo = Integer.parseInt(maxViewNo);
                    minNo = maxPageNo - PAGINATION_NO;
                    pagination = null;
                }
            }
            
            if (maxViewNo != null && pagination != null) {
                model.addAttribute(PAGINATION, pagination);
                minNo = Integer.parseInt(maxViewNo);
            }
            if (pagination != null) {
                if (pagination.equals(new ErrorMsgLoader().getErrorMessage(PAGINATION_NEXT))) {
                    maxPageNo = minNo + PAGINATION_NO;
                } else {
                    minNo = minNo - PAGINATION_NO - 1;
                    maxPageNo = minNo + PAGINATION_NO;
                }
            }
            
            // maximum page number to be previewed on the view.
            if (totalNoOfItems == 0) {
                model.addAttribute(PAGE_MIN_NO, minNo);
            } else {
                model.addAttribute(PAGE_MIN_NO, minNo + 1);
            }
            getModelAttributes(model, minNo, maxPageNo, totalNoOfItems);
            model.addAttribute(TOT_NO_OF_ITEMS, totalNoOfItems);
            
            List<Publication> publicationList = commonService.getPublicationsWithPagination(minNo, PAGINATION_NO);
            for (Publication publication : publicationList) {
                imagePathMap = previewDatabaseImage(publication, model, imagePathMap);
            }
            model.addAttribute(MODEL_IMAGE_MAP, imagePathMap);
            model.addAttribute(PUBLICATION_LIST, publicationList);
        } catch (AkuraAppException e) {
            LOG.debug("Error while retrieving publications", e);
            throw e;
        }
    }
    
    /**
     * Retrieves the model attributes to be displayed on the view.
     * 
     * @param model - a HashMap contains the information of the pagination.
     * @param minNo - minimum number of the publication item.
     * @param maxPageNo - maximum number of the publication item.
     * @param totalNoOfItems - total number of publication.
     */
    public void getModelAttributes(ModelMap model, int minNo, int maxPageNo, int totalNoOfItems) {
    
        if (maxPageNo < totalNoOfItems) {
            model.addAttribute(PAGE_MAX_NO, maxPageNo);
        }
        if (maxPageNo == totalNoOfItems && minNo == 0) {
            model.addAttribute(PAGE_MAX_NO, maxPageNo);
            model.addAttribute(EXCEED_MAX_AND_MIN, EXCEED_MAX_AND_MIN);
        }
        if (maxPageNo < totalNoOfItems && minNo != 0) {
            model.addAttribute(PAGE_MAX_NO, maxPageNo); // exceeds the max then 'next' link is
            // disabled.
        }
        if (maxPageNo >= totalNoOfItems && minNo != 0) {
            model.addAttribute(PAGE_MAX_NO, totalNoOfItems);
            model.addAttribute(EXCEED_MAX, EXCEED_MAX); // exceeds the max then 'next' link is disabled.
        }
        if (maxPageNo > totalNoOfItems && minNo == 0) {
            model.addAttribute(PAGE_MAX_NO, totalNoOfItems);
            model.addAttribute(EXCEED_MAX_AND_MIN, EXCEED_MAX_AND_MIN); // exceeds the max then 'next'
            
        }
        if (minNo == 0 && maxPageNo < totalNoOfItems) {
            model.addAttribute(EXCEED_MIN, EXCEED_MIN);
        }
    }
    
    /**
     * Returns a HashMap containing the image paths as the value for the related id of the publication as the
     * key.
     * 
     * @param publication - an instance of publication.
     * @param model - a HashMap contains the details specific to the pagination.
     * @param imagePathMap - a HashMap contains the paths of the images for the related key.
     * @return - the HashMap of the paths of the images.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    public Map<Integer, String> previewDatabaseImage(Publication publication, ModelMap model,
            Map<Integer, String> imagePathMap) throws AkuraAppException {
    
        try {
            if (publication != null && publication.getPublicationId() > 0) {
                Publication publicationDB = commonService.findPublication(publication.getPublicationId());
                if (publicationDB.getImage() != null && publicationDB.getImage().length > 0) {
                    byte[] image = publicationDB.getImage();
                    String imageLoadPath =
                            PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                                    + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, PUB_FOLDER_CONFIG_KEY);
                    imageLoadPath = imageLoadPath + publication.getPublicationId() + IMAGE_EXTENSION;
                    StaticDataUtil.previewProfile(imageLoadPath, image);
                    imagePathMap.put(publication.getPublicationId(),
                            PUB_FOLDER_REL_PATH + publication.getPublicationId() + IMAGE_EXTENSION);
                    
                } else {
                    imagePathMap.put(publication.getPublicationId(),
                            PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, DEFAULT_IMG_PATH));
                }
            }
            return imagePathMap;
        } catch (AkuraAppException e) {
            LOG.debug(PREIVEW_IMAGE_ERROR, e);
            throw e;
        }
    }
    
    /**
     * Returns the max page size of the publication.
     * 
     * @param model - a hash map contains the data.
     * @param request - request scope data.
     * @return - the name of the view to be redirected.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = ADMIN_PAGINATION, method = RequestMethod.POST)
    public String getPagination(ModelMap model, HttpServletRequest request) throws AkuraAppException {
    
        getPublications(model, request);
        return ADMIN_WELCOME;
    }
    
    /**
     * Returns the max page size of the publication for the student.
     * 
     * @param model - a hash map contains the data.
     * @param request - request scope data.
     * @return - the name of the view to be redirected.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = STUDENT_PAGINATION, method = RequestMethod.POST)
    public String getStudentPagination(ModelMap model, HttpServletRequest request) throws AkuraAppException {
    
        getPublications(model, request);
        return STUDENT_WELCOME;
    }
    
    /**
     * Returns the max page size of the publication for the Parent.
     * 
     * @param model - a hash map contains the data.
     * @param request - request scope data.
     * @return - the name of the view to be redirected.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = GET_PARENT_PAGINATION_HTM, method = RequestMethod.POST)
    public String getParentPagination(ModelMap model, HttpServletRequest request) throws AkuraAppException {
    
        getPublications(model, request);
        return STUDENT_PARENT_WELCOME;
    }
    
    /**
     * enable of disable progress bar.
     * 
     * @param session the session.
     * @throws AkuraAppException the AkuraAppException.
     */
    private void displayProgressBar(HttpSession session) throws AkuraAppException {
    
        String showAttendanceBar =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, ATTENDANCE_PROGRESSBAR_DISPLAY);
        session.setAttribute(SHOW_ATTENDANCE_BAR, showAttendanceBar);
        String showReligiousActivitiesBar =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, RELIGIOUSACTIVITIES_PROGRESSBAR_DISPLAY);
        session.setAttribute(SHOW_RELIGIOUS_ACTIVITIES_BAR, showReligiousActivitiesBar);
        String showAccademicLifeBar =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, ACADEMICLIFE_PROGRESSBAR_DISPLAY);
        session.setAttribute(SHOW_ACADEMIC_LIFE_BAR, showAccademicLifeBar);
    }
}
