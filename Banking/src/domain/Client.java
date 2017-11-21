package domain;


import java.io.*;
import java.net.Socket;

public class Client{

    public Client(){ }

    public Customer login(String id, String pw){
        try{
            Socket socket = new Socket("127.0.0.1",3000);
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out,true);

            writer.println(id);
            writer.println(pw);

            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String msg = reader.readLine();

            if(msg.equals("Login Succeeded")){
                ObjectInputStream objectInputStream = new ObjectInputStream(in);
                return (Customer)objectInputStream.readObject();
            }else {
                //login failed
                socket.close();
                return null;
            }

        }catch(IOException | ClassNotFoundException ex){
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * Send request and if the operation has succeeded, return the updated customer information.
     *
     * @param telegraph the telegraph
     * @return the customer, null if the operation has failed.
     */
    public Customer sendRequest(Telegraph telegraph){
        try{
            Socket commandSocket = new Socket("127.0.0.1",3001);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(commandSocket.getOutputStream());

            objectOutputStream.writeObject(telegraph);
            objectOutputStream.flush();

            ObjectInputStream objectInputStream = new ObjectInputStream(commandSocket.getInputStream());
            telegraph = (Telegraph)objectInputStream.readObject();

            if(telegraph.getIfSuccess()){
                return (Customer)objectInputStream.readObject();
            }
        }catch(IOException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return null;
    }
}






