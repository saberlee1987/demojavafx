package com.saber.demojavafx.utils;

import java.util.concurrent.ThreadLocalRandom;

public class IranianMobileGenerator {
    private IranianMobileGenerator() {
    }
    public static String generate() {
        int prefix = ThreadLocalRandom.current()
                .nextInt(10, 100);
        int number = ThreadLocalRandom.current()
                .nextInt(0, 10000000);
        return String.format("09%d%07d", prefix, number);
    }
}
