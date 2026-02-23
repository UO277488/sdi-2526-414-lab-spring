# SDI 2526 - Laboratorio Spring

Desarrollado por Luis Sánchez de Posada Orihuela - UO277488@uniovi.es.

## 📦 Características

- Inyección de dependencias sin uso de `@Autowired`.
- Focus en la barra de navegación en el módulo del proyecto correspondiente.
- Internacionalización completa.
- Funcionalidad del departamento de profesorado.

## 🚀 Ejecución

La aplicación se ejecuta en el puerto **8080** (Spring Boot por defecto).

Accede localmente en:

`http://localhost:8080`

## ⚠️ Advertencias

### Profesores y departamentos
La funcionalidad de profesores y departamentos **no está integrada** en la aplicación. No tiene un rol importante en nada; solo quería completar de manera correcta el ejercicio complementario. Los profesores no son los únicos que pueden modificar las notas.

### Datos Hardcodeados (Seed)

Al iniciar la aplicación se insertan datos de ejemplo desde `InsertDataSampleService`:

- Usuarios de login de prueba:
  - `99999990A` (Pedro Díaz)
  - `99999988F` (Edward Núñez)
  - contraseña por defecto: `123456`
- Departamentos de ejemplo (si su `code` no existe ya):
  - `COMP0001A` → `department.seed.computerScience.name`
  - `SOFT0002B` → `department.seed.softwareEngineering.name`
  - `MATH0003C` → `department.seed.math.name`
  - `ELEC0004D` → `department.seed.electronics.name`

---
## 🌍 Internacionalización 

En este proyecto se usan `message keys` para desacoplar lógica y textos visibles:

- Los textos de interfaz están en `messages.properties`, `messages_es.properties`, `messages_en.properties` y `messages_ast.properties`.
- En los departamentos seed, el campo `name` guarda una clave i18n (por ejemplo `department.seed.computerScience.name`) en lugar de un texto fijo.
- En las vistas Thymeleaf se intenta resolver esa clave y, si no existe, se muestra el valor literal como fallback:
  - `#messages.msgOrNull(...)` + fallback.
- En operaciones de servicio/controlador, se devuelven claves como:
  - `professor.api.update.ok`
  - `professor.api.update.error.notFound`
- El controlador pasa esa clave a la vista (`successKey`/`errorKey`) y Thymeleaf renderiza el texto traducido con `#{__${key}__}`.

Beneficio: la lógica de negocio no depende del idioma y los mensajes se traducen de forma centralizada.
