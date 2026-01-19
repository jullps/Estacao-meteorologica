package modelo;

import java.io.Serializable;

/**
 * DTO usado pela camada de apresentação contendo valores formatados
 * (strings) prontos para exibição na UI.
 */
public class ValoresLeituraDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String timestamp;
    private String temperatura;
    private String umidade;
    private String precipitacao;

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getTemperatura() { return temperatura; }
    public void setTemperatura(String temperatura) { this.temperatura = temperatura; }

    public String getUmidade() { return umidade; }
    public void setUmidade(String umidade) { this.umidade = umidade; }

    public String getPrecipitacao() { return precipitacao; }
    public void setPrecipitacao(String precipitacao) { this.precipitacao = precipitacao; }
}
