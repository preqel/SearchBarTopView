package search.preqel.com.searchtopbarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by preqel on 2018/3/23.
 */

public class SearchTopBarView extends RelativeLayout {

    private Context mContext;
    private View rl_topbar;
    private View fl_left, fl_right;
    protected EditText et_search;
    private TextView tv_right;
    private TextView tv_left;

    public SearchTopBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context, attrs);
    }

    public SearchTopBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.search_top_bar, this);
        rl_topbar = findViewById(R.id.rl_topbar);
        et_search = findViewById(R.id.et_search);
        tv_right = findViewById(R.id.tv_right);
        tv_left = findViewById(R.id.tv_left);
        fl_left = findViewById(R.id.fl_left);
//        fl_right = findViewById(R.id.fl_right);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBarView);
        String titleValue = typedArray.getString(R.styleable.TopBarView_titleText);
        String rightValue = typedArray.getString(R.styleable.TopBarView_menuText);
        Drawable rightImage = typedArray.getDrawable(R.styleable.TopBarView_menuImage);
        int leftVisible = typedArray.getInt(R.styleable.TopBarView_leftVisible, 0);
        int rightTextColor = typedArray.getColor(R.styleable.TopBarView_rightTextColor, Color.parseColor("#ff000000"));
        setLeftVisible(leftVisible);
        tv_right.setTextColor(rightTextColor);
        if (rightValue != null && rightValue.length() > 0) {
            tv_right.setText(rightValue);
            tv_right.setVisibility(View.VISIBLE);
        }
        typedArray.recycle();
        setNormalOnLeftClickListener();
    }

    public void addNormalTextChangeListener(TextWatcher textWatcher) {
        et_search.addTextChangedListener(textWatcher);
    }

    public void setRrightClickListener(View.OnClickListener listener) {
        tv_right.setOnClickListener(listener);
    }

    public String getText() {
        if (et_search != null) {
            return et_search.getText().toString();
        }
        return "";
    }


    private void setLeftVisible(int leftVisible) {
        switch (leftVisible) {
            case VISIBLE:
                fl_left.setVisibility(VISIBLE);
                break;
            case INVISIBLE:
                fl_left.setVisibility(INVISIBLE);
                break;
            case GONE:
                fl_left.setVisibility(GONE);
                break;
            default:
                break;
        }
    }

    public void setTitleText(String text) {
        setText(et_search, text);
    }


    private void setText(TextView view, CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            view.setVisibility(GONE);
        } else {
            view.setVisibility(VISIBLE);
            view.setText(text);
        }
    }

    private void setNormalOnLeftClickListener() {
        //Back
        fl_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doBack(mContext);
            }
        });
    }


    private void doBack(final Context context) {
        Class c = context.getClass();
        try {
            Method method = c.getMethod("onBackPressed");
            method.invoke(context);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
