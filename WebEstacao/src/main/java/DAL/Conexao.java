package DAL;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Conexao {
    public String mensagem = "";
    private static final String JNDI_NAME = "jdbc/estacaoDS";  

    public static Connection obterConexao() throws SQLException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(JNDI_NAME);
            if (ds == null) {
                throw new SQLException("JNDI não encontrado: " + JNDI_NAME);
            }
            return ds.getConnection();
        } catch (NamingException ex) {
            throw new SQLException("Falha na busca do JNDI para " + JNDI_NAME, ex);
        }
    }
}
