package com.toedter.e4.ui.workbench.renderers.swing.controls;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

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
			toolBar.setBounds(parent.getWidth() - BUTTON_SIZE * 2 - 7, 2, BUTTON_SIZE * 2 + 6, BUTTON_SIZE);
			tabbedPane.setBounds(0, 0, parent.getWidth(), parent.getHeight());
		}
	}

	private final JButton minButton = new JButton();
	private final JButton maxButton = new JButton();
	private final JButton restoreButton = new JButton();
	private final JToolBar toolBar = new JToolBar();
	private final JTabbedPane tabbedPane = new JTabbedPane();

	public CTabbedPane() {
		toolBar.setFloatable(false);
		toolBar.setBorder(BorderFactory.createEmptyBorder());
		ImageIcon maxIcon = new ImageIcon(getClass().getResource("max.png"));
		maxButton.setIcon(maxIcon);
		ImageIcon minIcon = new ImageIcon(getClass().getResource("min.png"));
		minButton.setIcon(minIcon);
		ImageIcon restoreIcon = new ImageIcon(getClass().getResource("restore.png"));
		restoreButton.setIcon(restoreIcon);
		toolBar.add(minButton);
		toolBar.add(maxButton);
		super.add(toolBar);
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
				toolBar.remove(maxButton);
				toolBar.add(restoreButton);
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
		restoreButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handler.run();
				toolBar.remove(restoreButton);
				toolBar.add(maxButton);
			}
		});
	}

	@Override
	public void setVisible(boolean isVisible) {
		super.setVisible(isVisible);
		tabbedPane.setVisible(isVisible);
		toolBar.setVisible(isVisible);
	}
}
