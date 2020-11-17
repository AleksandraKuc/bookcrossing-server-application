package project.bookcrossing.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.bookcrossing.entity.Report;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long> {
}
