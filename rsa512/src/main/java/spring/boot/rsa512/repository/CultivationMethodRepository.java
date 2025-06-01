package spring.boot.rsa512.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.boot.rsa512.model.CultivationMethod;

public interface CultivationMethodRepository extends JpaRepository<CultivationMethod, Long> {

}
