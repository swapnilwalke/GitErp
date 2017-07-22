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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.Section;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.SectionValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * SectionController is to handle Add/Edit/Delete operations for Section reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class SectionController {
    
    /** view post method manage Section. */
    private static final String VIEW_POST_MANAGE_SECTION = "redirect:manageSection.htm";
    
    /** view get method manage Section. */
    private static final String VIEW_GET_MANAGE_SECTION = "reference/manageSection";
    
    /** model attribute of Section. */
    private static final String MODEL_ATT_SECTION = "section";
    
    /** model attribute of Section list. */
    private static final String MODEL_ATT_SECTION_LIST = "sectionList";
    
    /** request mapping value for save or update Section. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateSection.htm";
    
    /** request mapping value for delete Section. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteSection.htm";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_DELETE = "REF.UI.SECTION.DELETE";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** Model attribute of "showEditSection". */
    private static final String SHOW_EDIT_SECTION = "showEditSection";
    
    /**
     * {@link CommonService}.
     */
    private StaffCommonService staffCommonService;
    
    /**
     * {@link SectionValidator}.
     */
    private SectionValidator sectionValidator;
    
    /**
     * @param staffCommonServiceVlaue the staffCommonService to set
     */
    public void setStaffCommonService(StaffCommonService staffCommonServiceVlaue) {
    
        this.staffCommonService = staffCommonServiceVlaue;
    }
    
    /**
     * @param sectionValidatorValue the sectionValidator to set
     */
    public void setSectionValidator(SectionValidator sectionValidatorValue) {
    
        this.sectionValidator = sectionValidatorValue;
    }
    
    /**
     * the constructor of the Section Controller class.
     */
    public SectionController() {
    
    }
    
    /**
     * handle GET requests for Section_details view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showSectionDetail(ModelMap model) {
    
        Section section = (Section) model.get(MODEL_ATT_SECTION);
        
        if (section == null) {
            section = new Section();
        }
        model.addAttribute(MODEL_ATT_SECTION, section);
        
        return VIEW_GET_MANAGE_SECTION;
    }
    
    /**
     * This is to manage Add and Edit section details.
     * 
     * @param section - {@link Section}.
     * @param request - HttpServletRequest
     * @param model {@link ModelMap}
     * @param bindingResult {@link BindingResult}
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - throw this
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateSection(HttpServletRequest request, ModelMap model,
            @ModelAttribute(MODEL_ATT_SECTION) Section section, BindingResult bindingResult) throws AkuraAppException {
    
        int secId = section.getSectionId();
        String strMessage = null;
        String strDescription = null;
        Section selectedObj = null;
        
        // validations
        sectionValidator.validate(section, bindingResult);
        
        if (bindingResult.hasErrors()) {
            if (section.getSectionId() != 0) {
                selectedObj = staffCommonService.findSection(section.getSectionId());
                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
            }
            model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
            return VIEW_GET_MANAGE_SECTION;
            
        } else {
            strDescription = section.getDescription().trim().replaceAll("\\s+", " ");
            strMessage = new ErrorMsgLoader().getErrorMessage("REF.UI.RECORD.EXIST.ERROR");
            model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
            section.setDescription(strDescription);
            
            // section already exist.
            
            if (staffCommonService.isExistsSection(strDescription, secId)) {
                if (secId == 0) {
                    strMessage = new ErrorMsgLoader().getErrorMessage("REF.UI.RECORD.EXIST.ERROR");
                    model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                    model.addAttribute(MESSAGE, strMessage);
                    return VIEW_GET_MANAGE_SECTION;
                    
                } else {
                    if (staffCommonService.findSection(secId).getDescription()
                            .compareToIgnoreCase(strDescription) == 0) {
                        
                        if (staffCommonService.findSection(secId).getDescription().equals(strDescription)) {
                            
                            Section newSection = new Section();
                            newSection = staffCommonService.findSection(secId);
                            model.addAttribute(MESSAGE, newSection);
                            
                        } else {
                            
                            staffCommonService.updateSection(section);
                            Section newSection = new Section();
                            newSection = staffCommonService.findSection(secId);
                            model.addAttribute(MESSAGE, newSection);
                            
                        }
                        
                    } else {
                        selectedObj = staffCommonService.findSection(section.getSectionId());
                        model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                        model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                        strMessage = new ErrorMsgLoader().getErrorMessage("REF.UI.RECORD.EXIST.ERROR");
                        model.addAttribute(MESSAGE, strMessage);
                        return VIEW_GET_MANAGE_SECTION;
                    }
                    
                }
                
            } else {
                
                if (secId == 0) {
                    staffCommonService.createSection(section);
                } else {
                    try {
                        
                        staffCommonService.updateSection(section);
                    } catch (AkuraAppException e) {
                        strMessage = new ErrorMsgLoader().getErrorMessage("REF.UI.RECORD.EXIST.ERROR");
                        Section newSection = staffCommonService.findSection(secId);
                        model.addAttribute(MESSAGE, newSection);
                        model.addAttribute(MESSAGE, strMessage);
                        model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                        return VIEW_GET_MANAGE_SECTION;
                    }
                }
                
            }
            
        }
        
        return VIEW_POST_MANAGE_SECTION;
    }
    
    /**
     * Deletes the relevant Section object.
     * 
     * @param model - a HashMap that contains information of the Section
     * @param section - an instance of Section
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a Section instance.
     */
    @RequestMapping(REQ_MAP_VALUE_DELETE)
    public final String deleteSection(final ModelMap model, @ModelAttribute(MODEL_ATT_SECTION) final Section section)
            throws AkuraAppException {
    
        try {
            
            // get the section id - it is auto generated on DB table
            int id = section.getSectionId();
            
            // get Section object by passing relevant Section id.
            Section findSection = staffCommonService.findSection(id);
            
            // call delete method to be deleted relevant class object
            staffCommonService.deleteSection(findSection);
            return VIEW_POST_MANAGE_SECTION;
            
        } catch (AkuraAppException e) {
            
            // check , if there are violations of deleting record, when they have references.
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                Section newSection = new Section();
                model.addAttribute(MESSAGE, message);
                model.addAttribute(MODEL_ATT_SECTION, newSection);
                return VIEW_GET_MANAGE_SECTION;
            } else {
                throw e;
            }
        }
    }
    
    /**
     * Method is to get section list.
     * 
     * @return list of section
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_SECTION_LIST)
    public List<Section> populateSectionList() throws AkuraAppException {
    
        return SortUtil.sortSectionList(staffCommonService.getSectionList());
    }
}
