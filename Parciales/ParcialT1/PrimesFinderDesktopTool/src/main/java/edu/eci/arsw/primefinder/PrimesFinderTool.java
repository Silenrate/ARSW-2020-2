package edu.eci.arsw.primefinder;

import edu.eci.arsw.mouseutils.MouseMovementMonitor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimesFinderTool {

    private static final long WAIT_AMOUNT = 10000;
    private static boolean isPaused;

    public static void main(String[] args) {
        int maxPrim = 1000000;
        int amountOfThreads = 4;
        isPaused = false;
        AtomicInteger threadsAlive = new AtomicInteger(amountOfThreads);
        PrimesResultSet prs = new PrimesResultSet("john");
        List<PrimeFinder> threads = new ArrayList<>();
        int amount = maxPrim / amountOfThreads;
        int start = 0;
        for (int i = 0; i < amountOfThreads; i++) {
            threads.add(new PrimeFinder(new BigInteger(Integer.toString(start)), new BigInteger(Integer.toString(start + amount)), prs, threadsAlive));
            start += amount;
        }
        threads.forEach(Thread::start);

        while (threadsAlive.get() > 0) {
            if (MouseMovementMonitor.getInstance().getTimeSinceLastMouseMovement() < WAIT_AMOUNT) {
                //System.out.println("Pausa");
                isPaused = true;
            } else {
                //System.out.println("No pausa");
                isPaused = false;
            }
        }
        System.out.println("Prime numbers found:");
        System.out.println(prs.getPrimes());
    }

    public static boolean isPause() {
        return isPaused;
    }
}


