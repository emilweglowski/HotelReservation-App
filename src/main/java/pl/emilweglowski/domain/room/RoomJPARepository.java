package pl.emilweglowski.domain.room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class RoomJPARepository implements RoomRepository {

    private static final RoomRepository instance = new RoomJPARepository();

    public static RoomRepository getInstance() {
        return instance;
    }

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("unit");
    private static EntityManager em = factory.createEntityManager();

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
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Room roomToRemove = em.find(Room.class, id);
        em.remove(roomToRemove);
        transaction.commit();
        System.out.println(roomToRemove.getInfo()+" has been deleted");
    }

    @Override
    public void editRoom(long id, int roomNumber, List<BedType> bedTypes) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Room room = em.find(Room.class, id);
        room.setNumber(roomNumber);
        room.setBeds(bedTypes);
        transaction.commit();
        System.out.println("Room object after edit is: "+room.getInfo());
    }

    @Override
    public Room getById(long id) {
        return em.find(Room.class, id);
    }

    @Override
    public Room createNewRoom(int roomNumber, List<BedType> bedType) {
        Room newRoom = new Room(roomNumber, bedType);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(newRoom);
        transaction.commit();

        return newRoom;
    }

    @Override
    public List<Room> getAllRooms() {
        return em.createQuery("SELECT a FROM Room a", Room.class).getResultList();
    }
}
