package src;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class ReadJson {
    

    public ReadJson() {}

    public void read(String fileName){
        

        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader(fileName+".json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray jsonArray = (JSONArray) obj;
            System.out.println(jsonArray);
             
            //Iterate over employee array
 
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}


