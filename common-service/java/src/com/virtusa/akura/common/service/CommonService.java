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

package com.virtusa.akura.common.service;

import java.util.Date;
import java.util.List;

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
import com.virtusa.akura.api.dto.StudentStatus;
import com.virtusa.akura.api.dto.StudyMedium;
import com.virtusa.akura.api.dto.SubTerm;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.dto.WarningLevel;
import com.virtusa.akura.api.dto.WorkingSegment;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.EmploymentStatusDao;

/**
 * CommonService contains all reference data that is common and can be shared.
 * 
 * @author Virtusa Corporation
 */
public interface CommonService {
    
    /**
     * Method is to Religion reference data.
     * 
     * @return religion list.
     * @throws AkuraAppException - Detailed exception.
     */
    List<Religion> getReligionList() throws AkuraAppException;
    
    /**
     * Method is to House reference data.
     * 
     * @return House list.
     * @throws AkuraAppException - Detailed exception.
     */
    List<House> getHouseList() throws AkuraAppException;
    
    /**
     * Method is to Language reference data.
     * 
     * @return Language list.
     * @throws AkuraAppException - Detailed exception.
     */
    List<Language> getLanguageList() throws AkuraAppException;
    
    /**
     * Returns the list of nationality instances.
     * 
     * @return - a list of nationalities.
     * @throws AkuraAppException - The exception details that occurred when retrieving a list of Nationality
     *         instances.
     */
    List<Nationality> getNationalityList() throws AkuraAppException;
    
    /**
     * Returns the list of BloodGroup instances.
     * 
     * @return - a list of BloodGroups.
     * @throws AkuraAppException - The exception details that occurred when retrieving a list of BloodGroup
     *         instances.
     */
    List<BloodGroup> getBloodGroupList() throws AkuraAppException;
    
    /**
     * Returns the list of MethodOfTravel.
     * 
     * @return list of MethodOfTravel.
     * @throws AkuraAppException - occurs when retrieving the MethodOfTravel list.
     */
    List<MethodOfTravel> getMethodOfTravelList() throws AkuraAppException;
    
    /**
     * Returns a list of Cities.
     * 
     * @return - a list of cities.
     * @throws AkuraAppException - The exception details that occurred when retrieving a list of City
     *         instances.
     */
    List<City> getCityList() throws AkuraAppException;
    
    /**
     * Adds a new instance of Province.
     * 
     * @param province - Province instance.
     * @return - the relevant Province object.
     * @throws AkuraAppException - The exception details that occurred when saving a Province.
     */
    Province addProvince(Province province) throws AkuraAppException;
    
    /**
     * Deletes the relevant Province object.
     * 
     * @param province - instance of Province.
     * @throws AkuraAppException - The exception details that occurred when deleting a Province.
     */
    void deleteProvince(Province province) throws AkuraAppException;
    
    /**
     * Returns the Province object that relevant to the province id.
     * 
     * @param provinceId - the id of the province.
     * @return - a province object relevant to the given id
     * @throws AkuraAppException - The exception details that occurred when finding a Province.
     */
    Province findProvince(Integer provinceId) throws AkuraAppException;
    
    /**
     * Updates the relevant Province.
     * 
     * @param province - the instance of Province.
     * @throws AkuraAppException - The exception details that occurred when updating a Province.
     */
    void updateProvince(Province province) throws AkuraAppException;
    
    /**
     * Returns a list of Province instances.
     * 
     * @return - a list of Province instances.
     * @throws AkuraAppException - The exception details that occurred when retrieving a list of Province
     *         instances.
     */
    List<Province> getProvinceList() throws AkuraAppException;
    
    /**
     * Adds a new instance of City.
     * 
     * @param city - an instance of City.
     * @return - the saved city object.
     * @throws AkuraAppException - The exception details that occurred when saving a City.
     */
    City addCity(City city) throws AkuraAppException;
    
    /**
     * Adds a new instance of Country.
     * 
     * @param country - an instance of Country.
     * @return - the saved country object.
     * @throws AkuraAppException - The exception details that occurred when saving a Country.
     */
    Country addCountry(Country country) throws AkuraAppException;
    
    /**
     * Finds the city that relevant to the city id.
     * 
     * @param cityId - id of the city.
     * @return - a City instance.
     * @throws AkuraAppException - The exception details that occurred when finding a City.
     */
    City findCity(Integer cityId) throws AkuraAppException;
    
    /**
     * Updates the relevant City object.
     * 
     * @param city - City instance.
     * @throws AkuraAppException - The exception details that occurred when updating a City.
     */
    void updateCity(City city) throws AkuraAppException;
    
    /**
     * Updates the relevant Country object.
     * 
     * @param country - Country instance.
     * @throws AkuraAppException - The exception details that occurred when updating a Country.
     */
    void updateCountry(Country country) throws AkuraAppException;
    
    /**
     * Deletes the relevant city object.
     * 
     * @param city - an instance of City.
     * @throws AkuraAppException - The exception details that occurred when deleting a City.
     */
    void deleteCity(City city) throws AkuraAppException;
    
    /**
     * Deletes the relevant country object.
     * 
     * @param country - an instance of Country.
     * @throws AkuraAppException - The exception details that occurred when deleting a Country.
     */
    void deleteCountry(Country country) throws AkuraAppException;
    
    /**
     * Returns a District for the successful saving of a District instance.
     * 
     * @param district - instance of district.
     * @return - the saved district object.
     * @throws AkuraAppException - The exception details that occurred when saving a District.
     */
    District addDistrict(District district) throws AkuraAppException;
    
    /**
     * Finds the District that relevant to the District id.
     * 
     * @param districtId - the id of the District.
     * @return - an instance of District.
     * @throws AkuraAppException - The exception details that occurred when finding a District.
     */
    District findDistrict(Integer districtId) throws AkuraAppException;
    
    /**
     * Updates the relevant District object.
     * 
     * @param district - District instance.
     * @throws AkuraAppException - The exception details that occurred when updating a District.
     */
    void updateDistrict(District district) throws AkuraAppException;
    
    /**
     * Deletes the relevant District object.
     * 
     * @param district - an instance of District.
     * @throws AkuraAppException - The exception details that occurred when deleting a District.
     */
    void deleteDistrict(District district) throws AkuraAppException;
    
    /**
     * Returns a list of District instances.
     * 
     * @return - an list of District instances.
     * @throws AkuraAppException - The exception details that occurred when retrieving a list of District
     *         instances.
     */
    List<District> getDistrictList() throws AkuraAppException;
    
    /**
     * Returns all the provinces that starts with the given information.
     * 
     * @param provinceName - description of the Province
     * @return - a list of Provinces that relevant to the given information.
     * @throws AkuraAppException - The exception details that occurred when searching Province instances.
     */
    List<Province> searchProvince(String provinceName) throws AkuraAppException;
    
    /**
     * Returns all the districts that starts with the given information.
     * 
     * @param searchDescription - description of the District
     * @return - a list of Districts that relevant to the given information.
     * @throws AkuraAppException - The exception details that occurred when searching District instances.
     */
    List<District> searchDistrict(String searchDescription) throws AkuraAppException;
    
    /**
     * Returns all the cities that starts with the given information.
     * 
     * @param searchDescription - description of the City.
     * @return - a list of Cities that relevant to the given information.
     * @throws AkuraAppException - The exception details that occurred when searching City instances.
     */
    List<City> searchCity(String searchDescription) throws AkuraAppException;
    
    /**
     * Returns all the countries that starts with the given information.
     * 
     * @param searchDescription - description of the Country.
     * @return - a list of Countries that relevant to the given information.
     * @throws AkuraAppException - The exception details that occurred when searching Country instances.
     */
    List<Country> searchCountry(String searchDescription) throws AkuraAppException;
    
    /**
     * Returns a list of Term instances.
     * 
     * @return a list of Term instances.
     * @throws AkuraAppException - if error occurs when retrieving Term instances.
     */
    List<Term> getTermList() throws AkuraAppException;
    
    /**
     * Returns a list of SubTerm instances.
     * 
     * @return a list of SubTerm instances.
     * @throws AkuraAppException - if error occurs when retrieving SubTerm instances.
     */
    List<SubTerm> getSubTermList() throws AkuraAppException;
    
    /**
     * Method is to return Subject type object.
     * 
     * @param subjectId - integer type object.
     * @return Subject type object.
     * @throws AkuraAppException when exception occurs.
     */
    Subject findSubjectById(int subjectId) throws AkuraAppException;
    
    /**
     * Returns a ClubSociety for the successful saving of a ClubSociety instance.
     * 
     * @param clubSociety ClubSociety instance.
     * @return - the saved ClubSociety object.
     * @throws AkuraAppException throws if exception occurs when saving a ClubSociety instance.
     */
    ClubSociety addClubSociety(ClubSociety clubSociety) throws AkuraAppException;
    
    /**
     * Updates the relevant ClubSociety object.
     * 
     * @param clubSociety - ClubSociety instance.
     * @throws AkuraAppException - if error occurs when updating a ClubSociety instance.
     */
    void editClubSociety(ClubSociety clubSociety) throws AkuraAppException;
    
    /**
     * Deletes the relevant ClubSociety object.
     * 
     * @param clubSocietyId - id of clubSocietyId.
     * @throws AkuraAppException - if error occurs when deleting a clubSociety instance.
     */
    void deleteClubSociety(int clubSocietyId) throws AkuraAppException;
    
    /**
     * Returns a list of ClubSociety instances.
     * 
     * @return a list of ClubSociety instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of ClubSociety instances.
     */
    List<ClubSociety> getClubsSocietiesList() throws AkuraAppException;
    
    /**
     * Finds the ClubSociety that relevant to the ClubSociety id.
     * 
     * @param clubSocietyId - ClubSociety id.
     * @return a ClubSociety instance.
     * @throws AkuraAppException throws if exception occurs when finding a ClubSociety instance.
     */
    ClubSociety findClubSocietyById(int clubSocietyId) throws AkuraAppException;
    
    /**
     * Retrieve the available ClubSociety object.
     * 
     * @param clubSociety - String.
     * @return ClubSociety object.
     * @throws AkuraAppException - throw detailed exception.
     */
    ClubSociety getAnyClubSociety(String clubSociety) throws AkuraAppException;
    
    /**
     * Check whether ClubSociety is already added.
     * 
     * @param clubSocietyName - String
     * @param clubSocietyId - int
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    boolean isExistsClubSociety(String clubSocietyName, int clubSocietyId) throws AkuraAppException;
    
    /**
     * Service to add Sport instance.
     * 
     * @param sport Sport instance.
     * @return - the saved Sport object.
     * @throws AkuraAppException throws if exception occurs when saving a Sport instance.
     */
    Sport addSport(Sport sport) throws AkuraAppException;
    
    /**
     * Updates the relevant Sport object.
     * 
     * @param sport - Sport instance.
     * @throws AkuraAppException - if error occurs when updating a sport instance.
     */
    void editSport(Sport sport) throws AkuraAppException;
    
    /**
     * Deletes the relevant Sport object.
     * 
     * @param sportId - id of Sport.
     * @throws AkuraAppException - if error occurs when deleting a sport instance.
     */
    void deleteSport(int sportId) throws AkuraAppException;
    
    /**
     * Returns a list of Sport instances.
     * 
     * @return a list of Sport instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Sport instances.
     */
    List<Sport> getSportsList() throws AkuraAppException;
    
    /**
     * Finds the sport that relevant to the sport id.
     * 
     * @param sportId - Sport id.
     * @return a Sport instance.
     * @throws AkuraAppException throws if exception occurs when finding a Sport instance.
     */
    Sport findSportById(int sportId) throws AkuraAppException;
    
    /**
     * Service to add SportSub instance.
     * 
     * @param sportSub SportSub instance.
     * @return - the saved SportSub object.
     * @throws AkuraAppException throws if exception occurs when saving a SportSub instance.
     */
    SportSub addSportSub(SportSub sportSub) throws AkuraAppException;
    
    /**
     * Updates the relevant SportSub object.
     * 
     * @param sportSub - SportSub instance.
     * @throws AkuraAppException - if error occurs when updating a sportSub instance.
     */
    void editSportSub(SportSub sportSub) throws AkuraAppException;
    
    /**
     * Deletes the relevant SportSub object.
     * 
     * @param sportSubId - id of SportSub.
     * @throws AkuraAppException - if error occurs when deleting a sportSub instance.
     */
    void deleteSportSub(int sportSubId) throws AkuraAppException;
    
    /**
     * Returns a list of SportSub instances.
     * 
     * @return a list of SportSub instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of SportSub instances.
     */
    List<SportSub> getSportSubsList() throws AkuraAppException;
    
    /**
     * Finds the sportSub that relevant to the sportSub id.
     * 
     * @param sportSubId - SportSub id.
     * @return a SportSub instance.
     * @throws AkuraAppException throws if exception occurs when finding a SportSub instance.
     */
    SportSub findSportSubById(int sportSubId) throws AkuraAppException;
    
    /**
     * Get list of sub terms of a term.
     * 
     * @param termId - termId id.
     * @return a list of SubTerm.
     * @throws AkuraAppException throws if exception occurs when
     */
    List<SubTerm> getSubTermsOfATerm(int termId) throws AkuraAppException;
    
    /**
     * Service to add PrefectType instance.
     * 
     * @param prefectType PrefectType instance.
     * @return - the saved PrefectType object.
     * @throws AkuraAppException throws if exception occurs when saving a PrefectType instance.
     */
    PrefectType addPrefectType(PrefectType prefectType) throws AkuraAppException;
    
    /**
     * Finds the PrefectType instance that relates to the PrefectType id.
     * 
     * @param prefectTypeId - the id of the PrefectType.
     * @return - a PrefectType instance.
     * @throws AkuraAppException - The exception details that occurred when finding a PrefectType instance.
     */
    PrefectType findPrefectType(Integer prefectTypeId) throws AkuraAppException;
    
    /**
     * Updates the relevant PrefectType object.
     * 
     * @param prefectType - an instance of PrefectType.
     * @throws AkuraAppException - The exception details that occurred when updating a PrefectType instance.
     */
    void updatePrefectType(PrefectType prefectType) throws AkuraAppException;
    
    /**
     * Deletes the relevant prefectType object.
     * 
     * @param prefectType - an instance of PrefectType
     * @throws AkuraAppException - The exception details that occurred when deleting a PrefectType instance.
     */
    void deletePrefectType(PrefectType prefectType) throws AkuraAppException;
    
    /**
     * Get list of PrefectType.
     * 
     * @return a list of PrefectType.
     * @throws AkuraAppException throws if exception occurs when
     */
    List<PrefectType> getPrefectTypeList() throws AkuraAppException;
    
    /**
     * Get PrefectType object by PrefectTypeId.
     * 
     * @param prefectTypeId integer type object.
     * @return a PrefectType object.
     * @throws AkuraAppException throws if exception occurs when
     */
    PrefectType findPrefectTypeById(int prefectTypeId) throws AkuraAppException;
    
    /**
     * Service to add SchoolClass instance.
     * 
     * @param schoolClass SchoolClass instance.
     * @return - the saved SchoolClass object.
     * @throws AkuraAppException throws if exception occurs when saving a SchoolClass instance.
     */
    SchoolClass addSchoolClass(SchoolClass schoolClass) throws AkuraAppException;
    
    /**
     * Finds the SchoolClass instance that relates to the SchoolClass id.
     * 
     * @param schoolClassId - the id of the SchoolClass.
     * @return - a SchoolClass instance.
     * @throws AkuraAppException - The exception details that occurred when finding a SchoolClass instance.
     */
    SchoolClass findSchoolClass(Integer schoolClassId) throws AkuraAppException;
    
    /**
     * Updates the relevant SchoolClass object.
     * 
     * @param schoolClass - an instance of SchoolClass.
     * @throws AkuraAppException - The exception details that occurred when updating a SchoolClass instance.
     */
    void updateSchoolClass(SchoolClass schoolClass) throws AkuraAppException;
    
    /**
     * Deletes the relevant schoolClass object.
     * 
     * @param schoolClass - an instance of SchoolClass
     * @throws AkuraAppException - The exception details that occurred when deleting a SchoolClass instance.
     */
    void deleteSchoolClass(SchoolClass schoolClass) throws AkuraAppException;
    
    /**
     * Get list of SchoolClass.
     * 
     * @return a list of SchoolClass.
     * @throws AkuraAppException throws if exception occurs when
     */
    List<SchoolClass> getSchoolClassList() throws AkuraAppException;
    
    /**
     * Get SchoolClass object by SchoolClassId.
     * 
     * @param schoolClassId integer type object.
     * @return a SchoolClass object.
     * @throws AkuraAppException throws if exception occurs when
     */
    SchoolClass findSchoolClassById(int schoolClassId) throws AkuraAppException;
    
    /**
     * Service to add SportCategory instance.
     * 
     * @param sportCategory SportCategory instance.
     * @return - the saved SportCategory object.
     * @throws AkuraAppException throws if exception occurs when saving a SportCategory instance.
     */
    SportCategory addSportCategory(SportCategory sportCategory) throws AkuraAppException;
    
    /**
     * Updates the relevant SportCategory object.
     * 
     * @param sportCategory - SportCategory instance.
     * @throws AkuraAppException - if error occurs when updating a sportCategory instance.
     */
    void editSportCategory(SportCategory sportCategory) throws AkuraAppException;
    
    /**
     * Deletes the relevant SportCategory object.
     * 
     * @param sportCategoryId - id of SportCategory.
     * @throws AkuraAppException - if error occurs when deleting a sportCategory instance.
     */
    void deleteSportCategory(int sportCategoryId) throws AkuraAppException;
    
    /**
     * Gets list of SportCategory instances.
     * 
     * @return list of SportCategory instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of SportCategory instances.
     */
    List<SportCategory> getSportCategoriesList() throws AkuraAppException;
    
    /**
     * Finds the sportCategory that relevant to the sportCategory id.
     * 
     * @param sportCategoryId - SportCategory id.
     * @return a SportCategory instance.
     * @throws AkuraAppException throws if exception occurs when finding a SportCategory instance.
     */
    SportCategory findSportCategoryById(int sportCategoryId) throws AkuraAppException;
    
    /**
     * Finds the sportCategory that relevant to the sport id.
     * 
     * @param sportId - Sport id.
     * @return SportCategory list.
     * @throws AkuraAppException throws if exception occurs when finding a SportCategory instance.
     */
    List<SportCategory> getSportCategoryBySport(int sportId) throws AkuraAppException;
    
    /**
     * Service to add Holiday instance.
     * 
     * @param holiday Holiday instance.
     * @return - the saved Holiday object.
     * @throws AkuraAppException throws if exception occurs when saving a Holiday instance.
     */
    Holiday saveHoliday(Holiday holiday) throws AkuraAppException;
    
    /**
     * Updates the relevant Holiday object.
     * 
     * @param holiday - Holiday instance.
     * @throws AkuraAppException - if error occurs when updating a Holiday instance.
     */
    void updateHoliday(Holiday holiday) throws AkuraAppException;
    
    /**
     * Deletes the relevant Holiday object.
     * 
     * @param holidayId - id of Holiday.
     * @throws AkuraAppException - if error occurs when deleting a Holiday instance.
     */
    void deleteHoliday(int holidayId) throws AkuraAppException;
    
    /**
     * Returns a list of Holiday instances.
     * 
     * @param startDate - Date.
     * @param endDate - Date.
     * @return a list of Holiday instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Holiday instances.
     */
    List<Holiday> findHolidayByYear(Date startDate, Date endDate) throws AkuraAppException;
    
    /**
     * Finds the Holiday that relevant to the holiday id.
     * 
     * @param holidayId - Holiday id.
     * @return a Holiday instance.
     * @throws AkuraAppException throws if exception occurs when finding a Holiday instance.
     */
    Holiday findHolidayById(int holidayId) throws AkuraAppException;
    
    /**
     * Returns a list of Holiday instances.
     * 
     * @return a list of Holiday instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Holiday instances.
     */
    List<Holiday> findHolidays() throws AkuraAppException;
    
    /**
     * Returns a list of FaithLifeCategory instances.
     * 
     * @return a list of FaithLifeCategory instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of FaithLifeCategory instances.
     */
    List<FaithLifeCategory> findFaithLifeCategories() throws AkuraAppException;
    
    /**
     * Returns a list of FaithLifeComment instances.
     * 
     * @param categoryId - int.
     * @return a list of FaithLifeComment instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of FaithLifeComment instances.
     */
    List<FaithLifeComment> findFaithLifeCommentsByCategory(int categoryId) throws AkuraAppException;
    
    /**
     * Finds the FaithLifeComment that relevant to the FaithLifeComment id.
     * 
     * @param faithLifeCommentId - FaithLifeComment id.
     * @return a FaithLifeComment instance.
     * @throws AkuraAppException throws if exception occurs when finding a FaithLifeComment instance.
     */
    FaithLifeComment findFaithLifeCommentById(int faithLifeCommentId) throws AkuraAppException;
    
    /**
     * Creates a Seminar.
     * 
     * @param seminar {@link Seminar}
     * @return {@link Seminar}
     * @throws AkuraAppException throws if exception occurs when adding a Seminar instance.
     */
    Seminar createSeminar(Seminar seminar) throws AkuraAppException;
    
    /**
     * Returns all the instances of PublicationType.
     * 
     * @return - a list of all the instances of PublicationType.
     * @throws AkuraAppException - The exception details that occurred when retrieving all the instances of
     *         PublicationTypes.
     */
    List<PublicationType> getPublicationTypes() throws AkuraAppException;
    
    /**
     * Returns all the instances of Publication.
     * 
     * @return - a list of all the instances of Publication.
     * @throws AkuraAppException - The exception details that occurred when retrieving all the instances of
     *         Publication.
     */
    List<Publication> getPublications() throws AkuraAppException;
    
    /**
     * Updates the relevant publication object.
     * 
     * @param publication - an instance of Publication.
     * @throws AkuraAppException - The exception details that occurred when updating the relevant instance of
     *         Publication.
     */
    void updatePublication(Publication publication) throws AkuraAppException;
    
    /**
     * Saves the relevant instance of Publication.
     * 
     * @param publication - an instance of Publication.
     * @return - the newly saved instance of Publication.
     * @throws AkuraAppException - The exception details that occurred when saving the relevant instance of
     *         Publication.
     */
    Publication addPublication(Publication publication) throws AkuraAppException;
    
    /**
     * Deletes the relevant instance of Publication.
     * 
     * @param publication - an instance of Publication.
     * @throws AkuraAppException - The exception details that occurred when deleting the relevant instance of
     *         Publication.
     */
    void deletePublication(Publication publication) throws AkuraAppException;
    
    /**
     * Finds the publicationType for a given key.
     * 
     * @param getpTypeId - the id of the relevant publicationType.
     * @return - an instance of PublicationType.
     * @throws AkuraAppException - The exception details that occurred when retrieving the relevant instance
     *         of PublicationType.
     */
    PublicationType findPublicationType(int getpTypeId) throws AkuraAppException;
    
    /**
     * Finds the publication for a given key.
     * 
     * @param publicationId - the id of the relevant publication.
     * @return - an instance of Publication.
     * @throws AkuraAppException - The exception details that occurred when retrieving the relevant instance
     *         of Publication.
     */
    Publication findPublication(int publicationId) throws AkuraAppException;
    
    /**
     * Returns the a list of publication, with three items at a time for the given maximum and minimum item
     * number.
     * 
     * @param minSize - minimum number of the item.
     * @param maxSize - maximum number of the item to be displayed.
     * @return - a list of Publications.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Publication> getPublicationsWithPagination(int minSize, int maxSize) throws AkuraAppException;
    
    /**
     * Returns the total number of publication items.
     * 
     * @return - the maximum pageNumber.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    int getPageNo() throws AkuraAppException;
    
    /**
     * Edit a Seminar.
     * 
     * @param seminar - {@link Seminar} .
     * @throws AkuraAppException throws if exception occurs when update a Seminar instance.
     */
    void updateSeminar(Seminar seminar) throws AkuraAppException;
    
    /**
     * Get the Seminar by id.
     * 
     * @param seminarId - int .
     * @return {@link Seminar}
     * @throws AkuraAppException throws if exception occurs when get a Seminar instance.
     */
    Seminar findSeminarById(int seminarId) throws AkuraAppException;
    
    /**
     * Delete a Seminar.
     * 
     * @param seminar - {@link Seminar} .
     * @throws AkuraAppException throws if exception occurs when deleting a Seminar instance.
     */
    void deleteSeminar(Seminar seminar) throws AkuraAppException;
    
    /**
     * View all the available seminars.
     * 
     * @return list of seminars {@link Seminar}
     * @throws AkuraAppException - Detailed exception.
     */
    List<Seminar> getSeminars() throws AkuraAppException;
    
    /**
     * Returns the latest publications.
     * 
     * @return - the latest publications.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Publication> getLatestPublications() throws AkuraAppException;
    
    /**
     * Method is to Subject reference data.
     * 
     * @return subject list.
     * @throws AkuraAppException - Detailed exception.
     */
    List<Subject> getSubjectList() throws AkuraAppException;
    
    /**
     * Method is to StudyMedium reference data.
     * 
     * @return study medium list.
     * @throws AkuraAppException - Detailed exception.
     */
    List<StudyMedium> getStudyMediumList() throws AkuraAppException;
    
    /**
     * Method is to get list of ClassGrade.
     * 
     * @return ClassGrade list.
     * @throws AkuraAppException - throw this
     */
    List<ClassGrade> getClassGradeList() throws AkuraAppException;
    
    /**
     * Returns matching study mediums for given key String.
     * 
     * @param key - string
     * @return - the list of StudyMedium .
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudyMedium> findStudyMedium(String key) throws AkuraAppException;
    
    /**
     * Method is to get list of Grade.
     * 
     * @return Grade list.
     * @throws AkuraAppException - throw this
     */
    List<Grade> getGradeList() throws AkuraAppException;
    
    /**
     * Remove study medium for given id from the system .
     * 
     * @param studyMedium the study medium
     * @return - String .
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    String deleteStudyMedium(StudyMedium studyMedium) throws AkuraAppException;
    
    /**
     * Service method is to save classGrade.
     * 
     * @param classGrade - classGrade object to be saved.
     * @return {@link ClassGrade}
     * @throws AkuraAppException - throw this
     */
    ClassGrade saveClassGrade(ClassGrade classGrade) throws AkuraAppException;
    
    /**
     * save or updates study medium .
     * 
     * @param studyMedium - StudyMedium .
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void saveOrUpdateStudyMedium(StudyMedium studyMedium) throws AkuraAppException;
    
    /**
     * Service method is to save Grade.
     * 
     * @param grade - Grade object to be saved.
     * @return {@link Grade}
     * @throws AkuraAppException - throw this
     */
    Grade saveGrade(Grade grade) throws AkuraAppException;
    
    /**
     * Service method is to get ClassGrade by grade.
     * 
     * @param grade type {@link Grade}
     * @return {@link ClassGrade}
     * @throws AkuraAppException - throw this
     */
    List<ClassGrade> getClassGradeListByGrade(Grade grade) throws AkuraAppException;
    
    /**
     * Get the list of ClassGrade object for given grade id and class id.
     * 
     * @param gradeId - int type of grade Id for class grade object.
     * @param classId - int type of class Id for class grade object.
     * @return ClassGrade - The ClassGrade object for given grade id and class id.
     * @throws AkuraAppException - The detailed exception.
     */
    List<ClassGrade> getClassGradeByGradeIdAndClassId(int gradeId, int classId) throws AkuraAppException;
    
    /**
     * Create a religion.
     * 
     * @param religion {@link Religion}
     * @return {@link Religion}
     * @throws AkuraAppException throws if exception occurs when adding a Religion instance.
     */
    Religion createReligion(Religion religion) throws AkuraAppException;
    
    /**
     * Create a house.
     * 
     * @param house {@link House}
     * @return {@link House}
     * @throws AkuraAppException throws if exception occurs when adding a House instance.
     */
    House createHouse(House house) throws AkuraAppException;
    
    /**
     * Adds a new instance of Stream object.
     * 
     * @param stream - instance of Stream.
     * @return the newly saved stream instance.
     * @throws AkuraAppException - The exception details that occurred when saving a Stream instance.
     */
    Stream addStream(Stream stream) throws AkuraAppException;
    
    /**
     * Finds the Stream object that relevant to the Stream id.
     * 
     * @param streamId - Stream id.
     * @return - a Stream instance.
     * @throws AkuraAppException - The exception details that occurred when finding a Stream instance.
     */
    Stream findStream(Integer streamId) throws AkuraAppException;
    
    /**
     * Updates the Stream object.
     * 
     * @param stream - Stream instance.
     * @throws AkuraAppException - The exception details that occurred when updating a Stream instance.
     */
    void updateStream(Stream stream) throws AkuraAppException;
    
    /**
     * Deletes the relevant Stream object.
     * 
     * @param stream - an instance of Stream.
     * @throws AkuraAppException - The exception details that occurred when deleting a Stream instance.
     */
    void deleteStream(Stream stream) throws AkuraAppException;
    
    /**
     * Returns a list of Stream instances.
     * 
     * @return - a list of Stream instances.
     * @throws AkuraAppException - The exception details that occurred when retrieving a list of Stream
     *         instances.
     */
    List<Stream> getStreamList() throws AkuraAppException;
    
    /**
     * Adds a new instance of Subject.
     * 
     * @param subject - an instance of Subject.
     * @return - relevant Saved instance of Subject.
     * @throws AkuraAppException - The exception details that occurred when saving a Subject instance.
     */
    Subject addSubject(Subject subject) throws AkuraAppException;
    
    /**
     * Returns a message for the successful find of a Subject instance.
     * 
     * @param subjectId - id of the Subject.
     * @return - a Subject instance.
     * @throws AkuraAppException - The exception details that occurred when finding a Subject instance.
     */
    Subject findSubject(Integer subjectId) throws AkuraAppException;
    
    /**
     * Returns true for the successful update of a Subject instance.
     * 
     * @param subject - an instance of Subject.
     * @throws AkuraAppException - The exception details that occurred when updating a Subject instance.
     */
    void updateSubject(Subject subject) throws AkuraAppException;
    
    /**
     * Returns true for the successful deleting of a Subject instance.
     * 
     * @param subject - an instance of Subject
     * @throws AkuraAppException - The exception details that occurred when deleting a Subject instance.
     */
    void deleteSubject(Subject subject) throws AkuraAppException;
    
    /**
     * Adds a new instance of SchoolClass and returns that object.
     * 
     * @param classInstance - an instance of SchoolClass.
     * @return - the saved SchoolClass.
     * @throws AkuraAppException - The exception details that occurred when saving a SchoolClass instance.
     */
    SchoolClass addClass(SchoolClass classInstance) throws AkuraAppException;
    
    /**
     * Returns the relevant SchoolClass that matches to the schoolClass id.
     * 
     * @param classInstanceId - SchoolClass id.
     * @return - an instance of SchoolClass.
     * @throws AkuraAppException - The exception details that occurred when finding a SchoolClass instance.
     */
    SchoolClass findClass(Integer classInstanceId) throws AkuraAppException;
    
    /**
     * Updates the SchoolClass.
     * 
     * @param classInstance - an instance of SchoolClass.
     * @throws AkuraAppException - The exception details that occurred when updating a SchoolClass instance.
     */
    void updateClass(SchoolClass classInstance) throws AkuraAppException;
    
    /**
     * Deletes a SchoolClass of the relevant object.
     * 
     * @param classInstance - an instance of SchoolClass.
     * @throws AkuraAppException - The exception details that occurred when deleting a SchoolClass instance.
     */
    void deleteClass(SchoolClass classInstance) throws AkuraAppException;
    
    /**
     * Returns a list of Class instances.
     * 
     * @return - a list of Class instances.
     * @throws AkuraAppException - The exception details that occurred when retrieving a list of SchoolClass
     *         instances.
     */
    List<SchoolClass> getClassList() throws AkuraAppException;
    
    /**
     * Delete a house.
     * 
     * @param houseId - int .
     * @throws AkuraAppException throws if exception occurs when deleting a house instance.
     */
    void deleteHouse(int houseId) throws AkuraAppException;
    
    /**
     * Edit a house.
     * 
     * @param house - {@link House} .
     * @throws AkuraAppException throws if exception occurs when update a house instance.
     */
    void updateHouse(House house) throws AkuraAppException;
    
    /**
     * Get the house by name.
     * 
     * @param houseId - int .
     * @return {@link House}
     * @throws AkuraAppException throws if exception occurs when get a house instance.
     */
    House findHouseById(int houseId) throws AkuraAppException;
    
    /**
     * Edit a religion.
     * 
     * @param religion - {@link Religion} .
     * @throws AkuraAppException throws if exception occurs when update a religion instance.
     */
    void updateReligion(Religion religion) throws AkuraAppException;
    
    /**
     * Get the religion by id.
     * 
     * @param religionId - int .
     * @return {@link Religion}
     * @throws AkuraAppException throws if exception occurs when get a religion instance.
     */
    Religion findReligionById(int religionId) throws AkuraAppException;
    
    /**
     * Delete a religion.
     * 
     * @param religionId - int .
     * @throws AkuraAppException throws if exception occurs when deleting a religion instance.
     */
    void deleteReligion(int religionId) throws AkuraAppException;
    
    /**
     * Get the grade by id.
     * 
     * @param gradeId - int .
     * @return {@link Grade}
     * @throws AkuraAppException throws if exception occurs when get a grade instance.
     */
    Grade findGradeById(int gradeId) throws AkuraAppException;
    
    /**
     * Edit a grade.
     * 
     * @param grade - {@link Grade} .
     * @throws AkuraAppException throws if exception occurs when update a grade instance.
     */
    void updateGrade(Grade grade) throws AkuraAppException;
    
    /**
     * delete a grade.
     * 
     * @param grade - {@link Grade} .
     * @throws AkuraAppException throws if exception occurs when update a grade instance.
     */
    void deleteGrade(Grade grade) throws AkuraAppException;
    
    /**
     * Get the grade by description.
     * 
     * @param description - String .
     * @return {@link Grade}
     * @throws AkuraAppException throws if exception occurs when get a grade instance.
     */
    Grade getGradeByGradeName(String description) throws AkuraAppException;
    
    /**
     * Edit a classGrade.
     * 
     * @param classGrade - {@link ClassGrade} .
     * @throws AkuraAppException throws if exception occurs when update a classGrade instance.
     */
    void updateClassGrade(ClassGrade classGrade) throws AkuraAppException;
    
    /**
     * Gets the class grade by name.
     * 
     * @param description the description
     * @return the class grade by name
     * @throws AkuraAppException the sMS app exception
     */
    List<ClassGrade> getClassGradeByName(String description) throws AkuraAppException;
    
    /**
     * Delete classGrade by grade.
     * 
     * @param classGrades - list of ClassGrade.
     * @throws AkuraAppException throws if exception occurs when delete a grade instance.
     */
    void deleteClassGradeList(List<ClassGrade> classGrades) throws AkuraAppException;
    
    /**
     * Returns a ClassGrade object for a specific classGradeID.
     * 
     * @param classGradeId Class grade id.
     * @return a list of GradeSubject.
     * @throws AkuraAppException throws if exception occurs.
     */
    ClassGrade findClassGrade(int classGradeId) throws AkuraAppException;
    
    /**
     * Returns a list of gradeSubject in a specific grade.
     * 
     * @param gradeId grade id.
     * @return a list of GradeSubject.
     * @throws AkuraAppException throws if exception occurs.
     */
    List<GradeSubject> getGradeSubjectList(int gradeId) throws AkuraAppException;
    
    /**
     * Create a scholarship.
     * 
     * @param scholarship {@link Scholarship}
     * @return {@link scholarship}
     * @throws AkuraAppException throws if exception occurs when adding a scholarship instance.
     */
    Scholarship createScholarship(Scholarship scholarship) throws AkuraAppException;
    
    /**
     * Edit a scholarship.
     * 
     * @param scholarship - {@link Scholarship} .
     * @throws AkuraAppException throws if exception occurs when update a scholarship instance.
     */
    void updateScholarship(Scholarship scholarship) throws AkuraAppException;
    
    /**
     * Get the scholarship by id.
     * 
     * @param scholarshipId - int .
     * @return {@link Scholarship}
     * @throws AkuraAppException throws if exception occurs when get a scholarship instance.
     */
    Scholarship findScholarshipById(int scholarshipId) throws AkuraAppException;
    
    /**
     * Delete a scholarship.
     * 
     * @param scholarshipId - int .
     * @throws AkuraAppException throws if exception occurs when deleting a scholarship instance.
     */
    void deleteScholarship(int scholarshipId) throws AkuraAppException;
    
    /**
     * Retrieve all the available scholarship.
     * 
     * @return list of scholarship
     * @throws AkuraAppException throws if exception occurs when deleting a scholarship instance.
     */
    List<Scholarship> getScholarshipList() throws AkuraAppException;
    
    /**
     * Create a MethodOfTravel.
     * 
     * @param methodOfTravel {@link MethodOfTravel}
     * @return {@link MethodOfTravel}
     * @throws AkuraAppException throws if exception occurs when adding a MethodOfTravel instance.
     */
    MethodOfTravel createMethodOfTravel(MethodOfTravel methodOfTravel) throws AkuraAppException;
    
    /**
     * Edit a MethodOfTravel.
     * 
     * @param methodOfTravel - {@link MethodOfTravel} .
     * @throws AkuraAppException throws if exception occurs when update a MethodOfTravel instance.
     */
    void updateMethodOfTravel(MethodOfTravel methodOfTravel) throws AkuraAppException;
    
    /**
     * Get the MethodOfTravel by id.
     * 
     * @param methodOfTravelId - int .
     * @return {@link MethodOfTravel}
     * @throws AkuraAppException throws if exception occurs when get a MethodOfTravel instance.
     */
    MethodOfTravel findMethodOfTravelById(int methodOfTravelId) throws AkuraAppException;
    
    /**
     * Delete a MethodOfTravel.
     * 
     * @param methodOfTravelId - int .
     * @throws AkuraAppException throws if exception occurs when deleting a MethodOfTravel instance.
     */
    void deleteMethodOfTravel(int methodOfTravelId) throws AkuraAppException;
    
    /**
     * Returns all the subjects that starts with the given information.
     * 
     * @param searchDescription - description of the Subject.
     * @return - a list of Subjects that relevant to the given information.
     * @throws AkuraAppException - The exception details that occurred when searching Subject instances.
     */
    List<Subject> searchSubject(String searchDescription) throws AkuraAppException;
    
    /**
     * Returns all the streams that starts with the given information.
     * 
     * @param searchDescription - description of the Stream.
     * @return - a list of Streams that relevant to the given information.
     * @throws AkuraAppException - The exception details that occurred when searching Stream instances.
     */
    List<Stream> searchStream(String searchDescription) throws AkuraAppException;
    
    /**
     * @return list of GradeSubject
     * @throws AkuraAppException - throw this
     */
    List<GradeSubject> getGradeSubjectList() throws AkuraAppException;
    
    /**
     * Create a Term.
     * 
     * @param term {@link Term}
     * @return {@link Term}
     * @throws AkuraAppException throws if exception occurs when adding a Term instance.
     */
    Term createTerm(Term term) throws AkuraAppException;
    
    /**
     * Edit a Term.
     * 
     * @param term - {@link Term} .
     * @throws AkuraAppException throws if exception occurs when update a Term instance.
     */
    void updateTerm(Term term) throws AkuraAppException;
    
    /**
     * Get the Term by id.
     * 
     * @param termId - int .
     * @return {@link Term}
     * @throws AkuraAppException throws if exception occurs when get a Term instance.
     */
    Term findTermById(int termId) throws AkuraAppException;
    
    /**
     * Delete a Term.
     * 
     * @param termId - int .
     * @throws AkuraAppException throws if exception occurs when deleting a Term instance.
     */
    void deleteTerm(int termId) throws AkuraAppException;
    
    /**
     * Create a SubTerm.
     * 
     * @param subTerm {@link SubTerm}
     * @return {@link SubTerm}
     * @throws AkuraAppException throws if exception occurs when adding a SubTerm instance.
     */
    SubTerm createSubTerm(SubTerm subTerm) throws AkuraAppException;
    
    /**
     * Edit a SubTerm.
     * 
     * @param subTerm - {@link SubTerm} .
     * @throws AkuraAppException throws if exception occurs when update a SubTerm instance.
     */
    void updateSubTerm(SubTerm subTerm) throws AkuraAppException;
    
    /**
     * Get the SubTerm by id.
     * 
     * @param subTermId - int .
     * @return {@link SubTerm}
     * @throws AkuraAppException throws if exception occurs when get a SubTerm instance.
     */
    SubTerm findSubTermById(int subTermId) throws AkuraAppException;
    
    /**
     * Delete a SubTerm.
     * 
     * @param subTermId - int .
     * @throws AkuraAppException throws if exception occurs when deleting a SubTerm instance.
     */
    void deleteSubTerm(int subTermId) throws AkuraAppException;
    
    /**
     * Finds the subjects by the given description of a grade.
     * 
     * @param description - the description of the Grade
     * @return - a list of Subjects of the given description of the Grade
     * @throws AkuraAppException - The exception details that occurred when finding subjects for a grade
     *         instance.
     */
    List<GradeSubject> findSubjectsByGrade(String description) throws AkuraAppException;
    
    /**
     * Check whether the given grade, has already assigned subjects.
     * 
     * @param description - the description of the Grade
     * @return boolean
     * @throws AkuraAppException - The exception details that occurred when finding subjects for a grade
     *         instance.
     */
    boolean hasAlreadyAssignedSubjectsForGrade(String description) throws AkuraAppException ;
    
    /**
     * Saves the relevant gradeSubject object.
     * 
     * @param gradeSubject - an instance of GradeSubject
     * @return - the saved instance of GradeSubject
     * @throws AkuraAppException - The exception details that occurred when saving the instance of
     *         GradeSubject.
     */
    GradeSubject addGradeSubject(GradeSubject gradeSubject) throws AkuraAppException;
    
    /**
     * Finds the GradeSubject object for the given description of a Grade and the description of a Subject.
     * 
     * @param gradeDes - the description of the Grade
     * @param subjectDes - the description of the Subject
     * @return - a list of GradeSubjects
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<GradeSubject> findGradeSubjectByDes(String gradeDes, String subjectDes) throws AkuraAppException;
    
    /**
     * Deletes the relevant gradeSubject.
     * 
     * @param gradSubje - the instance of the GradeSubject
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void deleteGradeSubject(GradeSubject gradSubje) throws AkuraAppException;
    
    /**
     * Gets a list of GradeSubjects for a given grade Id.
     * 
     * @param gradeId - the id of a Grade
     * @return - a list of GradeSubjects
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<GradeSubject> getGradeSubjectIdListByGrade(int gradeId) throws AkuraAppException;
    
    /**
     * Gets a list of GradeCoreSubjects for a given grade Id.
     * 
     * @param gradeId - the id of a Grade
     * @return - a list of GradeSubjects
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<GradeSubject> getGradeCoreSubjectIdListByGrade(int gradeId) throws AkuraAppException;
    
    /**
     * Finds the GradeSubject by the given gradeSubjectradeId.
     * 
     * @param gradeSubjectradeId - the gradeSubjectradeId of the GradeSubject.
     * @return - a list of GradeSubject of the given gradeSubjectradeId.
     * @throws AkuraAppException - The exception details that occurred.
     */
    GradeSubject findGradeSubject(int gradeSubjectradeId) throws AkuraAppException;
    
    /**
     * Adding warning level information to the database (Master data).
     * 
     * @param warningLevel specifies the warning level object
     * @return WarningLevel type object.
     * @throws AkuraAppException SMS Exception.
     */
    WarningLevel addWarningLevelsInfo(WarningLevel warningLevel) throws AkuraAppException;
    
    /**
     * Editing warning level information in the database (Master data).
     * 
     * @param warningLevel specifies the warning level object
     * @throws AkuraAppException SMS Exception.
     */
    void editWarningLevelInfo(WarningLevel warningLevel) throws AkuraAppException;
    
    /**
     * Deleting warning level information in the database (Master data).
     * 
     * @param warnLevel specifies the warning level object
     * @throws AkuraAppException SMS Exception.
     */
    void deleteWarningLevelsInfo(WarningLevel warnLevel) throws AkuraAppException;
    
    /**
     * Display warning level information given by warnLevelId.
     * 
     * @param warnLevelId specifies the warning level id, defined by an integer
     * @return WarningLevel type object
     * @throws AkuraAppException SMS Exception.
     */
    WarningLevel viewWarnLevelInfoById(int warnLevelId) throws AkuraAppException;
    
    /**
     * Viewing all the Warning Level Information available in the table.
     * 
     * @return List of WarningLevel objects
     * @throws AkuraAppException SMS Exception.
     */
    List<WarningLevel> viewAllWarningLevelInfo() throws AkuraAppException;
    
    /**
     * Adds a new instance of BloodGroup.
     * 
     * @param bloodGroup - instance of BloodGroup.
     * @return - the saved bloodGroup object.
     * @throws AkuraAppException - The exception details that occurred when saving a BloodGroup instance.
     */
    BloodGroup addBloodGroup(BloodGroup bloodGroup) throws AkuraAppException;
    
    /**
     * Finds the BloodGroup instance that relates to the BloodGroup id.
     * 
     * @param bloodGroupId - the id of the BloodGroup.
     * @return - a BloodGroup instance.
     * @throws AkuraAppException - The exception details that occurred when finding a BloodGroup instance.
     */
    BloodGroup findBloodGroup(Integer bloodGroupId) throws AkuraAppException;
    
    /**
     * Updates the relevant BloodGroup object.
     * 
     * @param bloodGroup - an instance of BloodGroup.
     * @throws AkuraAppException - The exception details that occurred when updating a BloodGroup instance.
     */
    void updateBloodGroup(BloodGroup bloodGroup) throws AkuraAppException;
    
    /**
     * Deletes the relevant bloodGroup object.
     * 
     * @param bloodGroup - an instance of BloodGroup
     * @throws AkuraAppException - The exception details that occurred when deleting a BloodGroup instance.
     */
    void deleteBloodGroup(BloodGroup bloodGroup) throws AkuraAppException;
    
    /**
     * Adds a new instance of Nationality object.
     * 
     * @param nationality - an instance of Nationality.
     * @return - the saved Nationality object.
     * @throws AkuraAppException - The exception details that occurred when saving a Nationality instance.
     */
    Nationality addNationality(Nationality nationality) throws AkuraAppException;
    
    /**
     * Finds the nationality that relevant to the Nationality id.
     * 
     * @param nationalityId - the id of Nationality.
     * @return - newly saved instance of Nationality.
     * @throws AkuraAppException - The exception details that occurred when finding a Nationality instance.
     */
    Nationality findNationality(Integer nationalityId) throws AkuraAppException;
    
    /**
     * Updates the relevant Nationality object.
     * 
     * @param nationality - an instance of Nationality.
     * @throws AkuraAppException - The exception details that occurred when updating a Nationality instance.
     */
    void updateNationality(Nationality nationality) throws AkuraAppException;
    
    /**
     * Deletes the relevant Nationality object.
     * 
     * @param nationality - an instance of Nationality .
     * @throws AkuraAppException - The exception details that occurred when deleting a Nationality instance.
     */
    void deleteNationality(Nationality nationality) throws AkuraAppException;
    
    /**
     * Adds a new instance of Race object.
     * 
     * @param race - an instance of Race.
     * @return - the saved Race object.
     * @throws AkuraAppException - The exception details that occurred when saving a Race instance.
     */
    Race addRace(Race race) throws AkuraAppException;
    
    /**
     * Finds the race that relevant to the Race id.
     * 
     * @param raceId - the id of Race.
     * @return - newly saved instance of Race.
     * @throws AkuraAppException - The exception details that occurred when finding a Race instance.
     */
    Race findRace(Integer raceId) throws AkuraAppException;
    
    /**
     * Updates the relevant Race object.
     * 
     * @param race - an instance of Race.
     * @throws AkuraAppException - The exception details that occurred when updating a Race instance.
     */
    void updateRace(Race race) throws AkuraAppException;
    
    /**
     * Deletes the relevant Race object.
     * 
     * @param race - an instance of Race .
     * @throws AkuraAppException - The exception details that occurred when deleting a Race instance.
     */
    void deleteRace(Race race) throws AkuraAppException;
    
    /**
     * Create a Position.
     * 
     * @param position {@link Position}
     * @return {@link Position}
     * @throws AkuraAppException throws if exception occurs when adding a Position instance.
     */
    Position createPosition(Position position) throws AkuraAppException;
    
    /**
     * Edit a Position.
     * 
     * @param position - {@link Position} .
     * @throws AkuraAppException throws if exception occurs when update a Position instance.
     */
    void updatePosition(Position position) throws AkuraAppException;
    
    /**
     * Get the Position by id.
     * 
     * @param positionId - int .
     * @return {@link Position}
     * @throws AkuraAppException throws if exception occurs when get a Position instance.
     */
    Position findPositionById(int positionId) throws AkuraAppException;
    
    /**
     * Delete a Position.
     * 
     * @param positionId - int .
     * @throws AkuraAppException throws if exception occurs when deleting a Position instance.
     */
    void deletePosition(int positionId) throws AkuraAppException;
    
    /**
     * Retrieve all the available Position.
     * 
     * @return list of Position
     * @throws AkuraAppException throws if exception occurs when deleting a Position instance.
     */
    List<Position> getPositionList() throws AkuraAppException;
    
    /**
     * Create a Grading.
     * 
     * @param grading {@link Grading}
     * @return {@link Grading}
     * @throws AkuraAppException throws if exception occurs when adding a Grading instance.
     */
    Grading createGrading(Grading grading) throws AkuraAppException;
    
    /**
     * Edit a Grading.
     * 
     * @param grading - {@link Grading} .
     * @throws AkuraAppException throws if exception occurs when update a Grading instance.
     */
    void updateGrading(Grading grading) throws AkuraAppException;
    
    /**
     * Get the Grading by id.
     * 
     * @param gradingId - int .
     * @return {@link Grading}
     * @throws AkuraAppException throws if exception occurs when get a Grading instance.
     */
    Grading findGradingById(int gradingId) throws AkuraAppException;
    
    /**
     * Get the FaithLife by id.
     * 
     * @param gradingId - int .
     * @return {@link FaithLife}
     * @throws AkuraAppException throws if exception occurs when get a Grading instance.
     */
    FaithLifeGrading findFaithLifeGradingById(int gradingId) throws AkuraAppException;
    
    /**
     * Delete a Grading.
     * 
     * @param gradingId - int .
     * @throws AkuraAppException throws if exception occurs when deleting a Grading instance.
     */
    void deleteGrading(int gradingId) throws AkuraAppException;
    
    /**
     * Retrieve all the available Grading.
     * 
     * @return list of Grading
     * @throws AkuraAppException throws if exception occurs when deleting a Grading instance.
     */
    List<Grading> getGradingList() throws AkuraAppException;
    
    /**
     * Retrieve all the available FaithLifeGrading.
     * 
     * @return list of FaithLifeGrading
     * @throws AkuraAppException throws if exception occurs when deleting a Grading instance.
     */
    List<FaithLifeGrading> getFaithLifeGradingList() throws AkuraAppException;
    
    /**
     * Finds the SportCategory that relevant to the given sport and sportSub.
     * 
     * @param sportId - Sport id.
     * @param sportSubId - SportSub id.
     * @return a SportCategory instance.
     * @throws AkuraAppException throws if exception occurs when finding a SportCategory instance.
     */
    List<SportCategory> findSportCategoryObj(int sportId, int sportSubId) throws AkuraAppException;
    
    /**
     * Find warning level by id.
     * 
     * @param warningLevelId the warning level id
     * @return the warning level
     * @throws AkuraAppException the sMS app exception
     */
    WarningLevel findWarningLevelById(int warningLevelId) throws AkuraAppException;
    
    /**
     * Create a WorkingSegment.
     * 
     * @param workingSegment {@link WorkingSegment}
     * @return {@link WorkingSegment}
     * @throws AkuraAppException throws if exception occurs when adding a WorkingSegment instance.
     */
    WorkingSegment createWorkingSegment(WorkingSegment workingSegment) throws AkuraAppException;
    
    /**
     * Edit a WorkingSegment.
     * 
     * @param workingSegment - {@link WorkingSegment} .
     * @throws AkuraAppException throws if exception occurs when update a WorkingSegment instance.
     */
    void updateWorkingSegment(WorkingSegment workingSegment) throws AkuraAppException;
    
    /**
     * Get the WorkingSegment by id.
     * 
     * @param workingSegmentId - int .
     * @return {@link WorkingSegment}
     * @throws AkuraAppException throws if exception occurs when get a WorkingSegment instance.
     */
    WorkingSegment findWorkingSegmentById(int workingSegmentId) throws AkuraAppException;
    
    /**
     * Delete a WorkingSegment.
     * 
     * @param workingSegmentId - int .
     * @throws AkuraAppException throws if exception occurs when deleting a WorkingSegment instance.
     */
    void deleteWorkingSegment(int workingSegmentId) throws AkuraAppException;
    
    /**
     * Retrieve all the available WorkingSegment.
     * 
     * @return list of WorkingSegment
     * @throws AkuraAppException throws if exception occurs when deleting a WorkingSegment instance.
     */
    List<WorkingSegment> getWorkingSegmentList() throws AkuraAppException;
    
    /**
     * Service to find the donationType object.
     * 
     * @param donationTypeId to find DonationType
     * @return donationType object
     * @throws AkuraAppException - throw this
     */
    DonationType findDonationType(int donationTypeId) throws AkuraAppException;
    
    /**
     * Service to modify DonationType object.
     * 
     * @param donationType DonationType object to modify
     * @throws AkuraAppException - throw this
     */
    void modifyDonationType(DonationType donationType) throws AkuraAppException;
    
    /**
     * Service to add DonationType object.
     * 
     * @param donationType DonationType object to add
     * @return donationType object
     * @throws AkuraAppException - throw this
     */
    DonationType addDonationType(DonationType donationType) throws AkuraAppException;
    
    /**
     * Service to delete DonationType object.
     * 
     * @param donationType DonationType object to delete
     * @throws AkuraAppException - throw this
     */
    void deleteDonationType(DonationType donationType) throws AkuraAppException;
    
    /**
     * Service to view all donation types.
     * 
     * @return returns list of DonationTypes.
     * @throws AkuraAppException - throw this
     */
    List<DonationType> viewAllDonationType() throws AkuraAppException;
    
    /**
     * Set the employmentStatusDao.
     * 
     * @param employmentStatusDaoRef the EmploymentStatusDao to set
     */
    void setEmploymentStatusDao(EmploymentStatusDao employmentStatusDaoRef);
    
    /**
     * service to add a EmploymentStatus to module.
     * 
     * @param employmentStatus the EmploymentStatus object to add.
     * @return a employmentStatus instance.
     * @throws AkuraAppException - The exception details that occurred when adding an EmploymentStatus
     *         instances.
     */
    EmploymentStatus addEmploymentStatus(EmploymentStatus employmentStatus) throws AkuraAppException;
    
    /**
     * service to delete a EmploymentStatus of a student.
     * 
     * @param employmentStatus the id of the EmploymentStatus object to delete.
     * @throws AkuraAppException - The exception details that occurred when delete a EmploymentStatus
     *         instances.
     */
    void deleteEmploymentStatus(EmploymentStatus employmentStatus) throws AkuraAppException;
    
    /**
     * Service to update an already existing EmploymentStatus object.
     * 
     * @param employmentStatus - the EmploymentStatus object to update
     * @throws AkuraAppException - The exception details that occurred when updating a EmploymentStatus
     *         instance.
     */
    void updateEmploymentStatus(EmploymentStatus employmentStatus) throws AkuraAppException;
    
    /**
     * Get EmploymentStatus object by EmploymentStatusId.
     * 
     * @param employmentStatusId employment status id defined by integer type object
     * @return a EmploymentStatus object.
     * @throws AkuraAppException throws if exception occurs when finding an EmploymentStatus instance.
     */
    EmploymentStatus findEmploymentStatusById(int employmentStatusId) throws AkuraAppException;
    
    /**
     * Service to view all employmentStatus objects.
     * 
     * @return returns list of all Employment Statuses
     * @throws AkuraAppException - throw this
     */
    List<EmploymentStatus> findAllEmploymentStatus() throws AkuraAppException;
    
    /**
     * Delete grade subject list.
     * 
     * @param gradeSubjectList the grade subject list
     * @throws AkuraAppException the akura app exception
     */
    void deleteGradeSubjectList(List<GradeSubject> gradeSubjectList) throws AkuraAppException;
    
    /**
     * Retrieve all the available ParticipantCategory.
     * 
     * @return list of ParticipantCategory
     * @throws AkuraAppException throws if exception occurs when deleting a ParticipantCategory instance.
     */
    List<ParticipantCategory> getParticipantCategoryList() throws AkuraAppException;
    
    /**
     * Retrieve all the available special events.
     * 
     * @return list of special events.
     * @throws AkuraAppException - throws if exception occurs when retrieving special events list
     */
    List<SpecialEvents> getSpecialEventList() throws AkuraAppException;
    
    /**
     * Retrieve the Special Events Participations List by special event id.
     * 
     * @param selectSpecialEvent of type int
     * @return SpecialEventsParticipation object list.
     * @throws AkuraAppException - throws if exception occurs when retrieving special events list
     */
    List<SpecialEventsParticipation> getSpecialEventParticipationListById(int selectSpecialEvent)
            throws AkuraAppException;
    
    /**
     * Service to find the SpecialEventsParticipation object.
     * 
     * @param participationId to find SpecialEventsParticipation
     * @return SpecialEventsParticipation object
     * @throws AkuraAppException -throws if exception occurs
     */
    SpecialEventsParticipation getSpecialEventsParticipation(int participationId) throws AkuraAppException;
    
    /**
     * Gets the available subjects list.
     * 
     * @param selectedSubjectsIdList the selected subjects id list
     * @return the available subjects list
     * @throws AkuraAppException the akura app exception
     */
    List<Subject> getAvailableSubjectsList(List<Integer> selectedSubjectsIdList) throws AkuraAppException;
    
    /**
     * Retrieve the special events by id.
     * 
     * @param selectSpecialEvent of type int
     * @return special event object.
     * @throws AkuraAppException - throws if exception occurs when retrieving special events list
     */
    SpecialEvents getSpecialEventById(int selectSpecialEvent) throws AkuraAppException;
    
    /**
     * Service to add SpecialEvents instance.
     * 
     * @param specialEvents SpecialEvents instance.
     * @return - the saved SpecialEvents object.
     * @throws AkuraAppException throws if exception occurs when saving a SpecialEvents instance.
     */
    SpecialEvents addSpecialEvents(SpecialEvents specialEvents) throws AkuraAppException;
    
    /**
     * Service to edit SpecialEvents instance.
     * 
     * @param specialEvents SpecialEvents instance.
     * @throws AkuraAppException throws if exception occurs when editing a SpecialEvents instance.
     */
    void editSpecialEvents(SpecialEvents specialEvents) throws AkuraAppException;
    
    /**
     * Deletes the relevant SpecialEvents object.
     * 
     * @param specialEventsId - id of SpecialEvents.
     * @throws AkuraAppException - if error occurs when deleting a SpecialEvents instance.
     */
    void deleteSpecialEvents(int specialEventsId) throws AkuraAppException;
    
    /**
     * Finds the specialEvents that relevant to the specialEvents id.
     * 
     * @param specialEventsId - SpecialEvents id.
     * @return a SpecialEvents instance.
     * @throws AkuraAppException throws if exception occurs when finding a SpecialEvents instance.
     */
    SpecialEvents findSpecialEventsById(int specialEventsId) throws AkuraAppException;
    
    /**
     * Retrieve the available SpecialEvents object.
     * 
     * @param specialEvents - String.
     * @param date -Date
     * @return SpecialEvents object.
     * @throws AkuraAppException - throw detailed exception.
     */
    SpecialEvents getAnySpecialEvents(String specialEvents, Date date) throws AkuraAppException;
    
    /**
     * Check whether specialEvent is already added.
     * 
     * @param specialEvent - SpecialEvents
     * @return true if it already exist else false
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    boolean isExistsSpecialEvents(SpecialEvents specialEvent) throws AkuraAppException;
    
    /**
     * Service to add SpecialEventsParticipation instance.
     * 
     * @param specialEventsParticipation SpecialEventsParticipation instance.
     * @return - the saved SpecialEventsParticipation object.
     * @throws AkuraAppException throws if exception occurs when saving a SpecialEventsParticipation instance.
     */
    SpecialEventsParticipation addSpecialEventsParticipation(SpecialEventsParticipation specialEventsParticipation)
            throws AkuraAppException;
    
    /**
     * Retrieve all the participation list for any given special events.
     * 
     * @param event SpecialEvents object
     * @return list of SpecialEventsParticipation.
     * @throws AkuraAppException - throws if exception occurs when retrieving SpecialEventsParticipation
     */
    List<SpecialEventsParticipation> getParticipantListBySpecialEvent(SpecialEvents event) throws AkuraAppException;
    
    /**
     * Deletes the relevant specialEventsParticipation object.
     * 
     * @param specialEventsParticipationObj - SpecialEventsParticipation.
     * @throws AkuraAppException - if error occurs when deleting a specialEventsParticipation instance.
     */
    void deleteSpecialEventsParticipation(SpecialEventsParticipation specialEventsParticipationObj)
            throws AkuraAppException;
    
    /**
     * Update grade subject.
     * 
     * @param gradeSubject the grade subject
     * @throws AkuraAppException the akura app exception
     */
    void updateGradeSubject(GradeSubject gradeSubject) throws AkuraAppException;
    
    /**
     * Adds a new instance of Assignment and returns that object.
     * 
     * @param assignmentInstance - an instance of Assignment.
     * @return - the saved SchoolClass.
     * @throws AkuraAppException - The exception details that occurred when saving a Assignment instance.
     */
    Assignment addAssignment(Assignment assignmentInstance) throws AkuraAppException;
    
    /**
     * Returns the relevant Assignment that matches to the assignment id.
     * 
     * @param assignmentInstanceId - Assignment id.
     * @return - an instance of Assignment.
     * @throws AkuraAppException - The exception details that occurred when finding a Assignment instance.
     */
    Assignment findAssignmentById(Integer assignmentInstanceId) throws AkuraAppException;
    
    /**
     * Updates the Assignment.
     * 
     * @param assignmentInstance - an instance of Assignment.
     * @throws AkuraAppException - The exception details that occurred when updating a Assignment instance.
     */
    void updateAssignment(Assignment assignmentInstance) throws AkuraAppException;
    
    /**
     * Deletes a Assignment of the relevant object.
     * 
     * @param assignmentInstance - an instance of Assignment.
     * @throws AkuraAppException - The exception details that occurred when deleting a Assignment instance.
     */
    void deleteAssignment(Assignment assignmentInstance) throws AkuraAppException;
    
    /**
     * Returns a list of Assignment instances.
     * 
     * @return - a list of Assignment instances.
     * @throws AkuraAppException - The exception details that occurred when retrieving a list of Assignment
     *         instances.
     */
    List<Assignment> getAssignmentList() throws AkuraAppException;
    
    /**
     * Service to add Exam instance.
     * 
     * @param exam Exam instance.
     * @return - the saved exam object.
     * @throws AkuraAppException the akura app exception
     */
    Exam addExam(Exam exam) throws AkuraAppException;
    
    /**
     * Finds the Exam instance that relates to the Exam id.
     * 
     * @param examId - the id of the Exam.
     * @return - a Exam instance.
     * @throws AkuraAppException - The exception details that occurred when finding a Exam instance.
     */
    Exam findExam(Integer examId) throws AkuraAppException;
    
    /**
     * Updates the relevant Exam object.
     * 
     * @param exam - an instance of Exam.
     * @throws AkuraAppException - The exception details that occurred when updating a Exam instance.
     */
    void updateExam(Exam exam) throws AkuraAppException;
    
    /**
     * Deletes the relevant Exam object.
     * 
     * @param exam - an instance of Exam
     * @throws AkuraAppException - The exception details that occurred when deleting a Exam instance.
     */
    void deleteExam(Exam exam) throws AkuraAppException;
    
    /**
     * Get list of Exam.
     * 
     * @return a list of Exam.
     * @throws AkuraAppException throws if exception occurs
     */
    List<Exam> getExamList() throws AkuraAppException;
    
    /**
     * Get Exam object by ExamId.
     * 
     * @param examId integer type object.
     * @return a Exam object.
     * @throws AkuraAppException throws if exception occurs
     */
    Exam findExamById(int examId) throws AkuraAppException;
    
    /**
     * Finds the ExamSubject that relevant to the ExamSubject id.
     * 
     * @param examSubjectId - the id of the ExamSubject.
     * @return - an instance of ExamSubject.
     * @throws AkuraAppException - The exception details that occurred when finding a ExamSubject.
     */
    ExamSubject findExamSubject(int examSubjectId) throws AkuraAppException;
    
    /**
     * Updates the relevant ExamSubject object.
     * 
     * @param examSubject - ExamSubject instance.
     * @throws AkuraAppException - The exception details that occurred when updating a ExamSubject.
     */
    void updateExamSubject(ExamSubject examSubject) throws AkuraAppException;
    
    /**
     * Deletes the relevant ExamSubject object.
     * 
     * @param examSubject - an instance of ExamSubject.
     * @throws AkuraAppException - The exception details that occurred when deleting a ExamSubject.
     */
    void deleteExamSubject(ExamSubject examSubject) throws AkuraAppException;
    
    /**
     * Returns a list of ExamSubject instances.
     * 
     * @return - an list of ExamSubject instances.
     * @throws AkuraAppException - The exception details that occurred when retrieving a list of ExamSubject
     *         instances.
     */
    List<ExamSubject> getAllExamSubjectList() throws AkuraAppException;
    
    /**
     * Adds a new instance of ExamSubject.
     * 
     * @param examSubject - ExamSubject instance.
     * @return - the relevant ExamSubject object.
     * @throws AkuraAppException - The exception details that occurred when saving a ExamSubject.
     */
    ExamSubject addExamSubject(ExamSubject examSubject) throws AkuraAppException;
    
    /**
     * Retrieves the subjects for a given description of exam.
     * 
     * @param description - description of the exam.
     * @return - a list of Exam Subjects.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<ExamSubject> findSubjectsByExam(final String description) throws AkuraAppException;
    
    /**
     * Retrieves Exam Subjects for a given descriptions of the Exam and the Subject.
     * 
     * @param examDescription - the description of the Exam.
     * @param subjectDescription - the description of the Subject.
     * @return - a list of ExamSubject.
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<ExamSubject> findExamSubjectByDes(final String examDescription, final String subjectDescription)
            throws AkuraAppException;
    
    /**
     * Checks the existence of the Exam Subject for a given keys of the exam and the subject.
     * 
     * @param examId - the key of the Exam.
     * @param subjectId - the key of the Subject.
     * @return - a list of existing Exam Subject.s
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    boolean isExistExamSubject(final int examId, final Integer subjectId) throws AkuraAppException;
    
    /**
     * Retrieves a list of Exams for a given description of the Exam.
     * 
     * @param examDescription - the description of the Exam.
     * @return - a list of Exams for a given description of the Exam.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    Exam getExamByExamName(final String examDescription) throws AkuraAppException;
    
    /**
     * Retrieves Exam Subjects for a given exam key.
     * 
     * @param examId - the key of the Exam.
     * @return - a list of Exam Subject.s
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<ExamSubject> getExamSubjectIdListByExam(final int examId) throws AkuraAppException;
    
    /**
     * Deletes the Exam Subjects.
     * 
     * @param examSubjectIdList - a list of Exam Subjects to be deleted.
     * @throws AkuraAppException - The exception details that occurred when deleting.
     */
    void deleteExamSubjectList(final List<ExamSubject> examSubjectIdList) throws AkuraAppException;
    
    /**
     * Checks the existence of the Assignment for grade - subject for a given keys of the grade-subject id and
     * the assignment name.
     * 
     * @param gradeSubjectId - the key of the grade subject.
     * @param year - the key of the year of the submitted date.
     * @param assignmentName - the key of the assignment name.
     * @param marksType - the key of the assignment marks type(grade or marks).
     * @return - a list of existing Assignment for selected grade-subject
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    boolean isExistAssignmentGradeSubject(final int gradeSubjectId, final String assignmentName, final int year,
            final boolean marksType) throws AkuraAppException;
    
    /**
     * Get the list of countries.
     * 
     * @return list of countries.
     * @throws AkuraAppException a detailed exception.
     */
    List<Country> getCountryList() throws AkuraAppException;
    
    /**
     * Returns a list of Section instances.
     * 
     * @return a list of Section instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Section instances.
     */
    // List<Section> getSectionsList() throws AkuraAppException;
    
    /**
     * Returns a list of Race instances.
     * 
     * @return a list of Race instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Race instances.
     */
    List<Race> getRaceList() throws AkuraAppException;
    
    /**
     * Gets the assignments list by grade.
     * 
     * @param classGradeId the class grade id
     * @return the assignments list by grade
     * @throws AkuraAppException the akura app exception
     */
    List<Object[]> getAssignmentsListByGrade(int classGradeId) throws AkuraAppException;
    
    /**
     * Gets the class grade by staff registration no.
     * 
     * @param registrationNo the registration no
     * @return the class grade by staff registration no
     * @throws AkuraAppException the akura app exception
     */
    List<ClassGrade> getClassGradeByStaffRegistrationNo(String registrationNo) throws AkuraAppException;
    
    /**
     * Finds exam subject by given exam key.
     * 
     * @param examId - the key of the Exam.
     * @return - the Exam subject key.
     * @throws AkuraAppException - The exception details that occurred when finding.
     */
    int findExamSubjectByExamId(final int examId) throws AkuraAppException;
    
    /**
     * Finds the grading by the given description.
     * 
     * @param marks - the description of the marks.
     * @return - the grading by the given description.
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    Integer findGradingByDes(final String marks) throws AkuraAppException;
    
    /**
     * Returns the count of the exam subjects.
     * 
     * @param examId - the key of the exam
     * @return - the count of the exam subjects
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    int getCountExamSubjects(final int examId) throws AkuraAppException;
    
    /**
     * Returns the new exam subjects for a given exam key.
     * 
     * @param examId - the key of the exam
     * @param year - the relevant year
     * @return - a list of exam subject keys for exam key that is newly added.
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<Integer> getNewSubjects(final int examId, final int year) throws AkuraAppException;
    
    /**
     * Get the country by Id.
     * 
     * @param countryId - the id of the country.
     * @return - a country.
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    Country findCountry(int countryId) throws AkuraAppException;
    
    /**
     * Get study medium object by id.
     * 
     * @param studyMediumId study medium id
     * @return StudyMedium object
     * @throws AkuraAppException - The detail exception that occurred when retrieving.
     */
    StudyMedium getStudyMediumById(int studyMediumId) throws AkuraAppException;
    
    /**
     * Gets the grade subject list by class grade id.
     * 
     * @param classGradeId the class grade id
     * @return the grade subject list by class grade id
     * @throws AkuraAppException the akura app exception
     */
    List<GradeSubject> getGradeSubjectListByClassGradeId(int classGradeId) throws AkuraAppException;
    
    /**
     * Get the grade subject by grade and subject.
     * 
     * @param gradeId - Grade.
     * @param subjectId - subject.
     * @return list of Grade subjects.
     * @throws AkuraAppException detailed exception throws.
     */
    List<GradeSubject> getGradeSubjectByGradeAndSubject(int gradeId, int subjectId) throws AkuraAppException;
    
    /**
     * Checks wether logged staff or the clerical staff is a teacher of the relevant class grade.
     * 
     * @param userId - the id of the staff/ clerical staff
     * @param classGradeKey - the key of the class grade
     * @return - if the class teacher returns true else false
     * @throws AkuraAppException - - The exception details that occurred when retrieving.
     */
    boolean isExistStaff(final int userId, final String classGradeKey) throws AkuraAppException;
    
    /**
     * Retrieves the optional subject list for a given exam key.
     * 
     * @param examId - the key of the exam
     * @return - a list of optional subjects for a exam
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<?> getOptionalSubjectList(final int examId) throws AkuraAppException;
    
    /**
     * Retrieves the subjects list by the exam.
     * 
     * @param selectedExamId - the key of the exam
     * @return - a list of subjects for a given exam
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<?> getSubjectListByExam(final int selectedExamId) throws AkuraAppException;
    
    /**
     * Checks wether exam subject is already exist or not.
     * 
     * @param examId - the key of the exam
     * @return - the status of the existance
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    boolean isAlreadyExistExamSubject(final int examId) throws AkuraAppException;
    
    /**
     * This method is used to get class grade by class id.
     * 
     * @param classId int value
     * @return list of class grade objects
     * @throws AkuraAppException throws if exception occurs
     */
    List<ClassGrade> getClassGradeListByClassId(int classId) throws AkuraAppException;
    
    /**
     * Returns the status of publication which exists on the table.
     * 
     * @param header - the description of the header.
     * @return - a list of exist publications
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    boolean isExistPublication(final String header) throws AkuraAppException;
    
    /**
     * Returns a list of AuditEventType instances.
     * 
     * @return a list of AuditEventType instances.
     * @throws AkuraAppException - if error occurs when retrieving a list of Race instances.
     */
    List<AuditEventType> getAuditEventType() throws AkuraAppException;
    
    /**
     * Deletes the relevant specialEventsParticipation object list.
     * 
     * @param specialEventsParticipationList - SpecialEventsParticipation list.
     * @throws AkuraAppException - if error occurs when deleting a specialEventsParticipation instance.
     */
    void deleteAllSpecialEventsParticipation(List<SpecialEventsParticipation> specialEventsParticipationList)
            throws AkuraAppException;
    
    /**
     * Checks weather exam marks are exist for the relevant exam subject key.
     * 
     * @param examSubjectId - the k
     * @return - the count of the exam marks
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    boolean isExistExamMarks(int examSubjectId) throws AkuraAppException;
    
    /**
     * Get the any Sport object by passing the sport's name.
     * 
     * @param sportName - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the Sport object.
     */
    Sport getAnySport(String sportName) throws AkuraAppException;
    
    /**
     * Service to add FaithLifeGrading object.
     * 
     * @param faithLifeGrading - FaithLifeGrading object to add
     * @return faithLifeGrading object
     * @throws AkuraAppException - throw this
     */
    
    FaithLifeGrading saveFaithLifeGrading(FaithLifeGrading faithLifeGrading) throws AkuraAppException;
    
    /**
     * Get the any races Race object by passing the race's racename.
     * 
     * @param raceName - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the Race object.
     */
    Race getAnyRace(String raceName) throws AkuraAppException;
    
    /**
     * Get the any Religion object by passing the description and religionId.
     * 
     * @param description - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the Religion object.
     */
    Religion getAnyReligion(String description) throws AkuraAppException;
    
    /**
     * Check whether religion is already added.
     * 
     * @param description - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    boolean isExistsReligion(String description) throws AkuraAppException;
    
    /**
     * Check whether religion is already added.
     * 
     * @param description - String
     * @param religionId - int
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    boolean isExistsReligion(String description, int religionId) throws AkuraAppException;
    
    /**
     * Check whether DonationType is already added.
     * 
     * @param donationTypeName - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    boolean isExistsDonationType(String donationTypeName) throws AkuraAppException;
    
    /**
     * Check whether sportCategory is already added.
     * 
     * @param intSport - Integer
     * @param intSportSub - Integer
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    boolean isExistsSportCategory(int intSport, int intSportSub) throws AkuraAppException;
    
    /**
     * Check whether the race is exist or not .
     * 
     * @param raceName - String
     * @throws AkuraAppException throws when fails
     * @return returns true or false.
     */
    
    boolean isRaceExist(String raceName) throws AkuraAppException;
    
    /**
     * Check whether the country is exists.
     * 
     * @param countryId - Integer
     * @param description - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the Country object.
     */
    boolean isExistsCountry(String description, int countryId) throws AkuraAppException;
    
    /**
     * Check whether holiday already exist.
     * 
     * @param holidayName - Holiday
     * @return true if the holiday exist else false.
     * @throws AkuraAppException - Throw detailed exception.
     */
    
    boolean isExistHoliday(Holiday holidayName) throws AkuraAppException;
    
    
    /**
     * Check whether holiday already exist.
     * 
     * @param holidayName - Holiday
     * @return true if the holiday exist else false.
     * @throws AkuraAppException - Throw detailed exception.
     */
    
    boolean isExistHolidayByDate(Holiday holidayName) throws AkuraAppException;
    
    /**
     * return list of holiday already exist.
     * 
     * @param holidayObj - Holiday
     * @return List of holiday exist else null.
     * @throws AkuraAppException - Throw detailed exception.
     */
    
    boolean getHolidayListWithInDateRange(Holiday holidayObj) throws AkuraAppException;    
    
    
    /**
     * Check whether the term is exist or not .
     * 
     * @param termName - Term
     * @param termId - integer
     * @param startDate start date
     * @param endDate end date
     * @throws AkuraAppException throws when fails
     * @return returns true or false.
     */
    boolean isExistsTerm(Term termName, int termId, Date startDate, Date endDate) throws AkuraAppException;
    
    /**
     * Check whether the SportSub is exists.
     * 
     * @param sportSubName - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the SportSub object.
     */
    boolean isExistsSportSub(String sportSubName) throws AkuraAppException;
    
    /**
     * Check whether the Method Of Travel is exists.
     * 
     * @param description - String
     * @throws AkuraAppException Detailed exception
     * @return returns the MethodOfTravel object.
     */
    boolean isExistsMethodOfTravel(String description) throws AkuraAppException;
    
    /**
     * Check whether sport is already added.
     * 
     * @param sportName - String
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    boolean isExistsSport(String sportName) throws AkuraAppException;
    
    /**
     * Check whether the Working Segment is exists.
     * 
     * @param description - String
     * @throws AkuraAppException Detailed exception
     * @return returns the WorkingSegment object.
     */
    boolean isExistsWorkingSegment(String description) throws AkuraAppException;
    
    /**
     * Check whether the Warning Level is exists.
     * 
     * @param warningLevelObj - WarningLevel
     * @throws AkuraAppException Detailed exception
     * @return returns a boolean value.
     */
    
    boolean isExistWarningLevel(WarningLevel warningLevelObj) throws AkuraAppException;
    
    /**
     * Check whether the Position is exists.
     * 
     * @param positionObj - Position
     * @throws AkuraAppException Detailed exception
     * @return returns a boolean value.
     */
    boolean isExistPosition(Position positionObj) throws AkuraAppException;
    
    /**
     * Check whether the grading information is exists.
     * 
     * @param gradingId -Integer
     * @param strGradeAcronym - String
     * @throws AkuraAppException AkuraAppException
     * @return true if it already exist else false.
     */
    boolean isExistsGradingAcronym(String strGradeAcronym, int gradingId) throws AkuraAppException;
    
    /**
     * Check whether the grading information is exists.
     * 
     * @param gradingId -Integer
     * @param decription - String
     * @throws AkuraAppException AkuraAppException
     * @return true if it already exist else false.
     */
    boolean isExistsGradingDecription(String decription, int gradingId) throws AkuraAppException;
    
    /**
     * Check whether the grading information is exists.
     * 
     * @param strGradeAcronym - String
     * @param decription - String
     * @throws AkuraAppException AkuraAppException
     * @return true if it already exist else false.
     */
    boolean isExistsGradingInformation(String strGradeAcronym, String decription) throws AkuraAppException;
    
    /**
     * Check whether SubTerm is already added.
     * 
     * @param subTermName - String
     * @param termId - Integer
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    boolean isExistsSubTerm(int termId, String subTermName) throws AkuraAppException;
    
    /**
     * Check whether SubTerm is already added.
     * 
     * @param toDate - Date
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    boolean isExistsSubTermToDate(Date toDate) throws AkuraAppException;
    
    /**
     * Check whether SubTerm is already added.
     * 
     * @param fromDate - Date
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    boolean isExistsSubTermFromDate(Date fromDate) throws AkuraAppException;
    
    /**
     * Method is to get list of Student status.
     * 
     * @return StudentStatus list.
     * @throws AkuraAppException - throw this
     */
    List<StudentStatus> getStudentStatusList() throws AkuraAppException;
    
    /**
     * Method is to get Student status.
     * 
     * @return StudentStatus list.
     * @throws AkuraAppException - throw this
     */
    List<StudentStatus> getTerminateStatus() throws AkuraAppException;
    
    /**
     * Retrieves the maximum mark for the grade subject.
     * 
     * @param gradeSubjectId - the key of the grade subject.
     * @return - the maximum mark.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    int findGradeSubjectMaxMarkById(int gradeSubjectId) throws AkuraAppException;
    
    /**
     * @param subjectId -THe id of Grade Subject
     * @param gradeDescription is key of grade
     * @return gradeSubject object
     * @throws AkuraAppException -throws when occurred the exception
     */
    
    GradeSubject findGradeSubjectByGradeAndSubjectId(int subjectId, String gradeDescription) throws AkuraAppException;
    
    /**
     * @param scholarshipName scholarshipName
     * @return String
     * @throws AkuraAppException -throws when occurred the exception
     */
    String getSponsorship(String scholarshipName) throws AkuraAppException;
    
}
