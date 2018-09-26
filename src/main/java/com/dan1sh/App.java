package com.dan1sh;

import org.jsoup.helper.Validate;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
// https://agileengine.bitbucket.io/beKIvpUlPMtzhfAy/samples/sample-0-origin.html https://agileengine.bitbucket.io/beKIvpUlPMtzhfAy/samples/sample-2-container-and-clone.html make-everything-ok-button

// file:///C:/sample-0-origin.html file:///C:/sample-1-evil-gemini.html make-everything-ok-button
public class App {

    private static final Logger APP_LOGGER = LoggerFactory.getLogger(App.class);

    private static final String FILE_PATH = "result.txt";

    public static void main(String[] args) {

        Validate.isTrue(args.length == 3, "usage: supply two URLs to fetch and original attribute");
        String urlOriginal = args[0];
        String urlDiff = args[1];
        String target = args[2]; //selector #id

        APP_LOGGER.info(String.format("Original: %s", urlOriginal));
        APP_LOGGER.info(String.format("Diff: %s", urlDiff));
        APP_LOGGER.info(String.format("Target: %s", target));

        PrepareData prepareData = new PrepareData(urlOriginal, target);
        List<String> potentialTargets = prepareData.prepare();

        SimpleAnalyzer analyzer = new SimpleAnalyzer(urlDiff, potentialTargets);
        Optional<Elements> elements = Optional.ofNullable(analyzer.analyze());

        Output output = new Output(elements, FILE_PATH);
        output.createResult();
    }

}
