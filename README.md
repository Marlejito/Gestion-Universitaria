# Sistema de Gestión Universitaria

> Sistema web completo para administración académica con **Java**, **React** y **TypeScript**

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![React](https://img.shields.io/badge/React-18.3-blue)](https://react.dev/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.6-blue)](https://www.typescriptlang.org/)

---

## 📋 Descripción

Sistema web full-stack para gestionar estudiantes, profesores, cursos, inscripciones y calificaciones en una institución educativa. Incluye interfaz moderna en español, sistema de calificaciones colombiano (escala 1.0-5.0) y actualizaciones en tiempo real.

### ✨ Características Principales

- ✅ **Interfaz 100% en Español**
- ✅ **Sistema de Calificaciones Colombiano** (1.0-5.0)
- ✅ **Actualizaciones en Tiempo Real** (WebSockets)
- ✅ **Diseño Moderno** (Modo claro/oscuro)
- ✅ **API REST Completa** (25+ endpoints)
- ✅ **Validación Robusta** (Frontend y Backend)

---

## 🛠️ Tecnologías

### Backend
- **Java 17** - Lenguaje principal
- **Javalin 5.6.3** - Framework web ligero
- **Jackson 2.15.2** - Procesamiento JSON
- **Maven** - Gestión de dependencias

### Frontend
- **React 18.3.1** - Biblioteca UI
- **TypeScript 5.6.3** - Tipado estático
- **Vite 5.4.20** - Build tool
- **Tailwind CSS 3.4.17** - Framework CSS
- **TanStack Query 5.60.5** - Gestión de estado
- **React Hook Form 7.55.0** - Formularios
- **Zod 3.24.2** - Validación

### Base de Datos
- **JSON** (`data.json`) - Almacenamiento portable
- **ConcurrentHashMap** - Caché en memoria

---

## 🚀 Inicio Rápido

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+
- Node.js 18+ y npm (solo para desarrollo)

### Opción 1: Ejecutar JAR (Producción)

```bash
# 1. Compilar el proyecto
mvn clean package -DskipTests

# 2. Ejecutar la aplicación
java -jar target/gestion-universitaria-1.0.jar
```

**Acceder a**: http://localhost:7000

### Opción 2: Modo Desarrollo (Hot Reload)

```bash
# Terminal 1 - Backend
mvn compile exec:java

# Terminal 2 - Frontend
cd client
npm install
npm run dev
```

**Acceder a**: http://localhost:5173

---

## 📁 Estructura del Proyecto

```
Proyecto Gestion Universitaria/
├── src/main/java/              # Backend Java
│   ├── app/Main.java           # Punto de entrada
│   ├── controllers/            # 6 controladores REST
│   ├── models/                 # 5 modelos de datos
│   └── utils/                  # DataStore y validación
├── client/                     # Frontend React + TypeScript
│   ├── src/pages/              # 6 páginas principales
│   ├── src/components/         # Componentes UI
│   └── src/shared/             # Esquemas Zod
├── data.json                   # Base de datos JSON
└── pom.xml                     # Configuración Maven
```

---

## 🎓 Sistema de Calificaciones

Escala colombiana (1.0 - 5.0):

| Letra | Valor | Rango | Descripción |
|-------|-------|-------|-------------|
| **A** | 5.0 | ≥ 90% | Excelente |
| **B** | 4.5 | 80-89% | Sobresaliente |
| **C** | 4.0 | 70-79% | Aceptable |
| **D** | 3.0 | 60-69% | Insuficiente |
| **F** | 1.0-2.9 | < 60% | Reprobado |

**Nota mínima aprobatoria**: 3.0 (60%)

---

## 🌐 API REST

### Endpoints Principales

| Recurso | GET | POST | PATCH | DELETE |
|---------|-----|------|-------|--------|
| `/api/students` | ✅ Listar | ✅ Crear | ✅ Actualizar | ✅ Eliminar |
| `/api/professors` | ✅ Listar | ✅ Crear | ✅ Actualizar | ✅ Eliminar |
| `/api/courses` | ✅ Listar | ✅ Crear | ✅ Actualizar | ✅ Eliminar |
| `/api/enrollments` | ✅ Listar | ✅ Crear | ✅ Actualizar | ✅ Eliminar |
| `/api/grades` | ✅ Listar | ✅ Crear | ✅ Actualizar | ✅ Eliminar |

### Reportes
- `GET /api/boletin/{id}` - Boletín de estudiante
- `GET /api/reporte/resumen` - Resumen general

### WebSocket
- `WS /api/ws` - Actualizaciones en tiempo real

---

## 🏗️ Arquitectura

```
┌─────────────────┐         ┌─────────────────┐         ┌──────────┐
│    FRONTEND     │  HTTP   │     BACKEND     │         │  DATOS   │
│ (Interfaz Web)  │ ◄─────► │   (Servidor)    │ ◄─────► │ (Archivo)│
│ React + TS      │         │ Java + Javalin  │         │ JSON     │
└─────────────────┘         └─────────────────┘         └──────────┘
        ▲                            │
        └────────────────────────────┘
           Actualizaciones en Tiempo Real
                  (WebSocket)
```

**3 Capas**:
1. **Presentación**: React + TypeScript + Tailwind CSS
2. **Aplicación**: Java + Javalin (API REST + WebSocket)
3. **Datos**: JSON + ConcurrentHashMap (caché en memoria)

---

## ⚡ Funcionalidades

### 1. Gestión de Estudiantes
- CRUD completo
- Búsqueda avanzada
- Validación de datos
- Estados (activo, inactivo, graduado)

### 2. Gestión de Profesores
- CRUD completo
- Departamentos y especialización
- Asignación a cursos

### 3. Gestión de Cursos
- CRUD completo
- Código único, créditos, capacidad
- Horarios y aulas
- Asignación de profesor

### 4. Inscripciones
- Inscribir estudiantes en cursos
- Validación de capacidad
- Estados (inscrito, completado, retirado)

### 5. Calificaciones
- Registro de calificaciones
- Conversión automática a escala colombiana
- Cálculo de promedios

### 6. Reportes y Analítica
- Dashboard con estadísticas
- Distribución de calificaciones
- Gráficos y visualizaciones

---

## 🔧 Configuración

### Puerto del Servidor

Modificar en `src/main/java/app/Main.java`:

```java
.start(7000)  // Cambiar a otro puerto si es necesario
```

### Proxy Frontend → Backend

Configurado en `client/vite.config.ts`:

```typescript
server: {
  proxy: {
    "/api": {
      target: "http://localhost:7000",
      changeOrigin: true,
    },
  },
}
```

---

## 🐛 Solución de Problemas

### Puerto 7000 ocupado

**Windows**:
```bash
netstat -ano | findstr :7000
taskkill /PID <PID> /F
```

**Linux/Mac**:
```bash
lsof -ti:7000 | xargs kill -9
```

### Error al compilar frontend

```bash
cd client
rm -rf node_modules package-lock.json
npm install
npm run build
```

### Error de versión de Java

Verificar versión (debe ser 17+):
```bash
java -version
```

---

## 📚 Documentación Adicional

- **[DOCUMENTACION_TECNICA.txt](DOCUMENTACION_TECNICA.txt)** - Detalles técnicos del sistema
- **[PRESENTACION_CODIGO.txt](PRESENTACION_CODIGO.txt)** - Explicación detallada del código

---

## 🔗 Repositorio

**GitHub**: https://github.com/Marlejito/Gestion-Universitaria

---

## 📝 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

---

**¡Gracias por revisar este proyecto!** 🎓
