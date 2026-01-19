# 🌦️ Estação Meteorológica

Sistema completo de monitoramento meteorológico com coleta de dados via Arduino, aplicação desktop Java e interface web.

## 📋 Sobre o Projeto

Este projeto académico é uma solução integrada para coletar, armazenar e visualizar dados meteorológicos em tempo real. Composto por:

- **Módulo Arduino**: Coleta dados de sensores (temperatura, umidade, precipitação)
- **Aplicação Desktop (Estacao)**: Interface Swing que coleta dados via porta serial e os armazena no banco de dados
- **Aplicação Web (WebEstacao)**: Interface JSF com Primefaces para visualização e análise dos dados coletados

## 🏗️ Arquitetura

```
Estação Meteorológica
├── Arduino (Coleta de dados)
│   └── Sensores (Temperatura, Umidade, Precipitação)
│
├── Estacao (Aplicação Desktop - Java Swing)
│   ├── Coleta via porta serial
│   ├── Validação de dados
│   └── Persistência em BD
│
└── WebEstacao (Aplicação Web - JSF/Primefaces)
    ├── Dashboard
    ├── Gráficos de dados
    └── Consultas históricas
```

## 🔧 Tecnologias Utilizadas

### Backend
- **Java 23** (Desktop)
- **Java 11** (Web)
- **Maven** - Gerenciador de dependências
- **JPA/EclipseLink** - ORM
- **Microsoft SQL Server** - Banco de dados

### Desktop (Estacao)
- **Java Swing** - Interface gráfica
- **jSerialComm** - Comunicação com Arduino
- **EclipseLink** - JPA

### Web (WebEstacao)
- **JSF (JavaServer Faces)** - Framework MVC
- **Primefaces** - Componentes JSF
- **Bootstrap 5** - Responsividade
- **Chart.js** - Gráficos
- **Payara/Glassfish** - Application Server

## 📦 Estrutura do Projeto

```
Projeto/
├── Estacao/                    # Aplicação Desktop
│   ├── src/main/java/
│   │   ├── apresentacao/       # Interface Swing
│   │   ├── modelo/             # Classes de negócio
│   │   ├── DAL/                # Data Access Layer
│   │   └── com/mycompany/      # Package raiz
│   ├── src/main/resources/     # Configurações JPA
│   └── pom.xml
│
├── WebEstacao/                 # Aplicação Web
│   ├── src/main/java/
│   │   ├── beans/              # Managed Beans JSF
│   │   ├── modelo/             # Entidades
│   │   ├── DAL/                # Data Access Layer
│   │   └── com/mycompany/      # Package raiz
│   ├── src/main/webapp/        # Arquivos web
│   │   ├── index.xhtml         # Dashboard principal
│   │   ├── estilo.css          # Estilos
│   │   └── WEB-INF/            # Configurações web
│   ├── src/main/resources/     # Configurações JPA
│   └── pom.xml
│
├── arduino_C.ino               # Sketch Arduino
└── README.md
```

## 🚀 Como Executar

### Pré-requisitos
- Java 23 (para Estacao) e Java 11 (para WebEstacao)
- Maven 3.6+
- SQL Server
- Arduino com sketch `arduino_C.ino`
- Payara/Glassfish Server (para WebEstacao)

### Configuração do Banco de Dados

1. Crie um banco de dados SQL Server
2. Configure a conexão em `src/main/resources/META-INF/persistence.xml`:

```xml
<persistence-unit name="EstacaoPU" transaction-type="RESOURCE_LOCAL">
    <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
    <properties>
        <property name="jakarta.persistence.jdbc.url" value="jdbc:sqlserver://localhost:1433;databaseName=seu_banco"/>
        <property name="jakarta.persistence.jdbc.user" value="seu_usuario"/>
        <property name="jakarta.persistence.jdbc.password" value="sua_senha"/>
        <property name="jakarta.persistence.jdbc.driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver"/>
    </properties>
</persistence-unit>
```

### Executar Aplicação Desktop

```bash
cd Estacao
mvn clean compile exec:java
```

### Executar Aplicação Web

```bash
cd WebEstacao
mvn clean package
# Deploy no Payara/Glassfish
asadmin deploy target/WebEstacao-1.0-SNAPSHOT.war
```

Acesse: `http://localhost:8080/WebEstacao`

### Upload para Arduino

1. Abra `arduino_C.ino` na IDE Arduino
2. Selecione porta serial (COM6 ou a porta conectada)
3. Faça upload do sketch

## 📊 Funcionalidades

### Estacao (Desktop)
- ✅ Leitura de dados via porta serial do Arduino
- ✅ Validação de dados coletados
- ✅ Armazenamento automático no banco de dados
- ✅ Interface gráfica para monitoramento
- ✅ Atualização em tempo real (a cada 10 segundos)

### WebEstacao (Web)
- ✅ Dashboard de dados meteorológicos
- ✅ Visualização em gráficos
- ✅ Histórico de medições
- ✅ Interface responsiva (mobile-friendly)
- ✅ Status de conexão em tempo real

## 📁 Principais Classes

### Estacao
- `frmPrincipal.java` - Interface principal (Swing)
- `Controle.java` - Lógica de negócio
- `Serial.java` - Comunicação com Arduino
- `Validacao.java` - Validação de dados
- `SensorDAO.java` - Acesso a dados de sensores
- `LeituraDAO.java` - Acesso a dados de leituras

### WebEstacao
- `LeituraBean.java` - Managed Bean para leituras
- `LeituraDAO.java` - Acesso a dados
- `index.xhtml` - Dashboard principal

## 🔌 Hardware

### Sensores Necessários
- Sensor DHT22 (Temperatura e Umidade)
- Sensor de Precipitação
- Arduino (Uno, Mega, ou compatível)

### Configuração Serial
- Porta padrão: `COM6` (configurável em `Serial.java`)
- Baud rate: 9600
- Formato: `umidade,temperatura,precipitacao\n`


## 📝 Notas Importantes

- **Java 23 (Desktop)**: Verificar compatibilidade com EclipseLink
- **SQL Server**: Ajustar credenciais em `persistence.xml`
- **Porta Serial**: COM6 é hardcoded em `Serial.java` - considerar parametrizar
- **Timeout**: Aumentar timeout de conexão se necessário para redes lentes

## 👤 Autor

Julia Lopes

