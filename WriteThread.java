package readwrite;

public class WriteThread extends Thread {
    private MyFileHandler fileHandler;

    public WriteThread(MyFileHandler filehandler){
        this.fileHandler = filehandler;
    }

    public void run(){
        this.fileHandler.write();
    }
}
