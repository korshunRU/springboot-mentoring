package ru.korshun.solbeg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.korshun.solbeg.entity.TagEntity;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, Long> {

  TagEntity findByNameIgnoreCase(String name);

}
