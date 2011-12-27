package com.toedter.e4.ui.workbench.renderers.swt;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;

@SuppressWarnings("restriction")
public class SWTRenderer extends GenericRenderer {

	protected Map<String, Image> imageMap = new HashMap<String, Image>();

	@PostConstruct
	public void init() {
		Display.getCurrent().disposeExec(new Runnable() {
			@Override
			public void run() {
				for (Image image : imageMap.values()) {
					image.dispose();
				}
			}
		});
	}

	protected Image getImage(MUILabel element) {
		String iconURI = element.getIconURI();
		if (iconURI != null && iconURI.length() > 0) {
			Image image = imageMap.get(iconURI);
			if (image == null) {
				image = imageDescriptorFromURI(URI.createURI(iconURI)).createImage();
				imageMap.put(iconURI, image);
			}
			return image;
		}
		return null;
	}

	public ImageDescriptor getImageDescriptor(MUILabel element) {
		String iconURI = element.getIconURI();
		if (iconURI != null && iconURI.length() > 0) {
			return imageDescriptorFromURI(URI.createURI(iconURI));
		}
		return null;
	}

	public ImageDescriptor imageDescriptorFromURI(URI iconPath) {
		try {
			return ImageDescriptor.createFromURL(new URL(iconPath.toString()));
		} catch (MalformedURLException e) {
			System.err.println("iconURI \"" + iconPath.toString()
					+ "\" is invalid, a \"missing image\" icon will be shown");
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}

}
