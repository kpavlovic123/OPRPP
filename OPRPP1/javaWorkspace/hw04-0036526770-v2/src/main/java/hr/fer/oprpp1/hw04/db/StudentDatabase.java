package hr.fer.oprpp1.hw04.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StudentDatabase {
    Map<String,StudentRecord> index;
    List<StudentRecord> list;
    public StudentDatabase(List<String> o){
        index = new HashMap<String,StudentRecord>();
        list = new LinkedList<StudentRecord>();
        for(var e : o){
            var att = e.split("\t",4);
            if(Integer.valueOf(att[3])>5 || Integer.valueOf(att[3])<1){
                throw new IllegalArgumentException("Final grade defined incorrectly.");
            }
            if(index.containsKey(att[0])){
                throw new IllegalArgumentException("Duplicate JMBAG not allowed.");
            }
            var sr = new StudentRecord(att[0], att[1], att[2], att[3]);
            if(sr.jmbag.length()>0){
                index.put(sr.jmbag, sr);
            }
            list.add(sr);
        }
    }
    public StudentRecord forJMBAG(String jmbag){
        return index.get(jmbag);
    };

    public List<StudentRecord> filter(IFilter filter){
        List<StudentRecord> l = new LinkedList<>();
        for(var e : list){
            if(filter.accepts(e))  
                l.add(e);
        }
        return l;
    };

}
