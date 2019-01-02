package de.splotycode.bamboo.html.parser.attriute;

public class ToggleAttribute extends ValueAttribute<Boolean> {

    private boolean value;
    private String rawValue;

    public ToggleAttribute(String name, String rawValue) {
        super(name);
        this.rawValue = rawValue;
        rawValue = rawValue.toLowerCase();
        if(rawValue.equals("on")) value = true;
        else if(rawValue.equals("off")) value = false;
        else throw new RuntimeException("Invalid Toggle value: '" + rawValue + "'");
    }

    public ToggleAttribute(String name, Boolean value){
        super(name);
        this.value = value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String getStringValue() {
        return rawValue;
    }
}
