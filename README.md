# CRUD-Gateway-Telecomunicaciones
Oficialmente titulado Sistema Interno de Gestión de información para Gateway Telecomunicaciones S.A. de C.V.

**Acerca de...**

Este proyecto fue parte de mis prácticas profesionales con una estadía profesional con duración de 4 meses, durante el periodo de Enero - Abril del año 2020, 
en representación como alumno de la Universidad Tecnológica Fidel Velázquez, mismo que sirvió de base para la realización de mi tésis profesional que para 
obtener el grado de Ingeniería en Tecnologías de la Información: Área Sistemas Informáticos.

**Objetivo del Proyecto:**

Este fue un proyecto desarrollado como práctica profesional en la empresa de telecomunicaciones Gateway con socios Huawei, Telcel, ALTAN.
El objetivo del mismo era cubrir la necesidad de poder extraer la información de las hojas de cálculo excel proporcionadas por el sitio web de huawei ISDP, 
las cuales tenían información relacionada a los proyectos que se llevaban a cabo cada día, semana, mes o año, dependiendo el filtro especificado 
en la plataforma de Huawei para la descarga de hojas de cálculo,
los clientes en este caso deseaban que esta información fuese más local, por lo que surge la necesidad de contar con un sistema que pudiese respaldar los datos
de las hojas de cálculo con grandes cantidades de información y mantenerlas en una Base de Datos local.

**Tecnologías usadas:**
  
    - Librería de Apache POI para el manejo de las hojas de cálculo de Excel en Java, configurado en el sistema solo 
    archivos con extensión (.xlsx).
    - Conector MySQL para Java.
    - Java SE 8.
    - MySQL (Línea de Comandos).
    - PHPMyAdmin para la normalización del archivo SQL que crea la BD al instalar el software.

**Entorno de Desarrollo IDE utilizado:**

    - NetBeans 8.

**Requerimientos:**
1. Un sistema de escritorio con opciones CRUD para todas las áreas, esto debido a que Huawei no permitía editar la información en la plataforma web.
2. Cada área tendrá acceso a una parte del sistema acorde a la categpría de su área.
3. Se requería un sistema interno que pudiese obtener la información de estas hojas de cálculo, misma que sería procesada por el sistema que desarrollé y llevada a una base de datos por medio de Java como gestor visual y MySQL como Base de Datos.
4. Categorías de Áreas (Ventanas):
  
  **1) Login:**
    
        - El sistema debía contar debidamente con un inicio de sesión.
  
  ![Ventana de Inicio de Sesión](https://raw.githubusercontent.com/EduardoUT/CRUD-Gateway-Telecomunicaciones/master/assets/Capturas-GatewaySW/Login/Login.PNG)
  
  **2) Administrador:** 
       
       - Se encargará de gestionar a los usuarios del sistema y administradores o jefes de área, asignando y controlando los inicios de sesión y 
       recuperación de contraseñas por medio de categorías de usuarios acorde a sus áreas o roles en la empresa.
       
  ![]()
  ![Ventana del Administrador de Usuarios: Administradores](https://raw.githubusercontent.com/EduardoUT/CRUD-Gateway-Telecomunicaciones/master/assets/Capturas-GatewaySW/Administrador/Gesti%C3%B3nAdministradores.PNG)
  ![Ventana del Administrador de Usuarios: Operadores](https://raw.githubusercontent.com/EduardoUT/CRUD-Gateway-Telecomunicaciones/master/assets/Capturas-GatewaySW/Administrador/Gesti%C3%B3nUsuarios.PNG)
  
     
  **3) Administrador Facturación:** 
    
    - Controla la asignación de Proyectos a Operadores de su área, la información de las asignaciones es la que es extraida de las
       hojas de Excel a la Base de Datos.
    - Se encargará de respaldar las hojas de Excel de la plataforma Huawei ISDP al sistema con un botón de exportación.
    - Puede eliminar registro u actualizar o ingresar manualmente por medio de un formulario.
    - Realiza también la facturación de los proyectos.
  
  ![Ventana del Administrador de Facturación: Asignación de Proyectos](https://raw.githubusercontent.com/EduardoUT/CRUD-Gateway-Telecomunicaciones/master/assets/Capturas-GatewaySW/Admin-Facturaci%C3%B3n/Gesti%C3%B3nAsignaciones.PNG)
  ![Ventana del Administrador de Facturación: Historial de Asignaciones](https://raw.githubusercontent.com/EduardoUT/CRUD-Gateway-Telecomunicaciones/master/assets/Capturas-GatewaySW/Admin-Facturaci%C3%B3n/HistorialAsignaciones.PNG)
  ![Ventana del Administrador de Facturación: Gestión de Facturas (Proyectos)](https://raw.githubusercontent.com/EduardoUT/CRUD-Gateway-Telecomunicaciones/master/assets/Capturas-GatewaySW/Admin-Facturaci%C3%B3n/Gesti%C3%B3nFacturas.PNG)
  ![Ventana del Administrador de Facturación: Principal](https://raw.githubusercontent.com/EduardoUT/CRUD-Gateway-Telecomunicaciones/master/assets/Capturas-GatewaySW/Admin-Facturaci%C3%B3n/Principal.PNG)
  
  **4) Administrador Cierre de Proyectos:**
  
    - Realiza el ajuste de precios del proyecto al ser finalizado, su interfaz es similar al del Administrador de Facturación, a excepción de
      la limitación de editar información de Facturación.
      
  ![Ventana del Administrador de Cierre de Proyectos](https://raw.githubusercontent.com/EduardoUT/CRUD-Gateway-Telecomunicaciones/master/assets/Capturas-GatewaySW/Admin-CierreProyectos/Principal.PNG)
  
      
  **5)Administrador Almacén:**
  
    - Registrar los productos e insumos tanto entradas como salidas de los mismos en una Base de Datos con fecha, usando mi sistema para el control
      de gastos, permitiendo editar, ingresar, eliminar y actualizar los insumos.
     
  ![Ventana del Administrador de Almacén](https://raw.githubusercontent.com/EduardoUT/CRUD-Gateway-Telecomunicaciones/master/assets/Capturas-GatewaySW/Admin-Almac%C3%A9n/Principal.PNG)
   




