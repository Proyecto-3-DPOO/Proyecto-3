package main.java.open.dpoo.controller;

import java.io.*;
import java.nio.file.*;
import java.util.logging.Logger;
import java.nio.charset.StandardCharsets;

public class FileHandler {
    private static final Logger logger = Logger.getLogger(FileHandler.class.getName());

    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
    }

    public static void writeFile(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void appendToFile(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}

