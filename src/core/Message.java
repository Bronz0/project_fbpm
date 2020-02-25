package core;

public class Message {
	private String sernder;
	private String content;
	public Message(String sernder, String content) {
		super();
		this.sernder = sernder;
		this.content = content;
	}
	public String getSernder() {
		return sernder;
	}
	public void setSernder(String sernder) {
		this.sernder = sernder;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return this.getSernder()+": \t"+this.getContent();
	}
}
