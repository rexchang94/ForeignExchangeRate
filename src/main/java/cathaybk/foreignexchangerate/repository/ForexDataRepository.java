package cathaybk.foreignexchangerate.repository;

import cathaybk.foreignexchangerate.entity.ForexData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForexDataRepository extends JpaRepository<ForexData, Long> {
    List<ForexData> findByDateBetween(String startDate, String endDate);

    boolean existsByDate(String date);
}
