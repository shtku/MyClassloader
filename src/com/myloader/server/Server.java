package com.myloader.server;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;

import com.myloader.server.service.BusService;

public class Server {
	String codePath = "C:\\Users\\Administrator\\wordspace-kepler\\SHT201709\\bin\\";
	String busServiceClass = "ClassLoader.server.service.impl.BusServiceImpl";
	BusService busService;
	
	public String doWork(String name){
		if(busService != null){
			return busService.doIt(name);
		}
		return "default";
	}
	
	public void init(){
		new Thread(){
			long lastTime = 0;
			public void run(){
				File f = new File(codePath+"version.txt");
				while(true){
					System.out.println(lastTime != f.lastModified());
					if(lastTime != f.lastModified()){
						lastTime = f.lastModified();
						ClassLoader c1 = this.getClass().getClassLoader();
						System.out.println(c1);
						MyClassloader myLoader = new MyClassloader(new URL[0]);
						try {
							myLoader.addDir(codePath);
							Class<BusService> clazz = (Class<BusService>) myLoader.loadClass(busServiceClass);
							BusService busService2  =  clazz.newInstance();
							BusService temp  =  busService;
							busService = busService2;
							temp.close();
							ClassLoader c = temp.getClass().getClassLoader();
							if(c instanceof URLClassLoader){
								((URLClassLoader)c).close();
							}
							System.out.println("busService:"+busService + "  ,classloader:"+busService.getClass().getClassLoader());
							System.out.println("end test "+ new Date().toLocaleString());
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		}.start();
	}
}
