package com.company;

import java.util.ArrayList;
import java.util.Map;

public class Task1_req {
    static ArrayList<Map.Entry<String, String>> grammar;

    static boolean reqCheck(String from, ArrayList<Integer> rules, String to) {
        if (from.equals(to)) {
            for (Integer rule : rules)
                System.out.printf("%d. %s → %s\n", rule+1, grammar.get(rule).getKey(), grammar.get(rule).getValue());
            return true;
        } else if (!isCor(from, to)) {
            return false;
        }

        int nonTerm = getNonterm(from);
        for (int i = 0; i < grammar.size(); ++i) {
            if (grammar.get(i).getKey().charAt(0) == from.charAt(nonTerm)) {
                ArrayList<Integer> newArr = new ArrayList<Integer>(rules);

                newArr.add(i);
                String newstr = from.substring(0, nonTerm) + (grammar.get(i).getValue().equals("e") ? "" : grammar.get(i).getValue()) + from.substring(nonTerm + 1);
                if (reqCheck(newstr, newArr, to))
                    return true;
            }
        }
        return false;
    }

    static int getNonterm(String str) {
        for (int i = 0; i < str.length(); ++i)
            if (Character.isUpperCase(str.charAt(i)))
                return i;
        return -1;
    }

    static boolean isCor(String str, String corStr) {
        int i;
        for (i = 0; i < corStr.length() && i < str.length(); ++i) {
            if (Character.isUpperCase(str.charAt(i)))
                return true;
            else if (str.charAt(i) != corStr.charAt(i))
                return false;
        }
        if (corStr.length() == i)
            return Character.isUpperCase(str.charAt(i));
        else
            return true;
    }

    static {
        grammar = new ArrayList<Map.Entry<String, String>>();
        grammar.add(Map.entry("S", "O;C"));
        grammar.add(Map.entry("O", "aD"));
        grammar.add(Map.entry("E", "TA"));
        grammar.add(Map.entry("T", "PB"));
        grammar.add(Map.entry("P", "(E)"));
        grammar.add(Map.entry("P", "-(E)"));
        grammar.add(Map.entry("P", "a"));
        grammar.add(Map.entry("A", "+TA"));
        grammar.add(Map.entry("A", "e"));
        grammar.add(Map.entry("B", "*PB"));
        grammar.add(Map.entry("B", "e"));
        grammar.add(Map.entry("C", "S"));
        grammar.add(Map.entry("C", "e"));
        grammar.add(Map.entry("D", "[S]F"));
        grammar.add(Map.entry("D", "=E"));
        grammar.add(Map.entry("F", "[S]"));
        grammar.add(Map.entry("F", "e"));

    }

    static void solve(String expr) {
        ArrayList<Integer> ruls = new ArrayList<Integer>();
        System.out.println(expr);
        if (reqCheck("S", ruls, expr))
            System.out.println("Допущено");
        else
            System.out.println("Не допущено");
    }

}
