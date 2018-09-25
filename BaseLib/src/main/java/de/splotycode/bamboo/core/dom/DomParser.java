package de.splotycode.bamboo.core.dom;

import de.splotycode.bamboo.core.editor.error.CodeWarning;
import de.splotycode.bamboo.core.editor.error.ErrorType;
import de.splotycode.bamboo.core.editor.error.QuickFix;

import java.util.List;

public interface DomParser<O, I, R extends DomParser> {

    O parse(I input);
    int getIndex();
    char getChar();
    char getChar(int next);
    void reHandle();

    DomReader<R>[] getReaders();
    List<DomReader<R>> getActivReaders();
    void disableReader(DomReader<R> reader);

    void skip(int i);
    default void skip() {skip(1);}
    boolean skipIfFollow(String next);
    boolean skipIfFollowIgnoreCase(String text);

    void stopCurrent();

    void setLocked(DomReader<R> reader);
    boolean isLocked();
    DomReader<R> getLocked();

    void addError(CodeWarning warning);

    default void addError(String message, int start, int end, ErrorType type, QuickFix... fixes) {
        addError(new CodeWarning(type, fixes, message, start, end));
    }

    default void addError(String message, int start, int end) {
        addError(message, start, end, ErrorType.WARNING_LOW);
    }

    default void addError(String message, int start, int end, QuickFix... fixes) {
        addError(message, start, end, ErrorType.WARNING_LOW, fixes);
    }

}
