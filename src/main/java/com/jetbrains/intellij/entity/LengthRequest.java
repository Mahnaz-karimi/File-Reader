package com.jetbrains.intellij.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class LengthRequest {
    private List<String> strings;
    private int maxLength;
    @JsonCreator
    public LengthRequest(List<String> strings, int maxLength) {
        this.strings = strings;
        this.maxLength = maxLength;
    }

    public LengthRequest() {

    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
