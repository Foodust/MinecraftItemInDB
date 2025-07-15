package org.foodust.minecraftItemInDB.db.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "item")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "material")
    private String material;

    @Column(name = "display_name", length = 648)
    @Builder.Default
    private String displayName = null;

    @Column(name = "lore")
    @Builder.Default
    private List<String> lore = null;

    @Column(name = "custom_model_data")
    @Builder.Default
    private Integer customModelData = null;

    @Lob
    @Column(name = "item_blob", columnDefinition = "BYTEA")
    private byte[] itemBlob;

}
