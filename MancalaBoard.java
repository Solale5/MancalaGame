import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MancalaBoard {


    ChangeListener l;

    //pitStones
    private int[] stones = new int[]{4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0};

    private int player = 1;


    public MancalaBoard() {


    }

    public void attach(ChangeListener ml) {
        l = ml;
    }


    /**
     * Perform a player's by automoving the stones
     *
     * @param slot the pit that a player picks from that maps to the array index
     * @return whether the user's turn is ended
     */
    protected boolean moveStones(int slot) {
        int pointer = slot;

        // returns if slot is empty
        if (stones[slot] < 1) {
            return true;
        }

        // take stones out of pit
        int theStones = stones[slot];
        stones[slot] = 0;

        while (theStones > 0) {
            ++pointer;

            //  reset pointer
            if (pointer == 13) {
                stones[pointer]++;
                theStones--;
                pointer = 0;
                stones[pointer]++;
                theStones--;
            } else {
                stones[pointer]++;
                theStones--;
            }


        }

        l.stateChanged(new ChangeEvent(this));
        return true;

    }


    public int[] getData() {
        return stones;
    }

    public static void main(String[] args) {
        MancalaBoard b = new MancalaBoard();
        for (int i = 0; i < b.stones.length; i++) {
            System.out.print(b.stones[i]);

        }
        System.out.println();
        b.moveStones(1);
        for (int i = 0; i < b.stones.length; i++) {
            System.out.print(b.stones[i]);

        }
        System.out.println();
    }
}
