import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author users
 */
public class JobScheduler<K,V> {
    
    public Stream<AJob<K,V>> emit(){
        return emitter.emit();
    }
    
    public Stream<Pair<K,V>> compute(Stream<AJob<K,V>> js){
        return js/*.parallel()*/.map(x->x.execute()).reduce(Stream.empty(), (c,e)->Stream.concat(c, e));
    }
    
    
    
    public Stream<Pair<K,List<V>>> collect(Stream<Pair<K,V>> comp){
        return convert(comp.collect(()->new HashMap<K,List<V>>(), (c,e)->this.accumulate(c, e), (c1, c2)->this.combine(c1, c2)).entrySet()).stream();
    }
    
    private List<Pair<K,List<V>>> convert(Set<Entry<K,List<V>>> s){
        List<Pair<K,List<V>>> l = new ArrayList<>();
        s.forEach(x->l.add(new Pair(x.getKey(),x.getValue())));
        return l;
    }
    
    private void accumulate(HashMap<K,List<V>> c, Pair<K,V> e){
        if(c.containsKey(e.getKey()))
            c.get(e.getKey()).add(e.getValue());
        else {
            ArrayList<V> a = new ArrayList<>();
            a.add(e.getValue());
            c.put(e.getKey(), a);
        }
    }
    
    private void combine(HashMap<K,List<V>> c1, HashMap<K,List<V>> c2){
        for (HashMap.Entry<K,List<V>> entry : c2.entrySet()){
            if(c1.containsKey(entry.getKey()))
                c1.get(entry.getKey()).addAll(entry.getValue());
            else c1.put(entry.getKey(), entry.getValue());
        }
    }
    
    public void output(Stream<Pair<K,List<V>>> coll){
        coll.parallel().forEach(x->printer.print(x));
        printer.closeWriter();
    }
    
    private Emitter emitter;
    private Printer printer;
    
    public JobScheduler(Emitter e, Printer p){
        this.emitter = e;
        this.printer = p;
    }
    
    public void setSource (Emitter e){
        this.emitter = e;
    }
    
    public void setSource (Printer p){
        this.printer = p;
    }
    
    public void allToghether(){
        this.output(this.collect(this.compute(this.emit())));
    }
}
