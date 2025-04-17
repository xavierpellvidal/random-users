package com.random.user.navigation.viewmodel

import androidx.lifecycle.ViewModel
import com.random.user.presentation.navigation.FeatureNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel
    @Inject
    constructor(
        private val featureNavigation: Set<@JvmSuppressWildcards FeatureNavigation>,
    ) : ViewModel() {
        val subNavigation: Set<FeatureNavigation>
            get() = featureNavigation
    }
