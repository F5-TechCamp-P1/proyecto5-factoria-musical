# 🎵 Factoría Musical

## 📌 Descripción del Proyecto
Factoria Musical es una aplicación web interactiva que permite a los usuarios tocar y grabar música utilizando un teclado MIDI virtual. Los usuarios pueden seleccionar partituras según su nivel de habilidad y seguirlas mientras tocan en el teclado virtual.

## 🚀 Características Principales
- Selección de nivel de dificultad: **Iniciación, Medio, Avanzado**.
- Lista de partituras disponibles según el nivel seleccionado.
- Visualización de la partitura en la parte superior de la pantalla.
- Teclado MIDI virtual en la parte inferior de la pantalla.
- Reproducción de sonidos de teclado: **Piano, Órgano y Sintetizador**.
- Funciones de grabación y reproducción:
  - Grabar, pausar grabación, detener grabación.
  - Reproducir, pausar reproducción, detener reproducción.
  - Pasar a la siguiente pista de audio o volver a la pista anterior.

## 🛠️ Tecnologías Utilizadas
### Frontend:
- HTML, CSS, React, JavaScript.

### Backend:
- Java (sin frameworks como Spring Boot).

### Persistencia de Datos:
- Base de datos H2 en memoria.

### Herramientas de Trabajo Colaborativo:
- **Figma**: Diseño y prototipado.
- **Jira**: Gestión de tareas y metodología ágil.
- **Git/GitHub**: Control de versiones y repositorio compartido dentro de una Organización.

## 📌 Componentes del Proyecto
### 1. Página Inicial
- Selección de nivel: **Iniciación, Medio, Avanzado**.
- Muestra una lista de partituras disponibles según el nivel.
- La partitura seleccionada aparece en la parte superior de la pantalla.

### 2. Teclado MIDI Virtual
- Ubicado en la parte inferior de la pantalla.
- Simula un teclado real con opciones de sonido: **Piano, Órgano y Sintetizador**.
- Responde a la interacción del usuario con animaciones y reproducción de sonido.

### 3. Grabación y Reproducción
- **Grabación**: Iniciar, pausar y detener grabación.
- **Reproducción**: Reproducir, pausar y detener grabaciones.
- **Navegación de pistas**: Pasar a la siguiente pista o volver a la anterior.
- Datos almacenados en H2.

### 4. Configuración de la Aplicación
- Ajustes de volumen y selección del tipo de sonido.
- Preferencias almacenadas en H2.

### 5. Maquetación y Diseño
- Prototipo interactivo en **Figma**.
- Diseño responsivo para escritorio.

### 6. Diagramas
- **Diagrama de Flujo**: Descripción de navegación e interacciones.
- **Diagrama de Clases**: Estructura de clases en frontend y backend.

## 📌 Metodología de Trabajo
- **Scrum** con sprints de 1 semana.
- **Jira** para organización y gestión de tareas.
- **Reuniones diarias (Stand-ups)**.

## 📦 Entregables
- **Código fuente**: Repositorio con frontend y backend.
- **Prototipo en Figma**.
- **Documentación técnica**: Instrucciones de instalación, diagramas y arquitectura.
- **Presentación final**.
- **Pruebas (Testing)** con cobertura mínima del 70% en frontend y backend.

## 📝 Instalación y Ejecución
### 1️⃣ Clonar el repositorio:
```bash
    git clone https://github.com/F5-TechCamp-P1/proyecto5-factoria-musical
```
### 2️⃣ Instalación de dependencias (Frontend):
```bash
    cd frontend
    npm install
```
### 3️⃣ Ejecución del frontend:
```bash
    npm start
```
### 4️⃣ Compilación y ejecución del backend:
```bash
    cd backend
    javac Main.java
    java Main
```
## 📌 Consideraciones Técnicas
- **Backend en Java puro** sin frameworks adicionales.
- **Persistencia** en H2.
- **Comunicación entre frontend y backend** mediante llamadas a funciones para gestionar la información de grabaciones y configuraciones.

## 👥 Equipo de Desarrollo (Equipo 3)
- **Alejandro** - Product Owner
- **Grigory** - Scrum Master
- **Erika** - Coder & Designer
- **Rosa** - Coder
- **Yeferson** - Coder
- **Mabel** - Coder - [![Linkedin](https://i.sstatic.net/gVE0j.png) LinkedIn](https://www.linkedin.com/in/mabel-rincon/)
&nbsp;
Check out other repositories at
&nbsp;
[![GitHub](https://i.sstatic.net/tskMh.png) GitHub](https://github.com/MabelRincon)

---

Este documento se actualizará conforme avance el desarrollo del proyecto. ¡Gracias por tu interés! 🎶
