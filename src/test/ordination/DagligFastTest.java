package test.ordination;

import ordination.DagligFast;
import ordination.Dosis;
import ordination.Lægemiddel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DagligFastTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void samletDosisTc1() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligFast dagligFast = new DagligFast(LocalDate.of(2024, 12, 10), LocalDate.of(2024, 12, 20), paracetamol);
        dagligFast.opretDosis(0, LocalTime.of(8, 0), 5);
        dagligFast.opretDosis(1, LocalTime.of(12, 0), 5);
        dagligFast.opretDosis(2, LocalTime.of(17, 0), 5);
        dagligFast.opretDosis(3, LocalTime.of(23, 0), 5);

        double samletDosis = dagligFast.samletDosis();
        assertEquals(220,samletDosis);

    }


    @Test
    void samletDosisTc2() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligFast dagligFast = new DagligFast(LocalDate.of(2024, 12, 10), LocalDate.of(2024, 12, 20), paracetamol);

        double samletDosis = dagligFast.samletDosis();
        assertEquals(0,samletDosis);

    }

    @Test
    void døgnDosisTc1() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligFast dagligFast = new DagligFast(LocalDate.of(2024, 12, 10), LocalDate.of(2024, 12, 20), paracetamol);
        dagligFast.opretDosis(0, LocalTime.of(8, 0), 5);
        dagligFast.opretDosis(1, LocalTime.of(12, 0), 5);
        dagligFast.opretDosis(2, LocalTime.of(17, 0), 5);
        dagligFast.opretDosis(3, LocalTime.of(23, 0), 5);

        double døgnDosis = dagligFast.døgnDosis();
        assertEquals(20,døgnDosis);
    }


    @Test
    void døgnDosisTc2() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        DagligFast dagligFast = new DagligFast(LocalDate.of(2024, 12, 10), LocalDate.of(2024, 12, 20), paracetamol);

        double døgnDosis = dagligFast.døgnDosis();
        assertEquals(0,døgnDosis);
    }
}