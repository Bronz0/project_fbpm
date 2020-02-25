package com.sma.sprintTwo.ui;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.sma.entitee.User;
import com.sma.sprintOne.service.SocialNetwork;
import com.sprintOne.ui.Login;

import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Home implements EventHandler {
	// Attribute
	private TextField userInput,passInput;
	private Label info;
	private Button btnSend,btnLogout,btnFileChoose;
	private ComboBox destinationList;
	private FileInputStream input;
    private javafx.scene.image.Image image;
    private ImageView iconFile;
    private Label Title,userLabel,labelDistination,labelSearchFile,labelMessage,labelReception,labelPathFile;
    private Stage primaryStage;
    private TextArea msgArea;
	public Home(User user) throws Exception  {
		// initialize stage from calling from another window
		primaryStage= new Stage();
		primaryStage.setTitle("Home");
		// Layout
		Group root = new Group();
		Scene scene = new Scene(root,700,500);  
		// initialize attribute
        userInput=new TextField();  
        passInput=new TextField();  
        info = new Label("");
        userLabel = new Label("User : "+user.getUsername());
        Title = new Label("Project\nFundamentals of Business\n Process Management");
        labelDistination = new Label("Destination : ");
        labelSearchFile = new Label("Search File : ");
        labelMessage = new Label("Message : ");
        msgArea = new TextArea();
        labelReception = new Label("Mailbox :");
        labelPathFile =new Label("No file selected..");
        
        btnFileChoose = new Button("choose ..");
        btnSend=new Button ("Send");
        btnLogout = new Button("Logout");
        destinationList = new ComboBox();

        destinationList.getItems().add("distination");
        
        List<User> listCollegue = this.getmyCommunauteCollegue(user);
        
        for(User u:listCollegue) {
        	destinationList.getItems().add(""+u.getUsername());
        }
        
        destinationList.getSelectionModel().select("distination");
        //  load icons
        input = new FileInputStream("resources/file.png");
        image = new javafx.scene.image.Image(input);
        iconFile = new ImageView(image);
	    
	    
		
		// Background
		Rectangle rect = new Rectangle(0, 0, 350, 500);  
        rect.setFill(Color.web("#0598ff",1)); 
        
        // Personalization
        userInput.setPromptText("Username");
        passInput.setPromptText("Password");
        
        userInput.setStyle("-fx-background-color:transparent;-fx-border-color:#0598ff;-fx-border-width:0px 0px 2px 0px");
        destinationList.setStyle("-fx-background-color:transparent;-fx-border-color:#0598ff;");
        destinationList.setPrefWidth(150);
        passInput.setStyle("-fx-background-color:transparent;-fx-border-color:#0598ff;-fx-border-width:0px 0px 2px 0px");
        
        
        btnSend.setStyle("-fx-background-color: #0598ff;-fx-text-fill: #ffffff");
        btnSend.setFont(new Font("Consolas",16));
        
        btnFileChoose.setStyle("-fx-background-color: #0598ff;-fx-text-fill: #ffffff");
        btnFileChoose.setFont(new Font("Consolas",16));
        
        btnLogout.setStyle("-fx-background-color: #ffffff ;-fx-text-fill: #0598ff");
        btnLogout.setFont(new Font("System",16));
       
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
        //labelPathFile.setWrapText(true);
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
        // Set scene and show stage
        primaryStage.setScene(scene);  
        primaryStage.show(); 
	}
	@Override
	public void handle(Event event) {
		if(event.getSource()==btnFileChoose) {
			FileChooser file = new FileChooser();  
	        file.setTitle("Open File");  
	        File fileChosed =file.showOpenDialog(primaryStage); 
	        labelPathFile.setText(""+fileChosed.getAbsolutePath().toString());
	       
		}else if(event.getSource()==btnLogout) {
			this.primaryStage.close();
			new Login(true);
		}
	
	}
	private List<User> getmyCommunauteCollegue(User user){
		List<User> users = SocialNetwork.getByCommunaute(user.getCommunaute().toString());
		User rmv=null;
		for(User u :users) {
			if(user.getUsername().equals(u.getUsername())) {
				rmv=u;
			}
		}
		users.remove(rmv);
		
		return users;
	}
	
}
		
		
		
	
	


