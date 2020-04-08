package utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor(staticName = "of")
public class Task {
    int number;
    int durationOnFirstMachine;
    int durationOnSecondMachine;
}
