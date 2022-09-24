package application.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import application.EmailManager;
import application.controller.service.MessageRendereService;
import application.model.EmailMessage;
import application.model.EmailTreeItem;
import application.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;

public class MainWindowControlle extends BaseController implements Initializable {

	public MainWindowControlle(EmailManager emailManager, ViewFactory viewFactory, String fxml) {
		super(emailManager, viewFactory, fxml);
	}

	protected boolean isMainWindowView = true;
	@FXML
	private TableColumn<EmailMessage, Date> dateCol;

	@FXML
	private WebView emailWebView;

	@FXML
	private TableView<EmailMessage> emailsTableView;

	@FXML
	private TreeView emailsTreeView;

	@FXML
	private TableColumn<EmailMessage, String> recipientCol;

	@FXML
	private TableColumn<EmailMessage, String> senderCol;

	@FXML
	private TableColumn<EmailMessage, SizeInteger> sizeCol;

	@FXML
	private TableColumn<EmailMessage, String> subjectCol;
	private MessageRendereService messageRendereService;

	@FXML
	void optionsAction() {

	}

	@FXML
	void addAccount() {

		viewFactory.loginWindow();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		setUpEmail();
		setUpEmailTableView();
		setUpFolderSelection();
		setUpRows();
		setUpMessageRenderedService();
		setUpMessageSelection();
	}
	private void setUpMessageRenderedService() {
		
		this.messageRendereService = new MessageRendereService(emailWebView.getEngine());
	}
	private void setUpMessageSelection() {
		
		emailsTableView.setOnMouseClicked(event->{
			
		EmailMessage emailMessage = emailsTableView.getSelectionModel().getSelectedItem();	
		if(emailMessage !=null) {
			System.out.println(emailMessage);
			this.messageRendereService.setEmailMessage(emailMessage);
			this.messageRendereService.restart();//restart because of we want use multiple times [start() method just used only ones]
		}
		});
	}

	private void setUpRows() {
									 //it accepting callback method
									 //callback->it invoked when event occures
		emailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
			
			@Override
			public TableRow<EmailMessage> call(TableView<EmailMessage> arg0) {
				return new TableRow<EmailMessage>() {
					
					protected void updateItem(EmailMessage item, boolean empty) {
						
						super.updateItem(item, empty);
						if(item !=null) {
							if(item.isRead()) {
								setStyle("");
							}
							else {
								setStyle("-fx-font-weight:bold");
							}
						}
					}
				};
			}
		});
		
	}

	private void setUpFolderSelection() {
	emailsTreeView.setOnMouseClicked(event->{
															//on each folder we are getting data
		EmailTreeItem<String> item = (EmailTreeItem<String>)emailsTreeView.getSelectionModel().getSelectedItem();
		if(item !=null) {
			emailsTableView.setItems(item.getMessage());
		}
	});
	}

	private void setUpEmailTableView() {
		//setCellValueFactory() used to put content into column

		senderCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("sender")));
		subjectCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("subject")));
		recipientCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("recipient")));
		sizeCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, SizeInteger>("size")));
		dateCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, Date>("date")));

	}

	private void setUpEmail() {

		emailsTreeView.setRoot(emailManager.getFolderRoot());
		emailsTreeView.setShowRoot(false);
	}
	
	@FXML
    void composeMessageOnAction() {

		viewFactory.composeWindow();
    }

}
