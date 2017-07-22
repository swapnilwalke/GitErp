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

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.dto.SpecialEventsParticipation;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the working SpecialEventsParticipation object.
 * 
 * @author Virtusa Corporation
 */
public class SpecialEventsParticipationDaoImpl extends BaseDaoImpl<SpecialEventsParticipation> implements
        SpecialEventsParticipationDao {
    
    /** attribute for holding string getParticipantListBySpecialEvent. */
    private static final String GET_PARTICIPANT_LIST_BY_SPECIAL_EVENT = "getParticipantListBySpecialEvent";

    /** attribute for holding string getSpecialEventParticipationByEventId. */
    private static final String GET_SPECIAL_EVENT_PARTICIPATION_BY_EVENT_ID = "getSpecialEventParticipationByEventId";
    
    /** attribute for holding string getSpecialEventParticipationListById. */
    private static final String GET_SPECIAL_EVENT_PARTICIPATION_LIST_BY_ID = "getSpecialEventParticipationListById";
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<SpecialEventsParticipation> getSpecialEventParticipationListById(int selectSpecialEvent)
            throws AkuraAppException {
    
        try {
            return (List<SpecialEventsParticipation>) getHibernateTemplate().findByNamedQuery(
                    GET_SPECIAL_EVENT_PARTICIPATION_LIST_BY_ID, selectSpecialEvent);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * Retrieve SpecialEventsParticipation list for given special event.
     * 
     * @param selectSpecialEvent of type int
     * @return list of SpecialEventsParticipation objects
     * @throws AkuraAppException throws if exception occurs.
     */
    @SuppressWarnings("unchecked")
    public List<SpecialEventsParticipation> getSpecialEventsParticipationsList(int selectSpecialEvent)
            throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_SPECIAL_EVENT_PARTICIPATION_BY_EVENT_ID,
                    selectSpecialEvent);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    
    /**
     * Retrieve SpecialEventsParticipation list for given special event.
     * 
     * @param specialEvent of type SpecialEvents
     * @return list of SpecialEventsParticipation objects
     * @throws AkuraAppException throws if exception occurs.
     */
    @SuppressWarnings("unchecked")
    public List<SpecialEventsParticipation> getParticipantListBySpecialEvent(SpecialEvents specialEvent)
            throws AkuraAppException{
        
        try {
            return getHibernateTemplate().findByNamedQuery(GET_PARTICIPANT_LIST_BY_SPECIAL_EVENT, specialEvent);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    public void deleteAllSpecialEventsParticipation(List<SpecialEventsParticipation> specialEventsParticipationList)
            throws AkuraAppException {
    
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            tx.begin();
            for (SpecialEventsParticipation participation : specialEventsParticipationList) {
                getHibernateTemplate().delete(participation);
            }
            tx.commit();

        } catch (DataAccessException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }finally {
            if(session!=null && session.isOpen()){
                session.close();
            }
        }
        
    }
    
    
}
