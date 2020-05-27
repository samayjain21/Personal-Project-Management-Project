package in.blog.ppmapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.blog.ppmapi.domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

	List<ProjectTask> findByProjectIdentiferOrderByPriority(String id);
	
	ProjectTask findByProjectSequence(String sequence);
	
}
