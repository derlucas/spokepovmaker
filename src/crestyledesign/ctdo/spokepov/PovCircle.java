package crestyledesign.ctdo.spokepov;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class PovCircle extends JPanel {
	private static final long serialVersionUID = -5560303330892137973L;
	private int ledCount = 16;
	private int padding = 20;
	private int angularSteps = 40;
	private int angularStepsMax = 80;
	private int middleRadiusMax = 25;
	private int middleRadius = 20;
	private DotDataEntry[][] myData = null;
	private Color drawColor = Color.white;
	private Image imgBackground;

	public PovCircle() {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					setPixelByCoordinates(e.getX(), e.getY(), drawColor);
					PovCircle.this.repaint();
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setPixelByCoordinates(e.getX(), e.getY(), drawColor);
				PovCircle.this.repaint();
			}
		});
		
		imgBackground = Toolkit.getDefaultToolkit().getImage("/home/lucas/home/bilder/100_0466.geaendert.JPG");
		
		this.setForeground(Color.white);
		clearPoints();
	}

	public void clearPoints() {
		this.myData = new DotDataEntry[angularSteps][ledCount];
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		Dimension size = getSize();

		g.setColor(Color.black);
		g.fillRect(0, 0, size.width, size.height);
		
		if(imgBackground != null) {
			g.drawImage(imgBackground, padding, padding, size.width-padding*2, size.height-padding*2, null);
		}
		
		paintGrid(g);
		drawElementsInGrid(g);
	}
	
	private void drawElementsInGrid(Graphics g) {
		Dimension size = getSize();
        int outerRadius = Math.min(size.width - padding, size.height - padding); 
        int innerRadius = (int)(outerRadius * (middleRadius/100.0));
        int ledHeight = (outerRadius-innerRadius*2) / (ledCount * 2);
        double angle = 0d;
		double stepDegree = 2.0 * Math.PI / angularSteps;		
		
		int centerX = size.width / 2;
        int centerY = size.height / 2;
    	
    	for (int i = 0; i < angularSteps; i++) {
    		double radius = innerRadius + (ledHeight/2.0);
    		
			for (int j = 0; j < ledCount; j++) {
				if(myData[i][j] != null) {
					g.setColor(myData[i][j].getColor());
					g.fillOval(centerX+(int)(Math.cos(angle+stepDegree/2)*radius)-ledHeight/2,
							   centerY+(int)(Math.sin(angle+stepDegree/2)*radius)-ledHeight/2, ledHeight, ledHeight);
				}
				radius+=ledHeight;
			}
			angle += stepDegree;
		}
	}

	private void paintGrid(Graphics g) {
		Dimension size = getSize();
        int outerRadius = Math.min(size.width - padding, size.height - padding); 
        int innerRadius = (int)(outerRadius * (middleRadius/100.0));
        int radius = innerRadius;        

        double angle = 0d;
		double stepDegree = 2.0 * Math.PI / angularSteps;
		
		int ledHeight = (outerRadius-innerRadius*2) / (ledCount * 2);
		

		g.setColor(new Color(0,90,0));
		
        for (int i = 0; i <= ledCount; i++)
        {
        	int radiustemp = radius * 2;
        	g.drawOval((size.width - radiustemp) / 2, (size.height - radiustemp) / 2, radiustemp, radiustemp);
            radius += ledHeight;
        }

        radius -= ledHeight;
        
        int centerX = size.width / 2;
        int centerY = size.height / 2;
        
        for (int i = 0; i < angularSteps; i++)
        {
            double mCos = Math.cos(angle);
            double mSin = Math.sin(angle);
            
            if(i == 0) g.setColor(Color.blue);
            else if(i == 1) g.setColor(new Color(0,200,0));
            else if(i == 2) g.setColor(new Color(0,150,0));
            else g.setColor(new Color(0,90,0));
            
            g.drawLine((int)(centerX + mCos * innerRadius), (int)(centerY + mSin * innerRadius),
            		   (int)(centerX + mCos * radius), (int)(centerY + mSin * radius));
            
            angle += stepDegree;
        }

        // a circle as wheel
        g.setColor(new Color(185,0,0));
		g.drawOval((size.width - outerRadius) / 2, (size.height - outerRadius) / 2, outerRadius, outerRadius);		
	}

	private void setPixelByCoordinates(int x, int y, Color col) {
		Dimension size = getSize();
		int outerRadius = Math.min(size.width - padding, size.height - padding); 
        int innerRadius = (int)(outerRadius * (middleRadius/100.0));
        int ledHeight = (outerRadius-innerRadius*2) / (ledCount * 2);
    	double realOuter = ledCount*ledHeight;
        double angle = 0d;
        int cordX = x - size.width / 2;
        int cordY = y - size.height / 2;
        
        if (cordX >= 0 && cordY >= 0)
            angle = Math.atan(cordY / (double)cordX);
        else if (cordX >= 0 && cordY <= 0)
            angle = Math.atan(cordY / (double)cordX) + 2.0 * Math.PI;
        else
            angle = Math.atan(cordY / (double)cordX) + Math.PI;

        double radius = Math.sqrt(Math.pow(cordX, 2) + Math.pow(cordY, 2))-innerRadius;
        
        if(radius >= 0 && radius <= realOuter) {
        	int elemX = (int)(ledCount * ( radius/(double)(realOuter) ));
            int elemA = (int)(angularSteps * (angle / (2.0 * Math.PI)));
        	
//            System.out.println("elemA=" + elemA + " elemX=" + elemX);
            
            if(elemA >= 0 && elemA < angularSteps && elemX >= 0 && elemX < ledCount) {
            	if(this.drawColor != null)
            		myData[elemA][elemX] = new DotDataEntry(drawColor);
            	else 
            		myData[elemA][elemX] = null;
            }
        }
	}

	
	
	
	public boolean loadFile(String filename) {
		FileDataObject fdo = new FileDataObject();
		boolean retVal = PersistManager.loadFile(filename, fdo);
		if(retVal) {
			this.angularSteps = fdo.getAngularSteps();
			this.ledCount = fdo.getLedCount();
			this.myData = fdo.getData();
			fdo = null;
			this.repaint();
		}
		return retVal;
	}
	
	public boolean saveFile(String filename) {
		FileDataObject fdo = new FileDataObject();
		fdo.setAngularSteps(this.angularSteps);
		fdo.setLedCount(this.ledCount);
		fdo.setData(this.myData);
		boolean retVal = PersistManager.saveFile(filename, fdo);
		fdo = null;
		return retVal;
	}
	
	public boolean saveDataFile(String filename) {
		FileDataObject fdo = new FileDataObject();
		fdo.setAngularSteps(this.angularSteps);
		fdo.setLedCount(this.ledCount);
		fdo.setData(this.myData);
		boolean retVal = PersistManager.saveCHeaderFile(filename, fdo);
		fdo = null;
		return retVal;
	}
	
	/* ******************************************************************
	 * getter and setter
	 */
	public void setAngularSteps(int angularSteps) {
		if(angularSteps > 0 && angularSteps <= angularStepsMax) {
			this.angularSteps = angularSteps;
			clearPoints();
		}
	}
	public void setMiddleRadius(int middleRadius) {
		if(middleRadius >= 0 && middleRadius <= middleRadiusMax) {
			this.middleRadius = middleRadius;
			clearPoints();
		}
	}
	public int getAngularSteps() {
		return angularSteps;
	}
	public int getMiddleRadius() {
		return middleRadius;
	}
	public Color getDrawColor() {
		return drawColor;
	}
	public void setDrawColor(Color drawColor) {
		this.drawColor = drawColor;
	}
	
}
