package test.ordination;

import controller.Controller;
import ordination.DagligSkæv;
import ordination.Dosis;
import ordination.Lægemiddel;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DagligSkævTest {

    @Test
    void constructorDagligSkævTC1() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligSkæv dagligSkæv = new DagligSkæv(LocalDate.of(1995, 2, 7), LocalDate.of(1995, 3, 7), paracetamol);

        assertEquals(LocalDate.of(1995, 2, 7), dagligSkæv.getStartDato());
        assertEquals(LocalDate.of(1995, 3, 7), dagligSkæv.getSlutDato());
        assertEquals(paracetamol, dagligSkæv.getLaegemiddel());
    }


    @Test
    void opretDosisTC1() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligSkæv dagligSkæv = new DagligSkæv(LocalDate.of(1995, 2, 7), LocalDate.of(1995, 2, 7), paracetamol);
        dagligSkæv.opretDosis(LocalTime.of(12, 0), 3);
        dagligSkæv.opretDosis(LocalTime.of(14, 0), 2);
        dagligSkæv.opretDosis(LocalTime.of(18, 0), 5);

        assertEquals(LocalTime.of(12, 0), dagligSkæv.getDoser().get(0).getTid());
        assertEquals(3, dagligSkæv.getDoser().get(0).getAntal());
        assertEquals(LocalTime.of(14, 0), dagligSkæv.getDoser().get(1).getTid());
        assertEquals(2, dagligSkæv.getDoser().get(1).getAntal());
        assertEquals(LocalTime.of(18, 0), dagligSkæv.getDoser().get(2).getTid());
        assertEquals(5, dagligSkæv.getDoser().get(2).getAntal());
    }

    @Test
    void opretDosisTC2() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligSkæv dagligSkæv = new DagligSkæv(LocalDate.of(1995, 2, 7), LocalDate.of(1995, 2, 7), paracetamol);

        assertThrows(DateTimeException.class, () -> {
            dagligSkæv.opretDosis(LocalTime.of(25, 12), 3);

        });
    }

    @Test
    void opretDosisTC3() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligSkæv dagligSkæv = new DagligSkæv(LocalDate.of(1995, 2, 7), LocalDate.of(1995, 2, 7), paracetamol);
        assertThrows(IllegalArgumentException.class, () -> {
            dagligSkæv.opretDosis(LocalTime.of(12, 0), -3);
        });
    }

    @Test
    void getDoserTC1() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligSkæv dagligSkæv = new DagligSkæv(LocalDate.of(1995, 2, 7), LocalDate.of(1995, 2, 7), paracetamol);
        dagligSkæv.opretDosis(LocalTime.of(12, 0), 3);
        dagligSkæv.opretDosis(LocalTime.of(14, 0), 2);
        dagligSkæv.opretDosis(LocalTime.of(18, 0), 5);

        assertEquals(LocalDate.of(1995, 2, 7), dagligSkæv.getStartDato());
        assertEquals(LocalDate.of(1995, 2, 7), dagligSkæv.getSlutDato());

        assertEquals(LocalTime.of(12, 0), dagligSkæv.getDoser().get(0).getTid());
        assertEquals(3, dagligSkæv.getDoser().get(0).getAntal());
        assertEquals(LocalTime.of(14, 0), dagligSkæv.getDoser().get(1).getTid());
        assertEquals(2, dagligSkæv.getDoser().get(1).getAntal());
        assertEquals(LocalTime.of(18, 0), dagligSkæv.getDoser().get(2).getTid());
        assertEquals(5, dagligSkæv.getDoser().get(2).getAntal());
    }

    @Test
    void getDoserTC2() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligSkæv dagligSkæv = new DagligSkæv(LocalDate.of(1995, 2, 7), LocalDate.of(1995, 2, 7), paracetamol);

        assertEquals(new ArrayList<Dosis>().isEmpty(), dagligSkæv.getDoser().isEmpty());
    }

    @Test
    void samletDosisTC1() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligSkæv dagligSkæv = new DagligSkæv(LocalDate.of(1995, 11, 9), LocalDate.of(1995, 11, 15), paracetamol);
        dagligSkæv.opretDosis(LocalTime.of(12, 0), 3);
        dagligSkæv.opretDosis(LocalTime.of(14, 0), 2);
        dagligSkæv.opretDosis(LocalTime.of(18, 0), 5);

        assertEquals(70, dagligSkæv.samletDosis());
    }

    @Test
    void samletDosisTC2() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligSkæv dagligSkæv = new DagligSkæv(LocalDate.of(1995, 2, 7), LocalDate.of(1995, 2, 7), paracetamol);

        assertEquals(0, dagligSkæv.samletDosis());
    }

    @Test
    void samletDosisTC3() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligSkæv dagligSkæv = new DagligSkæv(LocalDate.of(1995, 2, 7), LocalDate.of(1995, 2, 7), paracetamol);
        dagligSkæv.opretDosis(LocalTime.of(12, 0), 2);
        dagligSkæv.opretDosis(LocalTime.of(14, 0), 2);
        dagligSkæv.opretDosis(LocalTime.of(18, 0), 1);
        assertEquals(5, dagligSkæv.samletDosis());
    }

    @Test
    void døgnDosisTC1() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligSkæv dagligSkæv = new DagligSkæv(LocalDate.of(1995, 2, 7), LocalDate.of(1995, 2, 7), paracetamol);
        dagligSkæv.opretDosis(LocalTime.of(12, 0), 3);
        dagligSkæv.opretDosis(LocalTime.of(14, 0), 2);
        dagligSkæv.opretDosis(LocalTime.of(18, 0), 5);

        assertEquals(10, dagligSkæv.døgnDosis());
    }

    @Test
    void døgnDosisTC2() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligSkæv dagligSkæv = new DagligSkæv(LocalDate.of(1995, 2, 7), LocalDate.of(1995, 2, 7), paracetamol);


        assertEquals(0, dagligSkæv.døgnDosis());
    }


    @Test
    void getTypeTC1() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligSkæv dagligSkæv = new DagligSkæv(LocalDate.of(1995, 2, 7), LocalDate.of(1995, 2, 7), paracetamol);

        assertEquals("Daglig Skæv", dagligSkæv.getType());
    }
}