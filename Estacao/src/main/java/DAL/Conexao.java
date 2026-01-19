package DAL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conexao {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_Estacao_jar_1.0-SNAPSHOTPU");

    public static EntityManager obterEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        emf.close();
    }

}
