import javax.swing.*;

public class Main {

    /**
     * This method is run when the program starts
     *
     * @param args arguments passed to the program
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mancala");
        frame.setSize(777, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MancalaBoard mb = new MancalaBoard();

        View v = new View(mb);

        frame.add(v);


        frame.setVisible(true);
    }


}
