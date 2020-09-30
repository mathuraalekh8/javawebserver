package webserver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class WorkerThread implements Runnable{

	private Socket clientSocket = null;
	static final File ROOT_PATH = new File(".");
	static final String INDEX_FILE = "index.html";
	
	public WorkerThread(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		InputStream input = null;
		OutputStream output = null;
		BufferedOutputStream dataOut = null;
		PrintWriter out = null;
		try {
			 input  = clientSocket.getInputStream();
			 
			 dataOut = new BufferedOutputStream(clientSocket.getOutputStream());
			 out = new PrintWriter(clientSocket.getOutputStream());
			
			 File file = new File(ROOT_PATH, INDEX_FILE);
			 int fileLength = (int) file.length();
			 byte[] fileData = readFileData(file, fileLength);
			 /***********This piece of code was copied to see how the response is sent back to client*************/
			 out.println("HTTP/1.1 200 OK");
			 out.println("Server: Java HTTP Server by Aalekh : 1.0");
			 out.println("Date: " + new Date());
			 out.println("Content-type:text/html ");
			 out.println("Content-length: " + fileLength);
			 out.flush();
			 /***********This piece of code was copied to see how the response is sent back to client*************/
			 dataOut.write(fileData, 0, fileLength);
			 dataOut.flush();
			 
			 System.out.println("Request processed: " + new Date());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				input.close();
				out.close();
				dataOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
	}
	
	private byte[] readFileData(File file, int fileLength) throws IOException {
		FileInputStream fileIStream = null;
		byte[] fileData = new byte[fileLength];
		try {
			fileIStream = new FileInputStream(file);
			fileIStream.read(fileData);
		} finally {
			if (fileIStream != null) 
				fileIStream.close();
		}
		return fileData;
	}

}
