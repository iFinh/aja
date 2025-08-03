# IS-PROYECTO-2025-GROUP-05

# Sistema de Gestión de Comedor Empresarial

## 🏗️ La Arquitectura - Construyendo con Patrones Sólidos

### El Corazón del Sistema: Patrón MVC
Decidimos implementar el patrón **Model-View-Controller** como columna vertebral del proyecto porque nos permite:

### La Gestión de Estado: Patrón Singleton
La clase `Sesion` implementa el patrón **Singleton** para:
- Mantener una única sesión activa en todo el sistema
- Proporcionar acceso global al usuario actual
- Garantizar la consistencia de datos de sesión

### La Fábrica de Componentes: Patrón Factory
`UIFactory` centraliza la creación de componentes visuales:
- Estandariza el diseño visual
- Facilita cambios globales de estilo
- Reduce la duplicación de código

### La Comunicación: Patrón Observer
Los listeners de Swing implementan el patrón **Observer**:
- Las vistas notifican cambios a los controladores
- Desacopla la interfaz de usuario de la lógica de negocio
- Permite respuestas dinámicas a eventos del usuario

### La Herencia Inteligente: Patrón Template Method
`BaseUserModel` define operaciones comunes:
- Establece un algoritmo base para operaciones de usuario
- Permite personalización en clases derivadas
- Promueve la reutilización de código

## 🛠️ Tecnologías y Herramientas

### Core Technologies
- **Java**: Lenguaje principal del proyecto
- **Swing**: Framework para la interfaz gráfica
- **File I/O**: Persistencia de datos mediante archivos

### Principios de Diseño
- **SOLID**: Aplicados en la estructura de clases
- **DRY**: Evitando duplicación de código
- **Separation of Concerns**: Cada clase tiene una responsabilidad específica

## 📁 Estructura del Proyecto

```
src/main/
├── controllers/           # Controladores MVC
│   ├── AdminControllers/  # Lógica administrativa
│   └── UserControllers/   # Lógica de usuarios
├── models/               # Modelos de datos
├── views/                # Interfaces gráficas
│   ├── Admin/           # Vistas administrativas
│   ├── Usuario/         # Vistas de usuario
│   └── Components/      # Componentes reutilizables
└── data/                # Archivos de persistencia
```

## 🎯 Características Destacadas

### Gestión de Costos Inteligente
- Cálculo automático de costo por bandeja (CCB)
- Consideración de mermas y costos variables
- Historiales de costos por período

### Arquitectura Escalable

## 🚀 Ejecutando el Proyecto

### Prerrequisitos
- Java Development Kit (JDK) 11 o superior
- IDE con soporte para Java (IntelliJ IDEA, Eclipse, NetBeans)

### Instalación
1. Clona el repositorio
2. Abre el proyecto en tu IDE
3. Compila y ejecuta `Main.java`

### Datos de Prueba
- **Admin**: Contraseña de acceso: `admin123`
- **Usuarios**: Registra nuevos usuarios o usa credenciales existentes

## 🤝 Contribuyendo

Este proyecto es el resultado de aplicar principios de ingeniería de software sólidos. Si deseas contribuir:

1. Mantén la coherencia con los patrones existentes
2. Documenta nuevas funcionalidades
3. Respeta la separación de responsabilidades
4. Incluye validaciones apropiadas


---

*"La mejor arquitectura no es la que permite hacer todo, sino la que hace fácil hacer lo correcto."*
