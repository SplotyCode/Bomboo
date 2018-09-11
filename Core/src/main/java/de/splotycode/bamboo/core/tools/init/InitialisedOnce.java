package de.splotycode.bamboo.core.tools.init;

public abstract class InitialisedOnce implements Initialisable {

    private boolean initialised;

    public final void initalize() {
        if (initialised) throw new AlreadyInitailizedException();
        initialised = true;
        init();
    }

    protected abstract void init();

}
