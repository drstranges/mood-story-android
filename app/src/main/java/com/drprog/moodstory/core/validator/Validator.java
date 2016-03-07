package com.drprog.moodstory.core.validator;

import android.databinding.ObservableField;

import java.util.Map;

/**
 * Created by roman.donchenko on 24.02.16.
 */
public abstract class Validator<M> {
    protected final Map<Integer, ObservableMessagePair> mErrorFields;
    protected final OnValidateListener mOnValidateListener;

    protected M mModel;
    protected final int[] mFieldsForValidate;

    public Validator(Map<Integer, ObservableMessagePair> errorFields, OnValidateListener onValidateListener) {
        mErrorFields = errorFields;
        mOnValidateListener = onValidateListener;
        mFieldsForValidate = getFieldsForValidate();
    }

    public abstract int[] getFieldsForValidate();
    public abstract boolean validateField(int validationMask);

    public void setModel(M _model) {
        mModel = _model;
    }

    public boolean isValid(M model) {
        setModel(model);
        return isValid();
    }


    public boolean isValid() {
        if (mModel == null) return false;
        for (Integer field : mFieldsForValidate) {
            if (!isValid(field)) return false;
        }
        return true;
    }

    public boolean isValid(int validationMask) {
        boolean isValid = validateField(validationMask);
        applyError(isValid, validationMask);
        if (mOnValidateListener != null)
            mOnValidateListener.onFieldValidated(validationMask, isValid);
        return isValid;
    }


    //==============================================================================================

    private void applyError(boolean isValid, int fieldName) {
        final ObservableMessagePair messagePair = mErrorFields.get(fieldName);
        if (messagePair != null) messagePair.apply(isValid);
    }

    public static class ObservableMessagePair {
        private final ObservableField<String> mObservableField;
        private final String mMessage;

        public ObservableMessagePair(ObservableField<String> observableField, String message) {
            mObservableField = observableField;
            mMessage = message;
        }

        public void apply(boolean clear) {
            mObservableField.set(clear ? null : mMessage);
        }
    }

    public interface OnValidateListener {
        void onFieldValidated(int field, boolean isValid);
    }
}

