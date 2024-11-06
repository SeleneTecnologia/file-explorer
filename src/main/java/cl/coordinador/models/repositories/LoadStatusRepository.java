package cl.coordinador.models.repositories;

import cl.coordinador.models.entities.LoadStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoadStatusRepository implements PanacheRepository<LoadStatus> {

}