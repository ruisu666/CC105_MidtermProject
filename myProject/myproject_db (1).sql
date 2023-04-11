-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 11, 2023 at 03:27 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `myproject_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_accounts`
--

CREATE TABLE `tbl_accounts` (
  `lastName` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` text NOT NULL,
  `userType` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_accounts`
--

INSERT INTO `tbl_accounts` (`lastName`, `firstName`, `gender`, `username`, `password`, `userType`) VALUES
('Pelaez', 'Luis', 'Male', 'admin', 'adminadmin', 'Administrator'),
('EmanuelEmanuel', 'Emanuel', 'Male', 'Emanuel', 'EmanuelEmanuel', 'Administrator'),
('Pelaez', 'Luis', 'Male', 'Luis', 'LuisLuis', 'User'),
('Pelaez', 'Emanuel', 'Male', 'user', 'useruseruser', 'User');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_products`
--

CREATE TABLE `tbl_products` (
  `prodID` int(255) NOT NULL,
  `productName` varchar(255) NOT NULL,
  `productPrice` int(255) NOT NULL,
  `productQuantity` int(255) NOT NULL,
  `productModel` varchar(255) NOT NULL,
  `productBrand` varchar(255) NOT NULL,
  `productCategory` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_products`
--

INSERT INTO `tbl_products` (`prodID`, `productName`, `productPrice`, `productQuantity`, `productModel`, `productBrand`, `productCategory`) VALUES
(2, 'ASUS_VG249Q1R_23.8', 13395, 200, 'VG249Q1R', 'ASUS', 'Monitor'),
(3, 'ACER_NITRO_VGO_SERIES', 9950, 131, 'VGO_SERIES', 'ACER', 'Monitor'),
(4, 'ACER_EG220Q_PBIPX_21.5_LED', 7499, 200, 'EG220Q', 'ACER', 'Monitor'),
(5, 'LG_24MP400-B_24_IPS_FULL_HD', 6795, 213, '24MP400-B', 'LG', 'Monitor'),
(6, 'GLORIOUS_GMMK_PRO', 8095, 213, 'GMMK_PRO', 'GLORIOUS', 'Mouse'),
(7, 'GLORIOUS_GMMK_TKL', 5550, 223, 'GMMK', 'GLORIOUS', 'Mouse'),
(8, 'LOGITECH_PRO_X', 5495, 277, 'PRO_X', 'LOGITECH', 'Keyboard'),
(9, 'LOGITECH_G123_PRODIGY', 2095, 398, 'PRODIGY', 'LOGITECH', 'Keyboard');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_accounts`
--
ALTER TABLE `tbl_accounts`
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `tbl_products`
--
ALTER TABLE `tbl_products`
  ADD PRIMARY KEY (`prodID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_products`
--
ALTER TABLE `tbl_products`
  MODIFY `prodID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
