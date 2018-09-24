package de.splotycode.bamboo.core.gui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BambooFileView extends FileView {

    @Getter private static BambooFileView instance = new BambooFileView();

    private FileSystemView view = FileSystemView.getFileSystemView();

    @Override
    public Icon getIcon(File file) {
        if (file.isDirectory()) {
            return view.getSystemIcon(file);
        }
        URL url = getClass().getResource("/icons/files/" + FilenameUtils.getExtension(file.getName()) + ".png");
        if (url == null) {
            return view.getSystemIcon(file);
        }
        return new ImageIcon(url);
    }

    static Icon scale(Icon icon, double scaleFactor, JTree tree) {
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();

        width = (int) Math.ceil(width * scaleFactor);
        height = (int) Math.ceil(height * scaleFactor);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = image.createGraphics();
        g.scale(scaleFactor, scaleFactor);
        icon.paintIcon(tree, g, 0, 0);
        g.dispose();

        return new ImageIcon(image);
    }

}
