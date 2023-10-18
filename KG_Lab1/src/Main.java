import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


class Cmyk {
	public double c;
	public double m;
	public double y;
	public double k;
	Cmyk(double c, double m, double y, double k) {
		this.c = c;
		this.m = m;
		this.y = y;
		this.k = k;
	}
	
	Color toRgb() {
		return new Color((int) (255  * (1 - c) * (1- k)),
				(int) (255 * (1 - m) * (1 - k)),
				(int) (255 * (1 - y) * (1 - k))
				);
		
	}
}


class Hls {
	
	public double h;
	public double l;
	public double s;
	
	Hls(double h, double l, double s) {
		this.h = h;
		this.l = l;
		this.s = s;
	}
	
	Color toRgb() {
		double q;
		if (l < 0.5) {
			q = l * (1 + s);
		}
		else {
			q = l + s - l * s;
		}
		double p = 2 * l - q;
		double hk = h / 360;
		double tr  = hk + 1.0 / 3;
		double tg = hk;
		double tb = hk - 1.0 / 3;
		if (tr < 0) {
			tr++;
		}
		if (tr > 1) {
			tr--;
		}
		if (tg < 0) {
			tg++;
		}
		if (tg > 1) {
			tg--;
		}
		if (tb < 0) {
			tb++;
		}
		if (tb > 1) {
			tb--;
		}
		
		double r;
		double g;
		double b;
		if (tr < 1.0 / 6) {
			r = (p + (q - p) * 0.6 * tr);
		}
		else if (tr < 1.0 / 2) {
			r = q;
		}
		else if (tr < 2.0 / 3) {
			r = p + (q - p) * (2.0 / 3 - tr) * 6;
		}
		else  {
			r = p;
		}
		
		
		
		if (tg < 1.0 / 6) {
			g = (p + (q - p) * 0.6 * tg);
		}
		else if (tg < 1.0 / 2) {
			g = q;
		}
		else if (tg < 2.0 / 3) {
			g = p + (q - p) * (2.0 / 3 - tg) * 6;
		}
		else  {
			g = p;
		}
		
		
		
		if (tb < 1.0 / 6) {
			b = (p + (q - p) * 0.6 * tb);
		}
		else if (tb < 1.0 / 2) {
			b = q;
		}
		else if (tr < 2.0 / 3) {
			b = p + (q - p) * (2.0 / 3 -tb) * 6;
		}
		else  {
			b = p;
		}
		
		return new Color((int) (255 * r), (int) (255 * g), (int) (255 * b));
	}
}

class MyFrame extends JFrame {
	Color colorRgb;
	Cmyk colorCmyk;
	Hls colorHls;
	JSlider sliderC;
	JSlider sliderM;
	JSlider sliderY;
	JSlider sliderK;
	JSlider sliderR;
	JSlider sliderG;
	JSlider sliderB;
	JSlider sliderH;
	JSlider sliderL;
	JSlider sliderS;
	
	MyFrame() {
		super();
		this.setLayout(null);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) (3 * screenSize.getWidth() / 4);
		int height = (int) (3 * screenSize.getHeight() / 4);
		this.setMinimumSize(new Dimension(width, height));
		this.setLayout(new GridLayout(2, 1));
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		colorRgb = new Color(255, 255, 255);
		colorCmyk = toCmyk(colorRgb);
		colorHls = toHls(colorRgb);
		JLabel colorLabel = new JLabel();
		colorLabel.setOpaque(true);
		colorLabel.setBackground(colorRgb);
		

		JColorChooser chooser = new JColorChooser();

		
		JButton buttonChooser = new JButton("Choose");
		buttonChooser.addActionListener((ActionEvent e) -> {
			colorRgb = chooser.showDialog(this, "Choose a color", colorRgb);
			colorLabel.setBackground(colorRgb);
		});
		
		this.add(buttonChooser);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3, width / 12, 0));
		panel.setBounds(0, 0, width, height / 2);
		
		
		
		
		JPanel cmyk = new JPanel();
		cmyk.setLayout(new GridLayout(8, 2));
		JLabel labelC = new JLabel("C:");
		cmyk.add(labelC);
		cmyk.add(new JPanel());
		sliderC = new JSlider();		
		sliderC.setMinimum(0);
		sliderC.setMaximum(100);
		sliderC.setMajorTickSpacing(20);
		sliderC.setMinorTickSpacing(5);
		sliderC.setPaintTicks(true);
		sliderC.setPaintLabels(true);
		sliderC.setValue((int)(100 * colorCmyk.c));
		cmyk.add(sliderC);
		
		sliderC.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				colorCmyk.c = sliderC.getValue() / 100.0;
				colorRgb = colorCmyk.toRgb();
				colorHls = toHls(colorRgb);
				setSliders();
				colorLabel.setBackground(colorRgb);
			}
		});
		
		JTextField fieldC = new JTextField();
		fieldC.setText((int)(100 * colorCmyk.c) + "");
		cmyk.add(fieldC);
		
		JLabel labelM = new JLabel("M:");
		cmyk.add(labelM);
		cmyk.add(new JPanel());
		sliderM = new JSlider();
		sliderM.setMinimum(0);
		sliderM.setMaximum(100);
		sliderM.setMajorTickSpacing(20);
		sliderM.setMinorTickSpacing(5);
		sliderM.setPaintTicks(true);
		sliderM.setPaintLabels(true);
		sliderM.setValue((int)(100 * colorCmyk.m));
		cmyk.add(sliderM);
		
		sliderM.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				colorCmyk.m = sliderM.getValue() / 100.0;
				colorRgb = colorCmyk.toRgb();
				colorHls = toHls(colorRgb);
				setSliders();
				colorLabel.setBackground(colorRgb);
			}
		});
		JTextField fieldM = new JTextField();
		fieldM.setText((int)(100 * colorCmyk.m) + "");
		cmyk.add(fieldM);
		
		JLabel labelY = new JLabel("Y:");
		cmyk.add(labelY);
		cmyk.add(new JPanel());
		sliderY = new JSlider();
		sliderY.setMinimum(0);
		sliderY.setMaximum(100);
		sliderY.setMajorTickSpacing(20);
		sliderY.setMinorTickSpacing(5);
		sliderY.setPaintTicks(true);
		sliderY.setPaintLabels(true);
		sliderY.setValue((int)(100 * colorCmyk.y));
		cmyk.add(sliderY);
		JTextField fieldY = new JTextField();
		fieldY.setText((int)(100 * colorCmyk.y) + "");
		cmyk.add(fieldY);
		
		sliderY.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				colorCmyk.y = sliderY.getValue() / 100.0;
				colorRgb = colorCmyk.toRgb();
				colorHls = toHls(colorRgb);
				setSliders();
				colorLabel.setBackground(colorRgb);
			}
		});
		
		JLabel labelK = new JLabel("K:");
		cmyk.add(labelK);
		cmyk.add(new JPanel());
		sliderK = new JSlider();
		sliderK.setMinimum(0);
		sliderK.setMaximum(100);
		sliderK.setMajorTickSpacing(20);
		sliderK.setMinorTickSpacing(5);
		sliderK.setPaintTicks(true);
		sliderK.setPaintLabels(true);
		sliderK.setValue((int)(100 * colorCmyk.k));
		cmyk.add(sliderK);
		
		sliderK.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				colorCmyk.k = sliderK.getValue() / 100.0;
				colorRgb = colorCmyk.toRgb();
				colorHls = toHls(colorRgb);
				setSliders();
				colorLabel.setBackground(colorRgb);
			}
		});
		
		JTextField fieldK = new JTextField();
		fieldK.setText((int)(100 * colorCmyk.k) + "");
		cmyk.add(fieldK);
		this.add(cmyk);
		
		
		
		
		
		
		JPanel rgb = new JPanel();
		rgb.setLayout(new GridLayout(8, 2));
		JLabel labelR = new JLabel("R:");
		rgb.add(labelR);
		rgb.add(new JPanel());
		sliderR = new JSlider();
		sliderR.setMinimum(0);
		sliderR.setMaximum(255);
		sliderR.setMajorTickSpacing(255);
		sliderR.setMinorTickSpacing(255);
		sliderR.setPaintTicks(true);
		sliderR.setPaintLabels(true);
		sliderR.setValue((int)(colorRgb.getRed()));
		rgb.add(sliderR);
		sliderR.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				colorRgb = new Color(sliderR.getValue(), colorRgb.getGreen(),
						colorRgb.getBlue());
				colorCmyk = toCmyk(colorRgb);
				colorHls = toHls(colorRgb);
				setSliders();
				colorLabel.setBackground(colorRgb);
			}
		});
		JTextField fieldR = new JTextField();
		fieldR.setText(colorRgb.getRed() + "");
		rgb.add(fieldR);
		
		JLabel labelG = new JLabel("G:");
		rgb.add(labelG);
		rgb.add(new JPanel());
		sliderG = new JSlider();
		sliderG.setMinimum(0);
		sliderG.setMaximum(255);
		sliderG.setMajorTickSpacing(255);
		sliderG.setMinorTickSpacing(255);
		sliderG.setPaintTicks(true);
		sliderG.setPaintLabels(true);
		sliderG.setValue((int)(colorRgb.getGreen()));
		rgb.add(sliderG);
		sliderG.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				colorRgb = new Color(colorRgb.getRed(),sliderG.getValue(),
						colorRgb.getBlue());
				colorCmyk = toCmyk(colorRgb);
				colorHls = toHls(colorRgb);
				setSliders();
				colorLabel.setBackground(colorRgb);
			}
		});
		JTextField fieldG = new JTextField();
		fieldG.setText(colorRgb.getGreen() + "");
		rgb.add(fieldG);
		
		JLabel labelB = new JLabel("B:");
		rgb.add(labelB);
		rgb.add(new JPanel());
		sliderB = new JSlider();
		sliderB.setMinimum(0);
		sliderB.setMaximum(255);
		sliderB.setMajorTickSpacing(255);
		sliderB.setMinorTickSpacing(255);
		sliderB.setPaintTicks(true);
		sliderB.setPaintLabels(true);
		sliderB.setValue((int)(colorRgb.getBlue()));
		rgb.add(sliderB);
		sliderB.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				colorRgb = new Color(colorRgb.getRed(), colorRgb.getGreen(),
						sliderB.getValue());
				colorCmyk = toCmyk(colorRgb);
				colorHls = toHls(colorRgb);
				setSliders();
				colorLabel.setBackground(colorRgb);
			}
		});
		JTextField fieldB = new JTextField();
		fieldB.setText(colorRgb.getBlue() + "");
		rgb.add(fieldB);
		
		rgb.add(new JPanel());
		rgb.add(new JPanel());
		rgb.add(buttonChooser);
		
		
		
		
		
		
		JPanel hls = new JPanel();
		hls.setLayout(new GridLayout(8, 2));
		JLabel labelH = new JLabel("H:");
		hls.add(labelH);
		hls.add(new JPanel());
		sliderH = new JSlider();
		sliderH.setMinimum(0);
		sliderH.setMaximum(359);
		sliderH.setMajorTickSpacing(359);
		sliderH.setMinorTickSpacing(359);
		sliderH.setPaintTicks(true);
		sliderH.setPaintLabels(true);
		hls.add(sliderH); 
		
		JTextField fieldH = new JTextField();
		if (colorHls.h < 0) {
			sliderH.setValue(0);
			fieldH.setText("Undefined");
		}
		else {
			sliderH.setValue((int) (359 * colorHls.h));
			fieldH.setText((int) (359 * colorHls.h) + "");
		}
		hls.add(fieldH);
		
		JLabel labelL = new JLabel("L:");
		hls.add(labelL);
		hls.add(new JPanel());
		sliderL = new JSlider();
		sliderL.setMinimum(0);
		sliderL.setMaximum(100);
		sliderL.setMajorTickSpacing(20);
		sliderL.setMinorTickSpacing(5);
		sliderL.setPaintTicks(true);
		sliderL.setPaintLabels(true);
		hls.add(sliderL);

		JTextField fieldL = new JTextField();
		sliderL.setValue((int) (100 * colorHls.l));
		fieldL.setText((int) (100 * colorHls.l) + "");
		hls.add(fieldL);
		
		JLabel labelS = new JLabel("S:");
		hls.add(labelS);
		hls.add(new JPanel());
		sliderS = new JSlider();
		sliderS.setMinimum(0);
		sliderS.setMaximum(100);
		sliderS.setMajorTickSpacing(20);
		sliderS.setMinorTickSpacing(5);
		sliderS.setPaintTicks(true);
		sliderS.setPaintLabels(true);
		hls.add(sliderS);
		JTextField fieldS = new JTextField();
		sliderS.setValue((int) (100 * colorHls.s));
		fieldS.setText((int) (100 * colorHls.s) + "");
		hls.add(fieldS);
		
		hls.add(new JPanel());
		hls.add(new JPanel());
		hls.add(new JPanel());
		hls.add(new JPanel());
		
		
		panel.add(cmyk);
		panel.add(rgb);
		panel.add(hls);
		this.add(panel);
		this.add(colorLabel);
	}
	
	Cmyk toCmyk(Color color) {
		double k = Math.min(Math.min(1 - color.getRed() / 255.0, 
				1 - color.getGreen() / 255.0),
				Math.min(1 - color.getBlue() / 255.0, 0.99));
		double c = (1 - color.getRed() / 255.0 - k) / (1 - k);
		double m = (1 - color.getGreen() / 255.0 - k) / (1 - k);
		double y = (1 - color.getBlue() / 255.0 - k) / (1 - k);
		return new Cmyk(c, m, y, k);
	}
	
	Hls toHls(Color color) {
		double r = color.getRed();
		double g = color.getGreen();
		double b = color.getBlue();

		double h = 0;
		double l = 0; 
		double s = 0;
		r /= 255;
		g /= 255;
		b /= 255;
		double max = Math.max(Math.max(r,  g), b);
		double min = Math.min(Math.min(r,  g), b);
		
		if (max == min) {
			h = -1;
		}
		else if (max == r && g >= b) {
			h = (g - b) * 60 / (max - min);
		}
		else if (max == r && g < b) {
			h = (g - b) * 60 / (max - min)  + 360;
		}
		else if (max == g) {
			h = (b - r) * 60 / (max - min) + 120;
		}
		else if (max == b) {
			h = (r - g) * 60 / (max - min) + 240;
		}
		s = (max - min) / (1 - Math.abs(1 - (max + min)));
		l = (max + min) / 2;
		return new Hls(h, s, l);
	}
	
	void setSliders() {

		sliderC.setValue((int) (100 * colorCmyk.c));
		sliderM.setValue((int) (100 * colorCmyk.m));
		sliderY.setValue((int) (100 * colorCmyk.y));
		sliderK.setValue((int) (100 * colorCmyk.k));

		
		

		sliderR.setValue(colorRgb.getRed());
		sliderG.setValue(colorRgb.getGreen());
		sliderB.setValue(colorRgb.getBlue());


		
		
		if (colorHls.h < 0) {
			sliderH.setValue(0);
		}
		else {
			sliderH.setValue((int) colorHls.h);
		}
		sliderL.setValue((int) (100 * colorHls.l));
		sliderS.setValue((int) (100 * colorHls.s));


	}
}



public class Main {
	public static void main(String[] srgs) {
		MyFrame frame = new MyFrame();
		frame.setVisible(true);
	}
}
