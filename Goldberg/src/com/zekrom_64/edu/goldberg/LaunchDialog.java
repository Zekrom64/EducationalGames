package com.zekrom_64.edu.goldberg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class LaunchDialog extends JFrame implements ActionListener {

	public boolean doLaunch = false;
	public final Object dialogWaitObj = new Object();
	private JPanel contentPane;
	private JSpinner spnWidth;
	private JSpinner spnHeight;
	private JSpinner spnFPSLimit;

	/**
	 * Create the frame.
	 */
	public LaunchDialog() {
		setResizable(false);
		setTitle("Goldberg Launcher");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panelWindowOptions = new JPanel();
		panelWindowOptions.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JButton btnLaunch = new JButton("Launch!");
		btnLaunch.setActionCommand("Launch");
		btnLaunch.addActionListener(this);
		
		JLabel lblWindowOptions = new JLabel("Window Options");
		
		JLabel lblWindowSize = new JLabel("Window Size:");
		
		spnWidth = new JSpinner();
		spnWidth.setModel(new SpinnerNumberModel(new Integer(800), new Integer(1), null, new Integer(1)));
		
		JLabel lblX = new JLabel("x");
		
		spnHeight = new JSpinner();
		spnHeight.setModel(new SpinnerNumberModel(new Integer(600), new Integer(1), null, new Integer(1)));
		
		JLabel lblFpsLimit = new JLabel("FPS Limit:");
		
		spnFPSLimit = new JSpinner();
		spnFPSLimit.setModel(new SpinnerNumberModel(new Integer(60), new Integer(1), null, new Integer(1)));
		SpringLayout sl_panelWindowOptions = new SpringLayout();
		sl_panelWindowOptions.putConstraint(SpringLayout.WEST, spnFPSLimit, 10, SpringLayout.EAST, lblFpsLimit);
		sl_panelWindowOptions.putConstraint(SpringLayout.EAST, spnFPSLimit, 60, SpringLayout.EAST, lblFpsLimit);
		sl_panelWindowOptions.putConstraint(SpringLayout.WEST, spnHeight, 10, SpringLayout.EAST, lblX);
		sl_panelWindowOptions.putConstraint(SpringLayout.EAST, spnHeight, 70, SpringLayout.EAST, lblX);
		sl_panelWindowOptions.putConstraint(SpringLayout.EAST, lblX, 20, SpringLayout.EAST, spnWidth);
		sl_panelWindowOptions.putConstraint(SpringLayout.WEST, spnWidth, 10, SpringLayout.EAST, lblWindowSize);
		sl_panelWindowOptions.putConstraint(SpringLayout.EAST, spnWidth, 70, SpringLayout.EAST, lblWindowSize);
		sl_panelWindowOptions.putConstraint(SpringLayout.WEST, lblX, 10, SpringLayout.EAST, spnWidth);
		sl_panelWindowOptions.putConstraint(SpringLayout.NORTH, spnFPSLimit, 32, SpringLayout.NORTH, panelWindowOptions);
		sl_panelWindowOptions.putConstraint(SpringLayout.NORTH, lblFpsLimit, 35, SpringLayout.NORTH, panelWindowOptions);
		sl_panelWindowOptions.putConstraint(SpringLayout.WEST, lblFpsLimit, 12, SpringLayout.WEST, panelWindowOptions);
		sl_panelWindowOptions.putConstraint(SpringLayout.NORTH, spnHeight, 7, SpringLayout.NORTH, panelWindowOptions);
		sl_panelWindowOptions.putConstraint(SpringLayout.NORTH, lblX, 10, SpringLayout.NORTH, panelWindowOptions);
		sl_panelWindowOptions.putConstraint(SpringLayout.NORTH, spnWidth, 7, SpringLayout.NORTH, panelWindowOptions);
		sl_panelWindowOptions.putConstraint(SpringLayout.NORTH, lblWindowSize, 10, SpringLayout.NORTH, panelWindowOptions);
		sl_panelWindowOptions.putConstraint(SpringLayout.WEST, lblWindowSize, 12, SpringLayout.WEST, panelWindowOptions);
		panelWindowOptions.setLayout(sl_panelWindowOptions);
		panelWindowOptions.add(lblWindowSize);
		panelWindowOptions.add(spnWidth);
		panelWindowOptions.add(lblX);
		panelWindowOptions.add(spnHeight);
		panelWindowOptions.add(lblFpsLimit);
		panelWindowOptions.add(spnFPSLimit);
		SpringLayout sl_contentPane = new SpringLayout();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panelWindowOptions, 36, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panelWindowOptions, 15, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panelWindowOptions, 96, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panelWindowOptions, 285, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblWindowOptions, 16, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblWindowOptions, 15, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnLaunch, 232, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnLaunch, 358, SpringLayout.WEST, contentPane);
		contentPane.setLayout(sl_contentPane);
		contentPane.add(btnLaunch);
		contentPane.add(lblWindowOptions);
		contentPane.add(panelWindowOptions);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Launch")) {
			Config.setConfig("window.width", spnWidth.getValue());
			Config.setConfig("window.height", spnHeight.getValue());
			Config.setConfig("render.fpslimit", spnFPSLimit.getValue());
			setVisible(false);
			dispose();
			doLaunch = true;
			synchronized(dialogWaitObj) {
				dialogWaitObj.notifyAll();
			}
		}
	}
}
