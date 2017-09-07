package byAJ.Securex.controllers;

import byAJ.Securex.models.Person;
import byAJ.Securex.repositories.PersonRepository;
import byAJ.Securex.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonService personService;

    @RequestMapping(value="/register",method= RequestMethod.GET)
    public String showRegistrationPage(Model model)
    {
        model.addAttribute("user",new Person());
        return "registration";
    }
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public  String processRegistrationPage(@Valid @ModelAttribute("user") Person person,
                                           BindingResult bindingResult, Model model){
        model.addAttribute("user", person);

        Iterable<Person>checkusername=personRepository.findAllByUsername(person.getUsername());
        long count=checkusername.spliterator().getExactSizeIfKnown();
        System.out.println("++++++++++++++++++"+count+"++++++++++++++");
        if(count>0)
        {
            String existingusername="username '"+person.getUsername()+"' isn't available. Choose a different one";
            model.addAttribute("msg",existingusername);
            model.addAttribute("count",count);
            return "registration";
        }

        Iterable<Person>checkemail=personRepository.findAllByEmail(person.getEmail());
        long emcount=checkemail.spliterator().getExactSizeIfKnown();
        System.out.println("++++++++++++++++++"+emcount+"++++++++++++++");
        if(emcount>0)
        {
            String existingemail="This email address '"+person.getEmail()+"' is already registered.";
            model.addAttribute("emmsg",existingemail);
            model.addAttribute("emcount",emcount);
            return "registration";
        }

        if(bindingResult.hasErrors()){
            return"registration";
        }

        else
        {
            personService.saveUser(person);
            model.addAttribute("message","User Account Successfully Created");
        }
        return "login";
    }
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
