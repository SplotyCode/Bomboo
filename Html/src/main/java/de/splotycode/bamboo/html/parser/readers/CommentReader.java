package de.splotycode.bamboo.html.parser.readers;

import de.splotycode.bamboo.html.parser.DomHtmlParser;
import de.splotycode.bamboo.html.parser.DomReader;
import de.splotycode.bamboo.html.parser.dom.CommentNode;

public class CommentReader implements DomReader<DomHtmlParser> {

    private String comment = "";
    private int start;

    @Override
    public void readNext(char c, DomHtmlParser parser) {
        int current = parser.getIndex();
        if (parser.isLocked()){
            if(parser.skipIfFollow("-->")) {
                parser.setLocked(null);
                parser.getCurrentParent().getChilds().add(new CommentNode(comment, parser.getCurrentParent(), start, parser.getIndex()));
                comment = "";
            } else comment += c;
        }else if(parser.skipIfFollow("<!--")){
            start = current;
            parser.stopCurrent();
            parser.setLocked(this);
        }
    }

    @Override public void parseDone(DomHtmlParser parser) {}

}
