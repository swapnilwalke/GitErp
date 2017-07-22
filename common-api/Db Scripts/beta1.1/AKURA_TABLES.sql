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

--
-- Current Database: `akura`
--


USE `akura`;

ALTER TABLE `STUDENT_SUB_TERM_MARK` 
ADD COLUMN `MARKS` float DEFAULT NULL;

--
-- Changes for `SPECIAL_EVENT_PARTICIPATION`
--
ALTER TABLE `SPECIAL_EVENT_PARTICIPATION` 
DROP FOREIGN KEY`fk_SPECIALEVENT_SPECIALEVENT1`; 

ALTER TABLE `SPECIAL_EVENT_PARTICIPATION` 
ADD CONSTRAINT `fk_SPECIALEVENT_SPECIALEVENT1` 
FOREIGN KEY (`SPECIAL_EVENT_ID` ) 
REFERENCES `SPECIAL_EVENTS` (`SPECIAL_EVENTS_ID` ) 
ON DELETE CASCADE 
ON UPDATE CASCADE;

--
-- User Creation of AKURA
--
use `mysql`;

CREATE USER 'akura'@'localhost' IDENTIFIED BY 'akura';
GRANT SELECT,INSERT,UPDATE,DELETE,execute  
ON `akura`.*
TO 'akura'@'localhost';
FLUSH PRIVILEGES;