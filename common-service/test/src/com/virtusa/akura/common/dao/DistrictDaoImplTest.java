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

package com.virtusa.akura.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This test class, tests all the persistence related functionality for the
 * District domain object.
 *
 * @author adesaram
 */
public class DistrictDaoImplTest extends BaseCommonTest {

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private ProvinceDao provinceDao;

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private DistrictDao districtDao;

    /**
     * Instantiates a date.
     */
    private Date date = new Date();

    /**
     * Represents an instance of Province.
     */
    private Province province;

    /**
     * Represents an instance of District.
     */
    private District district;

    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {

        // Instantiate a Province object.
        province = new Province();
        province.setDescription("SoudfdthernDDD");
        province.setModifiedTime(date);
        province = provinceDao.save(province);
        assertNotNull(province);
        // Instantiates a District object.
        district = new District();
        district.setDescription("dfdDDD");
        district.setModifiedTime(date);
        district.setProvince(province);
    }

    /**
     * Test method for {@link com.virtusa.sms.school.dao.DistrictDaoImpl#
     * searchDistrict(java.lang.String)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSearchDistrict() throws AkuraAppException {

        try {
            District newDistrict = districtDao.save(district);
            assertNotNull(newDistrict);
            List<District> districtList = districtDao.searchDistrict(
                    newDistrict.getDescription());
            assertTrue(districtList.size() > 0);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#
     * save(com.virtusa.akura.api.dto.BaseDomain)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSave() throws AkuraAppException {

        try {
            District newDistrict = districtDao.save(district);
            assertNotNull("District Type object should not null ", newDistrict);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#
     * update(com.virtusa.akura.api.dto.BaseDomain)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdate() throws AkuraAppException {

        try {
            District newDistrict = districtDao.save(district);
            assertNotNull(newDistrict);
            newDistrict.setDescription("Mathara");
            districtDao.update(newDistrict);
            District findDistrict = (District) districtDao.findById(
                    District.class, newDistrict.getDistrictId());
            assertEquals(newDistrict.getDescription(),
                    findDistrict.getDescription());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#
     * saveOrUpdateAll(java.util.List)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSaveOrUpdateAll() throws AkuraAppException {

        try {
            District d = new District();
            d.setDescription("6dfDDD");
            d.setModifiedTime(date);
            d.setProvince(province);
            List<District> districtList = new ArrayList<District>();
            districtList.add(district);
            districtList.add(d);
            districtDao.saveOrUpdateAll(districtList);
            List<District> updateDistrictList = districtDao.findAll(
                    District.class);
            assertTrue(updateDistrictList.size() > 0);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#
     * findById(java.lang.Class, int)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindById() throws AkuraAppException {

        try {
            District newDistrict = districtDao.save(district);
            assertNotNull(newDistrict);
            District findDistrict = (District) districtDao.findById(
                    District.class, newDistrict.getDistrictId());
            assertNotNull("District Type object should not null ",
                    findDistrict);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#
     * findAll(java.lang.Class)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindAll() throws AkuraAppException {

        try {
            District newDistrict = districtDao.save(district);
            assertNotNull(newDistrict);
            List<District> districtList = districtDao.findAll(District.class);
            assertTrue(districtList.size() > 0);
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
    public final void teardown() throws AkuraAppException {

        try {
            districtDao.delete(district);
            provinceDao.delete(province);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
