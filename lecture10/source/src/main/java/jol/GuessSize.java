/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * @author Aleksey Shipilev
 */
public class GuessSize {

    /*
     * This sample showcases the basic field layout.
     * You can see a few notable things here:
     *   a) how much the object header consumes;
     *   b) how fields are laid out;
     *   c) how the external alignment beefs up the object size
     */

  public static void main(String[] args) throws Exception {
    out.println(VM.current().details());
    int[] intArray = new int[1000];
    Integer[] integerArray = new Integer[1000];
    ArrayList<Integer> arrayList = new ArrayList<>();
    LinkedList<Integer> linkedList = new LinkedList<>();
    HashSet<Integer> hashSet = new HashSet<>();

    for (int i = 0; i < 1000; i++) {
      Integer io = i;
      intArray[i] = io;
      integerArray[i] = io;
      arrayList.add(io);
      linkedList.add(io);
      hashSet.add(io);
    }

    arrayList.trimToSize();

    PrintWriter pw = new PrintWriter(out);
    pw.println(">> new Integer(10)");
    pw.println(GraphLayout.parseInstance(new Integer(10)).toPrintable());
    pw.println(">> new Long(10)");
    pw.println(GraphLayout.parseInstance(new Long(10)).toPrintable());
    pw.println(">> new int[1000]");
    pw.println(GraphLayout.parseInstance((Object) intArray).toFootprint());
    pw.println(">> new Integer[1000]");
    pw.println(GraphLayout.parseInstance((Object) integerArray).toFootprint());
    pw.println(">> new ArrayList<Integer>(1000)");
    pw.println(GraphLayout.parseInstance(arrayList).toFootprint());
    pw.println(">> new LinkedList<Integer>(1000)");
    pw.println(GraphLayout.parseInstance(linkedList).toFootprint());
    pw.println(">> new HashSet<Integer>(1000)");
    pw.println(GraphLayout.parseInstance(hashSet).toFootprint());
    pw.println(">> new ArrayList<Integer>(1000).stream()");
    pw.println(GraphLayout.parseInstance(arrayList.stream()).toFootprint());
    pw.close();
  }
}
