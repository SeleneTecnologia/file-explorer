package cl.coordinador.models.repositories;

import cl.coordinador.models.entities.CoordinatedAdditionalInfo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CoordinatedAdditionalInfoRepository implements PanacheRepository<CoordinatedAdditionalInfo> {

    public Long secuenceId() {
        return (Long) getEntityManager()
                .createNativeQuery("SELECT NEXT VALUE FOR seq_coordinated_additional_info_id")
                .getSingleResult();
    }
}