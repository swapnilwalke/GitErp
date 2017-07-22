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

-- views for attendance reports
-- update akura EMPLOYEEATTENDANCE views

CREATE OR REPLACE VIEW EMPLOYEEATTENDANCE
AS 
select dta.DATE AS `DDDDate`,
s.REGISTRATION_NO AS `DDDEmpNo`,
s.FULL_NAME AS `DDDEmpName`,
dta.TIME_IN AS `TimeIn`,
dta.TIME_OUT AS `TimeOut`,
format((time_to_sec(timediff(dta.TIME_OUT,
dta.TIME_IN)) / 3600),2) AS NumHours
from DAILY_TEACHER_ATTENDANCE dta, STAFF s 
where dta.STAFF_ID=s.Staff_id
group by dta.DATE,dta.STAFF_ID;


-- views for staff attendance reports - get all staff.
-- update akura STAFFATTENDANCE views

CREATE OR REPLACE VIEW STAFFATTENDANCE
AS 
select dta.DATE AS `DDDDate`,
s.REGISTRATION_NO AS `DDDEmpNo`,
s.FULL_NAME AS `DDDEmpName`,
dta.TIME_IN AS `TimeIn`,
dta.TIME_OUT AS `TimeOut`,
format((time_to_sec(timediff(dta.TIME_OUT,
dta.TIME_IN)) / 3600),2) AS NumHours,
s.DATE_OF_DEPARTURE AS DateOfDeparture
from DAILY_TEACHER_ATTENDANCE dta, STAFF s 
where dta.STAFF_ID=s.Staff_id AND (s.DATE_OF_DEPARTURE is null OR dta.DATE <= s.DATE_OF_DEPARTURE)
group by dta.DATE,dta.STAFF_ID;