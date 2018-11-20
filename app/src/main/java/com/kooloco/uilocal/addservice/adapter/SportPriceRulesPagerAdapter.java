package com.kooloco.uilocal.addservice.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kooloco.model.SportPriceRules;
import com.kooloco.uilocal.addservice.fragment.SportPriceRulesFragment;

import java.util.List;

/**
 * Created by hlink44 on 27/11/17.
 */

public class SportPriceRulesPagerAdapter extends FragmentStatePagerAdapter {
    List<SportPriceRules> sportPriceRules;
    CallBack callBack;

    public SportPriceRulesPagerAdapter(FragmentManager fm, List<SportPriceRules> sportPriceRules, CallBack callBack) {
        super(fm);
        this.sportPriceRules = sportPriceRules;
        this.callBack = callBack;
    }

    @Override
    public Fragment getItem(int position) {
        SportPriceRulesFragment sportPriceRulesFragment = new SportPriceRulesFragment();

        sportPriceRulesFragment.setData(sportPriceRules.get(position));
        sportPriceRulesFragment.setPosition(position);
        sportPriceRulesFragment.setCallBack(callBack);

        return sportPriceRulesFragment;
    }

    @Override
    public int getCount() {
        return sportPriceRules.size();
    }

    public interface CallBack {
        void setData(int position, SportPriceRules sportPriceRules);
    }
}
