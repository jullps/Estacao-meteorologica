package modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Leitura")
public class Leitura implements Serializable { //ENTIDADE
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DATA_HORA", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "VALOR_MEDIDO", nullable = false)
    private Double valorMedido;

    @ManyToOne
    @JoinColumn(name = "ID_SENSOR", nullable = false)
    private Sensor sensor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Double getValorMedido() {
        return valorMedido;
    }

    public void setValorMedido(Double valorMedido) {
        this.valorMedido = valorMedido;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
