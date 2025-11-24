# Sistema de Gestión Universitaria

Sistema web completo para administración académica con interfaz React moderna y backend Java. **Completamente en español** con sistema de calificaciones (escala 1.0-5.0).

## 🚀 Inicio Rápido

```bash
# 1. Compilar el proyecto
mvn package -DskipTests

# 2. Ejecutar la aplicación
java -jar target/gestion-universitaria-1.0.jar
```

**Acceder a:** http://localhost:5173/

---

## 📋 Características Principales

- ✅ **Gestión Completa**: Estudiantes, Profesores, Cursos, Inscripciones y Calificaciones
- ✅ **Sistema Calificaciones**: Escala de calificaciones 1.0-5.0 (A=5.0, B=4.5, C=4.0, D=3.0, F=1.0)
- ✅ **Interfaz en Español**: Todas las páginas, formularios y mensajes traducidos
- ✅ **Tiempo Real**: Actualizaciones instantáneas con WebSockets
- ✅ **Modo Oscuro**: Interfaz moderna con tema claro/oscuro
- ✅ **Diseño Responsivo**: Optimizado para desktop y móvil
- ✅ **Reportes y Analítica**: Estadísticas detalladas y distribución de calificaciones

---

## 🏗️ Arquitectura del Proyecto

```
Proyecto Gestion Universitaria/
├── src/main/java/              # Backend Java
│   ├── app/
│   │   └── Main.java           # Punto de entrada, configuración Javalin
│   ├── controllers/            # 6 controladores REST
│   │   ├── EstudianteController.java
│   │   ├── ProfesorController.java
│   │   ├── CursoController.java
│   │   ├── InscripcionController.java
│   │   ├── CalificacionController.java
│   │   └── ReporteController.java
│   ├── models/                 # 5 modelos de datos
│   │   ├── Estudiante.java
│   │   ├── Profesor.java
│   │   ├── Curso.java
│   │   ├── Inscripcion.java
│   │   └── Calificacion.java
│   └── utils/
│       ├── DataStore.java      # Persistencia JSON
│       └── Validator.java      # Validación de datos
├── src/main/resources/
│   ├── public/                 # Frontend compilado
│   └── version.properties
├── client/                     # Frontend React + TypeScript
│   ├── src/
│   │   ├── pages/              # 6 páginas principales
│   │   │   ├── dashboard.tsx   # Panel principal
│   │   │   ├── students.tsx    # Gestión de estudiantes
│   │   │   ├── professors.tsx  # Gestión de profesores
│   │   │   ├── courses.tsx     # Gestión de cursos
│   │   │   ├── enrollments.tsx # Inscripciones
│   │   │   └── reports.tsx     # Reportes y analítica
│   │   ├── components/         # Componentes UI (shadcn/ui)
│   │   ├── lib/                # Utilidades y configuración
│   │   └── hooks/              # React hooks personalizados
│   ├── shared/                 # Esquemas Zod compartidos
│   ├── package.json
│   ├── vite.config.ts          # Configuración Vite + proxy
│   ├── tailwind.config.ts      # Configuración Tailwind CSS
│   └── tsconfig.json
├── data.json                   # Base de datos JSON
├── pom.xml                     # Configuración Maven
└── README.md
```

---

## 🛠️ Tecnologías

### Backend
- **Java 17**: Lenguaje principal
- **Javalin 5.6**: Framework web ligero
- **Jackson**: Serialización/deserialización JSON
- **Maven**: Gestión de dependencias
- **WebSockets**: Comunicación en tiempo real

### Frontend
- **React 18**: Biblioteca UI
- **TypeScript**: Tipado estático
- **Vite**: Build tool y dev server
- **Tailwind CSS**: Framework CSS utility-first
- **shadcn/ui**: Componentes UI modernos
- **React Query**: Gestión de estado del servidor
- **Wouter**: Routing ligero
- **Zod**: Validación de esquemas
- **React Hook Form**: Gestión de formularios

### Base de Datos
- **JSON**: Almacenamiento en archivo (`data.json`)
- **ConcurrentHashMap**: Caché en memoria para rendimiento

---

## 📦 Instalación y Compilación

### Prerrequisitos
- Java 17 o superior
- Maven 3.6+
- Node.js 18+ y npm

### Compilar Frontend

```bash
# Desde la raíz del proyecto
cd client
npm install
npm run build
```

El frontend compilado se genera en `src/main/resources/public/`

### Compilar Backend

```bash
# Desde la raíz del proyecto
mvn clean package -DskipTests
```

Genera el JAR ejecutable en `target/gestion-universitaria-1.0.jar`

---

## 🚀 Ejecución

### Modo Producción (JAR único)

```bash
java -jar target/gestion-universitaria-1.0.jar
```

Acceder a: **http://localhost:7000**

### Modo Desarrollo (Hot Reload)

```bash
# Terminal 1 - Backend
mvn compile exec:java

# Terminal 2 - Frontend (con hot reload)
cd client
npm run dev
```

- Backend: http://localhost:7000
- Frontend: http://localhost:5173 (con proxy a backend)

---

## 📡 API REST Endpoints

### Estudiantes
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/students` | Listar todos los estudiantes |
| POST | `/api/students` | Crear nuevo estudiante |
| PATCH | `/api/students/{id}` | Actualizar estudiante |
| DELETE | `/api/students/{id}` | Eliminar estudiante |

### Profesores
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/professors` | Listar todos los profesores |
| POST | `/api/professors` | Crear nuevo profesor |
| PATCH | `/api/professors/{id}` | Actualizar profesor |
| DELETE | `/api/professors/{id}` | Eliminar profesor |

### Cursos
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/courses` | Listar todos los cursos |
| POST | `/api/courses` | Crear nuevo curso |
| PATCH | `/api/courses/{id}` | Actualizar curso |
| DELETE | `/api/courses/{id}` | Eliminar curso |

### Inscripciones
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/enrollments` | Listar todas las inscripciones |
| POST | `/api/enrollments` | Crear nueva inscripción |
| PATCH | `/api/enrollments/{id}` | Actualizar inscripción |
| DELETE | `/api/enrollments/{id}` | Eliminar inscripción |

### Calificaciones
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/grades` | Listar todas las calificaciones |
| POST | `/api/grades` | Crear nueva calificación |
| PATCH | `/api/grades/{id}` | Actualizar calificación |
| DELETE | `/api/grades/{id}` | Eliminar calificación |

### Reportes
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/boletin/{id}` | Obtener boletín de estudiante |
| GET | `/api/reporte/resumen` | Resumen general del sistema |

### WebSocket
| Ruta | Descripción |
|------|-------------|
| WS `/api/ws` | Conexión WebSocket para actualizaciones en tiempo real |

---

## 🎓 Sistema de Calificaciones Colombiano

### Escala Numérica (1.0 - 5.0)

| Letra | Valor | Rango | Descripción |
|-------|-------|-------|-------------|
| A | 5.0 | ≥ 4.5 | Excelente |
| B | 4.5 | 4.0 - 4.4 | Sobresaliente |
| C | 4.0 | 3.0 - 3.9 | Aceptable |
| D | 3.0 | 2.0 - 2.9 | Insuficiente |
| F | 1.0 | < 2.0 | Reprobado |

### Implementación

La conversión de calificaciones se realiza en el frontend (`reports.tsx`):

```typescript
const calculateColombianGrade = (grade: number): number => {
  if (grade >= 90) return 5.0;
  if (grade >= 80) return 4.5;
  if (grade >= 70) return 4.0;
  if (grade >= 60) return 3.0;
  return 1.0 + (grade / 60) * 2.0;
};
```

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

```bash
# Windows
netstat -ano | findstr :7000
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:7000 | xargs kill -9
```

O cambiar el puerto en `Main.java`

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

### Datos no se guardan

Verificar permisos de escritura en `data.json`

---

## 📚 Documentación Adicional

- **Documentación Técnica**: Ver `DOCUMENTACION_TECNICA.txt`
- **Presentación del Código**: Ver `PRESENTACION_CODIGO.txt`

---

## 🔗 Enlaces Útiles

- [Javalin Documentation](https://javalin.io/)
- [React Documentation](https://react.dev/)
- [Tailwind CSS](https://tailwindcss.com/)
- [shadcn/ui](https://ui.shadcn.com/)
- [Vite](https://vitejs.dev/)
