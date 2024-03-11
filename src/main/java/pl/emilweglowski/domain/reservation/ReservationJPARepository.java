package pl.emilweglowski.domain.reservation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import pl.emilweglowski.domain.guest.Guest;
import pl.emilweglowski.domain.room.Room;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationJPARepository implements ReservationRepository{

    private static final ReservationRepository instance = new ReservationJPARepository();

    public static ReservationRepository getInstance(){
        return instance;
    }

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("unit");
    private static EntityManager em = factory.createEntityManager();

    @Override
    public Reservation createNewReservation(Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
        Reservation newReservation = new Reservation(room, guest, from, to);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(newReservation);
        transaction.commit();

        return newReservation;
    }

    @Override
    public void readAll() {
        System.out.println("Not implemented yet / not needed");
    }

    @Override
    public void saveAll() {
        System.out.println("Not implemented yet / not needed");
    }

    @Override
    public List<Reservation> getAll() {
        return em.createQuery("SELECT a FROM Reservation a", Reservation.class).getResultList();
    }

    @Override
    public void remove(long id) {
        Reservation reservationToRemove = em.find(Reservation.class, id);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(reservationToRemove);
        transaction.commit();
        System.out.println(reservationToRemove.getInfo()+" has been deleted");
    }
}
