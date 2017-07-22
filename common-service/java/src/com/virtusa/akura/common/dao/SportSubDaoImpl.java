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

import java.util.List;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.EducationalQualification;
import com.virtusa.akura.api.dto.SportSub;


/**
 * This class provides persistence related functionality for the sport sub
 * entity.
 *
 * @author Virtusa Corporation
 *
 */

public class SportSubDaoImpl extends BaseDaoImpl<SportSub> implements SportSubDao{

	/** String Constant for holding respective query name. */
	private static final String GET_SPORTSUB_BY_DESCRIPTION = "getSportSubByDescription";
	
	/**
     * Method is to retrieve SportSub for given description.
     *
     * @param sportSubName - sportSubName
     * @return SportSub for respective userName.
     */
	@SuppressWarnings("unchecked")
	public SportSub getAnySportSubByDes(String sportSubName) {
		List<SportSub> sportSubs =
	        getHibernateTemplate().findByNamedQuery(GET_SPORTSUB_BY_DESCRIPTION,
	                new Object[] { sportSubName});

		SportSub sportSub = null;

	    if(sportSubs != null && !sportSubs.isEmpty()){
	    	sportSub = sportSubs.get(0);
	        
	    }
	    return sportSub;
	}

}
