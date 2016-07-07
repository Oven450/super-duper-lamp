package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFile {

		private String path;
		private boolean res;
		
		public ReadFile(String filePath, boolean res) {
			this.path = filePath;
			this.res = res;
		}
		
		public String[] openFile() throws IOException {
			BufferedReader reader = null;
			if (res) {
				reader = new BufferedReader(new InputStreamReader(getClass().getResource(path).openStream()));
			} else {
				FileReader fr = new FileReader(path);
				reader = new BufferedReader(fr);
			}
			
			int numberOfLines = readLines(path);
			
			String[] out = new String[numberOfLines];
			
			for (int i = 0; i < numberOfLines; i++) {
				out[i] = reader.readLine();
			}
			
			reader.close();
			
			return out;
		}
		
		public int readLines(String filePath) throws IOException{
			BufferedReader reader = null;
			if (res) {
				reader = new BufferedReader(new InputStreamReader(getClass().getResource(path).openStream()));
			} else {
				FileReader fr = new FileReader(path);
				reader = new BufferedReader(fr);
			}
			
			int out = 0;
			
			while (reader.readLine() != null) {
				out++;
			}
			
			reader.close();
			
			return out;
		}
}
