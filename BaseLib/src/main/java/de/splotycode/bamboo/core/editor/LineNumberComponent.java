package de.splotycode.bamboo.core.editor;

import de.splotycode.bamboo.core.gui.ColorConstants;
import de.splotycode.bamboo.core.util.ui.DrawUtils;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class LineNumberComponent extends JComponent {

    private Editor editor;

    public static final int LEFT_ALIGNMENT = 0;
    public static final int RIGHT_ALIGNMENT = 1;
    public static final int CENTER_ALIGNMENT = 2;

    private static final int HORIZONTAL_PADDING = 1;
    private static final int VERTICAL_PADDING = 3;

    private int alignment = LEFT_ALIGNMENT;

    public LineNumberComponent(Editor editor) {
        super();
        this.editor = editor;
    }

    public void adjustWidth() {
        int max = editor.getLineCount();
        if (getGraphics() == null) return;

        int width = getGraphics().getFontMetrics().stringWidth(String.valueOf(max)) + 3 + 2 * HORIZONTAL_PADDING;

        JComponent c = (JComponent) getParent();

        if (c == null) {
            return;
        }

        Dimension dimension = c.getPreferredSize();

        if (c instanceof JViewport) {
            JViewport view = (JViewport) c;
            Component parent = view.getParent();
            if (parent != null && parent instanceof JScrollPane) {
                JScrollPane scroller = (JScrollPane) view.getParent();
                dimension = scroller.getViewport().getView().getPreferredSize();
            }
        }
        if (width > getPreferredSize().width || width < getPreferredSize().width) {
            setPreferredSize(new Dimension(width + 2 * HORIZONTAL_PADDING, dimension.height));
            revalidate();
            repaint();
        }
    }

    public void setAlignment(int alignment) throws IllegalArgumentException{
        if ( alignment < 0 || alignment > 2 ){
            throw new IllegalArgumentException("Invalid alignment option");
        }
        this.alignment = alignment;
    }



    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = DrawUtils.get2dGraphics(g);
        super.paintComponent(g2d);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(ColorConstants.COLOR_BACKGROUND);

        g2d.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(ColorConstants.COLOR_OUTLINE);
        g.fillRect(getWidth() - 2, 0, getWidth(), getHeight());

        g.setColor(Color.white);

        for (int i = 0; i < editor.getLineCount(); i++) {

            Rectangle rect = getLineRect(i);
            String text = String.valueOf(i + 1);

            int yPosition = rect.y + rect.height - VERTICAL_PADDING;
            int xPosition = HORIZONTAL_PADDING;

            switch (alignment) {
                case RIGHT_ALIGNMENT:
                    xPosition = getPreferredSize().width - g.getFontMetrics().stringWidth(text) - HORIZONTAL_PADDING;
                    break;
                case CENTER_ALIGNMENT:
                    xPosition = getPreferredSize().width/2 - g.getFontMetrics().stringWidth(text)/2;
                    break;
                default:
                    break;
            }
            g2d.drawString(String.valueOf(i+1), xPosition, yPosition);
        }
    }

    public Rectangle getLineRect(int line) {
        try {
            return editor.modelToView(editor.getLineStartOffset(line));
        } catch(BadLocationException e) {
            e.printStackTrace();
            return new Rectangle();
        }
    }

}
