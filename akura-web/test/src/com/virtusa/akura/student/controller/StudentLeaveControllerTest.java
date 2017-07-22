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

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentLeave;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.student.validator.StudentLeaveValidator;

/**
 * @author Virtusa Corporation
 */

public class StudentLeaveControllerTest extends BaseWebTest {

    /** StudentService attribute for holding studentService. */
    @Autowired
    private StudentService studentService;

    /** Holds CommonService instance. */
    @Autowired
    private CommonService commonService;

    /** Holds StudentLeaveController instance. */
    private StudentLeaveController studentLeaveController;

    /** Holds StudentLeaveValidator instance. */
    @Autowired
    private StudentLeaveValidator studentLeaveValidator;

    /** Represents an instance of MockHttpSession. */
    private MockHttpSession session;

    /** Represents an instance of MockHttpServletRequest. */
    private MockHttpServletRequest request;

    /** Represents an instance of ModelMap. */
    private ModelMap model;

    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;

    /** Holds StudentLeave instance. */
    private StudentLeave studentLeave;

    /** Holds Student instance. */
    private Student student;

    /** Holds Language instance. */
    private Language language;

    /** Holds LanguageDao instance. */
    @Autowired
    private LanguageDao languageDao;

    /** Holds Religion instance. */
    private Religion religion;

    /** Holds MethodOfTravel instance. */
    private MethodOfTravel methodOfTravel;

    /** Holds BloodGroup instance. */
    private BloodGroup bloodGroup;

    /** Holds House instance. */
    private House house;

    /** Holds province instance. */
    private Province province;

    /** Holds city instance. */
    private City city;

    /** Holds district instance. */
    private District district;

    /** Represents an instance of Nationality. */
    private Nationality nationality;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws AkuraAppException {

        // Instantiates a Language object.
        language = new Language();
        language.setLanguage("Sinhala111ww");
        language.setModifiedTime(new Date());

        language = languageDao.save(language);
        assertNotNull(" language saved to db " + language);

        religion = new Religion();
        religion.setDescription("Religion11ww");
        religion.setModifiedTime(new Date());

        religion = commonService.createReligion(religion);
        assertNotNull(" religion saved to db " + religion);

        // Instantiates a Method of travel object.
        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("Bus11ww");
        methodOfTravel.setModifiedTime(new Date());

        methodOfTravel = commonService.createMethodOfTravel(methodOfTravel);
        assertNotNull(" methodOftravel saved to db " + methodOfTravel);

        // Instantiates a BloodGroup object.
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("AAAA1ww");
        bloodGroup.setModifiedTime(new Date());

        bloodGroup = commonService.addBloodGroup(bloodGroup);
        assertNotNull(" bloodGroup saved to db " + bloodGroup);

        // Instantiates a House object.
        house = new House();
        house.setName("House11ww");
        house.setModifiedTime(new Date());

        house = commonService.createHouse(house);
        assertNotNull(" house saved to db " + house);

        // Instantiate a Province object.
        province = new Province();
        province.setDescription("North22ww");

        province = commonService.addProvince(province);
        assertNotNull(" province saved to db " + province);

        // Instantiates a District object.
        district = new District();
        district.setDescription("Gampaha11ww");

        district.setProvince(province);
        district = commonService.addDistrict(district);
        assertNotNull(" district saved to db " + district);

        // Instantiates a City object.
        city = new City();
        city.setDescription("Dematagoda11ww");

        city.setDistrict(district);
        city = commonService.addCity(city);
        assertNotNull(" district saved to db " + city);

        // Instantiates a Nationality object.
        nationality = new Nationality();
        nationality.setDescription("Sri Lankan1111ww");
        nationality.setModifiedTime(new Date());

        nationality = commonService.addNationality(nationality);
        assertNotNull(" nationality saved to db " + nationality);

        // Instantiates student object.
        student = new Student();
        student.setAdmissionNo("A123456");
        student.setNameWtInitials("I L");
        student.setLastName("Lolratne ");
        student.setAddress("Gampaha");
        student.setFullName("Lol Lolrathne");
        student.setModifiedTime(new Date());
        student.setLanguageId(language.getLanguageId());
        student.setReligionId(religion.getReligionId());
        student.setTravelId(methodOfTravel.getTravelId());
        student.setBloodGroupId(bloodGroup.getBloodGroupId());
        student.setHouseId(house.getHouseId());
        student.setCityId(city.getCityId());
        student.setNationalityId(nationality.getNationalityId());
        student.setGender('M');
        student.setIsOldBoy(false);
        student.setDateOfBirth(new Date());
        student.setAdmissionDate(new Date());
        student.setFirstSchoolDay(new Date());

        student = studentService.saveStudent(student);
        assertNotNull(" Student saved to db " + student);

        studentLeave = new StudentLeave();
        studentLeave.setStudentId(student.getStudentId());
        studentLeave.setReason("New Reason");
        studentLeave.setFromDate(new Date());
        studentLeave.setToDate(new Date());
        studentLeave.setModifiedTime(new Date());

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        model.addAttribute("studentLeave", studentLeave);
        request = new MockHttpServletRequest();
        session = new MockHttpSession();

        studentLeaveController = new StudentLeaveController();
        studentLeaveController.setStudentService(studentService);
    }

    /**
     * Test method for SaveOrUpdateStudentDiscipline of StudentDisciplineController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateStudentDiscipline() throws AkuraAppException {

        try {

            session.setAttribute("studentId", student.getStudentId());
            studentLeaveController.setStudentLeaveValidator(studentLeaveValidator);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view =
                    studentLeaveController.saveOrUpdateStudentLeave(studentLeave, mockBindingResult, request, model,
                            session);

            assertEquals("Student/studentLeave", view);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView =
                    studentLeaveController.saveOrUpdateStudentLeave(studentLeave, mockBindingResult, request, model,
                            session);
            assertEquals("redirect:studentLeave.htm", savedView);
            assertTrue(model.size() > 0);

            request.addParameter("studentLeaveId", "" + studentLeave.getStudentLeaveId());

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        // studentService.deleteStudentLeave(studentLeave);

        studentLeaveController.deleteStudentLeave(request, model);

        studentService.deleteStudent(student.getStudentId());
        commonService.deleteBloodGroup(bloodGroup);
        commonService.deleteNationality(nationality);
        commonService.deleteHouse(house.getHouseId());
        languageDao.delete(language);
        commonService.deleteMethodOfTravel(methodOfTravel.getTravelId());
        commonService.deleteCity(city);
        commonService.deleteDistrict(district);
        commonService.deleteProvince(province);
        commonService.deleteReligion(religion.getReligionId());
    }
}
