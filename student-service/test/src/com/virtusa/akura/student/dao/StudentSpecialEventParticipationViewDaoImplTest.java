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

package com.virtusa.akura.student.dao;

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
import com.virtusa.akura.api.dto.SpecialEventsParticipation;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentClubSociety;
import com.virtusa.akura.api.dto.StudentSpecialEventParticipationView;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.ClassGradeDao;
import com.virtusa.akura.common.dao.GradeDao;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.dao.ParticipantCategoryDao;
import com.virtusa.akura.common.dao.SchoolClassDao;
import com.virtusa.akura.common.dao.SpecialEventsDao;
import com.virtusa.akura.common.dao.SpecialEventsParticipationDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.BaseStudentTest;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;

/**
 * Student Service Implementation.
 */
public class StudentSpecialEventParticipationViewDaoImplTest extends BaseStudentTest {
    
    /** attribute for holding string. */
    private static final String START_YEAR = "-01-01";
    
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
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SpecialEventsParticipationDao specialEventsParticipationDao;
    
    /** studentSpecialEventParticipationViewDao attribute for holding StudentSpecialEventParticipationViewDao. */
    @Autowired
    private StudentSpecialEventParticipationViewDao studentSpecialEventParticipationViewDao;
    
    /** Holds CommonService instance. */
    @Autowired
    private CommonService commonService;
    
    /** Holds LanguageDao instance. */
    @Autowired
    private LanguageDao languageDao;
    
    /** Holds StudentService instance. */
    @Autowired
    private StudentService studentService;
    
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
     * Represents an instance of ClassGradeDao.
     */
    @Autowired
    private ClassGradeDao classGradeDao;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private StudentClassInfoDao studentClassInfoDao;
    
    /**
     * Represents an instance of participantCategory.
     */
    private ParticipantCategory participantCategory;
    
    /**
     * Represents an instance of SpecialEvents.
     */
    private SpecialEvents specialEvents;
    
    /**
     * Represents an instance of SpecialEvents.
     */
    private SpecialEvents newSpecialEvents;
    
    /**
     * Represents an instance of SpecialEventsParticipation.
     */
    private SpecialEventsParticipation specialEventsParticipation;
    
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
    
    /** Holds StudentClubSociety instance. */
    private StudentClubSociety studentClubSociety;
    
    /** Holds StudentClassInfo instance. */
    
    private StudentClassInfo studentClassInfo;
    
    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public void setUp() throws Exception {
    
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
        
        /***************************************/
        
        classInstace = new SchoolClass();
        classInstace.setDescription("NewClass");
        classInstace.setModifiedTime(new Date());
        SchoolClass newClassInstace = schoolClassDao.save(classInstace);
        assertNotNull(newClassInstace);
        
        grade = new Grade();
        grade.setDescription("newGrade");
        Grade newGrade = gradeDao.save(grade);
        assertNotNull(newGrade);
        
        classGrade = new ClassGrade();
        classGrade.setSchoolClass(newClassInstace);
        classGrade.setGrade(newGrade);
        classGrade.setDescription(grade.getDescription() + classInstace.getDescription());
        ClassGrade newClassGrade = classGradeDao.save(classGrade);
        assertNotNull(newClassGrade);
        
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
        newSpecialEvents = specialEventsDao.save(specialEvents);
        assertNotNull(newSpecialEvents);
        
        specialEventsParticipation = new SpecialEventsParticipation();
        specialEventsParticipation.setClassGrade(newClassGrade);
        specialEventsParticipation.setModifiedTime(new Date());
        specialEventsParticipation.setSpecialEvents(newSpecialEvents);
        
        String year = DateUtil.getStringYear(new Date()) + START_YEAR;
        Date dateYear = DateUtil.getParseDate(year);
        
        studentClassInfo = new StudentClassInfo();
        studentClassInfo.setCheckMonitor(true);
        studentClassInfo.setClassGrade(classGrade);
        studentClassInfo.setModifiedTime(new Date());
        studentClassInfo.setStudent(student);
        studentClassInfo.setYear(dateYear);
        
        studentClassInfoDao.save(studentClassInfo);
    }
    
    /**
     * Test method for getSpecialEventParticipationStudentInfo method in
     * StudentSpecialEventParticipationViewDaoImpl.
     * 
     * @throws AkuraAppException if exception occurs
     */
    @Test
    public void test() throws AkuraAppException {
    
        SpecialEventsParticipation newSpecialEventsParticipation =
                specialEventsParticipationDao.save(specialEventsParticipation);
        
        String year = DateUtil.getStringYear(specialEvents.getDate()) + START_YEAR;
        Date dateYear = DateUtil.getParseDate(year);
        
        List<StudentSpecialEventParticipationView> list =
                studentSpecialEventParticipationViewDao.getSpecialEventParticipationStudentInfo(
                        newSpecialEventsParticipation.getSpecialEventsParticipationId(), dateYear);
        
        assertTrue(list.size() > 0);
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public final void teardown() throws AkuraAppException {
    
        try {
            if (studentClassInfo.getStudentClassInfoId() > 0) {
                studentClassInfoDao.delete(studentClassInfo);
            }
            specialEventsParticipationDao.delete(specialEventsParticipation);
            specialEventsDao.delete(specialEvents);
            participantCategoryDao.delete(participantCategory);
            classGradeDao.delete(classGrade);
            schoolClassDao.delete(classInstace);
            gradeDao.delete(grade);
            
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
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
}
