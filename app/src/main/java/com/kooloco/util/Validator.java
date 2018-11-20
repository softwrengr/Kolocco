package com.kooloco.util;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.kooloco.exception.ApplicationException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Created by hlink21 on 5/5/16.
 */
@Singleton
public class Validator {

    private Context context;

    @Inject
    public Validator(Context context) {
        this.context = context;
    }

    String subject;
    TextView editText;
    List<MessageBuilder> messageBuilders = new ArrayList<>();
    private TextInputLayout textInputLayout;

    public Validator submit(TextView s) {
        subject = s.getText().toString();
        editText = s;
        messageBuilders = new ArrayList<>();
        return this;
    }

    public Validator submit(TextView s, TextInputLayout textInputLayout) {
        subject = s.getText().toString();
        this.textInputLayout = textInputLayout;
        messageBuilders = new ArrayList<>();
        return this;
    }


    public MessageBuilder checkEmpty() {
        return new MessageBuilder(Type.EMPTY);
    }

    public MessageBuilder checkEmptyWithoutTrim() {
        return new MessageBuilder(Type.EMPTY_WITHOUT_TRIM);
    }

    public MessageBuilder checkValidEmail() {
        return new MessageBuilder(Type.EMAIL);
    }

    public MessageBuilder checkMaxDigits(int max) {

        return new MessageBuilder(Type.MAX_LENGTH, max);
    }

    public MessageBuilder checkMinDigits(int min) {
        return new MessageBuilder(Type.MIN_LENGTH, min);
    }

    public MessageBuilder matchString(String s) {
        return new MessageBuilder(Type.MATCH, s);
    }

    public MessageBuilder matchPatter(String patter) {
        return new MessageBuilder(Type.PATTERN_MATCH, patter);
    }

    public boolean check() throws ApplicationException {

        for (MessageBuilder builder : messageBuilders) {

            try {
                switch (builder.getType()) {

                    case EMPTY:
                        isEmpty(subject, builder.getMessage(), true);
                        break;
                    case EMPTY_WITHOUT_TRIM:
                        isEmpty(subject, builder.getMessage(), false);
                        break;
                    case EMAIL:
                        isValidEmail(subject, builder.getMessage());
                        break;
                    case MAX_LENGTH:
                        checkMaxDigits(subject, builder.getValidLength(), builder.getMessage());
                        break;
                    case MIN_LENGTH:
                        checkMinDigits(subject, builder.getValidLength(), builder.getMessage());
                        break;
                    case MATCH:
                        match(subject, builder.getMatch(), builder.getMessage());
                        break;
                    case PATTERN_MATCH:
                        matchPatter(subject, builder.getMatch(), builder.getMessage());
                        break;
                }

                if (textInputLayout != null) {
                    textInputLayout.setError(null);
                    Log.e("Validation", "No error");
                }

            } catch (ApplicationException e) {

                editText.clearFocus();
                editText.requestFocus();

              //  new Handler().postDelayed(() -> showKeyboard(editText),500);

                if (textInputLayout != null) {
                    Log.e("Validation", " Error ");
                    if (!textInputLayout.isErrorEnabled())
                        textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(e.getLocalizedMessage());
                }
                e.setType(ApplicationException.Type.VALIDATION);
                throw e;
            }


        }
        return true;
    }

    void isEmpty(String subject, String errorMessage, boolean byTrimming)
            throws ApplicationException {

        if (subject == null)
            throw new ApplicationException(errorMessage);

        if (byTrimming)
            subject = subject.trim();

        if (subject.isEmpty())
            throw new ApplicationException(errorMessage);
    }

    void isValidEmail(String subject, String errorMessage)
            throws ApplicationException {
        if (!subject.matches(Patterns.EMAIL_ADDRESS.pattern()))
            throw new ApplicationException(errorMessage);
    }

    void checkMinDigits(String subject, int min, String errorMessage)
            throws ApplicationException {
        if (subject.length() < min)
            throw new ApplicationException(errorMessage);
    }

    void checkMaxDigits(String subject, int max, String errorMessage)
            throws ApplicationException {
        if (subject.length() > max)
            throw new ApplicationException(errorMessage);
    }

    void match(String subject, String toMatch, String errorMessage)
            throws ApplicationException {
        if (toMatch == null || !subject.equals(toMatch))
            throw new ApplicationException(errorMessage);
    }

    void matchPatter(String subject, String pattern, String errorMessage)
            throws ApplicationException {

        if (subject == null || !subject.matches(pattern))
            throw new ApplicationException(errorMessage);
    }


    public class MessageBuilder {
        private final Type type;
        private String message;

        private int validLength;
        private String match;

        public MessageBuilder(Type type) {
            this.type = type;
        }

        public MessageBuilder(Type type, int validLength) {
            this.type = type;
            this.validLength = validLength;
        }

        public MessageBuilder(Type type, String match) {
            this.type = type;
            this.match = match;
        }


        public Validator errorMessage(String message) {
            this.message = message;
            messageBuilders.add(this);
            return Validator.this;
        }

        public Validator errorMessage(@StringRes int message) {
            this.message = context.getResources().getString(message);
            messageBuilders.add(this);
            return Validator.this;
        }

        public Type getType() {
            return type;
        }

        public String getMessage() {
            return message;
        }

        public int getValidLength() {
            return validLength;
        }

        public String getMatch() {
            return match;
        }

        public void setMatch(String match) {
            this.match = match;
        }
    }

    private enum Type {
        EMPTY, EMAIL, MIN_LENGTH, MAX_LENGTH, MATCH, PATTERN_MATCH, EMPTY_WITHOUT_TRIM
    }

    private void showKeyboard(View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }
}
