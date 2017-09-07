package byAJ.Securex.repositories;

import byAJ.Securex.models.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long>{
    Person findByUsername(String username);
    Person findByEmail(String email);
    Long countByEmail(String email);
    Long countByUsername(String username);
    Iterable<Person>findAllByUsername(String username);
    Iterable<Person>findAllByEmail(String email);
}
