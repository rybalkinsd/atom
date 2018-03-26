package ru.atom.chat;

import java.util.List;

public class FileUtils {
    private String fileName;
    private List<String> list;
    public FileUtils(String fileName, List<String> list)
    {
        this.fileName = fileName;
        this.list = list;
    }

//    public saveToFile()
//    {
//
//    }
//    List<String> clubNames = clubs.stream()
//            .map(Club::getName)
//            .collect(Collectors.toList())
//
//try {
//        Files.write(Paths.get(fileName), clubNames);
//    } catch (IOException e) {
//        log.error("Unable to write out names", e);
//    }
}
