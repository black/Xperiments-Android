package com.example.userdictionarydemo;

public class Dictionary {
    private String word;
    private String locale;

    public Dictionary(String word, String locale) {
        this.word = word;
        this.locale = locale;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
