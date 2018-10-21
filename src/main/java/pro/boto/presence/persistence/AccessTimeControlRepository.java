package pro.boto.presence.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.boto.presence.persistence.model.AccessTimeControl;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccessTimeControlRepository extends JpaRepository<AccessTimeControl, String> {

    @Query(" select tc " +
           "   from AccessTimeControl tc " +
           "  where tc.employee = ?1 " +
           "    and tc.timestamp >= ?2 and tc.timestamp <= ?3 " +
           "  order by tc.timestamp asc")
    List<AccessTimeControl> findByEmployeeAndDate(String employee, LocalDateTime fromTime, LocalDateTime toTime);

}
