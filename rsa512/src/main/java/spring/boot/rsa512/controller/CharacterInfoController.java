package spring.boot.rsa512.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.boot.rsa512.model.CharacterInfo;
import spring.boot.rsa512.service.CharacterInfoService;
@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterInfoController {
    @Autowired
    private CharacterInfoService characterInfoService;

    // ADMIN được thêm nhân vật
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CharacterInfo> addCharacter(@RequestBody CharacterInfo characterInfo) {
        CharacterInfo saved = characterInfoService.addCharacter(characterInfo);
        return ResponseEntity.ok(saved);
    }

    // User và Admin đều xem được danh sách
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<CharacterInfo>> getAllCharacters() {
        List<CharacterInfo> list = characterInfoService.getAllCharacters();
        return ResponseEntity.ok(list);
    }

    // ADMIN xóa nhân vật
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        characterInfoService.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }
}
