package ru.atom.lecture12;

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class GuessSize {
    private static final Logger log = LoggerFactory.getLogger(GuessSize.class);
    
    public static void guess() throws Exception {
        log.info(VM.current().details());
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

        log.info("====================================================================");
        log.info(">> new Integer(10)");
        log.info(GraphLayout.parseInstance(new Integer(10)).toPrintable());
        log.info("====================================================================");
        log.info(">> new Long(10)");
        log.info(GraphLayout.parseInstance(new Long(10)).toPrintable());
        log.info("====================================================================");
        log.info(">> new int[1000]");
        log.info(GraphLayout.parseInstance((Object) intArray).toFootprint());
        log.info("====================================================================");
        log.info(">> new Integer[1000]");
        log.info(GraphLayout.parseInstance((Object) integerArray).toFootprint());
        log.info("====================================================================");
        log.info(">> new ArrayList<Integer>(1000)");
        log.info(GraphLayout.parseInstance(arrayList).toFootprint());
        log.info("====================================================================");
        log.info(">> new LinkedList<Integer>(1000)");
        log.info(GraphLayout.parseInstance(linkedList).toFootprint());
        log.info("====================================================================");
        log.info(">> new HashSet<Integer>(1000)");
        log.info(GraphLayout.parseInstance(hashSet).toFootprint());
        log.info("====================================================================");
        log.info(">> new HashMap<Integer>(1000)");
        log.info(GraphLayout.parseInstance(hashMap).toFootprint());
        log.info("====================================================================");
        log.info(">> new ConcurrentHashMap<Integer>(1000)");
        log.info(GraphLayout.parseInstance(concurrentHashMap).toFootprint());
        log.info("====================================================================");
        log.info(">> new ArrayList<Integer>(1000).stream()");
        log.info(GraphLayout.parseInstance(arrayList.stream()).toFootprint());
        log.info("====================================================================");
        log.info(">> new String(\"Hello, World!\")");
        log.info(GraphLayout.parseInstance("Hello, World!").toPrintable());
        log.info("====================================================================");
    }
}
