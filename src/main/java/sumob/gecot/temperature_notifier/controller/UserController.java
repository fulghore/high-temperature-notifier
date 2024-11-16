package sumob.gecot.temperature_notifier.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sumob.gecot.temperature_notifier.domain.model.Name; // Corrigido
import sumob.gecot.temperature_notifier.service.UserService;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Name> findById(@PathVariable Long id) {
        var user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Name> create(@RequestBody Name userToCreate) {
        try {
            var userCreated = userService.create(userToCreate);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(userCreated.getId())
                    .toUri();
            return ResponseEntity.created(location).body(userCreated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Retorna 400 Bad Request se o e-mail já estiver em uso
        } catch (Exception e) {
            e.printStackTrace(); //Registra a stack trace no console
            return ResponseEntity.status(500).body(null); //Retorna 500 Internal Server Error
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Name> update(@PathVariable Long id, @RequestBody Name userToUpdate) {
        try {
            var updatedUser  = userService.update(id, userToUpdate);
            return ResponseEntity.ok(updatedUser );
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}