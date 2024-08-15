package com.epm.gestepm.model.user.dao;

import com.epm.gestepm.modelapi.user.dto.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {

	User findBySigningId(Long signingId);

	User findUsuarioByEmailAndPassword(String email, String password);

	@Modifying(clearAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE users u \n" +
			"LEFT JOIN activity_center ac ON ac.activity_center_id = u.ACTIVITY_CENTER_ID\n" +
			"LEFT JOIN countries c ON c.ID = ac.COUNTRY_ID\n" +
			"SET u.LAST_YEAR_HOLIDAYS_COUNT = u.CURRENT_YEAR_HOLIDAYS_COUNT,\n" +
			"u.CURRENT_YEAR_HOLIDAYS_COUNT = CASE WHEN c.ID = 1 THEN 22 ELSE 30 END")
	void updateHolidaysInNewYear();

	@Modifying(clearAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE users u SET u.LAST_YEAR_HOLIDAYS_COUNT = 0")
	void resetLastYearHolidays();

}
