package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.mail.Folder;

import application.controller.service.FetchFolderService;
import application.controller.service.FolderUpdateService;
import application.model.EmailAccount;
import application.model.EmailTreeItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class EmailManager {

	
	private EmailTreeItem<String> folderRoot = new EmailTreeItem<String>("");
	private List<Folder> folderList = new ArrayList<>();
	private ObservableList<EmailAccount>emailAccount = FXCollections.observableArrayList();
	
	public ObservableList<EmailAccount> getEmailAccount(){
		
		return emailAccount;
	}
	FolderUpdateService folderUpdateService;
	
	public EmailManager() {
		
		folderUpdateService = new FolderUpdateService(folderList);
		folderUpdateService.start();
	}
	public List<Folder> getFolderList(){
		
		return this.folderList;
	}
	public EmailTreeItem<String> getFolderRoot(){
		
		return folderRoot;
	}
	
	public void addAccount(EmailAccount emailAccount) {
		
		this.emailAccount.add(emailAccount);
	//	System.out.println(this.emailAccount);
		EmailTreeItem<String> tree =new EmailTreeItem<String>(emailAccount.getEmailAddress());
		FetchFolderService fetchFolderService = new FetchFolderService(emailAccount.getStore(),tree,folderList);
		fetchFolderService.start();
		tree.setExpanded(true);
		folderRoot.getChildren().add(tree);
	}
	
}
