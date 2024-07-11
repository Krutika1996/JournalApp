package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }


    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
       try {
           journalEntry.setDate(LocalDate.now());
           journalEntryService.saveEntry(journalEntry);
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

    @DeleteMapping("/id/{myid}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myid) {
         journalEntryService.DeleteById(myid);
         return true;
    }

    @PutMapping("id/{myid}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId myid, @RequestBody JournalEntry newEntry) {
     JournalEntry oldEntry = journalEntryService.findById(myid).orElse(null);
        if(oldEntry != null){
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("")? newEntry.getTitle() : oldEntry.getTitle());
           oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")?newEntry.getContent() : oldEntry.getContent());
        }
        journalEntryService.saveEntry(oldEntry);
        return oldEntry;

    }
}