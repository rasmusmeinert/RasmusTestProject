package test.controller;

import controller.Controller;
import ordination.DagligFast;
import ordination.Dosis;
import ordination.Lægemiddel;
import ordination.Patient;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void opretDagligFastOrdinationTc1() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient patient = new Patient("123456-1234","Hans Hansen", 83.5);

        DagligFast dagligFast = Controller.opretDagligFastOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,2,12),patient,paracetamol,2,1,1,0);

        assertEquals(LocalDate.of(1995,2,7), dagligFast.getStartDato());
        assertEquals(LocalDate.of(1995,2,12),dagligFast.getSlutDato());
        assertEquals(paracetamol,dagligFast.getLaegemiddel());
        assertEquals(dagligFast,patient.getOrdinationer().getFirst());
        assertEquals(2,dagligFast.getDoser()[0].getAntal());
        assertEquals(1,dagligFast.getDoser()[1].getAntal());
        assertEquals(1,dagligFast.getDoser()[2].getAntal());
        assertEquals(0,dagligFast.getDoser()[3].getAntal());

    }

    @Test
    void opretDagligFastOrdinationTc2() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient patient = new Patient("123456-1234","Hans Hansen", 83.5);

        Exception exception = assertThrows(RuntimeException.class, () -> Controller.opretDagligFastOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,2,2),patient,paracetamol,2,1,1,0));
        assertEquals(exception.getMessage(), "startDato er efter slutDato");
    }

    @Test
    void opretDagligFastOrdinationTc3() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient patient = new Patient("123456-1234","Hans Hansen", 83.5);

        Exception exception = assertThrows(RuntimeException.class, () -> Controller.opretDagligFastOrdination(LocalDate.of(1995,2,31),LocalDate.of(1995,12,0),patient,paracetamol,2,1,1,0));
        assertEquals(exception.getMessage(), "Invalid date 'FEBRUARY 31'");
    }
    @Test
    void opretDagligSkævOrdination() {
    }

    @Test
    void anbefaletDosisPrDøgnTc1() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient patient = new Patient("123456-1234","Jan Oluf", 120);
        Patient patient2 = new Patient("456789-2345","Bille Jensen", 25);
        Patient patient3 = new Patient("567890-3456","Flenning Jakke", 140);

        double anbefaletDosis = Controller.anbefaletDosisPrDøgn(patient,paracetamol);

        assertEquals(180,anbefaletDosis);
    }

    @Test
    void anbefaletDosisPrDøgnTc2() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient patient = new Patient("456789-2345","Bille Jensen", 25);

        double anbefaletDosis = Controller.anbefaletDosisPrDøgn(patient,paracetamol);

        assertEquals(25,anbefaletDosis);
    }
    @Test
    void anbefaletDosisPrDøgnTc3() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient patient = new Patient("567890-3456","Flenning Jakke", 140);

        double anbefaletDosis = Controller.anbefaletDosisPrDøgn(patient,paracetamol);

        assertEquals(280,anbefaletDosis);
    }
    @Test
    void antalOrdinationerPrVægtPrLægemiddel() {
        Lægemiddel paracetamol = Controller.opretLægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient Hans = Controller.opretPatient("123456-1234","Hans Hansen",93.5);
        Patient Pedro = Controller.opretPatient("123456-4322","Pedro Pascal",95);

        double actualoutput = Controller.antalOrdinationerPrVægtPrLægemiddel(90,110,paracetamol);

        assertEquals(2,actualoutput,0.0001);

    }
}