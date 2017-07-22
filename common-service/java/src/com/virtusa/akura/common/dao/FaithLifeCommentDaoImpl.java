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

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.FaithLifeComment;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides all the persistence related operations for FaithLifeComment.
 *
 * @author Virtusa Corporation
 */

public class FaithLifeCommentDaoImpl extends BaseDaoImpl<FaithLifeComment> implements FaithLifeCommentDao {

    /** key to hold named query getFaithLifeCommentByCategory. */
    private static final String GET_FAITH_LIFE_COMMENT_BY_CATEGORY = "getFaithLifeCommentByCategory";

    /**
     * Retrieves the list of FaithLifeComments for given FaithLifeCategoryId.
     *
     * @param categoryId - the id of FaithLifeCategory object.
     * @return List - The list of FaithLifeComment.
     * @throws AkuraAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    public List<FaithLifeComment> findFaithLifeCommentByCategory(int categoryId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_FAITH_LIFE_COMMENT_BY_CATEGORY, categoryId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }

    }

}
