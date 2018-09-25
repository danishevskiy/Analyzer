package com.dan1sh;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Output {

    private static final Logger appLogger = LoggerFactory.getLogger(Output.class);

    private String filePath;
    private Elements diffElements;

    public Output(Elements diffElements, String filePath) {
        this.diffElements = diffElements;
        this.filePath = filePath;
    }

    public void createResult() {
        if (diffElements.size()!=0) {
            for (Element e : diffElements){
                String path = getXPath(e);
                writeToOutputFile(path);
            }
            appLogger.info("You can find the result on: " + filePath);
        }
    }

    private String getXPath(Element element) {
        List<String> item = element.parents()
                .stream()
                .map(Output::getItemXPath)
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= item.size() - 1; i++) {
            sb.append(item.get(item.size()-1 - i)).append(">");
        }
        sb.append(getItemXPath(element));

        return sb.toString();
    }

    private void writeToOutputFile(String s) {
        try {
            Files.write(Paths.get(filePath), s.getBytes());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static String getItemXPath(Element element) {
        return String.format("%s[%s]", element.tagName(), element.elementSiblingIndex()+1);
    }
}
