package dominoes;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.stream.IntStream;

import dominoes.models.Domino;

public class GUI {
    private JPanel panel1;
    private JTable field;
    private JPanel controlPanel;
    private JTable hand;
    private JLabel deckLeft;
    private JButton pickDomino;
    private JButton giveUp;
    private JButton newGame;
    private JTextField numPlayers;
    private JPanel gamePanel;
    private JScrollPane handScroll;
    private JLabel playerTurn;
    private JLabel endGameText;

    private GameEngine engine = new GameEngine(0);

    private int selectedDomino = 0;

    public GUI() {
        try {
            // set look and feel windows
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

        }
        JFrame frame = new JFrame("Domino");

        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(panel1.getPreferredSize().width, 720));
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        setupTables();


        // Hide panels
        handScroll.setVisible(false);
        controlPanel.setVisible(false);

        field.repaint();

        initializeListeners();
    }

    public void newGame() {
        int numplayers = Integer.parseInt(numPlayers.getText());
        engine = new GameEngine(numplayers);
        handScroll.setVisible(true);
        controlPanel.setVisible(true);
        gamePanel.setVisible(false);
        endGameText.setText("Идет игра");
        setupTables();
        deckLeft.setText(String.format("Ост. %d домино", engine.getDeckSize()));
        field.setEnabled(true);
    }

    private void initializeListeners() {
        newGame.addActionListener(e -> newGame());

        pickDomino.addActionListener(e -> {
            engine.pickDomino();
            deckLeft.setText(String.format("Ост. %d домино", engine.getDeckSize()));
            field.setModel(new DefaultTableModel(28, 1));
            setupTables();
        });

        giveUp.addActionListener(e -> {
            engine.removePlayer();
            playerTurn.setText(String.format("Ходит %s", engine.getCurrentPlayer().getName()));
            setupTables();
            if (engine.getNumPlayers() <= 1) {
                endGameText.setText(String.format("%s победил!", engine.getCurrentPlayer().getName()));
                controlPanel.setVisible(false);
                handScroll.setVisible(false);
                gamePanel.setVisible(true);
                field.setEnabled(false);
            }
        });

        MouseAdapter fAdapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if(engine.getNumPlayers() <= 1)
                    return;
                int row = field.rowAtPoint(e.getPoint());
                int col = field.columnAtPoint(e.getPoint());
                var domino = engine.getCurrentPlayer().getDomino(selectedDomino);
                System.out.printf("Placing %s at %d,%d\n", domino.toString(), row, col);
                if (engine.placeDomino(domino, col, row)) {
                    engine.getCurrentPlayer().removeDomino(selectedDomino);
                    selectedDomino = 0;
                    engine.nextPlayer();
                    playerTurn.setText(String.format("Ходит %s", engine.getCurrentPlayer().getName()));
                    setupTables();
                }
                field.repaint();
            }
        };

        MouseAdapter hAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedDomino = hand.columnAtPoint(e.getPoint());
            }
        };

        field.addMouseListener(fAdapter);
        field.addMouseMotionListener(fAdapter);

        hand.addMouseListener(hAdapter);
        hand.addMouseMotionListener(hAdapter);
    }

    private void setupTables() {
        // Init Board
        field.setModel(new DefaultTableModel(28, 28));
        field.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        field.setRowHeight(96);
        IntStream.rangeClosed(0, 27).forEach(i -> {
            field.getColumnModel().getColumn(i).setPreferredWidth(96);
        });
        field.getTableHeader().setResizingAllowed(false);
        field.getTableHeader().setEnabled(false);
        field.setDefaultRenderer(Object.class, new FieldRenderer());

        field.setEnabled(false);

        // Init Hand
        var p = engine.getCurrentPlayer();
        int col = 6;
        if (p!=null) {
            col = p.getDominoInHand().size();
        }
        hand.setModel(new DefaultTableModel(1, col));
        hand.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        hand.setRowHeight(96);
        IntStream.rangeClosed(0, col-1).forEach(i -> {
            hand.getColumnModel().getColumn(i).setPreferredWidth(96);
        });
        hand.getTableHeader().setResizingAllowed(false);
        hand.getTableHeader().setEnabled(false);
        hand.setDefaultRenderer(Object.class, new HandRenderer());



        field.repaint();
        hand.repaint();
    }


    // ------------------ RENDERERS ------------------
    public class FieldRenderer extends DefaultTableCellRenderer {
        final class DrawComponent extends Component {
            private int row = 0, column = 0;

            @Override
            public void paint(Graphics gr) {
                Graphics2D g2d = (Graphics2D) gr;
                int width = getWidth() - 2;
                int height = getHeight() - 2;
                paintCell(row, column, g2d, width, height);
            }
        }

        DrawComponent comp = new DrawComponent();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            comp.row = row;
            comp.column = column;
            return comp;
        }

        private void paintCell(int row, int column, Graphics2D g2d, int cellWidth, int cellHeight) {

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int size = Math.min(cellWidth, cellHeight);

            Domino d = getCell(row, column);
            if (d == null)
                return;
            boolean horizontal = d.getSide() == Domino.Side.LEFT || d.getSide() == Domino.Side.RIGHT;
            boolean reversal = d.getSide() == Domino.Side.RIGHT || d.getSide() == Domino.Side.BOTTOM;

            g2d.setColor(Color.BLACK);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, g2d.getFont().getSize() + 4));
            if (horizontal)
                g2d.fillRoundRect(0, Math.round(size * 0.25f), size, Math.round(size * 0.5f), Math.round(size * 0.1f), Math.round(size * 0.1f));
            else
                g2d.fillRoundRect(Math.round(size * 0.25f), 0, Math.round(size * 0.5f), size, Math.round(size * 0.1f), Math.round(size * 0.1f));
            Utils.drawStringDomino(g2d, g2d.getFont(), d.getSideTop(), d.getSideBottom(),
                    horizontal, reversal, 0, 0, cellWidth, cellHeight);
        }


        public Domino getCell(int row, int col) {
            return (row < 0 || row >= 28 || col < 0 || col >= 28) ? null : engine.board[row][col];
        }
    }

    public class HandRenderer extends DefaultTableCellRenderer {
        final class DrawComponent extends Component {
            private int row = 0, column = 0;

            @Override
            public void paint(Graphics gr) {
                Graphics2D g2d = (Graphics2D) gr;
                int width = getWidth() - 2;
                int height = getHeight() - 2;
                paintCell(row, column, g2d, width, height);
            }
        }

        DrawComponent comp = new DrawComponent();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            comp.row = row;
            comp.column = column;
            return comp;
        }

        private void paintCell(int row, int column, Graphics2D g2d, int cellWidth, int cellHeight) {

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int size = Math.min(cellWidth, cellHeight);

            Domino d = getCell(row, column);
            if (d == null)
                return;
            boolean horizontal = d.getSide() == Domino.Side.LEFT || d.getSide() == Domino.Side.RIGHT;
            boolean reversal = d.getSide() == Domino.Side.RIGHT || d.getSide() == Domino.Side.BOTTOM;

            g2d.setColor(Color.BLACK);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, g2d.getFont().getSize() + 4));
            if (horizontal)
                g2d.fillRoundRect(0, Math.round(size * 0.25f), size, Math.round(size * 0.5f), Math.round(size * 0.1f), Math.round(size * 0.1f));
            else
                g2d.fillRoundRect(Math.round(size * 0.25f), 0, Math.round(size * 0.5f), size, Math.round(size * 0.1f), Math.round(size * 0.1f));
            Utils.drawStringDomino(g2d, g2d.getFont(), d.getSideTop(), d.getSideBottom(),
                    horizontal, reversal, 0, 0, cellWidth, cellHeight);
        }


        public Domino getCell(int row, int col) {
            var dominoes = engine.getCurrentPlayer().getDominoInHand();
            return (row != 0 || col < 0 || col >= dominoes.size()) ? null : dominoes.get(col);
        }
    }
}