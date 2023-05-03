package com.skillset.userapi.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Data
@NoArgsConstructor
public class SkillRatingKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "employee_id")
    Long employeeId;

    @Column(name="skill_id")
    Long skillId;

    public SkillRatingKey(Long employeeId, Long skillId) {
        this.employeeId = employeeId;
        this.skillId = skillId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
        result = prime * result + ((skillId == null) ? 0 : skillId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SkillRatingKey other = (SkillRatingKey) obj;
        if (employeeId == null) {
            if (other.employeeId != null)
                return false;
        } else if (!employeeId.equals(other.employeeId))
            return false;
        if (skillId == null) {
            if (other.skillId != null)
                return false;
        } else if (!skillId.equals(other.skillId))
            return false;
        return true;
    }

}
