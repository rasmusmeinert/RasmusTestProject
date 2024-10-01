package ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class PN extends Ordination{
    private final double antalEnheder;

    private final ArrayList<LocalDate> datoForAnvendelse = new ArrayList<LocalDate>();


    public PN(LocalDate startDato, LocalDate slutDato, Lægemiddel lægemiddel, double antalEnheder) {
        super(startDato, slutDato, lægemiddel);
        this.antalEnheder = antalEnheder;
    }

    public double getAntalEnheder() {
        return antalEnheder;
    }

    /**
     * Registrer datoen for en anvendt dosis.
     */
    public void anvendDosis(LocalDate dato) {
        datoForAnvendelse.add(dato);
    }

    /** Returner antal gange ordinationen er anvendt. */
    public int antalGangeAnvendt() {
        return datoForAnvendelse.size();
    }

    @Override
    public double samletDosis() {
        return antalGangeAnvendt()*antalEnheder;
    }
    @Override
    public double døgnDosis() {
        return ((antalGangeAnvendt()*getAntalEnheder()) / (antalDage()));
    }

    @Override
    public String getType() {
        return "PN";
    }
}
