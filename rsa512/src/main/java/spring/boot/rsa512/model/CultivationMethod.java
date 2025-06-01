package spring.boot.rsa512.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cultivation_methods")
public class CultivationMethod {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
     private Long id;

    private String name;
    @JsonProperty("description")
    private String description;
}
