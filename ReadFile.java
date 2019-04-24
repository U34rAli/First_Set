import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.HashMap;

public class ReadFile {
    public static void main(String[] args) {
        BufferedReader reader;
        LinkedList<LinkedList<String>> li = new LinkedList<LinkedList<String>>();
        // JSONObject obj = new JSONObject();

        try {
            reader = new BufferedReader(new FileReader("grammer.txt"));

            String line = reader.readLine();
            while (line != null) {
                LinkedList<String> productionList = new LinkedList<String>();

                String[] prodution = line.split("\\-\\>|\\|");

                for (int i = 0; i < prodution.length; i++) {
                    productionList.add(prodution[i]);
                }

                li.add(productionList);
                line = reader.readLine();
            }

            System.out.println(li);

            getFirstSet(li);
        } catch (

        IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean isUpperCase(String st) {
        for (int i = 0; i < st.length(); i++) {
            char var = st.charAt(i);
            if (!(var >= 'A' && var <= 'Z')) {
                return false;
            }
        }
        return true;
    }

    private static int getNonTerminalIndex(String t, LinkedList<LinkedList<String>> l) {
        int index = -1;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).get(0).equals(t)) {
                return i;
            }

        }
        return index;
    }

    private static void getFirstSet(LinkedList<LinkedList<String>> l) {
        HashMap<String, FirstSet> hmap = new HashMap<String, FirstSet>();
        LinkedList<String> stack = new LinkedList<>();

        for (LinkedList<String> i : l) {
            hmap.put(i.get(0), new FirstSet());
        }

        for (int i = 0; i < l.size(); i++) {
            boolean completeFSOfSinglePro = true;
            String fSWithoutFSOfOther = "";
            String nonTerminal = l.get(i).get(0);
            System.out.print("\nFirst(" + nonTerminal + ") = ");
            for (int j = 1; j < l.get(i).size(); j++) {
                String prod = l.get(i).get(j);
                String fElement = prod.charAt(0) + "";

                if (fElement.equals(nonTerminal)) {

                } else if (isUpperCase(fElement)) {
                    System.out.print("first(" + fElement + "), ");
                    completeFSOfSinglePro = false;
                    stack.push(fElement);
                    hmap.get(nonTerminal).firstSet += fElement + ",";
                } else {
                    System.out.print(fElement + ", ");
                    fSWithoutFSOfOther += fElement + ",";
                    hmap.get(nonTerminal).firstSet += fElement + ",";
                }
            }
            if (completeFSOfSinglePro) {
                hmap.get(nonTerminal).firstSetCalculated = true;
                hmap.get(nonTerminal).firstSet = fSWithoutFSOfOther;
            }
        }

        System.out.println("\n" + hmap);

        for (int i = l.size() - 1; i > -1; i--) {
            String pop = l.get(i).get(0);
            if (hmap.get(pop).firstSetCalculated == false) {

                boolean isFSComputed = true;
                String data = hmap.get(pop).firstSet;
                // System.out.println("sit " + pop+ " data = "+data);
                String[] dataA = data.split(",");
                for (int j = 0; j < dataA.length; j++) {
                    String va = dataA[j];
                    if (isUpperCase(va)) {
                        if (hmap.get(va).firstSetCalculated == true) {
                            String hp = data.replace(va + ",", hmap.get(va).firstSet);
                            data = hp;
                            hmap.get(pop).firstSet = hp;
                            // System.out.println("data = " + data +" sit va =" + va +" hp = "+hp);
                        } else {
                            isFSComputed = false;
                        }
                    } else {

                    }
                }
                if (isFSComputed) {
                    hmap.get(pop).firstSetCalculated = true;
                }
            }
            // "".contains("s");
        }

        System.out.println("\n" + hmap);
    }

    // private String recursiveFirstSet() {

    // }

}