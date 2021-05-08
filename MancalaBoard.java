import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * @author Kevin Grewal, Solomon Alemu, Jeremy Esch
 */

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
    private boolean boardCleanedUp;

    /**
     * Mancala Board constructor
     */
    public MancalaBoard() {
        stones = new int[]{4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0};
        p1turn = true;
        p2turn = false;
        boardCleanedUp = false;
    }

    /**
     * sets initiall array
     * @param i
     */
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

    /**
     * attachs changeListener to Mancala Board
     * @param ml
     */
    public void attach(ChangeListener ml) {
        l = ml;
    }

    /**
     * returns previous board state
     * @return integer array
     */
    public int[] getPrevStones() {
        return prevStones;
    }

    /**
     * sets curr array to prev board state
     */
    public void setStones() {
        stones = prevStones;
    }

    /**
     * moves the stones
     * @param slot
     * @return true or false
     */
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

    /**
     * checks if game is over
     * @return true or false
     */
    public boolean gameFinished()
    {
        boolean gameState = true;
        boolean p1turn = true;
        int counter = 0;
        for(int i = 0; i <  stones.length-1; i++)
        {
            if(counter == 6) //Player finished check
            {
                if(gameState)
                {
                    break;
                }
            }

            if(stones[i] != 0)
            {
                gameState = false;
            }
            counter++;

            if(i > 6 && p1turn)
            {
                p1turn = false;
                counter = 0;
                gameState = true;
            }
        }
        return gameState;
    }

    /**
     * Returns the player that won and moves remaining stones to the end pits
     * @return winning player
     */
    public int getWinningPlayer()
    {
        int winner = 0;
        if(!boardCleanedUp)
        {
            cleanUpBoard();
        }
        if(stones[6] > stones[13])
        {
            winner = 1;
        }
        else if(stones[6] < stones[13])
        {
            winner = 2;
        }
        else if(stones[6] == stones[13])
        {
            winner = 3;
        }
        return winner;
    }

    /**
     * when the game ends, moves all stones from one side to that side’s end mancala
     */
    public void cleanUpBoard()
    {
        int storage = 0;
        for(int i = 0; i < stones.length; i++)
        {
            if(i == 6)
            {
                stones[6] += storage;
                storage = 0;
            }

            if(i == 13)
            {
                stones[13] += storage;
                storage = 0;
            }

            if(i != 6 && i != 13)
            {
                storage+= stones[i];
                stones[i] = 0;
            }
        }
        l.stateChanged(new ChangeEvent(this));
        boardCleanedUp = true;
    }

    /**
     * boolean
     * @return if stones moves
     */
    public boolean stonesMoved() {
        return stonesMoved;
    }

    /**
     * set needs to confirm to given boolean
     * @param x
     */
    public void setNeedsToConfirm(boolean x)
    {
        needsToConfirm = x;
    }

    /**
     * switches the turn from one player to another
     */
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

    /**
     * integer
     * @return what players turn it is
     */
    public int whichTurn() {
        return player;
    }

    /**
     * returns stones array
     * @return integer array
     */
    public int[] getData() {
        return stones;
    }

    /**
     * Takes in JFrame and visualizes it
     * @param afterStart
     */
    public void afterStartFrame(JFrame afterStart)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        afterStart.setSize(new Dimension(screenSize.width * 2 / 3, screenSize.height * 2 / 3));
        afterStart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        afterStart.setLocationRelativeTo(null);
        afterStart.setVisible(true);
    }

}