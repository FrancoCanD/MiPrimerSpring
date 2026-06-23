# 🥋 Escuela Kenpo — Sistema de Gestión

Aplicación web desarrollada con **Spring Boot** para gestionar la información de una escuela de Kenpo. Permite administrar estudiantes, instructores, clases, grados y asistencias tanto desde una interfaz web con Thymeleaf como a través de una API REST documentada con Swagger/OpenAPI.

---

## 🛠️ Tecnologías Utilizadas

| Categoría | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Framework | Spring Boot 4.0.6 |
| Persistencia | Spring Data JPA + Hibernate |
| Motor de BD | MySQL |
| Motor de plantillas | Thymeleaf |
| Servicios web | Spring MVC + Spring WebServices |
| Validación | Spring Boot Validation (Jakarta Bean Validation) |
| Reducción de boilerplate | Lombok |
| Documentación API | SpringDoc OpenAPI / Swagger UI |
| Build tool | Maven (Maven Wrapper incluido) |
| Empaquetado | WAR (desplegable en Tomcat externo) |
| Dev tools | Spring Boot DevTools |

---

## 📦 Estructura del Proyecto

```
MiPrimerSpringboot/
├── src/
│   └── main/
│       ├── java/cl/kibernum/miprimerspringboot/
│       │   ├── bl/entity/          # Entidades JPA (Persona, Estudiante, Instructor, Grado, Clase, Asistencia)
│       │   ├── config/             # Configuración de Swagger/OpenAPI
│       │   ├── controller/         # Controladores MVC (vistas Thymeleaf)
│       │   ├── dto/                # DTOs de request y response para la API REST
│       │   │   ├── request/
│       │   │   └── response/
│       │   ├── mapper/             # Mapeadores entre entidades y DTOs
│       │   ├── repository/         # Repositorios JPA (Spring Data)
│       │   ├── restcontroller/     # Controladores REST (API JSON)
│       │   └── service/            # Interfaces de servicio e implementaciones
│       │       └── serviceimpl/
│       └── resources/
│           ├── data/data.sql       # Script SQL de creación e inicialización de BD
│           ├── static/             # Recursos estáticos (CSS, imágenes)
│           └── templates/          # Plantillas Thymeleaf
│               ├── fragments/      # Header y footer reutilizables
│               ├── asistencias/
│               ├── clases/
│               ├── estudiantes/
│               ├── grados/
│               ├── instructores/
│               └── index.html
└── pom.xml
```

---

## 🗄️ Modelo de Datos

El sistema usa herencia JPA (`JOINED` strategy) para modelar personas:

- **Persona** *(abstracta)* — id, nombres, apellido1, apellido2, fechaNac, rut (único)
  - **Estudiante** — grado, fechaAscenso, fechaInscripcion, activo
  - **Instructor** — grado, especialidad, fechaInicio, activo, anosExperiencia
- **Grado** — nombre, descripcion, kyuDan
- **Clase** — tipoClase, descripcion, estado
- **Asistencia** — registro (datetime), fechaClase, estudiante, instructor, clase

---

## ⚙️ Configuración y Ejecución

### Requisitos previos

- Java 21
- MySQL 8+
- Maven (o usar el wrapper incluido `./mvnw`)

### 1. Crear la base de datos

Ejecuta el script incluido en el proyecto:

```bash
mysql -u root -p < src/main/resources/data/data.sql
```

### 2. Configurar credenciales

Edita `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/escuela_kenpo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
```

### 3. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

O con Maven instalado:

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

---

## 🌐 Funcionalidades

### Interfaz Web (Thymeleaf)

| Módulo | Ruta | Funciones |
|---|---|---|
| Inicio / Dashboard | `/` | Estadísticas generales (totales de estudiantes, instructores, clases, asistencias) |
| Grados | `/grados` | Listar, crear, editar y eliminar grados (cinturones) |
| Clases | `/clases/listar` | Listar, crear, editar y eliminar tipos de clase |
| Estudiantes | `/estudiantes/listar` | Listar, crear, editar y eliminar estudiantes |
| — Perfil estudiante | `/estudiantes/perfil?rut=...` | Ver ficha completa del estudiante por RUT |
| — Asistencias estudiante | `/estudiantes/asistencias?rut=...` | Ver historial de asistencias por RUT |
| Instructores | `/instructores/listar` | Listar, crear, editar y eliminar instructores |
| Asistencias | `/asistencias/listar` | Listar, registrar, editar y eliminar asistencias |

### API REST (JSON)

Todos los endpoints REST siguen el patrón `/api/{recurso}` y soportan operaciones CRUD completas:

| Recurso | Endpoint base | Operaciones |
|---|---|---|
| Grados | `/api/grados` | GET, GET /{id}, POST, PUT /{id}, DELETE /{id} |
| Clases | `/api/clases` | GET, GET /{id}, POST, PUT /{id}, DELETE /{id} |
| Estudiantes | `/api/estudiantes` | GET, GET /{id}, POST, PUT /{id}, DELETE /{id} |
| Instructores | `/api/instructores` | GET, GET /{id}, POST, PUT /{id}, DELETE /{id} |
| Asistencias | `/api/asistencias` | GET, GET /{id}, POST, PUT /{id}, DELETE /{id} |

### Documentación interactiva (Swagger UI)

Accede a la documentación y prueba los endpoints directamente en:

```
http://localhost:8080/swagger-ui.html
```

---

## 📐 Arquitectura

El proyecto sigue una arquitectura en capas:

```
Controller / RestController
        ↓
    Service (interfaz + implementación)
        ↓
    Repository (Spring Data JPA)
        ↓
    Entity (JPA / Hibernate)
        ↓
    MySQL
```

Los datos que viajan entre capas hacia afuera usan **DTOs** (Data Transfer Objects) con sus respectivos **Mappers**, manteniendo las entidades desacopladas de la API.

---

## 📁 Archivos a ignorar en Git

Crea un archivo `.gitignore` con al menos:

```gitignore
# IntelliJ IDEA
.idea/
*.iml

# Maven
target/

# Credenciales
application.properties

# Sistema
.DS_Store
```

---

## 👨‍💻 Autor

Desarrollado como proyecto de aprendizaje en Bootcamp de Fullstack Java **Kibernum** — primer proyecto Spring Boot.
