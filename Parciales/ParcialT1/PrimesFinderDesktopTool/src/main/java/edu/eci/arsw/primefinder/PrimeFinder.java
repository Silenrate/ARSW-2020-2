package edu.eci.arsw.primefinder;

import edu.eci.arsw.math.MathUtilities;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeFinder extends Thread {

    private BigInteger start;
    private BigInteger end;
    private final PrimesResultSet prs;
    private final AtomicInteger threadsAlive;


    public PrimeFinder(BigInteger start, BigInteger end, PrimesResultSet prs, AtomicInteger threadsAlive) {
        this.start = start;
        this.end = end;
        this.prs = prs;
        this.threadsAlive = threadsAlive;
    }

    @Override
    public void run() {
        MathUtilities mt = new MathUtilities();
        BigInteger i = start;
        while (i.compareTo(end) <= 0) {
            synchronized (this) {
                if (PrimesFinderTool.isPause()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (mt.isPrime(i)) {
                prs.addPrime(i);
            }
            i = i.add(BigInteger.ONE);
        }
        threadsAlive.getAndDecrement();
    }


}
