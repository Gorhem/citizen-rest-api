package com.gorhem.citizenrestapi.repository;

import com.gorhem.citizenrestapi.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface CitizenRepository extends JpaRepository<Citizen, Long>, JpaSpecificationExecutor<Citizen> {

    @Transactional
    @Modifying
    @Query("UPDATE Citizen c set c.parentId = :parent where c.id in :ids")
    void assignChildren(@Param(value = "ids") Set<Long> ids, @Param(value = "parent") Long parent);

    List<Citizen> findAllByIdIn(Set<Long> ids);
}
