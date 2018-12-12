package com.alevel;

import java.util.ArrayList;
import java.util.Map;


public class Decompression {

    public int[] fileBytes;
    Dictionary dictionary;


    public Decompression(int[] fileBytes, Dictionary dictionary) {
        this.fileBytes = fileBytes;
        this.dictionary = dictionary;
    }

    public byte[] decompress() {
        ArrayList<Integer> bytesInt = new ArrayList<>();
        String bytes = "";
        for (String s : bytesToString(fileBytes)) {
            bytes += s;
        }

        int size = bytes.length() - dictionary.getLenght();
        Map<Integer, Bit[]> dict = dictionary.getDictionary();

        return fetchBytes(size, dict, bytes, bytesInt);


    }

    private byte[] fetchBytes(int size, Map<Integer, Bit[]> dict, String bytes, ArrayList<Integer> bytesInt) {
        while (bytes.length() > size) {
            for (int key : dict.keySet()) {
                if (bytes.startsWith(bitArrayToString(dict.get(key)))) {
                    bytesInt.add(key);
                    bytes = bytes.substring(dict.get(key).length);
                    fetchBytes(bytes.length(), dict, bytes, bytesInt);
                }
            }
        }
        return intArrayToByte(bytesInt.toArray(new Integer[0]));
    }

    public static String bitArrayToString(Bit[] array) {
        String res = "";
        for (Bit bit : array) {
            if (bit == Bit.ONE) res += "1";
            if (bit == Bit.ZERO) res += "0";
        }
        return res;
    }

    public static byte[] intArrayToByte(Integer[] arr) {
        byte[] bytes = new byte[arr.length];
        for (Integer i = 0; i < arr.length; i++) {
            bytes[i] = arr[i].byteValue();
        }
        return bytes;
    }

    public static String[] bytesToString(int[] bytes) {
        String[] bytesString = new String[bytes.length];
        for (int i = 0; i < bytesString.length; i++) {
            bytesString[i] = Integer.toString(bytes[i], 2);
            if (bytesString[i].length() < 8) {
                int size = 8 - bytesString[i].length();
                for (int y = 0; y < size; y++) {
                    bytesString[i] = "0" + bytesString[i];
                }
            }
        }
        return bytesString;
    }


}
