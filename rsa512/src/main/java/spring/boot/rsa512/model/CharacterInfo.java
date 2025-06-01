package spring.boot.rsa512.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "character_info")
public class CharacterInfo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
     private Long id;

    private String name;            // Tên nhân vật
    @JsonProperty("cultivation_level")
    private String cultivationLevel; // Tu vi, ví dụ "Kim Đan", "Huyền Hỏa tầng 3",...

    private String mount;           // Tọa kỵ (thú cưỡi)
     @JsonProperty("magic_weapon")
    private String magicWeapon;     // Pháp bảo
    @JsonProperty("cultivation_tip")
    private String cultivationTip;  // Phương pháp tu luyện

    private String power;           // Năng lực (mô tả hoặc cấp độ)

    private Integer tiers;          // Các tầng (cấp bậc tu luyện)
}
