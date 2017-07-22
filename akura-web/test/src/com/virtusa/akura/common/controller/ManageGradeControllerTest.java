
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.GradeValidator;


/**
 * @author Virtusa Corporation
 *
 */

public class ManageGradeControllerTest extends BaseWebTest {

    /** Represents an instance of ManageGradeController. */
    private ManageGradeController gradeController;
    /** Represents an instance of Grade. */
    private Grade grade;
    /** Represents an instance of SchoolClass. */
    private SchoolClass schoolClass;
    /** Represents an instance of GradeValidator. */
    @Autowired
    private GradeValidator gradeValidator;
//    /** Represents an instance of ClassGrade. */
//    private ClassGrade classGrade;
//
//    List<ClassGrade> classGrades;
    /** CommonService attribute for holding commonService. */
    @Autowired
    private CommonService commonService;

    /** Represents an instance of MockHttpServletRequest. */
    private MockHttpServletRequest request;

    /** Represents an instance of ModelMap. */
    private ModelMap model;

    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws AkuraAppException {
        grade = new Grade();
        grade.setDescription("Grade-15");
        grade.setModifiedTime(new Date());

//        grade = schoolService.saveGrade(grade);
//        assertNotNull("Grade type should not be null " + grade);

        schoolClass = new SchoolClass();
        schoolClass.setDescription("ScoolClass");
        schoolClass.setModifiedTime(new Date());

        schoolClass = commonService.addClass(schoolClass);
        assertNotNull("SchoolClass type should not be null " + schoolClass);

//        // Instantiates classGrade object.
//        classGrade = new ClassGrade();
//        classGrade.setSchoolClass(schoolClass);
//        classGrade.setGrade(grade);
//        classGrade.setDescription("ClassGrade");
//        classGrade.setModifiedTime(new Date());
//
//        classGrade = schoolService.saveClassGrade(classGrade);
//        assertNotNull("ClassGrade type should not be null " + classGrade);

//        classGrades = new ArrayList<ClassGrade>();
//        classGrades.add(classGrade);


        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        model.addAttribute("grade", grade);
        request = new MockHttpServletRequest();

        gradeController = new ManageGradeController();
        gradeController.setCommonService(commonService);

    }

    /**
     * Test method for ShowGradeDetail of ManageGradeController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowGradeDetail() throws AkuraAppException {
//        try {
        String result = gradeController.showGradeDetail(model);
        assertNotNull("ModelAndView should not be null", result);

//
//        } catch (AkuraAppException e) {
//            e.printStackTrace();
//        }
    }
    /**
     * Test method for SaveOrUpdateClassGrade of ManageGradeController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateClassGrade() throws AkuraAppException {
        try {

            request.addParameter("selectedGrade", "");
            request.addParameter("classId", ""+schoolClass.getClassId());

            gradeController.setGradeValidator(gradeValidator);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view = gradeController.saveOrUpdateClassGrade(grade, mockBindingResult, request, model);

            assertEquals("reference/manageGrade", view);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView = gradeController.saveOrUpdateClassGrade(grade, mockBindingResult, request, model);
            assertEquals("redirect:manageGrade.htm", savedView);
            assertTrue(model.size() > 0);
        } catch(AkuraAppException e) {
            e.printStackTrace();
        } finally {
            List<ClassGrade> list = commonService.getClassGradeList();
            ClassGrade temp = list.get(list.size()-1);
            List<ClassGrade> tempList = new ArrayList<ClassGrade>();
            tempList.add(temp);
            commonService.deleteClassGradeList(tempList);
        }
    }

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        try {

           // schoolService.deleteClassGradeList(classGrades);
            commonService.deleteClass(schoolClass);
            //commonService.deleteEducationalQualification(educationalQualification.getEducationalQualificationId());
            commonService.deleteGrade(grade);
//            request.addParameter("selectedGrade", ""+grade.getDescription());
//            String result = gradeController.deleteGrade(request, model);
//             assertNotNull("Model and View should not be null " + result);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
