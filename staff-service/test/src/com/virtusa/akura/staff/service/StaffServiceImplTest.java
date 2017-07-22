/*
 * © 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND

 * PROPRIETARY INFORMATION The information contained herein (the

 * 'Proprietary Information') is highly confidential and proprietary to and

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published,

 * communicated, disclosed or divulged to any person, firm, corporation or

 * other legal entity, directly or indirectly, without the prior written

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.staff.service;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.StaffCategoryDao;
import com.virtusa.akura.staff.BaseStaffTest;
import com.virtusa.akura.staff.dao.StaffDao;
import com.virtusa.akura.staff.dao.StaffLeaveDao;


/**
 * This test class, tests all the functionalities for the StaffServiceImpl.
 *
 * @author Virtusa Corporation
 */
public class StaffServiceImplTest extends BaseStaffTest {

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private StaffService staffService;

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

    /** Holds StaffLeaveDao instance. */
    @Autowired
    private StaffLeaveDao staffLeaveDao;

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

    /** Holds StaffLeave instance. */
    private StaffLeave staffLeave;

    /**
     * Setting up resources.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Before
    public final void setUp() throws AkuraAppException {

        // Instantiate a Province object
        Province newProvince = new Province();
        newProvince.setDescription("StaffServiceImplTest Description test");
        newProvince.setModifiedTime(new Date());
        province = provinceDao.save(newProvince);
        assertNotNull(" Province saved to db " + province);

        // Instantiate a District object
        District newDistrict = new District();
        newDistrict.setDescription("StaffServiceImplTest Descrition test");
        newDistrict.setProvince(province);
        newDistrict.setModifiedTime(new Date());
        district = districtDao.save(newDistrict);
        assertNotNull(" District saved to db " + district);

        // Instantiate a city
        City newCity = new City();
        newCity.setDescription("StaffServiceImplTest Description test");
        newCity.setDistrict(district);
        newCity.setModifiedTime(new Date());
        city = cityDao.save(newCity);
        assertNotNull(" City saved to db " + city);

        // Instantiate a staffCategory object.
        StaffCategory newStaffCategory = new StaffCategory();
        newStaffCategory.setAcademic(true);
        newStaffCategory.setDescription("StaffServiceImplTest Description test");
        newStaffCategory.setModifiedTime(new Date());
        staffCategory = staffCategoryDao.save(newStaffCategory);
        assertNotNull(" StaffCategory saved to db " + staffCategory);

        // Instantiate a staff object.
        staff = new Staff();
        staff.setStaffCategory(staffCategory);
        staff.setRegistrationNo("StaffServiceImplTest RegNo");
        staff.setDateOfHire(new Date());
        staff.setFullName("StaffServiceImplTest FullName test");
        staff.setLastName("StaffServiceImplTest LastName test");
        staff.setNationalID("StaffServiceImplTest ID");
        staff.setDateOfBirth(new Date());
        staff.setAddress("StaffServiceImplTest Description");
        staff.setGender('M');
        staff.setCityId(city.getCityId());
        staff.setModifiedTime(new Date());

        staffLeave = new StaffLeave();
        staffLeave.setStaffId(2);
        staffLeave.setFromDate(new Date());
        staffLeave.setToDate(new Date());
        staffLeave.setReason("New Reason test");
        staffLeave.setModifiedTime(new Date());
        staffLeave.setNoOfDays(0.0f);

    }

    /**
     * Test case to test isValidRegistrationNo Search.
     *
     * @throws AkuraAppException
     * @throws AkuraAppException SMSExceptions
     */
    @Test
    public void testIsValidRegistrationNo() throws AkuraAppException {

        Staff newStaff = staffDao.save(staff);
        assertNotNull("New Staff should not be null " + newStaff);

        List<Staff> staffList = staffService.searchStaff(staff);
        assertEquals(1, staffList.size());

        boolean isValidRegistrationNo = staffService.isValidRegistrationNo(newStaff.getRegistrationNo());
        assertEquals(true, isValidRegistrationNo);
    }

    /**
     * Test case to test add staff leave.
     *
     * @throws AkuraAppException SMSExceptions
     */
    @Test
    public void testAddStaffLeave() throws AkuraAppException {

        StaffLeave newStaffLeave = staffLeaveDao.save(staffLeave);
        assertNotNull("newStaffLeave should not be null " + newStaffLeave);
    }

    /**
     * Test case to test update staff leave.
     *
     * @throws AkuraAppException SMSExceptions
     */
    @Test
    public void testUpdateStaffLeave() throws AkuraAppException {

        StaffLeave newStaffLeave = staffLeaveDao.save(staffLeave);
        assertNotNull("newStaffLeave should not be null " + newStaffLeave);

        newStaffLeave.setReason("Updated reason");
        staffLeaveDao.update(newStaffLeave);
        StaffLeave updatedStaffLeave =
                (StaffLeave) staffLeaveDao.findById(StaffLeave.class, newStaffLeave.getStaffLeaveId());
        assertEquals("Updated reason", updatedStaffLeave.getReason());
    }
    /**
     * Test method to check Staff leave records by staff id from the db for given
     * staff ID.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindStaffLeaveByStaffId() throws AkuraAppException {

        StaffLeave newStaffLeave = staffLeaveDao.save(staffLeave);
        assertNotNull("newStaffLeave should not be null " + newStaffLeave);

        List<StaffLeave> staffLeaves = staffLeaveDao.findStaffLeaveByStaffId(newStaffLeave.getStaffId());
        assertNotNull(staffLeaves.size());
    }
    
    /**
     * Test method to check Staff leave records by staff id and date period from the db for given
     * staff ID.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindStaffLeaveByDatePeriodAndStaffId() throws AkuraAppException {

        StaffLeave newStaffLeave = staffLeaveDao.save(staffLeave);
        assertNotNull("newStaffLeave should not be null " + newStaffLeave);

        List<StaffLeave> staffLeaves = staffLeaveDao.findStaffLeaveByDatePeriodAndStaffId(
                newStaffLeave.getFromDate(), newStaffLeave.getToDate(), newStaffLeave.getStaffId());
        assertNotNull(staffLeaves.size());
    }

    /**
     * Tear down method for each test case.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {

        try {

            staffDao.delete(staff);
            staffCategoryDao.delete(staffCategory);

            cityDao.delete(city);
            districtDao.delete(district);
            provinceDao.delete(province);

            staffLeaveDao.delete(staffLeave);

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }

    }

}
