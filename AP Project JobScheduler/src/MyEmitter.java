/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author user
 */
public class MyEmitter extends Emitter {
    
    private final File source;
    
    public MyEmitter(File source){
        this.source=source;
    }

    @Override
    public Stream<AJob<String,String>> emit() {
        return Stream.of(source.listFiles())
            .filter(file -> !file.isDirectory())
            .map(File::getPath)
            .filter(filename -> filename.endsWith(".txt"))
            .map(filename->new MyAJob(filename));
    }
    
}
