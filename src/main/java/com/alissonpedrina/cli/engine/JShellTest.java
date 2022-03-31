package com.alissonpedrina.cli.engine;

import jdk.jshell.JShell;

import java.io.IOException;

public class JShellTest {
    public static void main(String args[]) throws IOException {
        JShell jshell = JShell.create();
        var list = jshell.eval("public String hello(){return 'eee'} var x = hello();");
        jshell.methods().forEach(m -> System.out.println("dfdf" + m));
        list.forEach(r -> System.out.println(r.value()));
    }
}