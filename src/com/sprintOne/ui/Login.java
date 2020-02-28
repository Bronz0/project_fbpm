package com.sprintOne.ui;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.ws.handler.MessageContext.Scope;

import com.sma.entitee.User;
import com.sma.sprintOne.service.SocialNetwork;
import com.sma.sprintTwo.ui.Home;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.Application;

import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import javafx.stage.Stage;

public class Login extends Application implements EventHandler {
	// Attribute
	private TextField userInput, passInput;
	private Button btnLogin, btnSignUp;
	private Label info;
	private FileInputStream input;
	private Image image;
	private ImageView iconGroup, iconUser, iconKey, iconFile;
	private Label Title;
	private Stage primaryStage;

	public Login() {
	}

	public Login(boolean override) {
		// initialize stage from calling from another Window
		this.primaryStage = new Stage();
		try {
			this.CreateWindow(primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.CreateWindow(primaryStage);
	}

	public void CreateWindow(Stage primaryStage) throws IOException {
		// initialize stage for the first time
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Login");
		// set Layout
		Group root = new Group();
		Scene scene = new Scene(root, 700, 500);
		// initialize attribut
		userInput = new TextField();
		passInput = new TextField();
		info = new Label("");
		Label loginLabel = new Label("USER LOGIN");
		Title = new Label("Project\nFundamentals of Business\n Process Management");

		btnLogin = new Button("Login");
		btnSignUp = new Button("Don't have an account ? SignUp");
		// load Icons
		input = new FileInputStream("resources/team.png");
		image = new Image(input);
		iconGroup = new ImageView(image);

		input = new FileInputStream("resources/user.png");
		image = new Image(input);
		iconUser = new ImageView(image);

		input = new FileInputStream("resources/key.png");
		image = new Image(input);
		iconKey = new ImageView(image);

		input = new FileInputStream("resources/file.png");
		image = new Image(input);
		iconFile = new ImageView(image);

		// set Background Blue left half
		Rectangle rect = new Rectangle(0, 0, 350, 500);
		rect.setFill(Color.web("#0598ff", 1));

		// Personalization
		userInput.setPromptText("Username");
		passInput.setPromptText("Password");

		userInput
				.setStyle("-fx-background-color:transparent;-fx-border-color:#0598ff;-fx-border-width:0px 0px 2px 0px");
		passInput
				.setStyle("-fx-background-color:transparent;-fx-border-color:#0598ff;-fx-border-width:0px 0px 2px 0px");

		btnLogin.setStyle("-fx-background-color: #0598ff;-fx-text-fill: #ffffff");
		btnLogin.setFont(new Font("System", 16));

		btnSignUp.setStyle("-fx-background-color: #ffffff;-fx-text-fill: #0598ff");

		info.setStyle("-fx-text-fill: #ff0000");

		loginLabel.setFont(new Font("System", 24));
		loginLabel.setStyle("-fx-text-fill: #0598ff");

		Title.setFont(new Font("Consolas", 26));
		Title.setTextAlignment(TextAlignment.CENTER);
		Title.setStyle("-fx-text-fill: #ffffff");

		// set Position for each element
		userInput.setLayoutX(450);
		userInput.setLayoutY(200);

		passInput.setLayoutX(450);
		passInput.setLayoutY(250);

		btnLogin.setLayoutX(500);
		btnLogin.setLayoutY(325);

		btnSignUp.setLayoutX(420);
		btnSignUp.setLayoutY(455);

		info.setLayoutX(430);
		info.setLayoutY(370);

		loginLabel.setLayoutX(455);
		loginLabel.setLayoutY(150);

		iconGroup.setLayoutX(460);
		iconGroup.setLayoutY(20);

		iconUser.setLayoutX(410);
		iconUser.setLayoutY(195);

		iconKey.setLayoutX(410);
		iconKey.setLayoutY(245);

		iconFile.setLayoutX(100);
		iconFile.setLayoutY(50);

		Title.setLayoutX(5);
		Title.setLayoutY(200);

		// Add Event for button
		btnLogin.setOnAction(this);
		btnSignUp.setOnAction(this);

		// add child
		root.getChildren().add(rect);
		root.getChildren().add(userInput);
		root.getChildren().add(passInput);
		root.getChildren().add(btnLogin);
		root.getChildren().add(btnSignUp);
		root.getChildren().add(info);
		root.getChildren().add(loginLabel);
		root.getChildren().add(iconGroup);
		root.getChildren().add(iconUser);
		root.getChildren().add(iconKey);
		root.getChildren().add(iconFile);
		root.getChildren().add(Title);

		// Set scene and show the stage(Frame)
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	// Events
	@Override
	public void handle(Event event) {
		if (event.getSource() == btnLogin) {
			if (userInput.getText().equals("") || passInput.equals("")) {

				info.setStyle("-fx-text-fill: #ff0000");
				info.setText("username or password is empty !!");

			} else {// Succes block
				String username, userpass;
				username = userInput.getText().toString();
				userpass = passInput.getText().toString();
				if (SocialNetwork.Login(username, userpass)) {// succes

					info.setText("Succesful login !!");
					info.setStyle("-fx-text-fill: #00ff00");
					// dialog box
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText("*Congratulation*");
					alert.setContentText("You have successfully logged in !!");
					alert.showAndWait();
					//this.primaryStage.close();
					try {
						User u = SocialNetwork.getByName(username);
						new Home(u);
						userInput.clear();
						passInput.clear();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// go to Home Page
				} else { // pass or user name incorrect

					info.setText("username or pass is incorrect !!");
					info.setStyle("-fx-text-fill: #ff0000");
				}
			}
		} else {// SignUp
			try {
				this.primaryStage.close(); // fermer la fenetre courante
				new SignUp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		// deployer le main container
		try {
			Runtime runtime = Runtime.instance();
			Properties properties = new Properties();
			properties.setProperty(Profile.GUI, "true");
			Profile profile = new ProfileImpl(properties);
			AgentContainer mainContainer = runtime.createMainContainer(profile);
			mainContainer.start();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// deployer google container
		try {
			Runtime runtime = Runtime.instance();
			Profile profile = new ProfileImpl(false);
			profile.setParameter(Profile.MAIN_HOST, "localhost");
			AgentContainer userContainer = runtime.createAgentContainer(profile);
			AgentController userController = userContainer.createNewAgent("google", "com.sma.sprintTwo.agents.GoogleAgent",
					new Object[] {});
			userController.start();

		} catch (ControllerException e) {
			e.printStackTrace();
		}
		launch(args);

	}
}
