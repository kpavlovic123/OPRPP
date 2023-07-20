package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

    List<SingleDocumentModel> documents;
    SingleDocumentModel currentDocument;
    List<MultipleDocumentListener> listeners;

    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return documents.iterator();
    }

    @Override
    public JComponent getVisualComponent() {
        return this;
    }

    @Override
    public SingleDocumentModel createNewDocument() {
        return new DefaultSingleDocumentModel(Path.of("unnamed"), "");
    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return currentDocument;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) {
        String content = "";
        Charset c = StandardCharsets.UTF_8;
        try {
            InputStream in = Files.newInputStream(path);
            byte[] bytes = in.readAllBytes();
            content = new String(bytes,c);
            in.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return new DefaultSingleDocumentModel(path, content);
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {
        if(newPath == null){
            newPath = model.getFilePath();
        }
        String content = model.getTextComponent().getText();
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        try {
            OutputStream out = Files.newOutputStream(newPath);
            out.write(bytes);
            out.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
       }

    @Override
    public void closeDocument(SingleDocumentModel model) {
        documents.remove(model);
    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.remove(l);

    }

    @Override
    public int getNumberOfDocuments() {
        return documents.size();
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        return documents.get(index);
    }

    @Override
    public SingleDocumentModel findForPath(Path path) {
        if(path == null){
            throw new IllegalArgumentException();
        }
        for (var d : documents){
            if(d.getFilePath()==path)
                return d;
        }
        return null;
    }

    @Override
    public int getIndexOfDocument(SingleDocumentModel doc) {
        return documents.indexOf(doc);
    }

    public static void main(String[] args) {
        var test = new DefaultMultipleDocumentModel();
        var s = test.loadDocument(Path.of("./test.txt"));
        var d = new DefaultSingleDocumentModel(Path.of("./text.txt"), "Ovo je test222");
        test.saveDocument(d, Path.of("./test2.txt"));
    }

}
