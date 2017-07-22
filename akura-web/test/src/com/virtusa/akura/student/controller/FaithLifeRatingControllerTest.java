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
import com.virtusa.akura.api.dto.FaithLifeCategory;
import com.virtusa.akura.api.dto.FaithLifeComment;
import com.virtusa.akura.api.dto.FaithLifeGrading;
import com.virtusa.akura.api.dto.FaithLifeRating;
import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.FaithLifeCategoryDao;
import com.virtusa.akura.common.dao.FaithLifeCommentDao;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentProgressBarService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.student.validator.FaithLifeRatingValidator;

/**
 * This test class, tests all the presentation related functionality for the FaithLifeRating domain object.
 *
 * @author Virtusa Corporation
 */
public class FaithLifeRatingControllerTest extends BaseWebTest {

    /** Represents an instance of MockHttpSession. */
    private MockHttpSession session;

    /** Represents an instance of MockHttpServletRequest. */
    private MockHttpServletRequest request;

    /** Represents an instance of ModelMap. */
    private ModelMap model;

    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;

    /** StudentService attribute for holding studentService. */
    @Autowired
    private StudentService studentService;

    /** Holds CommonService instance. */
    @Autowired
    private CommonService commonService;

    /** Holds StudentProgressBarService instance. */
    @Autowired
    private StudentProgressBarService studentProgressBarService;

    /** Holds FaithLifeRatingController instance. */
    private FaithLifeRatingController faithLifeRatingController;

    /** Holds Student instance. */
    private Student newStudent;

    /** Holds Language instance. */
    private Language language;

    /** Holds LanguageDao instance. */
    @Autowired
    private LanguageDao languageDao;

    /** Holds Religion instance. */
    private Religion religion;

    /** Holds MethodOfTravel instance. */
    private MethodOfTravel newMethodOfTravel;

    /** Holds BloodGroup instance. */
    private BloodGroup savedBloodGroup;

    /** Holds House instance. */
    private House newHouse;

    /** Holds province instance. */
    private Province newProvince;

    /** Holds city instance. */
    private City newCity;

    /** Holds district instance. */
    private District newDistrict;

    /** Represents an instance of Nationality. */
    private Nationality newNationality;

    /** Represents an instance of FaithLifeGrading. */
    private FaithLifeGrading newGrading;

    /** Represents an instance of FaithLifeRating. */
    private FaithLifeRating faithLifeRating;

    /** Represents an instance of FaithLifeCategory. */
    private FaithLifeCategory faithLifeCategory;

    /** Represents an instance of FaithLifeComment. */
    private FaithLifeComment faithLifeComment;

    /** Holds FaithLifeCategoryDao instance. */
    @Autowired
    private FaithLifeCategoryDao faithLifeCategoryDao;

    /** Holds FaithLifeCommentDao instance. */
    @Autowired
    private FaithLifeCommentDao faithLifeCommentDao;

    /** FaithLifeRatingValidator attribute for holding faithLifeRatingValidator. */
    @Autowired
    private FaithLifeRatingValidator faithLifeRatingValidator;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();
        session = new MockHttpSession();

        // Instantiate a Province object.
        Province province = new Province();
        province.setDescription("provinceFL12370");

        newProvince = commonService.addProvince(province);
        assertNotNull(" province saved to db " + newProvince);

        // Instantiates a District object.
        District district = new District();
        district.setDescription("districtFL12370");
        district.setProvince(province);

        newDistrict = commonService.addDistrict(district);
        assertNotNull(" district saved to db " + newDistrict);

        // Instantiates a City object.
        City city = new City();
        city.setDescription("cithFL12370");
        city.setDistrict(district);
        city.setModifiedTime(new Date());

        newCity = commonService.addCity(city);
        assertNotNull(" city saved to db " + newCity);

        // Instantiates a Language object.
        language = new Language();
        language.setLanguage("LanguageFL12370");
        language.setModifiedTime(new Date());

        language = languageDao.save(language);
        assertNotNull(" language saved to db " + language);
        // Instantiates a Religion object.
        religion = new Religion();
        religion.setDescription("ReligionFL12370");
        religion.setModifiedTime(new Date());

        religion = commonService.createReligion(religion);
        assertNotNull(" religion saved to db " + religion);

        // Instantiates a BloodGroup object.
        BloodGroup bloodGroup = new BloodGroup();
        bloodGroup.setDescription("testFL12370");
        savedBloodGroup = commonService.addBloodGroup(bloodGroup);
        assertNotNull(" bloodGroup saved to db " + savedBloodGroup);

        // Instantiates a Nationality object.
        Nationality nationality = new Nationality();
        nationality.setDescription("nationalityFL12370");
        newNationality = commonService.addNationality(nationality);
        assertNotNull(" nationality saved to db " + nationality);

        // Instantiates a House object.
        House house = new House();
        house.setName("houseFL12370");
        newHouse = commonService.createHouse(house);
        assertNotNull(" house saved to db " + newHouse);

        // Instantiates a Method of travel object.
        MethodOfTravel methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("testBusFL12370");
        newMethodOfTravel = commonService.createMethodOfTravel(methodOfTravel);
        assertNotNull(" methodOftravel saved to db " + newMethodOfTravel);

        Student student = new Student();
        student.setAdmissionNo("K0045112370");
        student.setNameWtInitials("K wa");
        student.setLastName("LstasFL237");
        student.setAddress("TestFLAdd");
        student.setFullName("Lo FLdsa");
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

        newStudent = studentService.saveStudent(student);
        assertNotNull("student saved to db " + newStudent);

        // Instantiates a House object.
        FaithLifeGrading grade = new FaithLifeGrading();
        grade.setDescription("A");
        grade.setValue(1);
        newGrading = commonService.saveFaithLifeGrading(grade);
        assertNotNull(" grading saved to db " + newGrading);

        // Instantiates a FaithLifeCategory object.
        faithLifeCategory = new FaithLifeCategory();
        faithLifeCategory.setDescription("trtade0");
        FaithLifeCategory newFC = faithLifeCategoryDao.save(faithLifeCategory);
        assertNotNull(" faithLifeCategory saved to db " + newFC);

        // Instantiates a FaithLifeComment object.
        faithLifeComment = new FaithLifeComment();
        faithLifeComment.setDescription("commenFL1260");
        faithLifeComment.setFaithLifeCategory(faithLifeCategory);
        FaithLifeComment newFaithLifeComment = faithLifeCommentDao.save(faithLifeComment);
        assertNotNull(" faithLifeComment saved to db " + newFaithLifeComment);

        // Instantiates a FaithLifeRating object.
        faithLifeRating = new FaithLifeRating();
        faithLifeRating.setFaithLifeComment(faithLifeComment);
        faithLifeRating.setFaithLifeGrading(newGrading);
        faithLifeRating.setStudent(student);
        faithLifeRating.setYear(new Date());

        faithLifeRatingController = new FaithLifeRatingController();
        faithLifeRatingController.setCommonService(commonService);
        faithLifeRatingController.setStudentService(studentService);
        faithLifeRatingController.setStudentProgressBarService(studentProgressBarService);

    }

    /**
     * Test method for ShowFaithLifeRatingDetail of FaithLifeRatingController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowFaithLifeRating() throws AkuraAppException {

        faithLifeRating = studentService.addFaithLifeRating(faithLifeRating);
        assertNotNull("faithLifeRating should not be null " + faithLifeRating);

        session.setAttribute("studentId", newStudent.getStudentId());
        String result = faithLifeRatingController.showFaithLifeRating(model, request, session);
        assertNotNull("ModelAndView should not be null", result);

    }

    /**
     * Test method for {@link com.virtusa.sms.common.controller.CreateSportCategoryController#
     * saveOrUpdateSportCategory ( org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.SportCategory, o
     * rg.springframework.validation.BindingResult)}.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateFaithLifeRating() throws AkuraAppException {

        try {
            session.setAttribute("studentId", newStudent.getStudentId());
            request.addParameter("selectedYear", "2012");
            request.addParameter("faithLifeCommentId", faithLifeComment.getFaithLifeCommentId() + "");

            faithLifeRatingController.setFaithLifeRatingValidator(faithLifeRatingValidator);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view =
                    faithLifeRatingController.saveOrUpdateFaithLifeRating(faithLifeRating, mockBindingResult, request,
                            session, model);

            assertEquals("Student/faithLife", view);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView =
                    faithLifeRatingController.saveOrUpdateFaithLifeRating(faithLifeRating, mockBindingResult, request,
                            session, model);

            assertEquals("Student/faithLife", savedView);
            assertTrue(model.size() > 0);

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        try {

            String result = faithLifeRatingController.deleteFaithLifeRating(faithLifeRating, request, session, model);

            faithLifeCommentDao.delete(faithLifeComment);
            faithLifeCategoryDao.delete(faithLifeCategory);

            commonService.deleteGrading(newGrading.getGradingId());
            studentService.deleteStudent(newStudent.getStudentId());

            commonService.deleteBloodGroup(savedBloodGroup);
            commonService.deleteNationality(newNationality);
            commonService.deleteHouse(newHouse.getHouseId());
            languageDao.delete(language);
            commonService.deleteMethodOfTravel(newMethodOfTravel.getTravelId());
            commonService.deleteCity(newCity);
            commonService.deleteDistrict(newDistrict);
            commonService.deleteProvince(newProvince);
            commonService.deleteReligion(religion.getReligionId());

            assertNotNull("FaithLifeRating should be null " + result);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }

    }

}
