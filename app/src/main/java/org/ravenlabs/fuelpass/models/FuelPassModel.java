package org.ravenlabs.fuelpass.models;

import android.content.Context;
import android.os.Bundle;
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
import java.util.Calendar;


public class FuelPassModel implements Parcelable {
    public static final String TAG = "FuelPassModel";

    private static final String FilePath = "fuelpass.json";
    private static FuelPassModel _Instance;

    private BigDecimal mFuelPrice;
    private BigDecimal mDiscount;
    private BigDecimal mMoneyAmount;
    private FuelType mFuelType;
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

    public String getNfcTag() {
        return mNfcTag;
    }

    public void setNfcTag(String mNfcTag) {
        this.mNfcTag = mNfcTag;
    }

    public FuelType getFuelType() {
        return mFuelType;
    }

    public void setFuelType(FuelType fuel) {
        mFuelType = fuel;

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);
        int hour = c.get(Calendar.HOUR_OF_DAY);

        if (day != Calendar.SUNDAY && hour >= 7 && hour <= 18) {
            if (fuel == FuelType.A95 || fuel == FuelType.Diesel)
                this.setDiscount(new BigDecimal("0.07", MathContext.DECIMAL32));
            if (fuel == FuelType.A98)
                this.setDiscount(new BigDecimal("0.10", MathContext.DECIMAL32));
            if (fuel == FuelType.LPG)
                this.setDiscount(new BigDecimal("0.07", MathContext.DECIMAL32));
        } else {
            if (fuel == FuelType.A95 || fuel == FuelType.Diesel)
                this.setDiscount(new BigDecimal("0.09", MathContext.DECIMAL32));
            if (fuel == FuelType.A98)
                this.setDiscount(new BigDecimal("0.10", MathContext.DECIMAL32));
            if (fuel == FuelType.LPG)
                this.setDiscount(new BigDecimal("0.08", MathContext.DECIMAL32));
        }
    }

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
        parcel.writeInt(mFuelType.ordinal());
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
        this.mFuelType = FuelType.values()[in.readInt()];
        this.mNfcTag = in.readString();
    }

    public FuelPassModel() {
        this.mFuelPrice = new BigDecimal("2.30", MathContext.DECIMAL32);
        this.mDiscount = new BigDecimal("0.00", MathContext.DECIMAL32);
        this.mMoneyAmount = new BigDecimal("0.00", MathContext.DECIMAL32);
        this.mFuelType = FuelType.A98;
        this.mNfcTag = null;
    }


    public static FuelPassModel GetInstance(Context c) {
        return FuelPassModel.GetInstance(c, null);
    }

    public static FuelPassModel GetInstance(Context c, Bundle b) {
        if (_Instance == null) {
            if (b != null && b.containsKey(TAG)) {
                _Instance = (FuelPassModel) b.getParcelable(TAG);
            }
            if (_Instance == null) {
                try {
                    InputStream io = c.openFileInput(FilePath);
                    InputStreamReader reader = new InputStreamReader(io);
                    Gson gson = new Gson();
                    _Instance = gson.fromJson(reader, FuelPassModel.class);
                    reader.close();
                } catch (Exception ex) {
                    Log.d(TAG, ex.toString());
                    return new FuelPassModel();
                }
            }

            if (_Instance == null)
                _Instance = new FuelPassModel();
        }

        return _Instance;
    }


    public static boolean SaveInstance(Context c) {
        return FuelPassModel.SaveInstance(c, null);
    }

    public static boolean SaveInstance(Context c, Bundle b) {
        if (b != null) {
            b.putParcelable(TAG, _Instance);
        }
        try {
            OutputStream io = c.openFileOutput(FilePath, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(io);
            Gson gson = new Gson();
            String json = gson.toJson(_Instance);
            writer.write(json);
            writer.flush();
            writer.close();
            return true;
        } catch (Exception ex) {
            Log.d(TAG, ex.toString());
            return false;
        }
    }
}
