package app.hanks.com.conquer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import app.hanks.com.conquer.R;
import app.hanks.com.conquer.activity.AlertActivity;
import app.hanks.com.conquer.activity.FriendDataActivity;
import app.hanks.com.conquer.bean.Zixi;
import app.hanks.com.conquer.util.A;
import app.hanks.com.conquer.util.TimeUtil;
import app.hanks.com.conquer.util.ZixiUtil;
import app.hanks.com.conquer.view.CircularImageView;

public class FriendZixiFragment extends BaseFragment {

    private Zixi        zixi;
    private ImageLoader loader;

    private View root;

    public View getRootView() {
        return root;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_friendzixi, container, false);
        Bundle bundle = getArguments();
        zixi = (Zixi) bundle.getSerializable("zixi");
        loader = ImageLoader.getInstance();
        root = v;
        init(v);
        return v;
    }

    /**
     * 初始化
     *
     * @param v
     */
    private void init(View view) {
        CircularImageView iv_photo = (CircularImageView) view.findViewById(R.id.iv_photo);
        ImageView iv_gender = (ImageView) view.findViewById(R.id.iv_gender);
        TextView tv_Nick = (TextView) view.findViewById(R.id.tv_nickname);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
        TextView tv_dis = (TextView) view.findViewById(R.id.tv_dis);
        TextView tv_created_time = (TextView) view.findViewById(R.id.tv_created_time);
        TextView tv_zixitime = (TextView) view.findViewById(R.id.tv_zixitime);
        TextView tv_note = (TextView) view.findViewById(R.id.tv_note);
        try {
            tv_created_time.setText("("
                    + TimeUtil.getDescriptionTimeFromTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(zixi.getCreatedAt())
                    .getTime()) + ")");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tv_note.setText(zixi.getNote());
        tv_zixitime.setText(ZixiUtil.getDescriptionTimeFromTimestamp(zixi.getTime()));
        tv_time.setText(ZixiUtil.getZixiTimeS(zixi));
        tv_name.setText(zixi.getName());
        tv_Nick.setText(zixi.getUser().getNick());
        tv_dis.setText(ZixiUtil.getDistance(currentUser, zixi.getUser().getLocation()));
        loader.displayImage(zixi.getUser().getAvatar(), iv_photo);
        iv_photo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FriendDataActivity.class);
                i.putExtra("friendName", zixi.getUser().getUsername());
                A.goOtherActivity(context, i);
            }
        });
        iv_gender.setImageResource(zixi.getUser().isMale() ? R.drawable.ic_male : R.drawable.ic_female);
        // 陪她按钮
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, AlertActivity.class);
                intent.putExtra("zixi", zixi);
                A.goOtherActivity(context, intent);
            }
        });
    }
}
