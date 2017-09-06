package telesketch.lmsierra.com.telesketch.drawingUtils;

import android.graphics.Color;
import android.graphics.Paint;

public class UserLine extends Paint {

    public UserLine(){
        this(Color.GRAY);
    }

    public UserLine(int color){
        super();

        this.setColor(color);
        this.setAntiAlias(true);
        this.setDither(true);
        setStyle(Style.STROKE);
        setStrokeJoin(Join.ROUND);
        setStrokeCap(Cap.SQUARE);
        setStrokeWidth(1f);
    }
}
