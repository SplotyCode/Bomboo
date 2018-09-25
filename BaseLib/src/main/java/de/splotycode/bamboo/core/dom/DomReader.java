package de.splotycode.bamboo.core.dom;

public interface DomReader<T extends DomParser> {

    void readNext(char c, T parser) throws RuntimeException;

    void parseDone();

}
