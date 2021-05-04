import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

public class View extends JPanel implements ChangeListener {
    private Color color;
    final int Padding = 15, padding2 = 30;
    final int pitWidth = 75, pitHeight = 90;
    private static int undoCounter;


    //helps determine if a color has been selected at the start of the game, if it is true, draw the board
    boolean colorSelected;


    MancalaBoard mb;

    public View(MancalaBoard board) {
        colorSelected = false;
        mb = board;
        setLayout(new BorderLayout());

        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                BoardVisualizer board = new BoardVisualizer(mb, color);
                int x, y;
                int mx = e.getX();
                int my = e.getY();

                // loop through all pits in the bottom row
                for (int pit = 0; pit < 14; ++pit) {
                    x = board.getPitX(pit);
                    y = board.getPitY(pit);

                    // check if the click was inside the pit area.
                    if (mx > x && mx < x + board.pitWidth && my > y && my < y + board.pitHeight) {
                        mb.moveStones(pit);

                    }
                }


            }


            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JButton undoButton = new JButton("Undo");
        undoCounter = 0;
        undoButton.addActionListener(e12 -> {
            if (mb.stonesMoved() == true) {
                if (undoCounter == 3) {
                    mb.switchTurn();
                    undoCounter = 0;
                } else if (undoCounter < 3) {
                    undoCounter++;
                    getPreviousBoardState();
                }
            }

        });
        //Confirm Move button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e1 -> mb.switchTurn());

        panel.add(confirmButton);
        panel.add(undoButton);
        add(panel, BorderLayout.SOUTH);
        panel.setSize(100, 50);
        panel.setVisible(true);

    }

    public void getPreviousBoardState() {
        mb.setStones(mb.getPrevStones());
        repaint();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setDesign(Design d) {
        setColor(d.getColor());
    }


    protected void paintComponent(Graphics g) {


        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //if the color has been selected we draw the game / else draw color selection screen

        BoardVisualizer b = new BoardVisualizer(mb, color);
        b.drawBoard(g);


        for (int i = 0; i < 14; i++) {
            if (i < 6) {
                g2.drawString("A" + (i + 1), b.getPitCenterX(i), b.getPitCenterY(i) + 60);
                g2.drawString("B" + (6 - i), b.getPitCenterX(i), b.getPitCenterY(i) - 143);
            }
            for (int j = 0; j < mb.getData()[i]; j++) {
                if (mb.getData()[i] == 1) {
                    g2.draw(new Ellipse2D.Double(b.getPitCenterX(i), b.getPitCenterY(i), 16, 16));
                } else if (j < 4) {
                    g2.draw(new Ellipse2D.Double(b.getPitCenterX(i) + (16 * j) - 25, b.getPitCenterY(i) - 32, 16, 16));
                } else if (j < 8 && j >= 4) {
                    g2.draw(new Ellipse2D.Double(b.getPitCenterX(i) + (16 * j) - 90, b.getPitCenterY(i) - 16, 16, 16));
                } else if (j < 12 && j >= 8) {
                    g2.draw(new Ellipse2D.Double(b.getPitCenterX(i) + (16 * j) - 150, b.getPitCenterY(i), 16, 16));
                } else if (j < 16 && j >= 12) {
                    g2.draw(new Ellipse2D.Double(b.getPitCenterX(i) + (16 * j) - 210, b.getPitCenterY(i) + 16, 16, 16));
                } else if (j < 20 && j >= 16) {
                    g2.draw(new Ellipse2D.Double(b.getPitCenterX(i) + (16 * j) - 270, b.getPitCenterY(i) + 32, 16, 16));
                }


            }

//            g2.drawString(Integer.toString(mb.getData()[i]), b.getPitCenterX(i) + 5, b.getPitCenterY(i) + 7);
        }
        g2.drawString("Player " + mb.whichTurn() + "s turn", 599, 231);
        g2.drawString("press confirm to end turn ", 99, 231);
    }

    public void stateChanged(ChangeEvent e) {
        repaint();
    }

    class BoardVisualizer {


        final int outerPadding = 15, innerPadding = 20;
        final int pitWidth = 85, pitHeight = 85;
        final int storeWidth = 80, storeHeight = 205;

        private MancalaBoard game;

        /**
         * Initialize the class
         *
         * @param game instance of MancalaGame
         */
        public BoardVisualizer(MancalaBoard game, Color c) {
            color = c;
            this.game = game;

        }


        /**
         * Get the size of the board as a Dimension object
         *
         * @return
         */
        public Dimension getSize() {
            int height = 2 * (outerPadding + pitHeight) + innerPadding;
            int width = 6 * (pitWidth + innerPadding) + 2 * (storeWidth + outerPadding);
            return new Dimension(width, height);
        }

        /**
         * Draw a row of pits
         *
         * @param g Graphics object
         * @param x Beginning X position of row
         * @param y Beginning Y position of row
         */
        protected void drawRow(Graphics g, int x, int y) {

            for (int i = 0; i < 6; ++i) {
                g.drawOval(x, y, pitWidth, pitHeight);
                x += pitWidth + outerPadding;
            }
        }

        /**
         * Draw the storage spaces
         *
         * @param g Graphics object
         */
        protected void drawStores(Graphics g) {
            int round = 30;
            int resize = 20;

            // begin first mancala at padding position
            g.setColor(color);
            g.drawRoundRect(
                    outerPadding, outerPadding + resize,
                    storeWidth, storeHeight - resize * 2,
                    round, round
            );

            /* second mancala must be after all six boxes,
             * plus the first mancala, plus padding */
            int x = outerPadding + storeWidth + 6 * (innerPadding + pitWidth);

            g.setColor(color);
            g.drawRoundRect(
                    x, outerPadding + resize,
                    storeWidth, storeHeight - resize * 2,
                    round, round
            );
        }

        /**
         * Draw the board pits and stores
         *
         * @param g Graphics object
         */
        public void drawBoard(Graphics g) {
            drawStores(g);

            int rowX = storeWidth + innerPadding * 2;

            g.setColor(color);
            drawRow(g, rowX, outerPadding);

            g.setColor(color);
            drawRow(g, rowX, outerPadding + pitHeight + innerPadding);
        }

        /**
         * Retrieve the X position of a pit
         *
         * @param pit a pit number
         * @return the pit's X position
         */
        public int getPitX(int pit) {
            int x;

            // check if pit is a store
            if (pit == 6 || pit == 13) {
                x = outerPadding + storeWidth / 2;

                // subtract pit x from screen width
                x = (pit == 6) ? getSize().width - x : x;
            } else {

                // reverse the top row numbers
                if (pit > 6) pit = -pit + 12;

                // begin with outside padding + mancala
                x = outerPadding + storeWidth;

                // add padding for each box
                x += outerPadding * (pit + 1);

                // add boxes
                x += pit * pitWidth;
            }

            return x;
        }

        /**
         * Retrieve the Y position of a pit
         *
         * @param pit a pit number
         * @return the pit's Y position
         */
        public int getPitY(int pit) {

            // check if a pit is a store or in the second row
            if (pit <= 6 || pit == 13) {
                return outerPadding * 2 + pitHeight;
            }

            return outerPadding;
        }

        /**
         * Get the X coordinate in the center of a pit
         *
         * @param pit a pit number
         * @return X position
         */
        public int getPitCenterX(int pit) {
            int x = getPitX(pit);

            if (pit != 6 && pit != 13) {
                x += pitWidth / 2;
            }

            return x;
        }

        /**
         * Get the Y coordinate in the center of a pit
         *
         * @param pit a pit number
         * @return Y position
         */
        public int getPitCenterY(int pit) {
            int y = getPitY(pit);

            if (pit != 6 && pit != 13) {
                y += pitHeight / 2;
            }

            return y;
        }
    }
}
