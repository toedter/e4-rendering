package com.toedter.e4.ui.workbench.renderers.javafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;

import at.bestsolution.efxclipse.runtime.services.theme.Theme;
import at.bestsolution.efxclipse.runtime.services.theme.ThemeManager;

import com.toedter.e4.ui.workbench.generic.GenericRenderer;
import com.toedter.e4.ui.workbench.generic.IPresentationEngine2;

@SuppressWarnings("restriction")
public class WorkbenchWindowRenderer extends GenericRenderer {

	@Inject
	@Optional
	ThemeManager themeManager;

	private static class PosAndSizeListener implements ChangeListener<Number> {

		private final Stage stage;
		private final MWindow mWindow;

		public PosAndSizeListener(Stage stage, MWindow mWindow) {
			super();
			this.stage = stage;
			this.mWindow = mWindow;
		}

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			mWindow.setX(((Double) stage.getX()).intValue());
			mWindow.setY(((Double) stage.getY()).intValue());
			mWindow.setWidth(((Double) stage.getWidth()).intValue());
			mWindow.setHeight(((Double) stage.getHeight()).intValue());
		}

	}

	@Override
	public void createWidget(MUIElement element, MElementContainer<MUIElement> parent) {
		if (element instanceof MWindow) {
			final MWindow mWindow = (MWindow) element;
			Stage stage = new Stage();
			stage.setX(mWindow.getX());
			stage.setY(mWindow.getY());
			stage.setWidth(mWindow.getWidth());
			stage.setHeight(mWindow.getHeight());
			stage.setTitle(((MWindow) element).getLocalizedLabel());

			BorderPane root = new BorderPane();
			VBox topAreaBox = new VBox();
			root.setTop(topAreaBox);

			root.setStyle("-fx-background-color: #999;");
			Scene scene = new Scene(root, Integer.MAX_VALUE, Integer.MAX_VALUE);

			stage.setScene(scene);

			if (themeManager != null) {
				Theme theme = themeManager.getCurrentTheme();
				if (theme != null) {
					List<String> sUrls = new ArrayList<String>();
					for (URL url : theme.getStylesheetURL()) {
						sUrls.add(url.toExternalForm());
					}

					scene.getStylesheets().addAll(sUrls);
				}
				themeManager.registerScene(scene);
			}

			element.setWidget(stage);
		}
	}

	@Override
	public void processContents(MElementContainer<MUIElement> element) {
		if ((MUIElement) element instanceof MWindow) {
			Stage stage = (Stage) element.getWidget();
			BorderPane root = (BorderPane) stage.getScene().getRoot();
			VBox topBox = (VBox) root.getTop();
			IPresentationEngine2 renderer = (IPresentationEngine2) context.get(IPresentationEngine.class.getName());

			for (MUIElement child : element.getChildren()) {
				root.setCenter((Node) child.getWidget());
			}
			MWindow window = (MWindow) (MUIElement) element;

			if (window.getMainMenu() != null) {
				Node node = (Node) renderer.createGui(window.getMainMenu(), element);
				if (node != null) {
					topBox.getChildren().add(node);
				}
			}

			if (window instanceof MTrimmedWindow) {
				MTrimmedWindow tWindow = (MTrimmedWindow) window;
				for (MTrimBar trim : tWindow.getTrimBars()) {
					Node n = (Node) renderer.createGui(trim);
					if (n != null) {
						switch (trim.getSide()) {
						case BOTTOM:
							root.setBottom(n);
							break;
						case LEFT:
							root.setLeft(n);
							break;
						case RIGHT:
							root.setRight(n);
							break;
						case TOP:
							topBox.getChildren().add(n);
							break;
						}
					}
				}
			}

			// now the hole stage is created and we can show it
			stage.show();
		}
	}

	@Override
	public void hookControllerLogic(MUIElement element) {
		if (element instanceof MWindow) {
			MWindow window = (MWindow) element;
			Stage stage = (Stage) element.getWidget();
			ChangeListener<Number> resizeListener = new PosAndSizeListener(stage, window);
			stage.widthProperty().addListener(resizeListener);
			stage.heightProperty().addListener(resizeListener);
			stage.xProperty().addListener(resizeListener);
			stage.yProperty().addListener(resizeListener);
		}
	}

	@Override
	public void removeChild(MUIElement element, MElementContainer<MUIElement> parent) {
		System.out.println("GenericRenderer.removeChild(): " + element + ", parent: " + parent);
		if ((MUIElement) parent instanceof MWindow) {
			MWindow window = (MWindow) (MUIElement) parent;
			Stage stage = (Stage) window.getWidget();
			BorderPane root = (BorderPane) stage.getScene().getRoot();
			root.setCenter(null);
		}
	}
}