/*
 SQLyog Community v13.3.1 (64 bit)
 MySQL - 8.0.44 : Database - themeweek
 *********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`themeweek` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `themeweek`;

/*Table structure for table `user` (原有表 - 保留原结构) */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `unionId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信开放平台id',
  `mpOpenId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公众号openId',
  `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `userAvatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
  `userProfile` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户简介',
  `userRole` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_unionId` (`unionId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2015629289921245186 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户';

/*Data for the table `user` */

insert  into `user`(`id`,`userAccount`,`userPassword`,`unionId`,`mpOpenId`,`userName`,`userAvatar`,`userProfile`,`userRole`,`createTime`,`updateTime`,`isDelete`) values
(1935152147047858177,'admin','b03d227f78c0c79334fca76e7b8eb46a',NULL,NULL,'管理员',NULL,NULL,'admin','2025-06-18 09:46:39','2025-06-27 14:03:30',0),
(2015629289921245185,'13070849125','14c8f4f580cb3653f62466011e59feaa',NULL,NULL,'13070849125',NULL,NULL,'user','2026-01-26 11:34:05','2026-01-26 11:34:05',0);

/*Table structure for table `post` (原有表 - 帖子/心得) */

DROP TABLE IF EXISTS `post`;

CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `tags` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签列表（json 数组）',
  `thumbNum` int NOT NULL DEFAULT '0' COMMENT '点赞数',
  `favourNum` int NOT NULL DEFAULT '0' COMMENT '收藏数',
  `userId` bigint NOT NULL COMMENT '创建用户 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='帖子';

/*Data for the table `post` */

/*Table structure for table `post_favour` (原有表 - 帖子收藏) */

DROP TABLE IF EXISTS `post_favour`;

CREATE TABLE `post_favour` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `postId` bigint NOT NULL COMMENT '帖子 id',
  `userId` bigint NOT NULL COMMENT '创建用户 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_postId` (`postId`) USING BTREE,
  KEY `idx_userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='帖子收藏';

/*Data for the table `post_favour` */

/*Table structure for table `post_thumb` (原有表 - 帖子点赞) */

DROP TABLE IF EXISTS `post_thumb`;

CREATE TABLE `post_thumb` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `postId` bigint NOT NULL COMMENT '帖子 id',
  `userId` bigint NOT NULL COMMENT '创建用户 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_postId` (`postId`) USING BTREE,
  KEY `idx_userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='帖子点赞';

/*Data for the table `post_thumb` */

/*====================================================*/
/* 新增主题周相关表 */
/*====================================================*/

/*Table structure for table `theme_category` (主题分类) */

DROP TABLE IF EXISTS `theme_category`;

CREATE TABLE `theme_category` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `categoryName` varchar(50) NOT NULL COMMENT '分类名称',
    `categoryIcon` varchar(50) DEFAULT '' COMMENT '分类图标',
    `categoryDescription` varchar(500) DEFAULT '' COMMENT '分类描述',
    `sortOrder` int DEFAULT 0 COMMENT '排序',
    `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='主题分类表';

/*Data for the table `theme_category` */

insert  into `theme_category`(`categoryName`, `categoryIcon`, `categoryDescription`, `sortOrder`) values
('学习', '📖', '学习知识，提升自我', 1),
('运动', '🏃', '锻炼身体，保持健康', 2),
('美育', '🎨', '培养审美，提升气质', 3),
('心理', '🧠', '关注内心，呵护心灵', 4),
('文化', '🏛', '传承文化，拓宽视野', 5),
('技能', '💻', '掌握技能，增值自我', 6),
('旅行', '✈️', '探索世界，增长见闻', 7),
('手工', '🎨', '动手创作，享受乐趣', 8),
('社交', '💬', '沟通交流，扩展人脉', 9),
('生活', '🌿', '热爱生活，享受当下', 10),
('健康', '💪', '关注健康，养成好习惯', 11),
('语言', '🗣', '学习语言，沟通世界', 12);

/*Table structure for table `theme_template` (主题模板表) */

DROP TABLE IF EXISTS `theme_template`;

CREATE TABLE `theme_template` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '模板ID',
    `categoryId` bigint NOT NULL COMMENT '所属分类ID',
    `templateName` varchar(100) NOT NULL COMMENT '模板名称',
    `templateIcon` varchar(50) NOT NULL COMMENT '图标',
    `templateDescription` text COMMENT '模板描述',
    `templateTags` varchar(500) DEFAULT '' COMMENT '标签,逗号分隔',
    `templateIdeas` text COMMENT '创意建议,JSON数组',
    `participants` int DEFAULT 0 COMMENT '参与人数',
    `difficulty` varchar(20) DEFAULT 'medium' COMMENT '难度: easy, medium, hard',
    `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_categoryId` (`categoryId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='主题模板表';

/*Data for the table `theme_template` */

insert  into `theme_template`(`categoryId`, `templateName`, `templateIcon`, `templateDescription`, `templateTags`, `templateIdeas`, `participants`, `difficulty`) values
(1, '阅读周', '📖', '放下手机，沉浸在文字的世界里', '学习,阅读', '["读完一本书","写读书笔记","分享读书心得"]', 512, 'easy'),
(1, '历史周', '📚', '穿越时空，了解人类文明的足迹', '学习,知识', '["读一本历史书","看一部历史纪录片","了解一个历史时期"]', 256, 'medium'),
(1, '科学周', '🔬', '探索科学的奥秘，满足好奇心', '学习,知识', '["做一个科学小实验","看一部科普纪录片","了解一个科学原理"]', 189, 'medium'),
(1, '地理周', '🌍', '了解世界，拓宽视野', '学习,知识', '["了解一个国家","看一部地理纪录片","学习地理知识"]', 145, 'easy'),
(1, '数学周', '🧮', '探索数学之美，训练逻辑思维', '学习,思维', '["解决一道难题","了解数学史","学习速算技巧"]', 98, 'hard'),
(2, '跑步周', '🏃', '用脚步丈量城市，感受奔跑的快乐', '运动,健康', '["每天跑步3公里","尝试间歇跑","参加一次跑步活动"]', 1024, 'medium'),
(2, '游泳周', '🏊', '在水中感受自由，锻炼身体', '运动,健康', '["每天游泳30分钟","学习一种泳姿","提高游泳速度"]', 367, 'medium'),
(2, '骑行周', '🚴', '用两个轮子探索城市，感受风的自由', '运动,健康', '["每天骑行5公里","探索一条新路线","记录骑行轨迹"]', 289, 'medium'),
(2, '瑜伽周', '🧘', '在呼吸与伸展中，找到身心的平衡', '运动,健康', '["每天练习瑜伽20分钟","学习基础体式","尝试冥想瑜伽"]', 445, 'easy'),
(2, '拳击周', '🥊', '释放力量，挑战自我极限', '运动,力量', '["学习基本拳法","练习组合拳","进行体能训练"]', 156, 'hard'),
(3, '绘画周', '🎨', '用画笔记录生活，表达内心世界', '美育,创作', '["每天画一幅画","尝试不同画材","学习构图技巧"]', 378, 'easy'),
(3, '音乐周', '🎧', '聆听不同风格的音乐，感受旋律的力量', '美育,音乐', '["每天听一张专辑","学习一种乐器","了解音乐流派"]', 423, 'easy'),
(3, '观影周', '🎬', '每天一部电影，体验不同的人生', '美育,电影', '["每天看一部电影","写影评","了解电影制作"]', 534, 'easy'),
(3, '摄影周', '📸', '用镜头捕捉生活中的美好瞬间', '美育,记录', '["每天拍一张主题照片","学习构图技巧","制作摄影集"]', 267, 'medium'),
(4, '冥想周', '🧠', '静下心来，与自己对话', '心理,健康', '["每天冥想10分钟","尝试不同冥想方式","记录内心感受"]', 312, 'easy'),
(4, '睡眠周', '😴', '改善睡眠质量，让身体充分休息', '心理,健康', '["每天11点前入睡","睡前不玩手机","记录睡眠时长"]', 456, 'easy'),
(4, '日记周', '📔', '记录内心想法，梳理情绪脉络', '心理,记录', '["每天写日记","记录情绪变化","进行自我反思"]', 234, 'easy'),
(4, '自信周', '💪', '建立自信，勇敢面对挑战', '心理,成长', '["记录自己的优点","挑战一件害怕的事","练习自我肯定"]', 189, 'medium'),
(5, '博物馆周', '🏛', '走进博物馆，与历史对话', '文化,历史', '["参观一座博物馆","了解一件文物","学习展览策划"]', 178, 'easy'),
(5, '诗词周', '📜', '品味古典诗词，感受文字之美', '文化,文学', '["背诵一首古诗","了解诗人故事","尝试写一首诗"]', 145, 'medium'),
(5, '传统节日周', '🧧', '了解传统节日，传承文化根脉', '文化,传统', '["了解一个传统节日","学习相关习俗","制作传统美食"]', 289, 'easy'),
(5, '茶道周', '🍵', '品茶悟道，感受东方美学', '文化,生活', '["学习泡茶技巧","了解茶叶种类","体验茶席布置"]', 167, 'easy'),
(6, '编程周', '💻', '学习编程，用代码创造可能', '技能,科技', '["完成一个编程入门课程","写一个小程序","学习一种新语言"]', 567, 'hard'),
(6, '演讲周', '🎤', '提升表达能力，勇敢说出自己的想法', '技能,沟通', '["每天练习演讲5分钟","录制一段演讲视频","参加一次公开演讲"]', 234, 'medium'),
(6, '写作周', '✍️', '提升写作能力，清晰表达思想', '技能,创作', '["每天写一篇短文","学习写作技巧","完成一篇长文"]', 345, 'medium');

/*Table structure for table `user_theme_week` (用户主题周表) */

DROP TABLE IF EXISTS `user_theme_week`;

CREATE TABLE `user_theme_week` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主题周ID',
    `userId` bigint NOT NULL COMMENT '用户ID',
    `templateId` bigint COMMENT '来源模板ID',
    `weekIcon` varchar(50) NOT NULL COMMENT '图标',
    `weekName` varchar(100) NOT NULL COMMENT '主题周名称',
    `weekDescription` text COMMENT '描述',
    `weekDifficulty` varchar(20) DEFAULT 'medium' COMMENT '难度: easy, medium, hard',
    `weekStatus` varchar(20) DEFAULT 'upcoming' COMMENT '状态: upcoming-未开始, ongoing-进行中, completed-已完成',
    `startDate` date NOT NULL COMMENT '开始日期',
    `endDate` date COMMENT '结束日期',
    `progress` int DEFAULT 0 COMMENT '完成进度 0-100',
    `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_userId` (`userId`) USING BTREE,
    KEY `idx_templateId` (`templateId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户主题周表';

/*Data for the table `user_theme_week` */

/*Table structure for table `week_task` (主题周任务表) */

DROP TABLE IF EXISTS `week_task`;

CREATE TABLE `week_task` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `weekId` bigint NOT NULL COMMENT '主题周ID',
    `taskName` varchar(200) NOT NULL COMMENT '任务名称',
    `taskOrder` int DEFAULT 0 COMMENT '排序',
    `isCompleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否完成: 0-未完成, 1-已完成',
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_weekId` (`weekId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='主题周任务表';

/*Data for the table `week_task` */

/*Table structure for table `checkin_record` (打卡记录表) */

DROP TABLE IF EXISTS `checkin_record`;

CREATE TABLE `checkin_record` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '打卡记录ID',
    `weekId` bigint NOT NULL COMMENT '主题周ID',
    `userId` bigint NOT NULL COMMENT '用户ID',
    `checkinDate` date NOT NULL COMMENT '打卡日期',
    `dayIndex` int NOT NULL COMMENT '第几天(0-6)',
    `checkinContent` text COMMENT '打卡内容',
    `checkinDuration` int DEFAULT 0 COMMENT '打卡时长(分钟)',
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_userId` (`userId`) USING BTREE,
    KEY `idx_weekId` (`weekId`) USING BTREE,
    UNIQUE KEY `uk_week_day` (`weekId`, `dayIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='打卡记录表';

/*Data for the table `checkin_record` */

/*Table structure for table `user_favorite` (用户收藏表) */

DROP TABLE IF EXISTS `user_favorite`;

CREATE TABLE `user_favorite` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    `userId` bigint NOT NULL COMMENT '用户ID',
    `templateId` bigint NOT NULL COMMENT '收藏的模板ID',
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_userId` (`userId`) USING BTREE,
    KEY `idx_templateId` (`templateId`) USING BTREE,
    UNIQUE KEY `uk_user_template` (`userId`, `templateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户收藏表';

/*Data for the table `user_favorite` */

/*Table structure for table `browse_history` (浏览历史表) */

DROP TABLE IF EXISTS `browse_history`;

CREATE TABLE `browse_history` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '历史ID',
    `userId` bigint NOT NULL COMMENT '用户ID',
    `templateId` bigint NOT NULL COMMENT '浏览的模板ID',
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '浏览时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_userId` (`userId`) USING BTREE,
    KEY `idx_templateId` (`templateId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='浏览历史表';

/*Data for the table `browse_history` */

/*Table structure for table `user_message` (用户消息表) */

DROP TABLE IF EXISTS `user_message`;

CREATE TABLE `user_message` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    `userId` bigint NOT NULL COMMENT '用户ID',
    `messageType` varchar(30) DEFAULT 'system' COMMENT '消息类型: system-系统, user-用户, activity-活动',
    `messageContent` text COMMENT '消息内容',
    `isRead` tinyint NOT NULL DEFAULT 0 COMMENT '是否已读: 0-未读, 1-已读',
    `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户消息表';

/*Data for the table `user_message` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;