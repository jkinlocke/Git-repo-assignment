package cop2805;
import java.net.*;
import java.nio.charset.*;
import java.util.List;
import java.util.Scanner;
import java.io.*;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class client {
	//Variable made to hold the search result
	  private static DefaultListModel<String> resultListModel = new DefaultListModel<String>();
	  
	public static void main(String[] args){
		//Set up for the GUI components
	       JFrame frame = new JFrame("Line Searcher");
	        JPanel inputPanel = new JPanel();
	        JLabel filePathLabel = new JLabel("File Path:");
	        JTextField filePathField = new JTextField(20);
	        JLabel lineSearchLabel = new JLabel("Line Number:");
	        JTextField lineSearchField = new JTextField(5);
	        JButton searchButton = new JButton("Search");
	        inputPanel.setLayout(new GridLayout(2,2));
	        inputPanel.add(filePathLabel);
	        inputPanel.add(filePathField);
	        inputPanel.add(lineSearchLabel);
	        inputPanel.add(lineSearchField);
	        inputPanel.add(searchButton);
	        
	        //Variable to store the searched result in
	        JList<String> resultList = new JList<>(resultListModel);
	        JScrollPane resultScrollPane = new JScrollPane(resultList);
	        JPanel resultPanel = new JPanel ();
	        resultPanel.setLayout(new BorderLayout());
	        resultPanel.add(new JLabel("Results: "), BorderLayout.NORTH);
	        resultPanel.add(resultScrollPane, BorderLayout.CENTER);


	        
	        frame.setLayout(new BorderLayout());
	        frame.add(inputPanel, BorderLayout.NORTH);
	        frame.add(resultPanel, BorderLayout.CENTER);
	        frame.pack();
	        frame.setVisible(true);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        
		//BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
	        searchButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        
		try {
			
			
			String filePath = filePathField.getText();
            String lineSearch = lineSearchField.getText();
            Socket connection = new Socket("127.0.0.1",1238);
            InputStream input = connection.getInputStream();
            OutputStream output = connection.getOutputStream();					
			
        	output.write(lineSearch.length());
			output.write(lineSearch.getBytes());
			
			int n = input.read();
			byte[] data = new byte[n];
			input.read(data);
			
			 String serverResponse = new String(data, StandardCharsets.UTF_8);
			 
			  
				int convertedString = Integer.parseInt(lineSearch);
		        try {
		            LineSearcher searcher = new LineSearcher(filePath);
		            while (true) {
		              
		                if (convertedString == -1) {
		                    break;
		                }
		                    List<String> lines = searcher.getLinesAround(convertedString);
		                    for (String line : lines) {
		                       
		                    	 resultListModel.addElement(line + "\n");		                 
		                    }
		                    // Ask user for another line to search
		                 
		                    output.write(lineSearch.getBytes());
		                    n = input.read();
		                    data = new byte[n];
		                    input.read(data);
		                    serverResponse = new String(data, StandardCharsets.UTF_8);
		                    convertedString = Integer.parseInt(serverResponse);
		                }
		            } catch (IOException ex) {
		                System.err.println("Error reading file: " + ex.getMessage());
		            }
		            
		            // Close connection
		           if (!connection.isClosed()) {
		                connection.close();
		            }
		            
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
	        	}
		    });
		}
	}


