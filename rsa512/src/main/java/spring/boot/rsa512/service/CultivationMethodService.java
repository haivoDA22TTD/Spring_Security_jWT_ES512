package spring.boot.rsa512.service;

import java.util.List;

import org.springframework.stereotype.Service;

import spring.boot.rsa512.model.CultivationMethod;
import spring.boot.rsa512.repository.CultivationMethodRepository;
@Service
public class CultivationMethodService {
     private final CultivationMethodRepository repo;

    public CultivationMethodService(CultivationMethodRepository repo) {
        this.repo = repo;
    }

    public List<CultivationMethod> getAll() {
        return repo.findAll();
    }

    public CultivationMethod addMethod(CultivationMethod method) {
        return repo.save(method);
    }

    public void deleteMethod(Long id) {
        repo.deleteById(id);
    }
}
