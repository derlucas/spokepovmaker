package crestyledesign.ctdo.spokepov;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import crestyledesign.ctdo.spokepov.filters.CHeaderFileFilter;
import crestyledesign.ctdo.spokepov.filters.DataFileFilter;

public class mainWindow implements ChangeListener {

	private JFrame frame;
	private PovCircle pc;
	private JColorChooser jcolor;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow window = new mainWindow();
					window.frame.setVisible(true);
					window.frame.setLocation(200, 100);
					window.frame.setSize(1000, 600);
					window.frame.setTitle("SpokePOV RGB maker | www.ctdo.de (c) xleave 2011");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public mainWindow() {
		initialize();
	}
	
	public void stateChanged(ChangeEvent e) {
        if(jcolor != null && pc != null) {
        	Color newColor = jcolor.getColor();
        	pc.setDrawColor(newColor);
        }
    }

	private void initialize() {
		pc = new PovCircle();
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jcolor = new JColorChooser(pc.getDrawColor());
		jcolor.setPreviewPanel(new JPanel());
		jcolor.getSelectionModel().addChangeListener(this);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Load");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new DataFileFilter());
				if( fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					boolean retVal = pc.loadFile(fc.getSelectedFile().getPath());
					if(!retVal) JOptionPane.showMessageDialog(frame, "#fail");
				}
			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new DataFileFilter());
				if( fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
					String filename = fc.getSelectedFile().getPath();
					if(!filename.endsWith(".ser")) filename += ".ser";
					boolean retVal = pc.saveFile(filename);
					if(!retVal) JOptionPane.showMessageDialog(frame, "#fail");
				}
			}
		});
		mnFile.add(mntmSave);
		
		JMenuItem mntmExport = new JMenuItem("Export");
		mntmExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new CHeaderFileFilter());
				if( fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
					String filename = fc.getSelectedFile().getPath();
					if(!filename.endsWith(".h")) filename += ".h";
					boolean retVal = pc.saveDataFile(filename);
					if(!retVal) JOptionPane.showMessageDialog(frame, "#fail");
				}
			}
		});
		mnFile.add(mntmExport);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmClearPoints = new JMenuItem("Clear points");
		mntmClearPoints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pc.clearPoints();
			}
		});
		mnEdit.add(mntmClearPoints);
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		JMenuItem mntmSetAngularSteps = new JMenuItem("Set angular steps");
		mntmSetAngularSteps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = JOptionPane.showInputDialog("please enter the angular steps count (1 to 80)", pc.getAngularSteps());
				if( str != null ) {
					try {
						int count = Integer.parseInt(str);
						pc.setAngularSteps(count);
					}
					catch (Exception exp) { }
				}
			}
		});
		mnOptions.add(mntmSetAngularSteps);
		
		JMenuItem mntmSetMiddleRadius = new JMenuItem("Set middle radius");
		mntmSetMiddleRadius.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = JOptionPane.showInputDialog("please enter the middle radius (in percent as integer)", pc.getMiddleRadius());
				if( str != null ) {
					try {
						int count = Integer.parseInt(str);
						pc.setMiddleRadius(count);
					}
					catch (Exception exp) { }
				}
			}
		});
		mnOptions.add(mntmSetMiddleRadius);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));				
		frame.setMinimumSize(new Dimension(600,600));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0,0));
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pc.setDrawColor(null);
			}
		});
		
		panel.add(btnRemove, BorderLayout.NORTH);
		
		frame.getContentPane().add(pc, BorderLayout.CENTER);
		panel.add(jcolor, BorderLayout.SOUTH);
		
	}

}
