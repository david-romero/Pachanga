/**
 * 
 */
package com.p.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author David
 *
 */
public class ConvertCSVToJson {

	static List<String> lines = Lists.newArrayList();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		BufferedReader br = null;
		 
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("C:\\Medidas2.csv"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				lines.add(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		try {
			 
 
			File file = new File("C:\\Medidas.json");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for ( String line : lines ){
				bw.write(line + ",\n");
			}
			
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
