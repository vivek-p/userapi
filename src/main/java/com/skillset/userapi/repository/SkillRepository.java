package com.skillset.userapi.repository;

import com.skillset.userapi.domain.Skill;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>, PagingAndSortingRepository<Skill, Long> {

    List<Skill> findBySkillNameLike(String criteria, Pageable pageable);
}
