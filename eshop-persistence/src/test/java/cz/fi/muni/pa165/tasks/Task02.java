package cz.fi.muni.pa165.tasks;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.assertj.core.api.SoftAssertions;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

import static org.assertj.core.api.Assertions.*;
 
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;

	private Category electro;
	private Category kitchen;
	private Product flashlight;
	private Product kitchenRobot;
	private Product plate;


	@BeforeClass
	public void setup() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		electro = new Category();
		electro.setName("Electro");
		em.persist(electro);

		kitchen = new Category();
		kitchen.setName("Kitchen");
		em.persist(kitchen);

		flashlight = new Product();
		flashlight.setName("Flashlight");
		flashlight.addCategory(electro);
		em.persist(flashlight);


		kitchenRobot = new Product();
		kitchenRobot.setName("Kitchen Robot");
		kitchenRobot.addCategory(electro);
		kitchenRobot.addCategory(kitchen);
		em.persist(kitchenRobot);


		plate = new Product();
		plate.setName("Plate");
		plate.addCategory(kitchen);
		em.persist(plate);

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testElectro() {
		EntityManager em = emf.createEntityManager();

		Category found = em.find(Category.class, electro.getId());

		assertThat(found)
				.isNotNull();
		assertThat(found)
				.hasFieldOrPropertyWithValue("name", "Electro");
		assertThat(found.getProducts().size())
				.isEqualTo(2);
		assertContainsProductWithName(found.getProducts(), "Flashlight");
		assertContainsProductWithName(found.getProducts(), "Kitchen Robot");

		em.close();
	}

	@Test
	public void testKitchen() {
		EntityManager em = emf.createEntityManager();

		Category found = em.find(Category.class, kitchen.getId());

		assertThat(found)
				.isNotNull();
		assertThat(found)
				.hasFieldOrPropertyWithValue("name", "Kitchen");
		assertThat(found.getProducts().size())
				.isEqualTo(2);
		assertContainsProductWithName(found.getProducts(), "Plate");
		assertContainsProductWithName(found.getProducts(), "Kitchen Robot");

		em.close();
	}

	@Test
	public void testFlashlight() {
		EntityManager em = emf.createEntityManager();
		Product found = em.find(Product.class, flashlight.getId());

		assertThat(found)
				.isNotNull();
		assertThat(found.getCategories().size())
				.isEqualTo(1);
		assertContainsCategoryWithName(found.getCategories(), "Electro");

		em.close();
	}

	@Test
	public void testKitchenRobot() {
		EntityManager em = emf.createEntityManager();
		Product found = em.find(Product.class, kitchenRobot.getId());

		assertThat(found)
				.isNotNull();
		assertThat(found.getCategories().size())
				.isEqualTo(2);
		assertContainsCategoryWithName(found.getCategories(), "Electro");
		assertContainsCategoryWithName(found.getCategories(), "Kitchen");

		em.close();
	}

	@Test
	public void testPlate() {
		EntityManager em = emf.createEntityManager();
		Product found = em.find(Product.class, plate.getId());

		assertThat(found)
				.isNotNull();
		assertThat(found.getCategories().size())
				.isEqualTo(1);
		assertContainsCategoryWithName(found.getCategories(), "Kitchen");

		em.close();
	}

	@Test(expectedExceptions=ConstraintViolationException.class)
	public void testDoesntSaveNullName() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Product product = new Product();
		em.persist(product);

		em.getTransaction().commit();
		em.close();
	}

	private void assertContainsCategoryWithName(Set<Category> categories,
			String expectedCategoryName) {
		for(Category cat: categories){
			if (cat.getName().equals(expectedCategoryName))
				return;
		}
			
		Assert.fail("Couldn't find category "+ expectedCategoryName+ " in collection "+categories);
	}
	private void assertContainsProductWithName(Set<Product> products,
			String expectedProductName) {
		
		for(Product prod: products){
			if (prod.getName().equals(expectedProductName))
				return;
		}
			
		Assert.fail("Couldn't find product "+ expectedProductName+ " in collection "+products);
	}

	
}
