package com.example.google.playservices.placecomplete;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Dr.Abhay on 08-06-2015.
 */
public class MyFragmentPageAdapter extends FragmentPagerAdapter {



        final int PAGE_COUNT = 2;

        /** Constructor of the class */
        public MyFragmentPageAdapter(FragmentManager fm)
        {
            super(fm);
        }
       /* public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }*/

        /** This method will be invoked when a page is requested to create */
        @Override
        public Fragment getItem(int arg0) {
            if (arg0==0)
            {
                MyFragment myFragment = new MyFragment();
                Bundle data = new Bundle();
                data.putInt("current_page", arg0+1);
                myFragment.setArguments(data);
                return myFragment;
            }
            else
            {
                UMatchFragment umf = new UMatchFragment();
                Bundle data = new Bundle();
                data.putInt("current_page", arg0+1);
                umf.setArguments(data);
                return umf;
                //getItem(1);
                //return null;
            }
        }



        /** Returns the number of pages */
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position==0)
                return "Map";
            else
                return "Matched";

        }

    }
