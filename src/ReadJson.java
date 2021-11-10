package src;

import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadJson {
    

    public ReadJson() {}

    public AccessControlObj[] read(String fileName){

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(Paths.get(fileName).toFile(), AccessControlObj[].class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}


