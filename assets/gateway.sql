-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 14-07-2020 a las 22:31:27
-- Versión del servidor: 10.4.10-MariaDB
-- Versión de PHP: 7.3.12
DROP DATABASE IF EXISTS `gateway`;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gateway`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `almacen`
--

CREATE DATABASE `gateway`;
USE `gateway`;
DROP TABLE IF EXISTS `almacen` ;
CREATE TABLE IF NOT EXISTS `almacen` (
  `clave` varchar(10) NOT NULL,
  `descrip` varchar(200) NOT NULL,
  `medida` varchar(40) NOT NULL,
  `line` varchar(20) NOT NULL,
  `f_ent` date NOT NULL,
  `stock` integer(11) NOT NULL,
  `ult_cost` double NOT NULL,
  PRIMARY KEY (`clave`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asignaciones`
--

DROP TABLE IF EXISTS `asignaciones`;
CREATE TABLE IF NOT EXISTS `asignaciones` (
  `id_asignacion` int(5) NOT NULL AUTO_INCREMENT,
  `fecha_asignacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `id` bigint(15) NOT NULL,
  `id_usuario` int(5) NOT NULL,
  `orden_compra_dt` varchar(15) NOT NULL DEFAULT 'No Definido',
  `importe` double NOT NULL DEFAULT 0,
  `total_pagar` double NOT NULL DEFAULT 0,
  `status_facturacion` double NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_asignacion`),
  UNIQUE KEY `id` (`id`),
  KEY `fk_clave_usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facturacion`
--

DROP TABLE IF EXISTS `facturacion`;
CREATE TABLE IF NOT EXISTS `facturacion` (
  `id` bigint(20) NOT NULL,
  `project_code` varchar(20) NOT NULL,
  `project_name` varchar(50) NOT NULL DEFAULT 'Undefined',
  `customer` varchar(50) NOT NULL,
  `PO_status` varchar(10) NOT NULL,
  `PO_NO` varchar(20) NOT NULL,
  `PO_Line_NO` int(5) NOT NULL,
  `shipment_NO` int(3) NOT NULL,
  `site_code` varchar(90) NOT NULL,
  `site_name` varchar(50) NOT NULL,
  `item_code` bigint(15) NOT NULL,
  `item_desc` varchar(200) NOT NULL DEFAULT 'Undefined',
  `requested_qty` double NOT NULL,
  `due_qty` double NOT NULL,
  `billed_qty` double NOT NULL,
  `unit_price` double NOT NULL,
  `line_amount` double NOT NULL,
  `unit` varchar(5) NOT NULL,
  `payment_terms` varchar(150) NOT NULL,
  `category` varchar(150) NOT NULL,
  `bidding_area` varchar(30) NOT NULL DEFAULT 'Undefined',
  `publish_date` datetime NOT NULL,
  `status_cierre` double NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial_asignaciones`
--

DROP TABLE IF EXISTS `historial_asignaciones`;
CREATE TABLE IF NOT EXISTS `historial_asignaciones` (
  `id_historial` int(5) NOT NULL AUTO_INCREMENT,
  `id_asignacion` int(5) NOT NULL DEFAULT 0,
  `fecha_asignacion` datetime NOT NULL,
  `id` bigint(15) NOT NULL,
  `id_usuario` int(5) NOT NULL DEFAULT 0,
  `orden_compra_dt` varchar(15) NOT NULL DEFAULT 'No Definido',
  `importe` double NOT NULL DEFAULT 0,
  `total_pagar` double NOT NULL DEFAULT 0,
  `status_facturacion` varchar(4) DEFAULT '0',
  PRIMARY KEY (`id_historial`),
  KEY `fk_clave_asignacion` (`id_asignacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id_usuario` int(5) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `ape_pat` varchar(30) NOT NULL,
  `ape_mat` varchar(30) NOT NULL,
  `nombre_usuario` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `cat_usuario` varchar(30) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--
-- Categorías de Usuarios Asignables en el Software:
-- Administrador Principal
-- Administrador Facturacion
-- Administrador Cierre
-- Administrador Entradas Almacen
-- Administrador Salidas Almacen
-- Operador Salidas Almacen
-- Operador Entradas Almacen

INSERT INTO `usuarios` (`id_usuario`, `nombre`, `ape_pat`, `ape_mat`, `nombre_usuario`, `password`, `cat_usuario`) VALUES
(1, 'Alejandra', 'Salazar', 'Fuentes', 'AdministradoraAle', '15323', 'Administrador Principal'),
(2,'Andres','Estrada','Rosales','Andresuser','1234','Administrador Facturacion'),
(3,'Ana','Estrada','Tamarindo','Ana','123','Administrador Cierre');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asignaciones`
--
ALTER TABLE `asignaciones`
  ADD CONSTRAINT `fk_clave_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`),
  ADD CONSTRAINT `fk_datos_fact` FOREIGN KEY (`id`) REFERENCES `facturacion` (`id`);

--
-- Filtros para la tabla `historial_asignaciones`
--
ALTER TABLE `historial_asignaciones`
  ADD CONSTRAINT `fk_clave_asignacion` FOREIGN KEY (`id_asignacion`) REFERENCES `asignaciones` (`id_asignacion`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
