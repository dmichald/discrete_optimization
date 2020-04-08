package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {
    public static List<String> generate(int listSize) {
        List<String> randomList = new ArrayList<>();

        for (int i = 0; i < listSize; i++) {
            int rand = ThreadLocalRandom.current().nextInt(1, 100 + 1);
            randomList.add(i, String.valueOf(rand));
        }
        return randomList;
    }
}