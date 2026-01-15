package com.example.repository.spec;

import com.example.dto.request.DocumentSearchRequest;
import com.example.entity.Document;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class DocumentSpecification {

    public static Specification<Document> search(DocumentSearchRequest req) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // 1. Search theo tên document
            if (req.getName() != null && !req.getName().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("originalFilename")),
                                "%" + req.getName().toLowerCase() + "%"
                        )
                );
            }

            // 2. Search theo người tạo
            if (req.getCreateBy() != null && !req.getCreateBy().isBlank()) {
                predicates.add(
                        cb.equal(root.get("createdBy"), req.getCreateBy())
                );
            }

            // 3. Search theo status
            if (req.getStatus() != null) {
                predicates.add(
                        cb.equal(root.get("status"), req.getStatus())
                );
            }

            // 4. Search theo ngày upload (from)
            if (req.getFromDate() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("createAt"),
                                req.getFromDate().atStartOfDay()
                        )
                );
            }

            // 5. Search theo ngày upload (to)
            if (req.getToDate() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("createAt"),
                                req.getToDate().atTime(23, 59, 59)
                        )
                );
            }

            // Không có điều kiện → trả về tất cả
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}