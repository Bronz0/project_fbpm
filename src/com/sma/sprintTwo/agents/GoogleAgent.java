package com.sma.sprintTwo.agents;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * @author M. BOUDISSA, github.com/bronz0
 *
 */
public class GoogleAgent extends Agent { 
	@Override
	protected void setup() {
		ParallelBehaviour behaviour = new ParallelBehaviour();
		behaviour.addSubBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				ACLMessage message = receive();
				if (message != null) {
					// if(message.getPerformative() == ACLMessage.REQUEST) {
					String requette = message.getContent().toString();
					List<String> links = getLinks(requette);
					ACLMessage response = new ACLMessage(ACLMessage.AGREE);
					try {
						response.setContentObject((Serializable) links);
					} catch (IOException e) {
						e.printStackTrace();
					}
					response.addReceiver(message.getSender());
					send(response);
					// }
				} else {
					block();
				}
			}

			private List<String> getLinks(String requette) {
				List<String> links = new ArrayList<String>();
				Document doc;
				try {
					doc = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(requette, "UTF-8"))
							.get();
					Elements elements = doc.select(".rc .r a");
					for (Element element : elements) {
						String link = element.getElementsByAttributeValue("class", "iUh30 bc tjvcx").text()
								.replace(" › ", "/");
						if (link.length() != 0) {

							links.add(link);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				return links;
			}
		});
		addBehaviour(behaviour);

		super.setup();
	}

}
