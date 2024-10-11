package com.jetbrains.intellij.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class LengthRequest {
    private String strings;
    private int maxLength;

    public LengthRequest() {}

    public LengthRequest(String strings, int maxLength) {
        this.strings = strings;
        this.maxLength = maxLength;
    }

    public String getStrings() {
        return strings;
    }

    public void setStrings(String strings) {
        this.strings = strings;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
