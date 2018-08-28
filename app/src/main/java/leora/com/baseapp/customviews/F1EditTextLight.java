package leora.com.baseapp.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import leora.com.baseapp.Constants;

/**
 * Created by Azr on 22/2/17.
 */
public class F1EditTextLight extends EditText{

    public F1EditTextLight(Context context) {
        super(context);
        init();
    }

    public F1EditTextLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public F1EditTextLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setTypeface(Constants.getFont1Light(getContext()));
    }
}
