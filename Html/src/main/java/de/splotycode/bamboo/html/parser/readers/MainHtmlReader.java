package de.splotycode.bamboo.html.parser.readers;

import de.splotycode.bamboo.core.editor.error.CodeWarning;
import de.splotycode.bamboo.core.editor.error.ErrorType;
import de.splotycode.bamboo.html.parser.DomHtmlParser;
import de.splotycode.bamboo.html.parser.DomReader;
import de.splotycode.bamboo.html.parser.attriute.Attribute;
import de.splotycode.bamboo.html.parser.attriute.ValueAttribute;
import de.splotycode.bamboo.html.util.AttributeHelper;
import de.splotycode.bamboo.html.parser.attriute.StandardAttribute;
import de.splotycode.bamboo.html.parser.attriute.ToggleAttribute;
import de.splotycode.bamboo.html.parser.dom.TagNode;
import de.splotycode.bamboo.html.parser.dom.TextNode;
import de.splotycode.bamboo.html.util.StringUtil;

public class MainHtmlReader implements DomReader<DomHtmlParser> {

    private State state = State.TEXT;

    private String name = "", value = "", text = "";
    private char endChar;

    private TagNode atributeNode = null;

    private int tagStart, textStart, nameStart, attributeNameStart;

    @Override
    public void readNext(char c, DomHtmlParser parser) {
        switch (state){
            case TEXT:
                if(c == '<') {
                    if(!StringUtil.isEmpty(text)) parser.getCurrentParent().getChilds().add(new TextNode(text, parser.getCurrentParent(), textStart, parser.getIndex() - 1));
                    state = State.TAGNAME;
                    tagStart = parser.getIndex();
                    text = "";
                } else if(StringUtil.isNoSpecialSpace(c)) {
                    if (text.isEmpty()) {
                        textStart = parser.getIndex();
                    }
                    text += c;
                }
                break;
            case TAGNAME:
                if(c == ' '){
                    atributeNode = new TagNode(name.toLowerCase(), tagStart, parser.getIndex());
                    atributeNode.setParent(parser.getCurrentParent());
                    name = "";
                    state = State.AFTER_TAGNAME;
                    parser.getCurrentParent().getChilds().add(atributeNode);
                    if(!atributeNode.canSelfClose())
                        parser.setCurrentParent(atributeNode);
                }else if(c == '>') {
                    TagNode node = new TagNode(name.toLowerCase(), tagStart, parser.getIndex());
                    node.setParent(parser.getCurrentParent());
                    parser.getCurrentParent().getChilds().add(node);
                    if(!node.canSelfClose())
                        parser.setCurrentParent(node);
                    name = "";
                    state = State.TEXT;
                } else if (c == '/'){
                    state = State.CLOSESTART;
                } else {
                    if (name.isEmpty()) nameStart = parser.getIndex();
                    name += c;
                }
                break;
            case AFTER_TAGNAME:
                if (c == '>') {
                    state = State.TEXT;
                    atributeNode.setLastEnd(parser.getIndex());
                    if(!atributeNode.canSelfClose())
                        parser.setCurrentParent(atributeNode);
                    atributeNode = null;
                } else if (c == '/'){
                    state = State.AUTOCLOSE;
                } else if (StringUtil.isNoWhiteSpace(c)) {
                    state = State.ATRIBUTE_NAME;
                    parser.rehandle();
                }
                break;
            case ATRIBUTE_NAME:
                if (StringUtil.isWhiteSpace(c)) {
                    state = State.AFTER_ATRIBUTE_NAME;
                } else if (c == '=') {
                    state = State.AFTERQUALS;
                }else if (c == '>'){
                    Attribute attribute = new Attribute(name.toLowerCase());
                    attribute.setNameBounds(parser.getIndex() - name.length(), parser.getIndex());
                    atributeNode.getAttributes().add(attribute);
                    state = State.TEXT;
                    atributeNode.setLastEnd(parser.getIndex());
                    if(!atributeNode.canSelfClose())
                        parser.setCurrentParent(atributeNode);
                    atributeNode = null;
                    name = "";
                } else if (c == '/'){
                    state = State.AUTOCLOSE;
                } else {
                    if (name.isEmpty()) attributeNameStart = parser.getIndex();
                    name += c;
                }
                break;
            case AFTER_ATRIBUTE_NAME:
                if (c == '>'){
                    Attribute attribute = new Attribute(name.toLowerCase());
                    attribute.setNameBounds(parser.getIndex() - name.length(), parser.getIndex());
                    atributeNode.getAttributes().add(attribute);

                    name = "";
                } else if (c == '/'){
                    state = State.AUTOCLOSE;
                } else if (c == '='){
                    state = State.AFTERQUALS;
                } else if (StringUtil.isNoWhiteSpace(c)) {
                    Attribute attribute = new Attribute(name.toLowerCase());
                    atributeNode.getAttributes().add(attribute);
                    attribute.setNameBounds(parser.getIndex() - name.length(), parser.getIndex());
                    name = "";
                    state = State.AFTER_TAGNAME;
                    parser.rehandle();
                }
                break;
            case AFTERQUALS:
                if (c == '"' || c == '\''){
                    endChar = c;
                    state = State.VALUE;
                } else if (StringUtil.isNoWhiteSpace(c)) {
                    state = State.VALUE;
                    endChar = Character.MIN_VALUE;
                }
                break;
            case VALUE:
                //System.out.println(endChar + " " + c);
                if (c == endChar || (endChar == Character.MIN_VALUE &&
                        (StringUtil.isWhiteSpace(c) || c == '>' || c == '/'))) {
                    name = name.toLowerCase();
                    ValueAttribute attribute;
                    if (AttributeHelper.isBoolean(name)) {
                        attribute = new ToggleAttribute(name, value);
                    } else {
                        attribute = new StandardAttribute(name, value);
                    }
                    attribute.setNameBounds(attributeNameStart, attributeNameStart + name.length() + 1);
                    attribute.setValueBounds(parser.getIndex() - value.length() - 1, parser.getIndex());
                    atributeNode.getAttributes().add(attribute);
                    name = value = "";
                    state = c == '/'? State.AUTOCLOSE: State.AFTER_TAGNAME;
                    if (AttributeHelper.isSelftClosing(name)) throw new RuntimeException("Attribute '" + name + "' for tag '" + parser.getCurrentParent().getName() + "' should not have a value!");
                } else value += c;
                break;
            case AUTOCLOSE:
                if (c == '>') {
                    /* Go one element back */
                    if(atributeNode != null && !atributeNode.canSelfClose()) {
                        parser.setCurrentParent(parser.getCurrentParent().getParent());
                    }
                    atributeNode = null;
                    state = State.TEXT;
                } else if (StringUtil.isNoWhiteSpace(c)){
                    throw new RuntimeException("Expected > but not '" + c + "'!(0)");
                }
                break;
            case CLOSESTART:
                if (StringUtil.isNoWhiteSpace(c)) {
                    parser.rehandle();
                    state = State.CLOSENAME;
                    name = "";
                }
                break;
            case CLOSENAME:
                if (StringUtil.isWhiteSpace(c) || c ==  '>') {
                    parser.rehandle();
                    state = State.CLOSEFINISHED;
                } else {
                    if (name.isEmpty()) nameStart = parser.getIndex();
                    name += c;
                }
                break;
            case CLOSEFINISHED:
                if (c == '>') {
                    TagNode node = (TagNode) parser.getCurrentParent();
                    node.setTagEndBounds(nameStart - 2, parser.getIndex() + 1);
                    if(!name.toLowerCase().equals(node.getName())) throw new RuntimeException("Closed '" + name + "' without closing '" + parser.getCurrentParent().getName() + "'!");
                    if(!node.canSelfClose()) parser.setCurrentParent(node.getParent());
                    name = "";
                    state = State.TEXT;
                } else if (StringUtil.isNoWhiteSpace(c)) throw new RuntimeException("Expected > but not '" + c + "'!(1)");
                break;
        }
    }

    @Override
    public void parseDone(DomHtmlParser parser) {
        if(!StringUtil.isEmpty(text)) {
            parser.getCurrentParent().getChilds().add(new TextNode(text, parser.getCurrentParent(), textStart, parser.getIndex()));
        }
        if (!StringUtil.isEmpty(name) ||
           !StringUtil.isEmpty(value) ||
           atributeNode != null) {
            parser.getEditor().addWarning(new CodeWarning("Unexpected EOF", ErrorType.ERROR, parser.getIndex() - 1, parser.getIndex()));
            //throw new RuntimeException("MainHtmlReader is not cleaned up... But parse id done! Text: '" + text + "' name: '" + name + "' value: '" + value + "' attrNode: '" + atributeNode + "'");
        }
    }

    private enum State {

        TAGNAME,
        AFTER_TAGNAME,
        ATRIBUTE_NAME,
        AFTER_ATRIBUTE_NAME,
        AFTERQUALS,
        VALUE,
        TEXT,
        AUTOCLOSE,
        CLOSESTART,
        CLOSENAME,
        CLOSEFINISHED

    }
}
