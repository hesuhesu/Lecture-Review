package com.webp.p7;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String>{
	@Transactional
	@Modifying
	@Query("update Member set pw=?2, name=?3, phone=?4 where id=?1")
	int updateMyinfo(String id, String pw, String name, String phone);
	@Query("select count(id) from Member")
	int findCount();
	@Query("select balance from Member where id=?1")
	int findBalance(String id);
	
}