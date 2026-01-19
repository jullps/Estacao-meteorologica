package DAL;

import javax.persistence.EntityManager;
import modelo.LeituraEntity;

public class LeituraDAO {
    public String mensagem = "";

    // Salva uma leitura (insere ou atualiza). Garante que dataHora não tenha
    // milissegundos antes de persistir.
    public void salvarLeitura(LeituraEntity leitura) {
        EntityManager em = Conexao.obterEntityManager();
        try {
            

            em.getTransaction().begin();
            if (leitura.getId() == null) {
                em.persist(leitura);
            } else {
                em.merge(leitura);
            }
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            this.mensagem = "Erro ao salvar leitura: " + ex.getMessage();
            throw ex;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

}
