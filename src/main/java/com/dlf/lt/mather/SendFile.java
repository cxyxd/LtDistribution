package com.dlf.lt.mather;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import com.dlf.lt.Address;
import com.dlf.lt.ConfigUtil;

public class SendFile {
	public static void main(String[] args) {
		sendfile(null, null, null);
	}
	
	public static void sendObject (Address address, List<Address> adds) { 
		try {
			InetAddress addr = InetAddress.getByName(address.getIp());
			System.out.println("我要给 "+address+" 这个son_trunk 发送很多消息 ");
			ObjectOutputStream os=null;
			for (int i=0;i<adds.size();i++) {
				Socket socket = new Socket(addr, ConfigUtil.toSonTrunkAddressList);

				// 创建网络输出流并提供数据包装器
				os = new ObjectOutputStream(socket.getOutputStream());
				Address add=adds.get(i);
				os.writeObject(add);// 把文件数据写出网络缓冲区
				os.flush();// 刷新缓冲区把数据写往客户端
			}
			if (os!=null) 
				os.close();
			
		

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
	public static void sendfile (String localFileLocation, Address add, String from) { 
		File file = new File(localFileLocation);
		try {
			FileInputStream fos = new FileInputStream(file);

            // 设置连接地址类,连接本地

            InetAddress addr = InetAddress.getByName(add.getIp());        

            //要对应服务器端的3333端口号

            Socket socket = new Socket(addr, ConfigUtil.toSon);

			// 创建网络输出流并提供数据包装器
			OutputStream netOut = socket.getOutputStream();
			OutputStream doc = new DataOutputStream(
					new BufferedOutputStream(netOut));

			// 创建文件读取缓冲区
			byte[] buf = new byte[2048];
			int num = fos.read(buf);
			while (num != (-1)) {// 是否读完文件
				doc.write(buf, 0, num);// 把文件数据写出网络缓冲区
				doc.flush();// 刷新缓冲区把数据写往客户端
				num = fos.read(buf);// 继续从文件中读取数据
			}
			System.out.println("我写完了");
			fos.close();
			doc.close();
			sendOKForMather(socket,from);
		} catch (Exception e) {
			System.out.println("错误 :"+add);
			e.printStackTrace();
		}
		
	}

	private static void sendOKForMather(Socket socket, String from) {
		try {
			
			InetAddress addr=null;
			String matherIp=socket.getInetAddress().getHostAddress();
			if (from.equals("mather")) {
				addr = InetAddress.getByName(matherIp);
			}else {
				addr = InetAddress.getByName(from);
			}
			
			Socket s = new Socket(addr,ConfigUtil.tellMatherOK );
			ObjectOutputStream os = new ObjectOutputStream(
					s.getOutputStream());
			Address add=ConfigUtil.getMyAddress();
			System.out.println("我要告诉mather "+add.getIp()+"已经收到文件了");
			os.writeObject(add);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}
}
