package com.sma.sprintTwo.containers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import core.Message;
import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.sma.sprintTwo.agents.UserAgent;
/**
 * @author M. BOUDISSA, github.com/bronz0
 *
 */
public class UserContainer extends Application{
	private UserAgent userAgent;
	ObservableList<Message> messageList;
	ObservableList<String> contactList;

	public static void main(String[] args) {
		launch(UserContainer.class);
	}

	public static boolean deployUser(String username) {
		try {
			Runtime runtime = Runtime.instance();
			Profile profile = new ProfileImpl(false);
			profile.setParameter(Profile.MAIN_HOST, "localhost");
			AgentContainer userContainer = runtime.createAgentContainer(profile);
			AgentController userController = userContainer.createNewAgent(username, "sma.agents.UserAgent",
					new Object[] {});
			userController.start();
			return true;

		} catch (ControllerException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void startContainer() {
		try {
			Runtime runtime = Runtime.instance();
			Profile profile = new ProfileImpl(false);
			profile.setParameter(Profile.MAIN_HOST, "localhost");
			AgentContainer acheteurContainer = runtime.createAgentContainer(profile);
			AgentController agentController = acheteurContainer.createNewAgent("user2", "sma.agents.UserAgent",
					new Object[] { this });
			agentController.start();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		startContainer();
		primaryStage.setTitle("User2");
		BorderPane borderPane = new BorderPane();
		
		// boite de reception
		VBox vbox = new VBox();
		GridPane gridPane = new GridPane();
		messageList = FXCollections.observableArrayList();
		contactList = FXCollections.observableArrayList();
		ListView<Message> listViewMessage = new ListView<Message>(messageList);
		gridPane.add(listViewMessage, 0, 0);
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(10);
		vbox.getChildren().add(gridPane);
		borderPane.setCenter(vbox);
		
		// liste des contacts
		VBox vboxContacts = new VBox();
		GridPane gridPaneContacts = new GridPane();
		messageList = FXCollections.observableArrayList();
		ListView<String> listViewContacts = new ListView<String>(contactList);
		gridPaneContacts.add(listViewContacts, 0, 0);
		vboxContacts.setPadding(new Insets(10));
		vboxContacts.setSpacing(10);
		vboxContacts.getChildren().add(gridPaneContacts);
		borderPane.setLeft(vboxContacts);
		
		
		// 
		Scene scene = new Scene(borderPane, 600, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	
	public void afficherMessage(GuiEvent guiEvent) {
		Message message = (Message) guiEvent.getParameter(0);
		messageList.add(message);
	}

	public UserAgent getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(UserAgent userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * @param guiEvent
	 * mettre a jour la liste des contacts
	 * ajouter les nouveaux contacts
	 * supprimer les contacts qui ne sont pas dans la nouvelle liste
	 */
	public void updateListContacts(GuiEvent guiEvent) {
		AID[] listContacts = (AID[]) guiEvent.getParameter(0);
		for(AID aid: listContacts) {
			//l'agent ne doit pas afficher leur nom dans leur liste de contacts
			// ajouter les nouveaux contacts
			if(!contactList.contains(aid.getName().split("@")[0])) {
				
				if( ! aid.getName().split("@")[0].equals(userAgent.getName().split("@")[0]) ) {
					contactList.add(aid.getName().split("@")[0]);
				}
			}
			// supprimer les contacts qui ne sonte pas dans la nouvelle liste
		}
	}
	
	
	
	

}
