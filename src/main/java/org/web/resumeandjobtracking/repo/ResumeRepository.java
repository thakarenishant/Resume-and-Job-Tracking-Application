package org.web.resumeandjobtracking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.web.resumeandjobtracking.Model.Resume;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {


}
