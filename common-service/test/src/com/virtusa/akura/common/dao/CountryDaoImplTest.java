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

package com.virtusa.akura.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseCommonTest;

/**
 * This test class, tests all the persistence related functionality for the
 * Country domain object.
 *
 * @author adesaram
 */

public class CountryDaoImplTest extends BaseCommonTest {
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private CountryDao countryDao;

    /**
     * Instantiates a date.
     */
    private Date date = new Date();

    /**
     * Represents an instance of Country.
     */
    private Country country;

    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {
        
        // Instantiates a Country object.
        country = new Country();
        country.setCountryName("Burundi123456");
        country.setModifiedTime(date);
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
            Country newCountry = countryDao.save(country);
            assertNotNull("Country Type object should not null ", newCountry);
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
            Country newCountry = countryDao.save(country);
            assertNotNull(newCountry);
            Country findCountry = (Country) countryDao.findById(
                    Country.class, newCountry.getCountryId());
            assertNotNull("Country Type object should not null ", findCountry);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.school.dao.CountryDaoImpl#
     * searchCountry(java.lang.String)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSearchCountry() throws AkuraAppException {

        try {
            Country newCountry = countryDao.save(country);
            assertNotNull(newCountry);
            List<Country> countryList = countryDao.searchCountry(newCountry.getCountryName());
            assertNotNull(countryList.size() > 0);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#
     * update(com.virtusa.akura.api.dto.BaseDomain)}
     * .
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdate() throws AkuraAppException {

        try {
            Country newCountry = countryDao.save(country);
            assertNotNull(newCountry);
            newCountry.setCountryName("Bahamasuuuuuuuu");
            countryDao.update(newCountry);
            Country findCountry = (Country) countryDao.findById(Country.class,
                    newCountry.getCountryId());
            assertEquals(newCountry.getCountryName(), findCountry.getCountryName());
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
            List<Country> countryList = new ArrayList<Country>();
            countryList.add(country);
            countryDao.saveOrUpdateAll(countryList);
            assertNotNull(country);
            List<Country> updateCountryList = countryDao.findAll(Country.class);
            assertTrue(updateCountryList.size() > 0);
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
            Country newCountry = countryDao.save(country);
            assertNotNull(newCountry);
            List<Country> countryList = countryDao.findAll(Country.class);
            assertTrue(countryList.size() > 0);
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
            countryDao.delete(country);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

}
