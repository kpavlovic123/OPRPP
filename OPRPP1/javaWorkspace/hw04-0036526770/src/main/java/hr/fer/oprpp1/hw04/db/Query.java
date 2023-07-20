package hr.fer.oprpp1.hw04.db;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private String addPluses(String s, int j, int l, int f, int g) {
        s += "+";
        for (int i = 0; i < j+2; i++) {
            s += "=";
        }
        s += "+";
        for (int i = 0; i < l+2; i++) {
            s += "=";
        }
        ;
        s += "+";
        for (int i = 0; i < f+2; i++) {
            s += "=";
        }
        s += "+";
        for (int i = 0; i < g+2; i++) {
            s += "=";
        }
        s += "+\n";
        return s;
    }

    private void formatAndPrint(List<StudentRecord> r) {
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
        String t = "";
        t = addPluses(t, j, l, f, g);
        for(var s : r){
            t += String.format("| %-"+Integer.toString(j)+"s | %-"+Integer.toString(l)+"s | %-"+
            Integer.toString(f)+"s | %-"+Integer.toString(g)+"s |\n",s.jmbag,s.lastName,s.firstName,s.finalGrade);
        }
        t = addPluses(t, j, l, f, g);
        System.out.print(t);
    };

    private void formatAndPrint(StudentRecord r){
        int j = r.jmbag.length();
        int l = r.lastName.length();
        int f = r.firstName.length();
        int g = r.finalGrade.length();
        String t = "";
        t = addPluses(t,j,l,f,g);
        t += String.format("| %-"+Integer.toString(j)+"s | %-"+Integer.toString(l)+"s | %-"+
            Integer.toString(f)+"s | %-"+Integer.toString(g)+"s |\n",r.jmbag,r.lastName,r.firstName,r.finalGrade);
        t = addPluses(t,j,l,f,g);
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
            QueryParser qp = new QueryParser(l);
            if (!qp.check()) {
                System.out.println("Command line holds syntax error.");
                continue;
            }

            if (qp.isDirectQuery()) {
                q.formatAndPrint(q.db.forJMBAG(qp.getQueriedJMBAG())); 

            } else {
                q.formatAndPrint(q.db.filter(new QueryFilter(qp.getQuery())));
            }
        }
    }
}
