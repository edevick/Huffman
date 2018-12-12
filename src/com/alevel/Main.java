package com.alevel;

import java.util.*;

import static com.alevel.CheckIOFiles.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("\nFile location:");
        Scanner scanner = new Scanner(System.in);
        String file = scanner.nextLine();
        String ext = checkFilenameExtension(file);
        if (ext.equals(".hf")) {
            caseHuffmanFile(file);
        } else {
            caseAnotherFile(file);
        }
    }

}
