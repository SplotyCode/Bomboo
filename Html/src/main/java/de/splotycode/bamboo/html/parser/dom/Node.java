package de.splotycode.bamboo.html.parser.dom;

import de.splotycode.bamboo.html.parser.attriute.StandardAttribute;
import de.splotycode.bamboo.html.util.TagHelper;
import de.splotycode.bamboo.html.parser.attriute.Attribute;
import de.splotycode.bamboo.html.parser.attriute.ValueAttribute;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode
public class Node implements Cloneable {

    @Getter private int start, end;

    @Getter private final String name;

    private Node parent;
    protected List<Node> childs;

    private Set<Attribute> attributes;

    public Node(String name, int start, int end) {
        this.name = name;
        childs = new ArrayList<>();
        attributes = new HashSet<>();
        this.start = start;
        this.end = end;
    }

    public Node(String name, Node parent, int start, int end) {
        this.name = name;
        this.parent = parent;
        childs = new ArrayList<>();
        attributes = new HashSet<>();
        this.start = start;
        this.end = end;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public Node getParent() {
        return parent;
    }

    public int getAbsoluteEnd() {
        return end;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChilds() {
        return childs;
    }

    public void setChilds(List<Node> childs) {
        this.childs = childs;
    }

    public boolean hasAttribute(String name) {
        for(Attribute attribute : attributes)
            if(attribute.getName().equals(name))
                return true;
        return false;
    }

    public Attribute getAttribute(String name) {
        for(Attribute attribute : attributes)
            if(attribute.getName().equals(name))
                return attribute;
        return null;
    }

    public StandardAttribute getStandardAttribute(String name) {
        try {
            for(Attribute attribute : attributes)
                if(attribute.getName().equals(name))
                    return (StandardAttribute) attribute;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Not right value type for '" + name + "'!");
        }
        return null;
    }

    public Node getNodeBefore() {
        if (parent == null) return null;
        return parent.getChilds().get(parent.getChilds().indexOf(this)  - 1);
    }

    public List<Node> getParents() {
        List<Node> parents = new ArrayList<>();
        if(parent == null) return parents;

        Node current = parent;
        while (current != null) {
            parents.add(current);
            current = current.getParent();
        }
        return parents;
    }

    public String getRawAttribute(String name) {
        for(Attribute attribute : attributes)
            if (attribute instanceof ValueAttribute && attribute.getName().equals(name))
                return ((ValueAttribute) attribute).getStringValue();
        return null;
    }

    public ValueAttribute getValueAttribute(String name) {
        for(Attribute attribute : attributes)
            if (attribute instanceof ValueAttribute && attribute.getName().equals(name))
                return ((ValueAttribute) attribute);
        return null;
    }

    @Override
    public Node clone() {
        try {
            return (Node) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    public boolean canSelfClose(){
        return TagHelper.canSelfClose(name);
    }

}
