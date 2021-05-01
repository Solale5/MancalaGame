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
        JTextArea jta = new JTextArea("Pick a board color");

        Button blue = new Button("Blue");
        Button red = new Button("Red");
        Button black = new Button("Black");


        start.add(jta);
        start.add(black);
        start.add(blue);
        start.add(red);


        start.setSize(777, 500);
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setVisible(true);


        JFrame frame = new JFrame("Mancala");
        frame.setSize(777, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MancalaBoard mb = new MancalaBoard();

        View v = new View(mb);

        frame.add(v);
        mb.attach(v);
        black.addActionListener(e -> {
                    v.setColor(Color.black);
                    start.setVisible(false);

                    frame.setVisible(true);

                }
        );
        blue.addActionListener(e -> {
                    v.setColor(Color.blue);
                    start.setVisible(false);
                    frame.setVisible(true);
                }
        );
        red.addActionListener(e -> {
                    v.setColor(Color.red);
                    start.setVisible(false);
                    frame.setVisible(true);
                }
        );


    }


}
