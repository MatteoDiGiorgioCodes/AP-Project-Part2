import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;


public class MyAJob extends AJob {
    
    private final String filepath;

    public MyAJob (String filepath){
        this.filepath = filepath;
    }
    
    @Override
    //l'esecuzione dell'AJob genera uno Stream contenente una coppia di stringhe per ogni parola di più di
    //3 lettere contenuta nel file indicato dal filepath passato al costruttore, tale che per ognuna
    //il primo valore sia il ciao della parola e il secondo la parola stessa
    public Stream<Pair<String,String>> execute(){
        File f = new File(filepath);
        try { 
           Scanner sc = new Scanner(f);
           return sc.useDelimiter("\\s").tokens().filter(word->word.length()>3&&this.onlyLetters(word)).map(x->this.ciao(x));
        } catch (IOException ex) {
            return Stream.empty();
        }  
    }
    
    //restituisce true se la stringa passata contiene solo lettere
    public boolean onlyLetters(String s) {
      if (s == null) { 
         return false;
      }
      int len = s.length();
      for (int i = 0; i < len; i++) {
         if ((Character.isLetter(s.charAt(i)) == false)) {
            return false;
         }
      }
      return true;
    }
    
    //restituisce un oggetto Pair avente come valore la stringa passata per parametro e come chiave il suo 
    //ciao
    public Pair<String,String> ciao(String word){
        char[] wordArray = word.toLowerCase().toCharArray();
        ArrayList<Character> wordList = new ArrayList<>();
        for(char c : wordArray)
            wordList.add(c);
        wordList.sort((x,y)->Character.compare(x, y));
        String ciao ="";
        for(char c : wordList)
            ciao=ciao+c;
        return new Pair(ciao, word);
    }
    
}
