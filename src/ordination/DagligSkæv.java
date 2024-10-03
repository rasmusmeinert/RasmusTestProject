package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DagligSkæv extends Ordination {
    // Instance fields.
    private final List<Dosis> doser = new ArrayList<Dosis>();

    public DagligSkæv(LocalDate startDato, LocalDate slutDato, Lægemiddel lægemiddel) {
        super(startDato, slutDato, lægemiddel);
    }

    public void opretDosis(LocalTime tid, double antal) {
        Dosis dosis = new Dosis(tid, antal);
        doser.add(dosis);
    }

    public List<Dosis> getDoser() {
        return new ArrayList<>(doser);
    }

    /**
     * Returner den totale dosis, der er givet i den periode, ordinationen er gyldig.
     */
    @Override
    public double samletDosis() {
        return døgnDosis() * antalDage();
    }

    /**
     * Returner den gennemsnitlige dosis givet per dag.
     */
    @Override
    public double døgnDosis() {
        double totalDosis = 0;
        for (Dosis dosis : doser) {
            totalDosis += dosis.getAntal();
        }
        return totalDosis;
    }

    /**
     * Returner ordinationstypen som en String.
     */
    @Override
    public String getType() {
        return "Daglig Skæv";
    }
}
