package in.blog.ppmapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import in.blog.ppmapi.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{

	@Override
	Iterable<Project> findAllById(Iterable<Long> ids) ;
	
	Project findByProjectIdentifier(String projectIdentifier);
	
	@Override
	Iterable<Project> findAll() ;
}
