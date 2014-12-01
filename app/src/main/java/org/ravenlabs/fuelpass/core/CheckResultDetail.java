package org.ravenlabs.fuelpass.core;

/**
 * Created by Ravenheart on 1.12.2014 г..
 */
public class CheckResultDetail {
    public CheckResultType Type;
    public String Message;
    public String Property;

    public CheckResultDetail(CheckResultType type, String message, String property)
    {
        this.Type = type;
        this.Message = message;
        this.Property=property;
    }
}
