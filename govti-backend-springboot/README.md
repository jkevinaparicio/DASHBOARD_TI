# GOVTI Backend – Milenio PC S.A.

Backend inicial para una plataforma académica de Gobierno Estratégico de TI.

## Requisitos
- Java 17
- Maven 3.8+
- MySQL 8

## 1. Crear base de datos
```sql
CREATE DATABASE govti_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

## 2. Configurar conexión
Edita `src/main/resources/application.properties` y cambia `spring.datasource.username` y `spring.datasource.password`.

## 3. Ejecutar
Desde la carpeta del proyecto:
```bash
mvn spring-boot:run
```
Servidor: `http://localhost:8081`

Hibernate crea las tablas al iniciar (`ddl-auto=update`). Para producción se debe cambiar esta configuración y usar migraciones.

## 4. Endpoints principales
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET, POST, PUT, DELETE /api/objetivos`
- `GET, POST, PUT, DELETE /api/indicadores`
- `GET, POST, DELETE /api/mediciones`
- `GET, POST, PUT, DELETE /api/riesgos`
- `GET /api/dashboard`

Excepto autenticación, todos requieren `Authorization: Bearer <token>`.

## 5. Ejemplo: registrar usuario
```json
{
  "username": "responsable1",
  "password": "ClaveTemporal123!",
  "nombre": "Responsable de TI",
  "role": "RESPONSABLE_TI"
}
```
Roles permitidos en el registro público: `RESPONSABLE_TI` y `AUDITOR`. Los roles ADMIN y CIO deben asignarse por un proceso administrativo seguro; no habilites su registro público.

## 6. Iniciar sesión
```json
{
  "username": "responsable1",
  "password": "ClaveTemporal123!"
}
```
La respuesta incluye el token JWT. Enviar ese token como Bearer para consumir los endpoints.

## 7. Ejemplo de objetivo
```json
{
  "nombre": "Fortalecer la seguridad de la información",
  "descripcion": "Mejorar el gobierno y tratamiento de riesgos tecnológicos",
  "perspectiva": "PROCESOS INTERNOS",
  "responsable": "CIO",
  "fechaInicio": "2026-11-17",
  "fechaFin": "2027-06-30",
  "estado": "EN_CURSO"
}
```

## 8. Ejemplo de indicador
```json
{
  "nombre": "Riesgos tecnológicos tratados",
  "formula": "Riesgos tratados / riesgos identificados * 100",
  "unidad": "%",
  "meta": 90,
  "frecuencia": "MENSUAL",
  "responsable": "Responsable de seguridad",
  "objetivo": {"id": 1}
}
```

## 9. Ejemplo de medición
```json
{
  "indicador": {"id": 1},
  "valor": 75,
  "fecha": "2026-11-30",
  "observacion": "Medición de prueba",
  "registradoPor": "responsable1"
}
```

## 10. Ejemplo de riesgo
```json
{
  "nombre": "Interrupción de servicios tecnológicos",
  "descripcion": "Indisponibilidad de plataformas que soportan los servicios DaaS",
  "categoria": "CONTINUIDAD",
  "probabilidad": "MEDIA",
  "impacto": "ALTO",
  "nivel": "ALTO",
  "tratamiento": "Definir y probar planes de continuidad",
  "responsable": "CIO",
  "estado": "ABIERTO"
}
```

## Importante
Este es un MVP académico. Antes de producción se deben agregar DTOs, manejo global de errores, paginación, auditoría, migraciones, pruebas, política de contraseñas, protección contra abuso, revisión de permisos por operación y gestión segura de secretos. El registro público no debe permitir crear administradores ni CIO.
