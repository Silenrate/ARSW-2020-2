package snakepackage;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;

import enums.GridSize;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jd-
 */
public class SnakeApp {

    private static SnakeApp app;
    public static final int MAX_THREADS = 8;
    Snake[] snakes = new Snake[MAX_THREADS];
    private static final Cell[] spawn = {
            new Cell(1, (GridSize.GRID_HEIGHT / 2) / 2),
            new Cell(GridSize.GRID_WIDTH - 2,
                    3 * (GridSize.GRID_HEIGHT / 2) / 2),
            new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2, 1),
            new Cell((GridSize.GRID_WIDTH / 2) / 2, GridSize.GRID_HEIGHT - 2),
            new Cell(1, 3 * (GridSize.GRID_HEIGHT / 2) / 2),
            new Cell(GridSize.GRID_WIDTH - 2, (GridSize.GRID_HEIGHT / 2) / 2),
            new Cell((GridSize.GRID_WIDTH / 2) / 2, 1),
            new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2,
                    GridSize.GRID_HEIGHT - 2)};
    private JFrame frame;
    private static Board board;
    int nr_selected = 0;
    Thread[] thread = new Thread[MAX_THREADS];
    private JButton start, pause, resume;
    private JTextField longest, dead;
    private AtomicInteger worstSnake, maximumSnakeSize, snakesAlive;
    private Notifier notifier;

    public SnakeApp() {
        worstSnake = new AtomicInteger(-1);
        maximumSnakeSize = new AtomicInteger(-1);
        snakesAlive = new AtomicInteger(MAX_THREADS);
        notifier = new ServerNotifier(MAX_THREADS,thread);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("The Snake Race");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(618, 640);
        frame.setSize(GridSize.GRID_WIDTH * GridSize.WIDTH_BOX + 17 + 120,
                GridSize.GRID_HEIGHT * GridSize.HEIGH_BOX + 40 + 100);
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2,
                dimension.height / 2 - frame.getHeight() / 2);
        board = new Board();
        frame.add(board, BorderLayout.CENTER);
        JPanel actionsBPabel = new JPanel();
        actionsBPabel.setLayout(new FlowLayout());
        start = new JButton("Start");
        actionsBPabel.add(start);
        pause = new JButton("Pause");
        pause.setEnabled(false);
        actionsBPabel.add(pause);
        resume = new JButton("Resume");
        resume.setEnabled(false);
        actionsBPabel.add(resume);
        longest = new JTextField("There´s no longest snake yet ", 23);
        longest.setEditable(false);
        actionsBPabel.add(longest);
        dead = new JTextField("There´s no worst snake yet", 20);
        dead.setEditable(false);
        actionsBPabel.add(dead);
        frame.add(actionsBPabel, BorderLayout.SOUTH);
        prepareAcciones();
    }

    private void prepareAcciones() {
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Thread t : thread) {
                    t.start();
                }
                start.setEnabled(false);
                pause.setEnabled(true);
            }
        });
        pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Snake s : snakes) {
                    s.pause();
                }
                pause.setEnabled(false);
                resume.setEnabled(true);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                updateInfo();
            }
        });
        resume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Snake s : snakes) {
                    s.resume();
                }
                resume.setEnabled(false);
                pause.setEnabled(true);
            }
        });
    }

    private void updateInfo() {
        updateLongestSnake();
        updateWorstSnake();
    }

    private void updateWorstSnake() {
        int worst = worstSnake.get();
        if (worst >= 0) {
            dead.setText("The worst snake is # " + worst);
        }
    }

    private void updateLongestSnake() {
        ArrayList<Integer> longestSnakes = new ArrayList<Integer>();
        for (Snake s : snakes) {
            if (s.getBody().size() == maximumSnakeSize.get()) {
                longestSnakes.add(s.getIdt());
            }
        }
        if (longestSnakes.size() > 1) {
            longest.setText("The longest snakes are " + longestSnakes.toString());
        } else {
            longest.setText("The longest snake is " + longestSnakes.get(0));
        }
    }


    public static void main(String[] args) {
        app = new SnakeApp();
        app.init();
    }

    private void init() {
        for (int i = 0; i != MAX_THREADS; i++) {
            snakes[i] = new Snake(i + 1, spawn[i], i + 1, worstSnake, maximumSnakeSize, snakesAlive, notifier);
            snakes[i].addObserver(board);
            thread[i] = new Thread(snakes[i]);
        }
        frame.setVisible(true);
    }

    public static SnakeApp getApp() {
        return app;
    }
}
