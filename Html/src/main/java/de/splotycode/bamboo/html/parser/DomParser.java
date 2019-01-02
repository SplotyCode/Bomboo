package de.splotycode.bamboo.html.parser;

import de.splotycode.bamboo.core.editor.Editor;

import java.util.List;

public interface DomParser<O, I, R extends DomParser> {

    O parse(I input, Editor editor);
    int getIndex();
    char getChar();
    char getChar(int next);
    void rehandle();

    Editor getEditor();

    DomReader<R>[] getReaders();
    List<DomReader<R>> getActivReaders();
    void disableReader(DomReader<R> reader);

    void skip(int i);
    void skip();
    boolean skipIfFollow(String next);
    boolean skipIfFollowIgnoreCase(String text);

    void stopCurrent();

    void setLocked(DomReader<R> reader);
    boolean isLocked();
    DomReader<R> getLocked();

}
