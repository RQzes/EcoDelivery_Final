DROP DATABASE IF EXISTS ecodelivery;
CREATE DATABASE ecodelivery;
USE ecodelivery;

CREATE TABLE usuario (
    codigo INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    clave VARCHAR(100) NOT NULL,
    rol VARCHAR(30) NOT NULL
);

CREATE TABLE cliente (
    codigo INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(150)
);

CREATE TABLE vehiculo (
    codigo INT PRIMARY KEY,
    tipo VARCHAR(100) NOT NULL,
    capacidad DOUBLE NOT NULL,
    velocidad DOUBLE NOT NULL,
    estado VARCHAR(30) NOT NULL
);

CREATE TABLE pedido (
    codigo INT PRIMARY KEY,
    descripcion VARCHAR(200) NOT NULL,
    distancia DOUBLE NOT NULL,
    codigo_cliente INT NOT NULL,
    estado VARCHAR(30) NOT NULL,
    FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo)
);

CREATE TABLE entrega (
    codigo INT PRIMARY KEY,
    fecha VARCHAR(30) NOT NULL,
    tiempo DOUBLE NOT NULL,
    co2 DOUBLE NOT NULL,
    codigo_vehiculo INT NOT NULL,
    codigo_pedido INT NOT NULL,
    estado VARCHAR(30) NOT NULL,
    FOREIGN KEY (codigo_vehiculo) REFERENCES vehiculo(codigo),
    FOREIGN KEY (codigo_pedido) REFERENCES pedido(codigo)
);

CREATE TABLE factor_vehiculo (
    codigo INT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(100) NOT NULL UNIQUE,
    factor_co2 DOUBLE NOT NULL
);

INSERT INTO usuario(nombre, usuario, clave, rol)
VALUES ('Administrador', 'admin', '12345', 'ADMIN');

INSERT INTO factor_vehiculo(tipo, factor_co2)
VALUES
('Bicicleta', 0.00),
('Scooter', 0.02),
('Moto eléctrica', 0.04),
('Triciclo', 0.03),
('Otro', 0.08);

DROP PROCEDURE IF EXISTS sp_procesar_entrega;

DELIMITER //

CREATE PROCEDURE sp_procesar_entrega(
    IN p_codigo_entrega INT,
    IN p_fecha VARCHAR(30),
    IN p_codigo_pedido INT,
    IN p_codigo_vehiculo INT,
    OUT p_ok INT,
    OUT p_mensaje VARCHAR(255)
)
BEGIN
    DECLARE v_existe INT DEFAULT 0;
    DECLARE v_distancia DOUBLE DEFAULT 0;
    DECLARE v_estado_pedido VARCHAR(30);
    DECLARE v_tipo VARCHAR(100);
    DECLARE v_velocidad DOUBLE DEFAULT 0;
    DECLARE v_estado_vehiculo VARCHAR(30);
    DECLARE v_factor DOUBLE DEFAULT 0.08;
    DECLARE v_tiempo DOUBLE DEFAULT 0;
    DECLARE v_co2 DOUBLE DEFAULT 0;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_ok = 0;
        SET p_mensaje = 'Error en la transacción. No se realizó ningún cambio.';
    END;

    START TRANSACTION;

    SELECT COUNT(*) INTO v_existe
    FROM entrega
    WHERE codigo = p_codigo_entrega;

    IF v_existe > 0 THEN
        ROLLBACK;
        SET p_ok = 0;
        SET p_mensaje = 'Ya existe una entrega con ese código.';
    ELSE
        SELECT COUNT(*) INTO v_existe
        FROM pedido
        WHERE codigo = p_codigo_pedido;

        IF v_existe = 0 THEN
            ROLLBACK;
            SET p_ok = 0;
            SET p_mensaje = 'No existe el pedido indicado.';
        ELSE
            SELECT COUNT(*) INTO v_existe
            FROM vehiculo
            WHERE codigo = p_codigo_vehiculo;

            IF v_existe = 0 THEN
                ROLLBACK;
                SET p_ok = 0;
                SET p_mensaje = 'No existe el vehículo indicado.';
            ELSE
                SELECT distancia, estado
                INTO v_distancia, v_estado_pedido
                FROM pedido
                WHERE codigo = p_codigo_pedido
                FOR UPDATE;

                SELECT tipo, velocidad, estado
                INTO v_tipo, v_velocidad, v_estado_vehiculo
                FROM vehiculo
                WHERE codigo = p_codigo_vehiculo
                FOR UPDATE;

                IF UPPER(v_estado_pedido) = 'EN ENTREGA' OR UPPER(v_estado_pedido) = 'ENTREGADO' THEN
                    ROLLBACK;
                    SET p_ok = 0;
                    SET p_mensaje = 'El pedido ya está en proceso o entregado.';

                ELSEIF UPPER(v_estado_vehiculo) <> 'DISPONIBLE' THEN
                    ROLLBACK;
                    SET p_ok = 0;
                    SET p_mensaje = 'El vehículo no está disponible.';

                ELSEIF v_velocidad <= 0 THEN
                    ROLLBACK;
                    SET p_ok = 0;
                    SET p_mensaje = 'La velocidad del vehículo debe ser mayor a 0.';

                ELSE
                    SELECT COALESCE(
                        (
                            SELECT factor_co2
                            FROM factor_vehiculo
                            WHERE LOWER(tipo) = LOWER(v_tipo)
                            LIMIT 1
                        ),
                        (
                            SELECT factor_co2
                            FROM factor_vehiculo
                            WHERE LOWER(tipo) = 'otro'
                            LIMIT 1
                        ),
                        0.08
                    )
                    INTO v_factor;

                    SET v_tiempo = ROUND((v_distancia / v_velocidad) * 60, 2);

                    SET v_co2 = ROUND((v_distancia * 0.21) - (v_distancia * v_factor), 2);

                    IF v_co2 < 0 THEN
                        SET v_co2 = 0;
                    END IF;

                    INSERT INTO entrega(
                        codigo,
                        fecha,
                        tiempo,
                        co2,
                        codigo_vehiculo,
                        codigo_pedido,
                        estado
                    )
                    VALUES (
                        p_codigo_entrega,
                        p_fecha,
                        v_tiempo,
                        v_co2,
                        p_codigo_vehiculo,
                        p_codigo_pedido,
                        'EN ENTREGA'
                    );

                    UPDATE pedido
                    SET estado = 'EN ENTREGA'
                    WHERE codigo = p_codigo_pedido;

                    UPDATE vehiculo
                    SET estado = 'OCUPADO'
                    WHERE codigo = p_codigo_vehiculo;

                    COMMIT;

                    SET p_ok = 1;
                    SET p_mensaje = CONCAT(
                        'Entrega procesada correctamente. Tiempo: ',
                        v_tiempo,
                        ' minutos. CO2 evitado: ',
                        v_co2,
                        ' kg.'
                    );
                END IF;
            END IF;
        END IF;
    END IF;
END //

DELIMITER ;