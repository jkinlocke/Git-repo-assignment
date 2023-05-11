package cop2805;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class ServerMain {

	 public static void main(String[] args) {
	        
	        
	        //Server Setup
	        ServerSocket server = null;
	        boolean shutdown = false;
	        try {
				server = new ServerSocket(1238);
				System.out.println("Port bound. Accepting connections.");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(-1);
			}
	        while(!shutdown) {
	        	Socket client = null;
	        	InputStream input = null;
	        	OutputStream output = null;
	        	
	        	try {
	        		client = server.accept();
	        		input = client.getInputStream();
	        		output = client.getOutputStream();
	        		
	        		int n = input.read();
	        		byte[] data = new byte[n];
	        		input.read(data);
	        		
	        		String clientInput = new String(data, StandardCharsets.UTF_8);
	        		int convertedString = Integer.parseInt(clientInput);
	        		System.out.println("Client said:" + convertedString);
	        		
	        		String response = "You searcehed for line [" + clientInput + "]";
	        		output.write(response.length());
	        		output.write(response.getBytes());
	        		
	        		client.close();
	        		
	        	}catch (IOException e) {
	        		e.printStackTrace();
	        		continue;
	        	}
	        }
	        
	        
	    }
	 	 
	}
