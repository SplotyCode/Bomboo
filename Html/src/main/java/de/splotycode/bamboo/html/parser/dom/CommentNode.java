package de.splotycode.bamboo.html.parser.dom;

public class CommentNode extends Node {
    public CommentNode(String name, int start, int end) {
        super(name, start, end);
    }

    public CommentNode(String name, Node parent, int start, int end) {
        super(name, parent, start, end);
    }
}
