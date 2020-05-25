package in.blog.ppmapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import in.blog.ppmapi.domain.ProjectTask;
@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

}
