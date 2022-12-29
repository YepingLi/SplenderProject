package com.hexanome.splendor.assetsTest;


import static org.junit.Assert.assertSame;
import com.hexanome.splendor.assets.Bonus;
import com.hexanome.splendor.assets.Card;
import com.hexanome.splendor.assets.GameBoard;
import com.hexanome.splendor.assets.Gem;
import com.hexanome.splendor.assets.Noble;
import com.hexanome.splendor.assets.Player;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GameBoardTest {
    List<Gem> gemListGold= new ArrayList<Gem>();
    List<Gem> gemListDimand= new ArrayList<Gem>();
    List<Gem> gemListEmerald= new ArrayList<Gem>();
    List<Gem> gemListOnyx = new ArrayList<Gem>();
    List<Gem> gemListRuby= new ArrayList<Gem>();
    List<Gem> gemListSapphire= new ArrayList<Gem>();
    List<Bonus> bonusList= new ArrayList<Bonus>();
    List<Card> cardList= new ArrayList<Card>();
    List<Noble> nobleList = new ArrayList<Noble>();
    Player testPlayer = new Player("abc", gemListGold, gemListDimand, gemListEmerald,
            gemListOnyx, gemListRuby, gemListSapphire,bonusList, cardList, nobleList);

    ArrayList<Gem> gemListGoldB= new ArrayList<Gem>();
    ArrayList<Gem> gemListDimandB= new ArrayList<Gem>();
    ArrayList<Gem> gemListEmeraldB= new ArrayList<Gem>();
    ArrayList<Gem> gemListOnyxB = new ArrayList<Gem>();
    ArrayList<Gem> gemListRubyB= new ArrayList<Gem>();
    ArrayList<Gem> gemListSapphireB= new ArrayList<Gem>();
    ArrayList<Bonus> bonusListB= new ArrayList<Bonus>();
    ArrayList<Card> cardListB= new ArrayList<Card>();

    GameBoard testGameBoard = new GameBoard(testPlayer, cardListB, gemListGoldB,
            gemListDimandB, gemListEmeraldB, gemListOnyxB, gemListRubyB, gemListSapphireB);
    @Test
    public void getAvailableCardsTest(){
        assertSame(testGameBoard.getAvailableCards(), cardListB);
    }
    @Test
    public void getCurPlayerTest(){
        assertSame(testGameBoard.getCurPlayer(), testPlayer);
    }
    @Test
    public void getAvailableGoldTest(){
        assertSame(testGameBoard.getAvailableGold(), gemListGoldB);
    }
    @Test
    public void getAvailableEmeraldTest(){
        assertSame(testGameBoard.getAvailableEmerald(), gemListEmeraldB);
    }
    @Test
    public void getAvailableOnyxTest(){
        assertSame(testGameBoard.getAvailableOnyx(), gemListOnyxB);
    }
    @Test
    public void getAvailableSapphireTest(){
        assertSame(testGameBoard.getAvailableSapphire(), gemListSapphireB);
    }
    @Test
    public void getAvailableRuby(){
        assertSame(testGameBoard.getAvailableRuby(), gemListRubyB);
    }


