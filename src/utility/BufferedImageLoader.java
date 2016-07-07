package utility;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class BufferedImageLoader {
	
	private BufferedImage image;
	
	public BufferedImage loadImage(String path) throws IOException {
		if (!path.substring(0, 1).equals("/")) {
			path = "/" + path;
		}
		image = ImageIO.read(getClass().getResource(path));
		return image;
	}
}
