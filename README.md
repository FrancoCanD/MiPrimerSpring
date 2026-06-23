# 🥋 Gestión de Escuelas - Dojo Management Web App

[![Java](https://shields.io)](https://oracle.com)
[![Spring Boot](https://shields.io)](https://spring.io)
[![Spring Security](https://shields.io)](https://spring.io)
[![MySQL](https://shields.io)](https://mysql.com)
[![Thymeleaf](https://shields.io)](https://thymeleaf.org)

Aplicación web Full Stack robusta diseñada para la digitalización y gestión integral de escuelas de artes marciales. 
Este proyecto ha sido desarrollado por un equipo de desarrolladores Full Stack Java nivel trainee, aplicando patrones de diseño empresariales, arquitectura limpia y control de acceso basado en roles (RBAC).

---

## 📺 Pitch Técnico y Demostración
Puedes ver nuestro video de presentación de 1:30 minutos donde explicamos la arquitectura y las funcionalidades clave aquí:  
👉 **[Enlace al video de YouTube / LinkedIn]**

---

## 🚀 Funcionalidades Clave

### 🧑‍💼 Módulo de Administración (Backoffice)
* **Gestión de Usuarios:** Alta, baja y modificación de estudiantes, instructores y personal administrativo.
* **Control de Configuración:** Creación dinámica de grados, planes de entrenamiento y cinturones del sistema.
* **Seguridad:** Gestión y asignación de usuarios y credenciales cifradas.

### 👨‍🏫 Panel del Instructor (Profesor)
* **Control de Asistencia:** Interfaz ágil para registrar la asistencia de los alumnos a las clases asignadas en tiempo real.
* **Monitoreo de Clases:** Visualización de clases asistidas por alumno específico e instructores de la misma. 

### 🥇 Progreso de Cinturón e Inscripción
* **Flujo de Promoción:** Automatización de la evolución de grados de los alumnos.
* **Historial Académico:** Registro visual del avance dinámico de cada estudiante desde su inscripción.

---

## 🛠️ Stack Tecnológico y Arquitectura

La aplicación implementa una arquitectura en capas basada en el patrón **MVC (Model-View-Controller)**, garantizando una separación clara de responsabilidades:

* **Backend:** Java 17+, Spring Boot, Spring Data JPA.
* **Seguridad:** Spring Security (Autenticación basada en sesiones y control de acceso por roles: `ROLE_ADMIN`, `ROLE_PROFESOR`, `ROLE_ALUMNO` con cifrado BCrypt).
* **Base de Datos:** MySQL 8.x, mapeo relacional gestionado mediante Hibernate.
* **Frontend:** Thymeleaf (Renderizado dinámico del lado del servidor), HTML5 y CSS3 adaptativo.

```text
📁 src/main/java/com/escuela/
├── 📁 config       # Configuración de Spring Security y Beans
├── 📁 controllers  # Controladores web (Mapeo de peticiones y vistas)
├── 📁 models       # Entidades del dominio (JPA/Hibernate)
├── 📁 repositories # Abstracción de persistencia de datos (Spring Data JPA)
└── 📁 services     # Lógica de negocio e interconexión
```

---

## 💾 Modelo de Datos (Diagrama ER Simplificado)

El sistema cuenta con una base de datos relacional que maneja la integridad referencial para asegurar consistencia en los flujos críticos:

* `Usuario` 1:1 `Perfil` (Datos personales de alumnos o profesores)
* `Usuario` N:M `Rol` (Seguridad y permisos)
* `Clase` N:M `Usuario` (Control de asistencia a los entrenamientos)
* `HistorialGrado` N:1 `Usuario` (Evolución y progresión de cinturones)

---

## ⚙️ Configuración e Instalación Local

Sigue estos pasos para levantar el entorno de desarrollo localmente:

### Prerrequisitos
* Java JDK 17 o superior instalado.
* Maven 3.x.
* MySQL Server activo.

### 1. Clonar el repositorio
```bash
git clone https://github.com
cd gestion-escuelas
```

### 2. Configurar la Base de Datos
Crea una base de datos en tu entorno local de MySQL llamada `escuela_db`. Luego, edita las credenciales en el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/escuela_db?serverTimezone=UTC
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASENA

# Inicialización de tablas mediante Hibernate
spring.jpa.hibernate.ddl-auto=update
```

### 3. Ejecutar la aplicación
Ejecuta el proyecto utilizando Maven desde la raíz:

```bash
mvn spring-boot:run
```
La aplicación estará disponible en: `http://localhost:8080`

---

## 👥 Desarrolladores (Equipo Full Stack)
* **Nombre Apellido** - Backend & Seguridad - [GitHub](https://github.com) / [LinkedIn](https://linkedin.com)
* **Nombre Apellido** - Frontend & Thymeleaf - [GitHub](https://github.com) / [LinkedIn](https://linkedin.com)
* **Nombre Apellido** - Base de Datos & Negocio - [GitHub](https://github.com) / [LinkedIn](https://linkedin.com)
