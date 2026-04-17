package at.htl.flowstate.Components.Player;

public class RecallTridentComponent extends TridentComponent{
    public RecallTridentComponent(double damage) {
        super(damage);
    }

    @Override
    protected void hitGround() {
        super.hitGround();
        entity.getComponent(HomingProjectileComponent.class).start();
    }
}
