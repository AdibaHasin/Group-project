import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    // Server data
    ArrayList<ArrayList<String>> allServerInfo = new ArrayList<>();
    ArrayList<ArrayList<String>> initialAllServerInfo = new ArrayList<>();
    ArrayList<ArrayList<String>> sortOrder = new ArrayList<>();
    
  

    public static void main(String args[]) {
            Client client = new Client("127.0.0.1", 8096);
}
    private Socket socket               = null;
    private BufferedReader in           = null;
    private DataOutputStream out        = null;

    public Client(String address, int port) {
        // establish a connection
        try {

            // Connect to the server
            socket = new Socket(address, port);

            // Commands received from server
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Commands sent to the server
            out = new DataOutputStream(socket.getOutputStream());

        } catch(UnknownHostException u) { System.out.println(u);
        } catch(IOException i) { System.out.println(i); }
           // Say Hello and sign in to Server.
            ClientSetup();

            // Schedule all jobs based on default algorithm unless specified otherwise.
            ClientScheduler();

            // Close connection to server once all jobs have been scheduled.
            sendCommand("QUIT");

           try {

            out.close();
            socket.close();
            in.close();

        } catch(IOException i) { System.out.println(i); }

    }
    public void ClientSetup() {

        // Say Hello and sign in
        sendCommand("HELO");
        sendCommand("AUTH "+System.getProperty("user.name"));

    }
    public void ClientScheduler() {

        String currentJob = sendCommand("REDY");
        String[] currentJobDetails = currentJob.split(" ");
        String[] status = {""};

       
        initialAllServerInfo = allServerInfo;

       
        int indexOfLargestServer = 0;
        for(int i = 0; i < allServerInfo.size(); i++) {
            if( Integer.parseInt(allServerInfo.get(indexOfLargestServer).get(4)) < Integer.parseInt(allServerInfo.get(i).get(4)) )
                indexOfLargestServer = i;
        }

        findAllServerInfoSortOrder();

        while(!currentJob.equals("NONE") && !status[0].equals("ERR:")) {

            currentJobDetails = currentJob.split(" ");
            String serverType = "";
            String serverID = "";

            

            // AllToLargest
           
                serverType = allServerInfo.get(indexOfLargestServer).get(0);
                serverID = "0";
            
}
}
 public String sendCommand(String argument){

        try {
           

            // Read and return response from the server.
            String serverResponse = in.readLine();
            

        } catch(IOException i) { System.out.println(i); }

        return "ERR: No Response from Server.";

    }
    public void findAllServerInfoSortOrder() {

        for(int i = 0; i < allServerInfo.size(); i++) {

            if(!isServerTypeInList(allServerInfo.get(i).get(0))) {
                addServerType(allServerInfo.get(i).get(0), allServerInfo.get(i).get(4));
            }

        }
}
   public int addServerType(String serverType, String coreCount) {

        ArrayList<String> temp = new ArrayList<>();
        temp.add(serverType);
        temp.add(coreCount);

        for(int i = 0; i < sortOrder.size(); i++) {
            if(Integer.parseInt(coreCount) < Integer.parseInt(sortOrder.get(i).get(1))) {
                sortOrder.add(i, temp);
                return 0;
            }
        }
sortOrder.add(temp);
        return 0;
}
 public boolean isServerTypeInList(String otherServerType) {

        for(ArrayList<String> server: sortOrder) {

            if(server.get(0).equals(otherServerType))
                return true;

        }

        return false;

    }
}

