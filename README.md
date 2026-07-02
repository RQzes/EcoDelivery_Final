\# EcoDelivery Final



EcoDelivery es una aplicación de escritorio desarrollada en Java Swing y MySQL para la gestión de repartos ecológicos.



\## Funcionalidades principales



\- Inicio de sesión de usuarios.

\- Registro de usuarios.

\- Gestión de clientes.

\- Gestión de vehículos ecológicos.

\- Gestión de pedidos.

\- Gestión de entregas.

\- Procesamiento de entregas mediante procedimiento almacenado en MySQL.



\## Estructura del proyecto



El proyecto está organizado en paquetes:



\- `Conexion`: contiene la conexión a MySQL mediante JDBC.

\- `Modelo`: contiene las clases que representan las entidades del sistema.

\- `Dao`: contiene las clases que acceden a la base de datos.

\- `Vista`: contiene las interfaces gráficas desarrolladas con Java Swing.

\- `Pruebas`: contiene clases de prueba.



\## Base de datos



La aplicación utiliza una base de datos MySQL llamada `ecodelivery`.



Tablas principales:



\- `usuario`

\- `cliente`

\- `vehiculo`

\- `pedido`

\- `entrega`

\- `factor\_vehiculo`



\## Operación principal



La operación principal del sistema es procesar una entrega.



Esta operación se realiza mediante el procedimiento almacenado:



`sp\_procesar\_entrega`



El procedimiento realiza:



1\. Validación del pedido.

2\. Validación del vehículo disponible.

3\. Cálculo del tiempo estimado.

4\. Cálculo del CO2 evitado.

5\. Registro de la entrega.

6\. Actualización del estado del pedido.

7\. Actualización del estado del vehículo.

8\. Uso de transacción con `COMMIT` y `ROLLBACK`.



\## Tecnologías utilizadas



\- Java

\- Java Swing

\- MySQL

\- JDBC

\- Maven

\- Git y GitHub



\## Ejecución



Para ejecutar el proyecto se debe configurar la base de datos MySQL y luego ejecutar la aplicación desde NetBeans o mediante Maven.



Usuario de prueba:



\- Usuario: `admin`

\- Contraseña: `12345`

