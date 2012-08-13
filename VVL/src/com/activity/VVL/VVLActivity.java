package com.activity.VVL;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.entity.scene.background.Background;
import org.andengine.opengl.texture.TextureOptions;

import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.entity.util.FPSLogger;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.Entity;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.CameraScene;
import org.andengine.engine.Engine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;

import android.view.KeyEvent;

/**
*	@author Fangirl
**/


public class VVLActivity extends SimpleBaseGameActivity{
    private static int CAMERA_WIDTH = 1280;
    private static int CAMERA_HEIGHT = 720;

    private Camera mCamera;
    private BitmapTextureAtlas mBitmapTextureAtlas, mBitmapTextureAtlas2;

    private Scene mMainScene;
    private ITextureRegion mBackgroundTextureRegion;
    private ITextureRegion mPausedTextureRegion;
    private CameraScene mPauseScene;


   	 @Override
   	 public EngineOptions onCreateEngineOptions(){
   	 	this.mCamera = new Camera(0,0,CAMERA_WIDTH,CAMERA_HEIGHT);
      
       return new EngineOptions(true,ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT),this.mCamera);
   	}

   	@Override
   	public void onCreateResources(){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("menu/");
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
        this.mPausedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "pause.png", 0, 0);
        this.mBitmapTextureAtlas.load();
        this.mBitmapTextureAtlas2 = new BitmapTextureAtlas(this.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
        this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas2, this, "lakebkg.png", 0, 0);
        this.mBitmapTextureAtlas2.load();
   	}
   	@Override
   	public Scene onCreateScene(){
   	
      this.mEngine.registerUpdateHandler(new FPSLogger());

      this.mPauseScene = new CameraScene(this.mCamera);
      /* Make the 'PAUSED'-label centered on the camera. */
      final float centerX = (CAMERA_WIDTH - this.mPausedTextureRegion.getWidth()) / 2;
      final float centerY = (CAMERA_HEIGHT - this.mPausedTextureRegion.getHeight()) / 2;
      final Sprite pausedSprite = new Sprite(centerX, centerY, this.mPausedTextureRegion, this.getVertexBufferObjectManager());
      this.mPauseScene.attachChild(pausedSprite);
      /* Makes the paused Game look through. */
      this.mPauseScene.setBackgroundEnabled(false);

      /* Just a simple */
      this.mMainScene = new Scene();
      this.mMainScene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
      Sprite backgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
      this.mMainScene.attachChild(backgroundSprite);
      return this.mMainScene;
   	}

    @Override
    public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
      if(pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
        if(this.mEngine.isRunning()) {
          this.mMainScene.setChildScene(this.mPauseScene, false, true, true);
          this.mEngine.stop();
        } else {
          this.mMainScene.clearChildScene();
          this.mEngine.start();
        }
        return true;
      } else return super.onKeyDown(pKeyCode, pEvent);
    }
}