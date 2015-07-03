package com.p.model.repositories;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.p.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("select u from User u where u.email = ?1") 
    public User findByEmail(String email);
	@Query("select u from User u where u.email != ?1 and u.id > 0") 
	public Collection<User> findAllDifferent(String email);
	@Query("select u from User u where u.role = 'ROLE_ADMIN'") 
	public Collection<User> findAdministradores();
	//This "imageFindCache" is delcares in ehcache.xml
	@Query("select u.imagen from User u where u.id = ?1") 
	@Cacheable(value="imageFindCache")
	public byte[] findImage(Integer id);
	@Query("select u from User u where UPPER(u.email) like UPPER(CONCAT(CONCAT('%', ?1), '%')) or"
			+ " UPPER(u.firstName) like UPPER(CONCAT(CONCAT('%', ?1), '%')) or"
			+ " UPPER(u.lastName) like UPPER(CONCAT(CONCAT('%', ?1), '%')) ") 
	public Collection<? extends User> findFullText(String texto);
}