package com.alissonpedrina.cli.engine;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SpringBootEngine {

    public static void main(String[] args) {
        var maven = new SpringBootEngine();
        maven.createProject();

    }


    @ShellMethod(value = "Create project.", key = "create")
    public void createProject(){
        System.out.println("aqui....");

    }

}
