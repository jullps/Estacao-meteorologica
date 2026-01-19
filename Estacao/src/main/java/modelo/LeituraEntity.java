package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "LeituraEntity")
@Table(name = "LEITURA")
@NamedQueries({
    @NamedQuery(name = "LeituraEntity.findAll", query = "SELECT l FROM LeituraEntity l"),
    @NamedQuery(name = "LeituraEntity.findById", query = "SELECT l FROM LeituraEntity l WHERE l.id = :id"),
    @NamedQuery(name = "LeituraEntity.findByDataHora", query = "SELECT l FROM LeituraEntity l WHERE l.dataHora = :dataHora"),
    @NamedQuery(name = "LeituraEntity.findByValorMedido", query = "SELECT l FROM LeituraEntity l WHERE l.valorMedido = :valorMedido")
})
public class LeituraEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "DATA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "VALOR_MEDIDO")
    private Double valorMedido;
    @JoinColumn(name = "ID_SENSOR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private SensorEntity idSensor;

    public LeituraEntity() {
    }

    public LeituraEntity(Integer id) {
        this.id = id;
    }

    public LeituraEntity(Integer id, Date dataHora, Double valorMedido) {
        this.id = id;
        this.dataHora = dataHora;
        this.valorMedido = valorMedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public Double getValorMedido() {
        return valorMedido;
    }

    public void setValorMedido(Double valorMedido) {
        this.valorMedido = valorMedido;
    }

    public SensorEntity getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(SensorEntity idSensor) {
        this.idSensor = idSensor;
    }

    
}