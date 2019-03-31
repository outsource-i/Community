package com.onion.community.di.component;


import com.onion.community.di.module.LoanModule;
import com.onion.community.di.scope.LoanScope;
import com.onion.community.engine.community.detail.CommunityActivity;
import com.onion.community.engine.community.detail.detail.CommunityDetailActivity;
import com.onion.community.engine.community.post.PostActivity;
import com.onion.community.engine.self.activity.ActivityActivity;
import com.onion.community.engine.self.collection.CollectionActivity;
import com.onion.community.engine.self.mycommunity.MyCommunityActivity;
import dagger.Component;

/**
 * Created by OnionMac on 2018/7/30.
 */
@LoanScope
@Component(dependencies = ActivityComponent.class, modules = LoanModule.class)
public interface LoanComponent {

    void inject(CommunityActivity communityActivity);

    void inject(CommunityDetailActivity communityDetailActivity);

    void inject(PostActivity postActivity);

    void inject(CollectionActivity collectionActivity);

    void inject(MyCommunityActivity myCommunityActivity);

    void inject(ActivityActivity activityActivity);

}
