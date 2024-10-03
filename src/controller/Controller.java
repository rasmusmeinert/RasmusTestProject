package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import ordination.*;
import storage.Storage;

public abstract class Controller {
    private static Storage storage;

    public static void setStorage(Storage storage) {
        Controller.storage = storage;
    }

    /**
     * Opret og returner en PN ordination.
     * Hvis startDato er efter slutDato, kastes en IllegalArgumentException,
     * og ordinationen oprettes ikke.
     * Pre: antal > 0.
     */
    public static PN opretPNOrdination(
            LocalDate startDato, LocalDate slutDato, Patient patient, Lægemiddel lægemiddel,
            double antal) {
        if (startDato.isAfter(slutDato)) {
            throw new IllegalArgumentException("StartDato er efter SlutDato");
        } else {
            PN pnOrdination = new PN(startDato, slutDato, lægemiddel, antal);
            patient.tilføjOrdination(pnOrdination);
            return pnOrdination;
        }

    }

    /**
     * Opret og returner en DagligFast ordination.
     * Hvis startDato er efter slutDato, kastes en IllegalArgumentException,
     * og ordinationen oprettes ikke.
     * Pre: morgenAntal, middagAntal, aftenAntal, natAntal >= 0
     */
    public static DagligFast opretDagligFastOrdination(
            LocalDate startDato, LocalDate slutDato, Patient patient, Lægemiddel lægemiddel,
            double morgenAntal, double middagAntal, double aftenAntal, double natAntal) {
        if (startDato.isAfter(slutDato)) {
            throw new IllegalArgumentException("StartDato er efter SlutDato");
        } else {
            DagligFast dagligFastOrdination = new DagligFast(startDato, slutDato, lægemiddel);
            dagligFastOrdination.opretDosis(0, LocalTime.of(8, 0), morgenAntal);
            dagligFastOrdination.opretDosis(1, LocalTime.of(12, 0), middagAntal);
            dagligFastOrdination.opretDosis(2, LocalTime.of(17, 0), aftenAntal);
            dagligFastOrdination.opretDosis(3, LocalTime.of(23, 0), natAntal);
            patient.tilføjOrdination(dagligFastOrdination);
            return dagligFastOrdination;
        }
    }

    /**
     * Opret og returner en DagligSkæv ordination.
     * Hvis startDato er efter slutDato, kastes en IllegalArgumentException,
     * og ordinationen oprettes ikke.
     * Hvis antallet af elementer i klokkeSlet og antalEnheder er forskellige,
     * kastes en IllegalArgumentException, og ordinationen oprettes ikke.
     * Pre: I antalEnheder er alle tal >= 0.
     */
    public static DagligSkæv opretDagligSkævOrdination(
            LocalDate startDen, LocalDate slutDen, Patient patient, Lægemiddel lægemiddel,
            LocalTime[] klokkeSlet, double[] antalEnheder) {

        if (!startDen.isAfter(slutDen)) {
            if (klokkeSlet.length == antalEnheder.length) {
                DagligSkæv dagligSkæv = new DagligSkæv(startDen, slutDen, lægemiddel);
                for (int i = 0; i < klokkeSlet.length; i++) {
                    dagligSkæv.opretDosis(klokkeSlet[i], antalEnheder[i]);
                }
                patient.tilføjOrdination(dagligSkæv);
                return dagligSkæv;
            } else
                throw new IllegalArgumentException("Klokkeslet og antal enheder forskellige.");
        } else
            throw new IllegalArgumentException("Startdato er efter slutdato.");
    }

    /**
     * Tilføj en dato for anvendelse af PN ordinationen.
     * Hvis datoen ikke er indenfor ordinationens gyldighedsperiode,
     * kastes en IllegalArgumentException.
     */
    public static void anvendOrdinationPN(PN ordination, LocalDate dato) {
        if (dato.isAfter(ordination.getSlutDato())) {
            throw new IllegalArgumentException("Dato er efter slutDato");
        } else {
            ordination.anvendDosis(dato);
        }

    }

    /**
     * Returner den anbefalede dosis pr. døgn af lægemidlet til patienten
     * (afhænger af patientens vægt).
     */
    public static double anbefaletDosisPrDøgn(Patient patient, Lægemiddel lægemiddel) {
        if (patient.getVægt() <= 25) {
            return patient.getVægt() * lægemiddel.getEnhedPrKgPrDøgnLet();
        }
        if (25 < patient.getVægt() && patient.getVægt() <= 120) {
            return patient.getVægt() * lægemiddel.getEnhedPrKgPrDøgnNormal();
        } else {
            return patient.getVægt() * lægemiddel.getEnhedPrKgPrDøgnTung();
        }
    }

    /**
     * Returner antal ordinationer for det givne vægtinterval og det givne lægemiddel.
     */
    public static int antalOrdinationerPrVægtPrLægemiddel(
            double vægtStart, double vægtSlut, Lægemiddel lægemiddel) {
        int antalOrdinationer = 0;
        for (Patient patient : getAllPatienter()) {
            if (patient.getVægt() >= vægtStart && patient.getVægt() <= vægtSlut) {
                for (Ordination ordination : patient.getOrdinationer()) {
                    if (ordination.getLaegemiddel() == lægemiddel) {
                        antalOrdinationer++;
                    }
                }
            }
            return antalOrdinationer;
        }
        return 0;
    }

    public static List<Patient> getAllPatienter() {
        return storage.getAllPatienter();
    }

    public static List<Lægemiddel> getAllLægemidler() {
        return storage.getAllLægemidler();
    }

    public static Patient opretPatient(String cpr, String navn, double vægt) {
        Patient p = new Patient(cpr, navn, vægt);
        storage.storePatient(p);
        return p;
    }

    public static Lægemiddel opretLægemiddel(
            String navn, double enhedPrKgPrDøgnLet, double enhedPrKgPrDøgnNormal,
            double enhedPrKgPrDøgnTung, String enhed) {
        Lægemiddel lm = new Lægemiddel(navn, enhedPrKgPrDøgnLet,
                enhedPrKgPrDøgnNormal, enhedPrKgPrDøgnTung, enhed);
        storage.storeLægemiddel(lm);
        return lm;
    }

    public static void initStorage() {
        Patient jane = opretPatient("121256-0512", "Jane Jensen", 63.4);
        Patient finn = opretPatient("070985-1153", "Finn Madsen", 83.2);
        opretPatient("050972-1233", "Hans Jørgensen", 89.4);
        opretPatient("011064-1522", "Ulla Nielsen", 59.9);
        Patient ib = opretPatient("090149-2529", "Ib Hansen", 87.7);

        Lægemiddel acetylsalicylsyre = opretLægemiddel(
                "Acetylsalicylsyre", 0.1, 0.15,
                0.16, "Styk");
        Lægemiddel paracetamol = opretLægemiddel(
                "Paracetamol", 1, 1.5,
                2, "Ml");
        Lægemiddel fucidin = opretLægemiddel(
                "Fucidin", 0.025, 0.025,
                0.025, "Styk");
        opretLægemiddel(
                "Methotrexate", 0.01, 0.015,
                0.02, "Styk");

        opretPNOrdination(LocalDate.parse("2019-01-01"), LocalDate.parse("2019-01-12"),
                jane, paracetamol, 123);

        opretPNOrdination(LocalDate.parse("2019-02-12"), LocalDate.parse("2019-02-14"),
                jane, acetylsalicylsyre, 3);

        opretPNOrdination(LocalDate.parse("2019-01-20"), LocalDate.parse("2019-01-25"),
                ib, fucidin, 5);

        opretPNOrdination(LocalDate.parse("2019-01-01"), LocalDate.parse("2019-01-12"),
                jane, paracetamol, 123);

        opretDagligFastOrdination(LocalDate.parse("2019-01-10"), LocalDate.parse("2019-01-12"),
                finn, fucidin, 2, 0, 1, 0);

        LocalTime[] kl = {
                LocalTime.parse("12:00"), LocalTime.parse("12:40"),
                LocalTime.parse("16:00"), LocalTime.parse("18:45")};
        double[] an = {0.5, 1, 2.5, 3};
        opretDagligSkævOrdination(LocalDate.parse("2019-01-23"), LocalDate.parse("2019-01-24"),
                finn, fucidin, kl, an);
    }
}