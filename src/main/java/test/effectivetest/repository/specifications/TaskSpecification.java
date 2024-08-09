package test.effectivetest.repository.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import test.effectivetest.models.dto.search.TaskSearchFields;
import test.effectivetest.models.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {

    public static Specification<Task> filterTasks(TaskSearchFields searchFields) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchFields.getTitle() != null && !searchFields.getTitle().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + searchFields.getTitle().toLowerCase() + "%"));
            }

            if (searchFields.getDescription() != null && !searchFields.getDescription().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + searchFields.getDescription().toLowerCase() + "%"));
            }

            if (searchFields.getPriority() != null) {
                predicates.add(cb.equal(root.get("priority"), searchFields.getPriority()));
            }

            if (searchFields.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), searchFields.getStatus()));
            }

            if (searchFields.getCreatedFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("created"), searchFields.getCreatedFrom()));
            }

            if (searchFields.getCreatedTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("created"), searchFields.getCreatedTo()));
            }

            if (searchFields.getContractor() != null) {
                predicates.add(cb.equal(root.get("contractor.id"), searchFields.getContractor()));
            }

            if (searchFields.getAuthor() != null) {
                predicates.add(cb.equal(root.get("author.id"), searchFields.getAuthor()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}