package de.splotycode.bamboo.html.parser;

public interface DomReader<T extends DomParser> {

    void readNext(char c, T parser) throws RuntimeException;

    void parseDone(T parser);

}
