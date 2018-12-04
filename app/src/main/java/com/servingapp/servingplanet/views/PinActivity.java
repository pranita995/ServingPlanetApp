package com.servingapp.servingplanet.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.servingapp.servingplanet.R;
import com.servingapp.servingplanet.utils.SharedPreferenceUtils;
import com.servingapp.servingplanet.utils.Utils;
import com.servingapp.servingplanet.utils.ViewUtils;

public class PinActivity extends AppCompatActivity {

    private TextView m_textMessageUserId, m_textPageTitle;
    private Button m_buttonContinue;
    private LinearLayout m_layoutPin, m_layoutConfirmPin;
    private RelativeLayout m_layoutParent;
    private PinEntryEditText m_editPin, m_editConfirmPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (Utils.checkInternetConnection(PinActivity.this)) {
            initializeViews();
            setupData();
        } else {
            ViewUtils utils = new ViewUtils();
            utils.showAlert(PinActivity.this);
        }
    }

    private void setupData() {

        if (SharedPreferenceUtils.getSecurityPin(PinActivity.this) == null ||
                SharedPreferenceUtils.getSecurityPin(PinActivity.this).isEmpty()) {
            m_textPageTitle.setText(getString(R.string.set_pin));
            m_layoutConfirmPin.setVisibility(View.VISIBLE);
            m_editPin.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        } else {
            m_textPageTitle.setText(getString(R.string.enter_pin_code));
            m_editPin.setImeOptions(EditorInfo.IME_ACTION_DONE);
            m_layoutConfirmPin.setVisibility(View.GONE);
        }

        m_buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pin = m_editPin.getText().toString().trim();
                if (SharedPreferenceUtils.getSecurityPin(PinActivity.this) == null) {
                    if (validatePin() && validateConfirmPin()) {
                        pin = m_editPin.getText().toString().trim();
                        SharedPreferenceUtils.storeSecurityPin(PinActivity.this, pin);
                        startActivity(new Intent(PinActivity.this, DashboardActivity.class));
                        finish();
                    }
                } else {
                    if (validatePin() && pin.equals(SharedPreferenceUtils.getSecurityPin(PinActivity.this))) {
                        SharedPreferenceUtils.storeSecurityPin(PinActivity.this, pin);
                        startActivity(new Intent(PinActivity.this, DashboardActivity.class));
                        finish();
                    } else {
                        Toast.makeText(PinActivity.this, getString(R.string.invalid_pin),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validateConfirmPin() {
        String pin = m_editPin.getText().toString().trim();
        String confirmPin = m_editConfirmPin.getText().toString().trim();
        if (confirmPin.isEmpty() || confirmPin.length() < 4 || !confirmPin.equals(pin)) {
            Toast.makeText(PinActivity.this, getString(R.string.invalid_confirm_pin), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePin() {
        String pin = m_editPin.getText().toString().trim();
        if (pin.isEmpty() || pin.length() < 6) {
            return false;
        }
        return true;
    }

    private void initializeViews() {
        m_textMessageUserId = findViewById(R.id.text_pin_message);
        m_textPageTitle = findViewById(R.id.text_pin_title);
        m_editPin = findViewById(R.id.edit_pin_entry);
        m_editConfirmPin = findViewById(R.id.edit_confirm_pin_entry);
        m_buttonContinue = findViewById(R.id.button_reset_pin);
        m_layoutConfirmPin = findViewById(R.id.layout_confirm_pin);
        m_layoutPin = findViewById(R.id.layout_pin);
        m_layoutParent = findViewById(R.id.layout_parent);
    }
}
