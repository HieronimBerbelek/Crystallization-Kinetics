package inputProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ProteusFileOpener {
	private Scanner connection;
	
	public ProteusFileOpener(String path) throws IOException{
		Path dataFile = Paths.get(path);
		connection = new Scanner(dataFile);
	}
	
	public Scanner getDataFile(){
		return connection;
	}
}
