package de.splotycode.bamboo.html.inspections;

import de.splotycode.bamboo.core.editor.Editor;
import de.splotycode.bamboo.core.editor.error.ErrorType;
import de.splotycode.bamboo.core.editor.error.Inspection;
import de.splotycode.bamboo.html.parser.attriute.Attribute;
import de.splotycode.bamboo.html.parser.attriute.ValueAttribute;
import de.splotycode.bamboo.html.parser.dom.TagNode;
import de.splotycode.bamboo.html.util.DomTreeUtil;

import java.nio.charset.Charset;

public class InappropriateMetaCharsetInspection extends Inspection {

    private static final String HTML4_PREFIX = "text/html;charset=";

    @Override
    public void inspect(Editor editor) {
        for (TagNode tag : DomTreeUtil.getNodes(editor, "meta")) {
            //Html 5
            ValueAttribute charset = tag.getValueAttribute("charset");
            String rawCharset;
            if (charset != null) {
                if ((rawCharset = charset.getStringValue()).isEmpty()) {
                    report(editor, "No Charset '" + rawCharset + "'", tag.getStart(), tag.getAbsoluteEnd());
                } else if (!Charset.isSupported(rawCharset)) {
                    report(editor, "Inappropriate Charset '" + rawCharset + "'", charset.getValueStart(), charset.getValueEnd());
                }
            }
            //Html 4
            ValueAttribute equiv = tag.getValueAttribute("http-equiv");
            if (equiv != null && equiv.getStringValue().equalsIgnoreCase("Content-Type")) {
                charset = tag.getValueAttribute("content");
                if (charset == null) {
                    report(editor, "No Content", tag.getStart(), tag.getAbsoluteEnd());
                } else {
                    rawCharset = charset.getStringValue();
                    if (!rawCharset.startsWith(HTML4_PREFIX)) {
                        report(editor, "Invalid Charset format", charset.getValueStart(), charset.getValueEnd());
                    } else {
                        rawCharset = rawCharset.substring(HTML4_PREFIX.length());
                        if (rawCharset.isEmpty()) {
                            report(editor, "No Charset", tag.getStart(), tag.getAbsoluteEnd());
                        } else if (!Charset.isSupported(rawCharset)) {
                            report(editor, "Inappropriate Charset '" + rawCharset + "'", charset.getValueStart() + 1 + HTML4_PREFIX.length(), charset.getValueEnd() - 1);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected ErrorType getType() {
        return ErrorType.WARNING_LOW;
    }

}
