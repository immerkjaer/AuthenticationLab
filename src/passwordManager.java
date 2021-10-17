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

public class passwordManager {

    public static void main(String[] args){
        try{
            addPassword("Tommy", "TheseTwoPasswordsAreNotTheSame");
            addPassword("Not_Tommy", "TheseTwoRasswordsAreNotTheSame");
            System.out.println(authorizeUser("Tommy2", "TheseTwoRasswordsAreNotTheSame"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //add user with username and password. Generates random salt for the hash
    private static boolean addPassword(String user, String password) throws NoSuchAlgorithmException {
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
    public static String hashPasswordSHA255(String password, byte[] salt){
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
    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        //System.out.println("seed; " + random.getSeed(8));
        return salt;
    }

    //Not done, might delete later
    private static boolean authorizeUser(String username, String password) throws IOException {
        Path fileName = Path.of("passwords.txt");
        String fileContent = Files.readString(fileName);
        String[] users = fileContent.split("\n");

        boolean authorize = false;

        for (String s : users){
            String[] user = s.split(",");

            if (user[0].equals(username)){
                //System.out.println("User found with hash: " + user[1]);
                //System.out.println("The salt of the user is: " + user[2]);
                if (user[1].equals(hashPasswordSHA255(password,stringToByte(user[2])))){
                    authorize = true;
                }
            }
        }

        return authorize;
    }

    //made since writing a byte[] in file inputs the bytecode of the array. Translates it into readable string
    private static String byteToString(byte[] salt){
        String s = "";
        for (byte b: salt){
            s += b + " ";
        }
        return s;
    }

    private static byte[] stringToByte(String s){ //Translates readable string back into byte array
        String[] bytes = s.split("\\s+");

        byte[] salt = new byte[16];
        for (int j = 0; j < 16; j++){
            salt[j] = (byte) (Integer.parseInt(bytes[j]));
            //System.out.println("byte: " + salt[j]);
        }
        return salt;
    }
}
