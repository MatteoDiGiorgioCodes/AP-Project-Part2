import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class AnagramsCounter {
    //chiede di inserire il path di una directory, stampa in un nuovo file nella stessa directory
    //la lista dei ciao di ogni parola composta da più tre lettere in ogni file di testo nella directory 
    //seguiti dal conteggio delle loro rispettive occorrenze
    public static void main(String args[]){
        Boolean finished = false;
        while(!finished){ 
            try {
                System.out.println("Insert absolute path of the directory to use as source");
                Scanner s = new Scanner (System.in);
                File source =new File(s.nextLine());
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
