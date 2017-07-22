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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentParent;
import com.virtusa.akura.api.dto.WorkingSegment;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.BloodGroupDao;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.HouseDao;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.dao.MethodOfTravelDao;
import com.virtusa.akura.common.dao.NationalityDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.ReligionDao;
import com.virtusa.akura.common.dao.WorkingSegmentDao;
import com.virtusa.akura.student.BaseStudentTest;

/**
 * This test class, tests all the persistence related functionality for the StudentParent domain object.
 * 
 * @author Virtusa Corporation
 */

public class StudentParentDaoImpTest extends BaseStudentTest {
    
    /** Holds ParentDao instance. */
    @Autowired
    private ParentDao parentDao;
    
    /** Holds Province instance. */
    @Autowired
    private ProvinceDao provinceDao;
    
    /** Holds District instance. */
    @Autowired
    private DistrictDao districtDao;
    
    /** Holds City instance. */
    @Autowired
    private CityDao cityDao;
    
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
    
    /** Holds WorkingSegmentDao instance. */
    @Autowired
    private WorkingSegmentDao workingSegmentDao;
    
    /** Holds Religion instance. */
    @Autowired
    private ReligionDao religionDao;
    
    /** Holds StudentParentDao instance. */
    @Autowired
    private StudentParentDao studentParentDao;
    
    /** Holds StudentDao instance. */
    @Autowired
    private StudentDao studentDao;
    
    /** Holds Parent instance. */
    private Parent parent;
    
    /** Holds Student instance. */
    private Student student;
    
    /** Holds StudentParent instance. */
    private StudentParent studentParent;
    
    /** Holds workingSegment instance. */
    private WorkingSegment workingSegment;
    
    /** Holds Religion instance. */
    private Religion religion;
    
    /** Holds province instance. */
    private Province province;
    
    /** Holds city instance. */
    private City city;
    
    /** Holds district instance. */
    private District district;
    
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
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public void setUp() throws Exception {
    
        String randomString = String.valueOf(Math.round(Math.random() * 1000));
        
        // Instantiate a Province object.
        Province newProvince = new Province();
        newProvince.setDescription("Western1111" + randomString);
        newProvince.setModifiedTime(new Date());
        
        // Instantiates a District object.
        District newDistrict = new District();
        newDistrict.setDescription("Gampaha1111" + randomString);
        newDistrict.setModifiedTime(new Date());
        
        // Instantiates a City object.
        City newCity = new City();
        newCity.setDescription("Dematagoda1111" + randomString);
        newCity.setModifiedTime(new Date());
        
        // Instantiates a Religion object.
        Religion newReligion = new Religion();
        newReligion.setDescription("Religion1111" + randomString);
        newReligion.setModifiedTime(new Date());
        
        // Instantiates a newWorkingSegment object.
        WorkingSegment newWorkingSegment = new WorkingSegment();
        newWorkingSegment.setDescription("Engineer1111" + randomString);
        newWorkingSegment.setModifiedTime(new Date());
        
        province = provinceDao.save(newProvince);
        assertNotNull(" province saved to db " + province);
        
        newDistrict.setProvince(newProvince);
        district = districtDao.save(newDistrict);
        assertNotNull(" district saved to db " + district);
        
        newCity.setDistrict(newDistrict);
        city = cityDao.save(newCity);
        assertNotNull(" district saved to db " + city);
        
        workingSegment = workingSegmentDao.save(newWorkingSegment);
        assertNotNull(" working segment saved to db " + workingSegment);
        
        religion = religionDao.save(newReligion);
        assertNotNull(" religion saved to db " + religion);
        
        // Instantiates a BloodGroup object.
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("AAAA" + randomString);
        bloodGroup.setModifiedTime(new Date());
        
        bloodGroup = bloodGroupDao.save(bloodGroup);
        assertNotNull(" bloodGroup saved to db " + bloodGroup);
        
        // Instantiates a Nationality object.
        nationality = new Nationality();
        nationality.setDescription("Sri Lankan1111" + randomString);
        nationality.setModifiedTime(new Date());
        
        nationality = nationalityDao.save(nationality);
        assertNotNull(" nationality saved to db " + nationality);
        
        // Instantiates a House object.
        house = new House();
        house.setName("House" + randomString);
        house.setModifiedTime(new Date());
        
        house = houseDao.save(house);
        assertNotNull(" house saved to db " + house);
        
        // Instantiates a Language object.
        language = new Language();
        language.setLanguage("Sinhala111" + randomString);
        language.setModifiedTime(new Date());
        
        language = languageDao.save(language);
        assertNotNull(" language saved to db " + language);
        
        // Instantiates a Method of travel object.
        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("Bus" + randomString);
        methodOfTravel.setModifiedTime(new Date());
        
        methodOfTravel = methodOfTravelDao.save(methodOfTravel);
        assertNotNull(" methodOftravel saved to db " + methodOfTravel);
        
        // Instantiates a newParent object.
        Parent newParent = new Parent();
        newParent.setDesignation("designation");
        newParent.setTeacher(false);
        newParent.setPastPupil(false);
        newParent.setFullName("fullName" + randomString);
        newParent.setNameWithInitials("nameWithInitials");
        newParent.setLastName("lastName");
        newParent.setNationalIdNo(randomString + "111050V");
        newParent.setAddress("address");
        newParent.setMobileNo("0123456789");
        newParent.setResidenceNo("0123456789");
        newParent.setOfficeNo("0123456789");
        newParent.setRelationship("Mother");
        newParent.setEmail("emal");
        newParent.setModifiedTime(new Date());
        newParent.setCityId(city.getCityId());
        newParent.setTempCityId(city.getCityId());
        newParent.setOfficeCityId(city.getCityId());
        newParent.setWorkingSegmentId(workingSegment.getWorkingSegmentId());
        newParent.setReligionId(religion.getReligionId());
        
        parent = parentDao.save(newParent);
        assertNotNull("New Parent should not be null. " + parent);
        
        // Instantiates a student object.
        Student newStudent = new Student();
        newStudent.setAdmissionNo("A1123" + randomString);
        newStudent.setNameWtInitials("I L");
        newStudent.setLastName("Lolratne ");
        newStudent.setAddress("Gampaha");
        newStudent.setFullName("Lol Lolrathne");
        newStudent.setModifiedTime(new Date());
        
        newStudent.setLanguageId(language.getLanguageId());
        newStudent.setReligionId(religion.getReligionId());
        newStudent.setTravelId(methodOfTravel.getTravelId());
        newStudent.setBloodGroupId(bloodGroup.getBloodGroupId());
        newStudent.setHouseId(house.getHouseId());
        
        newStudent.setCityId(city.getCityId());
        newStudent.setNationalityId(nationality.getNationalityId());
        newStudent.setGender('M');
        newStudent.setIsOldBoy(false);
        newStudent.setDateOfBirth(new Date());
        newStudent.setAdmissionDate(new Date());
        newStudent.setFirstSchoolDay(new Date());
        
        student = studentDao.save(newStudent);
        
        // Instantiate a Student Parent object.
        studentParent = new StudentParent();
        studentParent.setStudent(student);
        studentParent.setParent(parent);
        studentParent.setRelationship('F');
        studentParent.setModifiedTime(new Date());
    }
    
    /**
     * Test method to check if a StudentParent object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveStudentParent() throws AkuraAppException {
    
        try {
            
            StudentParent newStudentParent = studentParentDao.save(studentParent);
            assertNotNull("Student Parent type should not be null " + newStudentParent);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a StudentParent object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateStudentParent() throws AkuraAppException {
    
        try {
            StudentParent newStudentParent = studentParentDao.save(studentParent);
            assertNotNull("Student Parent type should not be null " + newStudentParent);
            
            newStudentParent.setParent(parent);
            studentParentDao.update(newStudentParent);
            
            StudentParent upStudentParent =
                    (StudentParent) studentParentDao.findById(StudentParent.class,
                            newStudentParent.getStudentParentId());
            assertEquals(parent.getParentId(), upStudentParent.getParent().getParentId());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a list of objects are saved / updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveOrUpdateStudentParent() throws AkuraAppException {
    
        try {
            
            List<StudentParent> studentParentList = new ArrayList<StudentParent>();
            studentParentList.add(studentParent);
            
            studentParentDao.saveOrUpdateAll(studentParentList);
            List<StudentParent> newStudentParentList = studentParentDao.findAll(StudentParent.class);
            assertTrue(newStudentParentList.size() > 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check find the newStudentParent records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindStudentParentByID() throws AkuraAppException {
    
        try {
            
            StudentParent newStudentParent = studentParentDao.save(studentParent);
            assertNotNull("New StudentParent should not be null " + newStudentParent);
            
            StudentParent findStudentParent =
                    (StudentParent) studentParentDao.findById(StudentParent.class,
                            newStudentParent.getStudentParentId());
            assertNotNull("Find StudentParent should not be null ", findStudentParent);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the StudentParent records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindAllStudentParent() throws AkuraAppException {
    
        try {
            
            StudentParent newStudentParent = studentParentDao.save(studentParent);
            assertNotNull("New StudentParent should not be null " + newStudentParent);
            
            List<StudentParent> studentParentList = studentParentDao.findAll(StudentParent.class);
            assertNotNull(studentParentList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * method to test getetStudentByParnetId(int).
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void getetStudentByParnetId() throws AkuraAppException{
        try {    
            StudentParent newStudentParent = studentParentDao.save(studentParent);
            assertNotNull("Student Parent type should not be null " + newStudentParent);
            
            // test 
            List<Student> stu=studentParentDao.getStudentByParnetId(parent.getParentId());
            assertNotNull("Can not be null",stu);
            assertTrue("Can not be empty", !stu.isEmpty());

    
            studentParentDao.delete(studentParent);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }     
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        try {
            // if this object studentParent already deleted. 
            studentParentDao.delete(studentParent);
   
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        try {
            studentDao.delete(student);
            parentDao.delete(parent);
            
            cityDao.delete(city);
            districtDao.delete(district);
            provinceDao.delete(province);
            religionDao.delete(religion);
            workingSegmentDao.delete(workingSegment);
            
            bloodGroupDao.delete(bloodGroup);
            nationalityDao.delete(nationality);
            houseDao.delete(house);
            methodOfTravelDao.delete(methodOfTravel);
            languageDao.delete(language);
        }catch (AkuraAppException e) {
            e.printStackTrace();
        }
        
    }
}
