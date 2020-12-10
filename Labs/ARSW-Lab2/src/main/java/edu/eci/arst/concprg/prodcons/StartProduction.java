/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arst.concprg.prodcons;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartProduction {
    public static final int STOCK_LIMIT = 10;

    public static void main(String[] args) {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(STOCK_LIMIT);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        producer.start();
        //let the producer create products for 5 seconds (stock).
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(StartProduction.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("--------------------------------------------------");
        consumer.start();
    }
}