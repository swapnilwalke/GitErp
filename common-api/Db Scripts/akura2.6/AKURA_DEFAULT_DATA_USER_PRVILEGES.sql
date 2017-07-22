
USE `akura`;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

--
-- Delete Search Student privillage from student role
--

DELETE FROM ROLE_PRIVILEGE WHERE `ROLE_PRIVILEGE_ID`='459';

--
-- Delete View Student Search Page privillage from student role
--

DELETE FROM ROLE_PRIVILEGE WHERE `ROLE_PRIVILEGE_ID`='455';

--
-- Delete invalid dependency for attendence dashboard to employment status.
--

DELETE FROM PRIVILEGE_DEPENDENCY WHERE `PRIVILEGE_DEPENDENCY_ID` = '507';

DELETE FROM PRIVILEGE_DEPENDENCY WHERE `PRIVILEGE_DEPENDENCY_ID` = '503';

--
-- Add privillage for staff, to access View Class Wise Student Disciplinary Action Report.
--

INSERT INTO ROLE_PRIVILEGE (`USER_ROLE_ID`, `PRIVILEGE_ID`) VALUES ('2', '192');

/*!40014 SET FOREIGN_KEY_CHECKS=1 */;