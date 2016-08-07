package com.dlf.lt;

import java.io.Serializable;

public class Address  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1666409478903637231L;
	private String ip;
	private int port;
	private String tomcat;
	
	public Address(String ip,int port, String tomcat){
		this.ip=ip;
		this.port=port;
		this.tomcat=tomcat;
	}
	public Address(){}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getTomcat() {
		return tomcat;
	}
	public void setTomcat(String tomcat) {
		this.tomcat = tomcat;
	}
	
	public String toString(){
		return (super.toString()+"  "+ip+":"+port+"--"+tomcat);
	}
	
	
	
}
