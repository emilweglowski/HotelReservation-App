package pl.emilweglowski.domain.room;

import org.junit.jupiter.api.Test;
import pl.emilweglowski.domain.room.dto.RoomDTO;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoomTest {

    @Test
    public void toCSVTest() {

        //given
        List<BedType> beds = Arrays.asList(BedType.values());
        Room room = new Room(1, 302, beds);

        //when
        String createdCSV = room.toCSV();

        //then
        String csvTemplate = "1,302,Single#Double#King size"+System.getProperty("line.separator");
        assertEquals(csvTemplate, createdCSV, "Comparison of generated CVS formats");
    }

    @Test
    public void toCSVWithNullBedListTest() {

        //given
        Room room = new Room(1, 302, null);

        //when
        String createdCVS = room.toCSV();

        //then
        assertNotNull(room.getBeds());
        String cvsTemplate = "1,302,"+System.getProperty("line.separator");
        assertEquals(cvsTemplate, createdCVS, "Comparison of generated CVS formats with bed list == null");
    }

    @Test
    public void toDTOTest() {

        //given
        List<BedType> beds = Arrays.asList(BedType.values());
        Room room = new Room(1, 302, beds);

        //when
        RoomDTO roomDTO = room.generateDTO();

        //then
        assertEquals(roomDTO.getId(), 1);
        assertEquals(roomDTO.getNumber(), 302);
        assertEquals(roomDTO.getBedsCount(), 3);
        assertEquals(roomDTO.getRoomSize(), 5);
        assertEquals(roomDTO.getBeds(), "Single,Double,King size");
    }

    @Test
    public void toDTOFromRoomWithNullBedsListTest() {

        //given
        Room room = new Room(1, 302, null);

        //when
        RoomDTO roomDTO = room.generateDTO();

        //then
        assertEquals(roomDTO.getId(), 1);
        assertEquals(roomDTO.getNumber(), 302);
        assertEquals(roomDTO.getBedsCount(), 0);
        assertEquals(roomDTO.getRoomSize(), 0);
        assertEquals(roomDTO.getBeds(), "");
    }
}
