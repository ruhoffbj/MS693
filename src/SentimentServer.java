
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class SentimentServer {
	
    public static void main(String[] args) throws IOException {
        SentimentWorker wHolding = new SentimentWorker(null); //We do this to prevent garbage collection.
        wHolding.initializeNLP();
        int maxPendingConn = 30;
        int port = 4444;
        Socket sock;
        @SuppressWarnings("resource")
		ServerSocket servsock = new ServerSocket(port, maxPendingConn);
		
        while(true)
        {
    		sock=servsock.accept();
            if (sock != null)
            {
                SentimentWorker w = new SentimentWorker(sock);
                w.start();
            }
        }	
    }
}


class SentimentWorker extends Thread
{
    private Socket connectionSocket;
    private static List<NLP> freeList = new ArrayList<NLP>();
    
    public void initializeNLP() {
        synchronized (freeList) {
        	if(freeList.size() == 0) {
        		for(int x=0; x< 2; x++) {
            		NLP nlp = new NLP();
                    nlp.init();
                    freeList.add(nlp);
        		}
        	} 
        }
    }
    
    
    public SentimentWorker(Socket c) throws IOException
    {
        connectionSocket = c;
    }
 
    public void run() 
    {
        try
        {    
        	BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        	DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            String clientSentence = inFromClient.readLine();

            NLP nlp = findAvailableNLPClass();
            
            String senimentValue = nlp.findSentiment(clientSentence);
            outToClient.writeBytes(senimentValue);
            outToClient.flush();

            putNLPClassOnFreelist(nlp);
        }
        catch(IOException e)
        {
            System.out.println("Errore: " + e);
        }
    }


	private void putNLPClassOnFreelist(NLP nlp) {
		synchronized (freeList) 
		{
			freeList.add(nlp);
		}
	}


	private NLP findAvailableNLPClass() {
		NLP nlp;
		synchronized (freeList) {
			if(freeList.size() > 0) {
				nlp = freeList.get(0);
				freeList.remove(0);
			} else {
		        nlp = new NLP();
		        nlp.init();
			}
		}
		return nlp;
	}
	
}   
