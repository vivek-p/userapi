package com.skillset.userapi.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Data
public class SkillRatingKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    Long userId;

    @Column(name="skill_id")
    Long skillId;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (skillId == null) {
            if (other.skillId != null)
                return false;
        } else if (!skillId.equals(other.skillId))
            return false;
        return true;
    }

}
