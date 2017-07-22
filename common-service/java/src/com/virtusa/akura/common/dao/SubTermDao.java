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

import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.dto.SubTerm;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * SubTermDao interface provides persistent related functionalities for sub term
 * object.
 *
 * @author Virtusa Corporation
 *
 */
public interface SubTermDao extends BaseDao<SubTerm> {

    /**
     * Get list of sub terms of a term.
     *
     * @param termId - termId id.
     * @return a list of  SubTerm.
     * @throws AkuraAppException throws if exception occurs when
     */
    List<SubTerm> getSubTermsOfATerm(int termId)throws AkuraAppException;
    
    /**
     * Retrieve the subTerm by passing the subTermName and termId.
     * @param termId - Integer
     * @param subTermName - String
     * @return SubTerm object.
     * @throws AkuraAppException throws the detailed exception.
     */
     SubTerm getAnySubTermByName(int termId,String subTermName) throws AkuraAppException;
     
     /**
      * Retrieve the subTerm by passing the toDate.
      * 
      * @param toDate - Date
      * @return SubTerm object.
      * @throws AkuraAppException throws the detailed exception.
      */
      SubTerm getAnySubTermByToDate(Date toDate) throws AkuraAppException;
      
      /**
       * Retrieve the subTerm by passing the fromDate.
       * 
       * @param fromDate - Date
       * @return SubTerm object.
       * @throws AkuraAppException throws the detailed exception.
       */
       SubTerm getAnySubTermFromDate(Date fromDate) throws AkuraAppException;

}
