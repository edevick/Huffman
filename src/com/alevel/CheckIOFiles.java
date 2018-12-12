package com.alevel;

import java.io.*;
import java.util.Scanner;

public class CheckIOFiles {


    public static String checkFilenameExtension(String path) {

        int lastIndex = path.lastIndexOf(".");
        if (lastIndex == -1) {
            throw new IllegalArgumentException("Filename should contain the extension.");
        }
        return path.substring(lastIndex);
    }

    public static void caseHuffmanFile(String file) {
        System.out.println("Enter dictionary location:");
        Scanner scanner = new Scanner(System.in);
        String dictLocation = scanner.nextLine();
        byte[] bytes = readFile(file);
        Dictionary dictionary = readDictionary(dictLocation);
        int[] bytesInt = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++)
            bytesInt[i] = bytes[i] & 0xFF;
        Decompression decompressor = new Decompression(bytesInt, dictionary);
        System.out.println("Enter decompressed.txt file location:");
        String decFileLocation = scanner.nextLine();
        writeDecompressedFile(decompressor.decompress(), decFileLocation);
    }

    public static void caseAnotherFile(String file) {
        Scanner scanner = new Scanner(System.in);
        Compression compressor = new Compression(file);
        CompressionResult compressionResult = compressor.toCompress();
        System.out.println("\nEnter compressed file path");
        String filePath = scanner.nextLine();
        System.out.println("\nEnter dictionary file path");
        String dictPath = scanner.nextLine();
        writeCompressedFile(compressionResult.getLength(), compressionResult.getBytes(compressionResult.getBytesInt()), compressor.getDictionary(), filePath, dictPath);
    }

    private static void writeDecompressedFile(byte[] content, String filePath) {
        try (FileOutputStream decompressedFos = new FileOutputStream(filePath)) {
            decompressedFos.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void writeCompressedFile(int size, byte[] content, Dictionary dictionary, String filePath, String dictPath) {
        try (FileOutputStream compressedFos = new FileOutputStream(filePath);
             FileOutputStream dict = new FileOutputStream(dictPath)) {

            ObjectOutputStream oos = new ObjectOutputStream(dict);
            oos.writeObject(dictionary);
            oos.close();

            compressedFos.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static Dictionary readDictionary(String dictPath) {
        try (FileInputStream fis = new FileInputStream(dictPath)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (Dictionary) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
            return null;
        }
    }


}
