package com.spike.user.helper;

import com.spike.user.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserDashboardHelper {


    public Specification<User> filterByName(String name) {
        return (root, query, cb) -> name != null ? cb.equal(root.get("name"), name) : null;
    }

    public Specification<User> filterByEmail(String email) {
        return (root, query, cb) ->  email!= null ? cb.equal(root.get("email"), email) : null;
    }

    public Specification<User> filterByJoiningDate(Date joiningDate) {
        return (root, query, cb) -> joiningDate != null ? cb.equal(root.get("joiningDate"), joiningDate) : null;
    }

    public Specification<User> filterBySalary(Double salary) {
        return (root, query, cb) -> salary != null ? cb.equal(root.get("salary"), salary) : null;
    }
}
