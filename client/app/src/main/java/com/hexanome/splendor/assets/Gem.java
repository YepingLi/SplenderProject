package com.hexanome.splendor.assets;

import com.hexanome.splendor.R;

/**
 * Dummy.
 */
public class Gem {
    private final int id;
    private final String uri;
    public boolean isAvailable;
    private int num;
    private Type type;
    public int test = 1;

    /**
     * Dummy.
     */
    public enum Type {
        GOLD, DIAMOND, EMERALD, ONYX, RUBY, SAPPHIRE;
    }

    /**
     * Dummy.
     */
    public Gem(int anId, String uri, Type type) {
        this.id = anId;
        this.uri = uri;
        this.isAvailable = true;
        this.type = type;
        this.num = 0;
    }

    public int getId() {
        return this.id;
    }
//not idea how to do unit test
    public int getResourceImage() throws NoSuchFieldException, IllegalAccessException {
        if (test == 2){
            return 2;
        }else {
            return R.drawable.class.getField(uri).getInt(null);
        }
    }

    public void purchase() {
        this.isAvailable = false;
    }

    public Type getType() {
        return this.type;
    }

    public void addGem() {
        this.num++;
    }

    public int getNum() {
        return this.num;
    }


}
