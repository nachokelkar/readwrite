import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import com.opencsv.*;

public class CSVFileHandler implements MyFileHandler{
    public void read() {
        Scanner scanner = null;
        //todo : change the name of the collection
        MyCollection instance = MyCollection.getInstance();

        try {
            scanner = new Scanner(new File("/Users/pranavkelkar/Downloads/employee.csv"));
            Scanner dataScanner = null;
            int index = 0;
            while (scanner.hasNextLine()) {
                dataScanner = new Scanner(scanner.nextLine());
                dataScanner.useDelimiter(",");
                Employee emp = new Employee();
                while (dataScanner.hasNext()) {
                    String data = dataScanner.next();
                    //todo : use switch instead of if else
                    if (index == 0)
                        emp.setFirstName(data);
                    else if (index == 1)
                        emp.setLastName(data);
                    else if (index == 2) {
                        Date date = new SimpleDateFormat("dd/MM/yy").parse(data);
                        emp.setDateOfBirth(date);
                    } else if (index == 3)
                        emp.setExperience(Double.parseDouble(data));
                    else
                        System.out.println("invalid data::" + data);
                    index++;
                }

                instance.add(emp);
                instance.incrementRead();

                index = 0;
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(){
        File file = new File("/Users/pranavkelkar/Downloads/GENemployee.csv");
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
//            String[] header = { "Name", "Class", "Marks" };
//            writer.writeNext(header);
            // add data to csv

            MyCollection instance = MyCollection.getInstance();
            Employee emp1;

            for(int i = 0; i<100; i++){
                try {
                    emp1 = instance.get();
                    //todo : figure out way to avoid having " in the output files
                    String[] data1 = { emp1.getFirstName(), emp1.getLastName(), emp1.getDateOfBirth().toString(), emp1.getExperience()+""};
                    writer.writeNext(data1);
                    instance.incrementWrite();
                }
                catch (CollectionException e){
                    System.out.println(e);
                }
            }
            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
