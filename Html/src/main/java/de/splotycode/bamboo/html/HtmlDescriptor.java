package de.splotycode.bamboo.html;

import de.splotycode.bamboo.core.data.DataKey;
import de.splotycode.bamboo.core.editor.AbstractTextDescriptor;
import de.splotycode.bamboo.core.editor.Editor;
import de.splotycode.bamboo.core.editor.error.Inspection;
import de.splotycode.bamboo.core.project.Language;
import de.splotycode.bamboo.core.project.LanguageDescriptor;
import de.splotycode.bamboo.core.project.Project;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.yaml.Configuration;
import de.splotycode.bamboo.html.inspections.EmptyTagInspection;
import de.splotycode.bamboo.html.inspections.InappropriateMetaCharsetInspection;
import de.splotycode.bamboo.html.parser.DomHtmlParser;
import de.splotycode.bamboo.html.parser.attriute.Attribute;
import de.splotycode.bamboo.html.parser.attriute.ValueAttribute;
import de.splotycode.bamboo.html.parser.dom.*;
import lombok.Getter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class HtmlDescriptor extends AbstractTextDescriptor {

    public static final DataKey<Node> BASE_NODE = new DataKey<>("html.base");
    private static final Inspection[] INSPECTIONS = new Inspection[] {
            new EmptyTagInspection(),
            new InappropriateMetaCharsetInspection()
    };

    @Override
    public void load(Project project, Configuration configuration, WorkSpace workSpace) {

    }

    @Override
    public void prepairEditor(Editor editor, WorkSpace workSpace) {

    }

    @Override
    public void onTextChange(Editor editor) {
        editor.clearHilights();
        editor.clearWarning();
        DomHtmlParser parser = new DomHtmlParser();
        parser.parse(editor.getText(), editor);
        Node base = parser.getBase();
        editor.getDataFactory().forePutData(BASE_NODE, base);
        paintNode(base, editor);
    }

    private void paintNode(Node node, Editor editor) {
        for (Node child : node.getChilds()) {
            paintNode(child, editor);
        }

        if (node instanceof TagNode) {
            TagNode tag = (TagNode) node;
            if (tag.getLastEnd() != -1) {
                editor.addHightlight(tag.getLastEnd(), tag.getLastEnd(), Color.decode("#15B5CC"));
            }
            if (tag.getEndTagStart() != -1) {
                editor.addHightlight(tag.getEndTagStart(), tag.getEndTagEnd(), Color.decode("#15B5CC"));
            }
            for (Attribute attribute : tag.getAttributes()) {
                editor.addHightlight(attribute.getNameStart(), attribute.getNameEnd(), Color.decode("#0E923D"));
                if (attribute instanceof ValueAttribute) {
                    ValueAttribute valueAttribute = (ValueAttribute) attribute;
                    editor.addHightlight(valueAttribute.getValueStart(), valueAttribute.getValueEnd(), Color.decode("#E5AB3C"));
                }
            }
            editor.addHightlight(node.getStart(), node.getEnd(), Color.decode("#15B5CC"));
        } else if (node instanceof CommentNode) {
            editor.addHightlight(node.getStart(), node.getEnd(), Color.decode("#647374"));
        } else if (node instanceof DocTypeNode) {
            editor.addHightlight(node.getStart(), node.getEnd(), Color.decode("#F07178"));
        }
    }

    @Override
    public String[] fileTypes() {
        return new String[] {"html", "htm"};
    }

    @Override
    public Language getLanguage() {
        return Language.HTML;
    }

    @Override
    public Inspection[] getInspections() {
        return INSPECTIONS;
    }
}
