package server.entity;

import java.io.Serializable;

public class Option implements Serializable {
    private String optionName;
    private boolean isCorrect;
    private int addPoints;
    private int removePoints;

    public Option(String optionName, boolean isCorrect, int addPoints, int removePoints) {
        this.optionName = optionName;
        this.isCorrect = isCorrect;
        this.addPoints = addPoints;
        this.removePoints = removePoints;
    }

    public String getOptionName() {
        return optionName;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public int getAddPoints() {
        return addPoints;
    }

    public int getRemovePoints() {
        return removePoints;
    }
}