-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               10.4.12-MariaDB - mariadb.org binary distribution
-- Операционная система:         Win64
-- HeidiSQL Версия:              10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Дамп структуры базы данных myblog
CREATE DATABASE IF NOT EXISTS `myblog` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `myblog`;

-- Дамп структуры для таблица myblog.articles
CREATE TABLE IF NOT EXISTS `articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL COMMENT 'Заголовок статьи',
  `description` mediumtext NOT NULL COMMENT 'Краткое описанте статьи',
  `id_text` int(11) NOT NULL COMMENT 'Текст статьи',
  `date` date NOT NULL COMMENT 'Дата добавления статьи.',
  `id_users` varchar(45) NOT NULL COMMENT 'вторичный ключ таблицы Users',
  PRIMARY KEY (`id`),
  UNIQUE KEY `title_UNIQUE` (`title`),
  KEY `id_text` (`id_text`),
  KEY `id_users` (`id_users`),
  CONSTRAINT `articles_ibfk_3` FOREIGN KEY (`id_users`) REFERENCES `users` (`id_users`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `articles_ibfk_4` FOREIGN KEY (`id_text`) REFERENCES `attachment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица myblog.attachment
CREATE TABLE IF NOT EXISTS `attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FILE_NAME` varchar(50) NOT NULL,
  `FILE_DATA` blob NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `date` datetime NOT NULL,
  `ref_id` int(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица myblog.groupuser
CREATE TABLE IF NOT EXISTS `groupuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_users` varchar(45) NOT NULL COMMENT 'Вторичный ключ от таблицы users',
  `id_role` varchar(50) NOT NULL COMMENT 'Вторичный ключ от таблицы roles',
  PRIMARY KEY (`id`),
  KEY `id_role` (`id_role`),
  KEY `id_users` (`id_users`),
  CONSTRAINT `groupuser_ibfk_1` FOREIGN KEY (`id_role`) REFERENCES `roles` (`id_role`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `groupuser_ibfk_2` FOREIGN KEY (`id_users`) REFERENCES `users` (`id_users`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица myblog.groupuser_has_articles
CREATE TABLE IF NOT EXISTS `groupuser_has_articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_role` varchar(50) NOT NULL,
  `id_articles` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_role` (`id_role`),
  KEY `id_articles` (`id_articles`),
  CONSTRAINT `groupuser_has_articles_ibfk_1` FOREIGN KEY (`id_role`) REFERENCES `roles` (`id_role`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `groupuser_has_articles_ibfk_2` FOREIGN KEY (`id_articles`) REFERENCES `articles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица myblog.messages
CREATE TABLE IF NOT EXISTS `messages` (
  `id` int(11) NOT NULL,
  `text` varchar(255) NOT NULL COMMENT 'Текст сообщения',
  `date` timestamp NOT NULL DEFAULT current_timestamp() COMMENT 'Дата мессаги',
  `id_users` varchar(15) NOT NULL COMMENT 'Юзер пославший мессагу',
  `id_articles` int(11) NOT NULL COMMENT 'Статье к которой послан комент',
  PRIMARY KEY (`id`),
  KEY `fk_messages_users1` (`id_users`),
  KEY `fk_messages_articles1` (`id_articles`),
  CONSTRAINT `fk_messages_articles1` FOREIGN KEY (`id_articles`) REFERENCES `articles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_messages_users1` FOREIGN KEY (`id_users`) REFERENCES `users` (`id_users`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица myblog.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id_role` varchar(50) NOT NULL,
  PRIMARY KEY (`id_role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Экспортируемые данные не выделены.

-- Дамп структуры для таблица myblog.users
CREATE TABLE IF NOT EXISTS `users` (
  `id_users` varchar(45) NOT NULL COMMENT 'Логин',
  `name` varchar(250) NOT NULL,
  `pass` varchar(255) NOT NULL COMMENT 'Пароль SHA-256',
  `email` varchar(55) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id_users`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Экспортируемые данные не выделены.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
