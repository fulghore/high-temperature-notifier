package sumob.gecot.temperature_notifier.service;

import sumob.gecot.temperature_notifier.domain.model.Name;

public interface UserService {
    Name create(Name user);
    Name findById(Long id);
    Name update(Long id, Name user);
    void delete(Long id);
    boolean existsById(Long id);
}