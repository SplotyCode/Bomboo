package de.splotycode.bamboo.html.util;

public final class AttributeHelper {

    public static boolean isSelftClosing(String attribute){
        if(attribute.startsWith("data-")) return false;
        switch (attribute) {
            case "controls":
            case "disabled":
            case "hidden":
            case "readonly":
                return true;
            default:
                return false;
        }
    }

    public static boolean isBoolean(String attribute){
        if(attribute.startsWith("data-")) return false;
        switch (attribute){
            case "autocomplete":
                return true;
            default:
                return false;
        }
    }

}
