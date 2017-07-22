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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.DailyStudentAttendance;
import com.virtusa.akura.api.dto.DailyTeacherAttendance;
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
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.attendance.BaseAttendanceTest;
import com.virtusa.akura.attendance.dao.DailyStudentAttendanceDao;
import com.virtusa.akura.attendance.dao.DailyTeacherAttendanceDao;
import com.virtusa.akura.attendance.dao.SpecialEventsAttendanceDao;
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
import com.virtusa.akura.common.dao.StaffCategoryDao;
import com.virtusa.akura.staff.dao.StaffDao;
import com.virtusa.akura.student.dao.StudentClassInfoDao;
import com.virtusa.akura.student.dao.StudentDao;

/**
 * This test class, tests all the persistence related functionality for the DailyAttendanceServiceImpl domain
 * object.
 * 
 * @author Virtusa Corporation
 */
public class DailyAttendanceServiceImplTest extends BaseAttendanceTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private DailyAttendanceService dailyAttendanceService;
    
    /** Holds dailyTeacherAttendance instance. */
    private DailyTeacherAttendance dailyTeacherAttendance;
    
    /** Holds dailyStudentAttendance instance. */
    private DailyStudentAttendance dailyStudentAttendance;
    
    /** Holds DailyStudentAttendanceDao instance. */
    @Autowired
    private DailyStudentAttendanceDao dailyStudentAttendanceDao;
    
    /** Holds DailyTeacherAttendanceDao instance. */
    @Autowired
    private DailyTeacherAttendanceDao dailyTeacherAttendanceDao;
    
    /** Holds StaffDao instance. */
    @Autowired
    private StaffDao staffDao;
    
    /** Holds StaffCategoryDao instance. */
    @Autowired
    private StaffCategoryDao staffCategoryDao;
    
    /** Holds CityDao instance. */
    @Autowired
    private CityDao cityDao;
    
    /** Holds DistrictDao instance. */
    @Autowired
    private DistrictDao districtDao;
    
    /** Holds ProvinceDao instance. */
    @Autowired
    private ProvinceDao provinceDao;
    
    /** Holds staff instance. */
    private Staff staff;
    
    /** Holds StaffCategory instance. */
    private StaffCategory staffCategory;
    
    /** Holds City instance. */
    private City city;
    
    /** Holds District instance. */
    private District district;
    
    /** Holds Province instance. */
    private Province province;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private BloodGroupDao bloodGroupDao;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private NationalityDao nationalityDao;
    
    /** Holds houseDAO instance of {@link HouseDao}. */
    @Autowired
    private HouseDao houseDao;
    
    /** Holds languageDao instance of {@link LanguageDao}. */
    @Autowired
    private LanguageDao languageDao;
    
    /** Holds methodOfTravelDAO instance of {@link MethodOfTravelDao}. */
    @Autowired
    private MethodOfTravelDao methodOfTravelDao;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private StudentDao studentDao;
    
    /** Represents an instance of BloodGroup. */
    private BloodGroup bloodGroup;
    
    /** Represents an instance of Nationality. */
    private Nationality nationality;
    
    /** Holds house instance of {@link House}. */
    private House house;
    
    /** Holds language instance of {@link Language}. */
    private Language language;
    
    /** Holds methodOfTravel instance of {@link MethodOfTravel}. */
    private MethodOfTravel methodOfTravel;
    
    /** Holds Religion instance. */
    private Religion religion;
    
    /** Holds Religion instance. */
    @Autowired
    private ReligionDao religionDao;
    
    /**
     * Instance of a Student entity.
     */
    private Student student;
    
    /**
     * Instance of a ClassGrade entity.
     */
    private ClassGrade classGrade;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private StudentClassInfoDao studentClassInfoDao;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private GradeDao gradeDao;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SchoolClassDao schoolClassDao;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private ClassGradeDao classGradeDao;
    
    /**
     * Instance of a SchoolClass entity.
     */
    private SchoolClass schoolClass;
    
    /**
     * Instance of a Grade entity.
     */
    private Grade grade;
    
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
     * This instance will be dependency injected by type.
     */
    
    private StudentClassInfo studentClassInfo;
    
    /** Represents an instance of SpecialEventsParticipation. */
    private SpecialEventsParticipation specialEventsParticipation;
    
    /** Injects an instance of StudentDao. */
    @Autowired
    private SpecialEventsParticipationDao specialEventsParticipationDao;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SpecialEventsAttendanceDao specialEventsAttendanceDao;
    
    /** Holds specialEventsAttendance instance. */
    private SpecialEventsAttendance specialEventsAttendance;
    
    /** random string. */
    private String randomString=String.valueOf(Math.round(Math.random() * 1000));
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public void setUp() throws Exception {
    
        Province newProvince = new Province();
        newProvince.setDescription("Test"+randomString);
        province = provinceDao.save(newProvince);
        assertNotNull(" Province saved to db " + province);
        
        District newDistrict = new District();
        newDistrict.setDescription("Test1"+randomString);
        newDistrict.setProvince(province);
        district = districtDao.save(newDistrict);
        assertNotNull(" District saved to db " + district);
        
        City newCity = new City();
        newCity.setDescription("Test1"+randomString);
        newCity.setDistrict(district);
        city = cityDao.save(newCity);
        assertNotNull(" City saved to db " + city);
        
        staffCategory = new StaffCategory();
        staffCategory.setAcademic(true);
        staffCategory.setDescription("DTest"+randomString);
        staffCategory = staffCategoryDao.save(staffCategory);
        assertNotNull(" StaffCategory saved to db " + staffCategory);
        
        Staff newStaff = new Staff();
        newStaff.setStaffCategory(staffCategory);
        newStaff.setRegistrationNo("DlTest"+randomString);
        newStaff.setDateOfHire(new Date());
        newStaff.setFullName("DailyAttl4Test");
        newStaff.setLastName("DailyAtIm4plTest");
        newStaff.setNationalID("DailyAtt4eeImplTest");
        newStaff.setDateOfBirth(new Date());
        newStaff.setAddress("DailyAtt4lTest");
        newStaff.setGender('M');
        newStaff.setCityId(city.getCityId());
        staff = staffDao.save(newStaff);
        assertNotNull(" Staff saved to db " + staff);
        
        dailyTeacherAttendance = new DailyTeacherAttendance();
        
        dailyTeacherAttendance.setStaffId(staff.getStaffId());
        dailyTeacherAttendance.setDate(new Date());
        dailyTeacherAttendance.setTimeIn("7.30");
        dailyTeacherAttendance.setTimeOut("1.30");
        dailyTeacherAttendance.setModifiedTime(new Date());
        
        religion = new Religion();
        religion.setDescription("Test1"+randomString);
        religion.setModifiedTime(new Date());
        religion = religionDao.save(religion);
        
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("Dail"+randomString);
        bloodGroup.setModifiedTime(new Date());
        bloodGroup = bloodGroupDao.save(bloodGroup);
        
        nationality = new Nationality();
        nationality.setDescription("DaiTest1"+randomString);
        nationality.setModifiedTime(new Date());
        nationality = nationalityDao.save(nationality);
        
        house = new House();
        house.setName("tes1"+randomString);
        house.setModifiedTime(new Date());
        house = houseDao.save(house);
        assertNotNull(" house saved to db " + house);
        
        language = new Language();
        language.setLanguage("DaTest1"+randomString);
        language.setModifiedTime(new Date());
        language = languageDao.save(language);
        assertNotNull(" language saved to db " + language);
        
        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("Dail"+randomString);
        methodOfTravel.setModifiedTime(new Date());
        methodOfTravel = methodOfTravelDao.save(methodOfTravel);
        assertNotNull(" methodOftravel saved to db " + methodOfTravel);
        
        student = new Student();
        student.setAdmissionNo("Da"+randomString);
        student.setNameWtInitials("Dail4yAmplTest1");
        student.setLastName("DailyAttp4lTest1");
        student.setAddress("DailyAImp4lTest1");
        student.setFullName("DailyAI4mplTest1");
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
        
        student = studentDao.save(student);
        assertNotNull("New Student should not be null " + student);
        
        dailyStudentAttendance = new DailyStudentAttendance();
        
        dailyStudentAttendance.setStudentId(student.getStudentId());
        dailyStudentAttendance.setDate(new Date());
        dailyStudentAttendance.setTimeIn("7.30");
        dailyStudentAttendance.setTimeOut("1.30");
        dailyStudentAttendance.setModifiedTime(new Date());
        
        grade = new Grade();
        grade.setDescription("est1"+randomString);
        grade.setModifiedTime(new Date());
        grade = gradeDao.save(grade);
        
        schoolClass = new SchoolClass();
        schoolClass.setDescription("DailTes"+randomString);
        schoolClass.setModifiedTime(new Date());
        schoolClass = schoolClassDao.save(schoolClass);
        
        classGrade = new ClassGrade();
        classGrade.setModifiedTime(new Date());
        classGrade.setGrade(grade);
        classGrade.setSchoolClass(schoolClass);
        classGrade.setDescription("DaTe"+randomString);
        classGrade = classGradeDao.save(classGrade);
        
        studentClassInfo = new StudentClassInfo();
        studentClassInfo.setCheckMonitor(true);
        studentClassInfo.setClassGrade(classGrade);
        studentClassInfo.setModifiedTime(new Date());
        studentClassInfo.setStudent(student);
        studentClassInfo.setYear(new Date());
        studentClassInfo = studentClassInfoDao.save(studentClassInfo);
        assertNotNull("New studentClassInfo should not be null " + studentClassInfo);
        
        participantCategory = new ParticipantCategory();
        participantCategory.setDescription("de"+randomString);
        participantCategory.setModifiedTime(new Date());
        participantCategory = participantCategoryDao.save(participantCategory);
        assertNotNull(participantCategory);
        
        specialEvents = new SpecialEvents();
        specialEvents.setDate(new Date());
        specialEvents.setDescription("de"+randomString);
        specialEvents.setModifiedTime(new Date());
        specialEvents.setName("nae4ef");
        specialEvents.setParticipantCategory(participantCategory);
        specialEvents = specialEventsDao.save(specialEvents);
        assertNotNull(specialEvents);
        
        specialEventsParticipation = new SpecialEventsParticipation();
        specialEventsParticipation.setClassGrade(classGrade);
        specialEventsParticipation.setModifiedTime(new Date());
        specialEventsParticipation.setSpecialEvents(specialEvents);
        specialEventsParticipation = specialEventsParticipationDao.save(specialEventsParticipation);
        assertNotNull(specialEventsParticipation);
        
        specialEventsAttendance = new SpecialEventsAttendance();
        specialEventsAttendance.setSpecialEventsParticipation(specialEventsParticipation);
        specialEventsAttendance.setStudentId(student.getStudentId());
        specialEventsAttendance.setModifiedTime(new Date());
        
    }
    
    /**
     * Test method for SpecialEventAttendance object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testAddSpecialEventAttendance() throws AkuraAppException {
    
        try {
            SpecialEventsAttendance newSpecialEventsAttendance =
                    specialEventsAttendanceDao.save(specialEventsAttendance);
            List<SpecialEventsAttendance> specialEventsAttendanceList = new ArrayList<SpecialEventsAttendance>();
            specialEventsAttendanceList.add(newSpecialEventsAttendance);
            dailyAttendanceService.saveSpecialEventsAttendance(specialEventsAttendanceList);
            List<SpecialEventsAttendance> updatedSpecialEventsAttendance =
                    specialEventsAttendanceDao.findAll(SpecialEventsAttendance.class);
            assertTrue(updatedSpecialEventsAttendance.size() > 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        }
    }
    
    /**
     * Test method for getSpecialEventsAttendanceObjectByStudentId.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testGetSpecialEventsAttendanceObjectByStudentId() throws AkuraAppException {
    
        try {
            specialEventsAttendance = specialEventsAttendanceDao.save(specialEventsAttendance);
            assertNotNull(specialEventsAttendance);
            
            List<SpecialEventsAttendance> specialEventsAttendances =
                    dailyAttendanceService.getSpecialEventsAttendanceObjectByStudentId(student.getStudentId(),
                            specialEventsParticipation.getSpecialEventsParticipationId());
            assertTrue(specialEventsAttendances.size() > 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        }
    }
    
    /**
     * Test method for getSpecialEventsAttendanceList.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testGetSpecialEventsAttendanceList() throws AkuraAppException {
    
        try {
            specialEventsAttendance = specialEventsAttendanceDao.save(specialEventsAttendance);
            assertNotNull(specialEventsAttendance);
            
            List<SpecialEventsAttendance> specialEventsAttendances =
                    dailyAttendanceService.getSpecialEventAttandanceList(specialEventsParticipation
                            .getSpecialEventsParticipationId());
            assertTrue(specialEventsAttendances.size() > 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        }
    }
    
    /**
     * Test method to check if a DailyStudentAttendance object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testAddDailyStudentAttendance() throws AkuraAppException {
    
        try {
            
            DailyStudentAttendance newdailyStudentAttendance = dailyStudentAttendanceDao.save(dailyStudentAttendance);
            assertNotNull("DailyStudentAttendance Type object should not be null ", newdailyStudentAttendance);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        }
    }
    
    /**
     * Test method to check if a DailyTeacherAttendance object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testAddDailyTeacherAttendance() throws AkuraAppException {
    
        try {
            
            DailyTeacherAttendance newdailyTeacherAttendance = dailyTeacherAttendanceDao.save(dailyTeacherAttendance);
            assertNotNull("DailyTeacherAttendance Type object should not be null ", newdailyTeacherAttendance);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        }
    }
    
    /**
     * Test method to check findByStaffId.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testfindByStaffId() throws AkuraAppException {
    
        try {
            
            DailyTeacherAttendance newdailyTeacherAttendance = dailyTeacherAttendanceDao.save(dailyTeacherAttendance);
            assertNotNull("DailyTeacherAttendance Type object should not be null ", newdailyTeacherAttendance);
            List<DailyTeacherAttendance> dailyTeacherAttendances =
                    dailyAttendanceService.findByTeacherId(newdailyTeacherAttendance.getStaffId(),
                            newdailyTeacherAttendance.getDate());
            assertNotNull(dailyTeacherAttendances.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        }
    }
    
    /**
     * Test method to check findByStudentId.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testfindByStudentId() throws AkuraAppException {
    
        try {
            
            DailyStudentAttendance newdailyStudentAttendance = dailyStudentAttendanceDao.save(dailyStudentAttendance);
            assertNotNull("DailyStudentAttendance Type object should not be null ", newdailyStudentAttendance);
            List<DailyStudentAttendance> dailyStudentAttendances =
                    dailyAttendanceService.findByStudentId(newdailyStudentAttendance.getStudentId(),
                            newdailyStudentAttendance.getDate());
            assertNotNull(dailyStudentAttendances.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        }
    }
    
    /**
     * Test method to check getTeacherAttandanceList.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testGetTeacherAttandanceList() throws AkuraAppException {
    
        try {
            
            DailyTeacherAttendance newdailyTeacherAttendance = dailyTeacherAttendanceDao.save(dailyTeacherAttendance);
            assertNotNull("DailyTeacherAttendance Type object should not be null ", newdailyTeacherAttendance);
            List<DailyTeacherAttendance> dailyTeacherAttendances =
                    dailyAttendanceService.getTeacherAttandanceList(newdailyTeacherAttendance.getDate(), true);
            assertNotNull(dailyTeacherAttendances.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        }
    }
    
    /**
     * Test method to check findByStudentId.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testGetStudentAttandanceList() throws AkuraAppException {
    
        try {
            
            DailyStudentAttendance newdailyStudentAttendance = dailyStudentAttendanceDao.save(dailyStudentAttendance);
            assertNotNull("DailyStudentAttendance Type object should not be null ", newdailyStudentAttendance);
            List<DailyStudentAttendance> dailyStudentAttendances =
                    dailyAttendanceService.getStudentAttandanceList(newdailyStudentAttendance.getDate(),
                            studentClassInfo.getStudentClassInfoId());
            assertNotNull(dailyStudentAttendances.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            
        }
    }
    
    @Test
    public void getAttendanceBettweenTest() throws AkuraAppException {
    
        DailyStudentAttendance newdailyStudentAttendance = dailyStudentAttendanceDao.save(dailyStudentAttendance);
        
        List<DailyStudentAttendance> attendeceList =
                dailyAttendanceService.getAttendanceBettween(student.getStudentId(), dailyStudentAttendance.getDate(),
                        dailyStudentAttendance.getDate());
        
        assertNotNull("list can not be null", attendeceList);
        assertTrue("list must contain value", attendeceList.size() > 0);
        
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        try {
            
            if (dailyStudentAttendance != null) {
                dailyAttendanceService.deleteDailyStudentAttendance(dailyStudentAttendance);
            }
            if (dailyTeacherAttendance != null) {
                dailyAttendanceService.deleteDailyTeacherAttendance(dailyTeacherAttendance);
            }
            if (specialEventsAttendance != null) {
                dailyAttendanceService.deleteSpecialEventAttendance(specialEventsAttendance);
            }
            
            staffDao.delete(staff);
            
            specialEventsParticipationDao.delete(specialEventsParticipation);
            specialEventsDao.delete(specialEvents);
            participantCategoryDao.delete(participantCategory);
            studentClassInfoDao.delete(studentClassInfo);
            studentDao.delete(student);
            staffDao.delete(staff);
            staffCategoryDao.delete(staffCategory);
            
            cityDao.delete(city);
            districtDao.delete(district);
            provinceDao.delete(province);
            bloodGroupDao.delete(bloodGroup);
            nationalityDao.delete(nationality);
            religionDao.delete(religion);
            houseDao.delete(house);
            methodOfTravelDao.delete(methodOfTravel);
            languageDao.delete(language);
            classGradeDao.delete(classGrade);
            gradeDao.delete(grade);
            schoolClassDao.delete(schoolClass);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        
    }
    
}
