package de.splotycode.bamboo.core.gui.components.button;

import de.splotycode.bamboo.core.gui.ColorConstants;
import de.splotycode.bamboo.core.gui.FontConstants;
import de.splotycode.bamboo.core.gui.MouseClickedLisener;
import de.splotycode.bamboo.core.gui.components.label.BambooLabel;
import de.splotycode.bamboo.core.util.ui.DrawUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

public class BambooButton extends JButton implements MouseListener {

    private Color backgroundColor = ColorConstants.COLOR_INTERACTIVE, textColor = Color.white;

    public BambooButton() {
        init();
    }

    public BambooButton(Consumer<ActionEvent> consumer) {
        this();
        addActionListener(consumer::accept);
        init();
    }

    public BambooButton(String s, Consumer<ActionEvent> consumer) {
        super(s);
        addActionListener(consumer::accept);
        init();
    }

    public BambooButton(String s) {
        super(s);
        init();
    }

    //public void addActionListener(ActionListener listener) {
    //    listenerList.add(ActionListener.class, listener);
    //}

    public void init() {
        setBackground(ColorConstants.COLOR_INTERACTIVE);
        setForeground(Color.white);
        setOpaque(true);

        setFont(FontConstants.getSegoe(getFont().getSize()));

        Border border = getBorder();
        Border margin = new EmptyBorder(10,10,10,10);
        setBorder(new CompoundBorder(border, margin));

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(this);
    }

    /*@Override
    protected void paintComponent(Graphics graphics) {
        graphics = DrawUtils.get2dGraphics(graphics);
        super.paintComponent(graphics);

        Insets insets = getInsets();
        int w = getWidth() - insets.left - insets.right;
        int h = getHeight() - insets.top - insets.bottom;
        graphics.setColor(backgroundColor);
        graphics.fillRoundRect(insets.left, insets.top, w, h, 8, 8);

        FontMetrics metrics = graphics.getFontMetrics(FontConstants.getSegoe(getFont().getSize()));
        int x2 = (getWidth() - metrics.stringWidth(getText())) / 2;
        int y2 = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        graphics.setFont(FontConstants.getSegoe(getFont().getSize()));
        graphics.setColor(textColor);
        graphics.drawString(getText(), x2, y2);
    }*/
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = DrawUtils.get2dGraphics(g);
        g2.setPaint(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
        g2.setPaint(textColor);
        FontMetrics metrics = g2.getFontMetrics(FontConstants.getSegoe(getFont().getSize()));
        g2.drawString(getText(), (getWidth() - metrics.stringWidth(getText())) / 2, ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent());
        g2.dispose();
    }

    @Override public void mouseClicked(MouseEvent mouseEvent) {}
    @Override public void mousePressed(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    /*@Override public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseEvent.BUTTON1) return;
        try {
            Thread.sleep(70);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (ActionListener listener : listenerList.getListeners(ActionListener.class)) {
            listener.actionPerformed(new ActionEvent(this, 1, getText()));
        }
    }*/

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        backgroundColor = ColorConstants.COLOR_INTERACTIVE_DARKER;
        textColor = ColorConstants.OFFWHITE;
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        backgroundColor = ColorConstants.COLOR_INTERACTIVE;
        textColor = Color.white;
    }
}
