#include <DHT.h> // Biblioteca do sensor DHT

#define pinoDHT11 4        // Pino de dados do DHT 4 = D2 → GPIO4 no NodeMCU
#define DHTTYPE DHT11   // Tipo de sensor usado DHTT11 ou DHT22

DHT dht(pinoDHT11, DHTTYPE); // Cria um objeto DHT para o sensor

int pinoSensorChuva = A0;  // Pino analógico para o sensor de chuva
int valorChuva = 0; // Variável para guardar a leitura do sensor de chuva

void setup() 
{
      Serial.begin(9600); // Inicia a comunicação serial na velocidade de 9600 bps
      dht.begin();  // Inicializa o sensor DHT, se não tiver os resultados de temperatura e humidade saem = nan
}

void loop() 
{
      //Se quiser evitar múltiplas chamadas (que às vezes causam leituras inconsistentes), você pode armazenar os valores em variáveis antes: Assim o código fica mais limpo e evita leituras duplicadas.
      int h = dht.readHumidity(); // Lê a umidade do ar (%)
      float t = dht.readTemperature(); // Lê a temperatura em °C
      valorChuva = analogRead(pinoSensorChuva); // Lê o valor do sensor de chuva (0–1023)
     
      // Envia os dados formatados para o Serial Monitor
      Serial.print(h); // Imprime a umidade
      Serial.print(",");
      Serial.print(t); // Imprime a temperatura
      Serial.print(",");
      Serial.println(valorChuva); // Imprime o valor do sensor de chuva
      
      delay(10000); // Espera 10 segundos antes da próxima coleta

}



