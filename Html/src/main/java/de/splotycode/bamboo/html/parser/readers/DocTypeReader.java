package de.splotycode.bamboo.html.parser.readers;

import de.splotycode.bamboo.html.parser.DomHtmlParser;
import de.splotycode.bamboo.html.parser.DomReader;
import de.splotycode.bamboo.html.parser.dom.DocTypeNode;
import de.splotycode.bamboo.html.util.StringUtil;

public class DocTypeReader implements DomReader<DomHtmlParser> {

    private String doctype = "";
    private int start;

    @Override
    public void readNext(char c, DomHtmlParser parser) {
        if(parser.isLocked()){
            if(c == '>'){
                parser.getCurrentParent().getChilds().add(new DocTypeNode(doctype, parser.getCurrentParent(), start, parser.getIndex()));
                parser.setLocked(null);
                doctype = "";
            }else doctype += c;
        } else {
            start = parser.getIndex();
            if (parser.skipIfFollowIgnoreCase("<!doctype ")) {
                parser.setLocked(this);
                parser.stopCurrent();
            } else if (StringUtil.isNoWhiteSpace(c)) {
                parser.disableReader(this);
            }
        }
    }

    @Override public void parseDone(DomHtmlParser parser) {}
}
