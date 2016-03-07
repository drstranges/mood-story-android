package com.drprog.moodstory.core.validator;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public class NewEventValidator extends Validator<Object> {

    @IntDef({EVENT_NAME, START_DATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NewEventField {
    }

    public static final int EVENT_NAME = 0;
    public static final int START_DATE = 1;

    public NewEventValidator(Map<Integer, ObservableMessagePair> errorFields, OnValidateListener onValidateListener) {
        super(errorFields, onValidateListener);
    }

    @Override
    public int[] getFieldsForValidate() {
        return new int[]{EVENT_NAME, START_DATE};
    }

    @Override
    public boolean validateField(@NewEventField int field) {
        boolean isValid = false;
        switch (field) {
            case EVENT_NAME:
//                isValid = mModel.getName() != null && mModel.getName().trim().length() > 0;
                break;
            case START_DATE:
//                final Date startDate = mModel.getStartDate();
//                isValid = startDate != null && startDate.after(DateTimeUtil.toUTC(new Date()));
                break;
        }
        return isValid;
    }

}
