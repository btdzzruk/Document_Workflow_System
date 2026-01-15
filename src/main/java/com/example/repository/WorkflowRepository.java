package com.example.repository;

import com.example.entity.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow,Long> {

    @Query("""
    select w.reviewer, count(w)
    from Workflow w
    where w.state in ('REVIEWED','APPROVED','REJECTED')
    group by w.reviewer
    """)
    List<Object[]> countByReviewer();

    @Query(value = """
select coalesce(
    avg(timestampdiff(second, review_at, approved_at)), 
    0
)
from workflows
where state = 'APPROVED'
  and review_at is not null
  and approved_at is not null
""", nativeQuery = true)
    Long avgApprovalTimeSeconds();

    @Query("""
select w from Workflow w
where w.state = 'SUBMITTED'
and w.reviewDeadline < :now
""")
    List<Workflow> findOverdueReview(LocalDateTime now);

    @Query("""
select w from Workflow w
where w.state = 'REVIEWED'
and w.approvedDeadline < :now
""")
    List<Workflow> findOverdueApprove(LocalDateTime now);

}
