/* Elimna la Base de Datos , si existe */
DROP DATABASE IF EXISTS nikaGES;
-- Crea la base de datos
CREATE DATABASE nikaGES;
-- Conecta con la base de datos
USE nikaGES;

CREATE TABLE proveedor
(
id int UNSIGNED primary key auto_increment,
nombre_pro varchar (200),
direccion varchar (200),
cif varchar (15) UNIQUE,
forma_pago  varchar (10)
)ENGINE= InnoDb;

CREATE TABLE categoria
(
id int UNSIGNED primary key auto_increment,
nombre_cat varchar (200),
descripcion varchar (200)
)ENGINE= InnoDb;
	
CREATE TABLE factura
(
id int UNSIGNED primary key auto_increment,
nombre_fac varchar(100),
numero varchar(200),
serie varchar(25),
lugar varchar(200),
descripcion varchar(200),
fecha_emision date
	)ENGINE= InnoDb;  
		
CREATE TABLE cliente
(
id int UNSIGNED primary key auto_increment,
	nombre_cli varchar (200),
	apellidos varchar (200),
	dni  varchar (10) UNIQUE,
	direccion varchar (200),
	telefono varchar (12)
)ENGINE= InnoDb;
	
CREATE TABLE almacen
(
id int UNSIGNED primary key auto_increment,
	nombre_alm varchar (200)
)ENGINE= InnoDb;

CREATE TABLE tipo_impuesto
(
id int UNSIGNED primary key auto_increment,
	nombre_imp varchar (200),
	valor float,
	descripcion varchar (200)
)ENGINE= InnoDb;


CREATE TABLE pedido
(id int UNSIGNED primary key auto_increment,
	numero varchar (200),
	forma_pago varchar (25),
	fecha_pedido date,
	id_cliente INT unsigned,
	INDEX(id_cliente),
	FOREIGN KEY (id_cliente) 
	REFERENCES cliente(id)
	ON DELETE CASCADE,
	id_factura INT unsigned,
	INDEX(id_factura),
	FOREIGN KEY (id_factura) 
	REFERENCES factura(id)
	ON DELETE CASCADE
	)ENGINE= InnoDb;
	
CREATE TABLE articulo
(id int UNSIGNED primary key auto_increment,
nombre_art varchar (200),
precio float default 0,
descripcion varchar (200),
stock int default 0,
id_proveedor INT unsigned,
	INDEX(id_proveedor),
	FOREIGN KEY (id_proveedor) 
	REFERENCES proveedor(id),

	
	id_categoria INT unsigned,
	INDEX(id_categoria),
	FOREIGN KEY (id_categoria) 
	REFERENCES categoria(id),
	
	id_almacen INT unsigned,
	INDEX(id_almacen),
	FOREIGN KEY (id_almacen) 
	REFERENCES almacen(id)
	ON DELETE CASCADE,
	
	id_tipo_impuesto INT unsigned,
	INDEX(id_tipo_impuesto),
	FOREIGN KEY (id_tipo_impuesto)
	REFERENCES tipo_impuesto(id)
	)ENGINE= InnoDb;
	

CREATE TABLE linea_detalle
(
id int(11) NOT NULL AUTO_INCREMENT,
cantidad int default 0,
precio float default 0,
id_articulo int(10) unsigned NOT NULL DEFAULT '0',
  id_pedido int(10) unsigned NOT NULL DEFAULT '0',
  	FOREIGN KEY (id_pedido) 
	REFERENCES pedido(id)
	ON DELETE CASCADE,
  PRIMARY KEY (id),
  KEY id_articulo (id_articulo),
  KEY id_pedido (id_pedido)
	)ENGINE= InnoDb;
	
	-- Estructura de tabla para la tabla `empresa`

CREATE TABLE IF NOT EXISTS `empresa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nif` varchar(20) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `nombre` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`id`, `nif`, `direccion`, `telefono`, `nombre`) VALUES
(1, '32265165165A', 'C/Rosales del Pilar.', '976280749', 'El Jardín del edén');

--
-- Volcado de datos para la tabla `tipo_impuesto`
--

INSERT INTO `tipo_impuesto` (`id`, `nombre_imp`, `valor`, `descripcion`) VALUES
(1, '21%', 0.21, 'Tipo general'),
(2, '10%', 0.1, 'Tipo reducido'),
(3, '4%', 0.04, 'Tipo superreducido');

-- Volcado de datos para la tabla `almacen`
--

INSERT INTO `almacen` (`id`, `nombre_alm`) VALUES
(1, 'Almacen 1'),
(2, 'Almacen 2'),
(3, 'Almacen 3'),
(4, 'Almacen 4'),
(5, 'Almacen 5'),
(6, 'Almacen 6'),
(7, 'Almacen 7'),
(8, 'Almacen 8'),
(9, 'Almacen 9'),
(10, 'Almacen 10'),
(11, 'Almacen 11'),
(12, 'Almacen 12'),
(13, 'Almacen 13'),
(14, 'Almacen 14'),
(15, 'Almacen 15'),
(16, 'Almacen 16'),
(17, 'Almacen 17'),
(18, 'Almacen 18'),
(19, 'Almacen 19'),
(20, 'Almacen 20'),
(21, 'Almacen 21'),
(22, 'Almacen 22'),
(23, 'Almacen 23'),
(24, 'Almacen 24'),
(25, 'Almacen 25'),
(26, 'Almacen 26'),
(27, 'Almacen 27'),
(28, 'Almacen 28'),
(29, 'Almacen 29'),
(30, 'Almacen 30');

-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `nombre_cat`, `descripcion`) VALUES
(1, 'Orquideas', 'Es una Orquidea.'),
(2, 'Ramos de flores', 'Es un ramo.'),
(3, 'Rosas', 'Es una rosa.'),
(4, 'Centro', 'Es un centro de flores.'),
(5, 'Plantas', 'Es una planta.'),
(6, 'Cestas', 'Es una cesta.'),
(7, 'Jarrones', 'Es un jarrón.'),
(8, 'Centros funerarios', 'Es un centro funerario.'),
(9, 'Coronas funerarias', 'Es una corona funeraria.');

-- Volcado de datos para la tabla `cliente`
INSERT INTO `cliente` (`id`, `nombre_cli`, `apellidos`, `dni`, `direccion`, `telefono`) VALUES
(1, 'Monica', 'Saenz Carrero', '7845154H', 'Rio esera 9', '6380569924'),
(2, 'Pedro', 'Martin Garcia', '34432154H', 'Crespo de aguero 13', '6880129869'),
(3, 'Benjamin', 'Garcia De La Rosa', '544234B', 'Crucero baleares 5', '6101256936'),
(4, 'Juan', 'Mendoza Sanchez', '5456234B', 'Paseo cuellar 8', '8558143760'),
(5, 'Luis', 'Hernandez Diaz', '3443543M', 'Paseo Damas 14', '6108342685'),
(6, 'Noelia', 'Fernandez Castillo', '3443783P', 'Fernando el catolico 18', '6619259359'),
(7, 'Adrian', 'Castillo Perez', '3999783J', 'Plaza de las flores 7', '6150293532'),
(8, 'David', 'Perez Ortiz', '3907830U', 'Rio cinca 4', '6150293534'),
(9, 'Marcos', 'Palacios Jerez', '3566789I', 'Paseo Echegaray 9', '6156793534'),
(10, 'Marco', 'Romero Barrios', '7868563S', 'Gran vía 19', '6456793534'),
(11, 'Santiago', 'Rojas Aranda', '7428563S', 'Paseo Independencia 2', '6456796634'),
(12, 'Miguel', 'Silva Villalba', '2427863N', 'Gomez Laguna 15', '6256796637'),
(13, 'Victor', 'Pons Perez', '2427263V', 'Federico Garcia Lorca 9', '976254896'),
(14, 'Laura', 'Puig Gonzalez', '2467983E', 'Jardines de la concordia 12', '976365214'),
(15, 'Oscar', 'Munoz Moreno', '2227983T', 'Jaime Ballesteros 23', '976859632'),
(16, 'Manolo', 'Duran Fernandez', '1117983D', 'Isla de Hierro 14', '976215236'),
(17, 'Susana', 'Montoya Pose', '6667983H', 'Laguna del Gallo', '976569870'),
(18, 'Victoria', 'Beckham Smith', '6634983H', 'Azeroth 25', '976569871'),
(19, 'Andrea', 'Montecarlo Laporta', '4567983H', 'Vertormenta 17', '976569872'),
(20, 'Julio', 'Moros Ruiz', '7767983H', 'Dalaran 1', '976569873'),
(21, 'Antonio', 'Escobar Perez', '8967983H', 'Darnassus 19', '976569875'),
(22, 'Julia', 'Hidalgo Caro', '4367983H', 'Alterac 86', '976569876'),
(23, 'Maria', 'Pino Leal', '7867983H', 'Kalimdor 15', '976569877'),
(24, 'Mario', 'Casa Ruiz', '9867983H', 'Lordaeron 12', '976569878'),
(25, 'Hector', 'Gil Moros', '0867983H', 'Northrend 7', '976569870'),
(26, 'Samuel', 'Iglesias Pardo', '0167983H', 'Darkshore 12', '976569880'),
(27, 'Veronica', 'Sanchez Vicario', '2167983H', 'Rio gallego 8', '976569881'),
(28, 'Eustaquio', 'Mormeneo Pascual', '1267983H', 'League of legends 9', '976569882'),
(29, 'Stefan', 'Salvatore Diez', '6767983H', 'Piratas 8', '976569883'),
(30, 'Jorge', 'Lopez Caballero', '6657986H', 'El olvido 23', '976200369');

-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`id`, `nombre_pro`, `direccion`, `cif`, `forma_pago`) VALUES
(1, 'Flores Polen', 'Rosaleda 13, 50003', '51245G', 'Efectivo'),
(2, 'Plantas mil', 'Lugar escondido,8', '656755E', 'Tarjeta'),
(3, 'Las Camelias', 'Calle de Pizarro, 46', '58655Q', 'Efectivo'),
(4, 'Regalar flores', 'Calle Alcalde García Filgueira, 11', '621365U', 'Tarjeta'),
(5, 'Floristeria Castilla', 'Carretera Alcañizes, s/n , 49562', '543255Z', 'Efectivo'),
(6, 'Servicios Funerarios Laureano', 'Calle Costa de les Germanetes, 10', '65165165M', 'Tarjeta'),
(7, 'Floristeria La Corona', 'Calle del Carril, 113 , 04600 ', '1145V', 'Efectivo'),
(8, 'Los Nopales', 'Calle de Don Sancho, 14', '65665R', 'Tarjeta'),
(9, 'Angel Floristas', 'Calle de Toledo, 61 , 45280', '5735G', 'Efectivo'),
(10, 'Flors Natalia', 'Carretera Caboalles, , km 6,5 , 24007', '614665Y', 'Tarjeta'),
(11, 'Viveros Las Gardenias', 'Calle de Sant Pere, 31, - Bajos , 43120 ', '51565G', 'Efectivo'),
(12, 'Floristeria Corpas', 'Calle Gaona, 9, - (Zona Centro) , 02001', '67865P', 'Tarjeta'),
(13, 'Flores Torrecillas', 'Calle Juan XXIII, 24', '51235G', 'Efectivo'),
(14, 'Floristeria Madreselva', 'Rua do Castineiro, 4, 15702', '121564165M', 'Tarjeta'),
(15, 'Laraflor', 'Av. Navarra, 180 , 50011 , ZARAGOZA', '653464165O', 'Efectivo'),
(16, 'Flores La Mimosa', 'Marqués de la Cadena, 39 , 50014 , ZARAGOZA', '651556165P', 'Tarjeta'),
(17, 'Flores Pilar Ruiz', 'Manuel Lasala, 36 , 50006 , ZARAGOZA  ', '651564165Q', 'Efectivo'),
(18, 'Flores Aznar', 'Azoque, 1 , 50004 , ZARAGOZA  ', '341564165R', 'Efectivo'),
(19, 'Viveros Joven Garden Center', 'Ctra. Aeropuerto, KM 300 , 50011 , ZARAGOZA', '671564165S', 'Tarjeta'),
(20, 'Bensiflor', 'Molino, 1 , 50180 , UTEBO (ZARAGOZA)', '691564165T', 'Efectivo'),
(21, 'Tu Dia Sc', 'Av. Jota, 44 , 50014 , ZARAGOZA', '611564165U', 'Efectivo'),
(22, 'Amaranto Floristeria', 'Mariano Pano y Ruata, 3 , 50015 , ZARAGOZA', '631564165V', 'Tarjeta'),
(23, 'Bienflor Sll.', 'Vicente Berdusán, 8 , 50010 , ZARAGOZA', '601564165W', 'Efectivo'),
(24, 'Siete Flores', 'Pl. Sitios, S/N , 50001 , ZARAGOZA', '351564165X', 'Tarjeta'),
(25, 'Kaprichos Arte Floral', 'Huesca, 2 , 50100 , LA ALMUNIA DE DOÑA GODINA (ZARAGOZA)', '901564165Y', 'Efectivo'),
(26, 'Flores Mary Carmen', 'Delicias, 6 , 50017 , ZARAGOZA', '671564165Z', 'Tarjeta'),
(27, 'Flores Y Plantas Sayde', 'Av. San Juan Bosco, 18 , 50009 , ZARAGOZA  ', '681564165A', 'Efectivo'),
(28, 'Casa De Las Flores', 'Joaquín Sorolla, 3 , 50007 , ZARAGOZA', '901564165B', 'Tarjeta'),
(29, 'Evaflor', 'Av. Francisco de Goya, 19 , 50006 , ZARAGOZA', '911564165C', 'Efectivo'),
(30, 'Viveros San Rafael', 'Plaza Santa Quiteria, 4 , 13600 , ALCAZAR DE SAN JUAN (CIUDAD REAL)', '3465165L', 'Tarjeta');

-- Volcado de datos para la tabla `articulo`
--
INSERT INTO `nikages`.`articulo` (`id`, `nombre_art`, `precio`, `descripcion`, `stock`, `id_proveedor`, `id_categoria`, `id_almacen`, `id_tipo_impuesto`) VALUES 
(1, 'Liliums naranjas', '22', 'Liliums', '90', '1', '2', '2', '1'),
(2, 'Detalle de anturios', '22', 'anturios', '100', '2', '2', '2', '1'),
(3, 'Ramo promo 1', '25', 'Ramo', '23', '3', '2', '2', '1'),
(4, 'Spatifilium', '24', 'Spatifilium', '323', '4', '2', '2', '1'),
(5, 'Guzmania en cristal con geles', '28', 'Guzmania', '45', '5', '5', '5', '1'),
(6, 'Briesia tifany con macetero', '29', 'Briesia', '23', '6', '5', '5', '1'),
(7, 'Sáhara', '34', 'Sáhara', '45', '7', '5', '5', '1'),
(8, 'Orquidea phalaenopsis', '34', 'Orquidea', '6454', '8', '5', '5', '1'),
(9, 'Calathea triostar', '36', 'Calathea', '2', '9', '5', '5', '1'),
(10, 'Anturio', '36', 'Anturio', '66', '10', '5', '5', '1'),
(11, 'Centro de difunto lineal', '49', 'difunto', '543', '11', '8', '8', '1'),
(12, 'Centro de difunto edén', '70', 'difunto', '342', '12', '8', '8', '1'),
(13, 'Centro de difunto rosa y blanco', '80', 'difunto', '664', '13', '8', '8', '1'),
(14, 'Centro exaltación', '88', 'difunto', '100', '14', '8', '8', '1'),
(15, 'Almohadón de rosas', '99.90', 'difunto', '0', '15', '9', '9', '1'),
(16, 'Almohadón amor', '89', 'difunto', '100', '16', '9', '9', '1'),
(17, 'Almohadón primavera', '70', 'difunto', '1000', '17', '9', '9', '1'),
(18, 'Ramo de difunto Eva', '49.90', 'difunto', '30', '18', '9', '9', '1'),
(19, 'Rosas rojas', '21', 'Rosas', '0', '19', '3', '3', '1'),
(20, 'Rosas estabilizadas', '21', 'Rosas', '10', '20', '3', '3', '1'),
(21, 'Botella romántica natural', '29.90', 'Rosas', '30', '21', '3', '3', '1'),
(22, 'Dulzura', '42', 'Rosas', '0', '22', '3', '3', '1'),
(23, 'Ramo de gerberas y margaritas', '26', 'Ramo', '70', '23', '2', '2', '1'),
(24, 'Natural', '29', 'Ramo', '80', '24', '2', '2', '1'),
(25, 'Tropical', '33', 'Ramo', '90', '1', '2', '2', '1'),
(26, 'Silvestre', '33', 'Ramo', '20', '13', '2', '2', '1'),
(27, 'Fiesta', '45', 'Centro', '3', '11', '4', '4', '1'),
(28, 'Cesta silvestre', '45', 'Cesta', '56', '1', '6', '6', '1'),
(29, 'Cesta variada con rosas', '54', 'Cesta', '543', '5', '6', '6', '1'),
(30, 'Fantasia', '48.99', 'Centro', '35', '1', '4', '4', '1');

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`id`, `nombre_fac`, `numero`, `serie`, `lugar`, `descripcion`, `fecha_emision`) VALUES
(1, 'fact30', '5', 'factura-5-5-1-6-2014', 'El olvido 23', 'Cliente:6657986H', '2014-06-01'),
(2, 'fact6', '6', 'factura-6-6-1-6-2014', 'Fernando el catolico 18', 'Cliente:3443783P', '2014-06-01'),
(3, 'fact28', '7', 'factura-7-7-1-6-2014', 'League of legends 9', 'Cliente:1267983H', '2014-06-01'),
(4, 'fact3', '8', 'factura-8-8-1-6-2014', 'Crucero baleares 5', 'Cliente:544234B', '2014-06-01'),
(5, 'fact2', '1', 'factura-1-1-1-6-2014', 'Crespo de aguero 13', 'Cliente:34432154H', '2014-06-01');


--
-- Volcado de datos para la tabla `pedido`
--
INSERT INTO `pedido` (`id`, `numero`, `forma_pago`, `fecha_pedido`, `id_cliente`, `id_factura`) VALUES
(1, '1', 'Tarjeta de Credito', '2014-06-01', 2, 5),
(2, '2', 'Efectivo', '2014-06-01', 5, NULL),
(3, '3', 'Tarjeta de Credito', '2014-06-01', 3, NULL),
(4, '4', 'Efectivo', '2014-05-14', 23, NULL),
(5, '5', 'Tarjeta de Credito', '2014-02-13', 30, 1),
(6, '6', 'Tarjeta de Credito', '2014-02-19', 6, 2),
(7, '7', 'Tarjeta de Credito', '2014-01-15', 28, 3),
(8, '8', 'Tarjeta de Credito', '2013-02-22', 3, 4),
(9, '9', 'Tarjeta de Credito', '2014-06-01', 13, NULL);
--
-- Volcado de datos para la tabla `linea_detalle`
--

INSERT INTO `linea_detalle` (`id`, `cantidad`, `precio`, `id_articulo`, `id_pedido`) VALUES
(1, 2, 44, 2, 1),
(2, 4, 100, 3, 1),
(3, 1, 22, 1, 1),
(4, 1, 25, 3, 2),
(5, 6, 293.94, 30, 3),
(6, 4, 100, 3, 3),
(7, 4, 216, 29, 3),
(8, 5, 145, 6, 3),
(9, 1, 24, 4, 4),
(10, 7, 342.93, 30, 5),
(11, 1, 45, 27, 5),
(12, 5, 125, 3, 6),
(13, 4, 132, 26, 7),
(14, 1, 33, 26, 8),
(15, 1, 45, 27, 8),
(16, 1, 22, 2, 8),
(17, 1, 24, 4, 8),
(18, 1, 29, 6, 8),
(19, 2, 42, 19, 9),
(20, 1, 24, 4, 9),
(21, 2, 42, 19, 9);
