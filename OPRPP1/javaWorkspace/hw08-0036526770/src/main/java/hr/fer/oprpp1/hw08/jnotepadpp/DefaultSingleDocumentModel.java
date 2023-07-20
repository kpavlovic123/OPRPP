package hr.fer.oprpp1.hw08.jnotepadpp;


import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

    Path file;
    List<SingleDocumentListener> listeners;
    boolean updated = false;
    private JTextArea textArea;

    public DefaultSingleDocumentModel(Path file,String content){
        this.file = file;
        textArea = new JTextArea();
        textArea.setText(content);
        this.listeners = new ArrayList<>();
        textArea.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                setModified(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setModified(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
            
        }
        );
    }

    @Override
    public JTextArea getTextComponent() {
        return textArea;
    }

    @Override
    public Path getFilePath() {
        return file;
    }

    @Override
    public void setFilePath(Path path) {
        if(path == null)
            throw new IllegalArgumentException();
        this.file = path;
        for(var l : listeners){
            l.documentFilePathUpdated(this);
        }
    }

    @Override
    public boolean isModified() {
        return updated;
    }

    @Override
    public void setModified(boolean modified) {
        updated = modified;
        if(modified == true){
            for (var l : listeners){
                l.documentModifyStatusUpdated(this);
            }
        }
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        listeners.remove(l);
    }
    
}
