package danielle.dev.com.peopleinplaces;

import com.orm.SugarRecord;

/**
 * Created by daniellevass on 04/07/15.
 */
public class RecordModel extends SugarRecord<RecordModel> {

    private String onsID;
    private String name;
    private String area;

    private Integer population;
    private Integer boys;
    private Integer girls;


    private Double births;
    private Double birthsUnder18;
    private Double deaths;


    public RecordModel() {
    }

    public RecordModel(String onsID, String name, String area, Integer population, Integer boys, Integer girls, Double births, Double birthsUnder18, Double deaths) {
        this.onsID = onsID;
        this.name = name;
        this.area = area;
        this.population = population;
        this.boys = boys;
        this.girls = girls;
        this.births = births;
        this.birthsUnder18 = birthsUnder18;
        this.deaths = deaths;
    }

    public String getOnsID() {
        return onsID;
    }

    public String getName() {
        return name;
    }

    public String getArea() {
        return area;
    }

    public Integer getPopulation() {
        return population;
    }

    public Integer getBoys() {
        return boys;
    }

    public Integer getGirls() {
        return girls;
    }

    public Double getBirths() {
        return births;
    }

    public Double getBirthsUnder18() {
        return birthsUnder18;
    }

    public Double getDeaths() {
        return deaths / 100; //because deaths was per
    }
}
