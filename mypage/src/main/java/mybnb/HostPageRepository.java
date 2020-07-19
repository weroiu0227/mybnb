package mybnb;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HostPageRepository extends CrudRepository<HostPage, Long> {

    List<HostPage> findByBookId(Long bookId);

}