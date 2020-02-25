package com.sma.sprintTwo.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;

import com.sma.entitee.User;
import com.sma.sprintOne.service.SocialNetwork;
import com.sprintOne.ui.Login;

import core.Message;
import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.event.MessageAdapter;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.sma.sprintTwo.agents.*;

public class Home implements EventHandler {
	// Attribute
	private TextField userInput, passInput;
	private Label info;
	private Button btnSend, btnLogout, btnFileChoose;
	private ComboBox destinationList;
	private FileInputStream input;
	private javafx.scene.image.Image image;
	private ImageView iconFile;
	private Label Title, userLabel, labelDistination, labelSearchFile, labelMessage, labelReception, labelPathFile;
	private Stage primaryStage;
	private TextArea msgArea;
	// ********************************************************************
	private UserAgent userAgent;
	ObservableList<Message> messageList;
	ObservableList<String> contactList;
	AID[] aidContacts;
	File fileChosed = null;

	public Home(User user) throws Exception {
		startContainer(user.getUsername(), user.getCommunaute());
		messageList = FXCollections.observableArrayList();
		contactList = FXCollections.observableArrayList();
		// initialize stage from calling from another window
		primaryStage = new Stage();
		primaryStage.setTitle("Home");
		// Layout
		Group root = new Group();
		Scene scene = new Scene(root, 700, 500);
		// initialize attribute
		userInput = new TextField();
		passInput = new TextField();
		info = new Label("");
		userLabel = new Label("User : " + user.getUsername());
		Title = new Label("Project\nFundamentals of Business\n Process Management");
		labelDistination = new Label("Destination : ");
		labelSearchFile = new Label("Search File : ");
		labelMessage = new Label("Message : ");
		msgArea = new TextArea();
		labelReception = new Label("Mailbox :");
		labelPathFile = new Label("No file selected..");

		btnFileChoose = new Button("choose ..");
		btnSend = new Button("Send");
		btnLogout = new Button("Logout");
		destinationList = new ComboBox();

		destinationList.getItems().add("distination");

//        List<User> listCollegue = this.getmyCommunauteCollegue(user);
//        
//        for(User u:listCollegue) {
//        	destinationList.getItems().add(""+u.getUsername());
//        }

//        destinationList.getSelectionModel().select("distination");
		destinationList.setItems(contactList);
		// load icons
		input = new FileInputStream("resources/file.png");
		image = new javafx.scene.image.Image(input);
		iconFile = new ImageView(image);

		// Background
		Rectangle rect = new Rectangle(0, 0, 350, 500);
		rect.setFill(Color.web("#0598ff", 1));

		// Personalization
		userInput.setPromptText("Username");
		passInput.setPromptText("Password");

		userInput
				.setStyle("-fx-background-color:transparent;-fx-border-color:#0598ff;-fx-border-width:0px 0px 2px 0px");
		destinationList.setStyle("-fx-background-color:transparent;-fx-border-color:#0598ff;");
		destinationList.setPrefWidth(150);
		passInput
				.setStyle("-fx-background-color:transparent;-fx-border-color:#0598ff;-fx-border-width:0px 0px 2px 0px");

		btnSend.setStyle("-fx-background-color: #0598ff;-fx-text-fill: #ffffff");
		btnSend.setFont(new Font("Consolas", 16));

		btnFileChoose.setStyle("-fx-background-color: #0598ff;-fx-text-fill: #ffffff");
		btnFileChoose.setFont(new Font("Consolas", 16));

		btnLogout.setStyle("-fx-background-color: #ffffff ;-fx-text-fill: #0598ff");
		btnLogout.setFont(new Font("System", 16));

		userLabel.setFont(new Font("Consolas", 18));
		userLabel.setStyle("-fx-text-fill: #0598ff");

		labelDistination.setFont(new Font("Consolas", 24));
		labelDistination.setStyle("-fx-text-fill: #0598ff");

		labelSearchFile.setFont(new Font("Consolas", 24));
		labelSearchFile.setStyle("-fx-text-fill: #0598ff");

		labelMessage.setFont(new Font("Consolas", 24));
		labelMessage.setStyle("-fx-text-fill: #0598ff");

		labelReception.setFont(new Font("Consolas", 24));
		labelReception.setStyle("-fx-text-fill: #0598ff");

		labelPathFile.setFont(new Font("Consolas", 12));
		labelPathFile.setStyle("-fx-text-fill: #000000");
		labelPathFile.setPrefWidth(350);
		labelPathFile.setMaxWidth(350);
		// labelPathFile.setWrapText(true);
		labelPathFile.setPrefHeight(30);

		info.setStyle("-fx-text-fill: #ff0000");

		Title.setFont(new Font("Consolas", 26));
		Title.setTextAlignment(TextAlignment.CENTER);
		Title.setStyle("-fx-text-fill: #ffffff");

		msgArea.setPrefWidth(320);
		msgArea.setPrefHeight(60);

		// set Position
		userInput.setLayoutX(450);
		userInput.setLayoutY(160);

		passInput.setLayoutX(450);
		passInput.setLayoutY(260);

		btnSend.setLayoutX(630);
		btnSend.setLayoutY(5);

		btnLogout.setLayoutX(10);
		btnLogout.setLayoutY(10);

		destinationList.setLayoutX(540);
		destinationList.setLayoutY(67);

		labelDistination.setLayoutX(364);
		labelDistination.setLayoutY(67);

		info.setLayoutX(400);
		info.setLayoutY(370);

		userLabel.setLayoutX(370);
		userLabel.setLayoutY(10);

		Title.setLayoutX(5);
		Title.setLayoutY(200);

		iconFile.setLayoutX(100);
		iconFile.setLayoutY(50);

		labelSearchFile.setLayoutX(363);
		labelSearchFile.setLayoutY(108);

		btnFileChoose.setLayoutX(550);
		btnFileChoose.setLayoutY(108);

		labelPathFile.setLayoutX(363);
		labelPathFile.setLayoutY(150);

		labelMessage.setLayoutX(363);
		labelMessage.setLayoutY(169);

		msgArea.setLayoutX(363);
		msgArea.setLayoutY(200);

		labelReception.setLayoutX(363);
		labelReception.setLayoutY(260);
		// Add Event to button
		btnSend.setOnAction(this);
		btnLogout.setOnAction(this);
		btnFileChoose.setOnAction(this);

		// boite de reception
		VBox vbox = new VBox();
		GridPane gridPane = new GridPane();
		ListView<Message> listViewMessage = new ListView<Message>(messageList);
		gridPane.add(listViewMessage, 0, 0);
		// vbox.setPadding(new Insets(10));
		// vbox.setSpacing(10);
		vbox.getChildren().add(gridPane);
		vbox.setLayoutX(369);
		vbox.setLayoutY(300);
		vbox.setPrefHeight(150);
		vbox.setPrefWidth(450);

		// add child
		root.getChildren().add(rect);
		root.getChildren().add(iconFile);
		root.getChildren().add(btnSend);
		root.getChildren().add(info);
		root.getChildren().add(destinationList);
		root.getChildren().add(userLabel);
		root.getChildren().add(labelDistination);
		root.getChildren().add(Title);
		root.getChildren().add(btnLogout);
		root.getChildren().add(labelSearchFile);
		root.getChildren().add(labelPathFile);
		root.getChildren().add(labelMessage);
		root.getChildren().add(msgArea);
		root.getChildren().add(labelReception);
		root.getChildren().add(btnFileChoose);
		root.getChildren().add(vbox);
		// Set scene and show stage
		primaryStage.setScene(scene);
		primaryStage.show();
		
		listViewMessage.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<Message>() {
          public void changed(ObservableValue<? extends Message> observable,
              Message oldValue, Message newValue) {
            System.out.println("selection changed: "+newValue);  
          }
        });
		
	}

	@Override
	public void handle(Event event) {

		if (event.getSource() == btnFileChoose) {
			FileChooser file = new FileChooser();
			file.setTitle("Open File");
			try {
				fileChosed = file.showOpenDialog(primaryStage);
				labelPathFile.setText("" + fileChosed.getAbsolutePath().toString());
			} catch (Exception e) {

			}
		} else if (event.getSource() == btnLogout) {
			userAgent.doDelete();
			this.primaryStage.close();
			// new Login(true);
		} else if (event.getSource() == btnSend) {

			if (destinationList.getValue() != null) { // il y a un destinataire
				if (fileChosed != null || !msgArea.getText().equals("")) {
					if (!msgArea.getText().equals("")) {// si il ya un message
						String content = msgArea.getText();
						String name = destinationList.getValue().toString();
						AID receiver = findAidByName(name);
						userAgent.sendMessage(content, receiver);
					}

					if (fileChosed != null) {// il y a un fichier
						String name = destinationList.getValue().toString();
						AID receiver = findAidByName(name);
						userAgent.sendFile(fileChosed, receiver);
					}
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText("*Problème*");
					alert.setContentText("chose a file or write a message !");
					alert.showAndWait();
				}

			} else { // pas de destinataire
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("*Problème*");
				alert.setContentText("reviever name is empty!");
				alert.showAndWait();
			}

		}

	}

	private List<User> getmyCommunauteCollegue(User user) {
		List<User> users = SocialNetwork.getByCommunaute(user.getCommunaute().toString());
		User rmv = null;
		for (User u : users) {
			if (user.getUsername().equals(u.getUsername())) {
				rmv = u;
			}
		}
		users.remove(rmv);

		return users;
	}

	// *************************************************************************
	// demarer le container et
	public void startContainer(String username, String communaute) {
		try {
			Runtime runtime = Runtime.instance();
			Profile profile = new ProfileImpl(false);
			profile.setParameter(Profile.MAIN_HOST, "localhost");
			AgentContainer acheteurContainer = runtime.createAgentContainer(profile);
			AgentController agentController = acheteurContainer.createNewAgent(username,
					"com.sma.sprintTwo.agents.UserAgent", new Object[] { this, communaute });
			agentController.start();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public UserAgent getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(UserAgent userAgent) {
		this.userAgent = userAgent;
	}

	public void afficherMessage(GuiEvent guiEvent) {
		int performative = (int) guiEvent.getParameter(1);
		System.out.println("performative = " + performative);
		if (performative == ACLMessage.INFORM) { // message normal
			Message message = (Message) guiEvent.getParameter(0);
			messageList.add(message);
			System.out.println("reception d'un message: " + message.getContent());
		} else if (performative == ACLMessage.CFP) { // fichier
			File file = (File) guiEvent.getParameter(0);
			Message message = (Message) guiEvent.getParameter(2);
			messageList.add(message);
			System.out.println("reception d'un fichier: " + file.getName());
		}
	}

	public void updateListContacts(GuiEvent guiEvent) {
		aidContacts = (AID[]) guiEvent.getParameter(0);
		for (AID aid : aidContacts) {
			// l'agent ne doit pas afficher leur nom dans leur liste de contacts
			// ajouter les nouveaux contacts
			if (!contactList.contains(aid.getName().split("@")[0])) {

				if (!aid.getName().split("@")[0].equals(userAgent.getName().split("@")[0])) {
					contactList.add(aid.getName().split("@")[0]);
				}
			}
			// supprimer les contacts qui ne sonte pas dans la nouvelle liste
		}
	}

	public AID findAidByName(String name) {

		for (AID aid : aidContacts) {
			if (aid.getName().split("@")[0].equals(name)) {
				return aid;
			}
		}
		return null;
	}

	public byte[] converToByte(File file) {// file --> bytes
		FileInputStream in = null;
		byte[] bArray = null;

		bArray = new byte[(int) file.length()];
		try {
			in = new FileInputStream(file.getAbsolutePath());
			in.read(bArray);
			in.close();

		} catch (IOException ioExp) {
			ioExp.printStackTrace();
		}

		// convertTofile(bArray);
		return bArray;

	}

	public void convertTofile(byte[] bs) { // bytes --> file (pdf/txt)
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("fichier/new.pdf");// this path["new.pdf"] mean in this project
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < bs.length; i++) {
			try {
				out.write(bs[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
