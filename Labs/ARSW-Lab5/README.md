# Laboratorio 5 ARSW

## Integrantes

*   Juan Sebastián Frásica Galeano
*   Daniel Felipe Walteros Trujillo

## Compile Instructions

Para compilar la aplicacion por medio de Maven es necesario abrir la consola de comandos, ubicarse en la carpeta donde tenemos la aplicacion, en este caso, la carpeta es "ARSW-Lab5\". Ya en ella debemos aplicar el comando ```mvn package```.

Como resultado obtendremos lo siguiente:

![](https://github.com/sebastianfrasic/ARSW-Lab5/blob/master/img/package.PNG)

## Run Instructions

La ejecucion de una aplicacion por medio de Maven Spring Boot consiste, al igual que la compilación, ubicarse primeramente en la carpeta de la aplicación, despues de compilar el proyecto dirigurse a la carpeta "target" y abrir la consola de comandos; seguidamente escribir el comando ```java -jar Cinema_API-1.0-SNAPSHOT.jar```, el resultado depende del programa que se va a ejecutar.

![](https://github.com/sebastianfrasic/ARSW-Lab5/blob/master/img/run.PNG)

# Heavy client - Cinema Book System

![](https://github.com/sebastianfrasic/ARSW-Lab5/blob/master/img/mock_.png)

## Descripción y objetivos

   * Crear un cliente que se conecte con el API Rest generado anteriormente de manera que se pueda interactuar con él desde el navegador.
   * Al oprimir el botón 'Get Functions', consultar las funciones de un determinado cine y una fecha dados por el usuario. Por ahora, si la consulta genera un error, sencillamente no se mostrará nada.
   * Al hacer una consulta exitosa, se debe mostrar un mensaje que incluya el nombre del cine, y una tabla con: el nombre de la película, el género, la hora de la función y un botón para abrir el detalle de la disponibilidad de la misma.
   * Al seleccionar una de las funciones se debe mostrar el dibujo de la disponibilidad de la misma. Por ahora, el dibujo será simplemente una serie de cuadrados en pantalla que representan las sillas, y dependiendo de su disponibilidad tendrán un color distinto.

## Parte I - Ajustes Backend

1. Trabaje sobre la base del proyecto anterior una vez solucionado (REST-API Cinema).

2. Incluya dentro de las dependencias de Maven los 'webjars' de jQuery y Bootstrap (esto permite tener localmente dichas librerías de JavaScript al momento de construír el proyecto).

![](https://github.com/sebastianfrasic/ARSW-Lab5/blob/master/img/dependencies.PNG)

## Parte II - Front-End - Vistas

1. Cree el directorio donde residirá la aplicación JavaScript. Como se está usando SpringBoot, la ruta para poner en el mismo contenido estático (páginas Web estáticas, aplicaciones HTML5/JS, etc) es ```src/main/resources/static```.

2. Cree, en el directorio anterior, la página index.html, sólo con lo básico: título, campo para la captura del nombre del cine, un campo de captura tipo fecha, botón de 'Get Functions', campodonde se mostrará el nombre del cine seleccionado, la tabla HTML donde se mostrará el listado de funciones (con sólo los encabezados). Recuerde asociarle identificadores a dichos componentes para facilitar su búsqueda mediante selectores.

3. En el elemento <head> de la página, agregue las referencia a las librerías de jQuery, Bootstrap y a la hoja de estilos de Bootstrap.

4. Suba la aplicación (mvn spring-boot:run), y rectifique:
   * Que la página sea accesible desde ```http://localhost:8080/index.html``.
   * Al abrir la consola de desarrollador del navegador, NO deben aparecer mensajes de error 404 (es decir, que las librerías de JavaScript se cargaron correctamente).
   
![](https://github.com/sebastianfrasic/ARSW-Lab5/blob/master/img/index1.PNG)

NOTA: Los 404 que aparecen no son debido a los javascript, sino a extensiones de chrome causadas porque el browser no esta actualizado.

## Parte III - Front-End - Lógica

1. Ahora, va a crear un Módulo JavaScript que, a manera de controlador, mantenga los estados y ofrezca las operaciones requeridas por la vista. Para esto tenga en cuenta el patrón Módulo de JavaScript, y cree un módulo en la ruta static/js/app.js.

2. Copie el módulo provisto (apimock.js) en la misma ruta del módulo antes creado. En éste agréguele más cinemas con las funciones 'quemadas' en el código.

3. Agregue la importación de los dos nuevos módulos a la página HTML (después de las importaciones de las librerías de jQuery y Bootstrap):

![](https://github.com/sebastianfrasic/ARSW-Lab5/blob/master/img/dependencies2.PNG)

4. Haga que el módulo antes creado mantenga de forma privada:
   * El nombre del cine seleccionado.
   * La fecha de las funciones a consultar
   * El listado de nombre, género y hora de las películas de las funciones del cine seleccionado. Es decir, una lista objetos, donde cada objeto tendrá tres propiedades: nombre de la película, género de la misma y hora de la función.
   
Junto con dos operaciones públicas, una que permita cambiar el nombre del cinema actualmente seleccionado y otra que permita cambiar la fecha.

5. Agregue al módulo 'app.js' una operación pública que permita actualizar el listado de las funciones,esto, a partir del nombre del cine y la fecha de la función (dados como parámetro). Para hacerlo, dicha operación debe invocar la operación 'getFunctionsByCinemaAndDate' del módulo 'apimock' provisto, enviándole como callback una función que:

   * Tome el listado de las funciones, y le aplique una función 'map' que convierta sus elementos a objetos con: el nombre, el género de la película y la hora de la función.
   
   * Sobre el listado resultante, haga otro 'map', que tome cada uno de estos elementos, y a través de jQuery agregue un elemento <tr> (con los respectvos <td>) a la tabla creada en el punto 4. Tenga en cuenta los selectores de jQuery y los tutoriales disponibles en línea. Por ahora no agregue botones a las filas generadas.
	
6. Asocie la operación antes creada (la de app.js) al evento 'on-click' del botón de consulta de la página.

7. Verifique el funcionamiento de la aplicación. Inicie el servidor, abra la aplicación HTML5/JavaScript, y rectifique que al ingresar un cine existente, y una fecha en donde estén agendadas funciones, se cargue el listado de funciones del mismo.

![](https://github.com/sebastianfrasic/ARSW-Lab5/blob/master/img/table.PNG)

## Parte IV

1. A la página, agregue un elemento de tipo Canvas, con su respectivo identificador. Haga que sus dimensiones no sean demasiado grandes para dejar espacio para los otros componentes, pero lo suficiente para poder visualizar cómodamente los asientos de la sala.

2. Al módulo app.js agregue una operación que, dado el nombre de un cine, la fecha (con hora de la función "yyyy-mm-dd hh:mm"), y el nombre de la película dados como parámetros, haciendo uso del método getFunctionsByCinemaAndDate de apimock.js y de una función callback:

   * Consulte los asientos de la función correspondiente, y con los mismos dibuje la respectiva sala del cine, haciendo uso de los elementos HTML5 (Canvas, 2DContext, etc) disponibles* Actualice con jQuery el campodonde se muestra el nombre de la película de la función que se está viendo (si dicho campo no existe, agruéguelo al DOM).
   
3. Verifique que la aplicación ahora, además de mostrar el listado de las funciones del cine, permita seleccionar una de éstas y graficar su disponibilidad. Para esto, haga que en las filas generadas para el punto 5 incluyan en la última columna un botón con su evento de clic asociado a la operación hecha anteriormente (enviándo como parámetro los nombres correspondientes).

4. Verifique que la aplicación ahora permita: consultar las funciones de un cine y graficar la disponibilidad de asientos de aquella que se seleccione.

![](https://github.com/sebastianfrasic/ARSW-Lab5/blob/master/img/index2.PNG)

5. Una vez funcione la aplicación (sólo front-end), haga un módulo (llámelo 'apiclient') que tenga las mismas operaciones del 'apimock', pero que para las mismas use datos reales consultados del API REST. Para lo anterior revise cómo hacer peticiones GET con jQuery, y cómo se maneja el esquema de callbacks en este contexto.

6. Modifique el código de app.js de manera que sea posible cambiar entre el 'apimock' y el 'apiclient' con sólo una línea de código.

Primero, se creo una variable dentro de app.js que asigna la respectiva api que se va a usar:

![](https://github.com/sebastianfrasic/ARSW-Lab5/blob/master/img/modulo1.PNG)

Después para usar el modulo escogido, se utiliza la siguiente linea con el método respectivo.

![](https://github.com/sebastianfrasic/ARSW-Lab5/blob/master/img/modulo2.PNG)

Para que esto funciones, tanto el apiclient como el apimock, deben tener a su modulo nombrado "api".

![](https://github.com/sebastianfrasic/ARSW-Lab5/blob/master/img/modulo3.PNG)

7. Revise la documentación y ejemplos de los estilos de Bootstrap (ya incluidos en el ejercicio), agregue los elementos necesarios a la página para que sea más vistosa, y más cercana al mock dado al inicio del enunciado.
