/*
 * Copyright © 2009 John B. Matthews. Distributed under the terms of the GPL
 */
package varioustests.swingworkertests;

/**
 *
 * @author https://sites.google.com/site/drjohnbmatthews/randomdata
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import org.h2.jdbcx.JdbcDataSource;

/**
 * @author John B. Matthews
 */
public class RandomDataDemo {

    private static final String RUN = "Run";
    private static final String CANCEL = "Cancel";
    private static final String QUIT = "Quit";
    private static final JProgressBar progressBar = new JProgressBar();
    private static final JTextArea textArea = new JTextArea(48, 120);
    private static final JButton run = new JButton(CANCEL);
    private static RandomDataTask task;

    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                init();
            }
        });
    }

    private static void init() {
        JFrame frame = new JFrame("Random Data Demo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        progressBar.setIndeterminate(true);
        frame.add(progressBar, BorderLayout.NORTH);

        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        run.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                if (RUN.equals(cmd)) {
                    task.cancel(true);
                    execTask();
                    run.setText(CANCEL);
                } else {
                    task.cancel(true);
                    run.setText(RUN);
                }
            }
        });
        panel.add(run);
        JButton quit = new JButton(QUIT);
        quit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                task.cancel(true);
                System.exit(0);
            }
        });
        panel.add(quit);
        frame.add(panel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        execTask();
    }

    private static void execTask() {
        textArea.replaceRange(null, 0, textArea.getSelectionEnd());
        progressBar.setIndeterminate(false);
        task = new RandomDataTask(2500);
        task.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("progress".equals(evt.getPropertyName())) {
                    progressBar.setValue((Integer) evt.getNewValue());
                }
            }
        });
        task.execute();
    }

    /**
     * Get pseudo-random numbers from a data source.
     */
    private static class RandomDataTask
        extends SwingWorker<List<Double>, Double> {

        private static final int BLOCK_SIZE = 10;
        private final int count;
        private final List<Double> numbers;
        private int index = 1;
        private Connection conn;
        private PreparedStatement ps;
        private ResultSet rs;

        RandomDataTask(int count) {
            this.count = count;
            this.numbers = new ArrayList<Double>(count);
            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL("jdbc:h2:file:~/src/java/jdbc/test;IFEXISTS=TRUE");
            ds.setUser("sa");
            ds.setPassword("");
            try {
                this.conn = ds.getConnection();
                ps = conn.prepareStatement(
                    "SELECT RAND() FROM SYSTEM_RANGE(1, ?)");
                ps.setInt(1, BLOCK_SIZE);
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            }
        }

        @Override
        public List<Double> doInBackground() {
            try {
                for (int i = 0; i < count / BLOCK_SIZE; i++) {
                    rs = ps.executeQuery();
                    while (rs.next() && !isCancelled()) {
                        if (rs.getBoolean(1)) {
                            double number = rs.getDouble(1);
                            numbers.add(number);
                            setProgress(100 * numbers.size() / count);
                            publish(number);
                        }
                    }
                    Thread.sleep(10); // simulate latency
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return numbers;
        }

        @Override
        protected void process(List<Double> chunks) {
            StringBuilder strBuilder = new StringBuilder();
            for (double number : chunks) {
                strBuilder.append(String.format("%1$11.8f", number));
                if (index % BLOCK_SIZE == 0) {
                    strBuilder.append('\n');
                }
                index++;
            }
            textArea.append(strBuilder.toString());
        }

        @Override
        protected void done() {
            run.setText(RUN);
        }
    }
}
