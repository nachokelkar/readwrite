package readwrite;

public class ReadThread extends Thread {
    private MyFileHandler fileHandler;

    public ReadThread(MyFileHandler filehandler){
        this.fileHandler = filehandler;
    }

    public void run(){
        this.fileHandler.read();
    }
}
