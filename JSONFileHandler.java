package read-write;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;


public class JSONFileHandler implements MyFileHandler{

    public void write(){
        try {
            FileWriter file = new FileWriter("/Users/pranavkelkar/Downloads/GENemployee.json");
            MyCollection instance = MyCollection.getInstance();
            Employee emp;

            for(int i=0;i<100;i++) {
                try {
                    emp = instance.get();
                    JSONObject obj = new JSONObject();
                    obj.put("firstName",emp.getFirstName());
                    obj.put("lastName",emp.getLastName());
                    obj.put("dateOfBirth",emp.getDateOfBirth());
                    obj.put("experience",emp.getExperience());


                    file.write(obj.toJSONString());
                    instance.incrementWrite();
                }
                catch (CollectionException e){
                    System.out.println(e);
                }

            }

            file.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public void read()
    {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("/Users/pranavkelkar/Downloads/employee.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray employeeList = (JSONArray) obj;
            //Iterate over employee array
            employeeList.forEach( emp -> {
                try {
                    parseEmployeeObject( (JSONObject) emp );
                } catch (java.text.ParseException e) {
                    System.out.println(e);
                }
            });
        } catch (IOException | ParseException e) {
            System.out.println(e);
        }
    }

    private static void parseEmployeeObject(JSONObject employee) throws java.text.ParseException {
        //Create employee object within list
        Employee emp = new Employee();

        //Get employee first name
        String firstName = (String) employee.get("firstName");

        emp.setFirstName(firstName);
        //Get employee last name
        String lastName = (String) employee.get("lastName");
//        System.out.println(lastName);
        emp.setLastName(lastName);

        String  dateOfBirth = (String) employee.get("dateOfBirth");
//        System.out.println(dateOfBirth);
        emp.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth));

        long experience = (long) employee.get("experience");
//        System.out.println(experience);
        emp.setExperience(experience);

        MyCollection instance = MyCollection.getInstance();
        try{
            instance.add(emp);
            instance.incrementRead();
        }
        catch (CollectionException e){
            System.out.println(e);
        }

    }
}