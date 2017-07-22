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

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.api.dto.PrefectType;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */

public class PrefectTypeDaoImplTest extends BaseCommonTest {
    
    /**
     * Holds prefectTypeDao instance of {@link PrefectTypeDao}.
     */
    @Autowired
    private PrefectTypeDao prefectTypeDao;
    
    /**
     * Holds prefectType instance of {@link PrefectType}.
     */
    private PrefectType prefectType;
    
    /**
     * Instantiate the set up method.
     * 
     * @throws java.lang.Exception {@link Exception}
     */
    @Before
    public final void setUp() throws Exception {

        prefectType = new PrefectType();
        prefectType.setDescription("Head Prefect");
    }
    
    /**
     * Test method for save PrefectType.
     */
    @Test
    public final void testAddPrefectType() {

        try {
            PrefectType addPrefectType = this.prefectTypeDao.save(prefectType);
            assertNotNull(" Added PrefectType object can not be null ", addPrefectType);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for update Position. This test method covers test for get Position by id too.
     */
    @Test
    public final void testModifyPosition() {

        try {
            PrefectType updatePrefectType = this.prefectTypeDao.save(prefectType);
            assertNotNull(" Added PrefectType object can not be null ", updatePrefectType);
            
            updatePrefectType.setDescription("Deputy Head Prefect");
            this.prefectTypeDao.update(updatePrefectType);
            
            PrefectType findPrefectType =
                    (PrefectType) prefectTypeDao.findById(PrefectType.class, updatePrefectType.getPrefectTypeId());
            assertEquals("PrefectType object has been modified successfully", updatePrefectType.getDescription(),
                    findPrefectType.getDescription());
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for get all the available PrefectType.
     */
    @Test
    public final void testGetPositions() {

        try {
            PrefectType addPrefectType = this.prefectTypeDao.save(prefectType);
            assertNotNull(" Added PrefectType object can not be null ", addPrefectType);
            
            List<PrefectType> prefectTypes = this.prefectTypeDao.findAll(PrefectType.class);
            assertTrue("Received PrefectType object list size cannot be zero", prefectTypes.size() > 0);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Tear down method for each test case. This handles delete Position.
     */
    @After
    public final void teardown() {

        try {
            this.prefectTypeDao.delete(prefectType);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
}
