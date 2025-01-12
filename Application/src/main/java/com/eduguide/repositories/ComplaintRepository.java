package com.eduguide.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eduguide.entities.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long>{

}
