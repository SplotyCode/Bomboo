package de.splotycode.bamboo.core.actions;

import de.splotycode.bamboo.core.data.DataFactory;
import de.splotycode.bamboo.core.data.DataKey;
import de.splotycode.bamboo.core.exceptions.MethodNotSupportedException;
import de.splotycode.bamboo.core.project.WorkSpace;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class BambooEvent extends ActionEvent {

    private WorkSpace workSpace;
    private EventCause cause;
    private DataFactory dataFactory;
    private Window window;

    public FactoryBuilder factoryBuilder() {
        dataFactory = new DataFactory();
        return new FactoryBuilder();
    }

    public FactoryBuilder factoryBuilder(DataFactory dataFactory) {
        this.dataFactory = dataFactory;
        return new FactoryBuilder();
    }

    public class FactoryBuilder {

        public <T> FactoryBuilder addData(DataKey<T> key, T data) {
            dataFactory.putData(key, data);
            return this;
        }

        public BambooEvent build() {
            return BambooEvent.this;
        }

    }

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
        window = SwingUtilities.windowForComponent((Component) o);
    }

    public BambooEvent(Object o, int i, String s, int i1, WorkSpace workSpace, EventCause cause) {
        super(o, i, s, i1);
        this.workSpace = workSpace;
        this.cause = cause;
        window = SwingUtilities.windowForComponent((Component) o);
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
