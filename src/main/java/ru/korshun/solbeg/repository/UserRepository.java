package ru.korshun.solbeg.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.korshun.solbeg.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Page<UserEntity> findAll(Pageable pageable);
  Optional<UserEntity> findByEmail(String email);

}
