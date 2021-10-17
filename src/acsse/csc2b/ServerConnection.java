/**
 * @author Thalukanyo
 * This class makes a connection between a server and a client
 * It makes a communication between a server and a client possible
 */
package acsse.csc2b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServerConnection {
	
	static Socket socket;
	/**
	 * This is a parameterless constructor that assigns null to a socket
	 * */
	public ServerConnection()
	{
		socket = null;
	}
	/**
	 * Method for establishing a connection and initiate communication
	 * */
	public void establishServerConnection()
	{
		try(ServerSocket ss = new ServerSocket(7777))
		{
			System.out.println("Ready for smart student to make connection…");
			socket = ss.accept();
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out.println("HOWZIT");
			//This strings contain random strings that the server will randomly answer to its client
			String[] happyResponse = {"Thanks", "Hooray!", "To You"};
			String[] elseResponse = {"Escusez-moi?","Oh ok!","Nein"};
			//Randomization
			Random rand = new Random();
	
			//Loop Flag
			boolean running = true;
			while(running)
			{
				String response = in.readLine();
				int responseCount = 0;
				
				if(response.startsWith("SUPER"))
				{
					for(int i = 1; i<=4;i++)
					{
						int randomHappyResponse = rand.nextInt(happyResponse.length);
						int randomElseResponse = rand.nextInt(elseResponse.length);
						
						out.println("ANSWER the Question:`How do you say Happy Birthday in South Africa?` or CHEERS");
						String answer =  in.readLine();
						if(answer.contains("ANS Gelukkige") || answer.contains("ANS Verjaarsdag"))
						{
							responseCount++;
							out.println( "0" + i + " Baie dankie!.");
						}else if(answer.startsWith("ANS Happy"))
						{
							responseCount++;
							out.println( "0" + i + " " + happyResponse[randomHappyResponse]);
						}else if(answer.contains("ANS Usuku olumnandi") || answer.contains("ANS lokuzalwa"))
						{
							responseCount++;
							out.println( "0" + i + " Yebo - kuhle!");
						}else if(answer.startsWith("CHEERS"))
						{
							responseCount++;
							int tempIndex = i;
							tempIndex--;
							out.println("0" + responseCount + " OK BYE - " + tempIndex + " answers provided (inside loop)");
							running = false;
							socket.close();
							out.close();
							in.close();
							return;
						}else
						{
							if(!answer.startsWith("CHEERS"))
							{
								responseCount++;
							}
							out.println("0" + responseCount + " " + elseResponse[randomElseResponse]);
						}
					}//end of question loop
					
					//This will be executed when the client has responded 4 times
					String lastMessage = in.readLine();
					if(lastMessage.startsWith("CHEERS"))
					{
						responseCount++;
						out.println("0" + responseCount + " OK BYE - 04 answers provided");
						running = false;
						socket.close();
						out.close();
						in.close();
						return;
					}else 
					{
						out.println("HAMBA KAHLE - you had 4 chances");
						running = false;
						socket.close();
						out.close();
						in.close();
					}
				}
			}
		} catch (IOException e) {
			
			System.err.println("The server might be already in use, please retry your connection or restart the server.");
		}
	}

}
