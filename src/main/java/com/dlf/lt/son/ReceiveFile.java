package com.dlf.lt.son;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.dlf.lt.Address;
import com.dlf.lt.ConfigUtil;
import com.dlf.lt.TomcatUtil;
import com.dlf.lt.mather.SendFile;

public class ReceiveFile {
	public static void main(String[] args) {
		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				receiveFile();
//				
//			}
//		}).start();
//		
//		receiveObject();
		TomcatUtil.restartMyTomcat();
	}
	
	public static void receiveObject(){
		 ServerSocket server;
		try {
			server = new ServerSocket(ConfigUtil.toSonTrunkAddressList);
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
				ObjectInputStream is = null;

				try {
					is = new ObjectInputStream(new BufferedInputStream(
							socket.getInputStream()));
					Object obj = is.readObject();
					Address address = (Address) obj;
					System.out.println("我收到 "+address);
					SendFile.sendfile(ConfigUtil.getLocalFileLocation(), address,socket.getInetAddress().getHostAddress());
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
		
	

	public static void receiveFile(){
		try {
			  //使用本地文件系统接受网络数据并存为新文件
			FileOutputStream outSTr = new FileOutputStream(new File(ConfigUtil.getLocalFileLocationSon()));   

			BufferedOutputStream bos=new BufferedOutputStream(outSTr);   
	       
	        
			// 创建网络服务器接受客户请求
			ServerSocket ss = new ServerSocket(ConfigUtil.toSon);
			Socket client = ss.accept();
			deltetFile();
	        // 通过Socket连接文件服务器
	 //       Socket server=new Socket(InetAddress.getLocalHost(),3108);
	       
	        //创建网络接受流接受服务器文件数据
	        InputStream netIn=client.getInputStream();
	        InputStream in=new DataInputStream(new BufferedInputStream(netIn));
	       
	        //创建缓冲区缓冲网络数据
	        byte[] buf=new byte[2048];
	        int num=in.read(buf);
	       
	        while(num!=(-1)){//是否读完所有数据
	        	bos.write(buf);
	               num=in.read(buf);//继续从网络中读取文件
	        }
	        in.close();
	        bos.close();
	        
	        TomcatUtil.restartMyTomcat();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void deltetFile() {
		File f=new File(ConfigUtil.getLocalFileLocationSon());
		f.delete();
	}

	
}
