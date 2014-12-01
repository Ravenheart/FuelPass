package org.ravenlabs.fuelpass.nfc;

import android.app.Activity;
import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.NfcA;
import android.util.Log;

import org.ravenlabs.fuelpass.core.NumberUtils;
import org.ravenlabs.fuelpass.core.StringUtils;

import java.lang.ref.WeakReference;

public class TagReader implements NfcAdapter.ReaderCallback {
    public static final String TAG = "TagReader";
    private static final int ReaderFlags = NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_NFC_B | NfcAdapter.FLAG_READER_NFC_F | NfcAdapter.FLAG_READER_NFC_V | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;

    private Activity mContext;
    private WeakReference<IReaderCallBack> mCallback;
    private NfcAdapter mNfc;

    public TagReader(Activity c, IReaderCallBack callback) {
        this.mContext = c;
        this.mCallback = new WeakReference<IReaderCallBack>(callback);
    }

    public boolean Initialize()
    {
        if(mNfc == null) {
            mNfc = NfcAdapter.getDefaultAdapter(mContext);
            if (mNfc == null) {
                mCallback.get().OnToast("No NFC hardware!");
                mCallback.get().OnCloseDialog();
                return false;
            }
            if (!mNfc.isEnabled()) {
                mCallback.get().OnToast("NFC is disabled!");
                mCallback.get().OnCloseDialog();
                return false;
            }
        }

        return true;
    }

    public void EnableReaderMode()
    {
        if(mNfc != null)
        {
            Log.i(TAG,"EnableReaderMode");
            mNfc.enableReaderMode(mContext, this, ReaderFlags, null);
        }
    }

    public void DisableReaderMode()
    {
        if(mNfc != null)
        {
            Log.i(TAG,"DisableReaderMode");
            mNfc.disableReaderMode(mContext);
        }
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        Log.i(TAG, tag.toString());

        byte[] id = tag.getId();
        String[] techs = tag.getTechList();
        Log.d(TAG, NumberUtils.ByteArrayToHexString(id));
        Log.d(TAG, StringUtils.Join(", ", techs));

        mCallback.get().OnCloseDialog();
    }

    public interface IReaderCallBack {
        public void OnCloseDialog();
        public void OnToast(String message);
    }
}
