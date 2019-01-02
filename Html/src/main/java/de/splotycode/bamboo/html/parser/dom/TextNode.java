package de.splotycode.bamboo.html.parser.dom;

import de.splotycode.bamboo.html.parser.attriute.Attribute;
import de.splotycode.bamboo.html.parser.attriute.StandardAttribute;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TextNode extends Node {

    public TextNode(String name, int start, int end) {
        super(name, start, end);
    }

    public TextNode(String name, Node parent, int start, int end) {
        super(name, parent, start, end);
    }

    @Override
    public List<Node> getChilds() {
        return Collections.emptyList();
    }

    @Override
    public Attribute getAttribute(String name) {
        throw new InternalError("Method blocked for Text Nodes!");
    }

    @Override
    public Set<Attribute> getAttributes() {
        throw new InternalError("Method blocked for Text Nodes!");
    }

    @Override
    public boolean hasAttribute(String name) {
        throw new InternalError("Method blocked for Text Nodes!");
    }

    @Override
    public StandardAttribute getStandardAttribute(String name) {
        throw new InternalError("Method blocked for Text Nodes!");
    }

    @Override
    public String getRawAttribute(String name) {
        throw new InternalError("Method blocked for Text Nodes!");
    }

    @Override
    public void setChilds(List<Node> childs) {
        throw new InternalError("Method blocked for Text Nodes!");
    }

}
