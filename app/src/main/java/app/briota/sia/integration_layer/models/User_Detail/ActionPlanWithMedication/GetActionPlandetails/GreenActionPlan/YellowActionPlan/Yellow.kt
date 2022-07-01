package com.example.briota.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan

import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.YellowActionPlan.YDaily
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.YellowActionPlan.YRescue
import app.briota.sia.integration_layer.models.User_Detail.ActionPlanWithMedication.GetActionPlandetails.GreenActionPlan.YellowActionPlan.YSteroid
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Yellow1 {

    @SerializedName("daily")
    @Expose
    var yellowDaily : ArrayList<YDaily>? = null

    @SerializedName("rescue")
    @Expose
    var yellowRescue : ArrayList<YRescue>? = null

    @SerializedName("steroid")
    @Expose
    var yellowSteroid : ArrayList<YSteroid>? = null

    }