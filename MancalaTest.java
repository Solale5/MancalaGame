import javax.swing.*;
import java.awt.*;

/**
 * @author Kevin Grewal, Solomon Alemu, Jeremy Esch
 */
public class MancalaTest {

    /**
     * This method is run when the program starts
     *
     * @param args arguments passed to the program
     */
    public static void main(String[] args) {
        JFrame start = new JFrame("Mancala Starting Screen");
        start.setLayout(new FlowLayout());
        JLabel jta = new JLabel("Pick a board color");

        JTextArea jta2 = new JTextArea("");
        Button blue = new Button("Blue");
        Button green = new Button("Green");
        Button black = new Button("Black");
        start.add(jta);
        start.add(jta2);
        start.add(black);
        start.add(blue);
        start.add(green);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        start.setSize(new Dimension(screenSize.width * 2 / 3, screenSize.height * 2 / 3));
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setLocationRelativeTo(null);
        start.setVisible(true);

        JFrame afterStart = new JFrame("Mancala Starting Screen");
        afterStart.setLayout(new FlowLayout());
        JLabel label= new JLabel("Pick the number of stones to begin with (3 or 4)");
        Button three = new Button("3");
        Button four = new Button("4");
        afterStart.add(label);
        afterStart.add(three);
        afterStart.add(four);



        JFrame frame = new JFrame("Mancala");
        frame.setSize(new Dimension(screenSize.width * 2 / 3, screenSize.height * 2 / 3));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MancalaBoard mb = new MancalaBoard();
        View v = new View(mb);
        frame.add(v);
        mb.attach(v);

        three.addActionListener(e -> {
            mb.setArray(3);
            int x = start.getX();
            int y = start.getY();
            frame.setLocation(x, y);
            frame.setVisible(true);
            afterStart.setVisible(false);
        });

        four.addActionListener(e -> {
            mb.setArray(4);
            int x = start.getX();
            int y = start.getY();
            frame.setLocation(x, y);
            frame.setVisible(true);
            afterStart.setVisible(false);
        });


        black.addActionListener(e -> {
                    BlackBoard b = new BlackBoard();
                    v.setDesign(b);
                    mb.afterStartFrame(afterStart);
                    start.setVisible(false);
                }
        );
        blue.addActionListener(e -> {
                BlueBoard b1 = new BlueBoard();
                v.setDesign(b1);
                mb.afterStartFrame(afterStart);
                start.setVisible(false);
                }
        );
        green.addActionListener(e -> {
                GreenBoard b2 = new GreenBoard();
                v.setDesign(b2);
                mb.afterStartFrame(afterStart);
                start.setVisible(false);
                }
        );
    }

}
