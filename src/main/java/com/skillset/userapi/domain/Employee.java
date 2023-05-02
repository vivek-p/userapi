package com.skillset.userapi.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employee", schema = "employee_api", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "employee_name"}, name = Employee.EmployeeInfoIndex.EMP_NAME_UNIQUE_IDX) })
public class Employee {

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "employee_name", nullable = false, updatable = false, unique = true, length = 200)
        private String employeeName;

        @Column(name = "department", length = 50)
        private String department;

        @OneToMany(mappedBy = "user")
        List<SkillRating> skillRatings;

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((id == null) ? 0 : id.hashCode());
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
                Employee other = (Employee) obj;
                if (id == null) {
                        if (other.id != null)
                                return false;
                } else if (!id.equals(other.id))
                        return false;
                return true;
        }

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static class EmployeeInfoIndex {
            public static final String EMP_NAME_UNIQUE_IDX = "employee_name_unique_idx";
        }

}
