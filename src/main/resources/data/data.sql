DROP DATABASE IF EXISTS escuela_kenpo;
CREATE DATABASE escuela_kenpo;
USE escuela_kenpo;

CREATE TABLE personas (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nombres VARCHAR(100) NOT NULL,
                          apellido1 VARCHAR(250)  NOT NULL,
                          apellido2 VARCHAR(250) NULL,
                          fecha_nac DATE NOT NULL,
                          rut VARCHAR(12) NOT NULL UNIQUE
);

CREATE TABLE grados (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(50) NOT NULL,
                        descripcion VARCHAR(255),
                        kyu_dan VARCHAR(50) NOT NULL
);

CREATE TABLE estudiantes (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             persona_id INT NOT NULL,
                             grado_id INT NOT NULL,
                             fecha_ascenso DATE NOT NULL,
                             activo BOOLEAN DEFAULT TRUE,

                             FOREIGN KEY (persona_id) REFERENCES personas(id),
                             FOREIGN KEY (grado_id) REFERENCES grados(id)
);

CREATE TABLE clases (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        tipo_clase VARCHAR(100) NOT NULL,
                        descripcion VARCHAR(300) NOT NULL,
                        estado BOOLEAN NOT NULL
);

CREATE TABLE asistencias (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             registro DATETIME NOT NULL,
                             fecha_clase DATE NOT NULL,
                             estudiante_id INT NOT NULL,
                             clase_id      INT NOT NULL,
                             FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id),
                             FOREIGN KEY (clase_id)      REFERENCES clases(id)
);

-- Crear tabla instructores

CREATE TABLE instructores (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              persona_id INT NOT NULL,
                              grado_id INT NOT NULL,
                              especialidad VARCHAR(100) NOT NULL,
                              fecha_inicio DATE NOT NULL,
                              activo BOOLEAN DEFAULT TRUE,
                              anos_experiencia INT NULL,

                              FOREIGN KEY (persona_id) REFERENCES personas(id),
                              FOREIGN KEY (grado_id) REFERENCES grados(id)
);

INSERT INTO grados (nombre, descripcion, kyu_dan) VALUES
                                                      ('Blanco', 'Grado inicial del estudiante', '10° Kyu'),
                                                      ('Amarillo', 'Primer avance técnico básico', '9° Kyu'),
                                                      ('Naranja', 'Nivel inicial-intermedio', '8° Kyu'),
                                                      ('Verde', 'Nivel intermedio', '7° Kyu'),
                                                      ('Azul', 'Nivel intermedio-avanzado', '6° Kyu'),
                                                      ('Marrón', 'Preparación para cinturón negro', '1° Kyu'),
                                                      ('Negro', 'Primer grado avanzado', '1° Dan');

INSERT INTO personas (nombres, apellido1, fecha_nac, rut) VALUES
                                                              ('Juan', 'Pérez', '2009-01-01', '11111111-1'),
                                                              ('María', 'Soto', '2007-01-01', '22222222-2');


INSERT INTO estudiantes (persona_id, grado_id, fecha_ascenso, activo) VALUES
                                                                          (1, 1, '2024-03-10', true),
                                                                          (2, 2, '2025-05-15', true);

INSERT INTO clases (tipo_clase, descripcion, estado) VALUES
                                                         ('Técnica',  'Golpe de puño frontal',    true),
                                                         ('Kata',     'Kata Pinan Shodan',         true),
                                                         ('Sparring', 'Combate controlado 2 min',  true);

INSERT INTO asistencias ( registro, fecha_clase, estudiante_id, clase_id) VALUES
    ('2026-05-05 20:30:00','2026-05-05', 1, 1);


-- Insertar datos de prueba para instructores
-- Primero insertamos las personas (instructores)
INSERT INTO personas (nombres, apellido1, apellido2, fecha_nac, rut) VALUES
                                                                         ('Carlos', 'Muñoz', 'Valdés', '1985-03-15', '15555555-5'),
                                                                         ('Andrea', 'González', 'Rojas', '1990-07-22', '16666666-6'),
                                                                         ('Roberto', 'Silva', 'Méndez', '1982-11-10', '17777777-7');

-- Luego insertamos los instructores asociados a las personas
-- Asumiendo que los IDs de personas son 3, 4, 5 (después de Juan y María)
-- Y usamos grados 6 (Marrón) y 7 (Negro)
INSERT INTO instructores (persona_id, grado_id, especialidad, fecha_inicio, activo, anos_experiencia) VALUES
                                                                                                          (3, 7, 'Instructor General', '2015-01-10', true, 11),
                                                                                                          (4, 7, 'Kata', '2018-06-15', true, 8),
                                                                                                          (5, 6, 'Kumite', '2020-03-20', true, 6);