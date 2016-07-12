/*
 * Copyright (c) 2016 Nick Guo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.nick.imageloader.display;

import android.support.annotation.DrawableRes;

import dev.nick.imageloader.display.animator.ImageAnimator;
import dev.nick.imageloader.display.processor.BitmapProcessor;

public class DisplayOption {

    private int defaultImgRes;
    private int loadingImgRes;

    private ImageQuality quality;

    private BitmapProcessor processor;
    private ImageAnimator animator;

    private boolean applyImageOneByOne;
    private boolean viewMaybeReused;

    private DisplayOption(int defaultImgRes,
                          int loadingImgRes,
                          ImageQuality quality,
                          BitmapProcessor processor,
                          ImageAnimator animator,
                          boolean applyImageOneByOne,
                          boolean viewMaybeReused) {
        this.defaultImgRes = defaultImgRes;
        this.loadingImgRes = loadingImgRes;
        this.quality = quality;
        this.processor = processor;
        this.animator = animator;
        this.applyImageOneByOne = applyImageOneByOne;
        this.viewMaybeReused = viewMaybeReused;
    }

    public int getDefaultImgRes() {
        return defaultImgRes;
    }

    public int getLoadingImgRes() {
        return loadingImgRes;
    }

    public ImageQuality getQuality() {
        return quality;
    }

    public BitmapProcessor getProcessor() {
        return processor;
    }

    public ImageAnimator getAnimator() {
        return animator;
    }

    public boolean isApplyImageOneByOne() {
        return applyImageOneByOne;
    }

    public boolean isViewMaybeReused() {
        return viewMaybeReused;
    }

    public static class Builder {

        private int defaultImgRes;
        private int loadingImgRes;

        private ImageQuality quality = ImageQuality.FIT_VIEW;

        private BitmapProcessor processor;
        private ImageAnimator animator;

        private boolean oneAfterOne;
        private boolean viewMaybeReused;

        /**
         * @param defaultImgRes Image res showing when load failure.
         * @return Instance of this builder.
         */
        public Builder defaultImgRes(@DrawableRes int defaultImgRes) {
            this.defaultImgRes = defaultImgRes;
            return this;
        }

        /**
         * @param loadingImgRes Image res showing when loading.
         * @return Instance of this builder.
         */
        public Builder loadingImgRes(@DrawableRes int loadingImgRes) {
            this.loadingImgRes = loadingImgRes;
            return this;
        }

        /**
         * @param processor {@link BitmapProcessor} instance using to process the bitmap before display.
         * @return Instance of this builder.
         */
        public Builder bitmapProcessor(BitmapProcessor processor) {
            this.processor = processor;
            return this;
        }

        /**
         * Set a image animator to perform an animation when displaying image.
         *
         * @param animator The animator you want to set.
         * @return @return Instance of this builder.
         */
        public Builder imageAnimator(ImageAnimator animator) {
            this.animator = animator;
            return this;
        }

        /**
         * Set the image quality displaying, lower image quality has
         * a better performance.
         *
         * @param quality Image quality when displaying.
         * @return Instance of this builder.
         */
        public Builder imageQuality(ImageQuality quality) {
            this.quality = quality;
            return this;
        }

        /**
         * Display the image one by one.
         *
         * @return Instance of this builder.
         */
        public Builder oneAfterOne() {
            this.oneAfterOne = true;
            return this;
        }

        /**
         * Indicate that the view may be reused, the image won't be set if there is a new request for the same view.
         *
         * @return Instance of this builder.
         */
        public Builder viewMaybeReused() {
            this.viewMaybeReused = true;
            return this;
        }

        public DisplayOption build() {
            return new DisplayOption(defaultImgRes, loadingImgRes, quality, processor, animator, oneAfterOne, viewMaybeReused);
        }
    }

    public enum ImageQuality {
        /**
         * Using raw image when decode and display the image.
         */
        RAW,

        /**
         * Decrease the size of the image to fit the view dimen.
         */
        FIT_VIEW
    }
}
