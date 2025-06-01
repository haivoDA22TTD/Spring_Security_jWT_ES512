package spring.boot.rsa512.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import spring.boot.rsa512.model.CultivationMethod;
import spring.boot.rsa512.service.CultivationMethodService;

@RestController
@RequestMapping("/api/cultivationmethods")
@RequiredArgsConstructor
public class CultivationMethodController {
    @Autowired
    private final CultivationMethodService service;


    // ✅ Anyone can GET
    @GetMapping
    public ResponseEntity<List<CultivationMethod>> getAllMethods() {
        List<CultivationMethod> methods = service.getAll();
        return ResponseEntity.ok(methods);
    }

    // ✅ Only ADMIN can POST
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CultivationMethod> addMethod(@RequestBody CultivationMethod method) {
        CultivationMethod saved = service.addMethod(method);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ✅ Only ADMIN can DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMethod(@PathVariable Long id) {
        service.deleteMethod(id);
        return ResponseEntity.noContent().build();
    }
}
