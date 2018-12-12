package com.alevel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Dictionary implements Serializable {

    private Map<Integer, Bit[]> dictionary;
    private int length;

    public Dictionary() {

    }

    public Map<Integer, Bit[]> getDictionary() {
        return dictionary;
    }

    public void setDictionary(Map<Integer, Bit[]> dictionary) {
        this.dictionary = dictionary;
    }

    public int getLenght() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public static String bitArrayToString(Bit[] array) {
        String res = "";
        for (Bit bit : array) {
            if (bit == Bit.ONE) res += "1";
            if (bit == Bit.ZERO) res += "0";
        }
        return res;
    }

    @Override
    public String toString() {
        String res = "Dictionary: ";
        for (int i : dictionary.keySet()) {
            res += " [" + i + "]=[" + bitArrayToString(dictionary.get(i)) + "]";
        }
        res += "\nLength: " + length;
        return res;
    }
}
