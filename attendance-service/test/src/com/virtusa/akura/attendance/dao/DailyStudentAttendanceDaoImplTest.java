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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.DailyStudentAttendance;
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
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.ReligionDao;
import com.virtusa.akura.common.dao.SchoolClassDao;
import com.virtusa.akura.student.dao.StudentDao;

/**
 * This test class tests all the functionalities of the DailyStudentAttendanceDaoImpl class.
 * 
 * @author Virtusa Corporation
 */

public class DailyStudentAttendanceDaoImplTest extends BaseAttendanceTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private DailyStudentAttendanceDao dailyStudentAttendanceDao;
    
    /** Holds dailyStudentAttendance instance. */
    private DailyStudentAttendance dailyStudentAttendance;
    
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
    
    /** Injects an instance of StudentDao. */
    @Autowired
    private StudentDao studentDao;
    
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
        
        dailyStudentAttendance = new DailyStudentAttendance();
        
        dailyStudentAttendance.setStudentId(student.getStudentId());
        dailyStudentAttendance.setDate(new Date());
        dailyStudentAttendance.setTimeIn("7.30");
        dailyStudentAttendance.setTimeOut("1.30");
    }
    
    /**
     * Test method to check if a Add DailyStudentAttendance object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testAddDailyStudentAttendance() throws AkuraAppException {
    
        try {
            
            DailyStudentAttendance newDailyStudentAttendance =
                    this.dailyStudentAttendanceDao.save(dailyStudentAttendance);
            assertNotNull("New DailyStudentAttendance object can not be null" + newDailyStudentAttendance);
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method to check update and find by id DailyStudentAttendance object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateDailyStudentAttendance() throws AkuraAppException {
    
        try {
            
            DailyStudentAttendance newDailyStudentAttendance =
                    this.dailyStudentAttendanceDao.save(dailyStudentAttendance);
            assertNotNull("New DailyStudentAttendance object can not be null" + newDailyStudentAttendance);
            newDailyStudentAttendance.setStudentId(2);
            dailyStudentAttendanceDao.update(newDailyStudentAttendance);
            DailyStudentAttendance updatedDailyStudentAttendance =
                    (DailyStudentAttendance) this.dailyStudentAttendanceDao.findById(DailyStudentAttendance.class,
                            newDailyStudentAttendance.getDailyStudentAttendanceId());
            assertEquals(updatedDailyStudentAttendance.getStudentId(), newDailyStudentAttendance.getStudentId());
            
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method to check load all the DailyStudentAttendance records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testViewAllStudentPrefect() throws AkuraAppException {
    
        try {
            DailyStudentAttendance newDailyStudentAttendance =
                    this.dailyStudentAttendanceDao.save(dailyStudentAttendance);
            assertNotNull("New DailyStudentAttendance object can not be null" + newDailyStudentAttendance);
            
            List<DailyStudentAttendance> list = this.dailyStudentAttendanceDao.findAll(DailyStudentAttendance.class);
            assertNotNull(list.size());
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.attendance.dao.DailyStudentAttendanceDaoImpl#getStudentAttandanceList(Date date, int classGradeId)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testStudentAttandanceList() throws AkuraAppException {
    
        dailyStudentAttendanceDao.getStudentAttandanceList(new Date(), classGrade.getClassGradeId());
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.attendance.dao.DailyStudentAttendanceDaoImpl#findByStudentId(int studentId , Date date)}
     * .
     * 
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Test
    public final void testFindByStudentId() throws AkuraAppException {
    
        dailyStudentAttendanceDao.findByStudentId(student.getStudentId(), new Date());
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.attendance.dao.DailyStudentAttendanceDaoImpl# loadDataIntoDatabase(java.util.Set)}
     * .
     * 
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Test
    public final void testLoadDataIntoDatabase() throws AkuraAppException {
    
        Set<DailyStudentAttendance> stuDailyAttendance = new HashSet<DailyStudentAttendance>();
        stuDailyAttendance.add(dailyStudentAttendance);
        dailyStudentAttendanceDao.loadDataIntoDatabase(stuDailyAttendance);
        DailyStudentAttendance attendance =
                (DailyStudentAttendance) dailyStudentAttendanceDao.findById(DailyStudentAttendance.class,
                        dailyStudentAttendance.getDailyStudentAttendanceId());
        assertEquals(dailyStudentAttendance.getDailyStudentAttendanceId(), attendance.getDailyStudentAttendanceId());
    }
    
    /**
     * Test method for getAttendanceBettween().
     * 
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Test
    public void getAttendanceBettweenTest() throws AkuraAppException {
    
        Set<DailyStudentAttendance> stuDailyAttendance = new HashSet<DailyStudentAttendance>();
        stuDailyAttendance.add(dailyStudentAttendance);
        dailyStudentAttendanceDao.loadDataIntoDatabase(stuDailyAttendance);
        
        List<DailyStudentAttendance> attendeceList =
                dailyStudentAttendanceDao.getAttendanceBettween(student.getStudentId(),
                        dailyStudentAttendance.getDate(), dailyStudentAttendance.getDate());
        assertNotNull("list can not be null", attendeceList);
        assertTrue("list must contain value", attendeceList.size() > 0);
        
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @After
    public final void teardown() throws AkuraAppException {
    
        try {
            if (dailyStudentAttendance != null) {
                dailyStudentAttendanceDao.delete(dailyStudentAttendance);
            }
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
    
}
