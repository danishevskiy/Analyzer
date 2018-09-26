package com.dan1sh;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrepareData {

    private static final Logger LOGGER = LoggerFactory.getLogger(Output.class);

    private String urlOriginal;
    private String target;

    public PrepareData(String urlOriginal, String target) {
        this.urlOriginal = urlOriginal;
        this.target = target;
    }

    public List<String> prepare() {
        List<String> potentialTargets = new ArrayList<>(Arrays.asList(target.split("-")));

        Document docOriginal = null;
        try {
            docOriginal = Jsoup.connect(urlOriginal).get();
        } catch (IOException e) {
            LOGGER.error("URL original isn't correct or null");
            e.printStackTrace();
        }
        Element id = docOriginal.getElementById(target);
        String text = id.text();

        potentialTargets.addAll(Arrays.asList(text.split(" ")));

        return potentialTargets;

    }


}
