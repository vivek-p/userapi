package com.skillset.userapi.repository;

import com.skillset.userapi.domain.SkillRating;
import com.skillset.userapi.domain.SkillRatingKey;
import com.skillset.userapi.model.SearchResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRatingRepository extends JpaRepository<SkillRating, SkillRatingKey>,
        PagingAndSortingRepository<SkillRating, SkillRatingKey> {

    List<SkillRating> findByIdEmployeeId(Long employeeId);

    List<SkillRating> findByIdSkillId(Long skillId);

    List<SkillRating> findByIdSkillIdIn(List<Long> skillIds);

    String searchQuery = "SELECT e.id AS employeeId, e.employee_name AS employeeName, e.department, s.id AS skillId, " +
            "s.skill_name AS skillName, r.rating FROM skill_rating r left join skill s on r.skill_id = s.id " +
            "left join employee e on r.employee_id = e.id where s.id in :skillIds";

    @Query(value = searchQuery, nativeQuery = true)
    List<SearchResponse> findDetailsBySkillIdsIn(List<Long> skillIds);

}
