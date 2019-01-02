package de.splotycode.bamboo.html.util;

public final class TagHelper {

    public static boolean canSelfClose(String name){
        switch (name){
            case "img":
            case "br":
            case "hr":
            case "area":
            case "base":
            case "link":
            case "meta":
            case "source":
            case "track":
            case "col":
            case "command":
            case "embed":
            case "input":
            case "keygen":
                return true;
            default:
                return false;
        }
    }
}
