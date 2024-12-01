
# Trabajo Evaluación 1 - Servidor

### Autor: 
**Iván Sánchez Sánchez**  
**Curso:** 2º DAW (Desarrollo de Aplicaciones Web)

---

## Descripción del Proyecto

Este proyecto consiste en desarrollar una **API REST** para la gestión de información relacionada con proyectos, incluyendo el manejo de tecnologías utilizadas y los desarrolladores que trabajaron en ellos. La API permite realizar operaciones CRUD completas sobre las entidades y está diseñada siguiendo el patrón **Controlador-Servicio-Repositorio (MVC)**.

El proyecto utiliza **Spring Boot** para la implementación de la API y está versionado mediante **GIT**.

---

## Esquema de la Base de Datos

El esquema relacional utilizado incluye las siguientes tablas principales:

- **Projects**: Contiene información sobre los proyectos (nombre, estado, etc.).
- **Developers**: Información de los desarrolladores que trabajaron en proyectos.
- **Technologies**: Detalle de las tecnologías utilizadas en los proyectos.

---

## Funcionalidades Principales

1. **Gestión de Proyectos**:
   - **GET /projects**: Obtiene todos los proyectos paginados.
   - **GET /projects/{word}**: Busca proyectos cuyo nombre contiene una palabra específica.
   - **POST /projects**: Crea un nuevo proyecto.
   - **PUT /projects/{id}**: Actualiza un proyecto existente.
   - **DELETE /projects/{id}**: Elimina un proyecto.

2. **Gestión de Desarrolladores**:
   - **POST /developers**: Inserta un nuevo desarrollador.
   - **DELETE /developers/{id}**: Elimina un desarrollador.

3. **Gestión de Tecnologías**:
   - **POST /technologies**: Inserta una nueva tecnología.
   - **DELETE /technologies/{id}**: Elimina una tecnología.

4. **Asociaciones**:
   - **POST /technologies/used/{id}**: Asocia una tecnología a un proyecto.
   - **POST /developers/worked/{id}**: Asocia un desarrollador a un proyecto.

5. **Estados del Proyecto**:
   - **PATCH /projects/totesting/{id}**: Cambia el estado de un proyecto a **Testing**.
   - **PATCH /projects/toprod/{id}**: Cambia el estado de un proyecto a **Production**.

6. **Consultas Avanzadas**:
   - **GET /projects/tec/{tech}**: Devuelve los proyectos en los que se ha utilizado una tecnología específica (consulta personalizada con JPA).

---

## Características Técnicas

### Arquitectura:
- **Modelo Vista Controlador (MVC)**:
  - **Controlador**: Define los endpoints y procesa las solicitudes HTTP.
  - **Servicio**: Implementa la lógica de negocio.
  - **Repositorio**: Interactúa con la base de datos.

### Validaciones:
- Uso de validaciones estándar y personalizadas para garantizar la integridad de los datos.

### Tratamiento de Excepciones:
- Implementación de un `RestControllerAdvice` para capturar y manejar excepciones.

### Documentación:
- Uso de **Swagger** con la librería **SpringDoc** para documentar la API y facilitar su exploración.

---

## Requisitos de Ejecución

1. **JDK 17 o superior**.
2. **Spring Boot**.
3. **Base de Datos H2** (puede adaptarse a MySQL o PostgreSQL).
4. **Herramienta de Gestión de Dependencias**: Maven.

---

## Instalación y Uso

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tuusuario/trabajo-evaluacion1-servidor.git
   cd trabajo-evaluacion1-servidor
   ```

2. Configura la base de datos en `application.properties`.

3. Ejecuta la aplicación:
   ```bash
   mvn spring-boot:run
   ```

4. Accede a la API mediante [http://localhost:8080/api/v1](http://localhost:8080/api/v1).

---

## Endpoints Documentados

La API está completamente documentada mediante Swagger. Una vez iniciada la aplicación, puedes acceder a la documentación en:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Ejemplo de Consultas

### Consultar todos los proyectos:
```bash
GET /api/v1/projects
```

### Agregar una nueva tecnología:
```bash
POST /api/v1/technologies
Body: {
  "name": "Java",
  "description": "Backend programming language"
}
```

### Asociar un desarrollador a un proyecto:
```bash
POST /api/v1/developers/worked/1
Body: {
  "developerId": 2
}
```

---

## Buenas Prácticas Implementadas

- Código modular y limpio.
- Uso de DTOs para encapsular las respuestas.
- Tratamiento centralizado de excepciones.
- Mapeo claro entre entidades y DTOs.

---

## Contacto

Cualquier duda o comentario, puedes contactarme en:  
**Nombre:** Iván Sánchez Sánchez  
**Email:** sansaniva357@gmail.com 
**GitHub:** [github.com/iivansaanchez](https://github.com/iivansaanchez)

---
