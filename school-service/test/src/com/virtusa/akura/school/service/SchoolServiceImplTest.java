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
package com.virtusa.akura.school.service;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.School;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.StaffCategoryDao;
import com.virtusa.akura.school.BaseSchoolTest;
import com.virtusa.akura.school.dao.SchoolDao;
import com.virtusa.akura.staff.dao.StaffDao;

/**
 * This test class, tests all the service related functionality for the
 * School, SchoolClass, Subject and Stream domain objects.
 *
 * @author Virtusa corporation
 */
public class SchoolServiceImplTest extends BaseSchoolTest {

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SchoolService schoolService;

    /**
     * Instantiates a date object.
     */
    private Date date = new Date();

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private DistrictDao districtDao;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private ProvinceDao provinceDao;

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private CityDao cityDao;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SchoolDao schoolDao;

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private StaffCategoryDao staffCategoryDao;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private StaffDao staffDao;

    /**
     * Represents an instance of Province.
     */
    private Province province;
    
    /**
     * Represents an instance of District.
     */
    private District district;

    /**
     * Represents an instance of City.
     */
    private City city;
    
    /**
     * Represents an instance of School.
     */
    private School school;
    
    /**
     * Represents an instance of School.
     */
    private StaffCategory staffCategory;

    /**
     * Represents an instance of School.
     */
    private Staff staff;
    
    
    /**
     * This method initialize all domain objects required for test methods.
     * 
     * @throws Exception - the exception that occurred.
     */
    @Before
    public final void setUp() throws Exception {

        // Instantiate a Province object
        Province newProvince = new Province();
        newProvince.setDescription("prov");
        province = provinceDao.save(newProvince);
        assertNotNull(" Province saved to db " + province);
        
        // Instantiate a District object
        District newDistrict = new District();
        newDistrict.setDescription("dist");
        newDistrict.setProvince(province);
        district = districtDao.save(newDistrict);
        assertNotNull(" District saved to db " + district);
        
        // Instantiate a city
        City newCity = new City();
        newCity.setDescription("cit");
        newCity.setDistrict(district);
        city = cityDao.save(newCity);
        assertNotNull(" City saved to db " + city);
        
        // Instantiate a staffCategory object.
        StaffCategory newStaffCategory = new StaffCategory();
        newStaffCategory.setAcademic(true);
        newStaffCategory.setDescription("principleTest");
        staffCategory = staffCategoryDao.save(newStaffCategory);
        assertNotNull(" StaffCategory saved to db " + staffCategory);
        
        // Instantiate a staff object.
        Staff newStaff = new Staff();
        newStaff.setStaffCategory(staffCategory);
        newStaff.setRegistrationNo("4563257");
        newStaff.setDateOfHire(new Date());
        newStaff.setFullName("FullNameTest");
        newStaff.setLastName("LastNameTest");
        newStaff.setNationalID("657845673V");
        newStaff.setDateOfBirth(new Date());
        newStaff.setAddress("StaffDaoImplTest Address");
        newStaff.setGender('M');
        newStaff.setCityId(city.getCityId());
        staff = staffDao.save(newStaff);
        assertNotNull(" StaffCategory saved to db " + staffCategory);

        // Instantiates a School instance;
        school = new School();
        school.setName("smsSchool");
        school.setAddress("smsSchool");
        school.setContactNo("0778563251");
        school.setFaxNo("0778563251");
        school.setWebsite("virtusa.lk");
        school.setStartedDate(date);
        school.setDistrict(district);
        school.setProvince(province);
        school.setPrincipalId(staff.getStaffId());
        school.setVicePrincipalId(Integer.valueOf(staff.getStaffId()));
        school.setModifiedTime(date);

        
    }

    /**
     * Test method for
     * {@link com.virtusa.sms.school.service. SchoolServiceImpl#
     * addSchool(com.virtusa.akura.api.dto.School)}.
     *
     * @throws Exception - the exception that occurred.
     */
    @Test
    public final void testAddSchool() throws Exception {

        try {
            
            School newSchool = schoolService.addSchool(school);
            assertNotNull("School Type object should not null ", newSchool);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for
     * {@link com.virtusa.sms.school. service.SchoolServiceImpl#
     * updateSchool(com.virtusa.akura.api.dto.School)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdateSchool() throws AkuraAppException {

        try {
            School newSchool = schoolService.addSchool(school);
            assertNotNull(newSchool);
            newSchool.setContactNo("0145785545");
            schoolService.updateSchool(newSchool);
            School findSchool = schoolService.findSchool(school.getSchoolId());
            assertEquals(newSchool.getContactNo(), findSchool.getContactNo());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.school.service.SchoolServiceImpl#
     * findSchool(java.lang.Integer)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindSchool() throws AkuraAppException {

        try {
            School newSchool = schoolService.addSchool(school);
            assertNotNull(newSchool);
            School findSchool = schoolService.findSchool(
                    newSchool.getSchoolId());
            assertNotNull("School Type object should not null ", findSchool);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.school.service.SchoolServiceImpl#
     * getSchoolList()}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetSchoolList() throws AkuraAppException {

        try {
            School newSchool = schoolService.addSchool(school);
            assertNotNull(newSchool);
            List<School> schoolList = schoolService.getSchoolList();
            assertTrue(schoolList.size() > 0);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tear down method for each test case.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public final void teardown() throws AkuraAppException {

        try {
            schoolDao.delete(school);
            staffDao.delete(staff);
            staffCategoryDao.delete(staffCategory);
            cityDao.delete(city);
            districtDao.delete(district);
            provinceDao.delete(province);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
