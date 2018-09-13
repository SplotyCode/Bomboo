package de.splotycode.bamboo.core.actions;

import de.splotycode.bamboo.core.exceptions.MethodNotSupportedException;
import de.splotycode.bamboo.core.project.WorkSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.event.ActionEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class BambooEvent extends ActionEvent {

    private WorkSpace workSpace;
    private EventCause cause;

    @Override
    protected void consume() {
        throw new MethodNotSupportedException();
    }

    @Override
    protected boolean isConsumed() {
        throw new MethodNotSupportedException();
    }

    public BambooEvent(Object o, int i, String s, WorkSpace workSpace, EventCause cause) {
        super(o, i, s);
        this.workSpace = workSpace;
        this.cause = cause;
    }

    public BambooEvent(Object o, int i, String s, int i1, WorkSpace workSpace, EventCause cause) {
        super(o, i, s, i1);
        this.workSpace = workSpace;
        this.cause = cause;
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
