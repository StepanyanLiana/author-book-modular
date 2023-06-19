package am.itspace.authorbookweb.controller;
import am.itspace.authorbookcommon.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping("/authors")
    public String authorsPage(ModelMap modelMap){
        modelMap.addAttribute("authors", authorRepository.findAll());
        return "authors";
    }

}