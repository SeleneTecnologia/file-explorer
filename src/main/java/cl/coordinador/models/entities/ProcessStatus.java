package cl.coordinador.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "process_statuses", schema = "dbo")
public class ProcessStatus {

    @Id
    @Column(name = "id", columnDefinition = "CHAR(4)", nullable = false)
    private String id;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Column(name = "is_error", nullable = false)
    private boolean isError;

    @Column(name = "[level]", length = 50)
    private String level;


    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}