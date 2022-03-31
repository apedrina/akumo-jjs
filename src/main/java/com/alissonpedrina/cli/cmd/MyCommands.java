package com.alissonpedrina.cli.cmd;

import javax.validation.constraints.Size;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

//@ShellComponent
public class MyCommands {

  //@ShellMethod(value = "Add numbers.", key = "sum")
  public int add(int a, int b) {
    return a + b;
  }

  //@ShellMethod(value = "Change password.", key = "changePassword")
  public String changePassword(@Size(min = 8, max = 40) String password) {
    return "Password successfully set to " + password;
  }


}
