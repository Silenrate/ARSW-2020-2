/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primefinder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 */
public class Control extends Thread {

    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;

    private PrimeFinderThread pft[];

    private List<Integer> primes;

    private boolean resume;

    private Control() {
        super();
        this.pft = new PrimeFinderThread[NTHREADS];
        primes = new CopyOnWriteArrayList<Integer>();
        int i;
        for (i = 0; i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i * NDATA, (i + 1) * NDATA, primes);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i * NDATA, MAXVALUE + 1, primes);
    }

    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
        for (int i = 0; i < NTHREADS; i++) {
            pft[i].start();
        }
        Timer timer = new Timer(TMILISECONDS, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < NTHREADS; i++) {
                    pft[i].pause();
                }
                System.out.println("Los primos encontrados hasta ahora fueron:");
                System.out.println(primes);
                resume = false;
                while (!resume) {
                    reanudar();
                }

            }
        });
        timer.start();
    }

    private void reanudar() {
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Ejecucion pausada despues de 5 segundos, presione la tecla Enter para continuar");
        System.out.println("        En caso de error de digitacion este mensaje volvera a aparecer.        ");
        System.out.println("-------------------------------------------------------------------------------");
        Scanner t = new Scanner(System.in);
        String enterkey = t.nextLine();
        if (enterkey.isEmpty()) {
            resume = true;
            for (int i = 0; i < NTHREADS; i++) {
                pft[i].reanudar();
            }
        }
    }

}
