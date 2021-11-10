package src;

import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadJson {
    

    public ReadJson() {}

    public void read(String fileName){

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AccessControlObj[] accessControlList = objectMapper.readValue(Paths.get(fileName).toFile(), AccessControlObj[].class);
            System.out.println(accessControlList[1].toString());
            for (var ac : accessControlList)
                System.out.println(ac.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}


