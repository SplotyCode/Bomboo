package de.splotycode.bamboo.core.editor.error;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
public class CodeWarning {

    protected ErrorType type;
    protected QuickFix[] fixes;
    protected String message;
    protected int start, to;

    public CodeWarning(String message, ErrorType type, int start, int to, QuickFix... fixes) {
        this.type = type;
        this.fixes = fixes;
        this.message = message;
        this.start = start;
        this.to = to;
    }
}
