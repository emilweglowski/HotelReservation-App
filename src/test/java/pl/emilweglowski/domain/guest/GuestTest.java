package pl.emilweglowski.domain.guest;

import org.junit.jupiter.api.Test;
import pl.emilweglowski.domain.guest.dto.GuestDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestTest {
    
    @Test
    public void toCSVTest() {
        
        //given
        Guest guest = new Guest(1, "Jan", "Kowalski", 40, Gender.MALE);
        
        //when
        String createdCSV = guest.toCSV();

        //then
        String csvTemplate = "1,Jan,Kowalski,40,Male"+System.getProperty("line.separator");
        assertEquals(csvTemplate, createdCSV);
    }

    @Test
    public void generateDTOTest() {

        //given
        Guest guest = new Guest(1, "Jan", "Kowalski", 40, Gender.MALE);

        //when
        GuestDTO guestDTO = guest.generateDTO();

        //then
        assertEquals(guestDTO.getId(), 1);
        assertEquals(guestDTO.getFirstName(), "Jan");
        assertEquals(guestDTO.getLastName(), "Kowalski");
        assertEquals(guestDTO.getAge(), 40);
        assertEquals(guestDTO.getGender(), "male");
    }

    @Test
    public void getInfoTest() {

        //given
        Guest guest = new Guest(1, "Jan", "Kowalski", 40, Gender.MALE);

        //when
        String guestInfo = guest.getInfo();

        //then
        String infoTemplate = "1 Jan Kowalski (40, Male)";
        assertEquals(guestInfo, infoTemplate);
    }
}
