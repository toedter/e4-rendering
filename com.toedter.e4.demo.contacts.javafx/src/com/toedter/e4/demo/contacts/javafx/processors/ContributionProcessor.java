package com.toedter.e4.demo.contacts.javafx.processors;

import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.commands.MHandler;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

@SuppressWarnings("restriction")
public class ContributionProcessor {

	@Inject
	@Named("part:detailsView")
	private MPart detailsView;

	@Inject
	@Named("part:listView")
	private MPart listView;

	@Inject
	@Named("handler:switchTheme")
	private MHandler switchThemeHandler;

	@Inject
	@Named("handler:exit")
	private MHandler exitHandler;

	@Execute
	public void process() {
		listView.setContributionURI("platform:/plugin/com.toedter.e4.demo.contacts.javafx/com.toedter.e4.demo.contacts.javafx.views.ListView");
		detailsView
				.setContributionURI("platform:/plugin/com.toedter.e4.demo.contacts.javafx/com.toedter.e4.demo.contacts.javafx.views.DetailsView");
		switchThemeHandler
				.setContributionURI("platform:/plugin/com.toedter.e4.demo.contacts.javafx/com.toedter.e4.demo.contacts.javafx.handlers.SwitchThemeHandler");
		exitHandler
				.setContributionURI("platform:/plugin/com.toedter.e4.demo.contacts.javafx/com.toedter.e4.demo.contacts.javafx.handlers.ExitHandler");
	}
}
