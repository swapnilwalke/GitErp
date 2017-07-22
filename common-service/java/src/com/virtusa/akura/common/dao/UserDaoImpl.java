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

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.UserLogin;

/**
 * Implementation for UserDao.
 * 
 * @author Virtusa Corporation
 */
public class UserDaoImpl extends BaseDaoImpl<UserLogin> implements UserDao {


    /** String Constant for holding respective query name. */
    private static final String GET_USER_BY_NAME = "getUserByName";

    /** String Constant for holding respective query name. */
    private static final String GET_ANY_USER_BY_NAME = "getAnyUserByName";


    /**
     * Method is to retrieve UserLogin for given userName.
     *
     * @param userName - userName
     * @return userLogin for respective userName.
     */
    @SuppressWarnings("unchecked")
    public UserLogin getUserLoginByName(String userName){

    List<UserLogin> userLoginList =
        getHibernateTemplate().findByNamedQuery(GET_USER_BY_NAME,
                new Object[] { userName});

    UserLogin userLogin = null;

    if(!userLoginList.isEmpty()){
        userLogin = userLoginList.get(0);
        getSession().update(userLogin);
    }
    return userLogin;
    }
    
    /**
     * Method is to retrieve any UserLogin for given userName.
     *
     * @param userName - userName
     * @return userLogin for respective userName.
     */
    @SuppressWarnings("unchecked")
    public UserLogin getAnyUserLoginByName(String userName){
        
        List<UserLogin> userLoginList =
            getHibernateTemplate().findByNamedQuery(GET_ANY_USER_BY_NAME,
                    new Object[] { userName});
        
        UserLogin userLogin = null;
        
        if(!userLoginList.isEmpty()){
            userLogin = userLoginList.get(0);
            getSession().update(userLogin);
        }
        return userLogin;
    }



}
