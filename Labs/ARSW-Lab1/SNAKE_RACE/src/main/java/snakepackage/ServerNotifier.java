package snakepackage;

public class ServerNotifier implements Notifier {

    private int snakesOriginalAmount;
    private Thread[] threads;

    public ServerNotifier(int snakesOriginalAmount, Thread threads[]) {
        this.snakesOriginalAmount = snakesOriginalAmount;
        this.threads = threads;
    }

    public void notifyEndOfGame() {
        System.out.println("Thread (snake) status:");
        for (int i = 0; i != snakesOriginalAmount; i++) {
            System.out.println("[" + (i+1) + "] :" + threads[i].getState());
        }
    }
}
