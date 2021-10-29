--CREATE TABLE `teams` (
--  `team_ID` int NOT NULL AUTO_INCREMENT,
--  `team_name` varchar(45) NOT NULL,
--  `description` varchar(45) NOT NULL,
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
--) ENGINE=InnoDB AUTO_INCREMENT=100007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
--
--
--CREATE TABLE `requests` (
--  `id` int NOT NULL AUTO_INCREMENT,
--  `user_id` int NOT NULL,
--  `current_team_id` int DEFAULT NULL,
--  `new_team_id` int DEFAULT NULL,
--  `status` varchar(45) NOT NULL DEFAULT 'PENDING',
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
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
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
--
--
--ALTER TABLE users AUTO_INCREMENT=100000;
--ALTER TABLE teams AUTO_INCREMENT=100;
--
--
insert into my_db.teams (team_name, description)
values
('Finance', 'Handling all the Finance of the organisation'),
('Marketing', 'Handling all the Marketing of the organisation'),
('Operations', 'Handling all the Operations of the organisation'),
('Human Resource', 'Handling all the Human Resource of the organisation'),
('General Management', 'Handling all the General Management of the organisation');
--



--insert into users(username,first_name,last_name,email,password,role,team_ID) values
--('KSK','Saba','Khan','todoroki.ice.fire.000@gmail.com','admin123','ROLE_ADMIN',100);

--ADD MORE DATA
