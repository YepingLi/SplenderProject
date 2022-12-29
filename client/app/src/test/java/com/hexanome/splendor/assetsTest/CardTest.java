package com.hexanome.splendor.assetsTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.hexanome.splendor.assets.Bonus;
import com.hexanome.splendor.assets.Card;
import com.hexanome.splendor.assets.Gem;
import com.hexanome.splendor.assets.Noble;
import com.hexanome.splendor.assets.Player;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

//missing getResourceImageTest
public class CardTest {
    Bonus Bunus1 = new Bonus(Bonus.BonusType.RUBY, 2);
    Bonus Bunus2 = new Bonus(Bonus.BonusType.DIAMOND, 3);
    Card card1 = new Card(123, 1, Bunus1, Card.State.FREE, "abc", true);
    Card card2 = new Card(456, 1, Bunus2, Card.State.PURCHASED,"efg", false);
    Card card3 = new Card(789, 1, Bunus1, Card.State.RESERVED,"hig", false);
    @Test
    public void isTerminatingTest(){
        assertTrue(card1.isTerminating());
        assertFalse(card2.isTerminating());
    }

    @Test
    public void getIdTest(){
        assertEquals(123,card1.getId());
        assertNotEquals(123,card2.getId());
    }

    @Test
    public void getStateTest(){
        assertEquals(Card.State.FREE,card1.getState());
        assertNotEquals(Card.State.PURCHASED, card3.getState());
    }

    @Test
    public void changeStateTestNobranch(){
        //unchange => no branch
        assertEquals(Card.State.PURCHASED,card2.getState());
        card2.changeState(Card.State.FREE);
        assertEquals(Card.State.PURCHASED,card2.getState());

        assertEquals(Card.State.RESERVED,card3.getState());
        card3.changeState(Card.State.FREE);
        assertEquals(Card.State.RESERVED,card3.getState());
    }
    @Test
    public void changeStateTestIfbranch(){
        //first if branch, 2 cases
        assertEquals(Card.State.FREE,card1.getState());
        card1.changeState(Card.State.PURCHASED);
        assertEquals(Card.State.PURCHASED,card1.getState());

        assertEquals(Card.State.RESERVED,card3.getState());
        card3.changeState(Card.State.PURCHASED);
        assertEquals(Card.State.PURCHASED,card3.getState());
    }
    @Test
    public void changeStateTestElseifbranch(){
        //second Elseif branch one case
        assertEquals(Card.State.FREE, card1.getState());
        card1.changeState(Card.State.RESERVED);
        assertEquals(Card.State.RESERVED, card1.getState());
    }
    @Test
    public void getBonusTest(){
        assertSame(Bunus1, card1.getBonus());
        assertNotSame(Bunus1, card2.getBonus());
    }
    @Test
    public void getPrestiagePointsTest(){
        assertEquals(1, card1.getPrestiagePoints());
        assertNotEquals(2, card1.getPrestiagePoints());
    }
}
