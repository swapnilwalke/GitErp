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

import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.TermValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageTermController is to handle Add/Edit/Delete/View operations for Manage Term reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageTermController {
    
    /** view post method manage grade. */
    private static final String VIEW_POST_MANAGE_TERM = "redirect:manageTerm.htm";
    
    /** view get method manage grade. */
    private static final String VIEW_GET_MANAGE_TERM = "reference/manageTerm";
    
    /** model attribute of Term. */
    private static final String MODEL_ATT_TERM = "term";
    
    /** model attribute of Term list. */
    private static final String MODEL_ATT_TERM_LIST = "termList";
    
    /** request mapping value for save or update Term. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateTerm.htm";
    
    /** request mapping value for delete Term. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteTerm.htm";
    
    /** request attribute for religionId. */
    private static final String REQ_TERMID = "termId";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY = "REF.UI.TERM.DESCRIPTION.EXIST";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_DELETE = "REF.UI.TERM.DELETE";
    
    /** String attribute for holding DEFALUT_DATE_FORMAT. */
    private static final String DEFALUT_DATE_FORMAT = "yyyy-MM-dd";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** Represents termValidator instance {@link TermValidator} {@link TermValidator}. */
    private TermValidator termValidator;
    
    /** Represents commonService instance {@link CommonService}. */
    private CommonService commonService;
    
    /**
     * Set the termValidator to inject the validator.
     * 
     * @param termValidatorValue the termValidator to set
     */
    public void setTermValidator(TermValidator termValidatorValue) {

        this.termValidator = termValidatorValue;
    }
    
    /**
     * Set the commonService to inject the service.
     * 
     * @param commonServiceVlaue the commonService to set
     */
    public void setCommonService(CommonService commonServiceVlaue) {

        this.commonService = commonServiceVlaue;
    }
    
    /**
     * intiBinder method is to bind date class with the date format.
     * 
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.initDirectFieldAccess();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFALUT_DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    /**
     * Handle GET requests for Term_details view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showTermDetail(ModelMap model) {

        Term term = (Term) model.get(MODEL_ATT_TERM);
        
        if (term == null) {
            term = new Term();
        }
        model.addAttribute(MODEL_ATT_TERM, term);
        
        return VIEW_GET_MANAGE_TERM;
    }
    
    /**
     * This method is to handle Add/Edit Term details.
     * 
     * @param term - Term obj.
     * @param request - HttpServletRequest
     * @param model {@link ModelMap}
     * @param bindingResult {@link BindingResult}
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - throw this
     * @throws ParseException
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateTerm(@ModelAttribute(MODEL_ATT_TERM) Term term, BindingResult bindingResult,
            HttpServletRequest request, ModelMap model) throws AkuraAppException {

        int termId = term.getTermId();
        termValidator.validate(term, bindingResult);
        
        if (bindingResult.hasErrors()) {
            return VIEW_GET_MANAGE_TERM;
        } else {
            if (commonService.isExistsTerm(term, termId, term.getFromMonth(), term.getToMonth())) {
                existMessage(model);
                return VIEW_GET_MANAGE_TERM;
            } else {
                
                // If the Term id is zero, it's an new entry.
                if (termId == 0) {
                    commonService.createTerm(term);
                } else {
                    try {
                        commonService.updateTerm(term);
                    } catch (AkuraAppException e) {
                        existMessage(model);
                        return VIEW_GET_MANAGE_TERM;
                    }
                }
            }
        }
        return VIEW_POST_MANAGE_TERM;
    }
    
    /**
     * This is to populate error message when add/update a term where already exist.
     * 
     * @param model - {@link ModelMap}
     */
    private void existMessage(ModelMap model) {

        String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
        Term newTerm = new Term();
        model.addAttribute(MODEL_ATT_TERM, newTerm);
        model.addAttribute(MESSAGE, message);
    }
    
    /**
     * Delete a term.
     * 
     * @param request {@link HttpServletRequest}
     * @param model {@link ModelMap}
     * @return name of the view.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteTerm(HttpServletRequest request, ModelMap model) {

        try {
            int termId = Integer.parseInt(request.getParameter(REQ_TERMID));
            commonService.deleteTerm(termId);
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                Term newTerm = new Term();
                model.addAttribute(MODEL_ATT_TERM, newTerm);
                model.addAttribute(MESSAGE, message);
                
                return VIEW_GET_MANAGE_TERM;
            }
        }
        
        return VIEW_POST_MANAGE_TERM;
    }
    
    /**
     * Method is to return Term list.
     * 
     * @return list of Term
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_TERM_LIST)
    public List<Term> populateTermList() throws AkuraAppException {

        return SortUtil.sortTermList(commonService.getTermList());
    }
}
