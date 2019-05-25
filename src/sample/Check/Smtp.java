package sample.Check;

import sample.MailClient.SendEmailWindows;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;

public class Smtp {
    static Base64.Encoder encoder = Base64.getEncoder();
    static PrintWriter writer;
    static BufferedReader reader;
    static SendEmailWindows sendEmailWindows = new SendEmailWindows();

    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return encoder.encodeToString(buffer);
    }

    public Smtp(String user, String password){
        try {
            Socket socket = new Socket("smtp.126.com", 25);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            writer = new PrintWriter(outputStream, true);
            System.out.println(reader.readLine());


            //登陆
            writer.println("helo hi");
            System.out.println(reader.readLine());
            writer.println("auth login");
            System.out.println(reader.readLine());
            writer.println(encoder.encodeToString(user.getBytes("UTF-8")));
            System.out.println(reader.readLine());
            writer.println(encoder.encodeToString(password.getBytes("UTF-8")));
            System.out.println(reader.readLine());
        }catch (Exception e){

        }
    }

    public static void sendEmail(String user, String password, String reciver, String subject, String text, ArrayList fileNames) {
        try {
            writer.println("Mail from: <" + user + ">");
            System.out.println(reader.readLine());
            writer.println("rcpt to:  <" + reciver +">");
            System.out.println(reader.readLine());

            //数据部分
            writer.println("data");
            System.out.println(reader.readLine());
            writer.println("from:" + user);
            writer.println("to:" + reciver);



            writer.println("Subject: =?UTF-8?B?" + encoder.encodeToString(subject.getBytes("UTF-8")) + "?=");
            writer.println("Mime-Version:1.0");
            writer.println("Content-Type:multipart/mixed;boundary=\"a\"");
            writer.println();

            writer.println("--a");
            writer.println("content-type:text/plain");
            writer.println("charset=\"UTF-8\" ");
            writer.println("Content-Transfer-Encoding: quoted-printable");
            writer.println();
            writer.println(text);

            for(int i = 0; i < fileNames.size(); i++) {
                String[] fileName = ((String)fileNames.get(i)).split("\\\\");
                if (fileName.length > 1) {
                    writer.println("--a");
                    writer.println("Content-type:application/octet-stream");
                    writer.println("Content-Disposition:attachment;");
                    writer.println(" filename=" + fileName[fileName.length - 1]);
                    writer.println("Content-Transfer-Encoding:base64");
                    writer.println();
                    writer.println(encodeBase64File((String)fileNames.get(i)));
                }
            }


            writer.println("--a--");
            writer.println(".");
            writer.println();

            String afterSend = reader.readLine();
            if(afterSend.startsWith("250")){
                sendEmailWindows.success();
            }
            else{
                sendEmailWindows.fail();
            }
            System.out.println(afterSend);
        }
        catch (Exception e){

        }
    }
}
