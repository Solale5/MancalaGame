public class MancalaBoard {


    private View view;

    //pitStones
    private int[] stones = new int[]{4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0};


    public MancalaBoard() {

        view = new View(this);
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

            // skip other player's storage pit and reset pointer
            if (pointer == 13) {
                pointer = 0;
            } else {
                stones[pointer]++;
                theStones--;
            }


        }
        return true;
    }

    /**
     * start opposite players turn
     */
    public void switchTurn() {


        // rotate game board
        int[] newStones = new int[14];
        System.arraycopy(stones, 7, newStones, 0, 7);
        System.arraycopy(stones, 0, newStones, 7, 7);

        stones = newStones;

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
