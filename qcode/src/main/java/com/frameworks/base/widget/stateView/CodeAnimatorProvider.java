package com.frameworks.base.widget.stateView;

import android.animation.Animator;
import android.view.View;

/**
 * @author Nukc.
 */

public interface CodeAnimatorProvider {

    Animator showAnimation(View view);

    Animator hideAnimation(View view);
}
