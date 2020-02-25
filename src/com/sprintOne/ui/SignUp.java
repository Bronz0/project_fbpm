package com.sprintOne.ui;

import java.io.FileInputStream;



import com.sma.entitee.User;
import com.sma.sprintOne.service.SocialNetwork;


import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import javafx.stage.Stage;

public class SignUp implements EventHandler {
	// Attribute
	private TextField userInput,passInput;
	private Label info;
	private Button btnSignUp,btnBack;
	private ComboBox communauteList;
	private ImageView iconAdd,iconUser,iconMenu,iconKey,iconFile;
	private FileInputStream input;
    private javafx.scene.image.Image image;
    private Label Title;
    private Stage primaryStage;
	public SignUp() throws Exception  {
		// initialize stage from calling from another window
		primaryStage= new Stage();
		primaryStage.setTitle("Sign Up");
		// Layout
		Group root = new Group();
		Scene scene = new Scene(root,700,500);  
		// initialize attribute
        userInput=new TextField();  
        passInput=new TextField();  
        info = new Label("");
        Label signUpLabel = new Label("USER SIGN UP");
        Title = new Label("Project\nFundamentals of Business\n Process Management");
        
        btnSignUp=new Button ("Sign Up");
        btnBack = new Button("Back");
        communauteList = new ComboBox();

        communauteList.getItems().add("communaute");
        communauteList.getItems().add("Informatique");
        communauteList.getItems().add("Math");
        
        communauteList.getSelectionModel().select("communaute");
        //  load icons
        input = new FileInputStream("resources/file.png");
		image = new javafx.scene.image.Image(input);
		iconFile = new ImageView(image);
		
		input = new FileInputStream("resources/add.png");
		image = new javafx.scene.image.Image(input);
		iconAdd = new ImageView(image);
		
		input = new FileInputStream("resources/user.png");
	    image = new javafx.scene.image.Image(input);
	    iconUser = new ImageView(image);
	    
	    input = new FileInputStream("resources/open-menu.png");
	    image = new javafx.scene.image.Image(input);
	    iconMenu = new ImageView(image);
	        
	    input = new FileInputStream("resources/key.png");
	    image = new javafx.scene.image.Image(input);
	    iconKey = new ImageView(image);
	    
	    
		
		// Background
		Rectangle rect = new Rectangle(0, 0, 350, 500);  
        rect.setFill(Color.web("#0598ff",1)); 
        
        // Personalization
        userInput.setPromptText("Username");
        passInput.setPromptText("Password");
        
        userInput.setStyle("-fx-background-color:transparent;-fx-border-color:#0598ff;-fx-border-width:0px 0px 2px 0px");
        communauteList.setStyle("-fx-background-color:transparent;-fx-border-color:#0598ff;");
        communauteList.setPrefWidth(150);
        passInput.setStyle("-fx-background-color:transparent;-fx-border-color:#0598ff;-fx-border-width:0px 0px 2px 0px");
        
        
        btnSignUp.setStyle("-fx-background-color: #0598ff;-fx-text-fill: #ffffff");
        btnSignUp.setFont(new Font("System",16));
        
        btnBack.setStyle("-fx-background-color: #ffffff ;-fx-text-fill: #0598ff");
        btnBack.setFont(new Font("System",16));
       
        signUpLabel.setFont(new Font("System", 24));
        signUpLabel.setStyle("-fx-text-fill: #0598ff");
        
       
        info.setStyle("-fx-text-fill: #ff0000");
        
        
        Title.setFont(new Font("Consolas", 26));
        Title.setTextAlignment(TextAlignment.CENTER);
        Title.setStyle("-fx-text-fill: #ffffff");
        
        // set Position
        userInput.setLayoutX(450);
        userInput.setLayoutY(160);
        
        passInput.setLayoutX(450);
        passInput.setLayoutY(260);
        
        btnSignUp.setLayoutX(500);
        btnSignUp.setLayoutY(325);
        
        btnBack.setLayoutX(10);
        btnBack.setLayoutY(10);
        
        communauteList.setLayoutX(450);
        communauteList.setLayoutY(220);
        
        info.setLayoutX(400);
        info.setLayoutY(370);
        
        signUpLabel.setLayoutX(455);
        signUpLabel.setLayoutY(120);
        
        iconFile.setLayoutX(100);
        iconFile.setLayoutY(50);
        
        Title.setLayoutX(5);
        Title.setLayoutY(200);
        
        iconAdd.setLayoutX(440);
        iconAdd.setLayoutY(5);
        
        iconUser.setLayoutX(410);
        iconUser.setLayoutY(155);
        
        iconMenu.setLayoutX(410);
        iconMenu.setLayoutY(215);
        
        iconKey.setLayoutX(410);
        iconKey.setLayoutY(265);
        
        // Add Event to button 
        btnSignUp.setOnAction(this);
        btnBack.setOnAction(this);
       
        
        // add child 
        root.getChildren().add(rect);
        root.getChildren().add(userInput);
        root.getChildren().add(passInput);
        root.getChildren().add(btnSignUp);
        root.getChildren().add(info);
        root.getChildren().add(communauteList);
        root.getChildren().add(signUpLabel);
        root.getChildren().add(iconAdd);
        root.getChildren().add(iconUser);
        root.getChildren().add(iconMenu);
        root.getChildren().add(iconKey);
        root.getChildren().add(iconFile);
        root.getChildren().add(Title);
        root.getChildren().add(btnBack);
        
        // Set scene and show stage
        primaryStage.setScene(scene);  
        primaryStage.show(); 
	}
	@Override
	public void handle(Event event) {
		if(event.getSource()== btnSignUp) {// Sign Up Button
			if(userInput.getText().equals("") || passInput.equals("") || communauteList.getValue().toString().equals("communaute")) {// one off input is empty
				
				info.setStyle("-fx-text-fill: #ff0000");
				info.setText("username or password or communaute is empty !!");
				
			}else {// all are filled
				
				String username,communaute,userpass;
				username = userInput.getText().toString();
				userpass = passInput.getText().toString();
				communaute = communauteList.getValue().toString();
				
				if(SocialNetwork.existUsername(username)) {// user name exist in hashMap
					info.setStyle("-fx-text-fill: #ff0000");
					info.setText("This username is already exist !!");
					
				}
				else { // does't exist
				SocialNetwork.SignUp(new User(username,userpass,communaute));
				SocialNetwork.display();
				info.setStyle("-fx-text-fill: #00ff00");
				info.setText("Succesful sign up !!");
				// dialog box
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("*Congratulation*");
				alert.setContentText("You have successfully signed up !!");
				alert.showAndWait();
				
				this.primaryStage.close(); //fermer la fentre courant
				new Login(true); //call new Login interface
				
				}
			}
			
		}else if(event.getSource()==btnBack) {
			this.primaryStage.close();
			new Login(true);
		}
	}
}
		
		
		
	
	


