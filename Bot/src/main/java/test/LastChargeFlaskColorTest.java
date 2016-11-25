package test;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;

public class LastChargeFlaskColorTest {
	static int lastChargeFlaskColor = -11070974;

	public static void main(String[] args) throws AWTException {
		Robot r = new Robot();
		r.delay(2000);
//		-11005181           
//		-11070974 
		Color color = r.getPixelColor(230, 746);
		if (color.getRGB() != lastChargeFlaskColor) {
			// flask is empty - drink next one sip or logout
			System.out.println("in");
			int colorRGB = color.getRGB();
			System.out.println(colorRGB + "             colorrgb");
			System.out.println(lastChargeFlaskColor + "         static flask int");
		}

	}

}
