package modelo;

import DAL.LeituraDAO;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Named("controle")
@ApplicationScoped
public class Controle implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mensagem = "";
    private String timestamp;
    private String temperatura;
    private String umidade;
    private String precipitacao;

    @Inject
    private LeituraDAO leituraDAO;
    private final ValoresLeituraDTO valoresDTO;

    public Controle() {
        this.valoresDTO = new ValoresLeituraDTO();
    }

    // Retorna uma lista de (n) leituras por tipo (usado no gráfico e para atualizar o DTO)
    public List<Leitura> ultimasLeiturasPorTipo(String tipo, int limite) {
        try {
            List<Leitura> leituras = leituraDAO.ultimasLeiturasPorTipo(tipo, limite);
            if (!leituraDAO.mensagem.isEmpty()) {
                this.mensagem = leituraDAO.mensagem;
                return null;
            } else if (leituras != null && !leituras.isEmpty()) {
                Leitura l = leituras.get(0);
                atualizarDTOComLeitura(tipo, l);
            }
            return leituras;
        } catch (Exception e) {
            this.mensagem = "Erro ao obter leituras: " + e.getMessage();
            return null;
        }
    }

    // Retorna uma lista de (n) valores medidos por tipo
    public List<Double> ultimosValoresPorTipo(String tipo, int limite) {
        List<Double> valores = new ArrayList<>();
        List<Leitura> lista = ultimasLeiturasPorTipo(tipo, limite);
        for (Leitura l : lista) {
            valores.add(l.getValorMedido());
        }
        return valores;
    }

    // Retorna uma lista de (n) horários das leituras por tipo
    public List<String> ultimosHorariosPorTipo(String tipo, int limite) {
        List<Leitura> lista = leituraDAO.ultimasLeiturasPorTipo(tipo, limite);
        List<String> horarios = new ArrayList<>();
        // Formatar apenas a parte de hora:minuto:segundo para exibição no gráfico
        DateTimeFormatter horaFmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (lista != null) {
            for (Leitura l : lista) {
                if (l.getDataHora() != null) {
                    horarios.add(l.getDataHora().format(horaFmt));
                } else {
                    horarios.add("--");
                }
            }
        }
        return horarios;
    }

    // Atualiza o DTO com os valores da leitura fornecida (usada pela ultimasLeiturasPorTipo)
    private void atualizarDTOComLeitura(String tipo, Leitura l) {
        if (l == null) {
            return;
        }
        switch (tipo) {
            case "Temperatura":
                valoresDTO.setTemperatura(String.valueOf(l.getValorMedido()));
                break;
            case "Umidade":
                valoresDTO.setUmidade(String.valueOf(l.getValorMedido()));
                break;
            case "Precipitação":
            case "Precipitacao":
                valoresDTO.setPrecipitacao(String.valueOf(l.getValorMedido()));
                break;
            default:
                break;
        }
        // Formata data/hora para "dd/MM/yyyy HH:mm:ss" para uso na UI
        if (l.getDataHora() != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            valoresDTO.setTimestamp(l.getDataHora().format(dtf));
        } else {
            valoresDTO.setTimestamp("--");
        }
    }

    // Obtém o DTO atualizado com os últimos valores de cada tipo e atualiza os atributos do controle
    public void obterDTOatualizado() {
        ultimasLeiturasPorTipo("Temperatura", 1);
        ultimasLeiturasPorTipo("Umidade", 1);
        ultimasLeiturasPorTipo("Precipitação", 1);
        this.timestamp = valoresDTO.getTimestamp();
        this.temperatura = valoresDTO.getTemperatura();
        this.umidade = valoresDTO.getUmidade();
        this.precipitacao = valoresDTO.getPrecipitacao();
    }


    // Getters
    public String getTimestamp() {
        return timestamp;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public String getUmidade() {
        return umidade;
    }

    public String getPrecipitacao() {
        return precipitacao;
    }

    public String getMensagem() {
        return mensagem;
    }

}
