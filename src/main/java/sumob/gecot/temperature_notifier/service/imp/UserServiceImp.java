package sumob.gecot.temperature_notifier.service.imp;

import org.springframework.stereotype.Service;
import sumob.gecot.temperature_notifier.domain.model.Name;
import sumob.gecot.temperature_notifier.repository.UserRepository;
import sumob.gecot.temperature_notifier.service.UserService;

import java.util.NoSuchElementException;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Name create(Name user) {
        if (userRepository.existsByEmail_Email(user.getEmail().getEmail())) {
            throw new IllegalArgumentException("Este e-mail já está em uso.");
        }
        return userRepository.save(user);
    }

    @Override
    public Name findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com id: " + id));
    }

    @Override
    public Name update(Long id, Name user) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("Usuário não encontrado com id: " + id);
        }
        user.setId(id); // Preservar o ID do usuário existente
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("Usuário não encontrado com id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}