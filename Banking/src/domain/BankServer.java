package domain;


import exceptions.OverdraftException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author BJTU16301130
 */
public class BankServer {

    private ObservableList<Customer> customers = null;

    private static BankServer bankServerInstance = new BankServer();


    public static BankServer getBankServer(){
        return bankServerInstance;
    }

    private BankServer(){
        customers = FXCollections.observableArrayList();
        loadFromFile();
    }

    public ObservableList<Customer> getCustomers(){
        return customers;
    }


    /**
     * Get account by account id.
     *
     * @param id the account id
     * @return the account
     */
    private Account searchAccount(String id){
        for(Customer customer : customers){
            for(Account account : customer.getAccounts()){
                if(account.getAccountId().equals(id)){
                    return account;
                }
            }
        }
        return null;
    }

    public void deleteCustomer(Customer customer){
        getBankServer().getCustomers().remove(customer);
    }

    public void addCustomer(Customer customer){
        getBankServer().getCustomers().add(customer);
    }


    /**
     * @param telegraph     received request
     * @param commandSocket used to resend the updated customer
     */
    private void handleRequest(Telegraph telegraph, Socket commandSocket){
        Account account = searchAccount(telegraph.getAccId());

        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(commandSocket.getOutputStream());

            //confirm to have found the account
            if(account != null){
                if(telegraph.getCommand() == Telegraph.Command.DEPOSIT){
                    account.deposit(telegraph.getAmt());
                }else if(telegraph.getCommand() == Telegraph.Command.WITHDRAW){
                    account.withdraw(telegraph.getAmt());
                }
                //send the message of success and update the customer info
                telegraph.setIfSuccess(true);
                objectOutputStream.writeObject(telegraph);
                objectOutputStream.writeObject(account.getOwner());
            }else{
                //account not found
                objectOutputStream.writeObject(telegraph);
            }
        }catch(IOException | OverdraftException ex){
            ex.printStackTrace();
        }
    }


    /**
     * Service.
     * launch a thread for each client connection and start to communicate
     *
     * @throws IOException the io exception
     */
    public void service() throws IOException{
        ServerSocket serverSocket = new ServerSocket(3000);
        while(true){
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try{
                    System.out.println("connecting succeed");
                    communicate(socket);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }).start();
        }
    }

    private void communicate(Socket socket){
        try{
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String id = reader.readLine();
            String pw = reader.readLine();

            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);

            boolean exit = false;
            Customer loginCustomer = loginVerify(id, pw);

            if(loginCustomer == null){
                exit = true;

                writer.println("Login Failed");
                System.out.println("Login Failed");

                socket.close();
            }else{
                writer.println("Login Succeeded");
                System.out.println("Login Succeeded");

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                objectOutputStream.writeObject(loginCustomer);
                objectOutputStream.flush();
            }

            if(! exit){
                //used to handle commands from client
                ServerSocket serverSocket = new ServerSocket(3001);
                while(true){
                    Socket commandSocket = serverSocket.accept();
                    System.out.println("get a command");
                    ObjectInputStream objIn = new ObjectInputStream(commandSocket.getInputStream());

                    //get the requests and carry out operations
                    Telegraph telegraph = (Telegraph)objIn.readObject();
                    if(telegraph != null){
                        handleRequest(telegraph, commandSocket);
                    }
                    commandSocket.close();
                    System.out.println("finish handling the command");
                }
            }
        }catch(IOException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }


    private Customer loginVerify(String id, String pw){
        for(Customer customer : getBankServer().getCustomers()){
            if(customer.getId().equals(id)){
                if(customer.getPassword().equals(pw)){
                    return customer;
                }
            }
        }
        return null;
    }


    private void loadFromFile(){
        try{
            File file = new File("bankInfo");
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));

            Integer size = (Integer)in.readObject();

            for(int i=0; i<size; i++){
                customers.add((Customer)in.readObject());
            }

        }catch(IOException | ClassNotFoundException ex){
            ex.printStackTrace();
        }

    }

    public void saveToFile(){
        try{
            File file = new File("bankInfo");
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));

            out.writeObject(customers.size());
            for(Customer customer : customers){
                out.writeObject(customer);
            }

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}

