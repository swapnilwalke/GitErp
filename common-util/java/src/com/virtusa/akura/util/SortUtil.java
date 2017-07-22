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

package com.virtusa.akura.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.virtusa.akura.api.dto.AppointmentClassification;
import com.virtusa.akura.api.dto.AppointmentNature;
import com.virtusa.akura.api.dto.Assignment;
import com.virtusa.akura.api.dto.AuditEventType;
import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.BusinessAudit;
import com.virtusa.akura.api.dto.CRStudentDisciplinaryActionClassWise;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.CivilStatus;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ClassTeacher;
import com.virtusa.akura.api.dto.ClassWiseStudentReportData;
import com.virtusa.akura.api.dto.ClubSociety;
import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Donation;
import com.virtusa.akura.api.dto.DonationType;
import com.virtusa.akura.api.dto.EducationalQualification;
import com.virtusa.akura.api.dto.EmploymentStatus;
import com.virtusa.akura.api.dto.Exam;
import com.virtusa.akura.api.dto.FaithLifeCategory;
import com.virtusa.akura.api.dto.FaithLifeComment;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.ParticipantCategory;
import com.virtusa.akura.api.dto.PerDayAttendanceTemplate;
import com.virtusa.akura.api.dto.Position;
import com.virtusa.akura.api.dto.PrefectType;
import com.virtusa.akura.api.dto.ProfessionalQualification;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Publication;
import com.virtusa.akura.api.dto.PublicationType;
import com.virtusa.akura.api.dto.Race;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Scholarship;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.Section;
import com.virtusa.akura.api.dto.SectionHead;
import com.virtusa.akura.api.dto.Seminar;
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.api.dto.SportCategory;
import com.virtusa.akura.api.dto.SportSub;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.dto.StaffLeaveType;
import com.virtusa.akura.api.dto.StaffServiceCategory;
import com.virtusa.akura.api.dto.Stream;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentAchievementTemplate;
import com.virtusa.akura.api.dto.StudentAssignmentMarkTemplate;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentClubSociety;
import com.virtusa.akura.api.dto.StudentFaithLifeTemplate;
import com.virtusa.akura.api.dto.StudentLeave;
import com.virtusa.akura.api.dto.StudentMarksTemplate;
import com.virtusa.akura.api.dto.StudentPrefect;
import com.virtusa.akura.api.dto.StudentScholarship;
import com.virtusa.akura.api.dto.StudentScholarshipTemplate;
import com.virtusa.akura.api.dto.StudentSpecialEventAttendanceTemplate;
import com.virtusa.akura.api.dto.StudentSport;
import com.virtusa.akura.api.dto.StudentTemporaryLeave;
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.dto.StudentTermMarksTemplate;
import com.virtusa.akura.api.dto.StudyMedium;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.SubjectTeacher;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.dto.UserRole;
import com.virtusa.akura.api.dto.WarningLevel;
import com.virtusa.akura.api.dto.WorkingSegment;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * SortUtil is to sort a list in alphabetic order.
 * 
 * @author Virtusa Corporation
 */
public final class SortUtil {
    
    /**
     * Logger to log the error information.
     */
    private static final Logger LOG = Logger.getLogger(SortUtil.class);
    
    /**
     * private constructor.
     */
    private SortUtil() {
    
    }
    
    /**
     * This is to sort the List of religion objects based on the description of the religion.
     * 
     * @param religionList - List of Religion
     * @return list of sorted religion
     */
    public static List<Religion> sortReligionList(List<Religion> religionList) {
    
        LOG.info("*** Inside sortReligionList method ***");
        
        Collections.sort(religionList, new Comparator<Religion>() {
            
            public int compare(Religion religion1, Religion religion2) {
            
                return religion1.getDescription().compareToIgnoreCase(religion2.getDescription());
            }
        });
        
        return religionList;
    }
    
    /**
     * This is to sort the List of Appointments objects based on the description of the Appointment.
     * 
     * @param appointmentNatureList - List of Appointments
     * @return list of sorted appoitmentNature
     */
    public static List<AppointmentNature> sortAppointmentNatureList(List<AppointmentNature> appointmentNatureList) {
    
        LOG.info("*** Inside sortAppointmentNatureList method ***");
        
        Collections.sort(appointmentNatureList, new Comparator<AppointmentNature>() {
            
            public int compare(AppointmentNature appointmentNature1, AppointmentNature appointmentNature2) {
            
                return appointmentNature1.getDescription().compareToIgnoreCase(appointmentNature2.getDescription());
            }
        });
        
        return appointmentNatureList;
    }
    
    /**
     * This is to sort the List of Language objects based on the Language.
     * 
     * @param languageList - List of Language
     * @return list of sorted Language
     */
    public static List<Language> sortLanguageList(List<Language> languageList) {
    
        LOG.info("*** Inside sortLanguageList method ***");
        
        Collections.sort(languageList, new Comparator<Language>() {
            
            public int compare(Language language1, Language language2) {
            
                return language1.getLanguage().compareToIgnoreCase(language2.getLanguage());
            }
        });
        
        return languageList;
    }
    
    /**
     * This is to sort the List of House objects based on the House name.
     * 
     * @param houseList - List of House
     * @return list of sorted House
     */
    public static List<House> sortHouseList(List<House> houseList) {
    
        LOG.info("*** Inside sortHouseList method ***");
        
        Collections.sort(houseList, new Comparator<House>() {
            
            public int compare(House house1, House house2) {
            
                return house1.getName().compareToIgnoreCase(house2.getName());
            }
        });
        
        return houseList;
    }
    
    /**
     * This is to sort the List of nationality objects based on the description of the nationality.
     * 
     * @param nationalityList - List of nationality
     * @return list of sorted nationality
     */
    public static List<Nationality> sortNationalityList(List<Nationality> nationalityList) {
    
        LOG.info("*** Inside sortNationalityList method ***");
        
        Collections.sort(nationalityList, new Comparator<Nationality>() {
            
            public int compare(Nationality nationality1, Nationality nationality2) {
            
                return nationality1.getDescription().compareToIgnoreCase(nationality2.getDescription());
            }
        });
        
        return nationalityList;
    }
    
    /**
     * This is to sort the List of race objects based on the description of the race.
     * 
     * @param raceList - List of race
     * @return list of sorted race
     */
    public static List<Race> sortRaceList(List<Race> raceList) {
    
        LOG.info("*** Inside sortRaceList method ***");
        
        Collections.sort(raceList, new Comparator<Race>() {
            
            public int compare(Race race1, Race race2) {
            
                return race1.getDescription().compareToIgnoreCase(race2.getDescription());
            }
        });
        
        return raceList;
    }
    
    /**
     * This is to sort the List of BloodGroup objects based on the description of the BloodGroup.
     * 
     * @param bloodGroupList - List of BloodGroup
     * @return list of sorted BloodGroup
     */
    public static List<BloodGroup> sortBloodGroupList(List<BloodGroup> bloodGroupList) {
    
        LOG.info("*** Inside sortNationalityList method ***");
        
        Collections.sort(bloodGroupList, new Comparator<BloodGroup>() {
            
            public int compare(BloodGroup bloodGroup1, BloodGroup bloodGroup2) {
            
                return bloodGroup1.getDescription().compareToIgnoreCase(bloodGroup2.getDescription());
            }
        });
        
        return bloodGroupList;
    }
    
    /**
     * This is to sort the List of MethodOfTravel objects based on the travel method of the MethodOfTravel.
     * 
     * @param methodOfTravelList - List of MethodOfTravel
     * @return list of sorted MethodOfTravel
     */
    public static List<MethodOfTravel> sortMethodOfTravelList(List<MethodOfTravel> methodOfTravelList) {
    
        LOG.info("*** Inside sortNationalityList method ***");
        
        Collections.sort(methodOfTravelList, new Comparator<MethodOfTravel>() {
            
            public int compare(MethodOfTravel methodOfTravel1, MethodOfTravel methodOfTravel2) {
            
                return methodOfTravel1.getTravelMethod().compareToIgnoreCase(methodOfTravel2.getTravelMethod());
            }
        });
        
        return methodOfTravelList;
    }
    
    /**
     * This is to sort the List of City objects based on the description of the City.
     * 
     * @param cityList - List of City
     * @return list of sorted MethodOfTravel
     */
    public static List<City> sortCityList(List<City> cityList) {
    
        LOG.info("*** Inside sortNationalityList method ***");
        
        Collections.sort(cityList, new Comparator<City>() {
            
            public int compare(City city1, City city2) {
            
                return city1.getDescription().compareToIgnoreCase(city2.getDescription());
            }
        });
        
        return cityList;
    }
    
    /**
     * This is to sort the List of Country objects based on the description of the Country.
     * 
     * @param countryList - List of Country
     * @return list of sorted MethodOfTravel
     */
    public static List<Country> sortCountryList(List<Country> countryList) {
    
        LOG.info("*** Inside sortNationalityList method ***");
        
        Collections.sort(countryList, new Comparator<Country>() {
            
            public int compare(Country country1, Country country2) {
            
                return country1.getCountryName().compareToIgnoreCase(country2.getCountryName());
            }
        });
        
        return countryList;
    }
    
    /**
     * This is to sort the List of StaffCategory objects based on the description of the StaffCategory.
     * 
     * @param staffCategoryList - List of StaffCategory
     * @return list of sorted StaffCategory
     */
    public static List<StaffCategory> sortStaffCategoryList(List<StaffCategory> staffCategoryList) {
    
        LOG.info("*** Inside sortStaffCategoryList method ***");
        
        Collections.sort(staffCategoryList, new Comparator<StaffCategory>() {
            
            public int compare(StaffCategory staffCategory1, StaffCategory staffCategory2) {
            
                return staffCategory1.getDescription().compareToIgnoreCase(staffCategory2.getDescription());
            }
        });
        
        return staffCategoryList;
    }
    
    /**
     * This is to sort the List of EducationalQualification objects based on the description of the
     * EducationalQualification.
     * 
     * @param educationalQualificationList - List of EducationalQualification
     * @return list of sorted EducationalQualification
     */
    public static List<EducationalQualification> sortEducationalQualificationList(
            List<EducationalQualification> educationalQualificationList) {
    
        LOG.info("*** Inside sortEducationalQualificationList method ***");
        
        Collections.sort(educationalQualificationList, new Comparator<EducationalQualification>() {
            
            public int compare(EducationalQualification educationalQualification1,
                    EducationalQualification educationalQualification2) {
            
                return educationalQualification1.getDescription().compareToIgnoreCase(
                        educationalQualification2.getDescription());
            }
        });
        
        return educationalQualificationList;
    }
    
    /**
     * This is to sort the List of ProfessionalQualification objects based on the description of the
     * ProfessionalQualification.
     * 
     * @param professionalQualificationList - List of ProfessionalQualification
     * @return list of sorted ProfessionalQualification
     */
    public static List<ProfessionalQualification> sortProfessionalQualificationList(
            List<ProfessionalQualification> professionalQualificationList) {
    
        LOG.info("*** Inside sortProfessionalQualificationList method ***");
        
        Collections.sort(professionalQualificationList, new Comparator<ProfessionalQualification>() {
            
            public int compare(ProfessionalQualification professionalQualification1,
                    ProfessionalQualification professionalQualification2) {
            
                return professionalQualification1.getDescription().compareToIgnoreCase(
                        professionalQualification2.getDescription());
            }
        });
        
        return professionalQualificationList;
    }
    
    /**
     * This is to sort the List of Districts objects based on the description of the district.
     * 
     * @param districtList - List of Districts
     * @return list of sorted districts
     */
    public static List<District> sortDistrictList(List<District> districtList) {
    
        LOG.info("*** Inside sortDistrictList method ***");
        
        Collections.sort(districtList, new Comparator<District>() {
            
            public int compare(District district1, District district2) {
            
                return district1.getDescription().compareToIgnoreCase(district2.getDescription());
            }
        });
        
        return districtList;
    }
    
    /**
     * This is to sort the List of Province objects based on the description of the province.
     * 
     * @param provinceList - List of Provinces
     * @return list of sorted Provinces
     */
    public static List<Province> sortProvinceList(List<Province> provinceList) {
    
        LOG.info("*** Inside sortProvinceList method ***");
        
        Collections.sort(provinceList, new Comparator<Province>() {
            
            public int compare(Province province1, Province province2) {
            
                return province1.getDescription().compareToIgnoreCase(province2.getDescription());
            }
        });
        return provinceList;
    }
    
    /**
     * This is to sort the List of Staff objects based on the description of the Staff.
     * 
     * @param staffList - List of Staff
     * @return list of sorted Staff
     */
    public static List<Staff> sortStaffList(List<Staff> staffList) {
    
        LOG.info("*** Inside sortStaffCategoryList method ***");
        
        Collections.sort(staffList, new Comparator<Staff>() {
            
            public int compare(Staff staff1, Staff staff2) {
            
                return staff1.getFullName().compareToIgnoreCase(staff2.getFullName());
            }
        });
        
        return staffList;
    }
    
    /**
     * This is to sort the List of Staff objects based on registration number of the Staff.
     * 
     * @param staffList - List of Staff
     * @return list of Staff sorted by registration number
     */
    public static List<Staff> sortStaffListByRegistrationNumber(List<Staff> staffList) {
    
        LOG.info("*** Inside sortStaffListByRegistrationNumber method ***");
        
        Collections.sort(staffList, new Comparator<Staff>() {
            
            /**
             * The compare method that compares the alphanumeric strings
             */
            public int compare(Staff staff1, Staff staff2) {
            
                return sortString(staff1.getRegistrationNo(), staff2.getRegistrationNo());
            }
            
        });
        
        return staffList;
    }
    
    /**
     * This is to sort the List of WorkingSegment objects based on the description of the WorkingSegment.
     * 
     * @param workingSegmentList - List of WorkingSegment
     * @return list of sorted WorkingSegment
     */
    public static List<WorkingSegment> sortWorkingSegmentList(List<WorkingSegment> workingSegmentList) {
    
        LOG.info("*** Inside sortWorkingSegmentList method ***");
        
        Collections.sort(workingSegmentList, new Comparator<WorkingSegment>() {
            
            public int compare(WorkingSegment segment1, WorkingSegment segment2) {
            
                return segment1.getDescription().compareToIgnoreCase(segment2.getDescription());
            }
        });
        
        return workingSegmentList;
    }
    
    /**
     * This is to sort the List of ClubSociety objects based on the description of the ClubSociety.
     * 
     * @param clubSocietyList - List of clubSociety
     * @return list of sorted clubSociety
     */
    public static List<ClubSociety> sortClubSocietyList(List<ClubSociety> clubSocietyList) {
    
        LOG.info("*** Inside sortClubSocietyList method ***");
        
        Collections.sort(clubSocietyList, new Comparator<ClubSociety>() {
            
            public int compare(ClubSociety clubSociety1, ClubSociety clubSociety2) {
            
                return clubSociety1.getName().compareToIgnoreCase(clubSociety2.getName());
            }
        });
        
        return clubSocietyList;
    }
    
    /**
     * This is to sort the List of gradingList objects based on the description of the grading.
     * 
     * @param gradingList - List of grading
     * @return list of sorted grading
     */
    public static List<Grading> sortGradingList(List<Grading> gradingList) {
    
        LOG.info("*** Inside sortGradingList method ***");
        
        Collections.sort(gradingList, new Comparator<Grading>() {
            
            public int compare(Grading grading1, Grading grading2) {
            
                return grading1.getGradeAcronym().compareToIgnoreCase(grading2.getGradeAcronym());
            }
        });
        
        return gradingList;
    }
    
    /**
     * This is to sort the List of donationTypeList objects based on the description of the donationType.
     * 
     * @param donationTypeList - List of donationType
     * @return list of sorted donationType
     */
    public static List<DonationType> sortDonationTypeList(List<DonationType> donationTypeList) {
    
        LOG.info("*** Inside sortDonationTypeList method ***");
        
        Collections.sort(donationTypeList, new Comparator<DonationType>() {
            
            public int compare(DonationType donationType1, DonationType donationType2) {
            
                return donationType1.getDescription().compareToIgnoreCase(donationType2.getDescription());
            }
        });
        
        return donationTypeList;
    }
    
    /**
     * This is to sort the List of prefectTypeList objects based on the description of the prefectType.
     * 
     * @param prefectTypeList - List of prefectType
     * @return list of sorted prefectType.
     */
    public static List<PrefectType> sortPrefectTypeList(List<PrefectType> prefectTypeList) {
    
        LOG.info("*** Inside sortPrefectTypeList method ***");
        
        Collections.sort(prefectTypeList, new Comparator<PrefectType>() {
            
            public int compare(PrefectType prefectType1, PrefectType prefectType2) {
            
                return prefectType1.getDescription().compareToIgnoreCase(prefectType2.getDescription());
            }
        });
        
        return prefectTypeList;
    }
    
    /**
     * This is to sort the List of examList objects based on the description of the exam.
     * 
     * @param examList - List of exam
     * @return list of sorted exam.
     */
    
    public static List<Exam> sortExamList(List<Exam> examList) {
    
        LOG.info("*** Inside sortExamList method ***");
        
        Collections.sort(examList, new Comparator<Exam>() {
            
            public int compare(Exam exam1, Exam exam2) {
            
                return exam1.getDescription().compareToIgnoreCase(exam2.getDescription());
            }
        });
        
        return examList;
    }
    
    /**
     * This is to sort the List of termList objects based on the description of the term.
     * 
     * @param termList - List of term
     * @return list of sorted term.
     */
    public static List<Term> sortTermList(List<Term> termList) {
    
        LOG.info("*** Inside sortTermList method ***");
        
        Collections.sort(termList, new Comparator<Term>() {
            
            public int compare(Term term1, Term term2) {
            
                return term1.getDescription().compareToIgnoreCase(term2.getDescription());
            }
        });
        
        return termList;
    }
    
    /**
     * This is to sort the List of positionList objects based on the description of the position.
     * 
     * @param positionList - List of position
     * @return list of sorted position.
     */
    public static List<Position> sortPositionList(List<Position> positionList) {
    
        LOG.info("*** Inside sortPositionList method ***");
        
        Collections.sort(positionList, new Comparator<Position>() {
            
            public int compare(Position position1, Position position2) {
            
                return position1.getDescription().compareToIgnoreCase(position2.getDescription());
            }
        });
        
        return positionList;
    }
    
    /**
     * This is to sort the List of scholarshipList objects based on the description of the scholarship.
     * 
     * @param scholarshipList - List of scholarship
     * @return list of sorted scholarship.
     */
    public static List<Scholarship> sortScholarshipList(List<Scholarship> scholarshipList) {
    
        LOG.info("*** Inside sortScholarshipList method ***");
        
        Collections.sort(scholarshipList, new Comparator<Scholarship>() {
            
            public int compare(Scholarship scholarship1, Scholarship scholarship2) {
            
                return scholarship1.getName().compareToIgnoreCase(scholarship2.getName());
            }
        });
        
        return scholarshipList;
    }
    
    /**
     * This is to sort the List of sportSubList objects based on the description of the sportSub.
     * 
     * @param sportSubList - List of sportSub
     * @return list of sorted sportSub.
     */
    public static List<SportSub> sortSportSubList(List<SportSub> sportSubList) {
    
        LOG.info("*** Inside sortSportSubList method ***");
        
        Collections.sort(sportSubList, new Comparator<SportSub>() {
            
            public int compare(SportSub sportSub1, SportSub sportSub2) {
            
                return sportSub1.getDescription().compareToIgnoreCase(sportSub2.getDescription());
            }
        });
        
        return sportSubList;
    }
    
    /**
     * This is to sort the List of sportList objects based on the description of the sport.
     * 
     * @param sportList - List of sport
     * @return list of sorted sport.
     */
    public static List<Sport> sortSportList(List<Sport> sportList) {
    
        LOG.info("*** Inside sortSportList method ***");
        
        Collections.sort(sportList, new Comparator<Sport>() {
            
            public int compare(Sport sport1, Sport sport2) {
            
                return sport1.getDescription().compareToIgnoreCase(sport2.getDescription());
            }
        });
        
        return sportList;
    }
    
    /**
     * This is to sort the List of Stream objects based on the description of the province.
     * 
     * @param streamList - List of Streams
     * @return list of sorted Streams
     */
    public static List<Stream> sortStreamList(List<Stream> streamList) {
    
        LOG.info("*** Inside sortStreamList method ***");
        
        Collections.sort(streamList, new Comparator<Stream>() {
            
            public int compare(Stream stream1, Stream stream2) {
            
                return stream1.getDescription().compareToIgnoreCase(stream2.getDescription());
            }
        });
        return streamList;
    }
    
    /**
     * This is to sort the List of Subject objects based on the description of the Subject.
     * 
     * @param subjectList - List of Subjects
     * @return list of sorted Subjects
     */
    public static List<Subject> sortSubjectList(List<Subject> subjectList) {
    
        LOG.info("*** Inside sortSubjectList method ***");
        
        Collections.sort(subjectList, new Comparator<Subject>() {
            
            public int compare(Subject subject1, Subject subject2) {
            
                return subject1.getDescription().compareToIgnoreCase(subject2.getDescription());
            }
        });
        return subjectList;
    }
    
    /**
     * This is to sort the List of UserRole objects based on the role.
     * 
     * @param userRoleList - List of userRole
     * @return list of sorted UserRole
     */
    public static List<UserRole> sortUserRoleList(List<UserRole> userRoleList) {
    
        LOG.info("*** Inside sortUserRoleList method ***");
        
        Collections.sort(userRoleList, new Comparator<UserRole>() {
            
            public int compare(UserRole userRole1, UserRole userRole2) {
            
                return userRole1.getDescription().compareToIgnoreCase(userRole2.getDescription());
            }
        });
        return userRoleList;
    }
    
    /**
     * This is to sort the List of UserLogin objects based on the username.
     * 
     * @param userLoginList - List of userLogin
     * @return list of sorted UserLogin
     */
    public static List<UserLogin> sortUserLoginList(List<UserLogin> userLoginList) {
    
        LOG.info("*** Inside sortUserLoginList method ***");
        
        Collections.sort(userLoginList, new Comparator<UserLogin>() {
            
            public int compare(UserLogin userLogin1, UserLogin userLogin2) {
            
                return userLogin1.getUsername().compareToIgnoreCase(userLogin2.getUsername());
            }
        });
        return userLoginList;
    }
    
    /**
     * This is to sort the List of Grade objects based on the description This sort for alphanumeric strings.
     * 
     * @param gradeList - List of Grade
     * @return list of sorted Grade
     */
    public static List<Grade> sortGradeList(List<Grade> gradeList) {
    
        LOG.info("*** Inside sortGradeList method ***");
        
        Collections.sort(gradeList, new Comparator<Grade>() {
            
            /**
             * The compare method that compares the alphanumeric strings
             */
            public int compare(Grade grade1, Grade grade2) {
            
                String firstString = grade1.getDescription();
                String secondString = grade2.getDescription();
                
                if (secondString == null || firstString == null) {
                    return 0;
                }
                
                int lengthFirstStr = firstString.length();
                int lengthSecondStr = secondString.length();
                
                int index1 = 0;
                int index2 = 0;
                
                while (index1 < lengthFirstStr && index2 < lengthSecondStr) {
                    char ch1 = firstString.charAt(index1);
                    char ch2 = secondString.charAt(index2);
                    
                    char[] space1 = new char[lengthFirstStr];
                    char[] space2 = new char[lengthSecondStr];
                    
                    int loc1 = 0;
                    int loc2 = 0;
                    
                    do {
                        space1[loc1++] = ch1;
                        index1++;
                        
                        if (index1 < lengthFirstStr) {
                            ch1 = firstString.charAt(index1);
                        } else {
                            break;
                        }
                    } while (Character.isDigit(ch1) == Character.isDigit(space1[0]));
                    
                    do {
                        space2[loc2++] = ch2;
                        index2++;
                        
                        if (index2 < lengthSecondStr) {
                            ch2 = secondString.charAt(index2);
                        } else {
                            break;
                        }
                    } while (Character.isDigit(ch2) == Character.isDigit(space2[0]));
                    
                    String str1 = new String(space1);
                    String str2 = new String(space2);
                    
                    int result;
                    
                    if (Character.isDigit(space1[0]) && Character.isDigit(space2[0])) {
                        Integer firstNumberToCompare = new Integer(Integer.parseInt(str1.trim()));
                        Integer secondNumberToCompare = new Integer(Integer.parseInt(str2.trim()));
                        result = firstNumberToCompare.compareTo(secondNumberToCompare);
                    } else {
                        result = str1.compareTo(str2);
                    }
                    
                    if (result != 0) {
                        return result;
                    }
                }
                return lengthFirstStr - lengthSecondStr;
                
            }
        });
        return gradeList;
    }
    
    /**
     * This is to sort the List of ClassGrade objects based on the description.
     * 
     * @param classGradeList - List of ClassGrade
     * @return list of sorted ClassGrade
     */
    public static List<ClassGrade> sortClassGradeList(List<ClassGrade> classGradeList) {
    
        LOG.info("*** Inside sortGradeList method ***");
        
        Collections.sort(classGradeList, new Comparator<ClassGrade>() {
            
            /**
             * The compare method that compares the alphanumeric strings
             */
            public int compare(ClassGrade classGrade1, ClassGrade classGrade2) {
            
                String firstString = classGrade1.getDescription();
                String secondString = classGrade2.getDescription();
                
                if (secondString == null || firstString == null) {
                    return 0;
                }
                
                int lengthFirstStr = firstString.length();
                int lengthSecondStr = secondString.length();
                
                int index1 = 0;
                int index2 = 0;
                
                while (index1 < lengthFirstStr && index2 < lengthSecondStr) {
                    char ch1 = firstString.charAt(index1);
                    char ch2 = secondString.charAt(index2);
                    
                    char[] space1 = new char[lengthFirstStr];
                    char[] space2 = new char[lengthSecondStr];
                    
                    int loc1 = 0;
                    int loc2 = 0;
                    
                    do {
                        space1[loc1++] = ch1;
                        index1++;
                        
                        if (index1 < lengthFirstStr) {
                            ch1 = firstString.charAt(index1);
                        } else {
                            break;
                        }
                    } while (Character.isDigit(ch1) == Character.isDigit(space1[0]));
                    
                    do {
                        space2[loc2++] = ch2;
                        index2++;
                        
                        if (index2 < lengthSecondStr) {
                            ch2 = secondString.charAt(index2);
                        } else {
                            break;
                        }
                    } while (Character.isDigit(ch2) == Character.isDigit(space2[0]));
                    
                    String str1 = new String(space1);
                    String str2 = new String(space2);
                    
                    int result;
                    
                    if (Character.isDigit(space1[0]) && Character.isDigit(space2[0])) {
                        Integer firstNumberToCompare = new Integer(Integer.parseInt(str1.trim()));
                        Integer secondNumberToCompare = new Integer(Integer.parseInt(str2.trim()));
                        result = firstNumberToCompare.compareTo(secondNumberToCompare);
                    } else {
                        result = str1.compareTo(str2);
                    }
                    
                    if (result != 0) {
                        return result;
                    }
                }
                return lengthFirstStr - lengthSecondStr;
                
            }
        });
        return classGradeList;
    }
    
    /**
     * This is to sort the List of Grade Subjects objects based on the description of the district.
     * 
     * @param gradeSubjectList - List of Grade Subjects
     * @return list of sorted districts
     */
    public static List<GradeSubject> sortGradeSubjectList(List<GradeSubject> gradeSubjectList) {
    
        LOG.info("*** Inside sortGradeSubjectList method ***");
        
        Collections.sort(gradeSubjectList, new Comparator<GradeSubject>() {
            
            public int compare(GradeSubject gradeSubject1, GradeSubject gradeSubject2) {
            
                return gradeSubject1.getSubjectCode().compareToIgnoreCase(gradeSubject2.getSubjectCode());
            }
        });
        
        return gradeSubjectList;
    }
    
    /**
     * This is to sort the List of Class Teacher objects based on the description of the district.
     * 
     * @param classTeacherList - List of Class Teacher
     * @return list of sorted districts
     */
    public static List<ClassTeacher> sortClassTeacherList(List<ClassTeacher> classTeacherList) {
    
        LOG.info("*** Inside sortClassTeacherList method ***");
        
        Collections.sort(classTeacherList, new Comparator<ClassTeacher>() {
            
            public int compare(ClassTeacher classTeacher1, ClassTeacher classTeacher2) {
            
                return classTeacher1.getStaff().getFullName()
                        .compareToIgnoreCase(classTeacher2.getStaff().getFullName());
            }
        });
        
        return classTeacherList;
    }
    
    /**
     * This is to sort the List of SubjectTeacher objects based on the description of the district.
     * 
     * @param subjectTeacherList - List of SubjectTeacher
     * @return list of sorted districts
     */
    public static List<SubjectTeacher> sortSubjectTeacherList(List<SubjectTeacher> subjectTeacherList) {
    
        LOG.info("*** Inside sortClassTeacherList method ***");
        
        Collections.sort(subjectTeacherList, new Comparator<SubjectTeacher>() {
            
            public int compare(SubjectTeacher subjectTeacher1, SubjectTeacher subjectTeacher2) {
            
                return subjectTeacher1.getStaff().getFullName()
                        .compareToIgnoreCase(subjectTeacher2.getStaff().getFullName());
            }
        });
        
        return subjectTeacherList;
    }
    
    /**
     * This is to sort the List of SectionHead objects based on the description of the district.
     * 
     * @param sectionHeadList - List of SectionHead
     * @return list of sorted districts
     */
    public static List<SectionHead> sortSectionHeadList(List<SectionHead> sectionHeadList) {
    
        LOG.info("*** Inside sortClassTeacherList method ***");
        
        Collections.sort(sectionHeadList, new Comparator<SectionHead>() {
            
            public int compare(SectionHead sectionHead1, SectionHead sectionHead2) {
            
                return sectionHead1.getStaff().getFullName().compareToIgnoreCase(sectionHead2.getStaff().getFullName());
            }
        });
        
        return sectionHeadList;
    }
    
    /**
     * This is to sort the List of donationList objects based on the purpose of the donation.
     * 
     * @param donationList - List of donation
     * @return list of sorted donationType
     */
    public static List<Donation> sortDonationList(List<Donation> donationList) {
    
        LOG.info("*** Inside sortDonationList method ***");
        
        Collections.sort(donationList, new Comparator<Donation>() {
            
            public int compare(Donation donation1, Donation donation2) {
            
                return donation1.getPurpose().compareToIgnoreCase(donation2.getPurpose());
            }
        });
        
        return donationList;
    }
    
    /**
     * This is to sort the List of schoolClassList objects based on the purpose of the classes.
     * 
     * @param schoolClassList - List of classes
     * @return list of sorted schoolClass type
     */
    public static List<SchoolClass> sortSchoolClassList(List<SchoolClass> schoolClassList) {
    
        LOG.info("*** Inside sortSchoolClassList method ***");
        
        Collections.sort(schoolClassList, new Comparator<SchoolClass>() {
            
            public int compare(SchoolClass class1, SchoolClass class2) {
            
                return class1.getDescription().compareToIgnoreCase(class2.getDescription());
            }
        });
        
        return schoolClassList;
    }
    
    /**
     * This is to sort the List of faithLifeCategoryList objects based on the description of the
     * FaithLifeCategory.
     * 
     * @param faithLifeCategoryList - List of FaithLifeCategory
     * @return list of sorted FaithLifeCategories
     */
    public static List<FaithLifeCategory> sortFaithLifeCategoryList(List<FaithLifeCategory> faithLifeCategoryList) {
    
        LOG.info("*** Inside sortFaithLifeCategoryList method ***");
        
        Collections.sort(faithLifeCategoryList, new Comparator<FaithLifeCategory>() {
            
            public int compare(FaithLifeCategory faithLifeCategory1, FaithLifeCategory faithLifeCategory2) {
            
                return faithLifeCategory1.getDescription().compareToIgnoreCase(faithLifeCategory2.getDescription());
            }
        });
        
        return faithLifeCategoryList;
    }
    
    /**
     * This is to sort the List of Seminar objects based on the description.
     * 
     * @param seminarList - List of Seminar
     * @return list of sorted Seminar
     */
    public static List<Seminar> sortSeminarList(List<Seminar> seminarList) {
    
        LOG.info("*** Inside sortSeminarList method ***");
        
        Collections.sort(seminarList, new Comparator<Seminar>() {
            
            public int compare(Seminar seminar1, Seminar seminar2) {
            
                return seminar1.getDescription().compareToIgnoreCase(seminar2.getDescription());
            }
        });
        return seminarList;
    }
    
    /**
     * Sorts the list of Publication according to the modified time.
     * 
     * @param publications - a list of publication.
     * @return - a sorted list of publication.
     */
    public static List<Publication> sortPublicationList(List<Publication> publications) {
    
        LOG.info("*** Inside publication method ***");
        
        Collections.sort(publications, new Comparator<Publication>() {
            
            public int compare(Publication publication1, Publication publication2) {
            
                return publication1.getModifiedTime().compareTo(publication2.getModifiedTime());
            }
        });
        Collections.reverse(publications);
        return publications;
    }
    
    /**
     * Sorts the list of PublicationType according to the type of the publicationType.
     * 
     * @param publicationTypes - a list of publicationType.
     * @return - a sorted list of publicationType.
     */
    public static List<PublicationType> sortPublicationTypeList(List<PublicationType> publicationTypes) {
    
        LOG.info("*** Inside publicationType method ***");
        
        Collections.sort(publicationTypes, new Comparator<PublicationType>() {
            
            public int compare(PublicationType publicationType1, PublicationType publicationType2) {
            
                return publicationType1.getType().compareTo(publicationType2.getType());
            }
        });
        
        return publicationTypes;
    }
    
    /**
     * This is to sort the List of studentMarksTemplateList objects.
     * 
     * @param studentMarksTemplateList - List of object
     * @return list of sorted studentMarksTemplate
     */
    public static List<Object> sortStudentMarksTemplateList(List<Object> studentMarksTemplateList) {
    
        LOG.info("*** Inside sortStudentMarksTemplateList method ***");
        
        Collections.sort(studentMarksTemplateList, new Comparator<Object>() {
            
            public int compare(Object o1, Object o2) {
            
                StudentMarksTemplate s1 = (StudentMarksTemplate) o1;
                StudentMarksTemplate s2 = (StudentMarksTemplate) o2;
                return s1.getTerm().compareToIgnoreCase(s2.getTerm());
            }
        });
        
        return studentMarksTemplateList;
    }
    
    /**
     * This is to sort the List of studentTermMarksTemplateList objects.
     * 
     * @param studentTermMarksTemplateList - List of object
     * @return list of sorted studentTermMarksTemplate
     */
    public static List<Object> sortStudentTermMarksTemplateList(List<Object> studentTermMarksTemplateList) {
    
        LOG.info("*** Inside studentTermMarksTemplateList method ***");
        
        Collections.sort(studentTermMarksTemplateList, new Comparator<Object>() {
            
            public int compare(Object o1, Object o2) {
            
                StudentTermMarksTemplate s1 = (StudentTermMarksTemplate) o1;
                StudentTermMarksTemplate s2 = (StudentTermMarksTemplate) o2;
                return s1.getTerm().compareToIgnoreCase(s2.getTerm());
            }
        });
        
        return studentTermMarksTemplateList;
    }
    
    /**
     * Sorts the list of StudentTermMarkDTO according to the term.
     * 
     * @param studentTermMarkDTOList - a list of studentTermMarkDTO.
     * @return - a sorted list of StudentTermMarkDTO.
     */
    public static List<StudentTermMarkDTO> sortStudentTermMarkDTOList(List<StudentTermMarkDTO> studentTermMarkDTOList) {
    
        LOG.info("*** Inside sortStudentTermMarkDTOList method ***");
        
        Collections.sort(studentTermMarkDTOList, new Comparator<StudentTermMarkDTO>() {
            
            public int compare(StudentTermMarkDTO studentTermMarkDTO1, StudentTermMarkDTO studentTermMarkDTO2) {
            
                return studentTermMarkDTO1.getYear().compareTo(studentTermMarkDTO2.getYear());
            }
        });
        
        return studentTermMarkDTOList;
    }
    
    /**
     * Sorts the list of StudentClubSociety according to the year added club society.
     * 
     * @param studentClubSocieties - a list of StudentClubSociety.
     * @return - a sorted list of StudentClubSociety.
     */
    public static List<StudentClubSociety> sortStudentClubSocietyList(List<StudentClubSociety> studentClubSocieties) {
    
        LOG.info("*** Inside sortStudentClubSocietyList method ***");
        
        Collections.sort(studentClubSocieties, new Comparator<StudentClubSociety>() {
            
            public int compare(StudentClubSociety studentClubSociety1, StudentClubSociety studentClubSociety2) {
            
                return studentClubSociety1.getYear().compareTo(studentClubSociety2.getYear());
            }
        });
        
        return studentClubSocieties;
    }
    
    /**
     * Sorts the list of StudentClubSociety according to the name of club society.
     * 
     * @param studentClubSocieties - a list of StudentClubSociety.
     * @return - a sorted list of StudentClubSociety.
     */
    public static List<StudentClubSociety> sortStudentClubSocietyListByName(
            List<StudentClubSociety> studentClubSocieties) {
    
        LOG.info("*** Inside sortStudentClubSocietyList method ***");
        
        Collections.sort(studentClubSocieties, new Comparator<StudentClubSociety>() {
            
            public int compare(StudentClubSociety studentClubSociety1, StudentClubSociety studentClubSociety2) {
            
                return studentClubSociety1.getClubSociety().getName()
                        .compareTo(studentClubSociety2.getClubSociety().getName());
            }
        });
        
        return studentClubSocieties;
    }
    
    /**
     * This is to sort the List of Student Sub Term Marks Template objects.
     * 
     * @param studentSubTermMarksTemplateList - List of StudentMarksTemplate object
     * @return list of sorted StudentMarksTemplate
     */
    public static List<StudentMarksTemplate> sortStudentSubTermMarksTemplateList(
            List<StudentMarksTemplate> studentSubTermMarksTemplateList) {
    
        LOG.info("*** Inside sortStudentSubTermMarksTemplateList method ***");
        
        Collections.sort(studentSubTermMarksTemplateList, new Comparator<StudentMarksTemplate>() {
            
            public int compare(StudentMarksTemplate studentMarksTemplate1, StudentMarksTemplate studentMarksTemplate2) {
            
                return studentMarksTemplate1.getTerm().compareToIgnoreCase(studentMarksTemplate2.getTerm());
            }
        });
        
        return studentSubTermMarksTemplateList;
    }
    
    /**
     * This is to sort the List of StudentPrefect objects based on the added year.
     * 
     * @param studentPrefectList - List of StudentPrefect
     * @return list of sorted StudentPrefect
     */
    public static List<StudentPrefect> sortStudentPrefectList(List<StudentPrefect> studentPrefectList) {
    
        LOG.info("*** Inside sortStudentPrefectList method ***");
        
        Collections.sort(studentPrefectList, new Comparator<StudentPrefect>() {
            
            public int compare(StudentPrefect studentPrefect1, StudentPrefect studentPrefect2) {
            
                return studentPrefect1.getYear().compareTo(studentPrefect2.getYear());
            }
        });
        
        return studentPrefectList;
    }
    
    /**
     * This is to sort the List of StudentPrefect objects based on the prefect type description.
     * 
     * @param studentPrefectList - List of StudentPrefect
     * @return list of sorted StudentPrefect
     */
    public static List<StudentPrefect> sortStudentPrefectListByName(List<StudentPrefect> studentPrefectList) {
    
        LOG.info("*** Inside sortStudentPrefectList method ***");
        
        Collections.sort(studentPrefectList, new Comparator<StudentPrefect>() {
            
            public int compare(StudentPrefect studentPrefect1, StudentPrefect studentPrefect2) {
            
                return studentPrefect1.getPrefectType().getDescription()
                        .compareTo(studentPrefect2.getPrefectType().getDescription());
            }
        });
        
        return studentPrefectList;
    }
    
    /**
     * This is to sort the List of StudentScholarship objects based on the added year.
     * 
     * @param studentScholarshipList - List of StudentScholarship
     * @return list of sorted StudentScholarship
     */
    public static List<StudentScholarship> sortStudentScholarshipList(List<StudentScholarship> studentScholarshipList) {
    
        LOG.info("*** Inside sortStudentScholarshipList method ***");
        
        Collections.sort(studentScholarshipList, new Comparator<StudentScholarship>() {
            
            public int compare(StudentScholarship studentScholarship1, StudentScholarship studentScholarship2) {
            
                return studentScholarship1.getYear().compareTo(studentScholarship2.getYear());
            }
        });
        
        return studentScholarshipList;
    }
    
    /**
     * This is to sort the List of Achievement objects based on the added year.
     * 
     * @param achievementList - List of Achievement
     * @return list of sorted Achievement
     */
    public static List<StudentAchievementTemplate> sortAchievementList(List<StudentAchievementTemplate> achievementList) {
    
        LOG.info("*** Inside sortAchievementList method ***");
        
        Collections.sort(achievementList, new Comparator<StudentAchievementTemplate>() {
            
            public int compare(StudentAchievementTemplate achievement1, StudentAchievementTemplate achievement2) {
            
                return achievement1.getYear().compareTo(achievement2.getYear());
            }
        });
        
        return achievementList;
    }
    
    /**
     * This is to sort the List of StudentSport objects based on the added year.
     * 
     * @param studentSportList - List of StudentSport
     * @return list of sorted StudentSport
     */
    public static List<StudentSport> sortStudentSportList(List<StudentSport> studentSportList) {
    
        LOG.info("*** Inside sortStudentSportList method ***");
        
        Collections.sort(studentSportList, new Comparator<StudentSport>() {
            
            public int compare(StudentSport studentSport1, StudentSport studentSport2) {
            
                return studentSport1.getYear().compareTo(studentSport2.getYear());
            }
        });
        
        return studentSportList;
    }
    
    /**
     * This is to sort the List of StudentFaithLifeTemplate objects based on the added year.
     * 
     * @param studentFaithLifeTemplateList - List of StudentFaithLifeTemplate
     * @return list of sorted StudentFaithLifeTemplate
     */
    public static List<StudentFaithLifeTemplate> sortStudentFaithLifeTemplateList(
            List<StudentFaithLifeTemplate> studentFaithLifeTemplateList) {
    
        LOG.info("*** Inside sortStudentFaithLifeTemplateList method ***");
        
        Collections.sort(studentFaithLifeTemplateList, new Comparator<StudentFaithLifeTemplate>() {
            
            public int compare(StudentFaithLifeTemplate studentFaithLifeTemplate1,
                    StudentFaithLifeTemplate studentFaithLifeTemplate2) {
            
                return studentFaithLifeTemplate1.getYear().compareTo(studentFaithLifeTemplate2.getYear());
            }
        });
        
        return studentFaithLifeTemplateList;
    }
    
    /**
     * This is to sort the List of StudentClassInfo objects based on the added year.
     * 
     * @param studentClassInfoList - List of StudentClassInfo
     * @return list of sorted StudentClassInfo
     */
    public static List<StudentClassInfo> sortStudentClassInfoList(List<StudentClassInfo> studentClassInfoList) {
    
        LOG.info("*** Inside sortStudentClassInfoList method ***");
        
        Collections.sort(studentClassInfoList, new Comparator<StudentClassInfo>() {
            
            public int compare(StudentClassInfo studentClassInfo1, StudentClassInfo studentClassInfo2) {
            
                return studentClassInfo1.getStudent().getFullName()
                        .compareToIgnoreCase(studentClassInfo2.getStudent().getFullName());
            }
        });
        
        return studentClassInfoList;
    }
    
    /**
     * This is to sort the List of StudyMedium objects based on the studyMediumName.
     * 
     * @param studyMediumList - List of study mediums
     * @return list of sorted List of study mediums
     */
    public static List<StudyMedium> sortStudyMedium(List<StudyMedium> studyMediumList) {
    
        LOG.info("*** Inside sortStudyMedium method ***");
        
        Collections.sort(studyMediumList, new Comparator<StudyMedium>() {
            
            public int compare(StudyMedium studyMedium1, StudyMedium studyMedium2) {
            
                return studyMedium1.getStudyMediumName().compareToIgnoreCase(studyMedium2.getStudyMediumName());
            }
        });
        
        return studyMediumList;
    }
    
    /**
     * This is to sort the List of DisciplinaryAction objects based on the FullName.
     * 
     * @param cRStudentDisciplinaryAction - List of CRStudentDisciplinaryActionClassWise
     * @return Collection
     */
    @SuppressWarnings({ "rawtypes" })
    public static Collection sortDisciplinaryActionByName(
            List<CRStudentDisciplinaryActionClassWise> cRStudentDisciplinaryAction) {
    
        LOG.info("*** Inside sortStudentDisciplinaryActionClassWiseList method ***");
        
        Collections.sort(cRStudentDisciplinaryAction, new Comparator<Object>() {
            
            public int compare(Object o1, Object o2) {
            
                CRStudentDisciplinaryActionClassWise s1 = (CRStudentDisciplinaryActionClassWise) o1;
                CRStudentDisciplinaryActionClassWise s2 = (CRStudentDisciplinaryActionClassWise) o2;
                return s1.getFullName().compareToIgnoreCase(s2.getFullName());
            }
        });
        
        return cRStudentDisciplinaryAction;
    }
    
    /**
     * Returns a list of StudentAttendanceReport sorting with the date of the attendance.
     * 
     * @param attendanceList - a list of StudentAttendanceReport.
     * @return - a list of StudentAttendanceReport.
     */
    public static List<StudentLeave> sortStudentAttendance(List<StudentLeave> attendanceList) {
    
        Collections.sort(attendanceList, new Comparator<Object>() {
            
            public int compare(Object o1, Object o2) {
            
                StudentLeave s1 = (StudentLeave) o1;
                StudentLeave s2 = (StudentLeave) o2;
                return s1.getFromDate().compareTo(s2.getFromDate());
            }
        });
        
        return attendanceList;
    }
    
    /**
     * Returns a list of sportCategoryList sorting with sports category.
     * 
     * @param sportCategoryList - a list of sportCategoryList.
     * @return - a list of sportCategoryList.
     */
    public static List<SportCategory> sortSportCategoriesList(List<SportCategory> sportCategoryList) {
    
        Collections.sort(sportCategoryList, new Comparator<Object>() {
            
            public int compare(Object o1, Object o2) {
            
                SportCategory s1 = (SportCategory) o1;
                SportCategory s2 = (SportCategory) o2;
                return s1.getSport().getDescription().compareTo(s2.getSport().getDescription());
            }
        });
        
        return sportCategoryList;
    }
    
    /**
     * Returns a list of SpecialEvents sorting with the date in descending order.
     * 
     * @param specialEventsList - a list of SpecialEvents.
     * @return - a list of SpecialEvents.
     */
    public static List<SpecialEvents> sortSpecialEventsList(List<SpecialEvents> specialEventsList) {
    
        Collections.sort(specialEventsList, new Comparator<SpecialEvents>() {
            
            public int compare(SpecialEvents se1, SpecialEvents se2) {
            
                return se2.getDate().compareTo(se1.getDate());
            }
        });
        Collections.reverse(specialEventsList);
        return specialEventsList;
    }
    
    /**
     * Returns a list of StudentSpecialEventAttendanceTemplate sorting with the attendance field First objects
     * in list contains present students and absent students at the last.
     * 
     * @param studentSpecialEventsAttendanceList - a list of StudentSpecialEventAttendanceTemplates.
     * @return - a list of StudentSpecialEventAttendanceTemplate.
     */
    public static List<StudentSpecialEventAttendanceTemplate> sortStudentSpecialEventsAttendanceList(
            List<StudentSpecialEventAttendanceTemplate> studentSpecialEventsAttendanceList) {
    
        Collections.sort(studentSpecialEventsAttendanceList, new Comparator<StudentSpecialEventAttendanceTemplate>() {
            
            public int compare(StudentSpecialEventAttendanceTemplate sseat1,
                    StudentSpecialEventAttendanceTemplate sseat2) {
            
                return sseat1.getAttendance().compareTo(sseat2.getAttendance());
            }
        });
        
        return studentSpecialEventsAttendanceList;
    }
    
    /**
     * This is to sort the List of Assignment objects based on the description of the assignment.
     * 
     * @param assignmentList - List of Assignments
     * @return list of sorted assignments
     */
    public static List<Assignment> sortAssignmenttList(List<Assignment> assignmentList) {
    
        LOG.info("*** Inside sortAssignmenttList method ***");
        
        Collections.sort(assignmentList, new Comparator<Assignment>() {
            
            public int compare(Assignment assignment1, Assignment assignment2) {
            
                return assignment1.getName().compareToIgnoreCase(assignment2.getName());
            }
        });
        
        return assignmentList;
    }
    
    /**
     * Sort the countries list.
     * 
     * @param countryList the list of countries.
     * @return list of countries.
     */
    public static List<Country> sortCountries(List<Country> countryList) {
    
        LOG.info("*** Inside sortStudyMedium method ***");
        
        Collections.sort(countryList, new Comparator<Country>() {
            
            public int compare(Country countryOne, Country countryTwo) {
            
                return countryOne.getCountryName().compareTo(countryTwo.getCountryName());
            }
        });
        
        return countryList;
    }
    
    /**
     * Returns a list of business audit details sorting with the date.
     * 
     * @param businessAuditList - a list of business audit details.
     * @return - a list of StudentAttendanceReport.
     */
    public static List<BusinessAudit> sortBusinessAudit(List<BusinessAudit> businessAuditList) {
    
        Collections.sort(businessAuditList, new Comparator<BusinessAudit>() {
            
            public int compare(BusinessAudit o1, BusinessAudit o2) {
            
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        
        return businessAuditList;
    }
    
    /**
     * Sort student assignment mark templates.
     * 
     * @param studentAssignmentMarkTemplatesList the student assignment mark templates list
     * @return the list
     */
    public static List<StudentAssignmentMarkTemplate> sortStudentAssignmentMarkTemplates(
            List<StudentAssignmentMarkTemplate> studentAssignmentMarkTemplatesList) {
    
        Collections.sort(studentAssignmentMarkTemplatesList, new Comparator<StudentAssignmentMarkTemplate>() {
            
            public int compare(StudentAssignmentMarkTemplate object1, StudentAssignmentMarkTemplate object2) {
            
                return object2.getYear().compareTo(object1.getYear());
            }
        });
        
        return studentAssignmentMarkTemplatesList;
    }
    
    /**
     * Returns a list objects sorted by admission number.
     * 
     * @param studentList - a list of Objects.
     * @return - a list of StudentAttendanceReport.
     */
    public static List<Object> sortStudentListByAdmissionNumber(List<Object> studentList) {
    
        Collections.sort(studentList, new Comparator<Object>() {
            
            public int compare(Object o1, Object o2) {
            
                Student s1 = (Student) o1;
                Student s2 = (Student) o2;
                
                String firstString = s1.getAdmissionNo();
                String secondString = s2.getAdmissionNo();
                
                return sortString(firstString, secondString);
            }
        });
        
        return studentList;
    }
    
    /**
     * Returns a list of StudentScholarship sorted by admission number.
     * 
     * @param studentTemplateList - a list of Student Scholarship Objects.
     * @return - a list of Student Scholarship.
     */
    public static List<Object> sortStudentTemplateListByAdmissionNumber(List<Object> studentTemplateList) {
        
        Collections.sort(studentTemplateList, new Comparator<Object>() {
            
            public int compare(Object o1, Object o2) {
            
                StudentScholarshipTemplate s1 = (StudentScholarshipTemplate) o1;
                StudentScholarshipTemplate s2 = (StudentScholarshipTemplate) o2;
                
                String firstString = s1.getAdmissionNo();
                String secondString = s2.getAdmissionNo();
                
                return sortString(firstString, secondString);
            }
        });
        
        return studentTemplateList;
    }
    
    /**
     * Returns a list of PerDayAttendanceTemplate objects sorted by admission number.
     * 
     * @param perDayAttendanceTemplateList - a list of PerDayAttendanceTemplate objects.
     * @return - a list of StudentAttendanceReport.
     */
    public static List<PerDayAttendanceTemplate> sortPerDayAttendanceTemplateListByAdmissionNumber(
            List<PerDayAttendanceTemplate> perDayAttendanceTemplateList) {
    
        Collections.sort(perDayAttendanceTemplateList, new Comparator<PerDayAttendanceTemplate>() {
            
            public int compare(PerDayAttendanceTemplate p1, PerDayAttendanceTemplate p2) {
            
                String firstString = p1.getStudentAdmissionNo();
                String secondString = p2.getStudentAdmissionNo();
                
                return sortString(firstString, secondString);
            }
        });
        
        return perDayAttendanceTemplateList;
    }
    
    /**
     * Sort Class wise Student mark sheet report result by name.
     * 
     * @param classWiseStudentReportData list of ClassWiseStudentReportData objects
     * @return sorted list
     */
    public static List<ClassWiseStudentReportData> sortClassWiseStudentReportDataByName(
            List<ClassWiseStudentReportData> classWiseStudentReportData) {
    
        Collections.sort(classWiseStudentReportData, new Comparator<ClassWiseStudentReportData>() {
            
            public int compare(ClassWiseStudentReportData object1, ClassWiseStudentReportData object2) {
            
                return object1.getName().compareTo(object2.getName());
            }
        });
        return classWiseStudentReportData;
    }
    
    /**
     * Sort Class wise Student mark sheet report result by rank.
     * 
     * @param classWiseStudentReportData list of ClassWiseStudentReportData objects
     * @return sorted list
     */
    public static List<ClassWiseStudentReportData> sortClassWiseStudentReportDataByRank(
            List<ClassWiseStudentReportData> classWiseStudentReportData) {
    
        Collections.sort(classWiseStudentReportData, new Comparator<ClassWiseStudentReportData>() {
            
            public int compare(ClassWiseStudentReportData object1, ClassWiseStudentReportData object2) {
            
                return object1.getMarks().get(AkuraConstant.RANK_INDEX)
                        .compareTo(object2.getMarks().get(AkuraConstant.RANK_INDEX));
            }
        });
        return classWiseStudentReportData;
    }
    
    /**
     * Sort list of ParticipantCategory by description.
     * 
     * @param participantCategoryData list of ParticipantCategory objects
     * @return sorted list
     */
    public static List<ParticipantCategory> sortParticipantCategory(List<ParticipantCategory> participantCategoryData) {
    
        Collections.sort(participantCategoryData, new Comparator<ParticipantCategory>() {
            
            public int compare(ParticipantCategory object1, ParticipantCategory object2) {
            
                return object1.getDescription().compareTo(object2.getDescription());
            }
        });
        return participantCategoryData;
    }
    
    /**
     * Sort list of FaithLifeComment by description.
     * 
     * @param faithLifeCommentData list of FaithLifeComment objects
     * @return sorted list
     */
    public static List<FaithLifeComment> sortFaithLifeComment(List<FaithLifeComment> faithLifeCommentData) {
    
        Collections.sort(faithLifeCommentData, new Comparator<FaithLifeComment>() {
            
            public int compare(FaithLifeComment object1, FaithLifeComment object2) {
            
                return object1.getDescription().compareTo(object2.getDescription());
            }
        });
        return faithLifeCommentData;
    }
    
    /**
     * Sort list of AuditEventType by description.
     * 
     * @param auditEventTypeList list of AuditEventType objects
     * @return sorted list
     */
    public static List<AuditEventType> sortAuditEventType(List<AuditEventType> auditEventTypeList) {
    
        Collections.sort(auditEventTypeList, new Comparator<AuditEventType>() {
            
            public int compare(AuditEventType object1, AuditEventType object2) {
            
                return object1.getDescription().compareTo(object2.getDescription());
            }
        });
        return auditEventTypeList;
    }
    
    /**
     * This method sort the List of Staff objects based on the staff last name.
     * 
     * @param staffList - List of Staff
     * @return list of sorted Staff
     */
    public static List<Staff> sortStaffListByLastName(List<Staff> staffList) {
    
        LOG.info("*** Inside sortStaffCategoryList method ***");
        
        Collections.sort(staffList, new Comparator<Staff>() {
            
            public int compare(Staff staff1, Staff staff2) {
            
                return staff1.getLastName().compareToIgnoreCase(staff2.getLastName());
            }
        });
        
        return staffList;
    }
    
    /**
     * . This method used to sort the search student result set.
     * 
     * @param students list of objects
     * @return list of object
     */
    public static List<Object> sortStudentSearch(List<Object> students) {
    
        LOG.info("*** Inside sortStudentSearch method ***");
        Collections.sort(students, new Comparator<Object>() {
            
            public int compare(Object student1, Object student2) {
            
                Object[] stringStudent1 = (Object[]) student1;
                Object[] stringStudent2 = (Object[]) student2;
                
                // index 1 contains student name
                return ((String) stringStudent1[1]).compareToIgnoreCase(((String) stringStudent2[1]));
            }
        });
        return students;
    }
    
    /**
     * Method to return sorted staffServiceCategory list according to the description.
     * 
     * @param staffServiceCategoryList - list of StaffServiceCategory
     * @return list of sorted StaffServiceCategory
     */
    public static List<StaffServiceCategory> sortStaffSeriviceCategoryList(
            List<StaffServiceCategory> staffServiceCategoryList) {
    
        Collections.sort(staffServiceCategoryList, new Comparator<StaffServiceCategory>() {
            
            public int compare(StaffServiceCategory staffServiceCategory1, StaffServiceCategory staffServiceCategory2) {
            
                return staffServiceCategory1.getDescription().compareToIgnoreCase(
                        staffServiceCategory2.getDescription());
            }
        });
        
        return staffServiceCategoryList;
    }
    
    /**
     * This is to sort the List of appointmentClassification objects based on the description of the
     * appointmentClassification.
     * 
     * @param appointmentClassificationList - List of AppointmentClassification
     * @return list of sorted appointmentClassification
     */
    public static List<AppointmentClassification> sortAppointmentClassificationList(
            List<AppointmentClassification> appointmentClassificationList) {
    
        LOG.info("*** Inside sortAppointmentClassificationList method ***");
        
        Collections.sort(appointmentClassificationList, new Comparator<AppointmentClassification>() {
            
            public int compare(AppointmentClassification appointmentClassification1,
                    AppointmentClassification appointmentClassification2) {
            
                return appointmentClassification1.getDescription().compareToIgnoreCase(
                        appointmentClassification2.getDescription());
            }
        });
        
        return appointmentClassificationList;
    }
    
    /**
     * This is to sort the List of section objects based on the description of the section.
     * 
     * @param sectionList - List of Section
     * @return list of sorted section
     */
    public static List<Section> sortSectionList(List<Section> sectionList) {
    
        LOG.info("*** Inside sortSectionList method ***");
        
        Collections.sort(sectionList, new Comparator<Section>() {
            
            public int compare(Section section1, Section section2) {
            
                return section1.getDescription().compareToIgnoreCase(section2.getDescription());
            }
        });
        
        return sectionList;
    }
    
    /**
     * This is to sort the List of staff leave type objects based on the description of the
     * staffLeaveTypeList.
     * 
     * @param staffLeaveTypeList - List of Staff Leave
     * @return list of sorted staffLeaveType
     */
    public static List<StaffLeaveType> sortStaffLeaveList(List<StaffLeaveType> staffLeaveTypeList) {
    
        LOG.info("*** Inside sortStaffLeaveTypeList method ***");
        
        Collections.sort(staffLeaveTypeList, new Comparator<StaffLeaveType>() {
            
            public int compare(StaffLeaveType staffLeaveType1, StaffLeaveType staffLeaveType2) {
            
                return staffLeaveType1.getDescription().compareToIgnoreCase(staffLeaveType2.getDescription());
                
            }
            
        });
        
        return staffLeaveTypeList;
    }
    
    /**
     * This is to sort the List of warningLevelList objects based on the description of the warningLevel.
     * 
     * @param warningLevelList - List of warningLevel
     * @return list of sorted warningLevel.
     */
    public static List<WarningLevel> sortWarningLevelList(List<WarningLevel> warningLevelList) {
    
        LOG.info("*** Inside sortWarningLevelList method ***");
        
        Collections.sort(warningLevelList, new Comparator<WarningLevel>() {
            
            public int compare(WarningLevel warningLevel1, WarningLevel warningLevel2) {
            
                return warningLevel1.getDescription().compareToIgnoreCase(warningLevel2.getDescription());
            }
            
        });
        
        return warningLevelList;
    }
    
    /**
     * This is to sort the List of civilStatusList objects based on the description of the donationType.
     * 
     * @param civilStatusList - List of civilStatus
     * @return list of sorted civilStatus
     */
    public static List<CivilStatus> sortCivilStatusList(List<CivilStatus> civilStatusList) {
    
        LOG.info("*** Inside sortCivilStatusList method ***");
        
        Collections.sort(civilStatusList, new Comparator<CivilStatus>() {
            
            public int compare(CivilStatus civilStatus1, CivilStatus civilStatus2) {
            
                return civilStatus1.getDescription().compareToIgnoreCase(civilStatus2.getDescription());
            }
        });
        
        return civilStatusList;
    }
    
    /**
     * This is to sort the List of StudentClassInfo objects based on the student admission number.
     * 
     * @param studentList - List of StudentClassInfo objects
     * @return list of sorted StudentClassInfo
     */
    public static List<StudentClassInfo> sortStudentListByAdmissionNo(List<StudentClassInfo> studentList) {
    
        LOG.info("*** Inside sortGradeList method ***");
        
        Collections.sort(studentList, new Comparator<StudentClassInfo>() {
            
            /**
             * The compare method that compares the alphanumeric strings
             */
            public int compare(StudentClassInfo s1, StudentClassInfo s2) {
            
                String firstString = s1.getStudent().getAdmissionNo();
                String secondString = s2.getStudent().getAdmissionNo();
                
                return sortString(firstString, secondString);
                
            }
        });
        return studentList;
    }
    
    /**
     * This is to sort the List of StudentClubSociety objects based on the student admission number.
     * 
     * @param studentList - List of StudentClubSociety objects
     * @return list of sorted StudentClubSociety
     */
    public static List<StudentClubSociety> sortStudentClubSocityByAdmissionNo(List<StudentClubSociety> studentList) {
    
        Collections.sort(studentList, new Comparator<StudentClubSociety>() {
            
            /**
             * The compare method that compares the alphanumeric strings
             */
            public int compare(StudentClubSociety s1, StudentClubSociety s2) {
            
                String firstString = s1.getStudent().getAdmissionNo();
                String secondString = s2.getStudent().getAdmissionNo();
                
                return sortString(firstString, secondString);
                
            }
        });
        return studentList;
    }
    
    /**
     * This is to sort the List of StudentSport objects based on the student admission number.
     * 
     * @param studentList - List of StudentSport objects
     * @return list of sorted StudentSport
     */
    public static List<StudentSport> sortStudentSportByAdmissionNo(List<StudentSport> studentList) {
    
        Collections.sort(studentList, new Comparator<StudentSport>() {
            
            /**
             * The compare method that compares the alphanumeric strings
             */
            public int compare(StudentSport s1, StudentSport s2) {
            
                String firstString = s1.getStudent().getAdmissionNo();
                String secondString = s2.getStudent().getAdmissionNo();
                
                return sortString(firstString, secondString);
                
            }
        });
        return studentList;
    }
    
    /**
     * custom sort method to sort string cantaing numbers.
     * 
     * @param firstString - String
     * @param secondString - String
     * @return integer
     */
    private static int sortString(String firstString, String secondString) {
    
        if (secondString == null || firstString == null) {
            return 0;
        }
        
        int lengthFirstStr = firstString.length();
        int lengthSecondStr = secondString.length();
        
        int index1 = 0;
        int index2 = 0;
        
        while (index1 < lengthFirstStr && index2 < lengthSecondStr) {
            char ch1 = firstString.charAt(index1);
            char ch2 = secondString.charAt(index2);
            
            char[] space1 = new char[lengthFirstStr];
            char[] space2 = new char[lengthSecondStr];
            
            int loc1 = 0;
            int loc2 = 0;
            
            do {
                space1[loc1++] = ch1;
                index1++;
                
                if (index1 < lengthFirstStr) {
                    ch1 = firstString.charAt(index1);
                } else {
                    break;
                }
            } while (Character.isDigit(ch1) == Character.isDigit(space1[0]));
            
            do {
                space2[loc2++] = ch2;
                index2++;
                
                if (index2 < lengthSecondStr) {
                    ch2 = secondString.charAt(index2);
                } else {
                    break;
                }
            } while (Character.isDigit(ch2) == Character.isDigit(space2[0]));
            
            String str1 = new String(space1);
            String str2 = new String(space2);
            
            int result;
            
            if (Character.isDigit(space1[0]) && Character.isDigit(space2[0])) {
                Double firstNumberToCompare = new Double(Double.parseDouble(str1.trim()));
                Double secondNumberToCompare = new Double(Double.parseDouble(str2.trim()));
                result = firstNumberToCompare.compareTo(secondNumberToCompare);
            } else {
                result = str1.compareTo(str2);
            }
            
            if (result != 0) {
                return result;
            }
        }
        return lengthFirstStr - lengthSecondStr;
        
    }
    
    /**
     * This is to sort the List of Grade Classes objects based on the description of the Grade Classes.
     * 
     * @param gradeClassList - List of Grade Classes
     * @return list of sorted Grade Classes.
     */
    public static List<SchoolClass> sortGradeClasses(List<SchoolClass> gradeClassList) {
    
        LOG.info("*** Inside sortGradeClasses method ***");
        
        Collections.sort(gradeClassList, new Comparator<SchoolClass>() {
            
            public int compare(SchoolClass gradeClass1, SchoolClass gradeClass2) {
            
                return gradeClass1.getDescription().compareToIgnoreCase(gradeClass2.getDescription());
            }
        });
        
        return gradeClassList;
    }
    
    /**
     * This is to sort the List of Staff objects based on the registration number of the Staff.
     * 
     * @param staffList - List of Staff
     * @return list of sorted Staff
     */
    public static List<Staff> sortStaffNoList(List<Staff> staffList) {
    
        LOG.info("*** Inside sortStaffNoList method ***");
        
        Collections.sort(staffList, new Comparator<Staff>() {
            
            public int compare(Staff staff1, Staff staff2) {
            
                return staff1.getResidenceNo().compareToIgnoreCase(staff2.getResidenceNo());
            }
        });
        
        return staffList;
    }
    
    /**
     * This is to sort the List of Staff Leave Type Information based on the staff leave type.
     * 
     * @param leaveInfoList - List of Staff Leave Type Info
     * @return list of sorted Staff
     */
    public static List<Object[]> sortStaffLeaveTypeInfoList(List<Object[]> leaveInfoList) {
    
        LOG.info("*** Inside sortStaffLeaveTypeInfo method ***");
        
        Collections.sort(leaveInfoList, new Comparator<Object[]>() {
            
            public int compare(Object[] object1, Object[] object2) {
            
                return object1[1].toString().compareToIgnoreCase(object2[1].toString());
            }
        });
        
        return leaveInfoList;
    }
    
    /**
     * This is to sort the List of Student Assignment Mark Information based on the student assignment mark
     * type.
     * 
     * @param assignmentMarkList - List of Student Assignment Marks
     * @return list of sorted Students
     */
    
    public static List<Object[]> sortStudentAssignmentMarks(List<Object[]> assignmentMarkList) {
    
        Collections.sort(assignmentMarkList, new Comparator<Object[]>() {
            
            public int compare(Object[] obj1, Object[] obj2) {
            
                return obj1[1].toString().compareToIgnoreCase(obj2[1].toString());
            }
            
        });
        
        return assignmentMarkList;
        
    }
    
    /**
     * Sorts the object array list with the desired index number.
     * 
     * @param objectArrayList - the list need to be sorted.
     * @param indexNo - the desired index number to retrieve the object.
     * @return - the sorted object array.
     */
    public static List<Object[]> sortObjectArrayList(List<Object[]> objectArrayList, final int indexNo) {
    
        LOG.info("*** Inside the sortObjectArrayList method ***");
        
        Collections.sort(objectArrayList, new Comparator<Object[]>() {
            
            public int compare(Object[] object1, Object[] object2) {
            
                return (((String) object1[indexNo]).trim().toLowerCase()).compareTo(((String) object2[indexNo]).trim()
                        .toLowerCase());
            }
        });
        
        return objectArrayList;
    }
    
    /**
     * This is to sort the List of String year objects.
     * 
     * @param yearList - List of years
     * @return list of sorted years
     */
    public static List<String> sortStringYearList(List<String> yearList) {
    
        LOG.info("*** Inside sortYearList method ***");
        
        Collections.sort(yearList, new Comparator<String>() {
            
            public int compare(String year1, String year2) {
            
                return year2.compareToIgnoreCase(year1);
            }
        });
        
        return yearList;
    }
    
    /**
     * This is to sort the List of EmploymentStatus by the description.
     * 
     * @param employmentStatusList - List of employment status.
     * @return list of sorted employment status.
     */
    public static List<EmploymentStatus> sortEmploymentStatusList(List<EmploymentStatus> employmentStatusList) {
    
        LOG.info("*** Inside sortEmploymentStatusList method ***");
        
        Collections.sort(employmentStatusList, new Comparator<EmploymentStatus>() {
            
            public int compare(EmploymentStatus empStatusOne, EmploymentStatus empStatusTwo) {
            
                return empStatusOne.getDescription().compareToIgnoreCase(empStatusTwo.getDescription());
            }
        });
        
        return employmentStatusList;
        
    }
    
    /**
     * Returns a list of Publications sorting with the date in descending order.
     * 
     * @param publicationsList - a list of publications.
     * @return - a list of Publication.
     */
    public static List<Publication> sortPublicationsListByDate(List<Publication> publicationsList) {
    
        Collections.sort(publicationsList, new Comparator<Publication>() {
            
            public int compare(Publication se1, Publication se2) {
            
                return se1.getExpiryDate().compareTo(se2.getExpiryDate());
            }
        });
        
        return publicationsList;
    }
    
    /**
     * Returns a list of StaffLeave sorting with the from date in descending order.
     * 
     * @param staffLeaveList - a list of staff leave
     * @return - a list of Publication.
     */
    public static List<StaffLeave> sortStaffLeaveListByFromDate(List<StaffLeave> staffLeaveList) {
    
        Collections.sort(staffLeaveList, new Comparator<StaffLeave>() {
            
            public int compare(StaffLeave staffLeave1, StaffLeave staffLeave2) {
            
                return staffLeave2.getFromDate().compareTo(staffLeave1.getFromDate());
            }
        });
        
        return staffLeaveList;
    }
    
    /**
     * Returns a sorted grade list with the description.
     * 
     * @param grades - a list of grades
     * @return - a sorted grade list with the description.
     */
    public static List<Grade> sortGradesList(List<Grade> grades) {
    
        Collections.sort(grades, new Comparator<Grade>() {
            
            public int compare(Grade gradeOne, Grade gradeTwo) {
            
                return gradeOne.getDescription().trim().compareToIgnoreCase(gradeTwo.getDescription().trim());
            }
        });
        
        return grades;
    }
    
    /**
     * Returns a list of SpecialEvents sorting with the name in ascending order.
     * 
     * @param specialEventsList - a list of SpecialEvents.
     * @return - a list of SpecialEvents.
     */
    public static List<SpecialEvents> sortSpecialEventsListByName(List<SpecialEvents> specialEventsList) {
    
        Collections.sort(specialEventsList, new Comparator<SpecialEvents>() {
            
            public int compare(SpecialEvents se1, SpecialEvents se2) {
            
                return se1.getName().compareToIgnoreCase(se2.getName());
            }
        });
        
        return specialEventsList;
    }
    
    /**
     * Returns a sorted Class grade list by the class grade description.
     * 
     * @param classGrades - a list of class grades.
     * @return - a sorted Class grade list by the class grade description.
     */
    public static List<ClassGrade> sortClassGrades(List<ClassGrade> classGrades) {
    
        LOG.info("*** Inside sortClassGrades method ***");
        
        Collections.sort(classGrades, new Comparator<ClassGrade>() {
            
            public int compare(ClassGrade classGradeOne, ClassGrade classGradeTwo) {
            
                return classGradeOne.getDescription().trim().compareToIgnoreCase(classGradeTwo.getDescription().trim());
            }
        });
        return classGrades;
    }
    
    /**
     * Returns a sorted Student temporary Leave.
     * 
     * @param studentTemporyLeaveList - a list of Student temporary Leave.
     * @return - a sorted StudentTemporaryLeave object sorted by the toDate.
     */
    public static List<StudentTemporaryLeave> sorttemporaryLeaveStudentListByDate(
            List<StudentTemporaryLeave> studentTemporyLeaveList) {
    
        Collections.sort(studentTemporyLeaveList, Collections.reverseOrder(new Comparator<StudentTemporaryLeave>() {
            
            public int compare(StudentTemporaryLeave studentTempLeaveone, StudentTemporaryLeave studentTempLeavetwo) {
            
                return studentTempLeaveone.getToDate().compareTo(studentTempLeavetwo.getToDate());
                
            }
        }));
        
        Collections.sort(studentTemporyLeaveList, Collections.reverseOrder(new Comparator<StudentTemporaryLeave>() {
            
            public int compare(StudentTemporaryLeave studentTempLeaveone, StudentTemporaryLeave studentTempLeavetwo) {
            
                return studentTempLeaveone.getFromDate().compareTo(studentTempLeavetwo.getFromDate());
                
            }
        }));
        
        return studentTemporyLeaveList;
    }
    
}
