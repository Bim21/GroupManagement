package com.vti.testing.specification;

import com.vti.testing.entity.Account;
import com.vti.testing.entity.filter.AccountFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AccountSpecification {
    private static final String USERNAME = "username";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String ROLE = "role";
    private static final String DEPARTMENT_NAME = "departmentName";

    public static Specification<Account> buildWhere(AccountFilter filter) {
        Specification<Account> whereUsername = new CustomSpecification(USERNAME, filter.getSearch());
        Specification<Account> whereFirstName = new CustomSpecification(FIRSTNAME, filter.getSearch());
        Specification<Account> whereLastName = new CustomSpecification(LASTNAME, filter.getSearch());
        Specification<Account> whereRole = new CustomSpecification(ROLE, filter.getRole());
        Specification<Account> whereDepartmentName = new CustomSpecification(DEPARTMENT_NAME, filter.getDepartment());
        return Specification.where(whereUsername.or(whereFirstName).or(whereLastName)).and(whereRole).and(whereDepartmentName);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class CustomSpecification implements Specification<Account> {

        private String key;
        private Object value;

        @Override
        public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            if (value == null) {
                return null;
            }
            switch (key) {
                case USERNAME:
                    return criteriaBuilder.like(root.get("username"), "%" + value.toString().trim() + "%");
                case FIRSTNAME:
                    return criteriaBuilder.like(root.get("firstName"), "%" + value.toString().trim() + "%");
                case LASTNAME:
                    return criteriaBuilder.like(root.get("lastName"), "%" + value.toString().trim() + "%");
                case ROLE:
                    return criteriaBuilder.equal(root.get("role"), value);
                case DEPARTMENT_NAME:
                    return criteriaBuilder.equal(root.get("department").get("name"), value.toString());
                default:
                    return null;
            }
        }
    }
}
