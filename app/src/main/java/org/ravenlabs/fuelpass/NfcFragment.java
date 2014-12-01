package org.ravenlabs.fuelpass;

import android.app.Activity;
import android.app.DialogFragment;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ravenlabs.fuelpass.nfc.TagReader;


public class NfcFragment extends DialogFragment implements TagReader.IReaderCallBack {
    public static final String TAG = "NfcFragment";
    private static final int ReaderFlags = NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_NFC_B | NfcAdapter.FLAG_READER_NFC_F | NfcAdapter.FLAG_READER_NFC_V | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;

    private TagReader mReader;
    private NfcAdapter mNfc;
    private OnFragmentInteractionListener mListener;

    public static NfcFragment newInstance() {
        NfcFragment fragment = new NfcFragment();

        return fragment;
    }

    public NfcFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nfc, container, false);

        mReader = new TagReader(getActivity(), this);

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        mReader.DisableReaderMode();
    }

    @Override
    public void onResume() {
        super.onResume();

        mReader.Initialize();
        mReader.EnableReaderMode();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void OnCloseDialog() {
        dismiss();
    }

    public void OnToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}
