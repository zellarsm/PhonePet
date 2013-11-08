package com.example.connect4;


import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;

public class AnimateDrawable extends ProxyDrawable {
    
    private Animation mAnimation;
    private Transformation mTransformation = new Transformation();
    private boolean isAnimationStarted = false;
    private long lastModified = 0;
    
    public AnimateDrawable(Drawable target) {
        super(target);
    }
    
    public AnimateDrawable(Drawable target, Animation animation) {
        super(target);
        mAnimation = animation;
    }
    
    public Animation getAnimation() {
        return mAnimation;
    }
    
    public void setAnimation(Animation anim) {
        mAnimation = anim;
    }

    public boolean hasStarted() {
        return mAnimation != null && mAnimation.hasStarted();
    }
    
    public boolean hasEnded() {
        return mAnimation == null || mAnimation.hasEnded();
    }
    
    public void enableStartFlag(){
    	isAnimationStarted = true;
    	this.lastModified = System.currentTimeMillis();
    }

    public boolean getStartFlag(){
    	return isAnimationStarted;
    }

    @Override
    public void draw(Canvas canvas) {
        Drawable dr = getProxy();
        if (dr != null) {
            int sc = canvas.save();
            Animation anim = mAnimation;
            if (anim != null) {
                anim.getTransformation(
                                    AnimationUtils.currentAnimationTimeMillis(),
                                    mTransformation);
                canvas.concat(mTransformation.getMatrix());
            }
            dr.draw(canvas);
            canvas.restoreToCount(sc);
            if(isAnimationStarted && System.currentTimeMillis() - lastModified > 1000 && hasEnded()){
            	isAnimationStarted = false;
            }
        }
    }
}
    
