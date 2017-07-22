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
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Seminar;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentSeminar;
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
import com.virtusa.akura.common.dao.SeminarDao;
import com.virtusa.akura.student.BaseStudentTest;

/** Unit test class to test studentSeminarDaoImpl functions. */
public class StudentSeminarDaoImplTest extends BaseStudentTest {
    
    /** value to multiply. */
    private static final int HUNDRED = 100;
    
    /** This object is to create nessory StudentSeminra persistent objects. */
    @Autowired
    private StudentSeminarDao studentSeminarDao;
    
    /** to create nessory persistent objects. */
    @Autowired
    private StudentDao studentDao;
    
    /** to create nessory persistent objects. */
    @Autowired
    private SeminarDao seminarDao;
    
    /** to create nessory persistent objects. */
    @Autowired
    private LanguageDao languageDao;
    
    /** to create nessory persistent objects. */
    @Autowired
    private ReligionDao religionDao;
    
    /** to create nessory persistent objects. */
    @Autowired
    private MethodOfTravelDao methodOfTravelDao;
    
    /** to create nessory persistent objects. */
    @Autowired
    private BloodGroupDao bloodGroupDao;
    
    /** to create nessory persistent objects. */
    @Autowired
    private HouseDao houseDao;
    
    /** to create nessory persistent objects. */
    @Autowired
    private ProvinceDao provinceDao;
    
    /** to create nessory persistent objects. */
    @Autowired
    private DistrictDao districtDao;
    
    /** to create nessory persistent objects. */
    @Autowired
    private CityDao cityDao;
    
    /** to create nessory persistent objects. */
    @Autowired
    private NationalityDao nationalityDao;
    
    /** Holds House instance to create Student. */
    private House house;
    
    /** Holds BloodGroup instance to create Student. */
    private BloodGroup bloodGroup;
    
    /** Holds Language instance to create Student. */
    private Language language;
    
    /** Holds Religion instance to create Student. */
    private Religion religion;
    
    /** Holds MethodOfTravel instance to create Student. */
    private MethodOfTravel methodOfTravel;
    
    /** Holds province instance. */
    private Province province;
    
    /** Holds district instance. */
    private District district;
    
    /** Holds city instance. */
    private City city;
    
    /** Holds Nationality instance to create Student. */
    private Nationality nationality;
    
    /** Holds student instance to create StudentSeminar persistent object. */
    private Student student;
    
    /** Holds seminar instance to create StudentSeminar persistent object. */
    private Seminar seminar;
    
    /** Holds StudentSeminar persistent object. */
    private StudentSeminar studentSeminars;
    
    /**
     * this method will test getAllStudentSeminars() in studentSeminarDao.
     * 
     * @throws AkuraAppException - when exception that occurred.
     */
    @Test
    public void getAllStudentSeminarsTest() throws AkuraAppException {
    
        // Search parameters
        int stuId = student.getStudentId();
        Date forYear = studentSeminars.getYear();
        
        List<StudentSeminar> ssList = (List<StudentSeminar>) studentSeminarDao.getAllStudentSeminars(stuId, forYear);
        
        assertNotNull("List must not be null", ssList);
        assertTrue("List must have at least one value", !ssList.isEmpty());
        
        // fail("Not yet implemented");
    }
    
    /**
     * Tear up method for each test class.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Before
    public void tearUp() throws AkuraAppException {
    
        createStudent();
        
        seminar = new Seminar();
        seminar.setDescription("test desc");
        seminar = seminarDao.save(seminar);
        
        studentSeminars = new StudentSeminar();
        studentSeminars.setDescription("test s seminar");
        studentSeminars.setStudentId(student.getStudentId());
        studentSeminars.setSeminar(seminar);
        studentSeminars.setYear(new Date());
        studentSeminarDao.save(studentSeminars);
        
    }
    
    /**
     * Tear down method for each test class.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        try {
            studentSeminarDao.delete(studentSeminars);
            studentDao.delete(student);
            seminarDao.delete(seminar);
            
            religionDao.delete(religion);
            languageDao.delete(language);
            methodOfTravelDao.delete(methodOfTravel);
            bloodGroupDao.delete(bloodGroup);
            houseDao.delete(house);
            nationalityDao.delete(nationality);
            cityDao.delete(city);
            districtDao.delete(district);
            provinceDao.delete(province);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * method will create Student record in the table and it assigned to student attribute.
     * 
     * @throws AkuraAppException - when exception that occurred.
     */
    private void createStudent() throws AkuraAppException {
    
        String ranString = String.valueOf(Math.round(Math.random() * HUNDRED));
        
        // Instantiates a Religion object.
        religion = new Religion();
        religion.setDescription("Religion" + ranString);
        religion.setModifiedTime(new Date());
        
        religion = religionDao.save(religion);
        assertNotNull(" religion saved to db " + religion);
        
        // Instantiates a Language object.
        language = new Language();
        language.setLanguage("Sinhala111" + ranString);
        language.setModifiedTime(new Date());
        
        language = languageDao.save(language);
        assertNotNull(" language saved to db " + language);
        
        // Instantiates a Method of travel object.
        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("Bus" + ranString);
        methodOfTravel.setModifiedTime(new Date());
        
        methodOfTravel = methodOfTravelDao.save(methodOfTravel);
        assertNotNull(" methodOftravel saved to db " + methodOfTravel);
        
        // Instantiates a BloodGroup object.
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("AAAA" + ranString);
        bloodGroup.setModifiedTime(new Date());
        
        bloodGroup = bloodGroupDao.save(bloodGroup);
        assertNotNull(" bloodGroup saved to db " + bloodGroup);
        
        // Instantiates a House object.
        house = new House();
        house.setName("House" + ranString);
        house.setModifiedTime(new Date());
        
        house = houseDao.save(house);
        assertNotNull(" house saved to db " + house);
        
        // Instantiate a Province object.
        province = new Province();
        province.setDescription("Westernn1111" + ranString);
        province.setModifiedTime(new Date());
        
        province = provinceDao.save(province);
        assertNotNull(" province saved to db " + province);
        
        // Instantiates a District object.
        district = new District();
        district.setDescription("Gampaha1111" + ranString);
        district.setProvince(province);
        district.setModifiedTime(new Date());
        
        district = districtDao.save(district);
        assertNotNull(" district saved to db " + district);
        
        // Instantiates a City object.
        city = new City();
        city.setDescription("Dematagoda1111" + ranString);
        city.setDistrict(district);
        city.setModifiedTime(new Date());
        
        city = cityDao.save(city);
        assertNotNull(" city saved to db " + city);
        
        // Instantiates a Nationality object.
        nationality = new Nationality();
        nationality.setDescription("Sri Lankan111" + ranString);
        nationality.setModifiedTime(new Date());
        
        nationality = nationalityDao.save(nationality);
        assertNotNull(" nationality saved to db " + nationality);
        
        // Instantiates student object.
        student = new Student();
        student.setAdmissionNo("A1123" + ranString);
        student.setNameWtInitials("I L");
        student.setLastName("Lolratne " + ranString);
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
        
        student = studentDao.save(student);
        assertNotNull("New Student should not be null " + student);
        
    }
    
}
