package spring.boot.rsa512.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.boot.rsa512.model.CharacterInfo;

public interface CharacterInfoRepository extends JpaRepository<CharacterInfo, Long> {

}
