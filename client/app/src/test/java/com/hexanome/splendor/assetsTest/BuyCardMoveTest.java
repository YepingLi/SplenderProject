package com.hexanome.splendor.assetsTest;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import com.hexanome.splendor.assets.Bonus;
import com.hexanome.splendor.assets.BuyCardMove;
import com.hexanome.splendor.assets.Card;
import com.hexanome.splendor.assets.Gem;
import com.hexanome.splendor.assets.Noble;
import com.hexanome.splendor.assets.Player;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BuyCardMoveTest {
    @Test
    public void getPlayerTest(){
        List<Gem> gemListGold= new ArrayList<Gem>();
        List<Gem> gemListDimand= new ArrayList<Gem>();
        List<Gem> gemListEmerald= new ArrayList<Gem>();
        List<Gem> gemListOnyx = new ArrayList<Gem>();
        List<Gem> gemListRuby= new ArrayList<Gem>();
        List<Gem> gemListSapphire= new ArrayList<Gem>();
        List<Bonus> bonusList= new ArrayList<Bonus>();
        List<Card> cardList= new ArrayList<Card>();
        List<Noble> nobleList= new ArrayList<Noble>();
        Bonus Bunus1 = new Bonus(Bonus.BonusType.RUBY, 2);
        Player testPlayer1 = new Player("abc", gemListGold, gemListDimand, gemListEmerald,
                gemListOnyx, gemListRuby, gemListSapphire,bonusList, cardList, nobleList);
        Player testPlayer2 = new Player("efg", gemListGold, gemListDimand, gemListEmerald,
                gemListOnyx, gemListRuby, gemListSapphire,bonusList, cardList, nobleList);
        Card card1 = new Card(456, 1, Bunus1, Card.State.PURCHASED,"efg", false);
        BuyCardMove testBuyCardMove = new BuyCardMove(testPlayer1, card1);

        assertSame(testPlayer1, testBuyCardMove.getPlayer());
        assertNotSame(testPlayer2, testBuyCardMove.getPlayer());
    }
}
