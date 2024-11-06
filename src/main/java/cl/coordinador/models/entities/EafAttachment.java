package cl.coordinador.models.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "eaf_attachments_5", schema = "dbo")
public class EafAttachment extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "eaf_header_id", nullable = false)
    private Integer eafHeaderId;

    @Column(name = "code_neomante", length = 100)
    private String codeNeomante;

    @Column(name = "company_infotecnica_id", nullable = false)
    private Integer companyInfotecnicaId;
    @Column(name = "record_number")
    private Integer recordNumber;

    @Column(name = "company_name", length = 100)
    private String companyName;

    @UuidGenerator
    @Column(name = "uuid", columnDefinition = "uniqueidentifier")
    private UUID uuid;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEafHeaderId() {
        return eafHeaderId;
    }

    public void setEafHeaderId(Integer eafHeaderId) {
        this.eafHeaderId = eafHeaderId;
    }

    public String getCodeNeomante() {
        return codeNeomante;
    }

    public void setCodeNeomante(String codeNeomante) {
        this.codeNeomante = codeNeomante;
    }

    public Integer getCompanyInfotecnicaId() {
        return companyInfotecnicaId;
    }

    public void setCompanyInfotecnicaId(Integer companyInfotecnicaId) {
        this.companyInfotecnicaId = companyInfotecnicaId;
    }

    public Integer getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(Integer recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}