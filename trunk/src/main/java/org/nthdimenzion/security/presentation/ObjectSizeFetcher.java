package org.nthdimenzion.security.presentation;

import java.lang.instrument.Instrumentation;

/**
 * Created by IntelliJ IDEA.
 * User: Nthdimenzion
 * Date: 2/5/13                                entityStatus
 * Time: 9:45 AM
 */
public class ObjectSizeFetcher {

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
        System.out.println("HHEEELLLOOO WOORRRLLLDDD");
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }
}
