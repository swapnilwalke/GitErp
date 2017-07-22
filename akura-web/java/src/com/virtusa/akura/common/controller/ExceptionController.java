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

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.exception.ErrorMsgLoader;

/**
 * This class handle exception redirection pages.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ExceptionController {
    
    /** Holds return model attribute DB_CONNECTION_ERROR. */
    private static final String DB_CONNECTION_ERROR = "dbConnectionError";
    
    /** Holds key for db connection exception. */
    private static final String GEN_DB_CONN_ERROR = "GEN.DB.CONN.ERROR";
    
    /** Holds return model PAGE DB_CONNECTION_ERROR. */
    private static final String DB_CONNECTION_ERROR_HTM = "/dbConnectionError.htm";
    
    /** Holds return model attribute SMS_APP_EXCEPTION. */
    private static final String SMS_APP_EXCEPTION_ATT = "smsAppException";
    
    /** Holds return model PAGE SMS_APP_EXCEPTION. */
    private static final String SMS_APP_EXCEPTION_HTM = "/smsAppException.htm";
    
    /** Holds return model attribute METHOD_NOT_ALLOWED. */
    private static final String METHOD_NOT_ALLOWED_ATT = "methodNotAllowed";
    
    /** Holds return model PAGE METHOD_NOT_ALLOWED. */
    private static final String METHOD_NOT_ALLOWED_HTM = "/methodNotAllowed.htm";
    
    /** Holds return model attribute ACCESS_DENIED. */
    private static final String ACCESS_DENIED_ATT = "accessDenied";
    
    /** Holds return model PAGE ACCESS_DENIED. */
    private static final String ACCESS_DENIED_HTM = "/accessDenied.htm";
    
    /** Holds return model attribute PAGE NOT FOUND. */
    private static final String PAGE_NOT_FOUND_ATT = "pageNotFound";
    
    /** Holds return page PAGE NOT FOUND. */
    private static final String PAGE_NOT_FOUND_HTM = "/pageNotFound.htm";
    
    /** Holds key for access denied exception. */
    private static final String ACCESS_DENIED = "COMMON.EXCEPTION.403";
    
    /** Holds key for page not found exception. */
    private static final String PAGE_NOT_FOUND = "COMMON.EXCEPTION.404";
    
    /** Holds key for method not allowed exception. */
    private static final String METHOD_NOT_ALLOWED = "COMMON.EXCEPTION.405";
    
    /** Holds key for sms exception. */
    private static final String SMS_APP_EXCEPTION = "COMMON.EXCEPTION.SMS";
    
    /** Holds return page of exception. */
    private static final String EXCEPTION = "exception";
    
    /**
     * Handle page not found exception and display proper message.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(value = PAGE_NOT_FOUND_HTM, method = RequestMethod.GET)
    public String pageNotFound(ModelMap model) {
    
        String message = new ErrorMsgLoader().getErrorMessage(PAGE_NOT_FOUND);
        model.addAttribute(PAGE_NOT_FOUND_ATT, message);
        return EXCEPTION;
    }
    
    /**
     * Handle access denied exception and display proper message.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(value = ACCESS_DENIED_HTM, method = RequestMethod.GET)
    public String accessDenied(ModelMap model) {
    
        String message = new ErrorMsgLoader().getErrorMessage(ACCESS_DENIED);
        model.addAttribute(ACCESS_DENIED_ATT, message);
        return EXCEPTION;
    }
    
    /**
     * Handle access denied exception and display proper message.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(value = METHOD_NOT_ALLOWED_HTM, method = RequestMethod.GET)
    public String methodNotAllowed(ModelMap model) {
    
        String message = new ErrorMsgLoader().getErrorMessage(METHOD_NOT_ALLOWED);
        model.addAttribute(METHOD_NOT_ALLOWED_ATT, message);
        return EXCEPTION;
    }
    
    /**
     * Handle sms app exception and display proper message.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(value = SMS_APP_EXCEPTION_HTM, method = RequestMethod.GET)
    public String smsAppException(ModelMap model) {
    
        String message = new ErrorMsgLoader().getErrorMessage(SMS_APP_EXCEPTION);
        model.addAttribute(SMS_APP_EXCEPTION_ATT, message);
        return EXCEPTION;
    }
    
    /**
     * Handle access denied exception and display proper message.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(value = DB_CONNECTION_ERROR_HTM, method = RequestMethod.GET)
    public String dbConnectionError(ModelMap model) {
    
        String message = new ErrorMsgLoader().getErrorMessage(GEN_DB_CONN_ERROR);
        model.addAttribute(DB_CONNECTION_ERROR, message);
        return EXCEPTION;
    }
    
}
