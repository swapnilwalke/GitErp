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
import com.virtusa.akura.api.dto.Page;
import com.virtusa.akura.api.dto.Tab;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseCommonTest;

/**
 * PageDaoImplTest is to test all the persistent related functionalities for the PageDaoImpl object.
 * 
 * @author Virtusa Corporation
 */
public class PageDaoImplTest extends BaseCommonTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private PageDao pageDao;
    
    /**
     * Represents an instance of Page.
     */
    private Page page;
    
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
        newmodule.setName("PageDaoImplTest :TestModuleName");
        module = moduleDao.save(newmodule);
        assertNotNull("Module saved to db ", module);
        
        // Instantiates a Tab object.
        Tab newTab = new Tab();
        newTab.setName("PageDaoImplTest :TestTabName");
        newTab.setModule(module);
        tab = tabDao.save(newTab);
        assertNotNull("Tab saved to db ", tab);
        
        // Instantiates a Page object.
        page = new Page();
        page.setName("PageDaoImplTest :TestPageName");
        page.setUrl("PageDaoImplTest :Test URL");
        page.setTab(tab);
        
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
            Page newPage = pageDao.save(page);
            assertNotNull("Page Type object should not null ", newPage);
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
            Page newPage = pageDao.save(page);
            assertNotNull(newPage);
            newPage.setName("PageDaoImplTest :New Test Page Name");
            pageDao.update(newPage);
            Page updatePage = (Page) pageDao.findById(Page.class, newPage.getPageId());
            assertEquals(newPage.getName(), updatePage.getName());
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
            List<Page> pageList = new ArrayList<Page>();
            pageList.add(page);
            pageDao.saveOrUpdateAll(pageList);
            pageList = pageDao.findAll(Page.class);
            assertTrue(pageList.size() > 0);
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
            Page newPage = pageDao.save(page);
            assertNotNull(newPage);
            Page findPage = (Page) pageDao.findById(Page.class, newPage.getPageId());
            assertNotNull("Page type object should not null ", findPage);
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
            Page newPage = pageDao.save(page);
            assertNotNull(newPage);
            List<Page> pageList = pageDao.findAll(Page.class);
            assertTrue(pageList.size() > 0);
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
            pageDao.delete(page);
            tabDao.delete(tab);
            moduleDao.delete(module);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
