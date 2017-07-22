use `akura`;

--
-- Dumping data for table `USER_ROLE`
--
INSERT INTO `USER_ROLE` (`USER_ROLE_ID`,`ROLE`,`DESCRIPTION`,`MODIFIED_TIME`) 
VALUES (5,'Parent','Parent','2011-10-21 18:25:59');

--
-- Dumping data for table `COUNTRY`
--
INSERT INTO `COUNTRY` (`COUNTRY_NAME`) VALUES ('UnitedStates'),('Canada'),('Mexico'),('Afghanistan'),('Albania'),('Algeria'),('Andorra'),('Angola'),('Anguilla'),('Antarctica'),('AntiguaandBarbuda'),('Argentina'),('Armenia'),('Aruba'),('AscensionIsland'),('Australia'),('Austria'),('Azerbaijan'),('Bahamas'),('Bahrain'),('Bangladesh'),('Barbados'),('Belarus'),('Belgium'),('Belize'),('Benin'),('Bermuda'),('Bhutan'),('Bolivia'),('Bophuthatswana'),('Bosnia-Herzegovina'),('Botswana'),('BouvetIsland'),('Brazil'),('BritishIndianOcean'),('BritishVirginIslands'),('BruneiDarussalam'),('Bulgaria'),('BurkinaFaso'),('Burundi'),('Cambodia'),('Cameroon'),('CapeVerdeIsland'),('CaymanIslands'),('CentralAfrica'),('Chad'),('ChannelIslands'),('Chile'),('China,PeoplesRepublic'),('ChristmasIsland'),('Cocos(Keeling)Islands'),('Colombia'),('ComorosIslands'),('Congo'),('CookIslands'),('CostaRica'),('Croatia'),('Cuba'),('Cyprus'),('CzechRepublic'),('Denmark'),('Djibouti'),('Dominica'),('DominicanRepublic'),('EasterIsland'),('Ecuador'),('Egypt'),('ElSalvador'),('England'),('EquatorialGuinea'),('Estonia'),('Ethiopia'),('FalklandIslands'),('FaeroeIslands'),('Fiji'),('Finland'),('France'),('FrenchGuyana'),('FrenchPolynesia'),('Gabon'),('Gambia'),('GeorgiaRepublic'),('Germany'),('Gibraltar'),('Greece'),('Greenland'),('Grenada'),('Guadeloupe(French)'),('Guatemala'),('GuernseyIsland'),('GuineaBissau'),('Guinea'),('Guyana'),('Haiti'),('HeardandMcDonaldIsls'),('Honduras'),('HongKong'),('Hungary'),('Iceland'),('India'),('Iran'),('Iraq'),('Ireland'),('IsleofMan'),('Israel'),('Italy'),('IvoryCoast'),('Jamaica'),('Japan'),('JerseyIsland'),('Jordan'),('Kazakhstan'),('Kenya'),('Kiribati'),('Kuwait'),('Laos'),('Latvia'),('Lebanon'),('Lesotho'),('Liberia'),('Libya'),('Liechtenstein'),('Lithuania'),('Luxembourg'),('Macao'),('Macedonia'),('Madagascar'),('Malawi'),('Malaysia'),('Maldives'),('Mali'),('Malta'),('Martinique(French)'),('Mauritania'),('Mauritius'),('Mayotte'),('Micronesia'),('Moldavia'),('Monaco'),('Mongolia'),('Montenegro'),('Montserrat'),('Morocco'),('Mozambique'),('Myanmar'),('Namibia'),('Nauru'),('Nepal'),('NetherlandsAntilles'),('Netherlands'),('NewCaledonia(French)'),('NewZealand'),('Nicaragua'),('Niger'),('Niue'),('NorfolkIsland'),('NorthKorea'),('NorthernIreland'),('Norway'),('Oman'),('Pakistan'),('Panama'),('PapuaNewGuinea'),('Paraguay'),('Peru'),('Philippines'),('PitcairnIsland'),('Poland'),('Polynesia(French)'),('Portugal'),('Qatar'),('ReunionIsland'),('Romania'),('Russia'),('Rwanda'),('S.GeorgiaSandwichIsls'),('SaoTome,Principe'),('SanMarino'),('SaudiArabia'),('Scotland'),('Senegal'),('Serbia'),('Seychelles'),('Shetland'),('SierraLeone'),('Singapore'),('SlovakRepublic'),('Slovenia'),('SolomonIslands'),('Somalia'),('SouthAfrica'),('SouthKorea'),('Spain'),('SriLanka'),('St.Helena'),('St.Lucia'),('St.PierreMiquelon'),('St.Martins'),('St.KittsNevisAnguilla'),('St.VincentGrenadines'),('Sudan'),('Suriname'),('SvalbardJanMayen'),('Swaziland'),('Sweden'),('Switzerland'),('Syria'),('Tajikistan'),('Taiwan'),('Tahiti'),('Tanzania'),('Thailand'),('Togo'),('Tokelau'),('Tonga'),('TrinidadandTobago'),('Tunisia'),('Turkmenistan'),('TurksandCaicosIsls'),('Tuvalu'),('Uganda'),('Ukraine'),('UnitedArabEmirates'),('Uruguay'),('Uzbekistan'),('Vanuatu'),('VaticanCityState'),('Venezuela'),('Vietnam'),('VirginIslands(Brit)'),('Wales'),('WallisFutunaIslands'),('WesternSahara'),('WesternSamoa'),('Yemen'),('Yugoslavia'),('Zaire'),('Zambia'),('Zimbabwe');

--
-- Dumping data for table `RACE`
--
INSERT INTO `RACE` (`DESCRIPTION`) VALUES ('Sinhala');
INSERT INTO `RACE` (`DESCRIPTION`) VALUES ('Tamil');
INSERT INTO `RACE` (`DESCRIPTION`) VALUES ('Muslim');
INSERT INTO `RACE` (`DESCRIPTION`) VALUES ('Other');

--
-- Dumping data for table `CIVIL_STATUS`
--
INSERT INTO `CIVIL_STATUS` (`DESCRIPTION`) VALUES ('Married');
INSERT INTO `CIVIL_STATUS` (`DESCRIPTION`) VALUES ('Un married');
INSERT INTO `CIVIL_STATUS` (`DESCRIPTION`) VALUES ('Nun');

--
-- Dumping data for table `APPOINTMENT_NATURE`
--
INSERT INTO `APPOINTMENT_NATURE` (`DESCRIPTION`) VALUES ('Non Certificated');
INSERT INTO `APPOINTMENT_NATURE` (`DESCRIPTION`) VALUES ('Certificated');
INSERT INTO `APPOINTMENT_NATURE` (`DESCRIPTION`) VALUES ('Diploma');
INSERT INTO `APPOINTMENT_NATURE` (`DESCRIPTION`) VALUES ('Trainees');
INSERT INTO `APPOINTMENT_NATURE` (`DESCRIPTION`) VALUES ('Volunteers');
INSERT INTO `APPOINTMENT_NATURE` (`DESCRIPTION`) VALUES ('Other');

--
-- Dumping data for table `STAFF_SERVICE_CATEGORY`
--
INSERT INTO `STAFF_SERVICE_CATEGORY` (`DESCRIPTION`) VALUES ('SLEAS');
INSERT INTO `STAFF_SERVICE_CATEGORY` (`DESCRIPTION`) VALUES ('SLPS');
INSERT INTO `STAFF_SERVICE_CATEGORY` (`DESCRIPTION`) VALUES ('SLTS');
INSERT INTO `STAFF_SERVICE_CATEGORY` (`DESCRIPTION`) VALUES ('Librarian');

--
-- Dumping data for table `APPOINTMENT_CLASSIFICATION`
--
INSERT INTO `APPOINTMENT_CLASSIFICATION` (`DESCRIPTION`) VALUES ('Primary');
INSERT INTO `APPOINTMENT_CLASSIFICATION` (`DESCRIPTION`) VALUES ('Science');
INSERT INTO `APPOINTMENT_CLASSIFICATION` (`DESCRIPTION`) VALUES ('Mathematics');
INSERT INTO `APPOINTMENT_CLASSIFICATION` (`DESCRIPTION`) VALUES ('English');
INSERT INTO `APPOINTMENT_CLASSIFICATION` (`DESCRIPTION`) VALUES ('Social Studies');
INSERT INTO `APPOINTMENT_CLASSIFICATION` (`DESCRIPTION`) VALUES ('Aesthetic');
INSERT INTO `APPOINTMENT_CLASSIFICATION` (`DESCRIPTION`) VALUES ('Religion');
INSERT INTO `APPOINTMENT_CLASSIFICATION` (`DESCRIPTION`) VALUES ('Technical Subject');
INSERT INTO `APPOINTMENT_CLASSIFICATION` (`DESCRIPTION`) VALUES ('Commerce gratuate');
INSERT INTO `APPOINTMENT_CLASSIFICATION` (`DESCRIPTION`) VALUES ('Science graduate');
INSERT INTO `APPOINTMENT_CLASSIFICATION` (`DESCRIPTION`) VALUES ('Agriculture');
INSERT INTO `APPOINTMENT_CLASSIFICATION` (`DESCRIPTION`) VALUES ('Special Education');
INSERT INTO `APPOINTMENT_CLASSIFICATION` (`DESCRIPTION`) VALUES ('Physical education');
INSERT INTO `APPOINTMENT_CLASSIFICATION` (`DESCRIPTION`) VALUES ('Other');

--
-- Dumping data for table `SECTION`
--
INSERT INTO `SECTION` (`DESCRIPTION`) VALUES ('Primary');
INSERT INTO `SECTION` (`DESCRIPTION`) VALUES ('6 - 7');
INSERT INTO `SECTION` (`DESCRIPTION`) VALUES ('8 - 9');
INSERT INTO `SECTION` (`DESCRIPTION`) VALUES ('10 - 11 ');
INSERT INTO `SECTION` (`DESCRIPTION`) VALUES ('12 - 13 Commerce');
INSERT INTO `SECTION` (`DESCRIPTION`) VALUES ('12 - 13 Science');

--
-- Dumping data for table `EMPLOYMENT_STATUS`
--
INSERT INTO `EMPLOYMENT_STATUS` (`EMPLOYMENT_STATUS_ID`,`DESCRIPTION`,`MODIFIED_TIME`) VALUES 
 (1,'Employed','2012-05-31 16:55:58'),
 (2,'Not Employed','2012-05-31 16:55:58'),
 (3,'Private Business Owner','2012-05-31 16:55:58');
