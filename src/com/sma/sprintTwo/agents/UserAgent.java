package com.sma.sprintTwo.agents;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.sma.sprintTwo.containers.UserContainer;
import com.sma.sprintTwo.ui.Home;

import core.Message;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class UserAgent extends GuiAgent {
	private int id;
	private String username;
	private String password;
	private String communaute;

	private Home gui;
	private AID[] listContacts;

	public UserAgent() {
	}

	public UserAgent(int id, String username, String password, String communaute) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.communaute = communaute;
	}

	public UserAgent(String username, String password, String communaute) {
		super();
		this.username = username;
		this.password = password;
		this.communaute = communaute;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCommunaute() {
		return communaute;
	}

	public void setCommunaute(String communaute) {
		this.communaute = communaute;
	}

	// **********************************************************************************************************

	@Override
	protected void setup() {
		gui = (Home) this.getArguments()[0];
		String comm = (String) this.getArguments()[1];
		this.setCommunaute(comm);
		gui.setUserAgent(this);

		System.out.println("creation et initialisation de l'agent: " + this.getAID().getName());
		ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
		addBehaviour(parallelBehaviour);

		// publier le nom et la communauté
		parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {

			@Override
			public void action() {
				DFAgentDescription dfa = new DFAgentDescription();
				dfa.setName(getAID());

				ServiceDescription sd = new ServiceDescription();
				// on peut l'utiliser pour spécifier la communaute
				sd.setType(communaute);
				sd.setName(communaute);
				dfa.addServices(sd);
				try {
					DFService.register(myAgent, dfa);
				} catch (FIPAException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		// recuperer la liste des contacts a partir du DF
		parallelBehaviour.addSubBehaviour(new TickerBehaviour(this, 6000) {
			@Override
			protected void onTick() {
				try {
					DFAgentDescription agentDescription = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					sd.setType(communaute);
					sd.setName(communaute);
					agentDescription.addServices(sd);
					DFAgentDescription[] agentDescriptions = DFService.search(myAgent, agentDescription);
					listContacts = new AID[agentDescriptions.length];
					for (int i = 0; i < agentDescriptions.length; i++) {
						listContacts[i] = agentDescriptions[i].getName();
					}
					GuiEvent guiEvent = new GuiEvent(this, 1);
					guiEvent.addParameter(listContacts);
					gui.updateListContacts(guiEvent);
					// TODO: Envoyer la liste des contacts au container pour l'afficher
					// gui.afficherContacts(guiEvent); on peut utiliser une liste observable tq a
					// chaque
					// fois la liste est modifier le gui va etre modifie

				} catch (FIPAException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		// attender des messages
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {

			@Override
			public void action() {
//				MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
				ACLMessage msg = receive();
				if (msg != null) {
					if(msg.getPerformative() == ACLMessage.INFORM) { // message normal
						// System.out.println("Reception d'un message: "+msg.getContent());
						GuiEvent guiEvent = new GuiEvent(this, 1);
						String content = msg.getContent();
						String sender = msg.getSender().getName().split("@")[0];
						Message message = new Message(sender, content);
						guiEvent.addParameter(message);
						guiEvent.addParameter(msg.getPerformative());
						gui.afficherMessage(guiEvent);
					}else if(msg.getPerformative() == ACLMessage.CFP) { // message with file
						try {
							GuiEvent guiEvent = new GuiEvent(this, 1);
							File file = (File) msg.getContentObject();
							String sender = msg.getSender().getName().split("@")[0];
							String content = file.getName();
							Message message = new Message(sender, content);
							guiEvent.addParameter(file);
							guiEvent.addParameter(msg.getPerformative());
							guiEvent.addParameter(message);
							gui.afficherMessage(guiEvent);
						} catch (UnreadableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else if (msg.getPerformative() == ACLMessage.AGREE) { // reception d'une reponse de google
						try {
							List<String> links =(List<String>) msg.getContentObject();
							for(String s : links) {
								System.out.println("nasro: "+s);
							}
							GuiEvent guiEvent = new GuiEvent(this, 1);
							guiEvent.addParameter(links);
							gui.afficherGoogleResults(guiEvent);
						} catch (UnreadableException e) {
							e.printStackTrace();
						}
					}
				} else {// si il y a pas de message reste blocker jusqu'a l'arriver d'un message
					block();
				}

			}
		});
		super.setup();

	}

	@Override
	protected void takeDown() {
		// TODO Auto-generated method stub
		super.takeDown();
	}

	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void sendMessage(String content, AID receiver) {

		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContent(content);
		msg.addReceiver(receiver);
		send(msg);

	}
	
	public void sendFile(File file, AID receiver) {
		try {
			ACLMessage msg = new ACLMessage(ACLMessage.CFP);
			msg.setContentObject(file);
			msg.addReceiver(receiver);
			send(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void googleSearch(String requette) {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setContent(requette);
		msg.addReceiver(new AID("google", false));
		send(msg);
	}

}
