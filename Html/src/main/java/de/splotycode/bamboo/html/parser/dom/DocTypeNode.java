package de.splotycode.bamboo.html.parser.dom;

import lombok.Getter;

public class DocTypeNode extends Node {

    @Getter private final String doctype;

    public DocTypeNode(String doctype, int start, int end) {
        super("Doctype", start, end);
        this.doctype = doctype;
    }

    public DocTypeNode(String doctype, Node parent, int start, int end) {
        super("Doctype", parent, start, end);
        this.doctype = doctype;
    }



}
