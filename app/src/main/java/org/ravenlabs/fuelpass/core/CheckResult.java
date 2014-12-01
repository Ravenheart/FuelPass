package org.ravenlabs.fuelpass.core;

import java.util.ArrayList;

/**
 * Created by Ravenheart on 1.12.2014 г..
 */
public class CheckResult<TResult> {
    public TResult Result;
    public ArrayList<CheckResultDetail> Details;

    public CheckResult() {
        this.Details = new ArrayList<CheckResultDetail>();
    }

    public boolean HasErrors() {
        for (CheckResultDetail d : this.Details) {
            if (d.Type == CheckResultType.Error)
                return true;
        }

        return false;
    }

    public boolean HasWarnings() {
        for (CheckResultDetail d : this.Details) {
            if (d.Type == CheckResultType.Warning)
                return true;
        }

        return false;
    }

    public String GetErrors() {
        StringBuilder bld = new StringBuilder();
        for (CheckResultDetail d : this.Details) {
            bld.append(d.Message);
            bld.append("\n");
        }

        bld.deleteCharAt(bld.length());
        return bld.toString();
    }


    public void AddError(String message) {
        this.AddError(message, "");
    }

    public void AddError(String message, String property) {
        CheckResultDetail d = new CheckResultDetail(CheckResultType.Error, message, property);
        this.Details.add(d);
    }


    public void AddWarning(String message) {
        this.AddWarning(message, "");
    }

    public void AddWarning(String message, String property) {
        CheckResultDetail d = new CheckResultDetail(CheckResultType.Warning, message, property);
        this.Details.add(d);
    }
}
