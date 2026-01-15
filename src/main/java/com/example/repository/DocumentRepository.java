package com.example.repository;

import com.example.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository
        extends JpaRepository<Document, Long>,
        JpaSpecificationExecutor<Document> {

    @Query("""
        select d.status, count(d)
        from Document d
        group by d.status
    """)
    List<Object[]> countByStatus();
}