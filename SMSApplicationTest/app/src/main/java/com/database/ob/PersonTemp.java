package com.database.ob;

/**
 * Created by 彪 on 2016/4/20.
 *
 *
 *
 * 建立空类作为显示的一个跳转，通过person的属性实例化personTemp传入到list中
 *
 */
public class PersonTemp {
    private String words;

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public PersonTemp(String words) {
        this.words = words;
    }
}
