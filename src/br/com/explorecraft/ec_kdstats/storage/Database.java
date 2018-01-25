package br.com.explorecraft.ec_kdstats.storage;

public class Database {
	
	public static String pltable = "statsplayer";
    public static String plcolumns = "uuid char(36) , name varchar(20), kills int(11) NULL , deaths int(11) NULL , PRIMARY KEY (uuid)";

}
