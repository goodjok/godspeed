package com.godspeed.service.dagger;

import com.godspeed.service.dagger.activity.DaggerActivity;
import com.godspeed.service.dagger.activity.DaggerFragment;
import com.godspeed.service.dagger.activity.DaggerFragmentActivity;
import com.godspeed.service.dagger.activity.DaggerV4Fragment;
import com.godspeed.service.dagger.adapter.DaggerAdapter;
import com.godspeed.service.dagger.widget.DaggerGridLayout;
import com.godspeed.service.dagger.widget.DaggerGridView;
import com.godspeed.service.dagger.widget.DaggerImageView;
import com.godspeed.service.dagger.widget.DaggerListView;
import com.godspeed.service.dagger.widget.DaggerRelativeLayout;
import com.godspeed.service.dagger.widget.DaggerScrollView;
import com.godspeed.service.dagger.widget.DaggerView;
import com.godspeed.source.dagger.GodspeedComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {HttpModule.class})
public  interface HttpComponent extends GodspeedComponent {



      void inject(DaggerActivity daggerActivity);
      void inject(DaggerFragment daggerFragment);
      void inject(DaggerFragmentActivity daggerFragmentActivity);
      void inject(DaggerV4Fragment daggerV4Fragment);

      void inject(DaggerAdapter daggerAdapter);

      void inject(DaggerGridLayout daggerGridLayout);
      void inject(DaggerGridView daggerGridView);
      void inject(DaggerImageView daggerImageView);
      void inject(DaggerListView daggerListView);
      void inject(DaggerRelativeLayout daggerRelativeLayout);
      void inject(DaggerScrollView daggerScrollView);
      void inject(DaggerView daggerView);

      void inject(DaggerObject daggerObject);


      DaggerHttpService provideHttpService();


}
