package com.toedter.e4.ui.workbench.renderers.swt;

import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;

public class Util {
	public static URL convertToOSGiURL(URI uri) {
		Bundle b = org.eclipse.core.runtime.Platform.getBundle(uri.segment(1));
		StringBuilder bundlePath = new StringBuilder();
		for( int i = 2; i < uri.segmentCount(); i++ ) {
			if( bundlePath.length() != 0 ) {
				bundlePath.append("/");
			}
			bundlePath.append(uri.segment(i));
		}
		return b.getResource(bundlePath.toString());
	}
}
