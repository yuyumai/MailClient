package sample.Check;

import java.io.*;
import java.net.Socket;

public class Pop3 {

    static PrintWriter writer;
    static BufferedReader reader;

    public boolean check(String user, String password){
        try {
            Socket socket = new Socket("pop3.126.com", 110);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            writer = new PrintWriter(outputStream, true);
            System.out.println(reader.readLine());

            writer.println("user " + user);
            System.out.println(reader.readLine());
            writer.println("pass " + password);
            String text =reader.readLine();
            if(text.startsWith("+OK")){
                String[] temp = text.split(" ");
                System.out.println(text);
                return true;
            }

        }catch(Exception e){

        }
        return false;
    }

    public StringBuffer getContent(String i) throws IOException {
        StringBuffer content = new StringBuffer("");
        writer.println("retr " + i);
        while (true) {
            String reply = reader.readLine();
            content.append(reply + "\n");
            if (reply.toLowerCase().equals(".")) {
                break;
            }
        }
        return  content;
    }

    public String getMailAmount() throws IOException {
        writer.println("stat");
        String[] temp = reader.readLine().split(" ");
        return temp[1];
    }

    public void deleteMail(String i) throws IOException {
        writer.println("dele " + i);
        reader.readLine();
    }
}
