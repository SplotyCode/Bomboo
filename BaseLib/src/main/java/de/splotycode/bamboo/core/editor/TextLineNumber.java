package de.splotycode.bamboo.core.editor;

import de.splotycode.bamboo.core.gui.ColorConstants;
import de.splotycode.bamboo.core.util.ui.DrawUtils;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.HashMap;

//http://www.camick.com/java/source/TextLineNumber.java
public class TextLineNumber extends JComponent {

    private final static int HEIGHT = Integer.MAX_VALUE - 1000000;

    private JTextComponent component;

    private int lastDigits;
    private int lastHeight;

    private HashMap<String, FontMetrics> fonts = new HashMap<>();

    public TextLineNumber(JTextComponent component) {
        this.component = component;
        setFont(component.getFont());
        setPreferredWidth();
    }

    private void setPreferredWidth() {
        Element root = component.getDocument().getDefaultRootElement();
        int lines = root.getElementCount();
        int digits = Math.max(String.valueOf(lines).length(), 3);

        if (lastDigits != digits) {
            lastDigits = digits;
            FontMetrics fontMetrics = getFontMetrics(getFont());
            int width = fontMetrics.charWidth('0') * digits;
            Insets insets = getInsets();
            int preferredWidth = insets.left + insets.right + width;

            Dimension d = getPreferredSize();
            d.setSize(preferredWidth, HEIGHT);
            setPreferredSize(d);
            setSize(d);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = DrawUtils.get2dGraphics(g);
        super.paintComponent(g2d);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        FontMetrics fontMetrics = component.getFontMetrics(component.getFont());
        Insets insets = getInsets();

        Rectangle clip = g.getClipBounds();
        int rowStartOffset = component.viewToModel(new Point(0, clip.y));
        int endOffset = component.viewToModel(new Point(0, clip.y + clip.height));

        g.setColor(ColorConstants.COLOR_BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(ColorConstants.COLOR_OUTLINE);
        g.fillRect(getWidth() - 2, 0, getWidth(), getHeight());

        while (rowStartOffset <= endOffset) {
            try {
                g.setColor(Editor.NORMAL_TEXT_COLOR);
                String lineNumber = getTextLineNumber(rowStartOffset);
                int x = insets.left;
                int y = getOffsetY(rowStartOffset, fontMetrics);
                g.drawString(lineNumber, x, y);

                rowStartOffset = Utilities.getRowEnd(component, rowStartOffset) + 1;
            } catch (Exception e) {
                break;
            }
        }
    }

    private String getTextLineNumber(int rowStartOffset) {
        Element root = component.getDocument().getDefaultRootElement();
        int index = root.getElementIndex(rowStartOffset);
        Element line = root.getElement(index);

        return line.getStartOffset() == rowStartOffset ? String.valueOf(index + 1) : "";
    }


    private int getOffsetY(int rowStartOffset, FontMetrics fontMetrics) throws BadLocationException {
        Rectangle r = component.modelToView(rowStartOffset);
        int lineHeight = fontMetrics.getHeight();
        int y = r.y + r.height;
        int descent = 0;

        if (r.height == lineHeight) {
            descent = fontMetrics.getDescent();
        } else {
            Element root = component.getDocument().getDefaultRootElement();
            int index = root.getElementIndex(rowStartOffset);
            Element line = root.getElement(index);

            for (int i = 0; i < line.getElementCount(); i++) {
                Element child = line.getElement(i);
                AttributeSet as = child.getAttributes();
                String fontFamily = (String) as.getAttribute(StyleConstants.FontFamily);
                Integer fontSize = (Integer) as.getAttribute(StyleConstants.FontSize);
                String key = fontFamily + fontSize;

                FontMetrics fm = fonts.get(key);

                if (fm == null) {
                    Font font = new Font(fontFamily, Font.PLAIN, fontSize);
                    fm = component.getFontMetrics(font);
                    fonts.put(key, fm);
                }

                descent = Math.max(descent, fm.getDescent());
            }
        }

        return y - descent;
    }

    public void update() {
        SwingUtilities.invokeLater(() -> {
            try {
                int endPos = component.getDocument().getLength();
                Rectangle rect = component.modelToView(endPos);

                if (rect != null && rect.y != lastHeight) {
                    setPreferredWidth();
                    repaint();
                    lastHeight = rect.y;
                }
            } catch (BadLocationException ignored) {

            }
        });
    }
}