package com.harry.mdapp.ui.book;

import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.harry.mdapp.R;
import com.harry.mdapp.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 * url=https://api.douban.com/v2/book/1003078
 * @author Harry
 * @date 2017/3/28.
 */

public class BookFragment extends BaseFragment {
    
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_address)
    TextView mAddress;
    @BindView(R.id.iv)
    SimpleDraweeView iv;
    @BindView(R.id.tv_content)
    TextView mContent;
    @BindView(R.id.head_bar_layout)
    AppBarLayout mHeadBarLayout;
    @BindView(R.id.collaps_layout)
    CollapsingToolbarLayout mCollLayout;
    
    
    @Override
    protected int getContentLayout() {
        return R.layout.fragment_book;
    }
    
    @Override
    protected void initInitData() {
        super.initInitData();
        setShowTitleBar(false);
    }
    
    @Override
    protected void initContentView(View view) {
        super.initContentView(view);
        ButterKnife.bind(this,view);
        mHeadBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -mHeadBarLayout.getHeight() / 2) {
                
                } else {
                    
                }
            }
        });
    }
    
    @Override
    protected void initData() {
        super.initData();
        mTitle.setText("小王子");
        mAddress.setText(Html.fromHtml("序言：法兰西玫瑰\\n小王子\\n圣埃克苏佩里年表\\n中国友谊出版公司"));
        iv.setImageURI(Uri.parse("https://img3.doubanio.com/mpic/s3294754.jpg"));
        mContent.setText("圣埃克苏佩里（1900－1944）1900年，尼采逝世。这一年，安德烈・纪德在布鲁塞尔一次会议上宣称：“当今文学土地的面貌可以说是一片沼泽。”1900年，圣埃克苏佩里诞生。净化沼泽的意愿和能力历史地落在这个“世纪儿”的身上，圣埃克苏佩里是尼采式的第二代法国作家，拿但业的儿子，琐罗亚斯德的孙子，这个飞行员受到极大的遗传影响。灾种影响使他在探索、忧虑和英雄主义的道路上走到尽头。如尼采所说：“你应该超截止自己，走得更远，登得更高，直至群星在你脚下。”尼采成为他座舱中想象的伙伴。这个飞越沙漠和海洋的年轻驾驶员同样也遵循着纪德的教诲：“与其过宁静的生活，不如过悲怆的生活。”圣埃克苏佩里在他整个的一生中都在反复思考力量和热诚的真谛。\n圣埃克苏佩里（AntoinedeSaiot-Exupery），1900年出生于法国里昂，1921-1923年在法国空军中服役，曾是后备飞行员，后来又成为民用航空驾驶员，参加了开辟法国――非洲――南美国际航线的工作，其间他还从事文学写作，作品有《南线班机》（1930），《夜航》（1931）等等。\n1939年德国法西斯入侵法国，鉴于圣埃克苏佩里曾多次受伤，医生认为他不能再入伍参战；但经他坚决要求，参加了抗德战争，被编入2/33空军侦察大队。1940年法国在战争中溃败，他所在的部队损失惨重，该部被调往阿尔及尔，随后即被复员，他只身流亡美国。在美国期间，他继续从事写作，1940年发表了《战斗飞行员》，1943年发表了《给一个人质的信》以及《小王子》。\n1943年，在他的强烈要求下，他回到法国在北非的抗战基地阿尔及尔。他的上级考虑到他的身体和年龄状况，只同意他执行五次飞行任务，他却要求到八次，1944年7月31日上午，他出航执行第八次任务，从此再也没有回来，牺牲时，年仅44岁。\n在欧洲某地的一个湖中，发现了圣・德克旭贝里的飞机残骸。这次搜索是经过对他最后一次出航的线路和德军当时的空军记录研究以后进行的，经过认证确认是那架失踪了半个世纪的侦察机。为了纪念这位伟大的战士和文学家，"
            + "当地决定为这架飞机的残骸建立一个博物馆，以他的名字命名，陈列他的作品和遗物。");
        
    }
}
