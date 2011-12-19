package com.toedter.e4.ui.workbench.swt;

import com.toedter.e4.ui.workbench.generic.GenericE4Application;

public class SWTE4Application extends GenericE4Application {
	public SWTE4Application() {
		System.out.println("SWTE4Application.SwingE4Application()");
		presentationEngineURI = "platform:/plugin/com.toedter.e4.ui.workbench.swt/"
				+ "com.toedter.e4.ui.workbench.swt.SWTPresentationEngine";
	}
}