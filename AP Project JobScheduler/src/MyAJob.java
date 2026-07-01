import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class MyAJob extends AJob {
    
    private String filepath;

    public MyAJob (String filepath){
        this.filepath = filepath;
    }
    
    @Override
    public Stream<Pair<String,String>> execute(){
        File f = new File(filepath);
        try { 
           Scanner sc = new Scanner(f);
           return sc.useDelimiter("\\s").tokens().filter(word->word.length()>3&&this.onlyLetters(word)).map(x->this.ciao(x));
        } catch (IOException ex) {
            return Stream.empty();
        }  
    }
    
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
