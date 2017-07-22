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

import com.virtusa.akura.api.dao.BaseDao;
import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * TermDao interface provides persistent related functionalities for term
 * object.
 *
 * @author Virtusa Corporation
 *
 */
public interface TermDao extends BaseDao<Term> {
    /**
     * Retrieve the term by passing the term's termName.This returns any term with the term passed.
     * 
     * @param termName - String
     * @param startDate start date
     * @param endDate end date
     * @return Term object.
     * @throws AkuraAppException SMS Exceptions.
     */
    Term getTermByName(String termName, Date startDate, Date endDate) throws AkuraAppException;

    /**
     * Retrieves the count of the terms.
     * 
     * @return - the number of the terms.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Long> getNoOfTerms()throws AkuraAppException;
}
