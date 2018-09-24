package de.splotycode.bamboo.core.gui.components.field;

import de.splotycode.bamboo.core.gui.ColorConstants;
import de.splotycode.bamboo.core.gui.FontConstants;
import de.splotycode.bamboo.core.util.ui.DrawUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;

public class BambooField extends JTextField implements FocusListener {

    private Shape shape;
    private Color borderColor = ColorConstants.COLOR_INTERACTIVE;

    public BambooField() {
        setOpaque(false);
        setBackground(ColorConstants.COLOR_BACKGROUND);
        setForeground(Color.white);
        setCaretColor(Color.white);
        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        setMargin(new Insets(2, 10, 2, 2));
        setHorizontalAlignment(SwingConstants.LEFT);
        setFont(FontConstants.getSegoe(getFont().getSize()));
        addFocusListener(this);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = DrawUtils.get2dGraphics(g);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
        super.paintComponent(g2);
    }

    protected void paintBorder(Graphics g) {
        Graphics2D g2 = DrawUtils.get2dGraphics(g);
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
    }

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
        }
        return shape.contains(x, y);
    }

    public void setBorderColor(Color color) {
        borderColor = color;
        repaint();
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        setForeground(Color.white);
        setBorderColor(ColorConstants.COLOR_INTERACTIVE);
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
        setForeground(ColorConstants.COLOR_OUTLINE);
        setBorderColor(ColorConstants.COLOR_OUTLINE);
    }
}
