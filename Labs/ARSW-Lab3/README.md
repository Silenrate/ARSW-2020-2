# Laboratorio 3 ARSW

## Integrantes

*   Juan Sebastián Frásica Galeano
*   Daniel Felipe Walteros Trujillo

## Compile Instructions

Para compilar la aplicacion por medio de Maven es necesario abrir la consola de comandos, ubicarse en la carpeta donde tenemos la aplicacion, en este caso, la carpeta es "ARSW-Lab3\CINEMA I" o "ARSW-Lab3\GRAMMAR-CHECKER". Ya en ella debemos aplicar el comando ```mvn package```.

Como resultado obtendremos lo siguiente:

![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/package.PNG)

## Run Instructions

La ejecucion de una aplicacion por medio de Maven consiste, al igual que la compilación, ubicarse primeramente en la carpeta de la aplicación y abrir la consola de comandos; seguidamente escribir el comando ```mvn exec:java -Dexec.mainclass="<paquete de la clase main>.<clase que ejecuta el main>"```, el resultado depende del programa que se va a ejecutar.

En el caso de la parte 1, es ```mvn exec:java -Dexec.mainClass=edu.eci.arsw.springdemo.ui.Main```

![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/run1.PNG)

Y para el caso de la parte 2, es ```mvn exec:java -Dexec.mainClass=edu.eci.arsw.cinema.Main```

![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/run2.PNG)

## Introduction to Spring and Configuration using annotations

### Part I - Basic workshop

To illustrate the use of the Spring framework, and the development environment for its use through Maven (and NetBeans), the configuration of a text analysis application will be made, which makes use of a grammar verifier that requires a spelling checker. The grammar checker will be injected, at the time of execution, with the spelling checker required (for now, there are two available: English and Spanish).

1. Open the project sources in NetBeans.

2. Review the Spring configuration file already included in the project (src / main / resources). It indicates that Spring will automatically search for the 'Beans' available in the indicated package.

3. Making use of the Spring configuration based on annotations mark with the annotations @Autowired and @Service the dependencies that must be injected, and the 'beans' candidates to be injected -respectively-:

  * GrammarChecker will be a bean, which depends on something like 'SpellChecker'.
  
  * EnglishSpellChecker and SpanishSpellChecker are the two possible candidates to be injected. One must be selected, or another, but NOT both (there would be dependency resolution conflict). For now, have EnglishSpellChecker used. 
  
  Para que la inyección de dependencias de Spring funcioné vamos a usar las anotaciones ```@Service```, ```@Autowired``` y ```@Qualifier```.
  
  La anotación ```@Service``` se usa para indicar  las clases que son un servicio sin necesidad de modificar el applicationContext.xml
  
  ![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/grammar.PNG)
  
  ![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/english3.PNG)
  
  La anotación ```@Autowired``` sustituye la declaración de los atributos del bean en el applicationContext.xml y el atributo ```@Qualifier``` nos indica la clase que va a ser inyectada por medio del nombre que se le dio como componente (en este caso como servicio).
  
  ![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/english2.PNG)
  
4. Make a test program, where an instance of GrammarChecker is created by Spring, and use it.

 El programa usado para probar la instancia del GrammarChecker se implementó de la siguiente forma:
 
 ![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/main1.PNG)
 
 Y obtuvo como resultado lo siguiente:
 
 ![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/english.PNG)

### Part II

Modify the configuration with annotations so that the Bean 'GrammarChecker' now makes use of the SpanishSpellChecker class (so that GrammarChecker is injected with EnglishSpellChecker instead of SpanishSpellChecker.) Verify the new result.

Para modificar la inyeccion de dependencias solo hubo que realizar dos cosas:

1. Incluir la anotación ```@Service``` en la clase SpanishSpellChecker:

![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/spanish3.PNG)

2. Modificar el ```@Qualifier``` de la clase GrammarChecker:

![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/spanish2.PNG)

Obteniendo como resultado:

![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/spanish.PNG)

## Cinema Book System

### Description

In this exercise we will build a class model for the logical layer of an application that allows managing the sale of cinema tickets for a prestigious company.

![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/CinemaClassDiagram.png)

### Part I

1. Configure the application to work under a dependency injection scheme, as shown in the previous diagram. The above requires:

 * Add the dependencies of Spring. 
 * Add the Spring configuration. 
 * Configure the application -by means of annotations- so that the persistence scheme is injected at the moment of creation of the 'CinemaServices' bean. 
 
 Para configurar la inyección de dependencias se realizó lo siguiente:
 
 * Se creo el archivo applicationContext.xml dentro de la ruta src/main/resources de la siguiente forma:
 
 ![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/xml.PNG)
 
 * Se pusieron las siguientes anotaciones en las clases CinemaServices e InMemoryCinemaPersistence respectivamente:
 
  ![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/services.PNG)
  
  ![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/inmemory.PNG)

2. Complete the getCinemaByName (), buyTicket (), and getFunctionsbyCinemaAndDate () operations. Implement everything required from the lower layers (for now, the available persistence scheme 'InMemoryCinemasPersistence') by adding the corresponding tests in 'InMemoryPersistenceTest'.

3. For later queries, we want to implement two functionalities:

 * A method 'getFunctionsbyCinemaAndDate' that allows to obtain all the functions of a certain cinema for a certain date. 
 
 * Allow the purchase or reservation of tickets for a certain position of chairs in the room through the 'buyTicket' method. 
 
4. Make a program in which you create (through Spring) an instance of CinemaServices, and rectify the functionality of it: register cinemas, consult cinemas, obtain the functions of certain cinema, buy / book tickets, etc.

![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/2main.PNG)

5. It is wanted that the consultations realize a filtering process of the films to exhibit, said filters look for to give him the facility to the user to see the most suitable films according to his necessity. Adjust the application (adding the abstractions and implementations that you consider) so that the CinemaServices class is injected with one of two possible 'filters' (or possible future filters). The use of more than one at a time is not contemplated:

 * (A) Filtered by gender: Allows you to obtain only the list of the films of a certain genre (of a certain cinema and a certain date) (The genre enters by parameter). 
 
 * (B) Filtering by availability: Allows you to obtain only the list of films that have more than x empty seats (of a certain cinema and a certain date) (The number of seats is entered per parameter).
 
 Para implementar estos filtros se creo la interfaz CinemaFilter y el metodo getFilteredFunctions() en la clase CinemaServices con los parámetros del nombre del cine, la fecha de la función y el respectivo valor de filtro(en String).
 
 ![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/filtrar.PNG)
 
 Para configurar la inyección se realizaron dos cosas:
 
  * Se añadió el atributo cinemaFilter dentro de la clase CinemaServices con sus respectivas anotaciones:
  
  ![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/filter.PNG)
  
  * Dentro de cada implementación del filtro se puso su respectiva anotación de servicio:
  
  ![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/gender.PNG)
  
  ![](https://github.com/sebastianfrasic/ARSW-Lab3/blob/master/img/availability.PNG)
 
6. Add the corresponding tests to each of these filters, and test their operation in the test program, verifying that only by changing the position of the annotations -without changing anything else-, the program returns the list of films filtered in the manner (A ) or in the way (B).
