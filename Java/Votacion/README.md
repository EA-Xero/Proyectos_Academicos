# Electo - Sistema de Votaciones

Proyecto desarrollado para el **Laboratorio 2** de la asignatura **Estructuras de Datos y Algoritmos (EDA) 2025-1** en la Universidad Diego Portales.

## 📋 Descripción
**Electo** es un sistema de votaciones que permite registrar candidatos, votantes y gestionar el proceso de votación de manera simple utilizando:
- **Listas enlazadas** (`LinkedList`)
- **Pilas** (`Stack`)
- **Colas** (`Queue`)

El sistema asegura que cada votante pueda emitir un solo voto y permite la verificación y reporte de votos.

---

## 🗂 Estructura del repositorio

```plaintext
Electo/
├── Clases/
│   ├── Voto.java
│   ├── Candidato.java
│   ├── Votante.java
│   ├── UrnaElectoral.java
│   └── Main.java
├── Comprimido/
│   └── Electo.zip
└── README.md
```

- **Clases/**: Contiene todos los archivos `.java` individuales para fácil edición.
- **Comprimido/**: Contiene un archivo `.zip` llamado `Electo.zip` que incluye todos los `.java` listos para descargar y utilizar en un IDE.

---

## 🚀 Cómo utilizar

1. Clonar el repositorio o descargar el contenido manualmente.
2. Abrir la carpeta `Clases/` o descomprimir `Comprimido/Electo.zip`.
3. Importar los archivos `.java` en tu proyecto de Java.
4. Compilar y ejecutar `Main.java` para interactuar con el sistema de votaciones en consola.

---

## 📚 Requerimientos

- Java 8 o superior
- Un IDE como IntelliJ IDEA, Eclipse, NetBeans o similar

---

## 🛠 Funcionalidades principales

- Registro de candidatos y votantes.
- Validación para evitar IDs duplicados.
- Proceso de votación seguro (un solo voto por persona).
- Reporte de votos inválidos o anulados.
- Visualización de resultados de la elección.

---

## ✍️ Notas

Este proyecto fue realizado como parte de una experiencia de aprendizaje en estructuras de datos básicas, modelado orientado a objetos y gestión de memoria.

Si bien el sistema funciona correctamente, podrían explorarse mejoras como:
- Implementar una base de datos para persistencia.
- Agregar soporte para múltiples elecciones (por facultades o categorías).
- Interfaz gráfica en lugar de consola.

---

## 📅 Entrega

**Abril 2025**  
Universidad Diego Portales - Facultad de Ingeniería y Ciencias
