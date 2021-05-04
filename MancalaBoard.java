import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MancalaBoard {
    ChangeListener l;
    //pitStones
    private int[] stones;
    private int[] prevStones;
    private int player = 1;
    private boolean p1turn;
    private boolean p2turn;
    private boolean stonesMoved;

    public MancalaBoard() {
        stones = new int[]{4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0};
        p1turn = true;
        p2turn = false;
    }

    public void attach(ChangeListener ml) {
        l = ml;
    }

    public int[] getPrevStones() {
        return prevStones;
    }

    public void setStones(int[] array) {
        stones = array;
    }

    /**
     * Perform a player's turn by automoving the stones
     *
     * @param slot the pit that a player picks from that maps to the array index
     * @return whether the user's turn is ended
     */
    protected boolean moveStones(int slot) {
        stonesMoved = true;
        int pointer = slot;
        prevStones = stones.clone();
        // returns if slot is empty
        if (stones[slot] < 1) {
            return true;
        }
        // returns if slot is the end pit
        if (slot == 6 || slot == 13) {
            return true;
        }
        // returns if slot selected is not the proper player's slot
        if (slot <= 5 && whichTurn() == 2) {
            return true;
        } else if (slot >= 7 && whichTurn() == 1) {
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

    public boolean stonesMoved() {
        return stonesMoved;
    }

    public void switchTurn() {
        stonesMoved = false;
        //After every turn the player's turn changes
        if (p1turn) {
            player = 2;
            p1turn = false;
            p2turn = true;
        } else if (p2turn) {
            player = 1;
            p1turn = true;
            p2turn = false;
        }
        l.stateChanged(new ChangeEvent(this));
    }

    public int whichTurn() {
        return player;
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
