package ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/*
Ved daglig skæv forstås, at medicinen gives dagligt, i den periode ordinationen er gyldig. Med skæv menes,
at man ved ordinations oprettelse bestemmer, hvilke klokkeslæt medicinen skal gives på. Når ordinationen
oprettes, angives det, hvor meget og på hvilke tidspunkter af dagen patienten skal have medicinen.
Her kan angives et vilkårligt antal tidspunkter på døgnet, og for hvert tidspunkt angives antallet af enheder, patienten
skal have af den pågældende medicin.

 • Kompositionen mellem DagligSkæv og Dosis skal realiseres ved en List<Dosis>
 */
public class DagligSkæv extends Ordination {
    // Instance fields
    private final List<Dosis> doser = new ArrayList<Dosis>();

    public DagligSkæv(LocalDate startDato, LocalDate slutDato, Lægemiddel lægemiddel) {
        super(startDato, slutDato, lægemiddel);
    }

    public void tilføjDosis(Dosis dosis) {
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
        return totalDosis / antalDage();
    }

    /**
     * Returner ordinationstypen som en String.
     */
    @Override
    public String getType() {
        return "Daglig Skæv";
    }
}
