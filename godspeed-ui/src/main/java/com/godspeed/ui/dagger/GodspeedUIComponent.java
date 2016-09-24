package com.godspeed.ui.dagger;


import com.godspeed.source.dagger.GodspeedComponent;
import com.godspeed.ui.activity.DaggerActivity;
import com.godspeed.ui.activity.DaggerFragmentActivity;
import com.godspeed.ui.adpter.DaggerBaseAdapter;
import com.godspeed.ui.fragment.DaggerFragment;
import com.godspeed.ui.widget.DaggerGridLayout;
import com.godspeed.ui.widget.DaggerGridView;
import com.godspeed.ui.widget.DaggerImageView;
import com.godspeed.ui.widget.DaggerListView;
import com.godspeed.ui.widget.DaggerRelativeLayout;
import com.godspeed.ui.widget.DaggerScrollView;
import com.godspeed.ui.widget.DaggerView;

public  interface GodspeedUIComponent extends GodspeedComponent {

      void inject(DaggerActivity daggerActivity);
      void inject(DaggerFragment daggerFragment);
      void inject(DaggerFragmentActivity daggerFragmentActivity);

      void inject(DaggerBaseAdapter daggerAdapter);

      void inject(DaggerGridLayout daggerGridLayout);
      void inject(DaggerGridView daggerGridView);
      void inject(DaggerImageView daggerImageView);
      void inject(DaggerListView daggerListView);
      void inject(DaggerRelativeLayout daggerRelativeLayout);
      void inject(DaggerScrollView daggerScrollView);
      void inject(DaggerView daggerView);

}
