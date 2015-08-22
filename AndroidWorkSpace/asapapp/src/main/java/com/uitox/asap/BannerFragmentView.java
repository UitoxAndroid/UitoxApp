package com.uitox.asap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.uitox.view.BaseFragmentView;
import com.uitox.lib.ShowYourMessage;

import java.util.HashMap;

public class BannerFragmentView extends BaseFragmentView implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    SliderLayout sliderShow;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.banner_fragment_view, container, false);
        sliderShow = (SliderLayout) rootView.findViewById(R.id.banner);
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            sliderShow.addSlider(textSliderView);
        }
        sliderShow.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderShow.setCustomAnimation(new DescriptionAnimation());
        sliderShow.setDuration(4000);
        sliderShow.addOnPageChangeListener(this);
        return rootView;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initBundle(Bundle bundle) {

    }

    //點到banner時要做的事情
    @Override
    public void onSliderClick(BaseSliderView slider) {
        ShowYourMessage.msgToShowShort(getActivity(), slider.getBundle().get("extra") + "");
    }

    //APP要回到主要activity時必須要讓動畫跑起來,因為在stop已經關掉
    @Override
    public void onResume() {
        sliderShow.startAutoCycle();
        super.onResume();
    }

    //當activity要停止時,動畫也跟著停止
    @Override
    public void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        //ShowYourMessage.msgToShowShort(getActivity(),"onPageScrolled");
    }

    @Override
    public void onPageSelected(int i) {
        //ShowYourMessage.msgToShowShort(getActivity(),"onPageSelected");
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        //ShowYourMessage.msgToShowShort(getActivity(),"onPageScrollStateChanged");
    }

    @Override
    public void updateUI() {

    }
}
