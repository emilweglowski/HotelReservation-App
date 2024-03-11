package pl.emilweglowski.domain.guest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;

public class GuestJPARepository implements GuestRepository {

    private static final GuestRepository instance = new GuestJPARepository();

    public static GuestRepository getInstance() {
        return instance;
    }

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("unit");
    private static EntityManager em = factory.createEntityManager();

    @Override
    public Guest createNewGuest(String firstName, String lastName, int age, Gender gender) {
        Guest newGuest = new Guest(firstName, lastName, age, gender);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(newGuest);
        transaction.commit();

        return newGuest;
    }

    @Override
    public List<Guest> getAll() {
        return em.createQuery("SELECT a FROM Guest a", Guest.class).getResultList();
    }

    @Override
    public void saveAll() {
        System.out.println("Not implemented yet / not needed");
    }

    @Override
    public void readAll() {
        System.out.println("Not implemented yet / not needed");
    }

    @Override
    public void remove(long id) {
        Guest guestToRemove = em.find(Guest.class, id);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(guestToRemove);
        transaction.commit();
        System.out.println(guestToRemove.getInfo()+" has been deleted");
    }

    @Override
    public void edit(long id, String firstName, String lastName, int age, Gender gender) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Guest guest = em.find(Guest.class, id);
        guest.setFirstName(firstName);
        guest.setLastName(lastName);
        guest.setAge(age);
        guest.setGender(gender);
        transaction.commit();
        System.out.println("Guest object after  is: "+guest.getInfo());
    }

    @Override
    public Guest getById(long id) {
        return em.find(Guest.class, id);
    }
}
