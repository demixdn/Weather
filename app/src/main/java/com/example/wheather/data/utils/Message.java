package com.example.wheather.data.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheather.R;


/**
 * Created by Aleksandr on 30.04.2015 in UkrainianFood.
 */
public class Message {
    public static void Toast(Context context, String msg)
    {
        if(msg!=null && !msg.isEmpty())
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static void Toast(Context context, int msg)
    {
        if(msg!=0)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static void ToastL(Context context, int msg)
    {
        if(msg!=0)
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    private static Toast ToastCustom(Context context, String msg, int viewId)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        TextView toastView = (TextView)inflater.inflate(viewId, null);
        toastView.setText(msg);
        Toast toast = new Toast(context);
        toast.setView(toastView);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 40);
        toast.setDuration(Toast.LENGTH_SHORT);
        return toast;
    }
    public static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.loading));
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static AlertDialog Alert(Context context, String title, String text, DialogInterface.OnClickListener listener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(android.R.string.ok, listener);
        builder.setTitle(title);
        builder.setMessage(text);
        return builder.create();
    }

    public static AlertDialog AlertClose(Activity activity)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setNegativeButton(android.R.string.no, null);
        builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
            activity.finish();
        });
        builder.setMessage(R.string.dialog_exit_text);
        return builder.create();
    }

    public static AlertDialog Alert(Context context, String title, String text)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setTitle(title);
        builder.setMessage(text);
        return builder.create();
    }
}
