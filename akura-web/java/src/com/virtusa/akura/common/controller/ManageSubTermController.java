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

import com.virtusa.akura.api.dto.SubTerm;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.SubTermValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageSubTermController is to handle view/Add/Edit/Delete operations for Sub Terms in reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageSubTermController {
    
    /** view post method manage grade. */
    private static final String VIEW_POST_MANAGE_SUBTERM = "redirect:manageSubTerm.htm";
    
    /** view get method manage grade. */
    private static final String VIEW_GET_MANAGE_SUBTERM = "reference/manageSubTerm";

    /** model attribute of SubTerm. */
    private static final String MODEL_ATT_SUBTERM = "subTerm";
    
    /** model attribute of SubTerm list. */
    private static final String MODEL_ATT_SUBTERM_LIST = "subTermList";
    
    /** model attribute of Term list. */
    private static final String MODEL_ATT_TERM_LIST = "termList";
    
    /** request mapping value for save or update SubTerm. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateSubTerm.htm";
    
    /** request mapping value for delete SubTerm. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteSubTerm.htm";
    
    /** request attribute for subTermId. */
    private static final String REQ_SUBTERMID = "subTermId";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY = "REF.UI.SUBTERM.DESCRIPTION.EXIST";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_DELETE = "REF.UI.SUBTERM.DELETE";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** String attribute for holding DEFALUT_DATE_FORMAT. */
    private static final String DEFALUT_DATE_FORMAT = "yyyy-MM-dd";
    
    /** Holds subTermValidator instance of {@link SubTermValidator}. */
    private SubTermValidator subTermValidator;
    
    /**
     * Set the subTermValidator to inject the validator.
     * 
     * @param subTermValidatorValue the subTermValidator to set
     */
    public void setSubTermValidator(SubTermValidator subTermValidatorValue) {
    
        this.subTermValidator = subTermValidatorValue;
    }
    
    /**
     * {@link CommonService}.
     */
    private CommonService commonService;
    
    /**
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
     * handle GET requests for SubTerm_details view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showSubTermDetail(ModelMap model) {
    
        SubTerm subTerm = (SubTerm) model.get(MODEL_ATT_SUBTERM);
        
        if (subTerm == null) {
            subTerm = new SubTerm();
        }
        model.addAttribute(MODEL_ATT_SUBTERM, subTerm);
        
        return VIEW_GET_MANAGE_SUBTERM;
    }
    
    /**
     * Add/Update SubTerm details.
     * 
     * @param subTerm - SubTerm obj.
     * @param request - HttpServletRequest
     * @param model {@link ModelMap}
     * @param bindingResult {@link BindingResult}
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - throw this
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateSubTerm(@ModelAttribute(MODEL_ATT_SUBTERM) SubTerm subTerm, BindingResult bindingResult,
            HttpServletRequest request, ModelMap model) throws AkuraAppException {
    try{
        subTermValidator.validate(subTerm, bindingResult);
        
        if (bindingResult.hasErrors()) {
            return VIEW_GET_MANAGE_SUBTERM;
        } else {
            
            int subTermId = subTerm.getSubTermId();
            String strSubTermName = subTerm.getDescription().trim();
            int termId = subTerm.getTermId();
            
            if(subTermId==0){
                if (commonService.isExistsSubTerm(termId,strSubTermName)){
                    String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
                    SubTerm newSubTerm = new SubTerm();
                    model.addAttribute(MODEL_ATT_SUBTERM, newSubTerm);
                    model.addAttribute(MESSAGE, message);
                    return VIEW_GET_MANAGE_SUBTERM;
                } else {
                    commonService.createSubTerm(subTerm);
                    }
                } else {
                    if (commonService.isExistsSubTerm(termId,strSubTermName)){
                        String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
                        SubTerm newSubTerm = new SubTerm();
                        model.addAttribute(MODEL_ATT_SUBTERM, newSubTerm);
                        model.addAttribute(MESSAGE, message);
                        return VIEW_GET_MANAGE_SUBTERM;
                    } else {
                        commonService.updateSubTerm(subTerm);
                        }  
                }
            
            }
    }catch(AkuraAppException e){
        String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
        SubTerm newSubTerm = new SubTerm();
        model.addAttribute(MODEL_ATT_SUBTERM, newSubTerm);
        model.addAttribute(MESSAGE, message);
        
        return VIEW_GET_MANAGE_SUBTERM;
        
    }
        return VIEW_POST_MANAGE_SUBTERM;
    }
    
    /**
     * delete sub term details.
     * 
     * @param request {@link HttpServletRequest}
     * @param model {@link ModelMap}
     * @throws AkuraAppException - Detailed exception
     * @return name of the view.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteSubTerm(HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        try {
            int subTermId = Integer.parseInt(request.getParameter(REQ_SUBTERMID));
            commonService.deleteSubTerm(subTermId);
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                SubTerm newSubTerm = new SubTerm();
                model.addAttribute(MODEL_ATT_SUBTERM, newSubTerm);
                model.addAttribute(MESSAGE, message);
                
                return VIEW_GET_MANAGE_SUBTERM;
            }
        }
        return VIEW_POST_MANAGE_SUBTERM;
    }
    
    /**
     * Method is to return SubTerm list.
     * 
     * @return list of SubTerm
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_SUBTERM_LIST)
    public List<SubTerm> populateSubTermList() throws AkuraAppException {
    
        return commonService.getSubTermList();
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
