package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {


@Autowired
private JournalEntryRepository journalEntryRepository;

@Autowired
private UserService userService;

@Transactional
public void saveEntry(JournalEntry journalEntry, String userName){
    UserEntry dbUser= userService.findByUserName(userName);
    JournalEntry savedEntry =  journalEntryRepository.save(journalEntry);
     dbUser.getJournalEntries().add(savedEntry);
     userService.saveEntry(dbUser);


}
public List<JournalEntry> getAll(){
    return journalEntryRepository.findAll();
}

public Optional<JournalEntry> findById(ObjectId id){
    return journalEntryRepository.findById(id);
}

public void  DeleteById(ObjectId id,String userName){
    UserEntry user = userService.findByUserName(userName);
    journalEntryRepository.deleteById(id);
}
}
