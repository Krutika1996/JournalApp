package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.UserEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {


@Autowired
private UserRepository userRepository;

private static final PasswordEncoder passwordencoder = new BCryptPasswordEncoder();
public void saveEntry(UserEntry userEntry){
    userRepository.save(userEntry);
}
    public void saveNewUser(UserEntry userEntry){
        userEntry.setPassword(passwordencoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("User"));
        userRepository.save(userEntry);
    }

    public void  saveAdmin (UserEntry userEntry){
        userEntry.setPassword(passwordencoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("Admin"));
        userRepository.save(userEntry);
    }
public List<UserEntry> getAll(){
    return userRepository.findAll();
}

public Optional<UserEntry> findById(ObjectId id){
    return userRepository.findById(id);
}

public void  DeleteById(ObjectId id){
    userRepository.deleteById(id);
}

public UserEntry findByUserName(String userName){
    return userRepository.findByUserName(userName);
}
}
