package org.example.tutorial.hello.osgi.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.example.tutorial.hello.osgi.service.api.SayingTeller;

public class SayingTellerImpl implements SayingTeller {

    private List<String> sayingLines;

    private String sayingsResourcePath;

    private Random random;

    public void init() {
        random = new Random();
        sayingLines = new LinkedList<>();

        try (InputStream is = SayingTellerImpl.class.getResourceAsStream(sayingsResourcePath);
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr)) {
            String line = br.readLine();

            while (line != null) {
                line = line.trim();

                if (!line.isEmpty() && !line.startsWith("#")) {
                    sayingLines.add(line);
                }

                line = br.readLine();
            }
        }
        catch (IOException e) {
            System.out.println("Error in reading " + sayingsResourcePath + ". " + e);
        }
    }

    public void setSayingsResourcePath(String sayingsResourcePath) {
        this.sayingsResourcePath = sayingsResourcePath;
    }

    public String getSayingsResourcePath() {
        return sayingsResourcePath;
    }

    @Override
    public String tellSaying() {
        final int index = random.nextInt(sayingLines.size());
        return sayingLines.get(index);
    }

}
