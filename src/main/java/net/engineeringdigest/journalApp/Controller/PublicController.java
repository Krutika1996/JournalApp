package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;
    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok ";
    }
    @PostMapping("/create-user")
    public void createUser (@RequestBody UserEntry user){
        userService.saveNewUser(user);
    }

}
