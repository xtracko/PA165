package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext
    private EntityManager em;

    public void create(Product p) {
        em.persist(p);
    }

    public void remove(Product p) {
        Product found = findById(p.getId());
        if (found != null)
            em.remove(found);
    }

    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();
    }

    public Product findById(Long id) {
        return em.find(Product.class, id);
    }


    public List<Product> findByName(String name) {
        return em.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class)
                .setParameter("name", name)
                .getResultList();
    }
}
