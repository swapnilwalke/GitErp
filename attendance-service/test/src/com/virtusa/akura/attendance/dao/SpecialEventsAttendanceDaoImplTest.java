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

package com.virtusa.akura.attendance.dao;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.ParticipantCategory;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.dto.SpecialEventsAttendance;
import com.virtusa.akura.api.dto.SpecialEventsParticipation;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.attendance.BaseAttendanceTest;
import com.virtusa.akura.common.dao.BloodGroupDao;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.ClassGradeDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.GradeDao;
import com.virtusa.akura.common.dao.HouseDao;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.dao.MethodOfTravelDao;
import com.virtusa.akura.common.dao.NationalityDao;
import com.virtusa.akura.common.dao.ParticipantCategoryDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.ReligionDao;
import com.virtusa.akura.common.dao.SchoolClassDao;
import com.virtusa.akura.common.dao.SpecialEventsDao;
import com.virtusa.akura.common.dao.SpecialEventsParticipationDao;
import com.virtusa.akura.student.dao.StudentDao;

/**
 * This test class tests all the functionalities of the SpecialEventsAttendanceDaoImpl class.
 * 
 * @author Virtusa Corporation
 */

public class SpecialEventsAttendanceDaoImplTest extends BaseAttendanceTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SpecialEventsAttendanceDao specialEventsAttendanceDao;
    
    /** Holds specialEventsAttendance instance. */
    private SpecialEventsAttendance specialEventsAttendance;
    
    /**
     * Represents an instance of SchoolClassDao.
     */
    @Autowired
    private SchoolClassDao schoolClassDao;
    
    /**
     * Represents an instance of GradeDao.
     */
    @Autowired
    private GradeDao gradeDao;
    
    /**
     * Represents an instance of SchoolClass.
     */
    private SchoolClass classInstace;
    
    /**
     * Represents an instance of Grade.
     */
    private Grade grade;
    
    /**
     * Represents an instance of ClassGrade.
     */
    private ClassGrade classGrade;
    
    /**
     * Represents an instance of ClassGradeDao.
     */
    @Autowired
    private ClassGradeDao classGradeDao;
    
    /**
     * Represents an instance of Province.
     */
    private Province province;
    
    /**
     * Represents an instance of District.
     */
    private District district;
    
    /** Holds Religion instance. */
    private Religion religion;
    
    /** Holds BloodGroup instance. */
    private BloodGroup bloodGroup;
    
    /** Holds Nationality instance. */
    private Nationality nationality;
    
    /** Holds Nationality instance. */
    private House house;
    
    /**
     * Represents an instance of City.
     */
    private City city;
    
    /** Injects an instance of CityDao. */
    @Autowired
    private CityDao cityDao;
    
    /** Represents an instance of the BloodGroupDao. */
    @Autowired
    private BloodGroupDao bloodGroupDao;
    
    /** Injects an instance of LanguageDao. */
    @Autowired
    private LanguageDao languageDao;
    
    /** Injects an instance of StudentDao. */
    @Autowired
    private ProvinceDao provinceDao;
    
    /** Injects an instance of StudentDao. */
    @Autowired
    private DistrictDao districtDao;
    
    /** Injects an instance of StudentDao. */
    @Autowired
    private NationalityDao nationalityDao;
    
    /** Injects an instance of StudentDao. */
    @Autowired
    private HouseDao houseDao;
    
    /** Injects an instance of StudentDao. */
    @Autowired
    private ReligionDao religionDao;
    
    /**
     * Holds methodOfTravelDAO instance of {@link MethodOfTravelDao}.
     */
    @Autowired
    private MethodOfTravelDao methodOfTravelDAO;
    
    /**
     * Holds methodOfTravel instance of {@link MethodOfTravel}.
     */
    private MethodOfTravel methodOfTravel;
    
    /**
     * Holds language instance of {@link Language}.
     */
    private Language language;
    
    /** Represents an instance of Student. */
    private Student student;
    
    /** Represents an instance of SpecialEventsParticipation. */
    private SpecialEventsParticipation specialEventsParticipation;
    
    /** Injects an instance of StudentDao. */
    @Autowired
    private SpecialEventsParticipationDao specialEventsParticipationDao;
    
    /** Injects an instance of StudentDao. */
    @Autowired
    private StudentDao studentDao;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private ParticipantCategoryDao participantCategoryDao;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SpecialEventsDao specialEventsDao;
    
    /**
     * Represents an instance of participantCategory.
     */
    private ParticipantCategory participantCategory;
    
    /**
     * Represents an instance of SpecialEvents.
     */
    private SpecialEvents specialEvents;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public void setUp() throws Exception {
    
        // instantiates SchoolClass object.
        classInstace = new SchoolClass();
        classInstace.setDescription("NewClass test");
        SchoolClass newClassInstace = schoolClassDao.save(classInstace);
        assertNotNull(newClassInstace);
        
        grade = new Grade();
        grade.setDescription("Grade-15 test1");
        Grade newGrade = gradeDao.save(grade);
        assertNotNull(newGrade);
        
        classGrade = new ClassGrade();
        classGrade.setSchoolClass(newClassInstace);
        classGrade.setGrade(newGrade);
        classGrade.setDescription(grade.getDescription() + classInstace.getDescription());
        classGradeDao.save(classGrade);
        assertNotNull(classGrade);
        
        // Instantiates Province object.
        province = new Province();
        province.setDescription("Province test6");
        provinceDao.save(province);
        assertNotNull(province);
        
        // Instantiates District object.
        district = new District();
        district.setDescription("District Description test6");
        district.setProvince(province);
        districtDao.save(district);
        assertNotNull(district);
        
        // Instantiates City object.
        city = new City();
        city.setDescription("City Description test6");
        city.setDistrict(district);
        cityDao.save(city);
        assertNotNull(city);
        
        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("Train test6");
        methodOfTravelDAO.save(methodOfTravel);
        assertNotNull(methodOfTravel);
        
        // Instantiates a Religion object.
        religion = new Religion();
        religion.setDescription("Religion test6");
        religionDao.save(religion);
        assertNotNull(religion);
        
        // Instantiates a BloodGroup object.
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("ABBB test6");
        bloodGroupDao.save(bloodGroup);
        assertNotNull(bloodGroup);
        
        // Instantiates a Nationality object.
        nationality = new Nationality();
        nationality.setDescription("Indian test6");
        nationalityDao.save(nationality);
        assertNotNull(nationality);
        
        // Instantiates a House object.
        house = new House();
        house.setName("House Test 123 test6");
        houseDao.save(house);
        assertNotNull(house);
        
        language = new Language();
        language.setLanguage("myLanguage test6");
        languageDao.save(language);
        assertNotNull(language);
        
        student = new Student();
        student.setAdmissionNo("my Admission test6");
        student.setAdmissionDate(new Date());
        student.setNameWtInitials("I L");
        student.setLastName("Lolratne test2");
        student.setAddress("Gampaha");
        student.setFullName("Lol Lolrathne");
        student.setFirstSchoolDay(new Date());
        student.setDateOfBirth(new Date());
        student.setCityId(city.getCityId());
        student.setHouseId(house.getHouseId());
        student.setBloodGroupId(bloodGroup.getBloodGroupId());
        student.setNationalityId(nationality.getNationalityId());
        student.setReligionId(religion.getReligionId());
        student.setLanguageId(language.getLanguageId());
        student.setTravelId(methodOfTravel.getTravelId());
        studentDao.save(student);
        assertNotNull(student);
        
        participantCategory = new ParticipantCategory();
        participantCategory.setDescription("descriptionRef");
        participantCategory.setModifiedTime(new Date());
        ParticipantCategory newParticipantCategory = participantCategoryDao.save(participantCategory);
        assertNotNull(newParticipantCategory);
        
        specialEvents = new SpecialEvents();
        specialEvents.setDate(new Date());
        specialEvents.setDescription("descriptionRef");
        specialEvents.setModifiedTime(new Date());
        specialEvents.setName("nameRef");
        specialEvents.setParticipantCategory(newParticipantCategory);
        SpecialEvents newSpecialEvents = specialEventsDao.save(specialEvents);
        assertNotNull(newSpecialEvents);
        
        specialEventsParticipation = new SpecialEventsParticipation();
        specialEventsParticipation.setClassGrade(classGrade);
        specialEventsParticipation.setModifiedTime(new Date());
        specialEventsParticipation.setSpecialEvents(newSpecialEvents);
        specialEventsParticipationDao.save(specialEventsParticipation);
        assertNotNull(specialEventsParticipation);
        
        specialEventsAttendance = new SpecialEventsAttendance();
        specialEventsAttendance.setSpecialEventsParticipation(specialEventsParticipation);
        specialEventsAttendance.setStudentId(student.getStudentId());
    }
    
    /**
     * Test method for save SpecialEventsAttendance domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSave() throws AkuraAppException {
    
        try {
            SpecialEventsAttendance newSpecialEventsAttendance =
                    specialEventsAttendanceDao.save(specialEventsAttendance);
            assertNotNull("SpecialEventsAttendance Type object should not null ", newSpecialEventsAttendance);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for find by ID SpecialEventsAttendance domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindById() throws AkuraAppException {
    
        try {
            SpecialEventsAttendance newSpecialEventsAttendance =
                    specialEventsAttendanceDao.save(specialEventsAttendance);
            assertNotNull("SpecialEventsAttendance Type object should not null ", newSpecialEventsAttendance);
            
            SpecialEventsAttendance findSpecialEventsAttendance =
                    (SpecialEventsAttendance) specialEventsAttendanceDao.findById(SpecialEventsAttendance.class,
                            newSpecialEventsAttendance.getSpecialEventsAttendanceId());
            assertNotNull("SpecialEventsAttendance Type object should not null ", findSpecialEventsAttendance);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for find all SpecialEventsAttendance domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindAll() throws AkuraAppException {
    
        try {
            SpecialEventsAttendance newSpecialEventsAttendance =
                    specialEventsAttendanceDao.save(specialEventsAttendance);
            assertNotNull("SpecialEventsAttendance Type object should not null ", newSpecialEventsAttendance);
            
            List<SpecialEventsAttendance> specialEventsAttendanceList =
                    specialEventsAttendanceDao.findAll(SpecialEventsAttendance.class);
            assertTrue(specialEventsAttendanceList.size() > 0);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for getSpecialEventAttandanceList SpecialEventsAttendance domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetSpecialEventAttandanceList() throws AkuraAppException {
    
        try {
            SpecialEventsAttendance newSpecialEventsAttendance =
                    specialEventsAttendanceDao.save(specialEventsAttendance);
            assertNotNull("SpecialEventsAttendance Type object should not null ", newSpecialEventsAttendance);
            List<SpecialEventsAttendance> specialEventsAttendanceList =
                    specialEventsAttendanceDao.getSpecialEventAttandanceList(specialEventsParticipation
                            .getSpecialEventsParticipationId());
            assertTrue(specialEventsAttendanceList.size() > 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for getSpecialEventAttandanceObjectByStudentId SpecialEventsAttendance domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetSpecialEventsAttendanceObjectByStudentId() throws AkuraAppException {
    
        try {
            SpecialEventsAttendance newSpecialEventsAttendance =
                    specialEventsAttendanceDao.save(specialEventsAttendance);
            assertNotNull("SpecialEventsAttendance Type object should not null ", newSpecialEventsAttendance);
            List<SpecialEventsAttendance> specialEventsAttendanceList =
                    specialEventsAttendanceDao.getSpecialEventsAttendanceObjectByStudentId(student.getStudentId(),
                            specialEventsParticipation.getSpecialEventsParticipationId());
            assertTrue(specialEventsAttendanceList.size() > 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @After
    public final void teardown() throws AkuraAppException {
    
        try {
            specialEventsAttendanceDao.delete(specialEventsAttendance);
            specialEventsParticipationDao.delete(specialEventsParticipation);
            specialEventsDao.delete(specialEvents);
            participantCategoryDao.delete(participantCategory);
            classGradeDao.delete(classGrade);
            gradeDao.delete(grade);
            schoolClassDao.delete(classInstace);
            studentDao.delete(student);
            cityDao.delete(city);
            districtDao.delete(district);
            provinceDao.delete(province);
            nationalityDao.delete(nationality);
            languageDao.delete(language);
            religionDao.delete(religion);
            houseDao.delete(house);
            methodOfTravelDAO.delete(methodOfTravel);
            bloodGroupDao.delete(bloodGroup);
            
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for findStudentsIdsOfSpecialEventAttendance mwthod in SpecialEventAttendanceDaoImpl.
     * 
     * @throws AkuraAppException if exception occurs
     */
    @Test
    public void testFindStudentsIdsOfSpecialEventAttendance() throws AkuraAppException {
    
        List<Integer> idList =
                specialEventsAttendanceDao.findStudentsIdsOfSpecialEventAttendance(specialEventsParticipation
                        .getSpecialEventsParticipationId());
        
        assertNotNull(idList);
        
    }
    
}
