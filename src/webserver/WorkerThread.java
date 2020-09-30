package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

public class WorkerThread implements Runnable{

	private Socket clientSocket = null;
	
	public WorkerThread(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		InputStream input = null;
		OutputStream output = null;
		try {
			 input  = clientSocket.getInputStream();
			 output = clientSocket.getOutputStream();
			output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " + new Date() + "").getBytes());
			
            System.out.println("Request processed: " + new Date());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				output.close();
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
	}

}
