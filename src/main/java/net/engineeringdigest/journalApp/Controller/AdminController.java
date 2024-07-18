package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.Services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserEntry> all = userService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody UserEntry userEntry){
        userService.saveAdmin(userEntry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
