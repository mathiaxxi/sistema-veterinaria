# 🐾 Sistema para Reserva de Citas para Veterinaria 

Este proyecto consiste en el desarrollo de un sistema informático de gestión para una veterinaria, que permite administrar clientes, mascotas, reservas de servicios y atención médica. La solución está desarrollada en Java bajo la arquitectura MVC, utilizando tecnologías como JavaFX y SQLite, e integrando buenas prácticas como TDD, DAO, y principios SOLID.

---

## 🧱 Arquitectura

- **Lenguaje**: Java 17
- **Interfaz gráfica**: JavaFX + FXML
- **Base de datos**: SQL Server
- **Control de versiones**:  GitHub
- **Arquitectura**: MVC + DAO + SOLID
- **Librerías externas**:
  - Google Guava
  - Apache Commons
  - Logback

---

## ✨ Funcionalidades Implementadas

- [x] Registro y gestión de clientes
- [x] Registro y gestión de mascotas
- [x] Reserva de servicios veterinarios
- [ ] Generación de reportes en Excel (próximamente con Apache POI)
- [ ] Gestión de historial clínico (en desarrollo)

---

## 📁 Estructura del Proyecto

```
src/
├── controller/
│   └── ClienteController.java
├── dao/
│   └── ClienteDAO.java
├── model/
│   └── Cliente.java
├── view/
│   └── cliente_form.fxml
├── service/
│   └── ClienteService.java
├── Main.java
```

---

## 🚀 Instalación y Ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/mathiaxxi/sistema-veterinaria.git
   cd sistema-veterinaria
   ```

2. Abre el proyecto en tu IDE (IntelliJ, Eclipse, NetBeans).

3. Asegúrate de tener Java 17 y JavaFX configurado.

4. Ejecuta la clase `Main.java`.

---

## 🧪 Pruebas

Este proyecto utiliza **JUnit 5** para pruebas unitarias. Puedes correr las pruebas desde tu IDE o por línea de comandos.

---

## 🛡️ Seguridad

- Validación de entradas para evitar inyecciones
- Encriptación de contraseñas (futuro)
- Control de acceso por rol (planificado)

---

## 📚 Créditos y Recursos

- JavaFX Documentation: https://openjfx.io
- Guava: https://github.com/google/guava
- Apache Commons: https://commons.apache.org/
- Logback: https://logback.qos.ch/
- SceneBuilder: https://gluonhq.com/products/scene-builder/

---

## 📄 Licencia

Este proyecto es de uso académico. Requiere autorización para su distribución o reutilización con fines comerciales.
