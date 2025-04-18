package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

public class Key extends CollectableItem {
    private int number;

    public Key(Position position, int number) {
        super(position);
        this.number = number;
    }

    public int getnumber() {
        return number;
    }
}
