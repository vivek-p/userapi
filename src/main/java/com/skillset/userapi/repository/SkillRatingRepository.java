package com.skillset.userapi.repository;

import com.skillset.userapi.domain.SkillRating;
import com.skillset.userapi.domain.SkillRatingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRatingRepository extends JpaRepository<SkillRating, SkillRatingKey> {
}
