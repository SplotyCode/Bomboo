package de.splotycode.bamboo.core.dom;

import de.splotycode.bamboo.core.editor.Editor;
import de.splotycode.bamboo.core.editor.error.CodeWarning;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DefaultStringDomParser<O, R extends DomParser> implements DomParser<O, String, R> {

    @Getter
    private String content;
    private DomReader<R> locked;

    @Getter private Editor editor;

    public DefaultStringDomParser(String content, Editor editor) {
        this.content = content;
    }

    @Getter
    @Setter
    private int index = 0, line = 1;

    @Getter
    @Setter
    private boolean skipThis = false, reHandle = false;

    @Getter
    @Setter
    private List<DomReader<R>> disabledReaders = new ArrayList<>();

    @Override
    public char getChar() {
        return content.charAt(index);
    }

    @Override
    public char getChar(int next) {
        return content.charAt(index+next);
    }

    @Override
    public void reHandle() {
        reHandle = true;
    }

    @Override
    public List<DomReader<R>> getActivReaders() {
        List<DomReader<R>> readers = new ArrayList<>(Arrays.asList(getReaders()));
        readers.removeAll(disabledReaders);
        return readers;
    }

    @Override
    public void disableReader(DomReader<R> reader) {
        disabledReaders.add(reader);
    }

    @Override
    public void skip(int i) {
        line += content.substring(index, index + i).split("\r\n|\r|\n").length;
        index += i;
    }

    @Override
    public boolean skipIfFollow(String next) {
        if(index+next.length() > content.length()) return false;
        for(int i = 0;i < next.length();i++)
            if (content.charAt(i + index) != next.charAt(i)) return false;
        line += content.substring(index, index + next.length() - 1).split("\r\n|\r|\n").length;
        index += next.length()-1;
        return true;
    }

    @Override
    public boolean skipIfFollowIgnoreCase(String text) {
        if(index+text.length() > content.length()) return false;
        for(int i = 0;i < text.length();i++)
            if (Character.toLowerCase(content.charAt(i + index)) != text.charAt(i)) return false;
        line += content.substring(index, index + text.length() - 1).split("\r\n|\r|\n").length;
        index += text.length()-1;
        return true;
    }

    @Override
    public void stopCurrent() {
        skipThis = true;
    }

    @Override public void setLocked(DomReader<R> reader) {
        locked = reader;
    }

    @Override
    public boolean isLocked() {
        return locked != null;
    }

    @Override
    public DomReader<R> getLocked() {
        return locked;
    }

    @Override
    public void addError(CodeWarning warning) {
        editor.addWarning(warning);
    }

}
