package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartWebServer implements Runnable{

	private int serverdefaultPortNumber = 8080;
	private boolean serverStartedFlag = true;
	private ServerSocket serverSocket = null;
	private ExecutorService serverThreadPool =
	        Executors.newFixedThreadPool(20);
	private Thread runningThread= null;
	
	StartWebServer(int port){
		this.serverdefaultPortNumber = port;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		StartWebServer startServer = new StartWebServer(10124);
		new Thread(startServer).start();
		try {
			Thread.sleep(5*60*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Stopping Server");
		startServer.stop();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		synchronized(this){
			this.runningThread = Thread.currentThread();
		}
		try {
			this.serverSocket = new ServerSocket(this.serverdefaultPortNumber);
			System.out.println("Server started at port 10124..");
			while(serverStartedFlag){
				Socket clientSocket = this.serverSocket.accept();
				this.serverThreadPool.execute(
		                new WorkerThread(clientSocket));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public synchronized void stop(){
        this.serverStartedFlag = false;
        try {
            this.serverSocket.close();
            System.out.println("Server stopped at port 10124..");
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

}
