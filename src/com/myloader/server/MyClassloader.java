package com.myloader.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;

public class MyClassloader extends URLClassLoader{
	
	private MyClassloader loader = null;
	Date startDate = new Date();

	public MyClassloader(URL[] urls) {
		super(urls);
	}
	
	public MyClassloader(ClassLoader parent) {
		super(new URL[0],parent);
	}

	@Override
	public void close() throws IOException {
		super.close();
	}
	
	public void addJarFile(String jarFile) throws MalformedURLException{
		URL url = new URL("file:"+jarFile);
		addURL(url);
	}
	
	public void addDir(String path) throws MalformedURLException{
		path = "file:"+path;
		URL url = new URL(path);
		addURL(url);
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString()+",time:"+startDate.toLocaleString();
	}
	
	

}
