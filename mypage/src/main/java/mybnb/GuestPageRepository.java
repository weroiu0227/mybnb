package mybnb;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GuestPageRepository extends CrudRepository<GuestPage, Long> {

    List<GuestPage> findByBookId(Long bookId);

}