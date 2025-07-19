package org.foodust.minecraftItemInDB.db.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ElementCollection
    @CollectionTable(name = "item_lore", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "lore_line")
    @Builder.Default
    private List<String> lore = new ArrayList<>();

    @Column(name = "custom_model_data")
    @Builder.Default
    private Integer customModelData = null;

    @Column(name = "item_blob", columnDefinition = "BYTEA")
    private byte[] itemBlob;

}
