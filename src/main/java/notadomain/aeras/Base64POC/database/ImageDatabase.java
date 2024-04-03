package notadomain.aeras.Base64POC.database;

import notadomain.aeras.Base64POC.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ImageDatabase extends JpaRepository<Image, Integer> {
    @Query("SELECT i FROM Image i WHERE CURRENT_TIMESTAMP BETWEEN i.startDate AND i.endDate")
    List<Image> findImageBetweenDates();

}
