package sumob.gecot.temperature_notifier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sumob.gecot.temperature_notifier.domain.model.Name;

public interface UserRepository extends JpaRepository<Name, Long> {
    boolean existsByEmail_Email(String email); // Método para verificar se o email já existe
}