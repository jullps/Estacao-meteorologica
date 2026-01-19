package DAL;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import modelo.Leitura;
import java.util.List;
import java.util.Collections;

@Stateless
public class LeituraDAO {

    public String mensagem = "";

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    // Retorna (n) valores de um tipo de sensor
    public List<Leitura> ultimasLeiturasPorTipo(String tipo, int limite) {
        try {
            TypedQuery<Leitura> q = em.createQuery(
                    "SELECT l FROM Leitura l JOIN l.sensor s WHERE s.tipo = :tipo ORDER BY l.dataHora DESC",
                    Leitura.class);
            q.setParameter("tipo", tipo);
            q.setMaxResults(limite);
            return q.getResultList();
        } catch (Exception e) {
            this.mensagem = "Erro ao buscar leituras no banco: " + e.getMessage();
            return Collections.emptyList();
        }

    }

}
