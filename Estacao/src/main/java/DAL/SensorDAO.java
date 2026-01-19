package DAL;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import modelo.SensorEntity;

public class SensorDAO {
	public String mensagem = "";
	String[] tipos = {"Temperatura", "Umidade", "Precipitação"};


	 // Garante que os sensores necessários existam no banco. Cria os que faltam.
	public void garantirSensoresExistem() {
		EntityManager em = Conexao.obterEntityManager();
		try {
			em.getTransaction().begin();
			for (String tipo : tipos) {
				TypedQuery<SensorEntity> q = em.createNamedQuery("SensorEntity.findByTipo", SensorEntity.class);
				q.setParameter("tipo", tipo);
				boolean existe = !q.getResultList().isEmpty();
				if (!existe) {
					System.out.println("Criando sensor do tipo: " + tipo);
					SensorEntity sensor = new SensorEntity();
					sensor.setTipo(tipo);
					String unidade = "%";
					if ("Temperatura".equals(tipo)) unidade = "°C";
					sensor.setUnidadeMedida(unidade);
					em.persist(sensor);
				}
			}
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			if (em != null && em.getTransaction().isActive()) {
			em.getTransaction().rollback();
			}
			this.mensagem = "Erro ao garantir sensores: " + ex.getMessage();
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}

	// Retorna uma lista com todos os sensores cadastrados
	public List<SensorEntity> obterTodosSensores() {
        EntityManager em = Conexao.obterEntityManager();
        try {
            TypedQuery<SensorEntity> q = em.createNamedQuery("SensorEntity.findAll", SensorEntity.class);
            return q.getResultList();
        } catch (RuntimeException ex) {
			this.mensagem = "Erro ao obter sensores: " + ex.getMessage();
			return null;
		} finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

}
