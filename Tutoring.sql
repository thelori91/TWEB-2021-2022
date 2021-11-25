-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Nov 25, 2021 alle 14:24
-- Versione del server: 10.4.21-MariaDB
-- Versione PHP: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Tutoring`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `Course`
--

CREATE TABLE `Course` (
  `Name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `Lesson`
--

CREATE TABLE `Lesson` (
  `Teacher` bigint(20) UNSIGNED NOT NULL,
  `Course` varchar(30) NOT NULL,
  `User` varchar(30) NOT NULL,
  `Day` enum('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday') NOT NULL,
  `ID` bigint(20) UNSIGNED NOT NULL,
  `Time` enum('08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `Teacher`
--

CREATE TABLE `Teacher` (
  `Name` varchar(30) NOT NULL,
  `ID` bigint(20) UNSIGNED NOT NULL,
  `Surname` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `User`
--

CREATE TABLE `User` (
  `Username` varchar(30) NOT NULL,
  `Password` varchar(30) NOT NULL,
  `Role` enum('Admin','Student') DEFAULT NULL,
  `Name` varchar(15) NOT NULL,
  `Surname` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `Course`
--
ALTER TABLE `Course`
  ADD PRIMARY KEY (`Name`);

--
-- Indici per le tabelle `Lesson`
--
ALTER TABLE `Lesson`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fkTeacher` (`Teacher`),
  ADD KEY `fkCourse` (`Course`),
  ADD KEY `fkUser` (`User`);

--
-- Indici per le tabelle `Teacher`
--
ALTER TABLE `Teacher`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`Username`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `Lesson`
--
ALTER TABLE `Lesson`
  MODIFY `ID` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `Teacher`
--
ALTER TABLE `Teacher`
  MODIFY `ID` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `Lesson`
--
ALTER TABLE `Lesson`
  ADD CONSTRAINT `fkCourse` FOREIGN KEY (`Course`) REFERENCES `Course` (`Name`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkTeacher` FOREIGN KEY (`Teacher`) REFERENCES `Teacher` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkUser` FOREIGN KEY (`User`) REFERENCES `User` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
