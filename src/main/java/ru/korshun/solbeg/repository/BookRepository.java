package ru.korshun.solbeg.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.korshun.solbeg.entity.BookEntity;
import ru.korshun.solbeg.entity.TagEntity;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {

  Optional<BookEntity> findById(Long id);
  Page<BookEntity> findAll(Pageable pageable);
  Page<BookEntity> findByTag(TagEntity tagEntity, Pageable pageable);
  Page<BookEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
  Page<BookEntity> findByTitleContainingIgnoreCaseOrTag(String title, TagEntity tag, Pageable pageable);

}
