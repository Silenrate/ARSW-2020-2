# Laboratorio 1 ARSW

## Integrantes

*   Juan Sebastián Frásica Galeano
*   Daniel Felipe Walteros Trujillo

## Compile Instructions

Para compilar la aplicacion por medio de Maven es necesario abrir la consola de comandos, ubicarse en la carpeta donde tenemos la aplicacion, en este caso, la carpeta es "ARSW-Lab1\BLACKLISTSEARCH" o "ARSW-Lab1\SNAKE_RACE". Ya en ella debemos aplicar el comando ```mvn package```.

Como resultado obtendremos lo siguiente:

![](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/package.PNG)

## Run Instructions

La ejecucion de una aplicacion por medio de Maven consiste, al igual que la compilación, ubicarse primeramente en la carpeta de la aplicación y abrir la consola de comandos; seguidamente escribir el comando ```mvn exec:java -Dexec.mainclass="<paquete de la clase main>.<clase que ejecuta el main>.main"```, el resultado depende del programa que se va a ejecutar.

## Black List Search

### Part I - Introduction to threads in JAVA
1. In agreement with the lectures, complete the classes CountThread, so that they define the life cycle of a thread that prints the numbers between A and B on the screen.
2. Complete the main method of the CountMainThreads class so that: 
    *   Create 3 threads of type CountThread, assigning the first interval [0..99], the second [99..199], and the third [200..299]. 
    *   Start all three threads with start(). 
    *   Run and check the output on screen. 
    *   Change the beginning with start() to run(). How does the output change? Why?

Cuando llamamos al método run() del thread, se ejecuta todo el fragmento de código que hay en este. Como lo llamamos con diferentes intervalos, se 
observa que el imprime los números entre A y B secuencialmente, es decir, de menor a mayor empezando con el primer intervalo.

![](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/run.PNG)

Cuando llamamos al método start() del thread, creamos una nueva instancia de Hilo que va a llamar al método run() desde un intervalo A hasta B. Por eso, el 
resultado que se ve en pantalla es diferentes intervalos de números, pero no en un orden secuencial. Esto ocurre porque cada hilo creado empieza a mostrar sus 
propios resultados al mismo tiempo.

![](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/start.PNG)

### Part II - Black List Search Exercise

For an automatic computer security surveillance software, a component is being developed to validate the IP addresses in several thousands of known malicious blacklists (of malicious hosts), and to report those that exist in at least five of said lists.

Said component is designed according to the following diagram, where:

* HostBlackListsDataSourceFacade is a class that offers a facade for queries in any of the N registered blacklists (method 'isInBlacklistServer'), and which also allows a report to a local database of when an IP address is considered dangerous. This class is NOT MODIFIED, but it is known to be 'Thread-Safe'.

* HostBlackListsValidator is a class that offers the checkHost method, which, through the HostBlackListDataSourceFacade class, validates a given host in each of the blacklists. In this method is considered the policy that when a HOST is found in at least five blacklists, it will be registered as not reliable, or as reliable otherwise. Additionally, it will return the list of the numbers of the blacklists where the HOST was registered.

![](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/Model.png)

When using the module, the evidence that the registration was made as reliable or not reliable is given by the messages of LOGs:

![](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/logs.PNG)

The test program provided (Main), takes only a few seconds to analyze and report the address provided (200.24.34.55), since it is registered more than five times in the first servers, so it does not need to cover all of them. However, doing the search in cases where there are NO reports, or where they are scattered in the thousands of blacklists, takes a lot of time.

This, like any search method, can be seen as a shamefully parallel problem, since there are no dependencies between one part of the problem and another.

To refactor this code, and have it exploit the multi-core capability of the computer's CPU, do the following:

1.  Create a Thread class that represents the life cycle of a thread that searches for a segment of the pool of available servers. Add to that class a method that allows you to ask the instances of it (the threads) how many occurrences of malicious servers it has found or found.
2.  Add to the checkHost method an integer parameter N, corresponding to the number of threads between which the search will be carried out (remember to take into account if N is even or odd!). Modify the code of this method so that it divides the search space between the indicated N parts, and parallels the search through N threads. Have that function wait until the N threads finish solving their respective sub-problem, add the occurrences found for each thread to the list that returns the method, and then calculate (adding the total number of occurrences found for each thread) if the Number of occurrences is greater than or equal to BLACK_LIST_ALARM_COUNT. If this is the case, in the end the host MUST be reported as reliable or not reliable, and the list should be shown with the numbers of the respective blacklists. To achieve this wait behavior, review the join method of the Java concurrency API. Also keep in mind:
    *  Inside the checkHost method the LOG must be kept informing, before returning the result, the number of revised blacklists VS. the number of total blacklists (line 60). It must be guaranteed that said information is true under the new parallel processing scheme proposed.
    *  It is known that HOST 202.24.34.55 is blacklisted in a more dispersed way, and that host 212.24.24.55 is NOT on any blacklist.

### Part III - Discussion

The strategy of parallelism previously implemented is inefficient in certain cases, since the search is still carried out even when the N threads (as a whole) have already found the minimum number of occurrences required to report to the server as malicious. How could the implementation be modified to minimize the number of queries in these cases? What new element would this bring to the problem?

Al usar el parametro BLACK_LIST_ALARM_COUNT como variable comun para todos los threads, este problema se reduce bastante, ya que en vez de considerarse como un limite para cada fragmento de consultas se usa como un conjunto para todas las consultas ya realizadas.

El usar de esta forma esa variable trae consigo una condición de carrera, ya que la actualización de ese entero al descubrir una aparición de la IP dentro de una lista negra no es thread-safe, en este caso se puede utilizar la clase Atomic Integer para desaparecer el problema.

### Part IV - Performance Evaluation

From the above, implement the following sequence of experiments to perform the validation of dispersed IP addresses (for example 202.24.34.55), taking the execution times of them (be sure to do them on the same machine):

1.  Single thread. 
2.  As many threads as processing cores (have the program determine this using the Runtime API). 
3.  So many threads as double processing cores. 
4.  50 threads.
5.  100 threads    

When starting the program, run the jVisualVM monitor, and as the tests run, check and record the CPU and memory consumption in each case.     

* Con un hilo – ip 202.24.34.55

![unhilo](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/1hilo.PNG)

![runhilo](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/1r.PNG)

* Con número de hilos igual a número de procesadores(4) – ip 202.24.34.55

![cuatrohilos](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/4hilos.PNG)

![rcuatrohilos](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/4r.PNG)

* Con número de hilos igual al doble  número de procesadores(8) – ip 202.24.34.55

![ochohilos](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/8hilos.PNG)

![rochohilos](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/8r.PNG)

* Con Numero de hilos igual a 50 - ip 202.24.34.55

![50hilos](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/50hilos.PNG)

![r50hilos](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/50r.PNG)

* Con Numero de hilos igual a 100 – ip 202.24.34.55

![100hilos](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/100hilos.PNG)

![r100hilos](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/100r.PNG)


With the above, and with the given execution times, make a graph of solution time vs. Number of threads. Analyze and hypothesize with your partner for the following questions (you can take into account what was reported by jVisualVM):

![graph](https://github.com/sebastianfrasic/ARSW-Lab1/blob/master/img/graph.PNG)

1. According to Amdahls law, where S(n) is the theoretical improvement of performance, P the parallel fraction of the algorithm, and n the number of threads, the greater n, the better this improvement should be. Why is the best performance not achieved with the 500 threads? How is this performance compared when using 200 ?.  

La ley de Amdahls dice que podemos mejorar el rendimiento de un sistema si mejoramos el rendimiento de una de sus procesos. Cuando usamos hilos en el programa Black List Search, estamos mejorando el tiempo de ejecución de cada uno de los subprocesos que tendrá nuestra solución, es decir, el tiempo de los hilos. Por lo cual, entre mayor sea el número de hilos que usemos, habrá un mejor rendimiento en nuestro programa.

Al observar los resultados, vemos que empieza a haber una constante en el tiempo de ejecución despues de 8 hilos, dado que el programa finaliza al encontrar 5 apariciones de la IP en las cuentas negras, esta constante casi no se nota.

 
2. How does the solution behave using as many processing threads as cores compared to the result of using twice as much?

Al usar cuatro hilos se demora 18 segundos y al usar ocho se demora solo 2 segundos, esto demuestra una gran reducción en la ejecucioó del programa. Sin embargo, hay que considerar que el límite de apariciones de cuentas negras es una variable compartida y con el bajo valor de 5.

En caso de que no tuvieramos esta variable, el tiempo de ejecución también resultaría constante a mayor cantidad de hilos, pero en vez de tener valores aproximados a 2 segundos serian de mayor tiempo.

3. According to the above, if for this problem instead of 100 threads in a single CPU could be used 1 thread in each of 100 hypothetical machines, Amdahls law would apply better ?. If x threads are used instead of 100/x distributed machines (where x is the number of cores of these machines), would it be improved? Explain your answer.

No habría una mejora en el rendimiento del sistema porque al momento de combinar los resultados de cada una de las diferentes máquinas estaríamos consumiendo recursos 
físicos que ralentizarían nuestro programa. Además, al analizar los casos anteriores, nos damos cuenta que en la gráfica de Número de Threads vs Tiempo después de usar 
8 hilos; la mejora del rendimiento de nuestro programa es casi constante.

Podemos concluir que hasta que cuando la cantidad de hilos excede por más del doble la cantidad de procesadores de la CPU, el tiempo se vuelve constante en vez de reducirse más; esto debido al tiempo que le toma a cada procesador realizar sus respectivos cambios de contexto.

## Snake Race

### Part I

Control threads using wait/notify.
 1. Download the project PrimeFinder. this is a program that calculates prime numbers beetween 0 and M (Control.MAXVALUE),concurrently, distributing the searching of them between n (Control.NTHREADS) independent threads.
 
 2.Modify the application in such way that each t milliseconds of thread execution, all the threads stop and show the number of primes found until that moment. Then, you have to wait until press ENTER in order to resume the threads execution.Use the synchronization mechanisms given by java (wait y notify, notifyAll).
 
Note that:
   * The synchronized statement is used in order to get exclusive access to an object
   
   * The statement A.wait executed in a B thread set it to suspended mode (Independently that objective A is being used as 'lock') To resume, other active thread can resume B calling notify () to the object used as 'lock' ( in this case A)
   
   * The notify() statement, wakes the first thread  up who called wait() on the object
   
   * The notifyAll() instruction, wake  every thread up that are waiting for the object

### Part II

SnakeRace is an autonomous version, multi-snake of the famous game called 'snake' based on the Joao Andrade´s project-this exercise is a fork thereof.

   * N snakes works as an autonomous way.
   
   * The collision concept does not exists among them. The only way that they die is by crashing against a wall.
   
   * There are mice distributed along the game. As in the classic game, each time a snake eats  a mouse, it grows.
   
   * There are some points (red arrows) that teleport the snakes

   * The rays causes that the snake increase its speed

### Part III

 1. Analyse the code in order to understand how the threads are being used to create an autonomous behavior in the N snakes.
 
 2. Accordingly, and using the game logic, identify and write clearly (ANSWERS.txt file).
 
   * Possible race conditions
   
   * An incorrect or inappropriate use of collections, considering its concurrent handling(For this increase the game speed and execute it multiples times until an error has been raised).
   
   * Unnecessary use of active waits

   #### Posibles condiciones de carrera

   *  La asignación incorrecta de que 2 serpientes al mismo tiempo consigan el mismo poder, ratón o choquen con la misma barrera.

   *  Un error de asignación del espacio cuando 2 serpientes se mueven al mismo lugar y alguna crece.

   #### Uso inapropiado de colecciones

   *  Ya que para dibujar los componentes se examina todo el cuerpo de cada serpiente, ocurren errores en el momento en el que esta variable se modifica por el crecimeiento de la serpiente.
   
   #### Uso inapropiado de esperas activas
  
   *   Para notificar el estado final del juego, se revisa constante e innecesariamente el estado de las serpientes.

 3. Identify critical regions associated with race conditions, and do something in order to eliminate them. Note that you have to synchronize strictly needed. In the answers document suggest the solution proposed for each item of the point 2. As the same way note that you don´t have to add more race conditions
 
   ### Soluciones
  
  *  Posibles condiciones de carrera: Cuando la serpiente se va a mover, pone un bloqueo sobre la celda a la que se va a mover para verificar si hay algo, de esta forma, es imposible que dos serpientes consigan el mismo poder.
  *  Uso inapropiado de colecciones: Revisando el comportamiento del cuerpo de la serpiente en una Linked List decidimos convertirla en una Concurrent Linked Deque, esto evito que al dibujar hubiera errores de concurrencia.
  *  Uso inapropiado de esperas activas: Si la ultima serpiente notifica el estado final al morir, desaparece la necesidad de evaluar esto constantemente, aunque el estado de la ultima serpiente viva se muestra en RUNNING, ya que esta fue la que notificó.
 
 4. As you can see, the game is incomplete. Write code in order to implement functionallities through buttons in the GUI to start / Pause / Resume the game: start the game if it has not started, Pause the game if it is on, Resume the game if it is suspended. Keep in mind:
 
   * When the game has been paused, in some point of the screen you have to show 

      * The longest snake
      
      * The worst snake:(the first snake  dead)
   * Remember that the pause of the snakes are not instantanious, and you have to guarantee that all the information showed is consistent
