package cl.coordinador.models.repositories;

import cl.coordinador.models.entities.EafAttachment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EafAttachmentRepository implements PanacheRepository<EafAttachment> {

    public Long secuenceId() {
        return (Long) getEntityManager()
                .createNativeQuery("SELECT NEXT VALUE FOR seq_coordinated_additional_info_id")
                .getSingleResult();
    }

    public Integer findEafIdByHeaderId(Integer idHeader) {
        // Realizar la consulta nativa
        return (Integer) getEntityManager()
                .createNativeQuery("SELECT eaf_id FROM eaf_headers WHERE id = :idHeader")
                .setParameter("idHeader", idHeader)
                .getSingleResult();
    }

}