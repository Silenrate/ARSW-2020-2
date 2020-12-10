# Laboratorio 2 ARSW

## Integrantes

*   Juan Sebastián Frásica Galeano
*   Daniel Felipe Walteros Trujillo

## JAVA IMMORTALS

Este laboratorio tiene como fin que el estudiante conozca y aplique conceptos propios de la programación concurrente, además de estrategias que eviten condiciones de carrera.

## Compile Instructions

Para compilar la aplicación por medio de Maven es necesario abrir la consola de comandos, ubicarse en la carpeta donde tenemos la aplicación, en este caso, la carpeta es "\ARSW-Lab2\". Ya en ella debemos aplicar el comando ```mvn package```.

Como resultado obtendremos lo siguiente:

![](https://github.com/sebastianfrasic/ARSW-Lab2/blob/master/img/package.PNG)

## Run Instructions

La ejecución de una aplicación por medio de Maven consiste, al igual que la compilación, ubicarse primeramente en la carpeta de la aplicación y abrir la consola de comandos; seguidamente escribir el comando ```mvn exec:java -Dexec.mainclass="<paquete de la clase main>.<clase que ejecuta el main>"```, el resultado depende del programa que se va a ejecutar.

En el caso de la parte 1, es ```mvn exec:java -Dexec.mainClass=edu.eci.arst.concprg.prodcons.StartProduction```

![](https://github.com/sebastianfrasic/ARSW-Lab2/blob/master/img/run1.PNG)

Y para el caso de la parte 2, es ```mvn exec:java -Dexec.mainClass=edu.eci.arsw.highlandersim.ControlFrame```

![](https://github.com/sebastianfrasic/ARSW-Lab2/blob/master/img/run2.PNG)

### Part I - Before finishing class

Thread control with wait/notify. Producer/consumer
1. Check the operation of the program and run it. While this occurs, run jVisualVM and check the CPU consumption of the corresponding process. Why is this consumption? Which is the responsible class?

![](https://github.com/sebastianfrasic/ARSW-Lab2/blob/master/img/cpu.PNG)

Este consumo es debido a que los threads de consumidor o productor están ejecutándose constantemente dentro de un loop infinito, la mayor clase responsable es el consumidor ya que este no posee algún sistema de espera como el productor que espera 1 milisegundo para aliviar el estrés de la CPU.

2. Make the necessary adjustments so that the solution uses the CPU more efficiently, taking into account that - for now - production is slow and consumption is fast. Verify with JVisualVM that the CPU consumption is reduced.

![](https://github.com/sebastianfrasic/ARSW-Lab2/blob/master/img/cpu2.PNG)

Para reducir el estrés de la CPU, la nueva implementación del consumidor aguarda a que exista algún elemento en la cola para después retirarlo.

![](https://github.com/sebastianfrasic/ARSW-Lab2/blob/master/img/consumidor.PNG)

3. Make the producer now produce very fast, and the consumer consumes slow. Taking into account that the producer knows a Stock limit (how many elements he should have, at most in the queue), make that limit be respected. Review the API of the collection used as a queue to see how to ensure that this limit is not exceeded. Verify that, by setting a small limit for the 'stock', there is no high CPU consumption or errors.

![](https://github.com/sebastianfrasic/ARSW-Lab2/blob/master/img/cpu3.PNG)

Para controlar la capacidad de la cola concurrente, esta clase tiene un constructor que tiene de parámetro su límite de elementos.

![](https://github.com/sebastianfrasic/ARSW-Lab2/blob/master/img/limite.PNG)

Y para controlar que el productor no incorpore elementos fuera del límite, existe el método ```offer``` que inserta el elemento y retorna true en caso de que lo haya logrado, es mejor usarlo al método ```add``` que genera una excepción para determinar más fácilmente si el elemento fue incluido en la cola.

![](https://github.com/sebastianfrasic/ARSW-Lab2/blob/master/img/productor.PNG)

### Part II - Inmortals

Synchronization and Dead-Locks.

![](https://github.com/sebastianfrasic/ARSW-Lab2/blob/master/img/inmortal.PNG)

1. Review the “highlander-simulator” program, provided in the edu.eci.arsw.highlandersim package. This is a game in which:
  * You have N immortal players. 
  * Each player knows the remaining N-1 player.
  * Each player permanently attacks some other immortal. The one who first attacks subtracts M life points from his opponent, and increases his own life points by the same amount. 
  * The game could never have a single winner. Most likely, in the end there are only two left, fighting indefinitely by removing and adding life points.
  
2. Review the code and identify how the functionality indicated above was implemented. Given the intention of the game, an invariant should be that the sum of the life points of all players is always the same (of course, in an instant of time in which a time increase / reduction operation is not in process ). For this case, for N players, what should this value be?

El valor de la suma de la vida de todos los inmortales es igual a la cantidad inicial de vida de cada inmortal multiplicado por la cantidad de inmortales, para este caso sería N*100.

3. Run the application and verify how the ‘pause and check’ option works. Is the invariant fulfilled?

El invariante no se satisface.

![](https://github.com/sebastianfrasic/ARSW-Lab2/blob/master/img/invarianteF.PNG)

4. A first hypothesis that the race condition for this function (pause and check) is presented is that the program consults the list whose values it will print, while other threads modify their values. To correct this, do whatever is necessary so that, before printing the current results, all other threads are paused. Additionally, implement the ‘resume’ option.

5. Check the operation again (click the button many times). Is the invariant fulfilled or not ?.

El invariante sigue sin satisfacerse.

![](https://github.com/sebastianfrasic/ARSW-Lab2/blob/master/img/invarianteF2.PNG)

6. Identify possible critical regions in regards to the fight of the immortals. Implement a blocking strategy that avoids race conditions. Remember that if you need to use two or more ‘locks’ simultaneously, you can use nested synchronized blocks.

7. After implementing your strategy, start running your program, and pay attention to whether it comes to a halt. If so, use the jps and jstack programs to identify why the program stopped.

8. Consider a strategy to correct the problem identified above (you can review Chapter 15 of Java Concurrency in Practice again).

9. Once the problem is corrected, rectify that the program continues to function consistently when 100, 1000 or 10000 immortals are executed. If in these large cases the invariant begins to be breached again, you must analyze what was done in step 4.

10. An annoying element for the simulation is that at a certain point in it there are few living 'immortals' making failed fights with 'immortals' already dead. It is necessary to suppress the immortal dead of the simulation as they die. 
  * Analyzing the simulation operation scheme, could this create a race condition? Implement the functionality, run the simulation and see what problem arises when there are many 'immortals' in it. Write your conclusions about it in the file ANSWERS.txt. 
  * Correct the previous problem WITHOUT using synchronization, since making access to the shared list of immortals sequential would make simulation extremely slow. 
  
11. To finish, implement the STOP option.

## Solución implementada:

Para corregir los problemas de concurrencia se implementaron los siguientes mecanismos de control de threads:

* La vida de los inmortales fue convertida en una variable Atómica, con el fin de evitar condiciones de carrera en la vida de cada inmortal.

* Al momento de que dos inmortales peleen se usan bloqueos sobre los dos inmortales para asegurar el cambio correcto de sus vidas, pero no se realiza en un bloqueo anidado que causa un deadlock sino en dos bloqueos secuenciales.

* Después de la pelea, se pone un bloqueo sobre el notificador del programa, con el fin de evitar la rara pero posible condición de que se notifiquen dos acciones al mismo tiempo (Nos ocurrió en algún punto del laboratorio).

* Dada la medida anterior y para evitar el hecho de que un inmortal "muerto" pelee, cuando la vida del inmortal llega a 0 el thread se detiene permanentemente y al momento de pelear se verifica que el inmortal siga vivo.
