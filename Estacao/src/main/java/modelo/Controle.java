package modelo;

import java.util.List;

import DAL.SensorDAO;
import DAL.LeituraDAO;
import java.util.Date;

public class Controle {

    public String mensagem = "";
    private final SensorDAO sensorDAO = new SensorDAO();

    public Controle() {
        this.mensagem = "";
        sensorDAO.garantirSensoresExistem();
        if (!sensorDAO.mensagem.isEmpty()) {
            this.mensagem = sensorDAO.mensagem;
        } else {
            SalvarLeitura();
        }
    }

    private void SalvarLeitura() {
        Validacao validacao = new Validacao(Estaticos.temperatura, Estaticos.umidade, Estaticos.precipitacao);
        if (validacao.mensagem.isEmpty()) {
            LeituraDAO leituraDAO = new LeituraDAO();
            List<SensorEntity> sensores = sensorDAO.obterTodosSensores();
            for (SensorEntity sensor : sensores) {
                Double valor = null;
                switch (sensor.getTipo()) {
                    case "Temperatura" -> valor = validacao.getTemperatura();
                    case "Umidade" -> valor = validacao.getUmidade();
                    case "Precipitação" -> valor = validacao.getPrecipitacao();
                }
                if (valor != null) {
                    LeituraEntity leitura = new LeituraEntity();
                    leitura.setIdSensor(sensor);
                    leitura.setValorMedido(valor);
                    long agora = System.currentTimeMillis();
                    Date datahoraAgora = new Date(agora);
                    leitura.setDataHora(datahoraAgora);
                    System.out.println("Leitura preparada:" + sensor.getTipo() + " | " + leitura.getValorMedido() + " | "+ leitura.getDataHora());
                    leituraDAO.salvarLeitura(leitura);
                }

            }
            if (!leituraDAO.mensagem.isEmpty()) {
                this.mensagem = leituraDAO.mensagem;
            }
        } else {
            this.mensagem = validacao.mensagem;
        }
    }

}
