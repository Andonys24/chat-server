# Chat Server

Servidor de chat concurrente desarrollado en Java que permite múltiples clientes conectarse simultáneamente y comunicarse en tiempo real a través de sockets TCP.

## 📋 Descripción

Chat Server es una aplicación educativa que implementa un servidor de chat multihilo capaz de gestionar múltiples conexiones de clientes simultáneamente. Los usuarios pueden enviar mensajes privados, mensajes grupales y consultar usuarios conectados mediante un protocolo personalizado en español.

### Características Principales

- ✅ **Comunicación Multihilo**: Manejo simultáneo de múltiples clientes usando hilos virtuales
- ✅ **Protocolo TCP/IP**: Comunicación confiable basada en sockets
- ✅ **Mensajes Privados**: Envío de mensajes entre usuarios específicos
- ✅ **Mensajes Grupales**: Broadcast a todos los usuarios conectados
- ✅ **Gestión de Usuarios**: Lista de usuarios activos con validación de nicknames únicos
- ✅ **Notificaciones**: Alertas cuando usuarios entran o salen del chat
- ✅ **Manejo de Desconexiones**: Limpieza automática de conexiones abruptas
- ✅ **Protocolo en Español**: Comandos y respuestas en idioma español

## 🏗️ Estructura del Proyecto

```
chat-server/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── chatserver/
│       │           ├── client/
│       │           │   ├── ClientApp.java
│       │           │   └── ClientProtocolHandler.java
│       │           ├── protocol/
│       │           │   └── Protocol.java
│       │           ├── server/
│       │           │   ├── ConnectionHandler.java
│       │           │   ├── ServerApp.java
│       │           │   └── ServerProtocolHandler.java
│       │           ├── user/
│       │           │   ├── User.java
│       │           │   └── UserManager.java
│       │           └── utils/
│       │               ├── Config.java
│       │               ├── ProtocolManager.java
│       │               ├── UI.java
│       │               └── Validators.java
│       └── resources/
│           └── config.properties
├── pom.xml
└── README.md
```

## 🚀 Inicio Rápido

### Requisitos

- **Java**: JDK 24+ (con soporte para hilos virtuales)
- **Maven**: 3.6+

### Compilación

```bash
# Clonar el repositorio
git clone https://github.com/Andonys24/chat-server.git
cd chat-server

# Compilar con Maven
mvn clean compile
```

### Ejecución

#### Iniciar el servidor

```bash
mvn exec:java -Dexec.mainClass="com.chatserver.server.ServerApp"
```

El servidor se iniciará en `localhost:8080` por defecto.

Para detener el servidor, escribe `exit` en la consola.

#### Iniciar clientes (en terminales separadas)

```bash
mvn exec:java -Dexec.mainClass="com.chatserver.client.ClientApp"
```

Se solicitará un nombre de usuario con las siguientes validaciones:
- Longitud: 3-12 caracteres
- Solo letras, números y guiones bajos
- No puede iniciar con número o guión bajo
- Debe ser único en el servidor

## 💡 Uso

### Comandos Disponibles

Una vez conectado al servidor, puedes usar los siguientes comandos:

| Comando | Descripción |
|---------|-------------|
| `MENSAJE` | Enviar mensaje privado a un usuario específico |
| `TODOS` | Enviar mensaje a todos los usuarios conectados |
| `USUARIOS` | Ver lista de usuarios activos |
| `LIMPIAR CONSOLA` | Limpiar la pantalla del chat |
| `SALIR` | Desconectarse del servidor |

### Ejemplo de Sesión

```
1. Ingresar nombre de usuario: Juan123
2. Escribir comando: TODOS
3. Escribir mensaje: Hola a todos!
4. El mensaje se envía a todos los usuarios conectados
```

## 🔧 Configuración

Puedes modificar los parámetros del servidor en [`config.properties`](src/main/resources/config.properties):

```properties
HOST=localhost
PORT=8080
```

También puedes ajustar límites en [`UserManager.java`](src/main/java/com/chatserver/user/UserManager.java):

```java
private static final int MAX_USERS = 50;  // Máximo de usuarios simultáneos
```

## 📚 Concepto Educativo

Este proyecto enseña:

- **Programación Multihilo**: Uso de hilos virtuales de Java para manejo concurrente
- **Sockets TCP**: Implementación de `ServerSocket` y `Socket`
- **I/O en Java**: Uso de `BufferedReader` y `PrintWriter` con encoding UTF-8
- **Protocolo Personalizado**: Diseño de comandos y respuestas en español
- **Sincronización**: Uso de `synchronized` para evitar condiciones de carrera
- **Gestión de Recursos**: Cierre adecuado de conexiones y streams
- **Validación de Datos**: Implementación de validadores para entrada de usuario

### Clases Principales

#### [`com.chatserver.server.ServerApp`](src/main/java/com/chatserver/server/ServerApp.java)
Punto de entrada del servidor. Inicia el `ServerSocket` y acepta conexiones de clientes.

#### [`com.chatserver.server.ConnectionHandler`](src/main/java/com/chatserver/server/ConnectionHandler.java)
Hilo que maneja la comunicación con un cliente individual. Implementa `Runnable`.

#### [`com.chatserver.server.ServerProtocolHandler`](src/main/java/com/chatserver/server/ServerProtocolHandler.java)
Procesa los comandos del protocolo del lado del servidor.

#### [`com.chatserver.client.ClientApp`](src/main/java/com/chatserver/client/ClientApp.java)
Aplicación cliente que se conecta al servidor.

#### [`com.chatserver.client.ClientProtocolHandler`](src/main/java/com/chatserver/client/ClientProtocolHandler.java)
Procesa las respuestas del servidor en el lado del cliente.

#### [`com.chatserver.protocol.Protocol`](src/main/java/com/chatserver/protocol/Protocol.java)
Define todas las constantes del protocolo (comandos y respuestas).

#### [`com.chatserver.user.UserManager`](src/main/java/com/chatserver/user/UserManager.java)
Gestiona la lista de usuarios conectados con sincronización thread-safe.

#### [`com.chatserver.utils.ProtocolManager`](src/main/java/com/chatserver/utils/ProtocolManager.java)
Clase abstracta base para manejo del protocolo de comunicación.

## 🧪 Requisitos de Validación

El proyecto cumple con los siguientes requisitos (según [`Instrucciones.txt`](Instrucciones.txt)):

- ✅ Servidor acepta múltiples conexiones simultáneas
- ✅ Un hilo por cliente conectado
- ✅ Validación de nicknames únicos
- ✅ Envío de mensajes privados
- ✅ Envío de mensajes grupales
- ✅ Lista de usuarios conectados
- ✅ Notificaciones de entrada/salida
- ✅ Manejo de desconexiones abruptas
- ✅ Protocolo en español
- ✅ Uso de arreglos para almacenamiento
- ✅ Sincronización para evitar condiciones de carrera

## 🔗 Repositorio Principal

- [Java Core Concepts](https://github.com/Andonys24/java-core-concepts.git): índice con referencias a todos los módulos y proyectos de la serie.

## 🤝 Contribuciones

Las mejoras y sugerencias son bienvenidas. Por favor, abre un issue o pull request.

## 📄 Licencia

Este proyecto es de uso educativo.

## 👨‍💻 Autor

**Andoni Hernández** - [@Andonys24](https://github.com/Andonys24)

---

**Última actualización**: 2025  
**Estado**: Completado y documentado