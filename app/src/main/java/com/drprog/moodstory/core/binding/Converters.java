package com.drprog.moodstory.core.binding;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.drprog.moodstory.R;
import com.drprog.moodstory.core.LogHelper;
import com.drprog.moodstory.core.binding.handler.ActionClickListener;
import com.drprog.moodstory.core.image.ImageProgressListener;
import com.drprog.moodstory.core.image.transform.BlurTransformation;
import com.drprog.moodstory.core.image.transform.CircleBorderedTransform;
import com.drprog.moodstory.core.list.ListConfig;
import com.drprog.moodstory.core.binding.handler.Model;

/**
 * All Binding Adapters and converters in one place
 * Created by roman.donchenko on 18.01.2016
 */

public class Converters {
    private static final String LOG_TAG = LogHelper.makeLogTag("Converters");

    @BindingConversion
    public static ColorDrawable colorToDrawable(int color) {
        return new ColorDrawable(color);
    }

    @BindingConversion
    public static int booleanToInt(Boolean value) {
        return (value != null && value) ? View.VISIBLE : View.GONE;
    }

    @BindingConversion
    public static String bindableToString(BindableString bindableString) {
        return bindableString != null ? bindableString.get() : "";
    }

    @BindingConversion
    public static boolean bindableToBoolean(BindableBoolean bindableBoolean) {
        return bindableBoolean != null && bindableBoolean.get();
    }

    @BindingConversion
    public static int bindableToInt(BindableBoolean bindableBoolean) {
        return booleanToInt(bindableToBoolean(bindableBoolean));

    }

    @BindingAdapter({"bindCheckedChangeListener"})
    public static void bindCheckedChangeListener(CompoundButton view, final BindableBoolean bindableBoolean) {
        if (bindableBoolean == null) return;
        if (view.getTag(R.id.binded) == null) {
            view.setTag(R.id.binded, true);
            view.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    bindableBoolean.set(isChecked);
                }
            });
        }
        Boolean newValue = bindableBoolean.get();
        view.setChecked(newValue);
    }

    @BindingAdapter({"binding"})
    public static void bindEditText(EditText view, final BindableString bindableString) {
        if (bindableString == null) return;
        if (view.getTag(R.id.binded) == null) {
            view.setTag(R.id.binded, true);
            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    bindableString.set(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
        String newValue = bindableString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }
    }

    @BindingAdapter({"listConfig"})
    public static void configRecyclerView(RecyclerView recyclerView, ListConfig config) {
        config.applyConfig(recyclerView);
    }

    @BindingAdapter({"isRefreshing"})
    public static void setRefreshing(final SwipeRefreshLayout _view,
                                     final BindableBoolean _isRefreshing) {
        _view.setRefreshing(_isRefreshing.get());
    }

    @BindingAdapter("android:setRefreshing")
    public static void setRefreshing(final SwipeRefreshLayout view, final Boolean refreshing) {
        if (refreshing != null) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.setRefreshing(refreshing);
                }
            });
        }
    }

    @BindingAdapter({"error"})
    public static void bindErrorText(final EditText _view,
                                     final String _observableString) {
        _view.setError(_observableString);
    }

    @BindingAdapter({"error"})
    public static void bindErrorText(final TextInputLayout _view,
                                     final String _observableString) {
        _view.setError(_observableString);
    }

    @BindingAdapter(value = {"actionHandler", "actionType", "actionTypeLongClick", "model"}, requireAll = false)
    public static void onActionFired(final View view,
                                     final ActionClickListener listener,
                                     final String actionType, final String actionTypeLongClick,
                                     final Model model) {
        if (listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onActionClick(view, actionType, model);
                }
            });
        }
        if (actionTypeLongClick != null) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onActionClick(view, actionTypeLongClick, model);
                    return true;
                }
            });
        }
    }


    @BindingAdapter({"textHtml"})
    public static void setHtmlText(final TextView _textView, final String _htmlText) {
        _textView.setText(Html.fromHtml(_htmlText));
    }

//
//    @BindingAdapter({"typeface"})
//    public static void setTypeface(final TextView _textView, final String _typeface) {
//        _textView.setTypeface(Typeface.createFromAsset(
//                _textView.getContext().getAssets(),
//                "font/" + _typeface));
//    }

    @BindingAdapter("bindTint") // color - color in integer format
    public static void setColorTint(ImageView view, Integer color) {
        if (color == null) return;
        Drawable viewDrawable = view.getDrawable();
        if (viewDrawable == null) return;
        Drawable drawable = DrawableCompat.wrap(viewDrawable);
        DrawableCompat.setTint(drawable, color);
    }

    @BindingAdapter({"android:src", "android:tint"}) // color - color in integer format
    public static void setColorTint(ImageView view, Drawable viewDrawable, Integer color) {
        if (color == null) return;
        if (viewDrawable == null) viewDrawable = view.getDrawable();
        if (viewDrawable == null) return;
        Drawable drawable = DrawableCompat.wrap(viewDrawable);
        DrawableCompat.setTint(drawable, color);
        view.setImageDrawable(viewDrawable);
    }

    @BindingAdapter("android:backgroundTint")
    public static void setBackgroundTint(TextView textView, @ColorRes int tint) {
        if (tint == 0) return;
        Drawable background = DrawableCompat.wrap(textView.getBackground());
        DrawableCompat.setTint(background, ContextCompat.getColor(textView.getContext(), tint));
    }


    @BindingAdapter("adapter")
    public static <T extends android.widget.ListAdapter & Filterable> void setAutoCompleteAdapter(final AutoCompleteTextView view, final T adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter("bindVisibility")
    public static void bindFabVisibility(final FloatingActionButton fab, final Boolean show) {
        if (show == null || fab.isShown() == show) return;
        if (show) fab.show(); else fab.hide();
    }

    @BindingAdapter("focused")
    public static void requestFocus(final View view, final Boolean focused) {
        if (focused == null || !view.isFocusable() || view.isFocused() == focused) return;
        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (focused) {
            view.requestFocus();
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        } else {
            view.clearFocus();
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @BindingAdapter(value = {"glideImage", "glideDefault", "glidePlaceholder",
            "glideUseDrawableAsPlaceholder", "glideCacheStrategy", "glideTransform",
            "glideBorderColor", "glideProgressBar", "glideAnimationDisabled",}, requireAll = false)
    public static void setImageUri(ImageView _imageView, String _image, Drawable _defaultImage, Drawable _placeholder,
            boolean _useDrawableAsPlaceholder, String _cacheStrategy, String _transform,
            Integer _borderColor, View _progressBar, boolean glideAnimationDisabled) {
        LogHelper.LOGD(LOG_TAG, "setImageUri. Uri: " + _image + ", Placeholder: " + _placeholder + ", cacheStrategy: " + _cacheStrategy + ", transform: " + _transform);
        Context context = _imageView.getContext();
        //TODO: Workaround to solve a bug with crash after reenter on MainActivity after logout on api 15-16 (17-18?)
        if (context instanceof Activity && ((Activity) context).isFinishing()) return;
        if (TextUtils.isEmpty(_image)) {
            if (_defaultImage != null || _placeholder != null) {
                _imageView.setImageDrawable(_defaultImage != null ? _defaultImage : _placeholder);
            }
            return;
        }
        RequestManager glide = Glide.with(context);
        DrawableRequestBuilder request = glide.load(_image);
        if (!TextUtils.isEmpty(_cacheStrategy)) {
            try {
                request.diskCacheStrategy(Enum.valueOf(DiskCacheStrategy.class, _cacheStrategy.toUpperCase()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if ("CIRCLE".equalsIgnoreCase(_transform)) {
            BitmapPool pool = Glide.get(context).getBitmapPool();
//            request.bitmapTransform(new CropCircleTransformation(pool));
            final int color = _borderColor != null ? _borderColor :
                    ContextCompat.getColor(context, R.color.imageBorderColorDefault);
            request.bitmapTransform(new CircleBorderedTransform(pool, color));
        } else if ("BLUR".equalsIgnoreCase(_transform)) {
            BitmapPool pool = Glide.get(context).getBitmapPool();
            request.bitmapTransform(new BlurTransformation(context, pool, 10, 2));
        } else if ("FIT_CENTER".equalsIgnoreCase(_transform)) {
            request.fitCenter();
        } else //noinspection StatementWithEmptyBody
            if ("NONE".equalsIgnoreCase(_transform)) {
                //do not apply centerCrop
        } else {
            request.centerCrop();
        }
        if (_placeholder != null || _useDrawableAsPlaceholder) {
            if (!glideAnimationDisabled) request.crossFade();
            request.placeholder(_placeholder != null ? _placeholder : _imageView.getDrawable());
        }
        if (_progressBar != null) {
            //noinspection unchecked
            request.listener(new ImageProgressListener(_progressBar));
        }
        request.into(_imageView);
    }

    @BindingAdapter(value = {"glideImage", "glideDefault", "glidePlaceholder",
            "glideUseDrawableAsPlaceholder", "glideCacheStrategy", "glideTransform",
            "glideBorderColor", "glideImageViewResId", "glideProgressBarResId", "glideAnimationDisabled"}, requireAll = false)
    public static void setImageLoading(ViewGroup _viewGroup, String _image, Drawable _defaultImage, Drawable _placeholder,
            boolean _useDrawableAsPlaceholder, String _cacheStrategy, String _transform,
            Integer _borderColor, int _imageViewResId, int _progressBarResId, boolean glideAnimationDisabled) {
        int childCount;
        if ((childCount = _viewGroup.getChildCount()) > 0 && _imageViewResId != 0) {
            ImageView imageView = null;
            View progressBar = null;
            View child;
            for (int index = 0; index < childCount; index++) {
                child = _viewGroup.getChildAt(index);
                if (child.getId() == _imageViewResId && child instanceof ImageView) {
                    imageView = (ImageView) child;
                } else if (child.getId() == _progressBarResId) {
                    progressBar = child;
                }
                if (imageView != null && progressBar != null) break;
            }
            if (imageView != null) {
                setImageUri(imageView, _image, _defaultImage, _placeholder, _useDrawableAsPlaceholder,
                            _cacheStrategy, _transform, _borderColor, progressBar, glideAnimationDisabled);
            }
        }
    }

}