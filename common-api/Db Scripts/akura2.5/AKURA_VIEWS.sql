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

use `akura`;

-- view for Grade Wise Best Student Attendance Report
-- view for Student Gamification Search
--
CREATE OR REPLACE
VIEW `DAILY_STUDENT_ATTENDANCE_VIEW` AS
    SELECT
        `dsa`.`DATE` AS `DDDDate`,
        month(`dsa`.`DATE`) AS `MonthId`,
		year(`dsa`.`DATE`) AS `YearId`,
        `cg`.`DESCRIPTION` AS `ClassDescription`,
		`cg`.`CLASS_ID` AS `ClassId`,
        `g`.`DESCRIPTION` AS `GradeDescription`,
		`g`.`GRADE_ID` AS `GradeId`,
        `s`.`ADMISSION_NO` AS `DDDEmpNo`,
        `s`.`FULL_NAME` AS `DDDEmpName`,
		`s`.`NAME_WT_INITIALS` AS `NameWithInitials`,
		`s`.`PHOTO` AS `Photo`,
        `dsa`.`TIME_IN` AS `TimeIn`,
        `dsa`.`TIME_OUT` AS `TimeOut`,
        format((time_to_sec(timediff(`dsa`.`TIME_OUT`, `dsa`.`TIME_IN`)) / 3600),
                2) AS `NumHours`
    FROM
        ((((`DAILY_STUDENT_ATTENDANCE` `dsa`
        JOIN `STUDENT` `s`)
        JOIN `CLASS_GRADE` `cg`)
        JOIN `STUDENT_CLASS_INFO` `info`)
        JOIN `GRADE` `g`)
    WHERE
        ((`dsa`.`STUDENT_ID` = `s`.`STUDENT_ID`)
            AND (`info`.`STUDENT_ID` = `s`.`STUDENT_ID`)
            AND (`info`.`CLASS_GRADE_ID` = `cg`.`CLASS_GRADE_ID`)
            AND (`g`.`GRADE_ID` = `cg`.`GRADE_ID`)
			AND (year(`dsa`.`DATE`) = year(`info`.`YEAR`)))
    GROUP BY `dsa`.`DATE`,`dsa`.`STUDENT_ID`;
	
	
	
/* Current student class view for Annual student attendance view. */		
CREATE OR REPLACE VIEW CURRUNT_STUDENT_CLASS_VIEW 
AS	
SELECT STUDENT_ID, CLASS_GRADE_ID,
            MAX(STUDENT_CLASS_INFO_ID) STUDENT_CLASS_INFO_ID
            FROM STUDENT_CLASS_INFO
            GROUP BY STUDENT_ID; 	
	
/* Annual student attendance view. */	
CREATE OR REPLACE VIEW  ANNUAL_STUDENT_ATTENDANCE_VIEW 
AS	
SELECT dsa.STUDENT_ID AS 'STUDENT_ID', 
    s.ADMISSION_NO AS 'ADMISSION_NO',
    s.FULL_NAME AS 'FULL_NAME',
    s.NAME_WT_INITIALS AS 'NAME_WT_INITIALS',
    YEAR(dsa.DATE) AS 'YEAR' , 
    COUNT(s.STUDENT_ID) AS 'PRESENT_COUNT', 
    cscv.CLASS_GRADE_ID AS 'CLASS_GRADE_ID',
    cg.DESCRIPTION AS 'CLASS_NAME'
FROM DAILY_STUDENT_ATTENDANCE dsa 
INNER JOIN STUDENT s  
    ON dsa.STUDENT_ID = s.STUDENT_ID 
INNER JOIN CURRUNT_STUDENT_CLASS_VIEW cscv
    ON s.STUDENT_ID = cscv.STUDENT_ID
INNER JOIN CLASS_GRADE cg
    ON cg.CLASS_GRADE_ID = cscv.CLASS_GRADE_ID
WHERE YEAR(dsa.DATE) = YEAR(CURDATE())
GROUP BY dsa.STUDENT_ID;

/* Student attendence view. */
CREATE OR REPLACE VIEW STUDENTATTENDANCE
AS
select dsa.DATE AS DDDDate,
dayname(dsa.DATE) AS DayName,
s.ADMISSION_NO AS DDDEmpNo,
s.FULL_NAME AS DDDEmpName,
s.NAME_WT_INITIALS AS NameWithInitials,
dsa.TIME_IN AS TimeIn,
dsa.TIME_OUT AS TimeOut,
format((time_to_sec(timediff(dsa.TIME_OUT,
dsa.TIME_IN)) / 3600),2) AS NumHours
from DAILY_STUDENT_ATTENDANCE dsa, STUDENT s where dsa.Student_id=s.student_id
group by dsa.DATE,dsa.student_id;


-- STUDENT_SPECIAL_EVENT_PARTICIPATION
-- purpose of this view to show the stdent special event participation id
-- ,student details with their allocated sport,class grade, club and society.

CREATE OR REPLACE VIEW STUDENT_SPECIAL_EVENT_PARTICIPATION

AS

SELECT s.ADMISSION_NO, s.FULL_NAME, s.NAME_WT_INITIALS, sep.SPECIAL_EVENT_PARTICIPATION_ID, 
category.SPORT_CATEGORY_ID, category.CLASS_GRADE_ID, category.CLUB_SOCIETY_ID,
category.STUDENT_ID, category.YEAR FROM SPECIAL_EVENT_PARTICIPATION sep

INNER JOIN 

STUDENT_SPECIAL_EVENT_PARTICIPATION_CATEGORY category 

ON (sep.SPORT_CATEGORY_ID = category.SPORT_CATEGORY_ID || 
sep.CLASS_GRADE_ID = category.CLASS_GRADE_ID || 
sep.CLUB_SOCIETY_ID = category.CLUB_SOCIETY_ID) 

LEFT JOIN 

STUDENT s on s.STUDENT_ID = category.STUDENT_ID ;


-- views for staff attendance reports - get all staff.
-- update akura STAFFATTENDANCE views

CREATE OR REPLACE VIEW STAFFATTENDANCE
AS 
select dta.DATE AS `DDDDate`,
s.REGISTRATION_NO AS `DDDEmpNo`,
s.FULL_NAME AS `DDDEmpName`,
s.NAME_WT_INITIALS AS `EmpNameWithInitials`,
dta.TIME_IN AS `TimeIn`,
dta.TIME_OUT AS `TimeOut`,
format((time_to_sec(timediff(dta.TIME_OUT,
dta.TIME_IN)) / 3600),2) AS NumHours,
s.DATE_OF_DEPARTURE AS DateOfDeparture
from DAILY_TEACHER_ATTENDANCE dta, STAFF s 
where dta.STAFF_ID=s.Staff_id AND (s.DATE_OF_DEPARTURE is null OR dta.DATE <= s.DATE_OF_DEPARTURE)
group by dta.DATE,dta.STAFF_ID;


/* replace teacher attendance view. */
CREATE OR REPLACE VIEW EMPLOYEEATTENDANCE
AS 
select dta.DATE AS `DDDDate`,
s.STAFF_ID AS `StaffId`,
s.REGISTRATION_NO AS `DDDEmpNo`,
s.FULL_NAME AS `DDDEmpName`,
s.NAME_WT_INITIALS AS `EmpNameWithInitials`,
dta.TIME_IN AS `TimeIn`,
dta.TIME_OUT AS `TimeOut`,
format((time_to_sec(timediff(dta.TIME_OUT,
dta.TIME_IN)) / 3600),2) AS NumHours
from DAILY_TEACHER_ATTENDANCE dta, STAFF s
where dta.STAFF_ID=s.Staff_id 
group by dta.DATE,dta.STAFF_ID;
	