package spring.boot.rsa512.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.boot.rsa512.model.CharacterInfo;
import spring.boot.rsa512.repository.CharacterInfoRepository;
@Service
@RequiredArgsConstructor
public class CharacterInfoService {
    @Autowired
    private CharacterInfoRepository characterInfoRepository;

    public CharacterInfo addCharacter(CharacterInfo characterInfo) {
        return characterInfoRepository.save(characterInfo);
    }

    public List<CharacterInfo> getAllCharacters() {
        return characterInfoRepository.findAll();
    }

    public void deleteCharacter(Long id) {
        characterInfoRepository.deleteById(id);
    }
}
