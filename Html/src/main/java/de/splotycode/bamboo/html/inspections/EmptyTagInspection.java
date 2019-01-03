package de.splotycode.bamboo.html.inspections;

import de.splotycode.bamboo.core.editor.Editor;
import de.splotycode.bamboo.core.editor.error.ErrorType;
import de.splotycode.bamboo.core.editor.error.Inspection;
import de.splotycode.bamboo.html.parser.dom.TagNode;
import de.splotycode.bamboo.html.util.DomTreeUtil;

public class EmptyTagInspection extends Inspection {

    @Override
    public void inspect(Editor editor) {
        for (TagNode node : DomTreeUtil.getAllTagNodes(editor)) {
            if (!node.getName().equals("script") && !node.canSelfClose() && node.getChilds().isEmpty()) {
                report(editor, "Empty Tag", node.getStart(), node.getAbsoluteEnd());
            }
        }
    }

    @Override
    protected ErrorType getType() {
        return ErrorType.WARNING_LOW;
    }
}
