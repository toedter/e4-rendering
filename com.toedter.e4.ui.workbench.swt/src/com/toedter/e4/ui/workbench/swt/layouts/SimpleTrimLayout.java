package com.toedter.e4.ui.workbench.swt.layouts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public class SimpleTrimLayout extends Layout {

	public static enum Position {
		TOP, LEFT, CENTER, RIGHT, BOTTOM
	};

	public final static Position TOP = Position.TOP;
	public final static Position LEFT = Position.LEFT;
	public final static Position CENTER = Position.CENTER;
	public final static Position RIGHT = Position.RIGHT;
	public final static Position BOTTOM = Position.BOTTOM;

	static class Size {
		public int width;
		public int height;

		public Size() {
			reset();
		}

		public Size(int width, int height) {
			this.width = width;
			this.height = height;
		}

		public Size(Point point) {
			width = point.x;
			height = point.y;
		}

		public void reset() {
			width = 0;
			height = 0;
		}

		@Override
		public String toString() {
			return "(" + width + "," + height + ")";
		}
	};

	private final Control[] controls = new Control[5];
	private final Size[] sizes;

	public SimpleTrimLayout() {
		sizes = new Size[5];
		for (int i = 0; i < 5; i++) {
			sizes[i] = new Size();
		}

	}

	@Override
	protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
		Size size = new Size();
		Control[] children = composite.getChildren();
		for (int i = 0; i < 5; i++) {
			sizes[i].reset();
		}

		for (Control child : children) {
			Object layoutData = child.getLayoutData();
			if (layoutData instanceof Position) {
				int index = ((Position) layoutData).ordinal();
				controls[index] = child;
				sizes[index] = new Size(child.computeSize(wHint, hHint));
			}
		}

		if (wHint != SWT.DEFAULT) {
			size.width = wHint;
		} else {
			size.width = Math.max(getWidth(TOP),
					Math.max(getWidth(BOTTOM), Math.max(getWidth(LEFT), Math.max(getWidth(CENTER), getWidth(BOTTOM)))));
		}
		if (hHint != SWT.DEFAULT) {
			size.height = hHint;
		} else {
			size.height = getHeight(TOP) + Math.max(getHeight(LEFT), Math.max(getHeight(CENTER), getHeight(RIGHT)))
					+ getHeight(BOTTOM);
		}
		return new Point(size.width, size.height);
	}

	@Override
	protected void layout(Composite composite, boolean flushCache) {
		Rectangle clientArea = composite.getClientArea();
		Control[] children = composite.getChildren();

		for (Control child : children) {
			try {
				switch ((Position) child.getLayoutData()) {
				case TOP:
					child.setBounds(clientArea.x, clientArea.y, clientArea.width, getHeight(TOP));
					break;

				case LEFT:
					child.setBounds(clientArea.x, clientArea.y + getHeight(TOP), getWidth(LEFT), clientArea.height
							- getHeight(TOP) - getHeight(BOTTOM));
					break;
				case RIGHT:
					child.setBounds(clientArea.width - getWidth(RIGHT), clientArea.y + getHeight(TOP), getWidth(RIGHT),
							clientArea.height - getHeight(TOP) - getHeight(BOTTOM));
					break;
				case BOTTOM:
					child.setBounds(clientArea.x, clientArea.y + clientArea.height - getHeight(BOTTOM),
							clientArea.width, getHeight(BOTTOM));
					break;
				case CENTER:
					child.setBounds(clientArea.x + getWidth(LEFT), clientArea.y + getHeight(TOP), clientArea.width
							- getWidth(LEFT) - getWidth(RIGHT), clientArea.height - getHeight(TOP) - getHeight(BOTTOM));
					break;

				default:
					break;
				}
			} catch (Exception e) {
				System.err
						.println("Children of a composite with SimpleTrimLayout need layout data like SimpleTrimLayout.TOP, .LEFT, .CENTER, .RIGHT, or .BOTTOM");
			}
		}
	}

	final private int getWidth(Position pos) {
		return sizes[pos.ordinal()].width;
	}

	final private int getHeight(Position pos) {
		return sizes[pos.ordinal()].height;
	}

}
