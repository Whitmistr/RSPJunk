package ned.com.scripts.screenshotHandler;

import org.rspeer.script.Script;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class ScreenshotSaver implements Consumer<Image> {

        private final String name;

        public ScreenshotSaver(String name) {
            this.name = name;
        }

        @Override
        public void accept(Image image) {
            BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            Graphics2D graphics2D = bi.createGraphics();
            graphics2D.drawImage(image, 0, 0, null);
            graphics2D.dispose();

            try {
                File img = new File(Script.getDataDirectory().toString(),
                        File.separator + "screenshots" + File.separator + name + ".png");
                if (!img.exists() && !img.mkdirs() && !img.createNewFile()) {
                    return;
                }

                ImageIO.write(bi, "png", img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }