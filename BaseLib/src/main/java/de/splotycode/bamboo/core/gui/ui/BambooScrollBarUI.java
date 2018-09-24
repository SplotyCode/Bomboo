package de.splotycode.bamboo.core.gui.ui;

import de.splotycode.bamboo.core.gui.ColorConstants;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BambooScrollBarUI extends BasicScrollBarUI implements MouseListener {

    public static ScrollBarUI createUI(JComponent var0) {
        BambooScrollBarUI ui = new BambooScrollBarUI();
        JScrollBar pane = (JScrollBar) var0;
        pane.setFocusable(false);
        pane.setRequestFocusEnabled(false);
        pane.addMouseListener(ui);
        return ui;
    }

    @Override
    protected void configureScrollBarColors() {
        thumbColor = ColorConstants.COLOR_OUTLINE;
    }

    @Override
    protected void paintThumb(Graphics graphics, JComponent jComponent, Rectangle rectangle) {
        if (isDragging) thumbColor = ColorConstants.COLOR_INTERACTIVE_DARKER;
        else thumbColor = ColorConstants.COLOR_INTERACTIVE;
        super.paintThumb(graphics, jComponent, rectangle);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton jbutton = new JButton();
        jbutton.setPreferredSize(new Dimension(0, 0));
        jbutton.setMinimumSize(new Dimension(0, 0));
        jbutton.setMaximumSize(new Dimension(0, 0));
        return jbutton;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        scrollbar.repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        scrollbar.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        scrollbar.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        scrollbar.repaint();
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        scrollbar.repaint();
    }
}
