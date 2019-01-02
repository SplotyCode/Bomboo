package de.splotycode.bamboo.html.util;

import de.splotycode.bamboo.core.editor.Editor;
import de.splotycode.bamboo.html.HtmlDescriptor;
import de.splotycode.bamboo.html.parser.dom.Node;
import de.splotycode.bamboo.html.parser.dom.TagNode;

import java.util.ArrayList;
import java.util.Collection;

public final class DomTreeUtil {

    public static Collection<Node> getAllNodes(Editor editor) {
        Collection<Node> collection = new ArrayList<>();
        getAll(editor.getDataFactory().getData(HtmlDescriptor.BASE_NODE), collection, true);
        return collection;
    }

    private static void getAll(Node node, Collection<Node> collection, boolean first) {
        for (Node child : node.getChilds()) {
            getAll(child, collection, false);
        }
        if (!first) collection.add(node);
    }

    public static Collection<TagNode> getAllTagNodes(Editor editor) {
        Collection<TagNode> collection = new ArrayList<>();
        getAllTags(editor.getDataFactory().getData(HtmlDescriptor.BASE_NODE), collection);
        return collection;
    }

    private static void getAllTags(Node node, Collection<TagNode> collection) {
        for (Node child : node.getChilds()) {
            getAllTags(child, collection);
        }
        if (node instanceof TagNode) {
            collection.add((TagNode) node);
        }
    }

    public static Collection<TagNode> getNodes(Editor editor, String name) {
        Collection<TagNode> collection = new ArrayList<>();
        getAllTags(name, editor.getDataFactory().getData(HtmlDescriptor.BASE_NODE), collection);
        return collection;
    }

    private static void getAllTags(String name, Node node, Collection<TagNode> collection) {
        for (Node child : node.getChilds()) {
            getAllTags(name, child, collection);
        }
        if (node instanceof TagNode && node.getName().equals(name)) {
            collection.add((TagNode) node);
        }
    }

}
