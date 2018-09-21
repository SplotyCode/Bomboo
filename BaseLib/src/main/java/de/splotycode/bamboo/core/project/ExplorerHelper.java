package de.splotycode.bamboo.core.project;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.util.Arrays;
import java.util.List;

public final class ExplorerHelper {

    public static String getExpansionState(JTree tree){
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < tree.getRowCount(); i++){
            TreePath tp = tree.getPathForRow(i);
            if (tree.isExpanded(i)){
                sb.append(tp.toString());
                sb.append("=");
            }
        }
        String result = sb.toString();
        if (result.endsWith("=")) return result.substring(0, result.length() - 1);
        return sb.toString();
    }

    public static void setExpansionState(JTree tree, String s){
        List<String> list = Arrays.asList(s.split("="));
        for (int i = 0 ; i < tree.getRowCount(); i++){
            TreePath tp = tree.getPathForRow(i);
            if (list.contains(tp.toString())){
                tree.expandRow(i);
            }
        }
    }

}
