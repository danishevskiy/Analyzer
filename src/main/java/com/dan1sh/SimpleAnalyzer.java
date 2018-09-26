package com.dan1sh;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class SimpleAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Output.class);

    private String urlDiff;
    private List<String> potentialTargets;

    public SimpleAnalyzer(String urlDiff, List<String> potentialTargets) {
        this.urlDiff = urlDiff;
        this.potentialTargets = potentialTargets;
    }

    public Elements analyze() {
        Document docDiff = null;
        try {
            docDiff = Jsoup.connect(urlDiff).get();
        } catch (IOException e) {
            LOGGER.error("URL diff isn't correct or null");
            e.printStackTrace();
        }
        Elements temp = new Elements();
        int numberСoincidences = 0;
        int size = potentialTargets.size();

        for (String potentialTarget : potentialTargets) {
            Elements elements = docDiff.select("a:contains(" + potentialTarget +")");
            // simple algorithm for analysis
            if (numberСoincidences >= (size/2)) {
                LOGGER.info("It's potential element");
                break;
            }
            for (Element e : elements){
                if(e.hasText() || e.text().contains(potentialTarget)){
                    numberСoincidences++;
                    temp.add(e);
                }
            }
        }

        return temp;
    }
}
