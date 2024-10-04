package test.ordination;

import controller.Controller;
import ordination.Lægemiddel;
import ordination.PN;
import ordination.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PNTest {


    @BeforeEach
    void setup() {
    }

    @Test
    void samletDosis() {
        Patient Jens = new Patient("123412-4321","Jens Hansen", 120);
        Lægemiddel Paracetamol = new Lægemiddel("Paracetamol",1,1.5,2,"ML");
        PN Ordination1 = new PN( LocalDate.of(2024,12,10),LocalDate.of(2024,12,20),Paracetamol,5);
        PN Ordination2 = new PN( LocalDate.of(2024,12,10),LocalDate.of(2024,12,20),Paracetamol,5);
        Ordination1.anvendDosis(LocalDate.of(2024,12,13));
        Ordination1.anvendDosis(LocalDate.of(2024,12,14));
        Ordination1.anvendDosis(LocalDate.of(2024,12,15));
        Ordination1.anvendDosis(LocalDate.of(2024,12,16));
        Ordination1.anvendDosis(LocalDate.of(2024,12,17));

        double actualOutput = Ordination1.samletDosis();
        assertEquals(25,actualOutput,0.0001);
    }
    @Test
    void samletDosis2() {
        Lægemiddel Paracetamol = new Lægemiddel("Paracetamol",1,1.5,2,"ML");
        PN Ordination2 = new PN( LocalDate.of(2024,12,10),LocalDate.of(2024,12,20),Paracetamol,5);

        double actualOutput2 = Ordination2.samletDosis();
        assertEquals(0,actualOutput2,0.0001);
    }

    @Test
    void døgnDosis() {
        Patient Jens = new Patient("123412-4321","Jens Hansen", 120);
        Lægemiddel Paracetamol = new Lægemiddel("Paracetamol",1,1.5,2,"ML");
        PN Ordination1 = new PN( LocalDate.of(2024,12,10),LocalDate.of(2024,12,20),Paracetamol,5);
        Ordination1.anvendDosis(LocalDate.of(2024,12,10));
        Ordination1.anvendDosis(LocalDate.of(2024,12,14));
        Ordination1.anvendDosis(LocalDate.of(2024,12,14));
        Ordination1.anvendDosis(LocalDate.of(2024,12,18));
        Ordination1.anvendDosis(LocalDate.of(2024,12,20));

        double actualOutput = Ordination1.døgnDosis();

        assertEquals(2.5,actualOutput,0.0001);
    }

    @Test
    void døgnDosis1() {
        Patient Jens = new Patient("123412-4321","Jens Hansen", 120);
        Lægemiddel Paracetamol = new Lægemiddel("Paracetamol",1,1.5,2,"ML");
        PN Ordination1 = new PN( LocalDate.of(2024,12,10),LocalDate.of(2024,12,20),Paracetamol,5);

        double actualOutput = Ordination1.døgnDosis();
        assertEquals(0,actualOutput,0.0001);

    }
}