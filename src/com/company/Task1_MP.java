package com.company;

import java.util.*;

public class Task1_MP {

    /*
     *   MPrules - список правил МП преобразователя  в виде словаря где ключ это строка состоящая из
     * конкатенации состония магазина и текущего элемента строки
     * пустой строкой обозначен маркер конца цепочки, но для вывода на экран используется -|
     * аналогично обозначен маркер дна магазина, но при выводе он отображается как #
     *
     * -1 означает правило Вытолк и сдвиг
     * -2 допуск
     * */
    static final Map<String, Integer> MPrules;

    static Stack<String> magazine;

    static {
        magazine = new Stack<>();
        magazine.push("");
        magazine.push("S");
        MPrules = Map.ofEntries(
                Map.entry("S{", 0),
                Map.entry("Sa", 0),
                Map.entry("S(", 0),
                Map.entry("S!", 0),
                Map.entry("O{", 4),
                Map.entry("Oa", 3),
                Map.entry("O(", 1),
                Map.entry("O!", 2),
                Map.entry("Ya", 7),
                Map.entry("Y(", 5),
                Map.entry("Y!", 6),
                Map.entry("A;", 8),
                Map.entry("A]", 9),
                Map.entry("A", 9),
                Map.entry("B;", 11),
                Map.entry("B[", 10),
                Map.entry("B]", 11),
                Map.entry("B", 11),
                Map.entry("C[", 12),
                Map.entry("Ca", 15),
                Map.entry("C(", 13),
                Map.entry("C!", 14),
                Map.entry("D|", 16),
                Map.entry("D&", 17),
                Map.entry("E[", 18),
                Map.entry("E=", 19),
                Map.entry("", -2),
                Map.entry("((", -1),
                Map.entry(";;", -1),
                Map.entry("[[", -1),
                Map.entry("]]", -1),
                Map.entry("}}", -1),
                Map.entry("))", -1)
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

        StringBuilder newExpr = new StringBuilder(expr);
        System.out.println(expr);
        while (!magazine.isEmpty()) {
            System.out.print("Магазин: <");
            for (int i = magazine.size() - 1; i >= 0; --i) {
                System.out.print((magazine.get(i).equals("") ? "#" : magazine.get(i)) + (i == 0 ? "" : ", "));
            }
            System.out.println(">");
            String symb = newExpr.isEmpty() ? "" : newExpr.substring(0, 1);
            Integer ruleInd = MPrules.get(magazine.pop() + symb);
            if (ruleInd != null) {
                System.out.printf("Цепочка: %s-|\n", newExpr);
                if (ruleInd >= 0) {
                    String rule = Task1_req.grammar.get(ruleInd).getValue().equals("e") ? "" : Task1_req.grammar.get(ruleInd).getValue();
                    System.out.printf("Применяемое правило %d: %s→%s\n", ruleInd, Task1_req.grammar.get(ruleInd).getKey(), Task1_req.grammar.get(ruleInd).getValue());
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


