package test;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Iterator;

public class AnotherLocatioNTest {
	static Robot r;

	public static void main(String[] args) throws AWTException {
		r = new Robot();
		r.delay(1500);
//		ifNiceItemFound();
		r.mouseMove(525, 340);

		Color color = r.getPixelColor(525, 340);
		
		System.out.println(color.getRGB());
		System.out.println(color);

		// full
		// -11399167
		// java.awt.Color[r=82,g=16,b=1]

		// empty
		// -11912425
		// java.awt.Color[r=74,g=59,b=23]

	}

	private static void ifNiceItemFound() {
		Rectangle scr = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

		BufferedImage image = r.createScreenCapture(scr);
//		int[] pixels = (((DataBufferInt) image.getRaster().getDataBuffer()).getData());
		Point point = null;
		boolean found = false;
		for(int i = 0; i < image.getWidth();i++){
			for(int p = 0; p < image.getHeight();p++){
				if(image.getRGB(i,p) == -3629439 ||image.getRGB(i,p) ==  -394503){
					// there is an alch here // or exalt
					point = new Point(i + 5, p + 5);
					found = true;
				}
				if(found){
					break;
				}
			}
			if(found){
				break;
			}
		}
		if(point != null){
			r.mouseMove(point.x, point.y);
		}
		


	}
	

}
