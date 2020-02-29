package com.sma.sprintOne.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sma.entitee.User;

public class SocialNetwork  {
	private static final Map<Integer,User> usrMap= new HashMap <Integer, User> ();
	
	static {
		usrMap.put(1,new User (1,"Mohamed","1234","Math"));
		usrMap.put(2,new User (2,"Nasr","1234","Math"));
		usrMap.put(3,new User (3,"Zaki","1234","Informatique"));
		usrMap.put(4,new User (4,"Abdallah","1234","Informatique"));
	}
	public SocialNetwork() {}
	
	public static void display() {
		
		for(int i=1;i<=usrMap.size();i++) {
			System.out.println(usrMap.get(i).toString());
		}
	}
	
	public static void SignUp(User u) {
		u.setId(usrMap.size()+1);
		usrMap.put(usrMap.size()+1,u);
	}
	public static boolean existUsername(String username) { // if name exist 	
		for(int i=1;i<=usrMap.size();i++) {
			if(usrMap.get(i).getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	public static boolean Login(String username,String pass) { // if name and pass exist	
		
		
		for(int i=1;i<=usrMap.size();i++) {
			if(usrMap.get(i).getUsername().equals(username) && usrMap.get(i).getPassword().equals(pass)) {
				return true;
			}
		}
		return false;
	}
	public static User getByName(String name) {// get by name
		
		for(int i=1;i<=usrMap.size();i++) {
			if(usrMap.get(i).getUsername().equals(name)) {
				return usrMap.get(i);
			}
		}
		return null;
	}
	public static List<User> getByCommunaute(String communaute){
		List<User> users=new ArrayList<User>();
		for(int i=1;i<=usrMap.size();i++) {
			if(usrMap.get(i).getCommunaute().equals(communaute)) {
				users.add(usrMap.get(i));
			}
		}
		return users;
	}
}
