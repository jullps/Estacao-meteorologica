package modelo;

public class Validacao {

    public String mensagem = "";
    private final String tempString;
    private final String umidString;
    private final String precString;
    private Double temperatura;
    private Double umidade;
    private Double precipitacao;

    public Validacao(String temp, String umid, String prec) {
        this.tempString = temp;
        this.umidString = umid;
        this.precString = prec;
        ValidarNumeros();
    }

    private void ValidarNumeros() {
        try {
            if (tempString == null || tempString.trim().isEmpty()
                    || umidString == null || umidString.trim().isEmpty()
                    || precString == null || precString.trim().isEmpty()) {
                this.mensagem = "Valores estáticos vazios.";
                return;
            }

            this.temperatura = Double.valueOf(tempString.trim());
            this.umidade = Double.valueOf(umidString.trim());
            this.precipitacao = Double.valueOf(precString.trim());
            this.precipitacao = Math.round((1 - this.precipitacao / 1024.0) * 10000.0) / 100.0; // Converte para % e arredonda

        } catch (NumberFormatException ex) {
            this.mensagem = "Valores estáticos inválidos.";
        }
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public Double getUmidade() {
        return umidade;
    }

    public Double getPrecipitacao() {
        return precipitacao;
    }
    
    
}
