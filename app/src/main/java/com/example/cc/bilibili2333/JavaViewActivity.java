package com.example.cc.bilibili2333;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mytest.utils.BaseActivity;
import com.example.mytest.utils.JavaInterfaceUtils;
import com.example.mytest.utils.TextViewTest;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.example.mytest.utils.JavaInterfaceUtils.createAddr;
import static com.example.mytest.utils.JavaInterfaceUtils.createPerson;
import static com.example.mytest.utils.JavaInterfaceUtils.createStoreInfo;
import static com.example.mytest.utils.JavaInterfaceUtils.createTextViewEmpty;
import static com.example.mytest.utils.JavaInterfaceUtils.createTitle;
import static com.example.mytest.utils.JavaInterfaceUtils.initLayout;

public class JavaViewActivity extends BaseActivity {

    private LinearLayout m_view_linear;
    private TextView m_title;
    private Snackbar snackbar;
    private CoordinatorLayout m_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_view);
        m_title = (TextView) findViewById(R.id.title);
        m_container = (CoordinatorLayout) findViewById(R.id.container);
        m_container.setAnimation(AnimationUtils.loadAnimation(this,R.anim.alpha_into));
        testJava();
        snackbar = Snackbar.make(m_container, "这是主要内容", Snackbar.LENGTH_SHORT);
        snackbar.setAction("隐藏", new View.OnClickListener() {
            @Override
            public void onClick(View v){

            }
        });
    }

    private void testJava() {
        x.Ext.init(getApplication());
        RequestParams entity = new RequestParams("http://192.168.1.72:9090/user/123");
        entity.addBodyParameter("name","test");
        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                TextViewTest.setTxt(m_title,result);
                snackbar.show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                TextViewTest.setTxt(m_title,ex.getMessage());
                snackbar.show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                m_container.setAnimation(AnimationUtils.loadAnimation(JavaViewActivity.this,R.anim.alpha_out));
            }
        });
        m_view_linear = (LinearLayout) findViewById(R.id.view_linear);
        //屏幕适配计算
        initLayout(this);
        // title
        TextView 等待货主付款 = createTitle(this, "项目名称:"+getApplicationName());
        等待货主付款.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JavaViewActivity.this,SelectPayStateDialog.class));
            }
        });
        m_view_linear.addView(等待货主付款);
        //出发地 结束地
        m_view_linear.addView(createTextViewEmpty(this,null));
        m_view_linear.addView(createAddr(this,"出发地点青岛","目的地北京朝阳区","2017-5-6","2017-9-9"));
        //货源信息
        m_view_linear.addView(createTextViewEmpty(this,"货源信息"));
        JavaInterfaceUtils.GridBulid gridBulid = new JavaInterfaceUtils.GridBulid()
                .add("货物名称:","鲇鱼")
                .add("货物品类:","水产品")
                .add("货物类型:","冷冻")
                .add("货物规格:","7.9吨")
                .add("需要车长:","6.3米-9.8米")
                .add("需要车型:","普通货车")
                .add("温度要求:","-6度-0度")
                .add("备注:","无")
                .add("到货时间:","2017-5-6 → 2017-9-9");
        m_view_linear.addView(createStoreInfo(this, gridBulid, 0.3, 0.7));
        //承运方信息
        JavaInterfaceUtils.GridBulid gridBulid2 = new JavaInterfaceUtils.GridBulid()
                .add("订单编号:","GC1545523ADD")
                .add("生成时间:","2017-05-10 14:13:23");
        m_view_linear.addView(createPerson(this, R.mipmap.ic_launcher, gridBulid2));
        //订单信息的
        m_view_linear.addView(createTextViewEmpty(this,null));
        JavaInterfaceUtils.GridBulid gridBulid3 = new JavaInterfaceUtils.GridBulid()
                .add("订单编号:","GC1545523ADD")
                .add("生成时间:","2017-05-10 14:13:23");
        m_view_linear.addView(createStoreInfo(this, gridBulid3, 0.3, 0.7));
        //test
        m_view_linear.addView(createTextViewEmpty(this,"列表测试test"));
        int row = 0;
        JavaInterfaceUtils.GridBulid gridBulid0 = new JavaInterfaceUtils.GridBulid()
                .add((++row)+"","货物名称:","鲇鱼")
                .add((++row)+"","货物品类:","水产品")
                .add((++row)+"","货物类型:","冷冻")
                .add((++row)+"","货物规格:","7.9吨")
                .add((++row)+"","需要车长:","6.3米-9.8米")
                .add((++row)+"","需要车型:","普通货车")
                .add((++row)+"","温度要求:","-6度-0度")
                .add((++row)+"","备注:","无")
                .add((++row)+"","到货时间:","2017-5-6 → 2017-9-9");
        m_view_linear.addView(createStoreInfo(this, gridBulid0, 0.2, 0.3, 0.5));
    }

    public String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }
}
