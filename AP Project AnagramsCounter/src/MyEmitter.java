import java.io.File;
import java.util.stream.Stream;


public class MyEmitter extends Emitter {
    
    private final File source;
    
    public MyEmitter(File source){
        this.source=source;
    }

    @Override
    //genera uno stream contenente un MyAJob per ogni file di testo contenuto nella directory il cui filepath
    //è stato dato al costruttore, al costruttore di ogni MyAJob viene passato il filepath del corrispettivo file
    public Stream<AJob<String,String>> emit() {
        return Stream.of(source.listFiles())
            .filter(file -> !file.isDirectory())
            .map(File::getPath)
            .filter(filename -> filename.endsWith(".txt"))
            .map(filename->new MyAJob(filename));
    }
    
}
