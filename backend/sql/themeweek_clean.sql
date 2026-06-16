CREATE DATABASE IF NOT EXISTS `themeweek` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `themeweek`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userAccount` varchar(256) NOT NULL,
  `userPassword` varchar(512) NOT NULL,
  `unionId` varchar(256) DEFAULT NULL,
  `mpOpenId` varchar(256) DEFAULT NULL,
  `userName` varchar(256) DEFAULT NULL,
  `userAvatar` varchar(1024) DEFAULT NULL,
  `userProfile` varchar(512) DEFAULT NULL,
  `userRole` varchar(256) NOT NULL DEFAULT 'user',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isDelete` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_unionId` (`unionId`)
) ENGINE=InnoDB AUTO_INCREMENT=2015629289921245186 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

insert into `user`(`id`,`userAccount`,`userPassword`,`unionId`,`mpOpenId`,`userName`,`userAvatar`,`userProfile`,`userRole`,`createTime`,`updateTime`,`isDelete`) values
(1935152147047858177,'admin','b03d227f78c0c79334fca76e7b8eb46a',NULL,NULL,'admin',NULL,NULL,'admin','2025-06-18 09:46:39','2025-06-27 14:03:30',0),
(2015629289921245185,'13070849125','14c8f4f580cb3653f62466011e59feaa',NULL,NULL,'13070849125',NULL,NULL,'user','2026-01-26 11:34:05','2026-01-26 11:34:05',0);

DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(512) DEFAULT NULL,
  `content` text,
  `tags` varchar(1024) DEFAULT NULL,
  `thumbNum` int NOT NULL DEFAULT 0,
  `favourNum` int NOT NULL DEFAULT 0,
  `userId` bigint NOT NULL,
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isDelete` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `post_favour`;
CREATE TABLE `post_favour` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `postId` bigint NOT NULL,
  `userId` bigint NOT NULL,
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_postId` (`postId`),
  KEY `idx_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `post_thumb`;
CREATE TABLE `post_thumb` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `postId` bigint NOT NULL,
  `userId` bigint NOT NULL,
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_postId` (`postId`),
  KEY `idx_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `theme_category`;
CREATE TABLE `theme_category` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `categoryName` varchar(50) NOT NULL,
    `categoryIcon` varchar(50) DEFAULT '',
    `categoryDescription` varchar(500) DEFAULT '',
    `sortOrder` int DEFAULT 0,
    `isDelete` tinyint NOT NULL DEFAULT 0,
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

insert into `theme_category`(`categoryName`, `categoryIcon`, `categoryDescription`, `sortOrder`) values
('study', 'book', 'learning', 1),
('sports', 'run', 'sports', 2),
('aesthetic', 'art', 'aesthetic', 3),
('psychology', 'mind', 'psychology', 4),
('culture', 'building', 'culture', 5),
('skills', 'computer', 'skills', 6),
('travel', 'plane', 'travel', 7),
('handicraft', 'craft', 'handicraft', 8),
('social', 'chat', 'social', 9),
('life', 'leaf', 'life', 10),
('health', 'heart', 'health', 11),
('language', 'talk', 'language', 12);

DROP TABLE IF EXISTS `theme_template`;
CREATE TABLE `theme_template` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `categoryId` bigint NOT NULL,
    `templateName` varchar(100) NOT NULL,
    `templateIcon` varchar(50) NOT NULL,
    `templateDescription` text,
    `templateTags` varchar(500) DEFAULT '',
    `templateIdeas` text,
    `participants` int DEFAULT 0,
    `difficulty` varchar(20) DEFAULT 'medium',
    `isDelete` tinyint NOT NULL DEFAULT 0,
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_categoryId` (`categoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

insert into `theme_template`(`categoryId`, `templateName`, `templateIcon`, `templateDescription`, `templateTags`, `templateIdeas`, `participants`, `difficulty`) values
(1, 'Reading Week', 'book', 'Put down phone and immerse in books', 'study,reading', '["Finish a book","Write notes","Share thoughts"]', 512, 'easy'),
(1, 'History Week', 'books', 'Travel through time', 'study,knowledge', '["Read a history book","Watch documentary","Learn historical period"]', 256, 'medium'),
(1, 'Science Week', 'flask', 'Explore science mysteries', 'study,knowledge', '["Do a science experiment","Watch documentary","Learn a principle"]', 189, 'medium'),
(1, 'Geography Week', 'globe', 'Understand the world', 'study,knowledge', '["Learn about a country","Watch documentary","Learn geography"]', 145, 'easy'),
(1, 'Math Week', 'calc', 'Explore math beauty', 'study,thinking', '["Solve a problem","Learn math history","Learn speed calculation"]', 98, 'hard'),
(2, 'Running Week', 'run', 'Run and feel the happiness', 'sports,health', '["Run 3km daily","Try interval running","Join a running event"]', 1024, 'medium'),
(2, 'Swimming Week', 'swim', 'Feel freedom in water', 'sports,health', '["Swim 30min daily","Learn a stroke","Improve speed"]', 367, 'medium'),
(2, 'Cycling Week', 'bike', 'Explore city by bike', 'sports,health', '["Ride 5km daily","Explore new route","Record tracks"]', 289, 'medium'),
(2, 'Yoga Week', 'yoga', 'Find balance in breathing', 'sports,health', '["Practice 20min daily","Learn basic poses","Try meditation"]', 445, 'easy'),
(2, 'Boxing Week', 'boxing', 'Release power', 'sports,strength', '["Learn basic punches","Practice combos","Training"]', 156, 'hard'),
(3, 'Painting Week', 'paint', 'Express with brush', 'aesthetic,create', '["Paint daily","Try different materials","Learn composition"]', 378, 'easy'),
(3, 'Music Week', 'music', 'Feel music power', 'aesthetic,music', '["Listen to an album daily","Learn an instrument","Learn music genres"]', 423, 'easy'),
(3, 'Movie Week', 'film', 'Experience different lives', 'aesthetic,movie', '["Watch a movie daily","Write review","Learn filmmaking"]', 534, 'easy'),
(3, 'Photography Week', 'camera', 'Capture beautiful moments', 'aesthetic,record', '["Take themed photo daily","Learn composition","Make photo album"]', 267, 'medium'),
(4, 'Meditation Week', 'mind', 'Talk with yourself', 'psychology,health', '["Meditate 10min daily","Try different methods","Record feelings"]', 312, 'easy'),
(4, 'Sleep Week', 'sleep', 'Improve sleep quality', 'psychology,health', '["Sleep before 11pm","No phone before bed","Record sleep hours"]', 456, 'easy'),
(4, 'Diary Week', 'notebook', 'Record thoughts', 'psychology,record', '["Write diary daily","Record mood changes","Self reflection"]', 234, 'easy'),
(4, 'Confidence Week', 'strong', 'Build confidence', 'psychology,growth', '["Record strengths","Challenge fear","Practice self affirmation"]', 189, 'medium'),
(5, 'Museum Week', 'building', 'Dialogue with history', 'culture,history', '["Visit a museum","Learn about artifact","Learn exhibition planning"]', 178, 'easy'),
(5, 'Poetry Week', 'scroll', 'Taste classical poetry', 'culture,literature', '["Recite a poem","Learn poet story","Try writing poem"]', 145, 'medium'),
(5, 'Festival Week', 'gift', 'Learn traditional festivals', 'culture,tradition', '["Learn a festival","Learn customs","Make traditional food"]', 289, 'easy'),
(5, 'Tea Ceremony Week', 'tea', 'Experience oriental aesthetics', 'culture,life', '["Learn tea brewing","Learn tea types","Experience tea arrangement"]', 167, 'easy'),
(6, 'Programming Week', 'code', 'Learn programming', 'skills,tech', '["Complete intro course","Write a program","Learn a new language"]', 567, 'hard'),
(6, 'Speaking Week', 'mic', 'Improve expression', 'skills,communication', '["Practice 5min daily","Record speech","Give public speech"]', 234, 'medium'),
(6, 'Writing Week', 'pen', 'Improve writing', 'skills,create', '["Write short article daily","Learn writing skills","Complete a long article"]', 345, 'medium');

DROP TABLE IF EXISTS `user_theme_week`;
CREATE TABLE `user_theme_week` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `userId` bigint NOT NULL,
    `templateId` bigint,
    `weekIcon` varchar(50) NOT NULL,
    `weekName` varchar(100) NOT NULL,
    `weekDescription` text,
    `weekDifficulty` varchar(20) DEFAULT 'medium',
    `weekStatus` varchar(20) DEFAULT 'upcoming',
    `startDate` date NOT NULL,
    `endDate` date,
    `progress` int DEFAULT 0,
    `isDelete` tinyint NOT NULL DEFAULT 0,
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_userId` (`userId`),
    KEY `idx_templateId` (`templateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `week_task`;
CREATE TABLE `week_task` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `weekId` bigint NOT NULL,
    `taskName` varchar(200) NOT NULL,
    `taskOrder` int DEFAULT 0,
    `isCompleted` tinyint NOT NULL DEFAULT 0,
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_weekId` (`weekId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `checkin_record`;
CREATE TABLE `checkin_record` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `weekId` bigint NOT NULL,
    `userId` bigint NOT NULL,
    `checkinDate` date NOT NULL,
    `dayIndex` int NOT NULL,
    `checkinContent` text,
    `checkinDuration` int DEFAULT 0,
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_userId` (`userId`),
    KEY `idx_weekId` (`weekId`),
    UNIQUE KEY `uk_week_day` (`weekId`, `dayIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user_favorite`;
CREATE TABLE `user_favorite` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `userId` bigint NOT NULL,
    `templateId` bigint NOT NULL,
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_userId` (`userId`),
    KEY `idx_templateId` (`templateId`),
    UNIQUE KEY `uk_user_template` (`userId`, `templateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `browse_history`;
CREATE TABLE `browse_history` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `userId` bigint NOT NULL,
    `templateId` bigint NOT NULL,
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_userId` (`userId`),
    KEY `idx_templateId` (`templateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user_message`;
CREATE TABLE `user_message` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `userId` bigint NOT NULL,
    `messageType` varchar(30) DEFAULT 'system',
    `messageContent` text,
    `isRead` tinyint NOT NULL DEFAULT 0,
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;