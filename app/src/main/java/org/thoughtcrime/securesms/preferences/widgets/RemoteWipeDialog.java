package org.thoughtcrime.securesms.preferences.widgets;

import android.content.Context;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.components.SwitchPreferenceCompat;
import org.thoughtcrime.securesms.util.BackupUtil;
import org.thoughtcrime.securesms.util.TextSecurePreferences;

public class RemoteWipeDialog {

    public static void showEnableBackupDialog(@NonNull Context context, @NonNull SwitchPreferenceCompat preference) {
        String[]    password = BackupUtil.generateBackupPassphrase();
        AlertDialog dialog   = new AlertDialog.Builder(context)
                .setTitle(R.string.preferences_app_protection__passphrase_dialog_title)
                .setView(R.layout.remote_wipe_dialog)
                .setPositiveButton(R.string.preferences_app_protection__set_passphrase, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        dialog.setOnShowListener(created -> {
            Button button = ((AlertDialog) created).getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                EditText passphraseEditText = dialog.findViewById(R.id.wipe_passphrase);
                CheckBox confirmationCheckBox = dialog.findViewById(R.id.confirmation_check);
                if (confirmationCheckBox.isChecked()) {
                    TextSecurePreferences.setRemoteWipePassphrase(context, passphraseEditText.getText().toString());

                    preference.setChecked(true);
                    created.dismiss();
                } else {
                    Toast.makeText(context, R.string.BackupDialog_please_acknowledge_your_understanding_by_marking_the_confirmation_check_box, Toast.LENGTH_LONG).show();
                }
            });
        });

        dialog.show();
    }
}
