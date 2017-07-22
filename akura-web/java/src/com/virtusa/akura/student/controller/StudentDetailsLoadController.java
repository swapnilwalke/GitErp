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

package com.virtusa.akura.student.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;

/**
 * The StudentDetailsLoadController loads the primary key of the student on to the session.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class StudentDetailsLoadController {
    
    /**
     * Represents the name of the requested view.
     */
    private static final String LOAD_STUDENT = "/loadStudentDetails.htm";
    
    /**
     * Represents the name of the view to be redirected.
     */
    private static final String STUDENT_DETAILS = "redirect:studentDetails.htm";
    
    /**
     * Represents the attribute name of the session for primary key of the student.
     */
    private static final String STUDENT_ID = "studentId";
    
    /**
     * Represents the attribute name of the session for primary key of the student class.
     */
    private static final String STUDENT_CLASS = "studentClass";
    
    /**
     * Represent the constant for String "noSubjects".
     */
    private static final String SUBJECTS_EXIST = "subjectsExist";
    
    /**
     * Represent the constant for String "on".
     */
    private static final String ON = "on";
    
    /**
     * Represent the common service instance.
     */
    private CommonService commonService;
    
    /**
     * Represent the student service instance.
     */
    private StudentService studentService;
    
    /**
     * Get the common service object.
     * 
     * @return CommonService object.
     */
    public CommonService getCommonService() {

        return commonService;
    }
    
    /**
     * Set the common service object.
     * 
     * @param commonServiceObj object to be set.
     */
    public void setCommonService(CommonService commonServiceObj) {

        this.commonService = commonServiceObj;
    }
    
    /**
     * Get the student service object.
     * 
     * @return StudentService object.
     */
    public StudentService getStudentService() {

        return studentService;
    }
    
    /**
     * Set the student service object.
     * 
     * @param studentServiceVal object to be set.
     */
    public void setStudentService(StudentService studentServiceVal) {

        this.studentService = studentServiceVal;
    }
    
    /**
     * Sets the primary key of the student to session.
     * 
     * @param request - an instance of HttpServletRequest that contains the request data.
     * @param session - an instance of HttpSession contains the primary key of the student.
     * @return - the name of the view to be redirected.
     * @throws AkuraAppException throws when fails.
     */
    @RequestMapping(LOAD_STUDENT)
    public String loadData(final HttpServletRequest request, final HttpSession session) throws AkuraAppException {

        String studentIdString = request.getParameter(STUDENT_ID);
        String studentClass = request.getParameter(STUDENT_CLASS);
        session.removeAttribute(SUBJECTS_EXIST);
        
        if (studentIdString != null) {
            
            final int studentId = Integer.parseInt(studentIdString);
            
            if (studentClass != null && !studentClass.equals(AkuraConstant.EMPTY_STRING)) {
                session.setAttribute(STUDENT_CLASS, studentClass);
                
                List<ClassGrade> classGradeList = commonService.getClassGradeByName(studentClass);
                
                if (!classGradeList.isEmpty()
                        && studentService.getStudentTermMarksByStudentIdYearClassGrade(studentId,
                                DateUtil.currentYearOnly(), classGradeList.get(0).getClassGradeId()).size() != 0) {
                    
                    session.setAttribute(SUBJECTS_EXIST, ON);
                    
                }
            }
            session.setAttribute(STUDENT_ID, studentId);
        }
        
        return STUDENT_DETAILS;
    }
}
