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

    private static final Logger appLogger = LoggerFactory.getLogger(Output.class);

    private String urlOriginal;
    private String target;

    public PrepareData(String urlOriginal, String target) {
        this.urlOriginal = urlOriginal;
        this.target = target;
    }

    public List<String> prepare() throws IOException {
        List<String> potentialTargets = new ArrayList<>(Arrays.asList(target.split("-")));

        Document docOriginal = Jsoup.connect(urlOriginal).get();
        Element id = docOriginal.getElementById(target);
        String text = id.text();

        potentialTargets.addAll(Arrays.asList(text.split(" ")));

        return potentialTargets;

    }


}
