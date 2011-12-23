/*******************************************************************************
 * Copyright (c) 2011 Kai Toedter and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Kai Toedter - initial API and implementation
 ******************************************************************************/

package com.toedter.e4.ui.workbench.renderers.swt;

import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;

public class Util {
	public static URL convertToOSGiURL(URI uri) {
		Bundle b = org.eclipse.core.runtime.Platform.getBundle(uri.segment(1));
		StringBuilder bundlePath = new StringBuilder();
		for (int i = 2; i < uri.segmentCount(); i++) {
			if (bundlePath.length() != 0) {
				bundlePath.append("/");
			}
			bundlePath.append(uri.segment(i));
		}
		return b.getResource(bundlePath.toString());
	}
}
