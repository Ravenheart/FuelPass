package org.ravenlabs.fuelpass.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.MathContext;


public class FuelPassModel implements Parcelable {
    public static final String TAG = "FuelPassModel";

    private static final String FilePath = "fuelpass.json";
    private static FuelPassModel _Instance;

    private BigDecimal mFuelPrice;
    private BigDecimal mDiscount;
    private BigDecimal mMoneyAmount;
    private String mNfcTag;

    public BigDecimal getFuelPrice() {
        return mFuelPrice;
    }

    public void setFuelPrice(BigDecimal mFuelPrice) {
        this.mFuelPrice = mFuelPrice;
    }

    public BigDecimal getDiscount() {
        return mDiscount;
    }

    public void setDiscount(BigDecimal mDiscount) {
        this.mDiscount = mDiscount;
    }

    public BigDecimal getMoneyAmount() {
        return mMoneyAmount;
    }

    public void setMoneyAmount(BigDecimal mAmount) {
        this.mMoneyAmount = mAmount;
    }

    public String getNfcTag() { return mNfcTag; }
    public void setNfcTag(String mNfcTag){this.mNfcTag = mNfcTag;}

    public BigDecimal getResult() {
        BigDecimal fuelPrice = mFuelPrice.subtract(mDiscount, MathContext.DECIMAL32);
        return mMoneyAmount.divide(fuelPrice, MathContext.DECIMAL32);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(mFuelPrice);
        parcel.writeSerializable(mDiscount);
        parcel.writeSerializable(mMoneyAmount);
        parcel.writeString(mNfcTag);
    }

    public static final Parcelable.Creator<FuelPassModel> CREATOR
            = new Parcelable.Creator<FuelPassModel>() {
        public FuelPassModel createFromParcel(Parcel in) {
            return new FuelPassModel(in);
        }

        public FuelPassModel[] newArray(int size) {
            return new FuelPassModel[size];
        }
    };

    private FuelPassModel(Parcel in) {
        this.mFuelPrice = (BigDecimal) in.readSerializable();
        this.mDiscount = (BigDecimal) in.readSerializable();
        this.mMoneyAmount = (BigDecimal) in.readSerializable();
        this.mNfcTag = in.readString();
    }

    public FuelPassModel() {
        this.mFuelPrice = new BigDecimal("2.30", MathContext.DECIMAL32);
        this.mDiscount = new BigDecimal("0.00", MathContext.DECIMAL32);
        this.mMoneyAmount = new BigDecimal("0.00", MathContext.DECIMAL32);
        this.mNfcTag = null;
    }



    public static FuelPassModel GetInstance(Context c)
    {
        if(_Instance == null) {
            try {
                InputStream io = c.openFileInput(FilePath);
                InputStreamReader reader = new InputStreamReader(io);
                Gson gson = new Gson();
                _Instance = gson.fromJson(reader,FuelPassModel.class);
                if(_Instance == null)
                    _Instance = new FuelPassModel();
                reader.close();
            }
            catch (Exception ex)
            {
                Log.d(TAG,ex.toString());
                return new FuelPassModel();
            }
        }

        return _Instance;
    }

    public static boolean SaveInstance(Context c)
    {
        try
        {
            OutputStream io = c.openFileOutput(FilePath,Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(io);
            Gson gson = new Gson();
            String json = gson.toJson(_Instance);
            writer.write(json);
            writer.flush();
            writer.close();
            return true;
        }
        catch (Exception ex)
        {
            Log.d(TAG,ex.toString());
            return false;
        }
    }
}
