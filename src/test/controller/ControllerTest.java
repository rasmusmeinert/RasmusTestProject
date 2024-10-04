package test.controller;

import controller.Controller;
import ordination.*;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

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
    void opretDagligSkævOrdinationTc1() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient patient = new Patient("123456-1234","Hans Hansen", 83.5);
        LocalTime[] klokkeslæt = {LocalTime.of(12,0),LocalTime.of(14,0),LocalTime.of(18,0)};
        double enheder[] = {3,2,5};

        DagligSkæv dagligSkæv = Controller.opretDagligSkævOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,2,12),patient,paracetamol,klokkeslæt,enheder);


        assertEquals(LocalDate.of(1995,2,7), dagligSkæv.getStartDato());
        assertEquals(LocalDate.of(1995,2,12),dagligSkæv.getSlutDato());
        assertEquals(paracetamol,dagligSkæv.getLaegemiddel());
        assertEquals(dagligSkæv,patient.getOrdinationer().getFirst());
        assertEquals(3,dagligSkæv.getDoser().get(0).getAntal());
        assertEquals(2,dagligSkæv.getDoser().get(1).getAntal());
        assertEquals(5,dagligSkæv.getDoser().get(2).getAntal());
    }

    @Test
    void opretDagligSkævOrdinationTc2() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient patient = new Patient("123456-1234","Hans Hansen", 83.5);
        LocalTime[] klokkeslæt = {LocalTime.of(12,0),LocalTime.of(14,0),LocalTime.of(18,0)};
        double enheder[] = {3,2,5};

        Exception exception = assertThrows(RuntimeException.class, () -> Controller.opretDagligSkævOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,2,2),patient,paracetamol,klokkeslæt,enheder));
        assertEquals(exception.getMessage(), "startDato er efter slutDato.");
    }

    @Test
    void opretDagligSkævOrdinationTc3() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient patient = new Patient("123456-1234","Hans Hansen", 83.5);
        LocalTime[] klokkeslæt = {LocalTime.of(12,0),LocalTime.of(14,0),LocalTime.of(18,0),LocalTime.of(20,0)};
        double enheder[] = {3,2,5};

        Exception exception = assertThrows(RuntimeException.class, () -> Controller.opretDagligSkævOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,2,12),patient,paracetamol,klokkeslæt,enheder));
        assertEquals(exception.getMessage(), "Klokkeslet og antal enheder forskellige.");
    }
    @Test
    void opretDagligSkævOrdinationTc4() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient patient = new Patient("123456-1234","Hans Hansen", 83.5);
        LocalTime[] klokkeslæt = {LocalTime.of(12,0),LocalTime.of(14,0),LocalTime.of(18,0)};
        double enheder[] = {3,2,5,2};

        Exception exception = assertThrows(RuntimeException.class, () -> Controller.opretDagligSkævOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,2,12),patient,paracetamol,klokkeslæt,enheder));
        assertEquals(exception.getMessage(), "Klokkeslet og antal enheder forskellige.");
    }
    @Test
    void opretDagligSkævOrdinationTc5() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient patient = new Patient("123456-1234","Hans Hansen", 83.5);
        LocalTime[] klokkeslæt = {LocalTime.of(12,0),LocalTime.of(14,0),LocalTime.of(18,0)};
        double enheder[] = {3,2,5};


        Exception exception = assertThrows(RuntimeException.class, () -> Controller.opretDagligSkævOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,13,12),patient,paracetamol,klokkeslæt,enheder));
        assertEquals(exception.getMessage(), "Invalid value for MonthOfYear (valid values 1 - 12): 13");
    }
    @Test
    void opretDagligSkævOrdinationTc6() {
        Lægemiddel paracetamol = new Lægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient patient = new Patient("123456-1234","Hans Hansen", 83.5);
        LocalTime[] klokkeslæt = {LocalTime.of(25,0),LocalTime.of(14,0),LocalTime.of(18,0)};
        double enheder[] = {3,2,5};


        assertThrows(DateTimeException.class, () -> Controller.opretDagligSkævOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,12,12),patient,paracetamol,klokkeslæt,enheder));

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
    void antalOrdinationerPrVægtPrLægemiddelTC1() {
        Controller.setStorage(new Storage());

        Lægemiddel paracetamol = Controller.opretLægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient Hans = Controller.opretPatient("123456-1234","Hans Hansen",93.5);
        Patient Pedro = Controller.opretPatient("123456-4322","Pedro Pascal",95);

        DagligFast HansOrdination = Controller.opretDagligFastOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,2,12),Hans,paracetamol,2,1,1,0);
        DagligFast PedroOrdination = Controller.opretDagligFastOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,2,12),Pedro,paracetamol,2,1,1,0);


        double actualoutput = Controller.antalOrdinationerPrVægtPrLægemiddel(90,110,paracetamol);

        assertEquals(2,actualoutput,0.0001);
    }
    @Test
    void antalOrdinationerPrVægtPrLægemiddelTC2() {
        Controller.setStorage(new Storage());

        Lægemiddel paracetamol = Controller.opretLægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient Hans = Controller.opretPatient("123456-1234","Hans Hansen",93.5);
        Patient Pedro = Controller.opretPatient("123456-4322","Pedro Pascal",95);

        DagligFast HansOrdination = Controller.opretDagligFastOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,2,12),Hans,paracetamol,2,1,1,0);
        DagligFast PedroOrdination = Controller.opretDagligFastOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,2,12),Pedro,paracetamol,2,1,1,0);


        double actualoutput = Controller.antalOrdinationerPrVægtPrLægemiddel(60,90,paracetamol);

        assertEquals(0,actualoutput,0.0001);
    }

    @Test
    void antalOrdinationerPrVægtPrLægemiddelTC3() {
        Controller.setStorage(new Storage());

        Lægemiddel paracetamol = Controller.opretLægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient Hans = Controller.opretPatient("123456-1234","Hans Hansen",93.5);
        Patient Pedro = Controller.opretPatient("123456-4322","Pedro Pascal",95);

        DagligFast HansOrdination = Controller.opretDagligFastOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,2,12),Hans,paracetamol,2,1,1,0);
        DagligFast PedroOrdination = Controller.opretDagligFastOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,2,12),Pedro,paracetamol,2,1,1,0);


        Exception exception = assertThrows(IllegalArgumentException.class, () -> Controller.antalOrdinationerPrVægtPrLægemiddel(100,70,paracetamol));
        assertEquals(exception.getMessage(),"vægtSlut må ikke være højere end vægtStart");
    }
    @Test
    void antalOrdinationerPrVægtPrLægemiddelTC4() {
        Controller.setStorage(new Storage());

        Lægemiddel paracetamol = Controller.opretLægemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        Patient Hans = Controller.opretPatient("123456-1234","Hans Hansen",93.5);
        Patient Pedro = Controller.opretPatient("123456-4322","Pedro Pascal",95);

        DagligFast HansOrdination = Controller.opretDagligFastOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,2,12),Hans,paracetamol,2,1,1,0);
        DagligFast PedroOrdination = Controller.opretDagligFastOrdination(LocalDate.of(1995,2,7),LocalDate.of(1995,2,12),Pedro,paracetamol,2,1,1,0);


        Exception exception = assertThrows(IllegalArgumentException.class, () -> Controller.antalOrdinationerPrVægtPrLægemiddel(-1,50,paracetamol));
        assertEquals(exception.getMessage(),"vægtStart må ikke være negativ");
    }
}