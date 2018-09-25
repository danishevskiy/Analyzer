package com.dan1sh;

import org.jsoup.helper.Validate;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class App {

    private static final Logger appLogger = LoggerFactory.getLogger(App.class);
    private static final String FILE_PATH = "result.txt";

    public static void main(String[] args) throws IOException {

        Validate.isTrue(args.length == 3, "usage: supply two URLs to fetch and original attribute");
        String urlOriginal = args[0];
        String urlDiff = args[1];
        String target = args[2]; //selector #id

        appLogger.info(String.format("Original: %s", urlOriginal));
        appLogger.info(String.format("Diff: %s", urlDiff));
        appLogger.info(String.format("Target: %s", target));

        PrepareData prepareData = new PrepareData(urlOriginal, target);
        List<String> potentialTargets = prepareData.prepare();

        SimpleAnalyzer analyzer = new SimpleAnalyzer(urlDiff, potentialTargets);
        Elements elements = analyzer.analyze();

        Output output = new Output(elements, FILE_PATH);
        output.createResult();
    }

}
