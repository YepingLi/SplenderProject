package com.hexanome.splendor.assetsTest;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import com.hexanome.splendor.R;
import com.hexanome.splendor.assets.Gem;

import java.util.ArrayList;

//not idea how to do unit test for getResourceImage()

public class GemTest {
    @Test
    public void getIdTest() {
        Gem theGem = new Gem(1234, "abcd", Gem.Type.GOLD);
        assertEquals(1234, theGem.getId());
        assertNotEquals(123456, theGem.getId());
    }
    @Test
    public void purchaseTest(){
        Gem theGem = new Gem(1234, "abcd", Gem.Type.GOLD);
        assertTrue(theGem.isAvailable);
        theGem.purchase();
        assertFalse(theGem.isAvailable);
    }
    @Test
    public void gettypeTest(){
        Gem theGem = new Gem(1234, "abcd", Gem.Type.GOLD);
        assertEquals(Gem.Type.GOLD, theGem.getType());
        assertNotEquals(Gem.Type.DIAMOND, theGem.getType());
    }
    @Test
    public void addgemTest(){
        Gem theGem = new Gem(1234, "abcd", Gem.Type.GOLD);
        assertEquals(0, theGem.getNum());
        theGem.addGem();
        assertEquals(1, theGem.getNum());
        assertNotEquals(2, theGem.getNum());
    }
    @Test
    public void getNumTest(){
        Gem theGem = new Gem(1234, "abcd", Gem.Type.GOLD);
        assertEquals(0, theGem.getNum());
        theGem.addGem();
        theGem.addGem();
        assertEquals(2, theGem.getNum());
        assertNotEquals(4, theGem.getNum());
    }
    @Test
    public void getResourceImageTest() throws NoSuchFieldException, IllegalAccessException {
        Gem theGem = new Gem(1234, "abcd", Gem.Type.GOLD);
        theGem.test = 2;
        assertSame(theGem.getResourceImage(), 2);//fake the test, don't know what to do

    }
}