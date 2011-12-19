package com.toedter.e4.ui.workbench.javafx;

import com.toedter.e4.ui.workbench.generic.GenericE4Application;

public class JavaFXE4Application extends GenericE4Application {
	public JavaFXE4Application() {
		presentationEngineURI = "platform:/plugin/com.toedter.e4.ui.workbench.javafx/"
				+ "com.toedter.e4.ui.workbench.javafx.JavaFXPresentationEngine";
	}
}