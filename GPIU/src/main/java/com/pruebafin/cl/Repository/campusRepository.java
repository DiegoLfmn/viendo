package com.pruebafin.cl.Repository;

import com.pruebafin.cl.Entity.campusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface campusRepository extends JpaRepository<campusEntity, Long> {
//    List<campusEntity> findByName(String name);
}
