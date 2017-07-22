/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.SubjectTeacher;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.ClassGradeDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.GradeDao;
import com.virtusa.akura.common.dao.GradeSubjectDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.SchoolClassDao;
import com.virtusa.akura.common.dao.StaffCategoryDao;
import com.virtusa.akura.common.dao.SubjectDao;
import com.virtusa.akura.staff.BaseStaffTest;

/**
 * This test class, tests all the functionalities for the SectionHeadDaoImpl.
 * 
 * @author Virtusa Corporation
 */
public class SubjectTeacherDaoImplTest extends BaseStaffTest {
    
    /** year value. */
    private static final int YEAR_2011 = 2011;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SubjectTeacherDao subjectTeacherDao;
    
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
    
    /** Holds GradeSubjectDao instance. */
    @Autowired
    private GradeSubjectDao gradeSubjectDao;
    
    /** Holds ClassGradeDao instance. */
    @Autowired
    private ClassGradeDao classGradeDao;
    
    /** Holds GradeDao instance. */
    @Autowired
    private GradeDao gradeDao;
    
    /** Holds SubjectDao instance. */
    @Autowired
    private SubjectDao subjectDao;
    
    /** Holds SchoolClassDao instance. */
    @Autowired
    private SchoolClassDao schoolClassDao;
    
    /** Holds SubjectTeacher instance. */
    private SubjectTeacher subjectTeacher;
    
    /** Holds staff instance. */
    private Staff staff;
    
    /** Holds StaffCategory instance. */
    private StaffCategory staffCategory;
    
    /** Holds City instance. */
    private City city;
    
    /** Holds District instance. */
    private District district;
    
    /** Holds District instance. */
    private Province province;
    
    /** Holds GradeSubject instance. */
    private GradeSubject gradeSubject;
    
    /** Holds Grade instance. */
    private Grade grade;
    
    /** Holds Subject instance. */
    private Subject subject;
    
    /** Holds ClassGrade instance. */
    private ClassGrade classGrade;
    
    /** Holds SchoolClass instance. */
    private SchoolClass schoolClass;
    
    /** Holds SchoolClass instance. */
    private SchoolClass updatedSchoolClass;
    
    /** randonm string to . */
    private static final String RANDOM_STRING = String.valueOf(Math.round(Math.random() * 1000));
    
    /**
     * Setting up resources.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Before
    public final void setUp() throws AkuraAppException {
    
        // Instantiate a Province object
        Province newProvince = new Province();
        newProvince.setDescription("SubjectTeacherDaoImplTes" + RANDOM_STRING);
        province = provinceDao.save(newProvince);
        assertNotNull(" Province saved to db " + province);
        
        // Instantiate a District object
        District newDistrict = new District();
        newDistrict.setDescription("SubjectTeacherDaoImplTest" + RANDOM_STRING);
        newDistrict.setProvince(province);
        district = districtDao.save(newDistrict);
        assertNotNull(" District saved to db " + district);
        
        // Instantiate a city
        City newCity = new City();
        newCity.setDescription("SubjectTeacherDaoImplTest" + RANDOM_STRING);
        newCity.setDistrict(district);
        city = cityDao.save(newCity);
        assertNotNull(" City saved to db " + city);
        
        // Instantiate a staffCategory object.
        StaffCategory newStaffCategory = new StaffCategory();
        newStaffCategory.setAcademic(true);
        newStaffCategory.setDescription("SubjectTeacherDaoImplTest" + RANDOM_STRING);
        staffCategory = staffCategoryDao.save(newStaffCategory);
        assertNotNull(" StaffCategory saved to db " + staffCategory);
        
        // Instantiate a staff object.
        Staff newStaff = new Staff();
        newStaff.setStaffCategory(staffCategory);
        newStaff.setRegistrationNo("SubjectTeacherDaoImplTes" + RANDOM_STRING);
        newStaff.setDateOfHire(new Date());
        newStaff.setFullName("SubjectTeacherDaoImplTest FullName");
        newStaff.setLastName("SubjectTeacherDaoImplTest LastName");
        newStaff.setNationalID("SubjectTeacherDaoImplTest ID---++" + RANDOM_STRING);
        newStaff.setDateOfBirth(new Date());
        newStaff.setAddress("SubjectTeacherDaoImplTest Address");
        newStaff.setGender('M');
        newStaff.setCityId(city.getCityId());
        staff = staffDao.save(newStaff);
        assertNotNull(" Staff saved to db " + staff);
        
        // Instantiate a grade
        Grade newGrade = new Grade();
        newGrade.setDescription("SubjectTeacherDaoImplTest" + RANDOM_STRING);
        grade = gradeDao.save(newGrade);
        assertNotNull(" grade saved to db " + grade);
        
        // Instantiate a subject
        Subject newSubject = new Subject();
        newSubject.setDescription("SubjectTeacherDaoImplTest" + RANDOM_STRING);
        subject = subjectDao.save(newSubject);
        assertNotNull(" subject saved to db " + subject);
        
        // Instantiate a GradeSubject
        GradeSubject newGradeSubject = new GradeSubject();
        newGradeSubject.setGrade(grade);
        newGradeSubject.setSubject(subject);
        newGradeSubject.setSubjectCode("SubjectTeacherDaoImplTest" + RANDOM_STRING);
        gradeSubject = gradeSubjectDao.save(newGradeSubject);
        assertNotNull(" GradeSubject saved to db " + gradeSubject);
        
        // Instantiate a schoolClass
        SchoolClass newSchoolClass = new SchoolClass();
        newSchoolClass.setDescription("SubjectTeacherDaoImplTest" + RANDOM_STRING);
        schoolClass = schoolClassDao.save(newSchoolClass);
        assertNotNull(" SchoolClass saved to db " + schoolClass);
        
        // Instantiate a ClassGrade
        classGrade = new ClassGrade();
        classGrade.setGrade(grade);
        classGrade.setSchoolClass(schoolClass);
        classGrade.setDescription("Test Class Grade" + RANDOM_STRING);
        classGrade = classGradeDao.save(classGrade);
        
        // Instantiate a updatedSchoolClass
        SchoolClass newSchoolClass2 = new SchoolClass();
        newSchoolClass2.setDescription("SubjectTeacherDaoImplTest2" + RANDOM_STRING);
        updatedSchoolClass = schoolClassDao.save(newSchoolClass2);
        assertNotNull(" SchoolClass saved to db " + updatedSchoolClass);
        
        // Instantiate a SubjectTeacher
        subjectTeacher = new SubjectTeacher();
        subjectTeacher.setStaff(staff);
        subjectTeacher.setSchoolClass(schoolClass);
        subjectTeacher.setGradeSubject(gradeSubject);
        subjectTeacher.setYear(String.valueOf(YEAR_2011));
    }
    
    /**
     * Test method to check if a SubjectTeacher object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveSubjectTeacher() throws AkuraAppException {
    
        try {
            
            SubjectTeacher newSubjectTeacher = subjectTeacherDao.save(subjectTeacher);
            assertNotNull("New SubjectTeacher should not be null. " + newSubjectTeacher);
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
            
            List<SubjectTeacher> subjectTeacherList = new ArrayList<SubjectTeacher>();
            subjectTeacherList.add(subjectTeacher);
            
            subjectTeacherDao.saveOrUpdateAll(subjectTeacherList);
            List<SubjectTeacher> newSubjectTeacherList = subjectTeacherDao.findAll(SubjectTeacher.class);
            assertNotNull("SubjectTeacher list should not be null ", newSubjectTeacherList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check find the SubjectTeacher records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindSubjectTeacherById() throws AkuraAppException {
    
        try {
            
            SubjectTeacher newSubjectTeacher = subjectTeacherDao.save(subjectTeacher);
            assertNotNull("New SubjectTeacher should not be null " + newSubjectTeacher);
            
            SubjectTeacher findSubjectTeacher =
                    (SubjectTeacher) subjectTeacherDao.findById(SubjectTeacher.class,
                            newSubjectTeacher.getSubjectTeacherId());
            assertNotNull("find SubjectTeacher should not be null ", findSubjectTeacher);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the SubjectTeacher records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindAllSubjectTeacher() throws AkuraAppException {
    
        try {
            
            SubjectTeacher newSubjectTeacher = subjectTeacherDao.save(subjectTeacher);
            assertNotNull("New SubjectTeacher should not be null " + newSubjectTeacher);
            
            List<SubjectTeacher> subjectTeacherList = subjectTeacherDao.findAll(SubjectTeacher.class);
            assertNotNull("SubjectTeacher list should not be null ", subjectTeacherList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test case to test getSubjectTeacherList.
     * 
     * @throws AkuraAppException - AkuraAppException.
     */
    @Test
    public void testGetSubjectTeacherList() throws AkuraAppException {
    
        SubjectTeacher newSubjectTeacher = subjectTeacherDao.save(subjectTeacher);
        assertNotNull("New SubjectTeacher should not be null " + newSubjectTeacher);
        
        List<SubjectTeacher> subjectTeacherList =
                subjectTeacherDao.getSubjectTeacherList(newSubjectTeacher.getStaff().getLastName(), "");
        
        assertTrue(subjectTeacherList.size() > 0);
        
    }
    
    /**
     * test case to test deleteSubTeacher.
     * 
     * @throws AkuraAppException - AkuraAppException.
     */
    @Test
    public void testDeleteSubTeacher() throws AkuraAppException {
    
        SubjectTeacher newSubjectTeacher = subjectTeacherDao.save(subjectTeacher);
        assertNotNull("New SubjectTeacher should not be null " + newSubjectTeacher);
        
        subjectTeacherDao.deleteSubTeacher(newSubjectTeacher);
        SubjectTeacher checkSubjectTeacher =
                (SubjectTeacher) subjectTeacherDao.findById(SubjectTeacher.class,
                        newSubjectTeacher.getSubjectTeacherId());
        
        assertEquals(null, checkSubjectTeacher);
        subjectTeacherDao.save(subjectTeacher);
    }
    
    /**
     * test case to test deleteSubTeacher.
     * 
     * @throws AkuraAppException - AkuraAppException.
     */
    @Test
    public void testDeleteSubTeacher2() throws AkuraAppException {
    
        SubjectTeacher newSubjectTeacher = subjectTeacherDao.save(subjectTeacher);
        assertNotNull("New SubjectTeacher should not be null " + newSubjectTeacher);
        
        subjectTeacherDao.deleteSubTeacher(newSubjectTeacher.getStaff().getStaffId(), newSubjectTeacher
                .getGradeSubject().getGradeSubjectId(), newSubjectTeacher.getYear());
        SubjectTeacher checkSubjectTeacher =
                (SubjectTeacher) subjectTeacherDao.findById(SubjectTeacher.class,
                        newSubjectTeacher.getSubjectTeacherId());
        
        assertEquals(null, checkSubjectTeacher);
        subjectTeacherDao.save(subjectTeacher);
    }
    
    /**
     * test case to test getClassList.
     * 
     * @throws AkuraAppException - AkuraAppException.
     */
    @Test
    public void testGetClassList() throws AkuraAppException {
    
        SubjectTeacher newSubjectTeacher = subjectTeacherDao.save(subjectTeacher);
        assertNotNull("New SubjectTeacher should not be null " + newSubjectTeacher);
        
        List<Integer> classrList =
                subjectTeacherDao.getClassList(newSubjectTeacher.getStaff().getStaffId(), newSubjectTeacher
                        .getGradeSubject().getGradeSubjectId(), newSubjectTeacher.getYear());
        
        assertTrue(classrList.size() > 0);
    }
    
    /**
     * test case to test getSubjectTeachers.
     * 
     * @throws AkuraAppException - AkuraAppException.
     */
    @Test
    public void testGetSubjectTeachers() throws AkuraAppException {
    
        subjectTeacher = subjectTeacherDao.save(subjectTeacher);
        
        List<SubjectTeacher> list = subjectTeacherDao.getSubjectTeachers(classGrade, YEAR_2011);
        assertNotNull("List cannot be null", list);
        assertTrue("list should have one ele", !list.isEmpty());
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        try {
            if (subjectTeacher.getSubjectTeacherId() > 0) {
                subjectTeacherDao.delete(subjectTeacher);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
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
            if (gradeSubject.getGradeSubjectId() > 0) {
                gradeSubjectDao.delete(gradeSubject);
            }
            if (classGrade.getClassGradeId() > 0) {
                classGradeDao.delete(classGrade);
            }
            if (schoolClass.getClassId() > 0) {
                schoolClassDao.delete(schoolClass);
            }
            if (grade.getGradeId() > 0) {
                gradeDao.delete(grade);
            }
            if (subject.getSubjectId() > 0) {
                subjectDao.delete(subject);
            }
            if (updatedSchoolClass.getClassId() > 0) {
                schoolClassDao.delete(updatedSchoolClass);
            }
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        
    }
}
