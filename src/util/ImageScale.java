package util;

import java.awt.Image;
import javax.swing.ImageIcon;

public class ImageScale {
    public static ImageIcon getImage(ImageIcon icon,int width,int height){
        Image image=icon.getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH);
        icon.setImage(image);
        return icon;
    }
}
