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

package com.virtusa.akura.staff.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ClassTeacher;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.ClassGradeDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.GradeDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.SchoolClassDao;
import com.virtusa.akura.common.dao.StaffCategoryDao;
import com.virtusa.akura.staff.BaseStaffTest;

/**
 * This test class, tests all the functionalities for the ClassTeacherDaoImpl.
 * 
 * @author Virtusa Corporation
 */
public class ClassTeacherDaoImplTest extends BaseStaffTest {
    
    /** . */
    private static final int YEAR_2012 = 2012;
    
    /** . */
    private static final String YEAR_2012_01_01 = String.valueOf(YEAR_2012) + "-01-01";
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private ClassTeacherDao classTeacherDao;
    
    /**
     * Represents an instance of ClassTeacher.
     */
    private ClassTeacher classTeacher;
    
    /** Holds StaffDao instance. */
    @Autowired
    private StaffDao staffDao;
    
    /** Holds StaffCategoryDao instance. */
    @Autowired
    private StaffCategoryDao staffCategoryDao;
    
    /** Holds GradeDao instance. */
    @Autowired
    private GradeDao gradeDao;
    
    /** Holds ClassGradeDao instance. */
    @Autowired
    private ClassGradeDao classGradeDao;
    
    /** Holds SchoolClassDao instance. */
    @Autowired
    private SchoolClassDao schoolClassDao;
    
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
    
    /** Holds Grade instance. */
    private Grade grade;
    
    /** Holds SchoolClass instance. */
    private SchoolClass schoolClass;
    
    /** Holds ClassGrade instance. */
    private ClassGrade classGrade;
    
    /** Holds Grade instance. */
    private Grade updatedGrade;
    
    /** Holds SchoolClass instance. */
    private SchoolClass updatedSchoolClass;
    
    /** Holds ClassGrade instance. */
    private ClassGrade updatedClassGrade;
    
    /** Holds StaffCategory instance. */
    private StaffCategory staffCategory;
    
    /** Holds City instance. */
    private City city;
    
    /** Holds District instance. */
    private District district;
    
    /** Holds District instance. */
    private Province province;
    
    /** Holds date instance for year. */
    private Date date;
    
    /**
     * Setting up resources.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Before
    public final void setUp() throws AkuraAppException {
    
        // Instantiate a Province object
        Province newProvince = new Province();
        newProvince.setDescription("Hello44");
        
        province = provinceDao.save(newProvince);
        assertNotNull(" Province saved to db " + province);
        
        // Instantiate a District object
        District newDistrict = new District();
        newDistrict.setDescription("ClassTeacherDaoImplTest44");
        newDistrict.setProvince(province);
        district = districtDao.save(newDistrict);
        assertNotNull(" District saved to db " + district);
        
        // Instantiate a city
        City newCity = new City();
        newCity.setDescription("ClassTeacherDaoImplTest44");
        newCity.setDistrict(district);
        city = cityDao.save(newCity);
        assertNotNull(" City saved to db " + city);
        
        // Instantiate a staffCategory object.
        StaffCategory newStaffCategory = new StaffCategory();
        newStaffCategory.setAcademic(true);
        newStaffCategory.setDescription("ClassTeacherDaoImplTest44");
        staffCategory = staffCategoryDao.save(newStaffCategory);
        assertNotNull(" StaffCategory saved to db " + staffCategory);
        
        // Instantiate a staff object.
        Staff newStaff = new Staff();
        newStaff.setStaffCategory(staffCategory);
        newStaff.setRegistrationNo("ClassTeacherDaoImplTest44");
        newStaff.setDateOfHire(new Date());
        newStaff.setFullName("ClassTeacherDaoImplTest FullName");
        newStaff.setLastName("ClassTeacherDaoImplTest Name");
        newStaff.setNationalID("ClassTeacherDaoImplTest44");
        newStaff.setDateOfBirth(new Date());
        newStaff.setAddress("ClassTeacherDaoImplTest Address");
        newStaff.setGender('M');
        newStaff.setCityId(city.getCityId());
        staff = staffDao.save(newStaff);
        assertNotNull(" Staff saved to db " + staff);
        
        // Instantiate a grade
        Grade newGrade = new Grade();
        newGrade.setDescription("ClassTeacherDaoImplTest44");
        grade = gradeDao.save(newGrade);
        assertNotNull(" grade saved to db " + grade);
        
        // Instantiate a schoolClass
        SchoolClass newSchoolClass = new SchoolClass();
        newSchoolClass.setDescription("ClassTeacherDaoImplTest44");
        schoolClass = schoolClassDao.save(newSchoolClass);
        assertNotNull(" SchoolClass saved to db " + schoolClass);
        
        // Instantiate a ClassGrade object
        ClassGrade newClassGrade = new ClassGrade();
        newClassGrade.setGrade(grade);
        newClassGrade.setSchoolClass(schoolClass);
        newClassGrade.setDescription("ClassTeacherDaoImplTest44");
        classGrade = classGradeDao.save(newClassGrade);
        assertNotNull(" classGrade saved to db " + classGrade);
        
        // Instantiate a updateGrade
        Grade updateGrade = new Grade();
        updateGrade.setDescription("ClassTeacherDaoImplTest55");
        updatedGrade = gradeDao.save(updateGrade);
        assertNotNull(" grade saved to db " + updatedGrade);
        
        // Instantiate a updateSchoolClass
        SchoolClass updateSchoolClass = new SchoolClass();
        updateSchoolClass.setDescription("ClassTeacherDaoImplTest55");
        updatedSchoolClass = schoolClassDao.save(updateSchoolClass);
        assertNotNull(" SchoolClass saved to db " + updatedSchoolClass);
        
        // Instantiate a updateClassGrade object
        ClassGrade updateClassGrade = new ClassGrade();
        updateClassGrade.setGrade(updatedGrade);
        updateClassGrade.setSchoolClass(updatedSchoolClass);
        updateClassGrade.setDescription("ClassTeacherDaoImplTest55");
        updatedClassGrade = classGradeDao.save(updateClassGrade);
        assertNotNull(" classGrade saved to db " + updatedClassGrade);
        
        // Instantiate a ClassTeacher
        classTeacher = new ClassTeacher();
        classTeacher.setClassGrade(classGrade);
        classTeacher.setStaff(staff);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = (Date) formatter.parse(YEAR_2012_01_01);
            classTeacher.setYear(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method to check if a ClassTeacher object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveClassTeacher() throws AkuraAppException {
    
        try {
            
            ClassTeacher newClassTeacher = classTeacherDao.save(classTeacher);
            assertNotNull("New ClassTeacher should not be null. " + newClassTeacher);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a ClassTeacher object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateClassTeacher() throws AkuraAppException {
    
        ClassTeacher newClassTeacher = null;
        try {
            newClassTeacher = classTeacherDao.save(classTeacher);
            assertNotNull("New ClassTeacher should not be null " + newClassTeacher);
            
            newClassTeacher.setClassGrade(updatedClassGrade);
            classTeacherDao.update(newClassTeacher);
            ClassTeacher updatedClassTeacher =
                    (ClassTeacher) classTeacherDao.findById(ClassTeacher.class, newClassTeacher.getClassTeacherId());
            assertEquals(updatedClassGrade.getClassGradeId(), updatedClassTeacher.getClassGrade().getClassGradeId());
            
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
    public void testSaveOrUpdateAll() throws AkuraAppException {
    
        try {
            
            List<ClassTeacher> classTeacherList = new ArrayList<ClassTeacher>();
            classTeacherList.add(classTeacher);
            
            classTeacherDao.saveOrUpdateAll(classTeacherList);
            List<ClassTeacher> newClassTeacherList = classTeacherDao.findAll(ClassTeacher.class);
            assertNotNull("ClassTeacher list should not be null ", newClassTeacherList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check find the ClassTeacher records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindClassTeacherById() throws AkuraAppException {
    
        try {
            
            ClassTeacher newClassTeacher = classTeacherDao.save(classTeacher);
            assertNotNull("New ClassTeacher should not be null " + newClassTeacher);
            
            ClassTeacher findClassTeacher =
                    (ClassTeacher) classTeacherDao.findById(ClassTeacher.class, newClassTeacher.getClassTeacherId());
            assertNotNull("find ClassTeacher should not be null ", findClassTeacher);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the ClassTeacher records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindAllClassTeachers() throws AkuraAppException {
    
        try {
            
            ClassTeacher newClassTeacher = classTeacherDao.save(classTeacher);
            assertNotNull("New ClassTeacher should not be null " + newClassTeacher);
            
            List<ClassTeacher> classTeacherList = classTeacherDao.findAll(ClassTeacher.class);
            assertNotNull("ClassTeacher list should not be null ", classTeacherList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test case to test ClassTeacher Search.
     * 
     * @throws AkuraAppException - AkuraAppException.
     */
    @Test
    public void testSearchClassTeacher() throws AkuraAppException {
    
        classTeacher.getStaff().setDateOfDeparture(null);
        ClassTeacher newClassTeacher = classTeacherDao.save(classTeacher);
        assertNotNull("New ClassTeacher should not be null " + newClassTeacher);
        
        SimpleDateFormat fomatter = new SimpleDateFormat("yyyy");
        
        List<ClassTeacher> classTeacherList =
                classTeacherDao.searchClassTeacher("", newClassTeacher.getStaff().getLastName(),
                        fomatter.format(newClassTeacher.getYear()));
        
        assertTrue(classTeacherList.size() > 0);
        
    }
    
    /**
     * test case to test getClassTeacher().
     * 
     * @throws AkuraAppException - AkuraAppException.
     */
    @Test
    public void testGetClassTeacher() throws AkuraAppException {
    
        classTeacher = classTeacherDao.save(classTeacher);
        
        ClassTeacher cTeacher = classTeacherDao.getClassTeacher(classGrade, YEAR_2012);
        assertNotNull("ClassTeacher can not be null", cTeacher);
        
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        try {
            if (classTeacher.getClassTeacherId() > 0) {
                classTeacherDao.delete(classTeacher);
            }
            if (staff.getStaffId() > 0) {
                staffDao.delete(staff);
            }
            if (staffCategory.getCatogaryID() > 0) {
                staffCategoryDao.delete(staffCategory);
            }
            if (city.getCityId() > 0) {
                cityDao.delete(city);
            }
            if (district.getDistrictId() > 0) {
                districtDao.delete(district);
            }
            if (province.getProvinceId() > 0) {
                provinceDao.delete(province);
            }
            if (updatedClassGrade.getClassGradeId() > 0) {
                classGradeDao.delete(updatedClassGrade);
            }
            if (classGrade.getClassGradeId() > 0) {
                classGradeDao.delete(classGrade);
            }
            if (updatedSchoolClass.getClassId() > 0) {
                schoolClassDao.delete(updatedSchoolClass);
            }
            if (schoolClass.getClassId() > 0) {
                schoolClassDao.delete(schoolClass);
            }
            if (updatedGrade.getGradeId() > 0) {
                gradeDao.delete(updatedGrade);
            }
            if (grade.getGradeId() > 0) {
                gradeDao.delete(grade);
            }
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        
    }
}
