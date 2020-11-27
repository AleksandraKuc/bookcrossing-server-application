package project.bookcrossing.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.bookcrossing.entity.Images;

@Repository
public interface ImagesRepository extends CrudRepository<Images, Long> {
}
