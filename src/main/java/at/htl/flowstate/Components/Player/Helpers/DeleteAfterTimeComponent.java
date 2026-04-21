package at.htl.flowstate.Components.Player.Helpers;

import com.almasb.fxgl.entity.component.Component;

public class DeleteAfterTimeComponent extends Component {
    private double time;

    public DeleteAfterTimeComponent(double time) {
        this.time = time;
    }

    @Override
    public void onUpdate(double tpf) {
        if(time <= 0) {
            entity.removeFromWorld();
        }else {
            time -= tpf;
        }
    }
}
