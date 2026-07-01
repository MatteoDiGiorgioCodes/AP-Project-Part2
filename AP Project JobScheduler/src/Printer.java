
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
abstract public class Printer<K,V> {
    abstract public void print(Pair<K,List<V>> source);
    abstract public void closeWriter();
}
