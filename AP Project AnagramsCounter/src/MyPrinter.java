import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MyPrinter extends Printer {
    
    private FileWriter writer;
    
    //istanzia un oggetto FileWriter associato al file count_anagrams.txt inserito nella cartella
    //passata come argomento
    public MyPrinter(File folder) throws IOException{
        this.writer =new FileWriter(folder.getAbsolutePath()+"/count_anagrams.txt");
    }
    
    @Override
    //chiude il FileWriter
    public void closeWriter(){
        try {
            this.writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    //stampa sul file count_anagrams.txt la coppia passata come valore nella forma
    //chiave, valore \n
    public void print(Pair source) {
        System.out.println(source.getKey()+","+((List<String>)source.getValue()).size());
        try {
            writer.write(source.getKey()+","+((List<String>)source.getValue()).size()+"\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
