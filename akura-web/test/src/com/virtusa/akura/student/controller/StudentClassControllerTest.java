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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.dao.StudentClassInfoDao;
import com.virtusa.akura.student.service.StudentService;

/**
 * @author Virtusa Corporation
 */

public class StudentClassControllerTest extends BaseWebTest {

    /** StudentService attribute for holding studentService. */
    @Autowired
    private StudentService studentService;

    /** CommonService attribute for holding commonService. */
    @Autowired
    private CommonService commonService;

    /** Represents an instance of StudentClassController. */
    private StudentClassController studentClassController;

    /** Represents an instance of MockHttpServletRequest. */
    private MockHttpServletRequest request;

    /** Represents an instance of ModelMap. */
    private ModelMap model;

    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;

    /** Represents an instance of the StudentClassInfo. */
    private StudentClassInfo studentClassInfo;

    /** Represents an instance of the StudentClassInfoDao. */
    @Autowired
    private StudentClassInfoDao studentClassInfoDao;

    /** Represents an instance of the Grade. */
    private Grade grade;

    /** Represents an instance of the SchoolClass. */
    private SchoolClass schoolClass;

    /** Represents an instance of the ClassGrade. */
    private ClassGrade classGrade;

    /** Represents an instance of the List. */
    private List<ClassGrade> classGradeList;

    /** Represents an instance of the Student. */
    private Student student;

    /** Represents an instance of the Language. */
    private Language language;

    /** Represents an instance of the LanguageDao. */
    @Autowired
    private LanguageDao languageDao;

    /** Represents an instance of the StudentClassInfoDao. */
    private Religion religion;

    /** Represents an instance of the MethodOFtravel. */
    private MethodOfTravel methodOfTravel;

    /** Represents an instance of the BloodGroup. */
    private BloodGroup bloodGroup;

    /** Represents an instance of the House. */
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

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        // model.addAttribute(attributeName, attributeValue)
        request = new MockHttpServletRequest();

        studentClassController = new StudentClassController();
        studentClassController.setStudentService(studentService);
        studentClassController.setCommonService(commonService);

        grade = new Grade();
        grade.setDescription("New Grade");
        grade.setModifiedTime(new Date());

        grade = commonService.saveGrade(grade);
        assertNotNull("Grade type should not be null " + grade);

        schoolClass = new SchoolClass();
        schoolClass.setDescription("SchoolClass");
        schoolClass.setModifiedTime(new Date());

        schoolClass = commonService.addClass(schoolClass);
        assertNotNull("SchoolClass type should not be null " + schoolClass);

        classGrade = new ClassGrade();
        classGrade.setDescription("ClassGrade");
        classGrade.setGrade(grade);
        classGrade.setSchoolClass(schoolClass);
        classGrade.setModifiedTime(new Date());

        classGrade = commonService.saveClassGrade(classGrade);
        assertNotNull("ClassGrade type should not be null " + classGrade);
        classGradeList = new ArrayList<ClassGrade>();
        classGradeList.add(classGrade);

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
        student.setAdmissionNo("A1123WW");
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

        studentClassInfo = new StudentClassInfo();
        // studentClassInfo.setStudentClassInfoId(100);
        studentClassInfo.setStudent(student);
        studentClassInfo.setClassGrade(classGrade);
        studentClassInfo.setYear(new Date());
        studentClassInfo.setCheckMonitor(false);
        studentClassInfo.setModifiedTime(new Date());

    }

    /**
     * Test method for SearchStudentByGradeYear of StudentClassController.
     *
     * @throws ParseException when an error has occurred during processing.
     * @throws AkuraException when an error has occurred during processing.
     */
    @Test
    public void testSearchStudentByGradeYear() throws ParseException, AkuraException {

        try {
            studentService.saveStudentClassInfoObj(studentClassInfo);

            request.addParameter("SelectedGrade", "" + grade.getGradeId());
            request.addParameter("SelectedYear", "2012");

            ModelAndView result = studentClassController.searchStudentByGradeYear(request);
            assertEquals("Admin/Student_class", result.getViewName());

        } catch (AkuraAppException e) {
            e.printStackTrace();
        } finally {
            studentClassInfoDao.delete(studentClassInfo);
        }
    }

    /*
     * @Test public void testSaveStudentList() throws AkuraAppException, ParseException { try { Grade newGrade
     * = new Grade(); newGrade.setDescription("New Selected Grade"); newGrade.setModifiedTime(new Date());
     * newGrade = schoolService.saveGrade(grade); assertNotNull("Grade type should not be null " + newGrade);
     * String []values = new String[1]; values[0] = ""+student.getStudentId();
     * request.addParameter("SelectedGrade", ""+grade.getGradeId()); request.addParameter("SelectedYear",
     * "2012"); request.addParameter("ToList", values); request.addParameter("NewSelectedYear", "2013");
     * request.addParameter("NewSelectedGrade", ""+newGrade.getGradeId()); ModelAndView result =
     * studentClassController.saveStudentList(request); assertEquals("Admin/Student_class",
     * result.getViewName()); //studentService.findS } catch (AkuraAppException e) { e.printStackTrace(); } }
     */
    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        commonService.deleteClassGradeList(classGradeList);
        commonService.deleteGrade(grade);
        commonService.deleteClass(schoolClass);

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
