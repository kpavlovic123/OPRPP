package hr.fer.oprpp1.hw04.db;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Query {
    StudentDatabase db;
    Scanner s;

    private void initializeDb() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(
                    Path.of("./database.txt"),
                    StandardCharsets.UTF_8);

        } catch (

        Exception e) {
            System.err.println("ne radi");
        }
        db = new StudentDatabase(lines);
    };

    private String addPluses(String s, int[] inc) {
        for(int i = 0;i<inc.length;i++){
            s += "+";
            for (int j = 0; j < inc[i] + 2; j++) {
                s += "=";
            }
        }
        s += "+\n";
        return s;
    }

    private List<String> parseColumns(String s) {
        List<String> l = new ArrayList<>();
        s = s.replaceAll(",", "");
        String[] columns = s.split(" ");
        for (int i = 0; i < columns.length; i++) {
            l.add(columns[i]);
        }
        return l;
    }

    private void formatAndPrint(List<StudentRecord> r,List<String> columns) {
        int j = 0;
        int l = 0;
        int f = 0;
        int g = 0;
        for (var s : r) {
            if (s.jmbag.length() > j)
                j = s.jmbag.length();
            if (s.lastName.length() > l)
                l = s.lastName.length();
            if (s.firstName.length() > f)
                f = s.firstName.length();
            if (s.finalGrade.length() > g)
                g = s.finalGrade.length();
        }
        int[] inc = new int[columns.size()];
        for(int i = 0;i<columns.size();i++){
            String column = columns.get(i);
            if(column.equals("jmbag"))
                inc[i] = j;
            else if(column.equals("lastname")){
                inc[i] = l;
            }
            else if(column.equals("firstname")){
                inc[i] = f;
            }
            else if(column.equals("finalgrade")){
                inc[i] = g;
            }
        }
        String t = "";
        t = addPluses(t, inc);
        for (var s : r) {
            for(int i = 0;i<columns.size();i++){
                String column = columns.get(i);
                String v = "";
                if(column.equals("jmbag"))
                    v = s.jmbag;
                else if(column.equals("lastname"))
                    v = s.lastName;
                else if(column.equals("firstname"))
                    v = s.firstName;
                else if(column.equals("finalgrade"))
                    v = s.finalGrade;
                t+=String.format("| %-"+Integer.toString(inc[i])+"s ",v);
            }
            t+= "|\n";
            /* t += String.format("| %-" + Integer.toString(j) + "s | %-" + Integer.toString(l) + "s | %-" +
                    Integer.toString(f) + "s | %-" + Integer.toString(g) + "s |\n", s.jmbag, s.lastName, s.firstName,
                    s.finalGrade); */
        }
        t = addPluses(t,inc);
        System.out.print(t);
    };

    private void formatAndPrint(StudentRecord r,List<String> columns) {
        int j = r.jmbag.length();
        int l = r.lastName.length();
        int f = r.firstName.length();
        int g = r.finalGrade.length();
        String t = "";
        int[] inc = new int[columns.size()];
        for(int i = 0;i<columns.size();i++){
            String column = columns.get(i);
            if(column.equals("jmbag"))
                inc[i] = j;
            else if(column.equals("lastname")){
                inc[i] = l;
            }
            else if(column.equals("firstname")){
                inc[i] = f;
            }
            else if(column.equals("finalgrade")){
                inc[i] = g;
            }
        }
        t = addPluses(t, inc);
        for(int i = 0;i<columns.size();i++){
            String column = columns.get(i);
            String v = "";
            if(column.equals("jmbag"))
                v = r.jmbag;
            else if(column.equals("lastname"))
                v = r.lastName;
            else if(column.equals("firstname"))
                v = r.firstName;
            else if(column.equals("finalgrade"))
                v = r.finalGrade;
            t+=String.format("| %-"+Integer.toString(inc[i])+"s ",v);
        }
        t+= "|\n";
        t = addPluses(t,inc);
        System.out.print(t);
    }

    public static void main(String[] args) {
        Query q = new Query();
        q.initializeDb();
        q.s = new Scanner(System.in);
        String l;
        System.out.println("Query command line started.");
        while (true) {
            System.out.print(">");
            l = q.s.nextLine();
            l = l.trim();
            if (l.equals("exit"))
                break;
            if (!l.startsWith("query")) {
                System.out.println("Command unknown.");
                continue;
            }
            l = l.substring("query".length());

            List<String> columns;

            if (l.indexOf("showing") != -1) {
                String show = l.substring(l.indexOf("showing")).toLowerCase();
                l = l.substring(0, l.indexOf("showing"));
                show = show.substring("showing".length()).trim();
                columns = q.parseColumns(show);
            } else {
                columns = new ArrayList<>();
                columns.add("jmbag");
                columns.add("lastname");
                columns.add("firstname");
                columns.add("finalgrade");
            }
            QueryParser qp = new QueryParser(l.trim());
            if (!qp.check()) {
                System.out.println("Command line holds syntax error.");
                continue;
            }

            if (qp.isDirectQuery()) {
                q.formatAndPrint(q.db.forJMBAG(qp.getQueriedJMBAG()),columns);

            } else {
                q.formatAndPrint(q.db.filter(new QueryFilter(qp.getQuery())),columns);
            }
        }
    }
}
