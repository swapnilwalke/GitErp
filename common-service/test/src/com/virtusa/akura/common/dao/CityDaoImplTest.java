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
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This test class, tests all the persistence related functionality for the
 * City domain object.
 *
 * @author adesaram
 */
public class CityDaoImplTest extends BaseCommonTest {

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private CityDao cityDao;

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
     * Instantiates a date.
     */
    private Date date = new Date();

    /**
     * Represents an instance of City.
     */
    private City city;

    /**
     * Represents an instance of District.
     */
    private District district;

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
        province.setDescription("WesternDDD");
        province.setModifiedTime(date);
        province = provinceDao.save(province);
        
        // Instantiates a District object.
        district = new District();
        district.setDescription("GampahaDDD");
        district.setModifiedTime(date);
        district.setProvince(province);
        district = districtDao.save(district);
        
        // Instantiates a City object.
        city = new City();
        city.setDescription("RathmalanaDDD");
        city.setModifiedTime(date);
        city.setDistrict(district);
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
            City newCity = cityDao.save(city);
            assertNotNull("City Type object should not null ", newCity);
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
            City newCity = cityDao.save(city);
            assertNotNull(newCity);
            City findCity = (City) cityDao.findById(
                    City.class, newCity.getCityId());
            assertNotNull("City Type object should not null ", findCity);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.school.dao.CityDaoImpl#
     * searchCity(java.lang.String)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSearchCity() throws AkuraAppException {

        try {
            City newCity = cityDao.save(city);
            assertNotNull(newCity);
            List<City> cityList = cityDao.searchCity(newCity.getDescription());
            assertNotNull(cityList.size() > 0);
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
            City newCity = cityDao.save(city);
            assertNotNull(newCity);
            newCity.setDescription("KadawathaDDD");
            cityDao.update(newCity);
            City findCity = (City) cityDao.findById(City.class,
                    newCity.getCityId());
            assertEquals(newCity.getDescription(), findCity.getDescription());
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
            List<City> cityList = new ArrayList<City>();
            cityList.add(city);
            cityDao.saveOrUpdateAll(cityList);
            assertNotNull(city);
            List<City> updateCityList = cityDao.findAll(City.class);
            assertTrue(updateCityList.size() > 0);
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
            City newCity = cityDao.save(city);
            assertNotNull(newCity);
            List<City> cityList = cityDao.findAll(City.class);
            assertTrue(cityList.size() > 0);
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
            cityDao.delete(city);
            districtDao.delete(district);
            provinceDao.delete(province);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
