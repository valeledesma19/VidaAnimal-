# VidaAnimal

Aplicación web para la gestión de una veterinaria. Permite a los clientes reservar turnos en línea y consultar el historial de sus mascotas, y al personal administrar la agenda, registrar vacunas y peso, y acceder a alertas de vencimiento y reportes estadísticos.

Trabajo Final Integrador de la Tecnicatura Universitaria en Programación, UTN Facultad Regional Villa María.

**Autora:** Valentina Luz Ledesma

## Funcionalidades

**Cliente**
- Registro e inicio de sesión.
- Alta de mascotas y consulta de su ficha con historial de vacunas y peso.
- Reserva de turnos mediante la selección de un horario libre, con confirmación inmediata y envío de un email.
- Edición y cancelación de turnos propios, siempre que falten 24 horas o más para la fecha del turno.

**Empleado/a**
- Consulta de la agenda diaria y alta, edición y cancelación de cualquier turno, sin la restricción de 24 horas.
- Búsqueda de mascotas por nombre y registro de vacunas y peso.
- Consulta de las vacunas que vencen en los próximos 30 días.
- Reportes: turnos por mes, vacunas más aplicadas y mascotas nuevas por mes.

**Procesos automáticos**
- Envío de un email de confirmación cada vez que se crea un turno, independientemente de quién lo registre.
- Envío diario, a las 8:00, de un email a los clientes cuyas mascotas tienen vacunas próximas a vencer.

## Tecnologías

| Componente | Tecnología |
|---|---|
| Frontend | React (Vite), React Router, Axios |
| Backend | Java 21, Spring Boot 4, Spring Data JPA |
| Seguridad | Spring Security y JWT (jjwt) |
| Base de datos | PostgreSQL |
| Envío de emails | JavaMailSender (SMTP de Gmail) |
| Pruebas | JUnit 5, Mockito, MockMvc |

## Estructura del repositorio

```
VidaAnimal/
├── vidaanimal-backend/    API REST (Spring Boot)
│   └── src/main/java/com/vidaanimal/vidaanimal_backend/
│       ├── controller/  service/  repository/  entity/
│       └── dto/  security/  exception/
└── frontend/              Aplicación en React
    └── src/
        ├── pages/  components/  services/  context/
```

## Requisitos previos

- Java 21
- Node.js 20.19 o superior
- PostgreSQL 15 o superior
- Cuenta de Gmail con verificación en dos pasos y una contraseña de aplicación, necesaria para el envío de emails

## Instalación y ejecución

### 1. Base de datos

Ejecutar cada bloque por separado, ya que la sentencia `CREATE DATABASE` no puede correr junto con otras:

```sql
CREATE DATABASE vidaanimal_db;
```

Luego, conectado a la base `vidaanimal_db`:

```sql
CREATE USER vidaanimal_user WITH PASSWORD 'changeme';
GRANT ALL PRIVILEGES ON DATABASE vidaanimal_db TO vidaanimal_user;
GRANT ALL ON SCHEMA public TO vidaanimal_user;
```

El último `GRANT` es necesario a partir de PostgreSQL 15, que ya no otorga permiso de creación de tablas sobre el esquema `public` por defecto.

### 2. Backend

1. Copiar el archivo `vidaanimal-backend/src/main/resources/application-example.properties` con el nombre `application.properties` en la misma carpeta. Este archivo no se versiona, porque contiene credenciales.
2. Completar los datos de conexión a PostgreSQL y las credenciales de Gmail:

```properties
spring.application.name=vidaanimal-backend

spring.datasource.url=jdbc:postgresql://localhost:5432/vidaanimal_db
spring.datasource.username=vidaanimal_user
spring.datasource.password=changeme

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=TU_EMAIL@gmail.com
spring.mail.password=TU_CONTRASEÑA_DE_APLICACION
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

3. Iniciar la API, que queda disponible en `http://localhost:8080`:

```powershell
cd vidaanimal-backend
.\mvnw.cmd spring-boot:run
```

En Linux o macOS el comando es `./mvnw spring-boot:run`. En la primera ejecución, Hibernate crea las tablas automáticamente.

### 3. Usuario empleado

La aplicación no incluye una pantalla de alta de empleados: estas cuentas se cargan directamente en la base de datos. Una vez iniciado el backend por primera vez (para que exista la tabla `usuario`), ejecutar:

```sql
INSERT INTO usuario (email, contrasenia_hash, rol)
VALUES (
    'empleado@vidaanimal.com',
    '$2b$10$g3FswOL0ZBrM3XAuLJq19e8zXt2EUr72AH1sakNMdcnm.CE3WcXQ.',
    'EMPLEADO'
);
```

Credenciales de prueba: `empleado@vidaanimal.com` / `empleado123`. Las cuentas de cliente se crean desde la pantalla de registro.

### 4. Frontend

```powershell
cd frontend
npm install
```

Crear un archivo `.env` dentro de la carpeta `frontend/` con el siguiente contenido:

```
VITE_API_URL=http://localhost:8080/api
```

Iniciar la aplicación, que queda disponible en `http://localhost:5173`:

```powershell
npm run dev
```

## Pruebas automatizadas

```powershell
cd vidaanimal-backend
.\mvnw.cmd test
```

Se incluyen 10 pruebas: las reglas de negocio de los turnos (horario libre, horario ocupado, fecha pasada y cancelación por parte del empleado), el cálculo del vencimiento de las vacunas, el inicio de sesión y el registro mediante MockMvc, y la carga del contexto de la aplicación. Es necesario que PostgreSQL esté en ejecución, dado que una de las pruebas inicia la aplicación completa.

## Reglas de negocio

- No puede existir más de un turno confirmado en el mismo día y horario. Los turnos tienen una duración de 30 minutos y se ofrecen entre las 9:00 y las 18:00. Un turno cancelado libera su horario.
- No es posible reservar ni reprogramar turnos para una fecha u hora que ya transcurrió.
- El cliente solo puede editar o cancelar sus turnos si faltan 24 horas o más. La validación se realiza en el backend y no únicamente en la interfaz.
- El cliente solo accede a sus propias mascotas y turnos; el empleado puede operar sobre cualquiera.
- La fecha de vencimiento de una vacuna se calcula (fecha de aplicación más duración en meses) y no se almacena.
- Existen únicamente los roles `CLIENTE` y `EMPLEADO`.

## Endpoints principales

| Método | Ruta | Acceso |
|---|---|---|
| POST | `/api/auth/login` | Público |
| POST | `/api/auth/registro` | Público (crea una cuenta de cliente) |
| GET | `/api/turnos/disponibilidad?fecha=` | Usuarios autenticados |
| POST | `/api/turnos` | Cliente o empleado |
| GET | `/api/turnos/agenda?fecha=` | Empleado |
| GET | `/api/turnos/mios` | Cliente |
| GET, PUT, DELETE | `/api/turnos/{id}` | Cliente (turnos propios, con la regla de 24 horas) o empleado |
| GET | `/api/mascotas` | Cliente: las propias. Empleado: mediante `?clienteId=` |
| GET | `/api/mascotas/buscar?nombre=` | Empleado |
| GET | `/api/mascotas/{id}` | Ficha con vacunas y peso |
| POST | `/api/mascotas` | Cliente o empleado |
| POST | `/api/mascotas/{id}/vacunas` | Empleado |
| POST | `/api/mascotas/{id}/peso` | Empleado |
| GET | `/api/vacunas/alertas` | Empleado |
| GET | `/api/reportes/turnos-por-mes` | Empleado |
| GET | `/api/reportes/vacunas-mas-aplicadas` | Empleado |
| GET | `/api/reportes/mascotas-nuevas-por-mes` | Empleado |

## Problemas frecuentes y que llegué a tener

- **`permission denied for schema public` al iniciar el backend:** falta ejecutar el último `GRANT` del paso 1.
- **`Port 8080 was already in use`:** existe otra instancia del backend en ejecución. Debe detenerse antes de iniciar una nueva.
- **Error de CORS en el navegador:** el backend admite solicitudes desde `http://localhost:5173` y `http://localhost:5174`. Si el servidor de desarrollo utiliza otro puerto, debe agregarse en `SecurityConfig`.
- **Respuesta 403 o token inválido al iniciar sesión:** se debe limpiar el almacenamiento del navegador (`localStorage.clear()` en la consola) y volver a iniciar sesión.
- **No se reciben los emails:** verificar que se utilice una contraseña de aplicación de Gmail y no la contraseña habitual de la cuenta, y que la verificación en dos pasos esté activa.
- **Errores por archivos bloqueados durante la compilación:** se recomienda no ubicar la carpeta del proyecto dentro de OneDrive.

## Decisiones de diseño y limitaciones

- La clave de firma del JWT está definida en el código (`JwtService`) por simplicidad en el marco de un trabajo académico; en un entorno productivo debería provenir de una variable de entorno.
- El esquema de la base de datos lo genera Hibernate (`ddl-auto=update`) en lugar de utilizar migraciones versionadas.
- La unicidad de horario se valida en la capa de servicio y no mediante una restricción en la base de datos, para que los turnos cancelados puedan liberar su horario. Como consecuencia, dos reservas simultáneas sobre el mismo horario podrían superar la validación.
- Quedan fuera del alcance la recuperación de contraseña, los refresh tokens, los pagos en línea, las notificaciones por SMS, WhatsApp o push, la carga de archivos y las agendas de múltiples veterinarios.