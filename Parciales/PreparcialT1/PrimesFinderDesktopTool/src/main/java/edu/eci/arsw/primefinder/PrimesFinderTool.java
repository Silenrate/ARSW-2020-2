package edu.eci.arsw.primefinder;

import edu.eci.arsw.mouseutils.MouseMovementMonitor;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimesFinderTool {

    private static boolean pause;
    public static final int WAIT_AMOUNT = 10000;

    public static void main(String[] args) {
        int maxPrim = 100;
        int numberOfThreads = 4;
        pause = false;
        PrimesResultSet prs = new PrimesResultSet("john");
        PrimeFinderThread[] threads = new PrimeFinderThread[numberOfThreads];
        AtomicInteger num = new AtomicInteger(numberOfThreads);
        int amount = maxPrim / numberOfThreads;
        int start = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new PrimeFinderThread(new BigInteger(Integer.toString(start)), new BigInteger(Integer.toString(start + amount)), prs, num);
            start += amount;
            threads[i].start();
        }
        while (num.get() > 0) {
            if (MouseMovementMonitor.getInstance().getTimeSinceLastMouseMovement() < WAIT_AMOUNT) {
                //System.out.println("no pausa");
                pause = false;
            } else {
                //System.out.println("pausa");
                pause = true;
            }
        }
        System.out.println("Prime numbers found:");
        System.out.println(prs.getPrimes());
    }

    public static boolean isPause() {
        return pause;
    }
}


