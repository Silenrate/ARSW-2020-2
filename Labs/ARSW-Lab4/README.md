# Laboratorio 4 ARSW

## Integrantes

*   Juan Sebastián Frásica Galeano
*   Daniel Felipe Walteros Trujillo

## Compile Instructions

Para compilar la aplicacion por medio de Maven es necesario abrir la consola de comandos, ubicarse en la carpeta donde tenemos la aplicacion, en este caso, la carpeta es "ARSW-Lab4\". Ya en ella debemos aplicar el comando ```mvn package```.

Como resultado obtendremos lo siguiente:

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/package.PNG)

## Run Instructions

La ejecucion de una aplicacion por medio de Maven Spring Boot consiste, al igual que la compilación, ubicarse primeramente en la carpeta de la aplicación, despues de compilar el proyecto dirigurse a la carpeta "target" y abrir la consola de comandos; seguidamente escribir el comando ```java -jar Cinema_API-1.0-SNAPSHOT.jar```, el resultado depende del programa que se va a ejecutar.

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/run.PNG)

# Cinema Book System II

## Descripción 

En este ejercicio se va a construír el componente CinemaRESTAPI, el cual permita gestionar la reserva de boletos de una prestigiosa compañia de cine. La idea de este API es ofrecer un medio estandarizado e 'independiente de la plataforma' para que las herramientas que se desarrollen a futuro para la compañía puedan gestionar los boletos de forma centralizada. El siguiente, es el diagrama de componentes que corresponde a las decisiones arquitectónicas planteadas al inicio del proyecto:

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/ClassDiagram.png)

Donde se definió que:

* El componente CinemaRESTAPI debe resolver los servicios de su interfaz a través de un componente de servicios, el cual -a su vez- estará asociado con un componente que provea el esquema de persistencia. Es decir, se quiere un bajo acoplamiento entre el API, la implementación de los servicios, y el esquema de persistencia usado por los mismos.
    
Del anterior diagrama de componentes (de alto nivel), se desprendió el siguiente diseño detallado, cuando se decidió que el API estará implementado usando el esquema de inyección de dependencias de Spring (el cual requiere aplicar el principio de Inversión de Dependencias), la extensión SpringMVC para definir los servicios REST, y SpringBoot para la configurar la aplicación:

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/CompDiag.png)

## Parte I

1. Integre al proyecto base suministrado los Beans desarrollados en el Ejercicio Anterior. Sólo copie las clases, NO los archivos de configuración. Rectifique que se tenga correctamente configurado el esquema de inyección de dependencias con las anotaciones @Service y @Autowired.

2. Modifique el bean de persistecia 'InMemoryCinemaPersistence' para que por defecto se inicialice con al menos otras 2 salas de cine, y al menos 2 funciones asociadas a cada una.

3. Configure su aplicación para que ofrezca el recurso "/cinema", de manera que cuando se le haga una petición GET, retorne -en formato jSON- el conjunto de todos los cines. Para esto:

    1. Modifique la clase CinemaAPIController teniendo en cuenta el ejemplo de controlador REST hecho con SpringMVC/SpringBoot.
     2. Haga que en esta misma clase se inyecte el bean de tipo CinemaServices (al cual, a su vez, se le inyectarán sus dependencias de persistencia y de filtrado de películas).
    3. De ser necesario modifique el método getAllCinemas(), de manera que utilice la persistencia previamente inyectada y retorne todos los cines registrados.


4. Verifique el funcionamiento de a aplicación lanzando la aplicación con maven. Y luego enviando una petición GET a:  http://localhost:8080/cinemas. Rectifique que, como respuesta, se obtenga un objeto jSON con una lista que contenga el detalle de los cines suministados por defecto.

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/getAllCinemas.PNG)

5. Modifique el controlador para que ahora, acepte peticiones GET al recurso /cinemas/{name}, el cual retorne usando una representación jSON todas las funciones del cine cuyo nombre sea {name}. Si no existe dicho cine, se debe responder con el código de error HTTP 404. Para esto, revise en la documentación de Spring, sección 22.3.2, el uso de @PathVariable. De nuevo, verifique que al hacer una petición GET -por ejemplo- a recurso http://localhost:8080/cinemas/cinemaY , se obtenga en formato jSON el conjunto de funciones asociadas al cine 'cinemaY' (ajuste esto a los nombres de cine usados en el punto 2).

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/cine1.PNG)

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/cine2.PNG)

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/cine3.PNG)

6. Modifique el controlador para que ahora, acepte peticiones GET al recurso /cinemas/{name}/{date}, el cual retorne usando una representación jSON una lista de funciones asociadas al cine cuyo nombre es {name} y cuya fecha sea {date}, para mayor facilidad se seguirá manejando el formato "yyyy-MM-dd". De nuevo, si no existen dichas funciones, se debe responder con el código de error HTTP 404.

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/funcion1.PNG)

7. Modifique el controlador para que ahora, acepte peticiones GET al recurso /cinemas/{name}/{date}/{moviename}, el cual retorne usando una representación jSON sólo UNA función, en este caso es necesario detallar además de la fecha, la hora exacta de la función de la forma "yyyy-MM-dd HH:mm". Si no existe dicha función, se debe responder con el código de error HTTP 404.

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/funcion2.PNG)

## Parte II

1. Agregue el manejo de peticiones POST (creación de nuevas funciones), de manera que un cliente http pueda registrar una nueva función a un determinado cine haciendo una petición POST al recurso ‘/cinemas/{name}’, y enviando como contenido de la petición todo el detalle de dicho recurso a través de un documento jSON. Para esto, tenga en cuenta el siguiente ejemplo, que considera -por consistencia con el protocolo HTTP- el manejo de códigos de estados HTTP (en caso de éxito o error)

2. Para probar que el recurso ‘cinemas’ acepta e interpreta correctamente las peticiones POST, use el comando curl de Unix. Este comando tiene como parámetro el tipo de contenido manejado (en este caso jSON), y el ‘cuerpo del mensaje’ que irá con la petición, lo cual en este caso debe ser un documento jSON equivalente a la clase Cliente (donde en lugar de {ObjetoJSON}, se usará un objeto jSON correspondiente a una nueva función:Con lo anterior, registre un nueva función (para 'diseñar' un objeto jSON, puede usar esta herramienta): Nota: puede basarse en el formato jSON mostrado en el navegador al consultar una función con el método GET.

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/post1.PNG)

3. Teniendo en cuenta el nombre del cine, la fecha y hora de la función y el nombre de la película, verifique que el mismo se pueda obtener mediante una petición GET al recurso '/cinemas/{name}/{date}/{moviename}' correspondiente.

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/post2.PNG)

4. Agregue soporte al verbo PUT para los recursos de la forma '/cinemas/{name}', de manera que sea posible actualizar una función determinada, el servidor se encarga de encontrar la función correspondiente y actualizarla o crearla.

Primero miramos alguna función:

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/put1.PNG)

Despues realizamos el metodo PUT sobre esa función:

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/put2.PNG)

Y al final vemos que el recurso se actualizo:

![](https://github.com/sebastianfrasic/ARSW-Lab4/blob/master/img/put3.PNG)


## Parte III

1. El componente CinemaRESTAPI funcionará en un entorno concurrente. Es decir, atederá múltiples peticiones simultáneamente (con el stack de aplicaciones usado, dichas peticiones se atenderán por defecto a través múltiples de hilos). Dado lo anterior, debe hacer una revisión de su API (una vez funcione), e identificar:

    1. Qué condiciones de carrera se podrían presentar?
    
    * Al insertar nuevas CinemaFunctions, mientras otro usuario consulta recursos que incluyan esa función, allí puede 
	ocurrir que esta consulta no tenga el nuevo cine que se agregó.
    
	* Al modificar CinemaFunctions, mientras otro usuario consulta recursos que incluyan esa función, allí puede 
	ocurrir que esta consulta no tenga los nuevos valores de esa función.	

    2. Cuales son las respectivas regiones críticas?
    
    * Cuando se realizan operaciones sobre la lista de CinemaFunctions de un cine
    
	* Cuando se realizan operaciones sobre la lista de asientos de un CinemaFunction
    
    * Cuando se realizan operaciones sobre el hashMap de todos los cines

Ajuste el código para suprimir las condiciones de carrera. Tengan en cuenta que simplemente sincronizar el acceso a las operaciones de persistencia/consulta DEGRADARÁ SIGNIFICATIVAMENTE el desempeño de API, por lo cual se deben buscar estrategias alternativas.

Decidimos solucionarlo utilizando:

* CopyOnWriteArrayList para la lista de CinemaFunction del cine y la matriz de asientos de cada CinemaFunction

* ConcurrentHashMap para los cines de InMemoryCinemaPersistence

