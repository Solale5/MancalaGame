import javax.swing.*;
import java.awt.*;

public class Main {


    /**
     * This method is run when the program starts
     *
     * @param args arguments passed to the program
     */
    public static void main(String[] args) {
        JFrame start = new JFrame("Mancala Starting Screen");
        start.setLayout(new FlowLayout());
        JLabel jta = new JLabel("Pick a board color and enter number of stones");

        JTextArea jta2 = new JTextArea("");
        Button blue = new Button("Blue");
        Button red = new Button("Green");
        Button black = new Button("Black");
        start.add(jta);
        start.add(jta2);
        start.add(black);
        start.add(blue);
        start.add(red);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        start.setSize(new Dimension(screenSize.width * 2 / 3, screenSize.height * 2 / 3));
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setLocationRelativeTo(null);
        start.setVisible(true);
        JFrame frame = new JFrame("Mancala");
        frame.setSize(new Dimension(screenSize.width * 2 / 3, screenSize.height * 2 / 3));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MancalaBoard mb = new MancalaBoard();
        View v = new View(mb);
        frame.add(v);
        mb.attach(v);


        black.addActionListener(e -> {
                    int jml = Integer.parseInt(jta2.getText());
                    mb.setArray(jml);
                    BlackBoard b = new BlackBoard();
                    v.setDesign(b);
                    int x = start.getX();
                    int y = start.getY();
                    frame.setLocation(x, y);
                    frame.setVisible(true);
                    start.setVisible(false);
                }
        );
        blue.addActionListener(e -> {
                    int jml = Integer.parseInt(jta2.getText());
                    mb.setArray(jml);
                    BlueBoard bl = new BlueBoard();
                    v.setDesign(bl);
                    int x = start.getX();
                    int y = start.getY();
                    frame.setLocation(x, y);
                    frame.setVisible(true);
                    start.setVisible(false);
                }
        );
        red.addActionListener(e -> {
                    int jml = Integer.parseInt(jta2.getText());
                    mb.setArray(jml);
                    GreenBoard gb = new GreenBoard();
                    v.setDesign(gb);
                    int x = start.getX();
                    int y = start.getY();
                    frame.setLocation(x, y);
                    frame.setVisible(true);
                    start.setVisible(false);
                }
        );
    }
}