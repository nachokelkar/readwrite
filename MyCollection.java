public class MyCollection {

    private static int readCounter;
    private static int writeCounter;
    private Employee[] collection;
    private static volatile MyCollection instance;

    private int top;

    private MyCollection(){
        readCounter = 0;
        writeCounter = 0;
        collection = new Employee[300];
        top = 0;
    }

    public static MyCollection getInstance(){
        if(instance == null){
            synchronized (MyCollection.class){
                if(instance == null){
                    instance = new MyCollection();
                }
            }
        }
        return instance;
    }

    public Employee get() throws CollectionException{
        if(top<0){
            throw new CollectionException("No records to get");
        }

        top-=1;

        return collection[top];
    }

    public void add(Employee record) throws CollectionException{
        if(top>=300){
            throw new CollectionException("Collection limit reached");
        }

        if(record == null){
            throw new CollectionException("Null input");
        }

        collection[top] = record;
        top+=1;
    }

    public static void incrementRead(){
        readCounter += 1;
    }

    public static void incrementWrite(){
        writeCounter += 1;
    }

    public static int getReadCounter() {
        return readCounter;
    }

    public static int getWriteCounter() { return writeCounter; }

}