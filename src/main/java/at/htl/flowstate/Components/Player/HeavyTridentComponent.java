package at.htl.flowstate.Components.Player;

public class HeavyTridentComponent extends TridentComponent {

    public HeavyTridentComponent(double damage) {
        super(damage);
    }

    @Override
    protected void hitGround() {
        super.hitGround();
        //implement knockback
    }
}
