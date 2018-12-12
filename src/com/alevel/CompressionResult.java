package com.alevel;

import java.util.ArrayList;
import java.util.List;

public class CompressionResult {
    private List<Integer> bytes;
    private List<String> bytesString;
    private int length;


    private CompressionResult(List<Integer> bytes, List<String> bytesString, int length) {
        this.bytes = bytes;
        this.bytesString = bytesString;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public Integer[] getBytesInt() {
        return bytes.toArray(new Integer[0]);
    }

    public static ResultBuilder newBuilder() {
        return new ResultBuilder();
    }

    public byte[] getBytes(Integer[] array) {
        byte[] bytes = new byte[array.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = this.bytes.get(i).byteValue();
        }
        return bytes;
    }


    public static class ResultBuilder {


        public List<Integer> bytes = new ArrayList<>();
        public int count = 0;
        private String stringByte = "";
        private boolean over = false;
        private int length = 0;
        private List<String> bytesString = new ArrayList<>();

        public ResultBuilder addBit(Bit bit) {
            if (count < 8) {
                stringByte += bit == Bit.ZERO ? "0" : "1";
                if (!over)
                    length++;
                count++;
            } else {
                bytes.add(Integer.parseInt(stringByte, 2));
                bytesString.add(stringByte);
                stringByte = "";
                count = 0;
                addBit(bit);
            }
            return this;
        }


        public void checkTail() {
            over = true;
            int size = 8 - stringByte.length();
            if (stringByte.length() < 8) {
                for (int i = 0; i < size; i++) {
                    addBit(Bit.ZERO);
                }
            }
            bytes.add(Integer.parseInt(stringByte, 2));
            bytesString.add(stringByte);
        }

        public CompressionResult build() {
            checkTail();
            return new CompressionResult(bytes, bytesString, length);
        }

    }

}
