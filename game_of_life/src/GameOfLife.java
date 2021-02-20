import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class GameOfLife extends JFrame {

    private final Color color = Color.GRAY;
    public JPanel gridPanel;
    public JPanel[][] panelCells;
    public JLabel gen;
    public JLabel alive;
    JToggleButton start;
    JToggleButton pause;

    public GameOfLife() {
        super("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        initComponents();

        setResizable(false);
        setVisible(true);
    }

    public void initComponents() {

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        westPanel.setMaximumSize(new Dimension(200, 500));
        westPanel.setAlignmentX(LEFT_ALIGNMENT);
        westPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));

        start = new JToggleButton("->");
        pause = new JToggleButton("||");
        pause.setName("PlayToggleButton");

        start.addActionListener(e -> startGeneration());
        pause.addActionListener(e -> myListener());

        JButton reset = new JButton("@");
        reset.setName("ResetButton");

        buttonsPanel.add(start);
        buttonsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonsPanel.add(pause);
        buttonsPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonsPanel.add(reset);

        JPanel countPanel = new JPanel();
        countPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        countPanel.setLayout(new BoxLayout(countPanel, BoxLayout.Y_AXIS));
        countPanel.add(Box.createHorizontalGlue());


        gen = new JLabel();
        gen.setName("GenerationLabel");
        gen.setText("Generation #");

        alive = new JLabel();
        alive.setName("AliveLabel");
        alive.setText("Alive: ");

        countPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        countPanel.add(gen);
        countPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        countPanel.add(alive);

        westPanel.add(buttonsPanel);
        westPanel.add(countPanel);

        add(westPanel, BorderLayout.WEST);
        add(setGridPanel(), BorderLayout.CENTER);
    }

    public JPanel setGridPanel() {

        int sizeGrid = 20;
        gridPanel = new JPanel(new GridLayout(sizeGrid, sizeGrid));
        panelCells = new JPanel[sizeGrid][sizeGrid];

        for (int row = 0; row < sizeGrid; row++) {
            for (int col = 0; col < sizeGrid; col++) {
                JPanel panelCell = new JPanel();
                panelCell.setBackground(Color.WHITE);
                Border border;
                if (row < sizeGrid - 1) {
                    if (col < sizeGrid - 1) {
                        border = new MatteBorder(1, 1, 0, 0, color);
                    } else {
                        border = new MatteBorder(1, 1, 0, 1, color);
                    }
                } else {
                    if (col < sizeGrid - 1) {
                        border = new MatteBorder(1, 1, 1, 0, color);
                    } else {
                        border = new MatteBorder(1, 1, 1, 1, color);
                    }
                }
                panelCells[row][col] = panelCell;
                panelCell.setBorder(border);
                gridPanel.add(panelCell);
            }
        }
        return gridPanel;
    }

    public void updateGridPanel(Cell[][] generation, int genNum, int aliveNum) {

        gen.setText("Generation #" + genNum);
        alive.setText("Alive: " + aliveNum);

        for (int i = 0; i < generation.length; i++) {
            for (int j = 0; j < generation.length; j++) {
                if (generation[i][j].alive) {
                    panelCells[i][j].setBackground(color);
                } else {
                    panelCells[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    public synchronized void myListener() {

        if (pause.isSelected()) {
            try {
                wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pause.setSelected(false);
        notifyAll();
    }

    public boolean startGeneration() {
        gen.setText("Generation #0");
        alive.setText("Alive: 0");
        return start.isSelected();
    }

    public JLabel getGen() {
        return gen;
    }

    public JLabel getAlive() {
        return alive;
    }
}
