package de.splotycode.bamboo.core.editor.error;

import de.splotycode.bamboo.core.editor.Editor;

public abstract class Inspection {

    public abstract void inspect(Editor editor);

    protected abstract ErrorType getType();

    protected void report(Editor editor, String message, int start, int end, QuickFix... quickFixes) {
        System.out.println(getClass().getSimpleName() + " reported error: " + message);
        editor.addWarning(new CodeWarning(message, getType(), start, end, quickFixes));
    }

}
