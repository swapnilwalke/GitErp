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
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dto.Module;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseCommonTest;

/**
 * ModuleDaoImplTest is to test all the persistent related functionalities for the ModuleDaoImpl object.
 * 
 * @author Virtusa Corporation
 */
public class ModuleDaoImplTest extends BaseCommonTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private ModuleDao moduleDao;
    
    /**
     * Represents an instance of Module.
     */
    private Module module;
    
    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {

        // Instantiates a Module object.
        module = new Module();
        module.setName("ModuleDaoImplTest :TestModuleName");
    }
    
    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl# save(com.virtusa.akura.api.dto.BaseDomain)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSave() throws AkuraAppException {

        try {
            Module newModule = moduleDao.save(module);
            assertNotNull("Module Type object should not null ", newModule);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.api.dao.BaseDaoImpl# update(com.virtusa.akura.api.dto.BaseDomain)}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdate() throws AkuraAppException {

        try {
            Module newModule = moduleDao.save(module);
            assertNotNull(newModule);
            newModule.setName("ModuleDaoImplTest :New Test Module Name");
            moduleDao.update(newModule);
            Module updateModule = (Module) moduleDao.findById(Module.class, newModule.getModuleId());
            assertEquals(newModule.getName(), updateModule.getName());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl# saveOrUpdateAll(java.util.List)}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSaveOrUpdateAll() throws AkuraAppException {

        try {
            List<Module> moduleList = new ArrayList<Module>();
            moduleList.add(module);
            moduleDao.saveOrUpdateAll(moduleList);
            moduleList = moduleDao.findAll(Module.class);
            assertTrue(moduleList.size() > 0);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl# findById(java.lang.Class, int)}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindById() throws AkuraAppException {

        try {
            Module newModule = moduleDao.save(module);
            assertNotNull(newModule);
            Module findModule = (Module) moduleDao.findById(Module.class, newModule.getModuleId());
            assertNotNull("Module type object should not null ", findModule);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl# findAll(java.lang.Class)}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindAll() throws AkuraAppException {

        try {
            Module newModule = moduleDao.save(module);
            assertNotNull(newModule);
            List<Module> moduleList = moduleDao.findAll(Module.class);
            assertTrue(moduleList.size() > 0);
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
            moduleDao.delete(module);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
