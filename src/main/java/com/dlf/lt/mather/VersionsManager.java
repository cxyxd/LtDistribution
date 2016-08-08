package com.dlf.lt.mather;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.dlf.lt.ConfigUtil;

public class VersionsManager {
    public static void main(String args[]) {
     //   writer();//自己可以注释掉代码试试
        getMap();
    }
    public static void  persistentMap(Map map){

		File f=null;
		try {
			//如果不采用tomcat 下面的代码会报错 使用catch里面的file
			//如果使用tomcat 就使用下面的代码
			String path=ConfigUtil.class.getClassLoader().getResource("/versions.txt").getFile();
			f = new File(path);
		} catch (Exception e) {
			f = new File("src/main/resources/versions.txt");
		}
		
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream( new FileOutputStream(f));
            oos.writeObject(map);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                oos.flush();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Map<String,Integer> getMap(){
    	 Map<String,Integer> map= null;
    	 File f=null;
 		try {
 			//如果不采用tomcat 下面的代码会报错 使用catch里面的file
 			//如果使用tomcat 就使用下面的代码
 			String path=ConfigUtil.class.getClassLoader().getResource("/versions.txt").getFile();
 			f = new File(path);
 		} catch (Exception e) {
 			f = new File("src/main/resources/versions.txt");
 		}
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(f));
            Object o = ois.readObject();
            map = (Map<String,Integer>)o;
            Iterator<Entry<String,Integer>> iterator= map.entrySet().iterator();
            while (iterator.hasNext()) {
				Entry<String, Integer> entry=iterator.next();
				System.out.println(entry.getKey()+" -- "+entry.getValue());
				
			}
            System.out.println(map);
            return map;
        } catch (Exception e) {
        	 e.printStackTrace(); 
        	return null;   
        }finally{
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
     
}