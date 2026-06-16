USE `themeweek`;

-- 重命名 theme_category 表字段
ALTER TABLE `theme_category` 
CHANGE COLUMN `categoryName` `category_name` VARCHAR(50) NOT NULL,
CHANGE COLUMN `categoryIcon` `category_icon` VARCHAR(50) DEFAULT '',
CHANGE COLUMN `categoryDescription` `category_description` VARCHAR(500) DEFAULT '',
CHANGE COLUMN `sortOrder` `sort_order` INT DEFAULT 0;

-- 重命名 theme_template 表字段
ALTER TABLE `theme_template` 
CHANGE COLUMN `categoryId` `category_id` BIGINT NOT NULL,
CHANGE COLUMN `templateName` `template_name` VARCHAR(100) NOT NULL,
CHANGE COLUMN `templateIcon` `template_icon` VARCHAR(50) NOT NULL,
CHANGE COLUMN `templateDescription` `template_description` TEXT;

-- 重命名 user_theme_week 表字段
ALTER TABLE `user_theme_week` 
CHANGE COLUMN `userId` `user_id` BIGINT NOT NULL,
CHANGE COLUMN `templateId` `template_id` BIGINT NOT NULL,
CHANGE COLUMN `weekName` `week_name` VARCHAR(100) NOT NULL;

-- 重命名 week_task 表字段
ALTER TABLE `week_task` 
CHANGE COLUMN `weekId` `week_id` BIGINT NOT NULL,
CHANGE COLUMN `taskName` `task_name` VARCHAR(200) NOT NULL,
CHANGE COLUMN `isCompleted` `is_completed` TINYINT NOT NULL DEFAULT 0;

-- 重命名 checkin_record 表字段
ALTER TABLE `checkin_record` 
CHANGE COLUMN `weekId` `week_id` BIGINT NOT NULL,
CHANGE COLUMN `userId` `user_id` BIGINT NOT NULL,
CHANGE COLUMN `checkinDate` `checkin_date` DATE NOT NULL;

-- 重命名 user_favorite 表字段
ALTER TABLE `user_favorite` 
CHANGE COLUMN `userId` `user_id` BIGINT NOT NULL,
CHANGE COLUMN `templateId` `template_id` BIGINT NOT NULL,
CHANGE COLUMN `createTime` `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- 重命名 browse_history 表字段
ALTER TABLE `browse_history` 
CHANGE COLUMN `userId` `user_id` BIGINT NOT NULL,
CHANGE COLUMN `templateId` `template_id` BIGINT NOT NULL,
CHANGE COLUMN `createTime` `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- 重命名 user_message 表字段
ALTER TABLE `user_message` 
CHANGE COLUMN `userId` `user_id` BIGINT NOT NULL,
CHANGE COLUMN `messageType` `message_type` VARCHAR(50) NOT NULL,
CHANGE COLUMN `messageContent` `message_content` TEXT NOT NULL,
CHANGE COLUMN `isRead` `is_read` TINYINT NOT NULL DEFAULT 0,
CHANGE COLUMN `createTime` `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;
