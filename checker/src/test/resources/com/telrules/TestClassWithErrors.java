package com.telrules;

public class TestClassWithErrors {



    /**
     * Method scope vars should not be tested
     */
    public void doSomething(
            String str) { //Not ok
        String sFoo; // OK
        String foo; // Not OK
        Integer iInt; //Ok
        Integer nt; //Not Ok
        Map hNt; //Ok
        Map nt; //Ok
        List vList; //Ok
        List list; //Not Ok
    }

}