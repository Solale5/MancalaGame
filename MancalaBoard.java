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
    private boolean needsToConfirm;

    public MancalaBoard() {
        stones = new int[]{4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0};
        p1turn = true;
        p2turn = false;
    }

    public void setArray(int i) {

        int[] newStones = new int[14];
        for (int j = 0; j < newStones.length; j++) {
            if (j != 6 && j != 13) {
                newStones[j] = i;
            }
        }
        stones = newStones;
        l.stateChanged(new ChangeEvent(this));

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

    public boolean moveStones(int slot) {
        if(needsToConfirm)
        {
            return true;
        }
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
                if(theStones == 0)
                {
                    break;
                }
                pointer = 0;
                if(theStones > 0)
                {
                    stones[pointer]++;
                }
            } else {
                stones[pointer]++;
                theStones--;
            }
        }
        //Condition if player ends turn in their mancala -> Go again
        if((p1turn) && (pointer == 6))
        {
            l.stateChanged(new ChangeEvent(this));
            return true;
        }
        else if((p2turn) && (pointer == 13))
        {
            l.stateChanged(new ChangeEvent(this));
            return true;
        }

        //Condition if player ends on empty pit of their own -> capture enemy's stones across from pit
        int oppositePit = 12 - pointer;
        if(pointer!=6 && pointer != 13)
        { ;
            if((p1turn) && (stones[pointer] == 1) && (stones[oppositePit] != 0))
            {
                stones[6]+= stones[pointer] + stones[oppositePit];
                stones[pointer] = 0;
                stones[oppositePit] = 0;
            }
            else if((p2turn) && (stones[pointer] == 1) && (stones[oppositePit] != 0))
            {
                stones[13] += stones[pointer] + stones[oppositePit];
                stones[pointer] = 0;
                stones[oppositePit] = 0;
            }
        }

        l.stateChanged(new ChangeEvent(this));
        needsToConfirm = true;
        return true;
    }
    

    public boolean stonesMoved() {
        return stonesMoved;
    }

    public void setNeedsToConfirm(boolean x)
    {
        needsToConfirm = x;
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

}