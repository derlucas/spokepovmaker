import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.image.renderable.ParameterBlock;


class Pixeltester extends Frame {
	Image rawImage;
	int rawWidth;
	int rawHeight;
	Image modImage;

	int inTop;
	int inLeft;

	public static void main(String[] args) {
		Pixeltester obj = new Pixeltester();
		obj.repaint();
	}

	public Pixeltester() {
		rawImage = Toolkit.getDefaultToolkit().getImage(
				"/home/lucas/home/bilder/100_0466.geaendert.JPG");

		MediaTracker tracker = new MediaTracker(this);
		tracker.addImage(rawImage, 1);

		try {
			if (!tracker.waitForID(1, 10000)) {
				System.out.println("Load error.");
				System.exit(1);
			}
		} catch (InterruptedException e) {
			System.out.println(e);
		}

		rawWidth = rawImage.getWidth(this);
		rawHeight = rawImage.getHeight(this);

		this.setVisible(true);

		inTop = this.getInsets().top;
		inLeft = this.getInsets().left;

		this.setSize(inLeft + rawWidth, inTop + 2 * rawHeight);
		this.setTitle("Copyright 1997, Baldwin");
		this.setBackground(Color.yellow);

		int[] pix = new int[rawWidth * rawHeight];

		try {
			PixelGrabber pgObj = new PixelGrabber(rawImage, 0, 0, rawWidth,
					rawHeight, pix, 0, rawWidth);

			if (pgObj.grabPixels()
					&& ((pgObj.getStatus() & ImageObserver.ALLBITS) != 0)) {
				for (int cnt = 0; cnt < (rawWidth * rawHeight); cnt++) {
					pix[cnt] = pix[cnt] & 0xC000FFFF;
				}
			} else
				System.out.println("Pixel grab not successful");
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		



		modImage = this.createImage(new MemoryImageSource(rawWidth, rawHeight, pix, 0, rawWidth));
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void paint(Graphics g) {
		if (modImage != null) {
			g.drawImage(rawImage, inLeft, inTop, this);
			g.drawImage(modImage, inLeft, inTop + rawHeight, this);
		}
	}
}
