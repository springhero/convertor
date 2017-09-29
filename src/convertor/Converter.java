package convertor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Converter {

  public static void main(String[] args) {
    System.out.println("Running");
    BufferedReader br = null;
    FileReader fr = null;
    try {
      String fileName = System.getProperty("fileName"); 
      String output = System.getProperty("output");
      File file = new File(fileName);
      fr = new FileReader(fileName);
      br = new BufferedReader(fr);
      String sCurrentLine;
      JsonArray jsonArray = new JsonArray();
      while ((sCurrentLine = br.readLine()) != null) {
        String[] split = sCurrentLine.split("=");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("key", split[0]);
        jsonObject.addProperty("value", split[1]);
        jsonObject.addProperty("flags", 0);
        jsonArray.add(jsonObject);
      }
      writeFile(jsonArray, output, file);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null)
          br.close();
        if (fr != null)
          fr.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  private static void writeFile(JsonArray jsonArray, String output, File file) {
    try {
      String name = file.getName().split("\\.")[0];
      File outputFile = new File(output + "/" + name + ".json");
      FileOutputStream os = new FileOutputStream(outputFile, true);
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String temp = gson.toJson(jsonArray);
      bw.append(temp);
      bw.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
