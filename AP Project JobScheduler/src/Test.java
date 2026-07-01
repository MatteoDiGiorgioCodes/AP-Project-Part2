
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
public class Test {
    public static void main(String args[]){
        
        //"C:/Users/user/Desktop/aux_files"
        Boolean finished = false;
        while(!finished){ 
            try {
                System.out.println("Insert absolute path of the directory to use as source");
                Scanner s = new Scanner (System.in);
                File source =new File(/*s.nextLine()*/"C:/Users/user/Desktop/aux_files");
                if(source.isDirectory()){
                    MyEmitter me = new MyEmitter(source);
                    MyPrinter mp = new MyPrinter(source);
                    JobScheduler js = new JobScheduler(me, mp);
                    js.allToghether();
                    finished=true;
                }
                else System.out.println("Given path doesn't point to a directory, try again \n");
            } 
            catch (IOException ex) {
                System.out.println("Given path isn't valid, try again \n");
            }
        }    
        
        
    }
}
