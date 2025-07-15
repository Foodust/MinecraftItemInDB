package org.foodust.minecraftItemInDB.db.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "item")
@SuperBuilder
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

    @Column(name = "display_name",length = 648)
    private String displayName;

    @Column(name = "lore")
    private List<String> lore;

    @Column(name = "custom_model_data")
    private Integer customModelData;

    @Lob
    @Column(name = "item_blob", columnDefinition = "BYTEA")
    private byte[] itemBlob;

}
