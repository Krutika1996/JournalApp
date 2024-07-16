package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.Services.JournalEntryService;
import net.engineeringdigest.journalApp.Services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesofUsers(@PathVariable String userName) {
       UserEntry dbUser= userService.findByUserName(userName);
       List<JournalEntry> all = dbUser.getJournalEntries();
       if(all != null && !all.isEmpty()){
           return new ResponseEntity<>(HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName) {
       try {
           journalEntryService.saveEntry(journalEntry,userName);
           return new ResponseEntity<>(HttpStatus.CREATED);
       } catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }


    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myid) {
        Optional<JournalEntry> myEntry = journalEntryService.findById(myid);
        if(myEntry.isPresent()){
            return new ResponseEntity<>(myEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/id/{userName}/{myid}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myid, String userName) {
         UserEntry userEntry = userService.findByUserName(userName);
         userEntry.getJournalEntries().removeIf(x -> x.getId().equals(myid));
         userService.saveEntry(userEntry);
         journalEntryService.DeleteById(myid,userName);
         return true;
    }

//    @PutMapping("id/{myid}")
//    public JournalEntry updateJournalEntryById(@PathVariable ObjectId myid, @RequestBody JournalEntry newEntry) {
////     JournalEntry oldEntry = journalEntryService.findById(myid).orElse(null);
////        UserEntry dbUser= userService.findByUserName(userName);
////        if(oldEntry != null){
////            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("")? newEntry.getTitle() : oldEntry.getTitle());
////           oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")?newEntry.getContent() : oldEntry.getContent());
////        }
////        journalEntryService.saveEntry(oldEntry, dbUser);
////        return oldEntry;
//
//    }
}