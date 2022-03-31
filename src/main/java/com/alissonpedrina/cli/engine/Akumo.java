package com.alissonpedrina.cli.engine;

import com.alissonpedrina.cli.domain.Recipe;
import com.alissonpedrina.cli.repo.RecipeRepository;
import com.alissonpedrina.cli.ui.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Size;

@Transactional
@ShellComponent
public class Akumo {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    ShellHelper shellHelper;

    @ShellMethod("Displays greeting message to the user whose name is supplied")
    public String echo(@ShellOption({"-N", "--name"}) String name) {
        String message = String.format("Hello %s!", name);
        shellHelper.print(message.concat(" (Default style message)"));
        shellHelper.printError(message.concat(" (Error style message)"));
        shellHelper.printWarning(message.concat(" (Warning style message)"));
        shellHelper.printInfo(message.concat(" (Info style message)"));
        shellHelper.printSuccess(message.concat(" (Success style message)"));
        String output = shellHelper.getSuccessMessage(message);
        return output.concat(" You are running spring shell cli-demo.");
    }

    @ShellMethod(value = "Save template.", key = "save")
    public void save(
            @ShellOption(defaultValue = "") String json,
            String type, String desc, String label, String keyWords) {
        Recipe recipe = new Recipe();
        recipe.setType("");
        recipe.setBody(json);
        recipe.setDesc("");
        recipe.setKeyWord("");
        recipe.setLabel(label);
        em.persist(recipe);

    }

    @ShellMethod(value = "List all recipes.", key = "list")
    public void list(){
        recipeRepository.findAll().forEach(r -> System.out.println(r.getLabel()));

    }

}
