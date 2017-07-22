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

package com.virtusa.akura.reporting.controller;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.ClubSociety;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.ParticipantCategory;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.dto.SpecialEventsAttendance;
import com.virtusa.akura.api.dto.SpecialEventsParticipation;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentSpecialEventAttendanceTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.attendance.dao.SpecialEventsAttendanceDao;
import com.virtusa.akura.attendance.service.SpecialEventAttendanceService;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.validator.StudentSpecialEventAttendanceReportValidator;
import com.virtusa.akura.student.service.StudentService;

/**
 * @author Virtusa Corporation
 */
public class StudentSpecialEventAttendanceReportControllerTest extends BaseWebTest {
    
    /** Constant for 3.*/
    private static final int CLUB_WISE_PARTICIPATION_CATEGORY_ID = 3;

    /** Holds instance of StudentSpecialEventAttendanceReportController. */
    private StudentSpecialEventAttendanceReportController studEventAttendReportController;
    
    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;
    
    /** Represents an instance of response request. */
    private MockHttpServletResponse response;
    
    /** commonService attribute to hold CommonService instance. */
    @Autowired
    private CommonService commonService;
    
    /** specialEventAttendanceService attribute to hold SpecialEventAttendanceService instance. */
    @Autowired
    private SpecialEventAttendanceService specialEventAttendanceService;
    
    /** The student service. */
    @Autowired
    private StudentService studentService;
    
    /** The studentSpecialEventAttendanceReport validator. */
    @Autowired
    private StudentSpecialEventAttendanceReportValidator studentSpecialEventAttendanceReportValidator;
    
    /** Holds SpecialEventsAttendanceDao instance. */
    @Autowired
    private SpecialEventsAttendanceDao specialEventsAttendanceDao;
    
    /** Holds LanguageDao instance. */
    @Autowired
    private LanguageDao languageDao;
    
    /** Represents an instance of ModelMap. */
    private ModelMap modelMap;
    
    /** Represents an instance of StudentSpecialEventAttendanceTemplate. */
    private StudentSpecialEventAttendanceTemplate studEventAttendTemplate;
    
    /** Holds Province instance. */
    private Province province;
    
    /** Holds District instance. */
    private District district;
    
    /** Holds City instance. */
    private City city;
    
    /** Holds Language instance. */
    private Language language;
    
    /** Holds Religion instance. */
    private Religion religion;
    
    /** Holds MethodOfTravel instance. */
    private MethodOfTravel methodOfTravel;
    
    /** Holds BloodGroup instance. */
    private BloodGroup bloodGroup;
    
    /** Holds House instance. */
    private House house;
    
    /** Holds Nationality instance. */
    private Nationality nationality;
    
    /** Holds Nationality instance. */
    private ClubSociety clubSociety;
    
    /** Holds Student instance. */
    private Student student;
    
    /** Represents an instance of ParticipantCategory. */
    private ParticipantCategory participantCategory;
    
    /** Represents an instance of SpecialEvents. */
    private SpecialEvents specialEvents;
    
    /** Represents an instance of ParticipantCategory. */
    private SpecialEventsParticipation specialEventsParticipation;
    
    /** Holds SpecialEventsAttendance instance. */
    private SpecialEventsAttendance specialEventsAttendance;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {
    
        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        
        studEventAttendReportController = new StudentSpecialEventAttendanceReportController();
        studEventAttendReportController.setCommonService(commonService);
        studEventAttendReportController.setSpecialEventAttendanceService(specialEventAttendanceService);
        studEventAttendReportController.setStudentService(studentService);
        studEventAttendReportController
                .setStudentSpecialEventAttendanceReportValidator(studentSpecialEventAttendanceReportValidator);
        modelMap = new ModelMap();
        studEventAttendTemplate = new StudentSpecialEventAttendanceTemplate();
        
        // Instantiate a Province object.
        province = new Province();
        province.setDescription("province Test1 SSEASRC");
        
        commonService.addProvince(province);
        
        // Instantiates a District object.
        district = new District();
        district.setDescription("District Test1 SSEASRC");
        district.setProvince(province);
        
        commonService.addDistrict(district);
        
        // Instantiates a City object.
        city = new City();
        city.setDescription("City Test1 SSEASRC");
        city.setDistrict(district);
        city.setModifiedTime(new Date());
        
        commonService.addCity(city);
        
        // Instantiates a Language object.
        language = new Language();
        language.setLanguage("Sinhala SSEASRC");
        language.setModifiedTime(new Date());
        
        languageDao.save(language);
        
        religion = new Religion();
        religion.setDescription("Religion11ww SSEASRC");
        religion.setModifiedTime(new Date());
        
        commonService.createReligion(religion);
        
        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("Travel By Bus Test SSEASRC");
        
        commonService.createMethodOfTravel(methodOfTravel);
        
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("Blood Group Test SSEASRC");
        
        commonService.addBloodGroup(bloodGroup);
        
        // Instantiates a House object.
        house = new House();
        house.setName("House Test SSEASRC");
        
        commonService.createHouse(house);
        
        // Instantiates a Nationality object.
        nationality = new Nationality();
        nationality.setDescription("Nationality Test SSEASRC");
        
        commonService.addNationality(nationality);
        
        student = new Student();
        student.setAdmissionNo("Admission No Test SSEASRC");
        student.setAdmissionDate(new Date());
        student.setFirstSchoolDay(new Date());
        student.setFullName("Student Full Name Test");
        student.setNameWtInitials("Student Name With Initials Test");
        student.setLastName("Student Last Name Test");
        student.setDateOfBirth(new Date());
        student.setAddress("Student Address Test");
        student.setGender('M');
        student.setIsOldBoy(false);
        
        student.setTravelId(methodOfTravel.getTravelId());
        student.setLanguageId(language.getLanguageId());
        student.setReligionId(religion.getReligionId());
        student.setBloodGroupId(bloodGroup.getBloodGroupId());
        student.setHouseId(house.getHouseId());
        student.setCityId(city.getCityId());
        student.setNationalityId(nationality.getNationalityId());
        
        studentService.saveStudent(student);
        
        // Instantiates a ClubSociety object.
        clubSociety = new ClubSociety();
        clubSociety.setName("Club Society Test SSEASRC");
        clubSociety.setModifiedTime(new Date());
        
        commonService.addClubSociety(clubSociety);
        
        participantCategory = new ParticipantCategory();
        participantCategory.setParticipantCategoryId(CLUB_WISE_PARTICIPATION_CATEGORY_ID);
        participantCategory.setDescription("CLUB AND SOCIETY WISE SSEASRC");
        
        specialEvents = new SpecialEvents();
        specialEvents.setParticipantCategory(participantCategory);
        specialEvents.setName("Event Name Test SEAS");
        specialEvents.setDescription("event description test SSEASRC");
        specialEvents.setDate(new Date());
        
        commonService.addSpecialEvents(specialEvents);
        
        specialEventsParticipation = new SpecialEventsParticipation();
        specialEventsParticipation.setSpecialEvents(specialEvents);
        specialEventsParticipation.setClubSociety(clubSociety);
        
        commonService.addSpecialEventsParticipation(specialEventsParticipation);
        
        specialEventsAttendance = new SpecialEventsAttendance();
        specialEventsAttendance.setSpecialEventsParticipation(specialEventsParticipation);
        specialEventsAttendance.setStudentId(student.getStudentId());
        
        specialEventsAttendanceDao.save(specialEventsAttendance);
        
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        if (specialEventsAttendance.getSpecialEventsAttendanceId() > 0) {
            specialEventsAttendanceDao.delete(specialEventsAttendance);
        }
        if (student.getStudentId() > 0) {
            studentService.deleteStudent(student.getStudentId());
        }
        if (city.getCityId() > 0) {
            commonService.deleteCity(city);
        }
        if (district.getDistrictId() > 0) {
            commonService.deleteDistrict(district);
        }
        if (province.getProvinceId() > 0) {
            commonService.deleteProvince(province);
        }
        if (language.getLanguageId() > 0) {
            languageDao.delete(language);
        }
        if (religion.getReligionId() > 0) {
            commonService.deleteReligion(religion.getReligionId());
        }
        if (methodOfTravel.getTravelId() > 0) {
            commonService.deleteMethodOfTravel(methodOfTravel.getTravelId());
        }
        if (bloodGroup.getBloodGroupId() > 0) {
            commonService.deleteBloodGroup(bloodGroup);
        }
        if (house.getHouseId() > 0) {
            commonService.deleteHouse(house.getHouseId());
        }
        if (nationality.getNationalityId() > 0) {
            commonService.deleteNationality(nationality);
        }
        if (specialEventsParticipation.getSpecialEventsParticipationId() > 0) {
            commonService.deleteSpecialEventsParticipation(specialEventsParticipation);
        }
        if (clubSociety.getClubSocietyId() > 0) {
            commonService.deleteClubSociety(clubSociety.getClubSocietyId());
        }
        if (specialEvents.getSpecialEventsId() > 0) {
            commonService.deleteSpecialEvents(specialEvents.getSpecialEventsId());
        }
    }
    
    /**
     * Test method for ShowStudentSpecialEventAttendanceReportForm methpd in.
     * StudentSpecialEventAttendanceReportController class
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testShowStudentSpecialEventAttendanceReportForm() throws AkuraAppException {
    
        String page = studEventAttendReportController.showStudentSpecialEventAttendanceReportForm(modelMap);
        
        assertEquals("reporting/studentSpecialEventAttendanceReport", page);
    }
    
    /**
     * Test method for populateSpecialEventList method in StudentSpecialEventAttendanceReportController class.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testPopulateSpecialEventList() throws AkuraAppException {
    
        List<SpecialEvents> list = studEventAttendReportController.populateSpecialEventList();
        
        assertNotNull(list);
    }
    
    /**
     * Test method for loadSpecialEventAttendanceFilters method in.
     * StudentSpecialEventAttendanceReportController class.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testLoadSpecialEventAttendanceFilters() throws AkuraAppException {
    
        List<SpecialEvents> list = studEventAttendReportController.populateSpecialEventList();
        SpecialEvents specialEvent = new SpecialEvents();
        specialEvent.setSpecialEventsId(list.get(0).getSpecialEventsId());
        
        studEventAttendTemplate.setSpecialEvents(specialEvent);
        
        String page =
                studEventAttendReportController.loadSpecialEventAttendanceFilters(modelMap, studEventAttendTemplate);
        
        assertTrue(modelMap.containsAttribute("filterOptionsList"));
        assertTrue(modelMap.containsAttribute("studSpecEventAttendeTempl"));
        assertEquals("reporting/studentSpecialEventAttendanceReport", page);
    }
    
    /**
     * Test method for generateStudentSpecialEventAttendanceReport method in.
     * StudentSpecialEventAttendanceReportController class.
     * 
     * @throws AkuraException throws if exception occurs
     */
    public void testGenerateStudentSpecialEventAttendanceReport() throws AkuraException {
    
        studEventAttendTemplate.setSpecialEvents(specialEvents);
        studEventAttendTemplate.setSpecialEventsParticipation(specialEventsParticipation);
        ModelAndView modelAndView =
                studEventAttendReportController.generateStudentSpecialEventAttendanceReport(response,
                        studEventAttendTemplate, mockBindingResult, modelMap);
        assertNull(modelAndView);
    }
    
}
