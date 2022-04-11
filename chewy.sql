-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.7.3-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- chewy 데이터베이스 구조 내보내기
DROP DATABASE IF EXISTS `chewy`;
CREATE DATABASE IF NOT EXISTS `chewy` /*!40100 DEFAULT CHARACTER SET utf8mb3 */;
USE `chewy`;

-- 테이블 chewy.cart 구조 내보내기
DROP TABLE IF EXISTS `cart`;
CREATE TABLE IF NOT EXISTS `cart` (
  `c_product` int(11) NOT NULL,
  `c_count` int(11) DEFAULT NULL,
  KEY `cart_productKey` (`c_product`),
  CONSTRAINT `cart_productKey` FOREIGN KEY (`c_product`) REFERENCES `product` (`p_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='카트';

-- 테이블 데이터 chewy.cart:~0 rows (대략적) 내보내기
DELETE FROM `cart`;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;

-- 테이블 chewy.category 구조 내보내기
DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `ct_id` int(11) NOT NULL AUTO_INCREMENT,
  `ct_L` varchar(200) DEFAULT NULL,
  `ct_M` varchar(200) DEFAULT NULL,
  `ct_S` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ct_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='카테고리';

-- 테이블 데이터 chewy.category:~0 rows (대략적) 내보내기
DELETE FROM `category`;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;

-- 테이블 chewy.product 구조 내보내기
DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `p_ct` int(11) NOT NULL,
  `p_name` varchar(255) DEFAULT NULL,
  `p_img` varchar(255) DEFAULT NULL,
  `p_price` float unsigned NOT NULL DEFAULT 0,
  `p_total` int(11) DEFAULT NULL,
  `p_discount` int(11) NOT NULL DEFAULT 0,
  `p_star` float NOT NULL DEFAULT 0,
  `p_review` int(11) DEFAULT NULL,
  `p_QnA` int(11) DEFAULT NULL,
  PRIMARY KEY (`p_id`),
  KEY `ct_key` (`p_ct`),
  KEY `QnA_key` (`p_QnA`),
  CONSTRAINT `QnA_key` FOREIGN KEY (`p_QnA`) REFERENCES `qna` (`Q_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ct_key` FOREIGN KEY (`p_ct`) REFERENCES `category` (`ct_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='상품';

-- 테이블 데이터 chewy.product:~0 rows (대략적) 내보내기
DELETE FROM `product`;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- 테이블 chewy.qna 구조 내보내기
DROP TABLE IF EXISTS `qna`;
CREATE TABLE IF NOT EXISTS `qna` (
  `Q_id` int(11) NOT NULL AUTO_INCREMENT,
  `Q_question` text DEFAULT NULL,
  `Q_answer` text DEFAULT NULL,
  `Q_good` int(11) DEFAULT NULL,
  PRIMARY KEY (`Q_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='QnA';

-- 테이블 데이터 chewy.qna:~0 rows (대략적) 내보내기
DELETE FROM `qna`;
/*!40000 ALTER TABLE `qna` DISABLE KEYS */;
/*!40000 ALTER TABLE `qna` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
