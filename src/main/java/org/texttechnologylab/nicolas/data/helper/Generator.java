package org.texttechnologylab.nicolas.data.helper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Generator {

    private static final Random random = new Random();
    private static final Set<String> inUseIDs = new HashSet<>(0);

    /**
     * Generate a unique numeric id and add it to the occupied set
     * @return an id unique to the occupied ids
     */
    public static String generateID() {

        String uuid;
        int len = 15;
        boolean nonUnique;

        do {
            // Generate the number for the ID
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer)random.nextInt(10)).toString();
            }

            // Makes sure is a unique ID
            nonUnique = false;
            for (String s: inUseIDs) {
                if (uuid.equals(s)){
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        inUseIDs.add(uuid);
        return uuid;
    }
}
