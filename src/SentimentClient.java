import java.io.*;
import java.net.*;

public class SentimentClient  {

	public static void main(String[] arg) throws IOException {
        Socket sock;
        BufferedReader dis;
        PrintWriter dat;
        String textMsg = "";
 
		// Make sure that one parameter was passed in the command line
		if (arg.length != 1) {
			textMsg = "I hope that ted cruz loses big.  I hate ted so much.    I don't like this.";
		} else {
			textMsg = arg[0];
		}
		try {
	        sock = new Socket("node01",4444);
	    
	        dis = new BufferedReader( new InputStreamReader(sock.getInputStream()) );
	        dat = new PrintWriter( sock.getOutputStream() );
			
			// Send the request to the server
	        dat.println( textMsg );
	        dat.flush();

	        String fromServer = dis.readLine();
	        while ( !fromServer.contains("[~DONE~]") ) 
	        {
	        	 System.out.println("Got this from server:" + fromServer);
	        	 fromServer = dis.readLine();
	        }
	        System.out.println("Got this from server:" + fromServer); 
	        
	        sock.close();
		} catch (NumberFormatException e) {
		    System.out.println("The parameter needs to be a number.");
		}
	}

}
