package org.ravenlabs.fuelpass;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.ravenlabs.fuelpass.core.NumberUtils;
import org.ravenlabs.fuelpass.core.UIUtils;
import org.ravenlabs.fuelpass.models.FuelPassModel;
import org.w3c.dom.Text;

import java.math.BigDecimal;


public class FuelPassFragment extends Fragment {

    private static final String VIEW_MODEL = "VIEW_MODEL";
    private FuelPassModel mViewModel;
    private EditText mFuelPrice;
    private EditText mDiscount;
    private EditText mAmount;
    private ImageButton mRefresh;
    private TextView mResult;

    private OnFragmentInteractionListener mListener;

    public static FuelPassFragment newInstance() {
        FuelPassFragment fragment = new FuelPassFragment();

        return fragment;
    }

    public FuelPassFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = FuelPassModel.GetInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fuel_pass, container, false);

        mFuelPrice = (EditText) v.findViewById(R.id.txt_fuel_price);
        mDiscount = (EditText) v.findViewById(R.id.txt_discount);
        mAmount = (EditText) v.findViewById(R.id.txt_amount);
        mRefresh = (ImageButton) v.findViewById(R.id.btn_refresh);
        mResult = (TextView) v.findViewById(R.id.txt_result);

        SetupFragment(v);
        BindFuelPrice();
        BindDiscount();
        BindAmount();
        BindRefresh();

        return v;
    }

    private void SetupFragment(View fragment)
    {
        fragment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                UIUtils.hideSoftKeyboard(getActivity());
                return false;
            }
        });
    }

    private void BindFuelPrice() {
        mFuelPrice.setText(mViewModel.getFuelPrice().toString());
        mFuelPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String value = mFuelPrice.getText().toString();
                if (NumberUtils.IsNumber(value))
                    mViewModel.setFuelPrice(new BigDecimal(value));
            }
        });
    }

    private void BindDiscount() {
        mDiscount.setText(mViewModel.getDiscount().toString());
        mDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String value = mDiscount.getText().toString();
                if (NumberUtils.IsNumber(value))
                    mViewModel.setDiscount(new BigDecimal(value));
            }
        });
    }

    private void BindAmount() {
        mAmount.setText(mViewModel.getMoneyAmount().toString());
        mAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String value = mAmount.getText().toString();
                if (NumberUtils.IsNumber(value))
                    mViewModel.setMoneyAmount(new BigDecimal(value));
            }
        });
    }

    private void BindRefresh()
    {
        mResult.setText(mViewModel.getResult().toString());
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResult.setText(mViewModel.getResult().toString());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(VIEW_MODEL, mViewModel);
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onPause() {
        super.onPause();
        FuelPassModel.SaveInstance(getActivity());
    }
}
