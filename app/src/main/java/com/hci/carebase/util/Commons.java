package com.hci.carebase.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class Commons {

    public static void sendFeedback(
            Context callingActivity,
            String emailAddress,
            String subject,
            String message) {
        Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", emailAddress, null));
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT,  message);

        try {
            callingActivity.startActivity(email);
        } catch(ActivityNotFoundException ex) {
            try {
                /* If no mailing client, send to playstore */
                final String appPackageName = callingActivity.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    callingActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    /*Fails if GPlay Not Installed */
                    callingActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            } catch (Exception e) {
                Toast.makeText(callingActivity, "Can't send feedback at this time", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
