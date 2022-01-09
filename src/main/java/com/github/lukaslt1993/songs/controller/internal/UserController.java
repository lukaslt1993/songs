package com.github.lukaslt1993.songs.controller.internal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.lukaslt1993.songs.controller.EndpointNames;
import com.github.lukaslt1993.songs.model.User;
import com.github.lukaslt1993.songs.service.UserService;

import java.util.List;

@RestController
@RequestMapping(EndpointNames.USER)
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return service.getUsers();
    }

    @GetMapping(path = { "/{name}" })
    public User getUser(@PathVariable String name) {
        return service.getUser(name);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        service.createUser(user);
        return ResponseEntity.ok("User created");
    }
    
    @PutMapping(path = { "/{id}" })
    public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable Long id) {
        service.updateUser(id, user);
        return ResponseEntity.ok("User updated");
    }
    
    @DeleteMapping(path = { "/{id}" })
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }

}
