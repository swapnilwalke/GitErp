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

package com.virtusa.akura.student.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.dto.StudentSubTermMarkDTO;
import com.virtusa.akura.api.dto.SubTerm;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;

/**
 * This is a controller where controls manage student sub term marks details.
 * 
 * @author Virtusa Corporation
 */

@Controller
public class StudentSubTermMarkController {
    
    /** attribute for holding student id. */
    private static final String STUDENT_ID = "studentId";
    
    /** attribute for holding view page. */
    private static final String VIEW_MARKS_DETAILS_PAGE = "student/marksDetails";
    
    /** attribute for holding action. */
    private static final String VIEW_ACTION_FOR_SHOW_SUBTERM_VIEW = "/showSubTermView.htm";
    
    /**
     * commonService To invoke service methods.
     */
    private CommonService commonService;
    
    /**
     * studentService To invoke service methods.
     */
    private StudentService studentService;
    
    /**
     * setter to inject CommonService object.
     * 
     * @param commonServiceVal the commonService to set
     */
    public void setCommonService(CommonService commonServiceVal) {

        this.commonService = commonServiceVal;
    }
    
    /**
     * setter to inject StudentService object.
     * 
     * @param studentServiceVal the studentService to set
     */
    public void setStudentService(StudentService studentServiceVal) {

        this.studentService = studentServiceVal;
    }
    
    /**
     * Method to map GET requests from JSP.
     * 
     * @return the JSP to display.
     * @param model to hold model object for JSP.
     * @param request of the HttpServletRequest
     * @param session - HttpSession attribute
     * @throws AkuraAppException when exception occurs
     */
    @RequestMapping(value = VIEW_ACTION_FOR_SHOW_SUBTERM_VIEW, method = RequestMethod.GET)
    public String showSubTermView(ModelMap model, HttpServletRequest request, HttpSession session)
            throws AkuraAppException {

        // Getting jsp request parameters.
        String selectedYear = request.getParameter(AkuraWebConstant.YEAR);
        String subjectStr = request.getParameter(AkuraConstant.SUBJECT).trim();
        int intStudentId = 0;
        
        if (session.getAttribute(STUDENT_ID) != null) { // Gets the studentId from the session.
        
            intStudentId = (Integer) session.getAttribute(STUDENT_ID);
        }
        // Parsing parameters to integer type variables.
        int year = Integer.parseInt(selectedYear);
        
        // Retrieving term and sub term lists
        List<SubTerm> subtrmList = commonService.getSubTermList();
        List<Term> trmList = commonService.getTermList();
        
        List<StudentSubTermMarkDTO> studentSubTermMarks =
                studentService.getStudentSubTermMarksBySubject(intStudentId, DateUtil.getDate(year), subjectStr);
        
        // Initializing the map to tree map
        Map<String, Object> finalMap = new TreeMap<String, Object>();
        if (!studentSubTermMarks.isEmpty()) { // If StudentSubTermMark objects list is not empty
        
            String gradingDes = AkuraConstant.EMPTY_STRING;
            for (StudentSubTermMarkDTO studentSubTermMarkDTO : studentSubTermMarks) {
                String keyValue =
                        studentSubTermMarkDTO.getTerm() + AkuraWebConstant.UNDERSCORE_STRING
                                + studentSubTermMarkDTO.getSubTerm();
                
                // if the student is absent for the examination
                if (studentSubTermMarkDTO.isAbsent()) {
                    
                    if (studentSubTermMarkDTO.getGradingId() == null || studentSubTermMarkDTO.getMarks() == null) {
                        gradingDes = AkuraWebConstant.ABSENT_STRING;
                    } else {
                        gradingDes = AkuraConstant.EMPTY_STRING;
                    }
                } else {
                    
                    if (studentSubTermMarkDTO.getGradingId() != null) {
                        
                        Grading gradingObj = commonService.findGradingById(studentSubTermMarkDTO.getGradingId());
                        gradingDes = gradingObj.getGradeAcronym();
                    } else if (studentSubTermMarkDTO.getMarks() != null) {
                        gradingDes = String.valueOf(studentSubTermMarkDTO.getMarks());
                    } else {
                        gradingDes = AkuraConstant.EMPTY_STRING;
                    }
                }
                
                // adding sub term and grading to the final map
                finalMap.put(keyValue, gradingDes);
            }
        }
        
        // filling other sub terms with null values to send the map to jsp.
        fillOtherSubterms(subtrmList, finalMap);
        
        // adding required term and sub term data to request object
        request.setAttribute(AkuraWebConstant.SUBJECT_STRING, subjectStr);
        request.setAttribute(AkuraWebConstant.YEAR_STRING, year);
        request.setAttribute(AkuraWebConstant.TERM_LIST, trmList);
        request.setAttribute(AkuraWebConstant.SUB_TERM_LIST, subtrmList);
        request.setAttribute(AkuraWebConstant.LAST_LIST, finalMap);
        
        return VIEW_MARKS_DETAILS_PAGE;
    }
    
    /**
     * Filling other sub terms with null values to send the map to jsp.
     * 
     * @param subtrmList list of sub terms to be filled.
     * @param finalMap a map to add sub term and grading to the final map
     */
    private void fillOtherSubterms(List<SubTerm> subtrmList, Map<String, Object> finalMap) {

        for (SubTerm subTermNew : subtrmList) {
            String verifyKey =
                    subTermNew.getTermId() + AkuraWebConstant.UNDERSCORE_STRING + subTermNew.getDescription();
            
            if (!finalMap.containsKey(verifyKey)) {
                finalMap.put(verifyKey, null);
            }
        }
    }
}
