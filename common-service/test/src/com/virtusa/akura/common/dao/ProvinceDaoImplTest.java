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
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This test class, tests all the persistence related functionality for the
 * Province domain object.
 *
 * @author adesaram
 */
public class ProvinceDaoImplTest extends BaseCommonTest {

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private ProvinceDao provinceDao;

    /**
     * Represents an instance of Province.
     */
    private Province province;

    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {

        // Instantiate a Province object.
        province = new Province();
        province.setDescription("Southern");
        province.setModifiedTime(new Date());
    }

    /**
     * Test method for {@link com.virtusa.sms.school.dao.ProvinceDaoImpl#
     * searchProvince(java.lang.String)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSearchProvince() throws AkuraAppException {

        try {
            Province newProvince = provinceDao.save(province);
            assertNotNull(newProvince);
            List<Province> provinceList = provinceDao.searchProvince(
                    newProvince.getDescription());
            assertTrue(provinceList.size() > 0);
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
            Province newProvince = provinceDao.save(province);
            assertNotNull("Province Type object should not null ",
                    newProvince);
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
            Province newProvince = provinceDao.save(province);
            assertNotNull(newProvince);
            newProvince.setDescription("East");
            provinceDao.update(newProvince);
            Province findProvince = (Province) provinceDao.findById(
                    Province.class, newProvince.getProvinceId());
            assertEquals(newProvince.getDescription() ,
                    findProvince.getDescription());
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
            List<Province> provinceList = new ArrayList<Province>();
            provinceList.add(province);
            provinceDao.saveOrUpdateAll(provinceList);
            List<Province> searchProvinceList = provinceDao.findAll(
                    Province.class);
            assertTrue(searchProvinceList.size() > 0);
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
            Province newProvince = provinceDao.save(province);
            assertNotNull(newProvince);
            Province findProvince = (Province) provinceDao.findById(
                    Province.class, newProvince.getProvinceId());
            assertNotNull("Province Type object should not null ",
                    findProvince);
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
            Province newProvince = provinceDao.save(province);
            assertNotNull(newProvince);
            List<Province> provinceList = provinceDao.findAll(Province.class);
            assertTrue(provinceList.size() > 0);
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
            provinceDao.delete(province);
        } catch (AkuraAppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
