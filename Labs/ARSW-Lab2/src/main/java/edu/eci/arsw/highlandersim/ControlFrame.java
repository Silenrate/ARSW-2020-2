package edu.eci.arsw.highlandersim;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JScrollBar;

public class ControlFrame extends JFrame {

    private static final int DEFAULT_IMMORTAL_HEALTH = 100;
    private static final int DEFAULT_DAMAGE_VALUE = 10;

    private JPanel contentPane;

    private CopyOnWriteArrayList<Immortal> immortals;

    private JTextArea output;
    private JLabel statisticsLabel;
    private JScrollPane scrollPane;
    private JTextField numOfImmortals;
    private JButton btnStart, btnResume, btnPauseAndCheck, btnStop;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ControlFrame frame = new ControlFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ControlFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 647, 248);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        JToolBar toolBar = new JToolBar();
        contentPane.add(toolBar, BorderLayout.NORTH);
        btnStart = new JButton("Start");
        btnResume = new JButton("Resume");
        btnPauseAndCheck = new JButton("Pause and check");
        btnStop = new JButton("STOP");
        btnStop.setForeground(Color.RED);
        toolBar.add(btnStart);
        toolBar.add(btnPauseAndCheck);
        toolBar.add(btnResume);
        JLabel lblNumOfImmortals = new JLabel("num. of immortals:");
        toolBar.add(lblNumOfImmortals);
        numOfImmortals = new JTextField();
        numOfImmortals.setText("3");
        toolBar.add(numOfImmortals);
        numOfImmortals.setColumns(10);
        toolBar.add(btnStop);
        scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        output = new JTextArea();
        output.setEditable(false);
        scrollPane.setViewportView(output);
        statisticsLabel = new JLabel("Immortals total health:");
        contentPane.add(statisticsLabel, BorderLayout.SOUTH);
        prepareAcciones();
    }

    private void prepareAcciones() {
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                immortals = setupInmortals();
                if (immortals != null) immortals.forEach(Thread::start);
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
                btnResume.setEnabled(true);
                btnPauseAndCheck.setEnabled(true);
            }
        });
        btnPauseAndCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                immortals.forEach(Immortal::pause);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                check();
                btnPauseAndCheck.setEnabled(false);
                btnResume.setEnabled(true);
            }
        });
        btnResume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                immortals.forEach(Immortal::reanudar);
                btnResume.setEnabled(false);
                btnPauseAndCheck.setEnabled(true);
            }
        });
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnStart.setEnabled(true);
                btnPauseAndCheck.setEnabled(false);
                btnResume.setEnabled(false);
                immortals.forEach(Immortal::detener);
                check();
                immortals = null;
                btnStop.setEnabled(false);
            }
        });
    }

    private void check() {
        int sum = immortals.stream().mapToInt(im -> im.getHealth().get()).sum();
        statisticsLabel.setText("<html>" + immortals.toString() + "<br>Health sum:" + sum + " - Inmortals Alive:" + immortals.size());
    }

    public CopyOnWriteArrayList<Immortal> setupInmortals() {
        ImmortalUpdateReportCallback ucb = new TextAreaUpdateReportCallback(output, scrollPane);
        try {
            int ni = Integer.parseInt(numOfImmortals.getText());
            CopyOnWriteArrayList<Immortal> il = new CopyOnWriteArrayList<Immortal>();
            for (int i = 0; i < ni; i++) {
                Immortal i1 = new Immortal("im" + i, il, new AtomicInteger(DEFAULT_IMMORTAL_HEALTH), DEFAULT_DAMAGE_VALUE, ucb);
                il.add(i1);
            }
            return il;
        } catch (NumberFormatException e) {
            JOptionPane.showConfirmDialog(null, "Número inválido.");
            return null;
        }
    }

}

class TextAreaUpdateReportCallback implements ImmortalUpdateReportCallback {

    JTextArea ta;
    JScrollPane jsp;

    public TextAreaUpdateReportCallback(JTextArea ta, JScrollPane jsp) {
        this.ta = ta;
        this.jsp = jsp;
    }

    @Override
    public void processReport(String report) {
        ta.append(report);
        //move scrollbar to the bottom
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JScrollBar bar = jsp.getVerticalScrollBar();
                bar.setValue(bar.getMaximum());
            }
        });
    }
}