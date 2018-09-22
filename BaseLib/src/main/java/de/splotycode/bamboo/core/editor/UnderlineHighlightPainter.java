package de.splotycode.bamboo.core.editor;

import lombok.NoArgsConstructor;

import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.awt.*;

@NoArgsConstructor
public class UnderlineHighlightPainter implements Highlighter.HighlightPainter {

    private Color color = null;

    public UnderlineHighlightPainter(Color color) {
        this.color = color;
    }

    private void paintLine(Graphics g, Rectangle r, int x2) {
        int yTop = r.y + r.height - 3;
        g.fillRect(r.x, yTop, x2 - r.x, 3);
    }

    @Override
    public void paint(Graphics graphics, int start, int end, Shape shape, JTextComponent component) {
        Rectangle r0, r1, bounds = shape.getBounds();
        int right = bounds.x + bounds.width;
        try {
            r0 = component.modelToView(start);
            r1 = component.modelToView(end);
        } catch (BadLocationException ex) {
            return;
        }
        if (r0 == null || r1 == null) return;

        graphics.setColor(color == null ? component.getSelectionColor() : color);

        if (r0.y == r1.y) {
            paintLine(graphics, r0, r1.x);
            return;
        }

        paintLine(graphics, r0, right);

        r0.y += r0.height;
        r0.x = bounds.x;
        while (r0.y < r1.y) {
            paintLine(graphics, r0, right);
            r0.y += r0.height;
        }

        paintLine(graphics, r0, r1.x);
    }
}
