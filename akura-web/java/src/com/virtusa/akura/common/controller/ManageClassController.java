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

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.SchoolClassValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * This class handles the requests for domains of the Add/Edit/Delete, related functions.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageClassController {
    
    /** string attribute for holding description error message. */
    private static final String ERROR_MSG_SCHOOLCLASS_DESCRIPTION = "REF.UI.SCHOOLCLASS.DESCRIPTION";
    
    /** string attribute for holding delete error message. */
    private static final String ERROR_MSG_SCHOOLCLASS_DELETE = "REF.UI.SCHOOLCLASS.DELETE";
    
    /** view post method add school class. */
    private static final String VIEW_POST_MANAGE_ADD_CLASSES = "redirect:manageClass.htm";
    
    /** view get method add class. */
    private static final String VIEW_GET_MANAGE_ADD_CLASSES = "reference/manageClass";
    
    /** request mapping value for save or update school class. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/manageSaveOrUpdateSchoolClass.htm";
    
    /** model attribute of school class. */
    private static final String MODEL_ATT_SCHOOL_CLASS = "schoolClass";
    
    /** model attribute of school class. */
    private static final String MODEL_ATT_SCHOOL_CLASS_LIST = "schoolClassList";
    
    /** request mapping value for delete school class. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteSchoolClass.htm";
    
    /** request mapping value for add class. */
    private static final String REQ_MAP_VALUE_ADD = "/manageClass.htm";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** attribute for holding message. */
    private static final String EDITPANE = "editpane";
    
    /** Represents an instance of schoolClassValidator. */
    private SchoolClassValidator schoolClassValidator;
    
    /** Model attribute of "selectedObjId". */
    private static final String SELECTED_OBJ_ID = "selectedObjId";
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /**
     * Sets an instance of SchoolClassValidator.
     * 
     * @param schoolClassValidatorVal - the relevant instance of SchoolClassValidator.
     */
    public void setSchoolClassValidator(SchoolClassValidator schoolClassValidatorVal) {

        this.schoolClassValidator = schoolClassValidatorVal;
    }
    
    /**
     * setter method from CommonService.
     * 
     * @param commonServiceVal - CommonService
     */
    
    public void setCommonService(CommonService commonServiceVal) {

        this.commonService = commonServiceVal;
    }
    
    /**
     * Method is to return SchoolClass reference data.
     * 
     * @throws AkuraAppException - AkuraAppException
     * @return schoolClassList - schoolClass reference list.
     */
    @ModelAttribute(MODEL_ATT_SCHOOL_CLASS_LIST)
    public final List<SchoolClass> populateSchoolClassList() throws AkuraAppException {

        return SortUtil.sortSchoolClassList(commonService.getSchoolClassList());
    }
    
    /**
     * Initializes the reference data that is to be previewed on the UI.
     * 
     * @param model - a HashMap that contains information of the class.
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(REQ_MAP_VALUE_ADD)
    public final String addClass(final ModelMap model) throws AkuraAppException {

        SchoolClass schoolClass = new SchoolClass();
        model.addAttribute(MODEL_ATT_SCHOOL_CLASS, schoolClass);
        return VIEW_GET_MANAGE_ADD_CLASSES;
    }
    
    /**
     * Saves or updates the relevant SchoolClass.
     * 
     * @param model - a HashMap that contains information of the SchoolClass
     * @param schoolClass - an instance of SchoolClass
     * @param result - containing list of errors and schoolClass instance to which data was bound
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when saving or updating a SchoolClass
     *         instance.
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public final String saveOrUpdateSchoolClass(@ModelAttribute(MODEL_ATT_SCHOOL_CLASS) final SchoolClass schoolClass,
            BindingResult result, final ModelMap model) throws AkuraAppException {

        // call validate method , to be validated class type
        schoolClassValidator.validate(schoolClass, result);
        
        if (result.hasErrors()) {
            model.addAttribute(EDITPANE, schoolClass.getDescription());
            model.addAttribute(SELECTED_OBJ_ID, schoolClass.getClassId());
            
            return VIEW_GET_MANAGE_ADD_CLASSES;
        } else {
            // get the class description
            String description = schoolClass.getDescription();
            // get the class id - it is generated auto on DB table
            int id = schoolClass.getClassId();
            try {
                // check class record has been added already
                if (id > 0) {
                    this.updateClassGradeDescription(id, description);
                    
                    // call addClass , to be updated the class
                    updateClass(id, description);
                } else {
                    // call saveClass , to be added(save) the class
                    saveClass(schoolClass, description);
                }
                return VIEW_POST_MANAGE_ADD_CLASSES;
            } catch (AkuraAppException e) {
                // check , if there are violations of add/upadate record, when they have references.
                if (e.getCause() instanceof DataIntegrityViolationException) {
                    model.addAttribute(SELECTED_OBJ_ID, schoolClass.getClassId());
                    model.addAttribute(EDITPANE, schoolClass.getDescription());
                    schoolClassMessages(model, schoolClass);
                    return VIEW_GET_MANAGE_ADD_CLASSES;
                } else {
                    throw e;
                }
            }
        }
    }
    
    /**
     * this method is for saving class.
     * 
     * @param schoolClass - an instance of SchoolClass
     * @param description - name of the class.
     * @throws AkuraAppException - The exception details that occurred when saving a SchoolClass instance.
     */
    private void saveClass(SchoolClass schoolClass, String description) throws AkuraAppException {

        // set the description of the class, to the SchoolClass object.
        schoolClass.setDescription(description);
        // call add method with(class description)to be added the class
        commonService.addSchoolClass(schoolClass);
    }
    
    /**
     * this method is for updating class.
     * 
     * @param classId - id of the class
     * @param description - name of the class.
     * @throws AkuraAppException - The exception details that occurred when updating a SchoolClass instance.
     */
    private void updateClass(int classId, String description) throws AkuraAppException {

        // get class by given it's generated id
        SchoolClass findSchoolClass = commonService.findSchoolClassById(classId);
        // set the class name to that class id
        findSchoolClass.setDescription(description);
        // pass SchoolClass object(with class name) to be updated
        commonService.updateSchoolClass(findSchoolClass);
    }
    
    /**
     * Deletes the relevant SchoolClass object.
     * 
     * @param model - a HashMap that contains information of the SchoolClass
     * @param schoolClass - an instance of SchoolClass
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a SchoolClass instance.
     */
    @RequestMapping(REQ_MAP_VALUE_DELETE)
    public final String deleteSchoolClass(final ModelMap model,
            @ModelAttribute(MODEL_ATT_SCHOOL_CLASS) final SchoolClass schoolClass) throws AkuraAppException {

        String message;
        try {
            // get the class id - it is generated auto on DB table
            int id = schoolClass.getClassId();
            // get schoolclass object by passing relavant class id.
            SchoolClass findSchoolClass = commonService.findSchoolClass(id);
            // call delete method to be deleted relavant class object
            commonService.deleteSchoolClass(findSchoolClass);
            return VIEW_POST_MANAGE_ADD_CLASSES;
        } catch (AkuraAppException e) {
            // check , if there are violations of deleting record, when they have references.
            if (e.getCause() instanceof DataIntegrityViolationException) {
                message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_SCHOOLCLASS_DELETE);
                SchoolClass newSchoolClass = new SchoolClass();
                model.addAttribute(MESSAGE, message);
                model.addAttribute(MODEL_ATT_SCHOOL_CLASS, newSchoolClass);
                return VIEW_GET_MANAGE_ADD_CLASSES;
            } else {
                throw e;
            }
        }
    }
    
    /**
     * schoolClass method to be kept error messages in model attribute.
     * 
     * @param model - a HashMap that contains information of the SchoolClass
     * @param schoolClass - SchoolClass object to set to the model.
     */
    private void schoolClassMessages(final ModelMap model, SchoolClass schoolClass) {

        String message;
        message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_SCHOOLCLASS_DESCRIPTION);
        model.addAttribute(MODEL_ATT_SCHOOL_CLASS, schoolClass);
        model.addAttribute(MESSAGE, message);
        
    }
    
    /**
     * update existing Class grade description.
     * 
     * @param classId class id.
     * @param description class description.
     * @throws AkuraAppException the AkuraAppException.
     */
    private void updateClassGradeDescription(int classId, String description) throws AkuraAppException {

        // Get the existing class grade list.
        List<ClassGrade> classGradeList = commonService.getClassGradeListByClassId(classId);
        
        if (!classGradeList.isEmpty()) {
            
            for (ClassGrade classGrade : classGradeList) {
                classGrade.setDescription(classGrade.getGrade().getDescription().trim() 
                        + description.trim());
                commonService.updateClassGrade(classGrade);
            }
        }
    }
    
}
