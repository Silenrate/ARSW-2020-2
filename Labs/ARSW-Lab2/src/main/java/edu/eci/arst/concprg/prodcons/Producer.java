/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arst.concprg.prodcons;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hcadavid
 */
public class Producer extends Thread {

    //private final long stockLimit;
    private int dataSeed = 0;
    private Random rand = null;
    private LinkedBlockingQueue<Integer> queue = null;

    public Producer(LinkedBlockingQueue<Integer> queue) {
        this.queue = queue;
        rand = new Random(System.currentTimeMillis());
        //this.stockLimit = stockLimit;
    }

    @Override
    public void run() {
        while (true) {
            dataSeed = dataSeed + rand.nextInt(100);
            if(queue.offer(dataSeed)){
                System.out.println("Producer added " + dataSeed);
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
