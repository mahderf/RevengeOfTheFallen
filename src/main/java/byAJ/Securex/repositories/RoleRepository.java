package byAJ.Securex.repositories;

import byAJ.Securex.models.PersonRole;
import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends CrudRepository<PersonRole,Long>{
Iterable<PersonRole>findAllById(Long Long);
Iterable<PersonRole>findPersonRoleByRole(String String);
PersonRole findByRole(String personRole);

}
