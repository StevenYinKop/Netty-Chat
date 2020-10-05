package cc.stevenyin.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import cc.stevenyin.dao.Users;

public interface UserRepository extends CrudRepository<Users, String>, JpaSpecificationExecutor<Users> {

	Users findByUsername(String username);
	Users findByUsernameAndPassword(String username, String password);
}
