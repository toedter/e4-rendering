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

package com.toedter.e4.demo.contacts.javafx.views;

import com.toedter.e4.demo.contacts.generic.databinding.AggregateNameObservableValue;
import com.toedter.e4.demo.contacts.generic.model.Contact;
import java.io.ByteArrayInputStream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javax.inject.Inject;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.fx.core.databinding.IJFXBeanValueProperty;
import org.eclipse.fx.core.databinding.JFXBeanProperties;
import org.eclipse.osgi.internal.signedcontent.Base64;


@SuppressWarnings("restriction")
public class DetailsView {
	private final WritableValue writableValue = new WritableValue();

	private int detailsPanelRow;
	private GridPane grid;

	private IJFXBeanValueProperty uiProp;

	private DataBindingContext ctx;

	private ImageView imageView;

	private TextField titleText;

	@Inject
	private EPartService partService;

	@Inject
	public DetailsView(BorderPane parent, final MApplication application) {
		Node node = createDetailsPanel();
		parent.setCenter(node);
	}

	private Node createDetailsPanel() {
		uiProp = JFXBeanProperties.value("text");
		ctx = new DataBindingContext();

		grid = new GridPane();
		grid.getStyleClass().add("my-gridpane");

		grid.setHgap(10);
		grid.setVgap(5);
		grid.setPadding(new Insets(10, 10, 10, 10));

		detailsPanelRow = 0;
		addSeparator("General");

		titleText = addProperty("Title", "title");
		addProperty("Name", "name");
		addProperty("Company", "company");
		addProperty("Job Title", "jobTitle");
		addProperty("Note", "note", 2);

		Image image = new Image(getClass().getResourceAsStream("dummy.png"));
		imageView = new ImageView(image);
		grid.add(imageView, 3, 0, 1, 5);
		GridPane.setValignment(imageView, VPos.BOTTOM);
		GridPane.setHalignment(imageView, HPos.LEFT);
		double scaleFactor = 102 / image.getHeight();
		imageView.setFitHeight(scaleFactor * image.getHeight());
		imageView.setFitWidth(scaleFactor * image.getWidth());

		titleText.heightProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Image image = imageView.getImage();
				double scaleFactor = ((Double) newValue + 3.5) * 4 / image.getHeight();
				imageView.setFitHeight(scaleFactor * image.getHeight());
				imageView.setFitWidth(scaleFactor * image.getWidth());
			}
		});

		addSeparator("Business Address");
		addProperty("Street", "street", 2);
		addProperty("City", "city", 2);
		addProperty("Zip", "zip", 2);
		addProperty("Country", "country", 2);

		addSeparator("Business Phones");
		addProperty("Phone", "phone", 2);
		addProperty("Mobile", "mobile", 2);

		addSeparator("Business Internet");
		addProperty("E-Mail", "email", 2);
		addProperty("Web Site", "webPage", 2);

		ColumnConstraints separatorConstraints = new ColumnConstraints();
		separatorConstraints.setHalignment(HPos.LEFT);
		grid.getColumnConstraints().add(separatorConstraints);

		ColumnConstraints labelConstraints = new ColumnConstraints();
		labelConstraints.setHalignment(HPos.RIGHT);
		grid.getColumnConstraints().add(labelConstraints);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(grid);
		scrollPane.autosize();

		return scrollPane;
	}

	public void addSeparator(String text) {
		Label label = new Label(text);
		label.getStyleClass().add("separator-label");
		grid.add(label, 0, detailsPanelRow++, 4, 1);
	}

	public TextField addProperty(String labelText, String property, int span) {
		Label label = new Label(labelText);

		grid.add(label, 1, detailsPanelRow);

		TextField textField = new TextField();

		grid.add(textField, 2, detailsPanelRow);

		GridPane.setConstraints(textField, 2, detailsPanelRow, span, 1, HPos.LEFT, VPos.BASELINE, Priority.ALWAYS,
				Priority.ALWAYS);

		detailsPanelRow++;
		if ("Name".equals(labelText)) {
			ctx.bindValue(uiProp.observe(textField), new AggregateNameObservableValue(writableValue));
		} else {
			ctx.bindValue(uiProp.observe(textField), BeanProperties.value(property).observeDetail(writableValue));
		}

		return textField;
	}

	public TextField addProperty(String labelText, String inputText) {
		return addProperty(labelText, inputText, 1);
	}

	@Inject
	public void setSelection(@Optional final Contact contact) {
		if (contact != null) {
			writableValue.setValue(contact);

			String jpegString = contact.getJpegString();
			byte[] imageBytes = Base64.decode(jpegString.getBytes());
			ByteArrayInputStream is = new ByteArrayInputStream(imageBytes);

			imageView.setImage(new Image(is));
		}
	}
}
