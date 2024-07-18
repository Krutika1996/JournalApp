package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.Services.JournalEntryService;
import net.engineeringdigest.journalApp.Services.UserService;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

       @Autowired
        private UserService userService;

        @Autowired
        private UserRepository userRepository;




       @PutMapping
       public ResponseEntity<?> updateUser(@RequestBody UserEntry user)
       {
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           String username = authentication.getName();
          UserEntry userInDb = userService.findByUserName(username);

              userInDb.setUserName(user.getUserName());
              userInDb.setPassword(user.getPassword());
              userService.saveNewUser(userInDb);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
     @DeleteMapping
    public ResponseEntity<?> deleteUservyid()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}