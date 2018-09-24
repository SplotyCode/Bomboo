package de.splotycode.bamboo.core.gui.ui;

import de.splotycode.bamboo.core.gui.ColorConstants;
import de.splotycode.bamboo.core.gui.FontConstants;
import de.splotycode.bamboo.core.util.ui.DrawUtils;
import sun.awt.AppContext;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BambooButtonUI extends BasicButtonUI implements MouseListener {

    private AbstractButton button;
    private Color backgroundColor = ColorConstants.COLOR_INTERACTIVE, textColor = Color.white;

    public BambooButtonUI(AbstractButton button) {
        this.button = button;
    }

    public static ComponentUI createUI(JComponent var0) {
        AbstractButton button = (AbstractButton) var0;
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        BambooButtonUI ui = new BambooButtonUI(button);
        var0.addMouseListener(ui);
        return ui;
    }

    @Override
    public void paint(Graphics graphics, JComponent jComponent) {
        Graphics2D g2 = DrawUtils.get2dGraphics(graphics);
        g2.setPaint(backgroundColor);
        g2.fillRoundRect(0, 0, jComponent.getWidth(), jComponent.getHeight(), 8, 8);
        g2.setPaint(textColor);
        FontMetrics metrics = g2.getFontMetrics(FontConstants.getSegoe(jComponent.getFont().getSize()));
        g2.setFont(FontConstants.getSegoe(jComponent.getFont().getSize()));
        if (button.getText() != null) {
            g2.drawString(button.getText(), (jComponent.getWidth() - metrics.stringWidth(button.getText())) / 2, ((button.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent());
        }
        g2.dispose();
    }

    @Override
    protected void paintFocus(Graphics graphics, AbstractButton abstractButton, Rectangle rectangle, Rectangle rectangle1, Rectangle rectangle2) {
    }

    @Override
    protected void paintText(Graphics graphics, JComponent jComponent, Rectangle rectangle, String s) { }

    @Override
    protected void paintButtonPressed(Graphics graphics, AbstractButton abstractButton) {

    }

    @Override
    protected void paintIcon(Graphics graphics, JComponent jComponent, Rectangle rectangle) {

    }

    @Override
    protected void paintText(Graphics graphics, AbstractButton abstractButton, Rectangle rectangle, String s) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

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
