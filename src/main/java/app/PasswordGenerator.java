package main.java.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PasswordGenerator {
    private String viableCharacters;
    private int passwordLength = 20;
    private Map<String, String> charMap = new HashMap<>();
    private Random randomGenerator = new Random();

    private static final String CAPITAL_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!$%&?´`";
    private static final String BRACKETS = "()[]{}";

    public PasswordGenerator() {
        charMap.put("capitalCharacters", CAPITAL_CHARACTERS);
        charMap.put("lowerCharacters", LOWER_CHARACTERS);
        charMap.put("numbers", NUMBERS);
        charMap.put("specialCharacters", SPECIAL_CHARACTERS);
        charMap.put("brackets", BRACKETS);

        viableCharacters = String.join("", CAPITAL_CHARACTERS, LOWER_CHARACTERS,
                NUMBERS, SPECIAL_CHARACTERS, BRACKETS);
    }

    public void configureCharacters(Map<String, Boolean> map) {
        StringBuilder viableCharBuilder = new StringBuilder();

        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            String key = entry.getKey();
            if (charMap.containsKey(key) && entry.getValue()) {
                viableCharBuilder.append(charMap.get(key));
            }
        }

        viableCharacters = viableCharBuilder.toString();
    }

    public void configureLength(int length) {
        passwordLength = Math.max(1, length);
    }

    public String generatePassword() {
        if (viableCharacters.isEmpty()) {
            throw new IllegalStateException("Could not find any characters to generate a password with. This " +
                    "is most likely a result of all types of characters being deactivated.");
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int index = 0; index <= passwordLength; index++) {
            stringBuilder.append(viableCharacters.charAt(randomGenerator.nextInt(viableCharacters.length())));
        }

        return stringBuilder.toString();
    }
}
