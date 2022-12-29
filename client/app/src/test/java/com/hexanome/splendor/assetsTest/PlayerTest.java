package com.hexanome.splendor.assetsTest;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import com.hexanome.splendor.assets.Bonus;
import com.hexanome.splendor.assets.Card;
import com.hexanome.splendor.assets.Gem;
import com.hexanome.splendor.assets.Noble;
import com.hexanome.splendor.assets.Player;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class PlayerTest {
    Gem Gold1 = new Gem(10, "aa", Gem.Type.GOLD);
    Gem Dimand1 = new Gem(20, "ba", Gem.Type.DIAMOND);
    Gem Emerald1 = new Gem(30, "ca", Gem.Type.EMERALD);
    Gem Onyx1 = new Gem(40, "da", Gem.Type.ONYX);
    Gem Ruby1 = new Gem(60, "ea", Gem.Type.RUBY);
    Gem Sapphire1 = new Gem(60, "fa", Gem.Type.SAPPHIRE);
    Bonus Bunus1 = new Bonus(Bonus.BonusType.RUBY, 2);
    Card card1 = new Card(123, 1, Bunus1, "abc", true);
    List<Gem> gemListNoblegem= new ArrayList<Gem>();
    Noble noble1 = new Noble(1, gemListNoblegem);
    List<Gem> gemListGold= new ArrayList<Gem>();
    List<Gem> gemListDimand= new ArrayList<Gem>();
    List<Gem> gemListEmerald= new ArrayList<Gem>();
    List<Gem> gemListOnyx = new ArrayList<Gem>();
    List<Gem> gemListRuby= new ArrayList<Gem>();
    List<Gem> gemListSapphire= new ArrayList<Gem>();
    List<Bonus> bonusList= new ArrayList<Bonus>();
    List<Card> cardList= new ArrayList<Card>();
    List<Noble> nobleList= new ArrayList<Noble>();
    Player testPlayer = new Player("abc", gemListGold, gemListDimand, gemListEmerald,
            gemListOnyx, gemListRuby, gemListSapphire,bonusList, cardList, nobleList);
    @Test
    public void getMyGoldTest(){
        gemListGold.add(Gold1);
        assertSame(gemListGold, testPlayer.getMyGold());
        assertNotSame(gemListDimand, testPlayer.getMyGold());
    }
    @Test
    public void getDimandTest(){
        gemListDimand.add(Dimand1);
        assertSame(gemListDimand, testPlayer.getMyDiamond());
        assertNotSame(gemListGold, testPlayer.getMyDiamond());
    }
    @Test
    public void getMyEmeraldTest(){
        gemListEmerald.add(Emerald1);
        assertSame(gemListEmerald, testPlayer.getMyEmerald());
        assertNotSame(gemListDimand, testPlayer.getMyEmerald());
    }
    @Test
    public void getOnyxTest(){
        gemListOnyx.add(Onyx1);
        assertSame(gemListOnyx, testPlayer.getMyOnyx());
        assertNotSame(gemListDimand, testPlayer.getMyOnyx());
    }
    @Test
    public void getMyGRubyTest(){
        gemListRuby.add(Ruby1);
        assertSame(gemListRuby, testPlayer.getMyRuby());
        assertNotSame(gemListDimand, testPlayer.getMyRuby());
    }
    @Test
    public void getMySapphireTest(){
        gemListSapphire.add(Sapphire1);
        assertSame(gemListSapphire, testPlayer.getMySapphire());
        assertNotSame(gemListDimand, testPlayer.getMySapphire());
    }
}
