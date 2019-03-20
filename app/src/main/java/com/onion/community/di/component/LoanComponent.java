package com.onion.community.di.component;


import com.onion.community.di.module.LoanModule;
import com.onion.community.di.scope.LoanScope;
import dagger.Component;

/**
 * Created by OnionMac on 2018/7/30.
 */
@LoanScope
@Component(dependencies = ActivityComponent.class, modules = LoanModule.class)
public interface LoanComponent {

}
