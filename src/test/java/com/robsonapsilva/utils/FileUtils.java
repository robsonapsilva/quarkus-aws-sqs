package com.robsonapsilva.utils;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static String readFile(String path) {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(path);
            File file = new File(url.getFile());
            return Files.readString(Path.of(file.getPath()), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}