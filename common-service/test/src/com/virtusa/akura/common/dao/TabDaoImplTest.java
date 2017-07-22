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
import com.virtusa.akura.api.dto.Tab;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseCommonTest;

/**
 * TabDaoImplTest is to test all the persistent related functionalities for the TabDaoImpl object.
 * 
 * @author Virtusa Corporation
 */
public class TabDaoImplTest extends BaseCommonTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private TabDao tabDao;
    
    /**
     * Represents an instance of Tab.
     */
    private Tab tab;
    
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
        Module newmodule = new Module();
        newmodule.setName("TabDaoImplTest: TestModuleName");
        module = moduleDao.save(newmodule);
        assertNotNull("Module saved to db ", module);
        
        // Instantiates a Tab object.
        tab = new Tab();
        tab.setName("TabDaoImplTest: TestTabName");
        tab.setModule(module);
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
            Tab newTab = tabDao.save(tab);
            assertNotNull("Tab Type object should not null ", newTab);
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
            Tab newTab = tabDao.save(tab);
            assertNotNull(newTab);
            newTab.setName("TabDaoImplTest: New Test Tab Name");
            tabDao.update(newTab);
            Tab updateTab = (Tab) tabDao.findById(Tab.class, newTab.getTabId());
            assertEquals(newTab.getName(), updateTab.getName());
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
            List<Tab> tabList = new ArrayList<Tab>();
            tabList.add(tab);
            tabDao.saveOrUpdateAll(tabList);
            tabList = tabDao.findAll(Tab.class);
            assertTrue(tabList.size() > 0);
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
            Tab newTab = tabDao.save(tab);
            assertNotNull(newTab);
            Tab findTab = (Tab) tabDao.findById(Tab.class, newTab.getTabId());
            assertNotNull("Tab type object should not null ", findTab);
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
            Tab newTab = tabDao.save(tab);
            assertNotNull(newTab);
            List<Tab> tabList = tabDao.findAll(Tab.class);
            assertTrue(tabList.size() > 0);
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
            tabDao.delete(tab);
            moduleDao.delete(module);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
