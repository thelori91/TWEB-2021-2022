-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Gen 03, 2022 alle 14:05
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

--
-- Dump dei dati per la tabella `Course`
--

INSERT INTO `Course` (`Name`) VALUES
('Comunicazione'),
('Elettronica'),
('Fisica'),
('Matematica'),
('Scienze');

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
  `Time` enum('08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00') NOT NULL,
  `State` enum('Active','Done','Cancelled') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `Lesson`
--

INSERT INTO `Lesson` (`Teacher`, `Course`, `User`, `Day`, `ID`, `Time`, `State`) VALUES
(15, 'Matematica', 'Polletto!', 'Monday', 3, '09:00', 'Active'),
(17, 'Matematica', 'Polletto!', 'Tuesday', 4, '10:00', 'Active'),
(14, 'Fisica', 'Polletto!', 'Thursday', 5, '14:00', 'Cancelled'),
(14, 'Fisica', 'Polletto!', 'Monday', 6, '12:00', 'Done');

-- --------------------------------------------------------

--
-- Struttura della tabella `Teacher`
--

CREATE TABLE `Teacher` (
  `Name` varchar(30) NOT NULL,
  `ID` bigint(20) UNSIGNED NOT NULL,
  `Surname` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `Teacher`
--

INSERT INTO `Teacher` (`Name`, `ID`, `Surname`) VALUES
('Alberto', 13, 'Angela'),
('Piero', 14, 'Angela'),
('Nadir', 15, 'Murru'),
('Andrea', 16, 'Grosso'),
('Roberta', 17, 'Sirovich');

-- --------------------------------------------------------

--
-- Struttura della tabella `TeacherCourse`
--

CREATE TABLE `TeacherCourse` (
  `Teacher` bigint(20) UNSIGNED NOT NULL,
  `Course` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `TeacherCourse`
--

INSERT INTO `TeacherCourse` (`Teacher`, `Course`) VALUES
(13, 'Fisica'),
(13, 'Scienze'),
(14, 'Scienze'),
(15, 'Matematica'),
(17, 'Matematica');

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
-- Dump dei dati per la tabella `User`
--

INSERT INTO `User` (`Username`, `Password`, `Role`, `Name`, `Surname`) VALUES
('Polletto!', 'ginogino', 'Student', 'Andrea', 'Cacioli'),
('TheLori91', 'giobin69', 'Admin', 'Lorenzo', 'Cassinelli');

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
  ADD KEY `fkUser` (`User`),
  ADD KEY `fkCourse` (`Course`),
  ADD KEY `fkTeacher` (`Teacher`);

--
-- Indici per le tabelle `Teacher`
--
ALTER TABLE `Teacher`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `TeacherCourse`
--
ALTER TABLE `TeacherCourse`
  ADD PRIMARY KEY (`Teacher`,`Course`),
  ADD KEY `fk_Course` (`Course`);

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
  MODIFY `ID` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT per la tabella `Teacher`
--
ALTER TABLE `Teacher`
  MODIFY `ID` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `Lesson`
--
ALTER TABLE `Lesson`
  ADD CONSTRAINT `fkCourse` FOREIGN KEY (`Course`) REFERENCES `TeacherCourse` (`Course`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkTeacher` FOREIGN KEY (`Teacher`) REFERENCES `TeacherCourse` (`Teacher`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkUser` FOREIGN KEY (`User`) REFERENCES `User` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `TeacherCourse`
--
ALTER TABLE `TeacherCourse`
  ADD CONSTRAINT `fk_Course` FOREIGN KEY (`Course`) REFERENCES `Course` (`Name`),
  ADD CONSTRAINT `fk_Teacher` FOREIGN KEY (`Teacher`) REFERENCES `Teacher` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
