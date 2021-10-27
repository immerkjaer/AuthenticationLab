package src;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.ArrayList;
import java.sql.Timestamp;

public class passwordManager {

    private static class AuthorizedUser{
        public AuthorizedUser(String userId, Timestamp authTime){
            this.userId = userId;
            this.authTime = authTime;
        }

        public String userId;
        public Timestamp authTime; 
    }
    static ArrayList<AuthorizedUser> authorizedUsers = new ArrayList<AuthorizedUser>();
    private static int AUTH_TIMEOUT = 5000; // Milliseconds
    private static int UPDATE_INTERVAL = 1000; // Milliseconds


    public passwordManager(){
        try{
            addPassword("t", "p");
            addPassword("1", "1");
            // Eternal main loop; 1s update interval
            // This could run on a separate thread if needed
           // while(true){
                // Simple infinite timeout test demonstrating timeout.
                // Boolean output ignored here; would be used in real scenario
             //   authorizeUser("Tommy", "Password");
//                authorizeUser("evilhacker", "evilpassword");

//                checkExpiredAuths();
//                Thread.sleep(UPDATE_INTERVAL);
//            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private boolean isUserAuthorized(String userId){
        for (int i = 0; i < authorizedUsers.size(); i++){
            if (authorizedUsers.get(i).userId == userId) return true;
        }
        return false;
    }

    // Remove any authenticated users from list of authenticated users
    // if their timestamps are older than AUTH_TIMEOUT seconds
    private static void checkExpiredAuths(){
        for (int i = 0; i < authorizedUsers.size(); i++){
            Timestamp curTime = new Timestamp(System.currentTimeMillis());

            if ((curTime.getTime() - authorizedUsers.get(i).authTime.getTime()) > AUTH_TIMEOUT){
                System.out.println("Authentication for user " + authorizedUsers.get(i).userId + " expired");
                authorizedUsers.remove(i);
            }
        }
    }




    //add user with username and password. Generates random salt for the hash
    private boolean addPassword(String user, String password) throws NoSuchAlgorithmException {
        byte[] salt = generateSalt();
        try {
            FileWriter myWriter = new FileWriter("passwords.txt", true);
            myWriter.write(user + "," + hashPasswordSHA255(password, salt) + "," + byteToString(salt) + "\n");
            myWriter.close();
            System.out.println("Wrote something to the passwords file.");
        } catch (Exception e) {
            System.out.println("Something went wrong :/");
            e.printStackTrace();
        }
        return true;
    }

    //Takes a password and generates the hashed password
    public  String hashPasswordSHA255(String password, byte[] salt){
        String hash = "";

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length; i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hash = sb.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return hash;
    }

    //Generates a random salt
    private byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        //System.out.println("seed; " + random.getSeed(8));
        return salt;
    }

    //Not done, might delete later
    public boolean authorizeUser(String username, String password) throws IOException {
        Path fileName = Path.of("passwords.txt");
        String fileContent = Files.readString(fileName);
        String[] users = fileContent.split("\n");

        for (String s : users){
            String[] user = s.split(",");

            if (user[0].equals(username)){
                //System.out.println("User found with hash: " + user[1]);
                //System.out.println("The salt of the user is: " + user[2]);
                if (user[1].equals(hashPasswordSHA255(password,stringToByte(user[2])))){
                    // User is already authorized; do nothing
                    if (isUserAuthorized(username)){
                        System.out.println("User " + username + " is already authorized");
                        return true; 
                    } else {
                        authorizedUsers.add(new AuthorizedUser(username, new Timestamp(System.currentTimeMillis())));
                        System.out.println("User " + username + " has been authorized");
                        return true;
                    }
                }
            }
        }

        System.out.println("User " + username + " has not been authorized. Access denied");
        return false;
    }

    //made since writing a byte[] in file inputs the bytecode of the array. Translates it into readable string
    private String byteToString(byte[] salt){
        String s = "";
        for (byte b: salt){
            s += b + " ";
        }
        return s;
    }

    private byte[] stringToByte(String s){ //Translates readable string back into byte array
        String[] bytes = s.split("\\s+");

        byte[] salt = new byte[16];
        for (int j = 0; j < 16; j++){
            salt[j] = (byte) (Integer.parseInt(bytes[j]));
            //System.out.println("byte: " + salt[j]);
        }
        return salt;
    }
}
