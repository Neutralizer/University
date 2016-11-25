package bot;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

public class BotHomeToWork {

	static Robot r;
	static Random ra;
	static Point newInstance;
	static Point left;
	static Point right;
	static Point bot;
	static Point top;
	static Point logout;
	static Point townFirstMove;
	static Point townSecondMove;
	static Point townClickingDoor;
	static Point clickSelf;
	static Point ballHealthPixelColorLocation;
	static Point lastCharge1stFlaskLocation;
	static Point levelUpGemsLocation;
	static boolean lowHealth = false; // not used
	static boolean breakCycle = false;
	static boolean logoutBoolean = false;
	static int ballHealthColor;
	static int lastChargeFlaskColor;
	static int timeAfterEnteringLakeMS;
	static int timeAfterLoggingInMS;

	// commented checkIfHealthIsLow(); not commented

	public static void main(String[] args) throws Exception {
		r = new Robot();
		r.setAutoDelay(20);
		r.setAutoWaitForIdle(true);
		ra = new Random();

		// townFirstMove = new Point(1120, 1050); // work
		// townSecondMove = new Point(280, 1020);
		// townClickingDoor = new Point(850, 600);
		// newInstance = new Point(450, 365);
		//
		// clickSelf = new Point(950, 550);
		// logout = new Point(1001, 525);
		// ballHealthPixelColorLocation = new Point(180, 1040);
		// lastCharge1stFlaskLocation = new Point(358, 1170);
		// ballHealthColor = -6875105;
		// lastChargeFlaskColor = -11070204;
		// timeAfterEnteringLakeMS = 15000;
		// timeAfterLoggingInMS = 20000;
		//
		// left = new Point(500, 500);
		// right = new Point(1300, 501);
		// bot = new Point(902, 904);
		// top = new Point(903, 180);

		townFirstMove = new Point(420, 680); // home
		townSecondMove = new Point(220, 650);
		townClickingDoor = new Point(440, 350);
		newInstance = new Point(190, 230);
		levelUpGemsLocation = new Point(980, 220);

		clickSelf = new Point(525, 340);
		logout = new Point(550, 336);
		ballHealthPixelColorLocation = new Point(130, 680);
		lastCharge1stFlaskLocation = new Point(228, 748);
		ballHealthColor = -8778219;
		lastChargeFlaskColor = -11399167;
		timeAfterEnteringLakeMS = 25000;
		timeAfterLoggingInMS = 30000;

		left = new Point(200, 300);
		right = new Point(800, 302);
		bot = new Point(501, 500);
		top = new Point(502, 110);

		run();

	}

	private static void run() throws Exception {
		while (true) {
			breakCycle = false;
			inCharSelect();
			inTownAndOutsideImpr();
			outside4thTown();
		}
		// moveVariation1();

	}

	private static void inCharSelect() {
		r.delay(7000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(40);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(timeAfterLoggingInMS);

	}

	private static void inTownAndOutsideImpr() throws Exception {

		// moveVariation1();
		originalMoveVariationInTown();

		// going to the instance button
		instaMoveMouse((int) newInstance.getX(), (int) newInstance.getY());
		r.delay(40);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.delay(40);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.delay(timeAfterEnteringLakeMS);

		castCursesAndGolem();
		r.delay(1000);

		int temp = ra.nextInt(100);
		// level up gems
		if (temp < 20) {
			instaMoveMouse(levelUpGemsLocation.x, levelUpGemsLocation.y);
			for (int i = 0; i < 10; i++) {
				r.delay(40);
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.delay(40);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
				r.delay(40);
			}
		}

		instaClickAndMoveWith1SDelay(bot.x, bot.y);
		instaClickAndMoveWith1SDelay(bot.x, bot.y);
		instaClickAndMoveWith1SDelay(right.x, right.y);
		instaClickAndMoveWith1SDelay(right.x, right.y);
		instaClickAndMoveWith1SDelay(right.x, right.y);

		// instaClickAndMoveWith1SDelay(right.x,right.y);
	}

	private static void outside4thTown() throws Exception {

		 r.delay(2000);

		long startTime = System.currentTimeMillis();
		while (true) {
			if (breakCycle) {
				break;
			}
			//TODO char is moving when locating the item and missing it - sh b fx
			ifNiceItemFound();
			
			r.mousePress(InputEvent.BUTTON3_MASK);
			r.delay(200);

			// 1 time guaranteed right and every other - 1 bot
			instaClickAndMoveWith1SDelay(right.x, right.y);
			if (ra.nextBoolean()) {
				instaClickAndMoveWith1SDelay(bot.x, bot.y);
			}
			checkIfHealthIsLow();
			int temp = ra.nextInt(2);
			if (temp == 0) {
				instaClickAndMoveWith1SDelay(left.x, left.y);
			}else 
//				if (temp == 1) {
//				instaClickAndMoveWith1SDelay(right.x, right.y);
//			} else 
				if (temp == 2 || temp == 1) {
					instaClickAndMoveWith1SDelay(bot.x, bot.y);
			} 
//			else if (temp == 3) {
//				instaClickAndMoveWith1SDelay(top.x, top.y);
//			}

			checkIfHealthIsLow();
			r.delay(100);
			
			r.mouseRelease(InputEvent.BUTTON3_MASK);

			long estimatedTime = System.currentTimeMillis() - startTime;
			// if the time in the instance is more than 2 min - relog
			if (estimatedTime > 100000) {
				globalLogout();
				break;
			}
		}
	}

	private static void ifNiceItemFound() {
		Rectangle scr = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage image = r.createScreenCapture(scr);
		Point point = null;
		boolean found = false;
		
		for (int i = 0; i < image.getWidth(); i++) {
			if (found) {
				break;
			}
			for (int p = 0; p < image.getHeight(); p++) {
				if (found) {
					break;
				}
				if (image.getRGB(i, p) == -394503 || image.getRGB(i, p) == -3629439) {
					// there is an exalt here // or an alchemy
					point = new Point(i + 5, p + 5);
					found = true;
				}

			}

		}

		if (point != null) {
//			r.delay(50);
//			r.mouseRelease(InputEvent.BUTTON3_MASK);
			instaMoveMouse(clickSelf.x, clickSelf.y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.delay(50);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			instaClickAndMoveWith2SDelay(point.x + 5, point.y + 5);

//			r.mousePress(InputEvent.BUTTON3_MASK);
		}
	}

	public static void originalMoveVariationInTown() {
		// r.delay(6000);
		// first step after login
		instaClickAndMoveWith2SDelay((int) townFirstMove.getX(),
				(int) townFirstMove.getY());
		// going to above potion location before the gate
		instaClickAndMoveWith2SDelay((int) townSecondMove.getX(),
				(int) townSecondMove.getY());
		// clicking the door
		instaMoveMouse((int) townClickingDoor.getX(), (int) townClickingDoor.getY());
		r.keyPress(KeyEvent.VK_CONTROL);
		r.delay(200);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.delay(60);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.delay(1000);
	}

	public static void moveVariation1() {
		r.delay(1000);
		Point customMove1_1 = new Point(900, 900);
		Point customMove1_2 = new Point(570, 900);
		Point customMove1_3 = new Point(700, 700);
		// first step after login
		instaClickAndMoveWith1SDelay(customMove1_1.x, customMove1_1.y);
		// going to above potion location before the gate
		instaClickAndMoveWith1SDelay(customMove1_2.x, customMove1_2.y);
		// clicking the door
		instaMoveMouse(customMove1_3.x, customMove1_3.y);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.delay(200);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.delay(60);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.delay(1000);
	}


	private static void castCursesAndGolem() {
		r.delay(100);
		// instaMoveMouse(540, 330);//click self
		instaMoveMouse((int) clickSelf.getX(), (int) clickSelf.getY());

		r.keyPress(KeyEvent.VK_R);
		r.delay(40);
		r.keyRelease(KeyEvent.VK_R);
		r.delay(1000);

		r.keyPress(KeyEvent.VK_T);
		r.delay(40);
		r.keyRelease(KeyEvent.VK_T);
		r.delay(1000);

		r.keyPress(KeyEvent.VK_Q);
		r.delay(40);
		r.keyRelease(KeyEvent.VK_Q);
		r.delay(500);

	}


	private static void checkIfHealthIsLow() throws Exception {
		Color color = r.getPixelColor(ballHealthPixelColorLocation.x,
				ballHealthPixelColorLocation.y);
		if (color.getRGB() != ballHealthColor) {
			// lowHealth = true;
			ifPotionEmptyLogout();
			useHealthPotion();
		}
	}

	private static void ifPotionEmptyLogout() throws Exception {
		Color color = r.getPixelColor(lastCharge1stFlaskLocation.x,
				lastCharge1stFlaskLocation.y);
		if (color.getRGB() != lastChargeFlaskColor) {
			// flask is empty - drink next one or logout
			globalLogout();
		}

	}

	private static void globalLogout() throws Exception {
		int randomDelay = ra.nextInt(450 - 250 + 1) + 250;
		r.delay(randomDelay);
		r.keyPress(KeyEvent.VK_ESCAPE);
		r.delay(200);
		r.keyRelease(KeyEvent.VK_ESCAPE);
		instaMoveMouse(logout.x, logout.y);
		breakCycle = true;
		emergency2ndPotDrink();
		r.delay(randomDelay);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.delay(150);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		// r.delay(10000);
	}

	private static void emergency2ndPotDrink() {
		r.delay(100);
		r.keyPress(KeyEvent.VK_1);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_1);

	}

	private static void useHealthPotion() {
		r.delay(100);
		r.keyPress(KeyEvent.VK_1);
		r.delay(100);
		r.keyRelease(KeyEvent.VK_1);

	}

	private static void instaMoveMouse(int x, int y) {
		r.delay(50);
		r.mouseMove(x, y);
		int randomDelay = ra.nextInt(260 - 140 + 1) + 140;
		r.delay(randomDelay);
	}

	private static void instaClickAndMoveWith1SDelay(int x, int y) {
		r.delay(50);
		r.mouseMove(x, y);
		int randomDelay = ra.nextInt(260 - 140 + 1) + 140;
		r.delay(randomDelay);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.delay(1000);
	}

	private static void instaClickAndMoveWith2SDelay(int x, int y) {
		r.delay(50);
		r.mouseMove(x, y);
		int randomDelay = ra.nextInt(260 - 140 + 1) + 140;
		r.delay(randomDelay);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.delay(2000);
	}


	private static void testPositionAndHealth(int x, int y) throws Exception {
		checkIfHealthIsLow();
		Point p = MouseInfo.getPointerInfo().getLocation();
		if (p.x != x || p.y != y) {
			throw new Exception();
		}

	}


	// full -11070204
	// empty -11848175

	// alch
	// -3629439
	// java.awt.Color[r=200,g=158,b=129]

	// divine, exalted, 6L
	// -395017
	// java.awt.Color[r=249,g=248,b=247]

	// SetBackgroundColor 254 253 252 251
	// SetBackgroundColor 252 253 252 253 - home

	//ilvl 80 - nightmare bascinet
	//-3645668
	//java.awt.Color[r=200,g=95,b=28]
}
