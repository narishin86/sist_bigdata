package com.sist.mongodb;

import java.net.InetSocketAddress;

import com.mongodb.*;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			MongoClient mc=new MongoClient(new ServerAddress(new InetSocketAddress("211.238.142.20", 27017)));
			DB db=mc.getDB("mydb");
			DBCollection dbc=db.getCollection("member");
			
			
			BasicDBObject obj=new BasicDBObject();
			obj.put("no", 7);
			obj.put("name", "jin");
			dbc.insert(obj);
			System.out.println("Save!!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}