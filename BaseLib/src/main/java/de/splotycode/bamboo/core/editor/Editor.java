package de.splotycode.bamboo.core.editor;

import de.splotycode.bamboo.core.data.DataFactory;
import de.splotycode.bamboo.core.editor.error.CodeWarning;
import de.splotycode.bamboo.core.editor.error.ErrorType;
import de.splotycode.bamboo.core.editor.error.Inspection;
import de.splotycode.bamboo.core.editor.error.QuickFix;
import de.splotycode.bamboo.core.gui.components.BambooScrollPane;
import de.splotycode.bamboo.core.gui.components.field.BambooTextArea;
import de.splotycode.bamboo.core.gui.components.menu.BambooMenuItem;
import de.splotycode.bamboo.core.project.LanguageDescriptor;
import de.splotycode.bamboo.core.project.WorkSpace;
import de.splotycode.bamboo.core.util.Destroyable;
import lombok.Getter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Editor extends JTextPane implements Destroyable, DocumentListener, MouseListener, MouseMotionListener, KeyListener  {

    @Getter private DataFactory dataFactory = new DataFactory();

    @Getter private File file;
    private BambooScrollPane scrollPane = new BambooScrollPane(this);
    @Getter private LanguageDescriptor descriptor;
    private WorkSpace workSpace;

    private boolean lastChanged = false;

    private String diskState;

    private TextLineNumber lineNumbers;

    private List<CodeWarning> warnings = new ArrayList<>();
    private JPopupMenu quickfixMenu = new JPopupMenu();

    private AttributeSet defaultAttributes = getParagraphAttributes();

    public static final Color NORMAL_TEXT_COLOR = Color.decode("#B8C1BD");

    public Editor(File file, LanguageDescriptor descriptor, WorkSpace workSpace) {
        setText("Loading...");
        setEditable(false);
        setBackground(Color.decode("#333336"));
        setCaretColor(NORMAL_TEXT_COLOR);
        setForeground(NORMAL_TEXT_COLOR);
        setFont(new Font("Fira Code", getFont().getStyle(), 14));
        this.file = file;
        this.workSpace = workSpace;
        this.descriptor = descriptor;
        lineNumbers = new TextLineNumber(this);
        scrollPane.setRowHeaderView(lineNumbers);
        ToolTipManager.sharedInstance().registerComponent(this);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        getDocument().addDocumentListener(this);
        try {
            diskState = descriptor.loadContent(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setText(diskState);
        lineNumbers.update();
        descriptor.prepairEditor(this, workSpace);
        updateDocument();
        setEditable(true);
    }

    public Component getComponent() {
        return scrollPane;
    }

    public void save() {
        if (hasChanged()) {
            setEditable(false);
            try {
                descriptor.saveContent(this, getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
            diskState = getText();
            setEditable(true);
            workSpace.triggerFileEditedChanged();
        }
    }

    public boolean hasChanged() {
        return !getText().equals(diskState);
    }

    public void addWarning(CodeWarning warning) {
        warnings.add(warning);
        try {
            getHighlighter().addHighlight(warning.getStart(), warning.getTo(), new UnderlineHighlightPainter(warning.getType() == ErrorType.ERROR ? Color.red : Color.yellow));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        save();
        ToolTipManager.sharedInstance().unregisterComponent(this);
    }

    private void updateDocument() {
        lineNumbers.update();

        if (hasChanged() != lastChanged) {
            workSpace.triggerFileEditedChanged();
        }
        descriptor.onTextChange(this);
        for (Inspection inspection : descriptor.getInspections()) {
            inspection.inspect(this);
        }
        lastChanged = hasChanged();
    }

    @Override
    public void insertUpdate(DocumentEvent event) {
        updateDocument();
    }

    @Override
    public void removeUpdate(DocumentEvent event) {
        updateDocument();
    }

    @Override
    public void changedUpdate(DocumentEvent event) {
        //updateDocument();
    }

    @Override
    public void mouseClicked(MouseEvent event) {

    }

    @Override
    public void mousePressed(MouseEvent event) {

    }

    @Override
    public void mouseReleased(MouseEvent event) {

    }

    @Override
    public void mouseEntered(MouseEvent event) {

    }

    @Override
    public void mouseExited(MouseEvent event) {

    }

    @Override
    public void mouseDragged(MouseEvent event) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public String getToolTipText(MouseEvent event) {
        int index = viewToModel(event.getPoint());
        for (CodeWarning warning : warnings) {
            if (warning.getStart() <= index && warning.getTo() >= index) {
                return warning.getMessage();
            }
        }
        return super.getToolTipText();
    }

    @Override
    public void keyTyped(KeyEvent event) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent event) {
        try {
            if (event.getKeyCode() == KeyEvent.VK_ENTER && (event.getModifiers() & KeyEvent.ALT_MASK) != 0) {
                for (CodeWarning warning : warnings) {
                    if (warning.getStart() <= getCaretPosition() && warning.getTo() >= getCaretPosition()) {
                        quickfixMenu.removeAll();
                        Rectangle point = modelToView(warning.getStart());
                        for (QuickFix fix : warning.getFixes()) {
                            BambooMenuItem item = new BambooMenuItem(fix.getName());
                            item.setToolTipText(fix.getDescription());
                            item.addActionListener(actionEvent -> {
                                fix.fix();
                                quickfixMenu.setVisible(false);
                            });
                            quickfixMenu.add(item);
                        }
                        quickfixMenu.show(this, point.x, point.y + point.height);
                        quickfixMenu.setVisible(true);
                        return;
                    }
                }
            } else {
                quickfixMenu.setVisible(false);
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    public void clearHilights() {
        System.out.println("CLEARED HIGHLIGHTS");
        SwingUtilities.invokeLater(() -> getStyledDocument().setCharacterAttributes(0, getText().length(), defaultAttributes, true));
    }

    public void clearWarning() {
        warnings.clear();
        getHighlighter().removeAllHighlights();
    }

    public void addHightlight(int start, int end, Color color) {
        System.out.println("Added Highlight: " + start + " " + end);
        if (end < start) throw new IllegalArgumentException("End is smaller then start");
        int delta = end - start + 1;
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, color);
        SwingUtilities.invokeLater(() -> getStyledDocument().setCharacterAttributes(start, delta, set, false));
    }

    public int getLineCount() {
        Element var1 = this.getDocument().getDefaultRootElement();
        return var1.getElementCount();
    }

    public int getLineStartOffset(int var1) {
        int var2 = this.getLineCount();
        if (var1 < 0) {
            throw new IllegalArgumentException("Negative line");
        } else if (var1 >= var2) {
            throw new IllegalArgumentException("No such line");
        } else {
            Element var3 = this.getDocument().getDefaultRootElement();
            Element var4 = var3.getElement(var1);
            return var4.getStartOffset();
        }
    }
}
