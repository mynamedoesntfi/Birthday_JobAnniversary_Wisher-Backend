CREATE TABLE `teams` (
  `team_ID` int NOT NULL AUTO_INCREMENT,
  `team_name` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`team_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `users` (
  `user_ID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `profile_image` varchar(200) DEFAULT "https://i.pinimg.com/originals/90/71/88/90718823e398e86b0260ff47d7187acd.jpg",
  `gender` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `contact` varchar(45) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `hire_date` date DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL DEFAULT 'ROLE_USER',
  `team_ID` int NOT NULL,
  PRIMARY KEY (`user_ID`),
constraint `fk_users_teams` foreign key(`team_ID`) references teams(`team_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


ALTER TABLE users AUTO_INCREMENT=100000;
ALTER TABLE teams AUTO_INCREMENT=100;


insert into teams(team_name, description) values 
('Finance','Handling all the finances of the oragnisation');

insert into users(username,first_name,last_name,email,password,team_ID) values
('Itachi','Itachi','Uchiha','todoroki.ice.fire.000@gmail.com','itachi123',100),
('Shoto','Shoto','Todoroki','todoroki.ice.fire.000@gmail.com','shoto123',100),
('Levi','Levi','Ackerman','todoroki.ice.fire.000@gmail.com','levi123',100),
('Langa','Langa','Hasegawa','todoroki.ice.fire.000@gmail.com','langa123',100);

