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

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This test class, tests all the persistence related functionality for the SchoolClass domain object.
 * 
 * @author Virtusa Corporation
 */
public class SchoolClassDaoImplTest extends BaseCommonTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SchoolClassDao schoolClassDao;
    
    /**
     * Represents an instance of SchoolClass.
     */
    private SchoolClass classInstace;
    
    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {

        // instantiates SchoolClass object.
        classInstace = new SchoolClass();
        classInstace.setDescription("NewClass");
        classInstace.setModifiedTime(new Date());
    }
    
    /**
     * Test method for {@link com.virtusa.akura.common.dao.SchoolClassDaoImpl.
     * addClass(com.virtusa.akura.api.dto.SchoolClass)}
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testAddClass() throws AkuraAppException {

        try {
            SchoolClass addClass = schoolClassDao.save(classInstace);
            assertNotNull("SchoolClass Type object should not null ", addClass);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.akura.common.dao.SchoolClassDaoImpl.
     * updateClass(com.virtusa.akura.api.dto.SchoolClass)}
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdateClass() throws AkuraAppException {

        try {
            SchoolClass newClassInstace = schoolClassDao.save(classInstace);
            assertNotNull("SchoolClass Type object should not null ", newClassInstace);
            newClassInstace.setDescription("ModiiedNewClass");
            schoolClassDao.update(newClassInstace);
            SchoolClass findClass =
                    (SchoolClass) schoolClassDao.findById(SchoolClass.class, newClassInstace.getClassId());
            assertEquals("SchoolClass object has been modified successfully", newClassInstace.getDescription(),
                    findClass.getDescription());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.akura.common.dao.SchoolClassDaoImpl# getClassList()}.
     * 
     *@throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetClassList() throws AkuraAppException {

        try {
            SchoolClass newClassInstace = schoolClassDao.save(classInstace);
            assertNotNull("SchoolClass Type object should not null ", newClassInstace);
            List<SchoolClass> classList = schoolClassDao.findAll(SchoolClass.class);
            assertTrue("Received SchoolClass object list size cannot be zero",classList.size() > 0);
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
            schoolClassDao.delete(classInstace);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
