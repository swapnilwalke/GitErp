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

import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.DailyTeacherAttendance;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.attendance.BaseAttendanceTest;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.StaffCategoryDao;
import com.virtusa.akura.staff.dao.StaffDao;

/**
 * This test class tests all the functionalities of the DailyTeacherAttendanceDaoImpl class.
 * 
 * @author Virtusa Corporation
 */

public class DailyTeacherAttendanceDaoImplTest extends BaseAttendanceTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private DailyTeacherAttendanceDao dailyTeacherAttendanceDao;
    
    /** Holds dailyTeacherAttendance instance. */
    private DailyTeacherAttendance dailyTeacherAttendance;
    
    
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
    
    /** Holds District instance. */
    private Province province;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public void setUp() throws Exception {
        
        // Instantiate a Province object
        Province newProvince = new Province();
        newProvince.setDescription("DailyTeacherAttendanceDaoImplTest");
        province = provinceDao.save(newProvince);
        assertNotNull(" Province saved to db " + province);
        
        // Instantiate a District object
        District newDistrict = new District();
        newDistrict.setDescription("DailyTeacherAttendanceDaoImplTest");
        newDistrict.setProvince(province);
        district = districtDao.save(newDistrict);
        assertNotNull(" District saved to db " + district);
        
        // Instantiate a city
        City newCity = new City();
        newCity.setDescription("DailyTeacherAttendanceDaoImplTest");
        newCity.setDistrict(district);
        city = cityDao.save(newCity);
        assertNotNull(" City saved to db " + city);
        
        // Instantiate a staffCategory object.
        StaffCategory newStaffCategory = new StaffCategory();
        newStaffCategory.setAcademic(true);
        newStaffCategory.setDescription("DailyTeacherAttendanceDaoImplTest");
        staffCategory = staffCategoryDao.save(newStaffCategory);
        assertNotNull(" StaffCategory saved to db " + staffCategory);
        
        // Instantiate a staff object.
        Staff newStaff = new Staff();
        newStaff.setStaffCategory(staffCategory);
        newStaff.setRegistrationNo("DailyTeacherAttendanceDaoImplTest");
        newStaff.setDateOfHire(new Date());
        newStaff.setFullName("DailyTeacherAttendanceDaoImplTest");
        newStaff.setLastName("DailyTeacherAttendanceDaoImplTest");
        newStaff.setNationalID("DailyTeacherAttendanceDaoImplTest");
        newStaff.setDateOfBirth(new Date());
        newStaff.setAddress("DailyTeacherAttendanceDaoImplTest");
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
        
    }
    
    /**
     * Test method to check if a Add DailyTeacherAttendance object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testAddDailyTeacherAttendance() throws AkuraAppException {
    
        try {
            
            DailyTeacherAttendance newDailyTeacherAttendance =
                    this.dailyTeacherAttendanceDao.save(dailyTeacherAttendance);
            assertNotNull("New DailyTeacherAttendance object can not be null" + newDailyTeacherAttendance);
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method to check update and find by id DailyTeacherAttendance object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateDailyTeacherAttendance() throws AkuraAppException {
    
        try {
            
            DailyTeacherAttendance newDailyTeacherAttendance =
                    this.dailyTeacherAttendanceDao.save(dailyTeacherAttendance);
            assertNotNull("New DailyTeacherAttendance object can not be null" + newDailyTeacherAttendance);
            newDailyTeacherAttendance.setStaffId(2);
            dailyTeacherAttendanceDao.update(newDailyTeacherAttendance);
            DailyTeacherAttendance updatedDailyTeacherAttendance =
                    (DailyTeacherAttendance) this.dailyTeacherAttendanceDao.findById(DailyTeacherAttendance.class,
                            newDailyTeacherAttendance.getDailyTeacherAttendanceId());
            assertEquals(updatedDailyTeacherAttendance.getStaffId(), newDailyTeacherAttendance.getStaffId());
            
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method to check load all the DailyTeacherAttendance records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testViewAllStudentPrefect() throws AkuraAppException {
    
        try {
            DailyTeacherAttendance newDailyTeacherAttendance =
                    this.dailyTeacherAttendanceDao.save(dailyTeacherAttendance);
            assertNotNull("New DailyTeacherAttendance object can not be null" + newDailyTeacherAttendance);
            
            List<DailyTeacherAttendance> list = this.dailyTeacherAttendanceDao.findAll(DailyTeacherAttendance.class);
            assertNotNull(list.size());
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method to check findByTeacherId.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindByTeacherId() throws AkuraAppException {
    
        try {
            DailyTeacherAttendance newDailyTeacherAttendance =
                    this.dailyTeacherAttendanceDao.save(dailyTeacherAttendance);
            assertNotNull("New DailyTeacherAttendance object can not be null" + newDailyTeacherAttendance);
            
            List<DailyTeacherAttendance> list =
                    this.dailyTeacherAttendanceDao.findByTeacherId(newDailyTeacherAttendance.getStaffId(),
                            newDailyTeacherAttendance.getDate());
            assertNotNull(list.size());
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
    public final void testGetTeacherAttandanceList() throws AkuraAppException {
    
        try {
            DailyTeacherAttendance newDailyTeacherAttendance =
                    this.dailyTeacherAttendanceDao.save(dailyTeacherAttendance);
            assertNotNull("New DailyTeacherAttendance object can not be null" + newDailyTeacherAttendance);
            
            List<DailyTeacherAttendance> list =
                    this.dailyTeacherAttendanceDao.getTeacherAttandanceList(newDailyTeacherAttendance.getDate(), true);
            assertNotNull(list.size());
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method for loadDataIntoDatabase.
     *
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Test
    public final void testLoadDataIntoDatabase() throws AkuraAppException {
        Set<DailyTeacherAttendance> newDailyTeacherAttendance = new HashSet<DailyTeacherAttendance>();
        newDailyTeacherAttendance.add(dailyTeacherAttendance);
        dailyTeacherAttendanceDao.loadDataIntoDatabase(newDailyTeacherAttendance);
        DailyTeacherAttendance attendance = (DailyTeacherAttendance) dailyTeacherAttendanceDao.findById(
                DailyTeacherAttendance.class, dailyTeacherAttendance.getDailyTeacherAttendanceId());
        assertEquals(dailyTeacherAttendance.getDailyTeacherAttendanceId(), attendance.getDailyTeacherAttendanceId());
    }
    
    
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public final void teardown() throws AkuraAppException {
    
        try {
            dailyTeacherAttendanceDao.delete(dailyTeacherAttendance);
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
