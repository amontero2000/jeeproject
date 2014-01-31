package com.microforum.prototipo.web.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AutenticacionUtil {

	/**
	 * Recupera la IP para poder autenticar mediante SSA
	 * 
	 * @return IP
	 */
	public static String getIPAddress() {
		String sIPAddress="";
		
		try {
			InetAddress address = InetAddress.getLocalHost();
			// Cogemos la IP
			byte[] bIPAddress = address.getAddress();
			 
			// IP en formato String
			
			 
			for (int x=0; x<bIPAddress.length; x++) {
			  if (x > 0) {
			    // A todos los numeros les anteponemos
			    // un punto menos al primero
			    sIPAddress += ".";
			  }
			  // Jugamos con los bytes y cambiamos el bit del signo
			  sIPAddress += bIPAddress[x] & 255;
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sIPAddress;
	}
}
