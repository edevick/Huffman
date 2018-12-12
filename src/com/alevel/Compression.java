package com.alevel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class Compression {
    public int[] array = new int[256];//array of repetitions of symbols
    Dictionary dictionary;
    public String fileName;
    private int[] fileBytes;
    public static int lengthDict;
    private CompressionResult compressionResult;

    public Compression(String fileName) {
        this.fileName = fileName;
        this.fileBytes = byteArrayToIntArray(readFile(fileName));
    }

    public static int getLength() {
        return lengthDict;
    }

    public void setLength(int length) {
        this.lengthDict = length;
    }

    public CompressionResult toCompress() {
        dictionary = new Dictionary();
        CompressionResult.ResultBuilder compressionResultBuilder = CompressionResult.newBuilder();
        array = toCalcFrequency(fileName);
        Map<Integer, Bit[]> dict = createDictionary();
        dictionary.setDictionary(dict);

        for (int i : fileBytes) {
            for (Bit bit : dictionary.getDictionary().get(i)) {
                compressionResultBuilder.addBit(bit);
            }
        }
        compressionResult = compressionResultBuilder.build();
        int l = compressionResult.getLength();
        dictionary.setLength(l);
        setLength(l);
        return compressionResult;

    }

    private static byte[] readFile(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] content = fis.readAllBytes();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int[] byteArrayToIntArray(byte[] array) {
        int[] intArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            intArray[i] = array[i] & 0xFF;
        }
        return intArray;
    }


    private int[] toCalcFrequency(String fileName) {
        int[] array = new int[256];
        for (byte b : readFile(fileName)) {
            int i = b & 0xFF;
            array[i]++;
        }
        return array;
    }

    public PriorityQueue<Node> putInQueue() {
        PriorityQueue<Node> nodes = new PriorityQueue<>(Comparator.comparingInt(Node::getWeight));
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                nodes.add(new Node(i, array[i]));
            }
        }
        return nodes;
    }

    public PriorityQueue<Node> createTree() {
        PriorityQueue<Node> tree = putInQueue();

        while (tree.size() > 1) {
            Node firstNode = tree.poll();
            Node secondNode = tree.poll();
            tree.offer(Node.builder()
                    .setLeftNode(firstNode)
                    .setRightNode(secondNode)
                    .setValueNode(firstNode.getValue() + secondNode.getValue())
                    .setWeightNode(firstNode.getWeight() + secondNode.getWeight())
                    .biuldNode());

        }
        return tree;
    }

    public Map<Integer, Bit[]> createDictionary() {
        Map<Integer, Bit[]> dict = new HashMap<>();
        PriorityQueue<Node> huffmanTree = createTree();
        dict = collectDictionaryData(huffmanTree.peek(), dict, "");
        /*for debbuging only loop:
        for (Map.Entry<Integer, Bit[]> entry : dict.entrySet()) {
            System.out.print(entry.getKey());
            for (int i = 0; i < entry.getValue().length; i++) {
                System.out.println(" " + entry.getValue()[i] + " ");

            }

        }*/
        return dict;
    }

    public Map<Integer, Bit[]> collectDictionaryData(Node node, Map<Integer, Bit[]> dictionary, String codeString) {

        if (node.isLeaf()) {
            dictionary.put(node.getValue(), buildBitArray(codeString));
        } else {
            collectDictionaryData(node.getLeft(), dictionary, codeString + "0");
            collectDictionaryData(node.getRight(), dictionary, codeString + "1");
        }
        return dictionary;
    }

    public Bit[] buildBitArray(String codeString) {
        Bit arrayBits[] = new Bit[codeString.length()];
        for (int i = 0; i < codeString.toCharArray().length; i++) {
            if (codeString.toCharArray()[i] == '0')
                arrayBits[i] = Bit.ZERO;
            if (codeString.toCharArray()[i] == '1')
                arrayBits[i] = Bit.ONE;
        }


        return arrayBits;
    }


    public Dictionary getDictionary() {
        return dictionary;
    }

}
