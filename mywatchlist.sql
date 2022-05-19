-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 17, 2022 at 11:02 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mywatchlist`
--
CREATE DATABASE IF NOT EXISTS `mywatchlist` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `mywatchlist`;

-- --------------------------------------------------------

--
-- Table structure for table `movielist`
--

CREATE TABLE `movielist` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `score` int(11) NOT NULL,
  `comments` varchar(100) NOT NULL,
  `movies_id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `movies`
--

CREATE TABLE `movies` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `producer` varchar(100) NOT NULL,
  `studio` varchar(100) NOT NULL,
  `genre` varchar(100) NOT NULL,
  `runtime` varchar(100) NOT NULL,
  `rating` varchar(100) NOT NULL,
  `score` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `movies`
--

INSERT INTO `movies` (`id`, `title`, `producer`, `studio`, `genre`, `runtime`, `rating`, `score`) VALUES
(1, 'Avengers Endgame', 'Kevin Feige', 'Marvel Studios', 'Action', '181 minutes', 'PG-13', '8.4/10'),
(2, 'Dune', 'Brian Herbert', 'Warner Bros', 'Sci-Fi', '155 minutes', 'PG-13', '8.2/10'),
(3, 'No Time To Die ', 'Barbara A Broccoli ', 'Universal Studios', 'Action', '163 minutes', 'PG-13', '7.2/10');

-- --------------------------------------------------------

--
-- Table structure for table `series`
--

CREATE TABLE `series` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `producer` varchar(100) NOT NULL,
  `studio` varchar(100) NOT NULL,
  `genre` varchar(100) NOT NULL,
  `runtime` varchar(100) NOT NULL,
  `rating` varchar(100) NOT NULL,
  `score` varchar(100) NOT NULL,
  `episode` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `series`
--

INSERT INTO `series` (`id`, `title`, `producer`, `studio`, `genre`, `runtime`, `rating`, `score`, `episode`) VALUES
(1, 'Brooklyn Nine-Nine', 'Dan Goor', 'Fremulon', 'Sitcom', '21-23 minutes', 'TV-14', '8.3/10', '153'),
(2, 'Squid Game', 'Hwang Dong-Hyuk', 'Siren Pictures Inc.', 'Thriller', '32-63 minutes', 'TV-MA', '8.3/10', '9'),
(3, 'Attack on Titan S1', 'George Wada', 'Wit Studios', 'Thriller', '23 minutes', 'TV-MA', '9/10', '25');

-- --------------------------------------------------------

--
-- Table structure for table `serieslist`
--

CREATE TABLE `serieslist` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `score` int(11) NOT NULL,
  `episode` int(11) NOT NULL,
  `series_id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `gender` varchar(100) NOT NULL,
  `bio` varchar(255) NOT NULL,
  `age` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `movielist`
--
ALTER TABLE `movielist`
  ADD PRIMARY KEY (`id`),
  ADD KEY `movies_id_fk` (`movies_id`),
  ADD KEY `users_id_fk` (`users_id`);

--
-- Indexes for table `movies`
--
ALTER TABLE `movies`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `series`
--
ALTER TABLE `series`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `serieslist`
--
ALTER TABLE `serieslist`
  ADD PRIMARY KEY (`id`),
  ADD KEY `series_id_fk` (`series_id`),
  ADD KEY `serieslist_users_id_fk` (`users_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `movielist`
--
ALTER TABLE `movielist`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `movies`
--
ALTER TABLE `movies`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `series`
--
ALTER TABLE `series`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `serieslist`
--
ALTER TABLE `serieslist`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `movielist`
--
ALTER TABLE `movielist`
  ADD CONSTRAINT `movies_id_fk` FOREIGN KEY (`movies_id`) REFERENCES `movies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `users_id_fk` FOREIGN KEY (`users_id`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `serieslist`
--
ALTER TABLE `serieslist`
  ADD CONSTRAINT `series_id_fk` FOREIGN KEY (`series_id`) REFERENCES `series` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `serieslist_users_id_fk` FOREIGN KEY (`users_id`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
