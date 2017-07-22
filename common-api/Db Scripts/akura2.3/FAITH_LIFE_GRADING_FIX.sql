use `akura`;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

ALTER TABLE `FAITH_LIFE_RATING` 
DROP FOREIGN KEY `fk_FAITH_LIFE_RATING_GRADING1` ;

INSERT INTO `FAITH_LIFE_GRADING` (`FAITH_LIFE_GRADING_ID`, `DESCRIPTION`, `VALUE`, `MODIFIED_TIME`) VALUES (1, 'A', 5, '2012-01-03 11:40:26');

INSERT INTO `FAITH_LIFE_GRADING` (`FAITH_LIFE_GRADING_ID`, `DESCRIPTION`, `VALUE`, `MODIFIED_TIME`) VALUES (2, 'B', 4, '2012-01-03 11:40:26');

INSERT INTO `FAITH_LIFE_GRADING` (`FAITH_LIFE_GRADING_ID`, `DESCRIPTION`, `VALUE`, `MODIFIED_TIME`) VALUES (3, 'C', 3, '2012-01-03 11:40:26');

INSERT INTO `FAITH_LIFE_GRADING` (`FAITH_LIFE_GRADING_ID`, `DESCRIPTION`, `VALUE`, `MODIFIED_TIME`) VALUES (4, 'D', 2, '2012-01-03 11:40:26');

INSERT INTO `FAITH_LIFE_GRADING` (`FAITH_LIFE_GRADING_ID`, `DESCRIPTION`, `VALUE`, `MODIFIED_TIME`) VALUES (5, 'S', 1, '2012-01-03 11:40:26');

INSERT INTO `FAITH_LIFE_GRADING` (`FAITH_LIFE_GRADING_ID`, `DESCRIPTION`, `VALUE`, `MODIFIED_TIME`) VALUES (6, 'F', 0, '2012-01-03 11:40:26');

UPDATE `FAITH_LIFE_RATING` SET `GRADING_ID`=3;

ALTER TABLE `FAITH_LIFE_RATING`  
  ADD CONSTRAINT `fk_FAITH_LIFE_RATING_GRADING1`
  FOREIGN KEY (`GRADING_ID` )
  REFERENCES `FAITH_LIFE_GRADING` (`FAITH_LIFE_GRADING_ID` )
  ON DELETE CASCADE
  ON UPDATE CASCADE;

/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40014 SET UNIQUE_CHECKS=1 */;
