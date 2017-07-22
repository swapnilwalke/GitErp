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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.servlet.ModelAndView;

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
import com.virtusa.akura.api.dto.StudentDetails;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.student.service.StudentService;

/**
 * This test class, tests all the presentation related functionality for the generation of the student
 * progress report.
 *
 * @author Virtusa Corporation
 */
public class MessageBoardControllerTest extends BaseWebTest {

    /**
     * Represents an instance of StudentService.
     */
    @Autowired
    private StudentService studentService;

    /**
     * Represents an instance of UserService.
     */
    @Autowired
    private UserService userService;

    /**
     * Represents an instance of CommonService.
     */
    @Autowired
    private CommonService commonService;

    /**
     * Represents an instance of Religion.
     */
    private Religion newReligion;

    /**
     * Represents an instance of Province.
     */
    private Province newProvince;

    /**
     * Represents an instance of City.
     */
    private City newCity;

    /**
     * Represents an instance of District.
     */
    private District newDistrict;

    /**
     * Represents an instance of BloodGroup.
     */
    private BloodGroup savedBloodGroup;

    /**
     * Represents an instance of Nationality.
     */
    private Nationality newNationality;

    /**
     * Represents an instance of House.
     */
    private House newHouse;

    /**
     * Represents an instance of Language.
     */
    private Language newLanguage;

    /**
     * Represents an instance of MethodOfTravel.
     */
    private MethodOfTravel newMethodOfTravel;

    /**
     * Represents an instance of UserLogin.
     */
    private UserLogin user;

    /**
     * Represents an instance of Student.
     */
    private Student newStudent;

    /**
     * Represents an instance of MockHttpServletRequest.
     */
    private MockHttpServletRequest request;

    /**
     * Represents an instance of MessageBoardController.
     */
    private MessageBoardController controller;

    /**
     * Represents an instance of MockHttpSession.
     */
    private MockHttpSession session;

    /**
     * Represents an instance of LanguageDao.
     */
    @Autowired
    private LanguageDao languageDao;

    /**
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        controller = new MessageBoardController();
        controller.setStudentService(studentService);
        controller.setCommonService(commonService);
        controller.setUserService(userService);

        // Instantiate a Province object.
        Province province = new Province();
        province.setDescription("testWestern");

        newProvince = commonService.addProvince(province);
        assertNotNull(" province saved to db " + newProvince);

        // Instantiates a District object.
        District district = new District();
        district.setDescription("testGampaha");
        district.setProvince(province);

        newDistrict = commonService.addDistrict(district);
        assertNotNull(" district saved to db " + newDistrict);

        // Instantiates a City object.
        City city = new City();
        city.setDescription("testDematagoda");
        city.setDistrict(district);
        city.setModifiedTime(new Date());

        newCity = commonService.addCity(city);
        assertNotNull(" city saved to db " + newCity);

        // Instantiates a Religion object.
        Religion religion = new Religion();
        religion.setDescription("testReligion");

        newReligion = commonService.createReligion(religion);
        assertNotNull(" religion saved to db " + newReligion);

        // Instantiates a BloodGroup object.
        BloodGroup bloodGroup = new BloodGroup();
        bloodGroup.setDescription("testA");
        savedBloodGroup = commonService.addBloodGroup(bloodGroup);
        assertNotNull(" bloodGroup saved to db " + savedBloodGroup);

        // Instantiates a Nationality object.
        Nationality nationality = new Nationality();
        nationality.setDescription("testSri Lankanan");
        newNationality = commonService.addNationality(nationality);
        assertNotNull(" nationality saved to db " + nationality);

        // Instantiates a House object.
        House house = new House();
        house.setName("testHouse");
        newHouse = commonService.createHouse(house);
        assertNotNull(" house saved to db " + newHouse);

        // Instantiates a Language object.
        Language language = new Language();
        language.setLanguage("testSinhala1a");
        newLanguage = languageDao.save(language);
        assertNotNull(" language saved to db " + newLanguage);

        // Instantiates a Method of travel object.
        MethodOfTravel methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("testBus");
        newMethodOfTravel = commonService.createMethodOfTravel(methodOfTravel);
        assertNotNull(" methodOftravel saved to db " + newMethodOfTravel);

        Student student = new Student();
        student.setAdmissionNo("A112a33");
        student.setNameWtInitials("I Laa3");
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
        student.setEmail("afernando.virtusa.com");
        student.setIsOldBoy(false);
        student.setDateOfBirth(new Date());
        student.setAdmissionDate(new Date());
        student.setFirstSchoolDay(new Date());

        newStudent = studentService.saveStudent(student);
        request = new MockHttpServletRequest();
        user = new UserLogin();
        user.setUserRoleId(1);
        user.setFirstName("testAdmin");
        user.setLastName("lastName");
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("afernando.virtusa.com");
        user.setStatus(true);
        userService.createUser(user, Integer.parseInt(user.getUserIdentificationNo()));
        assertNotNull(user);
        session = new MockHttpSession();
        session.setAttribute("user", user);

    }

    /**
     * Test method for
     * {@link com.virtusa.sms.student.controller.MessageBoardController# studentProgressReportPDF(org.springframework.web.servlet.ModelAndView, javax.servlet.http.HttpServletRequest, org.springframework.ui.ModelMap)}
     * .
     *
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Test
    public void testStudentProgressReportPDF() throws AkuraAppException {

        try {
            request.addParameter("comments", "Good");
            request.getSession().setAttribute("studentId", newStudent.getStudentId());
            ModelAndView mav = new ModelAndView();
            List<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
            UserInfo userInfo = new StudentDetails("test", "test", "test", 1, "test", true, true, grantedAuthority);
            request.getSession().setAttribute("user", userInfo);
            ModelAndView returnMav = controller.studentProgressReportPDF(mav, request);
            ModelAndViewAssert.assertViewName(returnMav, "emptyReport");
        } catch (AkuraAppException e) {
            throw new AkuraAppException();
        }
    }

    /**
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        studentService.deleteStudent(newStudent.getStudentId());
        commonService.deleteBloodGroup(savedBloodGroup);
        commonService.deleteNationality(newNationality);
        commonService.deleteCity(newCity);
        commonService.deleteDistrict(newDistrict);
        commonService.deleteProvince(newProvince);
        commonService.deleteMethodOfTravel(newMethodOfTravel.getTravelId());
        commonService.deleteHouse(newHouse.getHouseId());
        commonService.deleteReligion(newReligion.getReligionId());
        languageDao.delete(newLanguage);
        userService.deleteUser(user);
    }
}
