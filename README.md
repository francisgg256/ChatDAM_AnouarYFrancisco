# ChatDAM - Aplicación de Mensajería Corporativa

Este proyecto corresponde a la Tarea online AD06 - Programación de componentes de acceso a datos del ciclo de Desarrollo de Aplicaciones Multiplataforma (DAM).

Desarrollado por Anouar Sabri Achahid y Francisco Jose Gomez Garcia.

## Descripción del Proyecto
ChatDAM es una aplicación de escritorio de mensajería interna diseñada para entornos corporativos. Permite la comunicación en tiempo real entre empleados distribuidos en distintas oficinas mediante una arquitectura de Cliente-Servidor.

La aplicación garantiza la persistencia de datos en la nube y la transmisión instantánea de mensajes gracias a la combinación de una API REST y Sockets UDP (Multicast).

## Características y Funcionalidades
- Autenticación Segura: Login validado contra base de datos. Las contraseñas viajan y se almacenan cifradas utilizando el algoritmo SHA-256.
- Sistema de Roles:
  - EMPLEADO: Puede iniciar sesión, ver el historial, recibir y enviar mensajes.
  - ADMINISTRADOR: Tiene las mismas opciones y además acceso a un menú exclusivo para Dar de Alta a nuevos empleados.
- Tiempo Real: Los mensajes se envían de forma instantánea al resto de usuarios conectados mediante un grupo Multicast.
- Persistencia en la Nube: Historial de mensajes y credenciales gestionadas a través de MongoDB Atlas.

## Tecnologías Utilizadas
El proyecto está dividido en dos submódulos gestionados con Maven:

### Backend (/servidor)
* Spring Boot: Framework principal para la creación de la API REST.
* Spring Data MongoDB: Conexión e inyección de dependencias con la base de datos documental.
* MongoDB Atlas: Base de datos alojada en la nube (NoSQL).

### Frontend (/cliente)
* JavaFX: Creación de la interfaz gráfica de usuario (GUI).
* FXML & CSS: Diseño y maquetación de las vistas (Login, Chat, GestionEmpleados, ErrorLogin).
* Gson: Librería de Google para la serialización y deserialización de objetos JSON.
* MulticastSocket: Sockets UDP para la comunicación Peer-to-Peer en tiempo real.

## Arquitectura de Comunicación
1. Inicio de sesión y Registro: El Cliente realiza peticiones HTTP (POST) a la API REST del Servidor.
2. Historial de Chat: El Cliente realiza una petición HTTP (GET) para recuperar los últimos 10 mensajes.
3. Envío de un Mensaje: Se realizan dos acciones simultáneas:
   * Se difunde el mensaje por la red local a la IP de Multicast (por ejemplo, 230.0.0.0) para que aparezca al instante en las pantallas de los demás terminales.
   * Se envía por HTTP (POST) a la API REST para que quede guardado de forma permanente en MongoDB.

## Instrucciones de Instalación y Ejecución

### Requisitos Previos
* Java JDK 17 o 21 instalado.
* Maven configurado en el entorno de desarrollo.

### 1. Ejecutar el Servidor (Backend)
1. Navegar a la carpeta /servidor.
2. Abrir el archivo src/main/resources/application.properties y asegurarse de que la cadena de conexión URI de MongoDB Atlas es correcta.
3. Ejecutar la clase principal ServidorApplication.java.
4. Comprobar en la consola del IDE que el servidor ha arrancado en el puerto 8080.

### 2. Ejecutar el Cliente (Frontend)
1. Navegar a la carpeta /cliente.
2. Abrir la clase ApiService.java y verificar que la IP base apunta correctamente a la máquina donde se ejecuta el servidor (por defecto http://localhost:8080 si se prueba en la misma máquina, o la dirección IPv4 correspondiente en red local).
3. Ejecutar la clase principal Launcher.java o Main.java.
4. Datos de prueba: Iniciar sesión con algún usuario registrado en la base de datos (Nota: Asegúrese de que la contraseña en la base de datos esté guardada en formato SHA-256).

## Criterios de Evaluación Cubiertos (AD06)
* Uso correcto de Git y GitHub con commits frecuentes y significativos.
* Organización mediante proyecto estructurado con Maven.
* Uso de programación orientada a componentes.
* Creación y consumo de una API REST funcional.
* Persistencia en base de datos documental NoSQL empleando MongoDB Atlas.
* Seguridad de la información mediante el cifrado de contraseñas.
