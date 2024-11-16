package sumob.gecot.temperature_notifier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sumob.gecot.temperature_notifier.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
