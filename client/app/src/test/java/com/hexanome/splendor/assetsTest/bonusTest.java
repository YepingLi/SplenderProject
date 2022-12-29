package com.hexanome.splendor.assetsTest;
import org.junit.Test;
import static org.junit.Assert.*;

import com.hexanome.splendor.assets.Bonus;

public class bonusTest {
    @Test
    public void addBonusTest() {
    Bonus theBonus = new Bonus(Bonus.BonusType.DIAMOND);
    assertEquals(0, theBonus.getNum());
    theBonus.addBonus();
    assertEquals(1, theBonus.getNum());
    theBonus.addBonus();
    assertEquals(2, theBonus.getNum());
    }
}
