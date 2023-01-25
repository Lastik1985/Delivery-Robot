package org.example;

import java.util.*;

import static java.util.Collections.max;


class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {


        Thread[] threads = new Thread[1000];

        for (int i = 0; i < threads.length; i++) {
            Runnable task = () -> {
                String route = generateRoute("RLRFR", 100);
                int key = Math.toIntExact(route.chars().filter(n -> n == 'R').count());
                addMap(key);
            };
            threads[i] = new Thread(task);
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("Самое частое количество повторений: "
                + Collections.max(sizeToFreq.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey() +
                " (встретилось " + max(sizeToFreq.values()) + " раз)");
        System.out.println("Другие размеры:");
        sizeToFreq.forEach((key, value) ->
                System.out.println(" - " + key + " " + value + " раз."));

    }

    public static synchronized void addMap(Integer key) {
        if (sizeToFreq.containsKey(key)) {
            sizeToFreq.put(key, sizeToFreq.get(key) + 1);
        } else {
            sizeToFreq.put(key, 1);
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}