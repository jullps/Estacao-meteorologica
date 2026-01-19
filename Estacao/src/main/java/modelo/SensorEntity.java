/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SENSOR")
@NamedQueries({
    @NamedQuery(name = "SensorEntity.findAll", query = "SELECT s FROM SensorEntity s"),
    @NamedQuery(name = "SensorEntity.findById", query = "SELECT s FROM SensorEntity s WHERE s.id = :id"),
    @NamedQuery(name = "SensorEntity.findByTipo", query = "SELECT s FROM SensorEntity s WHERE s.tipo = :tipo"),
    @NamedQuery(name = "SensorEntity.findByUnidadeMedida", query = "SELECT s FROM SensorEntity s WHERE s.unidadeMedida = :unidadeMedida")
})
public class SensorEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "UNIDADE_MEDIDA")
    private String unidadeMedida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSensor")
    private List<LeituraEntity> leituraEntityList;

    public SensorEntity() {
    }

    public SensorEntity(Integer id) {
        this.id = id;
    }

    public SensorEntity(Integer id, String tipo, String unidadeMedida) {
        this.id = id;
        this.tipo = tipo;
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public List<LeituraEntity> getLeituraEntityList() {
        return leituraEntityList;
    }

    public void setLeituraEntityList(List<LeituraEntity> leituraEntityList) {
        this.leituraEntityList = leituraEntityList;
    }

    
}
