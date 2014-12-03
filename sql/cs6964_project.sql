-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 03, 2014 at 10:03 AM
-- Server version: 5.5.40-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `cs6964_project`
--
CREATE DATABASE IF NOT EXISTS `cs6964_project` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `cs6964_project`;

-- --------------------------------------------------------

--
-- Table structure for table `AccessLevels`
--

DROP TABLE IF EXISTS `AccessLevels`;
CREATE TABLE `AccessLevels` (
  `Level` tinyint(4) unsigned NOT NULL,
  `Name` varchar(20) NOT NULL,
  UNIQUE KEY `Level` (`Level`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `AccessLevels`
--

INSERT INTO `AccessLevels` (`Level`, `Name`) VALUES
(0, 'Guest'),
(7, 'Administrator');

-- --------------------------------------------------------

--
-- Table structure for table `Groups`
--

DROP TABLE IF EXISTS `Groups`;
CREATE TABLE `Groups` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Name` (`Name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `Groups`
--

INSERT INTO `Groups` (`ID`, `Name`) VALUES
(1, 'Group Members');

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
CREATE TABLE `Users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(20) NOT NULL,
  `Password` char(40) NOT NULL,
  `FirstName` varchar(20) DEFAULT NULL,
  `LastName` varchar(20) DEFAULT NULL,
  `AccessLevel` tinyint(4) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Username` (`Username`),
  KEY `AccessLevel` (`AccessLevel`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`ID`, `Username`, `Password`, `FirstName`, `LastName`, `AccessLevel`) VALUES
(1, 'christopher', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'Christopher', 'Becker', 7),
(2, 'shivam', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'Shivam', 'Sharma', 7);

-- --------------------------------------------------------

--
-- Table structure for table `XREF_Users_Groups`
--

DROP TABLE IF EXISTS `XREF_Users_Groups`;
CREATE TABLE `XREF_Users_Groups` (
  `UserID` int(11) NOT NULL,
  `GroupID` int(11) NOT NULL,
  KEY `UserID` (`UserID`),
  KEY `GroupID` (`GroupID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `XREF_Users_Groups`
--

INSERT INTO `XREF_Users_Groups` (`UserID`, `GroupID`) VALUES
(1, 1),
(2, 1);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Users`
--
ALTER TABLE `Users`
  ADD CONSTRAINT `Users_ibfk_1` FOREIGN KEY (`AccessLevel`) REFERENCES `AccessLevels` (`Level`);

--
-- Constraints for table `XREF_Users_Groups`
--
ALTER TABLE `XREF_Users_Groups`
  ADD CONSTRAINT `XREF_Users_Groups_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `Users` (`ID`) ON DELETE CASCADE,
  ADD CONSTRAINT `XREF_Users_Groups_ibfk_2` FOREIGN KEY (`GroupID`) REFERENCES `Groups` (`ID`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
