import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * @author Kevin Grewal, Solomon Alemu, Jeremy Esch
 */
public class View extends JPanel implements ChangeListener {


    private int undoCounter;

    public boolean winnerRevealed = false;
    public int p = 0;
    public int counter = 0;

    //helps determine if a color has been selected at the start of the game, if it is true, draw the board
    boolean colorSelected;
    private Color color;

    MancalaBoard mb;

    /**
     * Contructor for View Object
     * @param board
     */
    public View(MancalaBoard board) {
        colorSelected = false;
        mb = board;
        setLayout(new BorderLayout());

        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                BoardView board = new BoardView(mb, color);
                int x, y;
                int mx = e.getX();
                int my = e.getY();

                // loop through all pits in the bottom row
                for (int pit = 0; pit < 14; pit++) {
                    x = (int) board.getPitPoint(pit).getX();
                    y = (int) board.getPitPoint(pit).getY();

                    // check if the click was inside the pit area.
                    if (mx > x && mx < x + 118 && my > y && my < y + 118) {
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

        //undoButton creation
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JButton undoButton = new JButton("Undo");
        undoCounter = 0;
        undoButton.addActionListener(e12 -> {
            if (mb.stonesMoved()) {
                if (undoCounter == 3) {
                    mb.switchTurn();
                    undoCounter = 0;
                } else if (undoCounter < 3) {
                    undoCounter++;
                    getPreviousBoardState();
                    mb.setNeedsToConfirm(false);
                }
            }

        });


        if (mb.gameFinished()) {
            int p = mb.getWinningPlayer();
            if (p == 3) {
                System.out.println("Draw");
            } else {
                System.out.println(mb.getData()[6] + " " + mb.getData()[13]);
                System.out.println(p + " won");
            }
        }


        //Confirm move button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e1 -> {
            mb.setNeedsToConfirm(false);
            mb.switchTurn();
        });

        panel.add(confirmButton);
        panel.add(undoButton);
        add(panel, BorderLayout.SOUTH);
        panel.setSize(100, 50);
        panel.setVisible(true);

    }

    /**
     * undos the players move
     */
    public void getPreviousBoardState() {
        mb.setStones();
        repaint();
    }

    /**
     * sets Color
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * sets design
     * @param d
     */
    public void setDesign(Design d) {
        setColor(d.getColor());
    }

    /**
     * paints component aka the pits
     * @param g
     */
    public void paintComponent(Graphics g) {


        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //if the color has been selected we draw the game / else draw color selection screen

        BoardView b = new BoardView(mb, color);
        b.drawMancala(g2);


        g2.drawString("Player " + mb.whichTurn() + "s turn", 839, 423);
        g2.drawString("Press confirm to end turn ", 139, 423);
        for (int i = 0; i < 14; i++) {

            if (i < 6) {
                g2.setColor(Color.BLACK);
                g2.drawString("A" + (i + 1), (int) b.getPitCenterPoint(i).getX(), (int) b.getPitCenterPoint(i).getY() + 90);
                g2.drawString("B" + (6 - i), (int) b.getPitCenterPoint(i).getX(), (int) b.getPitCenterPoint(i).getY() - 205);
            }
            for (int j = 0; j < mb.getData()[i]; j++) {
                if (mb.getData()[i] == 1) {
                    Ellipse2D.Double e = new Ellipse2D.Double(b.getPitCenterPoint(i).getX(), b.getPitCenterPoint(i).getY(), 16, 16);

                    g2.setColor(Color.BLACK);
                    g2.fill(e);
                    g2.draw(e);
                } else if (j < 4) {
                    Ellipse2D.Double e = new Ellipse2D.Double(b.getPitCenterPoint(i).getX() + (16 * j) - 25, b.getPitCenterPoint(i).getY() - 32, 16, 16);
                    g2.setColor(Color.blue.darker());
                    g2.fill(e);
                    g2.draw(e);
                } else if (j < 8 && j >= 4) {
                    Ellipse2D.Double e = new Ellipse2D.Double(b.getPitCenterPoint(i).getX() + (16 * j) - 90, b.getPitCenterPoint(i).getY() - 16, 16, 16);
                    g2.setColor(Color.BLUE.brighter());
                    g2.fill(e);
                    g2.draw(e);
                } else if (j < 12 && j >= 8) {
                    Ellipse2D.Double e = new Ellipse2D.Double(b.getPitCenterPoint(i).getX() + (16 * j) - 150, b.getPitCenterPoint(i).getY(), 16, 16);
                    g2.setColor(Color.YELLOW.darker());
                    g2.fill(e);
                    g2.draw(e);
                } else if (j < 16 && j >= 12) {
                    Ellipse2D.Double e = new Ellipse2D.Double(b.getPitCenterPoint(i).getX() + (16 * j) - 210, b.getPitCenterPoint(i).getY() + 16, 16, 16);
                    g2.setColor(Color.PINK);
                    g2.fill(e);
                    g2.draw(e);
                } else if (j < 20 && j >= 16) {
                    Ellipse2D.Double e = new Ellipse2D.Double(b.getPitCenterPoint(i).getX() + (16 * j) - 270, b.getPitCenterPoint(i).getY() + 32, 16, 16);
                    g2.setColor(Color.cyan);
                    g2.fill(e);
                    g2.draw(e);
                }
            }

        }


        if (mb.gameFinished() && !winnerRevealed && (counter < 2)) {
            g2.setColor(Color.BLACK);
            p = mb.getWinningPlayer();
            if (p == 3) {
                g2.drawString("Game has ended, it's a draw!", 500, 423);
            } else {
                g2.drawString("Game has ended, player " + p + " has won!", 500, 423);
            }
            counter++;
            if (counter == 2) {
                winnerRevealed = true;
            }
        }

    }

    /**
     * recognizes if state changed in the model
     * @param e
     */
    public void stateChanged(ChangeEvent e) {
        repaint();
    }

    /**
     * inner class
     */
    public class BoardView {

        private MancalaBoard mb;

        /**
         * Board View Constructor
         * @param mb
         * @param c
         */
        public BoardView(MancalaBoard mb, Color c) {
            color = c;
            this.mb = mb;
        }


        /**
         * Draws the Mancala Board
         * @param g2 Graphics object
         */
        public void drawMancala(Graphics2D g2) {


            // draws first store
            g2.setColor(color);
            g2.drawRoundRect(21, 41, 112, 247, 30, 30);

            //draws second store (placed to the right of all pits)
            g2.setColor(color);
            g2.drawRoundRect(1015, 41, 112, 247, 30, 30);

            int x = 168;
            int i = 0;
            int j = 0;

            g2.setColor(color);

            //draws pits for upper row - Player 2
            while (i < 6)
            {
                g2.drawOval(x, 21, 118, 118);
                x = x + 139;
                i++;
            }

            //reset x
            x=168;
            g2.setColor(color);

            while(j < 6)
            {
                g2.drawOval(x, 167, 118, 118);
                x = x + 139;
                j++;
            }
        }

        /**
         * gets the pit location as a point
         * @param pit
         * @return Point object
         */
        public Point2D.Double getPitPoint(int pit) {
            int x, y;

            //checks if the pit is the store or in second row
            if (pit <= 6 || pit == 13) {
                y = 160;
            } else {
                y = 21;
            }

            // check if pit is a store
            if (pit == 6 || pit == 13) {
                x = 66;
                if (pit == 6)
                    x = 1142 - x;

            } else {
                // reverse the top row numbers
                if (pit > 6)
                {
                    pit = 12-pit;
                }
                // begin with outside padding + mancala
                x = 133;
                // add padding for each box
                x = x + 21 * (pit + 1);
                // add boxes
                x = x + pit * 118;
            }

            return new Point2D.Double(x, y);
        }

        /**
         * gets the pit's center as a point
         * @param pit
         * @return Point object
         */
        public Point2D.Double getPitCenterPoint(int pit) {
            double yCoordinate = getPitPoint(pit).getY();
            double xCoordinate = getPitPoint(pit).getX();
            //if not a store
            if (pit != 6 && pit != 13) {
                yCoordinate = yCoordinate + 59;
                xCoordinate = xCoordinate + 59;
            }
            return new Point2D.Double(xCoordinate, yCoordinate);
        }


    }
}
