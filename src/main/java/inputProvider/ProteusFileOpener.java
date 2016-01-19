package inputProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
/*
*Pretty straightforward object, implements DataProvider 
*(in case if any other data source would need to be implemented)
**/
public class ProteusFileOpener implements DataProvider {
	private Scanner connection;
	
	public ProteusFileOpener(String path) throws IOException{
		Path dataFile = Paths.get(path);
		connection = new Scanner(dataFile);
	}
	
	public Scanner getData(){
		return connection;
	}
}
