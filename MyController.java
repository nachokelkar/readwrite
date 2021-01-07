package read-write;

public class MyController {
    public static void main(String[] args) {
        CSVFileHandler csvFH = new CSVFileHandler();
        JSONFileHandler jsonFH = new JSONFileHandler();
        XMLFileHandler xmlFH = new XMLFileHandler();

        ReadThread csvRead = new ReadThread(csvFH);
        ReadThread jsonRead = new ReadThread(jsonFH);
        ReadThread xmlRead = new ReadThread(xmlFH);

        WriteThread csvWrite = new WriteThread(csvFH);
        WriteThread jsonWrite = new WriteThread(jsonFH);
        WriteThread xmlWrite = new WriteThread(xmlFH);

        csvRead.start();
        jsonRead.start();
        xmlRead.start();

        try{
            csvRead.join();
            jsonRead.join();
            xmlRead.join();
        }
        catch (InterruptedException e){
            System.out.println(e);
        }

        MyCollection instance = MyCollection.getInstance();

        System.out.println("------------\nAll Reads Complete\n------------");
        System.out.println("READ COUNTER = " +instance.getReadCounter());

        csvWrite.start();
        jsonWrite.start();
        xmlWrite.start();

        try{
            csvWrite.join();
            jsonWrite.join();
            xmlWrite.join();
        }
        catch (InterruptedException e){
            System.out.println(e);
        }

        System.out.println("------------\nAll Writes Complete\n------------");
        System.out.println("WRITE COUNTER = " +instance.getWriteCounter());
    }
}
