package utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;


@Getter
@Setter
@ToString
@NoArgsConstructor

public class Item {
    private static int numb = 1;
    private String name;
    @Min(1)
    private int price;
    @Min(1)
    private int weight;
    private double ratio;

    public Item(int price, int weight) {
        this.name = "item " + numb;
        this.price = price;
        this.weight = weight;
        this.ratio = (double) price / weight;
        numb++;
    }

}
