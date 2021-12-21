package com.company;

import java.util.*;

public class Task1_MP {

    /*
     *   MPrules - список правил МП преобразователя  в виде словаря где ключ это строка состоящая из
     * конкатенации состония магазина и текущего элемента строки
     *
     * -1 означает правило Вытолк и сдвиг
     * -2 допуск
     * */
    static final Map<String, Integer> MPrules;

    static Stack<String> magazine;

    static {
        magazine = new Stack<>();
        magazine.push("∇");
        magazine.push("S");
        MPrules = Map.ofEntries(
                Map.entry("Sa", 0),
                Map.entry("Oa", 1),
                Map.entry("Ea", 2),
                Map.entry("E(", 2),
                Map.entry("E-", 2),
                Map.entry("Ta", 3),
                Map.entry("T(", 3),
                Map.entry("T-", 3),
                Map.entry("Pa", 6),
                Map.entry("P(", 4),
                Map.entry("P-", 5),
                Map.entry("A;", 8),
                Map.entry("A)", 8),
                Map.entry("A+", 7),
                Map.entry("B;", 10),
                Map.entry("B)", 10),
                Map.entry("B+", 10),
                Map.entry("B*", 9),
                Map.entry("C]", 12),
                Map.entry("Ca", 11),
                Map.entry("C┤", 12),
                Map.entry("D[", 13),
                Map.entry("D=", 14),
                Map.entry("F;", 16),
                Map.entry("F[", 15),
                Map.entry(";;", -1),
                Map.entry("((", -1),
                Map.entry("))", -1),
                Map.entry("]]", -1),
                Map.entry("∇┤", -2)
        );
    }


    public static void pushMag(String rule) {
        if (rule.isEmpty())
            return;
        for (int i = rule.length() - 1; i >= 0; --i) {
            magazine.push(rule.substring(i, i + 1));
        }
    }


    public static void solve(String expr) {

        StringBuilder newExpr = new StringBuilder(expr + "┤");
        System.out.println(expr);
        while (!magazine.isEmpty()) {
            System.out.print("Магазин: <");
            for (int i = magazine.size() - 1; i >= 0; --i) {
                System.out.print((magazine.get(i)) + (i == 0 ? "" : ", "));
            }
            System.out.println(">");
            String symb = newExpr.isEmpty() ? "" : newExpr.substring(0, 1);
            Integer ruleInd = MPrules.get(magazine.pop() + symb);
            if (ruleInd != null) {
                System.out.printf("Цепочка: %s\n", newExpr);
                if (ruleInd >= 0) {
                    String rule = Task1_req.grammar.get(ruleInd).getValue().equals("e") ? "" : Task1_req.grammar.get(ruleInd).getValue();
                    System.out.printf("Применяемое правило %d: %s→%s\n", ruleInd+1, Task1_req.grammar.get(ruleInd).getKey(), Task1_req.grammar.get(ruleInd).getValue());
                    if (rule.startsWith(symb)) {
                        newExpr.delete(0, 1);
                        pushMag(rule.length() == 0 ? rule : rule.substring(1));
                    } else {
                        pushMag(rule);
                    }
                } else {
                    if (ruleInd == -1) {
                        newExpr.delete(0, 1);
                        System.out.print("Применяемое правило: Вытолкнуть и сдвиг\n");
                    } else {
                        System.out.println("Допущено");
                    }
                }
                System.out.println();
            } else {
                System.out.println("Не допущено");
                break;
            }


        }


    }
}


