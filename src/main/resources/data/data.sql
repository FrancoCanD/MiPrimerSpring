DROP DATABASE IF EXISTS escuela_kenpo;
CREATE DATABASE escuela_kenpo;
USE escuela_kenpo;


-- 1. Tabla base
CREATE TABLE personas (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nombres VARCHAR(100) NOT NULL,
                          apellido1 VARCHAR(250) NOT NULL,
                          apellido2 VARCHAR(250) NULL,
                          fecha_nac DATE NOT NULL,
                          rut VARCHAR(12) NOT NULL UNIQUE
);

-- 2. Tabla base de grados
CREATE TABLE grados (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(50) NOT NULL,
                        descripcion VARCHAR(255),
                        kyu_dan VARCHAR(50) NOT NULL
);

-- 3. Tabla estudiantes (depende de personas y grados)
CREATE TABLE estudiantes (
                             persona_id INT PRIMARY KEY,
                             grado_id INT NOT NULL,
                             fecha_ascenso DATE NOT NULL,
                             activo BOOLEAN DEFAULT TRUE,
                             FOREIGN KEY (persona_id) REFERENCES personas(id) ON DELETE CASCADE,
                             FOREIGN KEY (grado_id) REFERENCES grados(id)
);

-- 4. Tabla clases (independiente)
CREATE TABLE clases (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        tipo_clase VARCHAR(100) NOT NULL,
                        descripcion VARCHAR(300) NOT NULL,
                        estado BOOLEAN NOT NULL
);

-- 5. REORDENADO: Se crea primero INSTRUCTORES para que exista antes de ser referenciada
CREATE TABLE instructores (
                              persona_id INT PRIMARY KEY,
                              grado_id INT NOT NULL,
                              especialidad VARCHAR(100) NOT NULL,
                              fecha_inicio DATE NOT NULL,
                              activo BOOLEAN DEFAULT TRUE,
                              anos_experiencia INT NULL,
                              FOREIGN KEY (persona_id) REFERENCES personas(id) ON DELETE CASCADE,
                              FOREIGN KEY (grado_id) REFERENCES grados(id)
);

-- 6. REORDENADO: Ahora ASISTENCIAS puede apuntar a estudiantes e instructores sin errores
CREATE TABLE asistencias (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             registro DATETIME NOT NULL,
                             fecha_clase DATE NOT NULL,
                             estudiante_id INT NOT NULL,
                             instructor_id INT NOT NULL,
                             clase_id      INT NOT NULL,
                             FOREIGN KEY (estudiante_id) REFERENCES estudiantes(persona_id),
                             FOREIGN KEY (instructor_id) REFERENCES instructores(persona_id),
                             FOREIGN KEY (clase_id)      REFERENCES clases(id)
);

-- ==========================================
-- INSERCIÓN DE DATA DE PRUEBA SANEADA
-- ==========================================
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
                                                              ('María', 'Soto', '2007-01-01', '22222222-2'),
                                                              ('Carlos', 'Muñoz', '1985-03-15', '15555555-5'),
                                                              ('Andrea', 'González', '1990-07-22', '16666666-6'),
                                                              ('Roberto', 'Silva', '1982-11-10', '17777777-7');

INSERT INTO estudiantes (persona_id, grado_id, fecha_ascenso, activo) VALUES
                                                                          (1, 1, '2024-03-10', true),
                                                                          (2, 2, '2025-05-15', true);

INSERT INTO clases (tipo_clase, descripcion, estado) VALUES
                                                         ('Técnica',  'Golpe de puño frontal',    true),
                                                         ('Kata',     'Kata Pinan Shodan',         true),
                                                         ('Sparring', 'Combate controlado 2 min',  true);

INSERT INTO instructores (persona_id, grado_id, especialidad, fecha_inicio, activo, anos_experiencia) VALUES
                                                                                                          (3, 7, 'Instructor General', '2015-01-10', true, 11),
                                                                                                          (4, 7, 'Kata', '2018-06-15', true, 8),
                                                                                                          (5, 6, 'Kumite', '2020-03-20', true, 6);

-- Inserción de asistencia utilizando los IDs reales que ya existen en las tablas anteriores
INSERT INTO asistencias (registro, fecha_clase, estudiante_id, instructor_id, clase_id) VALUES
    ('2026-05-05 20:30:00', '2026-05-05', 1, 3, 1);
