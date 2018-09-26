package com.dan1sh;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Output {

    private static final Logger LOGGER = LoggerFactory.getLogger(Output.class);

    private String filePath;
    private Optional<Elements> diffElements;

    public Output(Optional<Elements> diffElements, String filePath) {
        this.diffElements = diffElements;
        this.filePath = filePath;
    }

    public void createResult() {
        if (diffElements.get().size() != 0) {
            for (Element e : diffElements.get()){
                String path = getXPath(e);
                writeToOutputFile(path);
            }
            LOGGER.info("You can find the result on: " + filePath);
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
        catch(IOException e){
            LOGGER.error("File path is wrong");
            e.printStackTrace();
        }
    }

    private static String getItemXPath(Element element) {
        return String.format("%s[%s]", element.tagName(), element.elementSiblingIndex()+1);
    }
}
