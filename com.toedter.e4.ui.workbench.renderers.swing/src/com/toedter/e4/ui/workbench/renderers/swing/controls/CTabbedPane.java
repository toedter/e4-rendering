package com.toedter.e4.ui.workbench.renderers.swing.controls;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class CTabbedPane extends JPanel {

	private class CTabbedPaneLayout implements LayoutManager {

		private final static int BUTTON_SIZE = 20;

		@Override
		public void addLayoutComponent(String name, Component comp) {
			System.out.println("CTabbedPane.CTabbedPaneLayout.addLayoutComponent()");
		}

		@Override
		public void removeLayoutComponent(Component comp) {
			System.out.println("CTabbedPane.CTabbedPaneLayout.removeLayoutComponent()");
		}

		@Override
		public Dimension preferredLayoutSize(Container parent) {
			return new Dimension(tabbedPane.getPreferredSize().width + 2 * BUTTON_SIZE,
					tabbedPane.getPreferredSize().height);
		}

		@Override
		public Dimension minimumLayoutSize(Container parent) {
			return new Dimension(tabbedPane.getMinimumSize().width + 3 * BUTTON_SIZE,
					tabbedPane.getMinimumSize().height);
		}

		@Override
		public void layoutContainer(Container parent) {
			maxButton.setBounds(parent.getWidth() - BUTTON_SIZE, 2, BUTTON_SIZE, BUTTON_SIZE);
			minButton.setBounds(parent.getWidth() - 2 * BUTTON_SIZE, 2, BUTTON_SIZE, BUTTON_SIZE);
			tabbedPane.setBounds(0, 0, parent.getWidth(), parent.getHeight());
		}
	}

	private final JButton minButton = new JButton();
	private final JButton maxButton = new JButton();
	private final JTabbedPane tabbedPane = new JTabbedPane();

	public CTabbedPane() {
		ImageIcon maxIcon = new ImageIcon(getClass().getResource("max.png"));
		maxButton.setIcon(maxIcon);
		ImageIcon minIcon = new ImageIcon(getClass().getResource("min.png"));
		minButton.setIcon(minIcon);
		super.add(maxButton);
		super.add(minButton);
		super.add(tabbedPane);
		setLayout(new CTabbedPaneLayout());
	}

	public int getTabCount() {
		return tabbedPane.getTabCount();
	}

	public void setTabComponentAt(int index, JLabel component) {
		tabbedPane.setTabComponentAt(index, component);
	}

	@Override
	public Component add(Component component) {
		return tabbedPane.add(component);
	}

	public void setMaximizeHandler(final Runnable handler) {
		maxButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handler.run();
			}
		});
	}

	public void setMinimizeHandler(final Runnable handler) {
		minButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handler.run();
			}
		});
	}

	public void setRestoreHandler(final Runnable handler) {
	}

	@Override
	public void setVisible(boolean isVisible) {
		System.out.println("CTabbedPane.setVisible()");
		super.setVisible(isVisible);
		tabbedPane.setVisible(isVisible);
		minButton.setVisible(isVisible);
		maxButton.setVisible(isVisible);
	}
}
