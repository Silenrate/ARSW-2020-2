package edu.eci.arsw.primefinder;

import edu.eci.arsw.math.MathUtilities;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeFinderThread extends Thread {

    private BigInteger a;
    private BigInteger b;
    private PrimesResultSet prs;
    private AtomicInteger num;

    public PrimeFinderThread(BigInteger a, BigInteger b, PrimesResultSet prs, AtomicInteger num) {
        this.a = a;
        this.b = b;
        this.prs = prs;
        this.num = num;
    }

    @Override
    public void run() {
        MathUtilities mt = new MathUtilities();
        BigInteger i = a;
        while (i.compareTo(b) <= 0) {
            synchronized (this) {
                if (PrimesFinderTool.isPause()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (mt.isPrime(i)) prs.addPrime(i);
            i = i.add(BigInteger.ONE);
        }
        num.decrementAndGet();
    }
}
