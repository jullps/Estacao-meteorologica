package beans;

import modelo.Controle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import org.primefaces.model.chart.*;

@Named("leituraBean")
@ApplicationScoped
public class LeituraBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String timestamp;
    private String temperatura;
    private String umidade;
    private String precipitacao;

    @Inject
    private Controle controle;
    private LineChartModel model;

    @PostConstruct
    // Chama os metodos que inicializam os sensores e a última leitura
    public void init() {

        try {
            criarGrafico();
        } catch (Exception e) {
            System.out.println("Não foi possivel criar o grafico - " + e.getMessage());
        }
        try {
            atualizarDadosGrafico();
            atualizarDadosSite();
        } catch (Exception ex) {
            this.temperatura = "--";
            this.umidade = "--";
            this.precipitacao = "--";
            if (!"".equals(controle.getMensagem())) {
                this.timestamp = controle.getMensagem();
            } else {
                this.timestamp = "Erro ao carregar os dados";
            }
        }
    }

    /**
     * Atualiza os valores buscando o DTO no Controle. Pode ser chamado pelo
     * poll da UI.
     */
    public void atualizarDadosSite() {
        controle.obterDTOatualizado();
        this.temperatura = controle.getTemperatura();
        this.umidade = controle.getUmidade();
        this.precipitacao = controle.getPrecipitacao();
        this.timestamp = getUltimaAtualizacao();
    }

    public String getUltimaAtualizacao() {
        try {
            String ts= controle.getTimestamp();
            if (ts == null || ts.trim().isEmpty() || "--".equals(ts)) return "Carregando dados...";
            // "dd/MM/yyyy HH:mm:ss" — separa em data e hora
            String dia = ts;
            String hora = "--";
            int sp = ts.indexOf(' ');
            if (sp > 0) {
                dia = ts.substring(0, sp);
                hora = ts.substring(sp + 1);
            }
            return "Última atualização no dia " + dia + " às " + hora;
        } catch (Exception ex) {
            return "Erro ao carregar o horário";
        }
    }

    // Getter {chartBean.model}
    public LineChartModel getModel() {
        return model;
    }

    // Cria o grafico de linhas e configura as legendas e eixos
    private void criarGrafico() {
        model = new LineChartModel();

        //Configuração das legendas
        model.setLegendPlacement(LegendPlacement.OUTSIDE);
        model.setLegendPosition("n");
        model.setLegendCols(3);

        // Configuração dos eixos
        CategoryAxis x = new CategoryAxis("Horário");
        model.getAxes().put(AxisType.X, x);
        Axis y = model.getAxis(AxisType.Y);
        if (y != null) {
            y.setLabel("Celsius");
        }
        Axis y2 = model.getAxis(AxisType.Y2);
        if (y2 == null) {
            y2 = new LinearAxis();
            model.getAxes().put(AxisType.Y2, y2);
        }
        y2.setLabel("Porcentagem");
    }

    private void atualizarDadosGrafico() {
        // Limpar séries existentes para não acumular novas a cada poll
        if (model.getSeries() != null) {
            model.getSeries().clear();
        }

        // Criação das series de valores
        LineChartSeries t = new LineChartSeries("Temperatura");
        LineChartSeries u = new LineChartSeries("Umidade");
        LineChartSeries p = new LineChartSeries("Precipitação");

        t.setSmoothLine(true);
        u.setSmoothLine(true);
        p.setSmoothLine(true);
        t.setYaxis(AxisType.Y);
        u.setYaxis(AxisType.Y2);
        p.setYaxis(AxisType.Y2);

        // Listas dos valores para atualização do grafico
        List<String> horarios = controle.ultimosHorariosPorTipo("Temperatura", 10);
        List<Double> valoresTemp = controle.ultimosValoresPorTipo("Temperatura", 10);
        List<Double> valoresUmid = controle.ultimosValoresPorTipo("Umidade", 10);
        List<Double> valoresPrecp = controle.ultimosValoresPorTipo("Precipitação", 10);

        // Reverter as listas para mostrar do mais antigo ao mais recente
        Collections.reverse(horarios);
        Collections.reverse(valoresTemp);
        Collections.reverse(valoresUmid);
        Collections.reverse(valoresPrecp);

        // Determina quantos pontos (valores) cada sensor tera no grafico
        int maxPontos = Math.min(10, Math.max(horarios.size(), Math.max(valoresTemp.size(), Math.max(valoresUmid.size(), valoresPrecp.size()))));

        for (int i = 0; i < maxPontos; i++) {
            String horario = (i < horarios.size()) ? horarios.get(i) : ("P" + (i + 1));

            Double vtemp = (i < valoresTemp.size()) ? valoresTemp.get(i) : null;
            Double vumid = (i < valoresUmid.size()) ? valoresUmid.get(i) : null;
            Double vprecp = (i < valoresPrecp.size()) ? valoresPrecp.get(i) : null;

            // Set apenas pontos com valores válidos
            if (vtemp != null) {
                t.set(horario, vtemp);
            }
            if (vumid != null) {
                u.set(horario, vumid);
            }
            if (vprecp != null) {
                p.set(horario, vprecp);
            }
        }
        
        model.addSeries(t);model.addSeries(u);model.addSeries(p);
        /*
        // Adiciona apenas séries que contenham dados
        if (t.getData() != null && !t.getData().isEmpty()) {
            model.addSeries(t);
        }
        if (u.getData() != null && !u.getData().isEmpty()) {
            model.addSeries(u);
        }
        if (p.getData() != null && !p.getData().isEmpty()) {
            model.addSeries(p);
        }
        */
        // Se nenhuma série foi adicionada, adiciona um placeholder
        if (model.getSeries() == null || model.getSeries().isEmpty()) {
            LineChartSeries placeholder = new LineChartSeries();
            placeholder.set("0", 0);
            model.addSeries(placeholder);
        }

        model.setSeriesColors("ff6384,36a2eb,ffcd56");

    }

    // Método público para ser chamado por p:poll
    public void atualizarTudo() {
        try {
            atualizarDadosGrafico();
            atualizarDadosSite();
        } catch (Exception ex) {
        }
    }

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

}
