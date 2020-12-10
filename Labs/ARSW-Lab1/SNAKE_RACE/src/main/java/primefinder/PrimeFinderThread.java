package primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread {


    int a, b;

    private List<Integer> primes;
    private boolean paused;

    public PrimeFinderThread(int a, int b, List<Integer> primes) {
        super();
        this.primes = primes;
        this.a = a;
        this.b = b;
        this.paused = false;
    }

    @Override
    public void run() {
        try {
            for (int i = a; i < b; i++) {
                if (isPrime(i)) {
                    primes.add(i);
                    //System.out.println(i);
                }
                synchronized (this) {
                    while (paused) {
                        wait();
                    }
                }
            }
        } catch (InterruptedException exc) {
            System.out.println("Hilo que tiene de rango " + a + "-" + b + "interrumpido.");
        }
    }

    synchronized void pause(){
        paused=true;
        notifyAll();
    }

    synchronized void reanudar(){
        paused=false;
        notifyAll();
    }

    boolean isPrime(int n) {
        boolean ans;
        if (n > 2) {
            ans = n % 2 != 0;
            for (int i = 3; ans && i * i <= n; i += 2) {
                ans = n % i != 0;
            }
        } else {
            ans = n == 2;
        }
        return ans;
    }

    public List<Integer> getPrimes() {
        return primes;
    }

}
