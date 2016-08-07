package com.dlf.lt.mather;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dlf.lt.Address;
import com.dlf.lt.ConfigUtil;
import com.dlf.lt.MyStore;

public class UpdateMyStore  implements ServletContextListener{
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		new  Thread(new Runnable() {
			@Override
			public void run() {
				UpdateMyStore.waiteUpdate();
				
			}
		}).start();
		
	}  
	
	public static void waiteUpdate() {
		 ServerSocket server;
			try {
				System.out.println("我初始化了");
				server = new ServerSocket(ConfigUtil.tellMatherOK);
				while (true) {
		            Socket socket = server.accept();  
		            invoke(socket);  
		        }  
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private static void invoke(final Socket socket) {
		new Thread(new Runnable() {
			public void run() {
				ObjectInputStream  is = null;

				try {
					is = new ObjectInputStream(new BufferedInputStream(
							socket.getInputStream()));
					Object obj = is.readObject();
					Address address = (Address) obj;

					System.out.println("我是mather 我收到"+address.getIp()+" 我知道它已经收到文件了");
					Iterator<Address> iterator=MyStore.notGetFilesSon.iterator();
					while (iterator.hasNext()) {
						Address add=iterator.next();
						if (add.getIp().equals(add.getIp())) 
							iterator.remove();
					}
					
					MyStore.getFilesSon.add(address);
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					try {
						is.close();
						socket.close();
					} catch (Exception ex) {
					}

				}
			}
		}).start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}



}
