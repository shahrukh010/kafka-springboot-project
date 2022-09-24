module JavaFx {
	requires javafx.controls;
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.web;
	requires java.mail;
	requires activation;
	requires javafx.media;
	requires java.desktop;
	
	opens application;
	opens application.controller;
	opens application.view;
	opens application.model;
}
