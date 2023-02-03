package utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageHelper {

    public static final byte FLIP_HORIZONTAL = 1;
    public static final byte FLIP_VERTICAL = 2;
    public static Exception wordOutOfLimit = new Exception();

    private ImageHelper(){}

    public static File imageAddString (@Nullable File input, String s, Color t, Color bg) throws Exception {
        try {
            BufferedImage getImage = null;
            int width = 900;
            int height = 900;
            //initialize variable

            if (t == null) t = Color.BLACK;
            if (bg == null) bg = Color.WHITE;
            //if color is null

            if (input != null) {
                getImage = ImageIO.read(input);
                width = getImage.getWidth();
                height = getImage.getHeight();
            }
            //input image if not null

            BufferedImage newImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = newImage.createGraphics();
            //setUp image

            if (input != null) g2d.drawImage(getImage,0,0,width,height,null);
            else {
                g2d.setColor(bg);
                g2d.fillRect(0,0,width,height);
            }
            //draw the background/image

            g2d.setColor(t);
            FontMetrics metrics;
            int font_index = 120;
            do {
                g2d.setFont(new Font(null, Font.PLAIN, font_index));
                metrics = g2d.getFontMetrics();
                font_index--;
            } while (metrics.stringWidth(s) >= width - 40 && g2d.getFont().getSize() > 40);
            //adjust font size

            String[] words = s.split(" ");
            for (int i = 0; i < words.length; i++) {
                words[i] += " ";
                if (words[i].length() > 40) throw wordOutOfLimit;
            }
            ArrayList<String> word_rows = new ArrayList<>();
            int word_count = 0;
            word_rows.add("");
            for (String word: words){
                word_count += word.length();
                if (word_count <= 40) word_rows.set(word_rows.size()-1,word_rows.get(word_rows.size()-1)+word);
                else {
                    word_count = word.length();
                    word_rows.add(word);
                }
            }
            //split out text with different rows

            for (int i = 0; i < word_rows.size();i++)
                g2d.drawString(word_rows.get(i),
                        (width-metrics.stringWidth(word_rows.get(i)))/2,
                        (height-metrics.getHeight()*word_rows.size())/2+(int)(metrics.getHeight()*(0.5+i)));
            //display text

            g2d.dispose();
            //draw on new image
            File output = new File("res/tmp/image.png");
            ImageIO.write(newImage,"png",output);
            return output;
            //output file
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File imageFlip(@NotNull File input, byte direction) throws Exception {
        try {
            BufferedImage getImage = ImageIO.read(input);
            if (getImage == null) throw new Exception("no image detected");
            int width = getImage.getWidth();
            int height = getImage.getHeight();
            BufferedImage newImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            for (int y = 0; y < height; y++){
                for (int x = 0; x < width; x++){
                    switch (direction){
                        case FLIP_HORIZONTAL -> newImage.setRGB((width - 1) - x, y, getImage.getRGB(x,y));
                        case FLIP_VERTICAL -> newImage.setRGB(x, (height - 1) -y, getImage.getRGB(x,y));
                    }
                }
            }
            File output = new File("res/tmp/flipped.png");
            ImageIO.write(newImage,"png",output);
            return output;
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
