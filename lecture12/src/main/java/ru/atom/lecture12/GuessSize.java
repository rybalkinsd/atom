package ru.atom.lecture12;

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.System.out;

public class GuessSize {
    public static void main(String[] args) throws Exception {
        out.println(VM.current().details());
        int[] intArray = new int[1000];
        Integer[] integerArray = new Integer[1000];
        ArrayList<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();
        HashSet<Integer> hashSet = new HashSet<>();
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();

        for (int i = 0; i < 1000; i++) {
            Integer io = i;
            intArray[i] = io;
            integerArray[i] = io;
            arrayList.add(io);
            linkedList.add(io);
            hashSet.add(io);
            hashMap.put(i, i);
            concurrentHashMap.put(i, i);
        }

        arrayList.trimToSize();

        PrintWriter pw = new PrintWriter(out);
        pw.println("====================================================================");
        pw.println(">> new Integer(10)");
        pw.println(GraphLayout.parseInstance(new Integer(10)).toPrintable());
        pw.println("====================================================================");
        pw.println(">> new Long(10)");
        pw.println(GraphLayout.parseInstance(new Long(10)).toPrintable());
        pw.println("====================================================================");
        pw.println(">> new int[1000]");
        pw.println(GraphLayout.parseInstance((Object) intArray).toFootprint());
        pw.println("====================================================================");
        pw.println(">> new Integer[1000]");
        pw.println(GraphLayout.parseInstance((Object) integerArray).toFootprint());
        pw.println("====================================================================");
        pw.println(">> new ArrayList<Integer>(1000)");
        pw.println(GraphLayout.parseInstance(arrayList).toFootprint());
        pw.println("====================================================================");
        pw.println(">> new LinkedList<Integer>(1000)");
        pw.println(GraphLayout.parseInstance(linkedList).toFootprint());
        pw.println("====================================================================");
        pw.println(">> new HashSet<Integer>(1000)");
        pw.println(GraphLayout.parseInstance(hashSet).toFootprint());
        pw.println("====================================================================");
        pw.println(">> new HashMap<Integer>(1000)");
        pw.println(GraphLayout.parseInstance(hashMap).toFootprint());
        pw.println("====================================================================");
        pw.println(">> new ConcurrentHashMap<Integer>(1000)");
        pw.println(GraphLayout.parseInstance(concurrentHashMap).toFootprint());
        pw.println("====================================================================");
        pw.println(">> new ArrayList<Integer>(1000).stream()");
        pw.println(GraphLayout.parseInstance(arrayList.stream()).toFootprint());
        pw.println("====================================================================");
        pw.println(">> new String(\"Hello, World!\")");
        pw.println(GraphLayout.parseInstance("Hello, World!").toPrintable());
        pw.println("====================================================================");
        pw.close();
    }
}
