/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class MyPrinter extends Printer {
    
    private FileWriter writer;
    
    public MyPrinter(File folder) throws IOException{
        this.writer =new FileWriter(folder.getAbsolutePath()+"/count_anagrams.txt");
    }
    
    @Override
    public void closeWriter(){
        try {
            this.writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void print(Pair source) {
        System.out.println(source.getKey()+","+((List<String>)source.getValue()).size());
        try {
            writer.write(source.getKey()+","+((List<String>)source.getValue()).size()+"\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
