package org.foodust.minecraftItemInDB.db.repository;

import org.foodust.minecraftItemInDB.db.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long>, JpaSpecificationExecutor<ItemEntity> {
    List<ItemEntity> findByDisplayName(String displayName);
}
