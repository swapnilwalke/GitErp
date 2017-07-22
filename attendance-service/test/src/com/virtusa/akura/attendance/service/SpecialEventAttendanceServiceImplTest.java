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

package com.virtusa.akura.attendance.service;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.attendance.BaseAttendanceTest;
import com.virtusa.akura.attendance.dao.SpecialEventsAttendanceDao;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;

/**
 * This test class, tests all the persistence related functionality for the DailyAttendanceServiceImpl domain
 * object.
 * 
 * @author Virtusa Corporation
 */
public class SpecialEventAttendanceServiceImplTest extends BaseAttendanceTest {
    
    /** Holds CommonService instance. */
    @Autowired
    private CommonService commonService;
    
    /** Holds StudentService instance. */
    @Autowired
    private StudentService studentService;
    
    /** Holds LanguageDao instance. */
    @Autowired
    private LanguageDao languageDao;
    
    /** Holds SpecialEventsAttendanceDao instance. */
    @Autowired
    private SpecialEventsAttendanceDao specialEventsAttendanceDao;
    
    /** Holds SpecialEventAttendanceServiceImpl instance. */
    @Autowired
    private SpecialEventAttendanceServiceImpl specialEventAttendanceServiceImpl;
    
    /** Holds Student instance. */
    private Student student;
    
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
    
    /** Holds Province instance. */
    private Province province;
    
    /** Holds District instance. */
    private District district;
    
    /** Holds City instance. */
    private City city;
    
    /** Holds Nationality instance. */
    private Nationality nationality;
    
    /** Represents an instance of SpecialEvents. */
    private SpecialEvents specialEvents;
    
    /** Represents an instance of ParticipantCategory. */
    private ParticipantCategory participantCategory;
    
    /** Represents an instance of ParticipantCategory. */
    private SpecialEventsParticipation specialEventsParticipation;
    
    /** Holds Nationality instance. */
    private ClubSociety clubSociety;
    
    /** Holds SpecialEventsAttendance instance. */
    private SpecialEventsAttendance specialEventsAttendance;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {
    
        // Instantiate a Province object.
        province = new Province();
        province.setDescription("province Test1 SEAS");
        
        commonService.addProvince(province);
        
        // Instantiates a District object.
        district = new District();
        district.setDescription("District Test1 SEAS");
        district.setProvince(province);
        
        commonService.addDistrict(district);
        
        // Instantiates a City object.
        city = new City();
        city.setDescription("City Test1 SEAS");
        city.setDistrict(district);
        city.setModifiedTime(new Date());
        
        commonService.addCity(city);
        
        // Instantiates a Language object.
        language = new Language();
        language.setLanguage("Sinhala SEAS");
        language.setModifiedTime(new Date());
        
        languageDao.save(language);
        
        religion = new Religion();
        religion.setDescription("Religion11ww SEAS");
        religion.setModifiedTime(new Date());
        
        commonService.createReligion(religion);
        
        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("Travel By Bus Test SEAS");
        
        commonService.createMethodOfTravel(methodOfTravel);
        
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("Blood Group Test SEAS");
        
        commonService.addBloodGroup(bloodGroup);
        
        // Instantiates a House object.
        house = new House();
        house.setName("House Test SEAS");
        
        commonService.createHouse(house);
        
        // Instantiates a Nationality object.
        nationality = new Nationality();
        nationality.setDescription("Nationality Test SEAS");
        
        commonService.addNationality(nationality);
        
        student = new Student();
        student.setAdmissionNo("Admission No Test");
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
        clubSociety.setName("Club Society Test SEAS");
        clubSociety.setModifiedTime(new Date());
        
        commonService.addClubSociety(clubSociety);
        
        participantCategory = new ParticipantCategory();
        participantCategory.setParticipantCategoryId(3);
        participantCategory.setDescription("CLUB AND SOCIETY WISE");
        
        specialEvents = new SpecialEvents();
        specialEvents.setParticipantCategory(participantCategory);
        specialEvents.setName("Event Name Test SEAS");
        specialEvents.setDescription("event description test SEAS");
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
     * Test method getStudentsIDOfSpecialEventAttendance in SpecialEventAttendanceServiceImpl.
     * 
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testGetStudentsIDOfSpecialEventAttendance() throws AkuraAppException {
    
        List<Integer> list =
                specialEventAttendanceServiceImpl.getStudentsIDOfSpecialEventAttendance(specialEventsParticipation
                        .getSpecialEventsParticipationId());
        assertTrue(list.size() > 0);
    }
    
}
