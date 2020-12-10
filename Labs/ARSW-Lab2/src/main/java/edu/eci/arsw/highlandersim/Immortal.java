package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback = null;
    private AtomicInteger health;
    private final int defaultDamageValue;
    private final List<Immortal> immortalsPopulation;
    private final String name;
    private final Random r = new Random(System.currentTimeMillis());
    private boolean isPaused = false;
    private boolean isAlive = true;


    public Immortal(String name, List<Immortal> immortalsPopulation, AtomicInteger health, int defaultDamageValue, ImmortalUpdateReportCallback ucb) {
        super(name);
        this.updateCallback = ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = health;
        this.defaultDamageValue = defaultDamageValue;
    }

    public void run() {
        while (isAlive) {
            synchronized (this) {
                while (isPaused) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Immortal im;
            //synchronized (immortalsPopulation){
            if (immortalsPopulation.size() > 1) im = decidirOponente();
            else {
                updateCallback.processReport("The inmortal " + this + " is the last alive!!!");
                break;
            }
            //}
            if (isAlive) this.fight(im);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void pause() {
        isPaused = true;
        notifyAll();
    }

    public synchronized void reanudar() {
        isPaused = false;
        notifyAll();
    }

    public synchronized void detener() {
        isAlive = false;
        notifyAll();
    }

    public void fight(Immortal i2) {
        boolean fight = false;
        synchronized (i2) {
            if (i2.getHealth().get() > 0) {
                i2.addHealth(-defaultDamageValue);
                fight = true;
            }
        }
        synchronized (this) {
            if (fight) health.addAndGet(defaultDamageValue);
        }
        synchronized (updateCallback) {
            updateCallback.processReport("Fight: " + this + " vs " + i2 + "\n");
        }
    }

    private Immortal decidirOponente() {
        int myIndex = immortalsPopulation.indexOf(this);
        int nextFighterIndex = r.nextInt(immortalsPopulation.size());
        //avoid self-fight
        if (nextFighterIndex == myIndex) {
            nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
        }
        return immortalsPopulation.get(nextFighterIndex);
    }

    public void changeHealth(int v) {
        health.set(v);
    }

    public void addHealth(int delta) {
        if (health.addAndGet(delta) <= 0) {
            this.pause();
            this.detener();
            immortalsPopulation.remove(this);
        }
    }

    public AtomicInteger getHealth() {
        return health;
    }

    @Override
    public String toString() {
        return name + "[" + health + "]";
    }

}
