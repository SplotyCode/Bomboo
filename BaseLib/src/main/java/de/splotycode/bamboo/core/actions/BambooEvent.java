package de.splotycode.bamboo.core.actions;

import de.splotycode.bamboo.core.project.Project;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.event.ActionEvent;

@Data
public class BambooEvent extends ActionEvent {

    private Project project;

    @Override
    protected void consume() {
        super.consume();
    }

    @Override
    protected boolean isConsumed() {
        return super.isConsumed();
    }

    public BambooEvent(Object o, int i, String s, Project project) {
        super(o, i, s);
        this.project = project;
    }

    public BambooEvent(Object o, int i, String s, int i1, Project project) {
        super(o, i, s, i1);
        this.project = project;
    }

    public BambooEvent(Object o, int i, String s, long l, int i1, Project project) {
        super(o, i, s, l, i1);
        this.project = project;
    }

    public boolean isAlt() {
        return (getModifiers() & ActionEvent.ALT_MASK) > 0;
    }

    public boolean isShift() {
        return (getModifiers() & ActionEvent.SHIFT_MASK) > 0;
    }

    public boolean isCtrl() {
        return (getModifiers() & ActionEvent.SHIFT_MASK) > 0;
    }

}
