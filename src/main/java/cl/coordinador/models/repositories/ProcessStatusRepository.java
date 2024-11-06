package cl.coordinador.models.repositories;

import cl.coordinador.models.entities.ProcessStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProcessStatusRepository implements PanacheRepository<ProcessStatus> {

}