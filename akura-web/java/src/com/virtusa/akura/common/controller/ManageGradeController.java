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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.GradeValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * The controller is to handle Add/Edit/Delete/View operations for Grade and ClassGrade reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageGradeController {
    
    /** Class Size. */
    private static final String CLASS_SIZE = "classSize";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(ManageGradeController.class);
    
    /** view post method manage grade. */
    private static final String VIEW_POST_MANAGE_GRADE = "redirect:manageGrade.htm";
    
    /** view get method manage grade. */
    private static final String VIEW_GET_MANAGE_GRADE = "reference/manageGrade";
    
    /** model attribute of grade. */
    private static final String MODEL_ATT_GRADE = "grade";
    
    /** model attribute of class grade map. */
    private static final String MODEL_ATT_GRADE_MAP = "gradeMap";
    
    /** model attribute of school Class List. */
    private static final String MODEL_ATT_SCHOOL_CLASS_LIST = "schoolClassList";
    
    /** request mapping value for save or update ClassGrade. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateGrade.htm";
    
    /** request mapping value for delete ClassGrade. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteGrade.htm";
    
    /** request parameter for class id. */
    private static final String REQ_CLASS_ID = "classId";
    
    /** request parameter for grade name. */
    private static final String REQ_SELECTEDGRADE = "selectedGrade";
    
    /** Check for classes field empty. */
    private static final String FIELD_NAME = "gradeId";
    
    /** this is an appender to append string. */
    private static final String APPENDER = ",";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY = "REF.UI.GRADE.DESCRIPTION.EXIST";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_DELETE = "REF.UI.GRADE.DELETE";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_EDIT = "REF.UI.GRADE.EDIT";
    
    /** attribute for holding message. */
    private static final String EDITPANE = "editpane";
    
    /** Model attribute of "selectedObjId". */
    private static final String SELECTED_OBJ_ID = "selectedObjId";

    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** {@link CommonService}. */
    private CommonService commonService;
    
    /**
     * Represents gradeValidator instance of {@link GradeValidator}.
     */
    private GradeValidator gradeValidator;
    
    /**
     * Set the commonService to inject the service.
     * 
     * @param commonServiceValue the commonService to set
     */
    public void setCommonService(CommonService commonServiceValue) {

        this.commonService = commonServiceValue;
    }
    
    /**
     * Set the gradeValidator to inject the validator.
     * 
     * @param gradeValidatorValue the gradeValidator to set
     */
    public void setGradeValidator(GradeValidator gradeValidatorValue) {

        this.gradeValidator = gradeValidatorValue;
    }
    
    /**
     * Handle GET requests for Grade_details view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showGradeDetail(ModelMap model) {

        Grade grade = (Grade) model.get(MODEL_ATT_GRADE);
        
        if (grade == null) {
            grade = new Grade();
        }
        
        model.addAttribute(MODEL_ATT_GRADE, grade);
        
        return VIEW_GET_MANAGE_GRADE;
    }
    
    /**
     * Method is to return list of grades with classes belongs to.
     * 
     * @return a map which has key - grade and value - list of classes.
     * @throws AkuraException - Details exception
     */
    @ModelAttribute(MODEL_ATT_GRADE_MAP)
    public Map<String, String> populateClassGrades() throws AkuraException {

        List<Grade> grades = commonService.getGradeList();
        grades = SortUtil.sortGradesList(grades);
        Map<String, String> newMap = new LinkedHashMap<String, String>();
        
        for (Grade grade : grades) {
            
            int classGradeSize = 0;
            StringBuilder builder = new StringBuilder();
            List<ClassGrade> classGrades = commonService.getClassGradeListByGrade(grade);
            Iterator<ClassGrade> classGradeIter = classGrades.iterator();
            
            while (classGradeIter.hasNext()) {
                
                classGradeSize++;
                
                ClassGrade classGrade = (ClassGrade) classGradeIter.next();
                SchoolClass schoolClass = classGrade.getSchoolClass();
                builder.append(schoolClass.getDescription());
                
                // Don't append , with the last class
                if (classGradeSize != classGrades.size()) {
                    builder.append(APPENDER);
                }
            }
            
            newMap.put(grade.getDescription(), builder.toString());
        }
        
        return newMap;
    }
    
    /**
     * Method is to return schoolClass list.
     * 
     * @param model - {@link ModelMap}
     * @return list of schoolClass
     * @throws AkuraAppException - Detailed exception.
     */
    @ModelAttribute(MODEL_ATT_SCHOOL_CLASS_LIST)
    public List<SchoolClass> populateSchoolClasss(ModelMap model) throws AkuraAppException {

        List<SchoolClass> schoolClasses = commonService.getClassList();       
        model.addAttribute(CLASS_SIZE, schoolClasses.size());
        
        return SortUtil.sortGradeClasses(schoolClasses);
    }
    
    /**
     * This method handles Add/Edit Grade and ClassGrade details.
     * 
     * @param grade - Grade obj.
     * @param request - HttpServletRequest
     * @param model {@link ModelMap}
     * @param bindingResult - holds errors
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - throw detailed exception.
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateClassGrade(@ModelAttribute(MODEL_ATT_GRADE) Grade grade, BindingResult bindingResult,
            HttpServletRequest request, ModelMap model) throws AkuraAppException {

        gradeValidator.validate(grade, bindingResult);
        String gradeName = grade.getDescription().trim();
        String selectedGradeName = request.getParameter(REQ_SELECTEDGRADE);
        
  
        if (bindingResult.hasErrors() || (request.getParameterValues(REQ_CLASS_ID) == null) || "".equals(gradeName)) {
            if ((request.getParameterValues(REQ_CLASS_ID) == null) || "".equals(gradeName)) {
        
                model.addAttribute(EDITPANE, EDITPANE);
                model.addAttribute(SELECTED_OBJ_ID, selectedGradeName);               
                bindingResult.rejectValue(FIELD_NAME, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            }
           // model.addAttribute(EDITPANE, grade.getGradeId());
            model.addAttribute(EDITPANE, EDITPANE);
            model.addAttribute(SELECTED_OBJ_ID, selectedGradeName); 
            return VIEW_GET_MANAGE_GRADE;
        } else {
            
           
            
            // Check whether the grade is already exist and populate a message
            // to user.
            if (isExistsGrade(gradeName, selectedGradeName)) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
                Grade newGrade = new Grade();
                
                model.addAttribute(EDITPANE, EDITPANE);
                model.addAttribute(SELECTED_OBJ_ID, selectedGradeName);
                model.addAttribute(MODEL_ATT_GRADE, newGrade);
                model.addAttribute(MESSAGE, message);        
                return VIEW_GET_MANAGE_GRADE;
            } else {
                
                try {
                    // If selectedGradeName is empty, add a new grade otherwise
                    // update already existing grade.
                    grade = saveorUpdateGrade(grade, gradeName, selectedGradeName);
                    
                    // Gets the classes for the selected grade.
                    String[] oldClassIds = getClassesForGrade(grade);
                    
                    // There is no update for class_grade table, we have
                    // add/remove or add&remove, so that we use the below logic to be done.
                    
                    // checked(from check boxes) new ids of classes
                    String[] classIds = request.getParameterValues(REQ_CLASS_ID);
                    
                    // add new classes for the garde.
                    addClassesForGrade(grade, classIds, oldClassIds);
                    
                    // Remove old clsses from the grade.
                    removeClassesFromGrade(grade, oldClassIds, classIds);
                    
                    // update ClassGrade description of existing records.
                    updateClassGradeDescription(grade);
                    
                } catch (AkuraAppException e) {
                    if (e.getCause() instanceof DataIntegrityViolationException) {
                        String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_EDIT);
                        Grade newGrade = new Grade();
                        model.addAttribute(EDITPANE, EDITPANE);
                        model.addAttribute(SELECTED_OBJ_ID, selectedGradeName);
                        model.addAttribute(MODEL_ATT_GRADE, newGrade);
                        model.addAttribute(MESSAGE, message);
                        
                        return VIEW_GET_MANAGE_GRADE;
                    } else {
                        throw e;
                    }
                }
            }
        }
        return VIEW_POST_MANAGE_GRADE;
    }
    
    /**
     * Removes the old classes from the grade.
     * 
     * @param grade the grade.
     * @param oldClassIds the old classes.
     * @param classIds the new classes.
     * @throws AkuraAppException the AkuraAppException.
     */
    private void removeClassesFromGrade(Grade grade, String[] oldClassIds, String[] classIds) throws AkuraAppException {

        // Get the class ids to be removed.
        @SuppressWarnings("unchecked")
        List<String> toBeRomoved = ListUtils.subtract(Arrays.asList(oldClassIds), Arrays.asList(classIds));
        
        // toBeRomoved list is not empty means, removing already
        // assigned classes from a grade
        if (!toBeRomoved.isEmpty()) {
            
            for (Object strClassId : toBeRomoved) {
                int classId = Integer.parseInt(strClassId + String.valueOf(""));
                List<ClassGrade> removeClassGrade =
                        commonService.getClassGradeByGradeIdAndClassId(grade.getGradeId(), classId);
                
                commonService.deleteClassGradeList(removeClassGrade);
            }
        }
    }
    
    /**
     * Gets the clases for the garde.
     * 
     * @param grade the garde.
     * @return the classes for the gared.
     * @throws AkuraAppException the AkuraAppException.
     */
    private String[] getClassesForGrade(Grade grade) throws AkuraAppException {

        List<ClassGrade> classGrades = commonService.getClassGradeListByGrade(grade);
        String[] oldClassIds = new String[classGrades.size()];
        int i = 0;
        
        for (ClassGrade oldClassGrade : classGrades) {
            oldClassIds[i] = oldClassGrade.getSchoolClass().getClassId() + String.valueOf("");
            i++;
        }
        return oldClassIds;
    }
    
    /**
     * Adds Classes for the garde.
     * 
     * @param grade the garde.
     * @param classIds the selected Class ids.
     * @param oldClassIds the previous class ids.
     * @throws AkuraAppException the AkuraAppException.
     */
    private void addClassesForGrade(Grade grade, String[] classIds, String[] oldClassIds) throws AkuraAppException {

        ClassGrade classGrade;
        // Get the class ids to be added.
        @SuppressWarnings("rawtypes")
        List toBeAdd = ListUtils.subtract(Arrays.asList(classIds), Arrays.asList(oldClassIds));
        
        // toBeAdd list is not empty means, assigning classes
        // additionally for a grade
        if (!toBeAdd.isEmpty()) {
            classGrade = new ClassGrade();
            
            for (Object strClassId : toBeAdd) {
                int classId = Integer.parseInt(strClassId + String.valueOf(""));
                SchoolClass schoolClass = commonService.findClass(classId);
                classGrade.setGrade(grade);
                classGrade.setSchoolClass(schoolClass);
                classGrade.setDescription(grade.getDescription().trim() + schoolClass.getDescription().trim());
                
                commonService.saveClassGrade(classGrade);
            }
        }
    }
    
    /**
     * update existing Class grade description.
     * 
     * @param grade the grade.
     * @throws AkuraAppException the AkuraAppException.
     */
    private void updateClassGradeDescription(Grade grade) throws AkuraAppException {

        // Get the existing class grade list.
        List<ClassGrade> classGradeList = commonService.getClassGradeListByGrade(grade);
        
        if (!classGradeList.isEmpty()) {
            
            for (ClassGrade classGrade : classGradeList) {
                classGrade.setDescription(grade.getDescription().trim()
                        + classGrade.getSchoolClass().getDescription().trim());
                commonService.updateClassGrade(classGrade);
            }
        }
    }
    
    /**
     * Saves or Updates the Grade.
     * 
     * @param grade the grade to save.
     * @param gradeName grade name.
     * @param selectedGradeName the slected grade name.
     * @return the updated grade.
     * @throws AkuraAppException the AkuraAppException
     */
    private Grade saveorUpdateGrade(Grade grade, String gradeName, String selectedGradeName) throws AkuraAppException {

        // If selectedGradeName is empty, add a new grade otherwise
        // update already existing grade.
        if (!"".equals(selectedGradeName)) {
            Grade gradeByName = commonService.getGradeByGradeName(selectedGradeName);
            gradeByName.setDescription(gradeName);
            commonService.updateGrade(gradeByName);
            grade = gradeByName;
        } else {
            grade.setDescription(gradeName);
            commonService.saveGrade(grade);
        }
        return grade;
    }
    
    /**
     * Delete a grade and classes belongs to.
     * 
     * @param request {@link HttpServletRequest}
     * @param model {@link ModelMap}
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - throw this.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteGrade(HttpServletRequest request, ModelMap model) throws AkuraAppException {

        String description = request.getParameter(REQ_SELECTEDGRADE);
        Grade grade = commonService.getGradeByGradeName(description);
        
        List<GradeSubject> gradeSubjectList = commonService.getGradeSubjectIdListByGrade(grade.getGradeId());
        
        List<ClassGrade> classGrades = commonService.getClassGradeListByGrade(grade);
        
        List<Integer> classGradeIds = new ArrayList<Integer>();
        
        for (ClassGrade classGrade : classGrades) {
            classGradeIds.add(classGrade.getClassGradeId());
        }
        
        try {
            if ((gradeSubjectList == null || gradeSubjectList.isEmpty())) {
                commonService.deleteClassGradeList(classGrades);
                commonService.deleteGrade(grade);
            } else {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                Grade newGrade = new Grade();
                model.addAttribute(MODEL_ATT_GRADE, newGrade);
                model.addAttribute(MESSAGE, message);
                
                return VIEW_GET_MANAGE_GRADE;
            }
            
        } catch (DataAccessException ex) {
            LOG.error("ManageGradeController - error occured while deleting list of class grade " + classGrades + "-->"
                    + ex.toString());
            throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, ex);
        } catch (AkuraAppException ex) {
            if (ex.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                Grade newGrade = new Grade();
                model.addAttribute(MODEL_ATT_GRADE, newGrade);
                model.addAttribute(MESSAGE, message);
                
                return VIEW_GET_MANAGE_GRADE;
            } else {
                LOG.error("ManageGradeController - error occured while deleting grade object " + grade + "-->"
                        + ex.toString());
                throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, ex);
            }
        }
        
        return VIEW_POST_MANAGE_GRADE;
    }
    
    /**
     * Check whether Grade is already added.
     * 
     * @param gradeName - String
     * @param selectedGradeName - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    private boolean isExistsGrade(String gradeName, String selectedGradeName) throws AkuraAppException {

        boolean isExists = false;
        List<Grade> grades = commonService.getGradeList();
        
        for (Grade grade : grades) {
            
            boolean check =
                    (!"".equals(selectedGradeName)) ? isExists : grade.getDescription().equalsIgnoreCase(gradeName);
            
            if (check) {
                isExists = check;
                break;
            }
        }
        return isExists;
    }
}
