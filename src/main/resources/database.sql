--CREATE TABLE `teams` (
--  `team_ID` int NOT NULL AUTO_INCREMENT,
--  `team_name` varchar(45) NOT NULL,
--  `description` varchar(200) NOT NULL,
--  PRIMARY KEY (`team_ID`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--
--
--CREATE TABLE `users` (
--  `user_ID` int NOT NULL AUTO_INCREMENT,
--  `username` varchar(45) NOT NULL,
--  `first_name` varchar(45) NOT NULL,
--  `last_name` varchar(45) NOT NULL,
--  `profile_image` varchar(200) DEFAULT 'https://i.pinimg.com/originals/90/71/88/90718823e398e86b0260ff47d7187acd.jpg',
--  `gender` varchar(45) DEFAULT NULL,
--  `address` varchar(45) DEFAULT NULL,
--  `contact` varchar(45) DEFAULT NULL,
--  `birth_date` date DEFAULT NULL,
--  `hire_date` date DEFAULT NULL,
--  `email` varchar(45) NOT NULL,
--  `password` varchar(45) NOT NULL,
--  `role` varchar(45) NOT NULL DEFAULT 'ROLE_USER',
--  `team_ID` int DEFAULT NULL,
--  PRIMARY KEY (`user_ID`),
--  UNIQUE KEY `username_UNIQUE` (`username`),
--  KEY `fk_users_teams` (`team_ID`),
--  CONSTRAINT `fk_users_teams` FOREIGN KEY (`team_ID`) REFERENCES `teams` (`team_ID`)
--) ENGINE=InnoDB AUTO_INCREMENT=100001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--
--
--CREATE TABLE `requests` (
--  `id` int NOT NULL AUTO_INCREMENT,
--  `user_id` int NOT NULL,
--  `current_team_id` int DEFAULT NULL,
--  `new_team_id` int DEFAULT NULL,
--  `status` varchar(45) NOT NULL DEFAULT 'PENDING',
--  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--  PRIMARY KEY (`id`),
--  KEY `fk_user_id_idx` (`user_id`),
--  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_ID`)
--) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--
--
--CREATE TABLE `wishes` (
--  `id` int NOT NULL AUTO_INCREMENT,
--  `sender_id` int NOT NULL,
--  `receiver_id` int NOT NULL,
--  `subject` varchar(45) NOT NULL DEFAULT 'BIRTHDAY WISHES',
--  `message` varchar(100) DEFAULT NULL,
--  `send_date` date NOT NULL,
--  `status` varchar(45) NOT NULL DEFAULT 'PENDING',
--  PRIMARY KEY (`id`),
--  KEY `fk_sender_id_idx` (`sender_id`),
--  KEY `fk_receiver_id_idx` (`receiver_id`),
--  CONSTRAINT `fk_receiver_id` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`user_ID`),
--  CONSTRAINT `fk_sender_id` FOREIGN KEY (`sender_id`) REFERENCES `users` (`user_ID`)
--) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--
--
--ALTER TABLE users AUTO_INCREMENT=100001;
--ALTER TABLE teams AUTO_INCREMENT=101;
--
--
--insert into my_db.teams (team_name, description)
--values
--('Finance',               'Handling all the Finance of the organisation'),
--('Marketing',             'Handling all the Marketing of the organisation'),
--('Operations',            'Handling all the Operations of the organisation'),
--('Human Resource',        'Handling all the Human Resource of the organisation'),
--('General Management',    'Handling all the General Management of the organisation');
--
--
--
--insert into my_db.users(username, first_name, last_name, birth_date, hire_date, email, password, team_ID) values
--('Itachi',	'Itachi',	'Uchiha',	    '1995-11-01',	'2014-11-04',	'example@gmail.com',	'itachi123',	101),
--('Todoroki',	'Shoto',	'Todoroki',	    '1995-11-15',	'2014-11-15',	'example@gmail.com',	'shoto123',		101),
--('Levi',	    'Levi',		'Ackerman',     '1995-11-06',	'2014-11-14',	'example@gmail.com',	'levi123',		101),
--('Langa',	    'Langa',	'Hasegawa',	    '1995-11-14',	'2014-11-16',	'example@gmail.com',	'langa123',		101),
--('Mikey',	    'Manjiro',	'Sano',	        '1991-10-14',	'2014-11-06',	'example@gmail.com',	'mikey123',		101),
--('Gintoki',	'Gintoki',	'Sakata',	    '1995-12-02',	'2014-12-03',	'example@gmail.com',	'gintoki123',	101),
--('Gojo',	    'Gojo',	    'Satoru',	    '1995-09-01',	'2010-11-12',	'example@gmail.com',	'gojo123',	    102),
--('Kyo',		'Kyo',		'Sohma',	    '1995-12-01',	'2014-11-13',	'example@gmail.com',	'kyo123',		102),
--('Rimuru',	'Rimuru',	'Tempest',	    '1995-11-12',	'2014-09-04',	'example@gmail.com',	'rimuru123',	102),
--('Kageyama',	'Tobio',	'Kageyama',	    '1995-11-12',	'2014-09-04',	'example@gmail.com',	'kageyama123',	102),
--('Bakugo',	'Katuski',	'Bakugo',	    '1995-11-06',	'2014-11-13',	'example@gmail.com',	'katuski123',	102),
--('Khun',	    'Khun',	    'Agero',	    '1995-11-13',	'2014-09-04',	'example@gmail.com',	'khun123',	    103),
--('Lelouch',  'Lelouch',   'Vi Britannia', '1995-11-14',	'2014-09-04',	'example@gmail.com',	'leloush123',	103),
--('Dazai',	    'Osamu',	'Dazai',	    '1995-11-12',	'2014-11-14',	'example@gmail.com',	'dazai123',	    103),
--('Kuro',	    'Kuro',	    'Yatogami',	    '1995-11-11',	'2014-11-15',	'example@gmail.com',	'kuro123',	    103),
--('Kaneki',	'Ken',	    'Kaneki',	    '1995-11-15',	'2014-09-04',	'example@gmail.com',	'ken123',	    103),
--('Sasuke',	'Sasuke',	'Uchiha',	    '1995-11-15',	'2014-09-04',	'example3@gmail.com',	'sasuke123',	104),
--('Kougami',	'Shinya',	'Kougami',	    '1995-11-16',	'2014-11-11',	'example@gmail.com',	'kougami123',	104),
--('Mikasa',	'Mikasa',	'Ackerman',	    '1995-11-14',	'2004-11-16',	'example@gmail.com',	'mikasa123',	104),
--('Makishima',	'Makishima','Shougo',	    '1995-11-15',	'2014-11-15',	'example2@gmail.com',	'makishima123',	105),
--('Ichigo',	'Ichigo',	'Kurosaki', 	'1995-11-15',	'2014-09-04',	'example@gmail.com',	'ichigo123',	105),
--('Madara',	'Madara',	'Uchiha',	    '1995-10-08',	'2014-11-04',	'example@gmail.com',	'madara123',	105),
--('Kakashi',	'Kakashi',	'Hatake',	    '1995-11-23',	'2014-08-25',	'example@gmail.com',	'kakashi123',	105);
--
--insert into my_db.users (username, first_name, last_name, birth_date, hire_date, email, password, role) values
--('KSK',		'Saba',		'Khan',	'1998-10-10',	'2021-08-09',	'saba@gmail.com',	    'admin123',	'ROLE_ADMIN'),
--('Nandy',	    'Nandan',	'Bhat',	'1999-07-12',	'2021-08-09',	'nandan@gmail.com',		'admin123',	'ROLE_ADMIN');
--
--
--insert into my_db.requests (user_id, current_team_id, new_team_id, status) values
--(100001, 101, 102,'DECLINED'),
--(100005, 101, 103,'DECLINED'),
--(100009, 102, 104,'DECLINED'),
--(100013, 103, 105,'DECLINED'),
--(100017, 104, 101,'DECLINED'),
--(100001, 101, 104,'PENDING'),
--(100005, 101, 105,'PENDING'),
--(100009, 102, 102,'PENDING'),
--(100013, 103, 103,'PENDING'),
--(100017, 104, 101,'PENDING');
--
--
--insert into my_db.wishes (sender_id, receiver_id, subject, message, send_date) values
--(100001, 100002, 'BIRTHDAY_WISHES',           'Happy Birthday!!!',            '2021-11-15'),
--(100003, 100002, 'BIRTHDAY_WISHES',           'Happy Birthday!!!',            '2021-11-15'),
--(100004, 100002, 'BIRTHDAY_WISHES',           'Happy Birthday!!!',            '2021-11-15'),
--(100005, 100002, 'JOB_ANNIVERSARY_WISHES',    'Happy Work Anniversary!!!',    '2021-11-15'),
--(100006, 100002, 'JOB_ANNIVERSARY_WISHES',    'Happy Work Anniversary!!!',    '2021-11-15'),
--(100021, 100020, 'BIRTHDAY_WISHES',           'Happy Birthday!!!',            '2021-11-15'),
--(100022, 100020, 'BIRTHDAY_WISHES',           'Happy Birthday!!!',            '2021-11-15'),
--(100023, 100020, 'BIRTHDAY_WISHES',           'Happy Birthday!!!',            '2021-11-15'),
--(100021, 100020, 'JOB_ANNIVERSARY_WISHES',    'Happy Work Anniversary!!!',    '2021-11-15'),
--(100023, 100020, 'JOB_ANNIVERSARY_WISHES',    'Happy Work Anniversary!!!',    '2021-11-15');
