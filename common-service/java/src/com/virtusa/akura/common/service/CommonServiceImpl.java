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

package com.virtusa.akura.common.service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.virtusa.akura.api.dto.Assignment;
import com.virtusa.akura.api.dto.AuditEventType;
import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ClubSociety;
import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.DonationType;
import com.virtusa.akura.api.dto.EmploymentStatus;
import com.virtusa.akura.api.dto.Exam;
import com.virtusa.akura.api.dto.ExamSubject;
import com.virtusa.akura.api.dto.FaithLifeCategory;
import com.virtusa.akura.api.dto.FaithLifeComment;
import com.virtusa.akura.api.dto.FaithLifeGrading;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.ParticipantCategory;
import com.virtusa.akura.api.dto.Position;
import com.virtusa.akura.api.dto.PrefectType;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Publication;
import com.virtusa.akura.api.dto.PublicationType;
import com.virtusa.akura.api.dto.Race;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Scholarship;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.Seminar;
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.dto.SpecialEventsParticipation;
import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.api.dto.SportCategory;
import com.virtusa.akura.api.dto.SportSub;
import com.virtusa.akura.api.dto.Stream;
import com.virtusa.akura.api.dto.StudentScholarshipTemplate;
import com.virtusa.akura.api.dto.StudentStatus;
import com.virtusa.akura.api.dto.StudyMedium;
import com.virtusa.akura.api.dto.SubTerm;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.dto.WarningLevel;
import com.virtusa.akura.api.dto.WorkingSegment;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

import com.virtusa.akura.common.dao.AssignmentDao;
import com.virtusa.akura.common.dao.AuditEventTypeDao;
import com.virtusa.akura.common.dao.BloodGroupDao;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.ClassGradeDao;
import com.virtusa.akura.common.dao.ClubSocietyDao;
import com.virtusa.akura.common.dao.CountryDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.DonationTypeDao;
import com.virtusa.akura.common.dao.EmploymentStatusDao;
import com.virtusa.akura.common.dao.ExamDao;
import com.virtusa.akura.common.dao.ExamSubjectDao;
import com.virtusa.akura.common.dao.FaithLifeCategoryDao;
import com.virtusa.akura.common.dao.FaithLifeCommentDao;
import com.virtusa.akura.common.dao.FaithLifeGradingDao;
import com.virtusa.akura.common.dao.GradeDao;
import com.virtusa.akura.common.dao.GradeSubjectDao;
import com.virtusa.akura.common.dao.GradingDao;
import com.virtusa.akura.common.dao.HolidayDao;
import com.virtusa.akura.common.dao.HouseDao;
import com.virtusa.akura.common.dao.LanguageDao;
import com.virtusa.akura.common.dao.MethodOfTravelDao;
import com.virtusa.akura.common.dao.NationalityDao;
import com.virtusa.akura.common.dao.ParticipantCategoryDao;
import com.virtusa.akura.common.dao.PositionDao;
import com.virtusa.akura.common.dao.PrefectTypeDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.PublicationDao;
import com.virtusa.akura.common.dao.PublicationTypeDao;
import com.virtusa.akura.common.dao.RaceDao;
import com.virtusa.akura.common.dao.ReligionDao;
import com.virtusa.akura.common.dao.ScholarshipDao;
import com.virtusa.akura.common.dao.SchoolClassDao;
import com.virtusa.akura.common.dao.SeminarDao;
import com.virtusa.akura.common.dao.SpecialEventsDao;
import com.virtusa.akura.common.dao.SpecialEventsParticipationDao;
import com.virtusa.akura.common.dao.SportCategoryDao;
import com.virtusa.akura.common.dao.SportDao;
import com.virtusa.akura.common.dao.SportSubDao;
import com.virtusa.akura.common.dao.StreamDao;
import com.virtusa.akura.common.dao.StudentStatusDao;
import com.virtusa.akura.common.dao.StudyMediumDao;
import com.virtusa.akura.common.dao.SubTermDao;
import com.virtusa.akura.common.dao.SubjectDao;
import com.virtusa.akura.common.dao.TermDao;
import com.virtusa.akura.common.dao.WarningLevelDao;
import com.virtusa.akura.common.dao.WorkingSegmentDao;

/**
 * CommonServiceImpl is implementation of CommonService.
 * 
 * @author Virtusa Corporation
 */
public class CommonServiceImpl implements CommonService {
    
    /** WorkingSegmentDao attribute for holding workingSegmentDao. */
    private WorkingSegmentDao workingSegmentDao;
    
    /**
     * {@link StudyMediumDao}.
     */
    private StudyMediumDao studyMediumDao;
    
    /** HouseDao attribute for holding houseDao. */
    private HouseDao houseDao;
    
    /** ReligionDao attribute for holding religionDao. */
    private ReligionDao religionDao;
    
    /** NationalityDao attribute for holding nationalityDao. */
    private NationalityDao nationalityDao;
    
    /** BloodGroupDao attribute for holding bloodGroupDao. */
    private BloodGroupDao bloodGroupDao;
    
    /** MethodOfTravel attribute for holding methodOfTravelDao. */
    private MethodOfTravelDao methodOfTravelDao;
    
    /** Refers an instance of AddressDaoImpl. */
    private DistrictDao districtDao;
    
    /** Refers an instance of AddressDaoImpl. */
    private CityDao cityDao;
    
    /** Refers an instance of countryDaoImpl. */
    private CountryDao countryDao;
    
    /** Refers an instance of AddressDaoImpl. */
    private ProvinceDao provinceDao;
    
    /** LanguageDao attribute for holding languageDao. */
    private LanguageDao languageDao;
    
    /** termDao attribute for holding TermDao. */
    private TermDao termDao;
    
    /** termDao attribute for holding subTermDao. */
    private SubTermDao subTermDao;
    
    /** SubjectDao attribute for holding subjectDao;. */
    private SubjectDao subjectDao;
    
    /** ClubSocietyDao attribute for holding clubSocietyDaoTarget. */
    private ClubSocietyDao clubSocietyDaoTarget;
    
    /** SportDao attribute for holding sportDaoTarget. */
    private SportDao sportDaoTarget;
    
    /** SportSubDao attribute for holding sportSubDaoTarget. */
    private SportSubDao sportSubDaoTarget;
    
    /** PrefectTypeDao attribute for holding PrefectTypeDao. */
    private PrefectTypeDao prefectTypeDao;
    
    /** ScholarshipDao attribute for holding PrefectTypeDao. */
    private ScholarshipDao scholarshipDao;
    
    /** SportCategoryDao attribute for holding sportCategoryDaoTarget. */
    private SportCategoryDao sportCategoryDaoTarget;
    
    /** schoolClassDao attribute for holding schoolClassDaoTarget. */
    private SchoolClassDao schoolClassDao;
    
    /** HolidayDao attribute for holding holidayDao. */
    private HolidayDao holidayDao;
    
    /** FaithLifeCategoryDao attribute for holding faithLifeCategoryDao. */
    private FaithLifeCategoryDao faithLifeCategoryDao;
    
    /** FaithLifeCommentDao attribute for holding faithLifeCommentDao. */
    private FaithLifeCommentDao faithLifeCommentDao;
    
    /** Holds instance for seminarDao. */
    private SeminarDao seminarDao;
    
    /** Represents an instance of PublicationTypeDaoImpl. */
    private PublicationTypeDao publicationTypeDao;
    
    /** Represents an instance of PublicationDaoImpl. */
    private PublicationDao publicationDao;
    
    /** Represents an instance of WarningLevelDao. */
    private WarningLevelDao warningLevelDao;
    
    /** PositionDao attribute for holding positionDao. */
    private PositionDao positionDao;
    
    /** GradingDao attribute for holding gradingDao;. */
    private GradingDao gradingDao;
    
    /** faithLifeGradingDao attribute for holding FaithLifeGradingDao;. */
    private FaithLifeGradingDao faithLifeGradingDao;
    
    /** DonationTypeDao attribute for holding donationTypeDao. */
    private DonationTypeDao donationTypeDao;
    
    /** EmploymentStatusDao attribute for holding employmentStatusDao. */
    private EmploymentStatusDao employmentStatusDao;
    
    /** ParticipantCategoryDao attribute for holding participantCategoryDao. */
    private ParticipantCategoryDao participantCategoryDao;
    
    /** SpecialEventsDao attribute for holding specialEventsDao. */
    private SpecialEventsDao specialEventsDao;
    
    /** SpecialEventsDao attribute for holding specialEventsDao. */
    private SpecialEventsParticipationDao specialEventsParticipationDao;
    
    /** AssignmentDao attribute for holding assignmentDao. */
    private AssignmentDao assignmentDao;
    
    /** ExamDao attribute for holding ExamDao. */
    private ExamDao examDao;
    
    /** Represents an instance of ExamSubjectDao. */
    private ExamSubjectDao examSubjectDao;
    
    /** Represents an instance of RaceDao. */
    private RaceDao raceDao;
    
    /** Represents an instance of AuditEventTypeDao. */
    private AuditEventTypeDao auditEventTypeDao;
    
    /**
     * Set the raceDao.
     * 
     * @param raceDaoObj the raceDao to set
     */
    public final void setRaceDao(final RaceDao raceDaoObj) {
    
        this.raceDao = raceDaoObj;
    }
    
    /**
     * Injects an instance of examExamSubjectDao.
     * 
     * @param examExamSubjectDao - an instance of examExamSubjectDao.
     */
    public void setExamSubjectDao(ExamSubjectDao examExamSubjectDao) {
    
        this.examSubjectDao = examExamSubjectDao;
    }
    
    /**
     * Get the Exam Dao.
     * 
     * @return the examDao
     */
    public ExamDao getExamDao() {
    
        return examDao;
    }
    
    /**
     * Set the Exam dao.
     * 
     * @param examDaoValue the examDao to set
     */
    public void setExamDao(ExamDao examDaoValue) {
    
        this.examDao = examDaoValue;
    }
    
    /**
     * Set the employmentStatusDao.
     * 
     * @param employmentStatusDaoRef the EmploymentStatusDao to set
     */
    public void setEmploymentStatusDao(EmploymentStatusDao employmentStatusDaoRef) {
    
        this.employmentStatusDao = employmentStatusDaoRef;
    }
    
    /**
     * Set the specialEventsParticipationDao type dao.
     * 
     * @param specialEventsParticipationDaoRef - the specialEventsParticipationDao to set
     */
    public void setSpecialEventsParticipationDao(SpecialEventsParticipationDao specialEventsParticipationDaoRef) {
    
        this.specialEventsParticipationDao = specialEventsParticipationDaoRef;
    }
    
    /**
     * Get the Assignment type dao.
     * 
     * @return the assignmentDao
     */
    public AssignmentDao getAssignmentDao() {
    
        return assignmentDao;
    }
    
    /**
     * Set the assignmentDao type dao.
     * 
     * @param assignmentDaoRef - the AssignmentDao to set
     */
    public void setAssignmentDao(AssignmentDao assignmentDaoRef) {
    
        this.assignmentDao = assignmentDaoRef;
    }
    
    /**
     * Set the specialEventsDao type dao.
     * 
     * @param specialEventsDaoRef - the specialEventsDao to set
     */
    public void setSpecialEventsDao(SpecialEventsDao specialEventsDaoRef) {
    
        this.specialEventsDao = specialEventsDaoRef;
    }
    
    /**
     * Set the ParticipantCategoryDao type dao.
     * 
     * @param participantCategoryDaoRef - the ParticipantCategoryDao to set
     */
    public void setParticipantCategoryDao(ParticipantCategoryDao participantCategoryDaoRef) {
    
        this.participantCategoryDao = participantCategoryDaoRef;
    }
    
    /**
     * Get the Class type dao.
     * 
     * @return the schoolClassDao
     */
    public SchoolClassDao getSchoolClassDao() {
    
        return schoolClassDao;
    }
    
    /**
     * Set the SchoolClass type dao.
     * 
     * @param schoolClassDaoVal - the SchoolClassDao to set
     */
    public void setSchoolClassDao(SchoolClassDao schoolClassDaoVal) {
    
        this.schoolClassDao = schoolClassDaoVal;
    }
    
    /**
     * @param faithLifeCategoryDaoObj the faithLifeCategoryDao to set
     */
    public void setFaithLifeCategoryDao(FaithLifeCategoryDao faithLifeCategoryDaoObj) {
    
        this.faithLifeCategoryDao = faithLifeCategoryDaoObj;
    }
    
    /**
     * @param faithLifeCommentDaoObj the faithLifeCommentDao to set
     */
    public void setFaithLifeCommentDao(FaithLifeCommentDao faithLifeCommentDaoObj) {
    
        this.faithLifeCommentDao = faithLifeCommentDaoObj;
    }
    
    /**
     * @param holidayDaoObj the holidayDao to set
     */
    public void setHolidayDao(HolidayDao holidayDaoObj) {
    
        this.holidayDao = holidayDaoObj;
    }
    
    /**
     * Set the Scholarship type dao.
     * 
     * @param scholarshipDaoVal - the scholarshipDao to set
     */
    public void setScholarshipDao(ScholarshipDao scholarshipDaoVal) {
    
        this.scholarshipDao = scholarshipDaoVal;
    }
    
    /**
     * Get the prefect type dao.
     * 
     * @return the prefectTypeDao
     */
    public PrefectTypeDao getPrefectTypeDao() {
    
        return prefectTypeDao;
    }
    
    /**
     * Set the prefect type dao.
     * 
     * @param prefectTypeDaoValue the prefectTypeDao to set
     */
    public void setPrefectTypeDao(PrefectTypeDao prefectTypeDaoValue) {
    
        this.prefectTypeDao = prefectTypeDaoValue;
    }
    
    /**
     * @param sportDaoTargetValue the SportDao to set
     */
    public void setSportDaoTarget(SportDao sportDaoTargetValue) {
    
        this.sportDaoTarget = sportDaoTargetValue;
    }
    
    /**
     * @param sportSubDaoTargetValue the SportSubDao to set
     */
    public void setSportSubDaoTarget(SportSubDao sportSubDaoTargetValue) {
    
        this.sportSubDaoTarget = sportSubDaoTargetValue;
    }
    
    /**
     * @param clubSocietyDaoTargetValue the ClubSocietyDao to set
     */
    public void setClubSocietyDaoTarget(ClubSocietyDao clubSocietyDaoTargetValue) {
    
        this.clubSocietyDaoTarget = clubSocietyDaoTargetValue;
    }
    
    /**
     * @param subjectDaoValue the subjectDao to set
     */
    public void setSubjectDao(SubjectDao subjectDaoValue) {
    
        this.subjectDao = subjectDaoValue;
    }
    
    /**
     * Adds a public setter to create an instance of type DistrictDao.
     * 
     * @param disDistrictDao set the property of type DistrictDao.
     */
    public final void setDistrictDao(final DistrictDao disDistrictDao) {
    
        this.districtDao = disDistrictDao;
    }
    
    /**
     * Adds a public setter to create an instance of type CityDao.
     * 
     * @param cityCityDao set the property of type CityDao.
     */
    public final void setCityDao(final CityDao cityCityDao) {
    
        this.cityDao = cityCityDao;
    }
    
    /**
     * Adds a public setter to create an instance of type CountryDao.
     * 
     * @param countryCountryDao set the property of type CityDao.
     */
    public final void setCountryDao(final CountryDao countryCountryDao) {
    
        this.countryDao = countryCountryDao;
    }
    
    /**
     * Adds a public setter to create an instance of type ProvinceDao.
     * 
     * @param prProvinceDao set the property of type ProvinceDao.
     */
    public final void setProvinceDao(final ProvinceDao prProvinceDao) {
    
        this.provinceDao = prProvinceDao;
    }
    
    /**
     * Adds a public setter to create an instance of type HouseDao.
     * 
     * @param houseDaoVal set the property of type HouseDao.
     */
    public final void setHouseDao(final HouseDao houseDaoVal) {
    
        this.houseDao = houseDaoVal;
    }
    
    /**
     * Adds a public setter to create an instance of type ReligionDao.
     * 
     * @param religionDaoVal set the property of type ReligionDao.
     */
    public final void setReligionDao(final ReligionDao religionDaoVal) {
    
        this.religionDao = religionDaoVal;
    }
    
    /**
     * Adds a public setter to create an instance of type LanguageDao.
     * 
     * @param languageDaoVal set the property of type LanguageDao.
     */
    public final void setLanguageDao(final LanguageDao languageDaoVal) {
    
        this.languageDao = languageDaoVal;
    }
    
    /**
     * Adds a public setter to create an instance of type TermDao.
     * 
     * @param objTermDao set the property of type TermDao.
     */
    public final void setTermDao(final TermDao objTermDao) {
    
        this.termDao = objTermDao;
    }
    
    /**
     * Adds a public setter to create an instance of type TermDao.
     * 
     * @param objSubTermDao set the property of type TermDao.
     */
    public final void setSubTermDao(final SubTermDao objSubTermDao) {
    
        this.subTermDao = objSubTermDao;
    }
    
    /**
     * @param sportCategoryDaoTargetObj the sportCategoryDaoTarget to set
     */
    public void setSportCategoryDaoTarget(SportCategoryDao sportCategoryDaoTargetObj) {
    
        this.sportCategoryDaoTarget = sportCategoryDaoTargetObj;
    }
    
    /**
     * Sets the seminar dao.
     * 
     * @param objSeminarDao the seminarDao to set
     */
    public void setSeminarDao(SeminarDao objSeminarDao) {
    
        this.seminarDao = objSeminarDao;
    }
    
    /**
     * Sets the publication type dao.
     * 
     * @param objPublicationTypeDao the new publication type dao
     */
    public void setPublicationTypeDao(PublicationTypeDao objPublicationTypeDao) {
    
        this.publicationTypeDao = objPublicationTypeDao;
    }
    
    /**
     * @param warningLevelDaoObj the warningLevelDaoTarget to set
     */
    public void setWarningLevelDao(WarningLevelDao warningLevelDaoObj) {
    
        this.warningLevelDao = warningLevelDaoObj;
    }
    
    /**
     * Sets the publication dao.
     * 
     * @param objPublicationDao the new publication dao
     */
    public void setPublicationDao(PublicationDao objPublicationDao) {
    
        this.publicationDao = objPublicationDao;
    }
    
    /**
     * Get study medium object.
     * 
     * @return study medium object.
     */
    public StudyMediumDao getStudyMediumDao() {
    
        return studyMediumDao;
    }
    
    /**
     * Set StudyMedium Object.
     * 
     * @param objStudyMediumDao study medium object.
     */
    public void setStudyMediumDao(StudyMediumDao objStudyMediumDao) {
    
        this.studyMediumDao = objStudyMediumDao;
    }
    
    /**
     * Sets the position dao.
     * 
     * @param objPositionDao the positionDao to set
     */
    public void setPositionDao(PositionDao objPositionDao) {
    
        this.positionDao = objPositionDao;
    }
    
    /**
     * Sets the grading dao.
     * 
     * @param objGradingDao the gradingDao to set
     */
    public void setGradingDao(GradingDao objGradingDao) {
    
        this.gradingDao = objGradingDao;
    }
    
    /**
     * Sets the faithLifeGradingDao.
     * 
     * @param faithLifeGradingDaoRef the faithLifeGradingDao to set
     */
    public void setFaithLifeGradingDao(FaithLifeGradingDao faithLifeGradingDaoRef) {
    
        this.faithLifeGradingDao = faithLifeGradingDaoRef;
    }
    
    /**
     * Adds a public setter to create an instance of type WorkingSegmentDao.
     * 
     * @param workingSegmentDaoValue set the property of type WorkingSegmentDao.
     */
    public void setWorkingSegmentDao(WorkingSegmentDao workingSegmentDaoValue) {
    
        this.workingSegmentDao = workingSegmentDaoValue;
    }
    
    /**
     * Adds a public setter to create an instance of type DonationTypeDao.
     * 
     * @param dTypeDao set the property of type DonationTypeDao.
     */
    public void setDonationTypeDao(final DonationTypeDao dTypeDao) {
    
        this.donationTypeDao = dTypeDao;
    }
    
    /**
     * Get the Country.
     * 
     * @return CountryDao object.
     */
    public CountryDao getCountryDao() {
    
        return countryDao;
    }
    
    /**
     * Set the AuditEventTypeDao object.
     * 
     * @param auditEventTypeDaoRef to be set.
     */
    public void setAuditEventTypeDao(AuditEventTypeDao auditEventTypeDaoRef) {
    
        this.auditEventTypeDao = auditEventTypeDaoRef;
    }
    
    /**
     * Default constructor.
     */
    public CommonServiceImpl() {
    
    }
    
    /**
     * Method is to House reference data.
     * 
     * @return House list.
     * @throws AkuraAppException - Detailed exception.
     */
    @Override
    public List<House> getHouseList() throws AkuraAppException {
    
        return houseDao.findAll(House.class);
    }
    
    /**
     * Method is to Religion reference data.
     * 
     * @return religion list.
     * @throws AkuraAppException - Detailed exception.
     */
    @Override
    public List<Religion> getReligionList() throws AkuraAppException {
    
        return religionDao.findAll(Religion.class);
    }
    
    /**
     * @return retrieve language reference list.
     * @throws AkuraAppException - Detailed exception.
     * @see com.virtusa.sms.api.service.CommonService#getLanguageList()
     */
    @Override
    public List<Language> getLanguageList() throws AkuraAppException {
    
        return languageDao.findAll(Language.class);
    }
    
    /**
     * @return list of City references.
     * @throws AkuraAppException - Detailed exception.
     * @see com.virtusa.sms.api.service.CommonService#getCityList()
     */
    @Override
    public List<City> getCityList() throws AkuraAppException {
    
        return cityDao.findAll(City.class);
    }
    
    /**
     * Returns the list of aviliable MethodOfTravel.
     * 
     * @return list of MethodOfTravel.
     * @throws AkuraAppException throws if exception occurs when retrieving the MethodOfTravel list.
     */
    @Override
    public List<MethodOfTravel> getMethodOfTravelList() throws AkuraAppException {
    
        return methodOfTravelDao.findAll(MethodOfTravel.class);
    }
    
    /**
     * Setter method for methodOfTravelDao.
     * 
     * @param methodOfTravelDaoVal the methodOfTravelDao to set
     */
    public void setMethodOfTravelDao(MethodOfTravelDao methodOfTravelDaoVal) {
    
        this.methodOfTravelDao = methodOfTravelDaoVal;
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public final City addCity(final City city) throws AkuraAppException {
    
        return cityDao.save(city);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public final Country addCountry(final Country country) throws AkuraAppException {
    
        return countryDao.save(country);
    }
    
    /**
     * {@inheritDoc}
     */
    public final District addDistrict(final District district) throws AkuraAppException {
    
        return districtDao.save(district);
    }
    
    /**
     * {@inheritDoc}
     */
    public final Province addProvince(final Province province) throws AkuraAppException {
    
        return provinceDao.save(province);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void deleteCity(final City city) throws AkuraAppException {
    
        cityDao.delete(city);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void deleteCountry(final Country country) throws AkuraAppException {
    
        countryDao.delete(country);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void deleteDistrict(final District district) throws AkuraAppException {
    
        districtDao.delete(district);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void deleteProvince(final Province province) throws AkuraAppException {
    
        provinceDao.delete(province);
    }
    
    /**
     * {@inheritDoc}
     */
    public final City findCity(final Integer cityId) throws AkuraAppException {
    
        return (City) cityDao.findById(City.class, cityId);
    }
    
    /**
     * {@inheritDoc}
     */
    public final Country findCountry(final Integer countryId) throws AkuraAppException {
    
        return (Country) countryDao.findById(Country.class, countryId);
    }
    
    /**
     * {@inheritDoc}
     */
    public final District findDistrict(final Integer districtId) throws AkuraAppException {
    
        return (District) districtDao.findById(District.class, districtId);
    }
    
    /**
     * {@inheritDoc}
     */
    public final Province findProvince(final Integer provinceId) throws AkuraAppException {
    
        return (Province) provinceDao.findById(Province.class, provinceId);
    }
    
    /**
     * {@inheritDoc}
     */
    public final List<District> getDistrictList() throws AkuraAppException {
    
        return districtDao.findAll(District.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public final List<Province> getProvinceList() throws AkuraAppException {
    
        return provinceDao.findAll(Province.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void updateCity(final City city) throws AkuraAppException {
    
        cityDao.update(city);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void updateCountry(final Country country) throws AkuraAppException {
    
        countryDao.update(country);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void updateDistrict(final District district) throws AkuraAppException {
    
        districtDao.update(district);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void updateProvince(final Province province) throws AkuraAppException {
    
        provinceDao.update(province);
    }
    
    /**
     * {@inheritDoc}
     */
    public final List<Province> searchProvince(final String provinceName) throws AkuraAppException {
    
        return provinceDao.searchProvince(provinceName);
    }
    
    /**
     * {@inheritDoc}
     */
    public final List<District> searchDistrict(final String searchDescription) throws AkuraAppException {
    
        return districtDao.searchDistrict(searchDescription);
    }
    
    /**
     * {@inheritDoc}
     */
    public final List<Nationality> getNationalityList() throws AkuraAppException {
    
        return nationalityDao.findAll(Nationality.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public final List<BloodGroup> getBloodGroupList() throws AkuraAppException {
    
        return bloodGroupDao.findAll(BloodGroup.class);
    }
    
    /**
     * Setter method for nationalityDao.
     * 
     * @param nationalityDaoVal the nationalityDao to set
     */
    public final void setNationalityDao(final NationalityDao nationalityDaoVal) {
    
        this.nationalityDao = nationalityDaoVal;
    }
    
    /**
     * Setter method for bloodGroupDao.
     * 
     * @param bloodGroupDaoVal the bloodGroupDao to set
     */
    public final void setBloodGroupDao(final BloodGroupDao bloodGroupDaoVal) {
    
        this.bloodGroupDao = bloodGroupDaoVal;
    }
    
    /**
     * {@inheritDoc}
     */
    public final List<City> searchCity(final String searchDescription) throws AkuraAppException {
    
        return cityDao.searchCity(searchDescription);
    }
    
    /**
     * {@inheritDoc}
     */
    public final List<Country> searchCountry(final String searchDescription) throws AkuraAppException {
    
        return countryDao.searchCountry(searchDescription);
    }
    
    /**
     * Returns the list of Terms.
     * 
     * @return list of Terms.
     * @throws AkuraAppException throws if exception occurs when retrieving the MethodOfTravel list.
     */
    public final List<Term> getTermList() throws AkuraAppException {
    
        return termDao.findAll(Term.class);
    }
    
    /**
     * Returns the list of sub Terms.
     * 
     * @return list of Sub Terms.
     * @throws AkuraAppException throws if exception occurs when retrieving the MethodOfTravel list.
     */
    public List<SubTerm> getSubTermList() throws AkuraAppException {
    
        return subTermDao.findAll(SubTerm.class);
    }
    
    /**
     * Method is to return Subject type object.
     * 
     * @param subjectId - integer type object.
     * @return Subject type object.
     * @throws AkuraAppException when exception occurs.
     */
    public Subject findSubjectById(int subjectId) throws AkuraAppException {
    
        return (Subject) subjectDao.findById(Subject.class, subjectId);
    }
    
    /**
     * Method to add clubSociety reference data.
     * 
     * @param clubSociety - ClubSociety type object.
     * @return - the saved ClubSociety object.
     * @throws AkuraAppException - Detailed exception.
     */
    @Override
    public ClubSociety addClubSociety(ClubSociety clubSociety) throws AkuraAppException {
    
        return clubSocietyDaoTarget.save(clubSociety);
        
    }
    
    /**
     * Updates the relevant ClubSociety object.
     * 
     * @param clubSociety - ClubSociety instance.
     * @throws AkuraAppException - if error occurs when updating a ClubSociety instance.
     */
    @Override
    public void editClubSociety(ClubSociety clubSociety) throws AkuraAppException {
    
        clubSocietyDaoTarget.update(clubSociety);
        
    }
    
    /**
     * Deletes the relevant ClubSociety object.
     * 
     * @param clubSocietyId - id of clubSocietyId.
     * @throws AkuraAppException - if error occurs when deleting a clubSociety instance.
     */
    @Override
    public void deleteClubSociety(int clubSocietyId) throws AkuraAppException {
    
        ClubSociety objClubSociety = (ClubSociety) clubSocietyDaoTarget.findById(ClubSociety.class, clubSocietyId);
        
        clubSocietyDaoTarget.delete(objClubSociety);
        
    }
    
    /**
     * Returns a list of ClubSociety instances.
     * 
     * @return a list of ClubSociety instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of ClubSociety instances.
     */
    @Override
    public List<ClubSociety> getClubsSocietiesList() throws AkuraAppException {
    
        return clubSocietyDaoTarget.findAll(ClubSociety.class);
    }
    
    /**
     * Finds the ClubSociety that relevant to the ClubSociety id.
     * 
     * @param clubSocietyId - ClubSociety id.
     * @return a ClubSociety instance.
     * @throws AkuraAppException throws if exception occurs when finding a ClubSociety instance.
     */
    @Override
    public ClubSociety findClubSocietyById(int clubSocietyId) throws AkuraAppException {
    
        return (ClubSociety) clubSocietyDaoTarget.findById(ClubSociety.class, clubSocietyId);
    }
    
    /**
     * Retrieve the available ClubSociety object.
     * 
     * @param clubSocietyName - String.
     * @return ClubSociety object.
     * @throws AkuraAppException - throw detailed exception.
     */
    public ClubSociety getAnyClubSociety(String clubSocietyName) throws AkuraAppException {
    
        return clubSocietyDaoTarget.getAnyClubSocietyByName(clubSocietyName);
    }
    
    /**
     * Check whether ClubSociety is already added.
     * 
     * @param clubSocietyName - String
     * @param clubSocietyId - int
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsClubSociety(String clubSocietyName, int clubSocietyId) throws AkuraAppException {
    
        String name = clubSocietyName.trim();
        ClubSociety clubSociety = getAnyClubSociety(name);
        
        boolean isExists = false;
        
        if (clubSociety != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Service to add Sport instance.
     * 
     * @param sport Sport instance.
     * @return - the saved Sport object.
     * @throws AkuraAppException throws if exception occurs when saving a Sport instance.
     */
    @Override
    public Sport addSport(Sport sport) throws AkuraAppException {
    
        return sportDaoTarget.save(sport);
        
    }
    
    /**
     * Updates the relevant Sport object.
     * 
     * @param sport - Sport instance.
     * @throws AkuraAppException - if error occurs when updating a sport instance.
     */
    @Override
    public void editSport(Sport sport) throws AkuraAppException {
    
        sportDaoTarget.update(sport);
        
    }
    
    /**
     * Deletes the relevant Sport object.
     * 
     * @param sportId - id of Sport.
     * @throws AkuraAppException - if error occurs when deleting a sport instance.
     */
    @Override
    public void deleteSport(int sportId) throws AkuraAppException {
    
        Sport sport = this.findSportById(sportId);
        sportDaoTarget.delete(sport);
        
    }
    
    /**
     * Returns a list of Sport instances.
     * 
     * @return a list of Sport instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Sport instances.
     */
    @Override
    public List<Sport> getSportsList() throws AkuraAppException {
    
        return sportDaoTarget.findAll(Sport.class);
    }
    
    /**
     * Finds the sport that relevant to the sport id.
     * 
     * @param sportId - Sport id.
     * @return a Sport instance.
     * @throws AkuraAppException throws if exception occurs when finding a Sport instance.
     */
    @Override
    public Sport findSportById(int sportId) throws AkuraAppException {
    
        return (Sport) sportDaoTarget.findById(Sport.class, sportId);
    }
    
    /**
     * Service to add SportSub instance.
     * 
     * @param sportSub SportSub instance.
     * @return - the saved SportSub object.
     * @throws AkuraAppException throws if exception occurs when saving a SportSub instance.
     */
    @Override
    public SportSub addSportSub(SportSub sportSub) throws AkuraAppException {
    
        return sportSubDaoTarget.save(sportSub);
        
    }
    
    /**
     * Updates the relevant SportSub object.
     * 
     * @param sportSub - SportSub instance.
     * @throws AkuraAppException - if error occurs when updating a sportSub instance.
     */
    @Override
    public void editSportSub(SportSub sportSub) throws AkuraAppException {
    
        sportSubDaoTarget.update(sportSub);
        
    }
    
    /**
     * Deletes the relevant SportSub object.
     * 
     * @param sportSubId - id of SportSub.
     * @throws AkuraAppException - if error occurs when deleting a sportSub instance.
     */
    @Override
    public void deleteSportSub(int sportSubId) throws AkuraAppException {
    
        SportSub sportSub = this.findSportSubById(sportSubId);
        sportSubDaoTarget.delete(sportSub);
        
    }
    
    /**
     * Returns a list of SportSub instances.
     * 
     * @return a list of SportSub instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of SportSub instances.
     */
    @Override
    public List<SportSub> getSportSubsList() throws AkuraAppException {
    
        return sportSubDaoTarget.findAll(SportSub.class);
    }
    
    /**
     * Finds the sportSub that relevant to the sportSub id.
     * 
     * @param sportSubId - SportSub id.
     * @return a SportSub instance.
     * @throws AkuraAppException throws if exception occurs when finding a SportSub instance.
     */
    @Override
    public SportSub findSportSubById(int sportSubId) throws AkuraAppException {
    
        return (SportSub) sportSubDaoTarget.findById(SportSub.class, sportSubId);
    }
    
    /**
     * Get list of sub terms of a term.
     * 
     * @param termId - termId id.
     * @return a list of SubTerm.
     * @throws AkuraAppException throws if exception occurs when
     */
    public List<SubTerm> getSubTermsOfATerm(int termId) throws AkuraAppException {
    
        return subTermDao.getSubTermsOfATerm(termId);
    }
    
    /**
     * service to add a PrefectType to student.
     * 
     * @param prefectType the PrefectType object to add.
     * @return a prefectType instance.
     * @throws AkuraAppException - The exception details that occurred when adding a PrefectType instances.
     */
    public PrefectType addPrefectType(PrefectType prefectType) throws AkuraAppException {
    
        return prefectTypeDao.save(prefectType);
    }
    
    /**
     * service to delete a PrefectType of a student.
     * 
     * @param prefectType the id of the PrefectType object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete a PrefectType instances.
     */
    public void deletePrefectType(PrefectType prefectType) throws AkuraAppException {
    
        prefectTypeDao.delete(prefectType);
    }
    
    /**
     * Finds the PrefectType that relevant to the PrefectType id.
     * 
     * @param prefectTypeId - prefectType id.
     * @return a prefectType instance.
     * @throws AkuraAppException throws if exception occurs when finding a PrefectType instance.
     */
    public final PrefectType findPrefectType(final Integer prefectTypeId) throws AkuraAppException {
    
        return (PrefectType) prefectTypeDao.findById(PrefectType.class, prefectTypeId);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<PrefectType> getPrefectTypeList() throws AkuraAppException {
    
        return prefectTypeDao.findAll(PrefectType.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public void updatePrefectType(PrefectType prefectType) throws AkuraAppException {
    
        prefectTypeDao.update(prefectType);
    }
    
    /**
     * Get PrefectType object by PrefectTypeId.
     * 
     * @param prefectTypeId prefect type id defined by integer type object
     * @return a PrefectType object.
     * @throws AkuraAppException throws if exception occurs when
     */
    public PrefectType findPrefectTypeById(int prefectTypeId) throws AkuraAppException {
    
        return (PrefectType) prefectTypeDao.findById(PrefectType.class, prefectTypeId);
    }
    
    /**
     * Get PrefectType object by scholarshipTypeId.
     * 
     * @param scholarshipId scholarship type id defined by integer type object
     * @return a Scholarship object.
     * @throws AkuraAppException throws if exception occurs when
     */
    public Scholarship findScholarshipById(int scholarshipId) throws AkuraAppException {
    
        return (Scholarship) scholarshipDao.findById(Scholarship.class, scholarshipId);
    }
    
    /**
     * Service to add SportCategory instance.
     * 
     * @param sportCategory SportCategory instance.
     * @return - the saved SportCategory object.
     * @throws AkuraAppException throws if exception occurs when saving a SportCategory instance.
     */
    public SportCategory addSportCategory(SportCategory sportCategory) throws AkuraAppException {
    
        return sportCategoryDaoTarget.save(sportCategory);
        
    }
    
    /**
     * Updates the relevant SportCategory object.
     * 
     * @param sportCategory - SportCategory instance.
     * @throws AkuraAppException - if error occurs when updating a sportCategory instance.
     */
    public void editSportCategory(SportCategory sportCategory) throws AkuraAppException {
    
        sportCategoryDaoTarget.update(sportCategory);
        
    }
    
    /**
     * Deletes the relevant SportCategory object.
     * 
     * @param sportCategoryId - id of SportCategory.
     * @throws AkuraAppException - if error occurs when deleting a sportCategory instance.
     */
    public void deleteSportCategory(int sportCategoryId) throws AkuraAppException {
    
        SportCategory sportCategoryObj = this.findSportCategoryById(sportCategoryId);
        sportCategoryDaoTarget.delete(sportCategoryObj);
        
    }
    
    /**
     * Returns a list of SportCategory instances.
     * 
     * @return a list of SportCategory instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of SportCategory instances.
     */
    public List<SportCategory> getSportCategoriesList() throws AkuraAppException {
    
        return sportCategoryDaoTarget.findAll(SportCategory.class);
        
    }
    
    /**
     * Finds the sportCategory that relevant to the sportCategory id.
     * 
     * @param sportCategoryId - SportCategory id.
     * @return a SportCategory instance.
     * @throws AkuraAppException throws if exception occurs when finding a SportCategory instance.
     */
    public SportCategory findSportCategoryById(int sportCategoryId) throws AkuraAppException {
    
        return (SportCategory) sportCategoryDaoTarget.findById(SportCategory.class, sportCategoryId);
        
    }
    
    /**
     * Finds the sportCategory that relevant to the sport id.
     * 
     * @param sportId - Sport id.
     * @return SportCategory list.
     * @throws AkuraAppException throws if exception occurs when finding a SportCategory instance.
     */
    public List<SportCategory> getSportCategoryBySport(int sportId) throws AkuraAppException {
    
        return sportCategoryDaoTarget.findSportCategoryBySportId(sportId);
        
    }
    
    /**
     * service to add a SchoolClass.
     * 
     * @param schoolClass the SchoolClass object to add.
     * @return a schoolClass instance.
     * @throws AkuraAppException - The exception details that occurred when adding a SchoolClass instances.
     */
    public SchoolClass addSchoolClass(SchoolClass schoolClass) throws AkuraAppException {
    
        return schoolClassDao.save(schoolClass);
    }
    
    /**
     * service to delete a SchoolClass of a student.
     * 
     * @param schoolClass the id of the SchoolClass object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete a SchoolClass instances.
     */
    public void deleteSchoolClass(SchoolClass schoolClass) throws AkuraAppException {
    
        schoolClassDao.delete(schoolClass);
    }
    
    /**
     * Finds the SchoolClass that relevant to the SchoolClass id.
     * 
     * @param schoolClassId - schoolClass id.
     * @return a schoolClass instance.
     * @throws AkuraAppException throws if exception occurs when finding a SchoolClass instance.
     */
    public SchoolClass findSchoolClass(Integer schoolClassId) throws AkuraAppException {
    
        return (SchoolClass) schoolClassDao.findById(SchoolClass.class, schoolClassId);
    }
    
    /**
     * Finds the schoolClass that relevant to the schoolClass id.
     * 
     * @param schoolClassId - SchoolClass id.
     * @return a SchoolClass instance.
     * @throws AkuraAppException throws if exception occurs when finding a SchoolClass instance.
     */
    public SchoolClass findSchoolClassById(int schoolClassId) throws AkuraAppException {
    
        return (SchoolClass) schoolClassDao.findById(SchoolClass.class, schoolClassId);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<SchoolClass> getSchoolClassList() throws AkuraAppException {
    
        return schoolClassDao.findAll(SchoolClass.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public void updateSchoolClass(SchoolClass schoolClass) throws AkuraAppException {
    
        schoolClassDao.update(schoolClass);
    }
    
    /**
     * Service to add Holiday instance.
     * 
     * @param holiday Holiday instance.
     * @return - the saved Holiday object.
     * @throws AkuraAppException throws if exception occurs when saving a Holiday instance.
     */
    @Override
    public Holiday saveHoliday(Holiday holiday) throws AkuraAppException {
    
        return holidayDao.save(holiday);
    }
    
    /**
     * Updates the relevant Holiday object.
     * 
     * @param holiday - Holiday instance.
     * @throws AkuraAppException - if error occurs when updating a Holiday instance.
     */
    @Override
    public void updateHoliday(Holiday holiday) throws AkuraAppException {
    
        holidayDao.update(holiday);
        
    }
    
    /**
     * Deletes the relevant Holiday object.
     * 
     * @param holidayId - id of Holiday.
     * @throws AkuraAppException - if error occurs when deleting a Holiday instance.
     */
    @Override
    public void deleteHoliday(int holidayId) throws AkuraAppException {
    
        Holiday holidayObj = this.findHolidayById(holidayId);
        holidayDao.delete(holidayObj);
        
    }
    
    /**
     * Returns a list of Holiday instances.
     * 
     * @param startDate - Date.
     * @param endDate - Date.
     * @return a list of Holiday instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Holiday instances.
     */
    @Override
    public List<Holiday> findHolidayByYear(Date startDate, Date endDate) throws AkuraAppException {
    
        return holidayDao.getHolidayListByYear(startDate, endDate);
    }
    
    /**
     * Finds the Holiday that relevant to the holiday id.
     * 
     * @param holidayId - Holiday id.
     * @return a Holiday instance.
     * @throws AkuraAppException throws if exception occurs when finding a Holiday instance.
     */
    @Override
    public Holiday findHolidayById(int holidayId) throws AkuraAppException {
    
        return (Holiday) holidayDao.findById(Holiday.class, holidayId);
    }
    
    /**
     * Returns a list of Holiday instances.
     * 
     * @return a list of Holiday instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Holiday instances.
     */
    @Override
    public List<Holiday> findHolidays() throws AkuraAppException {
    
        return holidayDao.findAll(Holiday.class);
    }
    
    /**
     * Returns a list of FaithLifeCategory instances.
     * 
     * @return a list of FaithLifeCategory instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of FaithLifeCategory instances.
     */
    @Override
    public List<FaithLifeCategory> findFaithLifeCategories() throws AkuraAppException {
    
        return faithLifeCategoryDao.findAll(FaithLifeCategory.class);
        
    }
    
    /**
     * Returns a list of FaithLifeComment instances.
     * 
     * @param categoryId - int.
     * @return a list of FaithLifeComment instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of FaithLifeComment instances.
     */
    @Override
    public List<FaithLifeComment> findFaithLifeCommentsByCategory(int categoryId) throws AkuraAppException {
    
        return faithLifeCommentDao.findFaithLifeCommentByCategory(categoryId);
        
    }
    
    /**
     * Finds the FaithLifeComment that relevant to the FaithLifeComment id.
     * 
     * @param faithLifeCommentId - FaithLifeComment id.
     * @return a FaithLifeComment instance.
     * @throws AkuraAppException throws if exception occurs when finding a FaithLifeComment instance.
     */
    @Override
    public FaithLifeComment findFaithLifeCommentById(int faithLifeCommentId) throws AkuraAppException {
    
        return (FaithLifeComment) faithLifeCommentDao.findById(FaithLifeComment.class, faithLifeCommentId);
    }
    
    /**
     * Create a Seminar.
     * 
     * @param seminar {@link Seminar}
     * @return {@link Seminar}
     * @throws AkuraAppException throws if exception occurs when adding a Seminar instance.
     */
    public Seminar createSeminar(Seminar seminar) throws AkuraAppException {
    
        return seminarDao.save(seminar);
    }
    
    /**
     * Edit a Seminar.
     * 
     * @param seminar - {@link Seminar} .
     * @throws AkuraAppException throws if exception occurs when update a Seminar instance.
     */
    public void updateSeminar(Seminar seminar) throws AkuraAppException {
    
        seminarDao.update(seminar);
        
    }
    
    /**
     * Get the Seminar by id.
     * 
     * @param seminarId - int .
     * @return {@link Seminar}
     * @throws AkuraAppException throws if exception occurs when get a Seminar instance.
     */
    public Seminar findSeminarById(int seminarId) throws AkuraAppException {
    
        return (Seminar) seminarDao.findById(Seminar.class, seminarId);
    }
    
    /**
     * Delete a Seminar.
     * 
     * @param seminar - {@link Seminar} .
     * @throws AkuraAppException throws if exception occurs when deleting a Seminar instance.
     */
    public void deleteSeminar(Seminar seminar) throws AkuraAppException {
    
        seminarDao.delete(seminar);
        
    }
    
    /**
     * View all the available seminars.
     * 
     * @return list of seminars {@link Seminar}
     * @throws AkuraAppException - Detailed exception.
     */
    public List<Seminar> getSeminars() throws AkuraAppException {
    
        return seminarDao.findAll(Seminar.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<PublicationType> getPublicationTypes() throws AkuraAppException {
    
        return publicationTypeDao.findAll(PublicationType.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<Publication> getPublications() throws AkuraAppException {
    
        return publicationDao.findAll(Publication.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public void updatePublication(Publication publication) throws AkuraAppException {
    
        publicationDao.update(publication);
        
    }
    
    /**
     * {@inheritDoc}
     */
    public Publication addPublication(Publication publication) throws AkuraAppException {
    
        return publicationDao.save(publication);
    }
    
    /**
     * {@inheritDoc}
     */
    public void deletePublication(Publication publication) throws AkuraAppException {
    
        publicationDao.delete(publication);
    }
    
    /**
     * {@inheritDoc}
     */
    public PublicationType findPublicationType(int pTypeId) throws AkuraAppException {
    
        return (PublicationType) publicationTypeDao.findById(PublicationType.class, pTypeId);
    }
    
    /**
     * {@inheritDoc}
     */
    public Publication findPublication(int publicationId) throws AkuraAppException {
    
        return (Publication) publicationDao.findById(Publication.class, publicationId);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<Publication> getPublicationsWithPagination(int minSize, int maxSize) throws AkuraAppException {
    
        return publicationDao.getPublicationsWithPagination(minSize, maxSize);
    }
    
    /**
     * {@inheritDoc}
     */
    public int getPageNo() throws AkuraAppException {
    
        List<Long> pageNoList = publicationDao.getMaxNoOfPublications();
        return (int) (pageNoList != null ? pageNoList.get(0) : 0);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<Publication> getLatestPublications() throws AkuraAppException {
    
        return publicationDao.getLatestPublications();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subject> getSubjectList() throws AkuraAppException {
    
        return subjectDao.findAll(Subject.class);
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudyMedium> getStudyMediumList() throws AkuraAppException {
    
        return studyMediumDao.findAll(StudyMedium.class);
        
    }
    
    /**
     * Finds the WarningLevel that relevant to the WarningLevel id.
     * 
     * @param warningLevelId - WarningLevel id.
     * @return a WarningLevel instance.
     * @throws AkuraAppException throws if exception occurs when finding a WarningLevel instance.
     */
    public WarningLevel findWarningLevelById(int warningLevelId) throws AkuraAppException {
    
        return (WarningLevel) warningLevelDao.findById(WarningLevel.class, warningLevelId);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StudyMedium> findStudyMedium(String key) throws AkuraAppException {
    
        return studyMediumDao.findStudyMediums(key);
    }
    
    /** Refers an instance of StreamDaoImpl. */
    private StreamDao streamDao;
    
    /** ClassGradeDao attribute for holding classGradeDao. */
    private ClassGradeDao classGradeDao;
    
    /** GradeDao attribute for holding gradeDao. */
    private GradeDao gradeDao;
    
    /** gradeSubjectDao attribute for holding GradeSubjectDao. */
    private GradeSubjectDao gradeSubjectDao;
    
    /** StutentStatus attribute for holding studentStatusDao. */
    private StudentStatusDao studentStatusDao;
    
    /**
     * @return the studentStatusDao
     */
    public StudentStatusDao getStudentStatusDao() {
    
        return studentStatusDao;
    }
    
    /**
     * @param studentStatusDaoObj the studentStatusDao to set
     */
    public void setStudentStatusDao(StudentStatusDao studentStatusDaoObj) {
    
        this.studentStatusDao = studentStatusDaoObj;
    }
    
    /**
     * Adds a public setter to create an instance of type schoolClassDao.
     * 
     * @param scschoolClassDao set the property of type schoolClassDao. {@inheritDoc}
     */
    public final void setschoolClassDao(final SchoolClassDao scschoolClassDao) {
    
        this.schoolClassDao = scschoolClassDao;
    }
    
    /**
     * Delete study medium.
     * 
     * @param studyMedium the study medium
     * @return the string
     * @throws AkuraAppException the sMS app exception
     * @see com.virtusa.sms.api.service.CommonService#deleteStudyMedium(com.virtusa.sms.api.dto.StudyMedium)
     */
    public String deleteStudyMedium(StudyMedium studyMedium) throws AkuraAppException {
    
        studyMediumDao.delete(studyMedium);
        
        return null;
    }
    
    /**
     * Adds a public setter to create an instance of type subjectDao.
     * 
     * @param ssubjectDao set the property of type subjectDao.
     */
    public final void setsubjectDao(final SubjectDao ssubjectDao) {
    
        this.subjectDao = ssubjectDao;
    }
    
    /**
     * save or updates study medium . If StudyMedium object contain id grater than 0, it will try to update.
     * if StudyMedium object contain medium name and id=0 it will add new entry
     * 
     * @param studyMedium - StudyMedium .
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    public void saveOrUpdateStudyMedium(StudyMedium studyMedium) throws AkuraAppException {
    
        int sMID = studyMedium.getStudyMediumId();
        String sMName = studyMedium.getStudyMediumName();
        
        if (studyMediumDao.findSameStudyMedium(sMName).isEmpty()) {
            // if system does not have similar medium
            if (sMID == 0 && sMName != null) {
                // if not the new medium already exist
                studyMediumDao.save(studyMedium);
                
            } else if (sMID > 0) {
                // update
                studyMediumDao.update(studyMedium);
            }
        } else if (sMID > 0) {
            // to update same mediums like "sinhala" to "Sinhala"
            StudyMedium crrentSM = (StudyMedium) studyMediumDao.findById(StudyMedium.class, sMID);
            if (crrentSM != null && crrentSM.getStudyMediumName().equalsIgnoreCase(sMName)) {
                studyMediumDao.update(studyMedium);
            } else {
                throw new AkuraAppException();
            }
        } else {
            // Medium exist in the db
            throw new AkuraAppException();
        }
        
    }
    
    /**
     * Adds a public setter to create an instance of type streamDao.
     * 
     * @param sstreamDao set the property of type streamDao.
     */
    public final void setstreamDao(final StreamDao sstreamDao) {
    
        this.streamDao = sstreamDao;
    }
    
    /**
     * Setter method for ClassGradeDao.
     * 
     * @param classGradeDaoValue - ClassGradeDao
     */
    public void setClassGradeDao(ClassGradeDao classGradeDaoValue) {
    
        this.classGradeDao = classGradeDaoValue;
    }
    
    /**
     * Setter method for GradeDao.
     * 
     * @param gradeDaoValue - GradeDao
     */
    public void setGradeDao(GradeDao gradeDaoValue) {
    
        this.gradeDao = gradeDaoValue;
    }
    
    /**
     * Setter method for HouseDao.
     * 
     * @param gradeSubjectDaoValue - GradeSubjectDao
     */
    public void setGradeSubjectDao(GradeSubjectDao gradeSubjectDaoValue) {
    
        this.gradeSubjectDao = gradeSubjectDaoValue;
    }
    
    /**
     * @see com.virtusa.sms.api.service.SchoolService#getClassGradeList()
     * @return list of ClassGrade references.
     * @throws AkuraAppException - throw this
     */
    @Override
    public List<ClassGrade> getClassGradeList() throws AkuraAppException {
    
        return classGradeDao.findAll(ClassGrade.class);
    }
    
    /**
     * @see com.virtusa.sms.api.service.SchoolService#getGradeList()
     * @return list of Grade references.
     * @throws AkuraAppException - throw this
     */
    @Override
    public List<Grade> getGradeList() throws AkuraAppException {
    
        return gradeDao.findAll(Grade.class);
    }
    
    /**
     * @see com.virtusa.sms.api.service.SchoolService#saveOrUpdateClassGrade(ClassGrade)
     * @param classGrade {@link ClassGrade}
     * @return list of Grade references.
     * @throws AkuraAppException - throw this
     */
    @Override
    public ClassGrade saveClassGrade(ClassGrade classGrade) throws AkuraAppException {
    
        return classGradeDao.save(classGrade);
    }
    
    /**
     * @see com.virtusa.sms.api.service.SchoolService#saveGrade(Grade grade)
     * @param grade {@link Grade}
     * @return list of Grade references.
     * @throws AkuraAppException - throw this
     */
    @Override
    public Grade saveGrade(Grade grade) throws AkuraAppException {
    
        return gradeDao.save(grade);
    }
    
    /**
     * @see com.virtusa.sms.api.service.SchoolService#getClassByGradeId(int gradeId)
     * @param grade type {@link Grade}
     * @return list of Grade references.
     * @throws AkuraAppException - throw this
     */
    public List<ClassGrade> getClassGradeListByGrade(Grade grade) throws AkuraAppException {
    
        return classGradeDao.findClassGradeListByGrade(grade);
    }
    
    /**
     * @see com.virtusa.sms.api.service.SchoolService#createReligion(Religion)
     * @param religion {@link Religion}
     * @return {@link Religion}.
     * @throws AkuraAppException - throws exception
     */
    @Override
    public Religion createReligion(Religion religion) throws AkuraAppException {
    
        return religionDao.save(religion);
    }
    
    /**
     * @see com.virtusa.sms.api.service.SchoolService#createHouse(House)
     * @param house {@link House}
     * @return {@link House}.
     * @throws AkuraAppException - throws exception
     */
    @Override
    public House createHouse(House house) throws AkuraAppException {
    
        return houseDao.save(house);
    }
    
    /**
     * {@inheritDoc}
     */
    public final SchoolClass addClass(final SchoolClass classInstance) throws AkuraAppException {
    
        return schoolClassDao.save(classInstance);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void deleteClass(final SchoolClass classInstance) throws AkuraAppException {
    
        schoolClassDao.delete(classInstance);
    }
    
    /**
     * {@inheritDoc}
     */
    public final SchoolClass findClass(final Integer classInstanceId) throws AkuraAppException {
    
        return (SchoolClass) schoolClassDao.findById(SchoolClass.class, classInstanceId);
    }
    
    /**
     * {@inheritDoc}
     */
    public final List<SchoolClass> getClassList() throws AkuraAppException {
    
        return schoolClassDao.findAll(SchoolClass.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void updateClass(final SchoolClass classInstance) throws AkuraAppException {
    
        schoolClassDao.update(classInstance);
    }
    
    /**
     * {@inheritDoc}
     */
    public final Stream addStream(final Stream stream) throws AkuraAppException {
    
        return streamDao.save(stream);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void deleteStream(final Stream stream) throws AkuraAppException {
    
        streamDao.delete(stream);
    }
    
    /**
     * {@inheritDoc}
     */
    public final Stream findStream(final Integer streamId) throws AkuraAppException {
    
        return (Stream) streamDao.findById(Stream.class, streamId);
    }
    
    /**
     * {@inheritDoc}
     */
    public final List<Stream> getStreamList() throws AkuraAppException {
    
        return streamDao.findAll(Stream.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void updateStream(final Stream stream) throws AkuraAppException {
    
        streamDao.update(stream);
    }
    
    /**
     * {@inheritDoc}
     */
    public final Subject addSubject(final Subject subject) throws AkuraAppException {
    
        return subjectDao.save(subject);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void deleteSubject(final Subject subject) throws AkuraAppException {
    
        subjectDao.delete(subject);
    }
    
    /**
     * {@inheritDoc}
     */
    public final Subject findSubject(final Integer subjectId) throws AkuraAppException {
    
        return (Subject) subjectDao.findById(Subject.class, subjectId);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void updateSubject(final Subject subject) throws AkuraAppException {
    
        subjectDao.update(subject);
    }
    
    /**
     * @see com.virtusa.sms.api.service.SchoolService#deleteHouse(HouseId)
     * @param houseId {@link int}
     * @throws AkuraAppException - throws exception
     */
    @Override
    public void deleteHouse(int houseId) throws AkuraAppException {
    
        House house = (House) houseDao.findById(House.class, houseId);
        houseDao.delete(house);
    }
    
    /**
     * @see com.virtusa.sms.api.service.SchoolService#updateHouse(HouseId)
     * @param house {@link House}
     * @throws AkuraAppException - throws exception
     */
    @Override
    public void updateHouse(House house) throws AkuraAppException {
    
        houseDao.update(house);
    }
    
    /**
     * @see com.virtusa.sms.api.service.SchoolService#findHouseByName(houseName)
     * @param houseId {@link int}
     * @return {@link House}
     * @throws AkuraAppException - throws exception
     */
    @Override
    public House findHouseById(int houseId) throws AkuraAppException {
    
        return (House) houseDao.findById(House.class, houseId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void updateReligion(Religion religion) throws AkuraAppException {
    
        religionDao.update(religion);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public Religion findReligionById(int religionId) throws AkuraAppException {
    
        return (Religion) religionDao.findById(Religion.class, religionId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void deleteReligion(int religionId) throws AkuraAppException {
    
        Religion religion = (Religion) religionDao.findById(Religion.class, religionId);
        religionDao.delete(religion);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public Grade findGradeById(int gradeId) throws AkuraAppException {
    
        return (Grade) gradeDao.findById(Grade.class, gradeId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void updateGrade(Grade grade) throws AkuraAppException {
    
        gradeDao.update(grade);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void updateClassGrade(ClassGrade classGrade) throws AkuraAppException {
    
        classGradeDao.update(classGrade);
    }
    
    /**
     * Gets the class grade by name.
     * 
     * @param description - type string
     * @return the class grade by name
     * @throws AkuraAppException the sMS app exception
     * @see com.virtusa.sms.api.service.CommonService#getClassGradeByName(java.lang.String)
     */
    public List<ClassGrade> getClassGradeByName(String description) throws AkuraAppException {
    
        return classGradeDao.getClassGradeByName(description);
    }
    
    /**
     * Returns a list of gradeSubject in a specific grade.
     * 
     * @param gradeId grade id.
     * @return a list of GradeSubject.
     * @throws AkuraAppException throws if exception occurs.
     */
    public List<GradeSubject> getGradeSubjectList(int gradeId) throws AkuraAppException {
    
        return this.gradeSubjectDao.getGradeSubjectList(gradeId);
        
    }
    
    /**
     * Returns a gradeSubject object for given grade subject id.
     * 
     * @param gradeSubjectId of type int.
     * @return a list of GradeSubject.
     * @throws AkuraAppException throws if exception occurs.
     */
    public GradeSubject findGradeSubjectList(int gradeSubjectId) throws AkuraAppException {
    
        return (GradeSubject) this.gradeSubjectDao.findById(GradeSubject.class, gradeSubjectId);
        
    }
    
    /**
     * Returns a ClassGrade object for a specific classGradeID.
     * 
     * @param classGradeId Class grade id.
     * @return a list of GradeSubject.
     * @throws AkuraAppException throws if exception occurs.
     */
    public ClassGrade findClassGrade(int classGradeId) throws AkuraAppException {
    
        return (ClassGrade) this.classGradeDao.findById(ClassGrade.class, classGradeId);
        
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public Scholarship createScholarship(Scholarship scholarship) throws AkuraAppException {
    
        return scholarshipDao.save(scholarship);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void updateScholarship(Scholarship scholarship) throws AkuraAppException {
    
        scholarshipDao.update(scholarship);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void deleteScholarship(int scholarshipId) throws AkuraAppException {
    
        Scholarship scholarship = findScholarshipById(scholarshipId);
        scholarshipDao.delete(scholarship);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public List<Scholarship> getScholarshipList() throws AkuraAppException {
    
        return scholarshipDao.findAll(Scholarship.class);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public MethodOfTravel createMethodOfTravel(MethodOfTravel methodOfTravel) throws AkuraAppException {
    
        return methodOfTravelDao.save(methodOfTravel);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void updateMethodOfTravel(MethodOfTravel methodOfTravel) throws AkuraAppException {
    
        methodOfTravelDao.update(methodOfTravel);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public MethodOfTravel findMethodOfTravelById(int methodOfTravelId) throws AkuraAppException {
    
        return (MethodOfTravel) methodOfTravelDao.findById(MethodOfTravel.class, methodOfTravelId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void deleteMethodOfTravel(int methodOfTravelId) throws AkuraAppException {
    
        MethodOfTravel methodOfTravel = findMethodOfTravelById(methodOfTravelId);
        methodOfTravelDao.delete(methodOfTravel);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public Grade getGradeByGradeName(String description) throws AkuraAppException {
    
        return gradeDao.findGradeByGradeName(description).get(0);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void deleteClassGradeList(List<ClassGrade> classGrades) throws AkuraAppException {
    
        classGradeDao.deleteClassGradeList(classGrades);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void deleteGrade(Grade grade) throws AkuraAppException {
    
        gradeDao.delete(grade);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<Subject> searchSubject(String searchDescription) throws AkuraAppException {
    
        return subjectDao.searchSubject(searchDescription);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<Stream> searchStream(String streamDescription) throws AkuraAppException {
    
        return streamDao.searchStream(streamDescription);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public List<GradeSubject> getGradeSubjectList() throws AkuraAppException {
    
        return gradeSubjectDao.findAll(GradeSubject.class);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public Term createTerm(Term term) throws AkuraAppException {
    
        return termDao.save(term);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void updateTerm(Term term) throws AkuraAppException {
    
        termDao.update(term);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public Term findTermById(int termId) throws AkuraAppException {
    
        return (Term) termDao.findById(Term.class, termId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void deleteTerm(int termId) throws AkuraAppException {
    
        Term term = findTermById(termId);
        termDao.delete(term);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public SubTerm createSubTerm(SubTerm subTerm) throws AkuraAppException {
    
        return subTermDao.save(subTerm);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void updateSubTerm(SubTerm subTerm) throws AkuraAppException {
    
        subTermDao.update(subTerm);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public SubTerm findSubTermById(int subTermId) throws AkuraAppException {
    
        return (SubTerm) subTermDao.findById(SubTerm.class, subTermId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void deleteSubTerm(int subTermId) throws AkuraAppException {
    
        SubTerm subTerm = findSubTermById(subTermId);
        subTermDao.delete(subTerm);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public List<ClassGrade> getClassGradeByGradeIdAndClassId(int gradeId, int classId) throws AkuraAppException {
    
        return classGradeDao.findClassGradeByGradeIdAndClassId(gradeId, classId);
    }
    
    /**
     * {@inheritDoc}
     */
    public GradeSubject addGradeSubject(GradeSubject gradeSubject) throws AkuraAppException {
    
        return gradeSubjectDao.save(gradeSubject);
        
    }
    
    /**
     * {@inheritDoc}
     */
    public List<GradeSubject> findGradeSubjectByDes(String gradeDes, String subjectDes) throws AkuraAppException {
    
        return gradeSubjectDao.findGradeSubjectByDes(gradeDes, subjectDes);
    }
    
    /**
     * {@inheritDoc}
     */
    public void deleteGradeSubject(GradeSubject gradSubje) throws AkuraAppException {
    
        gradeSubjectDao.delete(gradSubje);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<GradeSubject> getGradeSubjectIdListByGrade(int gradeId) throws AkuraAppException {
    
        return gradeSubjectDao.getGradeSubjectIdListByGrade(gradeId);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<GradeSubject> getGradeCoreSubjectIdListByGrade(int gradeId) throws AkuraAppException {
    
        return gradeSubjectDao.getGradeCoreSubjectIdListByGrade(gradeId);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<GradeSubject> findSubjectsByGrade(String description) throws AkuraAppException {
    
        return gradeSubjectDao.findSubjectsByGrade(description);
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean hasAlreadyAssignedSubjectsForGrade(String description) throws AkuraAppException {
    
        boolean hasAlreadyAssigned = false;
        
        if (findSubjectsByGrade(description).size() > 0) {
            hasAlreadyAssigned = true;
        }
        
        return hasAlreadyAssigned;
        
    }
    
    /**
     * {@inheritDoc}
     */
    public GradeSubject findGradeSubject(int gradeSubjectradeId) throws AkuraAppException {
    
        return (GradeSubject) gradeSubjectDao.findById(GradeSubject.class, gradeSubjectradeId);
    }
    
    /**
     * Adding warning level information to the database (Master data).
     * 
     * @param warningLevel specifies the warning level object
     * @return WarningLevel type object.
     * @throws AkuraAppException SMS Exception.
     */
    public WarningLevel addWarningLevelsInfo(WarningLevel warningLevel) throws AkuraAppException {
    
        return warningLevelDao.save(warningLevel);
    }
    
    /**
     * Editing warning level information in the database (Master data).
     * 
     * @param warningLevel specifies the warning level object
     * @throws AkuraAppException SMS Exception.
     */
    public void editWarningLevelInfo(WarningLevel warningLevel) throws AkuraAppException {
    
        warningLevelDao.update(warningLevel);
    }
    
    /**
     * Deleting warning level information in the database (Master data).
     * 
     * @param warnLevel specifies the warning level object
     * @throws AkuraAppException SMS Exception.
     */
    public void deleteWarningLevelsInfo(WarningLevel warnLevel) throws AkuraAppException {
    
        warningLevelDao.delete(warnLevel);
    }
    
    /**
     * Display warning level information given by warnLevelId.
     * 
     * @param warnLevelId specifies the warning level id, defined by an integer
     * @return WarningLevel type object
     * @throws AkuraAppException SMS Exception.
     */
    public WarningLevel viewWarnLevelInfoById(int warnLevelId) throws AkuraAppException {
    
        return (WarningLevel) warningLevelDao.findById(WarningLevel.class, warnLevelId);
    }
    
    /**
     * Viewing all the Warning Level Information available in the table.
     * 
     * @return List of WarningLevel objects
     * @throws AkuraAppException SMS Exception.
     */
    public List<WarningLevel> viewAllWarningLevelInfo() throws AkuraAppException {
    
        return warningLevelDao.findAll(WarningLevel.class);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public BloodGroup addBloodGroup(BloodGroup bloodGroup) throws AkuraAppException {
    
        return bloodGroupDao.save(bloodGroup);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public Nationality addNationality(Nationality nationality) throws AkuraAppException {
    
        return nationalityDao.save(nationality);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public Race addRace(Race race) throws AkuraAppException {
    
        return raceDao.save(race);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void deleteBloodGroup(BloodGroup bloodGroup) throws AkuraAppException {
    
        bloodGroupDao.delete(bloodGroup);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void deleteNationality(Nationality nationality) throws AkuraAppException {
    
        nationalityDao.delete(nationality);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void deleteRace(Race race) throws AkuraAppException {
    
        raceDao.delete(race);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public final BloodGroup findBloodGroup(final Integer bloodGroupId) throws AkuraAppException {
    
        return (BloodGroup) bloodGroupDao.findById(BloodGroup.class, bloodGroupId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public Nationality findNationality(Integer nationalityId) throws AkuraAppException {
    
        return (Nationality) nationalityDao.findById(Nationality.class, nationalityId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public Race findRace(Integer raceId) throws AkuraAppException {
    
        return (Race) raceDao.findById(Race.class, raceId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void updateBloodGroup(BloodGroup bloodGroup) throws AkuraAppException {
    
        bloodGroupDao.update(bloodGroup);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void updateNationality(Nationality nationality) throws AkuraAppException {
    
        nationalityDao.update(nationality);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void updateRace(Race race) throws AkuraAppException {
    
        raceDao.update(race);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public Position createPosition(Position position) throws AkuraAppException {
    
        return positionDao.save(position);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void updatePosition(Position position) throws AkuraAppException {
    
        positionDao.update(position);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public Position findPositionById(int positionId) throws AkuraAppException {
    
        return (Position) positionDao.findById(Position.class, positionId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void deletePosition(int positionId) throws AkuraAppException {
    
        Position position = findPositionById(positionId);
        positionDao.delete(position);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public List<Position> getPositionList() throws AkuraAppException {
    
        return positionDao.findAll(Position.class);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public Grading createGrading(Grading grading) throws AkuraAppException {
    
        return gradingDao.save(grading);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void updateGrading(Grading grading) throws AkuraAppException {
    
        gradingDao.update(grading);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public Grading findGradingById(int gradingId) throws AkuraAppException {
    
        return (Grading) gradingDao.findById(Grading.class, gradingId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public FaithLifeGrading findFaithLifeGradingById(int gradingId) throws AkuraAppException {
    
        return (FaithLifeGrading) faithLifeGradingDao.findById(FaithLifeGrading.class, gradingId);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public FaithLifeGrading saveFaithLifeGrading(FaithLifeGrading faithLifeGrading) throws AkuraAppException {
    
        return faithLifeGradingDao.save(faithLifeGrading);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public void deleteGrading(int gradingId) throws AkuraAppException {
    
        Grading grading = findGradingById(gradingId);
        gradingDao.delete(grading);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public List<Grading> getGradingList() throws AkuraAppException {
    
        return gradingDao.findAll(Grading.class);
    }
    
    /**
     * {@inheritDoc}
     * 
     * @throws AkuraAppException
     */
    public List<FaithLifeGrading> getFaithLifeGradingList() throws AkuraAppException {
    
        return faithLifeGradingDao.findAll(FaithLifeGrading.class);
    }
    
    /**
     * Finds the SportCategory that relevant to the given sport and sportSub.
     * 
     * @param sportId - Sport id.
     * @param sportSubId - SportSub id.
     * @return a SportCategory instance.
     * @throws AkuraAppException throws if exception occurs when finding a SportCategory instance.
     */
    public List<SportCategory> findSportCategoryObj(int sportId, int sportSubId) throws AkuraAppException {
    
        return sportCategoryDaoTarget.findSportCategory(sportId, sportSubId);
    }
    
    /**
     * Service to add a WorkingSegment object.
     * 
     * @param workingSegment WorkingSegment object to add.
     * @return workingSegment object
     * @throws AkuraAppException - throw this
     */
    public WorkingSegment createWorkingSegment(WorkingSegment workingSegment) throws AkuraAppException {
    
        return workingSegmentDao.save(workingSegment);
    }
    
    /**
     * Service to update a WorkingSegment object.
     * 
     * @param workingSegment WorkingSegment object to update
     * @throws AkuraAppException - throw this
     */
    public void updateWorkingSegment(WorkingSegment workingSegment) throws AkuraAppException {
    
        workingSegmentDao.update(workingSegment);
    }
    
    /**
     * Service to find a WorkingSegment object.
     * 
     * @param workingSegmentId WorkingSegment object to find
     * @return WorkingSegment object
     * @throws AkuraAppException - throw this
     */
    public WorkingSegment findWorkingSegmentById(int workingSegmentId) throws AkuraAppException {
    
        return (WorkingSegment) workingSegmentDao.findById(WorkingSegment.class, workingSegmentId);
    }
    
    /**
     * Service to delete a WorkingSegment object.
     * 
     * @param workingSegmentId WorkingSegment object to delete
     * @throws AkuraAppException - throw this
     */
    public void deleteWorkingSegment(int workingSegmentId) throws AkuraAppException {
    
        WorkingSegment workingSegment = findWorkingSegmentById(workingSegmentId);
        workingSegmentDao.delete(workingSegment);
    }
    
    /**
     * Service to view all WorkingSegment objects.
     * 
     * @return List of WorkingSegment returns list of WorkingSegments.
     * @throws AkuraAppException - throw this
     */
    public List<WorkingSegment> getWorkingSegmentList() throws AkuraAppException {
    
        return workingSegmentDao.findAll(WorkingSegment.class);
    }
    
    /**
     * Service to find a donationType object.
     * 
     * @param donationTypeId DonationType object to find
     * @return donationType object
     * @throws AkuraAppException - throw this
     */
    public DonationType findDonationType(final int donationTypeId) throws AkuraAppException {
    
        return (DonationType) this.donationTypeDao.findById(DonationType.class, donationTypeId);
    }
    
    /**
     * Service to modify a donationType object.
     * 
     * @param donationType DonationType object to update
     * @throws AkuraAppException - throw this
     */
    public void modifyDonationType(final DonationType donationType) throws AkuraAppException {
    
        this.donationTypeDao.update(donationType);
    }
    
    /**
     * Service to add a donationType object.
     * 
     * @param donationType DonationType object to add.
     * @return donationType object
     * @throws AkuraAppException - throw this
     */
    public DonationType addDonationType(final DonationType donationType) throws AkuraAppException {
    
        return (DonationType) this.donationTypeDao.save(donationType);
    }
    
    /**
     * Service to delete a donationType object.
     * 
     * @param donationType DonationType object to delete
     * @throws AkuraAppException - throw this
     */
    public void deleteDonationType(final DonationType donationType) throws AkuraAppException {
    
        this.donationTypeDao.delete(donationType);
    }
    
    /**
     * Service to view all donationType objects.
     * 
     * @return returns list of Donations.
     * @throws AkuraAppException - throw this
     */
    public List<DonationType> viewAllDonationType() throws AkuraAppException {
    
        return this.donationTypeDao.findAll(DonationType.class);
    }
    
    /**
     * Delete grade subject list.
     * 
     * @param gradeSubjectList the grade subject list
     * @throws AkuraAppException the akura app exception
     */
    public void deleteGradeSubjectList(List<GradeSubject> gradeSubjectList) throws AkuraAppException {
    
        gradeSubjectDao.deleteGradeSubjectList(gradeSubjectList);
        
    }
    
    /**
     * Service to view all WorkingSegment objects.
     * 
     * @return List of WorkingSegment returns list of WorkingSegments.
     * @throws AkuraAppException - throw this
     */
    public List<ParticipantCategory> getParticipantCategoryList() throws AkuraAppException {
    
        return participantCategoryDao.findAll(ParticipantCategory.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<SpecialEventsParticipation> getSpecialEventParticipationListById(int selectSpecialEvent)
            throws AkuraAppException {
    
        return specialEventsParticipationDao.getSpecialEventParticipationListById(selectSpecialEvent);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<SpecialEvents> getSpecialEventList() throws AkuraAppException {
    
        return specialEventsDao.findAll(SpecialEvents.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public SpecialEventsParticipation getSpecialEventsParticipation(int participationId) throws AkuraAppException {
    
        return (SpecialEventsParticipation) specialEventsParticipationDao.findById(SpecialEventsParticipation.class,
                participationId);
    }
    
    /**
     * Gets the available subjects list.
     * 
     * @param selectedSubjectsIdList the selected subjects id list
     * @return the available subjects list
     * @throws AkuraAppException the akura app exception
     */
    public List<Subject> getAvailableSubjectsList(List<Integer> selectedSubjectsIdList) throws AkuraAppException {
    
        return subjectDao.getAvailableSubjectsList(selectedSubjectsIdList);
    }
    
    /**
     * {@inheritDoc}
     */
    public SpecialEvents getSpecialEventById(int selectSpecialEvent) throws AkuraAppException {
    
        return (SpecialEvents) specialEventsDao.findById(SpecialEvents.class, selectSpecialEvent);
    }
    
    /**
     * Service to add SpecialEvents instance.
     * 
     * @param specialEvents SpecialEvents instance.
     * @return - the saved SpecialEvents object.
     * @throws AkuraAppException throws if exception occurs when saving a SpecialEvents instance.
     */
    @Override
    public SpecialEvents addSpecialEvents(SpecialEvents specialEvents) throws AkuraAppException {
    
        return specialEventsDao.save(specialEvents);
    }
    
    /**
     * Service to edit SpecialEvents instance.
     * 
     * @param specialEvents SpecialEvents instance.
     * @throws AkuraAppException throws if exception occurs when editing a SpecialEvents instance.
     */
    @Override
    public void editSpecialEvents(SpecialEvents specialEvents) throws AkuraAppException {
    
        specialEventsDao.update(specialEvents);
    }
    
    /**
     * Deletes the relevant SpecialEvents object.
     * 
     * @param specialEventsId - id of SpecialEvents.
     * @throws AkuraAppException - if error occurs when deleting a SpecialEvents instance.
     */
    @Override
    public void deleteSpecialEvents(int specialEventsId) throws AkuraAppException {
    
        SpecialEvents specialEvents = this.findSpecialEventsById(specialEventsId);
        
        List<SpecialEventsParticipation> specialEventsParticipations =
                specialEventsParticipationDao.getParticipantListBySpecialEvent(specialEvents);
        
        for (SpecialEventsParticipation sep : specialEventsParticipations) {
            
            specialEventsParticipationDao.delete(sep);
        }
        
        specialEventsDao.delete(specialEvents);
        
    }
    
    /**
     * Finds the specialEvents that relevant to the specialEvents id.
     * 
     * @param specialEventsId - SpecialEvents id.
     * @return a SpecialEvents instance.
     * @throws AkuraAppException throws if exception occurs when finding a SpecialEvents instance.
     */
    @Override
    public SpecialEvents findSpecialEventsById(int specialEventsId) throws AkuraAppException {
    
        return (SpecialEvents) specialEventsDao.findById(SpecialEvents.class, specialEventsId);
    }
    
    /**
     * Retrieve the available SpecialEvents object.
     * 
     * @param date - date.
     * @param specialEvents - String.
     * @return SpecialEvents object.
     * @throws AkuraAppException - throw detailed exception.
     */
    @Override
    public SpecialEvents getAnySpecialEvents(String specialEvents, Date date) throws AkuraAppException {
    
        return specialEventsDao.getAnySpecialEventsByName(specialEvents, date);
    }
    
    /**
     * Check whether specialEvent is already added.
     * 
     * @param specialEvent - SpecialEvents
     * @return true if it already exist else false
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public boolean isExistsSpecialEvents(SpecialEvents specialEvent) throws AkuraAppException {
    
        Date date = specialEvent.getDate();
        String event = specialEvent.getName().trim();
        SpecialEvents specialEvents = getAnySpecialEvents(event, date);
        
        boolean isExists = false;
        
        if (specialEvents != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Service to add SpecialEventsParticipation instance.
     * 
     * @param specialEventsParticipation SpecialEventsParticipation instance.
     * @return - the saved SpecialEventsParticipation object.
     * @throws AkuraAppException throws if exception occurs when saving a SpecialEventsParticipation instance.
     */
    public SpecialEventsParticipation addSpecialEventsParticipation(
            SpecialEventsParticipation specialEventsParticipation) throws AkuraAppException {
    
        return specialEventsParticipationDao.save(specialEventsParticipation);
    }
    
    /**
     * Retrieve all the participation list for any given special events.
     * 
     * @param event SpecialEvents object
     * @return list of SpecialEventsParticipation.
     * @throws AkuraAppException - throws if exception occurs when retrieving SpecialEventsParticipation
     */
    @Override
    public List<SpecialEventsParticipation> getParticipantListBySpecialEvent(SpecialEvents event)
            throws AkuraAppException {
    
        return specialEventsParticipationDao.getParticipantListBySpecialEvent(event);
        
    }
    
    /**
     * Deletes the relevant specialEventsParticipation object.
     * 
     * @param specialEventsParticipationObj - SpecialEventsParticipation.
     * @throws AkuraAppException - if error occurs when deleting a specialEventsParticipation instance.
     */
    @Override
    public void deleteSpecialEventsParticipation(SpecialEventsParticipation specialEventsParticipationObj)
            throws AkuraAppException {
    
        specialEventsParticipationDao.delete(specialEventsParticipationObj);
    }
    
    /**
     * Update grade subject.
     * 
     * @param gradeSubject the grade subject
     * @throws AkuraAppException the akura app exception
     */
    public void updateGradeSubject(GradeSubject gradeSubject) throws AkuraAppException {
    
        gradeSubjectDao.update(gradeSubject);
        
    }
    
    /**
     * {@inheritDoc}
     */
    public final Assignment addAssignment(final Assignment classInstance) throws AkuraAppException {
    
        return assignmentDao.save(classInstance);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void deleteAssignment(final Assignment classInstance) throws AkuraAppException {
    
        assignmentDao.delete(classInstance);
    }
    
    /**
     * {@inheritDoc}
     */
    public final Assignment findAssignmentById(final Integer classInstanceId) throws AkuraAppException {
    
        return (Assignment) assignmentDao.findById(Assignment.class, classInstanceId);
    }
    
    /**
     * {@inheritDoc}
     */
    public final List<Assignment> getAssignmentList() throws AkuraAppException {
    
        return assignmentDao.findAll(Assignment.class);
    }
    
    /**
     * {@inheritDoc}
     */
    public final void updateAssignment(final Assignment classInstance) throws AkuraAppException {
    
        assignmentDao.update(classInstance);
    }
    
    /**
     * {@inheritDoc}
     */
    public String getReportGeneratedDate(String locales) throws AkuraAppException {
    
        Locale locale = new Locale(locales);
        DateFormat formatter = new SimpleDateFormat("E dd MMM yyyy", locale);
        String date = formatter.format(new Date());
        return date;
    }
    
    /**
     * service to delete a Exam.
     * 
     * @param exam the id of the Exam object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete a PrefectType instances.
     */
    public void deleteExam(Exam exam) throws AkuraAppException {
    
        examDao.delete(exam);
    }
    
    /**
     * Finds the Exam that relevant to the Exam id.
     * 
     * @param examId - exam id.
     * @return a exam instance.
     * @throws AkuraAppException throws if exception occurs when find a Exam instance.
     */
    public final Exam findExam(final Integer examId) throws AkuraAppException {
    
        return (Exam) examDao.findById(Exam.class, examId);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<Exam> getExamList() throws AkuraAppException {
    
        return examDao.findAll(Exam.class);
    }
    
    /**
     * Update exams.
     * 
     * @param exam the Exam.
     * @throws AkuraAppException when exception occurs
     */
    public void updateExam(Exam exam) throws AkuraAppException {
    
        examDao.update(exam);
    }
    
    /**
     * Get Exam object by examId.
     * 
     * @param examId exam id defined by integer type object
     * @return a Exam object.
     * @throws AkuraAppException throws if exception occurs
     */
    public Exam findExamById(int examId) throws AkuraAppException {
    
        return (Exam) examDao.findById(Exam.class, examId);
    }
    
    /**
     * service to add a Exam.
     * 
     * @param exam the id of the Exam object to add
     * @throws AkuraAppException - The exception details that occurred when add a Exam instance.
     * @return a Exam instance
     */
    public Exam addExam(Exam exam) throws AkuraAppException {
    
        return examDao.save(exam);
    }
    
    /** {@inheritDoc} */
    public ExamSubject addExamSubject(ExamSubject examSubject) throws AkuraAppException {
    
        return examSubjectDao.save(examSubject);
    }
    
    /** {@inheritDoc} */
    public void deleteExamSubject(ExamSubject examSubject) throws AkuraAppException {
    
        examSubjectDao.delete(examSubject);
    }
    
    /** {@inheritDoc} */
    public ExamSubject findExamSubject(int examSubjectId) throws AkuraAppException {
    
        return (ExamSubject) examSubjectDao.findById(ExamSubject.class, examSubjectId);
    }
    
    /** {@inheritDoc} */
    public List<ExamSubject> getAllExamSubjectList() throws AkuraAppException {
    
        return examSubjectDao.findAll(ExamSubject.class);
    }
    
    /** {@inheritDoc} */
    public void updateExamSubject(ExamSubject examSubject) throws AkuraAppException {
    
        examSubjectDao.update(examSubject);
    }
    
    /** {@inheritDoc} */
    public List<ExamSubject> findSubjectsByExam(String description) throws AkuraAppException {
    
        return examSubjectDao.findSubjectsByExam(description);
    }
    
    /** {@inheritDoc} */
    public List<ExamSubject> findExamSubjectByDes(final String examDescription, final String subjectDescription)
            throws AkuraAppException {
    
        return examSubjectDao.findExamSubjectByDes(examDescription, subjectDescription);
    }
    
    /** {@inheritDoc} */
    public boolean isExistExamSubject(final int examId, final Integer subjectId) throws AkuraAppException {
    
        List<ExamSubject> examSubjectList = examSubjectDao.isExistExamSubject(examId, subjectId);
        return examSubjectList.isEmpty() ? false : true;
    }
    
    /** {@inheritDoc} */
    public Exam getExamByExamName(final String examDescription) throws AkuraAppException {
    
        List<Exam> examList = examDao.getExamByExamName(examDescription);
        return (Exam) (examList.isEmpty() ? 0 : examList.get(0));
    }
    
    /** {@inheritDoc} */
    public List<ExamSubject> getExamSubjectIdListByExam(final int examId) throws AkuraAppException {
    
        return examSubjectDao.getExamSubjectIdListByExam(examId);
    }
    
    /** {@inheritDoc} */
    public void deleteExamSubjectList(List<ExamSubject> examSubjectIdList) throws AkuraAppException {
    
        examSubjectDao.deleteExamSubjectList(examSubjectIdList);
    }
    
    /** {@inheritDoc} */
    public int findExamSubjectByExamId(final int examId) throws AkuraAppException {
    
        List<Integer> examIdList = examSubjectDao.findExamSubjectByExamId(examId);
        return !examIdList.isEmpty() ? examIdList.get(0) : 0;
    }
    
    /**
     * Service to add a EmploymentStatus to module.
     * 
     * @param employmentStatus the EmploymentStatus object to add.
     * @return a employmentStatus instance.
     * @throws AkuraAppException - The exception details that occurred when adding an EmploymentStatus
     *         instances.
     */
    public EmploymentStatus addEmploymentStatus(EmploymentStatus employmentStatus) throws AkuraAppException {
    
        return employmentStatusDao.save(employmentStatus);
    }
    
    /**
     * Service to delete a EmploymentStatus.
     * 
     * @param employmentStatus - the EmploymentStatus object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete a EmploymentStatus
     *         instances.
     */
    public void deleteEmploymentStatus(EmploymentStatus employmentStatus) throws AkuraAppException {
    
        employmentStatusDao.delete(employmentStatus);
    }
    
    /**
     * Service to update an already existing EmploymentStatus object.
     * 
     * @param employmentStatus - the EmploymentStatus object to update
     * @throws AkuraAppException - The exception details that occurred when updating a EmploymentStatus
     *         instance.
     */
    public void updateEmploymentStatus(EmploymentStatus employmentStatus) throws AkuraAppException {
    
        employmentStatusDao.update(employmentStatus);
    }
    
    /**
     * Get EmploymentStatus object by EmploymentStatusId.
     * 
     * @param employmentStatusId employment status id defined by integer type object
     * @return a EmploymentStatus object.
     * @throws AkuraAppException throws if exception occurs when finding an EmploymentStatus instance.
     */
    public EmploymentStatus findEmploymentStatusById(int employmentStatusId) throws AkuraAppException {
    
        return (EmploymentStatus) employmentStatusDao.findById(EmploymentStatus.class, employmentStatusId);
    }
    
    /**
     * Service to view all employmentStatus objects.
     * 
     * @return returns list of all Employment Statuses
     * @throws AkuraAppException - throw this
     */
    public List<EmploymentStatus> findAllEmploymentStatus() throws AkuraAppException {
    
        return this.employmentStatusDao.findAll(EmploymentStatus.class);
    }
    
    /** {@inheritDoc} */
    public Integer findGradingByDes(final String marks) throws AkuraAppException {
    
        List<Integer> gradingIdList = gradingDao.findGradingByDes(marks);
        return !gradingIdList.isEmpty() ? gradingIdList.get(0) : null;
    }
    
    /** {@inheritDoc} */
    public int getCountExamSubjects(int examId) throws AkuraAppException {
    
        List<Long> examSubjectCount = examSubjectDao.getCountExamSubjects(examId);
        return examSubjectCount.get(0).intValue();
    }
    
    /** {@inheritDoc} */
    public List<Integer> getNewSubjects(final int examId, int year) throws AkuraAppException {
    
        return examSubjectDao.getNewSubjects(examId, year);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExistAssignmentGradeSubject(final int gradeSubjectId, final String assignmentName, final int year,
            final boolean marksType) throws AkuraAppException {
    
        List<Assignment> assignmentGradeSubjectList =
                assignmentDao.isExistAssignmentGradeSubject(gradeSubjectId, assignmentName, year, marksType);
        return assignmentGradeSubjectList.isEmpty() ? false : true;
    }
    
    /**
     * {@inheritDoc}
     */
    public List<Country> getCountryList() throws AkuraAppException {
    
        return countryDao.findAll(Country.class);
    }
    
    /**
     * Returns a list of Section instances.
     * 
     * @return a list of Section instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Section instances.
     */
    // public List<Section> getSectionsList() throws AkuraAppException {
    //
    // return sectionDao.findAll(Section.class);
    // }
    
    /**
     * Retrieve the available Sport object.
     * 
     * @param sportName - Sport's name.
     * @return Sport object.
     * @throws AkuraAppException - throw detailed exception.
     */
    public Sport getAnySport(String sportName) throws AkuraAppException {
    
        return sportDaoTarget.getAnySportByName(sportName);
    }
    
    /**
     * Returns a list of Race instances.
     * 
     * @return a list of Race instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Race instances.
     */
    public List<Race> getRaceList() throws AkuraAppException {
    
        return raceDao.findAll(Race.class);
    }
    
    /**
     * Gets the assignments list by grade.
     * 
     * @param classGradeId the class grade id
     * @return the assignments list by grade
     * @throws AkuraAppException the akura app exception
     */
    public List<Object[]> getAssignmentsListByGrade(int classGradeId) throws AkuraAppException {
    
        return assignmentDao.getAssignmentsByGrade(classGradeId);
    }
    
    /**
     * Gets the class grade by staff registration no.
     * 
     * @param registrationNo the registration no
     * @return the class grade by staff registration no
     * @throws AkuraAppException the akura app exception
     */
    public List<ClassGrade> getClassGradeByStaffRegistrationNo(String registrationNo) throws AkuraAppException {
    
        return classGradeDao.getClassGradeByStaffRegistrationNo(registrationNo);
    }
    
    /**
     * {@inheritDoc}
     */
    public Country findCountry(int countryId) throws AkuraAppException {
    
        return (Country) countryDao.findById(Country.class, countryId);
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public StudyMedium getStudyMediumById(int studyMediumId) throws AkuraAppException {
    
        return (StudyMedium) studyMediumDao.findById(StudyMedium.class, studyMediumId);
        
    }
    
    /**
     * Gets the grade subject list by class grade id.
     * 
     * @param classGradeId the class grade id
     * @return the grade subject list by class grade id
     * @throws AkuraAppException the akura app exception
     */
    public List<GradeSubject> getGradeSubjectListByClassGradeId(int classGradeId) throws AkuraAppException {
    
        return gradeSubjectDao.getGradeSubjectListByClassGradeId(classGradeId);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<GradeSubject> getGradeSubjectByGradeAndSubject(int gradeId, int subjectId) throws AkuraAppException {
    
        return gradeSubjectDao.findSubjectsByGradeAndSubjectId(gradeId, subjectId);
    }
    
    /** {@inheritDoc} */
    public boolean isExistStaff(final int userId, final String classGradeKey) throws AkuraAppException {
    
        List<?> classGrade = classGradeDao.isExistStaff(userId, classGradeKey);
        return classGrade.isEmpty() ? false : true;
    }
    
    /** {@inheritDoc} */
    public List<?> getOptionalSubjectList(int examId) throws AkuraAppException {
    
        return subjectDao.getOptionalSubjectList(examId);
    }
    
    /** {@inheritDoc} */
    public List<?> getSubjectListByExam(final int selectedExamId) throws AkuraAppException {
    
        return subjectDao.getSubjectListByExam(selectedExamId);
    }
    
    /** {@inheritDoc} */
    public boolean isAlreadyExistExamSubject(final int examId) throws AkuraAppException {
    
        List<Integer> examSubjectList = examSubjectDao.isAlreadyExistExamSubject(examId);
        return examSubjectList.isEmpty() ? false : true;
    }
    
    /** {@inheritDoc} */
    @Override
    public List<ClassGrade> getClassGradeListByClassId(int classId) throws AkuraAppException {
    
        return classGradeDao.findClassGradeListByClassId(classId);
        
    }
    
    /** {@inheritDoc} */
    public boolean isExistPublication(final String header) throws AkuraAppException {
    
        List<Publication> publicationList = publicationDao.isExistPublication(header);
        return publicationList.isEmpty() ? false : true;
    }
    
    /** {@inheritDoc} */
    public List<AuditEventType> getAuditEventType() throws AkuraAppException {
    
        return (List<AuditEventType>) auditEventTypeDao.findAll(AuditEventType.class);
    }
    
    /** {@inheritDoc} */
    public boolean isExistExamMarks(int examSubjectId) throws AkuraAppException {
    
        List<BigInteger> examMarksListCount = examSubjectDao.isExistExamMarks(examSubjectId);
        return examMarksListCount.get(0).intValue() > 0 ? true : false;
    }
    
    /**
     * Deletes the relevant specialEventsParticipation object list.
     * 
     * @param specialEventsParticipationList - SpecialEventsParticipation list.
     * @throws AkuraAppException - if error occurs when deleting a specialEventsParticipation instance.
     */
    @Override
    public void deleteAllSpecialEventsParticipation(List<SpecialEventsParticipation> specialEventsParticipationList)
            throws AkuraAppException {
    
        specialEventsParticipationDao.deleteAllSpecialEventsParticipation(specialEventsParticipationList);
    }
    
    /**
     * Retrieve the available Race object.
     * 
     * @param raceName - racename.
     * @return Race object.
     * @throws AkuraAppException - throw detailed exception.
     */
    public Race getAnyRace(String raceName) throws AkuraAppException {
    
        return raceDao.getRaceByName(raceName);
    }
    
    /**
     * Retrieve the available religionDao object.
     * 
     * @param description - String.
     * @return Religion object.
     * @throws AkuraAppException - throw detailed exception.
     */
    @Override
    public Religion getAnyReligion(String description) throws AkuraAppException {
    
        return religionDao.getReligionByDescription(description);
    }
    
    /**
     * Check whether religion is already added.
     * 
     * @param description - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsReligion(String description) throws AkuraAppException {
    
        Religion religion = getAnyReligion(description);
        boolean isExists = false;
        
        if (religion != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Check whether religion is already added.
     * 
     * @param description - String
     * @param religionId - int
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsReligion(String description, int religionId) throws AkuraAppException {
    
        boolean isExists = false;
        List<Religion> religions = this.getReligionList();
        
        for (Religion religion : religions) {
            // boolean check = religion.getDescription().equalsIgnoreCase(description);
            
            boolean check =
                    (religionId == 0) ? religion.getDescription().equalsIgnoreCase(description) : religion
                            .getDescription().equals(description);
            
            if (check) {
                isExists = check;
                break;
            }
        }
        return isExists;
    }
    
    /**
     * Check whether DonationType is already added.
     * 
     * @param donationTypeName - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsDonationType(String donationTypeName) throws AkuraAppException {
    
        DonationType donationTypeDescription = donationTypeDao.getDonationTypeByName(donationTypeName);
        boolean isExists = false;
        
        if (donationTypeDescription != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Check whether race already exist.
     * 
     * @param raceName - type string
     * @return true if the race exist else false.
     * @throws AkuraAppException - Throw detailed exception.
     */
    public boolean isRaceExist(String raceName) throws AkuraAppException {
    
        Race race = raceDao.getRaceByName(raceName);
        boolean isExists = false;
        
        if (race != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Check whether the country is exists.
     * 
     * @param countryId - Integer
     * @param description - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the Country object.
     */
    public boolean isExistsCountry(String description, int countryId) throws AkuraAppException {
    
        Country country = countryDao.getAnyCountryByName(description);
        boolean isExists = false;
        
        if (country != null) {
            if (countryId == 0) {
                isExists = true;
            }
            if (countryId != 0) {
                if (!findCountry(countryId).getCountryName().equalsIgnoreCase(description)) {
                    isExists = true;
                } else {
                    isExists = false;
                }
                
            }
        }
        
        return isExists;
    }
    
    /**
     * Check whether holiday already exist.
     * 
     * @param holidayName - Holiday
     * @return true if the holiday exist else false.
     * @throws AkuraAppException - Throw detailed exception.
     */
    
    public boolean isExistHoliday(Holiday holidayName) throws AkuraAppException {
    
        Date startDate = holidayName.getStartDate();
        Date endDate = holidayName.getEndDate();
        String holidayName1 = holidayName.getDescription().trim();
        
        Holiday holiday = holidayDao.getHolidayByName(holidayName1, startDate, endDate);
        boolean isExists = false;
        
        if (holiday != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Check whether holiday already exist.
     * 
     * @param holidayName - Holiday
     * @return true if the holiday exist else false.
     * @throws AkuraAppException - Throw detailed exception.
     */
    
    public boolean isExistHolidayByDate(Holiday holidayName) throws AkuraAppException {
    
        Date startDate = holidayName.getStartDate();
        Date endDate = holidayName.getEndDate();
        
        Holiday holiday =
                holidayDao.getHolidayListbyStartDateAndEndDate(startDate, endDate, startDate, endDate, startDate,
                        endDate);
        boolean isExists = false;
        
        if (holiday != null) {
            isExists = true;
        }
        return isExists;
    }
    
    /**
     * Check whether term already exist.
     * 
     * @param termName - String
     * @param termId - integer
     * @param startDate start date
     * @param endDate end date
     * @return true if the term exist else false.
     * @throws AkuraAppException - Throw detailed exception.
     */
    
    public boolean isExistsTerm(Term termName, int termId, Date startDate, Date endDate) throws AkuraAppException {
    
        Date startDate1 = termName.getFromMonth();
        Date endDate1 = termName.getToMonth();
        String termName1 = termName.getDescription().trim();
        Term term = termDao.getTermByName(termName1, startDate1, endDate1);
        boolean isExists = false;
        
        if (term != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Check whether the SportSub is exists.
     * 
     * @param sportSubName - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the SportSub object.
     */
    public boolean isExistsSportSub(String sportSubName) throws AkuraAppException {
    
        SportSub sportSubDes = sportSubDaoTarget.getAnySportSubByDes(sportSubName);
        boolean isExists = false;
        
        if (sportSubDes != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Check whether the Method Of Travel is exists.
     * 
     * @param description - String
     * @throws AkuraAppException - Throw detailed exception.
     * @return returns the MethodOfTravel object.
     */
    public boolean isExistsMethodOfTravel(String description) throws AkuraAppException {
    
        MethodOfTravel methodOfTravel = methodOfTravelDao.getMethodOfTravelByName(description);
        boolean isExists = false;
        
        if (methodOfTravel != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Check whether sport is already added.
     * 
     * @param sportName - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsSport(String sportName) throws AkuraAppException {
    
        Sport sport = getAnySport(sportName);
        boolean isExists = false;
        
        if (sport != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Check whether the Working Segment is exists.
     * 
     * @param description - String
     * @throws AkuraAppException - Throw detailed exception.
     * @return returns the WorkingSegment object.
     */
    public boolean isExistsWorkingSegment(String description) throws AkuraAppException {
    
        WorkingSegment workingSegment = workingSegmentDao.getWorkingSegmentByName(description);
        boolean isExists = false;
        
        if (workingSegment != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Check whether the Warning Level is exists.
     * 
     * @param warningLevelObj - warningLevelObj
     * @throws AkuraAppException Detailed exception
     * @return returns a boolean value.
     */
    @Override
    public boolean isExistWarningLevel(WarningLevel warningLevelObj) throws AkuraAppException {
    
        boolean isExists = false;
        if (warningLevelObj.getWarningLevelId() == 0) {
            if (this.warningLevelDao.isExistWarningLevel(warningLevelObj.getDescription(), warningLevelObj.getColor())) {
                isExists = true;
            }
        } else {
            if (this.warningLevelDao.isExistWarningLevelDescription(warningLevelObj.getDescription())
                    && (!warningLevelObj.getDescription().equals(
                            findWarningLevelById(warningLevelObj.getWarningLevelId()).getDescription()) || this.warningLevelDao
                            .isExistWarningLevelColor(warningLevelObj.getColor()) == true)) {
                
                if (warningLevelObj.getDescription().equalsIgnoreCase(
                        findWarningLevelById(warningLevelObj.getWarningLevelId()).getDescription())) {
                    isExists = false;
                } else {
                    isExists = true;
                }
                
            }
            if (this.warningLevelDao.isExistWarningLevelColor(warningLevelObj.getColor())
                    && (!warningLevelObj.getColor().equals(
                            findWarningLevelById(warningLevelObj.getWarningLevelId()).getColor()) || this.warningLevelDao
                            .isExistWarningLevelDescription(warningLevelObj.getDescription()) == true)) {
                
                if (warningLevelObj.getDescription().equalsIgnoreCase(
                        findWarningLevelById(warningLevelObj.getWarningLevelId()).getDescription())
                        && warningLevelObj.getColor().equals(
                                findWarningLevelById(warningLevelObj.getWarningLevelId()).getColor())) {
                    isExists = false;
                } else {
                    isExists = true;
                }
            }
        }
        
        return isExists;
    }
    
    /**
     * Check whether the Position is exists.
     * 
     * @param positionObj - positionObj
     * @throws AkuraAppException Detailed exception
     * @return returns a boolean value.
     */
    
    public boolean isExistPosition(Position positionObj) throws AkuraAppException {
    
        boolean isExist = false;
        if (positionDao.isExistPosition(positionObj.getDescription())) {
            isExist = true;
        }
        return isExist;
    }
    
    /**
     * Check whether the grading information is exists.
     * 
     * @param strGradeAcronym - String
     * @param decription - String
     * @throws AkuraAppException AkuraAppException
     * @return true if it already exist else false.
     */
    public boolean isExistsGradingInformation(String strGradeAcronym, String decription) throws AkuraAppException {
    
        Grading grading = gradingDao.getAnyGradeByInformation(strGradeAcronym, decription);
        boolean isExists = false;
        
        if (grading != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Check whether the grading information is exists.
     * 
     * @param gradingId -Integer
     * @param decription - String
     * @throws AkuraAppException AkuraAppException
     * @return true if it already exist else false.
     */
    public boolean isExistsGradingDecription(String decription, int gradingId) throws AkuraAppException {
    
        Grading grading = gradingDao.getAnyGradeByDecription(decription);
        boolean isExists = false;
        
        if (grading != null) {
            if (gradingId == 0) {
                isExists = true;
            }
            if (gradingId != 0) {
                if (!findGradingById(gradingId).getDescription().equalsIgnoreCase(decription)) {
                    isExists = true;
                } else {
                    isExists = false;
                }
                
            }
        }
        return isExists;
    }
    
    /**
     * Check whether the grading information is exists.
     * 
     * @param gradingId -Integer
     * @param strGradeAcronym - String
     * @throws AkuraAppException AkuraAppException
     * @return true if it already exist else false.
     */
    public boolean isExistsGradingAcronym(String strGradeAcronym, int gradingId) throws AkuraAppException {
    
        Grading grading = gradingDao.getAnyGradeByAcronym(strGradeAcronym);
        boolean isExists = false;
        
        if (grading != null) {
            if (gradingId == 0) {
                isExists = true;
            }
            if (gradingId != 0) {
                if (!findGradingById(gradingId).getGradeAcronym().equalsIgnoreCase(strGradeAcronym)) {
                    isExists = true;
                } else {
                    isExists = false;
                }
                
            }
        }
        return isExists;
    }
    
    /**
     * Check whether SubTerm is already added.
     * 
     * @param subTermName - String
     * @param termId - Integer
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsSubTerm(int termId, String subTermName) throws AkuraAppException {
    
        SubTerm subterm = subTermDao.getAnySubTermByName(termId, subTermName);
        boolean isExists = false;
        
        if (subterm != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Check whether SubTerm is already added.
     * 
     * @param toDate - Date
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsSubTermToDate(Date toDate) throws AkuraAppException {
    
        SubTerm subterm = subTermDao.getAnySubTermByToDate(toDate);
        boolean isExists = false;
        
        if (subterm != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Check whether SubTerm is already added.
     * 
     * @param fromDate - Date
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsSubTermFromDate(Date fromDate) throws AkuraAppException {
    
        SubTerm subterm = subTermDao.getAnySubTermFromDate(fromDate);
        boolean isExists = false;
        
        if (subterm != null) {
            isExists = true;
        }
        
        return isExists;
    }
    
    /**
     * Check whether sportCategory is already added.
     * 
     * @param intSport - integer
     * @param intSportSub - integer
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    public boolean isExistsSportCategory(int intSport, int intSportSub) throws AkuraAppException {
    
        SportCategory sportCategory = sportCategoryDaoTarget.getSportCategoryById(intSport, intSportSub);
        boolean isExists = false;
        
        if (sportCategory != null) {
            isExists = true;
        }
        return isExists;
    }
    
    /**
     * Method is to Section reference data.
     * 
     * @return list of Student Status references.
     * @throws AkuraAppException - throw this
     */
    @Override
    public List<StudentStatus> getStudentStatusList() throws AkuraAppException {
    
        return studentStatusDao.findAll(StudentStatus.class);
    }
    
    /**
     * Method is to get Student status.
     * 
     * @return StudentStatus list.
     * @throws AkuraAppException - throw this
     */
    public List<StudentStatus> getTerminateStatus() throws AkuraAppException {
    
        return studentStatusDao.getStudentStatusList();
    }
    
    /** {@inheritDoc} */
    public int findGradeSubjectMaxMarkById(final int gradeSubjectId) throws AkuraAppException {
    
        List<Integer> maxMarksList = gradeSubjectDao.findGradeSubjectMaxMarkById(gradeSubjectId);
        return maxMarksList.isEmpty() ? 0 : maxMarksList.get(0);
    }
    
    /** {@inheritDoc} */
    public GradeSubject findGradeSubjectByGradeAndSubjectId(int subjectId, String gradeDescription)
            throws AkuraAppException {
    
        List<GradeSubject> gradeSubjectList =
                gradeSubjectDao.findGradeSubjectByGradeAndSubjectId(subjectId, gradeDescription);
        return gradeSubjectList.get(0);
    }
    
    /** {@inheritDoc} */
    @Override
    public String getSponsorship(String scholarshipName) throws AkuraAppException {
    
        String sponsorship = AkuraConstant.EMPTY_STRING;
        List<String> nameList = scholarshipDao.findSponsorship(scholarshipName);
        
        if ((nameList.get(AkuraConstant.PARAM_INDEX_ZERO)) != null) {
            sponsorship = nameList.get(AkuraConstant.PARAM_INDEX_ZERO);
        }
        return sponsorship;
    }
    
    /**
     * return list of holiday already exist.
     * 
     * @param holidayObj - Holiday
     * @return List of holiday exist else null.
     * @throws AkuraAppException - Throw detailed exception.
     */
    
    public boolean getHolidayListWithInDateRange(Holiday holidayObj) throws AkuraAppException{
        Date startDate = holidayObj.getStartDate();
        Date endDate = holidayObj.getEndDate();
        boolean found= false;
        List<Holiday> holidayLst =
                holidayDao.getHolidayListWithInDateRange(startDate, endDate, startDate, endDate, startDate,
                        endDate);
        if(holidayLst.size() == 1){
            Iterator holidayIteObj = holidayLst.iterator();
            while(holidayIteObj.hasNext()){
                
                Holiday hol = (Holiday) holidayIteObj.next();
                if(hol.getHolidayId() == holidayObj.getHolidayId()){
                    found = true;
                }
               
            }
        }
        
        
        
        return found;
    }
}
